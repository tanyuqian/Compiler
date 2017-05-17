package Environment;

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


}
