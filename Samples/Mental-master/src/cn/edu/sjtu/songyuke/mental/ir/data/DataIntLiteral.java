package cn.edu.sjtu.songyuke.mental.ir.data;

/**
 * Created by Songyu on 16/5/2.
 */
public class DataIntLiteral extends DataValue {
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    public static final int NULL = 0;
    public int literal;
    public DataIntLiteral() {
        this.literal = 0;
        this.registerName = -1;
        this.stackShift = 2147483647 >> 1;
        this.globalDataLabel = null;
    }
    public DataIntLiteral(int literal) {
        this.literal = literal;
        this.registerName = -1;
        this.stackShift = 2147483647 >> 1;
        this.globalDataLabel = null;
    }
}
