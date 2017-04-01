package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class CallToString extends UnaryExpression {
    public CallToString() {
        this.returnType = SymbolTable.MENTAL_M_STRING;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<toString(int)>:string\n";
        ret += this.childExpression.toPrintString(indent + 1);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + "toString(" + this.childExpression.toPrettyPrint() + ")";
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitCallToString(this);
    }
}
