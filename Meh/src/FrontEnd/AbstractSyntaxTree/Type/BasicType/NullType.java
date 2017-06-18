package FrontEnd.AbstractSyntaxTree.Type.BasicType;

import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class NullType extends Type {
    public boolean compatibleWith(Type other) {
        return (other instanceof NullType) || (other instanceof ArrayType);
    }

    @Override
    public String toString() {
        return "Null";
    }
}
