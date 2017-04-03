package FrontEnd.AbstractSyntaxTree.Statement;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Utility.CompilationError;

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

    public static SelectionStatement getStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
        if (!(condition.type instanceof BoolType)) {
            throw new CompilationError("Selection Statements' condition must be BoolType");
        }
        return new SelectionStatement(condition, trueStatement, falseStatement);
    }
}
