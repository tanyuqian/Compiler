package BackEnd.Allocator.RegisterAllocator;

import BackEnd.ControlFlowGraph.Operand.VirtualRegister.VirtualRegister;
import BackEnd.Translator.NASM.PhysicalRegistor;

import java.util.*;

/**
 * Created by tan on 5/29/17.
 */
public class ChaitinGraphColoring {
    public InterferenceGraph interferenceGraph;
    public Map<VirtualRegister, PhysicalRegistor> mapping;
    public Map<VirtualRegister, Integer> degree;
    public Set<VirtualRegister> leftVertices;

    public ChaitinGraphColoring(InterferenceGraph interferenceGraph) {
        this.interferenceGraph = interferenceGraph;
        mapping = new HashMap<>();
        degree = new HashMap<>();
        leftVertices = new HashSet<>();
    }

    public Map<VirtualRegister, PhysicalRegistor> analysis() {
        for (VirtualRegister register : interferenceGraph.vertices) {
            leftVertices.add(register);
            degree.put(register, interferenceGraph.forbids.get(register).size());
        }
        Stack<VirtualRegister> stack = new Stack<>();
        while (stack.size() < interferenceGraph.vertices.size()) {
            boolean modified = false;
            for (VirtualRegister vertice : leftVertices) {
                if (degree.get(vertice) < InterferenceGraph.colors.size()) {
                    stack.add(vertice);
                    remove(vertice);
                    modified = true;
                    break;
                }
            }
            if (!modified) {
                for (VirtualRegister vertice : leftVertices) {
                    if (degree.get(vertice) >= InterferenceGraph.colors.size()) {
                        stack.add(vertice);
                        remove(vertice);
                        break;
                    }
                }
            }
        }
        while (!stack.empty()) {
            putColor(stack.pop());
        }
        return mapping;
    }

    public void remove(VirtualRegister vertice) {
        leftVertices.remove(vertice);
        for (VirtualRegister v : interferenceGraph.forbids.get(vertice)) {
            degree.put(v, degree.get(v) - 1);
        }
    }

    public void putColor(VirtualRegister vertice) {
        Set<PhysicalRegistor> used = new HashSet<>();
        for (VirtualRegister v : interferenceGraph.forbids.get(vertice)) {
            if (mapping.containsKey(v)) {
                used.add(mapping.get(v));
            }
        }
        for (VirtualRegister v : interferenceGraph.recommend.get(vertice)) {
            if (mapping.containsKey(v)) {
                PhysicalRegistor color = mapping.get(v);
                if (!mapping.containsKey(vertice) && !used.contains(color)) {
                    mapping.put(vertice, color);
                    break;
                }
            }
        }
        for (PhysicalRegistor color : InterferenceGraph.colors) {
            if (!mapping.containsKey(vertice) && !used.contains(color)) {
                mapping.put(vertice, color);
                break;
            }
        }
    }
}
