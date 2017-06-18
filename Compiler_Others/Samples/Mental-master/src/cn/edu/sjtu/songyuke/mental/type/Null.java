package cn.edu.sjtu.songyuke.mental.type;

/**
 * Created by Songyu on 16/3/31.
 */
public class Null extends TypeBase {
    @Override
    public String toString() {
        return "null-pointer";
    }
    @Override
    public boolean equals(Object other) {
        if (other != null) {
            if (other instanceof Null || other instanceof Array || other instanceof Class) {
                return true;
            }
        }
        return false;
    }
}
