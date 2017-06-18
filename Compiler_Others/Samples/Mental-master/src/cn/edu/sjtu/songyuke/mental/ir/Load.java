package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ir.data.DataAddress;
import cn.edu.sjtu.songyuke.mental.ir.data.DataStringLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/25.
 */
public class Load extends Instruction {
    public DataAddress src;
    public DataValue dest;
    public int loadSize;
    public Load() {
        this.src = null;
        this.dest = null;
        this.loadSize = 4;
    }
    public Load(DataAddress src) {
        this.loadSize = 4;
        this.src = src;
        if (!(this.src instanceof DataStringLiteral)) {
            this.src.address.refCount++;
        }
        this.dest = new DataValue();
    }
    public Load(DataAddress src, int loadSize) {
        this.src = src;
        if (!(this.src instanceof DataStringLiteral)) {
            this.src.address.refCount++;
        }
        this.dest = new DataValue();
        this.loadSize = loadSize;
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        LinkedList<String> mipsInstructions = new LinkedList<>();

        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }


        if (this.src instanceof DataStringLiteral) {
            if (((DataStringLiteral) this.src).registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.src));
            }
            if (this.dest.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsMachine.rewriteFirstLoadRegister(this.dest);
            } else {
                mipsMachine.updateRegister(this.dest.registerName);
            }
            mipsInstructions.add(
                    String.format("\tmove %s, %s", this.dest.toRegister(), this.src.toRegister())
            );
            ((DataStringLiteral) this.src).registerName = -1;
        } else {
            this.src.address.refCount--;
            if (this.src.address.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.src.address));
            } else {
                mipsMachine.refreshRegister(this.src.address.registerName);
            }
            if (this.dest.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsMachine.rewriteFirstLoadRegister(this.dest);
            } else {
                mipsMachine.updateRegister(this.dest.registerName);
            }
            if (this.loadSize == 4) {
                mipsInstructions.add(
                        String.format("\tlw %s, 0(%s)", this.dest.toRegister(), this.src.address.toRegister())
                );
            } else {
                mipsInstructions.add(
                        String.format("\tlb %s, 0(%s)", this.dest.toRegister(), this.src.address.toRegister())
                );
            }
        }

        String str = "";
        for (String statement : mipsInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
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
        if (this.src instanceof DataStringLiteral) {
            mipsInstructions.add(
                    String.format("\tla $t0, %s", this.src.toAddress())
            );
        } else {
            mipsInstructions.add(
                    String.format("\tlw $t0, %s", this.src.address.toAddress())
            );
            if (this.loadSize == 4) {
                mipsInstructions.add(
                        String.format("\tlw $t0, 0($t0)")
                );
            } else {
                mipsInstructions.add(
                        String.format("\tlb $t0, 0($t0)")
                );
            }
        }
        mipsInstructions.add(
                String.format("\tsw $t0, %s", this.dest.toAddress())
        );

        String str = "";
        for (String statement : mipsInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
        }
        return str.substring(0, str.length() - 1);
    }
}
