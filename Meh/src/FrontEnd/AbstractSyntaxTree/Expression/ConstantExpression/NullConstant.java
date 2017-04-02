package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class NullConstant extends Constant {
    public NullConstant(Type type) {
        super(type);
    }

    @Override
    public String toString() {
        return "null";
    }
}
