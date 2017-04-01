package cn.edu.sjtu.songyuke.mental.ast.expressions;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/5/3.
 */
public class SuperExpression extends Expression {
    public LinkedList<Expression> expressions;
    public SuperExpression() {
        this.expressions = new LinkedList<>();
    }
}
