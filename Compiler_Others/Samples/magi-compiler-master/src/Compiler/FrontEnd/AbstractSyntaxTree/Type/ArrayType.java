package Compiler.FrontEnd.AbstractSyntaxTree.Type;

import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.NullType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Error.InternalError;

public class ArrayType extends Type {
	public Type baseType;
	public int dimension;

	private ArrayType(Type baseType, int dimension) {
		this.baseType = baseType;
		this.dimension = dimension;
	}

	public static Type getType(Type baseType, int dimension) {
		if (baseType instanceof VoidType) {
			throw new CompilationError("the void-array-type is not allowed");
		}
		if (dimension == 0) {
			throw new InternalError();
		}
		return new ArrayType(baseType, dimension);
	}

	public static Type getType(Type baseType) {
		if (baseType instanceof VoidType) {
			throw new CompilationError("the void-array-type is not allowed");
		}
		if (baseType instanceof ArrayType) {
			ArrayType arrayType = (ArrayType)baseType;
			return new ArrayType(arrayType.baseType, arrayType.dimension + 1);
		} else {
			return new ArrayType(baseType, 1);
		}
	}

	public Type reduce() {
		if (dimension == 1) {
			return baseType;
		} else {
			return ArrayType.getType(baseType, dimension - 1);
		}
	}

	@Override
	public boolean compatibleWith(Type other) {
		if (other instanceof NullType) {
			return true;
		} else if (other instanceof ArrayType) {
			ArrayType arrayType = (ArrayType)other;
			return arrayType.baseType.compatibleWith(baseType) && arrayType.dimension == dimension;
		}
		return false;
	}

	@Override
	public boolean castableTo(Type other) {
		return false;
	}

	@Override
	public String toString() {
		return "[array: " + baseType + ", " + dimension + "]";
	}
}
