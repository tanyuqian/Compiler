package BackEnd.Translator.NASM;

import BackEnd.Allocator.Allocator;
import BackEnd.ControlFlowGraph.Block;
import BackEnd.ControlFlowGraph.Graph;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.*;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.BitwiseNotInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.UnaryInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.UnaryMinusInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.ControlFlowInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.FunctionInstruction;
import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.*;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.VariableRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import FrontEnd.AbstractSyntaxTree.Function;
import Utility.CompilationError;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/29/17.
 */
public class NASMSimpleTranslator extends NASMTranslator {
    public Allocator allocator;

    public NASMSimpleTranslator(PrintStream output) {
        super(output);
    }

    public void protectScene() {
        for (PhysicalRegistor physicalRegistor : allocator.getUsedPhysicalRegister()) {
            if (!physicalRegistor.isCalleeSaved) {
                output.printf("\tmov    qword [rbp + (%d)], %s\n",
                        -graph.frame.size - NASMRegister.size() * physicalRegistor.identity,
                        physicalRegistor.name);
            }
        }
    }

    public void restoreScene() {
        for (PhysicalRegistor physicalRegistor : allocator.getUsedPhysicalRegister()) {
            if (!physicalRegistor.isCalleeSaved) {
                output.printf("\tmov    %s, qword [rbp + (%d)]\n",
                        physicalRegistor.name,
                        -graph.frame.size - NASMRegister.size() * physicalRegistor.identity);
            }
        }
    }

    public PhysicalRegistor loadToRead(Operand source, PhysicalRegistor target) {
        if ((source instanceof TemporaryRegister) && allocator.mapping.containsKey(source)) {
            return allocator.mapping.get(source);
        } else {
            output.printf("\tmov    %s, %s\n", target, getPhisicalMemoryName(source));
            return target;
        }
    }

    public PhysicalRegistor loadToWrite(Operand source, PhysicalRegistor target) {
        if ((source instanceof TemporaryRegister) && allocator.mapping.containsKey(source)) {
            return allocator.mapping.get(source);
        }
        return target;
    }

