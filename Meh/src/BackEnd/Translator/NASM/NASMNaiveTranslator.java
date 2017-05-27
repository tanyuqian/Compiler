package BackEnd.Translator.NASM;

import BackEnd.Allocator.PhysicalRegistor;
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
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.*;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Utility.CompilationError;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 5/23/17.
 */
public class NASMNaiveTranslator extends NASMTranslator {
    public Graph graph;

    public NASMNaiveTranslator(PrintStream output) {
        super(output);
    }

    public String getFunctionName(Function function) {
        return function.name;
    }

    public String getBlockName(Block block) {
        return String.format("%s_%s_%d", block.function.name, block.name, block.identity);
    }

    public String getPhisicalMemoryName(Operand register) {
        if (register instanceof GlobalRegister) {
            return "qword [rel " + ((GlobalRegister) register).symbol.name + "]";
        } else if (register instanceof TemporaryRegister) {
            int offset = graph.frame.temporaryMap.get(register);
            return String.format("qword [rbp+(%d)]", offset);
        } else if (register instanceof ParameterRegister) {
            int offset = graph.frame.parameterMap.get(register);
            return String.format("qword [rbp+(%d)]", offset);
        } else if (register instanceof ImmediateValue) {
            return String.valueOf(((ImmediateValue) register).value);
        } else if (register instanceof StringRegister) {
            return String.format("CONST_STRING_%d", ((StringRegister) register).identity);
        }
        return "FUCK";
    }


