package cn.edu.sjtu.songyuke.mental.ast;

import cn.edu.sjtu.songyuke.mental.type.TypeBase;

/**
 * Created by Songyu on 16/3/30.
 */
public class Variable extends BaseNode {
    public String variableName;
    public TypeBase variableType;
    public int globalID;
    public int localID;

    public Variable() {
        this.variableName = null;
        this.variableType = null;
        this.globalID = 0;
    }
    @Override
    public String toPrintString(int indent) {
        return this.toString();
    }
    @Override
    public String toPrettyPrint(int indent) {
        return this.variableName;
    }
    @Override
    public String toString() {
        return "[" + this.variableName + "@" + this.variableType.toString() + "], global id:" + Integer.toString(this.globalID) + ", local id:" + Integer.toString(this.localID);
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof Variable) {
                if (this.variableName.equals(((Variable) other).variableName)) {
                    if (this.variableType.equals(((Variable) other).variableType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
