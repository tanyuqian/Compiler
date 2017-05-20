package FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class ForStatement extends LoopStatement {
    public Expression initialization, condition, increment;
    public Statement statement;

    public ForStatement() {
        this.condition = new BoolConstant(true);
    }

    public ForStatement(Expression initialization, Expression condition, Expression increment, Statement statement) {
        this.initialization = initialization;
        this.condition = condition;
        this.increment = increment;
        this.statement = statement;
    }

    public void addInitialization(Expression expression) {
        this.initialization = expression;
    }

    public void addCondition(Expression expression) {
        if (expression == null) {
            this.condition = new BoolConstant(true);
            return ;
        }
        if (expression.type instanceof BoolType) {
            this.condition = expression;
        } else {
            throw new CompilationError("the condition of for statement must be BoolType");
        }
    }

    public void addIncrement(Expression expression) {
        this.increment = expression;
    }

    public void addStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public void emit(List<Instruction> instructions) {
        LabelInstruction conditionLabel = LabelInstruction.getInstruction("for_condition");
        LabelInstruction bodyLabel = LabelInstruction.getInstruction("for_body");
        loop = LabelInstruction.getInstruction("for_loop");
        after = LabelInstruction.getInstruction("for_after");
        if (initialization != null) {
            initialization.emit(instructions);
        }
        instructions.add(JumpInstruction.getInstruction(conditionLabel));
        instructions.add(conditionLabel);
        if (condition == null) {
            addCondition(null);
        }
        condition.emit(instructions);
        instructions.add(BranchInstruction.getInstruction(condition.operand, bodyLabel, after));
        instructions.add(bodyLabel);
        if (statement != null) {
            statement.emit(instructions);
        }
        instructions.add(JumpInstruction.getInstruction(loop));
        instructions.add(loop);
        if (increment != null) {
            increment.emit(instructions);
        }
        instructions.add(JumpInstruction.getInstruction(conditionLabel));
        instructions.add(after);
    }
}
