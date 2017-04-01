package cn.edu.sjtu.songyuke.mental.type;

/**
 * Created by Songyu on 16/3/29.
 */
public class Int extends TypeBase {
    @Override
    public String toString() {
        return "int";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Int;
    }
}
