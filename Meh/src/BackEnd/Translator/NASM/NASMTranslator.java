package BackEnd.Translator.NASM;

import BackEnd.ControlFlowGraph.Block;
import BackEnd.ControlFlowGraph.Operand.Operand;
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
        BufferedReader template = new BufferedReader(new InputStreamReader(new FileInputStream("lib/template.meh")));
        for (String str = template.readLine(); !str.equals("END"); str = template.readLine()) {
            output.println(str);
        }

        for (Function function : Environment.program.functions) {
            translate(function.graph);
        }
        output.println("SECTION .data");
        for (VirtualRegister register : Environment.registerTable.registers) {
            if (register instanceof GlobalRegister) {
                output.printf("%s: dd 0\n", ((GlobalRegister)register).symbol.name);
            }
        }
    }
}
