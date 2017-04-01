package cn.edu.sjtu.songyuke.mental.ast;

import cn.edu.sjtu.songyuke.mental.ast.expressions.Expression;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Songyu on 16/4/2.
 */
public class ExpressionList extends BaseNode {
    public List<Expression> expressions;
    public ExpressionList() {
        this.expressions = new ArrayList<>();
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitExpressionList(this);
    }
}
