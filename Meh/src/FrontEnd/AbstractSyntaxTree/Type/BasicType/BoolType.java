package FrontEnd.AbstractSyntaxTree.Type.BasicType;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class BoolType extends Type {
    public boolean compatibleWith(Type other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "Bool";
    }
}
