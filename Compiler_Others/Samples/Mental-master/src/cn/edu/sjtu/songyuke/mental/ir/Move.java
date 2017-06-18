package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataIntLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/5/1.
 */
public class Move extends Instruction {
    public DataValue src;
    public DataValue dest;
    public Move() {
        this.src = null;
        this.dest = null;
    }
    public Move(DataValue src) {
        this.src = src;
        this.src.refCount++;
        this.dest = new DataValue();
    }

    public Move(DataValue src, DataValue dest) {
        this.src = src;
        this.src.refCount++;
        this.dest = dest;
    }

    public Move(Data src, Data dest) {
        this.src = (DataValue) src;
        this.src.refCount++;
        this.dest = (DataValue) dest;
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        LinkedList<String> mipsInstructions = new LinkedList<>();
        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }

        if (this.dest.refCount != 0 || this.dest.globalID != -1) {
            this.src.refCount--;
            if (!(this.src instanceof DataIntLiteral)) {
                if (this.src.registerName == -1) {
                    mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                    mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.src));
                } else {
                    mipsMachine.refreshRegister(this.src.registerName);
                }
            }

            if (this.dest.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsMachine.rewriteFirstLoadRegister(this.dest);
            } else {
                mipsMachine.updateRegister(this.dest.registerName);
            }
            if (this.src instanceof DataIntLiteral) {
                mipsInstructions.add(
                        String.format("\tli %s, %d", this.dest.toRegister(), ((DataIntLiteral) this.src).literal)
                );
            } else {
                mipsInstructions.add(
                        String.format("\tmove %s, %s", this.dest.toRegister(), this.src.toRegister())
                );
            }
        }

        String str = "";
        for (String statement : mipsInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
        }
        if (str.length() == 0) {
            return "";
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

        if (this.src instanceof DataIntLiteral) {
            mipsInstructions.add(
                    String.format("\tli $t0, %d", ((DataIntLiteral) this.src).literal)
            );
        } else {
            mipsInstructions.add(
                    String.format("\tlw $t0, %s", this.src.toAddress())
            );
        }

        mipsInstructions.add(
                String.format("\tsw $t0, %s", this.dest.toAddress())
        );

        String str = "";
        for (String statement : mipsInstructions) {
            str += statement + "\n";
        }
        return str.substring(0, str.length() - 1);
    }
}
