package com.abcdabcd987.compiler2016.AST;

import com.abcdabcd987.compiler2016.Symbol.Type;

/**
 * Created by abcdabcd987 on 2016-03-30.
 */
public class FunctionTypeNode extends TypeNode {
    @Override
    public Type.Types getType() {
        return Type.Types.FUNCTION;
    }

    @Override
    public void accept(IASTVisitor visitor) {
        visitor.visit(this);
    }
}
