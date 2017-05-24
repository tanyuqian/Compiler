package BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Operand.Operand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/18/17.
 */
public class JumpInstruction extends ControlFlowInstruction {
    public LabelInstruction to;

    public JumpInstruction(LabelInstruction to) {
        this.to = to;
    }

    public static Instruction getInstruction(LabelInstruction label) {
        return new JumpInstruction(label);
    }

    @Override
    public List<Operand> getDefinedOperands() {
        return new ArrayList<>();
    }

    @Override
    public List<Operand> getUsedOperands() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "jump " + to;
    }
}