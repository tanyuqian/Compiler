package FrontEnd.AbstractSyntaxTree.Type.BasicType;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class IntType extends Type {
    public boolean compatibleWith(Type other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "Int";
    }
}
