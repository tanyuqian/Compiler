package FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Utility.CompilationError;

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

    public void addCondition(Expression expression) {
        if (!(expression.type instanceof BoolType)) {
            throw new CompilationError("while statements' condition must be BoolType");
        }
        this.condition = expression;
    }

    public void addStatement(Statement statement) {
        this.statement = statement;
    }
}
