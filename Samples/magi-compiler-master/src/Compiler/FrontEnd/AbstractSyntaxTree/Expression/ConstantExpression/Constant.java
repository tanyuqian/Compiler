package Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Utility;

public abstract class Constant extends Expression {
	protected Constant(Type type) {
		super(type, false);
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		return stringBuilder.toString();
	}
}
