package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Utility.CompilationError;

import java.util.ArrayList;
import java.util.List;

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
    public List<Operand> getDefinedOperands() {
        return new ArrayList<Operand>() {{
            add(destination);
        }};
    }

    @Override
    public List<Operand> getUsedOperands() {
        return new ArrayList<Operand>() {{
            add(size);
        }};
    }

    @Override
    public String toString() {
        return destination + " = alloc " + size;
    }
}
