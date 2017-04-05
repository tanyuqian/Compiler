package FrontEnd.Listener;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Expression.BinaryExpression.*;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.BoolConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.IntConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.NullConstant;
import FrontEnd.AbstractSyntaxTree.Expression.ConstantExpression.StringConstant;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Expression.FunctionCallExpression;
import FrontEnd.AbstractSyntaxTree.Expression.NewExpression;
import FrontEnd.AbstractSyntaxTree.Expression.UnaryExpression.*;
import FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.FieldExpression;
import FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.IdentifierExpression;
import FrontEnd.AbstractSyntaxTree.Expression.VariableExpression.SubscriptExpression;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Statement.*;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.ForStatement;
import FrontEnd.AbstractSyntaxTree.Statement.LoopStatement.WhileStatement;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.Member;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberVariable;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import FrontEnd.ConcreteSyntaxTree.MehParser;
import Environment.Symbol;
import Utility.CompilationError;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
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
        if (!Environment.hasMain) {
            throw new CompilationError("no main-function.");
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
        if (ctx.parent instanceof MehParser.FunctionDeclarationContext) {
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

    @Override
    public void exitSelectionStatement(MehParser.SelectionStatementContext ctx) {
        Expression condition = (Expression)returnNode.get(ctx.expression());
        Statement trueStatement = (Statement)returnNode.get(ctx.statement(0));
        Statement falseStatement = (Statement)returnNode.get(ctx.statement(1));
        returnNode.put(ctx, SelectionStatement.getStatement(condition, trueStatement, falseStatement));
    }

    @Override
    public void enterWhileStatement(MehParser.WhileStatementContext ctx) {
        WhileStatement node = new WhileStatement(null, null);
        Environment.enterScope(node);
        returnNode.put(ctx, node);
    }

    @Override
    public void exitWhileStatement(MehParser.WhileStatementContext ctx) {
        WhileStatement node = (WhileStatement)returnNode.get(ctx);
        node.addCondition((Expression)returnNode.get(ctx.expression()));
        node.addStatement((Statement)returnNode.get(ctx.statement()));
        Environment.exitScope();
    }

    @Override
    public void enterForStatement(MehParser.ForStatementContext ctx) {
        ForStatement node = new ForStatement();
        returnNode.put(ctx, node);
        Environment.enterScope(node);
    }

    @Override
    public void exitForStatement(MehParser.ForStatementContext ctx) {
        ForStatement node = (ForStatement)returnNode.get(ctx);
        node.addStatement((Statement)returnNode.get(ctx.statement()));

        int cnt = 0;
        for (ParseTree u : ctx.children) {
            if (u.getText().equals(";")) {
                cnt++;
            }
            if (u instanceof MehParser.ExpressionContext) {
                Expression expression = (Expression)returnNode.get(u);
                if (cnt == 0) {
                    node.addInitialization(expression);
                } else if (cnt == 1) {
                    node.addCondition(expression);
                } else if (cnt == 2) {
                    node.addIncrement(expression);
                } else {
                    throw new CompilationError("Internal Error!");
                }
            }
        }
        Environment.exitScope();
    }

    @Override
    public void exitContinueStatement(MehParser.ContinueStatementContext ctx) {
        returnNode.put(ctx, ContinueStatement.getStatement());
    }

    @Override
    public void exitBreakStatement(MehParser.BreakStatementContext ctx) {
        returnNode.put(ctx, BreakStatement.getStatement());
    }

    @Override
    public void exitReturnStatement(MehParser.ReturnStatementContext ctx) {
        Expression expression = (Expression)returnNode.get(ctx.expression());
        returnNode.put(ctx, ReturnStatement.getStatement(expression));
    }

    @Override
    public void exitVariableDeclarationStatement(MehParser.VariableDeclarationStatementContext ctx) {
        if (!(ctx.parent instanceof MehParser.ClassDeclarationContext)) {
            Type type = (Type)returnNode.get(ctx.type());
            String name = ctx.IDENTIFIER().getText();
            Symbol symbol;
            if (Environment.scopeTable.getScope() == Environment.program) {
                symbol = Environment.symbolTable.addGlobalVariable(type, name);
            } else {
                symbol = Environment.symbolTable.addTemporatyVariable(type, name);
            }
            Expression expression = (Expression)returnNode.get(ctx.expression());
            returnNode.put(ctx, VariableDeclarationStatement.getStatement(symbol, expression));
        }
        if (ctx.IDENTIFIER().getText().equals("this")) {
            throw new CompilationError("this is a reserved word.");
        }
    }

    @Override
    public void exitConstantExpression(MehParser.ConstantExpressionContext ctx) {
        returnNode.put(ctx, returnNode.get(ctx.constant()));
    }

    @Override
    public void exitVariableExpression(MehParser.VariableExpressionContext ctx) {
        returnNode.put(ctx, IdentifierExpression.getExpression(ctx.getText()));
    }

    @Override
    public void exitFieldExpression(MehParser.FieldExpressionContext ctx) {
        returnNode.put(ctx, FieldExpression.getExpression(
                (Expression)returnNode.get(ctx.expression()),
                ctx.IDENTIFIER().getText()
        ));
    }

    @Override
    public void exitSubscriptExpression(MehParser.SubscriptExpressionContext ctx) {
        if (ctx.expression(0) instanceof MehParser.NewExpressionContext) {
            throw new CompilationError("how can you write this???");
        }
        returnNode.put(ctx, SubscriptExpression.getExpression(
                (Expression)returnNode.get(ctx.expression(0)),
                (Expression)returnNode.get(ctx.expression(1))
        ));
    }

    @Override
    public void exitSubExpression(MehParser.SubExpressionContext ctx) {
        returnNode.put(ctx, returnNode.get(ctx.expression()));
    }

    @Override
    public void exitPostfixExpression(MehParser.PostfixExpressionContext ctx) {
        Expression expression = (Expression)returnNode.get(ctx.expression());
        if (ctx.operator.getText().equals("++")) {
            returnNode.put(ctx, PostfixIncrementExpression.getExpression(expression));
        } else if (ctx.operator.getText().equals("--")) {
            returnNode.put(ctx, PostfixDecrementExpression.getExpression(expression));
        } else {
            throw new CompilationError("Internal Error!!");
        }
    }

    @Override
    public void exitUnaryExpression(MehParser.UnaryExpressionContext ctx) {
        Expression expression = (Expression)returnNode.get(ctx.expression());
        if (ctx.operator.getText().equals("+")) {
            returnNode.put(ctx, UnaryPlusExpression.getExpression(expression));
        } else if (ctx.operator.getText().equals("-")) {
            returnNode.put(ctx, UnaryMinusExpression.getExpression(expression));
        } else if (ctx.operator.getText().equals("!")) {
            returnNode.put(ctx, LogicalNotExpression.getExpression(expression));
        } else if (ctx.operator.getText().equals("~")) {
            returnNode.put(ctx, BitwiseNotExpression.getExpression(expression));
        } else if (ctx.operator.getText().equals("++")) {
            returnNode.put(ctx, PrefixIncrementExpression.getExpression(expression));
        } else if (ctx.operator.getText().equals("--")) {
            returnNode.put(ctx, PrefixDecrementExpression.getExpression(expression));
        } else {
            throw new CompilationError("Internal Error!!");
        }
    }

    @Override
    public void exitFunctionCallExpression(MehParser.FunctionCallExpressionContext ctx) {
        Expression function = (Expression)returnNode.get(ctx.expression(0));
        List<Expression> parameters = new ArrayList<>();
        for (int i = 1; i < ctx.expression().size(); i++) {
            Expression parameter = (Expression)returnNode.get(ctx.expression(i));
            parameters.add(parameter);
        }

        returnNode.put(ctx, FunctionCallExpression.getExpression(function, parameters));
    }

    @Override
    public void exitNewExpression(MehParser.NewExpressionContext ctx) {
        List<Expression> list = new ArrayList<>();
        ctx.expression().forEach(expressionCtx -> {
            Expression cur = (Expression)returnNode.get(expressionCtx);
            list.add(cur);
        });
        String last = null;
        for (ParseTree node : ctx.children) {
            if (node instanceof TerminalNode) {
                if (node.getText().equals("]") && last.equals("[")) {
                    list.add(null);
                }
            }
            last = node.getText();
        }
        Type baseType = (Type)returnNode.get(ctx.type());
        returnNode.put(ctx, NewExpression.getExpression(baseType, list));
    }

    @Override
    public void exitMultiplicativeExpression(MehParser.MultiplicativeExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        if (ctx.operator.getText().equals("*")) {
            returnNode.put(ctx, MultiplicationExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals("/")) {
            returnNode.put(ctx, DivisionExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals("%")) {
            returnNode.put(ctx, ModuloExpression.getExpression(left, right));
        } else  {
            throw new CompilationError("Internal Error!");
        }
    }

    @Override
    public void exitAdditiveExpression(MehParser.AdditiveExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        if (ctx.operator.getText().equals("+")) {
            returnNode.put(ctx, AdditionExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals("-")) {
            returnNode.put(ctx, SubtractionExpression.getExpression(left, right));
        } else {
            throw new CompilationError("Internal Error!");
        }
    }

    @Override
    public void exitShiftExpression(MehParser.ShiftExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        if (ctx.operator.getText().equals("<<")) {
            returnNode.put(ctx, BitwiseLeftShiftExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals(">>")) {
            returnNode.put(ctx, BitwiseRightShiftExpression.getExpression(left, right));
        } else {
            throw new CompilationError("Internal Error!");
        }
    }

    @Override
    public void exitRelationExpression(MehParser.RelationExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        if (ctx.operator.getText().equals("<")) {
            returnNode.put(ctx, LessThanExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals("<=")) {
            returnNode.put(ctx, LessThanOrEqualToExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals(">")) {
            returnNode.put(ctx, GreaterThanExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals(">=")) {
            returnNode.put(ctx, GreaterThanOrEqualToExpression.getExpression(left, right));
        } else {
            throw new CompilationError("Internal Error!");
        }
    }

    @Override
    public void exitEqualityExpression(MehParser.EqualityExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        if (ctx.operator.getText().equals("==")) {
            returnNode.put(ctx, EqualToExpression.getExpression(left, right));
        } else if (ctx.operator.getText().equals("!=")) {
            returnNode.put(ctx, NotEqualToExpression.getExpression(left, right));
        } else {
            throw new CompilationError("Internal Error!");
        }
    }

    @Override
    public void exitAndExpression(MehParser.AndExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        returnNode.put(ctx, BitwiseAndExpression.getExpression(left, right));
    }

    @Override
    public void exitExclusiveOrExpression(MehParser.ExclusiveOrExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        returnNode.put(ctx, BitwiseXorExpression.getExpression(left, right));
    }

    @Override
    public void exitInclusiveOrExpression(MehParser.InclusiveOrExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        returnNode.put(ctx, BitwiseOrExpression.getExpression(left, right));
    }

    @Override
    public void exitLogicalAndExpression(MehParser.LogicalAndExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        returnNode.put(ctx, LogicalAndExpression.getExpression(left, right));
    }

    @Override
    public void exitLogicalOrExpression(MehParser.LogicalOrExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        returnNode.put(ctx, LogicalOrExpression.getExpression(left, right));
    }

    @Override
    public void exitAssignmentExpression(MehParser.AssignmentExpressionContext ctx) {
        Expression left = (Expression)returnNode.get(ctx.expression(0));
        Expression right = (Expression)returnNode.get(ctx.expression(1));
        returnNode.put(ctx, AssignmentExpression.getExpression(left, right));
    }

    @Override
    public void exitBoolConstant(MehParser.BoolConstantContext ctx) {
        returnNode.put(ctx, new BoolConstant(Boolean.valueOf(ctx.getText())));
    }

    @Override
    public void exitIntConstant(MehParser.IntConstantContext ctx) {
        returnNode.put(ctx, new IntConstant(Integer.valueOf(ctx.getText())));
    }

    @Override
    public void exitStringConstant(MehParser.StringConstantContext ctx) {
        returnNode.put(ctx, new StringConstant(ctx.getText()));
    }

    @Override
    public void exitNullConstant(MehParser.NullConstantContext ctx) {
        returnNode.put(ctx, new NullConstant());
    }
}
