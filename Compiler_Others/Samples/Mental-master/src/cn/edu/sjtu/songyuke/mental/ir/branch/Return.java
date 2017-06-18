package cn.edu.sjtu.songyuke.mental.ir.branch;

import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.ir.label.IRLabel;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/25.
 */
public class Return extends Branch {
    public DataValue returnValue;
    public Return() {
        this.returnValue = null;
    }
    public Return(IRLabel gotoLabel, DataValue returnValue) {
        super(gotoLabel);
        this.returnValue = returnValue;
        if (this.returnValue != null) {
            this.returnValue.refCount++;
        }
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        if (this.nextInstruction != null) {
            throw new RuntimeException("basic block split error");
        }
        LinkedList<String> mipsInstructions = new LinkedList<>();
        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }
        if (this.returnValue != null) {
            if (this.returnValue.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.returnValue));
            }
            mipsInstructions.add(
                    String.format("\tmove $v0, %s", this.returnValue.toRegister())
            );
        }
        mipsInstructions.add(mipsMachine.storeAndCleanMachine());
        mipsInstructions.add(
                String.format("\tb %s", this.gotoLabel.toString())
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
