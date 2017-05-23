package BackEnd.Translator;

import java.io.PrintStream;

/**
 * Created by tan on 5/23/17.
 */
public abstract class Translator {
    public PrintStream output;

    public Translator(PrintStream output) {
        this.output = output;
    }
}
