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

    public static JumpInstruction getInstruction(LabelInstruction label) {
        return new JumpInstruction(label);
    }

    @Override
    public String toString() {
        return "jump " + to;
    }
}