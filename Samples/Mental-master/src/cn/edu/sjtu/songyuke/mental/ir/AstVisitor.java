package cn.edu.sjtu.songyuke.mental.ir;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;
import cn.edu.sjtu.songyuke.mental.ast.declarations.FunctionDefinition;
import cn.edu.sjtu.songyuke.mental.ast.declarations.SingleVariableDeclaration;
import cn.edu.sjtu.songyuke.mental.ast.declarations.VariableDeclaration;
import cn.edu.sjtu.songyuke.mental.ast.expressions.*;
import cn.edu.sjtu.songyuke.mental.ast.ExpressionList;
import cn.edu.sjtu.songyuke.mental.ast.Program;
import cn.edu.sjtu.songyuke.mental.ast.statements.*;
import cn.edu.sjtu.songyuke.mental.ir.arithmetic.*;
import cn.edu.sjtu.songyuke.mental.ir.branch.*;
import cn.edu.sjtu.songyuke.mental.ir.data.*;
import cn.edu.sjtu.songyuke.mental.ir.label.*;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolTable;
import cn.edu.sjtu.songyuke.mental.type.*;
import cn.edu.sjtu.songyuke.mental.type.Class;
import cn.edu.sjtu.songyuke.mental.type.MxString;
import cn.edu.sjtu.songyuke.mental.type.Void;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by Songyu on 16/4/19.
 */
public class AstVisitor {
    public HashMap<BaseNode, Data> expressionResult;

    public LinkedList<Instruction> functionInstructionLists;
    public LinkedList<Integer> functionStackSize;
    public int currentStackSize;

    public LinkedList<Instruction> globalVariableInitialize;

    public HashMap<java.lang.String, DataStringLiteral> stringMap;
    public LinkedList<DataStringLiteral> stringLiterals;
    public DataStringLiteral literalNewline;

    public HashMap<Integer, DataValue> variableMap;
    public HashMap<Integer, IRLabelGlobalData> globalVariableMap;
    public LinkedList<DataValue> globalVariables;

    public IRLabel endFunction;
    public IRLabel continueLoop;
    public IRLabel endLoop;

    public AstVisitor() {
        this.expressionResult = new HashMap<>();

        this.functionInstructionLists = new LinkedList<>();
        this.functionStackSize = new LinkedList<>();
        this.globalVariableInitialize = new LinkedList<>();
        this.currentStackSize = 0;

        this.stringLiterals = new LinkedList<>();
        this.stringMap = new HashMap<>();
        this.literalNewline = new DataStringLiteral("\"\\n\"");
        this.literalNewline.globalDataLabel = new IRLabelGlobalData();
        this.stringMap.put("\"\\n\"", this.literalNewline);
        this.stringLiterals.add(this.literalNewline);

        this.globalVariableMap = new HashMap<>();
        this.variableMap = new HashMap<>();
        this.globalVariables = new LinkedList<>();

        this.endFunction = null;
        this.endLoop = null;
        this.continueLoop = null;
    }

    public LinkedList<Instruction> visitBase(BaseNode node) {
        // would never be called.
        return null;
    }

    public LinkedList<Instruction> visitProgram(Program astProgram) {
        for (BaseNode astDeclaration : astProgram.declarations) {
            if (astDeclaration instanceof VariableDeclaration) {
                LinkedList<Instruction> variableInstructions = astDeclaration.visit(this);
                if (variableInstructions.size() > 0) {
                    this.globalVariableInitialize.add(variableInstructions.getFirst());
                }
            }
        }
        for (BaseNode astDeclaration : astProgram.declarations) {
            if (astDeclaration instanceof FunctionDefinition) {
                this.currentStackSize = ((FunctionDefinition) astDeclaration).lastVariableID - ((FunctionDefinition) astDeclaration).firstVariableID + 1;
                LinkedList<Instruction> functionInstructions = astDeclaration.visit(this);
                if (functionInstructions.size() > 0) {
                    this.functionInstructionLists.add(functionInstructions.getFirst());
                    this.functionStackSize.add(this.currentStackSize);
                }
            }
        }
        return null;
    }

