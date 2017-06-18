package FrontEnd.Listener;

import Utility.CompilationError;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Created by tan on 4/3/17.
 */
public class SyntaxErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object symbol, int row, int column, String message, RecognitionException e) {
        BaseListener.row = row;
        BaseListener.column = column;
        throw new CompilationError(message);
    }
}
