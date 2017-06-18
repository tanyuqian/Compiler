package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/27.
 */
public class IRLabelEndLoop extends IRLabel {
    public static int nextEndLoop = 0;
    public IRLabelEndLoop() {
        this.labelID = nextEndLoop++;
    }

    @Override
    public String toString() {
        return "_end_loop_" + Integer.toString(this.labelID);
    }
}
