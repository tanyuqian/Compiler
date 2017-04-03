package Environment;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/2/17.
 */
public class Symbol {
    public Type type;
    public String name;
    public Scope scope;

    public Symbol(Type type, String name, Scope scope) {
        this.type = type;
        this.name = name;
        this.scope = scope;
    }
}
