package Compiler.FrontEnd.ConcreteSyntaxTree.Listener;

import Compiler.Environment.Environment;
import Compiler.Environment.SymbolTable.Symbol;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression.*;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.NullConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.Expression;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.FunctionCallExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.NewExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression.*;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.FieldExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.IdentifierExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.SubscriptExpression;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.*;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.ForStatement;
import Compiler.FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.WhileStatement;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.Member;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberVariable;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.Type;
import Compiler.FrontEnd.ConcreteSyntaxTree.Parser.MagiParser;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Error.InternalError;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilderListener extends BaseListener {
	@Override
	public void exitProgram(MagiParser.ProgramContext ctx) {
		ctx.variableDeclarationStatement().forEach(statementContext -> {
			VariableDeclarationStatement variableDeclaration = (VariableDeclarationStatement)returnNode.get(statementContext);
			Environment.program.addGlobalVariable(variableDeclaration);
		});
	}

	@Override
	public void enterFunctionDeclaration(MagiParser.FunctionDeclarationContext ctx) {
		Function function = (Function)returnNode.get(ctx);
		Environment.enterScope(function);
	}

	@Override
	public void exitFunctionDeclaration(MagiParser.FunctionDeclarationContext ctx) {
		Function function = (Function)returnNode.get(ctx);
		function.addStatements((BlockStatement)returnNode.get(ctx.blockStatement()));
		Environment.exitScope();
	}

	@Override
	public void enterClassDeclaration(MagiParser.ClassDeclarationContext ctx) {
		ClassType classType = (ClassType)returnNode.get(ctx);
		Environment.enterScope(classType);
		classType.memberVariables.forEach((name, member) -> Environment.symbolTable.add(name, member.type));
		classType.memberFunctions.forEach((name, member) -> Environment.symbolTable.add(name, member.function));
	}

	@Override
	public void exitClassDeclaration(MagiParser.ClassDeclarationContext ctx) {
		ClassType classType = (ClassType)returnNode.get(ctx);
		ctx.variableDeclarationStatement().forEach(statementContext -> {
			String name = statementContext.IDENTIFIER().getText();
			if (statementContext.expression() != null) {
				Member member = classType.getMember(name);
				if (member instanceof MemberVariable) {
					MemberVariable memberVariable = (MemberVariable)member;
					memberVariable.expression = (Expression)returnNode.get(statementContext.expression());
				}
			}
		});
		Environment.exitScope();
	}

	public void enterStatement(MagiParser.StatementContext ctx) {
		if (ctx.parent instanceof MagiParser.SelectionStatementContext) {
			//	handle : if (*) int *;
			Environment.enterScope(null);
		}
	}

	@Override
	public void exitStatement(MagiParser.StatementContext ctx) {
		if (ctx.parent instanceof MagiParser.SelectionStatementContext) {
			//	handle : if (*) int *;
			Environment.exitScope();
		}
		returnNode.put(ctx, returnNode.get(ctx.getChild(0)));
	}

	@Override
	public void enterBlockStatement(MagiParser.BlockStatementContext ctx) {
		BlockStatement blockStatement = (BlockStatement)BlockStatement.getStatement();
		Environment.enterScope(blockStatement);
		if (ctx.parent instanceof MagiParser.FunctionDeclarationContext) {
			Function function = (Function)returnNode.get(ctx.parent);
			for (int i = 0; i < function.parameters.size(); ++i) {
				Symbol parameter = function.parameters.get(i);
				System.out.println(parameter.name);
				function.parameters.set(i, Environment.symbolTable.addParameterVariable(parameter.name, parameter.type));
			}
		}
		returnNode.put(ctx, blockStatement);
	}

	@Override
	public void exitBlockStatement(MagiParser.BlockStatementContext ctx) {
		ctx.statement().forEach(statementContext -> {
			((BlockStatement)returnNode.get(ctx)).addStatement(
					(Statement)returnNode.get(statementContext)
			);
		});
		Environment.exitScope();
	}

	@Override
	public void exitExpressionStatement(MagiParser.ExpressionStatementContext ctx) {
		returnNode.put(ctx, ExpressionStatement.getStatement(
				(Expression)returnNode.get(ctx.expression())
		));
	}

	@Override
	public void exitSelectionStatement(MagiParser.SelectionStatementContext ctx) {
		returnNode.put(ctx, IfStatement.getStatement(
				(Expression)returnNode.get(ctx.expression()),
				(Statement)returnNode.get(ctx.statement(0)),
				(Statement)returnNode.get(ctx.statement(1))
		));
	}

	@Override
	public void enterWhileStatement(MagiParser.WhileStatementContext ctx) {
		WhileStatement whileStatement = (WhileStatement)WhileStatement.getStatement();
		Environment.enterScope(whileStatement);
		returnNode.put(ctx, whileStatement);
	}

	@Override
	public void exitWhileStatement(MagiParser.WhileStatementContext ctx) {
		WhileStatement whileStatement = (WhileStatement)returnNode.get(ctx);
		whileStatement.addCondition((Expression)returnNode.get(ctx.expression()));
		whileStatement.addStatement((Statement)returnNode.get(ctx.statement()));
		Environment.exitScope();
	}

	@Override
	public void enterForStatement(MagiParser.ForStatementContext ctx) {
		ForStatement forStatement = (ForStatement)ForStatement.getStatement();
		Environment.enterScope(forStatement);
		returnNode.put(ctx, forStatement);
	}

	@Override
	public void exitForStatement(MagiParser.ForStatementContext ctx) {
		ForStatement forStatement = (ForStatement)returnNode.get(ctx);
		forStatement.addStatement((Statement)returnNode.get(ctx.statement()));
		//	handle : for (*; *; *)
		int semicolons = 0;
		for (ParseTree parseTree : ctx.children) {
			if (parseTree.getText().equals(";")) {
				semicolons++;
			}
			if (parseTree instanceof MagiParser.ExpressionContext) {
				Expression expression = (Expression)returnNode.get(parseTree);
				if (semicolons == 0) {
					forStatement.addInitialization(expression);
				} else if (semicolons == 1) {
					forStatement.addCondition(expression);
				} else if (semicolons == 2) {
					forStatement.addIncrement(expression);
				} else {
					throw new InternalError();
				}
			}
		}
		//	exit loop scope
		Environment.exitScope();
	}

	@Override
	public void exitContinueStatement(MagiParser.ContinueStatementContext ctx) {
		returnNode.put(ctx, ContinueStatement.getStatement());
	}

	@Override
	public void exitBreakStatement(MagiParser.BreakStatementContext ctx) {
		returnNode.put(ctx, BreakStatement.getStatement());
	}

	@Override
	public void exitReturnStatement(MagiParser.ReturnStatementContext ctx) {
		Expression returnExpression = (Expression)returnNode.get(ctx.expression());
		returnNode.put(ctx, ReturnStatement.getStatement(returnExpression));
	}

	@Override
	public void exitVariableDeclarationStatement(MagiParser.VariableDeclarationStatementContext ctx) {
		if (!(ctx.parent instanceof MagiParser.ClassDeclarationContext)) {
			Type type = (Type)returnNode.get(ctx.type());
			String name = ctx.IDENTIFIER().getText();
			Symbol symbol;
			if (Environment.scopeTable.getScope() == Environment.program) {
				symbol = Environment.symbolTable.addGlobalVariable(name, type);
			} else {
				symbol = Environment.symbolTable.addTemporaryVariable(name, type);
			}
			Expression expression = (Expression)returnNode.get(ctx.expression());
			returnNode.put(ctx, VariableDeclarationStatement.getStatement(symbol, expression));
		}
	}

	@Override
	public void exitConstantExpression(MagiParser.ConstantExpressionContext ctx) {
		returnNode.put(ctx, returnNode.get(ctx.constant()));
	}

	@Override
	public void exitVariableExpression(MagiParser.VariableExpressionContext ctx) {
		returnNode.put(ctx, IdentifierExpression.getExpression(ctx.getText()));
	}

	@Override
	public void exitFieldExpression(MagiParser.FieldExpressionContext ctx) {
		returnNode.put(ctx, FieldExpression.getExpression(
				(Expression)returnNode.get(ctx.expression()),
				ctx.IDENTIFIER().getText()
		));
	}

	@Override
	public void exitSubscriptExpression(MagiParser.SubscriptExpressionContext ctx) {
		returnNode.put(ctx, SubscriptExpression.getExpression(
				(Expression)returnNode.get(ctx.expression(0)),
				(Expression)returnNode.get(ctx.expression(1))
		));
	}

	@Override
	public void exitSubExpression(MagiParser.SubExpressionContext ctx) {
		returnNode.put(ctx, returnNode.get(ctx.expression()));
	}

	@Override
	public void exitPostfixExpression(MagiParser.PostfixExpressionContext ctx) {
		Expression expression = (Expression)returnNode.get(ctx.expression());
		if (ctx.operator.getText().equals("++")) {
			returnNode.put(ctx, PostfixIncrementExpression.getExpression(expression));
		} else if (ctx.operator.getText().equals("--")) {
			returnNode.put(ctx, PostfixDecrementExpression.getExpression(expression));
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void exitUnaryExpression(MagiParser.UnaryExpressionContext ctx) {
		Expression expression = (Expression)returnNode.get(ctx.expression());
		if (ctx.operator.getText().equals("+")) {
			returnNode.put(ctx, UnaryPlusExpression.getExpression(expression));
		} else if (ctx.operator.getText().equals("-")) {
			returnNode.put(ctx, UnaryMinusExpression.getExpression(expression));
		} else if (ctx.operator.getText().equals("!")) {
			returnNode.put(ctx, LogicalNotExpression.getExpression(expression));
		} else if (ctx.operator.getText().equals("~")) {
			returnNode.put(ctx, BitwiseNotExpression.getExpression(expression));
		} else if (ctx.operator.getText().equals("++")) {
			returnNode.put(ctx, PrefixIncrementExpression.getExpression(expression));
		} else if (ctx.operator.getText().equals("--")) {
			returnNode.put(ctx, PrefixDecrementExpression.getExpression(expression));
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void exitFunctionCallExpression(MagiParser.FunctionCallExpressionContext ctx) {
		Expression function = (Expression)returnNode.get(ctx.expression(0));
		List<Expression> parameters = new ArrayList<>();
		for (int i = 1; i < ctx.expression().size(); ++i) {
			Expression parameter = (Expression)returnNode.get(ctx.expression(i));
			parameters.add(parameter);
		}
		returnNode.put(ctx, FunctionCallExpression.getExpression(function, parameters));
	}

	@Override
	public void exitNewExpression(MagiParser.NewExpressionContext ctx) {
		List<Expression> dimensionExpressions = new ArrayList<>();
		ctx.expression().forEach(expressionContext -> {
			Expression dimensionExpression = (Expression)returnNode.get(expressionContext);
			dimensionExpressions.add(dimensionExpression);
		});
		ctx.children.forEach(parseTree -> {
			if (parseTree instanceof TerminalNode) {
				Token token = ((TerminalNode)parseTree).getSymbol();
				if (token.getText().equals("[]")) {
					dimensionExpressions.add(null);
				}
			}
		});
		Type baseType = (Type)returnNode.get(ctx.type());
		returnNode.put(ctx, NewExpression.getExpression(baseType, dimensionExpressions));
	}

	@Override
	public void exitMultiplicativeExpression(MagiParser.MultiplicativeExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		if (ctx.operator.getText().equals("*")) {
			returnNode.put(ctx, MultiplicationExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals("/")) {
			returnNode.put(ctx, DivisionExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals("%")) {
			returnNode.put(ctx, ModuloExpression.getExpression(lhs, rhs));
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void exitAdditiveExpression(MagiParser.AdditiveExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		if (ctx.operator.getText().equals("+")) {
			returnNode.put(ctx, AdditionExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals("-")) {
			returnNode.put(ctx, SubtractionExpression.getExpression(lhs, rhs));
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void exitShiftExpression(MagiParser.ShiftExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		if (ctx.operator.getText().equals("<<")) {
			returnNode.put(ctx, BitwiseLeftShiftExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals(">>")) {
			returnNode.put(ctx, BitwiseRightShiftExpression.getExpression(lhs, rhs));
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void exitRelationalExpression(MagiParser.RelationalExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		if (ctx.operator.getText().equals("<")) {
			returnNode.put(ctx, LessThanExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals(">")) {
			returnNode.put(ctx, GreaterThanExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals("<=")) {
			returnNode.put(ctx, LessThanOrEqualToExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals(">=")) {
			returnNode.put(ctx, GreaterThanOrEqualToExpression.getExpression(lhs, rhs));
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void exitEqualityExpression(MagiParser.EqualityExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		if (ctx.operator.getText().equals("==")) {
			returnNode.put(ctx, EqualToExpression.getExpression(lhs, rhs));
		} else if (ctx.operator.getText().equals("!=")) {
			returnNode.put(ctx, NotEqualToExpression.getExpression(lhs, rhs));
		} else {
			throw new InternalError();
		}
	}

	@Override
	public void exitAndExpression(MagiParser.AndExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		returnNode.put(ctx, BitwiseAndExpression.getExpression(lhs, rhs));
	}

	@Override
	public void exitExclusiveOrExpression(MagiParser.ExclusiveOrExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		returnNode.put(ctx, BitwiseXorExpression.getExpression(lhs, rhs));
	}

	@Override
	public void exitInclusiveOrExpression(MagiParser.InclusiveOrExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		returnNode.put(ctx, BitwiseOrExpression.getExpression(lhs, rhs));
	}

	@Override
	public void exitLogicalAndExpression(MagiParser.LogicalAndExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		returnNode.put(ctx, LogicalAndExpression.getExpression(lhs, rhs));
	}

	@Override
	public void exitLogicalOrExpression(MagiParser.LogicalOrExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		returnNode.put(ctx, LogicalOrExpression.getExpression(lhs, rhs));
	}

	@Override
	public void exitAssignmentExpression(MagiParser.AssignmentExpressionContext ctx) {
		Expression lhs = (Expression)returnNode.get(ctx.expression(0));
		Expression rhs = (Expression)returnNode.get(ctx.expression(1));
		returnNode.put(ctx, AssignmentExpression.getExpression(lhs, rhs));
	}

	@Override
	public void exitBoolConstant(MagiParser.BoolConstantContext ctx) {
		returnNode.put(ctx, BoolConstant.getConstant(Boolean.valueOf(ctx.getText())));
	}

	@Override
	public void exitIntConstant(MagiParser.IntConstantContext ctx) {
		try {
			returnNode.put(ctx, IntConstant.getConstant(Integer.valueOf(ctx.getText())));
		} catch (NumberFormatException e) {
			throw new CompilationError("the number format is wrong");
		}
	}

	@Override
	public void exitStringConstant(MagiParser.StringConstantContext ctx) {
		returnNode.put(ctx, StringConstant.getConstant(ctx.getText().substring(1, ctx.getText().length() - 1)));
	}

	@Override
	public void exitNullConstant(MagiParser.NullConstantContext ctx) {
		returnNode.put(ctx, NullConstant.getConstant());
	}
}
