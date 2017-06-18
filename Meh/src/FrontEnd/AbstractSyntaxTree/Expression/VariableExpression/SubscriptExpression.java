package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.AdditionInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.MultiplicationInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.LoadInstruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class SubscriptExpression extends Expression {
    public Expression expression;
    public Expression subscript;

    public SubscriptExpression(Type type, boolean isLeftValue, Expression expression, Expression subscript) {
        super(type, isLeftValue);
        this.expression = expression;
        this.subscript = subscript;
    }

    public static SubscriptExpression getExpression(Expression expression, Expression subscript) {
        if (!(expression.type instanceof ArrayType)) {
            throw new CompilationError("subscript expression's expression must be ArrayType");
        }
        if (!(subscript.type instanceof IntType)) {
            throw new CompilationError("subscript expression's subscript must be IntType");
        }
        ArrayType arrayType = (ArrayType)expression.type;
        return new SubscriptExpression(arrayType.reduce(), expression.isLeftValue, expression, subscript);
    }

    @Override
    public void emit(List<Instruction> instructions) {
        expression.emit(instructions);
        expression.load(instructions);
        subscript.emit(instructions);
        subscript.load(instructions);
        VirtualRegister address = Environment.registerTable.addTemporaryRegister();
        VirtualRegister delta = Environment.registerTable.addTemporaryRegister();
        instructions.add(MultiplicationInstruction.getInstruction(delta, subscript.operand, new ImmediateValue(type.size())));
        instructions.add(AdditionInstruction.getInstruction(address, expression.operand, delta));
        operand = new Address(address, type.size());
    }

    @Override
    public void load(List<Instruction> instructions) {
        if (operand instanceof Address) {
            Address address = (Address)operand;
            operand = Environment.registerTable.addTemporaryRegister();
            instructions.add(LoadInstruction.getInstruction(operand, address));
        }
    }
}
