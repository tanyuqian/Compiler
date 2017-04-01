package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.*;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/29.
 */
public class MIPSTranslator {
    public MIPSMachine machine;
    public LinkedList<String> mipsStatement;
    public MIPSTranslator() {
        this.machine = new MIPSMachine();
        this.mipsStatement = new LinkedList<>();
    }

    public void translate(Instruction instruction) {
        for (; instruction != null; instruction = instruction.nextInstruction) {
            this.mipsStatement.add(instruction.toMips());
        }
    }
}
