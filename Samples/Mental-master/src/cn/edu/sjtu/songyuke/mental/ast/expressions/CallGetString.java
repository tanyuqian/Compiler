package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class CallGetString extends Expression {
    public CallGetString() {
        this.returnType = SymbolTable.MENTAL_M_STRING;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<getString()>:string";
    }
    @Override
    public String toPrettyPrint(int indent) {
        return "getString()";
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitCallGetString(this);
    }
}