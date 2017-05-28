package FrontEnd.AbstractSyntaxTree.Expression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.AdditionInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.LessThanInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.LessThanOrEqualToInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.MultiplicationInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.BranchInstruction;
import BackEnd.ControlFlowGraph.Instruction.ControlFlowInstruction.JumpInstruction;
import BackEnd.ControlFlowGraph.Instruction.FunctionInstruction.CallInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.LabelInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.AllocateInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.MoveInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.Operand;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class NewExpression extends Expression {
    public List<Expression> subscripts;

    public NewExpression(Type type, boolean isLeftValue, List<Expression> subscripts) {
        super(type, isLeftValue);
        this.subscripts = subscripts;
    }

    public static NewExpression getExpression(Type baseType, List<Expression> subscriptList) {
        if (subscriptList.isEmpty()) {
            if (baseType instanceof ClassType) {
                return new NewExpression(baseType, false, subscriptList);
            }
            throw new CompilationError("type error in new expression");
        } else {
            Type arrayType = ArrayType.getType(baseType, subscriptList.size());
            return new NewExpression(arrayType, false, subscriptList);
        }
    }

    @Override
    public void emit(List<Instruction> instructions) {
        for (Expression subsctipt : subscripts) {
            if (subsctipt != null) {
                subsctipt.emit(instructions);
                subsctipt.load(instructions);
            }
        }
        operand = Environment.registerTable.addTemporaryRegister();
        if (type instanceof ClassType) {
            ClassType classType = (ClassType)type;
            instructions.add(AllocateInstruction.getInstruction(operand, new ImmediateValue(classType.allocateSize)));
            classType.memberVariables.forEach((name, member) -> {
                Address address = new Address((VirtualRegister)operand, new ImmediateValue(member.offset), member.type.size());
                if (member.expression != null) {
                    member.expression.emit(instructions);
                    member.expression.load(instructions);
                    instructions.add(StoreInstruction.getInstruction(member.expression.operand, address));
                }
            });
            if (classType.constructor != null) {
                List<Operand> operands = new ArrayList<Operand>() {{
                    add(operand);
                }};
                instructions.add(CallInstruction.getInstruction(null, classType.constructor, operands));
            }
        } else if (type instanceof ArrayType) {
            ArrayType arrayType = (ArrayType)type;
            VirtualRegister size = Environment.registerTable.addTemporaryRegister();
            instructions.add(MultiplicationInstruction.getInstruction(size, subscripts.get(0).operand, new ImmediateValue(arrayType.reduce().size())));
            instructions.add(AdditionInstruction.getInstruction(size, size, new ImmediateValue(new IntType().size())));
            instructions.add(AllocateInstruction.getInstruction(operand, size));
            instructions.add(StoreInstruction.getInstruction(subscripts.get(0).operand, new Address((VirtualRegister)operand, new IntType().size())));
            instructions.add(AdditionInstruction.getInstruction(operand, operand, new ImmediateValue(new IntType().size())));
            if ((subscripts.size() > 1 && subscripts.get(1) != null) || (((ArrayType) type).baseType instanceof ClassType)) {
                LabelInstruction condition = LabelInstruction.getInstruction("new_condition");
                LabelInstruction body = LabelInstruction.getInstruction("new_body");
                LabelInstruction loop = LabelInstruction.getInstruction("new_loop");
                LabelInstruction exit = LabelInstruction.getInstruction("new_exit");

                Type reduceType = ((ArrayType) type).reduce();
                List<Expression> reduceSubscripts;
                if (subscripts.size() <= 1) {
                    reduceSubscripts = new ArrayList<>();
                } else {
                    reduceSubscripts = subscripts.subList(1, subscripts.size());
                }
                NewExpression reduceNewExpression = NewExpression.getExpression(reduceType, reduceSubscripts);

                VirtualRegister tmp0 = Environment.registerTable.addTemporaryRegister();
                instructions.add(MoveInstruction.getInstruction(tmp0, new ImmediateValue(0)));
                instructions.add(JumpInstruction.getInstruction(condition));

                instructions.add(condition);
                VirtualRegister tmp1 = Environment.registerTable.addTemporaryRegister();
                instructions.add(LessThanInstruction.getInstruction(tmp1, tmp0, subscripts.get(0).operand));
                instructions.add(BranchInstruction.getInstruction(tmp1, body, exit));

                instructions.add(body);
                reduceNewExpression.emit(instructions);
                VirtualRegister tmp2 = Environment.registerTable.addTemporaryRegister();
                instructions.add(MultiplicationInstruction.getInstruction(tmp2, tmp0, new ImmediateValue(((ArrayType) type).baseType.size())));
                VirtualRegister tmp3 = Environment.registerTable.addTemporaryRegister();
                instructions.add(AdditionInstruction.getInstruction(tmp3, operand, tmp2));
                Address cur = new Address(tmp3, ((ArrayType) type).baseType.size());
                instructions.add(StoreInstruction.getInstruction(reduceNewExpression.operand, cur));
                instructions.add(JumpInstruction.getInstruction(loop));

                instructions.add(loop);
                instructions.add(AdditionInstruction.getInstruction(tmp0, tmp0, new ImmediateValue(1)));
                instructions.add(JumpInstruction.getInstruction(condition));
                instructions.add(JumpInstruction.getInstruction(exit));

                instructions.add(exit);

            }
        } else {
            throw new CompilationError("Internal Error!!");
        }
    }
}
