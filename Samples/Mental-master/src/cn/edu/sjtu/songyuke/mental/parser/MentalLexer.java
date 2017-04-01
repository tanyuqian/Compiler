// Generated from /Users/Songyu/Projects/Mental/src/MentalParser/Mental.g4 by ANTLR 4.5.1
package cn.edu.sjtu.songyuke.mental.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MentalLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "COMMENT", "WS", "INT", "Identifier", 
		"Hexquad", "Universalcharactername", "Identifiernondigit", "NONDIGIT", 
		"DIGIT", "STRING", "ESC", "UNICODE", "HEX", "PLUS", "MINUS", "MUL", "DIV", 
		"MOD", "INC", "DEC", "BIT_NOT", "LOGICAL_NOT", "BIT_AND", "BIT_XOR", "BIT_OR", 
		"LOGICAL_AND", "LOGICAL_OR", "ASSIGN", "EQUAL", "INEQUAL", "LESS", "GREATER", 
		"LESS_EQUAL", "GREATER_EQUAL", "LEFT_SHIFT", "RIGHT_SHIFT", "PERIOD", 
		"COMMA", "LBRACKET", "RBRACKET"
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


	public MentalLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mental.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\67\u0173\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\7\27\u00e3\n\27\f\27"+
		"\16\27\u00e6\13\27\3\27\5\27\u00e9\n\27\3\27\5\27\u00ec\n\27\3\27\3\27"+
		"\3\30\6\30\u00f1\n\30\r\30\16\30\u00f2\3\30\3\30\3\31\6\31\u00f8\n\31"+
		"\r\31\16\31\u00f9\3\32\3\32\3\32\7\32\u00ff\n\32\f\32\16\32\u0102\13\32"+
		"\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\5\34\u0113\n\34\3\35\3\35\5\35\u0117\n\35\3\36\3\36\3\37\3\37\3"+
		" \3 \3 \7 \u0120\n \f \16 \u0123\13 \3 \3 \3!\3!\3!\5!\u012a\n!\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3*\3"+
		"*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\62"+
		"\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\3\67"+
		"\38\38\38\39\39\39\3:\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3\u0121\2?\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\2\67\29\2;\2=\2?\34A\2C\2"+
		"E\2G\35I\36K\37M O!Q\"S#U$W%Y&[\'](_)a*c+e,g-i.k/m\60o\61q\62s\63u\64"+
		"w\65y\66{\67\3\2\t\4\2\f\f\17\17\3\3\f\f\5\2\13\f\17\17\"\"\5\2C\\aac"+
		"|\3\2\62;\n\2$$\61\61^^ddhhppttvv\5\2\62;CHch\u0175\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2?\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2"+
		"\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2"+
		"\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i"+
		"\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2"+
		"\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\3}\3\2\2\2\5\u0081\3\2\2\2\7\u0088"+
		"\3\2\2\2\t\u008d\3\2\2\2\13\u0093\3\2\2\2\r\u0095\3\2\2\2\17\u0097\3\2"+
		"\2\2\21\u009c\3\2\2\2\23\u009e\3\2\2\2\25\u00a0\3\2\2\2\27\u00a2\3\2\2"+
		"\2\31\u00a5\3\2\2\2\33\u00aa\3\2\2\2\35\u00ae\3\2\2\2\37\u00b4\3\2\2\2"+
		"!\u00bb\3\2\2\2#\u00c4\3\2\2\2%\u00ca\3\2\2\2\'\u00ce\3\2\2\2)\u00d3\3"+
		"\2\2\2+\u00d9\3\2\2\2-\u00de\3\2\2\2/\u00f0\3\2\2\2\61\u00f7\3\2\2\2\63"+
		"\u00fb\3\2\2\2\65\u0103\3\2\2\2\67\u0112\3\2\2\29\u0116\3\2\2\2;\u0118"+
		"\3\2\2\2=\u011a\3\2\2\2?\u011c\3\2\2\2A\u0126\3\2\2\2C\u012b\3\2\2\2E"+
		"\u0131\3\2\2\2G\u0133\3\2\2\2I\u0135\3\2\2\2K\u0137\3\2\2\2M\u0139\3\2"+
		"\2\2O\u013b\3\2\2\2Q\u013d\3\2\2\2S\u0140\3\2\2\2U\u0143\3\2\2\2W\u0145"+
		"\3\2\2\2Y\u0147\3\2\2\2[\u0149\3\2\2\2]\u014b\3\2\2\2_\u014d\3\2\2\2a"+
		"\u0150\3\2\2\2c\u0153\3\2\2\2e\u0155\3\2\2\2g\u0158\3\2\2\2i\u015b\3\2"+
		"\2\2k\u015d\3\2\2\2m\u015f\3\2\2\2o\u0162\3\2\2\2q\u0165\3\2\2\2s\u0168"+
		"\3\2\2\2u\u016b\3\2\2\2w\u016d\3\2\2\2y\u016f\3\2\2\2{\u0171\3\2\2\2}"+
		"~\7k\2\2~\177\7p\2\2\177\u0080\7v\2\2\u0080\4\3\2\2\2\u0081\u0082\7u\2"+
		"\2\u0082\u0083\7v\2\2\u0083\u0084\7t\2\2\u0084\u0085\7k\2\2\u0085\u0086"+
		"\7p\2\2\u0086\u0087\7i\2\2\u0087\6\3\2\2\2\u0088\u0089\7d\2\2\u0089\u008a"+
		"\7q\2\2\u008a\u008b\7q\2\2\u008b\u008c\7n\2\2\u008c\b\3\2\2\2\u008d\u008e"+
		"\7e\2\2\u008e\u008f\7n\2\2\u008f\u0090\7c\2\2\u0090\u0091\7u\2\2\u0091"+
		"\u0092\7u\2\2\u0092\n\3\2\2\2\u0093\u0094\7}\2\2\u0094\f\3\2\2\2\u0095"+
		"\u0096\7\177\2\2\u0096\16\3\2\2\2\u0097\u0098\7x\2\2\u0098\u0099\7q\2"+
		"\2\u0099\u009a\7k\2\2\u009a\u009b\7f\2\2\u009b\20\3\2\2\2\u009c\u009d"+
		"\7*\2\2\u009d\22\3\2\2\2\u009e\u009f\7+\2\2\u009f\24\3\2\2\2\u00a0\u00a1"+
		"\7=\2\2\u00a1\26\3\2\2\2\u00a2\u00a3\7k\2\2\u00a3\u00a4\7h\2\2\u00a4\30"+
		"\3\2\2\2\u00a5\u00a6\7g\2\2\u00a6\u00a7\7n\2\2\u00a7\u00a8\7u\2\2\u00a8"+
		"\u00a9\7g\2\2\u00a9\32\3\2\2\2\u00aa\u00ab\7h\2\2\u00ab\u00ac\7q\2\2\u00ac"+
		"\u00ad\7t\2\2\u00ad\34\3\2\2\2\u00ae\u00af\7y\2\2\u00af\u00b0\7j\2\2\u00b0"+
		"\u00b1\7k\2\2\u00b1\u00b2\7n\2\2\u00b2\u00b3\7g\2\2\u00b3\36\3\2\2\2\u00b4"+
		"\u00b5\7t\2\2\u00b5\u00b6\7g\2\2\u00b6\u00b7\7v\2\2\u00b7\u00b8\7w\2\2"+
		"\u00b8\u00b9\7t\2\2\u00b9\u00ba\7p\2\2\u00ba \3\2\2\2\u00bb\u00bc\7e\2"+
		"\2\u00bc\u00bd\7q\2\2\u00bd\u00be\7p\2\2\u00be\u00bf\7v\2\2\u00bf\u00c0"+
		"\7k\2\2\u00c0\u00c1\7p\2\2\u00c1\u00c2\7w\2\2\u00c2\u00c3\7g\2\2\u00c3"+
		"\"\3\2\2\2\u00c4\u00c5\7d\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7g\2\2\u00c7"+
		"\u00c8\7c\2\2\u00c8\u00c9\7m\2\2\u00c9$\3\2\2\2\u00ca\u00cb\7p\2\2\u00cb"+
		"\u00cc\7g\2\2\u00cc\u00cd\7y\2\2\u00cd&\3\2\2\2\u00ce\u00cf\7v\2\2\u00cf"+
		"\u00d0\7t\2\2\u00d0\u00d1\7w\2\2\u00d1\u00d2\7g\2\2\u00d2(\3\2\2\2\u00d3"+
		"\u00d4\7h\2\2\u00d4\u00d5\7c\2\2\u00d5\u00d6\7n\2\2\u00d6\u00d7\7u\2\2"+
		"\u00d7\u00d8\7g\2\2\u00d8*\3\2\2\2\u00d9\u00da\7p\2\2\u00da\u00db\7w\2"+
		"\2\u00db\u00dc\7n\2\2\u00dc\u00dd\7n\2\2\u00dd,\3\2\2\2\u00de\u00df\7"+
		"\61\2\2\u00df\u00e0\7\61\2\2\u00e0\u00e4\3\2\2\2\u00e1\u00e3\n\2\2\2\u00e2"+
		"\u00e1\3\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00e9\7\17\2\2\u00e8"+
		"\u00e7\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00eb\3\2\2\2\u00ea\u00ec\t\3"+
		"\2\2\u00eb\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ee\b\27\2\2\u00ee"+
		".\3\2\2\2\u00ef\u00f1\t\4\2\2\u00f0\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2"+
		"\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5"+
		"\b\30\2\2\u00f5\60\3\2\2\2\u00f6\u00f8\5=\37\2\u00f7\u00f6\3\2\2\2\u00f8"+
		"\u00f9\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\62\3\2\2"+
		"\2\u00fb\u0100\59\35\2\u00fc\u00ff\59\35\2\u00fd\u00ff\5=\37\2\u00fe\u00fc"+
		"\3\2\2\2\u00fe\u00fd\3\2\2\2\u00ff\u0102\3\2\2\2\u0100\u00fe\3\2\2\2\u0100"+
		"\u0101\3\2\2\2\u0101\64\3\2\2\2\u0102\u0100\3\2\2\2\u0103\u0104\5E#\2"+
		"\u0104\u0105\5E#\2\u0105\u0106\5E#\2\u0106\u0107\5E#\2\u0107\66\3\2\2"+
		"\2\u0108\u0109\7^\2\2\u0109\u010a\7w\2\2\u010a\u010b\3\2\2\2\u010b\u0113"+
		"\5\65\33\2\u010c\u010d\7^\2\2\u010d\u010e\7W\2\2\u010e\u010f\3\2\2\2\u010f"+
		"\u0110\5\65\33\2\u0110\u0111\5\65\33\2\u0111\u0113\3\2\2\2\u0112\u0108"+
		"\3\2\2\2\u0112\u010c\3\2\2\2\u01138\3\2\2\2\u0114\u0117\5;\36\2\u0115"+
		"\u0117\5\67\34\2\u0116\u0114\3\2\2\2\u0116\u0115\3\2\2\2\u0117:\3\2\2"+
		"\2\u0118\u0119\t\5\2\2\u0119<\3\2\2\2\u011a\u011b\t\6\2\2\u011b>\3\2\2"+
		"\2\u011c\u0121\7$\2\2\u011d\u0120\5A!\2\u011e\u0120\13\2\2\2\u011f\u011d"+
		"\3\2\2\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121\u0122\3\2\2\2\u0121"+
		"\u011f\3\2\2\2\u0122\u0124\3\2\2\2\u0123\u0121\3\2\2\2\u0124\u0125\7$"+
		"\2\2\u0125@\3\2\2\2\u0126\u0129\7^\2\2\u0127\u012a\t\7\2\2\u0128\u012a"+
		"\5C\"\2\u0129\u0127\3\2\2\2\u0129\u0128\3\2\2\2\u012aB\3\2\2\2\u012b\u012c"+
		"\7w\2\2\u012c\u012d\5E#\2\u012d\u012e\5E#\2\u012e\u012f\5E#\2\u012f\u0130"+
		"\5E#\2\u0130D\3\2\2\2\u0131\u0132\t\b\2\2\u0132F\3\2\2\2\u0133\u0134\7"+
		"-\2\2\u0134H\3\2\2\2\u0135\u0136\7/\2\2\u0136J\3\2\2\2\u0137\u0138\7,"+
		"\2\2\u0138L\3\2\2\2\u0139\u013a\7\61\2\2\u013aN\3\2\2\2\u013b\u013c\7"+
		"\'\2\2\u013cP\3\2\2\2\u013d\u013e\7-\2\2\u013e\u013f\7-\2\2\u013fR\3\2"+
		"\2\2\u0140\u0141\7/\2\2\u0141\u0142\7/\2\2\u0142T\3\2\2\2\u0143\u0144"+
		"\7\u0080\2\2\u0144V\3\2\2\2\u0145\u0146\7#\2\2\u0146X\3\2\2\2\u0147\u0148"+
		"\7(\2\2\u0148Z\3\2\2\2\u0149\u014a\7`\2\2\u014a\\\3\2\2\2\u014b\u014c"+
		"\7~\2\2\u014c^\3\2\2\2\u014d\u014e\7(\2\2\u014e\u014f\7(\2\2\u014f`\3"+
		"\2\2\2\u0150\u0151\7~\2\2\u0151\u0152\7~\2\2\u0152b\3\2\2\2\u0153\u0154"+
		"\7?\2\2\u0154d\3\2\2\2\u0155\u0156\7?\2\2\u0156\u0157\7?\2\2\u0157f\3"+
		"\2\2\2\u0158\u0159\7#\2\2\u0159\u015a\7?\2\2\u015ah\3\2\2\2\u015b\u015c"+
		"\7>\2\2\u015cj\3\2\2\2\u015d\u015e\7@\2\2\u015el\3\2\2\2\u015f\u0160\7"+
		">\2\2\u0160\u0161\7?\2\2\u0161n\3\2\2\2\u0162\u0163\7@\2\2\u0163\u0164"+
		"\7?\2\2\u0164p\3\2\2\2\u0165\u0166\7>\2\2\u0166\u0167\7>\2\2\u0167r\3"+
		"\2\2\2\u0168\u0169\7@\2\2\u0169\u016a\7@\2\2\u016at\3\2\2\2\u016b\u016c"+
		"\7\60\2\2\u016cv\3\2\2\2\u016d\u016e\7.\2\2\u016ex\3\2\2\2\u016f\u0170"+
		"\7]\2\2\u0170z\3\2\2\2\u0171\u0172\7_\2\2\u0172|\3\2\2\2\17\2\u00e4\u00e8"+
		"\u00eb\u00f2\u00f9\u00fe\u0100\u0112\u0116\u011f\u0121\u0129\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}