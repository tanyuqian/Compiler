package Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;

import java.util.List;

public class BoolConstant extends Constant {
	public boolean literal;

	private BoolConstant(boolean literal) {
		super(BoolType.getType());
		this.literal = literal;
	}

	public static Constant getConstant(boolean literal) {
		return new BoolConstant(literal);
	}

	@Override
	public String toString() {
		return "[constant: bool, value = " + literal + "]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		operand = new ImmediateValue(literal ? 1 : 0);
	}
}
