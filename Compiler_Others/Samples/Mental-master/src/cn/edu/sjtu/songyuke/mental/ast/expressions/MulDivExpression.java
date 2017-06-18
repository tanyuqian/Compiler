package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/1.
 */
public class MulDivExpression extends BinaryExpression {
    public static final int MUL = MentalParser.MUL;
    public static final int DIV = MentalParser.DIV;
    public static final int MOD = MentalParser.MOD;
    public int op;
    public MulDivExpression() {
        this.returnType = SymbolTable.mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<multiply expression";
        if (op == MUL) {
            ret += "(*)";
        } else if (op == DIV) {
            ret += "(/)";
        } else {
            ret += "(%)";
        }
        ret += ">:" + this.returnType.toString() + '\n';
        ret += leftExpression.toPrintString(indent + 1) + '\n';
        ret += rightExpression.toPrintString(indent + 1);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent);
        ret += leftExpression.toPrettyPrint();
        if (op == MUL) {
            ret += " * ";
        } else if (op == DIV) {
            ret += " / ";
        } else {
            ret += " % ";
        }
        ret += rightExpression.toPrettyPrint();
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitMulDivExpression(this);
    }
}
