package FrontEnd.AbstractSyntaxTree.Expression;

import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.FieldExpression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;
import jdk.nashorn.internal.ir.FunctionCall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class FunctionCallExpression extends Expression {
    public Function function;
    public List<Expression> parameters;

    public FunctionCallExpression(Type type, boolean isLeftValue, Function function, List<Expression> parameters) {
        super(type, isLeftValue);
        this.function = function;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "call -> " + function.toString();
    }

    public static FunctionCallExpression getExpression(Function function, List<Expression> parameters) {
        return new FunctionCallExpression(function.type, false, function, parameters);
    }

    public static Expression getExpression(Expression expression, List<Expression> parameters) {
        if (expression.type instanceof Function) {
            Function function = (Function)expression.type;
            if (expression instanceof FieldExpression) {
                parameters.add(0, ((FieldExpression) expression).expression);
            }
            if (parameters.size() != function.parameters.size()) {
                throw new CompilationError("the number of parameters error.");
            }
            for (int i = 0; i < parameters.size(); i++) {
                if (i == 0 && (expression instanceof FieldExpression)) {
                    continue;
                }
                if (!function.parameters.get(i).type.compatibleWith(parameters.get(i).type)) {
                    System.out.println(function.name);
                    throw new CompilationError("type of parameter Error!");
                }
            }
            return new FunctionCallExpression(function.type, false, function, parameters);
        }
        throw new CompilationError("function-call need a function");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        if (!peephole(instructions)) {
            List<Operand> operands = new ArrayList<>();
            for (Expression parameter : parameters) {
                parameter.emit(instructions);
                parameter.load(instructions);
                operands.add(parameter.operand);
            }
            if (function.type instanceof VoidType) {
                instructions.add(CallInstruction.getInstruction(null, function, operands));
            } else {
                operand = Environment.registerTable.addTemporaryRegister();
                instructions.add(CallInstruction.getInstruction(operand, function, operands));
            }
        }
    }

    public boolean peephole(List<Instruction> instructions) {
        // waiting to be finished...
        return false;
    }
}
