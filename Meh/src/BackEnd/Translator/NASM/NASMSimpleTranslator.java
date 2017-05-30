package BackEnd.Translator.NASM;

import BackEnd.Allocator.Allocator;
import BackEnd.ControlFlowGraph.Graph;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by tan on 5/29/17.
 */
public class NASMSimpleTranslator extends NASMTranslator {
    public Graph graph;
    public Allocator allocator;

    public NASMSimpleTranslator(PrintStream output) {
        super(output);
    }

    @Override
    public void translate(Graph graph) {

    }
}
