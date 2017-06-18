package cn.edu.sjtu.songyuke.mental.type;

import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;

/**
 * Created by Songyu on 16/3/29.
 */
public class Array extends TypeBase {
    public int arrayDim;
    public TypeBase arrayType;
    public String typeName;
    public Array() {
        this.arrayDim = 0;
        this.arrayType = null;
        this.typeName = null;
    }
    public Array(Array other) {
        this.arrayType = other.arrayType;
        this.arrayDim = other.arrayDim;
        this.typeName = other.typeName;
    }
    public Array(MentalParser.TypeContext typeCtx) {
        this.arrayType = null;
        this.arrayDim = typeCtx.array().size();
        this.typeName = typeCtx.typeName().getText();
        if (this.typeName.equals("int")) {
            this.arrayType = SymbolTable.mentalInt;
        } else if (this.typeName.equals("bool")) {
            this.arrayType = SymbolTable.mentalBool;
        } else if (this.typeName.equals("string")) {
            this.arrayType = SymbolTable.MENTAL_M_STRING;
        } else {
            this.typeName = typeCtx.typeName().getText();
            this.arrayType = SymbolTable.mentalUnknownType;
        }
    }
    @Override
    public String toString() {
        String ret = this.arrayType.toString();
        for (int i = 0; i < this.arrayDim; ++i) {
            ret += "[]";
        }
        return ret;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof Null) {
                return true;
            }
            if (other instanceof Array) {
                if (this.arrayType.equals(((Array) other).arrayType)) {
                    if (this.arrayDim == ((Array) other).arrayDim) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
