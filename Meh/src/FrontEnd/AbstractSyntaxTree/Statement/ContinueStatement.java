package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.Constant;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.LoopStatement;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class ContinueStatement extends Statement {
    public LoopStatement owner;

    public ContinueStatement(LoopStatement owner) {
        this.owner = owner;
    }

    public static ContinueStatement getStatement() {
        if (Environment.scopeTable.getLoopScope() == null) {
            throw new CompilationError("continue statements must be in some loop statement");
        }
        return new ContinueStatement(Environment.scopeTable.getLoopScope());
    }

    @Override
    public void emit(List<Instruction> instructions) {
        instructions.add(JumpInstruction.getInstruction(owner.loop));
    }
}
