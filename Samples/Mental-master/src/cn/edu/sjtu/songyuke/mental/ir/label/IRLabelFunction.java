package cn.edu.sjtu.songyuke.mental.ir.label;

/**
 * Created by Songyu on 16/4/27.
 */
public class IRLabelFunction extends IRLabel {
    public String functionName;
    public IRLabelFunction() {
        this.functionName = "";
    }
    public IRLabelFunction(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String toString() {
        return "_func_" + functionName;
    }
}
