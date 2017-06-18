package cn.edu.sjtu.songyuke.mental.ast.declarations;

import cn.edu.sjtu.songyuke.mental.ast.BaseNode;
import cn.edu.sjtu.songyuke.mental.symbols.SymbolType;
import cn.edu.sjtu.songyuke.mental.type.Class;
import cn.edu.sjtu.songyuke.mental.type.ClassMember;

import java.util.Map;

/**
 * Created by Songyu on 16/3/30.
 */
public class ClassDeclaration extends Declaration {
    public SymbolType classDetail;
    public ClassDeclaration() {
        this.classDetail = null;
    }
    @Override
    public String toPrintString(int indent) {
        Class classBody = (Class) this.classDetail.type;
        String ret = BaseNode.addIndent(indent) + "<begin>class:" + classBody.className + '\n';

        for (Map.Entry<String, ClassMember> entry : classBody.classComponents.entrySet()) {
            ret += BaseNode.addIndent(indent + 1) + "[" + entry.getKey() + "@" + entry.getValue().toString() + "]\n";
        }
        ret += BaseNode.addIndent(indent) + "<end>class";
        return ret;
    }
    @Override
    public String toPrettyPrint(int indent) {
        Class classBody = (Class) this.classDetail.type;
        String ret = BaseNode.addIndent(indent) + "class " + classBody.className + "{\n";

        for (Map.Entry<String, ClassMember> entry : classBody.classComponents.entrySet()) {
            ret += BaseNode.addIndent(indent + 1) + entry.getValue().memberType.toString() + " " + entry.getKey() + ";\n";
        }
        ret += BaseNode.addIndent(indent) + "}\n";
        return ret;
    }
    @Override
    public String toString() {
        return "<class>" + this.classDetail.type.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof ClassDeclaration) {
                if (this.classDetail.equals(((ClassDeclaration) other).classDetail)) {
                    return true;
                }
            }
        }
        return false;
    }
}
