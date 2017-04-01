package FrontEnd.AbstractSyntaxTree.Type;

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
}
