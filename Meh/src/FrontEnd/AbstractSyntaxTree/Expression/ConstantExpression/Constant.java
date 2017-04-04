package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public abstract class Constant extends Expression {
    public Constant(Type type, boolean isLeftValue) {
        super(type, isLeftValue);
    }
}
