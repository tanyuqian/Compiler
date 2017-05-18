package BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

/**
 * Created by tan on 5/18/17.
 */
public abstract class BinaryInstruction extends ArithmeticInstruction {
    public VirtualRegister destination;
    public Operand operand1, operand2;

    public BinaryInstruction(VirtualRegister destination, Operand operand1, Operand operand2) {
        this.destination = destination;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
}
