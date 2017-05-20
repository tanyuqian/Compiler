package BackEnd.ControlFlowGraph.Instruction.FunctionInstruction;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import FrontEnd.AbstractSyntaxTree.Function;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 5/18/17.
 */
public class CallInstruction extends FunctionInstruction {
    public VirtualRegister destination;
    public Function function;
    public List<Operand> parameters;

    public CallInstruction(VirtualRegister destination, Function function, List<Operand> parameters) {
        this.destination = destination;
        this.function = function;
        this.parameters = parameters;
    }

    public static Instruction getInstruction(Operand destination, Function function, List<Operand> parameters) {
        if (destination == null) {
            return new CallInstruction(null, function, parameters);
        } else if (destination instanceof VirtualRegister) {
            return new CallInstruction((VirtualRegister)destination, function, parameters);
        }
        throw new CompilationError("Internal Error!");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (destination != null) {
            stringBuilder.append(destination).append(" = ");
        }
        stringBuilder.append("call ").append(function.name);
        parameters.forEach(parameter -> {
            stringBuilder.append(" ").append(parameter);
        });
        return stringBuilder.toString();
    }
}
