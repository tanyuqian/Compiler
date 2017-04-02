package FrontEnd.AbstractSyntaxTree.Statement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;

/**
 * Created by tan on 4/1/17.
 */
public class VariableDeclarationStatement extends Statement {
    public String variable;
    public Expression expression;

    public VariableDeclarationStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }
}
