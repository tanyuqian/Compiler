package FrontEnd.AbstractSyntaxTree;

import FrontEnd.AbstractSyntaxTree.Type.Type;

import java.util.List;

/**
 * Created by tan on 3/30/17.
 */
public class Function extends Node {
    public Type type;
    public String name;
    public List<String> parameters;
}
