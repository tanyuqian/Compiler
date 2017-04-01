package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;


/**
 * Created by Songyu on 16/4/27.
 */
public class NullOperation extends Instruction {
    public NullOperation() {
        this.nextInstruction = null;
        this.label = null;
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips();
    }

    @Override
    public String toMips() {
        LinkedList<String> mipsInstructions = new LinkedList<>();
        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }

//        if (this.nextInstruction == null) {
//            mipsInstructions.add("\tnop");
//        }

        String str = "";
        for (String statement : mipsInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
        }
        if (str.length() == 0) {
            str = " ";
        }
        return str.substring(0, str.length() - 1);
    }
}
