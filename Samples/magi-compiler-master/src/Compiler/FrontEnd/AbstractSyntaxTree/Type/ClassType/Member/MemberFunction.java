package Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.Member;

import Compiler.FrontEnd.AbstractSyntaxTree.Function;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;

public class MemberFunction extends Member {
	public Function function;

	public MemberFunction(ClassType classType, String name, Function function, boolean isProtected, boolean isPrivate) {
		super(name, isProtected, isPrivate);
		this.function = function;
	}
}
