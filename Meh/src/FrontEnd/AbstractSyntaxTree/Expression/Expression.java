package FrontEnd.AbstractSyntaxTree.Expression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.Operand;
import FrontEnd.AbstractSyntaxTree.Node;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public abstract class Expression extends Node {
    public Type type;
    public boolean isLeftValue;
    public Operand operand;

    public Expression(Type type, boolean isLeftValue) {
        this.type = type;
        this.isLeftValue = isLeftValue;
        this.operand = null;
    }

    //public abstract void emit(List<Instruction> instructions);
    public void emit(List<Instruction> instructions) {

    }

    public void load(List<Instruction> instructions) {
    }
}