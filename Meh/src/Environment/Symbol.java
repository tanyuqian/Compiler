package Environment;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/2/17.
 */
public class Symbol {
    public Type type;
    public String name;
    public Scope scope;
    public VirtualRegister register;

    public Symbol(Type type, String name) {
        this.type = type;
        this.name = name;
        this.scope = Environment.scopeTable.getScope();
        this.register = null;
    }

    public String toString() {
        return "name = " + name + ", type: " + type.toString();
    }
}
