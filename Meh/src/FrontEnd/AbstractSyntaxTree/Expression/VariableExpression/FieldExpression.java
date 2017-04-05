package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

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
                        Environment.symbolTable.get("__builin_parseInt").type,
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
}
