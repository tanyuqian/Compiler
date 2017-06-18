package Compiler.BackEnd.ControlFlowGraph.SingleStaticAssignment.SSAConstructor;

import Compiler.BackEnd.ControlFlowGraph.Graph;

public class SSAConstructor {
	public SSAConstructor(Graph graph) {
		new DominanceAnalyser(graph);
		new PostDominanceAnalyser(graph);
		new PhiFunctionPlacer(graph);
		new RenamingProcessor(graph);
	}
}
