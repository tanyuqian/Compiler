package Compiler;

import Compiler.BackEnd.Allocator.GlobalRegisterAllocator.GlobalRegisterAllocator;
import Compiler.BackEnd.ControlFlowGraph.Graph;
import Compiler.BackEnd.Translator.MIPS.MIPSTranslator.MIPSBasicTranslator;
import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.ConcreteSyntaxTree.Listener.ClassFetcherListener;
import Compiler.FrontEnd.ConcreteSyntaxTree.Listener.DeclarationFetcherListener;
import Compiler.FrontEnd.ConcreteSyntaxTree.Listener.SyntaxErrorListener;
import Compiler.FrontEnd.ConcreteSyntaxTree.Listener.TreeBuilderListener;
import Compiler.FrontEnd.ConcreteSyntaxTree.Parser.MagiLexer;
import Compiler.FrontEnd.ConcreteSyntaxTree.Parser.MagiParser;
import Compiler.Utility.Error.CompilationError;
import Compiler.Utility.Error.InternalError;
import Compiler.Utility.Utility;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
	public static void main(String[] args) throws Exception {
		Utility.arguments = new HashSet<>(Arrays.asList(args));
		/*
		try {
			new Main().compile(System.in, System.out);
		} catch (CompilationError e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (InternalError e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		*/
		InputStream iStream = new FileInputStream("tests/2.meh");
		load(iStream);
	}

	public static void load(InputStream file) throws Exception {
		ANTLRInputStream input = new ANTLRInputStream(file);
		MagiLexer lexer = new MagiLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MagiParser parser = new MagiParser(tokens);

		parser.removeErrorListeners();
		parser.addErrorListener(new SyntaxErrorListener());
		ParseTree tree = parser.program();
		ParseTreeWalker walker = new ParseTreeWalker();

		walker.walk(new ClassFetcherListener(), tree);
		walker.walk(new DeclarationFetcherListener(), tree);
		Environment.classTable.analysis();
		walker.walk(new TreeBuilderListener(), tree);
	}

	void compile(InputStream input, OutputStream output) throws Exception {
		Environment.initialize();
		load(input);
		if (Utility.arguments.contains("-ast")) {
			System.err.println(Environment.program.toString(0));
		}
		for (Function function : Environment.program.functions) {
			function.graph = new Graph(function);
			function.allocator = new GlobalRegisterAllocator(function);
		}
		if (Utility.arguments.contains("-ir")) {
			for (Function function : Environment.program.functions) {
				System.err.println(function.graph.toString(0));
			}
		}
		new MIPSBasicTranslator(new PrintStream(output)).translate();
	}
}
