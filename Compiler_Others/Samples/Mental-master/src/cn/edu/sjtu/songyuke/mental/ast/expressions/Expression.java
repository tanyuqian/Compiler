package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;
import cn.edu.sjtu.songyuke.mental.type.TypeBase;

/**
 * Created by Songyu on 16/3/29.
 */
public class Expression extends BaseNode {
    public boolean leftValue;
    public TypeBase returnType;
    public Expression() {
        this.leftValue = false;
        this.returnType = SymbolTable.mentalUnknownType;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<expression>:" + returnType.toString();
    }
    @Override
    public String toString() {
        return "<unknown expression>";
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof Expression) {
                if (this.returnType.equals(((Expression) other).returnType)) {
                    return true;
                }
            }
        }
        return false;
    }
}
