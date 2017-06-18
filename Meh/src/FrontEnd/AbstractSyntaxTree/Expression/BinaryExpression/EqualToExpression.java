package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BitwiseAndInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.EqualToInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.NullConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Expression.FunctionCallExpression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.NullType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 4/4/17.
 */
public class EqualToExpression extends BinaryExpression {
    public EqualToExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if (!left.type.compatibleWith(right.type)) {
            throw new CompilationError("Type error in equal-to expression.");
        }
        if ((left instanceof NullConstant) && (right instanceof NullConstant)) {
            return new BoolConstant(true);
        } else if ((left instanceof BoolConstant) && (right instanceof BoolConstant)) {
            return new BoolConstant(((BoolConstant) left).tag == ((BoolConstant) right).tag);
        } else if ((left instanceof IntConstant) && (right instanceof IntConstant)) {
            return new BoolConstant(((IntConstant) left).number == ((IntConstant) right).number);
        } else if ((left instanceof StringConstant) && (right instanceof StringConstant)) {
            return new BoolConstant(((StringConstant) left).str.equals(((StringConstant) right).str));
        } else if ((left.type instanceof StringType) && (right.type instanceof StringType)) {
            return FunctionCallExpression.getExpression(
                    (Function)Environment.symbolTable.get("__builtin_string_equalTo").type,
                    new ArrayList<Expression>() {{
                        add(left);
                        add(right);
                    }}
            );
        }
        return new EqualToExpression(new BoolType(), false, left, right);
    }

    @Override
    public void emit(List<Instruction> instructions) {
        left.emit(instructions);
        left.load(instructions);
        right.emit(instructions);
        right.load(instructions);
        operand = Environment.registerTable.addTemporaryRegister();
        instructions.add(EqualToInstruction.getInstruction(operand, left.operand, right.operand));
    }
}
