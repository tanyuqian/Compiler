package FrontEnd.AbstractSyntaxTree.Type.ClassType.Member;

import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import FrontEnd.AbstractSyntaxTree.Type.Type;

/**
 * Created by tan on 3/31/17.
 */
public class MemberFunction extends Member {
    public Function function;

    public MemberFunction(String name, Function function) {
        super(name);
        this.function = function;
    }
}
