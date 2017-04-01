package Compiler.FrontEnd.AbstractSyntaxTree.Expression;

import Compiler.BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import Compiler.BackEnd.ControlFlowGraph.Instruction.Instruction;
import Compiler.BackEnd.ControlFlowGraph.Operand.Operand;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.FieldExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Error.InternalError;
import Compiler.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpression extends Expression {
	public Function function;
	public List<Expression> parameters;

	private FunctionCallExpression(Type type, boolean isLeftValue, Function function, List<Expression> parameters) {
		super(type, isLeftValue);
		this.function = function;
		this.parameters = parameters;
	}

	public static Expression getExpression(Function function, List<Expression> parameters) {
		return new FunctionCallExpression(function.type, false, function, parameters);
	}

	public static Expression getExpression(Expression expression, List<Expression> parameters) {
		if (expression instanceof NewExpression) {
			//	class constructor
			NewExpression newExpression = (NewExpression)expression;
			if (newExpression.type instanceof ClassType) {
				ClassType classType = (ClassType)newExpression.type;

				List<Type> parameterTypes = new ArrayList<Type>() {{
					add(classType);
					parameters.forEach(parameter -> add(parameter.type));
				}};

				if (!classType.constructors.containsKey(parameterTypes)) {
					throw new CompilationError("the class \"" + classType.name + "\" has no matching constructor");
				}
				newExpression.constructor = classType.constructors.get(parameterTypes);
				newExpression.parameters = parameters;
				return newExpression;
			}
			throw new CompilationError("the " + newExpression.type.toString() + "-type has no constructor");
		}
		if (expression.type instanceof Function) {
			Function function = (Function)expression.type;
			if (expression instanceof FieldExpression) {
				//	member function call : add "this" to the parameter list
				parameters.add(0, ((FieldExpression)expression).expression);
			}
			if (parameters.size() != function.parameters.size()) {
				throw new CompilationError("the number of parameters in the function-call expression is wrong");
			}
			for (int i = 0; i < parameters.size(); ++i) {
				if (i == 0 && expression instanceof FieldExpression) {
					//	no need to compare the type of "this"
					continue;
				}
				if (!parameters.get(i).type.compatibleWith(function.parameters.get(i).type)) {
					throw new CompilationError("the type of parameters in the function-call expression is wrong");
				}
			}
			return new FunctionCallExpression(function.type, false, function, parameters);
		}
		throw new CompilationError("a function is expected in the function-call expression");
	}

	@Override
	public String toString() {
		return "[expression: function-call, name = " + function.name + "]";
	}

	@Override
	public String toString(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utility.getIndent(indents)).append(this).append("\n");
		parameters.forEach(parameter -> stringBuilder.append(parameter.toString(indents + 1)));
		return stringBuilder.toString();
	}

	@Override
	public void emit(List<Instruction> instructions) {
		if (!peephole(instructions)) {
			List<Operand> operands = new ArrayList<>();
			for (Expression parameter : parameters) {
				parameter.emit(instructions);
				parameter.load(instructions);
				operands.add(parameter.operand);
			}
			if (type instanceof VoidType) {
				instructions.add(CallInstruction.getInstruction(null, function, operands));
			} else {
				operand = Environment.registerTable.addTemporaryRegister();
				instructions.add(CallInstruction.getInstruction(operand, function, operands));
			}
		}
	}

	private boolean peephole(List<Instruction> instructions) {
		if (function.name.startsWith("____builtin____print")) {
			if (parameters.size() != 1) {
				throw new InternalError();
			}
			Expression parameter = parameters.get(0);
			if (parameter instanceof FunctionCallExpression) {
				FunctionCallExpression call = (FunctionCallExpression)parameter;
				List<Expression> expressions = new ArrayList<>();
				if (call.function.name.equals("____builtin_string____concatenate")) {
					Expression current = call;
					while (current instanceof FunctionCallExpression) {
						FunctionCallExpression now = (FunctionCallExpression)current;
						if (!now.function.name.equals("____builtin_string____concatenate")) {
							break;
						}
						current = now.parameters.get(0);
						expressions.add(0, now.parameters.get(1));
					}
					expressions.add(0, current);
				} else {
					expressions.add(call);
				}
				int size = expressions.size() - (function.name.endsWith("ln") ? 1 : 0);
				for (int i = 0; i < size; ++i) {
					if (expressions.get(i) instanceof FunctionCallExpression) {
						FunctionCallExpression sub = (FunctionCallExpression)expressions.get(i);
						if (sub.function.name.equals("____builtin____to_string")) {
							emit(instructions, "printInt", sub.parameters.get(0));
							continue;
						}
					}
					emit(instructions, "print", expressions.get(i));
				}
				for (int i = size; i < expressions.size(); ++i) {
					emit(instructions, "println", expressions.get(i));
				}
				return true;
			}
		}
		return false;
	}

	private void emit(List<Instruction> instructions, String name, Expression parameter) {
		Function function = (Function)Environment.symbolTable.get(name).type;
		parameter.emit(instructions);
		parameter.load(instructions);
		List<Operand> operands = new ArrayList<Operand>() {{
			add(parameter.operand);
		}};
		instructions.add(CallInstruction.getInstruction(null, function, operands));
	}
}
