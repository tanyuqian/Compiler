package cn.edu.sjtu.songyuke.mental.ast.expressions;

import cn.edu.sjtu.songyuke.mental.ast.ExpressionList;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolFunction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/2.
 */
public class FunctionCall extends Expression {
    public SymbolFunction functionHead;
    public String functionName;
    public ExpressionList parameters;
    public FunctionCall() {
        this.functionHead = null;
        this.functionName = null;
        this.parameters = null;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<function call>:" + this.returnType.toString() + "\n"
                + addIndent(indent + 1) + this.functionHead.functionName;
        if (this.parameters == null || this.parameters.expressions.size() == 0) {
            ret += "()";
            return ret;
        } else {
            ret += "(\n" + addIndent(indent + 1) + this.functionHead.parameterName.get(0) + "=\n"
                    + this.parameters.expressions.get(0).toPrintString(indent + 2);
            for (int i = 1, count = this.parameters.expressions.size(); i < count; ++i) {
                ret += "\n" + addIndent(indent + 1) + this.functionHead.parameterName.get(i) + "=\n"
                        + this.parameters.expressions.get(i).toPrintString(indent + 2);
            }
            ret += "\n" + addIndent(indent + 1) + ")";
            return ret;
        }
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent) + this.functionHead.functionName;
        if (this.parameters == null || this.parameters.expressions.size() == 0) {
            ret += "()";
            return ret;
        } else {
            ret += "("  + this.parameters.expressions.get(0).toPrettyPrint();
            for (int i = 1, count = this.parameters.expressions.size(); i < count; ++i) {
                ret += ", " + this.parameters.expressions.get(i).toPrettyPrint();
            }
            ret += ")";
            return ret;
        }
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitFunctionCall(this);
    }
}
