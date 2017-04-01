package cn.edu.sjtu.songyuke.mental.ast;

import cn.edu.sjtu.songyuke.mental.ast.declarations.ClassDeclaration;
import cn.edu.sjtu.songyuke.mental.ast.declarations.FunctionDefinition;
import cn.edu.sjtu.songyuke.mental.ast.declarations.SingleVariableDeclaration;
import cn.edu.sjtu.songyuke.mental.ast.declarations.VariableDeclaration;
import cn.edu.sjtu.songyuke.mental.ast.expressions.*;
import cn.edu.sjtu.songyuke.mental.ast.statements.*;
import cn.edu.sjtu.songyuke.mental.parser.MentalBaseListener;
import cn.edu.sjtu.songyuke.mental.parser.MentalParser;
import cn.edu.sjtu.songyuke.mental.symbols.*;
import cn.edu.sjtu.songyuke.mental.type.Array;
import cn.edu.sjtu.songyuke.mental.type.Class;
import cn.edu.sjtu.songyuke.mental.type.Function;
import cn.edu.sjtu.songyuke.mental.type.MxString;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.LinkedList;

public class BuildTreeListener extends MentalBaseListener {
    /**
     * Initialize the listener
     */
    public boolean existError;                                      // record whether there is any error in the program.
	public HashMap<ParseTree, BaseNode> tree;                    // a K-V map used in building my abstract syntax tree.
	public SymbolTable curSymbolTable;                              // the symbol table in current scope.
	public LinkedList<SymbolTable> symbolTableList;                 // list of different stack scope
    public int variableCounter;
    public int globalVariableCounter;

