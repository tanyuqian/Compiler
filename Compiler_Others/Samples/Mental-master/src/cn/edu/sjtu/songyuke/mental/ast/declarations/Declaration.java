package cn.edu.sjtu.songyuke.mental.ast.declarations;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;

/**
 * Created by Songyu on 16/3/28.
 */
public abstract class Declaration extends BaseNode {
    @Override
    public String toPrintString(int indent) {
        return this.addIndent(indent) + "<declaration>";
    }
    @Override
    public String toString() {
        return "<declaration>";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Declaration;
    }
}
