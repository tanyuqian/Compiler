package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;

/**
 * Created by Songyu on 16/4/28.
 */
public abstract class Compare extends BinaryArithmetic {
    public Compare() {
        super();
    }
    public Compare(DataValue lhs, DataValue rhs) {
        super(lhs, rhs);
    }
    public Compare(Data lhs, Data rhs, DataValue res) {
        super(lhs, rhs, res);
    }
}
