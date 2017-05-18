package BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction;

import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;

/**
 * Created by tan on 5/18/17.
 */
public class JumpInstruction extends ControlFlowInstruction {
    public LabelInstruction to;

    public JumpInstruction(LabelInstruction to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "jump " + to;
    }
}
