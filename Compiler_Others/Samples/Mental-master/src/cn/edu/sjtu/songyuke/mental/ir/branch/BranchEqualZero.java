package cn.edu.sjtu.songyuke.mental.ir.branch;

import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.ir.label.IRLabel;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/27.
 */
public class BranchEqualZero extends BranchWithCondition {

    public BranchEqualZero() {
        this.gotoLabel = null;
        this.condition = null;
        this.nextInstruction = null;
    }

    public BranchEqualZero(DataValue condition, IRLabel gotoLabel) {
        this.gotoLabel = gotoLabel;
        this.condition = condition;
        this.condition.refCount++;
        this.nextInstruction = null;
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "beqz");
    }
}
