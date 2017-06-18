package Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType;

import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;

public class BoolType extends Type {
	private static BoolType instance = new BoolType();

	public static Type getType() {
		return instance;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean compatibleWith(Type other) {
		return other instanceof BoolType;
	}

	@Override
	public boolean castableTo(Type other) {
		return false;
	}

	@Override
	public String toString() {
		return "bool";
	}
}
