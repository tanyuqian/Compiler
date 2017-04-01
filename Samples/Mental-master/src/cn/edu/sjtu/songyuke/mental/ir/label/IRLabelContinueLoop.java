package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/27.
 */
public class IRLabelContinueLoop extends IRLabel {
    public static int nextContinueLoopLabel;
    public IRLabelContinueLoop() {
        this.labelID = nextContinueLoopLabel++;
    }

    @Override
    public String toString() {
        return "_continue_loop" + Integer.toString(this.labelID);
    }
}
