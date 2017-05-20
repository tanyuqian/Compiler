package FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class StringConstant extends Constant {
    public String str;

    public StringConstant(String str) {
        super(new StringType());
        this.str = str;
    }

    @Override
    public void emit(List<Instruction> instructions) {
        operand = Environment.registerTable.addStringRegister(str);
    }

    @Override
    public String toString() {
        return str;
    }
}
