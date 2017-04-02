import Environment.Environment;
import FrontEnd.ConcreteSyntaxTree.MehLexer;
import FrontEnd.ConcreteSyntaxTree.MehParser;
import FrontEnd.Listener.BaseListener;
import FrontEnd.Listener.classFetcherListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream iStream = new FileInputStream("tests/2.meh");
        ANTLRInputStream input = new ANTLRInputStream(iStream);
        MehLexer lexer = new MehLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MehParser parser = new MehParser(tokens);
        ParseTree tree = parser.program();

        Environment.initialize();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new classFetcherListener(), tree);
    }
}