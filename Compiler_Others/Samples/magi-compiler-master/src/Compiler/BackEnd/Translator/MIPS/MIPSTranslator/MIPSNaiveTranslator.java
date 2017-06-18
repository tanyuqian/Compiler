package Compiler.BackEnd.Translator.MIPS.MIPSTranslator;

import Compiler.BackEnd.Allocator.PhysicalRegister;
import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.BinaryInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.UnaryInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.ControlFlowInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.*;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.StringRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.GlobalRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.ParameterRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.TemporaryRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VariableRegister.VariableRegister;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.BackEnd.Translator.MIPS.MIPSRegister;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.Utility.Error.InternalError;

import java.io.PrintStream;

public class MIPSNaiveTranslator extends MIPSTranslator {
	private Graph graph;

	public MIPSNaiveTranslator(PrintStream output) {
		super(output);
	}

	private void load(Operand from, PhysicalRegister to) {
		if (from instanceof ImmediateValue) {
			output.printf("\tli %s, %s\n", to, from);
		} else if (from instanceof VirtualRegister) {
			if (from instanceof StringRegister) {
				output.printf("\tla %s, %s\n", to, getStringConstantName(from));
			} else if (from instanceof VariableRegister) {
				if (from instanceof GlobalRegister) {
					output.printf("\tlw %s, %s\n", to, getGlobalVariableName(from));
				} else if (from instanceof TemporaryRegister) {
					output.printf("\tlw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
				} else if (from instanceof ParameterRegister) {
					output.printf("\tlw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
				}
			}
		}
	}

	private void store(VirtualRegister from, PhysicalRegister to) {
		if (from instanceof StringRegister) {
			throw new InternalError();
		} else if (from instanceof VariableRegister) {
			if (from instanceof GlobalRegister) {
				output.printf("\tsw %s, %s\n", to, getGlobalVariableName(from));
			} else if (from instanceof TemporaryRegister) {
				output.printf("\tsw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
			} else if (from instanceof ParameterRegister) {
				output.printf("\tsw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
			}
		}
	}

	@Override
	public void translate(Graph graph) {
		this.graph = graph;
		output.printf("%s:\n", getFunctionName(graph.function));
		output.printf("\tsub %s, %s, %d\n", MIPSRegister.sp, MIPSRegister.sp, graph.frame.size);
		output.printf("\tsw %s, %d(%s)\n", MIPSRegister.ra, MIPSRegister.ra.identity * MIPSRegister.size(), MIPSRegister.sp);
		for (int k = 0; k < graph.blocks.size(); ++k) {
			Block block = graph.blocks.get(k);
			output.printf("\n%s:\n", getBlockName(block));
			for (Instruction instruction : block.instructions) {
//				output.printf("\n#\t%s\n", instruction);
				if (instruction instanceof ArithmeticInstruction) {
					if (instruction instanceof UnaryInstruction) {
						UnaryInstruction i = (UnaryInstruction)instruction;
						load(i.source, MIPSRegister.v0);
						output.printf("\t%s %s, %s\n", i.MIPSName(), MIPSRegister.v0, MIPSRegister.v0);
						store(i.destination, MIPSRegister.v0);
					} else if (instruction instanceof BinaryInstruction) {
						BinaryInstruction i = (BinaryInstruction)instruction;
						load(i.source1, MIPSRegister.v0);
						load(i.source2, MIPSRegister.v1);
						output.printf("\t%s %s, %s, %s\n", i.MIPSName(), MIPSRegister.v0, MIPSRegister.v0, MIPSRegister.v1);
						store(i.destination, MIPSRegister.v0);
					}
				} else if (instruction instanceof ControlFlowInstruction) {
					if (instruction instanceof BranchInstruction) {
						BranchInstruction i = (BranchInstruction)instruction;
						load(i.condition, MIPSRegister.v0);
						output.printf("\tbeqz %s, %s\n", MIPSRegister.v0, getBlockName(i.falseTo.block));
						if (k + 1 == graph.blocks.size() || graph.blocks.get(k + 1) != i.trueTo.block) {
							output.printf("\tj %s\n", getBlockName(i.trueTo.block));
						}
					} else if (instruction instanceof JumpInstruction) {
						JumpInstruction i = (JumpInstruction)instruction;
						if (k + 1 == graph.blocks.size() || graph.blocks.get(k + 1) != i.to.block) {
							output.printf("\tj %s\n", getBlockName(i.to.block));
						}
					}
				} else if (instruction instanceof CallInstruction) {
					CallInstruction i = (CallInstruction)instruction;
					Function function = i.function;
					if (function.name.startsWith("____builtin")) {
						if (i.parameters.size() >= 1) {
							load(i.parameters.get(0), MIPSRegister.a0);
						}
						if (i.parameters.size() >= 2) {
							load(i.parameters.get(1), MIPSRegister.a1);
						}
						if (i.parameters.size() >= 3) {
							load(i.parameters.get(2), MIPSRegister.a2);
						}
						if (i.parameters.size() >= 4) {
							load(i.parameters.get(3), MIPSRegister.a3);
						}
					} else {
						for (int p = 0; p < function.parameters.size(); ++p) {
							load(i.parameters.get(p), MIPSRegister.v0);
							int offset = function.graph.frame.size - function.graph.frame.getOffset(function.parameters.get(p).register);
							output.printf("\tsw %s, %d(%s)\n", MIPSRegister.v0, -offset, MIPSRegister.sp);
						}
					}
					output.printf("\tjal %s\n", getFunctionName(function));
					if (i.destination != null) {
						store(i.destination, MIPSRegister.v0);
					}
				} else if (instruction instanceof ReturnInstruction) {
					ReturnInstruction i = (ReturnInstruction)instruction;
					load(i.source, MIPSRegister.v0);
					output.printf("\tsw %s, %d(%s)\n", MIPSRegister.v0, MIPSRegister.v0.identity * MIPSRegister.size(), MIPSRegister.sp);
				} else if (instruction instanceof MemoryInstruction) {
					if (instruction instanceof AllocateInstruction) {
						AllocateInstruction i = (AllocateInstruction)instruction;
						load(i.size, MIPSRegister.a0);
						output.printf("\tli %s, %d\n", MIPSRegister.v0, 9);
						output.printf("\tsyscall\n");
						store(i.destination, MIPSRegister.v0);
					} else if (instruction instanceof LoadInstruction) {
						LoadInstruction i = (LoadInstruction)instruction;
						load(i.address.base, MIPSRegister.v0);
						output.printf("\t%s %s, %d(%s)\n", i.MIPSName(), MIPSRegister.v1, i.address.offset.literal, MIPSRegister.v0);
						store(i.destination, MIPSRegister.v1);
					} else if (instruction instanceof MoveInstruction) {
						MoveInstruction i = (MoveInstruction)instruction;
						load(i.source, MIPSRegister.v0);
						store(i.destination, MIPSRegister.v0);
					} else if (instruction instanceof StoreInstruction) {
						StoreInstruction i = (StoreInstruction)instruction;
						load(i.source, MIPSRegister.v0);
						load(i.address.base, MIPSRegister.v1);
						output.printf("\t%s %s, %d(%s)\n", i.MIPSName(), MIPSRegister.v0, i.address.offset.literal, MIPSRegister.v1);
					}
				}
			}
		}
		output.printf("\tlw %s, %d(%s)\n", MIPSRegister.v0, MIPSRegister.v0.identity * MIPSRegister.size(), MIPSRegister.sp);
		output.printf("\tlw %s, %d(%s)\n", MIPSRegister.ra, MIPSRegister.ra.identity * MIPSRegister.size(), MIPSRegister.sp);
		output.printf("\taddu %s, %s, %d\n", MIPSRegister.sp, MIPSRegister.sp, graph.frame.size);
		output.printf("\tjr %s\n\n", MIPSRegister.ra);
	}
}
