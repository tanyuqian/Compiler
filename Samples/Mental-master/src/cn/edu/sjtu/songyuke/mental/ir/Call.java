package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataIntLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.ir.label.IRLabelFunction;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/25.
 */
public class Call extends Instruction {
    public LinkedList<DataValue> parameters;
    public IRLabelFunction functionName;
    public Data res;
    public Call() {
        this.parameters = new LinkedList<>();
        this.functionName = null;
        this.res = null;
    }
    public Call(IRLabelFunction functionName) {
        this.parameters = new LinkedList<>();
        this.functionName = functionName;
        this.res = new DataValue();
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        if (this.nextInstruction != null) {
            throw new RuntimeException("basic block split error");
        }
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label + ":");
        }

        for (int i = 0, count = this.parameters.size(); i < count; ++i) {
            DataValue thisParameter = this.parameters.get(i);
            thisParameter.refCount--;
            if (thisParameter.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(thisParameter));
            }

            mipsInstructions.add(
                    String.format("\tsw %s, %d($sp)", thisParameter.toRegister(), -4 * (i + 1))
            );
        }
        mipsInstructions.add(mipsMachine.storeAndCleanMachine());
        mipsInstructions.add(
                String.format("jal %s", this.functionName.toString())
        );

        if (this.res != null) {
            mipsInstructions.add(
                    String.format("\tsw $v0, %s", this.res.toAddress())
            );
        }

        String str = "";
        for (String statement : mipsInstructions) {
            str += statement + '\n';
        }
        return str.substring(0, str.length() - 1);
    }

    @Override
    public String toMips() {
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label + ":");
        }

        for (int i = 0, count = this.parameters.size(); i < count; ++i) {
            DataValue thisParameter = this.parameters.get(i);

            if (thisParameter instanceof DataIntLiteral) {
                mipsInstructions.add(
                        String.format("\tli $t0, %d", ((DataIntLiteral) thisParameter).literal)
                );
            } else {
                mipsInstructions.add(
                        String.format("\tlw $t0, %s", thisParameter.toAddress())
                );
            }

            mipsInstructions.add(
                    String.format("\tsw $t0, %d($sp)", -4 * (i + 1))
            );
        }

        mipsInstructions.add(
                String.format("jal %s", this.functionName.toString())
        );

        if (this.res != null) {
            mipsInstructions.add(
                    String.format("\tsw $v0, %s", this.res.toAddress())
            );
        }

        String str = "";
        for (String statement : mipsInstructions) {
            str += statement + '\n';
        }
        return str.substring(0, str.length() - 1);
    }
}
