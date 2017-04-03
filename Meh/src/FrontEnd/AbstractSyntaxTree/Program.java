package FrontEnd.AbstractSyntaxTree;

import FrontEnd.AbstractSyntaxTree.Statement.VariableDeclarationStatement;
import FrontEnd.AbstractSyntaxTree.Type.ClassType.ClassType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class Program {
    public List<Function> functions;
    public List<VariableDeclarationStatement> globalVariables;
    public List<ClassType> classTypes;

    public Program() {
        functions = new ArrayList<>();
        globalVariables = new ArrayList<>();
        classTypes = new ArrayList<>();
    }

    public void addClassType(ClassType node) {
        classTypes.add(node);
    }

    public void addFunction(Function node) {
        functions.add(node);
    }
}
