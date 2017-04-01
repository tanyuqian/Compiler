package cn.edu.sjtu.songyuke.mental.translator;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/30.
 */
public class MIPSProgram {
    public MIPSStaticData staticData;
    public MIPSGlobalInitialize globalInitialize;
    public LinkedList<MIPSFunctions> functions;
    public LinkedList<String> beginMainLabel;
    public LinkedList<String> endMainLabel;
    public MIPSProgram() {
        this.staticData = new MIPSStaticData();
        this.globalInitialize = new MIPSGlobalInitialize();
        this.functions = new LinkedList<>();

        this.beginMainLabel = new LinkedList<>();
        this.beginMainLabel.add("\t.data");
        this.beginMainLabel.add("_buffer:");
        this.beginMainLabel.add("\t.word 0");
        this.beginMainLabel.add("\t.text");
        this.beginMainLabel.add("_buffer_init:");
        this.beginMainLabel.add("\tli $a0, 256");
        this.beginMainLabel.add("\tli $v0, 9");
        this.beginMainLabel.add("\tsyscall");
        this.beginMainLabel.add("\tsw $v0, _buffer");
        this.beginMainLabel.add("\tjr $ra");
        this.beginMainLabel.add("\t.text");
        this.beginMainLabel.add("main:");
        this.beginMainLabel.add("\tjal _buffer_init");
        this.beginMainLabel.add("\tadd $fp, $zero, $sp");

        this.endMainLabel = new LinkedList<>();
        this.endMainLabel.add("\tjal _func_main");
        this.endMainLabel.add("\tmove $a0, $v0");
        this.endMainLabel.add("\tli $v0, 17");
        this.endMainLabel.add("\tsyscall");
    }
    public String toString() {
        String program = "";
        for (String statement : this.staticData.mipsStatements) {
            program += statement + "\n";
        }
        for (String statement : this.beginMainLabel) {
            if (statement.length() > 0) {
                program += statement + "\n";
            }
        }
        for (String statement : this.globalInitialize.mipsStatement) {
            if (statement.length() > 0) {
                program += statement + "\n";
            }
        }
        for (String statement : this.endMainLabel) {
            program += statement + "\n";
        }

        for (MIPSFunctions function : this.functions) {
            for (String statement: function.mipsStatement) {
                if (statement.length() > 0) {
                    program += statement + "\n";
                }
            }
            program += "\n";
        }

        return program;
    }
}
