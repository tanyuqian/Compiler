package BackEnd.ControlFlowGraph.Instruction;

import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/18/17.
 */
public abstract class Instruction {
    public Instruction rebuild() {
        return this;
    }

    public abstract List<Operand> getDefinedOperands();
    public abstract List<Operand> getUsedOperands();

    public List<VirtualRegister> getDefinedRegisters() {
        List<VirtualRegister> result = new ArrayList<>();
        for (Operand operand : getDefinedOperands()) {
            if (operand instanceof VirtualRegister) {
                result.add((VirtualRegister)operand);
            }
        }
        return result;
    }

    public List<VirtualRegister> getUsedRegisters() {
        List<VirtualRegister> result = new ArrayList<>();
        for (Operand operand : getUsedOperands()) {
            if (operand instanceof VirtualRegister) {
                result.add((VirtualRegister)operand);
            }
        }
        return result;
    }
}
