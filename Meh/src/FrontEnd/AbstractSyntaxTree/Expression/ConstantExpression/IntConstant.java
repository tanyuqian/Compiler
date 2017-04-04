package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class IntConstant extends Constant {
    public int number;

    public IntConstant(int number) {
        super(new IntType());
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
