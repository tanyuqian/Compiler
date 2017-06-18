package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ast.declarations.VariableDeclaration;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/31.
 */
public class VarStatement extends Statement {
    public VariableDeclaration variableDeclaration;
    public VarStatement() {
        this.variableDeclaration = new VariableDeclaration();
    }
    @Override
    public String toPrintString(int indent) {
        return this.variableDeclaration.toPrintString(indent);
    }
    @Override
    public String toPrettyPrint(int indent) {
        return this.variableDeclaration.toPrettyPrint(indent);
    }
    @Override
    public String toString() {
        return "<variable declaration loopBody>";
    }
    @Override
    public boolean equals(Object other) {
        if (other != null) {
            if (other instanceof VarStatement) {
                if (this.variableDeclaration.equals(((VarStatement) other).variableDeclaration)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitAstVarStatement(this);
    }
}
