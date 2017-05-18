package BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

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
}
