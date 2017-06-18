package cn.edu.sjtu.songyuke.mental.ir.branch;

import cn.edu.sjtu.songyuke.mental.ir.data.DataIntLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.ir.label.IRLabel;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/5/5.
 */
public class BranchCompare extends Branch {
    public DataValue lhs, rhs;
    public BranchCompare() {
        this.lhs = null;
        this.rhs = null;
    }
    public BranchCompare(DataValue lhs, DataValue rhs, IRLabel gotoLabel) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.gotoLabel = gotoLabel;
    }

    public String toMips(MIPSMachine mipsMachine, String operand) {
        LinkedList<String> mipsInstructions = new LinkedList<>();
        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }

        this.lhs.refCount--;
        this.rhs.refCount--;

        if (this.lhs.registerName == -1) {
            mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
            mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.lhs));
        } else {
            mipsMachine.refreshRegister(this.lhs.registerName);
        }

        if (this.rhs instanceof DataIntLiteral) {
            String lhsRegister = this.lhs.toRegister();
            mipsInstructions.add(mipsMachine.storeAndCleanMachine());
            mipsInstructions.add(
                    String.format("\t%s %s, %d, %s", operand, lhsRegister, ((DataIntLiteral) this.rhs).literal, this.gotoLabel.toString())
            );
        } else {
            if (this.rhs.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.rhs));
            } else {
                mipsMachine.refreshRegister(this.rhs.registerName);
            }

            String lhsRegister = this.lhs.toRegister();
            String rhsRegister = this.rhs.toRegister();
            mipsInstructions.add(mipsMachine.storeAndCleanMachine());
            mipsInstructions.add(
                    String.format("\t%s %s, %s, %s", operand, lhsRegister, rhsRegister, this.gotoLabel.toString())
            );
        }

        String str = "";
        for (String statement : mipsInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
        }
        return str.substring(0, str.length() - 1);
    }
}
