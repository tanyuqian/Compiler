package FrontEnd.AbstractSyntaxTree.Statement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Function;

/**
 * Created by tan on 4/1/17.
 */
public class ReturnStatement extends Statement {
    public Function owner;
    public Expression expression;

    public ReturnStatement(Function owner, Expression expression) {
        this.owner = owner;
        this.expression = expression;
    }
}
