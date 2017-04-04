package FrontEnd.AbstractSyntaxTree.Expression;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/1/17.
 */
public class NewExpression extends Expression {
    public Expression expression;

    public NewExpression(Type type, boolean isLeftValue, Expression expression) {
        super(type, isLeftValue);
        this.expression = expression;
    }
}
