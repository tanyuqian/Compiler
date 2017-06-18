package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class CallOrd extends UnaryExpression {
    public CallOrd() {
        this.returnType = SymbolTable.mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<string.ord(int)>:int\n" + this.childExpression.toPrintString(indent + 1);
    }
    @Override
    public String toPrettyPrint(int indent) {
        return "ord(" + this.childExpression.toPrettyPrint() + ")";
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitCallOrd(this);
    }
}
