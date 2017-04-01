package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/1.
 */
public class PrefixExpression extends UnaryExpression {
    public static final int PLUS_PLUS = MentalParser.INC;
    public static final int MINUS_MINUS = MentalParser.DEC;
    public int op;
    public PrefixExpression() {
        this.leftValue = false;
        this.op = 0;
        this.returnType = SymbolTable.mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<prefix expression (";
        if (op == PLUS_PLUS) {
            ret += "++)>:int\n" + this.childExpression.toPrintString(indent + 1);
        } else {
            ret += "--)>:int\n" + this.childExpression.toPrintString(indent + 1);
        }
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent);
        if (op == PLUS_PLUS) {
            ret += "++" + this.childExpression.toPrettyPrint();
        } else {
            ret += "--" + this.childExpression.toPrettyPrint();
        }
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitPrefixExpression(this);
    }
}
