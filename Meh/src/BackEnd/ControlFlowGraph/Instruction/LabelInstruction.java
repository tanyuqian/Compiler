package BackEnd.ControlFlowGraph.Instruction;

import BackEnd.ControlFlowGraph.Block;

/**
 * Created by tan on 5/18/17.
 */
public class LabelInstruction extends Instruction {
    public String name;
    public Block block;

    @Override
    public String toString() {
        return "%" + name;
    }
}
