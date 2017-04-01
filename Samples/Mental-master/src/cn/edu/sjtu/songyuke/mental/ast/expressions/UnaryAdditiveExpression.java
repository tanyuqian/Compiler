package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/1.
 */
public class UnaryAdditiveExpression extends UnaryExpression {
    public int op;
    public static final int ADD = MentalParser.PLUS;
    public static final int SUB = MentalParser.MINUS;
    public UnaryAdditiveExpression() {
        this.op = 0;
        this.childExpression = null;
        this.returnType = SymbolTable.mentalInt;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<unary additive expression (";
        if (this.op == ADD) {
            ret += "+):int\n";
        } else {
            ret += "-):int\n";
        }
        ret += this.childExpression.toPrintString(indent + 1);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent);
        if (op == ADD) {
            ret += "+" + this.childExpression.toPrettyPrint();
        } else {
            ret += "-" + this.childExpression.toPrettyPrint();
        }
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitUnaryAdditiveExpression(this);
    }
}
