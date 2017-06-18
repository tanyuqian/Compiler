package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/28.
 */
public class CompareEqual extends Compare {
    public CompareEqual() {
        super();
    }
    public CompareEqual(DataValue lhs, DataValue rhs) {
        super(lhs, rhs);
    }
    public CompareEqual(Data lhs, Data rhs, DataValue res) {
        super(lhs, rhs, res);
    }
    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "seq");
    }
    @Override
    public String toMips() {
        return this.toMips("seq");
    }
}
