package FrontEnd.AbstractSyntaxTree.Type.ClassType;

import Environment.Scope;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.Member;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberFunction;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.Member.MemberVariable;
import FrontEnd.AbstractSyntaxTree.Type.Type;
import Utility.CompilationError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tan on 3/31/17.
 */
public class ClassType extends Type implements Scope {
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

    public void addMember(Type type, String name) {
        if (checkExisted(name)) {
            throw new CompilationError("class addMember gg!!");
        }
        if (type instanceof Function) {
            // waiting...
        } else {
            MemberVariable t = new MemberVariable(name, type);
            memberVariables.put(name, t);
        }
    }

    public void addConstructor(Function function) {
        constructor = function;
    }

    public boolean checkExisted(String name) {
        return memberFunctions.containsKey(name) || memberVariables.containsKey(name);
    }
}
