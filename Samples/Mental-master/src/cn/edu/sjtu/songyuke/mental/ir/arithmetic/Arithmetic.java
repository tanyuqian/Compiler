package cn.edu.sjtu.songyuke.mental.ir.arithmetic;

import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

/**
 * Created by Songyu on 16/4/25.
 */
public abstract class Arithmetic extends Instruction {
    public DataValue res;
    public Arithmetic() {
        this.res = null;
    }
}
