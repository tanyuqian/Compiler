package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/25.
 */
public class Add extends BinaryArithmetic {
    public Add() {
        super();
    }
    public Add(DataValue lhs, DataValue rhs) {
        super(lhs, rhs);
    }
    public Add(Data lhs, Data rhs, DataValue res) {
        super(lhs, rhs, res);
    }
    public Add(DataValue lhs, DataValue rhs, DataValue res) {
        super(lhs, rhs, res);
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "add");
    }
    @Override
    public String toMips() {
        return this.toMips("add");
    }
}
