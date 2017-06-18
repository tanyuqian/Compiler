package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/28.
 */
public class CompareNotEqual extends Compare {
    public CompareNotEqual() {
        super();
    }
    public CompareNotEqual(DataValue lhs, DataValue rhs) {
        super(lhs, rhs);
    }
    public CompareNotEqual(Data lhs, Data rhs, DataValue res) {
        super(lhs, rhs, res);
    }
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "sne");
    }
    @Override
    public String toMips() {
        return this.toMips("sne");
    }
}
