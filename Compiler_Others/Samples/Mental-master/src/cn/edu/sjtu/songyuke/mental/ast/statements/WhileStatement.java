package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ast.expressions.Expression;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/30.
 */
public class WhileStatement extends Statement {
    public Expression cond;
    public Statement loopBody;
    public WhileStatement() {
        this.cond = null;
        this.loopBody = null;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<while expression>\n";
        ret += addIndent(indent + 1) + "<condition expression>\n" + this.cond.toPrintString(indent + 2) + "\n";
        ret += addIndent(indent + 1) + "<loop body>\n" + this.loopBody.toPrintString(indent + 2);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent) + "while (";
        ret += this.cond.toPrettyPrint() + ") ";
        if (this.loopBody instanceof CompoundStatement) {
            ret += this.loopBody.toPrettyPrint(indent + 1);
        } else {
            ret += "\n" + this.loopBody.toPrettyPrint(indent + 1);
        }
        return ret;
    }
    @Override
    public String toString() {
        return "<while loopBody>";
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof WhileStatement) {
                if (this.cond.equals(((WhileStatement) other).cond)) {
                    if (this.loopBody.equals(((WhileStatement) other).loopBody)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitWhileStatement(this);
    }
}
