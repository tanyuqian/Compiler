package BackEnd.ControlFlowGraph;

import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.ControlFlowInstruction;
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
    public Frame frame;
    public List<Block> blocks;
    public Block enter, entry, exit;

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

        blocks = new ArrayList<>();
        for (int i = 0, j; i < instructions.size(); i = j) {
            if (!(instructions.get(i) instanceof LabelInstruction)) {
                j = i + 1;
            } else {
                LabelInstruction label = (LabelInstruction)instructions.get(i);
                Block block = label.block = addBlock(label.name, label);
                for (j = i + 1; j < instructions.size(); j++) {
                    if (instructions.get(j) instanceof LabelInstruction) {
                        break;
                    }
                    block.instructions.add(instructions.get(j));
                    if (instructions.get(j) instanceof ControlFlowInstruction) {
                        break;
                    }
                }
            }
        }
        for (Block block : blocks) {
            if (block.name.equals("enter")) {
                enter = block;
            } else if (block.name.equals("entry")) {
                entry = block;
            } else if (block.name.equals("exit")) {
                exit = block;
            }
        }

//        int cntLabel = 0;
//        for (int i = 0; i < instructions.size(); i++) {
//            Instruction instruction = instructions.get(i);
//            if (instruction instanceof LabelInstruction) {
//                ((LabelInstruction) instruction).block = new Block(((LabelInstruction) instruction).name, cntLabel++, function);
//            }
//        }

        refresh();
    }

    void refresh() {
        refreshGraph();
        analysisLiveliness();
        analysisFrame();
    }

    public Block addBlock(String name, LabelInstruction label) {
        Block block = new Block(name, blocks.size(), function);
        blocks.add(block);
        return block;
    }

    public void refreshGraph() {
        for (Block block : blocks) {
            block.predecessors = new ArrayList<>();
            block.successors = new ArrayList<>();
        }
        for (Block block : blocks) {
            if (!block.instructions.isEmpty()) {
                Instruction instruction = block.instructions.get(block.instructions.size() - 1);
                if (instruction instanceof JumpInstruction) {
                    block.successors.add(((JumpInstruction) instruction).to.block);
                } else if (instruction instanceof BranchInstruction) {
                    block.successors.add(((BranchInstruction) instruction).trueTo.block);
                    block.successors.add(((BranchInstruction) instruction).falseTo.block);
                }
            }
        }
        blocks = dfsBlocks(enter, new HashSet<>());
        for (Block block : blocks) {
            for (Block successor : block.successors) {
                successor.predecessors.add(block);
            }
        }
    }

    public List<Block> dfsBlocks(Block u, Set<Block> visited) {
        visited.add(u);
        List<Block> list = new ArrayList<Block>() {{
            add(u);
        }};
        for (Block v : u.successors) {
            if (visited.contains(v)) {
                continue;
            }
            if (v != exit) {
                visited.add(v);
                list.addAll(dfsBlocks(v, visited));
            }
        }
        if (u == enter) {
            list.add(exit);
        }
        return list;
    }

    public void analysisLiveliness() {
        for (Block block : blocks) {
            block.liveliness.used = new ArrayList<>();
            block.liveliness.defined = new ArrayList<>();
            for (Instruction instruction : block.instructions) {
                for (VirtualRegister register : instruction.getUsedRegisters()) {
                    if (!block.liveliness.defined.contains(register)) {
                        block.liveliness.used.add(register);
                    }
                }
                for (VirtualRegister register : instruction.getDefinedRegisters()) {
                    block.liveliness.defined.add(register);
                }
            }
        }
        for (Block block : blocks) {
            block.liveliness.liveIn = new HashSet<>();
            block.liveliness.liveOut = new HashSet<>();
        }
        while (true) {
            for (Block block : blocks) {
                block.liveliness.liveIn = new HashSet<>();
                for (VirtualRegister register : block.liveliness.liveOut) {
                    block.liveliness.liveIn.add(register);
                }
                for (VirtualRegister register : block.liveliness.defined) {
                    block.liveliness.liveIn.remove(register);
                }
                for (VirtualRegister register : block.liveliness.used) {
                    block.liveliness.liveIn.add(register);
                }
            }
            boolean modified = false;
            for (Block block : blocks) {
                Set<VirtualRegister> lastOut = block.liveliness.liveOut;
                block.liveliness.liveOut = new HashSet<VirtualRegister>() {{
                    for (Block successor : block.successors) {
                        addAll(successor.liveliness.liveIn);
                    }
                }};
                if (!block.liveliness.liveOut.equals(lastOut)) {
                    modified = true;
                }
            }
            if (!modified) {
                break;
            }
        }
    }

    public void analysisFrame() {
        Set<VirtualRegister> registers = new HashSet<>();
        for (Block block : blocks) {
            for (Instruction instruction : block.instructions) {
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
            frame.parameterMap.put(parameter.register, (i - 6) * NASMRegister.size() + 16);
        }
    }
}
