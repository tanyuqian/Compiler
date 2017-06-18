package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Scope;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void emit(List<Instruction> instructions) {
        statements.forEach(statement -> {
            statement.emit(instructions);
        });
    }
}
