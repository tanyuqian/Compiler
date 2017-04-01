package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/27.
 */
public class IRLabelEndIf extends IRLabel {
    public static int nextEndIfLabel = 0;
    public IRLabelEndIf() {
        this.labelID = nextEndIfLabel++;
    }

    @Override
    public String toString() {
        return "_end_if_" + Integer.toString(this.labelID);
    }
}
