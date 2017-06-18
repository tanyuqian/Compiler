package BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/18/17.
 */
public abstract class UnaryInstruction extends ArithmeticInstruction {
    public VirtualRegister destination;
    public Operand operand;

    public UnaryInstruction(VirtualRegister destination, Operand operand) {
        this.destination = destination;
        this.operand = operand;
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
            add(operand);
        }};
    }
}
