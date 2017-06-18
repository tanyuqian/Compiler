package cn.edu.sjtu.songyuke.mental.type;

/**
 * Created by Songyu on 16/3/29.
 */
public class Bool extends TypeBase {
    public String toString() {
        return "bool";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Bool;
    }
}
