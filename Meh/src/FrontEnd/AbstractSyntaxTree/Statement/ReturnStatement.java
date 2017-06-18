package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class ReturnStatement extends Statement {
    public Function owner;
    public Expression expression;

    public ReturnStatement(Function owner, Expression expression) {
        this.owner = owner;
        this.expression = expression;
    }

    public static ReturnStatement getStatement(Expression expression) {
        Function function = Environment.scopeTable.getFunctionScope();
        if (function == null) {
            throw new CompilationError("return statements must be in some function.");
        }
        if (expression == null) {
            if (function.type instanceof VoidType) {
                return new ReturnStatement(function, expression);
            }
        } else {
            if (function.type.compatibleWith(expression.type)) {
                return new ReturnStatement(function, expression);
            }
        }
        throw new CompilationError("return expression must have compatible type with declaration.");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        if (expression != null) {
            expression.emit(instructions);
            expression.load(instructions);
            instructions.add(ReturnInstruction.getInstruction(expression.operand));
        }
        instructions.add(JumpInstruction.getInstruction(owner.exit));
    }
}
