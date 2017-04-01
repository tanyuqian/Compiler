package Compiler.BackEnd.ControlFlowGraph.SingleStaticAssignment.SSAConstructor;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.Utility.Error.InternalError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PostDominanceAnalyser {
	private Graph graph;
	private Map<Block, Integer> order;

	PostDominanceAnalyser(Graph graph) {
		this.graph = graph.refresh();
		this.analysisPostDominance();
	}

	private void analysisPostDominance() {
		this.order = new HashMap<>();
		List<Block> list = postOrderSearch(graph.exit);
		for (int i = 0; i < list.size(); ++i) {
			order.put(list.get(i), i);
		}
		graph.exit.postDominance.dominator = graph.exit;
		while (true) {
			boolean modified = false;
			for (int i = list.size() - 2; i >= 0; --i) {
				Block block = list.get(i), immediate = null;
				for (Block successor : block.successors) {
					if (successor.postDominance.dominator != null) {
						immediate = successor;
						break;
					}
				}
				if (immediate == null) {
					throw new InternalError();
				}
				for (Block successor : block.successors) {
					if (successor.postDominance.dominator != null) {
						immediate = intersect(successor, immediate);
					}
				}
				if (block.postDominance.dominator != immediate) {
					block.postDominance.dominator = immediate;
					modified = true;
				}
			}
			if (!modified) {
				break;
			}
		}
		for (Block block : graph.blocks) {
			if (block.successors.size() > 1) {
				for (Block successor : block.successors) {
					Block runner = successor;
					while (runner != block.postDominance.dominator) {
						runner.postDominance.frontiers.add(block);
						runner = runner.postDominance.dominator;
					}
				}
			}
		}
		for (Block block : graph.blocks) {
			if (block != graph.exit) {
				block.postDominance.dominator.postDominance.children.add(block);
			}
		}
	}

	private List<Block> postOrderSearch(Block block) {
		order.put(block, -1);
		List<Block> list = new ArrayList<>();
		for (Block predecessor : block.predecessors) {
			if (!order.containsKey(predecessor)) {
				list.addAll(postOrderSearch(predecessor));
			}
		}
		list.add(block);
		return list;
	}

	private Block intersect(Block a, Block b) {
		while (a != b) {
			while (order.get(a) < order.get(b)) {
				a = a.postDominance.dominator;
			}
			while (order.get(b) < order.get(a)) {
				b = b.postDominance.dominator;
			}
		}
		return a;
	}
}
