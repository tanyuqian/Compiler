package Compiler.FrontEnd.AbstractSyntaxTree.Type;

import Compiler.FrontEnd.AbstractSyntaxTree.Node;
import Compiler.Utility.Utility;

public abstract class Type implements Node {
	public int size() {
		return 4;
	}

	public abstract boolean compatibleWith(Type other);
	public abstract boolean castableTo(Type other);

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		return stringBuilder.toString();
	}
}
