package FrontEnd.AbstractSyntaxTree.Statement;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;
import Utility.CompilationError;

/**
 * Created by tan on 4/1/17.
 */
public class BreakStatement extends Statement {
    public LoopStatement owner;

    public BreakStatement(LoopStatement owner) {
        this.owner = owner;
    }

    public static BreakStatement getStatement() {
        if (Environment.scopeTable.getLoopScope() == null) {
            throw new CompilationError("break statements must be in some loop statement.");
        }
        return new BreakStatement(Environment.scopeTable.getLoopScope());
    }
}
