package BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction;

import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Operand.Operand;

import java.security.PublicKey;

/**
 * Created by tan on 5/18/17.
 */
public class BranchInstruction extends ControlFlowInstruction {
    public Operand condition;
    public LabelInstruction trueTo, falseTo;

    public BranchInstruction(Operand condition, LabelInstruction trueTo, LabelInstruction falseTo) {
        this.condition = condition;
        this.trueTo = trueTo;
        this.falseTo = falseTo;
    }

    @Override
    public String toString() {
        return "br " + condition + " " + trueTo + " " falseTo;
    }
}
