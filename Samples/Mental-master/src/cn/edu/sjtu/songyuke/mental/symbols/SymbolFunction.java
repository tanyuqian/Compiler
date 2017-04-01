package cn.edu.sjtu.songyuke.mental.symbols;

import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.type.Array;
import cn.edu.sjtu.songyuke.mental.type.TypeBase;

import java.util.ArrayList;

/**
 * Created by Songyu on 16/3/29.
 */
public class SymbolFunction extends SymbolBase {
    public TypeBase returnType;
    public ArrayList<String> parameterName;
    public ArrayList<TypeBase> parameterType;
    public String functionName;
    public SymbolFunction() {
        returnType = SymbolTable.mentalUnknownType;
        parameterName = new ArrayList<>();
        parameterType = new ArrayList<>();
        functionName = "";
    }
    public SymbolFunction(SymbolFunction other) {
        this.returnType = other.returnType;
        this.parameterName = other.parameterName;
        this.parameterType = other.parameterType;
        this.functionName = other.functionName;
    }
    // Constructor a function symbol from a given scope and FunctionDefinitionContext.
    public boolean setFunction(SymbolTable scope, MentalParser.FunctionDefinitionContext funcDefCtx) {
        boolean existError = false;
        this.stackLayer = SymbolTable.maxLayer;
        // Set the name of the function.
        this.functionName = funcDefCtx.functionName.getText();
        this.parameterName = new ArrayList<>();
        this.parameterType = new ArrayList<>();

        // Determine the cn.edu.sjtu.songyuke.mental.type of return value of the function.
        if (funcDefCtx.type() == null) {
            this.returnType = SymbolTable.mentalVoid;
        } else {
            SymbolBase baseType = scope.getSymbol(funcDefCtx.type().typeName().getText());
            if (baseType == null || !(baseType instanceof SymbolType)) {
                System.err.println("fatal: no such a cn.edu.sjtu.songyuke.mental.type " + funcDefCtx.type().typeName().getText());
                existError = true;
                System.exit(1);
            }
            if (funcDefCtx.type().array().size() != 0) {
                this.returnType = new Array(funcDefCtx.type());
                if (((Array) this.returnType).arrayType.equals(SymbolTable.mentalUnknownType)) {
                    ((Array) this.returnType).arrayType = ((SymbolType) baseType).type;
                }
            } else {
                this.returnType = ((SymbolType) baseType).type;
            }
        }

        // Process the parameters of the function.
        if (funcDefCtx.parametersList() != null) {
            for (int i = 0, limit = funcDefCtx.parametersList().parameter().size(); i < limit; ++i) {
                // for each parameter

                MentalParser.ParameterContext parameterCtx = funcDefCtx.parametersList().parameter(i);

                // Get TypeContext
                MentalParser.TypeContext typeCtx = parameterCtx.type();
                TypeBase type = null;

                // Get the name of parameter.
                String name = parameterCtx.Identifier().getText();

                // Process the cn.edu.sjtu.songyuke.mental.type of a single variable.
                SymbolBase baseType = scope.getSymbol(typeCtx.typeName().getText());
                if (baseType == null || !(baseType instanceof SymbolType)) {
                    System.err.println("fatal: no such a cn.edu.sjtu.songyuke.mental.type " + typeCtx.typeName().getText());
                    existError = true;
                    System.exit(1);
                }
                if (typeCtx.array().size() != 0) {
                    type = new Array(typeCtx);
                    if (((Array) type).arrayType.equals(SymbolTable.mentalUnknownType)) {
                        ((Array) type).arrayType = ((SymbolType) baseType).type;
                    }
                } else {
                    type = ((SymbolType) baseType).type;
                }

                // Get the cn.edu.sjtu.songyuke.mental.type and name put to list.
                parameterName.add(name);
                parameterType.add(type);
            }
        }
        return existError;
    }
    @Override
    public String toString() {
        String ret = "<function>" + this.returnType.toString() + " " + this.functionName + '(';
        if (parameterName.size() > 0) {
            for (int i = 0, count = parameterName.size() - 1; i < count; ++i) {
                ret += parameterType.get(i).toString() + ',';
            }
            ret += parameterType.get(parameterType.size() - 1).toString();
        }
        ret += ')';
        return ret;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof SymbolFunction) {
                if (this.functionName.equals(((SymbolFunction) other).functionName)) {
                    if (this.returnType.equals(((SymbolFunction) other).returnType)) {
                        if (this.parameterType.equals(((SymbolFunction) other).parameterType)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
