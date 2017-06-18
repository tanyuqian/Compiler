package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;
import Utility.CompilationError;

import java.util.List;

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

    @Override
    public void emit(List<Instruction> instructions) {
        instructions.add(JumpInstruction.getInstruction(owner.after));
    }
}
