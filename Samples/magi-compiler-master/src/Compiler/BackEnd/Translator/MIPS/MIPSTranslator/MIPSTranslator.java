package Compiler.BackEnd.Translator.MIPS.MIPSTranslator;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.BackEnd.Translator.Translator;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.Utility.Utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

abstract class MIPSTranslator extends Translator {
	MIPSTranslator(PrintStream output) {
		super(output);
	}

	String getFunctionName(Function function) {
		if (function.name.equals("main") || function.name.startsWith("____builtin")) {
			return function.name;
		} else {
			return String.format("____%s____function", function.name);
		}
	}

	String getBlockName(Block block) {
		return String.format("____%s_%d____%s", block.function.name, block.identity, block.name);
	}

	String getGlobalVariableName(Operand operand) {
		return String.format("____global_%d____variable", ((VirtualRegister)operand).identity);
	}

	String getStringConstantName(Operand operand) {
		return String.format("____global_%d____string", ((VirtualRegister)operand).identity);
	}

	@Override
	public void translate() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./lib/library.s")));
		String line = reader.readLine();
		while (!line.startsWith("\t.data")) {
			output.println(line);
			line = reader.readLine();
		}
		for (Function function : Environment.program.functions) {
			translate(function.graph);
		}
		while (line != null) {
			output.println(line);
			line = reader.readLine();
		}
		for (VirtualRegister register : Environment.registerTable.registers) {
			if (register instanceof StringRegister) {
				String literal = ((StringRegister)register).literal;
				output.printf("\t.word %d\n", Utility.getLength(literal));
				output.printf("%s:\n", getStringConstantName(register));
				output.printf("\t.asciiz \"%s\"\n", literal);
				output.printf("\t.align 2\n");
			} else if (register instanceof GlobalRegister) {
				output.printf("%s:\n", getGlobalVariableName(register));
				output.printf("\t.space %d\n", ((GlobalRegister)register).symbol.type.size());
				output.printf("\t.align 2\n");
			}
		}
	}
}
