package FrontEnd.AbstractSyntaxTree.Statement;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import FrontEnd.AbstractSyntaxTree.Node;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public abstract class Statement extends Node {
    public abstract void emit(List<Instruction> instructions);
}
