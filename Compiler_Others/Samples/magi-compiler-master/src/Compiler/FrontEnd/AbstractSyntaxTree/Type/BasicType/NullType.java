package Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType;

import Compiler.FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;

public class NullType extends Type {
	private static NullType instance = new NullType();

	public static Type getType() {
		return instance;
	}

	@Override
	public boolean compatibleWith(Type other) {
		return other instanceof NullType || other instanceof ArrayType || other instanceof StringType;
	}

	@Override
	public boolean castableTo(Type other) {
		return false;
	}

	@Override
	public String toString() {
		return "null";
	}
}
