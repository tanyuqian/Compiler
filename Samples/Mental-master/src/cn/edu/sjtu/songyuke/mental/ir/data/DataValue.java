package cn.edu.sjtu.songyuke.mental.ir.data;

/**
 * Created by Songyu on 16/5/2.
 */
public class DataValue extends Data {
    public DataValue() {
        this.stackShift = 2147483647 >> 1;
        this.globalDataLabel = null;
        this.globalID = -1;
        this.registerName = -1;
        this.refCount = 0;
    }

    public String toAddress() {
        if (this.globalDataLabel == null) {
            if (this.stackShift != 2147483647 >> 1) {
                return String.format("%d($fp)", -4 * this.stackShift);
            } else {
                throw new RuntimeException("invalid stack shift.");
            }
        } else {
            return this.globalDataLabel.toString();
        }
    }
}
