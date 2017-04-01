package Compiler.FrontEnd.AbstractSyntaxTree;

import Compiler.Environment.ScopeTable.Scope;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.VariableDeclarationStatement;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class Program implements Node, Scope {
	public List<Function> functions;
	public List<VariableDeclarationStatement> globalVariables;
	private List<ClassType> classTypes;

	private Program() {
		classTypes = new ArrayList<>();
		functions = new ArrayList<>();
		globalVariables = new ArrayList<>();
	}

	public static Program getProgram() {
		return new Program();
	}

	public void addClassType(ClassType classType) {
		classTypes.add(classType);
	}

	public void addFunction(Function function) {
		functions.add(function);
	}

	public void addGlobalVariable(VariableDeclarationStatement globalVariable) {
		globalVariables.add(globalVariable);
	}

	@Override
	public String toString() {
		return "[program]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(toString()).append("\n");
		classTypes.forEach(classType -> {
			stringBuilder.append(classType.toString(indents + 1));
		});
		globalVariables.forEach(globalVariableDeclaration -> {
			stringBuilder.append(globalVariableDeclaration.toString(indents + 1));
		});
		functions.forEach(function -> {
			stringBuilder.append(function.toString(indents + 1));
		});
		return stringBuilder.toString();
	}
}
