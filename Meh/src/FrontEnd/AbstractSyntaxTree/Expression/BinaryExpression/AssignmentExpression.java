package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.LoadInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/3/17.
 */
public class AssignmentExpression extends BinaryExpression {
    public AssignmentExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if (!left.isLeftValue) {
            throw new CompilationError("left Value Error.");
        }
        if (!left.type.compatibleWith(right.type)) {
            throw new CompilationError("can't compatible in assignement expression");
        }
        return new AssignmentExpression(left.type, true, left, right);
    }

    @Override
    public void emit(List<Instruction> instructions) {
        left.emit(instructions);
        right.emit(instructions);
        right.load(instructions);
        operand = left.operand;
        if (left.operand instanceof Address) {
            instructions.add(StoreInstruction.getInstruction(right.operand, left.operand));
        } else {
            instructions.add(MoveInstruction.getInstruction(left.operand, right.operand));
        }
    }

    @Override
    public void load(List<Instruction> instructions) {
        if (operand instanceof Address) {
            Address address = (Address)operand;
            operand = Environment.registerTable.addTemporaryRegister();
            instructions.add(LoadInstruction.getInstruction(operand, address));
        }
    }
}
