package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/27.
 */
public class IRLabelBeginLoop extends IRLabel {
    public static int nextBeginLoopLabel = 0;
    public IRLabelBeginLoop() {
        this.labelID = nextBeginLoopLabel++;
    }

    @Override
    public String toString() {
        return "_begin_loop_" + Integer.toString(this.labelID);
    }
}
