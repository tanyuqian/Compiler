package FrontEnd.AbstractSyntaxTree.Type.ClassType.Member;

import Environment.Environment;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;

/**
 * Created by tan on 4/2/17.
 */
public abstract class Member {
    public String name;
    public ClassType origin;

    public Member(String name) {
        this.name = name;
        this.origin = Environment.scopeTable.classScopes.peek();
    }
}
