package FrontEnd.AbstractSyntaxTree.Statement;

import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;

/**
 * Created by tan on 4/1/17.
 */
public class BreakStatement extends Statement {
    public LoopStatement owner;

    public LoopStatement(LoopStatement owner) {
        this.owner = owner;
    }
}
