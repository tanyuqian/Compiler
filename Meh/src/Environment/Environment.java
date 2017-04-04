package Environment;

import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Program;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;

import java.util.ArrayList;
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
        symbolTable = new SymbolTable();
        scopeTable = new ScopeTable();
        classNameSet = new HashSet<>();
        enterScope(program = new Program());
        loadLibraryFunctions();
    }

    public static void enterScope(Scope scope) {
        scopeTable.enterScope(scope);
        symbolTable.enterScope();
    }

    public static void exitScope() {
        scopeTable.exitScope();
        symbolTable.exitScope();
    }

    public static void loadLibraryFunctions() {
        symbolTable.add(Function.getFunction(
                "__builtin_print",
                new VoidType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "str"));
                }}
        ), "print");
        symbolTable.add(Function.getFunction(
                "__builtin_println",
                new VoidType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "str"));
                }}
        ), "println");
        symbolTable.add(Function.getFunction(
                "__builtin_getString",
                new StringType(),
                new ArrayList<Symbol>()
        ), "getString");
        symbolTable.add(Function.getFunction(
                "__builtin_getInt",
                new IntType(),
                new ArrayList<Symbol>()
        ), "getInt");
        symbolTable.add(Function.getFunction(
                "__builtin_toString",
                new StringType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new IntType(), "i"));
                }}
        ), "toString");
    }
}
