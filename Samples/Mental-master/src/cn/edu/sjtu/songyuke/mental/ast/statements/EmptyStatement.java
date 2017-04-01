package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/30.
 */
public class EmptyStatement extends Statement {
    public EmptyStatement() { }
    @Override
    public String toPrintString(int indent) {
        return addIndent(indent) + "<empty statement>";
    }
    @Override
    public String toPrettyPrint(int indent) {
        return addIndent(indent) + ";";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof EmptyStatement;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitEmptyStatement(this);
    }
}
