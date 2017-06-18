package Compiler.Utility;

public class Triple<A, B, C> implements Cloneable {
	public A first;
	public B second;
	public C third;

	public Triple(A first, B second, C third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
}
