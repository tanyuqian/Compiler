package Compiler.FrontEnd.ConcreteSyntaxTree.Listener;

import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.FrontEnd.ConcreteSyntaxTree.Parser.MagiParser;

public class ClassFetcherListener extends BaseListener {
	@Override
	public void exitClassDeclaration(MagiParser.ClassDeclarationContext ctx) {
		String name = ctx.IDENTIFIER(0).getText();
		ClassType classType = (ClassType)ClassType.getType(name);
		//	put the class into class table
		Environment.classTable.put(name, classType);
		//	put the class into program
		Environment.program.addClassType(classType);
		returnNode.put(ctx, classType);
	}
}
