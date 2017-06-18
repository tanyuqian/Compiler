package cn.edu.sjtu.songyuke.mental.symbols;

import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.type.Array;
import cn.edu.sjtu.songyuke.mental.type.TypeBase;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/3/30.
 */
public class SymbolVariableList {
    public TypeBase variableType;
    public LinkedList<SymbolVariable> variables;
    public SymbolVariableList() {
        variables = new LinkedList<>();
    }
    public SymbolVariableList(SymbolTable scope, MentalParser.VariableDefinitionContext varDefCtx) {
        variables = new LinkedList<>();
        // Get the cn.edu.sjtu.songyuke.mental.type
        // find the cn.edu.sjtu.songyuke.mental.type from current scope
        SymbolBase baseType = scope.getSymbol(varDefCtx.type().typeName().getText());
        if (baseType == null || !(baseType instanceof SymbolType)) {
            System.err.println("fatal: no such a cn.edu.sjtu.songyuke.mental.type " + varDefCtx.type().typeName().getText());
            System.exit(1);
        }
        TypeBase type;
        if (varDefCtx.type().array().size() != 0) {
            // cn.edu.sjtu.songyuke.mental.type is array.
            type = new Array(varDefCtx.type());
            if (((Array) type).arrayType.equals(SymbolTable.mentalUnknownType)) {
                ((Array) type).arrayType = ((SymbolType) baseType).type;
            }
        } else {
            type = ((SymbolType) baseType).type;
        }
        this.variableType = type;
        for (int j = 0, idCount = varDefCtx.singleVariable().size(); j < idCount; ++j) {
            if (varDefCtx.singleVariable(j).Identifier() != null) {
                String id = varDefCtx.singleVariable(j).Identifier().getText();
                this.variables.add(new SymbolVariable(scope, type, id));
            } else {
                System.err.println("fatal: the identifier is illegal.\n\t" + varDefCtx.getText());
                System.exit(1);
            }
        }
    }
}
