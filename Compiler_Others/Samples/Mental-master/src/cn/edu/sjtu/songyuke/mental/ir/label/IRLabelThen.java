package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/27.
 */
public class IRLabelThen extends IRLabel {
    public static int nextThenLabel = 0;
    public IRLabelThen() {
        this.labelID = nextThenLabel++;
    }

    @Override
    public String toString() {
        return "_then_" + Integer.toString(this.labelID);
    }
}
