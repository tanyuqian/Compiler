package BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
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

    public static Instruction getInstruction(Operand condition, LabelInstruction i1, LabelInstruction i2) {
        return new BranchInstruction(condition, i1, i2).rebuild();
    }

    @Override
    public Instruction rebuild() {
        if (condition instanceof ImmediateValue) {
            int value = ((ImmediateValue)condition).value;
            if (value == 0) {
                return JumpInstruction.getInstruction(falseTo);
            } else {
                return JumpInstruction.getInstruction(trueTo);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "br " + condition + " " + trueTo + " " +  falseTo;
    }
}
