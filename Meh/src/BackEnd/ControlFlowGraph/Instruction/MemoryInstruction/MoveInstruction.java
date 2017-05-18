package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.ModuloInstruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/18/17.
 */
public class MoveInstruction extends MemoryInstruction {
    public VirtualRegister destination;
    public Operand operand;

    public MoveInstruction(VirtualRegister destination, Operand operand) {
        this.destination = destination;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return destination + " = move " + operand;
    }
}
