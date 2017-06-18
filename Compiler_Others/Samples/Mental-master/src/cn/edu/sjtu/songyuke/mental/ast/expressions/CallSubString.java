package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class CallSubString extends BinaryExpression {
    public CallSubString() {
        this.returnType = SymbolTable.MENTAL_M_STRING;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<string.substring(int,int)>:string\n";
        ret += addIndent(indent + 1) + "<left bound>\n" + this.leftExpression.toPrintString(indent + 2) + "\n";
        ret += addIndent(indent + 1) + "<right bound>\n" + this.rightExpression.toPrintString(indent + 2);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = "substring(";
        ret += this.leftExpression.toPrettyPrint() + ", ";
        ret += this.rightExpression.toPrettyPrint() + ")";
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitCallSubString(this);
    }
}
