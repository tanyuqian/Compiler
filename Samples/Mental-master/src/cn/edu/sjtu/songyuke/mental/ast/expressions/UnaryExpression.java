package cn.edu.sjtu.songyuke.mental.ast.expressions;

/**
 * Created by Songyu on 16/3/31.
 */
public class UnaryExpression extends Expression {
    public Expression childExpression;
    public UnaryExpression() {
        this.childExpression = null;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<unknown unary expression>";
    }
}
