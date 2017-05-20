package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.ModuloInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Utility.CompilationError;

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

    public static Instruction getInstruction(Operand destination, Operand operand) {
        if (destination instanceof VirtualRegister) {
            return new MoveInstruction((VirtualRegister)destination, operand);
        }
        throw new CompilationError("Internal Error");
    }

    @Override
    public String toString() {
        return destination + " = move " + operand;
    }
}
