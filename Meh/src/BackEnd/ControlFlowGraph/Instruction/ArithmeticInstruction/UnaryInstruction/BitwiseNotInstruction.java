package BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/18/17.
 */
public class BitwiseNotInstruction extends UnaryInstruction {
    public BitwiseNotInstruction(VirtualRegister destination, Operand operand) {
        super(destination, operand);
    }

    public static Instruction getInstruction(Operand destination, Operand operand) {
        return new BitwiseNotInstruction((VirtualRegister)destination, operand).rebuild();
    }

    @Override
    public Instruction rebuild() {
        if (operand instanceof ImmediateValue) {
            int value = ((ImmediateValue)operand).value;
            return MoveInstruction.getInstruction(destination, new ImmediateValue(~value));
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s = not %s", destination, operand);
    }
}
