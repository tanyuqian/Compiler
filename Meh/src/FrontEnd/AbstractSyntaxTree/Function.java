package FrontEnd.AbstractSyntaxTree;

import Environment.Symbol;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class Function extends Type {
    public Type type;
    public String name;
    public List<Symbol> parameters;

    public Function(Type type, String name, List<Symbol> parameters) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
    }

    public boolean compatibleWith(Type other) {
        return false;
    }
}
