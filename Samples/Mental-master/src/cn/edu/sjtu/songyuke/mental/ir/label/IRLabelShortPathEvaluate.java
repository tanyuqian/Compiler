package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/30.
 */
public class IRLabelShortPathEvaluate extends IRLabel {
    public static int nextShortPathEvaluateLabel = 0;
    public IRLabelShortPathEvaluate() {
        this.labelID = nextShortPathEvaluateLabel++;
    }

    @Override
    public String toString() {
        return "_short_evaluate_" + Integer.toString(this.labelID);
    }
}
