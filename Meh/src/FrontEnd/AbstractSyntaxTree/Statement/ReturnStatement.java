package FrontEnd.AbstractSyntaxTree.Statement;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Utility.CompilationError;

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

    public static ReturnStatement getStatement(Expression expression) {
        Function function = Environment.scopeTable.getFunctionScope();
        if (function == null) {
            throw new CompilationError("return statements must be in some function.");
        }
        if (expression == null) {
            if (function.type instanceof VoidType) {
                return new ReturnStatement(function, expression);
            }
        } else {
            if (function.type.compatibleWith(expression.type)) {
                return new ReturnStatement(function, expression);
            }
        }
        throw new CompilationError("return expression must have compatible type with declaration.");
    }
}
