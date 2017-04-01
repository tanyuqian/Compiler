package cn.edu.sjtu.songyuke.mental.symbols;

import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.type.Array;
import cn.edu.sjtu.songyuke.mental.type.Class;
import cn.edu.sjtu.songyuke.mental.type.TypeBase;
import cn.edu.sjtu.songyuke.mental.type.ClassMember;

import java.util.HashMap;

/**
 * Created by Songyu on 16/3/29.
 */

public class SymbolType extends SymbolBase {
    public TypeBase type;
    public SymbolType() {
        this.type = new Class();
    }
    public SymbolType(SymbolType other) {
        this.type = other.type;
    }
    public void setType(TypeBase type) {
        this.type = type;
    }
    public boolean setType(SymbolTable scope, MentalParser.ClassDeclarationContext classDeclCtx) {
        int classMemberCount = 0;
        boolean existError = false;
        this.stackLayer = SymbolTable.maxLayer;
        HashMap<String, ClassMember> classComponents = new HashMap<>();
        ((Class) this.type).setClassComponents(classComponents);
        // Get className
        ((Class) this.type).className = classDeclCtx.className().getText();
        // Process class components
        for (int i = 0, varCount = classDeclCtx.variableDefinition().size(); i < varCount; ++i) {
            // for each variable definition
            // get each definition
            MentalParser.VariableDefinitionContext varDefCtx = classDeclCtx.variableDefinition(i);

            // try to get base cn.edu.sjtu.songyuke.mental.type from scope
            SymbolBase baseType = scope.getSymbol(varDefCtx.type().typeName().getText());

            // if baseType is not a cn.edu.sjtu.songyuke.mental.type then halt
            if (!(varDefCtx.type().typeName().getText().equals(classDeclCtx.className().getText()))) {
                if (baseType == null || !(baseType instanceof SymbolType)) {
                    System.err.println("fatal: declarate a variable with bad cn.edu.sjtu.songyuke.mental.type.");
                    existError = true;
                    System.exit(1);
                }
            }

            TypeBase type;
            if (varDefCtx.type().array().size() != 0) {
                // if the cn.edu.sjtu.songyuke.mental.type is an array.

                type = new Array(varDefCtx.type());

                // find the base cn.edu.sjtu.songyuke.mental.type of the array.
                if (varDefCtx.type().typeName().getText().equals(classDeclCtx.className().getText())) {
                    // if it is a cn.edu.sjtu.songyuke.mental.type of itself.
                    ((Array) type).arrayType = this.type;
                } else {
                    ((Array) type).arrayType = ((SymbolType) baseType).type;
                }
            } else {
                if (varDefCtx.type().typeName().getText().equals(classDeclCtx.className().getText())) {
                    // the cn.edu.sjtu.songyuke.mental.type is itself.
                    type = this.type;
                } else {
                    type = ((SymbolType) baseType).type;
                }
            }

            // Process each variable with the cn.edu.sjtu.songyuke.mental.type.
            for (int j = 0, idCount = varDefCtx.singleVariable().size(); j < idCount; ++j) {
                String id = varDefCtx.singleVariable(j).Identifier().getText();
                if (classComponents.get(id) == null) {
                    classComponents.put(id, new ClassMember(classMemberCount++, type));
                } else {
                    // exit if redefinition occurs.
                    System.err.println("fatal: redefine a member " + id + " in class " + ((Class) this.type).className);
                    existError = true;
                }
            }
        }
        ((Class) type).classSize = classMemberCount;
        return existError;
    }
    @Override
    public String toString() {
        return "<cn.edu.sjtu.songyuke.mental.type>" + this.type.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof SymbolType) {
                if (this.type.equals(((SymbolType) other).type)) {
                    return true;
                }
            }
        }
        return false;
    }
}

