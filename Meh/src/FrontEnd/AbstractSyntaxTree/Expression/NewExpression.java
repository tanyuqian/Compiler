package FrontEnd.AbstractSyntaxTree.Expression;

import BackEnd.ControlFlowGraph.Instruction.Instruction;
import FrontEnd.AbstractSyntaxTree.Type.ArrayType;
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
        // waiting to be done...
    }
}
