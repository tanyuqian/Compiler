package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class StringConstant extends Constant {
    public String str;

    public StringConstant(Type type, boolean isLeftValue, String str) {
        super(type, isLeftValue);
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
