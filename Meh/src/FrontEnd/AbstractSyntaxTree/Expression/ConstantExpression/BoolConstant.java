package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

/**
 * Created by tan on 3/30/17.
 */
public class BoolConstant extends Constant {
    public boolean tag;

    public BoolConstant(boolean tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.valueOf(tag);
    }
}
