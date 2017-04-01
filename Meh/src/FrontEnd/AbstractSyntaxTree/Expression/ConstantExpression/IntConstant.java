package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

/**
 * Created by tan on 3/30/17.
 */
public class IntConstant extends Constant {
    public int number;

    public IntConstant(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
