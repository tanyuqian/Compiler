package FrontEnd.Listener;

import FrontEnd.AbstractSyntaxTree.Node;
import FrontEnd.ConcreteSyntaxTree.MehBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public abstract class BaseListener extends MehBaseListener {
    public static int row, column;
    public static ParseTreeProperty<Node> returnNode = new ParseTreeProperty<>();

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        row = ctx.getStart().getLine();
        column = ctx.getStart().getCharPositionInLine();
    }
}