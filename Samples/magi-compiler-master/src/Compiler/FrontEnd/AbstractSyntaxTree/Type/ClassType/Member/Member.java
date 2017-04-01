package Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.Member;

import Compiler.Environment.Environment;
import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.Utility.Error.InternalError;

public abstract class Member {
	public String name;
	public ClassType origin;
	public boolean isProtected, isPrivate;

	Member(String name, boolean isProtected, boolean isPrivate) {
		if (isPrivate && isProtected) {
			throw new InternalError();
		}
		this.name = name;
		this.origin = Environment.scopeTable.getClassScope();
		this.isProtected = isProtected;
		this.isPrivate = isPrivate;
	}
}
