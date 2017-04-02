package FrontEnd.AbstractSyntaxTree.Statement;

import java.util.ArrayList;

/**
 * Created by tan on 4/1/17.
 */
public class BlockStatement extends Statement {
    public ArrayList<Statement> statements;

    public BlockStatement() {
        statements = new ArrayList<>();
    }
}
