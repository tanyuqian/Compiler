package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.translator.MIPSMachine;

/**
 * Created by Songyu on 16/4/26.
 */
public class BitNot extends UnaryArithmetic {
    public BitNot() {
        super();
    }
    public BitNot(DataValue child, DataValue res) {
        super(child, res);
    }

    @Override
    public String toMips(MIPSMachine mipsMachine) {
        return this.toMips(mipsMachine, "not");
    }
    @Override
    public String toMips() {
        return this.toMips("not");
    }
}
