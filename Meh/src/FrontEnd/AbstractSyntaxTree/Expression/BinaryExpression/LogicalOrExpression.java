package FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/4/17.
 */
public class LogicalOrExpression extends BinaryExpression {
    public LogicalOrExpression(Type type, boolean isLeftValue, Expression left, Expression right) {
        super(type, isLeftValue, left, right);
    }

    public static Expression getExpression(Expression left, Expression right) {
        if ((left.type instanceof BoolType) && (right.type instanceof BoolType)) {
            if ((left instanceof BoolConstant) && (right instanceof BoolConstant)) {
                return new BoolConstant(((BoolConstant) left).tag || ((BoolConstant) right).tag);
            }
            return new LogicalOrExpression(new BoolType(), false, left, right);
        }
        throw new CompilationError("Type Error occur in \"||\"");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        LabelInstruction trueLabel = LabelInstruction.getInstruction("logical_true");
        LabelInstruction falseLabel = LabelInstruction.getInstruction("logical_false");
        LabelInstruction mergeLabel = LabelInstruction.getInstruction("logical_merge");

        left.emit(instructions);
        left.load(instructions);
        instructions.add(BranchInstruction.getInstruction(left.operand, trueLabel, falseLabel));
        // logical_false
        instructions.add(falseLabel);
        right.emit(instructions);
        right.load(instructions);
        operand = right.operand;
        instructions.add(JumpInstruction.getInstruction(mergeLabel));
        // logical_true
        instructions.add(trueLabel);
        instructions.add(MoveInstruction.getInstruction(operand, new ImmediateValue(1)));
        instructions.add(JumpInstruction.getInstruction(mergeLabel));
        // logical_merge
        instructions.add(mergeLabel);
    }
}
