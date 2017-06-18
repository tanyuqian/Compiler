package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/28.
 */
public class CompareLessEqual extends Compare {
    public CompareLessEqual() {
        super();
    }
    public CompareLessEqual(DataValue lhs, DataValue rhs) {
        super(lhs, rhs);
    }
    public CompareLessEqual(Data lhs, Data rhs, DataValue res) {
        super(lhs, rhs, res);
    }
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "sle");
    }
    @Override
    public String toMips() {
        return this.toMips("sle");
    }
}
