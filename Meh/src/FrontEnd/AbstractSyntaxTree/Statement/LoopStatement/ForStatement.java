package FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Utility.CompilationError;

/**
 * Created by tan on 4/1/17.
 */
public class ForStatement extends LoopStatement {
    public Expression initialization, condition, increment;
    public Statement statement;

    public ForStatement() {
        this.condition = new BoolConstant(true);
    }

    public ForStatement(Expression initialization, Expression condition, Expression increment, Statement statement) {
        this.initialization = initialization;
        this.condition = condition;
        this.increment = increment;
        this.statement = statement;
    }

    public void addInitialization(Expression expression) {
        this.initialization = expression;
    }

    public void addCondition(Expression expression) {
        if (expression == null) {
            this.condition = new BoolConstant(true);
            return ;
        }
        if (expression.type instanceof BoolType) {
            this.condition = expression;
        } else {
            throw new CompilationError("the condition of for statement must be BoolType");
        }
    }

    public void addIncrement(Expression expression) {
        this.increment = expression;
    }

    public void addStatement(Statement statement) {
        this.statement = statement;
    }
}
