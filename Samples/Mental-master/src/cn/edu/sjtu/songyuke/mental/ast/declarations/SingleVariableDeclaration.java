package cn.edu.sjtu.songyuke.mental.ast.declarations;

import cn.edu.sjtu.songyuke.mental.ast.expressions.Expression;
import cn.edu.sjtu.songyuke.mental.ast.Variable;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.type.TypeBase;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/30.
 */
public class SingleVariableDeclaration extends Declaration {
    public Variable variable;
    public Expression initializeExpression;
    public SingleVariableDeclaration() {
        this.variable = null;
        this.initializeExpression = null;
    }
    public SingleVariableDeclaration(TypeBase type, MentalParser.SingleVariableContext ctx) {
        this.variable = new Variable();
    }
    @Override
    public String toPrintString(int indent) {
        String ret = "";
        ret += addIndent(indent) + "<variable>" + this.variable.toString();
        if (this.initializeExpression != null) {
            ret += "\n" + addIndent(indent + 1) + "<initial value>\n" + this.initializeExpression.toPrintString(indent + 2);
        }
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = "";
        ret += this.variable.toPrettyPrint();
        if (this.initializeExpression != null) {
            ret += " = " + this.initializeExpression.toPrettyPrint();
        }
        return ret;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.variable != null) {
            ret += this.variable.toString();
        } else {
            return ret;
        }
        if (this.initializeExpression != null) {
            ret +=  "=" + this.initializeExpression.toString();
        }
        return ret;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof SingleVariableDeclaration) {
                if (this.variable.equals(((SingleVariableDeclaration) other).variable)) {
                    if (this.initializeExpression.equals(((SingleVariableDeclaration) other).initializeExpression)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitSingleVariableDeclaration(this);
    }
}
