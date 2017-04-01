package Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType;

import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;

public class IntType extends Type {
	private static IntType instance = new IntType();

	public static Type getType() {
		return instance;
	}

	@Override
	public boolean compatibleWith(Type other) {
		return other instanceof IntType;
	}

	@Override
	public boolean castableTo(Type other) {
		return false;
	}

	@Override
	public String toString() {
		return "int";
	}
}
