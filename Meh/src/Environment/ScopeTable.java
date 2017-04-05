package Environment;

import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.ForStatement;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Utility.CompilationError;

import java.util.Stack;

/**
 * Created by tan on 4/2/17.
 */
public class ScopeTable {
    public Stack<Scope> scopes;
    public Stack<ClassType> classScopes;
    public Stack<Function> functionScopes;
    public Stack<LoopStatement> loopScopes;

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
            throw new CompilationError("Internal Error!!");
        }
        Scope scope = scopes.peek();
        scopes.pop();
        if (scope instanceof ClassType) {
            if (classScopes.empty()) {
                throw new CompilationError("Internal Error!!");
            }
            classScopes.pop();
        }
        if (scope instanceof Function) {
            if (functionScopes.empty()) {
                throw new CompilationError("Internal Error!!");
            }
            functionScopes.pop();
        }
        if (scope instanceof LoopStatement) {
            if (loopScopes.empty()) {
                throw new CompilationError("Internal Error!!");
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
