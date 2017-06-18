package Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction;

import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.VariableDeclarationStatement;
import Compiler.Utility.Error.InternalError;

import java.util.ArrayList;
import java.util.List;

public class CallInstruction extends FunctionInstruction {
	public VirtualRegister destination;
	public Function function;
	public List<Operand> parameters;

	private CallInstruction(VirtualRegister destination, Function function, List<Operand> parameters) {
		this.destination = destination;
		this.function = function;
		this.parameters = parameters;
	}

	public static Instruction getInstruction(Operand destination, Function function, List<Operand> parameters) {
		if (destination == null) {
			return new CallInstruction(null, function, parameters);
		} else if (destination instanceof VirtualRegister) {
			return new CallInstruction((VirtualRegister)destination, function, parameters);
		}
		throw new InternalError();
	}

	@Override
	public List<Operand> getDefinedOperands() {
		return new ArrayList<Operand>() {{
			if (destination != null) {
				add(destination);
			}
			if (!function.name.startsWith("____builtin")) {
				for (VariableDeclarationStatement variable : Environment.program.globalVariables) {
					add(variable.symbol.register);
				}
			}
		}};
	}

	@Override
	public List<Operand> getUsedOperands() {
		return new ArrayList<Operand>() {{
			addAll(parameters);
			if (!function.name.startsWith("____builtin")) {
				for (VariableDeclarationStatement variable : Environment.program.globalVariables) {
					add(variable.symbol.register);
				}
			}
		}};
	}

	@Override
	public void setDefinedRegister(VirtualRegister from, VirtualRegister to) {
		if (destination == from) {
			destination = to;
		}
	}

	@Override
	public void setUsedRegister(VirtualRegister from, Operand to) {
		for (int i = 0; i < parameters.size(); ++i) {
			if (parameters.get(i) == from) {
				parameters.set(i, to);
			}
		}
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if (destination != null) {
			stringBuilder.append(destination).append(" = ");
		}
		stringBuilder.append("call ").append(function.name);
		parameters.forEach(parameter -> stringBuilder.append(" ").append(parameter));
		return stringBuilder.toString();
	}
}
