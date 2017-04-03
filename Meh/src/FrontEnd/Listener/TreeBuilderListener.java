package FrontEnd.Listener;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Statement.BlockStatement;
import FrontEnd.AbstractSyntaxTree.Statement.ExpressionStatement;
import FrontEnd.AbstractSyntaxTree.Statement.Statement;
import FrontEnd.AbstractSyntaxTree.Statement.VariableDeclarationStatement;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.Member;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberVariable;
import FrontEnd.ConcreteSyntaxTree.MehParser;
import Environment.Symbol;

import java.util.List;

/**
 * Created by tan on 4/3/17.
 */
public class TreeBuilderListener extends BaseListener {
    @Override
    public void exitProgram(MehParser.ProgramContext ctx) {
        List<MehParser.VariableDeclarationStatementContext> list = ctx.variableDeclarationStatement();
        for (MehParser.VariableDeclarationStatementContext u : list) {
            VariableDeclarationStatement node = (VariableDeclarationStatement)returnNode.get(u);
            Environment.program.addGlobalVariable(node);
        }
    }

    @Override
    public void enterNormalFunctionDeclaration(MehParser.NormalFunctionDeclarationContext ctx) {
        Function function = (Function)returnNode.get(ctx);
        Environment.enterScope(function);
    }

    @Override
    public void enterConstructorFunctionDeclaration(MehParser.ConstructorFunctionDeclarationContext ctx) {
        Function function = (Function)returnNode.get(ctx);
        Environment.enterScope(function);
    }

    @Override
    public void exitNormalFunctionDeclaration(MehParser.NormalFunctionDeclarationContext ctx) {
        Function function = (Function)returnNode.get(ctx);
        function.addStatements((BlockStatement)returnNode.get(ctx.blockStatement()));
        Environment.exitScope();
    }

    @Override
    public void exitConstructorFunctionDeclaration(MehParser.ConstructorFunctionDeclarationContext ctx) {
        Function function = (Function)returnNode.get(ctx);
        function.addStatements((BlockStatement)returnNode.get(ctx.blockStatement()));
        Environment.exitScope();
    }

    @Override
    public void enterClassDeclaration(MehParser.ClassDeclarationContext ctx) {
        ClassType classType = (ClassType)returnNode.get(ctx);
        Environment.enterScope(classType);
        classType.memberVariables.forEach((name, member) -> {
            Environment.symbolTable.add(member.type, name);
        });
        classType.memberFunctions.forEach((name, member) -> {
            Environment.symbolTable.add(member.function, name);
        });
    }

    @Override
    public void exitClassDeclaration(MehParser.ClassDeclarationContext ctx) {
        ClassType classType = (ClassType)returnNode.get(ctx);
        ctx.variableDeclarationStatement().forEach(statementCtx -> {
            String name = statementCtx.IDENTIFIER().getText();
            if (statementCtx.expression() != null) {
                Member member = classType.getMember(name);
                if (member instanceof MemberVariable) {
                    MemberVariable memberVariable = (MemberVariable)member;
                    memberVariable.expression = (Expression)returnNode.get(statementCtx.expression());
                }
            }
        });
        Environment.exitScope();
    }

    @Override
    public void enterStatement(MehParser.StatementContext ctx) {
        if (ctx.parent instanceof MehParser.SelectionStatementContext) {
            Environment.enterScope(null);
        }
    }

    @Override
    public void exitStatement(MehParser.StatementContext ctx) {
        if (ctx.parent instanceof MehParser.SelectionStatementContext) {
            Environment.exitScope();
        }
        returnNode.put(ctx, returnNode.get(ctx.getChild(0)));
    }

    @Override
    public void enterBlockStatement(MehParser.BlockStatementContext ctx) {
        BlockStatement node = new BlockStatement();
        Environment.enterScope(node);
        if (ctx.parent instanceof MehParser.NormalFunctionDeclarationContext) {
            Function function = (Function)returnNode.get(ctx.parent);
            for (int i = 0; i < function.parameters.size(); i++) {
                Symbol symbol = function.parameters.get(i);
                function.parameters.set(i, Environment.symbolTable.addParameterVariable(symbol.type, symbol.name));
            }
        }
        returnNode.put(ctx, node);
    }

    @Override
    public void exitBlockStatement(MehParser.BlockStatementContext ctx) {
        ctx.statement().forEach(statementCtx -> {
            ((BlockStatement)returnNode.get(ctx)).addStatement((Statement)returnNode.get(statementCtx));
        });
        Environment.exitScope();
    }

    @Override
    public void exitExpressionStatement(MehParser.ExpressionStatementContext ctx) {
        returnNode.put(ctx, ExpressionStatement.getStatement(
                (Expression)returnNode.get(ctx.expression())
        ));
    }
}
