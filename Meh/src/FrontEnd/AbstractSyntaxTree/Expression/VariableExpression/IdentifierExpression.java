package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import Environment.Symbol;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class IdentifierExpression extends Expression {
    public Symbol symbol;

    public IdentifierExpression(Type type, boolean resultIsLeftValue, Symbol symbol) {
        super(type, resultIsLeftValue);
        this.symbol = symbol;
    }

    public static Expression getExpression(String name) {
        //System.out.println("name is " + name);
        if (!Environment.symbolTable.contains(name)) {
            throw new CompilationError("\"" + name + "\" is not a symbol name");
        }
        Symbol symbol = Environment.symbolTable.get(name);
        if (symbol.scope instanceof ClassType) {
            return FieldExpression.getExpression(IdentifierExpression.getExpression("this"), name);
        } else {
            if (symbol.type instanceof Function) {
                return new IdentifierExpression(symbol.type, false, symbol);
            } else {
                return new IdentifierExpression(symbol.type, true, symbol);
            }
        }
    }

    @Override
    public void emit(List<Instruction> instructions) {
        operand = symbol.register;
    }
}
