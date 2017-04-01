package cn.edu.sjtu.songyuke.mental.type;

/**
 * Created by Songyu on 16/3/29.
 */
public class TypeBase {
    public TypeBase() { }
    @Override
    public String toString() {
        return "TypeBase";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof TypeBase;
    }
}

