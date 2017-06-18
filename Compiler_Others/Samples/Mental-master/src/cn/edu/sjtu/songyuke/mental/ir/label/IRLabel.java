package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/27.
 */
public class IRLabel {
    public static int nextLabel = 0;
    public int labelID;
    public IRLabel() {
        this.labelID = nextLabel++;
    }

    @Override
    public String toString() {
        return "_uniform_" + Integer.toString(this.labelID);
    }
}
