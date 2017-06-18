package Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType;

import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;

public class VoidType extends Type {
	private static VoidType instance = new VoidType();

	public static Type getType() {
		return instance;
	}

	@Override
	public boolean compatibleWith(Type other) {
		return false;
	}

	@Override
	public boolean castableTo(Type other) {
		return false;
	}

	@Override
	public String toString() {
		return "void";
	}
}
