package com.abcdabcd987.compiler2016.AST;

/**
 * Created by abcdabcd987 on 2016-03-28.
 */
public class NullLiteral extends Expr {

    @Override
    public void accept(IASTVisitor visitor) {
        visitor.visit(this);
    }
}
