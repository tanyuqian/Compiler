package cn.edu.sjtu.songyuke.mental.ast;

import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/30.
 */
public class Program extends BaseNode {
    public LinkedList<BaseNode> declarations;
    public Program() {
        this.declarations = new LinkedList<>();
    }
    @Override
    public String toPrintString(int indent) {
        String ret = addIndent(indent) + "<begin>program\n";
        if (this.declarations != null) {
            for (BaseNode e : this.declarations) {
                ret += e.toPrintString(indent + 1) + '\n';
            }
        }
        ret += addIndent(indent) + "<end>program";
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        String ret = addIndent(indent);
        if (this.declarations != null) {
            for (BaseNode e : this.declarations) {
                ret += e.toPrettyPrint(indent) + "\n";
            }
        }
        return ret;
    }
    @Override
    public String toString() {
        return "<program>";
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof Program) {
                if (this.declarations.equals(((Program) other).declarations)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitProgram(this);
    }
}
