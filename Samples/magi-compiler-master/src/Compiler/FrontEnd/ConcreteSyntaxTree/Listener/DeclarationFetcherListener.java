package Compiler.FrontEnd.ConcreteSyntaxTree.Listener;

import Compiler.Environment.Environment;
import Compiler.Environment.SymbolTable.Symbol;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.FrontEnd.ConcreteSyntaxTree.Parser.MagiParser;
import Compiler.Utility.Error.CompilationError;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class DeclarationFetcherListener extends BaseListener {
	@Override
	public void exitFunctionDeclaration(MagiParser.FunctionDeclarationContext ctx) {
		String name;
		Type type = VoidType.getType();
		ClassType classType = Environment.scopeTable.getClassScope();
		boolean isConstructorOrDestructor = !(ctx.IDENTIFIER().size() == ctx.type().size()), isDestructor = false;
		boolean isProtected = false, isPrivate = false;
		for (ParseTree parseTree : ctx.children) {
			if (parseTree instanceof TerminalNode) {
				TerminalNode token = (TerminalNode)parseTree;
				if (token.getText().equals("~")) {
					isDestructor = true;
				}
				if (token.getText().equals("protected")) {
					isProtected = true;
				}
				if (token.getText().equals("private")) {
					isPrivate = true;
				}
			}
		}
		if (!isConstructorOrDestructor) {
			name = ctx.IDENTIFIER(0).getText();
			type = (Type)returnNode.get(ctx.type(0));
		} else if (isDestructor) {
			//	class destructor
			if (classType == null) {
				throw new CompilationError("the class destructor should be put inside a class");
			}
			if (isProtected || isPrivate) {
				throw new CompilationError("the class destructor cannot be protected or private");
			}
			name = ctx.type(0).getText();
			if (!name.equals(classType.name)) {
				throw new CompilationError("the class destructor should have the same name as the class");
			}
			name = "~" + name;
		} else {
			//	class constructor
			if (classType == null) {
				throw new CompilationError("the class constructor should be put inside a class");
			}
			if (isProtected || isPrivate) {
				throw new CompilationError("the class constructor cannot be protected or private");
			}
			name = ctx.type(0).getText();
			if (!name.equals(classType.name)) {
				throw new CompilationError("the class constructor should have the same name as the class");
			}
		}
		if (classType == null) {
			//	global scope
			if (isProtected || isPrivate) {
				throw new CompilationError("the global function \"" + name + "\" cannot be protected or private");
			}
		}
		List<Symbol> parameters = new ArrayList<Symbol>() {{
			if (classType != null) {
				add(new Symbol("this", classType));
			}
			for (int i = 1; i < ctx.type().size(); ++i) {
				String parameterName = ctx.IDENTIFIER(i - (isConstructorOrDestructor ? 1 : 0)).getText();
				Type parameterType = (Type)returnNode.get(ctx.type(i));
				add(new Symbol(parameterName, parameterType));
			}
		}};
		if (isDestructor) {
			if (parameters.size() != 1) {
				throw new CompilationError("the class destructor cannot have any parameters");
			}
		}
		Function function = Function.getFunction(name, type, parameters);
		if (classType != null) {
			if (!isConstructorOrDestructor) {
				//	member function
				classType.addMember(name, function, isProtected, isPrivate);
			} else if (isDestructor) {
				//	class destructor
				classType.addDestructor(function);
			} else {
				//	class constructor
				classType.addConstructor(function);
			}
		} else {
			//	global function
			Environment.symbolTable.add(name, function);
		}
		Environment.program.addFunction(function);
		returnNode.put(ctx, function);
	}

	@Override
	public void enterClassDeclaration(MagiParser.ClassDeclarationContext ctx) {
		ClassType classType = (ClassType)returnNode.get(ctx);
		//	enter class scope
		Environment.enterScope(classType);
	}


	@Override
	public void exitClassDeclaration(MagiParser.ClassDeclarationContext ctx) {
		ClassType classType = (ClassType)returnNode.get(ctx);
		if (ctx.IDENTIFIER().size() == 2) {
			//	class *x* extends *y*
			String x = ctx.IDENTIFIER(0).getText();
			String y = ctx.IDENTIFIER(1).getText();
			if (!Environment.classTable.contains(y)) {
				throw new CompilationError("base type \"" + y + "\" is not a class name");
			}
			ClassType baseClass = Environment.classTable.get(y);
			ClassType extendClass = Environment.classTable.get(x);
			Environment.classTable.extendsFrom(extendClass, baseClass);
		}
		//	add class member variables
		ctx.variableDeclarationStatement().forEach(statementContext -> {
			int isProtected = 0, isPrivate = 0;
			for (ParseTree parseTree : statementContext.children) {
				if (parseTree instanceof TerminalNode) {
					TerminalNode token = (TerminalNode)parseTree;
					if (token.getText().equals("protected")) {
						isProtected = 1;
					}
					if (token.getText().equals("private")) {
						isPrivate = 1;
					}
				}
			}
			String memberName = statementContext.IDENTIFIER().getText();
			Type memberType = (Type)returnNode.get(statementContext.type());
			classType.addMember(memberName, memberType, isProtected == 1, isPrivate == 1);
		});
		//	exit class scope
		Environment.exitScope();
	}

	@Override
	public void exitVoidType(MagiParser.VoidTypeContext ctx) {
		returnNode.put(ctx, VoidType.getType());
	}

	@Override
	public void exitIntType(MagiParser.IntTypeContext ctx) {
		returnNode.put(ctx, IntType.getType());
	}

	@Override
	public void exitBoolType(MagiParser.BoolTypeContext ctx) {
		returnNode.put(ctx, BoolType.getType());
	}

	@Override
	public void exitStringType(MagiParser.StringTypeContext ctx) {
		returnNode.put(ctx, StringType.getType());
	}

	@Override
	public void exitClassType(MagiParser.ClassTypeContext ctx) {
		String className = ctx.getText();
		if (!Environment.classTable.contains(className)) {
			throw new CompilationError("the program has no type named \"" + className + "\"");
		}
		returnNode.put(ctx, Environment.classTable.get(className));
	}

	@Override
	public void exitArrayType(MagiParser.ArrayTypeContext ctx) {
		Type baseType = (Type)returnNode.get(ctx.type());
		returnNode.put(ctx, ArrayType.getType(baseType));
	}
}
