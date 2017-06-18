package FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class WhileStatement extends LoopStatement {
    public Expression condition;
    public Statement statement;

    public WhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    public void addCondition(Expression expression) {
        if (!(expression.type instanceof BoolType)) {
            throw new CompilationError("while statements' condition must be BoolType");
        }
        this.condition = expression;
    }

    public void addStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public void emit(List<Instruction> instructions) {
        LabelInstruction bodyLabel = LabelInstruction.getInstruction("while_body");
        loop = LabelInstruction.getInstruction("while_loop");
        after = LabelInstruction.getInstruction("while_after");
        instructions.add(JumpInstruction.getInstruction(loop));
        // while_loop
        instructions.add(loop);
        condition.emit(instructions);
        instructions.add(BranchInstruction.getInstruction(condition.operand, bodyLabel, after));
        // while_body
        instructions.add(bodyLabel);
        statement.emit(instructions);
        instructions.add(JumpInstruction.getInstruction(loop));
        // while_after
        instructions.add(after);
    }
}
