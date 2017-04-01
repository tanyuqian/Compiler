package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

/**
 * Created by tan on 3/30/17.
 */
public class StringConstant extends Constant {
    public String str;

    public StringConstant(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
