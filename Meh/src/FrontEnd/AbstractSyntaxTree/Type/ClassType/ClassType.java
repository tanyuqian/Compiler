package FrontEnd.AbstractSyntaxTree.Type.ClassType;

import Environment.Scope;
import FrontEnd.AbstractSyntaxTree.Function;
import FrontEnd.AbstractSyntaxTree.Type.BasicType.NullType;
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
    public Function constructor;
    public int allocateSize;

    public ClassType(String name) {
        this.name = name;
        memberFunctions = new HashMap<>();
        memberVariables = new HashMap<>();
        allocateSize = 0;
    }

    public boolean compatibleWith(Type other) {
        if (other instanceof NullType) {
            return true;
        }
        if (other instanceof ClassType) {
            if (((ClassType) other).name.equals(this.name)) {
                return true;
            }
        }
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
            Function function = (Function)type;
            function.name = this.name + "." + function.name;
            MemberFunction member = new MemberFunction(name, function);
            memberFunctions.put(name, member);
        } else {
            MemberVariable t = new MemberVariable(name, type);
            memberVariables.put(name, t);
        }
    }

    public Member getMember(String name) {
        Member result = null;
        if (memberFunctions.containsKey(name)) {
            result = memberFunctions.get(name);
        }
        if (memberVariables.containsKey(name)) {
            result = memberVariables.get(name);
        }
        if (result != null) {
            return result;
        } else {
            throw new CompilationError("class \"" + this.name + " \" has no member named \"" + name);
        }
    }

    public void addConstructor(Function function) {
        if (constructor != null) {
            throw new CompilationError("class \"" + name + "\" can only have one constructor function.");
        }
        constructor = function;
    }

    public boolean checkExisted(String name) {
        return memberFunctions.containsKey(name) || memberVariables.containsKey(name);
    }
}
