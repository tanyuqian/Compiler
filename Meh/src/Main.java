import Environment.Environment;
import FrontEnd.ConcreteSyntaxTree.MehLexer;
import FrontEnd.ConcreteSyntaxTree.MehParser;
import FrontEnd.Listener.ClassFetcherListener;
import FrontEnd.Listener.DeclarationFetcherListener;
import FrontEnd.Listener.SyntaxErrorListener;
import FrontEnd.Listener.TreeBuilderListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream iStream = System.in;
        //InputStream iStream = new FileInputStream("tests/2.meh");
        ANTLRInputStream input = new ANTLRInputStream(iStream);
        MehLexer lexer = new MehLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MehParser parser = new MehParser(tokens);

        Environment.initialize();

        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ClassFetcherListener(), tree);
        walker.walk(new DeclarationFetcherListener(), tree);
        walker.walk(new TreeBuilderListener(), tree);
    }
}