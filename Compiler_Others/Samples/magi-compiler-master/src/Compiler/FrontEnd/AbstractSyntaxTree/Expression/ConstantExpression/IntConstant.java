package Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;

import java.util.List;

public class IntConstant extends Constant {
	public int literal;

	private IntConstant(int literal) {
		super(IntType.getType());
		this.literal = literal;
	}

	public static Constant getConstant(int literal) {
		return new IntConstant(literal);
	}

	@Override
	public String toString() {
		return "[constant: int, value = " + literal + "]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		operand = new ImmediateValue(literal);
	}
}
