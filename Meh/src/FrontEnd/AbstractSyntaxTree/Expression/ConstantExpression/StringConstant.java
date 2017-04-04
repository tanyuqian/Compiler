package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class StringConstant extends Constant {
    public String str;

    public StringConstant(String str) {
        super(new StringType());
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
