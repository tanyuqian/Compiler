package Utility;

/**
 * Created by tan on 4/2/17.
 */
public class CompilationError extends Error {
    public CompilationError() {
        System.out.println("CompilationError!!");
    }

    public CompilationError(String str) {
        System.out.println(str);
    }
}
