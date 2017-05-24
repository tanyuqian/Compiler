package BackEnd.Translator.NASM;

import BackEnd.ControlFlowGraph.Block;
import BackEnd.ControlFlowGraph.Graph;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.*;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.BitwiseNotInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.UnaryInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.UnaryMinusInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.ControlFlowInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.FunctionInstruction;
import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MemoryInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Utility.CompilationError;

import java.io.OutputStream;
import java.io.PrintStream;

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
            return "dword [rel " + ((GlobalRegister) register).symbol.name + "]";
        }
        if (register instanceof TemporaryRegister) {
            int offset = graph.frame.temporaryMap.get(register);
            return String.format("dword [rbp+(%d)]", offset);
        }
        if (register instanceof ParameterRegister) {
            int offset = graph.frame.parameterMap.get(register);
            return String.format("dword [rbp+(%d)]", offset);
        }
        if (register instanceof ImmediateValue) {
            return String.valueOf(((ImmediateValue) register).value);
        }
        return "Fuck";
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
        output.printf("\tmov    dword [rbp-4H], edi\n");
        output.printf("\tmov    dword [rbp-8H], esi\n");
        output.printf("\tmov    dword [rbp-0CH], edx\n");
        output.printf("\tmov    dword [rbp-10H], ecx\n");
        output.printf("\tmov    dword [rbp-14H], r8d\n");
        output.printf("\tmov    dword [rbp-18H], r9d\n");

        for (Instruction instruction : graph.instructions) {
            //output.printf("\t;\t%s\n", instruction);
            if (instruction instanceof LabelInstruction) {
                output.printf("%s:\n", getBlockName(((LabelInstruction) instruction).block));
            } else if (instruction instanceof MemoryInstruction) {
                if (instruction instanceof MoveInstruction) {
                    VirtualRegister destination = ((MoveInstruction) instruction).destination;
                    Operand operand = ((MoveInstruction) instruction).operand;
                    output.printf("\tmov    %s, %s\n", NASMRegister.r11, getPhisicalMemoryName(operand));
                    output.printf("\tmov    %s, %s\n", getPhisicalMemoryName(destination), NASMRegister.r11);
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
                        output.printf("\tcdq\n");
                        output.printf("\tidiv   %s\n", getPhisicalMemoryName(operand2));
                        output.printf("\tmov    %s, %s\n", NASMRegister.r11, NASMRegister.rax);
                    } else if (instruction instanceof EqualToInstruction) {

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
                }
            } else if (instruction instanceof FunctionInstruction) {
                if (instruction instanceof ReturnInstruction) {
                    if (!(graph.function.type instanceof VoidType)) {
                        output.printf("\tmov    %s, %s\n", NASMRegister.rax, getPhisicalMemoryName(((ReturnInstruction) instruction).operand));
                    }
                    output.printf("\tleave\n");
                    output.printf("\tret\n");
                }
            }
        }
        output.printf("\tleave\n");
        output.printf("\tret\n\n\n");
    }
}
