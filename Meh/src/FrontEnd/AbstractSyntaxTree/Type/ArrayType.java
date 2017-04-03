package FrontEnd.AbstractSyntaxTree.Type;

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
}
