package BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister;

import Environment.Symbol;

/**
 * Created by tan on 5/17/17.
 */
public class ParameterRegister extends VariableRegister {
    public ParameterRegister(Symbol symbol) {
        super(symbol);
    }

    @Override
    public String toString() {
        return String.format("$p%s", identity);
    }
}
