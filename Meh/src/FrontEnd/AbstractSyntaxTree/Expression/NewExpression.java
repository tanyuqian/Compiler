package FrontEnd.AbstractSyntaxTree.Expression;

import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.AdditionInstruction;
import BackEnd.ControlFlowGraph.Instruction.ArithmeticInstruction.BinaryInstruction.MultiplicationInstruction;
import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.AllocateInstruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.StoreInstruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.IntType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

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

    public static Expression getExpression(Type baseType, List<Expression> subscriptList) {
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
        } else if (type instanceof ArrayType) {
            ArrayType arrayType = (ArrayType)type;
            VirtualRegister size = Environment.registerTable.addTemporaryRegister();
            instructions.add(MultiplicationInstruction.getInstruction(size, subscripts.get(0).operand, new ImmediateValue(arrayType.reduce().size())));
            instructions.add(AdditionInstruction.getInstruction(size, size, new ImmediateValue(new IntType().size())));
            instructions.add(AllocateInstruction.getInstruction(operand, size));
            instructions.add(StoreInstruction.getInstruction(subscripts.get(0).operand, new Address((VirtualRegister)operand, new IntType().size())));
            instructions.add(AdditionInstruction.getInstruction(operand, operand, new ImmediateValue(new IntType().size())));
        } else {
            throw new CompilationError("Internal Error!!");
        }
    }
}
