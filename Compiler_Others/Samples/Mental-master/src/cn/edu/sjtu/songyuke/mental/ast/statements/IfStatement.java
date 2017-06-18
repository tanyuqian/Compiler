package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ast.expressions.Expression;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/31.
 */
public class IfStatement extends Statement {
    public Expression condition;
    public Statement thenStatement;
    public Statement elseStatement;
    public IfStatement() {
        this.condition = null;
        this.thenStatement = null;
        this.elseStatement = null;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<if statement>\n";
        ret += addIndent(indent + 1) + "<condition>\n" + this.condition.toPrintString(indent + 2) + '\n';
        ret += addIndent(indent + 1) + "<then statement>\n" + this.thenStatement.toPrintString(indent + 2);
        if (this.elseStatement != null) {
            ret += '\n' + addIndent(indent + 1) + "<else statement>\n" + this.elseStatement.toPrintString(indent + 2);
        }
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent) + "if (";
        ret += this.condition.toPrettyPrint() + ") ";
        if (this.thenStatement instanceof CompoundStatement) {
            ret += this.thenStatement.toPrettyPrint(indent + 1);
        } else {
            ret += "\n" + this.thenStatement.toPrettyPrint(indent + 1);
        }
        if (this.elseStatement != null) {
            if (!(this.thenStatement instanceof CompoundStatement)) {
                ret += "\n";
            }
            ret += "else ";
            if (this.elseStatement instanceof CompoundStatement) {
                ret += this.elseStatement.toPrettyPrint(indent + 1);
            } else {
                ret += "\n" + this.elseStatement.toPrettyPrint(indent + 1);
            }
        }
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitIfStatement(this);
    }
}
