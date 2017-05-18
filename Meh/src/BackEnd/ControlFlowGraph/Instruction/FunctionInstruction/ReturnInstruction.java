package BackEnd.ControlFlowGraph.Instruction.FunctionInstruction;

import BackEnd.ControlFlowGraph.Operand.Operand;

/**
 * Created by tan on 5/18/17.
 */
public class ReturnInstruction extends FunctionInstruction {
    public Operand operand;

    public ReturnInstruction(Operand operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "ret " + operand;
    }
}
