package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/25.
 */
public class BitOr extends BinaryArithmetic {
    public BitOr() {
        super();
    }
    public BitOr(DataValue lhs, DataValue rhs, DataValue res) {
        super(lhs, rhs, res);
    }

    public BitOr(Data lhsRes, Data rhsRes, DataValue res) {
        super((DataValue) lhsRes, (DataValue) rhsRes, res);
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "or");
    }
    @Override
    public String toMips() {
        return this.toMips("or");
    }
}
