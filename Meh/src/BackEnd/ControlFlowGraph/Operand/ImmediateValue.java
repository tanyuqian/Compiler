package BackEnd.ControlFlowGraph.Operand;

/**
 * Created by tan on 5/17/17.
 */
public class ImmediateValue extends Operand {
    public int value;

    public ImmediateValue(int value) {
        this.value = value;
    }

    public boolean equals(Object object) {
        if (object instanceof ImmediateValue) {
            ImmediateValue other = (ImmediateValue)object;
            return other.value == value;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
