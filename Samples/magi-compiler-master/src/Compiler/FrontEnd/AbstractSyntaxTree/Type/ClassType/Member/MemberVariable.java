package Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.Member;

import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Utility;

public class MemberVariable extends Member {
	public Type type;
	public int offset;
	public Expression expression;

	public MemberVariable(ClassType classType, String name, Type type, boolean isProtected, boolean isPrivate) {
		super(name, isProtected, isPrivate);
		this.type = type;
		this.offset = classType.allocateSize;
		classType.allocateSize += Utility.getAligned(type.size());
	}

	@Override
	public String toString() {
		return "[name = " + name + ", type = " + type + ", offset = " + offset + "]";
	}

	public String toStringTree(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(this).append("\n");
		if (expression != null) {
			stringBuilder.append(expression.toString(indents + 1));
		}
		return stringBuilder.toString();
	}
}
