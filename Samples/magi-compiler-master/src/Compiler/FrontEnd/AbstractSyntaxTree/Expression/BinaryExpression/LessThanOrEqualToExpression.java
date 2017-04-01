package Compiler.FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.LessThanOrEqualToInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.FunctionCallExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.ArrayList;
import java.util.List;

public class LessThanOrEqualToExpression extends BinaryExpression {
	private LessThanOrEqualToExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
		super(type, isLeftValue, left, right);
	}

	public static Expression getExpression(Expression left, Expression right) {
		if (left.type instanceof IntType && right.type instanceof IntType) {
			if (left instanceof IntConstant && right instanceof IntConstant) {
				int literal1 = ((IntConstant)left).literal;
				int literal2 = ((IntConstant)right).literal;
				return BoolConstant.getConstant(literal1 <= literal2);
			} else {
				return new LessThanOrEqualToExpression(BoolType.getType(), false, left, right);
			}
		} else if (left.type instanceof StringType && right.type instanceof StringType) {
			if (left instanceof StringConstant && right instanceof StringConstant) {
				String literal1 = ((StringConstant)left).literal;
				String literal2 = ((StringConstant)right).literal;
				return BoolConstant.getConstant(literal1.compareTo(literal2) <= 0);
			}
			return FunctionCallExpression.getExpression(
					(Function)Environment.symbolTable.get("____builtin_string____less_than_or_equal_to").type,
					new ArrayList<Expression>() {{
						add(left);
						add(right);
					}}
			);
		}
		throw new CompilationError("two int-type or string-type expressions are expected in the less-than-or-equal-to expression");
	}

	@Override
	public String toString() {
		return "[expression: less-than-or-equal-to]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		left.emit(instructions);
		left.load(instructions);
		right.emit(instructions);
		right.load(instructions);
		operand = Environment.registerTable.addTemporaryRegister();
		instructions.add(LessThanOrEqualToInstruction.getInstruction(operand, left.operand, right.operand));
	}
}
