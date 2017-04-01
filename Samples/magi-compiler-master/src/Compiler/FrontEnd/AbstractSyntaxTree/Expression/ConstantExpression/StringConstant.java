package Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;

import java.util.List;

public class StringConstant extends Constant {
	public String literal;

	private StringConstant(String literal) {
		super(StringType.getType());
		this.literal = literal;
	}

	public static Constant getConstant(String literal) {
		return new StringConstant(literal);
	}

	@Override
	public String toString() {
		return "[constant: string, value = " + literal + "]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		operand = Environment.registerTable.addStringRegister(literal);
	}
}
