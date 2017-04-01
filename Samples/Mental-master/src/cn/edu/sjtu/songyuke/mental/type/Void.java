package cn.edu.sjtu.songyuke.mental.type;

/**
 * Created by Songyu on 16/3/29.
 */
public class Void extends TypeBase {
    @Override
    public String toString() {
        return "void";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Void;
    }
}
