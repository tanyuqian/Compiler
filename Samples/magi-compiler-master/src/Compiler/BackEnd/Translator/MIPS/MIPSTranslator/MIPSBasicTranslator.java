package Compiler.BackEnd.Translator.MIPS.MIPSTranslator;

import Compiler.BackEnd.Allocator.Allocator;
import Compiler.BackEnd.Allocator.PhysicalRegister;
import Compiler.BackEnd.ControlFlowGraph.Block;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.ArithmeticInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.*;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.UnaryInstruction.UnaryInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.ControlFlowInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.FunctionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.ReturnInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.*;
import Compiler.BackEnd.ControlFlowGraph.Operand.Address;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.CloneRegister;
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

public class MIPSBasicTranslator extends MIPSTranslator {
	private Graph graph;
	private Allocator allocator;

	public MIPSBasicTranslator(PrintStream output) {
		super(output);
	}

	private PhysicalRegister loadToRead(Operand from, PhysicalRegister to) {
		if (from instanceof VirtualRegister) {
			if (from instanceof VariableRegister) {
				if (from instanceof GlobalRegister) {
					output.printf("\tlw %s, %s\n", to, getGlobalVariableName(from));
				} else if (from instanceof ParameterRegister) {
					output.printf("\tlw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
				} else if (from instanceof TemporaryRegister) {
					PhysicalRegister register = allocator.mapping.get(from);
					if (register != null) {
						return register;
					} else {
						output.printf("\tlw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
					}
				} else {
					throw new InternalError();
				}
			} else if (from instanceof CloneRegister) {
				throw new InternalError();
			} else if (from instanceof StringRegister) {
				output.printf("\tla %s, %s\n", to, getStringConstantName(from));
			} else {
				throw new InternalError();
			}
		} else if (from instanceof Address) {
			throw new InternalError();
		} else if (from instanceof ImmediateValue) {
			output.printf("\tli %s, %s\n", to, from);
		} else {
			throw new InternalError();
		}
		return to;
	}

	private PhysicalRegister loadToWrite(VirtualRegister from, PhysicalRegister to) {
		if (from instanceof TemporaryRegister) {
			PhysicalRegister register = allocator.mapping.get(from);
			if (register != null) {
				return register;
			}
		}
		return to;
	}

	private void store(VirtualRegister from, PhysicalRegister to) {
		if (from instanceof VariableRegister) {
			if (from instanceof GlobalRegister) {
				output.printf("\tsw %s, %s\n", to, getGlobalVariableName(from));
			} else if (from instanceof ParameterRegister) {
				output.printf("\tsw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
			} else if (from instanceof TemporaryRegister) {
				PhysicalRegister register = allocator.mapping.get(from);
				if (register == null) {
					output.printf("\tsw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
				}
			} else {
				throw new InternalError();
			}
		} else if (from instanceof CloneRegister) {
			throw new InternalError();
		} else if (from instanceof StringRegister) {
			throw new InternalError();
		} else {
			throw new InternalError();
		}
	}

	private void move(Operand from, PhysicalRegister to) {
		if (from instanceof VirtualRegister) {
			if (from instanceof VariableRegister) {
				if (from instanceof GlobalRegister) {
					output.printf("\tlw %s, %s\n", to, getGlobalVariableName(from));
				} else if (from instanceof ParameterRegister) {
					output.printf("\tlw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
				} else if (from instanceof TemporaryRegister) {
					PhysicalRegister register = allocator.mapping.get(from);
					if (register != null) {
						if (register != to) {
							output.printf("\tmove %s, %s\n", to, register);
						}
					} else {
						output.printf("\tlw %s, %d(%s)\n", to, graph.frame.getOffset(from), MIPSRegister.sp);
					}
				} else {
					throw new InternalError();
				}
			} else if (from instanceof CloneRegister) {
				throw new InternalError();
			} else if (from instanceof StringRegister) {
				output.printf("\tla %s, %s\n", to, getStringConstantName(from));
			} else {
				throw new InternalError();
			}
		} else if (from instanceof Address) {
			throw new InternalError();
		} else if (from instanceof ImmediateValue) {
			output.printf("\tli %s, %s\n", to, from);
		} else {
			throw new InternalError();
		}
	}

	private void move(PhysicalRegister from, VirtualRegister to) {
		if (to instanceof VariableRegister) {
			if (to instanceof GlobalRegister) {
				output.printf("\tsw %s, %s\n", from, getGlobalVariableName(to));
			} else if (to instanceof ParameterRegister) {
				output.printf("\tsw %s, %d(%s)\n", from, graph.frame.getOffset(to), MIPSRegister.sp);
			} else if (to instanceof TemporaryRegister) {
				PhysicalRegister register = allocator.mapping.get(to);
				if (register != null) {
					if (register != from) {
						output.printf("\tmove %s, %s\n", register, from);
					}
				} else {
					output.printf("\tsw %s, %d(%s)\n", from, graph.frame.getOffset(to), MIPSRegister.sp);
				}
			} else {
				throw new InternalError();
			}
		} else if (to instanceof CloneRegister) {
			throw new InternalError();
		} else if (to instanceof StringRegister) {
			throw new InternalError();
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void translate(Graph graph) {
		this.graph = graph;
		this.allocator = graph.function.allocator;
		output.printf("%s:\n", getFunctionName(graph.function));
		output.printf("\tsub %s, %s, %d\n", MIPSRegister.sp, MIPSRegister.sp, graph.frame.size);
		if (graph.containsCall()) {
			output.printf("\tsw %s, %d(%s)\n", MIPSRegister.ra, MIPSRegister.ra.identity * MIPSRegister.size(), MIPSRegister.sp);
		}
		if (!graph.function.name.equals("main")) {
			for (PhysicalRegister register : allocator.getUsedPhysicalRegisters()) {
				output.printf("\tsw %s, %d(%s)\n", register, register.identity * MIPSRegister.size(), MIPSRegister.sp);
			}
		}
		graph.refresh();
		for (int k = 0; k < graph.blocks.size(); ++k) {
			Block block = graph.blocks.get(k);
			output.printf("\n%s:\n", getBlockName(block));

			Instruction reserved = null;
			for (int l = 0; l < block.instructions.size(); ++l) {
				Instruction instruction = block.instructions.get(l);
//				output.printf("\n#\t%s\n", instruction);

				if (instruction instanceof ArithmeticInstruction) {
					if (instruction instanceof BinaryInstruction) {
						BinaryInstruction i = (BinaryInstruction)instruction;
						if (l + 1 < block.instructions.size() && block.instructions.get(l + 1) instanceof BranchInstruction) {
							BranchInstruction j = (BranchInstruction)block.instructions.get(l + 1);
							if (j.condition == i.destination && !block.liveliness.liveOut.contains(i.destination)) {
								reserved = instruction;
								continue;
							}
						}
						if (i.source2 instanceof ImmediateValue) {
							PhysicalRegister a = loadToRead(i.source1, MIPSRegister.temporary1);
							PhysicalRegister c = loadToWrite(i.destination, MIPSRegister.temporary2);
							output.printf("\t%s %s, %s, %s\n", i.MIPSName(), c, a, i.source2);
							store(i.destination, c);
						} else {
							PhysicalRegister a = loadToRead(i.source1, MIPSRegister.temporary1);
							PhysicalRegister b = loadToRead(i.source2, MIPSRegister.temporary2);
							PhysicalRegister c = loadToWrite(i.destination, MIPSRegister.temporary2);
							output.printf("\t%s %s, %s, %s\n", i.MIPSName(), c, a, b);
							store(i.destination, c);
						}
					} else if (instruction instanceof UnaryInstruction) {
						UnaryInstruction i = (UnaryInstruction)instruction;
						PhysicalRegister a = loadToRead(i.source, MIPSRegister.temporary1);
						PhysicalRegister b = loadToWrite(i.destination, MIPSRegister.temporary1);
						output.printf("\t%s %s, %s\n", i.MIPSName(), b, a);
						store(i.destination, b);
					} else {
						throw new InternalError();
					}
				} else if (instruction instanceof ControlFlowInstruction) {
					if (instruction instanceof BranchInstruction) {
						BranchInstruction i = (BranchInstruction)instruction;
						if (reserved == null) {
							PhysicalRegister a = loadToRead(i.condition, MIPSRegister.temporary1);
							output.printf("\tbeqz %s, %s\n", a, getBlockName(i.falseTo.block));
						} else {
							BinaryInstruction j = (BinaryInstruction)reserved;
							if (j instanceof BitwiseXorInstruction) {
								PhysicalRegister a = loadToRead(j.source1, MIPSRegister.temporary1);
								output.printf("\tbnez %s, %s\n", a, getBlockName(i.falseTo.block));
							} else {
								PhysicalRegister a = loadToRead(j.source1, MIPSRegister.temporary1);
								PhysicalRegister b = loadToRead(j.source2, MIPSRegister.temporary2);
								if (j instanceof EqualToInstruction) {
									output.printf("\tbne %s, %s, %s\n", a, b, getBlockName(i.falseTo.block));
								} else if (j instanceof GreaterThanInstruction) {
									output.printf("\tble %s, %s, %s\n", a, b, getBlockName(i.falseTo.block));
								} else if (j instanceof GreaterThanOrEqualToInstruction) {
									output.printf("\tblt %s, %s, %s\n", a, b, getBlockName(i.falseTo.block));
								} else if (j instanceof LessThanInstruction) {
									output.printf("\tbge %s, %s, %s\n", a, b, getBlockName(i.falseTo.block));
								} else if (j instanceof LessThanOrEqualToInstruction) {
									output.printf("\tbgt %s, %s, %s\n", a, b, getBlockName(i.falseTo.block));
								} else if (j instanceof NotEqualToInstruction) {
									output.printf("\tbeq %s, %s, %s\n", a, b, getBlockName(i.falseTo.block));
								}
							}
							reserved = null;
						}
						if (k + 1 == graph.blocks.size() || graph.blocks.get(k + 1) != i.trueTo.block) {
							output.printf("\tj %s\n", getBlockName(i.trueTo.block));
						}
					} else if (instruction instanceof JumpInstruction) {
						JumpInstruction i = (JumpInstruction)instruction;
						if (k + 1 == graph.blocks.size() || graph.blocks.get(k + 1) != i.to.block) {
							output.printf("\tj %s\n", getBlockName(i.to.block));
						}
					} else {
						throw new InternalError();
					}
				} else if (instruction instanceof FunctionInstruction) {
					if (instruction instanceof CallInstruction) {
						CallInstruction i = (CallInstruction)instruction;
						Function function = i.function;
						if (function.name.startsWith("____builtin")) {
							if (i.parameters.size() >= 1) {
								move(i.parameters.get(0), MIPSRegister.a0);
							}
							if (i.parameters.size() >= 2) {
								move(i.parameters.get(1), MIPSRegister.a1);
							}
							if (i.parameters.size() >= 3) {
								move(i.parameters.get(2), MIPSRegister.a2);
							}
						} else {
							for (int p = 0; p < function.parameters.size(); ++p) {
								PhysicalRegister a = loadToRead(i.parameters.get(p), MIPSRegister.temporary1);
								int offset = function.graph.frame.size - function.graph.frame.getOffset(function.parameters.get(p).register);
								output.printf("\tsw %s, %d(%s)\n", a, -offset, MIPSRegister.sp);
							}
						}
						output.printf("\tjal %s\n", getFunctionName(function));
						if (i.destination != null) {
							move(MIPSRegister.v0, i.destination);
						}
					} else if (instruction instanceof ReturnInstruction) {
						ReturnInstruction i = (ReturnInstruction)instruction;
						move(i.source, MIPSRegister.v0);
					} else {
						throw new InternalError();
					}
				} else if (instruction instanceof MemoryInstruction) {
					if (instruction instanceof AllocateInstruction) {
						AllocateInstruction i = (AllocateInstruction)instruction;
						move(i.size, MIPSRegister.a0);
						output.printf("\tli %s, %d\n", MIPSRegister.v0, 9);
						output.printf("\tsyscall\n");
						move(MIPSRegister.v0, i.destination);
					} else if (instruction instanceof LoadInstruction) {
						LoadInstruction i = (LoadInstruction)instruction;
						PhysicalRegister a = loadToRead(i.address.base, MIPSRegister.temporary1);
						PhysicalRegister b = loadToWrite(i.destination, MIPSRegister.temporary2);
						output.printf("\t%s %s, %s(%s)\n", i.MIPSName(), b, i.address.offset, a);
						store(i.destination, b);
					} else if (instruction instanceof MoveInstruction) {
						MoveInstruction i = (MoveInstruction)instruction;
						if (i.source instanceof ImmediateValue) {
							PhysicalRegister a = loadToWrite(i.destination, MIPSRegister.temporary1);
							output.printf("\tli %s %s\n", a, i.source);
							store(i.destination, a);
						} else {
							PhysicalRegister a = loadToRead(i.source, MIPSRegister.temporary1);
							move(a, i.destination);
						}
					} else if (instruction instanceof StoreInstruction) {
						StoreInstruction i = (StoreInstruction)instruction;
						PhysicalRegister a = loadToRead(i.address.base, MIPSRegister.temporary1);
						PhysicalRegister b = loadToRead(i.source, MIPSRegister.temporary2);
						output.printf("\t%s %s, %s(%s)\n", i.MIPSName(), b, i.address.offset, a);
					} else {
						throw new InternalError();
					}
				} else {
					throw new InternalError();
				}
			}
		}
		if (!graph.function.name.equals("main")) {
			for (PhysicalRegister register : allocator.getUsedPhysicalRegisters()) {
				output.printf("\tlw %s, %d(%s)\n", register, register.identity * MIPSRegister.size(), MIPSRegister.sp);
			}
		}
		if (graph.containsCall()) {
			output.printf("\tlw %s, %d(%s)\n", MIPSRegister.ra, MIPSRegister.ra.identity * MIPSRegister.size(), MIPSRegister.sp);
		}
		output.printf("\taddu %s, %s, %d\n", MIPSRegister.sp, MIPSRegister.sp, graph.frame.size);
		output.printf("\tjr %s\n", MIPSRegister.ra);
		output.println();
	}
}