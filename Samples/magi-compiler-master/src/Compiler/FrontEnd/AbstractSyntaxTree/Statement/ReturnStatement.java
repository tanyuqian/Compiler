package Compiler.FrontEnd.AbstractSyntaxTree.Statement;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Utility;

import java.util.List;

public class ReturnStatement extends Statement {
	public Expression expression;
	public Function to;

	private ReturnStatement(Expression expression, Function to) {
		this.expression = expression;
		this.to = to;
	}

	public static Statement getStatement(Expression expression) {
		Function function = Environment.scopeTable.getFunctionScope();
		if (function == null) {
			throw new CompilationError("return statement should be placed in a function");
		}
		if (expression == null) {
			if (function.type instanceof VoidType) {
				return new ReturnStatement(expression, function);
			}
		} else {
			if (expression.type.compatibleWith(function.type)) {
				return new ReturnStatement(expression, function);
			}
		}
		throw new CompilationError("return value should have the same type as the function");
	}

	@Override
	public String toString() {
		return "[statement: return]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		if (expression != null) {
			stringBuilder.append(expression.toString(indents + 1));
		}
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		if (expression != null) {
			expression.emit(instructions);
			expression.load(instructions);
			instructions.add(ReturnInstruction.getInstruction(expression.operand));
		}
		instructions.add(JumpInstruction.getInstruction(to.exit));
	}
}