    @Override
    public void translate(Graph graph) {
        this.graph = graph;
        output.printf("%s:\n", getFunctionName(graph.function));
        /*
        push    rbp
        mov     rbp, rsp
        mov     dword [rbp-4H], edi
        mov     dword [rbp-8H], esi
        mov     dword [rbp-0CH], edx
        mov     dword [rbp-10H], ecx
        mov     dword [rbp-14H], r8d
        mov     dword [rbp-18H], r9d
         */
        output.printf("\tpush   rbp\n");
        output.printf("\tmov    rbp, rsp\n");
        output.printf("\tsub    rsp, %d\n", graph.frame.size);
        output.printf("\tmov    qword [rbp-8], rdi\n");
        output.printf("\tmov    qword [rbp-16], rsi\n");
        output.printf("\tmov    qword [rbp-24], rdx\n");
        output.printf("\tmov    qword [rbp-32], rcx\n");
        output.printf("\tmov    qword [rbp-40], r8\n");
        output.printf("\tmov    qword [rbp-48], r9\n");

        for (Instruction instruction : graph.instructions) {
            output.printf("\t\t\t\t\t\t\t\t\t\t\t\t\t\t;%s\n", instruction);
            if (instruction instanceof LabelInstruction) {
                output.printf("%s:\n", getBlockName(((LabelInstruction) instruction).block));
            } else if (instruction instanceof MemoryInstruction) {
                if (instruction instanceof AllocateInstruction) {
                    VirtualRegister destination = ((AllocateInstruction) instruction).destination;
                    Operand size = ((AllocateInstruction) instruction).size;
                    output.printf("\tmov    %s, %s\n", NASMRegister.rdi, getPhisicalMemoryName(size));
                    output.printf("\tcall   malloc\n");
                    output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(destination), NASMRegister.rax);
                } else if (instruction instanceof LoadInstruction) {
                    VirtualRegister destination = ((LoadInstruction) instruction).destination;
                    Address address = ((LoadInstruction) instruction).address;
                    output.printf("\tmov    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(address.base));
                    output.printf("\tadd    %s, %s\n", NASMRegister.r11, address.offset);
                    output.printf("\tmov    %s, qword [%s]\n", NASMRegister.r12, NASMRegister.r11);
                    output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(destination), NASMRegister.r12);
                } else if (instruction instanceof MoveInstruction) {
                    VirtualRegister destination = ((MoveInstruction) instruction).destination;
                    Operand operand = ((MoveInstruction) instruction).operand;
                    output.printf("\tmov    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand));
                    output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(destination), NASMRegister.r11);
                } else if (instruction instanceof StoreInstruction) {
                    Operand operand = ((StoreInstruction) instruction).operand;
                    Address address = ((StoreInstruction) instruction).address;
                    output.printf("\tmov    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(address.base));
                    output.printf("\tadd    %s, %s\n", NASMRegister.r11, address.offset);
                    output.printf("\tmov    %s, %s\n", NASMRegister.rax, getPhisicalMemoryName(operand));
                    output.printf("\tmov    qword [%s], %s\n", NASMRegister.r11, NASMRegister.rax);
                }
            } else if (instruction instanceof ArithmeticInstruction) {
                if (instruction instanceof BinaryInstruction) {
                    VirtualRegister destination = ((BinaryInstruction) instruction).destination;
                    Operand operand1 = ((BinaryInstruction) instruction).operand1;
                    Operand operand2 = ((BinaryInstruction) instruction).operand2;
                    output.printf("\tmov    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand1));
                    if (instruction instanceof AdditionInstruction) {
                        output.printf("\tadd    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                    } else if (instruction instanceof BitwiseAndInstruction) {
                        output.printf("\tand    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                    } else if (instruction instanceof BitwiseLeftShiftInstruction) {
                        output.printf("\tmov    %s, %s\n", NASMRegister.rcx, getPhisicalMemoryName(operand2));
                        output.printf("\tsal    %s, cl\n", NASMRegister.r11);
                    } else if (instruction instanceof BitwiseOrInstruction) {
                        output.printf("\tor    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                    } else if (instruction instanceof BitwiseRightShiftInstruction) {
                        output.printf("\tmov    %s, %s\n", NASMRegister.rcx, getPhisicalMemoryName(operand2));
                        output.printf("\tsar    %s, cl\n", NASMRegister.r11);
                    } else if (instruction instanceof BitwiseXorInstruction) {
                        output.printf("\txor    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                    } else if (instruction instanceof DivisionInstruction) {
                        output.printf("\tmov    %s, %s\n", NASMRegister.rax, getPhisicalMemoryName(operand1));
                        output.printf("\tcqo\n");
                        output.printf("\tmov    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tidiv   %s\n", NASMRegister.r11);
                        output.printf("\tmov    %s, %s\n", NASMRegister.r11, NASMRegister.rax);
                    } else if (instruction instanceof EqualToInstruction) {
                        output.printf("\tcmp    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tsete   al\n");
                        output.printf("\tmovzx  %s, al\n", NASMRegister.r11);
                    } else if (instruction instanceof GreaterThanInstruction) {
                        output.printf("\tcmp    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tsetg   al\n");
                        output.printf("\tmovzx  %s, al\n", NASMRegister.r11);
                    } else if (instruction instanceof GreaterThanOrEqualToInstruction) {
                        output.printf("\tcmp    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tsetge   al\n");
                        output.printf("\tmovzx  %s, al\n", NASMRegister.r11);
                    } else if (instruction instanceof LessThanInstruction) {
                        output.printf("\tcmp    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tsetl   al\n");
                        output.printf("\tmovzx  %s, al\n", NASMRegister.r11);
                    } else if (instruction instanceof LessThanOrEqualToInstruction) {
                        output.printf("\tcmp    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tsetle  al\n");
                        output.printf("\tmovzx  %s, al\n", NASMRegister.r11);
                    } else if (instruction instanceof ModuloInstruction) {
                        output.printf("\tmov    %s, %s\n", NASMRegister.rax, getPhisicalMemoryName(operand1));
                        output.printf("\tcqo\n");
                        output.printf("\tmov    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tidiv   %s\n", NASMRegister.r11);
                        output.printf("\tmov    %s, %s\n", NASMRegister.r11, NASMRegister.rdx);
                    } else if (instruction instanceof MultiplicationInstruction) {
                        output.printf("\timul   %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                    } else if (instruction instanceof NotEqualToInstruction) {
                        output.printf("\tcmp    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                        output.printf("\tsetne  al\n");
                        output.printf("\tmovzx  %s, al\n", NASMRegister.r11);
                    } else if (instruction instanceof SubtractionInstruction) {
                        output.printf("\tsub    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand2));
                    } else {
                        output.printf("FUCK_ArithmeticInstruction.\n");
                    }
                    output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(destination), NASMRegister.r11);
                } else if (instruction instanceof UnaryInstruction) {
                    Operand operand = ((UnaryInstruction)instruction).operand;
                    if (instruction instanceof BitwiseNotInstruction) {
                        output.printf("\tnot    %s\n", getPhisicalMemoryName(operand));
                    } else if (instruction instanceof UnaryMinusInstruction) {
                        output.printf("\tneg    %s\n", getPhisicalMemoryName(operand));
                    }
                }
            } else if (instruction instanceof ControlFlowInstruction) {
                if (instruction instanceof JumpInstruction) {
                    output.printf("\tjmp    %s\n", getBlockName(((JumpInstruction) instruction).to.block));
                } else if (instruction instanceof BranchInstruction) {
                    Operand condition = ((BranchInstruction)instruction).condition;
                    LabelInstruction trueTo = ((BranchInstruction)instruction).trueTo;
                    LabelInstruction falseTo = ((BranchInstruction)instruction).falseTo;
                    output.printf("\tcmp    %s, 0\n", getPhisicalMemoryName(condition));
                    output.printf("\tjnz    %s\n", getBlockName(trueTo.block));
                    output.printf("\tjz     %s\n", getBlockName(falseTo.block));
                }
            } else if (instruction instanceof FunctionInstruction) {
                if (instruction instanceof ReturnInstruction) {
                    if (!(graph.function.type instanceof VoidType)) {
                        output.printf("\tmov    %s, %s\n", NASMRegister.rax, getPhisicalMemoryName(((ReturnInstruction) instruction).operand));
                    }
                    output.printf("\tleave\n");
                    output.printf("\tret\n");
                } else if (instruction instanceof CallInstruction) {
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
                        output.printf("\tmov    %s, %s\n", order.get(i), getPhisicalMemoryName(parameters.get(i)));
                    }
                    if (parameters.size() > 6) {
                        for (int i = parameters.size() - 1; i >= 6; i--) {
                            output.printf("\tpush   %s\n", getPhisicalMemoryName(parameters.get(i)));
                        }
                    }
                    output.printf("\tcall   %s\n", getFunctionName(function));
                    if (destination != null) {
                        output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(destination), NASMRegister.rax);
                    }
                }
            }
        }
        output.printf("\tleave\n");
        output.printf("\tret\n\n\n");
    }
}
