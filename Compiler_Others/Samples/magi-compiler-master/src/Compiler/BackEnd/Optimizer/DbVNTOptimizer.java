package Compiler.BackEnd.Optimizer;

import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.PhiFunctionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.CloneRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DbVNTOptimizer {
	private static final int ITERATIONS = 5;

	private Graph graph;
	private Map<VirtualRegister, Operand> values;

	public DbVNTOptimizer(Graph graph) {
		this.graph = graph.refresh();
	}

	public void process() {
		for (int i = 0; i < ITERATIONS; ++i) {
			values = new HashMap<>();
			depthFirstSearch(graph.enter);
			graph.refresh();
		}
	}

	private void depthFirstSearch(Block block) {
		Map<Expression, Operand> expressions = new HashMap<>();
		for (int k = 0; k < block.phiFunctions.size(); ++k) {
			PhiFunctionInstruction i = (PhiFunctionInstruction)block.phiFunctions.get(k);
			Expression expression = new Expression("phi", i.getUsedOperands());
			if (new HashSet<>(i.getUsedOperands()).size() == 1) {
				values.put(i.destination, i.getUsedOperands().get(0));
				block.phiFunctions.set(k, null);
			} else if (expressions.containsKey(expression)) {
				values.put(i.destination, expressions.get(expression));
				block.phiFunctions.set(k, null);
			} else {
				values.put(i.destination, i.destination);
				expressions.put(expression, i.destination);
			}
		}
		for (int k = 0; k < block.instructions.size(); ++k) {
			Instruction instruction = block.instructions.get(k);
			if (instruction == null) {
				continue;
			}
			for (VirtualRegister register : instruction.getUsedRegisters()) {
				if (values.containsKey(register)) {
					instruction.setUsedRegister(register, values.get(register));
				}
			}
			block.instructions.set(k, instruction = instruction.rebuild());
			if (instruction instanceof ArithmeticInstruction) {
				ArithmeticInstruction i = (ArithmeticInstruction)instruction;
				VirtualRegister destination = instruction.getDefinedRegisters().get(0);
				Expression expression = new Expression(i.MIPSName(), i.getUsedOperands());
				if (expressions.containsKey(expression)) {
					values.put(destination, expressions.get(expression));
					if (!global(destination)) {
						block.instructions.set(k, null);
					}
				} else {
					values.put(destination, destination);
					expressions.put(expression, destination);
				}
			} else if (instruction instanceof MoveInstruction) {
				MoveInstruction i = (MoveInstruction)instruction;
				if (i.source instanceof ImmediateValue) {
					if (!global(i.destination)) {
						values.put(i.destination, i.source);
						block.instructions.set(k, null);
					}
				}
			}
		}
		for (Block successor : block.successors) {
			for (Instruction instruction : successor.phiFunctions) {
				if (instruction == null) {
					continue;
				}
				for (VirtualRegister register : instruction.getUsedRegisters()) {
					if (values.containsKey(register)) {
						instruction.setUsedRegister(register, values.get(register));
					}
				}
			}
		}
		for (Block child : block.dominance.children) {
			depthFirstSearch(child);
		}
	}

	private boolean global(VirtualRegister register) {
		if (register instanceof GlobalRegister) {
			return true;
		} else if (register instanceof CloneRegister) {
			CloneRegister clone = (CloneRegister)register;
			if (clone.origin instanceof GlobalRegister) {
				return true;
			}
		}
		return false;
	}

	public class Expression {
		String operator;
		List<Operand> operands;

		public Expression(String operator, List<Operand> operands) {
			this.operator = operator;
			this.operands = operands;
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(operator);
			for (Operand operand : operands) {
				stringBuilder.append(" ").append(operand);
			}
			return stringBuilder.toString();
		}

		@Override
		public boolean equals(Object object) {
			if (object instanceof Expression) {
				Expression other = (Expression)object;
				return other.operator.equals(operator) && other.operands.equals(operands);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return operator.hashCode() * 4423 + operands.hashCode();
		}
	}
}
