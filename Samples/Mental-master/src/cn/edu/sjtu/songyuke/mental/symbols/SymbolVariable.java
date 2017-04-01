package cn.edu.sjtu.songyuke.mental.symbols;

import cn.edu.sjtu.songyuke.mental.ast.Variable;
import cn.edu.sjtu.songyuke.mental.type.TypeBase;

/**
 * Created by Songyu on 16/3/30.
 */
public class SymbolVariable extends SymbolBase {
    public Variable variable;
    public SymbolVariable() {
        this.variable = new Variable();
        this.variable.variableName = null;
        this.variable.variableType = null;
        this.variable.globalID = 0;
        this.stackLayer = 0;
    }
    public SymbolVariable(SymbolTable scope, TypeBase type, String name) {
        this.variable = new Variable();
        this.stackLayer = scope.stackLayer;
        this.variable.variableType = type;
        this.variable.variableName = name;
        this.variable.globalID = 0;
    }
    public SymbolVariable(SymbolTable scope, TypeBase type, String name, int id) {
        this.variable = new Variable();
        this.stackLayer = scope.stackLayer;
        this.variable.variableType = type;
        this.variable.variableName = name;
        this.variable.globalID = id;
    }
    @Override
    public String toString() {
        return "<variable>" + this.variable.variableName + '[' + this.variable.variableType.toString() + "],id:" + Integer.toString(this.variable.globalID);
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof SymbolVariable) {
                if (this.variable.globalID == ((SymbolVariable) other).variable.globalID) {
                    return true;
                }
            }
        }
        return false;
    }
}
