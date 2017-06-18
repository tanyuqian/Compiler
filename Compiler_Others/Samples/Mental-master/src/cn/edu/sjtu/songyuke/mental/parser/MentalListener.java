// Generated from /Users/Songyu/Projects/Mental/src/MentalParser/Mental.g4 by ANTLR 4.5.1
package cn.edu.sjtu.songyuke.mental.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MentalParser}.
 */
public interface MentalListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MentalParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(MentalParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(MentalParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(MentalParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(MentalParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(MentalParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(MentalParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MentalParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MentalParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MentalParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MentalParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#parametersList}.
	 * @param ctx the parse tree
	 */
	void enterParametersList(MentalParser.ParametersListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#parametersList}.
	 * @param ctx the parse tree
	 */
	void exitParametersList(MentalParser.ParametersListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MentalParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MentalParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(MentalParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(MentalParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MentalParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MentalParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MentalParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MentalParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(MentalParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(MentalParser.DefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#singleVariable}.
	 * @param ctx the parse tree
	 */
	void enterSingleVariable(MentalParser.SingleVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#singleVariable}.
	 * @param ctx the parse tree
	 */
	void exitSingleVariable(MentalParser.SingleVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinition(MentalParser.VariableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinition(MentalParser.VariableDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(MentalParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(MentalParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(MentalParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(MentalParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MentalParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MentalParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(MentalParser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(MentalParser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(MentalParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(MentalParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#ifElseStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStatement(MentalParser.IfElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#ifElseStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStatement(MentalParser.IfElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MentalParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MentalParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MentalParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MentalParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStatement(MentalParser.JumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStatement(MentalParser.JumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(MentalParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(MentalParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BIT_XOR_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBIT_XOR_EXPRESSION(MentalParser.BIT_XOR_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BIT_XOR_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBIT_XOR_EXPRESSION(MentalParser.BIT_XOR_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LOGICAL_NOT_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLOGICAL_NOT_EXPRESSION(MentalParser.LOGICAL_NOT_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LOGICAL_NOT_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLOGICAL_NOT_EXPRESSION(MentalParser.LOGICAL_NOT_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MEMBER_ACCESS_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMEMBER_ACCESS_EXPRESSION(MentalParser.MEMBER_ACCESS_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MEMBER_ACCESS_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMEMBER_ACCESS_EXPRESSION(MentalParser.MEMBER_ACCESS_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FUNCTION_CALL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFUNCTION_CALL(MentalParser.FUNCTION_CALLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FUNCTION_CALL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFUNCTION_CALL(MentalParser.FUNCTION_CALLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ADDITIVE_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterADDITIVE_EXPRESSION(MentalParser.ADDITIVE_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ADDITIVE_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitADDITIVE_EXPRESSION(MentalParser.ADDITIVE_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CREATION_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCREATION_EXPRESSION(MentalParser.CREATION_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CREATION_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCREATION_EXPRESSION(MentalParser.CREATION_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BIT_NOT_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBIT_NOT_EXPRESSION(MentalParser.BIT_NOT_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BIT_NOT_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBIT_NOT_EXPRESSION(MentalParser.BIT_NOT_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RELATION_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRELATION_EXPRESSION(MentalParser.RELATION_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RELATION_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRELATION_EXPRESSION(MentalParser.RELATION_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EQUALITY_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEQUALITY_EXPRESSION(MentalParser.EQUALITY_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EQUALITY_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEQUALITY_EXPRESSION(MentalParser.EQUALITY_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INT_LITERAL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterINT_LITERAL(MentalParser.INT_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INT_LITERAL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitINT_LITERAL(MentalParser.INT_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IDENTIFIER}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIDENTIFIER(MentalParser.IDENTIFIERContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IDENTIFIER}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIDENTIFIER(MentalParser.IDENTIFIERContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SUFFIX_INC_DEC_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSUFFIX_INC_DEC_EXPRESSION(MentalParser.SUFFIX_INC_DEC_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SUFFIX_INC_DEC_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSUFFIX_INC_DEC_EXPRESSION(MentalParser.SUFFIX_INC_DEC_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MULTIPLY_DIVIDE_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMULTIPLY_DIVIDE_EXPRESSION(MentalParser.MULTIPLY_DIVIDE_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MULTIPLY_DIVIDE_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMULTIPLY_DIVIDE_EXPRESSION(MentalParser.MULTIPLY_DIVIDE_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LOGICAL_OR_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLOGICAL_OR_EXPRESSION(MentalParser.LOGICAL_OR_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LOGICAL_OR_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLOGICAL_OR_EXPRESSION(MentalParser.LOGICAL_OR_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGN_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterASSIGN_EXPRESSION(MentalParser.ASSIGN_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGN_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitASSIGN_EXPRESSION(MentalParser.ASSIGN_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NULL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNULL(MentalParser.NULLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NULL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNULL(MentalParser.NULLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TRUE}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTRUE(MentalParser.TRUEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TRUE}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTRUE(MentalParser.TRUEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BIT_OR_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBIT_OR_EXPRESSION(MentalParser.BIT_OR_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BIT_OR_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBIT_OR_EXPRESSION(MentalParser.BIT_OR_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LOGICAL_AND_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLOGICAL_AND_EXPRESSION(MentalParser.LOGICAL_AND_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LOGICAL_AND_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLOGICAL_AND_EXPRESSION(MentalParser.LOGICAL_AND_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BIT_SHIFT_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBIT_SHIFT_EXPRESSION(MentalParser.BIT_SHIFT_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BIT_SHIFT_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBIT_SHIFT_EXPRESSION(MentalParser.BIT_SHIFT_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PREFIX_INC_DEC_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPREFIX_INC_DEC_EXPRESSION(MentalParser.PREFIX_INC_DEC_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PREFIX_INC_DEC_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPREFIX_INC_DEC_EXPRESSION(MentalParser.PREFIX_INC_DEC_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ARRAY_SUBSCRIPTING_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterARRAY_SUBSCRIPTING_EXPRESSION(MentalParser.ARRAY_SUBSCRIPTING_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ARRAY_SUBSCRIPTING_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitARRAY_SUBSCRIPTING_EXPRESSION(MentalParser.ARRAY_SUBSCRIPTING_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SUBGROUP_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSUBGROUP_EXPRESSION(MentalParser.SUBGROUP_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SUBGROUP_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSUBGROUP_EXPRESSION(MentalParser.SUBGROUP_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BIT_AND_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBIT_AND_EXPRESSION(MentalParser.BIT_AND_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BIT_AND_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBIT_AND_EXPRESSION(MentalParser.BIT_AND_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSTRING_LITERAL(MentalParser.STRING_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSTRING_LITERAL(MentalParser.STRING_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FALSE}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFALSE(MentalParser.FALSEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FALSE}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFALSE(MentalParser.FALSEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UNRAY_PLUS_MINUS_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUNRAY_PLUS_MINUS_EXPRESSION(MentalParser.UNRAY_PLUS_MINUS_EXPRESSIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UNRAY_PLUS_MINUS_EXPRESSION}
	 * labeled alternative in {@link MentalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUNRAY_PLUS_MINUS_EXPRESSION(MentalParser.UNRAY_PLUS_MINUS_EXPRESSIONContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(MentalParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(MentalParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MentalParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(MentalParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MentalParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(MentalParser.ExpressionListContext ctx);
}