    public LinkedList<Instruction> visitIdentifier(Identifier astIdentifier) {
        DataValue variable;
        variable = this.variableMap.get(astIdentifier.variable.globalID);
        if (variable == null) {
            variable = new DataValue();
            variable.globalID = astIdentifier.variable.globalID;
            variable.stackShift = astIdentifier.variable.localID;
            variable.globalDataLabel = this.globalVariableMap.get(variable.globalID);
            this.variableMap.put(astIdentifier.variable.globalID, variable);
        }
        this.expressionResult.put(astIdentifier, variable);
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitIntLiteral(IntLiteral astIntLiteral) {
        DataIntLiteral dataIntLiteral;
        if (astIntLiteral.literalContext == 0) {
            dataIntLiteral = new ConstantZero();
        } else {
            dataIntLiteral = new DataIntLiteral(astIntLiteral.literalContext);
            dataIntLiteral.stackShift = this.currentStackSize++;
        }
        this.expressionResult.put(astIntLiteral, dataIntLiteral);
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitNullConstant(NullConstant astNullConstant) {
        DataIntLiteral irDataIntLiteral = new ConstantZero();
        this.expressionResult.put(astNullConstant, irDataIntLiteral);
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitBoolConstant(BoolConstant astBoolConstant) {
        DataIntLiteral irDataIntLiteral;
        if (astBoolConstant.boolConstant) {
            irDataIntLiteral = new DataIntLiteral();
            irDataIntLiteral.literal = DataIntLiteral.TRUE;
        } else {
            irDataIntLiteral = new ConstantZero();
        }
        this.expressionResult.put(astBoolConstant, irDataIntLiteral);
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitStringLiteral(StringLiteral astStringLiteral) {
        DataStringLiteral stringLiteral;
        if (this.stringMap.get(astStringLiteral.literalContext) == null) {
            stringLiteral = new DataStringLiteral(astStringLiteral.literalContext);
            this.stringLiterals.add(stringLiteral);
            stringLiteral.globalDataLabel = new IRLabelGlobalData();
            this.stringMap.put(astStringLiteral.literalContext, stringLiteral);
        } else {
            stringLiteral = this.stringMap.get(astStringLiteral.literalContext);
        }
        this.expressionResult.put(astStringLiteral, stringLiteral);
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitAssignExpression(AssignExpression astAssignExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astAssignExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astAssignExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astAssignExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astAssignExpression.rightExpression);
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        resultInstructions = rhsInstructions;
        if (resultInstructions.size() > 0) {
            if (lhsInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = lhsInstructions.getFirst();
            }
        }
        resultInstructions.addAll(lhsInstructions);
        Store irStore = new Store((DataValue) rhsRes, lhsRes);
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irStore;
        }
        resultInstructions.add(irStore);
        this.expressionResult.put(astAssignExpression, lhsRes);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitAdditiveExpression(AdditiveExpression astAdditiveExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astAdditiveExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astAdditiveExpression.rightExpression.visit(this);

        Data lhsRes = this.expressionResult.get(astAdditiveExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astAdditiveExpression.rightExpression);

        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        resultInstructions = lhsInstructions;
        if (resultInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }
        resultInstructions.addAll(rhsInstructions);
        if (astAdditiveExpression.returnType instanceof MxString) {
            Call irCall = new Call(new IRLabelFunction("____built_in_string_concatenate"));
            lhsRes.refCount++;
            rhsRes.refCount++;
            irCall.res.stackShift = this.currentStackSize++;
            irCall.parameters.add((DataValue) lhsRes);
            irCall.parameters.add((DataValue) rhsRes);
            this.expressionResult.put(astAdditiveExpression, irCall.res);
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irCall;
            }
            resultInstructions.add(irCall);
        } else if (astAdditiveExpression.returnType instanceof Int) {
            if (astAdditiveExpression.op == AdditiveExpression.ADD) {
                Add irAdd = new Add(lhsRes, rhsRes, new DataValue());
                irAdd.res.stackShift = this.currentStackSize++;
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irAdd;
                }
                resultInstructions.add(irAdd);
                this.expressionResult.put(astAdditiveExpression, irAdd.res);
            } else {
                Sub irSub = new Sub(lhsRes, rhsRes, new DataValue());
                irSub.res.stackShift = this.currentStackSize++;
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irSub;
                }
                resultInstructions.add(irSub);
                this.expressionResult.put(astAdditiveExpression, irSub.res);
            }
        } else {
            throw new RuntimeException();
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitUnaryAdditiveExpression(UnaryAdditiveExpression astUnaryAdditiveExpression) {
        LinkedList<Instruction> resultInstructions = astUnaryAdditiveExpression.childExpression.visit(this);
        Data childRes = this.expressionResult.get(astUnaryAdditiveExpression.childExpression);
        if (childRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) childRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irLoad;
            }
            resultInstructions.add(irLoad);
            childRes = irLoad.dest;
        }

        if (astUnaryAdditiveExpression.op == UnaryAdditiveExpression.ADD) {
            Add irAdd = new Add(new ConstantZero(), childRes, new DataValue());
            irAdd.res.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irAdd;
            }
            resultInstructions.add(irAdd);
            this.expressionResult.put(astUnaryAdditiveExpression, irAdd.res);
        } else {
            Sub irSub = new Sub(new ConstantZero(), childRes, new DataValue());
            irSub.res.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irSub;
            }
            resultInstructions.add(irSub);
            this.expressionResult.put(astUnaryAdditiveExpression, irSub.res);
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitBitNotExpression(BitNotExpression astBitNotExpression) {
        LinkedList<Instruction> resultInstructions = astBitNotExpression.childExpression.visit(this);
        Data childRes = this.expressionResult.get(astBitNotExpression.childExpression);
        if (childRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) childRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irLoad;
            }
            resultInstructions.add(irLoad);
            childRes = irLoad.dest;
        }
        BitNot irBitNot = new BitNot((DataValue) childRes, new DataValue());
        irBitNot.res.stackShift = this.currentStackSize++;
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irBitNot;
        }
        this.expressionResult.put(astBitNotExpression, irBitNot.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitMulDivExpression(MulDivExpression astMulDivExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astMulDivExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astMulDivExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astMulDivExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astMulDivExpression.rightExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        if (lhsInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }
        resultInstructions = lhsInstructions;
        resultInstructions.addAll(rhsInstructions);
        BinaryArithmetic thisInstruction;
        if (astMulDivExpression.op == MulDivExpression.MUL) {
            thisInstruction = new Mul(lhsRes, rhsRes, new DataValue());
        } else if (astMulDivExpression.op == MulDivExpression.DIV) {
            thisInstruction = new Div(lhsRes, rhsRes, new DataValue());
        } else if (astMulDivExpression.op == MulDivExpression.MOD) {
            thisInstruction = new Mod(lhsRes, rhsRes, new DataValue());
        } else {
            throw new RuntimeException();
        }
        thisInstruction.res.stackShift = this.currentStackSize++;

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = thisInstruction;
        }
        resultInstructions.add(thisInstruction);
        this.expressionResult.put(astMulDivExpression, thisInstruction.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitArraySubscriptingExpression(ArraySubscriptingExpression astArraySubscriptingExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> primaryInstructions = astArraySubscriptingExpression.primaryExpression.visit(this);
        LinkedList<Instruction> positionInstructions = astArraySubscriptingExpression.positionExpression.visit(this);
        Data primaryRes = this.expressionResult.get(astArraySubscriptingExpression.primaryExpression);
        Data positionRes = this.expressionResult.get(astArraySubscriptingExpression.positionExpression);
        if (primaryRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) primaryRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (primaryInstructions.size() > 0) {
                primaryInstructions.getLast().nextInstruction = irLoad;
            }
            primaryInstructions.add(irLoad);
            primaryRes = irLoad.dest;
        }
        if (positionRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) positionRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (positionInstructions.size() > 0) {
                positionInstructions.getLast().nextInstruction = irLoad;
            }
            positionInstructions.add(irLoad);
            positionRes = irLoad.dest;
        }

        resultInstructions = primaryInstructions;
        if (resultInstructions.size() > 0) {
            if (positionInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = positionInstructions.getFirst();
            }
        }
        resultInstructions.addAll(positionInstructions);

        Mul getRealPos = new Mul(positionRes, new DataIntLiteral(4), new DataValue());
        getRealPos.res.stackShift = this.currentStackSize++;

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = getRealPos;
        }
        resultInstructions.add(getRealPos);

        Add irAdd = new Add(primaryRes, getRealPos.res, new DataValue());
        irAdd.res.stackShift = this.currentStackSize++;
        resultInstructions.getLast().nextInstruction = irAdd;
        resultInstructions.add(irAdd);

        DataAddress irDataAddress = new DataAddress(irAdd.res);
        this.expressionResult.put(astArraySubscriptingExpression, irDataAddress);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitBitAndExpression(BitAndExpression astBitAndExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astBitAndExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astBitAndExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astBitAndExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astBitAndExpression.rightExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }

        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }

        if (lhsInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }

        resultInstructions = lhsInstructions;
        resultInstructions.addAll(rhsInstructions);
        BinaryArithmetic thisInstruction;
        thisInstruction = new BitAnd(lhsRes, rhsRes, new DataValue());
        thisInstruction.res.stackShift = this.currentStackSize++;

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = thisInstruction;
        }
        resultInstructions.add(thisInstruction);

        this.expressionResult.put(astBitAndExpression, thisInstruction.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitBitOrExpression(BitOrExpression astBitOrExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astBitOrExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astBitOrExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astBitOrExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astBitOrExpression.rightExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        if (lhsInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }
        resultInstructions = lhsInstructions;
        resultInstructions.addAll(rhsInstructions);
        BinaryArithmetic thisInstruction;
        thisInstruction = new BitOr(lhsRes, rhsRes, new DataValue());
        thisInstruction.res.stackShift = this.currentStackSize++;
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = thisInstruction;
        }
        resultInstructions.add(thisInstruction);
        this.expressionResult.put(astBitOrExpression, thisInstruction.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitBitXorExpression(BitXorExpression astBitXorExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astBitXorExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astBitXorExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astBitXorExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astBitXorExpression.rightExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        if (lhsInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }
        resultInstructions = lhsInstructions;
        resultInstructions.addAll(rhsInstructions);
        BinaryArithmetic thisInstruction;
        thisInstruction = new BitXor(lhsRes, rhsRes, new DataValue());
        thisInstruction.res.stackShift = this.currentStackSize++;
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = thisInstruction;
        }
        resultInstructions.add(thisInstruction);
        this.expressionResult.put(astBitXorExpression, thisInstruction.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitBitShiftExpression(BitShiftExpression astBitShiftExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astBitShiftExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astBitShiftExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astBitShiftExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astBitShiftExpression.rightExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        if (lhsInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }
        resultInstructions = lhsInstructions;
        resultInstructions.addAll(rhsInstructions);
        BinaryArithmetic thisInstruction;
        if (astBitShiftExpression.op == BitShiftExpression.LEFT_SHIFT) {
            thisInstruction = new BitLsh(lhsRes, rhsRes, new DataValue());
        } else if (astBitShiftExpression.op == BitShiftExpression.RIGHT_SHIFT) {
            thisInstruction = new BitRsh(lhsRes, rhsRes, new DataValue());
        } else {
            throw new RuntimeException();
        }
        thisInstruction.res.stackShift = this.currentStackSize++;
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = thisInstruction;
        }
        resultInstructions.add(thisInstruction);
        this.expressionResult.put(astBitShiftExpression, thisInstruction.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitLogicalAndExpression(LogicalAndExpression astLogicalAndExpression) {
        LinkedList<Instruction> resultInstructions;
        IRLabelShortPathEvaluate irLabelShortPathEvaluate = new IRLabelShortPathEvaluate();
        NullOperation irNullOperation = new NullOperation();
        irNullOperation.label = irLabelShortPathEvaluate;
        // final result
        Data finalRes = new DataValue();
        finalRes.stackShift = this.currentStackSize++;
        // get left expression result and instructions.
        LinkedList<Instruction> lhsInstructions = astLogicalAndExpression.leftExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astLogicalAndExpression.leftExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
            finalRes = lhsRes;
        } else {
            Move irMove = new Move(lhsRes, finalRes);
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irMove;
            }
            lhsInstructions.add(irMove);
        }

        resultInstructions = lhsInstructions;
        // if lhsRes == 0 then the right expression would not be evaluated.
        BranchEqualZero irBranchEqualZero = new BranchEqualZero((DataValue) finalRes, irLabelShortPathEvaluate);
        resultInstructions.getLast().nextInstruction = irBranchEqualZero;
        resultInstructions.add(irBranchEqualZero);

        LinkedList<Instruction> rhsInstructions = astLogicalAndExpression.rightExpression.visit(this);
        Data rhsRes = this.expressionResult.get(astLogicalAndExpression.rightExpression);

        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }

        if (rhsInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
        }
        resultInstructions.addAll(rhsInstructions);

        BinaryArithmetic thisInstruction = new BitAnd(finalRes, rhsRes, (DataValue) finalRes);
        resultInstructions.getLast().nextInstruction = thisInstruction;
        resultInstructions.add(thisInstruction);
        resultInstructions.getLast().nextInstruction = irNullOperation;
        resultInstructions.add(irNullOperation);

        this.expressionResult.put(astLogicalAndExpression, thisInstruction.res);

        return resultInstructions;
    }

    public LinkedList<Instruction> visitLogicalOrExpression(LogicalOrExpression astLogicalOrExpression) {
        LinkedList<Instruction> resultInstructions;
        IRLabelShortPathEvaluate irLabelShortPathEvaluate = new IRLabelShortPathEvaluate();
        NullOperation irNullOperation = new NullOperation();
        irNullOperation.label = irLabelShortPathEvaluate;
        // final result
        Data finalRes = new DataValue();
        finalRes.stackShift = this.currentStackSize++;
        // get left expression result and instructions.
        LinkedList<Instruction> lhsInstructions = astLogicalOrExpression.leftExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astLogicalOrExpression.leftExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
            finalRes = lhsRes;
        } else {
            Move irMove = new Move(lhsRes, finalRes);
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irMove;
            }
            lhsInstructions.add(irMove);
        }

        resultInstructions = lhsInstructions;
        // if lhsRes == 1 then the right expression would not be evaluated.
        BranchNotEqualZero irBranchNotEqualZero = new BranchNotEqualZero((DataValue) finalRes, irLabelShortPathEvaluate);
        resultInstructions.getLast().nextInstruction = irBranchNotEqualZero;
        resultInstructions.add(irBranchNotEqualZero);

        LinkedList<Instruction> rhsInstructions = astLogicalOrExpression.rightExpression.visit(this);
        Data rhsRes = this.expressionResult.get(astLogicalOrExpression.rightExpression);

        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }

        if (rhsInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
        }
        resultInstructions.addAll(rhsInstructions);

        BinaryArithmetic thisInstruction = new BitOr(finalRes, rhsRes, (DataValue) finalRes);
        resultInstructions.getLast().nextInstruction = thisInstruction;
        resultInstructions.add(thisInstruction);
        resultInstructions.getLast().nextInstruction = irNullOperation;
        resultInstructions.add(irNullOperation);

        this.expressionResult.put(astLogicalOrExpression, thisInstruction.res);

        return resultInstructions;
    }

    public LinkedList<Instruction> visitLogicalNotExpression(LogicalNotExpression astLogicalNotExpression) {
        LinkedList<Instruction> resultInstructions = astLogicalNotExpression.childExpression.visit(this);

        Data childRes = this.expressionResult.get(astLogicalNotExpression.childExpression);
        if (childRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) childRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irLoad;
            }
            resultInstructions.add(irLoad);
            childRes = irLoad.dest;
        }

        BitXor irLogicalNot = new BitXor(childRes, new DataIntLiteral(1), new DataValue());
        irLogicalNot.res.stackShift = this.currentStackSize++;

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irLogicalNot;
        }
        resultInstructions.add(irLogicalNot);

        this.expressionResult.put(astLogicalNotExpression, irLogicalNot.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitPrefixExpression(PrefixExpression astPrefixExpression) {
        LinkedList<Instruction> resultInstructions = astPrefixExpression.childExpression.visit(this);
        Data childRes = this.expressionResult.get(astPrefixExpression.childExpression);
        Data originChildRes = childRes;

        if (childRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) childRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irLoad;
            }
            resultInstructions.add(irLoad);
            childRes = irLoad.dest;
        }

        BinaryArithmetic thisInstruction;
        if (astPrefixExpression.op == PrefixExpression.PLUS_PLUS) {
            thisInstruction = new Add(childRes, new DataIntLiteral(1), (DataValue) childRes);
        } else if (astPrefixExpression.op == PrefixExpression.MINUS_MINUS) {
            thisInstruction = new Sub(childRes, new DataIntLiteral(1), (DataValue) childRes);
        } else {
            throw new RuntimeException();
        }

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = thisInstruction;
        }

        resultInstructions.add(thisInstruction);
        if (originChildRes instanceof DataAddress) {
            Store irStore = new Store(thisInstruction.res, originChildRes);
            resultInstructions.getLast().nextInstruction = irStore;
            resultInstructions.add(irStore);
        }

        this.expressionResult.put(astPrefixExpression, thisInstruction.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitSuffixExpression(SuffixExpression astSuffixExpression) {
        LinkedList<Instruction> resultInstructions = astSuffixExpression.childExpression.visit(this);
        Data childRes = this.expressionResult.get(astSuffixExpression.childExpression);
        Data originChildRes = childRes;
        Data exprRes;

        if (childRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) childRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irLoad;
            }
            resultInstructions.add(irLoad);
            childRes = irLoad.dest;
        }

        Move irMove = new Move((DataValue) childRes);
        irMove.dest.stackShift = this.currentStackSize++;
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irMove;
        }
        resultInstructions.add(irMove);
        exprRes = irMove.dest;

        //-------------------------------------------------------

        this.expressionResult.put(astSuffixExpression, exprRes);

        //-------------------------------------------------------

        BinaryArithmetic thisInstruction;
        if (astSuffixExpression.op == SuffixExpression.PLUS_PLUS) {
            thisInstruction = new Add(childRes, new DataIntLiteral(1), (DataValue) childRes);
        } else if (astSuffixExpression.op == SuffixExpression.MINUS_MINUS) {
            thisInstruction = new Sub(childRes, new DataIntLiteral(1), (DataValue) childRes);
        } else {
            throw new RuntimeException();
        }

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = thisInstruction;
        }
        resultInstructions.add(thisInstruction);

        Store irStore = new Store(thisInstruction.res, originChildRes);
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irStore;
        }
        resultInstructions.add(irStore);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitMemberAccessExpression(MemberAccessExpression astMemberAccessExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> primaryInstructions = astMemberAccessExpression.primaryExpression.visit(this);
        LinkedList<Instruction> memberInstructions;
        if (astMemberAccessExpression.memberExpression == null) {
            memberInstructions = new LinkedList<>();
        } else {
            memberInstructions = astMemberAccessExpression.memberExpression.visit(this);
        }

        Data primaryRes = this.expressionResult.get(astMemberAccessExpression.primaryExpression);
        if (primaryRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) primaryRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (primaryInstructions.size() > 0) {
                primaryInstructions.getLast().nextInstruction = irLoad;
            }
            primaryInstructions.add(irLoad);
            primaryRes = irLoad.dest;
        }

        resultInstructions = primaryInstructions;

        if (astMemberAccessExpression.memberExpression == null) {
            if (astMemberAccessExpression.primaryExpression.returnType instanceof Class) {
                Class classDetail = (Class) astMemberAccessExpression.primaryExpression.returnType;
                int shift = classDetail.classComponents.get(astMemberAccessExpression.memberName).memberID;
                int realShift = 4 * shift;
                Add irAdd = new Add(primaryRes, new DataIntLiteral(realShift), new DataValue());
                irAdd.res.stackShift = this.currentStackSize++;
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irAdd;
                }
                resultInstructions.add(irAdd);
                DataAddress expressionRes = new DataAddress(irAdd.res);

                this.expressionResult.put(astMemberAccessExpression, expressionRes);
                return primaryInstructions;
            } else {
                throw new RuntimeException();
            }
        } else {
            if (resultInstructions.size() > 0) {
                if (memberInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = memberInstructions.getFirst();
                }
            }
            resultInstructions.addAll(memberInstructions);
            if (astMemberAccessExpression.memberExpression instanceof CallOrd) {
                // call string.ord(pos)
                // get position

                Data callParameter = this.expressionResult.get(astMemberAccessExpression.memberExpression);
                if (callParameter instanceof DataAddress) {
                    Load irLoad = new Load();
                    if (resultInstructions.size() > 0) {
                        resultInstructions.getLast().nextInstruction = irLoad;
                    }
                    resultInstructions.add(irLoad);
                    callParameter = irLoad.dest;
                }
                // get char address.
                Add irAdd = new Add(primaryRes, callParameter, new DataValue());
                irAdd.res.stackShift = this.currentStackSize++;
                DataAddress irDataAddress = new DataAddress(irAdd.res);
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irAdd;
                }
                resultInstructions.add(irAdd);

                // load data
                Load irLoad = new Load(irDataAddress, 1);
                irLoad.dest.stackShift = this.currentStackSize++;
                resultInstructions.getLast().nextInstruction = irLoad;
                resultInstructions.add(irLoad);

                // set result
                this.expressionResult.put(astMemberAccessExpression, irLoad.dest);
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irLoad;
                }
                resultInstructions.add(irLoad);
            } else if (astMemberAccessExpression.memberExpression instanceof CallLength) {
                // call string.length()
                // locate the data of length
                // get the length address.
                Sub irSub = new Sub(primaryRes, new DataIntLiteral(4), new DataValue());
                irSub.res.stackShift = this.currentStackSize++;
                DataAddress irDataAddress = new DataAddress(irSub.res);
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irSub;
                }
                resultInstructions.add(irSub);
                // load the data.
                Load irLoad = new Load(irDataAddress);
                irLoad.dest.stackShift = this.currentStackSize++;
                resultInstructions.getLast().nextInstruction = irLoad;
                resultInstructions.add(irLoad);

                this.expressionResult.put(astMemberAccessExpression, irLoad.dest);
            } else if (astMemberAccessExpression.memberExpression instanceof CallSize) {
                // call array.size()
                // locate the data of length
                // get the address
                Sub irSub = new Sub(primaryRes, new DataIntLiteral(4), new DataValue());
                irSub.res.stackShift = this.currentStackSize++;
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irSub;
                }
                resultInstructions.add(irSub);

                DataAddress irDataAddress = new DataAddress(irSub.res);
                Load irLoad = new Load(irDataAddress);
                irLoad.dest.stackShift = this.currentStackSize++;
                resultInstructions.getLast().nextInstruction = irLoad;
                resultInstructions.add(irLoad);

                this.expressionResult.put(astMemberAccessExpression, irLoad.dest);
            } else if (astMemberAccessExpression.memberExpression instanceof CallSubString){

                // call string.substring(left, right)
                CallSubString astCallSubString = (CallSubString) astMemberAccessExpression.memberExpression;
                // get left and right boundary of the substring
                LinkedList<Instruction> leftBoundInstructions = astCallSubString.leftExpression.visit(this);
                LinkedList<Instruction> rightBoundInstructions = astCallSubString.rightExpression.visit(this);
                Data leftBoundRes = this.expressionResult.get(astCallSubString.leftExpression);
                Data rightBoundRes = this.expressionResult.get(astCallSubString.rightExpression);
                if (leftBoundRes instanceof DataAddress) {
                    Load irLoad = new Load((DataAddress) leftBoundRes);
                    irLoad.dest.stackShift = this.currentStackSize++;
                    if (leftBoundInstructions.size() > 0) {
                        leftBoundInstructions.getLast().nextInstruction = irLoad;
                    }
                    leftBoundInstructions.add(irLoad);
                    leftBoundRes = irLoad.dest;
                }
                if (rightBoundRes instanceof DataAddress) {
                    Load irLoad = new Load((DataAddress) rightBoundRes);
                    irLoad.dest.stackShift = this.currentStackSize++;
                    if (rightBoundInstructions.size() > 0) {
                        rightBoundInstructions.getLast().nextInstruction = irLoad;
                    }
                    rightBoundInstructions.add(irLoad);
                    rightBoundRes = irLoad.dest;
                }
                if (resultInstructions.size() > 0) {
                    if (leftBoundInstructions.size() > 0) {
                        resultInstructions.getLast().nextInstruction = leftBoundInstructions.getFirst();
                    }
                }
                resultInstructions.addAll(leftBoundInstructions);
                if (resultInstructions.size() > 0) {
                    if (rightBoundInstructions.size() > 0) {
                        resultInstructions.getLast().nextInstruction = rightBoundInstructions.getFirst();
                    }
                }
                resultInstructions.addAll(rightBoundInstructions);
                Call irCall = new Call(new IRLabelFunction("____built_in_substring"));
                irCall.parameters.add((DataValue) primaryRes);
                primaryRes.refCount++;
                irCall.parameters.add((DataValue) leftBoundRes);
                leftBoundRes.refCount++;
                irCall.parameters.add((DataValue) rightBoundRes);
                rightBoundRes.refCount++;
                irCall.res.stackShift = this.currentStackSize++;
                this.expressionResult.put(astMemberAccessExpression, irCall.res);
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irCall;
                }
                resultInstructions.add(irCall);
            } else if (astMemberAccessExpression.memberExpression instanceof CallParseInt) {
                // call string.parseInt();
                Call irCall = new Call(new IRLabelFunction("____built_in_parseInt"));
                irCall.parameters.add((DataValue) primaryRes);
                primaryRes.refCount++;
                irCall.res.stackShift = this.currentStackSize++;
                this.expressionResult.put(astMemberAccessExpression, irCall.res);
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irCall;
                }
                resultInstructions.add(irCall);
            }
        }
        return resultInstructions;
    }
    
    public LinkedList<Instruction> visitCallOrd(CallOrd astCallOrd) {
        LinkedList<Instruction> resultInstruction = astCallOrd.childExpression.visit(this);
        this.expressionResult.put(astCallOrd, this.expressionResult.get(astCallOrd.childExpression));
        return resultInstruction;
    }

    public LinkedList<Instruction> visitCallLength(CallLength astCallLength) {
        // do nothing.
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitCreationExpression(CreationExpression astCreationExpression) {
        LinkedList<Instruction> resultInstructions;
        if (astCreationExpression.resultDim - astCreationExpression.determinedDim > 1) {
            throw new RuntimeException("no grammar sugar.");
        }
        if (astCreationExpression.expressionList != null && astCreationExpression.expressionList.size() == 1) {
            // new a array.
            // allocate 4 * (1 + size) bytes memory to store data.
            // get the size of array.
            LinkedList<Instruction> sizeInstructions = astCreationExpression.expressionList.get(0).visit(this);
            Data sizeExprRes = this.expressionResult.get(astCreationExpression.expressionList.get(0));

            if (sizeExprRes instanceof DataAddress) {
                Load irLoad = new Load((DataAddress) sizeExprRes);
                irLoad.dest.stackShift = this.currentStackSize++;
                if (sizeInstructions.size() > 0) {
                    sizeInstructions.getLast().nextInstruction = irLoad;
                }
                sizeInstructions.add(irLoad);
                sizeExprRes = irLoad.dest;
            }

            resultInstructions = sizeInstructions;
            // size + 1

            Add irAdd = new Add(sizeExprRes, new DataIntLiteral(1), new DataValue());
            irAdd.res.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irAdd;
            }
            resultInstructions.add(irAdd);

            // 4 * (size + 1)
            Mul getRealSize = new Mul(irAdd.res, new DataIntLiteral(4), new DataValue());
            getRealSize.res.stackShift = this.currentStackSize++;
            resultInstructions.getLast().nextInstruction = getRealSize;
            resultInstructions.add(getRealSize);

            // add instruction to get heap memory
            MemoryAllocate irMemoryAllocate = new MemoryAllocate(getRealSize.res);
            irMemoryAllocate.res.stackShift = this.currentStackSize++;
            resultInstructions.getLast().nextInstruction = irMemoryAllocate;
            resultInstructions.add(irMemoryAllocate);

            DataAddress allocateRes = new DataAddress(irMemoryAllocate.res);

            //store the size of the array to the memory.
            Store irStore = new Store((DataValue) sizeExprRes, allocateRes);
            resultInstructions.getLast().nextInstruction = irStore;
            resultInstructions.add(irStore);

            //calculate the address of the head of the array as the result of this expression.
            Add irAddressAdd = new Add(irMemoryAllocate.res, new DataIntLiteral(4), new DataValue());
            irAddressAdd.res.stackShift = this.currentStackSize++;

            this.expressionResult.put(astCreationExpression, irAddressAdd.res);
            resultInstructions.getLast().nextInstruction = irAddressAdd;
            resultInstructions.add(irAddressAdd);
        } else if (astCreationExpression.resultDim == 0) {
            // new a class.
            resultInstructions = new LinkedList<>();
            // get size of class by the number of class members.
            DataIntLiteral amount = new DataIntLiteral(((Class) astCreationExpression.returnType).classSize * 4);
            // add instruction to get heap memory.
            MemoryAllocate irMemoryAllocate = new MemoryAllocate(amount);
            irMemoryAllocate.res.stackShift = this.currentStackSize++;

            this.expressionResult.put(astCreationExpression, irMemoryAllocate.res);
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irMemoryAllocate;
            }
            resultInstructions.add(irMemoryAllocate);
        } else {
            throw new RuntimeException();
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitEqualityExpression(EqualityExpression astEqualityExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astEqualityExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astEqualityExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astEqualityExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astEqualityExpression.rightExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        resultInstructions = lhsInstructions;
        if (resultInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }
        resultInstructions.addAll(rhsInstructions);
        if (astEqualityExpression.leftExpression.returnType instanceof MxString) {
            Call irCall;
            if (astEqualityExpression.op == EqualityExpression.EQUAL) {
                irCall = new Call(new IRLabelFunction("____built_in_string_equal"));
                irCall.parameters.add((DataValue) lhsRes);
                lhsRes.refCount++;
                irCall.parameters.add((DataValue) rhsRes);
                rhsRes.refCount++;
            } else if (astEqualityExpression.op == EqualityExpression.INEQUAL) {
                irCall = new Call(new IRLabelFunction(("____built_in_string_inequal")));
                irCall.parameters.add((DataValue) lhsRes);
                lhsRes.refCount++;
                irCall.parameters.add((DataValue) rhsRes);
                rhsRes.refCount++;
            } else {
                throw new RuntimeException();
            }
            irCall.res.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irCall;
            }
            resultInstructions.add(irCall);
            this.expressionResult.put(astEqualityExpression, irCall.res);
        } else {
            Compare irCompare;
            if (astEqualityExpression.op == EqualityExpression.EQUAL) {
                irCompare = new CompareEqual((DataValue) lhsRes, (DataValue) rhsRes);
            } else if (astEqualityExpression.op == EqualityExpression.INEQUAL) {
                irCompare = new CompareNotEqual((DataValue) lhsRes, (DataValue) rhsRes);
            } else {
                throw new RuntimeException();
            }
            irCompare.res.stackShift = this.currentStackSize++;

            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irCompare;
            }
            resultInstructions.add(irCompare);
            this.expressionResult.put(astEqualityExpression, irCompare.res);
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitFunctionCall(FunctionCall astFunctionCall) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        Instruction lastInstructions = null;
        Call irCall = new Call(new IRLabelFunction(astFunctionCall.functionName));
        if (astFunctionCall.returnType instanceof Void) {
            irCall.res = null;
        } else {
            irCall.res.stackShift = this.currentStackSize++;
        }
        if (astFunctionCall.parameters.expressions != null) {
            for (Expression expression : astFunctionCall.parameters.expressions) {
                LinkedList<Instruction> expressionInstructions = expression.visit(this);
                Data expressionRes = this.expressionResult.get(expression);
                if (expressionRes instanceof DataAddress) {
                    Load irLoad = new Load((DataAddress) expressionRes);
                    irLoad.dest.stackShift = this.currentStackSize++;
                    if (expressionInstructions.size() > 0) {
                        expressionInstructions.getLast().nextInstruction = irLoad;
                    }
                    expressionInstructions.add(irLoad);
                    expressionRes = irLoad.dest;
                }
                irCall.parameters.add((DataValue) expressionRes);
                expressionRes.refCount++;
                if (lastInstructions != null) {
                    if (expressionInstructions.size() > 0) {
                        lastInstructions.nextInstruction = expressionInstructions.getFirst();
                    }
                }
                resultInstructions.addAll(expressionInstructions);
                if (resultInstructions.size() > 0) {
                    lastInstructions = resultInstructions.getLast();
                }
            }
        }
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irCall;
        }
        resultInstructions.add(irCall);
        this.expressionResult.put(astFunctionCall, irCall.res);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitRelationExpression(RelationExpression astRelationExpression) {
        LinkedList<Instruction> resultInstructions;
        LinkedList<Instruction> lhsInstructions = astRelationExpression.leftExpression.visit(this);
        LinkedList<Instruction> rhsInstructions = astRelationExpression.rightExpression.visit(this);
        Data lhsRes = this.expressionResult.get(astRelationExpression.leftExpression);
        Data rhsRes = this.expressionResult.get(astRelationExpression.rightExpression);
        if (lhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) lhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (lhsInstructions.size() > 0) {
                lhsInstructions.getLast().nextInstruction = irLoad;
            }
            lhsInstructions.add(irLoad);
            lhsRes = irLoad.dest;
        }
        if (rhsRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) rhsRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (rhsInstructions.size() > 0) {
                rhsInstructions.getLast().nextInstruction = irLoad;
            }
            rhsInstructions.add(irLoad);
            rhsRes = irLoad.dest;
        }
        resultInstructions = lhsInstructions;
        if (resultInstructions.size() > 0) {
            if (rhsInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = rhsInstructions.getFirst();
            }
        }
        resultInstructions.addAll(rhsInstructions);
        if (astRelationExpression.leftExpression.returnType instanceof MxString) {
            Call irCall;
            lhsRes.refCount++;
            rhsRes.refCount++;
            if (astRelationExpression.op == RelationExpression.LESS) {
                irCall = new Call(new IRLabelFunction("____built_in_string_less"));
                irCall.parameters.add((DataValue) lhsRes);
                irCall.parameters.add((DataValue) rhsRes);
            } else if (astRelationExpression.op == RelationExpression.LESS_EQ) {
                irCall = new Call(new IRLabelFunction(("____built_in_string_less_equal")));
                irCall.parameters.add((DataValue) lhsRes);
                irCall.parameters.add((DataValue) rhsRes);
            } else if (astRelationExpression.op == RelationExpression.GREATER) {
                irCall = new Call(new IRLabelFunction(("____built_in_string_less")));
                irCall.parameters.add((DataValue) rhsRes);
                irCall.parameters.add((DataValue) lhsRes);
            } else if (astRelationExpression.op == RelationExpression.GREATER_EQ) {
                irCall = new Call(new IRLabelFunction(("____built_in_string_less_equal")));
                irCall.parameters.add((DataValue) rhsRes);
                irCall.parameters.add((DataValue) lhsRes);
            } else {
                throw new RuntimeException();
            }
            irCall.res.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irCall;
            }
            resultInstructions.add(irCall);
            this.expressionResult.put(astRelationExpression, irCall.res);
        } else {
            Compare irCompare;
            if (astRelationExpression.op == RelationExpression.LESS) {
                irCompare = new CompareLess((DataValue) lhsRes, (DataValue) rhsRes);
            } else if (astRelationExpression.op == RelationExpression.LESS_EQ) {
                irCompare = new CompareLessEqual((DataValue) lhsRes, (DataValue) rhsRes);
            } else if (astRelationExpression.op == RelationExpression.GREATER) {
                irCompare = new CompareGreater((DataValue) lhsRes, (DataValue) rhsRes);
            } else if (astRelationExpression.op == RelationExpression.GREATER_EQ) {
                irCompare = new CompareGreaterEqual((DataValue) lhsRes, (DataValue) rhsRes);
            } else {
                throw new RuntimeException();
            }
            irCompare.res.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irCompare;
            }
            resultInstructions.add(irCompare);
            this.expressionResult.put(astRelationExpression, irCompare.res);
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitSubgroupExpression(SubgroupExpression astSubgroupExpression) {
        LinkedList<Instruction> resultInstructions = astSubgroupExpression.childExpression.visit(this);
        this.expressionResult.put(astSubgroupExpression, this.expressionResult.get(astSubgroupExpression.childExpression));
        return resultInstructions;
    }

    public LinkedList<Instruction> visitCompoundStatement(CompoundStatement astCompoundStatement) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        for (BaseNode baseNode : astCompoundStatement.statements) {
            LinkedList<Instruction> instructions = baseNode.visit(this);
            if (instructions.size() > 0) {
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = instructions.getFirst();
                }
                resultInstructions.addAll(instructions);
            }
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitExpressionStatement(ExpressionStatement astExpressionStatement) {
        LinkedList<Instruction> resultInstruction = astExpressionStatement.expression.visit(this);
        Data expressionRes = this.expressionResult.get(astExpressionStatement.expression);
        return resultInstruction;
    }

    public LinkedList<Instruction> visitForStatement(ForStatement astForStatement) {
        IRLabel endLoopBackup = this.endLoop;
        IRLabel continueLoopBackup = this.continueLoop;
        //--------------------------------------------------------------
        IRLabel thisForCondition = new IRLabelBeginLoop();
        this.endLoop = new IRLabelEndLoop();
        this.continueLoop = new IRLabelContinueLoop();

        // a null operation for the end of loop.
        NullOperation endLoopInstructions = new NullOperation();
        endLoopInstructions.label = endLoop;

        // initial expression of a `for` statement.
        LinkedList<Instruction> resultInstructions = new LinkedList<>();

        if (astForStatement.start != null) {
            LinkedList<Instruction> initializeExpressionInstructions = astForStatement.start.visit(this);
            resultInstructions.addAll(initializeExpressionInstructions);
        }

        // condition expression and loop body for a `for` statement;
        LinkedList<Instruction> loopInstructions = new LinkedList<>();

        if (astForStatement.cond instanceof BoolConstant) {
            if (!((BoolConstant) astForStatement.cond).boolConstant) {
                JumpLabel irJumpLabel = new JumpLabel(this.endLoop);
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irJumpLabel;
                }
                resultInstructions.add(irJumpLabel);
            }
        } else {
            LinkedList<Instruction> conditionExpressionInstructions = astForStatement.cond.visit(this);
            Data conditionRes = this.expressionResult.get(astForStatement.cond);

            if (conditionRes instanceof DataAddress) {
                Load irLoad = new Load((DataAddress) conditionRes);
                irLoad.dest.stackShift = this.currentStackSize++;
                if (conditionExpressionInstructions.size() > 0) {
                    conditionExpressionInstructions.getLast().nextInstruction = irLoad;
                }
                conditionExpressionInstructions.add(irLoad);
                conditionRes = irLoad.dest;
            }

            loopInstructions.addAll(conditionExpressionInstructions);
            BranchEqualZero irBranchEqualZero = new BranchEqualZero((DataValue) conditionRes, this.endLoop);
            if (loopInstructions.size() > 0) {
                loopInstructions.getLast().nextInstruction = irBranchEqualZero;
            }
            loopInstructions.add(irBranchEqualZero);
        }

        LinkedList<Instruction> loopBodyInstrucions = astForStatement.loopBody.visit(this);
        if (loopInstructions.size() > 0){
            if (loopBodyInstrucions.size() > 0) {
                loopInstructions.getLast().nextInstruction = loopBodyInstrucions.getFirst();
            }
        }
        loopInstructions.addAll(loopBodyInstrucions);
        if (loopInstructions.size() > 0) {
            loopInstructions.getFirst().label = thisForCondition;
        } else {
            loopInstructions.add(new NullOperation());
            loopInstructions.getFirst().label = thisForCondition;
        }

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = loopInstructions.getFirst();
        }
        resultInstructions.addAll(loopInstructions);
        // consider condition expression and loop statements as the loop body.

        // step expression for a `for` statement;
        if (astForStatement.loop != null) {
            LinkedList<Instruction> stepInstructions = astForStatement.loop.visit(this);

            JumpLabel irJumpLabel = new JumpLabel(thisForCondition);
            if (stepInstructions.size() > 0) {
                stepInstructions.getLast().nextInstruction = irJumpLabel;
            }
            stepInstructions.add(irJumpLabel);
            stepInstructions.getFirst().label = this.continueLoop;

            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = stepInstructions.getFirst();
            }
            resultInstructions.addAll(stepInstructions);
        } else {
            JumpLabel irJumpLabel = new JumpLabel(thisForCondition);
            irJumpLabel.label = this.continueLoop;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irJumpLabel;
            }
            resultInstructions.add(irJumpLabel);
        }

        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = endLoopInstructions;
        }
        resultInstructions.add(endLoopInstructions);
        //--------------------------------------------------------------
        this.endLoop = endLoopBackup;
        this.continueLoop = continueLoopBackup;
        return resultInstructions;
    }

    public LinkedList<Instruction> visitIfStatement(IfStatement astIfStatement) {
        LinkedList<Instruction> resultInstructions;
        IRLabelElse irLabelElse = new IRLabelElse();
        IRLabelEndIf irLabelEndIf = new IRLabelEndIf();
        NullOperation irEndIfInstruction = new NullOperation();
        irEndIfInstruction.label = irLabelEndIf;

        // condition expression instructions.
        LinkedList<Instruction> conditionInstructions = astIfStatement.condition.visit(this);
        Data conditionRes = this.expressionResult.get(astIfStatement.condition);
        if (conditionRes instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) conditionRes);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (conditionInstructions.size() > 0) {
                conditionInstructions.getLast().nextInstruction = irLoad;
            }
            conditionInstructions.add(irLoad);
            conditionRes = irLoad.dest;
        }

        resultInstructions = conditionInstructions;

        // set branch instruction.
        BranchEqualZero irBranchEqualZero = new BranchEqualZero((DataValue) conditionRes, irLabelElse);

        // else statement.
        LinkedList<Instruction> elseInstructions;
        if (astIfStatement.elseStatement == null) {
            irBranchEqualZero.gotoLabel = irLabelEndIf;
            elseInstructions = new LinkedList<>();
        } else {
            elseInstructions = astIfStatement.elseStatement.visit(this);
            if (elseInstructions.size() > 0) {
                elseInstructions.getFirst().label = irLabelElse;
            } else {
                irBranchEqualZero.gotoLabel = irLabelEndIf;
            }
        }

        // translate branch instruction.
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irBranchEqualZero;
        }
        resultInstructions.add(irBranchEqualZero);

        // then statement.
        LinkedList<Instruction> thenInstructions = astIfStatement.thenStatement.visit(this);
        if (astIfStatement.elseStatement != null) {
            JumpLabel irJumpLabel = new JumpLabel(irLabelEndIf);
            if (thenInstructions.size() > 0) {
                thenInstructions.getLast().nextInstruction = irJumpLabel;
            }
            thenInstructions.add(irJumpLabel);
        }
        if (resultInstructions.size() > 0) {
            if (thenInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = thenInstructions.getFirst();
            }
        }
        resultInstructions.addAll(thenInstructions);

        // translate else instructions.
        if (resultInstructions.size() > 0) {
            if (elseInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = elseInstructions.getFirst();
            }
        }
        resultInstructions.addAll(elseInstructions);

        //translate null operation as EndIF.
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irEndIfInstruction;
        }
        resultInstructions.add(irEndIfInstruction);

        return resultInstructions;
    }

    public LinkedList<Instruction> visitJumpStatement(JumpStatement astJumpStatement) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        Branch irJumpLabel;
        if (astJumpStatement.variant == JumpStatement.CONTINUE) {
            irJumpLabel = new JumpLabel(this.continueLoop);
        } else if (astJumpStatement.variant == JumpStatement.BREAK) {
            irJumpLabel = new JumpLabel(this.endLoop);
        } else if (astJumpStatement.variant == JumpStatement.RETURN) {
            Data expressionRes = null;
            if (astJumpStatement.returnExpression != null && !astJumpStatement.returnExpression.returnType.equals(SymbolTable.mentalVoid)) {
                LinkedList<Instruction> expressionInstructions = astJumpStatement.returnExpression.visit(this);
                expressionRes = this.expressionResult.get(astJumpStatement.returnExpression);
                if (expressionRes instanceof DataAddress) {
                    Load irLoad = new Load((DataAddress) expressionRes);
                    irLoad.dest.stackShift = this.currentStackSize++;
                    if (expressionInstructions.size() > 0) {
                        expressionInstructions.getLast().nextInstruction = irLoad;
                    }
                    expressionRes = irLoad.dest;
                    expressionInstructions.add(irLoad);
                }
                resultInstructions.addAll(expressionInstructions);
            }
            irJumpLabel = new Return(this.endFunction, (DataValue) expressionRes);
        } else {
            throw new RuntimeException();
        }
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irJumpLabel;
        }
        resultInstructions.add(irJumpLabel);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitWhileStatement(WhileStatement astWhileStatement) {
        IRLabel endLoopBackup = this.endLoop;
        IRLabel continueLoopBackup = this.continueLoop;
        //--------------------------------------------------------------
        IRLabel thisWhileCondition = new IRLabelBeginLoop();
        this.endLoop = new IRLabelEndLoop();
        this.continueLoop = new IRLabelContinueLoop();

        // a null operation for the end of loop.
        NullOperation endLoopInstructions = new NullOperation();
        endLoopInstructions.label = endLoop;

        LinkedList<Instruction> resultInstructions = new LinkedList<>();

        // condition expression and loop body for a `while` statement;
        LinkedList<Instruction> loopInstructions = new LinkedList<>();

        if (astWhileStatement.cond instanceof BoolConstant) {
            if (!((BoolConstant) astWhileStatement.cond).boolConstant) {
                JumpLabel irJumpLabel = new JumpLabel(this.endLoop);
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irJumpLabel;
                }
                resultInstructions.add(irJumpLabel);
            }
        } else {
            LinkedList<Instruction> conditionExpressionInstructions = astWhileStatement.cond.visit(this);
            Data conditionRes = this.expressionResult.get(astWhileStatement.cond);

            if (conditionRes instanceof DataAddress) {
                Load irLoad = new Load((DataAddress) conditionRes);
                irLoad.dest.stackShift = this.currentStackSize++;
                if (conditionExpressionInstructions.size() > 0) {
                    conditionExpressionInstructions.getLast().nextInstruction = irLoad;
                }
                conditionExpressionInstructions.add(irLoad);
                conditionRes = irLoad.dest;
            }

            if (loopInstructions.size() > 0) {
                if (conditionExpressionInstructions.size() > 0) {
                    loopInstructions.getLast().nextInstruction = conditionExpressionInstructions.getFirst();
                }
            }

            loopInstructions.addAll(conditionExpressionInstructions);
            BranchEqualZero irBranchEqualZero = new BranchEqualZero((DataValue) conditionRes, this.endLoop);

            if (loopInstructions.size() > 0) {
                loopInstructions.getLast().nextInstruction = irBranchEqualZero;
            }
            loopInstructions.add(irBranchEqualZero);
        }

        LinkedList<Instruction> loopBodyInstructions = astWhileStatement.loopBody.visit(this);

        if (loopInstructions.size() > 0) {
            if (loopBodyInstructions.size() > 0) {
                loopInstructions.getLast().nextInstruction = loopBodyInstructions.getFirst();
            }
        }
        loopInstructions.addAll(loopBodyInstructions);
        JumpLabel irJumpLabel = new JumpLabel(this.continueLoop);
        if (loopInstructions.size() > 0) {
            loopInstructions.getLast().nextInstruction = irJumpLabel;
        }
        loopInstructions.add(irJumpLabel);

        if (loopInstructions.size() > 0) {
            loopInstructions.getFirst().label = this.continueLoop;
        }
        if (resultInstructions.size() > 0) {
            if (loopInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = loopInstructions.getFirst();
            }
        }
        resultInstructions.addAll(loopInstructions);

        // append the null operation.
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = endLoopInstructions;
        }
        resultInstructions.add(endLoopInstructions);
        //--------------------------------------------------------------
        this.endLoop = endLoopBackup;
        this.continueLoop = continueLoopBackup;
        return resultInstructions;
    }

    public LinkedList<Instruction> visitExpressionList(ExpressionList astExpressionList) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        Instruction lastInstruction = null;
        for (Expression expression : astExpressionList.expressions) {
            LinkedList<Instruction> instructions = expression.visit(this);
            if (instructions.size() > 0) {
                if (lastInstruction != null) {
                    if (instructions.size() > 0) {
                        lastInstruction.nextInstruction = instructions.getFirst();
                    }
                }
                resultInstructions.addAll(instructions);
                if (resultInstructions.size() > 0) {
                    lastInstruction = resultInstructions.getLast();
                }
            }
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitCallGetInt(CallGetInt astCallGetInt) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        GetInt irGetInt = new GetInt();
        irGetInt.res.stackShift = this.currentStackSize++;
        this.expressionResult.put(astCallGetInt, irGetInt.res);
        resultInstructions.add(irGetInt);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitCallGetString(CallGetString astCallGetString) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        Call irCall = new Call(new IRLabelFunction("____built_in_getString"));
        irCall.res.stackShift = this.currentStackSize++;
        this.expressionResult.put(astCallGetString, irCall.res);
        resultInstructions.add(irCall);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitCallParseInt(CallParseInt astCallParseInt) {
        // do nothing when called;
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitCallPrint(CallPrint astCallPrint) {
        LinkedList<Instruction> resultInstructions;
        if (astCallPrint.parameter instanceof AdditiveExpression) {
            resultInstructions = new LinkedList<>();
            CallPrint printLeft = new CallPrint();
            printLeft.parameter = ((AdditiveExpression) astCallPrint.parameter).leftExpression;
            resultInstructions.addAll(printLeft.visit(this));
            CallPrint printRight = new CallPrint();
            printRight.parameter = ((AdditiveExpression) astCallPrint.parameter).rightExpression;
            LinkedList<Instruction> printRightInstructions = printRight.visit(this);
            resultInstructions.getLast().nextInstruction = printRightInstructions.getFirst();
            resultInstructions.addAll(printRightInstructions);
        } else {
            resultInstructions = astCallPrint.parameter.visit(this);
            Data parameterRes = this.expressionResult.get(astCallPrint.parameter);
            if (parameterRes instanceof DataAddress) {
                Load irLoad = new Load((DataAddress) parameterRes);
                irLoad.dest.stackShift = this.currentStackSize++;
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irLoad;
                }
                resultInstructions.add(irLoad);
                parameterRes = irLoad.dest;
            }
            PrintString irPrintString = new PrintString((DataValue) parameterRes);
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irPrintString;
            }
            resultInstructions.add(irPrintString);
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitCallPrintln(CallPrintln astCallPrintln) {
        LinkedList<Instruction> resultInstructions;
        if (astCallPrintln.parameter instanceof AdditiveExpression) {
            resultInstructions = new LinkedList<>();
            CallPrint printLeft = new CallPrint();
            printLeft.parameter = ((AdditiveExpression) astCallPrintln.parameter).leftExpression;
            resultInstructions.addAll(printLeft.visit(this));
            CallPrintln printRight = new CallPrintln();
            printRight.parameter = ((AdditiveExpression) astCallPrintln.parameter).rightExpression;
            LinkedList<Instruction> printRightInstructions = printRight.visit(this);
            resultInstructions.getLast().nextInstruction = printRightInstructions.getFirst();
            resultInstructions.addAll(printRightInstructions);
        } else {
            resultInstructions = astCallPrintln.parameter.visit(this);
            Data parameterRes = this.expressionResult.get(astCallPrintln.parameter);
            if (parameterRes instanceof DataAddress) {
                Load irLoad = new Load((DataAddress) parameterRes);
                irLoad.dest.stackShift = this.currentStackSize++;
                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irLoad;
                }
                resultInstructions.add(irLoad);
                parameterRes = irLoad.dest;
            }
            PrintString irPrintString = new PrintString((DataValue) parameterRes);
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irPrintString;
            }
            resultInstructions.add(irPrintString);
            PrintString irPrintNewLine = new PrintString(this.literalNewline);
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irPrintNewLine;
            }
            resultInstructions.add(irPrintNewLine);
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitCallSubString(CallSubString astCallSubString) {
        // do nothing.
        return new LinkedList<>();
    }

    public LinkedList<Instruction> visitCallToString(CallToString astCallToString) {
        LinkedList<Instruction> resultInstructions = astCallToString.childExpression.visit(this);
        Data parameter = this.expressionResult.get(astCallToString.childExpression);
        if (parameter instanceof DataAddress) {
            Load irLoad = new Load((DataAddress) parameter);
            irLoad.dest.stackShift = this.currentStackSize++;
            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = irLoad;
            }
            resultInstructions.add(irLoad);
            parameter = irLoad.dest;
        }
        Call irCall = new Call(new IRLabelFunction("____built_in_toString"));
        irCall.res.stackShift = this.currentStackSize++;
        irCall.parameters.add((DataValue) parameter);
        parameter.refCount++;
        this.expressionResult.put(astCallToString, irCall.res);
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irCall;
        }
        resultInstructions.add(irCall);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitFunctionDefinition(FunctionDefinition astFunctionDefinition) {
        IRLabel endFunctionBackup = this.endFunction;
        this.endFunction = new IRLabelEndFunction(astFunctionDefinition.functionHead.functionName);
        LinkedList<Instruction> resultInstructions = astFunctionDefinition.functionBody.visit(this);
        if (resultInstructions.size() > 0) {
            if (resultInstructions.getFirst().label == null) {
                resultInstructions.getFirst().label = new IRLabelFunction(astFunctionDefinition.functionHead.functionName);
            } else {
                NullOperation irNullOperation = new NullOperation();
                irNullOperation.label = new IRLabelFunction(astFunctionDefinition.functionHead.functionName);
                irNullOperation.nextInstruction = resultInstructions.getFirst();
                resultInstructions.addFirst(irNullOperation);
            }
        }

        NullOperation irNullOperation = new NullOperation();
        irNullOperation.label = this.endFunction;
        if (resultInstructions.size() > 0) {
            resultInstructions.getLast().nextInstruction = irNullOperation;
        }
        resultInstructions.add(irNullOperation);
        this.endFunction = endFunctionBackup;
        return resultInstructions;
    }

    public LinkedList<Instruction> visitSingleVariableDeclaration(SingleVariableDeclaration astSingleVariableDeclaration) {
        // would never be called.
        throw new RuntimeException();
    }

    public LinkedList<Instruction> visitVariableDeclaration(VariableDeclaration variableDeclaration) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        Instruction lastInstruction = null;
        for (SingleVariableDeclaration astSingleVariableDeclaration : variableDeclaration.variables) {
            DataValue irVariable = this.variableMap.get(astSingleVariableDeclaration.variable.globalID);

            if (irVariable == null) {
                irVariable = new DataValue();
                irVariable.globalID = astSingleVariableDeclaration.variable.globalID;
                irVariable.stackShift = astSingleVariableDeclaration.variable.localID;
                this.variableMap.put(astSingleVariableDeclaration.variable.globalID, irVariable);
            }

            if (astSingleVariableDeclaration.parent.parent instanceof Program) {
                irVariable.globalDataLabel = new IRLabelGlobalData();
                this.globalVariableMap.put(irVariable.globalID, irVariable.globalDataLabel);
                this.globalVariables.add(irVariable);
            }

            if (astSingleVariableDeclaration.initializeExpression != null) {
                LinkedList<Instruction> initialExpressionInstructions = astSingleVariableDeclaration.initializeExpression.visit(this);
                if (lastInstruction != null) {
                    if (initialExpressionInstructions.size() > 0) {
                        lastInstruction.nextInstruction = initialExpressionInstructions.getFirst();
                    }
                }
                Data initialExpressionRes = this.expressionResult.get(astSingleVariableDeclaration.initializeExpression);
                if (initialExpressionRes instanceof DataAddress) {
                    Load irLoad = new Load((DataAddress) initialExpressionRes);
                    irLoad.dest.stackShift = this.currentStackSize++;
                    if (initialExpressionInstructions.size() > 0) {
                        initialExpressionInstructions.getLast().nextInstruction = irLoad;
                    }
                    initialExpressionInstructions.add(irLoad);
                    initialExpressionRes = irLoad.dest;
                }
                resultInstructions.addAll(initialExpressionInstructions);

                Store irStore = new Store((DataValue) initialExpressionRes, irVariable);

                if (resultInstructions.size() > 0) {
                    resultInstructions.getLast().nextInstruction = irStore;
                }
                resultInstructions.add(irStore);

                if (resultInstructions.size() > 0) {
                    lastInstruction = resultInstructions.getLast();
                }
            }
        }
        return resultInstructions;
    }

    public LinkedList<Instruction> visitAstVarStatement(VarStatement astVarStatement) {
        return astVarStatement.variableDeclaration.visit(this);
    }

    public LinkedList<Instruction> visitEmptyStatement(EmptyStatement astEmptyStatement) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        return resultInstructions;
    }

    public LinkedList<Instruction> visitSuperLogicalAndExpression(SuperLogicalAndExpression astSuperLogicalAndExpression) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        IRLabelShortPathEvaluate irLabelShortPathEvaluate = new IRLabelShortPathEvaluate();
        NullOperation irNullOperation = new NullOperation();
        irNullOperation.label = irLabelShortPathEvaluate;
        // final result
        DataValue finalRes = new DataValue();
        finalRes.stackShift = this.currentStackSize++;

        for (Expression expression : astSuperLogicalAndExpression.expressions) {
            LinkedList<Instruction> expressionInstructions = expression.visit(this);
            Data expressionRes = this.expressionResult.get(expression);
            if (expressionRes instanceof DataAddress) {
                Load irLoad = new Load((DataAddress) expressionRes);
                irLoad.dest = finalRes;
                if (expressionInstructions.size() > 0) {
                    expressionInstructions.getLast().nextInstruction = irLoad;
                }
                expressionInstructions.add(irLoad);
            } else {
                Move irMove = new Move(expressionRes, finalRes);
                if (expressionInstructions.size() > 0) {
                    expressionInstructions.getLast().nextInstruction = irMove;
                }
                expressionInstructions.add(irMove);
            }

            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = expressionInstructions.getFirst();
            }
            resultInstructions.addAll(expressionInstructions);

            BranchEqualZero irBranchEqualZero = new BranchEqualZero(finalRes, irLabelShortPathEvaluate);
            resultInstructions.getLast().nextInstruction = irBranchEqualZero;
            resultInstructions.add(irBranchEqualZero);
        }
        resultInstructions.removeLast();

        resultInstructions.getLast().nextInstruction = irNullOperation;
        resultInstructions.add(irNullOperation);
        this.expressionResult.put(astSuperLogicalAndExpression, finalRes);
        return resultInstructions;
    }

    public LinkedList<Instruction> visitSuperLogicalOrExpression(SuperLogicalOrExpression astSuperLogicalOrExpression) {
        LinkedList<Instruction> resultInstructions = new LinkedList<>();
        IRLabelShortPathEvaluate irLabelShortPathEvaluate = new IRLabelShortPathEvaluate();
        NullOperation irNullOperation = new NullOperation();
        irNullOperation.label = irLabelShortPathEvaluate;
        // final result
        DataValue finalRes = new DataValue();
        finalRes.stackShift = this.currentStackSize++;

        for (Expression expression : astSuperLogicalOrExpression.expressions) {
            LinkedList<Instruction> expressionInstructions = expression.visit(this);
            Data expressionRes = this.expressionResult.get(expression);
            if (expressionRes instanceof DataAddress) {
                Load irLoad = new Load((DataAddress) expressionRes);
                irLoad.dest = finalRes;
                if (expressionInstructions.size() > 0) {
                    expressionInstructions.getLast().nextInstruction = irLoad;
                }
                expressionInstructions.add(irLoad);
            } else {
                Move irMove = new Move(expressionRes, finalRes);
                if (expressionInstructions.size() > 0) {
                    expressionInstructions.getLast().nextInstruction = irMove;
                }
                expressionInstructions.add(irMove);
            }

            if (resultInstructions.size() > 0) {
                resultInstructions.getLast().nextInstruction = expressionInstructions.getFirst();
            }
            resultInstructions.addAll(expressionInstructions);

            BranchNotEqualZero irBranchNotEqualZero = new BranchNotEqualZero(finalRes, irLabelShortPathEvaluate);
            resultInstructions.getLast().nextInstruction = irBranchNotEqualZero;
            resultInstructions.add(irBranchNotEqualZero);
        }
        resultInstructions.removeLast();
        resultInstructions.getLast().nextInstruction = irNullOperation;
        resultInstructions.add(irNullOperation);
        this.expressionResult.put(astSuperLogicalOrExpression, finalRes);
        return resultInstructions;
    }
}
