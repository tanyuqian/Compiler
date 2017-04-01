package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ir.label.IRLabel;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/19.
 */
public abstract class Instruction {
    public Instruction nextInstruction = null;
    public IRLabel label = null;
    public Instruction() {
        this.nextInstruction = null;
        this.label = null;
    }

    public String toMips(MIPSMachine mipsMachine) {
        // would never be called.
        throw new RuntimeException(this.toString());
    }

    public String toMips() {
        // would never be called.
        throw new RuntimeException(this.toString());
    }
}
