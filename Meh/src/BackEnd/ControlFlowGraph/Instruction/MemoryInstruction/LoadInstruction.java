package BackEnd.ControlFlowGraph.Instruction.MemoryInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Utility.CompilationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/18/17.
 */
public class LoadInstruction extends MemoryInstruction {
    public VirtualRegister destination;
    public Address address;

    public LoadInstruction(VirtualRegister destination, Address address) {
        this.destination = destination;
        this.address = address;
    }

    public static Instruction getInstruction(Operand destination, Operand address) {
        if (destination instanceof VirtualRegister && address instanceof Address) {
            return new LoadInstruction((VirtualRegister)destination, (Address)address);
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
            add(address.base);
        }};
    }

    @Override
    public String toString() {
        return destination + " = load " + address.size + " " + address.base + " " + address.offset;
    }
}
