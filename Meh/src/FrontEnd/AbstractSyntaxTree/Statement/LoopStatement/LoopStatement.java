package FrontEnd.AbstractSyntaxTree.Statement.LoopStatement;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Environment.Scope;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;

/**
 * Created by tan on 4/1/17.
 */
public abstract class LoopStatement extends Statement implements Scope {
    public LabelInstruction loop, after;
}
