package Environment;

import FrontEnd.AbstractSyntaxTree.Program;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tan on 4/2/17.
 */
public class Environment {
    public static Program program;
    public static SymbolTable symbolTable;
    public static ScopeTable scopeTable;
    public static Set<String> classNameSet;

    public static void initialize() {
        program = new Program();
        symbolTable = new SymbolTable();
        scopeTable = new ScopeTable();
        classNameSet = new HashSet<>();
    }

    public static void enterScope(Scope scope) {
        scopeTable.enterScope(scope);
        symbolTable.enterScope();
    }

    public static void exitScope() {
        scopeTable.exitScope();
        symbolTable.exitScope();
    }
}
