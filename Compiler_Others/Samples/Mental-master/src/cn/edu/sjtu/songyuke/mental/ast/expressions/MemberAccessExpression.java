package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class MemberAccessExpression extends Expression {
    public Expression primaryExpression;
    public Expression memberExpression;
    public String memberName;
    public MemberAccessExpression() {
        this.primaryExpression = null;
        this.memberExpression = null;
        this.memberName = null;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<member access expression>:" + this.returnType + "\n";
        ret += addIndent(indent + 1) + "<primary expression>\n" + this.primaryExpression.toPrintString(indent + 2) + "\n";
        ret += addIndent(indent + 1) + "<member expression>\n";
        if (this.memberName == null) {
            ret += this.memberExpression.toPrintString(indent + 2) + "\n";
        } else {
            ret += addIndent(indent + 2) + "<member>" + this.memberName;
        }
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent);
        ret += this.primaryExpression.toPrettyPrint() + ".";
        if (this.memberName == null) {
            ret += this.memberExpression.toPrettyPrint();
        } else {
            ret += this.memberName;
        }
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitMemberAccessExpression(this);
    }
}
