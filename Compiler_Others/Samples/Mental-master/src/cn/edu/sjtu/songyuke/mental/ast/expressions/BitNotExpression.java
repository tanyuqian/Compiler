package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/1.
 */
public class BitNotExpression extends UnaryExpression {
    public BitNotExpression() {
        this.childExpression = null;
        this.returnType = SymbolTable.mentalInt;
    }

    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<bit not expression>:int\n";
        ret += this.childExpression.toPrintString(indent + 1);
        return ret;
    }

    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent) + "~";
        ret += this.childExpression.toPrettyPrint();
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitBitNotExpression(this);
    }
}
