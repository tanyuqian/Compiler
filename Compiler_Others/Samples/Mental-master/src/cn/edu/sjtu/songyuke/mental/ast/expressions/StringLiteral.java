package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/31.
 */
public class StringLiteral extends Expression {
    public String literalContext;
    public StringLiteral() {
        this.literalContext = "";
        this.returnType = SymbolTable.MENTAL_M_STRING;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<string-literal>:" + this.literalContext;
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + this.literalContext;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof StringLiteral) {
                if (this.literalContext.equals(((StringLiteral) other).literalContext)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitStringLiteral(this);
    }
}
