package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/5/3.
 */
public class SuperLogicalAndExpression extends SuperExpression {
    public SuperLogicalAndExpression() {
        super();
        this.returnType = SymbolTable.mentalBool;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitSuperLogicalAndExpression(this);
    }
}
