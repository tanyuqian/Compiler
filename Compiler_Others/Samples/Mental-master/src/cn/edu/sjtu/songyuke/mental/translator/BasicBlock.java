package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.Instruction;

/**
 * Created by Songyu on 16/5/3.
 */
public class BasicBlock {
    public Instruction instruction;
    public BasicBlock nextBlock;
    public boolean allowAppend;
    public BasicBlock() {
        this.instruction = null;
        this.nextBlock = null;
        this.allowAppend = true;
    }
}
