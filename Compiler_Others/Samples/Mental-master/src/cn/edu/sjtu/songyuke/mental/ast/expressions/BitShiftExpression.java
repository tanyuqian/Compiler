package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;

import java.util.LinkedList;

import static cn.edu.sjtu.songyuke.mental.symbols.SymbolTable.mentalInt;

/**
 * Created by Songyu on 16/4/2.
 */
public class BitShiftExpression extends BinaryExpression {
    public static final int LEFT_SHIFT = MentalParser.LEFT_SHIFT;
    public static final int RIGHT_SHIFT = MentalParser.RIGHT_SHIFT;
    public int op;
    public BitShiftExpression() {
        this.op = 0;
        this.returnType = mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<bit shift expression";
        if (this.op == LEFT_SHIFT) {
            ret += "(<<)";
        } else {
            ret += "(>>)";
        }
        ret += ">:" + this.returnType.toString() + '\n';
        ret += this.leftExpression.toPrintString(indent + 1) + '\n';
        ret += this.rightExpression.toPrintString(indent + 1);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent);
        ret += this.leftExpression.toPrettyPrint();
        if (this.op == LEFT_SHIFT) {
            ret += " << ";
        } else {
            ret += " >> ";
        }
        ret += this.rightExpression.toPrettyPrint();
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitBitShiftExpression(this);
    }
}
