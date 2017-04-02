package FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;

/**
 * Created by tan on 4/1/17.
 */
public class ForStatement extends LoopStatement {
    public Expression initialization, condition, increment;
    public Statement statement;

    public ForStatement(Expression initialization, Expression condition, Expression increment, Statement statement) {
        this.initialization = initialization;
        this.condition = condition;
        this.increment = increment;
        this.statement = statement;
    }
}
