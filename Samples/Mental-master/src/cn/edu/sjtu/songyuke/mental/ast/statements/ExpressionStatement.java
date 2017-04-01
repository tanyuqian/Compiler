package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ast.expressions.Expression;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/1.
 */
public class ExpressionStatement extends Statement {
    public Expression expression;
    public ExpressionStatement() {
        this.expression = null;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<expression statment>\n" + this.expression.toPrintString(indent + 1);
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + this.expression.toPrettyPrint() + ";";
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitExpressionStatement(this);
    }
}
