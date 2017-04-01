package Compiler.BackEnd.ControlFlowGraph.Operand;

public class ImmediateValue extends Operand {
	public int literal;

	public ImmediateValue(int literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return String.valueOf(literal);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ImmediateValue) {
			ImmediateValue other = (ImmediateValue)object;
			return other.literal == literal;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return literal;
	}
}
