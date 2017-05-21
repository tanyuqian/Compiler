package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import BackEnd.ControlFlowGraph.Instruction.MemoryInstruction.LoadInstruction;
import BackEnd.ControlFlowGraph.Operand.Address;
import BackEnd.ControlFlowGraph.Operand.ImmediateValue;
import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import Environment.Environment;
import Environment.Symbol;
import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.StringType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.Member;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberFunction;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberVariable;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;
import com.sun.java.accessibility.util.EventID;

import java.util.List;

/**
 * Created by tan on 4/1/17.
 */
public class FieldExpression extends Expression {
    public Expression expression;
    public String field;

    public FieldExpression(Type type, boolean isLeftValue, String field, Expression expression) {
        super(type, isLeftValue);
        this.field = field;
        this.expression = expression;
    }

    public static Expression getExpression(Expression expression, String name) {
        if (expression.type instanceof ClassType) {
            ClassType classType = (ClassType)expression.type;
            Member member = classType.getMember(name);
            if (member instanceof MemberVariable) {
                return new FieldExpression(((MemberVariable)member).type, expression.isLeftValue, name, expression);
            } else if (member instanceof MemberFunction) {
                return new FieldExpression(((MemberFunction)member).function, expression.isLeftValue, name, expression);
            }
            throw new CompilationError("Internal Error.");
        } else if (expression.type instanceof ArrayType) {
            if (name.equals("size")) {
                return new FieldExpression(
                        Environment.symbolTable.get("__builtin_getArraySize").type,
                        expression.isLeftValue, name, expression);
            }
        } else if (expression.type instanceof StringType) {
            if (name.equals("length")) {
                return new FieldExpression(
                        Environment.symbolTable.get("__builtin_getStringLength").type,
                        expression.isLeftValue, name, expression
                );
            } else if (name.equals("substring")) {
                return new FieldExpression(
                        Environment.symbolTable.get("__builtin_getSubstring").type,
                        expression.isLeftValue, name, expression
                );
            } else if (name.equals("parseInt")) {
                return new FieldExpression(
                        Environment.symbolTable.get("__builtin_parseInt").type,
                        expression.isLeftValue, name, expression
                );
            } else if (name.equals("ord")) {
                return new FieldExpression(
                        Environment.symbolTable.get("__builtin_ord").type,
                        expression.isLeftValue, name, expression
                );
            }
        }
        throw new CompilationError("Internal Error.");
    }

    @Override
    public void emit(List<Instruction> instructions) {
        if (expression.type instanceof ClassType) {
            ClassType classType = (ClassType)expression.type;
            Member member = classType.getMember(field);
            if (member instanceof MemberVariable) {
                MemberVariable memberVariable = (MemberVariable)member;
                expression.emit(instructions);
                expression.load(instructions);
                VirtualRegister base = (VirtualRegister)expression.operand;
                ImmediateValue offset = new ImmediateValue(memberVariable.offset);
                operand = new Address(base, offset, memberVariable.type.size());
            }
        }
    }

    @Override
    public void load(List<Instruction> instructions) {
        if (operand instanceof Address) {
            Address address = (Address)operand;
            operand = Environment.registerTable.addTemporaryRegister();
            instructions.add(LoadInstruction.getInstruction(operand, address));
        }
    }
}
