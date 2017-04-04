package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class NullConstant extends Constant {
    public NullConstant(Type type, boolean isLeftValue) {
        super(type, isLeftValue);
    }

    @Override
    public String toString() {
        return "null";
    }
}
