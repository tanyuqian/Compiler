package Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType;

import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;

public class StringType extends Type {
	private static StringType instance = new StringType();

	public static Type getType() {
		return instance;
	}

	@Override
	public boolean compatibleWith(Type other) {
		return other instanceof StringType;
	}

	@Override
	public boolean castableTo(Type other) {
		return false;
	}

	@Override
	public String toString() {
		return "string";
	}
}
