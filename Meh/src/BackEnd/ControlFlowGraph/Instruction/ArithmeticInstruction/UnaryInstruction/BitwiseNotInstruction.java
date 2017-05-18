package BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction;

import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/18/17.
 */
public class BitwiseNotInstruction extends UnaryInstruction {
    public BitwiseNotInstruction(VirtualRegister destination, Operand operand) {
        super(destination, operand);
    }

    @Override
    public String toString() {
        return String.format("%s = not %s", destination, operand);
    }
}
