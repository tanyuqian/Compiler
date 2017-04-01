package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.branch.Branch;
import cn.edu.sjtu.songyuke.mental.ir.Call;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.ir.SystemCall;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/5/4.
 */
public class BasicBlockSpliter {
    public LinkedList<BasicBlock> basicBlocks;
    public BasicBlockSpliter() {
        this.basicBlocks = new LinkedList<>();
    }
    public BasicBlockSpliter(Instruction instruction) {
        this.basicBlocks = new LinkedList<>();
        this.basicBlocks.add(new BasicBlock());
        if (instruction != null) {
            this.basicBlocks.getLast().instruction = instruction;
        }
        while (instruction != null) {
            if (instruction.nextInstruction != null) {
                Instruction nextInstruction = instruction.nextInstruction;
                if (instruction instanceof Branch
                        || instruction instanceof Call
                        || instruction instanceof SystemCall
                        || instruction.nextInstruction.label != null) {
                    if (instruction instanceof Call
                            || instruction instanceof SystemCall) {
                        basicBlocks.getLast().allowAppend = false;
                    }
                    BasicBlock newBasicBlock = new BasicBlock();
                    newBasicBlock.instruction = nextInstruction;
                    instruction.nextInstruction = null;
                    basicBlocks.add(newBasicBlock);
                }
                instruction = nextInstruction;
            } else {
                break;
            }
        }
    }
}
