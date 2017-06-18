package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Environment.Symbol;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class VariableDeclarationStatement extends Statement {
    public Symbol symbol;
    public Expression expression;

    public VariableDeclarationStatement(Symbol symbol, Expression expression) {
        this.symbol = symbol;
        this.expression = expression;
    }

    public static VariableDeclarationStatement getStatement(Symbol symbol, Expression expression) {
        if (symbol.type instanceof VoidType) {
            throw new CompilationError("variable declaration: left should not be an VoidType..");
        }
        if (expression == null || symbol.type.compatibleWith(expression.type)) {
            return new VariableDeclarationStatement(symbol, expression);
        }
        throw new CompilationError("variable declaration: the types is not match. name is \"" + symbol.name + "\"");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        if (expression != null) {
            expression.emit(instructions);
            expression.load(instructions);
            instructions.add(MoveInstruction.getInstruction(symbol.register, expression.operand));
        }
    }
}
