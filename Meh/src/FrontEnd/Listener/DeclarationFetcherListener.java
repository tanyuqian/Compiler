package FrontEnd.Listener;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.BoolType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.VoidType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import FrontEnd.ConcreteSyntaxTree.MehParser;
import Utility.CompilationError;
import Environment.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 4/2/17.
 */
public class DeclarationFetcherListener extends BaseListener {
    @Override
    public void enterNormalFunctionDeclaration(MehParser.NormalFunctionDeclarationContext ctx) {
        String name = ctx.IDENTIFIER(0).getText();
        Type type = (Type)returnNode.get(ctx.type(0));
        ClassType classType = Environment.scopeTable.getClassScope();

        List<Symbol> parameters = new ArrayList<>();
        if (classType != null) {
            parameters.add(new Symbol(classType, "this"));
        }
        for (int i = 1; i < ctx.type().size(); i++) {
            String parameterName = ctx.IDENTIFIER(i).getText();
            Type parameterType = (Type)returnNode.get(ctx.type(i));
            parameters.add(new Symbol(parameterType, parameterName));
        }
        Function function = new Function(type, name, parameters);
        if (classType != null) {
            classType.addMember(function, name);
        } else {
            Environment.symbolTable.add(function, name);
        }
        Environment.program.addFunction(function);
        returnNode.put(ctx, function);
    }

    @Override
    public void enterConstructorFunctionDeclaration(MehParser.ConstructorFunctionDeclarationContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        ClassType classType = Environment.scopeTable.getClassScope();
        if (classType == null) {
            throw new CompilationError("constructor function should be in a class");
        }
        if (name.equals(classType.name) == false) {
            throw new CompilationError("[className: " + classType.name + "]" + ": constructor must have same name with class");
        }
        List<Symbol> parameters = new ArrayList<Symbol>();
        parameters.add(new Symbol(new VoidType(), "this"));
        Function function = new Function(new VoidType(), classType.name, parameters);
        classType.addConstructor(function);
        Environment.program.addFunction(function);
        returnNode.put(ctx, function);
    }

    @Override
    public void enterClassDeclaration(MehParser.ClassDeclarationContext ctx) {
        ClassType node = (ClassType)returnNode.get(ctx);
        Environment.enterScope(node);
    }

    @Override
    public void exitClassDeclaration(MehParser.ClassDeclarationContext ctx) {
        ClassType node = (ClassType)returnNode.get(ctx);
        List<MehParser.VariableDeclarationStatementContext> list = ctx.variableDeclarationStatement();
        for (MehParser.VariableDeclarationStatementContext cc : list) {
            String name = cc.IDENTIFIER().getText();
            Type type = (Type)returnNode.get(cc.type());
            node.addMember(type, name);
        }
        Environment.exitScope();
    }

    @Override
    public void exitVoidType(MehParser.VoidTypeContext ctx) {
        returnNode.put(ctx, new VoidType());
    }

    @Override
    public void exitIntType(MehParser.IntTypeContext ctx) {
        returnNode.put(ctx, new IntType());
    }

    @Override
    public void exitBoolType(MehParser.BoolTypeContext ctx) {
        returnNode.put(ctx, new BoolType());
    }

    @Override
    public void exitStringType(MehParser.StringTypeContext ctx) {
        returnNode.put(ctx, new StringType());
    }

    @Override
    public void exitClassType(MehParser.ClassTypeContext ctx) {
        String name = ctx.getText();
        if (Environment.classNameSet.contains(name) == false) {
            throw new CompilationError("There is no class named " + name);
        }
        returnNode.put(ctx, new ClassType(name));
    }

    @Override
    public void exitArrayType(MehParser.ArrayTypeContext ctx) {
        Type baseType = (Type)returnNode.get(ctx.type());
        returnNode.put(ctx, ArrayType.getType(baseType));
    }
}
