package Compiler.FrontEnd.ConcreteSyntaxTree.Listener;

import Compiler.FrontEnd.AbstractSyntaxTree.Node;
import Compiler.FrontEnd.ConcreteSyntaxTree.Parser.MagiBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public abstract class BaseListener extends MagiBaseListener {
	public static int row, column;
	static ParseTreeProperty<Node> returnNode = new ParseTreeProperty<>();

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		row = ctx.getStart().getLine();
		column = ctx.getStart().getCharPositionInLine();
	}
}
