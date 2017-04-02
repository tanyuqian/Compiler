package Environment;

import FrontEnd.AbstractSyntaxTree.Program;

/**
 * Created by tan on 4/2/17.
 */
public class Environment {
    public static Program program;
    public static SymbleTable symbleTable;

    public static void initialize() {
        program = new Program();
        symbleTable = new SymbleTable();
    }
}
