package Environment;

import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by tan on 4/2/17.
 */
public class SymbolTable {
    public Map<String, Stack<Symbol>> currentSymbols;
    public Stack<Map<String, Symbol>> symbolTables;

    public SymbolTable() {
        currentSymbols = new HashMap<>();
        symbolTables = new Stack<>();
    }

    public void enterScope() {
        symbolTables.push(new HashMap<>());
    }

    public void exitScope() {
        Map<String, Symbol> map = symbolTables.peek();
        for (String str : map.keySet()) {
            currentSymbols.get(str).pop();
        }
        symbolTables.pop();
    }

    public Symbol add(Type type, String name) {
        if (symbolTables.peek().containsKey(name)) {
            throw new CompilationError("this scope have two symbol named \"" + name + "\"");
        }
        if (!currentSymbols.containsKey(name)) {
            currentSymbols.put(name, new Stack<>());
        }
        Symbol symbol = new Symbol(type, name);
        currentSymbols.get(name).push(symbol);
        symbolTables.peek().put(name, symbol);
        return symbol;
    }

    public Symbol addParameterVariable(Type type, String name) {
        return add(type, name);
    }

    public Symbol addGlobalVariable(Type type, String name) {
        return add(type, name);
    }

    public Symbol addTemporatyVariable(Type type, String name) {
        return add(type, name);
    }

    public boolean contains(String name) {
        return currentSymbols.containsKey(name) && !currentSymbols.get(name).empty();
    }

    public Symbol get(String name) {
        return currentSymbols.get(name).peek();
    }
}
