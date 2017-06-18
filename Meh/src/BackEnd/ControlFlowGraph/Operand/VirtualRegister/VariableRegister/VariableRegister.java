package BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Environment.Symbol;

/**
 * Created by tan on 5/17/17.
 */
public abstract class VariableRegister extends VirtualRegister {
    public Symbol symbol;

    public VariableRegister(Symbol symbol) {
        this.symbol = symbol;
    }
}
