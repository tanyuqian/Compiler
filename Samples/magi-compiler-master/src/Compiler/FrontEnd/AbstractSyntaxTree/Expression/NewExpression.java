package Compiler.FrontEnd.AbstractSyntaxTree.Expression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.AdditionInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.MultiplicationInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.AllocateInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Address;
import Compiler.BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Error.InternalError;
import Compiler.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class NewExpression extends Expression {
	public List<Expression> expressions;
	public Function constructor;
	public List<Expression> parameters;

	private NewExpression(Type type, boolean isLeftValue, List<Expression> expressions) {
		super(type, isLeftValue);
		this.expressions = expressions;
		this.constructor = null;
		this.parameters = new ArrayList<>();
	}

	public static Expression getExpression(Type type, List<Expression> dimensionExpressions) {
		if (dimensionExpressions.isEmpty()) {
			if (type instanceof ClassType) {
				return new NewExpression(type, false, dimensionExpressions);
			}
			throw new CompilationError("an array/class type is expected in the new expression");
		} else {
			Type arrayType = ArrayType.getType(type, dimensionExpressions.size());
			return new NewExpression(arrayType, false, dimensionExpressions);
		}
	}

	@Override
	public String toString() {
		return "[expression: new, type = " + type + "]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(this).append("\n");
		expressions.stream()
				.filter(expression -> expression != null)
				.forEach(expression -> stringBuilder.append(expression.toString(indents + 1)));
		if (constructor != null) {
			stringBuilder.append(Utility.getIndent(indents)).append("[constructor]").append("\n");
			parameters.forEach(expression -> stringBuilder.append(expression.toString(indents + 1)));
		}
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		expressions.stream().filter(expression -> expression != null).forEach(expression -> {
			expression.emit(instructions);
			expression.load(instructions);
		});
		operand = Environment.registerTable.addTemporaryRegister();
		if (type instanceof ClassType) {
			ClassType classType = (ClassType)type;
			instructions.add(AllocateInstruction.getInstruction(operand, new ImmediateValue(classType.allocateSize)));
			//	handle the class members' initial values
			classType.memberVariables.forEach((name, member) -> {
				Address address = new Address((VirtualRegister)operand, new ImmediateValue(member.offset), member.type.size());
				if (member.expression != null) {
					member.expression.emit(instructions);
					member.expression.load(instructions);
					instructions.add(StoreInstruction.getInstruction(member.expression.operand, address));
				}
			});
			if (constructor == null) {
				//	match the class constructor
				if (!classType.constructors.isEmpty()) {
					List<Type> parameterTypes = new ArrayList<Type>() {{
						add(classType);
					}};
					if (!classType.constructors.containsKey(parameterTypes)) {
						throw new CompilationError("the class \"" + classType.name + "\" has no matching constructor");
					}
					constructor = classType.constructors.get(parameterTypes);
				}
			}
			if (constructor != null) {
				//	handle the class constructor
				List<Operand> operands = new ArrayList<Operand>() {{
					add(operand);
					parameters.forEach(parameter -> {
						parameter.emit(instructions);
						parameter.load(instructions);
						add(parameter.operand);
					});
				}};
				instructions.add(CallInstruction.getInstruction(null, constructor, operands));
			}
		} else if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType)type;
			VirtualRegister size = Environment.registerTable.addTemporaryRegister();
			instructions.add(MultiplicationInstruction.getInstruction(size, expressions.get(0).operand, new ImmediateValue(arrayType.reduce().size())));
			instructions.add(AdditionInstruction.getInstruction(size, size, new ImmediateValue(IntType.getType().size())));
			instructions.add(AllocateInstruction.getInstruction(operand, size));
			instructions.add(StoreInstruction.getInstruction(expressions.get(0).operand, new Address((VirtualRegister)operand, IntType.getType().size())));
			instructions.add(AdditionInstruction.getInstruction(operand, operand, new ImmediateValue(IntType.getType().size())));
		} else {
			throw new InternalError();
		}
	}
}
