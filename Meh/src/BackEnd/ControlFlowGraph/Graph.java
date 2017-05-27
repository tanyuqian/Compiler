package BackEnd.ControlFlowGraph;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import BackEnd.Translator.NASM.NASMRegister;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Statement.VariableDeclarationStatement;
import Environment.Symbol;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tan on 5/19/17.
 */
public class Graph {
    public Function function;
    public List<Instruction> instructions;
    public Frame frame;

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

        this.instructions = instructions;
//
//        FileWriter fw = new FileWriter("tests/tbwIR.txt");
//        for (Instruction instruction : instructions) {
//            fw.write(instruction.toString());
//            fw.write("\n");
//        }
//        fw.close();

        int cntLabel = 0;
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            if (instruction instanceof LabelInstruction) {
                ((LabelInstruction) instruction).block = new Block(((LabelInstruction) instruction).name, cntLabel++, function);
            }
        }

        rebuild();
    }

    public void analysisFrame() {
        Set<VirtualRegister> registers = new HashSet<>();
        for (Instruction instruction : instructions) {
            for (VirtualRegister register : instruction.getDefinedRegisters()) {
                if (register instanceof TemporaryRegister) {
                    registers.add(register);
                }
            }
            for (VirtualRegister register : instruction.getUsedRegisters()) {
                if (register instanceof TemporaryRegister) {
                    registers.add(register);
                }
            }
        }
        frame = new Frame();
        frame.size = NASMRegister.size() * 8;
        int tmp = frame.size;
        for (VirtualRegister register : registers) {
            frame.temporaryMap.put(register, -frame.size);
            frame.size += NASMRegister.size();
        }
        for (int i = 0; i < 6 && i < function.parameters.size(); i++) {
            Symbol parameter = function.parameters.get(i);
            frame.parameterMap.put(parameter.register, -(i + 1) * NASMRegister.size());
        }
        for (int i = 6; i < function.parameters.size(); i++) {
            Symbol parameter = function.parameters.get(i);
            frame.parameterMap.put(parameter.register, (6 - i) * NASMRegister.size() - 16);
        }
    }

    public void rebuild() {
        analysisFrame();
    }
}
