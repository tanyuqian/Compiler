package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public class IdentifierExpression extends Expression {
    public String name;

    public IdentifierExpression(Type type, String name) {
        super(type);
        this.name = name;
    }
}
