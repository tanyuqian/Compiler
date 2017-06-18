package Environment;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tan on 5/17/17.
 */
public class RegisterTable {
    public Set<VirtualRegister> registers;

    public RegisterTable() {
        registers = new HashSet<>();
    }

    public VirtualRegister addGlobalRegister(Symbol symbol) {
        VirtualRegister register = new GlobalRegister(symbol);
        registers.add(register);
        return register;
    }

    public VirtualRegister addTemporaryRegister(Symbol symbol) {
        VirtualRegister register = new TemporaryRegister(symbol);
        registers.add(register);
        return register;
    }

    public VirtualRegister addTemporaryRegister() {
        return addTemporaryRegister(null);
    }

    public VirtualRegister addParameterRegister(Symbol symbol) {
        VirtualRegister register = new ParameterRegister(symbol);
        registers.add(register);
        return register;
    }

    public VirtualRegister addStringRegister(String str) {
        VirtualRegister register = new StringRegister(str);
        registers.add(register);
        return register;
    }
}
