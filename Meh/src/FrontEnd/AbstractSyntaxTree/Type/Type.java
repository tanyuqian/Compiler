package FrontEnd.AbstractSyntaxTree.Type;

import FrontEnd.AbstractSyntaxTree.Node;

/**
 * Created by tan on 3/30/17.
 */
public abstract class Type extends Node {
    public abstract boolean compatibleWith(Type other);

    public boolean castableTo(Type Other) {
        return false;
    }

}
