package FrontEnd.Listener;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.ConcreteSyntaxTree.MehParser;

import java.util.Map;

/**
 * Created by tan on 4/2/17.
 */
public class ClassFetcherListener extends BaseListener {
    @Override
    public void exitClassDeclaration(MehParser.ClassDeclarationContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        ClassType node = new ClassType(name);
        Environment.classNameSet.add(name);
        Environment.program.addClassType(node);
        returnNode.put(ctx, node);
    }
}
