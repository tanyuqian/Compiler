// Generated from /home/tan/Compiler/Samples/magi-compiler-master/src/Compiler/FrontEnd/ConcreteSyntaxTree/Parser/Magi.g4 by ANTLR 4.6
package Compiler.FrontEnd.ConcreteSyntaxTree.Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MagiParser}.
 */
public interface MagiListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MagiParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MagiParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MagiParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MagiParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MagiParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MagiParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MagiParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MagiParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MagiParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MagiParser#variableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarationStatement(MagiParser.VariableDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#variableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarationStatement(MagiParser.VariableDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MagiParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MagiParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MagiParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MagiParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(MagiParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(MagiParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MagiParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(MagiParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(MagiParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MagiParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(MagiParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MagiParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(MagiParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MagiParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MagiParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MagiParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MagiParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStatement}
	 * labeled alternative in {@link MagiParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MagiParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStatement}
	 * labeled alternative in {@link MagiParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MagiParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStatement}
	 * labeled alternative in {@link MagiParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(MagiParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStatement}
	 * labeled alternative in {@link MagiParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(MagiParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStatement}
	 * labeled alternative in {@link MagiParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(MagiParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStatement}
	 * labeled alternative in {@link MagiParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(MagiParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link MagiParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MagiParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link MagiParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MagiParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constantExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(MagiParser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constantExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(MagiParser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code shiftExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(MagiParser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code shiftExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(MagiParser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(MagiParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(MagiParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscriptExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptExpression(MagiParser.SubscriptExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscriptExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptExpression(MagiParser.SubscriptExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(MagiParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(MagiParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inclusiveOrExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveOrExpression(MagiParser.InclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inclusiveOrExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveOrExpression(MagiParser.InclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpression(MagiParser.NewExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpression(MagiParser.NewExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(MagiParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(MagiParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(MagiParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(MagiParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOrExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(MagiParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOrExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(MagiParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpression(MagiParser.VariableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpression(MagiParser.VariableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(MagiParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(MagiParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exclusiveOrExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOrExpression(MagiParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exclusiveOrExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOrExpression(MagiParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(MagiParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(MagiParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAndExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(MagiParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAndExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(MagiParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFieldExpression(MagiParser.FieldExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFieldExpression(MagiParser.FieldExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(MagiParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(MagiParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(MagiParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(MagiParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpression(MagiParser.SubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpression(MagiParser.SubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code postfixExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(MagiParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code postfixExpression}
	 * labeled alternative in {@link MagiParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(MagiParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(MagiParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(MagiParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(MagiParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(MagiParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void enterStringType(MagiParser.StringTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void exitStringType(MagiParser.StringTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code voidType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void enterVoidType(MagiParser.VoidTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code voidType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void exitVoidType(MagiParser.VoidTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(MagiParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(MagiParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code classType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void enterClassType(MagiParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code classType}
	 * labeled alternative in {@link MagiParser#type}.
	 * @param ctx the parse tree
	 */
	void exitClassType(MagiParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterBoolConstant(MagiParser.BoolConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitBoolConstant(MagiParser.BoolConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterIntConstant(MagiParser.IntConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitIntConstant(MagiParser.IntConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterStringConstant(MagiParser.StringConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitStringConstant(MagiParser.StringConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNullConstant(MagiParser.NullConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullConstant}
	 * labeled alternative in {@link MagiParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNullConstant(MagiParser.NullConstantContext ctx);
}