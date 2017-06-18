package cn.edu.sjtu.songyuke.mental.ir.data;

import cn.edu.sjtu.songyuke.mental.translator.MIPSRegister;

/**
 * Created by Songyu on 16/4/26.
 */

public class ConstantZero extends DataIntLiteral {
    public ConstantZero() {
        this.registerName = MIPSRegister.zero;
        this.stackShift = 2147483647 >> 1;
        this.literal = 0;
    }

    @Override
    public String toAddress() {
        return "$zero";
    }
    @Override
    public String toRegister() {
        return "$zero";
    }
}
