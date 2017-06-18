package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class IntConstant extends Constant {
    public int number;

    public IntConstant(int number) {
        super(new IntType());
        this.number = number;
    }

    @Override
    public void emit(List<Instruction> instructions) {
        operand = new ImmediateValue(number);
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
