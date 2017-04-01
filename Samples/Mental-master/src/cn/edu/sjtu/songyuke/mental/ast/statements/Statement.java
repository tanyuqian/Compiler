package cn.edu.sjtu.songyuke.mental.ast.statements;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;

/**
 * Created by Songyu on 16/3/28.
 */
public abstract class Statement extends BaseNode {
    public Statement() { }
    @Override
    public String toString() {
        return "<statement>";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Statement;
    }
}
