package FrontEnd.AbstractSyntaxTree.Expression;

import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class FunctionCallExpression extends Expression {
    public Function function;
    public List<Expression> parameters;

    public FunctionCallExpression(Type type, boolean isLeftValue, Function function, List<Expression> parameters) {
        super(type, isLeftValue);
        this.function = function;
        this.parameters = parameters;
    }
}
