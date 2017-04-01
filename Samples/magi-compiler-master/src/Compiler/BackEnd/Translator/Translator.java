package Compiler.BackEnd.Translator;

import Compiler.BackEnd.ControlFlowGraph.Graph;

import java.io.PrintStream;

public abstract class Translator {
	protected PrintStream output;

	protected Translator(PrintStream output) {
		this.output = output;
	}

	public abstract void translate(Graph graph);
	public abstract void translate() throws Exception;
}
