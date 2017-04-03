package Environment;

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
}
