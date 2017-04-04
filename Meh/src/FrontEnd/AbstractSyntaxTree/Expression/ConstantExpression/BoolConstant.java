package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/30/17.
 */
public class BoolConstant extends Constant {
    public boolean tag;

    public BoolConstant(boolean tag) {
        super(new BoolType());
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.valueOf(tag);
    }
}
