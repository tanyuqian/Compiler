package Compiler.Environment.ScopeTable;

import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.Utility.Error.InternalError;

import java.util.Stack;

public class ScopeTable {
	private Stack<Scope> scopes;
	private Stack<ClassType> classScopes;
	private Stack<Function> functionScopes;
	private Stack<LoopStatement> loopScopes;

	public ScopeTable() {
		scopes = new Stack<>();
		classScopes = new Stack<>();
		functionScopes = new Stack<>();
		loopScopes = new Stack<>();
	}

	public void enterScope(Scope scope) {
		scopes.push(scope);
		if (scope instanceof ClassType) {
			classScopes.push((ClassType)scope);
		}
		if (scope instanceof Function) {
			functionScopes.push((Function)scope);
		}
		if (scope instanceof LoopStatement) {
			loopScopes.push((LoopStatement)scope);
		}
	}

	public void exitScope() {
		if (scopes.empty()) {
			throw new InternalError();
		}
		Scope scope = scopes.pop();
		if (scope instanceof ClassType) {
			if (classScopes.empty()) {
				throw new InternalError();
			}
			classScopes.pop();
		}
		if (scope instanceof Function) {
			if (functionScopes.empty()) {
				throw new InternalError();
			}
			functionScopes.pop();
		}
		if (scope instanceof LoopStatement) {
			if (loopScopes.empty()) {
				throw new InternalError();
			}
			loopScopes.pop();
		}
	}

	public Scope getScope() {
		if (scopes.empty()) {
			return null;
		}
		return scopes.peek();
	}

	public ClassType getClassScope() {
		if (classScopes.empty()) {
			return null;
		}
		return classScopes.peek();
	}

	public Function getFunctionScope() {
		if (functionScopes.empty()) {
			return null;
		}
		return functionScopes.peek();
	}

	public LoopStatement getLoopScope() {
		if (loopScopes.empty()) {
			return null;
		}
		return loopScopes.peek();
	}
}
