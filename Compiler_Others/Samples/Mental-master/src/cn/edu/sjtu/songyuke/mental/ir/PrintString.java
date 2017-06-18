package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataStringLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/27.
 */
public class PrintString extends SystemCall {
    public Data stringLocation;
    public PrintString() {
        this.variant = 4;
        this.stringLocation = null;
    }
    public PrintString(Data stringLocation) {
        this.variant = 4;
        this.stringLocation = stringLocation;
        if (this.stringLocation instanceof DataValue) {
            this.stringLocation.refCount++;
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
        if (this.stringLocation instanceof DataValue) {
            this.stringLocation.refCount--;
        }
        if (this.stringLocation.registerName == -1) {
            mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
            mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.stringLocation));
        } else {
            mipsMachine.refreshRegister(this.stringLocation.registerName);
        }

        mipsInstructions.add("\tli $v0, 4");
        mipsInstructions.add(
                String.format("\tmove $a0, %s", this.stringLocation.toRegister())
        );
        mipsInstructions.add(mipsMachine.storeAndCleanMachine());
        mipsInstructions.add("\tsyscall");

        String str = "";
        for (String statement : mipsInstructions) {
            str += statement + "\n";
        }
        return str.substring(0, str.length() - 1);
    }

    // for cisc code generation.
    @Override
    public String toMips() {
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }

        mipsInstructions.add("\tli $v0, 4");
        if (this.stringLocation instanceof DataStringLiteral) {
            mipsInstructions.add(
                    String.format("\tla $a0, %s", this.stringLocation.globalDataLabel.toString())
            );
        } else {
            mipsInstructions.add(
                    String.format("\tlw $a0, %s", this.stringLocation.toAddress())
            );
        }
        mipsInstructions.add("\tsyscall");

        String str = "";
        for (String statement : mipsInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
        }
        return str.substring(0, str.length() - 1);
    }
}
