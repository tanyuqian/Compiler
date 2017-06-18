package cn.edu.sjtu.songyuke.mental.ir.branch;

import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.ir.label.IRLabel;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/5/5.
 */
public class BranchCompareNotEqual extends BranchCompare {
    public BranchCompareNotEqual() {
        super();
    }

    public BranchCompareNotEqual(DataValue lhs, DataValue rhs, IRLabel gotoLabel) {
        super(lhs, rhs, gotoLabel);
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "bne");
    }
}