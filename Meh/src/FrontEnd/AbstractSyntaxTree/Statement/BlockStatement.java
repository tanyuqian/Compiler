package FrontEnd.AbstractSyntaxTree.Statement;

import Environment.Scope;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

/**
 * Created by tan on 4/1/17.
 */
public class BlockStatement extends Statement implements Scope {
    public ArrayList<Statement> statements;

    public BlockStatement() {
        statements = new ArrayList<>();
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }
}
