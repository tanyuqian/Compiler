// Generated from /home/tan/Compiler/Meh/src/FrontEnd/ConcreteSyntaxTree/Meh.g4 by ANTLR 4.6
package FrontEnd.ConcreteSyntaxTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MehParser}.
 */
public interface MehListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MehParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MehParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MehParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MehParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MehParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MehParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MehParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MehParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MehParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MehParser#variableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarationStatement(MehParser.VariableDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#variableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarationStatement(MehParser.VariableDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MehParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(MehParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(MehParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MehParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MehParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MehParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MehParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(MehParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(MehParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MehParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(MehParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MehParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(MehParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MehParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MehParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MehParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MehParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStatement}
	 * labeled alternative in {@link MehParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MehParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStatement}
	 * labeled alternative in {@link MehParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MehParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link MehParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MehParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link MehParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MehParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStatement}
	 * labeled alternative in {@link MehParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(MehParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStatement}
	 * labeled alternative in {@link MehParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(MehParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStatement}
	 * labeled alternative in {@link MehParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(MehParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStatement}
	 * labeled alternative in {@link MehParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(MehParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constantExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(MehParser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constantExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(MehParser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code shiftExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(MehParser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code shiftExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(MehParser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(MehParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(MehParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscriptExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptExpression(MehParser.SubscriptExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscriptExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptExpression(MehParser.SubscriptExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inclusiveOrExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveOrExpression(MehParser.InclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inclusiveOrExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveOrExpression(MehParser.InclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpression(MehParser.NewExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpression(MehParser.NewExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(MehParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(MehParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationExpression(MehParser.RelationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationExpression(MehParser.RelationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(MehParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(MehParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOrExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(MehParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOrExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(MehParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpression(MehParser.VariableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpression(MehParser.VariableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(MehParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(MehParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exclusiveOrExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOrExpression(MehParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exclusiveOrExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOrExpression(MehParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(MehParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(MehParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAndExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(MehParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAndExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(MehParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFieldExpression(MehParser.FieldExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFieldExpression(MehParser.FieldExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(MehParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(MehParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(MehParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(MehParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpression(MehParser.SubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpression(MehParser.SubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code postfixExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(MehParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code postfixExpression}
	 * labeled alternative in {@link MehParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(MehParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(MehParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(MehParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(MehParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(MehParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void enterStringType(MehParser.StringTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void exitStringType(MehParser.StringTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code voidType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void enterVoidType(MehParser.VoidTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code voidType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void exitVoidType(MehParser.VoidTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(MehParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(MehParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code classType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void enterClassType(MehParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code classType}
	 * labeled alternative in {@link MehParser#type}.
	 * @param ctx the parse tree
	 */
	void exitClassType(MehParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterBoolConstant(MehParser.BoolConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitBoolConstant(MehParser.BoolConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterIntConstant(MehParser.IntConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitIntConstant(MehParser.IntConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterStringConstant(MehParser.StringConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitStringConstant(MehParser.StringConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNullConstant(MehParser.NullConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullConstant}
	 * labeled alternative in {@link MehParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNullConstant(MehParser.NullConstantContext ctx);
}