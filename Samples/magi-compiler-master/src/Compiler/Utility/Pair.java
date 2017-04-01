package Compiler.Utility;

public class Pair<A, B> implements Cloneable {
	public A first;
	public B second;

	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}
}
