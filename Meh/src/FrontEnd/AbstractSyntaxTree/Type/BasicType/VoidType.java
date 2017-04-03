package FrontEnd.AbstractSyntaxTree.Type.BasicType;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class VoidType extends Type {
    public boolean compatibleWith(Type other) {
        return false;
    }

    @Override
    public String toString() {
        return "Void";
    }
}
