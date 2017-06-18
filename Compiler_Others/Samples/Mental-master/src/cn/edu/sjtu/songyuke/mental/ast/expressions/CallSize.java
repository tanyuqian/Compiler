package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class CallSize extends Expression {
    public CallSize() {
        this.returnType = SymbolTable.mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<array.size()>:int";
    }
    @Override
    public String toPrettyPrint(int indent) {
        return "size()";
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return new LinkedList<>();
    }
}
