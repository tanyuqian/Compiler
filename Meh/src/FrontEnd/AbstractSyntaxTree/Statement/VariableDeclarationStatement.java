package FrontEnd.AbstractSyntaxTree.Statement;

import Environment.Symbol;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Utility.CompilationError;

/**
 * Created by tan on 4/1/17.
 */
public class VariableDeclarationStatement extends Statement {
    public Symbol symbol;
    public Expression expression;

    public VariableDeclarationStatement(Symbol symbol, Expression expression) {
        this.symbol = symbol;
        this.expression = expression;
    }

    public static VariableDeclarationStatement getStatement(Symbol symbol, Expression expression) {
        if (symbol.type instanceof VoidType) {
            throw new CompilationError("variable declaration: left should not be an VoidType..");
        }
        if (expression == null || symbol.type.compatibleWith(expression.type)) {
            return new VariableDeclarationStatement(symbol, expression);
        }
        throw new CompilationError("variable declaration: the types is not match. name is \"" + symbol.name + "\"");
    }
}
