package cn.edu.sjtu.songyuke.mental.ast;
import cn.edu.sjtu.songyuke.mental.ir.AstVisitor;
import cn.edu.sjtu.songyuke.mental.ir.Instruction;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/28.
 */
public abstract class BaseNode {
    public BaseNode parent;
    public BaseNode() {
        this.parent = null;
    }
    public static String addIndent(int indent) {
        String ret = "";
        for (int i = 0; i < indent; ++i) {
            ret += "    ";
        }
        return ret;
    }
    public String toPrintString() {
        return this.toPrintString(0);
    }
    public String toPrintString(int indent) {
        String ret = "";
        ret += addIndent(indent) + "<BASE NODE>";
        return ret;
    }
    public String toPrettyPrint() {
        return this.toPrettyPrint(0);
    }
    public String toPrettyPrint(int indent) {
        String ret = "";
        ret += addIndent(indent) + "";
        return ret;
    }
    @Override
    public String toString() {
        return "<BASE NODE>";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof BaseNode;
    }

    public LinkedList<Instruction> visit(AstVisitor visitor) {
        return visitor.visitBase(this);
    }
}
