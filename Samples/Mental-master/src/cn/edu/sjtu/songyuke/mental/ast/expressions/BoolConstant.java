package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/31.
 */
public class BoolConstant extends Expression {
    public boolean boolConstant;
    public BoolConstant() {
        this.returnType = SymbolTable.mentalBool;
    }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<bool>:" + Boolean.toString(this.boolConstant);
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + Boolean.toString(this.boolConstant);
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitBoolConstant(this);
    }
}
