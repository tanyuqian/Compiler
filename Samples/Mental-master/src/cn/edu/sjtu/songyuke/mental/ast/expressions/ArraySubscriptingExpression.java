package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class ArraySubscriptingExpression extends Expression {
    public Expression primaryExpression;
    public Expression positionExpression;
    public ArraySubscriptingExpression() {
        this.leftValue = true;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<array subscripting expression>:" + this.returnType + "\n";
        ret += addIndent(indent + 1) + "<primary expression>\n" + this.primaryExpression.toPrintString(indent + 2) + "\n";
        ret += addIndent(indent + 1) + "<position expression>\n" + this.positionExpression.toPrintString(indent + 2);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent);
        ret += this.primaryExpression.toPrettyPrint() + "[";
        ret += this.positionExpression.toPrettyPrint() + "]";
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitArraySubscriptingExpression(this);
    }
}
