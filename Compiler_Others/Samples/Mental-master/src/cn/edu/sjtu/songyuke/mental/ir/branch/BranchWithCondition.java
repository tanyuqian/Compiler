package cn.edu.sjtu.songyuke.mental.ir.branch;

import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/5/2.
 */
public class BranchWithCondition extends Branch {
    public DataValue condition;

    public String toMips(MIPSMachine mipsMachine, String operand) {
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }
        this.condition.refCount--;
        if (this.condition.registerName == -1) {
            mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
            mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.condition));
        } else {
            mipsMachine.refreshRegister(this.condition.registerName);
        }
        int registerName = this.condition.registerName;
        mipsInstructions.add(mipsMachine.storeAndCleanMachine());
        mipsInstructions.add(
                String.format("\t%s $%d, %s", operand, registerName, this.gotoLabel.toString())
        );

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
