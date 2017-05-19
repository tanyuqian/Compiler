package FrontEnd.AbstractSyntaxTree;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import Environment.Scope;
import Environment.Symbol;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.BlockStatement;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class Function extends Type implements Scope {
    public Type type;
    public String name;
    public List<Symbol> parameters;
    public BlockStatement statements;
    public Instruction entry, enter, exit;

    public Function(Type type, String name, List<Symbol> parameters) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "[function: name->" + name + "type->" + type.toString() + "]";
    }

    public boolean compatibleWith(Type other) {
        return false;
    }

    public void addStatements(BlockStatement statements) {
        this.statements = statements;
    }

    public static Function getFunction(String name, Type returnType, List<Symbol> parameters) {
        if (Environment.scopeTable.getClassScope() == null) {
            if (Environment.symbolTable.contains(name)) {
                throw new CompilationError("the program have two symbols names \"name\"");
            }
        }
        if (name.equals("main")) {
            if (!(returnType instanceof IntType)) {
                throw new CompilationError("main function should have int return-value");
            }
            if (!parameters.isEmpty()) {
                throw new CompilationError("main function should have no parameter.");
            }
            Environment.hasMain = true;
        }
        for (int i = 0; i < parameters.size(); i++) {
            for (int j = i + 1; j < parameters.size(); j++) {
                if (parameters.get(i).name.equals(parameters.get(j).name)) {
                    throw new CompilationError("function \"" + name + "\" have two parameters with same same.");
                }
            }
        }
        return new Function(returnType, name, parameters);
    }
}
