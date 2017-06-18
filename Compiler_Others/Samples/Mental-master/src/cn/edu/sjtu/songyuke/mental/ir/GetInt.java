package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/27.
 */
public class GetInt extends SystemCall {
    public Data res;
    public GetInt() {
        this.variant = 5;
        this.res = new DataValue();
    }
    public GetInt(Data res) {
        this.res = res;
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }

        mipsInstructions.add(mipsMachine.storeAndCleanMachine());
        mipsInstructions.add("\tli $v0, 5");
        mipsInstructions.add("\tsyscall");
        mipsInstructions.add(
                String.format("\tsw $v0, %s", this.res.toAddress())
        );
        String str = "";
        for (String statement : mipsInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
        }
        return str.substring(0, str.length() - 1);
    }

    @Override
    public String toMips() {
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }

        mipsInstructions.add("\tli $v0, 5");
        mipsInstructions.add("\tsyscall");
        mipsInstructions.add(
                String.format("\tsw $v0, %s", this.res.toAddress())
        );
        String str = "";
        for (String statement : mipsInstructions) {
            str += statement + "\n";
        }
        return str.substring(0, str.length() - 1);
    }
}