	public BuildTreeListener() {
        this.existError = false;
		this.tree = new HashMap<>();
		this.symbolTableList = new LinkedList<>();
		this.symbolTableList.add(new SymbolTable());
		this.curSymbolTable = this.symbolTableList.getLast();
        this.variableCounter = 0;
        this.globalVariableCounter = 0;
	}
    public boolean checkMain() {
        if (this.curSymbolTable.getSymbol("main") == null) {
            System.err.println("fatal: the function `main` is undefined.");
            return false;
        } else {
            SymbolFunction functionMain = (SymbolFunction) this.curSymbolTable.getSymbol("main");
            if (functionMain.returnType.equals(SymbolTable.mentalInt)) {
                if (functionMain.parameterType == null || functionMain.parameterType.size() == 0) {
                    return true;
                }
            }
            System.err.println("fatal: the format of `main` is incorrect.\n\t"
                    + "expects: int main()\n\t"
                    + "occurs:  " + functionMain.toString().substring(10)
            );
            return false;
        }
    }
    /**
     * make a copy of current scope and increase the stack label.
     */
	public void beginScope() {
		this.symbolTableList.add(new SymbolTable(curSymbolTable));
		this.curSymbolTable = this.symbolTableList.getLast();
		this.curSymbolTable.stackLayer++;
	}
    /**
     * remove the current scope and back the lastest one.
     */
	public void endScope() {
		this.symbolTableList.removeLast();
		this.curSymbolTable = this.symbolTableList.getLast();
	}
    public int newVariableID() {
        return this.variableCounter++;
    }
    @Override public void enterEveryRule(ParserRuleContext ctx) {
        if (this.existError) {
            System.exit(1);
        }
    }
    @Override public void exitEveryRule(ParserRuleContext ctx) {
        if (this.existError) {
            System.exit(1);
        }
    }
    @Override public void visitTerminal(TerminalNode node) { }
    @Override public void visitErrorNode(ErrorNode node) {
        System.err.println("fatal: there is an error in grammar analysis.");
        System.exit(1);
    }
    /**
     * function following do nothing because none of them have effect.
     */
	@Override public void enterClassName(MentalParser.ClassNameContext ctx) { }
	@Override public void exitClassName(MentalParser.ClassNameContext ctx) { }
	@Override public void enterTypeName(MentalParser.TypeNameContext ctx) { }
	@Override public void exitTypeName(MentalParser.TypeNameContext ctx) { }
	@Override public void enterArray(MentalParser.ArrayContext ctx) { }
	@Override public void exitArray(MentalParser.ArrayContext ctx) { }
	@Override public void enterType(MentalParser.TypeContext ctx) { }
	@Override public void exitType(MentalParser.TypeContext ctx) { }
	@Override public void enterParametersList(MentalParser.ParametersListContext ctx) { }
	@Override public void exitParametersList(MentalParser.ParametersListContext ctx) { }
    /**
     * iterate the child of program twice in this function.
     * first time
     *     get the name of all functions and classes and allocate a space in symbol table for each one.
     * second time
     *     process the members of a class and the format of a function.
     */
	@Override public void enterProgram(MentalParser.ProgramContext ctx) {
        this.tree.put(ctx, new Program());
        for (int i = 0, count = ctx.getChildCount(); i < count; ++i) {
            if (ctx.getChild(i) instanceof MentalParser.EmptyStatementContext) {
                continue;
            }
            if (ctx.getChild(i) instanceof MentalParser.DeclarationContext) {
                // class is declaration.
                MentalParser.DeclarationContext declarationContext = (MentalParser.DeclarationContext) ctx.getChild(i);
                if (declarationContext.classDeclaration() != null) {
                    this.curSymbolTable.add(declarationContext.classDeclaration().className().getText(), new SymbolType());
                }
            } else if (ctx.getChild(i) instanceof MentalParser.DefinitionContext) {
                // variable and function is definition.
                MentalParser.DefinitionContext definitionContext = (MentalParser.DefinitionContext) ctx.getChild(i);
                if (definitionContext.functionDefinition() != null) {
                    // it is a function definition.
                    this.curSymbolTable.add(definitionContext.functionDefinition().functionName.getText(), new SymbolFunction());
                }
            }
        }
        for (int i = 0, count = ctx.getChildCount(); i < count; ++i) {
            if (ctx.getChild(i) instanceof MentalParser.EmptyStatementContext) {
                continue;
            }
            if (ctx.getChild(i) instanceof MentalParser.DefinitionContext) {
                MentalParser.DefinitionContext definitionContext = (MentalParser.DefinitionContext) ctx.getChild(i);
                if (definitionContext.functionDefinition() != null) {
                    MentalParser.FunctionDefinitionContext functionDefinitionContext = definitionContext.functionDefinition();
                    SymbolFunction symbolFunction = (SymbolFunction) this.curSymbolTable.getSymbol(functionDefinitionContext.functionName.getText());
                    // setFunction will process the basic message of a function
                    //     function name, cn.edu.sjtu.songyuke.mental.type of return value, types of parameters.
                    // if there is anything wrong the function returns true.
                    if (symbolFunction.setFunction(this.curSymbolTable, functionDefinitionContext)) {
                        this.existError = true;
                    }
                }
            } else if (ctx.getChild(i) instanceof MentalParser.DeclarationContext) {
                MentalParser.DeclarationContext declarationContext = (MentalParser.DeclarationContext) ctx.getChild(i);
                if (declarationContext.classDeclaration() != null) {
                    MentalParser.ClassDeclarationContext classDeclaration = declarationContext.classDeclaration();
                    if (classDeclaration.className() != null) {
                        SymbolType symbolType = (SymbolType) this.curSymbolTable.getSymbol(classDeclaration.className().getText());
                        // setType will process the members of a class
                        //     name and cn.edu.sjtu.songyuke.mental.type of each member.
                        // returns true if there is anything wrong.
                        if (symbolType.setType(this.curSymbolTable, classDeclaration)) {
                            this.existError = true;
                        }
                    }
                }
            }
        }
    }
	@Override public void exitProgram(MentalParser.ProgramContext ctx) {
		Program program = (Program) tree.get(ctx);
		for (int i = 0, count = ctx.getChildCount(); i < count; ++i) {
            if (ctx.getChild(i) instanceof MentalParser.EmptyStatementContext) {
                continue;
            }
            // connect the child to their parent.
            program.declarations.add(tree.get(ctx.getChild(i)));
		}
        if (!this.checkMain()) {
            this.existError = true;
        }
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterDeclaration(MentalParser.DeclarationContext ctx) { }
	@Override public void exitDeclaration(MentalParser.DeclarationContext ctx) {
        if (ctx.classDeclaration() != null) {
            // just do the path compression.
            this.tree.put(ctx, tree.get(ctx.classDeclaration()));
            this.tree.get(ctx).parent = this.tree.get(ctx.parent);
        }
    }
	/**
	 * all the messages of a class have been got in enterProgram()
     *    so this function just build the AST for a class declaration from the message in the symbol table. 
	 */
	@Override public void enterClassDeclaration(MentalParser.ClassDeclarationContext ctx) {
		ClassDeclaration classDeclaration = new ClassDeclaration();
        classDeclaration.classDetail = (SymbolType) this.curSymbolTable.getSymbol(ctx.className().getText());
        this.tree.put(ctx, classDeclaration);
        if (ctx.variableDefinition() != null) {
            for (int i = 0, count = ctx.variableDefinition().size(); i < count; ++i) {
                MentalParser.VariableDefinitionContext variableDefinitionContext = ctx.variableDefinition(i);
                if (variableDefinitionContext.singleVariable() != null) {
                    for (int j = 0, varCount = variableDefinitionContext.singleVariable().size(); j < varCount; ++j) {
                        MentalParser.SingleVariableContext singleVariableContext = variableDefinitionContext.singleVariable(j);
                        if (singleVariableContext.expression() != null) {
                            System.err.println("warning: variable declaration in class declaration should not have initial value\n\t"
                                    + "which will ignored.:\n\t\t" + singleVariableContext.getText());
                        }
                    }
                }
            }
        }
	}
	@Override public void exitClassDeclaration(MentalParser.ClassDeclarationContext ctx) {	}
	/**
	 * there is no function declaration in this language,
     *     this pair of functions is for the furture support if function declaration is need.
	 */
	@Override public void enterFunctionDeclaration(MentalParser.FunctionDeclarationContext ctx) { }
	@Override public void exitFunctionDeclaration(MentalParser.FunctionDeclarationContext ctx) { }
	/**
	 * compress the path on AST.
	 */
	@Override public void enterDefinition(MentalParser.DefinitionContext ctx) { }
	@Override public void exitDefinition(MentalParser.DefinitionContext ctx) {
        if (ctx.functionDefinition() != null) {
            this.tree.put(ctx, this.tree.get(ctx.functionDefinition()));
        } else if (ctx.variableDefinition() != null) {
            this.tree.put(ctx, this.tree.get(ctx.variableDefinition()));
        }
        this.tree.get(ctx).parent = this.tree.get(ctx.parent);
    }
    /**
     * In my compiler, it is allowed to define several variables in a single variable definition.
     *     this pair of functions processes one variable in a variable definition.
     */
    @Override public void enterSingleVariable(MentalParser.SingleVariableContext ctx) {
        SingleVariableDeclaration singleVariableDeclaration = new SingleVariableDeclaration();
        this.tree.put(ctx, singleVariableDeclaration);
        VariableDeclaration variableDeclaration;
        if (ctx.parent != null && ctx.parent.parent instanceof MentalParser.ClassDeclarationContext) {
            // the parent of this definition is a class declaration, so the message of this statement is useless
            return ;
        }
        if (ctx.parent instanceof MentalParser.VariableDefinitionContext) {
            // the cn.edu.sjtu.songyuke.mental.type is contained in its parent, and the node of its parent is in the map.
            variableDeclaration = (VariableDeclaration) this.tree.get(ctx.parent);
        } else {
            System.err.println("unknown exception.");
            this.existError = true;
            return;
        }
        // new a node.
        singleVariableDeclaration.variable = new Variable();
        singleVariableDeclaration.variable.globalID = this.newVariableID();
        if ((ctx.parent.parent.parent instanceof MentalParser.ProgramContext)) {
            singleVariableDeclaration.variable.localID = this.globalVariableCounter++;
        } else {
            ParserRuleContext pCtx = (ParserRuleContext) ctx.parent.parent;
            while (pCtx != null && !(pCtx instanceof MentalParser.FunctionDefinitionContext)) {
                pCtx = (ParserRuleContext) pCtx.parent;
            }
            FunctionDefinition functionDefinition = (FunctionDefinition) this.tree.get(pCtx);
            singleVariableDeclaration.variable.localID = singleVariableDeclaration.variable.globalID - functionDefinition.firstVariableID;
        }
        // get the name of the variable.
        singleVariableDeclaration.variable.variableName = ctx.Identifier().getText();
        // set the cn.edu.sjtu.songyuke.mental.type of the variable.
        singleVariableDeclaration.variable.variableType = variableDeclaration.variableType;
    }
    @Override public void exitSingleVariable(MentalParser.SingleVariableContext ctx) {
        /**
         * this function mainly process the initializing expression of a variable.
         */
        if (ctx.parent != null && ctx.parent.parent instanceof MentalParser.ClassDeclarationContext) {
            return ;
        }
        SingleVariableDeclaration singleVariableDeclaration = (SingleVariableDeclaration) this.tree.get(ctx);
        if (ctx.expression() != null) {
            singleVariableDeclaration.initializeExpression = (Expression) this.tree.get(ctx.expression());
        }
        singleVariableDeclaration.parent = this.tree.get(ctx.parent);
        SymbolVariable var = new SymbolVariable();
        var.variable = singleVariableDeclaration.variable;
        var.stackLayer = this.curSymbolTable.stackLayer;
        this.curSymbolTable.add(var.variable.variableName, var);
        if (singleVariableDeclaration.initializeExpression == null) {
            return;
        }
        if (!singleVariableDeclaration.variable.variableType.equals(singleVariableDeclaration.initializeExpression.returnType)) {
            System.err.println("fatal: The types of variable and initial value are different.\n\t"
                    + "<var>" + singleVariableDeclaration.variable.variableType.toString()
                    + "<initial value>" + singleVariableDeclaration.initializeExpression.returnType
            );
            this.existError = true;
        }
    }
    /**
	 * process a sentence of variable definition, which may define several variable.
	 */
	@Override public void enterVariableDefinition(MentalParser.VariableDefinitionContext ctx) {
        if (ctx.parent instanceof MentalParser.ClassDeclarationContext) {
            return ;
        }
        SymbolVariableList variableList = new SymbolVariableList(this.curSymbolTable, ctx);
        VariableDeclaration variableDeclaration = new VariableDeclaration();
        variableDeclaration.variables = new LinkedList<>();
        variableDeclaration.variableType = variableList.variableType;
        this.tree.put(ctx, variableDeclaration);
	}
	@Override public void exitVariableDefinition(MentalParser.VariableDefinitionContext ctx) {
        if (ctx.parent instanceof MentalParser.ClassDeclarationContext) {
            return ;
        }
        VariableDeclaration variableDeclaration = (VariableDeclaration) this.tree.get(ctx);
        for (MentalParser.SingleVariableContext singleVariable : ctx.singleVariable()) {
            variableDeclaration.variables.add((SingleVariableDeclaration) this.tree.get(singleVariable));
        }
        variableDeclaration.parent = this.tree.get(ctx.parent);
    }
	/**
	 * build the cn.edu.sjtu.songyuke.mental.ast of a function.
	 */
	@Override public void enterFunctionDefinition(MentalParser.FunctionDefinitionContext ctx) {
        if (ctx.parent instanceof MentalParser.ClassDeclarationContext) {
            return;
        }
		this.beginScope();
        FunctionDefinition functionDefinition = new FunctionDefinition();
        functionDefinition.functionHead = (SymbolFunction) this.curSymbolTable.getSymbol(ctx.functionName.getText());
        // I think the name of itself cannot be redefine in the function.
        functionDefinition.functionHead.stackLayer = SymbolTable.maxLayer;
        // add the parameters to the scope.
        this.tree.put(ctx, functionDefinition);
        functionDefinition.firstVariableID = variableCounter;
        for (int i = 0, count = functionDefinition.functionHead.parameterName.size(); i < count; ++i) {
            SymbolVariable thisParameter = new SymbolVariable(
                    this.curSymbolTable,
                    functionDefinition.functionHead.parameterType.get(i),
                    functionDefinition.functionHead.parameterName.get(i),
                    this.variableCounter++
            );
            thisParameter.variable.localID = thisParameter.variable.globalID - functionDefinition.firstVariableID;
            this.curSymbolTable.add(
                    functionDefinition.functionHead.parameterName.get(i),
                    thisParameter
            );
        }
	}
	@Override public void exitFunctionDefinition(MentalParser.FunctionDefinitionContext ctx) {
        if (ctx.parent instanceof MentalParser.ClassDeclarationContext) {
            // for furture support of member method.
            return;
        }

        this.endScope();
        // connect the tree node.
        FunctionDefinition functionDefinition = (FunctionDefinition) this.tree.get(ctx);
        functionDefinition.functionBody = (CompoundStatement) this.tree.get(ctx.compoundStatement());
        functionDefinition.parent = this.tree.get(ctx.parent);
        functionDefinition.functionBody.parent = functionDefinition;
        functionDefinition.lastVariableID = this.variableCounter - 1;
    }
	/**
	 * just a C-style union of statements
	 */
	@Override public void enterStatement(MentalParser.StatementContext ctx) {
        if (ctx.parent instanceof MentalParser.ForStatementContext
                || ctx.parent instanceof MentalParser.WhileStatementContext
                || ctx.parent instanceof MentalParser.IfStatementContext
                || ctx.parent instanceof MentalParser.IfElseStatementContext) {
            this.beginScope();
        }
        if (ctx.variableDefinition() != null) {
            VarStatement varStatement = new VarStatement();
            this.tree.put(ctx, varStatement);
        }
    }
	@Override public void exitStatement(MentalParser.StatementContext ctx) {
        // compress the path
        if (ctx.variableDefinition() != null) {
            VarStatement varStatement = (VarStatement) this.tree.get(ctx);
            varStatement.variableDeclaration = (VariableDeclaration) this.tree.get(ctx.variableDefinition());
        } else if (ctx.ifElseStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.ifElseStatement()));
        } else if (ctx.ifStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.ifStatement()));
        } else if (ctx.compoundStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.compoundStatement()));
        } else if (ctx.emptyStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.emptyStatement()));
        } else if (ctx.expressionStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.expressionStatement()));
        } else if (ctx.forStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.forStatement()));
        } else if (ctx.whileStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.whileStatement()));
        } else if (ctx.jumpStatement() != null) {
            this.tree.put(ctx, this.tree.get(ctx.jumpStatement()));
        }
        // connect its parent link
        if (this.tree.get(ctx) != null) {
            this.tree.get(ctx).parent = this.tree.get(ctx.parent);
        } else {
            System.err.println("fatal: unknown statement.");
            this.existError = true;
        }
        if (ctx.parent instanceof MentalParser.ForStatementContext
                || ctx.parent instanceof MentalParser.WhileStatementContext
                || ctx.parent instanceof MentalParser.IfStatementContext
                || ctx.parent instanceof MentalParser.IfElseStatementContext) {
            this.endScope();
        }
    }
    /**
     * build tree of a compound statement
     * new a scope if it is not the function body.
     */
    @Override public void enterCompoundStatement(MentalParser.CompoundStatementContext ctx) {
        // new a scope if it is not the function body.
        if (!(ctx.parent instanceof MentalParser.FunctionDefinitionContext)) {
            if (ctx.parent instanceof MentalParser.StatementContext
                    && !(ctx.parent.parent instanceof MentalParser.ForStatementContext
                        || ctx.parent.parent instanceof MentalParser.WhileStatementContext
                        || ctx.parent.parent instanceof MentalParser.IfStatementContext
                        || ctx.parent.parent instanceof MentalParser.IfElseStatementContext)) {
                this.beginScope();
            }
        }
        CompoundStatement componentStatement = new CompoundStatement();
        this.tree.put(ctx, componentStatement);
    }
    @Override public void exitCompoundStatement(MentalParser.CompoundStatementContext ctx) {
        if (!(ctx.parent instanceof MentalParser.FunctionDefinitionContext)) {
            if (ctx.parent instanceof MentalParser.StatementContext
                    && !(ctx.parent.parent instanceof MentalParser.ForStatementContext
                            || ctx.parent.parent instanceof MentalParser.WhileStatementContext
                            || ctx.parent.parent instanceof MentalParser.IfStatementContext
                            || ctx.parent.parent instanceof MentalParser.IfElseStatementContext)) {
                this.endScope();
            }
        }
        CompoundStatement thisStatement = (CompoundStatement) this.tree.get(ctx);
        // it is not necessary to set the parent of children, it will be done in the exitStatement().
        for (MentalParser.StatementContext statementContext : ctx.statement()) {
            thisStatement.statements.add(this.tree.get(statementContext));
        }
        thisStatement.parent = this.tree.get(ctx.parent);
    }
    /**
     * empty statement
     */
	@Override public void enterEmptyStatement(MentalParser.EmptyStatementContext ctx) {
        EmptyStatement emptyStatement = new EmptyStatement();
        this.tree.put(ctx, emptyStatement);
    }
	@Override public void exitEmptyStatement(MentalParser.EmptyStatementContext ctx) { }
	/**
	 * process `if (expression) statement`
	 */
	@Override public void enterIfStatement(MentalParser.IfStatementContext ctx) {
        IfStatement ifStatement = new IfStatement();
        this.tree.put(ctx, ifStatement);
    }
	@Override public void exitIfStatement(MentalParser.IfStatementContext ctx) {
        IfStatement thisStatement = (IfStatement) this.tree.get(ctx);
        thisStatement.condition = (Expression) this.tree.get(ctx.expression());
        thisStatement.condition.parent = thisStatement;
        thisStatement.thenStatement = (Statement) this.tree.get(ctx.thenStatement);
        thisStatement.thenStatement.parent = thisStatement;
        if (!thisStatement.condition.returnType.equals(SymbolTable.mentalBool)) {
            System.err.println("fatal: the condition of if-statement is not boolean.\n\t"
                    + ctx.expression().getText() + "@" + thisStatement.condition.returnType.toString()
            );
            this.existError = true;
        }
    }
	/**
	 * process `if (expression) statement else statement`
	 */
	@Override public void enterIfElseStatement(MentalParser.IfElseStatementContext ctx) {
        IfStatement ifElseStatement = new IfStatement();
        this.tree.put(ctx, ifElseStatement);
    }
	@Override public void exitIfElseStatement(MentalParser.IfElseStatementContext ctx) {
        IfStatement thisStatement = (IfStatement) this.tree.get(ctx);
        thisStatement.condition = (Expression) this.tree.get(ctx.expression());
        thisStatement.condition.parent = thisStatement;
        thisStatement.thenStatement = (Statement) this.tree.get(ctx.thenStatement);
        thisStatement.thenStatement.parent = thisStatement;
        thisStatement.elseStatement = (Statement) this.tree.get(ctx.elseStatment);
        thisStatement.elseStatement.parent = thisStatement;
        if (!thisStatement.condition.returnType.equals(SymbolTable.mentalBool)) {
            System.err.println("fatal: the condition of if-statement is not boolean." + ctx.expression().getText());
            this.existError = true;
        }
    }
	/**
	 * for statement
     * for (start?; cond?; loop) loopBody
	 */
	@Override public void enterForStatement(MentalParser.ForStatementContext ctx) {
        ForStatement forStatement = new ForStatement();
        this.tree.put(ctx, forStatement);
    }
	@Override public void exitForStatement(MentalParser.ForStatementContext ctx) {
        ForStatement thisStatement = (ForStatement) this.tree.get(ctx);
        if (ctx.start != null) {
            thisStatement.start = (Expression) this.tree.get(ctx.start);
            thisStatement.start.parent = thisStatement;
        }
        if (ctx.cond != null) {
            thisStatement.cond = (Expression) this.tree.get(ctx.cond);
        } else {
            thisStatement.cond = new BoolConstant();
            ((BoolConstant) thisStatement.cond).boolConstant = true;
        }
        thisStatement.cond.parent = thisStatement;
        if (ctx.loop != null) {
            thisStatement.loop = (Expression) this.tree.get(ctx.loop);
            thisStatement.loop.parent = thisStatement;
        }
        thisStatement.loopBody = (Statement) this.tree.get(ctx.statement());
        thisStatement.loopBody.parent = thisStatement;
        if (!thisStatement.cond.returnType.equals(SymbolTable.mentalBool)) {
            System.err.println("fatal: the loop condition of for statement does not return boolean.\n\t"
                    + ctx.cond.getText() + "@" + thisStatement.cond.returnType.toString()
            );
            this.existError = true;
        }
    }
	/**
	 * while statement
     * while (cond) loopBody
	 */
	@Override public void enterWhileStatement(MentalParser.WhileStatementContext ctx) {
        WhileStatement whileStatement = new WhileStatement();
        this.tree.put(ctx, whileStatement);
    }
	@Override public void exitWhileStatement(MentalParser.WhileStatementContext ctx) {
        WhileStatement thisStatement = (WhileStatement) this.tree.get(ctx);
        thisStatement.cond = (Expression) this.tree.get(ctx.cond);
        thisStatement.cond.parent = thisStatement;
        thisStatement.loopBody = (Statement) this.tree.get(ctx.statement());
        thisStatement.loopBody.parent = thisStatement;
        if (!thisStatement.cond.returnType.equals(SymbolTable.mentalBool)) {
            System.err.println("fatal: the loop condition of for statement does not return boolean.\n\t"
                    + ctx.cond.getText() + "@" + thisStatement.cond.returnType.toString()
            );
            this.existError = true;
        }
    }
	/**
	 * return expression;
     * continue;
     * break;
	 */
	@Override public void enterJumpStatement(MentalParser.JumpStatementContext ctx) {
        JumpStatement jumpStatement = new JumpStatement();
        if (ctx.expression() == null) {
            if (ctx.getText().equals("continue;")) {
                jumpStatement.variant = JumpStatement.CONTINUE;
            } else if (ctx.getText().equals("break;")) {
                jumpStatement.variant = JumpStatement.BREAK;
            } else if (ctx.getText().equals("return;")) {
                jumpStatement.variant = JumpStatement.RETURN;
            }
        } else {
            jumpStatement.variant = JumpStatement.RETURN;
        }
        this.tree.put(ctx, jumpStatement);
    }
	@Override public void exitJumpStatement(MentalParser.JumpStatementContext ctx) {
        JumpStatement thisStatement = (JumpStatement) this.tree.get(ctx);
        if (thisStatement.variant == JumpStatement.BREAK || thisStatement.variant == JumpStatement.CONTINUE) {
            // continue and break are not allowed out of loop statement.
            RuleContext node = ctx;
            while (node != null && !(node.parent instanceof MentalParser.ForStatementContext
                    || node.parent instanceof MentalParser.WhileStatementContext)
                    ) {
                node = node.parent;
            }
            if (node != null) {
                return;
            }
        } else {
            if (ctx.expression() != null) {
                thisStatement.returnExpression = (Expression) this.tree.get(ctx.expression());
                thisStatement.returnExpression.parent = thisStatement;
            } else {
                thisStatement.returnExpression = new Expression();
                thisStatement.returnExpression.returnType = SymbolTable.mentalVoid;
                thisStatement.returnExpression.parent = thisStatement;
            }
            RuleContext node = ctx.parent;
            while (node != null && !(node instanceof MentalParser.FunctionDefinitionContext)) {
                node = node.parent;
            }
            if (node != null) {
                FunctionDefinition functionDefinition = (FunctionDefinition) this.tree.get(node);
                if (functionDefinition.functionHead.returnType.equals(thisStatement.returnExpression.returnType)) {
                    return;
                } else {
                    System.err.println("fatal: return a wrong cn.edu.sjtu.songyuke.mental.type value.\n"
                            + "\texpected " + functionDefinition.functionHead.returnType.toString()
                            + "\n\toccurs " + thisStatement.returnExpression.returnType.toString()
                    );
                    this.existError = true;
                }
            }
        }
        System.err.println("fatal: a illegal jump statement.\n\t" + ctx.getText());
        this.existError = true;
    }
	/**
	 * expression statement
     *     most important one is `a=b;`
	 */
	@Override public void enterExpressionStatement(MentalParser.ExpressionStatementContext ctx) { }
	@Override public void exitExpressionStatement(MentalParser.ExpressionStatementContext ctx) {
        ExpressionStatement expressionStatement = new ExpressionStatement();
        expressionStatement.expression = (Expression) this.tree.get(ctx.expression());
        this.tree.put(ctx, expressionStatement);
    }
	
	/**
	 * !a
     * a should be boolean.
	 */
	@Override public void enterLOGICAL_NOT_EXPRESSION(MentalParser.LOGICAL_NOT_EXPRESSIONContext ctx) {
        LogicalNotExpression expression = new LogicalNotExpression();
        this.tree.put(ctx, expression);
    }
	@Override public void exitLOGICAL_NOT_EXPRESSION(MentalParser.LOGICAL_NOT_EXPRESSIONContext ctx) {
        LogicalNotExpression thisExpression = (LogicalNotExpression) this.tree.get(ctx);
        thisExpression.childExpression = (Expression) this.tree.get(ctx.expression());
        thisExpression.childExpression.parent = thisExpression;
        if (!thisExpression.childExpression.returnType.equals(SymbolTable.mentalBool)) {
            System.err.println("fatal: try to apply logical-not-operator on a no-boolean item.\n\t" + ctx.getText());
            this.existError = true;
        }
        if (thisExpression.childExpression instanceof BoolConstant) {
            BoolConstant replaceNode = new BoolConstant();
            replaceNode.boolConstant = !((BoolConstant) thisExpression.childExpression).boolConstant;
            this.tree.replace(ctx, replaceNode);
        }
    }
	/**
	 * a.b 
     * a shouble be a class for member access 
     *     or a array for array.size() 
     *     or some other string method
	 */
	@Override public void enterMEMBER_ACCESS_EXPRESSION(MentalParser.MEMBER_ACCESS_EXPRESSIONContext ctx) {
        MemberAccessExpression memberAccessExpression = new MemberAccessExpression();
        this.tree.put(ctx, memberAccessExpression);
    }
	@Override public void exitMEMBER_ACCESS_EXPRESSION(MentalParser.MEMBER_ACCESS_EXPRESSIONContext ctx) {
        MemberAccessExpression thisExpression = (MemberAccessExpression) this.tree.get(ctx);
        thisExpression.primaryExpression = (Expression) this.tree.get(ctx.expression());
        thisExpression.primaryExpression.parent = thisExpression;
        if (ctx.functionCall() != null) {
            // this member access expression is a internal method call
            // the the function call message from functionCall context.
            FunctionCall functionCall = (FunctionCall) this.tree.get(ctx.functionCall());
            if (ctx.functionCall().functionName.getText().equals("length")) {
                // it may be string.length()
                if (thisExpression.primaryExpression.returnType instanceof MxString) {
                    thisExpression.memberExpression = new CallLength();
                    thisExpression.memberExpression.parent = thisExpression;
                    thisExpression.returnType = SymbolTable.mentalInt;
                    if (functionCall.parameters.expressions.size() != 0) {
                        System.err.println("fatal: the number of parameters of string.length is wrong.\n\t" + ctx.functionCall().getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: try to apply length() method on other cn.edu.sjtu.songyuke.mental.type.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (ctx.functionCall().functionName.getText().equals("substring")) {
                // it may be string.substring(left, right)
                //     both left and right should be int.
                if (thisExpression.primaryExpression.returnType instanceof MxString) {
                    thisExpression.memberExpression = new CallSubString();
                    thisExpression.memberExpression.parent = thisExpression;
                    thisExpression.returnType = SymbolTable.MENTAL_M_STRING;
                    if (functionCall.parameters.expressions.size() == 2) {
                        ((CallSubString) thisExpression.memberExpression).leftExpression = functionCall.parameters.expressions.get(0);
                        ((CallSubString) thisExpression.memberExpression).rightExpression = functionCall.parameters.expressions.get(1);
                        if (!((CallSubString) thisExpression.memberExpression).leftExpression.returnType.equals(SymbolTable.mentalInt)) {
                            System.err.println("fatal: the cn.edu.sjtu.songyuke.mental.type of the first parameter of string.substring is not int.\n\t" + ctx.functionCall().getText());
                        }
                        if (!((CallSubString) thisExpression.memberExpression).rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                            System.err.println("fatal: the cn.edu.sjtu.songyuke.mental.type of the second parameter of string.substring is not int.\n\t" + ctx.functionCall().getText());
                        }
                    } else {
                        System.err.println("fatal: the number of parameters of string.substring is wrong.\n\t" + ctx.functionCall().getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: try to apply substring(int, int) method on other cn.edu.sjtu.songyuke.mental.type.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (ctx.functionCall().functionName.getText().equals("ord")) {
                // it may be a string.ord(index)
                //     index should be int. 
                if (thisExpression.primaryExpression.returnType instanceof MxString) {
                    thisExpression.memberExpression = new CallOrd();
                    thisExpression.memberExpression.parent = thisExpression;
                    thisExpression.returnType = SymbolTable.mentalInt;
                    if (functionCall.parameters.expressions.size() == 1) {
                        ((CallOrd) thisExpression.memberExpression).childExpression = functionCall.parameters.expressions.get(0);
                        if (!((CallOrd) thisExpression.memberExpression).childExpression.returnType.equals(SymbolTable.mentalInt)) {
                            System.err.println("fatal: the cn.edu.sjtu.songyuke.mental.type of the parameter of string.ord is not int.\n\t" + ctx.functionCall().getText());
                        }
                    } else {
                        System.err.println("fatal: the number of parameters of string.ord is wrong.\n\t" + ctx.functionCall().getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: try to apply ord(int) method on other cn.edu.sjtu.songyuke.mental.type except string.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (ctx.functionCall().functionName.getText().equals("parseInt")) {
                // it may be a string.parseInt()
                if (thisExpression.primaryExpression.returnType instanceof MxString) {
                    thisExpression.memberExpression = new CallParseInt();
                    thisExpression.memberExpression.parent = thisExpression;
                    thisExpression.returnType = SymbolTable.mentalInt;
                    if (functionCall.parameters.expressions.size() != 0) {
                        System.err.println("fatal: the number of parameters of string.parseInt is wrong.\n\t" + ctx.functionCall().getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: try to apply parseInt() method on other cn.edu.sjtu.songyuke.mental.type except string.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (ctx.functionCall().functionName.getText().equals("size")) {
                // it may be a array.size()
                if (thisExpression.primaryExpression.returnType instanceof Array) {
                    thisExpression.memberExpression = new CallSize();
                    thisExpression.memberExpression.parent = thisExpression;
                    thisExpression.returnType = SymbolTable.mentalInt;
                    if (functionCall.parameters.expressions.size() != 0) {
                        System.err.println("fatal: the number of parameters of array.size is wrong.\n\t" + ctx.functionCall().getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: try to apply size() method on other cn.edu.sjtu.songyuke.mental.type except array.\n\t" + ctx.getText());
                    this.existError = true;
                }
            }
        } else {
            // it is a member access.
            thisExpression.memberExpression = null;
            thisExpression.memberName = ctx.Identifier().getText();
            if (thisExpression.primaryExpression.returnType instanceof Class) {
                // the primary expression is not a class.
                Class thisClass = (Class) thisExpression.primaryExpression.returnType;
                if (thisClass.classComponents.get(thisExpression.memberName) != null) {
                    thisExpression.leftValue = thisExpression.primaryExpression.leftValue;
                    thisExpression.returnType = thisClass.classComponents.get(thisExpression.memberName).memberType;
                } else {
                    System.err.println("fatal: the class `" + thisClass.className
                            + "` does not have the member `" + thisExpression.memberName + "`."
                    );
                    this.existError = true;
                }
            } else {
                System.err.println("fatal: " + ctx.expression().getText() + "is not a class. ");
                this.existError = true;
            }
        }
    }
	/**
	 * process function call.
	 */
	@Override public void enterFUNCTION_CALL(MentalParser.FUNCTION_CALLContext ctx) {
        FunctionCall functionCall = new FunctionCall();
        this.tree.put(ctx, functionCall);
        java.lang.String functionName = ctx.functionCall().functionName.getText();
        functionCall.functionName = functionName;
        if (functionName.equals("print")
                || functionName.equals("println")
                || functionName.equals("getInt")
                || functionName.equals("getString")
                || functionName.equals("toString")) {
            // internal functions are not in symbol table.
            // it will produce an error if find them in the symbol table.
            return ;
        }
        // get the message of the function
        //     function name, parameters' cn.edu.sjtu.songyuke.mental.type, set the return value of this call.
        SymbolBase symbol = this.curSymbolTable.getSymbol(functionName);
        if (symbol != null) {
             if (symbol instanceof SymbolFunction) {
                 functionCall.functionHead = (SymbolFunction) symbol;
                 functionCall.returnType = functionCall.functionHead.returnType;
             } else {
                 System.err.println("fatal: the symbol `" + functionName + "` is not a function.");
                 this.existError = true;
             }
        } else {
            System.err.println("fatal: the symbol `" + functionName + "` is not defined.");
            this.existError = true;
        }
    }
	@Override public void exitFUNCTION_CALL(MentalParser.FUNCTION_CALLContext ctx) {
        FunctionCall thisCall = (FunctionCall) this.tree.get(ctx);
        java.lang.String functionName = thisCall.functionName;
        if (ctx.functionCall().expressionList() != null) {
            thisCall.parameters = (ExpressionList) this.tree.get(ctx.functionCall().expressionList());
        } else {
            thisCall.parameters = new ExpressionList();
        }
        if (functionName.equals("print")
                || functionName.equals("println")
                || functionName.equals("getInt")
                || functionName.equals("getString")
                || functionName.equals("toString")) {
            // it is an internal function call
            //     and the node of this function call will be replaced by an internal function call node.
            if (functionName.equals("print")) {
                // may be print(str)
                if (thisCall.parameters.expressions.size() == 1) {
                    if (thisCall.parameters.expressions.get(0).returnType.equals(SymbolTable.MENTAL_M_STRING)) {
                        CallPrint callPrint = new CallPrint();
                        callPrint.parameter = thisCall.parameters.expressions.get(0);
                        callPrint.parameter.parent = callPrint;
                        this.tree.replace(ctx, callPrint);
                    } else {
                        System.err.println("fatal: print only accept string as parameter.\n\t" + ctx.getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: the number of parameters of print(str) is wrong.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (functionName.equals("println")) {
                // may be println(str)
                if (thisCall.parameters.expressions.size() == 1) {
                    if (thisCall.parameters.expressions.get(0).returnType.equals(SymbolTable.MENTAL_M_STRING)) {
                        CallPrintln callPrintln = new CallPrintln();
                        callPrintln.parameter = thisCall.parameters.expressions.get(0);
                        callPrintln.parameter.parent = callPrintln;
                        this.tree.replace(ctx, callPrintln);
                    } else {
                        System.err.println("fatal: println only accept string as parameter.\n\t" + ctx.getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: the number of parameters of println(str) is wrong.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (functionName.equals("getInt")) {
                // may be getInt()
                if (thisCall.parameters.expressions.size() == 0) {
                    CallGetInt callGetInt = new CallGetInt();
                    this.tree.replace(ctx, callGetInt);
                } else {
                    System.err.println("fatal: getInt() accepts no parameter.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (functionName.equals("getString")) {
                // may be getString()
                if (thisCall.parameters.expressions.size() == 0) {
                    CallGetString callGetString = new CallGetString();
                    this.tree.replace(ctx, callGetString);
                } else {
                    System.err.println("fatal: getString() accepts no parameter.\n\t" + ctx.getText());
                    this.existError = true;
                }
            } else if (functionName.equals("toString")) {
                // may be toString(int)
                if (thisCall.parameters.expressions.size() == 1) {
                    if (thisCall.parameters.expressions.get(0).returnType.equals(SymbolTable.mentalInt)) {
                        CallToString callToString = new CallToString();
                        callToString.childExpression = thisCall.parameters.expressions.get(0);
                        callToString.childExpression.parent = callToString;
                        this.tree.replace(ctx, callToString);
                    } else {
                        System.err.println("fatal: toString only accepts int as parameter.\n\t" + ctx.getText());
                        this.existError = true;
                    }
                } else {
                    System.err.println("fatal: the number of parameters of toString(int) is wrong.\n\t" + ctx.getText());
                    this.existError = true;
                }
            }
            return ;
        }
        thisCall.parameters.parent = thisCall;
        if (thisCall.functionHead == null) {
            return;
        }
        if (thisCall.functionHead.parameterType != null) {
            if (thisCall.functionHead.parameterType.size() == thisCall.parameters.expressions.size()) {
                for (int i = 0, count = thisCall.functionHead.parameterType.size(); i < count; ++i) {
                    if (!thisCall.functionHead.parameterType.get(i).equals(thisCall.parameters.expressions.get(i).returnType)) {
                        System.err.println("fatal: the cn.edu.sjtu.songyuke.mental.type of " + Integer.toString(i) + "-th parameter mismatched. "
                                + "\n\t expected " + thisCall.functionHead.parameterType.get(i).toString()
                                + "\n\t occurs " + thisCall.parameters.expressions.get(i).returnType.toString()
                        );
                        this.existError = true;
                    }
                }
            } else {
                System.err.println("fatal: the function call of `" + functionName
                        + "` has a wrong number of parameters.\n"
                        + "\t expected " + Integer.toString(thisCall.functionHead.parameterType.size())
                        + "\n\t occurs " + Integer.toString(thisCall.parameters.expressions.size())
                        + "\n\t\t with context: " + ctx.getText()
                );
                this.existError = true;
            }
        }
    }
    /**
     * x = y
     * x, y should be the same cn.edu.sjtu.songyuke.mental.type.
     */
    @Override public void enterASSIGN_EXPRESSION(MentalParser.ASSIGN_EXPRESSIONContext ctx) {
        AssignExpression assignExpression = new AssignExpression();
        this.tree.put(ctx, assignExpression);
    }
    @Override public void exitASSIGN_EXPRESSION(MentalParser.ASSIGN_EXPRESSIONContext ctx) {
        AssignExpression thisExpression = (AssignExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.rightExpression.parent = thisExpression;
        if (!thisExpression.leftExpression.leftValue) {
            System.err.println("fatal: the left side of operator= cannot be a left-value.\n\t" + ctx.getText());
            this.existError = true;
        } else {
            if (!thisExpression.leftExpression.returnType.equals(thisExpression.rightExpression.returnType)) {
                System.err.println("fatal: the types of both sides of operator= are different.\n"
                        + "\t left side: " + thisExpression.leftExpression.returnType + "\n"
                        + "\tright side: " + thisExpression.rightExpression.returnType
                );
                this.existError = true;
            } else {
                thisExpression.returnType = thisExpression.leftExpression.returnType;
            }
        }
    }
	/**
	 * new cn.edu.sjtu.songyuke.mental.type ([int])* ([])*
	 */
	@Override public void enterCREATION_EXPRESSION(MentalParser.CREATION_EXPRESSIONContext ctx) {
        CreationExpression creationExpression = new CreationExpression();
		SymbolType type = (SymbolType) this.curSymbolTable.getSymbol(ctx.typeName().getText());
		if ((ctx.expression() == null || ctx.expression().size() == 0)
                && (ctx.array() == null || ctx.array().size() == 0)) {
			creationExpression.returnType = type.type;
            creationExpression.baseType = type.type;
			creationExpression.expressionList = null;

            if (creationExpression.returnType.equals(SymbolTable.mentalInt)
                    || creationExpression.returnType.equals(SymbolTable.mentalBool)
                    || creationExpression.returnType.equals(SymbolTable.MENTAL_M_STRING)
                    ) {
                System.err.println("fatal: new a object of basic cn.edu.sjtu.songyuke.mental.type is unaccepted.\n\t" + ctx.getText());
                this.existError = true;
            }
		} else {
			creationExpression.returnType = new Array();
            creationExpression.baseType = type.type;
            creationExpression.resultDim = ctx.expression().size() + ctx.array().size();
            creationExpression.determinedDim = ctx.expression().size();
			((Array) creationExpression.returnType).arrayType = type.type;
			((Array) creationExpression.returnType).arrayDim = creationExpression.resultDim;
            if (creationExpression.determinedDim == 0) {
                System.err.println("warning: this new expression does nothing.\n\t" + ctx.toStringTree(new MentalParser(null)));
            }
		}
        this.tree.put(ctx, creationExpression);
    }
	@Override public void exitCREATION_EXPRESSION(MentalParser.CREATION_EXPRESSIONContext ctx) {
        CreationExpression thisExpression = (CreationExpression) this.tree.get(ctx);
		if (ctx.expression() != null) {
			for (int i = 0, count = ctx.expression().size(); i < count; ++i) {
				Expression childExpression = (Expression) this.tree.get(ctx.expression(i));
				childExpression.parent = thisExpression;
				if (!childExpression.returnType.equals(SymbolTable.mentalInt)) {
                    System.err.println("fatal: new an array with no-int size.\n\t" + ctx.expression(i).getText());
                    this.existError = true;
                } else {
                    if (childExpression instanceof IntLiteral) {
                        if (((IntLiteral) childExpression).literalContext <= 0) {
                            System.err.println("fatal: new an array with negative size.\n\t" + ctx.expression(i).getText());
                            this.existError = true;
                        }
                    }
                }
				thisExpression.expressionList.add(childExpression);
			}
		}
    }
    /**
     * x++, x--
     * x should be a left-value, and int
     */
    @Override public void enterSUFFIX_INC_DEC_EXPRESSION(MentalParser.SUFFIX_INC_DEC_EXPRESSIONContext ctx) {
        SuffixExpression suffixExpression = new SuffixExpression();
        suffixExpression.op = ctx.op.getType();
        this.tree.put(ctx, suffixExpression);
    }
    @Override public void exitSUFFIX_INC_DEC_EXPRESSION(MentalParser.SUFFIX_INC_DEC_EXPRESSIONContext ctx) {
        SuffixExpression thisExpression = (SuffixExpression) this.tree.get(ctx);
        thisExpression.childExpression = (Expression) this.tree.get(ctx.expression());
        thisExpression.childExpression.parent = thisExpression;
        thisExpression.returnType = thisExpression.childExpression.returnType;
        if (!thisExpression.childExpression.leftValue) {
            System.err.println("fatal: try to apply suffix (inc/dec)reasement on a no-leftvalue.\n\t" + ctx.expression().getText());
            this.existError = true;
        } else {
            if (!thisExpression.childExpression.returnType.equals(SymbolTable.mentalInt)) {
                System.err.println("fatal: try to apply suffix (inc/dec)reasement on a no-digit.\n\t" + ctx.expression().getText());
                this.existError = true;
            }
        }
    }
    /**
     * ++x, --x
     * x should be left-value and int.
     */
    @Override public void enterPREFIX_INC_DEC_EXPRESSION(MentalParser.PREFIX_INC_DEC_EXPRESSIONContext ctx) {
        PrefixExpression prefixExpression = new PrefixExpression();
        prefixExpression.op = ctx.op.getType();
        this.tree.put(ctx, prefixExpression);
    }
    @Override public void exitPREFIX_INC_DEC_EXPRESSION(MentalParser.PREFIX_INC_DEC_EXPRESSIONContext ctx) {
        PrefixExpression thisExpression = (PrefixExpression) this.tree.get(ctx);
        thisExpression.childExpression = (Expression) this.tree.get(ctx.expression());
        thisExpression.childExpression.parent = thisExpression;
        if (!thisExpression.childExpression.leftValue) {
            System.err.println("fatal: try to apply a prefix inc/dec on a non-left-value.\n\t" + ctx.getText());
            this.existError = true;
        } else {
            if (!thisExpression.childExpression.returnType.equals(SymbolTable.mentalInt)) {
                System.err.println("fatal: try to apply a prefix inc/dec on a no-integer.\n\t" + ctx.getText());
                this.existError = true;
            }
        }
    }
    /**
     * +x, -x
     * x should be int.
     */
    @Override public void enterUNRAY_PLUS_MINUS_EXPRESSION(MentalParser.UNRAY_PLUS_MINUS_EXPRESSIONContext ctx) {
        UnaryAdditiveExpression unaryAdditiveExpression = new UnaryAdditiveExpression();
        unaryAdditiveExpression.op = ctx.op.getType();
        this.tree.put(ctx, unaryAdditiveExpression);
    }
    @Override public void exitUNRAY_PLUS_MINUS_EXPRESSION(MentalParser.UNRAY_PLUS_MINUS_EXPRESSIONContext ctx) {
        UnaryAdditiveExpression thisExpression = (UnaryAdditiveExpression) this.tree.get(ctx);
        Expression childExpression = (Expression) this.tree.get(ctx.expression());
        childExpression.parent = thisExpression;
        thisExpression.childExpression = childExpression;
        if (!childExpression.returnType.equals(SymbolTable.mentalInt)) {
            System.err.println("fatal: try to apply unary plus/minus on a no-int cn.edu.sjtu.songyuke.mental.type.\n\t" + ctx.getText());
            this.existError = true;
        } else {
            if (childExpression instanceof IntLiteral) {
                if (thisExpression.op == UnaryAdditiveExpression.ADD) {
                    IntLiteral newIntLiteral = new IntLiteral();
                    newIntLiteral.literalContext = +((IntLiteral) childExpression).literalContext;
                    this.tree.replace(ctx, newIntLiteral);
                } else if (thisExpression.op == UnaryAdditiveExpression.SUB) {
                    IntLiteral newIntLiteral = new IntLiteral();
                    newIntLiteral.literalContext = -((IntLiteral) childExpression).literalContext;
                    this.tree.replace(ctx, newIntLiteral);
                }
            } else if (childExpression instanceof UnaryAdditiveExpression) {
                if (((UnaryAdditiveExpression) childExpression).op == UnaryAdditiveExpression.ADD) {
                    thisExpression.childExpression = ((UnaryAdditiveExpression) childExpression).childExpression;
                } else if (((UnaryAdditiveExpression) childExpression).op == UnaryAdditiveExpression.SUB) {
                    thisExpression.childExpression = ((UnaryAdditiveExpression) childExpression).childExpression;
                    if (thisExpression.op == UnaryAdditiveExpression.SUB) {
                        thisExpression.op = UnaryAdditiveExpression.ADD;
                    } else {
                        thisExpression.op = UnaryAdditiveExpression.SUB;
                    }
                }
            }
        }
    }
    /**
     * x + y, x - y, str1 + str2
     */
    @Override public void enterADDITIVE_EXPRESSION(MentalParser.ADDITIVE_EXPRESSIONContext ctx) {
        AdditiveExpression additiveExpression = new AdditiveExpression();
        additiveExpression.op = ctx.op.getType();
        this.tree.put(ctx, additiveExpression);
    }
    @Override public void exitADDITIVE_EXPRESSION(MentalParser.ADDITIVE_EXPRESSIONContext ctx) {
        AdditiveExpression thisExpression = (AdditiveExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(thisExpression.rightExpression.returnType)) {
            if (thisExpression.op == AdditiveExpression.ADD) {
                if (thisExpression.leftExpression.returnType.equals(SymbolTable.MENTAL_M_STRING)) {
                    if (thisExpression.rightExpression.returnType.equals(SymbolTable.MENTAL_M_STRING)) {
                        // string + string
                        thisExpression.returnType = SymbolTable.MENTAL_M_STRING;
                        if (thisExpression.leftExpression instanceof StringLiteral) {
                            if (thisExpression.rightExpression instanceof StringLiteral) {
                                StringLiteral replaceNode = new StringLiteral();
                                replaceNode.literalContext =
                                        ((StringLiteral) thisExpression.leftExpression).literalContext.substring(0, ((StringLiteral) thisExpression.leftExpression).literalContext.length() - 1)
                                                + ((StringLiteral) thisExpression.rightExpression).literalContext.substring(1, ((StringLiteral) thisExpression.rightExpression).literalContext.length());
                                this.tree.replace(ctx, replaceNode);
                            }
                        }
                        return;
                    }
                } else {
                    if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
                        if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                            // int + int
                            thisExpression.returnType = SymbolTable.mentalInt;
                            if (thisExpression.leftExpression instanceof IntLiteral) {
                                if (thisExpression.rightExpression instanceof IntLiteral) {
                                    IntLiteral replaceNode = new IntLiteral();
                                    replaceNode.literalContext =
                                            ((IntLiteral) thisExpression.leftExpression).literalContext
                                                    + ((IntLiteral) thisExpression.rightExpression).literalContext;
                                    this.tree.replace(ctx, replaceNode);
                                }
                            }
                            return;
                        }
                    }
                }
            } else {
                if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
                    if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                        // int - int
                        thisExpression.returnType = SymbolTable.mentalInt;
                        if (thisExpression.leftExpression instanceof IntLiteral) {
                            if (thisExpression.rightExpression instanceof IntLiteral) {
                                IntLiteral replaceNode = new IntLiteral();
                                replaceNode.literalContext =
                                        ((IntLiteral) thisExpression.leftExpression).literalContext
                                                - ((IntLiteral) thisExpression.rightExpression).literalContext;
                                this.tree.replace(ctx, replaceNode);
                            }
                        }
                        return;
                    }
                }
            }
        }
        System.err.println("fatal: the types of additive expression cannot accept. \n\t" + ctx.getText());
        this.existError = true;
    }
    /**
     * x * y, x / y, x % y
     * x, y should be int.
     */
    @Override public void enterMULTIPLY_DIVIDE_EXPRESSION(MentalParser.MULTIPLY_DIVIDE_EXPRESSIONContext ctx) {
        MulDivExpression mulDivExpression = new MulDivExpression();
        mulDivExpression.op = ctx.op.getType();
        this.tree.put(ctx, mulDivExpression);
    }
    @Override public void exitMULTIPLY_DIVIDE_EXPRESSION(MentalParser.MULTIPLY_DIVIDE_EXPRESSIONContext ctx) {
        MulDivExpression thisExpression = (MulDivExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
            if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                if (thisExpression.leftExpression instanceof IntLiteral) {
                    if (thisExpression.rightExpression instanceof IntLiteral) {
                        IntLiteral replaceNode = new IntLiteral();
                        if (thisExpression.op == MulDivExpression.MUL) {
                            replaceNode.literalContext =
                                    ((IntLiteral) thisExpression.leftExpression).literalContext
                                            * ((IntLiteral) thisExpression.rightExpression).literalContext;
                        } else if (thisExpression.op == MulDivExpression.DIV) {
                            replaceNode.literalContext =
                                    ((IntLiteral) thisExpression.leftExpression).literalContext
                                            / ((IntLiteral) thisExpression.rightExpression).literalContext;
                        } else if (thisExpression.op == MulDivExpression.MOD) {
                            replaceNode.literalContext =
                                    ((IntLiteral) thisExpression.leftExpression).literalContext
                                            % ((IntLiteral) thisExpression.rightExpression).literalContext;
                        }
                        this.tree.replace(ctx, replaceNode);
                    }
                }
                return;
            }
        }
        System.err.println("fatal: the types of multiply/divide expression cannot accept.\n\t" + ctx.getText());
        this.existError = true;
    }
    /**
     * x << y, x >> y
     * x, y should be int.
     */
    @Override public void enterBIT_SHIFT_EXPRESSION(MentalParser.BIT_SHIFT_EXPRESSIONContext ctx) {
        BitShiftExpression bitShiftExpression = new BitShiftExpression();
        bitShiftExpression.op = ctx.op.getType();
        this.tree.put(ctx, bitShiftExpression);
    }
    @Override public void exitBIT_SHIFT_EXPRESSION(MentalParser.BIT_SHIFT_EXPRESSIONContext ctx) {
        BitShiftExpression thisExpression = (BitShiftExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
            if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                if (thisExpression.leftExpression instanceof IntLiteral) {
                    if (thisExpression.rightExpression instanceof IntLiteral) {
                        IntLiteral replaceNode = new IntLiteral();
                        if (thisExpression.op == BitShiftExpression.LEFT_SHIFT) {
                            replaceNode.literalContext =
                                    ((IntLiteral) thisExpression.leftExpression).literalContext
                                            << ((IntLiteral) thisExpression.rightExpression).literalContext;
                        } else {
                            replaceNode.literalContext =
                                    ((IntLiteral) thisExpression.leftExpression).literalContext
                                            >> ((IntLiteral) thisExpression.rightExpression).literalContext;
                        }
                        this.tree.replace(ctx, replaceNode);
                    }
                }
                return;
            }
        }
        System.err.println("fatal: the types of bit-shift expression cannnot accept.\n\t" + ctx.getText());
        this.existError = true;
    }
    /**
     * x & y
     * x, y should be int
     */
    @Override public void enterBIT_AND_EXPRESSION(MentalParser.BIT_AND_EXPRESSIONContext ctx) {
        BitAndExpression bitAndExpression = new BitAndExpression();
        this.tree.put(ctx, bitAndExpression);
    }
    @Override public void exitBIT_AND_EXPRESSION(MentalParser.BIT_AND_EXPRESSIONContext ctx) {
        BitAndExpression thisExpression = (BitAndExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
            if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                if (thisExpression.leftExpression instanceof IntLiteral) {
                    if (thisExpression.rightExpression instanceof IntLiteral) {
                        IntLiteral replaceNode = new IntLiteral();
                        replaceNode.literalContext =
                                ((IntLiteral) thisExpression.leftExpression).literalContext
                                        & ((IntLiteral) thisExpression.rightExpression).literalContext;
                        this.tree.replace(ctx, replaceNode);
                    }
                }
                return ;
            }
        }
        System.err.println("fatal: the types of sides of bit-and expression cannot accept.\n\t" + ctx.getText());
        this.existError = true;
    }
    /**
     * x | y
     * x, y should be int.
     */
    @Override public void enterBIT_OR_EXPRESSION(MentalParser.BIT_OR_EXPRESSIONContext ctx) {
        BitOrExpression bitOrExpression = new BitOrExpression();
        this.tree.put(ctx, bitOrExpression);
    }
    @Override public void exitBIT_OR_EXPRESSION(MentalParser.BIT_OR_EXPRESSIONContext ctx) {
        BitOrExpression thisExpression = (BitOrExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
            if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                if (thisExpression.leftExpression instanceof IntLiteral) {
                    if (thisExpression.rightExpression instanceof IntLiteral) {
                        IntLiteral replaceNode = new IntLiteral();
                        replaceNode.literalContext =
                                ((IntLiteral) thisExpression.leftExpression).literalContext
                                        | ((IntLiteral) thisExpression.rightExpression).literalContext;
                        this.tree.replace(ctx, replaceNode);
                    }
                }
                return;
            }
        }
        System.err.println("fatal: the types of bit-or expression cannot accept.\n\t" + ctx.getText());
        this.existError = true;
    }
    /**
     * a ^ b
     * both a, b should be int.
     */
    @Override public void enterBIT_XOR_EXPRESSION(MentalParser.BIT_XOR_EXPRESSIONContext ctx) {
        BitXorExpression bitXorExpression = new BitXorExpression();
        this.tree.put(ctx, bitXorExpression);
    }
    @Override public void exitBIT_XOR_EXPRESSION(MentalParser.BIT_XOR_EXPRESSIONContext ctx) {
        BitXorExpression thisExpression = (BitXorExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
            if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                if (thisExpression.leftExpression instanceof IntLiteral) {
                    if (thisExpression.rightExpression instanceof IntLiteral) {
                        IntLiteral replaceNode = new IntLiteral();
                        replaceNode.literalContext =
                                ((IntLiteral) thisExpression.leftExpression).literalContext
                                        ^ ((IntLiteral) thisExpression.rightExpression).literalContext;
                        this.tree.replace(ctx, replaceNode);
                    }
                }
                return;
            }
        }
        System.err.println("fatal: the types of bit-xor expression cannot accept.\n\t" + ctx.getText());
        this.existError = true;
    }
	/**
	 * ~x
     * x should be a int
	 */
	@Override public void enterBIT_NOT_EXPRESSION(MentalParser.BIT_NOT_EXPRESSIONContext ctx) {
        BitNotExpression expression = new BitNotExpression();
        this.tree.put(ctx, expression);
    }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBIT_NOT_EXPRESSION(MentalParser.BIT_NOT_EXPRESSIONContext ctx) {
        BitNotExpression thisExpression = (BitNotExpression) this.tree.get(ctx);
        thisExpression.childExpression = (Expression) this.tree.get(ctx.expression());
        thisExpression.childExpression.parent = thisExpression;
        if (!thisExpression.childExpression.returnType.equals(SymbolTable.mentalInt)) {
            System.err.println("fatal: try to apply bit-not-operator on a no-integer item.\n\t" + ctx.getText());
            this.existError = true;
            return ;
        }
        if (thisExpression.childExpression instanceof IntLiteral) {
            IntLiteral replaceNode = new IntLiteral();
            replaceNode.literalContext = ~((IntLiteral) thisExpression.childExpression).literalContext;
            this.tree.replace(ctx, replaceNode);
        }
    }
	/**
	 * a < b, a <= b, a > b, a >= b
     *  a, b may both be int or string.
	 */
	@Override public void enterRELATION_EXPRESSION(MentalParser.RELATION_EXPRESSIONContext ctx) {
        RelationExpression relationExpression = new RelationExpression();
        if (ctx.op.getType() == MentalParser.LESS) {
            relationExpression.op = RelationExpression.LESS;
        } else if (ctx.op.getType() == MentalParser.LESS_EQUAL) {
            relationExpression.op = RelationExpression.LESS_EQ;
        } else if (ctx.op.getType() == MentalParser.GREATER) {
            relationExpression.op = RelationExpression.GREATER;
        } else {
            relationExpression.op = RelationExpression.GREATER_EQ;
        }
        this.tree.put(ctx, relationExpression);
    }
	@Override public void exitRELATION_EXPRESSION(MentalParser.RELATION_EXPRESSIONContext ctx) {
        RelationExpression thisExpression = (RelationExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(SymbolTable.mentalInt)) {
            if (thisExpression.rightExpression.returnType.equals(SymbolTable.mentalInt)) {
                return;
            }
        } else if (thisExpression.leftExpression.returnType.equals(SymbolTable.MENTAL_M_STRING)) {
            if (thisExpression.rightExpression.returnType.equals(SymbolTable.MENTAL_M_STRING)) {
                return;
            }
        }
        System.err.println("fatal: the types of relation expression cannot accept. " + ctx.getText());
        this.existError = true;
    }
	/**
	 * a == b, a != b
     * a, b should be the same cn.edu.sjtu.songyuke.mental.type.
	 */
	@Override public void enterEQUALITY_EXPRESSION(MentalParser.EQUALITY_EXPRESSIONContext ctx) {
        EqualityExpression equalityExpression = new EqualityExpression();
        equalityExpression.op = ctx.op.getType();
        this.tree.put(ctx, equalityExpression);
    }
	@Override public void exitEQUALITY_EXPRESSION(MentalParser.EQUALITY_EXPRESSIONContext ctx) {
        EqualityExpression thisExpression = (EqualityExpression) this.tree.get(ctx);
        thisExpression.leftExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.rightExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.leftExpression.parent = thisExpression;
        thisExpression.rightExpression.parent = thisExpression;
        if (thisExpression.leftExpression.returnType.equals(thisExpression.rightExpression.returnType)) {
            return;
        }
        System.err.println(thisExpression.leftExpression.toPrintString());
        System.err.println(thisExpression.rightExpression.toPrintString());
        System.err.println("fatal: the types of equality expression cannot accept.\n\t" + ctx.getText());
        this.existError = true;
    }
	/**
	 * use a variable such as "x", "y" in "x = y"
	 */
	@Override public void enterIDENTIFIER(MentalParser.IDENTIFIERContext ctx) {
        Identifier identifier = new Identifier();
        identifier.name = ctx.Identifier().getText();
        if (this.curSymbolTable.getSymbol(identifier.name) == null) {
            System.err.println("fatal: undefined identifier.\n\t" + ctx.Identifier());
            this.existError = true;
        } else {
            SymbolBase base = this.curSymbolTable.getSymbol(identifier.name);
            if (base instanceof SymbolVariable) {
                identifier.returnType = ((SymbolVariable) base).variable.variableType;
                identifier.leftValue = true;
            } else if (base instanceof SymbolFunction) {
                identifier.returnType = new Function();
                ((Function) identifier.returnType).functionHead = (SymbolFunction) base;
            } else {
                System.err.println("fatal: the identifier is a cn.edu.sjtu.songyuke.mental.type.\n\t" + identifier.name);
                this.existError = true;
            }
        }
        this.tree.put(ctx, identifier);
    }
	@Override public void exitIDENTIFIER(MentalParser.IDENTIFIERContext ctx) {
        Identifier identifier = (Identifier) this.tree.get(ctx);
        identifier.variable = ((SymbolVariable) this.curSymbolTable.getSymbol(identifier.name)).variable;
    }
	/**
	 * null
	 */
	@Override public void enterNULL(MentalParser.NULLContext ctx) {
		this.tree.put(ctx, new NullConstant());
	}
	@Override public void exitNULL(MentalParser.NULLContext ctx) { }
	/**
	 * x && y
     * x, y should be boolean.
	 */
	@Override public void enterLOGICAL_AND_EXPRESSION(MentalParser.LOGICAL_AND_EXPRESSIONContext ctx) {
        SuperLogicalAndExpression superLogicalAndExpression = new SuperLogicalAndExpression();
        this.tree.put(ctx, superLogicalAndExpression);
    }
	@Override public void exitLOGICAL_AND_EXPRESSION(MentalParser.LOGICAL_AND_EXPRESSIONContext ctx) {
        SuperLogicalAndExpression thisExpression = (SuperLogicalAndExpression) this.tree.get(ctx);
        if (ctx.expression(0) instanceof MentalParser.LOGICAL_AND_EXPRESSIONContext) {
            SuperLogicalAndExpression leftExpression = (SuperLogicalAndExpression) this.tree.get(ctx.expression(0));
            thisExpression.expressions.addAll(leftExpression.expressions);
        } else {
            thisExpression.expressions.add((Expression) this.tree.get(ctx.expression(0)));
        }
        if (ctx.expression(1) instanceof MentalParser.LOGICAL_AND_EXPRESSIONContext) {
            SuperLogicalAndExpression rightExpression = (SuperLogicalAndExpression) this.tree.get(ctx.expression(1));
            thisExpression.expressions.addAll(rightExpression.expressions);
        } else {
            thisExpression.expressions.add((Expression) this.tree.get(ctx.expression(1)));
        }

        for (Expression expression : thisExpression.expressions) {
            expression.parent = thisExpression;
            if (!expression.returnType.equals(SymbolTable.mentalBool)) {
                System.err.println("fatal: the types of logical-and expression cannot accept.\n\t" + ctx.getText());
                this.existError = true;
                return;
            }
        }
    }
    /**
     * x || y
     * x, y should be boolean.
     */
    @Override public void enterLOGICAL_OR_EXPRESSION(MentalParser.LOGICAL_OR_EXPRESSIONContext ctx) {
        SuperLogicalOrExpression superLogicalOrExpression = new SuperLogicalOrExpression();
        this.tree.put(ctx, superLogicalOrExpression);
    }
    @Override public void exitLOGICAL_OR_EXPRESSION(MentalParser.LOGICAL_OR_EXPRESSIONContext ctx) {
        SuperLogicalOrExpression thisExpression = (SuperLogicalOrExpression) this.tree.get(ctx);
        if (ctx.expression(0) instanceof MentalParser.LOGICAL_OR_EXPRESSIONContext) {
            SuperLogicalOrExpression leftExpression = (SuperLogicalOrExpression) this.tree.get(ctx.expression(0));
            thisExpression.expressions.addAll(leftExpression.expressions);
        } else {
            thisExpression.expressions.add((Expression) this.tree.get(ctx.expression(0)));
        }
        if (ctx.expression(1) instanceof MentalParser.LOGICAL_OR_EXPRESSIONContext) {
            SuperLogicalOrExpression rightExpression = (SuperLogicalOrExpression) this.tree.get(ctx.expression(1));
            thisExpression.expressions.addAll(rightExpression.expressions);
        } else {
            thisExpression.expressions.add((Expression) this.tree.get(ctx.expression(1)));
        }
        for (Expression expression : thisExpression.expressions) {
            expression.parent = thisExpression;
            if (!expression.returnType.equals(SymbolTable.mentalBool)) {
                System.err.println("fatal: the types of logical-or expression cannot accept.\n\t" + ctx.getText());
                this.existError = true;
                return;
            }
        }
    }
	/**
	 * x[i]
     * x should be a array, i should be a int.
	 */
	@Override public void enterARRAY_SUBSCRIPTING_EXPRESSION(MentalParser.ARRAY_SUBSCRIPTING_EXPRESSIONContext ctx) {
        ArraySubscriptingExpression arraySubscriptingExpression = new ArraySubscriptingExpression();
        this.tree.put(ctx, arraySubscriptingExpression);
    }
	@Override public void exitARRAY_SUBSCRIPTING_EXPRESSION(MentalParser.ARRAY_SUBSCRIPTING_EXPRESSIONContext ctx) {
        ArraySubscriptingExpression thisExpression = (ArraySubscriptingExpression) this.tree.get(ctx);
        thisExpression.primaryExpression = (Expression) this.tree.get(ctx.expression(0));
        thisExpression.positionExpression = (Expression) this.tree.get(ctx.expression(1));
        thisExpression.primaryExpression.parent = thisExpression;
        thisExpression.positionExpression.parent = thisExpression;
        if (thisExpression.primaryExpression.returnType instanceof Array) {
            if (thisExpression.positionExpression.returnType.equals(SymbolTable.mentalInt)) {
                if (((Array) thisExpression.primaryExpression.returnType).arrayDim > 1) {
                    thisExpression.returnType = new Array((Array) thisExpression.primaryExpression.returnType);
                    ((Array) thisExpression.returnType).arrayDim--;
                } else {
                    thisExpression.returnType = ((Array) thisExpression.primaryExpression.returnType).arrayType;
                }
                thisExpression.leftValue = thisExpression.primaryExpression.leftValue;
            } else {
                System.err.println("fatal: the result of position expression is not a integer.\n\t" + ctx.getText());
                this.existError = true;
            }
        } else {
            System.err.println("fatal: the result of primary expression is not a array.\n\t" + ctx.getText());
            this.existError = true;
        }
    }
	/**
	 * (expression)
	 */
	@Override public void enterSUBGROUP_EXPRESSION(MentalParser.SUBGROUP_EXPRESSIONContext ctx) {
        SubgroupExpression subgroupExpression = new SubgroupExpression();
        this.tree.put(ctx, subgroupExpression);
    }
	@Override public void exitSUBGROUP_EXPRESSION(MentalParser.SUBGROUP_EXPRESSIONContext ctx) {
        SubgroupExpression thisExpression = (SubgroupExpression) this.tree.get(ctx);
        thisExpression.childExpression = (Expression) this.tree.get(ctx.expression());
        this.tree.replace(ctx, thisExpression.childExpression);
    }
    /**
     * int literal
     * 12212, 124213, ...
     */
    @Override public void enterINT_LITERAL(MentalParser.INT_LITERALContext ctx) {
        IntLiteral intLiteral = new IntLiteral();
        intLiteral.literalContext = Integer.parseInt(ctx.getText());
        this.tree.put(ctx, intLiteral);
    }
    @Override public void exitINT_LITERAL(MentalParser.INT_LITERALContext ctx) { }
	/**
	 * string literal
	 */
	@Override public void enterSTRING_LITERAL(MentalParser.STRING_LITERALContext ctx) {
        StringLiteral stringLiteral = new StringLiteral();
        stringLiteral.literalContext = ctx.getText();
        this.tree.put(ctx, stringLiteral);
    }
	@Override public void exitSTRING_LITERAL(MentalParser.STRING_LITERALContext ctx) { }
    /**
     * true
     */
    @Override public void enterTRUE(MentalParser.TRUEContext ctx) {
        BoolConstant node = new BoolConstant();
        node.boolConstant = true;
        this.tree.put(ctx, node);
    }
    @Override public void exitTRUE(MentalParser.TRUEContext ctx) { }
	/**
	 * false
	 */
	@Override public void enterFALSE(MentalParser.FALSEContext ctx) {
        BoolConstant node = new BoolConstant();
        node.boolConstant = false;
        this.tree.put(ctx, node);
    }
	@Override public void exitFALSE(MentalParser.FALSEContext ctx) { }
    /**
     * function call
     *     only for member access expression
     *     FUNCTION_CALL expression will process in itself.
     */
    @Override public void enterFunctionCall(MentalParser.FunctionCallContext ctx) {
        if (ctx.parent instanceof MentalParser.FUNCTION_CALLContext) {
            return;
        }
        FunctionCall functionCall = new FunctionCall();
        functionCall.functionName = ctx.functionName.getText();
        this.tree.put(ctx, functionCall);
    }
    @Override public void exitFunctionCall(MentalParser.FunctionCallContext ctx) {
        if (ctx.parent instanceof MentalParser.FUNCTION_CALLContext) {
            return;
        }
        FunctionCall thisCall = (FunctionCall) this.tree.get(ctx);
        if (ctx.expressionList() != null) {
            thisCall.parameters = (ExpressionList) this.tree.get(ctx.expressionList());
        } else {
            thisCall.parameters = new ExpressionList();
        }
        thisCall.parameters.parent = thisCall;
    }
    /**
	 * which will transmit to a function call.
	 */
	@Override public void enterExpressionList(MentalParser.ExpressionListContext ctx) {
        ExpressionList expressionList = new ExpressionList();
        this.tree.put(ctx, expressionList);
    }
	@Override public void exitExpressionList(MentalParser.ExpressionListContext ctx) {
        ExpressionList thisList = (ExpressionList) this.tree.get(ctx);
        for (int i = 0, count = ctx.expression().size(); i < count; ++i) {
            thisList.expressions.add((Expression) this.tree.get(ctx.expression(i)));
            thisList.expressions.get(i).parent = thisList;
        }
    }
}