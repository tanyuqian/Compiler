package Compiler.Environment.SymbolTable;

import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
	private Map<String, Stack<Symbol>> currentSymbols;
	private Stack<Map<String, Symbol>> symbolTables;

	public SymbolTable() {
		currentSymbols = new HashMap<>();
		symbolTables = new Stack<>();
	}

	public Symbol add(String name, Type type) {
		if (symbolTables.peek().containsKey(name)) {
			throw new CompilationError("the scope cannot have two symbols named \"" + name + "\"");
		}
		if (!currentSymbols.containsKey(name)) {
			currentSymbols.put(name, new Stack<>());
		}
		Symbol symbol = new Symbol(name, type);
		currentSymbols.get(name).push(symbol);
		symbolTables.peek().put(name, symbol);
		return symbol;
	}

	public Symbol addGlobalVariable(String name, Type type) {
		Symbol symbol = add(name, type);
		symbol.register = Environment.registerTable.addGlobalRegister(symbol);
		return symbol;
	}

	public Symbol addTemporaryVariable(String name, Type type) {
		Symbol symbol = add(name, type);
		symbol.register = Environment.registerTable.addTemporaryRegister(symbol);
		return symbol;
	}

	public Symbol addParameterVariable(String name, Type type) {
		Symbol symbol = add(name, type);
		symbol.register = Environment.registerTable.addParameterRegister(symbol);
		return symbol;
	}

	public Symbol get(String name) {
		return currentSymbols.get(name).peek();
	}

	public boolean contains(String name) {
		return currentSymbols.containsKey(name) && !currentSymbols.get(name).empty();
	}

	public void enterScope() {
		symbolTables.push(new HashMap<>());
	}

	public void exitScope() {
		symbolTables.pop().forEach((name, symbol) -> currentSymbols.get(name).pop());
	}
}
