package FrontEnd.Listener;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.ConcreteSyntaxTree.MehParser;

/**
 * Created by tan on 4/2/17.
 */
public class classFetcherListener extends BaseListener {
    @Override
    public void exitClassDeclaration(MehParser.ClassDeclarationContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        ClassType node = new ClassType(name);

        returnNode.put(ctx, node);
    }
}
