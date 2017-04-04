package FrontEnd.AbstractSyntaxTree.Type;

import FrontEnd.AbstractSyntaxTree.Type.BasicType.NullType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Utility.CompilationError;

/**
 * Created by tan on 3/30/17.
 */
public class ArrayType extends Type {
    public Type baseType;
    public int dimension;

    public ArrayType(Type baseType, int dimension) {
        this.baseType = baseType;
        this.dimension = dimension;
    }

    public boolean compatibleWith(Type other) {
        if (other instanceof NullType) {
            return true;
        } else if (other instanceof ArrayType) {
            return baseType.compatibleWith(((ArrayType) other).baseType) &&
                    dimension == ((ArrayType) other).dimension;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[array: " + baseType + ", " + dimension + "]";
    }

    public static Type getType(Type baseType) {
        if (baseType instanceof VoidType) {
            throw new CompilationError("void array basetype!!");
        }
        if (baseType instanceof ArrayType) {
            ArrayType tmp = (ArrayType)baseType;
            return new ArrayType(tmp.baseType, tmp.dimension + 1);
        } else {
            return new ArrayType(baseType, 1);
        }
    }

    public static Type getType(Type baseType, int dimension) {
        if (baseType instanceof VoidType) {
            throw new CompilationError("void array basetype!!");
        }
        if (dimension == 0) {
            throw new CompilationError("Internal Error!");
        }
        return new ArrayType(baseType, dimension);
    }

    public Type reduce() {
        if (dimension == 1) {
            return baseType;
        } else {
            return ArrayType.getType(baseType, dimension - 1);
        }
    }
}
