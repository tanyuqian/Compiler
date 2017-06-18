package Environment;

import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Program;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;

import java.util.*;

/**
 * Created by tan on 4/2/17.
 */
public class Environment {
    public static Program program;
    public static SymbolTable symbolTable;
    public static ScopeTable scopeTable;
    public static Map<String, ClassType> classTable;
    public static boolean hasMain;
    public static RegisterTable registerTable;

    public static void initialize() {
        symbolTable = new SymbolTable();
        scopeTable = new ScopeTable();
        classTable = new HashMap<>();
        enterScope(program = new Program());
        hasMain = false;
        registerTable = new RegisterTable();
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

        symbolTable.add(Function.getFunction(
                "__builtin_getArraySize",
                new IntType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new VoidType(), "this"));
                }}
        ), "__builtin_getArraySize");
        symbolTable.add(Function.getFunction(
                "__builtin_getStringLength",
                new IntType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "this"));
                }}
        ), "__builtin_getStringLength");
        symbolTable.add(Function.getFunction(
                "__builtin_getSubstring",
                new StringType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "this"));
                    add(new Symbol(new IntType(), "left"));
                    add(new Symbol(new IntType(), "right"));
                }}
        ), "__builtin_getSubstring");
        symbolTable.add(Function.getFunction(
                "__builtin_parseInt",
                new IntType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "this"));
                }}
        ), "__builtin_parseInt");
        symbolTable.add(Function.getFunction(
                "__builtin_ord",
                new IntType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "this"));
                    add(new Symbol(new IntType(), "pos"));
                }}
        ), "__builtin_ord");
        symbolTable.add(Function.getFunction(
                "__builtin_string_concat",
                new StringType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "left"));
                    add(new Symbol(new StringType(), "right"));
                }}
        ), "__builtin_string_concat");
        symbolTable.add(Function.getFunction(
                "__builtin_string_equalTo",
                new BoolType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "left"));
                    add(new Symbol(new StringType(), "right"));
                }}
        ), "__builtin_string_equalTo");
        symbolTable.add(Function.getFunction(
                "__builtin_string_greaterThan",
                new BoolType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "left"));
                    add(new Symbol(new StringType(), "right"));
                }}
        ), "__builtin_string_greaterThan");
        symbolTable.add(Function.getFunction(
                "__builtin_string_greaterThanOrEqualTo",
                new BoolType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "left"));
                    add(new Symbol(new StringType(), "right"));
                }}
        ), "__builtin_string_greaterThanOrEqualTo");
        symbolTable.add(Function.getFunction(
                "__builtin_string_lessThan",
                new BoolType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "left"));
                    add(new Symbol(new StringType(), "right"));
                }}
        ), "__builtin_string_lessThan");
        symbolTable.add(Function.getFunction(
                "__builtin_string_lessThanOrEqualTo",
                new BoolType(),
                new ArrayList<Symbol>() {{
                    add(new Symbol(new StringType(), "left"));
                    add(new Symbol(new StringType(), "right"));
                }}
        ), "__builtin_string_lessThanOrEqualTo");
    }
}
