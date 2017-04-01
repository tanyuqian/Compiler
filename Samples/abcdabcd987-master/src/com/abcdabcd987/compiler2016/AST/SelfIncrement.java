package com.abcdabcd987.compiler2016.AST;

/**
 * Created by abcdabcd987 on 2016-03-26.
 */
public class SelfIncrement extends Expr {
    public final Expr self;
    public final SourcePosition posSelf;

    public SelfIncrement(Expr self, SourcePosition posSelf) {
        this.self = self;
        this.posSelf = posSelf;
    }

    @Override
    public void accept(IASTVisitor visitor) {
        visitor.visit(this);
    }
}
