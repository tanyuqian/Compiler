package Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.NullType;

import java.util.List;

public class NullConstant extends Constant {
	private NullConstant() {
		super(NullType.getType());
	}

	public static Constant getConstant() {
		return new NullConstant();
	}

	@Override
	public String toString() {
		return "[constant: null]";
	}

	@Override
	public void emit(List<Instruction> instructions) {
		operand = new ImmediateValue(0);
	}
}
