package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.NullType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class NullConstant extends Constant {
    public NullConstant() {
        super(new NullType());
    }

    @Override
    public void emit(List<Instruction> instructions) {
        operand = new ImmediateValue(0);
    }

    @Override
    public String toString() {
        return "null";
    }
}
