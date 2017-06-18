package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ast.Variable;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/1.
 */
public class Identifier extends Expression {
    public String name;
    public Variable variable;
    public Identifier() {
        this.name = "";
        this.returnType = SymbolTable.mentalUnknownType;
        this.variable = null;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<identifier>" + this.variable.toString();
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + this.name;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof Identifier) {
                if (this.name.equals(((Identifier) other).name)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitIdentifier(this);
    }
}
