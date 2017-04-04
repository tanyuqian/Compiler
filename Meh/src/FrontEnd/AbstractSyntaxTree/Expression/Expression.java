package FrontEnd.AbstractSyntaxTree.Expression;

import FrontEnd.AbstractSyntaxTree.Node;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class Expression extends Node {
    public Type type;
    public boolean isLeftValue;

    public Expression(Type type, boolean isLeftValue) {
        this.type = type;
        this.isLeftValue = isLeftValue;
    }
}
