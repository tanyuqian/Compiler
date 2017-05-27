package BackEnd.Translator.NASM;

import BackEnd.ControlFlowGraph.Block;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import BackEnd.Translator.Translator;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Function;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Created by tan on 5/23/17.
 */
public abstract class NASMTranslator extends Translator {
    public NASMTranslator(PrintStream output) {
        super(output);
    }

    @Override
    public void translate() throws Exception {
//        BufferedReader template = new BufferedReader(new InputStreamReader(new FileInputStream("lib/template.meh")));
//        for (String str = template.readLine(); !str.equals("END"); str = template.readLine()) {
//            output.println(str);
//        }

        Template template = new Template();
        output.print(template.templateStr);

        for (Function function : Environment.program.functions) {
            translate(function.graph);
        }
        output.println("SECTION .data");
        for (VirtualRegister register : Environment.registerTable.registers) {
            if (register instanceof GlobalRegister) {
                output.printf("%s:\n\tdq 0\n", ((GlobalRegister)register).symbol.name);
            }
            if (register instanceof StringRegister) {
                output.printf("CONST_STRING_%d:\n\tdb %s, 0\n", register.identity, ((StringRegister) register).str);
            }
        }
        output.printf("STRING_FORMAT:\n\tdb \"%%s\", 0\n");
        output.printf("INTEGER_FORMAT_NEXT_LINE:\n\tdb \"%%lld\", 10, 0\n");
        output.printf("INT_FORMAT_NEXT_LINE:\n\tdb \"%%d\", 10, 0\n");
        output.printf("INTEGER_FORMAT:\n\tdb \"%%lld\", 0\n");
        output.printf("CHAR_FORMAT:\n\tdb \"%%c\", 0\n");
    }
}
