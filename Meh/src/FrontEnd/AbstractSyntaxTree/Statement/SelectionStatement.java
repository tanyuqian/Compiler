package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class SelectionStatement extends Statement {
    public Expression condition;
    public Statement trueStatement, falseStatement;

    public SelectionStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    public static SelectionStatement getStatement(Expression condition, Statement trueStatement, Statement falseStatement) {
        if (!(condition.type instanceof BoolType)) {
            throw new CompilationError("Selection Statements' condition must be BoolType");
        }
        return new SelectionStatement(condition, trueStatement, falseStatement);
    }

    @Override
    public void emit(List<Instruction> instructions) {
        LabelInstruction trueLabel = LabelInstruction.getInstruction("if_true");
        LabelInstruction falseLabel = LabelInstruction.getInstruction("if_false");
        LabelInstruction mergeLabel = LabelInstruction.getInstruction("if_merge");

        condition.emit(instructions);
        condition.load(instructions);
        instructions.add(BranchInstruction.getInstruction(condition.operand, trueLabel, falseLabel));
        // if_true
        instructions.add(trueLabel);
        if (trueStatement != null) {
            trueStatement.emit(instructions);
        }
        instructions.add(JumpInstruction.getInstruction(mergeLabel));
        // if_false
        instructions.add(falseLabel);
        if (falseStatement != null) {
            falseStatement.emit(instructions);
        }
        instructions.add(JumpInstruction.getInstruction(mergeLabel));
        // if_merge
        instructions.add(mergeLabel);
    }
}
