package cn.edu.sjtu.songyuke.mental.type;

import java.util.HashMap;

/**
 * Created by Songyu on 16/3/29.
 */
public class Class extends TypeBase {
    public String className;
    public HashMap<String, ClassMember> classComponents;
    public int classSize;
    public Class() {
        this.classComponents = new HashMap<>();
        this.className = "";
        this.classSize = 0;
    }
    public Class(Class other) {
        this.className = other.className;
        this.classComponents = new HashMap<>(other.classComponents);
        this.classSize = other.classSize;
    }
    public void setClassComponents(HashMap<String, ClassMember> types) {
        this.classComponents = types;
        this.className = "";
        this.classSize = types.size();
    }
    @Override
    public String toString() {
        return this.className;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof Null) {
                return true;
            }
            if (other instanceof Class) {
                if (this.className.equals(((Class) other).className)) {
                    if (this.classComponents.equals(((Class) other).classComponents)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
