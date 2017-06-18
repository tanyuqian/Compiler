package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class CallGetInt extends Expression {
    public CallGetInt() {
        this.returnType = SymbolTable.mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<getInt()>:int";
    }
    @Override
    public String toPrettyPrint(int indent) {
        return "getInt()";
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitCallGetInt(this);
    }
}
