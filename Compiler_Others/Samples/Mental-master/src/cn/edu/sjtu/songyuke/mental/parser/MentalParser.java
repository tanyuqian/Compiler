// Generated from /Users/Songyu/Projects/Mental/src/MentalParser/Mental.g4 by ANTLR 4.5.1
package cn.edu.sjtu.songyuke.mental.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MentalParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, COMMENT=22, WS=23, INT=24, Identifier=25, 
		STRING=26, PLUS=27, MINUS=28, MUL=29, DIV=30, MOD=31, INC=32, DEC=33, 
		BIT_NOT=34, LOGICAL_NOT=35, BIT_AND=36, BIT_XOR=37, BIT_OR=38, LOGICAL_AND=39, 
		LOGICAL_OR=40, ASSIGN=41, EQUAL=42, INEQUAL=43, LESS=44, GREATER=45, LESS_EQUAL=46, 
		GREATER_EQUAL=47, LEFT_SHIFT=48, RIGHT_SHIFT=49, PERIOD=50, COMMA=51, 
		LBRACKET=52, RBRACKET=53;
	public static final int
		RULE_className = 0, RULE_typeName = 1, RULE_array = 2, RULE_type = 3, 
		RULE_parameter = 4, RULE_parametersList = 5, RULE_program = 6, RULE_declaration = 7, 
		RULE_classDeclaration = 8, RULE_functionDeclaration = 9, RULE_definition = 10, 
		RULE_singleVariable = 11, RULE_variableDefinition = 12, RULE_functionDefinition = 13, 
		RULE_compoundStatement = 14, RULE_statement = 15, RULE_emptyStatement = 16, 
		RULE_ifStatement = 17, RULE_ifElseStatement = 18, RULE_forStatement = 19, 
		RULE_whileStatement = 20, RULE_jumpStatement = 21, RULE_expressionStatement = 22, 
		RULE_expression = 23, RULE_functionCall = 24, RULE_expressionList = 25;
	public static final String[] ruleNames = {
		"className", "typeName", "array", "cn/edu/sjtu/songyuke/mental/type", "parameter", "parametersList",
		"program", "declaration", "classDeclaration", "functionDeclaration", "definition", 
		"singleVariable", "variableDefinition", "functionDefinition", "compoundStatement", 
		"statement", "emptyStatement", "ifStatement", "ifElseStatement", "forStatement", 
		"whileStatement", "jumpStatement", "expressionStatement", "expression", 
		"functionCall", "expressionList"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'int'", "'string'", "'bool'", "'class'", "'{'", "'}'", "'void'", 
		"'('", "')'", "';'", "'if'", "'else'", "'for'", "'while'", "'return'", 
		"'continue'", "'break'", "'new'", "'true'", "'false'", "'null'", null, 
		null, null, null, null, "'+'", "'-'", "'*'", "'/'", "'%'", "'++'", "'--'", 
		"'~'", "'!'", "'&'", "'^'", "'|'", "'&&'", "'||'", "'='", "'=='", "'!='", 
		"'<'", "'>'", "'<='", "'>='", "'<<'", "'>>'", "'.'", "','", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "COMMENT", 
		"WS", "INT", "Identifier", "STRING", "PLUS", "MINUS", "MUL", "DIV", "MOD", 
		"INC", "DEC", "BIT_NOT", "LOGICAL_NOT", "BIT_AND", "BIT_XOR", "BIT_OR", 
		"LOGICAL_AND", "LOGICAL_OR", "ASSIGN", "EQUAL", "INEQUAL", "LESS", "GREATER", 
		"LESS_EQUAL", "GREATER_EQUAL", "LEFT_SHIFT", "RIGHT_SHIFT", "PERIOD", 
		"COMMA", "LBRACKET", "RBRACKET"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mental.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MentalParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ClassNameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterClassName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitClassName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitClassName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_className);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeNameContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_typeName);
		try {
			setState(58);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				match(T__0);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(55);
				match(T__1);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(56);
				match(T__2);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(57);
				className();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayContext extends ParserRuleContext {
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_array);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(LBRACKET);
			setState(61);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public List<ArrayContext> array() {
			return getRuleContexts(ArrayContext.class);
		}
		public ArrayContext array(int i) {
			return getRuleContext(ArrayContext.class,i);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			typeName();
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET) {
				{
				{
				setState(64);
				array();
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			type();
			setState(71);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametersListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public ParametersListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametersList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterParametersList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitParametersList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitParametersList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersListContext parametersList() throws RecognitionException {
		ParametersListContext _localctx = new ParametersListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_parametersList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			parameter();
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(74);
				match(COMMA);
				setState(75);
				parameter();
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<DefinitionContext> definition() {
			return getRuleContexts(DefinitionContext.class);
		}
		public DefinitionContext definition(int i) {
			return getRuleContext(DefinitionContext.class,i);
		}
		public List<EmptyStatementContext> emptyStatement() {
			return getRuleContexts(EmptyStatementContext.class);
		}
		public EmptyStatementContext emptyStatement(int i) {
			return getRuleContext(EmptyStatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__6) | (1L << T__9) | (1L << Identifier))) != 0)) {
				{
				setState(84);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(81);
					declaration();
					}
					break;
				case 2:
					{
					setState(82);
					definition();
					}
					break;
				case 3:
					{
					setState(83);
					emptyStatement();
					}
					break;
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_declaration);
		try {
			setState(91);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				classDeclaration();
				}
				break;
			case T__0:
			case T__1:
			case T__2:
			case T__6:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				functionDeclaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public ClassNameContext name;
		public List<VariableDefinitionContext> variableDefinition() {
			return getRuleContexts(VariableDefinitionContext.class);
		}
		public VariableDefinitionContext variableDefinition(int i) {
			return getRuleContext(VariableDefinitionContext.class,i);
		}
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(T__3);
			setState(95);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(94);
				((ClassDeclarationContext)_localctx).name = className();
				}
			}

			setState(97);
			match(T__4);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__6) | (1L << Identifier))) != 0)) {
				{
				setState(100);
				switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
				case 1:
					{
					setState(98);
					variableDefinition();
					}
					break;
				case 2:
					{
					setState(99);
					functionDefinition();
					}
					break;
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ParametersListContext parametersList() {
			return getRuleContext(ParametersListContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case Identifier:
				{
				setState(107);
				type();
				}
				break;
			case T__6:
				{
				setState(108);
				match(T__6);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(111);
			match(Identifier);
			setState(112);
			match(T__7);
			setState(114);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << Identifier))) != 0)) {
				{
				setState(113);
				parametersList();
				}
			}

			setState(116);
			match(T__8);
			setState(117);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefinitionContext extends ParserRuleContext {
		public VariableDefinitionContext variableDefinition() {
			return getRuleContext(VariableDefinitionContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_definition);
		try {
			setState(121);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(119);
				variableDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(120);
				functionDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SingleVariableContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SingleVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterSingleVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitSingleVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitSingleVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleVariableContext singleVariable() throws RecognitionException {
		SingleVariableContext _localctx = new SingleVariableContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_singleVariable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(Identifier);
			setState(126);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(124);
				match(ASSIGN);
				setState(125);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDefinitionContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<SingleVariableContext> singleVariable() {
			return getRuleContexts(SingleVariableContext.class);
		}
		public SingleVariableContext singleVariable(int i) {
			return getRuleContext(SingleVariableContext.class,i);
		}
		public VariableDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterVariableDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitVariableDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitVariableDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDefinitionContext variableDefinition() throws RecognitionException {
		VariableDefinitionContext _localctx = new VariableDefinitionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_variableDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			type();
			setState(129);
			singleVariable();
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(130);
				match(COMMA);
				setState(131);
				singleVariable();
				}
				}
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(137);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public Token functionName;
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ParametersListContext parametersList() {
			return getRuleContext(ParametersListContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case Identifier:
				{
				setState(139);
				type();
				}
				break;
			case T__6:
				{
				setState(140);
				match(T__6);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(143);
			((FunctionDefinitionContext)_localctx).functionName = match(Identifier);
			setState(144);
			match(T__7);
			setState(146);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << Identifier))) != 0)) {
				{
				setState(145);
				parametersList();
				}
			}

			setState(148);
			match(T__8);
			setState(149);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundStatementContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterCompoundStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitCompoundStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitCompoundStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_compoundStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			match(T__4);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT) | (1L << Identifier) | (1L << STRING) | (1L << PLUS) | (1L << MINUS) | (1L << INC) | (1L << DEC) | (1L << BIT_NOT) | (1L << LOGICAL_NOT))) != 0)) {
				{
				{
				setState(152);
				statement();
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public IfElseStatementContext ifElseStatement() {
			return getRuleContext(IfElseStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public EmptyStatementContext emptyStatement() {
			return getRuleContext(EmptyStatementContext.class,0);
		}
		public VariableDefinitionContext variableDefinition() {
			return getRuleContext(VariableDefinitionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_statement);
		try {
			setState(169);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				compoundStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(161);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(162);
				ifElseStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(163);
				forStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(164);
				whileStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(165);
				expressionStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(166);
				jumpStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(167);
				emptyStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(168);
				variableDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyStatementContext extends ParserRuleContext {
		public EmptyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterEmptyStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitEmptyStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitEmptyStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyStatementContext emptyStatement() throws RecognitionException {
		EmptyStatementContext _localctx = new EmptyStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public StatementContext thenStatement;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(T__10);
			setState(174);
			match(T__7);
			setState(175);
			expression(0);
			setState(176);
			match(T__8);
			setState(177);
			((IfStatementContext)_localctx).thenStatement = statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfElseStatementContext extends ParserRuleContext {
		public StatementContext thenStatement;
		public StatementContext elseStatment;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfElseStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifElseStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterIfElseStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitIfElseStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitIfElseStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfElseStatementContext ifElseStatement() throws RecognitionException {
		IfElseStatementContext _localctx = new IfElseStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ifElseStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(T__10);
			setState(180);
			match(T__7);
			setState(181);
			expression(0);
			setState(182);
			match(T__8);
			setState(183);
			((IfElseStatementContext)_localctx).thenStatement = statement();
			setState(184);
			match(T__11);
			setState(185);
			((IfElseStatementContext)_localctx).elseStatment = statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public ExpressionContext start;
		public ExpressionContext cond;
		public ExpressionContext loop;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(T__12);
			setState(188);
			match(T__7);
			setState(190);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT) | (1L << Identifier) | (1L << STRING) | (1L << PLUS) | (1L << MINUS) | (1L << INC) | (1L << DEC) | (1L << BIT_NOT) | (1L << LOGICAL_NOT))) != 0)) {
				{
				setState(189);
				((ForStatementContext)_localctx).start = expression(0);
				}
			}

			setState(192);
			match(T__9);
			setState(194);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT) | (1L << Identifier) | (1L << STRING) | (1L << PLUS) | (1L << MINUS) | (1L << INC) | (1L << DEC) | (1L << BIT_NOT) | (1L << LOGICAL_NOT))) != 0)) {
				{
				setState(193);
				((ForStatementContext)_localctx).cond = expression(0);
				}
			}

			setState(196);
			match(T__9);
			setState(198);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT) | (1L << Identifier) | (1L << STRING) | (1L << PLUS) | (1L << MINUS) | (1L << INC) | (1L << DEC) | (1L << BIT_NOT) | (1L << LOGICAL_NOT))) != 0)) {
				{
				setState(197);
				((ForStatementContext)_localctx).loop = expression(0);
				}
			}

			setState(200);
			match(T__8);
			setState(201);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public ExpressionContext cond;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(T__13);
			setState(204);
			match(T__7);
			setState(205);
			((WhileStatementContext)_localctx).cond = expression(0);
			setState(206);
			match(T__8);
			setState(207);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitJumpStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitJumpStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_jumpStatement);
		int _la;
		try {
			setState(218);
			switch (_input.LA(1)) {
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(209);
				match(T__14);
				setState(211);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT) | (1L << Identifier) | (1L << STRING) | (1L << PLUS) | (1L << MINUS) | (1L << INC) | (1L << DEC) | (1L << BIT_NOT) | (1L << LOGICAL_NOT))) != 0)) {
					{
					setState(210);
					expression(0);
					}
				}

				setState(213);
				match(T__9);
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				match(T__15);
				setState(215);
				match(T__9);
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 3);
				{
				setState(216);
				match(T__16);
				setState(217);
				match(T__9);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			expression(0);
			setState(221);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BIT_XOR_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BIT_XOR_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterBIT_XOR_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitBIT_XOR_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitBIT_XOR_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LOGICAL_NOT_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LOGICAL_NOT_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterLOGICAL_NOT_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitLOGICAL_NOT_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitLOGICAL_NOT_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MEMBER_ACCESS_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public MEMBER_ACCESS_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterMEMBER_ACCESS_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitMEMBER_ACCESS_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitMEMBER_ACCESS_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FUNCTION_CALLContext extends ExpressionContext {
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public FUNCTION_CALLContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterFUNCTION_CALL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitFUNCTION_CALL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitFUNCTION_CALL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ADDITIVE_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ADDITIVE_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterADDITIVE_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitADDITIVE_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitADDITIVE_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CREATION_EXPRESSIONContext extends ExpressionContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ArrayContext> array() {
			return getRuleContexts(ArrayContext.class);
		}
		public ArrayContext array(int i) {
			return getRuleContext(ArrayContext.class,i);
		}
		public CREATION_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterCREATION_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitCREATION_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitCREATION_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BIT_NOT_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BIT_NOT_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterBIT_NOT_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitBIT_NOT_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitBIT_NOT_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RELATION_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RELATION_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterRELATION_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitRELATION_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitRELATION_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EQUALITY_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public EQUALITY_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterEQUALITY_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitEQUALITY_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitEQUALITY_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INT_LITERALContext extends ExpressionContext {
		public TerminalNode INT() { return getToken(MentalParser.INT, 0); }
		public INT_LITERALContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterINT_LITERAL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitINT_LITERAL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitINT_LITERAL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IDENTIFIERContext extends ExpressionContext {
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public IDENTIFIERContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterIDENTIFIER(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitIDENTIFIER(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitIDENTIFIER(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SUFFIX_INC_DEC_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SUFFIX_INC_DEC_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterSUFFIX_INC_DEC_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitSUFFIX_INC_DEC_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitSUFFIX_INC_DEC_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MULTIPLY_DIVIDE_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MULTIPLY_DIVIDE_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterMULTIPLY_DIVIDE_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitMULTIPLY_DIVIDE_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitMULTIPLY_DIVIDE_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LOGICAL_OR_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public LOGICAL_OR_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterLOGICAL_OR_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitLOGICAL_OR_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitLOGICAL_OR_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ASSIGN_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ASSIGN_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterASSIGN_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitASSIGN_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitASSIGN_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NULLContext extends ExpressionContext {
		public NULLContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterNULL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitNULL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitNULL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TRUEContext extends ExpressionContext {
		public TRUEContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterTRUE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitTRUE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitTRUE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BIT_OR_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BIT_OR_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterBIT_OR_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitBIT_OR_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitBIT_OR_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LOGICAL_AND_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public LOGICAL_AND_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterLOGICAL_AND_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitLOGICAL_AND_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitLOGICAL_AND_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BIT_SHIFT_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BIT_SHIFT_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterBIT_SHIFT_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitBIT_SHIFT_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitBIT_SHIFT_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PREFIX_INC_DEC_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PREFIX_INC_DEC_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterPREFIX_INC_DEC_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitPREFIX_INC_DEC_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitPREFIX_INC_DEC_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ARRAY_SUBSCRIPTING_EXPRESSIONContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ARRAY_SUBSCRIPTING_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterARRAY_SUBSCRIPTING_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitARRAY_SUBSCRIPTING_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitARRAY_SUBSCRIPTING_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SUBGROUP_EXPRESSIONContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SUBGROUP_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterSUBGROUP_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitSUBGROUP_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitSUBGROUP_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BIT_AND_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BIT_AND_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterBIT_AND_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitBIT_AND_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitBIT_AND_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class STRING_LITERALContext extends ExpressionContext {
		public TerminalNode STRING() { return getToken(MentalParser.STRING, 0); }
		public STRING_LITERALContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterSTRING_LITERAL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitSTRING_LITERAL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitSTRING_LITERAL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FALSEContext extends ExpressionContext {
		public FALSEContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterFALSE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitFALSE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitFALSE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UNRAY_PLUS_MINUS_EXPRESSIONContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UNRAY_PLUS_MINUS_EXPRESSIONContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterUNRAY_PLUS_MINUS_EXPRESSION(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitUNRAY_PLUS_MINUS_EXPRESSION(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitUNRAY_PLUS_MINUS_EXPRESSION(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				_localctx = new PREFIX_INC_DEC_EXPRESSIONContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(224);
				((PREFIX_INC_DEC_EXPRESSIONContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==INC || _la==DEC) ) {
					((PREFIX_INC_DEC_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(225);
				expression(22);
				}
				break;
			case 2:
				{
				_localctx = new UNRAY_PLUS_MINUS_EXPRESSIONContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(226);
				((UNRAY_PLUS_MINUS_EXPRESSIONContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
					((UNRAY_PLUS_MINUS_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(227);
				expression(21);
				}
				break;
			case 3:
				{
				_localctx = new BIT_NOT_EXPRESSIONContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(228);
				((BIT_NOT_EXPRESSIONContext)_localctx).op = match(BIT_NOT);
				setState(229);
				expression(20);
				}
				break;
			case 4:
				{
				_localctx = new LOGICAL_NOT_EXPRESSIONContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(230);
				((LOGICAL_NOT_EXPRESSIONContext)_localctx).op = match(LOGICAL_NOT);
				setState(231);
				expression(19);
				}
				break;
			case 5:
				{
				_localctx = new SUBGROUP_EXPRESSIONContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(232);
				match(T__7);
				setState(233);
				expression(0);
				setState(234);
				match(T__8);
				}
				break;
			case 6:
				{
				_localctx = new CREATION_EXPRESSIONContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				match(T__17);
				setState(237);
				typeName();
				setState(244);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(238);
						match(LBRACKET);
						setState(239);
						expression(0);
						setState(240);
						match(RBRACKET);
						}
						} 
					}
					setState(246);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				}
				setState(250);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(247);
						array();
						}
						} 
					}
					setState(252);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				}
				}
				break;
			case 7:
				{
				_localctx = new FUNCTION_CALLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(253);
				functionCall();
				}
				break;
			case 8:
				{
				_localctx = new IDENTIFIERContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(254);
				match(Identifier);
				}
				break;
			case 9:
				{
				_localctx = new INT_LITERALContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(255);
				match(INT);
				}
				break;
			case 10:
				{
				_localctx = new STRING_LITERALContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(256);
				match(STRING);
				}
				break;
			case 11:
				{
				_localctx = new TRUEContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(257);
				match(T__18);
				}
				break;
			case 12:
				{
				_localctx = new FALSEContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(258);
				match(T__19);
				}
				break;
			case 13:
				{
				_localctx = new NULLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(259);
				match(T__20);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(310);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(308);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new MULTIPLY_DIVIDE_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(262);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(263);
						((MULTIPLY_DIVIDE_EXPRESSIONContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((MULTIPLY_DIVIDE_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(264);
						expression(19);
						}
						break;
					case 2:
						{
						_localctx = new ADDITIVE_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(265);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(266);
						((ADDITIVE_EXPRESSIONContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((ADDITIVE_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(267);
						expression(18);
						}
						break;
					case 3:
						{
						_localctx = new BIT_SHIFT_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(268);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(269);
						((BIT_SHIFT_EXPRESSIONContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LEFT_SHIFT || _la==RIGHT_SHIFT) ) {
							((BIT_SHIFT_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(270);
						expression(17);
						}
						break;
					case 4:
						{
						_localctx = new RELATION_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(271);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(272);
						((RELATION_EXPRESSIONContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LESS) | (1L << GREATER) | (1L << LESS_EQUAL) | (1L << GREATER_EQUAL))) != 0)) ) {
							((RELATION_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(273);
						expression(16);
						}
						break;
					case 5:
						{
						_localctx = new EQUALITY_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(274);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(275);
						((EQUALITY_EXPRESSIONContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQUAL || _la==INEQUAL) ) {
							((EQUALITY_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(276);
						expression(15);
						}
						break;
					case 6:
						{
						_localctx = new BIT_AND_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(277);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(278);
						((BIT_AND_EXPRESSIONContext)_localctx).op = match(BIT_AND);
						setState(279);
						expression(14);
						}
						break;
					case 7:
						{
						_localctx = new BIT_XOR_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(280);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(281);
						((BIT_XOR_EXPRESSIONContext)_localctx).op = match(BIT_XOR);
						setState(282);
						expression(13);
						}
						break;
					case 8:
						{
						_localctx = new BIT_OR_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(283);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(284);
						((BIT_OR_EXPRESSIONContext)_localctx).op = match(BIT_OR);
						setState(285);
						expression(12);
						}
						break;
					case 9:
						{
						_localctx = new LOGICAL_AND_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(286);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(287);
						((LOGICAL_AND_EXPRESSIONContext)_localctx).op = match(LOGICAL_AND);
						setState(288);
						expression(11);
						}
						break;
					case 10:
						{
						_localctx = new LOGICAL_OR_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(289);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(290);
						((LOGICAL_OR_EXPRESSIONContext)_localctx).op = match(LOGICAL_OR);
						setState(291);
						expression(10);
						}
						break;
					case 11:
						{
						_localctx = new ASSIGN_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(292);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(293);
						((ASSIGN_EXPRESSIONContext)_localctx).op = match(ASSIGN);
						setState(294);
						expression(8);
						}
						break;
					case 12:
						{
						_localctx = new ARRAY_SUBSCRIPTING_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(295);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(296);
						match(LBRACKET);
						setState(297);
						expression(0);
						setState(298);
						match(RBRACKET);
						}
						break;
					case 13:
						{
						_localctx = new MEMBER_ACCESS_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(300);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(301);
						((MEMBER_ACCESS_EXPRESSIONContext)_localctx).op = match(PERIOD);
						setState(304);
						switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
						case 1:
							{
							setState(302);
							match(Identifier);
							}
							break;
						case 2:
							{
							setState(303);
							functionCall();
							}
							break;
						}
						}
						break;
					case 14:
						{
						_localctx = new SUFFIX_INC_DEC_EXPRESSIONContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(306);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(307);
						((SUFFIX_INC_DEC_EXPRESSIONContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==INC || _la==DEC) ) {
							((SUFFIX_INC_DEC_EXPRESSIONContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(312);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public Token functionName;
		public TerminalNode Identifier() { return getToken(MentalParser.Identifier, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			((FunctionCallContext)_localctx).functionName = match(Identifier);
			setState(314);
			match(T__7);
			setState(316);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT) | (1L << Identifier) | (1L << STRING) | (1L << PLUS) | (1L << MINUS) | (1L << INC) | (1L << DEC) | (1L << BIT_NOT) | (1L << LOGICAL_NOT))) != 0)) {
				{
				setState(315);
				expressionList();
				}
			}

			setState(318);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MentalListener ) ((MentalListener)listener).exitExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MentalVisitor ) return ((MentalVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			expression(0);
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(321);
				match(COMMA);
				setState(322);
				expression(0);
				}
				}
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 23:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 18);
		case 1:
			return precpred(_ctx, 17);
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 13);
		case 6:
			return precpred(_ctx, 12);
		case 7:
			return precpred(_ctx, 11);
		case 8:
			return precpred(_ctx, 10);
		case 9:
			return precpred(_ctx, 9);
		case 10:
			return precpred(_ctx, 8);
		case 11:
			return precpred(_ctx, 25);
		case 12:
			return precpred(_ctx, 24);
		case 13:
			return precpred(_ctx, 23);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\67\u014b\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\3\2\3\2\3\3\3\3\3\3\3\3\5\3=\n\3\3\4\3\4\3\4\3\5"+
		"\3\5\7\5D\n\5\f\5\16\5G\13\5\3\6\3\6\3\6\3\7\3\7\3\7\7\7O\n\7\f\7\16\7"+
		"R\13\7\3\b\3\b\3\b\7\bW\n\b\f\b\16\bZ\13\b\3\t\3\t\5\t^\n\t\3\n\3\n\5"+
		"\nb\n\n\3\n\3\n\3\n\7\ng\n\n\f\n\16\nj\13\n\3\n\3\n\3\13\3\13\5\13p\n"+
		"\13\3\13\3\13\3\13\5\13u\n\13\3\13\3\13\3\13\3\f\3\f\5\f|\n\f\3\r\3\r"+
		"\3\r\5\r\u0081\n\r\3\16\3\16\3\16\3\16\7\16\u0087\n\16\f\16\16\16\u008a"+
		"\13\16\3\16\3\16\3\17\3\17\5\17\u0090\n\17\3\17\3\17\3\17\5\17\u0095\n"+
		"\17\3\17\3\17\3\17\3\20\3\20\7\20\u009c\n\20\f\20\16\20\u009f\13\20\3"+
		"\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00ac\n\21"+
		"\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\5\25\u00c1\n\25\3\25\3\25\5\25\u00c5\n\25\3"+
		"\25\3\25\5\25\u00c9\n\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\5\27\u00d6\n\27\3\27\3\27\3\27\3\27\3\27\5\27\u00dd\n\27\3"+
		"\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u00f5\n\31\f\31\16\31\u00f8"+
		"\13\31\3\31\7\31\u00fb\n\31\f\31\16\31\u00fe\13\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\5\31\u0107\n\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0133\n\31\3\31\3\31\7\31\u0137\n"+
		"\31\f\31\16\31\u013a\13\31\3\32\3\32\3\32\5\32\u013f\n\32\3\32\3\32\3"+
		"\33\3\33\3\33\7\33\u0146\n\33\f\33\16\33\u0149\13\33\3\33\2\3\60\34\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\2\b\3\2\"#\3\2"+
		"\35\36\3\2\37!\3\2\62\63\3\2.\61\3\2,-\u0171\2\66\3\2\2\2\4<\3\2\2\2\6"+
		">\3\2\2\2\bA\3\2\2\2\nH\3\2\2\2\fK\3\2\2\2\16X\3\2\2\2\20]\3\2\2\2\22"+
		"_\3\2\2\2\24o\3\2\2\2\26{\3\2\2\2\30}\3\2\2\2\32\u0082\3\2\2\2\34\u008f"+
		"\3\2\2\2\36\u0099\3\2\2\2 \u00ab\3\2\2\2\"\u00ad\3\2\2\2$\u00af\3\2\2"+
		"\2&\u00b5\3\2\2\2(\u00bd\3\2\2\2*\u00cd\3\2\2\2,\u00dc\3\2\2\2.\u00de"+
		"\3\2\2\2\60\u0106\3\2\2\2\62\u013b\3\2\2\2\64\u0142\3\2\2\2\66\67\7\33"+
		"\2\2\67\3\3\2\2\28=\7\3\2\29=\7\4\2\2:=\7\5\2\2;=\5\2\2\2<8\3\2\2\2<9"+
		"\3\2\2\2<:\3\2\2\2<;\3\2\2\2=\5\3\2\2\2>?\7\66\2\2?@\7\67\2\2@\7\3\2\2"+
		"\2AE\5\4\3\2BD\5\6\4\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2F\t\3\2"+
		"\2\2GE\3\2\2\2HI\5\b\5\2IJ\7\33\2\2J\13\3\2\2\2KP\5\n\6\2LM\7\65\2\2M"+
		"O\5\n\6\2NL\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\r\3\2\2\2RP\3\2\2\2"+
		"SW\5\20\t\2TW\5\26\f\2UW\5\"\22\2VS\3\2\2\2VT\3\2\2\2VU\3\2\2\2WZ\3\2"+
		"\2\2XV\3\2\2\2XY\3\2\2\2Y\17\3\2\2\2ZX\3\2\2\2[^\5\22\n\2\\^\5\24\13\2"+
		"][\3\2\2\2]\\\3\2\2\2^\21\3\2\2\2_a\7\6\2\2`b\5\2\2\2a`\3\2\2\2ab\3\2"+
		"\2\2bc\3\2\2\2ch\7\7\2\2dg\5\32\16\2eg\5\34\17\2fd\3\2\2\2fe\3\2\2\2g"+
		"j\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh\3\2\2\2kl\7\b\2\2l\23\3\2\2"+
		"\2mp\5\b\5\2np\7\t\2\2om\3\2\2\2on\3\2\2\2pq\3\2\2\2qr\7\33\2\2rt\7\n"+
		"\2\2su\5\f\7\2ts\3\2\2\2tu\3\2\2\2uv\3\2\2\2vw\7\13\2\2wx\7\f\2\2x\25"+
		"\3\2\2\2y|\5\32\16\2z|\5\34\17\2{y\3\2\2\2{z\3\2\2\2|\27\3\2\2\2}\u0080"+
		"\7\33\2\2~\177\7+\2\2\177\u0081\5\60\31\2\u0080~\3\2\2\2\u0080\u0081\3"+
		"\2\2\2\u0081\31\3\2\2\2\u0082\u0083\5\b\5\2\u0083\u0088\5\30\r\2\u0084"+
		"\u0085\7\65\2\2\u0085\u0087\5\30\r\2\u0086\u0084\3\2\2\2\u0087\u008a\3"+
		"\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008b\3\2\2\2\u008a"+
		"\u0088\3\2\2\2\u008b\u008c\7\f\2\2\u008c\33\3\2\2\2\u008d\u0090\5\b\5"+
		"\2\u008e\u0090\7\t\2\2\u008f\u008d\3\2\2\2\u008f\u008e\3\2\2\2\u0090\u0091"+
		"\3\2\2\2\u0091\u0092\7\33\2\2\u0092\u0094\7\n\2\2\u0093\u0095\5\f\7\2"+
		"\u0094\u0093\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0097"+
		"\7\13\2\2\u0097\u0098\5\36\20\2\u0098\35\3\2\2\2\u0099\u009d\7\7\2\2\u009a"+
		"\u009c\5 \21\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2"+
		"\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0"+
		"\u00a1\7\b\2\2\u00a1\37\3\2\2\2\u00a2\u00ac\5\36\20\2\u00a3\u00ac\5$\23"+
		"\2\u00a4\u00ac\5&\24\2\u00a5\u00ac\5(\25\2\u00a6\u00ac\5*\26\2\u00a7\u00ac"+
		"\5.\30\2\u00a8\u00ac\5,\27\2\u00a9\u00ac\5\"\22\2\u00aa\u00ac\5\32\16"+
		"\2\u00ab\u00a2\3\2\2\2\u00ab\u00a3\3\2\2\2\u00ab\u00a4\3\2\2\2\u00ab\u00a5"+
		"\3\2\2\2\u00ab\u00a6\3\2\2\2\u00ab\u00a7\3\2\2\2\u00ab\u00a8\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac!\3\2\2\2\u00ad\u00ae\7\f\2\2"+
		"\u00ae#\3\2\2\2\u00af\u00b0\7\r\2\2\u00b0\u00b1\7\n\2\2\u00b1\u00b2\5"+
		"\60\31\2\u00b2\u00b3\7\13\2\2\u00b3\u00b4\5 \21\2\u00b4%\3\2\2\2\u00b5"+
		"\u00b6\7\r\2\2\u00b6\u00b7\7\n\2\2\u00b7\u00b8\5\60\31\2\u00b8\u00b9\7"+
		"\13\2\2\u00b9\u00ba\5 \21\2\u00ba\u00bb\7\16\2\2\u00bb\u00bc\5 \21\2\u00bc"+
		"\'\3\2\2\2\u00bd\u00be\7\17\2\2\u00be\u00c0\7\n\2\2\u00bf\u00c1\5\60\31"+
		"\2\u00c0\u00bf\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4"+
		"\7\f\2\2\u00c3\u00c5\5\60\31\2\u00c4\u00c3\3\2\2\2\u00c4\u00c5\3\2\2\2"+
		"\u00c5\u00c6\3\2\2\2\u00c6\u00c8\7\f\2\2\u00c7\u00c9\5\60\31\2\u00c8\u00c7"+
		"\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\7\13\2\2"+
		"\u00cb\u00cc\5 \21\2\u00cc)\3\2\2\2\u00cd\u00ce\7\20\2\2\u00ce\u00cf\7"+
		"\n\2\2\u00cf\u00d0\5\60\31\2\u00d0\u00d1\7\13\2\2\u00d1\u00d2\5 \21\2"+
		"\u00d2+\3\2\2\2\u00d3\u00d5\7\21\2\2\u00d4\u00d6\5\60\31\2\u00d5\u00d4"+
		"\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00dd\7\f\2\2\u00d8"+
		"\u00d9\7\22\2\2\u00d9\u00dd\7\f\2\2\u00da\u00db\7\23\2\2\u00db\u00dd\7"+
		"\f\2\2\u00dc\u00d3\3\2\2\2\u00dc\u00d8\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd"+
		"-\3\2\2\2\u00de\u00df\5\60\31\2\u00df\u00e0\7\f\2\2\u00e0/\3\2\2\2\u00e1"+
		"\u00e2\b\31\1\2\u00e2\u00e3\t\2\2\2\u00e3\u0107\5\60\31\30\u00e4\u00e5"+
		"\t\3\2\2\u00e5\u0107\5\60\31\27\u00e6\u00e7\7$\2\2\u00e7\u0107\5\60\31"+
		"\26\u00e8\u00e9\7%\2\2\u00e9\u0107\5\60\31\25\u00ea\u00eb\7\n\2\2\u00eb"+
		"\u00ec\5\60\31\2\u00ec\u00ed\7\13\2\2\u00ed\u0107\3\2\2\2\u00ee\u00ef"+
		"\7\24\2\2\u00ef\u00f6\5\4\3\2\u00f0\u00f1\7\66\2\2\u00f1\u00f2\5\60\31"+
		"\2\u00f2\u00f3\7\67\2\2\u00f3\u00f5\3\2\2\2\u00f4\u00f0\3\2\2\2\u00f5"+
		"\u00f8\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00fc\3\2"+
		"\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fb\5\6\4\2\u00fa\u00f9\3\2\2\2\u00fb"+
		"\u00fe\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u0107\3\2"+
		"\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0107\5\62\32\2\u0100\u0107\7\33\2\2\u0101"+
		"\u0107\7\32\2\2\u0102\u0107\7\34\2\2\u0103\u0107\7\25\2\2\u0104\u0107"+
		"\7\26\2\2\u0105\u0107\7\27\2\2\u0106\u00e1\3\2\2\2\u0106\u00e4\3\2\2\2"+
		"\u0106\u00e6\3\2\2\2\u0106\u00e8\3\2\2\2\u0106\u00ea\3\2\2\2\u0106\u00ee"+
		"\3\2\2\2\u0106\u00ff\3\2\2\2\u0106\u0100\3\2\2\2\u0106\u0101\3\2\2\2\u0106"+
		"\u0102\3\2\2\2\u0106\u0103\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0105\3\2"+
		"\2\2\u0107\u0138\3\2\2\2\u0108\u0109\f\24\2\2\u0109\u010a\t\4\2\2\u010a"+
		"\u0137\5\60\31\25\u010b\u010c\f\23\2\2\u010c\u010d\t\3\2\2\u010d\u0137"+
		"\5\60\31\24\u010e\u010f\f\22\2\2\u010f\u0110\t\5\2\2\u0110\u0137\5\60"+
		"\31\23\u0111\u0112\f\21\2\2\u0112\u0113\t\6\2\2\u0113\u0137\5\60\31\22"+
		"\u0114\u0115\f\20\2\2\u0115\u0116\t\7\2\2\u0116\u0137\5\60\31\21\u0117"+
		"\u0118\f\17\2\2\u0118\u0119\7&\2\2\u0119\u0137\5\60\31\20\u011a\u011b"+
		"\f\16\2\2\u011b\u011c\7\'\2\2\u011c\u0137\5\60\31\17\u011d\u011e\f\r\2"+
		"\2\u011e\u011f\7(\2\2\u011f\u0137\5\60\31\16\u0120\u0121\f\f\2\2\u0121"+
		"\u0122\7)\2\2\u0122\u0137\5\60\31\r\u0123\u0124\f\13\2\2\u0124\u0125\7"+
		"*\2\2\u0125\u0137\5\60\31\f\u0126\u0127\f\n\2\2\u0127\u0128\7+\2\2\u0128"+
		"\u0137\5\60\31\n\u0129\u012a\f\33\2\2\u012a\u012b\7\66\2\2\u012b\u012c"+
		"\5\60\31\2\u012c\u012d\7\67\2\2\u012d\u0137\3\2\2\2\u012e\u012f\f\32\2"+
		"\2\u012f\u0132\7\64\2\2\u0130\u0133\7\33\2\2\u0131\u0133\5\62\32\2\u0132"+
		"\u0130\3\2\2\2\u0132\u0131\3\2\2\2\u0133\u0137\3\2\2\2\u0134\u0135\f\31"+
		"\2\2\u0135\u0137\t\2\2\2\u0136\u0108\3\2\2\2\u0136\u010b\3\2\2\2\u0136"+
		"\u010e\3\2\2\2\u0136\u0111\3\2\2\2\u0136\u0114\3\2\2\2\u0136\u0117\3\2"+
		"\2\2\u0136\u011a\3\2\2\2\u0136\u011d\3\2\2\2\u0136\u0120\3\2\2\2\u0136"+
		"\u0123\3\2\2\2\u0136\u0126\3\2\2\2\u0136\u0129\3\2\2\2\u0136\u012e\3\2"+
		"\2\2\u0136\u0134\3\2\2\2\u0137\u013a\3\2\2\2\u0138\u0136\3\2\2\2\u0138"+
		"\u0139\3\2\2\2\u0139\61\3\2\2\2\u013a\u0138\3\2\2\2\u013b\u013c\7\33\2"+
		"\2\u013c\u013e\7\n\2\2\u013d\u013f\5\64\33\2\u013e\u013d\3\2\2\2\u013e"+
		"\u013f\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0141\7\13\2\2\u0141\63\3\2\2"+
		"\2\u0142\u0147\5\60\31\2\u0143\u0144\7\65\2\2\u0144\u0146\5\60\31\2\u0145"+
		"\u0143\3\2\2\2\u0146\u0149\3\2\2\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2"+
		"\2\2\u0148\65\3\2\2\2\u0149\u0147\3\2\2\2!<EPVX]afhot{\u0080\u0088\u008f"+
		"\u0094\u009d\u00ab\u00c0\u00c4\u00c8\u00d5\u00dc\u00f6\u00fc\u0106\u0132"+
		"\u0136\u0138\u013e\u0147";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}