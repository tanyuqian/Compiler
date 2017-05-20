package BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Utility.CompilationError;

/**
 * Created by tan on 5/18/17.
 */
public class MultiplicationInstruction extends BinaryInstruction {
    public MultiplicationInstruction(VirtualRegister destination, Operand operand1, Operand operand2) {
        super(destination, operand1, operand2);
    }
    public static Instruction getInstruction(Operand destination, Operand operand1, Operand operand2) {
        if (destination instanceof VirtualRegister) {
            return new BitwiseAndInstruction((VirtualRegister)destination, operand1, operand2).rebuild();
        }
        throw new CompilationError("Internal Error!");
    }

    @Override
    public Instruction rebuild() {
        if (operand1 instanceof ImmediateValue && operand2 instanceof ImmediateValue) {
            int value1 = ((ImmediateValue)operand1).value;
            int value2 = ((ImmediateValue)operand2).value;
            return MoveInstruction.getInstruction(destination, new ImmediateValue(value1 * value2));
        } else if (operand1 instanceof ImmediateValue) {
            Operand tmp = operand1;
            operand1 = operand2;
            operand2 = tmp;
        }
        return this;
    }
    @Override
    public String toString() {
        return String.format("%s = mul %s %s", destination, operand1, operand2);
    }
}
