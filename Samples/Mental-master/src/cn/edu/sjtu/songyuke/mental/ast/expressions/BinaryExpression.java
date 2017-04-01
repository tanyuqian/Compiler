package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;

/**
 * Created by Songyu on 16/3/31.
 */
public class BinaryExpression extends Expression {
    public Expression leftExpression, rightExpression;
    public BinaryExpression() {
        this.leftExpression = new Expression();
        this.rightExpression = new Expression();
    }
    @Override
    public String toPrintString(int indent) {
        String ret = BaseNode.addIndent(indent) + "<binary expression>:" + this.returnType.toString() + '\n';
        ret += leftExpression.toPrintString(indent + 1) + '\n';
        ret += rightExpression.toPrintString(indent + 1);
        return ret;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof BinaryExpression) {
                if (this.leftExpression.equals(((BinaryExpression) other).leftExpression)) {
                    if (this.rightExpression.equals(((BinaryExpression) other).rightExpression)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
