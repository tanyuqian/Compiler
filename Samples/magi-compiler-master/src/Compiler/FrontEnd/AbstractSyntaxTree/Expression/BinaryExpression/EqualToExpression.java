package Compiler.FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.EqualToInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.NullConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.FunctionCallExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.ArrayList;
import java.util.List;

public class EqualToExpression extends BinaryExpression {
	private EqualToExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
		super(type, isLeftValue, left, right);
	}

	public static Expression getExpression(Expression left, Expression right) {
		if (!left.type.compatibleWith(right.type)) {
			throw new CompilationError("two expressions with the same type are expected in the equal-to expression");
		}
		if (left instanceof NullConstant && right instanceof NullConstant) {
			return BoolConstant.getConstant(true);
		} else if (left instanceof BoolConstant && right instanceof BoolConstant) {
			boolean literal1 = ((BoolConstant)left).literal;
			boolean literal2 = ((BoolConstant)right).literal;
			return BoolConstant.getConstant(literal1 == literal2);
		} else if (left instanceof IntConstant && right instanceof IntConstant) {
			int literal1 = ((IntConstant)left).literal;
			int literal2 = ((IntConstant)right).literal;
			return BoolConstant.getConstant(literal1 == literal2);
		} else if (left instanceof StringConstant && right instanceof StringConstant) {
			String literal1 = ((StringConstant)left).literal;
			String literal2 = ((StringConstant)right).literal;
			return BoolConstant.getConstant(literal1.equals(literal2));
		}
		if (left.type instanceof StringType && right.type instanceof StringType) {
			return FunctionCallExpression.getExpression(
					(Function)Environment.symbolTable.get("____builtin_string____equal_to").type,
					new ArrayList<Expression>() {{
						add(left);
						add(right);
					}}
			);
		}
		return new EqualToExpression(BoolType.getType(), false, left, right);
	}

	@Override
	public String toString() {
		return "[expression: equal-to]";
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
