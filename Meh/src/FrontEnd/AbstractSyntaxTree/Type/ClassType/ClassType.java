package FrontEnd.AbstractSyntaxTree.Type.ClassType;

import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberFunction;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberVariable;
import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tan on 3/31/17.
 */
public class ClassType extends Type {
    public String name;
    public Map<String, MemberVariable> memberVariables;
    public Map<String, MemberFunction> memberFunctions;
    Function constructor;

    public ClassType(String name) {
        this.name = name;
        memberFunctions = new HashMap<>();
        memberFunctions = new HashMap<>();

    }

    public boolean compatibleWith(Type other) {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
