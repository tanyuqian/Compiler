package cn.edu.sjtu.songyuke.mental.ast.declarations;

import cn.edu.sjtu.songyuke.mental.ast.statements.CompoundStatement;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolFunction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/30.
 */
public class FunctionDefinition extends Declaration {
    public SymbolFunction functionHead;
    public CompoundStatement functionBody;
    public int firstVariableID;
    public int lastVariableID;
    public FunctionDefinition() {
        this.functionHead = new SymbolFunction();
        this.functionBody = new CompoundStatement();
        this.firstVariableID = 0;
        this.lastVariableID = 0;
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<begin>function\n";
        ret += addIndent(indent + 1) + "<format>" + this.functionHead.toString().substring(10) + '\n';
        ret += addIndent(indent + 1) + "<begin function body>\n";
        ret += this.functionBody.toPrintString(indent + 2) + '\n';
        ret += addIndent(indent + 1) + "<end function body>\n";
        ret += addIndent(indent) + "<end>function";
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent)
                + this.functionHead.returnType.toString() + " " + this.functionHead.functionName + "(";
        if (this.functionHead.parameterName.size() > 0) {
            ret += this.functionHead.parameterType.get(0).toString() + " " + this.functionHead.parameterName.get(0);
            for (int i = 1, count = this.functionHead.parameterName.size(); i < count; ++i) {
                ret += "," + this.functionHead.parameterType.get(i).toString() + " " + this.functionHead.parameterName.get(i);
            }
        }
        ret += ") ";
        ret += this.functionBody.toPrettyPrint(indent + 1) + '\n';
        return ret;
    }
    @Override
    public String toString() {
        if (this.functionHead == null || this.functionBody == null) {
            return "";
        }
        return this.functionHead.toString() + ":" + this.functionBody.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof FunctionDefinition) {
                if (this.functionHead.equals(((FunctionDefinition) other).functionHead)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitFunctionDefinition(this);
    }
}
