package BackEnd.Allocator.RegisterAllocator;

import BackEnd.Allocator.Allocator;
import BackEnd.ControlFlowGraph.Block;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BinaryInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import FrontEnd.AbstractSyntaxTree.Function;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tan on 5/29/17.
 */
public class RegisterAllocator extends Allocator {
    public RegisterAllocator(Function function) throws Exception {
        super(function);
        InterferenceGraph interGraph = new InterferenceGraph();
        for (Block block : function.graph.blocks) {
            for (Instruction instruction : block.instructions) {
                for (VirtualRegister register : instruction.getDefinedRegisters()) {
                    interGraph.addVertice(register);
                }
                for (VirtualRegister register : instruction.getUsedRegisters()) {
                    interGraph.addVertice(register);
                }
            }
        }
        for (Block block : function.graph.blocks) {
            Set<VirtualRegister> living = new HashSet<>();
            for (VirtualRegister register : block.liveliness.liveOut) {
                living.add(register);
            }
            for (int i = block.instructions.size() - 1; i >= 0; i--) {
                Instruction instruction = block.instructions.get(i);
                if (instruction instanceof BinaryInstruction) {
                    for (VirtualRegister livingRegister : living) {
                        interGraph.addForbid(((BinaryInstruction) instruction).destination, livingRegister);
                    }
                    living.remove(((BinaryInstruction) instruction).destination);
                    if (((BinaryInstruction) instruction).operand2 instanceof VirtualRegister) {
                        living.add((VirtualRegister) ((BinaryInstruction) instruction).operand2);
                    }

                    for (VirtualRegister livingRegister : living) {
                        interGraph.addForbid(((BinaryInstruction) instruction).destination, livingRegister);
                    }
                    living.remove(((BinaryInstruction) instruction).destination);
                    if (((BinaryInstruction) instruction).operand1 instanceof VirtualRegister) {
                        living.add((VirtualRegister) ((BinaryInstruction) instruction).operand1);
                    }
                } else {
                    for (VirtualRegister register : instruction.getDefinedRegisters()) {
                        for (VirtualRegister livingRegister : living) {
                            interGraph.addForbid(register, livingRegister);
                        }
                    }
                    for (VirtualRegister register : instruction.getDefinedRegisters()) {
                        living.remove(register);
                    }
                    for (VirtualRegister register : instruction.getUsedRegisters()) {
                        living.add(register);
                    }
                }
            }
        }
        for (Block block : function.graph.blocks) {
            for (Instruction instruction : block.instructions) {
                if (instruction instanceof MoveInstruction) {
                    if (((MoveInstruction) instruction).operand instanceof VirtualRegister) {
                        interGraph.addRecommend(((MoveInstruction) instruction).destination,
                                (VirtualRegister) ((MoveInstruction) instruction).operand);
                    }
                }
            }
        }
        mapping = new ChaitinGraphColoring(interGraph).analysis();

    }
}
