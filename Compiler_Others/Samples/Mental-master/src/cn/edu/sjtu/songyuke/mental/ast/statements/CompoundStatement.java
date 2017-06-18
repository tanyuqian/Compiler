package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Songyu on 16/3/30.
 */
public class CompoundStatement extends Statement {
    public List<BaseNode> statements;
    public CompoundStatement() {
        this.statements = new LinkedList<>();
    }
    @Override
    public String toPrintString(int indent) {
        String ret = "";
        if (this.statements.size() == 0) {
            ret += BaseNode.addIndent(indent) + "<empty component loopBody>";
        } else {
            for (BaseNode statement : this.statements) {
                ret += statement.toPrintString(indent) + '\n';
            }
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = "{\n";
        if (this.statements.size() > 0) {
            for (BaseNode statement : this.statements) {
                ret += statement.toPrettyPrint(indent) + '\n';
            }
        }
        ret += addIndent(indent - 1) + "}";
        return ret;
    }
    @Override
    public String toString() {
        if (this.statements == null) {
            return "<empty component loopBody>";
        } else {
            return "<component>[" + this.statements.size() + " statements]";
        }
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof CompoundStatement) {
                if (this.statements.equals(((CompoundStatement) other).statements)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitCompoundStatement(this);
    }
}
