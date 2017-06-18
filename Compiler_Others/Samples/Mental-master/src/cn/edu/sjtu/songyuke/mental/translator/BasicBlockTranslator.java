package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.Instruction;

/**
 * Created by Songyu on 16/5/4.
 */
public class BasicBlockTranslator extends MIPSTranslator {
    public BasicBlockTranslator() {
        super();
    }
    public void translate(Instruction instruction) {
//        this.mipsStatement.add("#Basic Block " + this.toString());
        for (; instruction != null; instruction = instruction.nextInstruction) {
            String resultStatement = instruction.toMips(this.machine);
            if (resultStatement.length() > 0) {
                this.mipsStatement.add(resultStatement);
            }
        }
        this.mipsStatement.add(this.machine.storeAndCleanMachine());
    }
}
