package cn.edu.sjtu.songyuke.mental.type;

/**
 * Created by Songyu on 16/4/25.
 */
public class ClassMember {
    public int memberID;
    public TypeBase memberType;
    public ClassMember() {
        this.memberID = -1;
        this.memberType = null;
    }
    public ClassMember(int id, TypeBase type) {
        this.memberID = id;
        this.memberType = type;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ClassMember) {
            if (((ClassMember) other).memberID == this.memberID) {
                if (((ClassMember) other).memberType.equals(this.memberType)) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "<member_id:" + Integer.toString(this.memberID) + ", type: " + this.memberType.toString() + ">";
    }
}
