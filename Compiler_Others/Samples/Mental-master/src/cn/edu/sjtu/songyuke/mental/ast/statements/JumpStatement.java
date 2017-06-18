package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ast.expressions.Expression;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/31.
 */
public class JumpStatement extends Statement {
    public static final int RETURN = 1000;
    public static final int CONTINUE = 2000;
    public static final int BREAK = 3000;
    public int variant;
    public Expression returnExpression;
    public JumpStatement() {
        this.variant = 0;
        this.returnExpression = null;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = "";
        if (this.variant == RETURN) {
            ret += addIndent(indent) + "<jump statement>@return\n" + this.returnExpression.toPrintString(indent + 1);
        } else if (this.variant == CONTINUE) {
            ret += addIndent(indent) + "<jump statement>@continue";
        } else {
            ret += addIndent(indent) + "<jump statement>@break";
        }
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = "";
        if (this.variant == RETURN) {
            ret += addIndent(indent) + "return";
            if (this.returnExpression.returnType.equals(SymbolTable.mentalVoid)) {
                ret += ";";
            } else {
                ret += " " + this.returnExpression.toPrettyPrint() + ";";
            }
        } else if (this.variant == CONTINUE) {
            ret += addIndent(indent) + "continue;";
        } else {
            ret += addIndent(indent) + "break;";
        }
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitJumpStatement(this);
    }
}
