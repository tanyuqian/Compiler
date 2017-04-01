package FrontEnd.AbstractSyntaxTree.Type.ClassType.Member;

import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/31/17.
 */
public class MemberVariable {
    public Type type;
    public String name;

    public MemberVariable(Type type, String name) {
        this.type = type;
        this.name = name;
    }
}
