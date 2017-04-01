package Compiler.BackEnd.ControlFlowGraph.SingleStaticAssignment.SSAConstructor;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.Utility.Error.InternalError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DominanceAnalyser {
	private Graph graph;
	private Map<Block, Integer> order;

	DominanceAnalyser(Graph graph) {
		this.graph = graph.refresh();
		this.analysisDominance();
	}

	private void analysisDominance() {
		this.order = new HashMap<>();
		List<Block> list = postOrderSearch(graph.enter);
		for (int i = 0; i < list.size(); ++i) {
			order.put(list.get(i), i);
		}
		graph.enter.dominance.dominator = graph.enter;
		while (true) {
			boolean modified = false;
			for (int i = list.size() - 2; i >= 0; --i) {
				Block block = list.get(i), immediate = null;
				for (Block predecessor : block.predecessors) {
					if (predecessor.dominance.dominator != null) {
						immediate = predecessor;
						break;
					}
				}
				if (immediate == null) {
					throw new InternalError();
				}
				for (Block predecessor : block.predecessors) {
					if (predecessor.dominance.dominator != null) {
						immediate = intersect(predecessor, immediate);
					}
				}
				if (block.dominance.dominator != immediate) {
					block.dominance.dominator = immediate;
					modified = true;
				}
			}
			if (!modified) {
				break;
			}
		}
		for (Block block : graph.blocks) {
			if (block.predecessors.size() > 1) {
				for (Block predecessor : block.predecessors) {
					Block runner = predecessor;
					while (runner != block.dominance.dominator) {
						runner.dominance.frontiers.add(block);
						runner = runner.dominance.dominator;
					}
				}
			}
		}
		for (Block block : graph.blocks) {
			if (block != graph.enter) {
				block.dominance.dominator.dominance.children.add(block);
			}
		}
	}

	private List<Block> postOrderSearch(Block block) {
		order.put(block, -1);
		List<Block> list = new ArrayList<>();
		for (Block successor : block.successors) {
			if (!order.containsKey(successor)) {
				list.addAll(postOrderSearch(successor));
			}
		}
		list.add(block);
		return list;
	}

	private Block intersect(Block a, Block b) {
		while (a != b) {
			while (order.get(a) < order.get(b)) {
				a = a.dominance.dominator;
			}
			while (order.get(b) < order.get(a)) {
				b = b.dominance.dominator;
			}
		}
		return a;
	}
}
