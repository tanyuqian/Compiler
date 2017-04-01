package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/25.
 */
public class Sub extends BinaryArithmetic {
    public Sub() {
        super();
    }
    public Sub(Data lhs, Data rhs, DataValue res) {
        super(lhs, rhs, res);
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "sub");
    }
    @Override
    public String toMips() {
        return this.toMips("sub");
    }
}
