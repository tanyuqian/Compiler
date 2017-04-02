package FrontEnd.AbstractSyntaxTree.Statement;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.Constant;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;

/**
 * Created by tan on 4/1/17.
 */
public class ContinueStatement extends Statement {
    public LoopStatement owner;

    public ContinueStatement(LoopStatement owner) {
        this.owner = owner;
    }
}
