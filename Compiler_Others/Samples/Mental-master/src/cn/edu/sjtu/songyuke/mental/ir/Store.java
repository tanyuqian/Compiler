package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataAddress;
import cn.edu.sjtu.songyuke.mental.ir.data.DataIntLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/25.
 */
public class Store extends Instruction {
    public DataValue src;
    public Data dest;
    public Store() {
        this.src = null;
        this.dest = null;
    }
    public Store(DataValue src, Data dest) {
        this.src = src;
        this.src.refCount++;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "\tstore " + this.src.toString() + ", " + this.dest.toString();
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }
        this.src.refCount--;
        if (!(this.src instanceof DataIntLiteral) || this.dest instanceof DataAddress) {
            if (this.src.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.src));
            } else {
                mipsMachine.refreshRegister(this.src.registerName);
            }
        }

        if (this.dest instanceof DataAddress) {
            if (((DataAddress) this.dest).address.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(((DataAddress) this.dest).address));
            } else {
                mipsMachine.refreshRegister(((DataAddress) this.dest).address.registerName);
            }
            mipsInstructions.add(
                    String.format("\tsw %s, 0(%s)", this.src.toRegister(), ((DataAddress) this.dest).address.toRegister())
            );
        } else {
            if (!(this.src instanceof DataIntLiteral)
                    && ((this.src.globalID == -1 && this.src.refCount <= 0)
                            || (mipsMachine.storeTime[this.src.registerName] >= mipsMachine.updateTime[this.src.registerName]))
                    ) {
                if (this.dest.registerName == -1) {
                    mipsMachine.rewriteSpecifiedRegister(this.src.registerName, this.dest);
                } else {
                    mipsMachine.clearSpecifiedRegister(this.dest.registerName);
                    mipsMachine.rewriteSpecifiedRegister(this.src.registerName, this.dest);
                }
            } else {
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
                    String.format("\tli $t0, %s", ((DataIntLiteral) this.src).literal)
            );
        } else {
            mipsInstructions.add(
                    String.format("\tlw $t0, %s", this.src.toAddress())
            );
        }

        if (this.dest instanceof DataAddress) {
            mipsInstructions.add(
                    String.format("\tlw $t1, %s", ((DataAddress) this.dest).address.toAddress())
            );
            mipsInstructions.add(
                    String.format("\tsw $t0, 0($t1)")
            );
        } else {
            mipsInstructions.add(
                    String.format("\tsw $t0, %s", this.dest.toAddress())
            );
        }

        String resultString = "";
        for (String line : mipsInstructions) {
            resultString += line + "\n";
        }
        return resultString.substring(0, resultString.length() - 1);
    }
}
