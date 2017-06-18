package Compiler.FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.AdditionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.MultiplicationInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.LoadInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Address;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Utility;

import java.util.List;

public class SubscriptExpression extends Expression {
	public Expression expression, subscript;

	private SubscriptExpression(Type type, boolean isLeftValue, Expression expression, Expression subscript) {
		super(type, isLeftValue);
		this.expression = expression;
		this.subscript = subscript;
	}

	public static Expression getExpression(Expression expression, Expression subscript) {
		if (!(expression.type instanceof ArrayType)) {
			throw new CompilationError("an array-type expression is expected in the left-hand-side of the subscript expression");
		}
		if (!(subscript.type instanceof IntType)) {
			throw new CompilationError("an int-type expression is expected in the right-hand-side of the subscript expression");
		}
		ArrayType arrayType = (ArrayType)expression.type;
		return new SubscriptExpression(arrayType.reduce(), expression.isLeftValue, expression, subscript);
	}

	@Override
	public String toString() {
		return "[expression: subscript]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(this).append("\n");
		stringBuilder.append(expression.toString(indents + 1));
		stringBuilder.append(subscript.toString(indents + 1));
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		expression.emit(instructions);
		expression.load(instructions);
		subscript.emit(instructions);
		subscript.load(instructions);
		VirtualRegister address = Environment.registerTable.addTemporaryRegister();
		VirtualRegister delta = Environment.registerTable.addTemporaryRegister();
		instructions.add(MultiplicationInstruction.getInstruction(delta, subscript.operand, new ImmediateValue(type.size())));
		instructions.add(AdditionInstruction.getInstruction(address, expression.operand, delta));
		operand = new Address(address, type.size());
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
