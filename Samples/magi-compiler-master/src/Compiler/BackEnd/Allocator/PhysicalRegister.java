package Compiler.BackEnd.Allocator;

public abstract class PhysicalRegister {
	public int identity;
	public String name;

	protected PhysicalRegister(int identity, String name) {
		this.identity = identity;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
