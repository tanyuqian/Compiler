package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/1.
 */
public class AdditiveExpression extends BinaryExpression {
    public int op;
    public static final int ADD = MentalParser.PLUS;
    public static final int SUB = MentalParser.MINUS;
    public AdditiveExpression() {
        this.op = 0;
        this.leftExpression = this.rightExpression = null;
        this.returnType = SymbolTable.mentalUnknownType;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = BaseNode.addIndent(indent) + "<additive expression";
        if (op == ADD) {
            ret += "(+)";
        } else {
            ret += "(-)";
        }
        ret += ">:" + this.returnType.toString() + '\n';
        ret += leftExpression.toPrintString(indent + 1) + '\n';
        ret += rightExpression.toPrintString(indent + 1);
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = BaseNode.addIndent(indent);
        ret += leftExpression.toPrettyPrint();
        if (op == ADD) {
            ret += " + ";
        } else {
            ret += " - ";
        }
        ret += rightExpression.toPrettyPrint();
        return ret;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitAdditiveExpression(this);
    }
}
