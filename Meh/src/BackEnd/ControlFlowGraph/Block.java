package BackEnd.ControlFlowGraph;

import FrontEnd.AbstractSyntaxTree.Function;

/**
 * Created by tan on 5/18/17.
 */
public class Block {
    public String name;
    public int identity;
    public Function function;

    public Block(String name, int identity, Function function) {
        this.name = name;
        this.identity = identity;
        this.function = function;
    }
}
