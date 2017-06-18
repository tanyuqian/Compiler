package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/31.
 */
public class IntLiteral extends Expression {
    public int literalContext;
    public IntLiteral() {
        this.literalContext = 0;
        this.returnType = SymbolTable.mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<int-literal>:" + Integer.toString(literalContext);
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + Integer.toString(literalContext);
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof IntLiteral) {
                if (this.literalContext == ((IntLiteral) other).literalContext) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitIntLiteral(this);
    }
}
