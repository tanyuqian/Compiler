package BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/18/17.
 */
public class LessThanOrEqualToInstruction extends BinaryInstruction {
    public LessThanOrEqualToInstruction(VirtualRegister destination, Operand operand1, Operand operand2) {
        super(destination, operand1, operand2);
    }

    @Override
    public String toString() {
        return String.format("%s = sle %s %s", destination, operand1, operand2);
    }
}
