package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/25.
 */
public class BitAnd extends BinaryArithmetic {
    public BitAnd() {
        super();
    }
    public BitAnd(DataValue lhs, DataValue rhs, DataValue res) {
        super(lhs, rhs, res);
    }

    public BitAnd(Data lhsRes, Data rhsRes, DataValue res) {
        super((DataValue) lhsRes, (DataValue) rhsRes, res);
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "and");
    }
    @Override
    public String toMips() {
        return this.toMips("and");
    }
}
