package FrontEnd.Listener;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.ConcreteSyntaxTree.MehParser;
import Utility.CompilationError;

import java.util.Map;

/**
 * Created by tan on 4/2/17.
 */
public class ClassFetcherListener extends BaseListener {
    @Override
    public void exitClassDeclaration(MehParser.ClassDeclarationContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        ClassType node = new ClassType(name);

        if (Environment.classTable.containsKey(name)) {
            throw new CompilationError("classes cannot have same name.");
        }
        Environment.classTable.put(name, node);

        Environment.program.addClassType(node);
        returnNode.put(ctx, node);
        if (name.equals("this")) {
            throw new CompilationError("this is a reserved word.");
        }
    }
}
