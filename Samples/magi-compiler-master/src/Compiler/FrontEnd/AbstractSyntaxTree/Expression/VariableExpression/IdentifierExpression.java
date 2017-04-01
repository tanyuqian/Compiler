package Compiler.FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.Environment.SymbolTable.Symbol;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Utility;

import java.util.List;

public class IdentifierExpression extends Expression {
	public Symbol symbol;

	private IdentifierExpression(Type resultType, boolean resultIsLeftValue, Symbol symbol) {
		super(resultType, resultIsLeftValue);
		this.symbol = symbol;
	}

	public static Expression getExpression(String symbolName) {
		if (!Environment.symbolTable.contains(symbolName)) {
			throw new CompilationError("\"" + symbolName + "\" is not a symbol name");
		}
		Symbol symbol = Environment.symbolTable.get(symbolName);
		if (symbol.scope instanceof ClassType) {
			//	symbol -> this.symbol
			return FieldExpression.getExpression(IdentifierExpression.getExpression("this"), symbolName);
		} else {
			if (symbol.type instanceof Function) {
				return new IdentifierExpression(symbol.type, false, symbol);
			} else {
				return new IdentifierExpression(symbol.type, true, symbol);
			}
		}
	}

	@Override
	public String toString() {
		return "[expression: identifier, register = " + symbol.register + ", name = " + symbol.name + ", type = " + type + "]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents));
		stringBuilder.append(toString()).append("\n");
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		operand = symbol.register;
	}
}
