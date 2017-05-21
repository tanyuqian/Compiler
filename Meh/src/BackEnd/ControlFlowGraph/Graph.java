package BackEnd.ControlFlowGraph;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Statement.VariableDeclarationStatement;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/19/17.
 */
public class Graph {
    public Function function;

    public Graph(Function function) throws Exception {
        this.function = function;
        this.buildGraph();
    }

    public void buildGraph() throws Exception {
        List<Instruction> instructions = new ArrayList<>();
        function.enter = LabelInstruction.getInstruction("enter");
        function.entry = LabelInstruction.getInstruction("entry");
        function.exit = LabelInstruction.getInstruction("exit");
        instructions.add(function.enter);
        if (function.name.equals("main")) {
            for (VariableDeclarationStatement declaration : Environment.program.globalVariables) {
                declaration.emit(instructions);
            }
        }
        instructions.add(JumpInstruction.getInstruction(function.entry));
        instructions.add(function.entry);
        function.statements.emit(instructions);
        instructions.add(JumpInstruction.getInstruction(function.exit));
        instructions.add(function.exit);

        FileWriter fw = new FileWriter("tests/tbwIR.txt");
        for (Instruction instruction : instructions) {
            fw.write(instruction.toString());
            fw.write("\n");
        }
        fw.close();
    }
}
