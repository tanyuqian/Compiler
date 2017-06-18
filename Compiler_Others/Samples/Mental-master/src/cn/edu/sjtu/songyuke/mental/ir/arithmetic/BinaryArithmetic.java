package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataIntLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;
import cn.edu.sjtu.songyuke.mental.translator.MIPSRegister;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/26.
 */
public abstract class BinaryArithmetic extends Arithmetic {
    public DataValue lhs, rhs;
    public BinaryArithmetic() {
        this.lhs = this.rhs = null;
    }
    public BinaryArithmetic(DataValue lhs, DataValue rhs) {
        this.lhs = lhs;
        this.lhs.refCount++;
        this.rhs = rhs;
        this.rhs.refCount++;
        this.res = new DataValue();
    }
    public BinaryArithmetic(DataValue lhs, DataValue rhs, DataValue res) {
        this.lhs = lhs;
        this.lhs.refCount++;
        this.rhs = rhs;
        this.rhs.refCount++;
        this.res = res;
    }

    public BinaryArithmetic(Data lhs, Data rhs, DataValue res) {
        this.lhs = (DataValue) lhs;
        this.lhs.refCount++;
        this.rhs = (DataValue) rhs;
        this.rhs.refCount++;
        this.res = res;
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
            if (this.res.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsMachine.rewriteFirstLoadRegister(this.res);
            } else {
                mipsMachine.updateRegister(this.res.registerName);
            }
            mipsInstructions.add(
                    String.format("\t%s %s, %s, %d", operand, this.res.toRegister(), this.lhs.toRegister(), ((DataIntLiteral) this.rhs).literal)
            );
        } else {
            if (this.rhs.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsInstructions.add(mipsMachine.replaceFirstLoadRegisterWithLoad(this.rhs));
            } else {
                mipsMachine.refreshRegister(this.rhs.registerName);
            }
            if (this.res.registerName == -1) {
                mipsInstructions.add(mipsMachine.storeFirstLoadRegister());
                mipsMachine.rewriteFirstLoadRegister(this.res);
            } else {
                mipsMachine.updateRegister(this.res.registerName);
            }
            mipsInstructions.add(
                    String.format("\t%s %s, %s, %s", operand, this.res.toRegister(), this.lhs.toRegister(), this.rhs.toRegister())
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

    // for cisc code generation.
    public String toMips(String operand) {

        LinkedList<String> mipsInstructions = new LinkedList<>();
        if (this.label != null) {
            mipsInstructions.add(this.label.toString() + ":");
        }

        if (this.lhs instanceof DataIntLiteral) {
            mipsInstructions.add(
                    String.format("\tli $%d, %d", MIPSRegister.t0, ((DataIntLiteral) this.lhs).literal)
            );
        } else {
            mipsInstructions.add(
                    String.format("\tlw $%d, %s", MIPSRegister.t0, this.lhs.toAddress())
            );
        }

        if (this.rhs instanceof DataIntLiteral) {
            mipsInstructions.add(
                    String.format("\t%s $t0, $t0, %d", operand, ((DataIntLiteral) this.rhs).literal)
            );
            mipsInstructions.add(
                    String.format("\tsw $t0, %s", this.res.toAddress())
            );
        } else {
            mipsInstructions.add(
                    String.format("\tlw $t1, %s", this.rhs.toAddress())
            );
            mipsInstructions.add(
                    String.format("\t%s $t0, $t0, $t1", operand)
            );
            mipsInstructions.add(
                    String.format("\tsw $t0, %s", this.res.toAddress())
            );
        }
        String str = "";
        for (String statement : mipsInstructions) {
            str += statement + "\n";
        }
        return str.substring(0, str.length() - 1);
    }
}
