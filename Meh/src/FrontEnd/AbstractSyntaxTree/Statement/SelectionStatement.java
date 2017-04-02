package FrontEnd.AbstractSyntaxTree.Statement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;

/**
 * Created by tan on 4/1/17.
 */
public class SelectionStatement extends Statement {
    public Expression condition;
    public Statement trueStatement, falseStatement;

    public SelectionStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }
}
