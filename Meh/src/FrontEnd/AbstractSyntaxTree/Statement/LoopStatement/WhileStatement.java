package FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;

/**
 * Created by tan on 4/1/17.
 */
public class WhileStatement extends LoopStatement {
    public Expression condition;
    public Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }
}
