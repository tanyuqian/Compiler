package FrontEnd.AbstractSyntaxTree.Type.ClassType.Member;

import FrontEnd.AbstractSyntaxTree.Expression.Expression;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 4/2/17.
 */
public class MemberVariable extends Member {
    public Type type;
    public Expression expression;
    public int offset;

    public MemberVariable(String name, Type type) {
        super(name);
        this.type = type;
        this.offset = origin.allocateSize;
        origin.allocateSize += type.size();
    }
}
