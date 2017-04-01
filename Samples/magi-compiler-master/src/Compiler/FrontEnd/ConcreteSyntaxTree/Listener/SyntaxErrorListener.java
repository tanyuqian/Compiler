package Compiler.FrontEnd.ConcreteSyntaxTree.Listener;

import Compiler.Utility.Error.CompilationError;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SyntaxErrorListener extends BaseErrorListener {
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object symbol, int row, int column, String message, RecognitionException e) {
		BaseListener.row = row;
		BaseListener.column = column;
		throw new CompilationError(message);
	}
}
