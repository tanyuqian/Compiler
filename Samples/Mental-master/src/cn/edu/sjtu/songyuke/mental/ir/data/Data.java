package cn.edu.sjtu.songyuke.mental.ir.data;

import cn.edu.sjtu.songyuke.mental.ir.label.IRLabelGlobalData;

/**
 * Created by Songyu on 16/4/19.
 */
public abstract class Data {
    public int registerName;
    public int stackShift;
    public IRLabelGlobalData globalDataLabel;
    public int globalID;
    public int refCount;
    public Data() {
        this.registerName = -1;
        this.stackShift = 2147483647 >> 1;
        this.globalDataLabel = null;
        this.globalID = -1;
        this.refCount = 0;
    }

    public String toRegister() {
        if (this.registerName == -1) {
            throw new RuntimeException("error register name.");
        }
        return "$" + Integer.toString(this.registerName);
    }
    public String toAddress() {
        // basic data class cannot call this function.
        throw new RuntimeException(this.toString());
    }
}
