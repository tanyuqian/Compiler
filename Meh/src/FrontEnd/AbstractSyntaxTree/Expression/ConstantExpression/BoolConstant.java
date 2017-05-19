package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class BoolConstant extends Constant {
    public boolean tag;

    public BoolConstant(boolean tag) {
        super(new BoolType());
        this.tag = tag;
    }

    @Override
    public void emit(List<Instruction> instructions) {
        operand = new ImmediateValue(tag ? 1 : 0);
    }

    @Override
    public String toString() {
        return String.valueOf(tag);
    }
}