    public void store(PhysicalRegistor source, VirtualRegister target) {
        if (allocator.mapping.containsKey(target)) {
            return ;
        } else {
            output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(target), source);
        }
    }

    public void move(PhysicalRegistor source, VirtualRegister target) {
        if (allocator.mapping.containsKey(target)) {
            output.printf("\tmov    %s, %s\n", allocator.mapping.get(target), source);
        } else {
            output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(target), source);
        }
    }

    public void moveFilter(PhysicalRegistor target, PhysicalRegistor source) {
        if (target == source) {
            return ;
        } else {
            output.printf("\tmov    %s, %s\n", target, source);
        }
    }

    @Override
    public void translate(Graph graph) {
        this.graph = graph;
        this.allocator = graph.function.allocator;
        output.printf("%s:\n", getFunctionName(graph.function));
        output.printf("\tpush   rbp\n");
        output.printf("\tmov    rbp, rsp\n");
        output.printf("\tsub    rsp, %d\n", graph.frame.size + NASMRegister.size() * 20);
        if (graph.function.parameters.size() >= 1) {
            output.printf("\tmov    qword [rbp-8], rdi\n");
        }
        if (graph.function.parameters.size() >= 2) {
            output.printf("\tmov    qword [rbp-16], rsi\n");
        }
        if (graph.function.parameters.size() >= 3) {
            output.printf("\tmov    qword [rbp-24], rdx\n");
        }
        if (graph.function.parameters.size() >= 4) {
            output.printf("\tmov    qword [rbp-32], rcx\n");
        }
        if (graph.function.parameters.size() >= 5) {
            output.printf("\tmov    qword [rbp-40], r8\n");
        }
        if (graph.function.parameters.size() >= 6) {
            output.printf("\tmov    qword [rbp-48], r9\n");
        }
        //protectScene();
        for (PhysicalRegistor physicalRegistor : allocator.getUsedPhysicalRegister()) {
            if (physicalRegistor.isCalleeSaved) {
                output.printf("\tmov    qword [rbp + (%d)], %s\n",
                        -graph.frame.size - physicalRegistor.identity * NASMRegister.size(),
                        physicalRegistor);
            }
        }

        for (Block block : graph.blocks) {
            output.printf("%s:\n", getBlockName(block));
            for (Instruction instruction : block.instructions) {
                output.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t;%s\n", instruction);
                if (instruction instanceof ArithmeticInstruction) {
                    if (instruction instanceof UnaryInstruction) {
                        PhysicalRegistor a = loadToRead(((UnaryInstruction) instruction).operand, NASMRegister.rax);
                        PhysicalRegistor b = loadToWrite(((UnaryInstruction) instruction).destination, NASMRegister.rax);
                        //output.printf("\tmov    %s, %s\n", b, a);
                        moveFilter(b, a);
                        if (instruction instanceof BitwiseNotInstruction) {
                            output.printf("\tnot    %s\n", b);
                        } else if (instruction instanceof UnaryMinusInstruction) {
                            output.printf("\tneg    %s\n", b);
                        }
                        store(b, ((UnaryInstruction) instruction).destination);
                    } else if (instruction instanceof BinaryInstruction) {
                        PhysicalRegistor a = loadToRead(((BinaryInstruction) instruction).operand1, NASMRegister.r10);
                        PhysicalRegistor b = loadToRead(((BinaryInstruction) instruction).operand2, NASMRegister.r11);
                        //output.printf("\tmov    %s, %s\n", NASMRegister.r10, a);
                        moveFilter(NASMRegister.r10, a);
                        //output.printf("\tmov    %s, %s\n", NASMRegister.r11, b);
                        moveFilter(NASMRegister.r11, b);
                        if (instruction instanceof AdditionInstruction) {
                            output.printf("\tadd    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                        } else if (instruction instanceof BitwiseAndInstruction) {
                            output.printf("\tand    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                        } else if (instruction instanceof BitwiseLeftShiftInstruction) {
                            output.printf("\tmov    %s, %s\n", NASMRegister.rcx, NASMRegister.r11);
                            output.printf("\tsal    %s, cl\n", NASMRegister.r10);
                        } else if (instruction instanceof BitwiseOrInstruction) {
                            output.printf("\tor     %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                        } else if (instruction instanceof BitwiseRightShiftInstruction) {
                            output.printf("\tmov    %s, %s\n", NASMRegister.rcx, NASMRegister.r11);
                            output.printf("\tsar    %s, cl\n", NASMRegister.r10);
                        } else if (instruction instanceof BitwiseXorInstruction) {
                            output.printf("\txor     %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                        } else if (instruction instanceof DivisionInstruction) {
                            output.printf("\tmov    %s, %s\n", NASMRegister.rax, NASMRegister.r10);
                            output.printf("\tcqo\n");
                            output.printf("\tidiv   %s\n", NASMRegister.r11);
                            output.printf("\tmov    %s, %s\n", NASMRegister.r10, NASMRegister.rax);
                        } else if (instruction instanceof EqualToInstruction) {
                            output.printf("\tcmp    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                            output.printf("\tsete   al\n");
                            output.printf("\tmovzx    %s, al\n", NASMRegister.r10);
                        } else if (instruction instanceof GreaterThanInstruction) {
                            output.printf("\tcmp    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                            output.printf("\tsetg   al\n");
                            output.printf("\tmovzx    %s, al\n", NASMRegister.r10);
                        } else if (instruction instanceof GreaterThanOrEqualToInstruction) {
                            output.printf("\tcmp    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                            output.printf("\tsetge   al\n");
                            output.printf("\tmovzx    %s, al\n", NASMRegister.r10);
                        } else if (instruction instanceof LessThanInstruction) {
                            output.printf("\tcmp    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                            output.printf("\tsetl   al\n");
                            output.printf("\tmovzx    %s, al\n", NASMRegister.r10);
                        } else if (instruction instanceof LessThanOrEqualToInstruction) {
                            output.printf("\tcmp    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                            output.printf("\tsetle   al\n");
                            output.printf("\tmovzx    %s, al\n", NASMRegister.r10);
                        } else if (instruction instanceof ModuloInstruction) {
                            output.printf("\tmov    %s, %s\n", NASMRegister.rax, NASMRegister.r10);
                            output.printf("\tcqo\n");
                            output.printf("\tidiv   %s\n", NASMRegister.r11);
                            output.printf("\tmov    %s, %s\n", NASMRegister.r10, NASMRegister.rdx);
                        } else if (instruction instanceof MultiplicationInstruction) {
                            output.printf("\timul    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                        } else if (instruction instanceof NotEqualToInstruction) {
                            output.printf("\tcmp    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                            output.printf("\tsetne   al\n");
                            output.printf("\tmovzx    %s, al\n", NASMRegister.r10);
                        } else if (instruction instanceof SubtractionInstruction) {
                            output.printf("\tsub    %s, %s\n", NASMRegister.r10, NASMRegister.r11);
                        }
                        PhysicalRegistor c = loadToWrite(((BinaryInstruction) instruction).destination, NASMRegister.rax);
                        //output.printf("\tmov    %s, %s\n", c, NASMRegister.r10);
                        moveFilter(c, NASMRegister.r10);
                        store(c, ((BinaryInstruction) instruction).destination);
                    }
                } else if (instruction instanceof MemoryInstruction) {
                    if (instruction instanceof MoveInstruction) {
                        PhysicalRegistor a = loadToRead(((MoveInstruction) instruction).operand, NASMRegister.rax);
                        move(a, ((MoveInstruction) instruction).destination);
                    } else if (instruction instanceof AllocateInstruction) {
                        PhysicalRegistor sizeR = loadToRead(((AllocateInstruction) instruction).size, NASMRegister.rax);
                        protectScene();
                        //output.printf("\tmov    %s, %s\n", NASMRegister.rdi, sizeR);
                        moveFilter(NASMRegister.rdi, sizeR);
                        output.printf("\tcall   malloc\n");
                        restoreScene();
                        move(NASMRegister.rax, ((AllocateInstruction) instruction).destination);
                    } else if (instruction instanceof LoadInstruction) {
                        PhysicalRegistor baseR = loadToRead(((LoadInstruction) instruction).address.base, NASMRegister.rax);
                        PhysicalRegistor destR = loadToWrite(((LoadInstruction) instruction).destination, NASMRegister.r10);
                        //output.printf("\tmov    %s, %s\n", NASMRegister.r11, baseR);
                        moveFilter(NASMRegister.r11, baseR);
                        output.printf("\tadd    %s, %s\n", NASMRegister.r11, ((LoadInstruction) instruction).address.offset);
                        output.printf("\tmov    %s, qword [%s]\n", destR, NASMRegister.r11);
                        store(destR, ((LoadInstruction) instruction).destination);
                    } else if (instruction instanceof StoreInstruction) {
                        PhysicalRegistor baseR = loadToRead(((StoreInstruction) instruction).address.base, NASMRegister.r10);
                        PhysicalRegistor a = loadToRead(((StoreInstruction) instruction).operand, NASMRegister.rax);
                        //output.printf("\tmov    %s, %s\n", NASMRegister.r11, baseR);
                        moveFilter(NASMRegister.r11, baseR);
                        output.printf("\tadd    %s, %s\n", NASMRegister.r11, ((StoreInstruction) instruction).address.offset);
                        output.printf("\tmov    qword [%s], %s\n", NASMRegister.r11, a);
                    }
                } else if (instruction instanceof ControlFlowInstruction) {
                    if (instruction instanceof JumpInstruction) {
                        output.printf("\tjmp    %s\n", getBlockName(((JumpInstruction) instruction).to.block));
                    } else if (instruction instanceof BranchInstruction) {
                        PhysicalRegistor condR = loadToRead(((BranchInstruction) instruction).condition, NASMRegister.rax);
                        output.printf("\tcmp    %s, 0\n", condR);
                        output.printf("\tjnz    %s\n", getBlockName(((BranchInstruction) instruction).trueTo.block));
                        output.printf("\tjz     %s\n", getBlockName(((BranchInstruction) instruction).falseTo.block));
                    }
                } else if (instruction instanceof FunctionInstruction) {
                    if (instruction instanceof ReturnInstruction) {
                        PhysicalRegistor a = loadToRead(((ReturnInstruction) instruction).operand, NASMRegister.rax);
                        //output.printf("\tmov    %s, %s\n", NASMRegister.rax, a);
                        moveFilter(NASMRegister.rax, a);
                        output.printf("\tjmp    %s\n", getBlockName(graph.exit));
                    } else if (instruction instanceof CallInstruction) {
                        protectScene();
                        VirtualRegister destination = ((CallInstruction) instruction).destination;
                        Function function = ((CallInstruction) instruction).function;
                        List<Operand> parameters = ((CallInstruction) instruction).parameters;
                        List<PhysicalRegistor> order = new ArrayList<PhysicalRegistor>() {{
                            add(NASMRegister.rdi);
                            add(NASMRegister.rsi);
                            add(NASMRegister.rdx);
                            add(NASMRegister.rcx);
                            add(NASMRegister.r8);
                            add(NASMRegister.r9);
                        }};
                        for (int i = 0; i < 6 && i < parameters.size(); i++) {
                            //rdi, rsi, rdx, rcx, r8, r9
                            PhysicalRegistor cur = loadToRead(parameters.get(i), NASMRegister.rax);
                            if (order.contains(cur) && !cur.isCalleeSaved) {
                                output.printf("\tmov    %s, qword[rbp + (%d)]\n", order.get(i),
                                        -graph.frame.size - cur.identity * NASMRegister.size());
                            } else {
                                output.printf("\tmov    %s, %s\n", order.get(i), cur);
                            }
                        }
                        if (parameters.size() > 6) {
                            for (int i = parameters.size() - 1; i >= 6; i--) {
                                PhysicalRegistor cur = loadToRead(parameters.get(i), NASMRegister.rax);
                                output.printf("\tpush   %s\n", cur);
                            }
                        }
                        output.printf("\tcall   %s\n", getFunctionName(function));
                        restoreScene();
                        if (destination != null) {
                            PhysicalRegistor destR = loadToWrite(destination, NASMRegister.r11);
                            //output.printf("\tmov    %s, %s\n", destR, NASMRegister.rax);
                            moveFilter(destR, NASMRegister.rax);
                            store(destR, destination);
                        }
                    }
                }
            }
        }
        //restoreScene();
        for (PhysicalRegistor physicalRegistor : allocator.getUsedPhysicalRegister()) {
            if (physicalRegistor.isCalleeSaved) {
                output.printf("\tmov    %s, qword [rbp + (%d)]\n",
                        physicalRegistor,
                        -graph.frame.size - physicalRegistor.identity * NASMRegister.size());
            }
        }
        output.printf("\tleave\n");
        output.printf("\tret\n");
    }
}
