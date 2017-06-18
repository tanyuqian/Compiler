package BackEnd.ControlFlowGraph;

import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Utility.CompilationError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tan on 5/23/17.
 */
public class Frame {
    public int size;
    public Map<VirtualRegister, Integer> temporaryMap, parameterMap;

    public Frame() {
        size = 0;
        temporaryMap = new HashMap<>();
        parameterMap = new HashMap<>();
    }

    public int getOffset(Operand operand) {
        if (operand instanceof VirtualRegister) {
            if (temporaryMap.containsKey(operand)) {
                return temporaryMap.get(operand);
            }
            if (parameterMap.containsKey(operand)) {
                return parameterMap.get(operand);
            }
        }
        throw new CompilationError("Internal Error!");
    }
}
