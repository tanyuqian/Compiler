package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Utility.CompilationError;

/**
 * Created by tan on 5/18/17.
 */
public class AllocateInstruction extends MemoryInstruction {
    public VirtualRegister destination;
    public Operand size;

    public AllocateInstruction(VirtualRegister destination, Operand size) {
        this.destination = destination;
        this.size = size;
    }

    public static Instruction getInstruction(Operand destination, Operand size) {
        if (destination instanceof VirtualRegister) {
            return new AllocateInstruction((VirtualRegister)destination, size);
        }
        throw new CompilationError("Internal Error!");
    }

    @Override
    public String toString() {
        return destination + " = alloc " + size;
    }
}
