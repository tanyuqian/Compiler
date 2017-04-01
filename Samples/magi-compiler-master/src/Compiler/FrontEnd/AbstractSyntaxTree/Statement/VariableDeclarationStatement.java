package Compiler.FrontEnd.AbstractSyntaxTree.Statement;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.Environment.SymbolTable.Symbol;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Utility;

import java.util.List;

public class VariableDeclarationStatement extends Statement {
	public Symbol symbol;
	public Expression expression;

	private VariableDeclarationStatement(Symbol symbol, Expression expression) {
		this.symbol = symbol;
		this.expression = expression;
	}

	public static Statement getStatement(Symbol symbol, Expression expression) {
		if (symbol.type instanceof VoidType) {
			throw new CompilationError("a non-void type is expected in the left-hand-side of the variable-declaration statement");
		}
		if (expression == null || symbol.type.compatibleWith(expression.type)) {
			return new VariableDeclarationStatement(symbol, expression);
		}
		throw new CompilationError("two expressions with compatible types are expected in the variable-declaration statement");
	}

	@Override
	public String toString() {
		return "[statement: variable-declaration, name = " + symbol.name + ", type = " + symbol.type + "]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(this).append("\n");
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
			instructions.add(MoveInstruction.getInstruction(symbol.register, expression.operand));
		}
	}
}
