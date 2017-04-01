package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/31.
 */
public class NullConstant extends Expression {
    public NullConstant() {
        this.returnType = SymbolTable.mentalNull;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<null>";
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + "null";
    }
    @Override
    public boolean equals(Object other) {
        if (other != null) {
            if (other instanceof NullConstant) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitNullConstant(this);
    }
}
