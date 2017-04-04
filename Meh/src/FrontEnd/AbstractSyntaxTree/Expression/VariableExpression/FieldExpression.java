package FrontEnd.AbstractSyntaxTree.Expression.VariableExpression;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.Member;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberFunction;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberVariable;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

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
        }
        throw new CompilationError("Internal Error.");
    }
}
