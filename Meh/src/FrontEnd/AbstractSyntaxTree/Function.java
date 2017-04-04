package FrontEnd.AbstractSyntaxTree;

import Environment.Scope;
import Environment.Symbol;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.BlockStatement;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class Function extends Type implements Scope {
    public Type type;
    public String name;
    public List<Symbol> parameters;
    public BlockStatement statements;

    public Function(Type type, String name, List<Symbol> parameters) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
    }

    public boolean compatibleWith(Type other) {
        return false;
    }

    public void addStatements(BlockStatement statements) {
        this.statements = statements;
    }
}
