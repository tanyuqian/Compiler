package BackEnd.ControlFlowGraph;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import FrontEnd.AbstractSyntaxTree.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/18/17.
 */
public class Block {
    public String name;
    public int identity;
    public Function function;
    public List<Instruction> instructions;
    public Liveliness liveliness;
    public List<Block> predecessors, successors;

    public Block(String name, int identity, Function function) {
        this.name = name;
        this.identity = identity;
        this.function = function;
        instructions = new ArrayList<>();
        liveliness = new Liveliness();
        predecessors = new ArrayList<>();
        successors = new ArrayList<>();
    }
}
