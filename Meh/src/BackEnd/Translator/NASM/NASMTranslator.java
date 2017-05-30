package BackEnd.Translator.NASM;

import BackEnd.ControlFlowGraph.Block;
import BackEnd.ControlFlowGraph.Graph;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import BackEnd.Translator.Translator;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Function;

import java.io.*;
import java.util.List;

/**
 * Created by tan on 5/23/17.
 */
public abstract class NASMTranslator extends Translator {
    public Graph graph;

    public NASMTranslator(PrintStream output) {
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
            return "qword [rel " + "GV_" + ((GlobalRegister) register).symbol.name + "]";
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
    public void translate() throws Exception {
//        BufferedReader template = new BufferedReader(new InputStreamReader(new FileInputStream("lib/template.meh")));
//        for (String str = template.readLine(); !str.equals("END"); str = template.readLine()) {
//            output.println(str);
//        }

//        FileWriter fw = new FileWriter("tests/tbwIR.txt");
//        for (Function function : Environment.program.functions) {
//            for (Block block : function.graph.blocks) {
//                fw.write("%" + block.name);
//                fw.write("\n");
//                fw.write("liveIn: {");
//                for (VirtualRegister register : block.liveliness.liveIn) {
//                    fw.write(register.toString());
//                    fw.write(", ");
//                }
//                fw.write("}\n");
//                for (Instruction instruction : block.instructions) {
//                    fw.write(instruction.toString());
//                    fw.write("\n");
//                }
//                fw.write("\n");
//            }
//            fw.write("\n");
//        }
//        fw.close();

        Template template = new Template();
        output.print(template.templateStr);

        for (Function function : Environment.program.functions) {
            translate(function.graph);
        }
        output.println("SECTION .data");
        for (VirtualRegister register : Environment.registerTable.registers) {
            if (register instanceof GlobalRegister) {
                output.printf("%s:\n\tdq 0\n", "GV_" + ((GlobalRegister)register).symbol.name);
            }
            if (register instanceof StringRegister) {
                output.printf("CONST_STRING_%d:\n\tdb ", register.identity);
                String str = ((StringRegister) register).str;
                for (int i = 0; i < str.length(); i++) {
                    if (i + 1 < str.length() && str.substring(i).startsWith("\\n")) {
                        output.printf("%d, ", 10);
                        i++;
                    } else if (i + 1 < str.length() && str.substring(i).startsWith("\\\\")) {
                        output.printf("%d, ", 92);
                        i++;
                    } else if (i + 1 < str.length() && str.substring(i).startsWith("\\\"")) {
                        output.printf("%d, ", 34);
                        i++;
                    } else {
                        int x = str.charAt(i);
                        output.printf("%d, ", x);
                    }
                }
                output.printf("0\n");
            }
        }
        output.printf("STRING_FORMAT:\n\tdb \"%%s\", 0\n");
        output.printf("INTEGER_FORMAT_NEXT_LINE:\n\tdb \"%%lld\", 10, 0\n");
        output.printf("INT_FORMAT_NEXT_LINE:\n\tdb \"%%d\", 10, 0\n");
        output.printf("INTEGER_FORMAT:\n\tdb \"%%lld\", 0\n");
        output.printf("CHAR_FORMAT:\n\tdb \"%%c\", 0\n");
        output.printf("NEXT_LINE:\n\tdb 10, 0\n");
    }
}
