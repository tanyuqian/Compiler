package Compiler.Environment;

import Compiler.FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;
import Compiler.Utility.Error.CompilationError;

import java.util.*;

public class ClassTable {
	private Map<String, ClassType> classes;
	private Map<ClassType, List<ClassType>> relations;

	private int index;
	private Map<ClassType, Integer> inIndices, outIndices;

	public ClassTable() {
		classes = new HashMap<>();
		relations = new HashMap<>();
	}

	public void put(String name, ClassType type) {
		if (classes.containsKey(name)) {
			throw new CompilationError("the program cannot have two classes named \"" + name + "\"");
		}
		classes.put(name, type);
		relations.put(type, new ArrayList<>());
	}

	public ClassType get(String name) {
		return classes.get(name);
	}

	public boolean contains(String name) {
		return classes.containsKey(name);
	}

	public void extendsFrom(ClassType base, ClassType extend) {
		relations.get(extend).add(base);
	}

	public boolean isBaseOf(ClassType base, ClassType extend) {
		if (!relations.containsKey(base) || !relations.containsKey(extend)) {
			return false;
		}
		return inIndices.get(base) <= inIndices.get(extend) && outIndices.get(extend) <= outIndices.get(base);
	}

	private void depthFirstSearch(ClassType x) {
		inIndices.put(x, ++index);
		relations.get(x).forEach(y -> {
			if (!inIndices.containsKey(y)) {
				depthFirstSearch(y);
			}
		});
		outIndices.put(x, index);
	}

	public void analysis() {
		//	calculate the in-degree of all the vertex
		Map<ClassType, Integer> inDegrees = new HashMap<>();
		relations.forEach((x, edges) -> {
			if (!inDegrees.containsKey(x)) {
				inDegrees.put(x, 0);
			}
			edges.forEach(y -> {
				if (!inDegrees.containsKey(y)) {
					inDegrees.put(y, 0);
				}
			});
		});
		relations.forEach((x, edges) -> edges.forEach(y -> inDegrees.put(y, inDegrees.get(y) + 1)));
		//	put all the vertex whose in-degree is zero into the queue
		Queue<ClassType> queue = new LinkedList<>();
		inDegrees.forEach((x, inDegree) -> {
			if (inDegree == 0) {
				queue.add(x);
			}
		});
		List<ClassType> order = new ArrayList<>();
		while (!queue.isEmpty()) {
			ClassType x = queue.poll();
			order.add(x);
			relations.get(x).forEach(y -> {
				inDegrees.put(y, inDegrees.get(y) - 1);
				if (inDegrees.get(y) == 0) {
					queue.add(y);
				}
			});
		}
		//	loop check
		if (order.size() != relations.size()) {
			throw new CompilationError("loop exists in the class diagram");
		}
		//	calculate the connection information
		index = 0;
		inIndices = new HashMap<>();
		outIndices = new HashMap<>();
		order.forEach(x -> {
			if (!inIndices.containsKey(x)) {
				depthFirstSearch(x);
			}
		});
		//	handle the "extends" relations
		order.forEach(x -> relations.get(x).forEach(y -> y.extendsFrom(x)));
	}
}
