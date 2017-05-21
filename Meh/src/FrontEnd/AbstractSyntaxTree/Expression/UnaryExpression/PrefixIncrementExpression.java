package FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.AdditionInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.SubtractionInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/4/17.
 */
public class PrefixIncrementExpression extends UnaryExpression {
    public PrefixIncrementExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue, expression);
    }

    public static Expression getExpression(Expression expression) {
        if (!expression.isLeftValue) {
            throw new CompilationError("preftix: left-value error");
        }
        if (expression.type instanceof IntType) {
            return new PrefixIncrementExpression(new IntType(), false, expression);
        }
        throw new CompilationError("prefix: type error");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        expression.emit(instructions);
        if (expression.operand instanceof Address) {
            Address address = (Address)expression.operand;
            address = new Address(address.base, address.offset, address.size);
            expression.load(instructions);
            operand = expression.operand;
            instructions.add(AdditionInstruction.getInstruction(operand, operand, new ImmediateValue(1)));
            instructions.add(StoreInstruction.getInstruction(operand, address));
        } else {
            expression.load(instructions);
            operand = expression.operand;
            instructions.add(AdditionInstruction.getInstruction(operand, operand, new ImmediateValue(1)));
        }
    }
}
