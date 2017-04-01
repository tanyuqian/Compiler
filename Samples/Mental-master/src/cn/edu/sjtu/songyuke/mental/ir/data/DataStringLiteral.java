package cn.edu.sjtu.songyuke.mental.ir.data;

/**
 * Created by Songyu on 16/5/3.
 */
public class DataStringLiteral extends DataAddress {
    public String litertal;
    public DataStringLiteral() {
        this.registerName = -1;
        this.litertal = null;
        this.globalDataLabel = null;
        this.stackShift = 2147483647 >> 1;
    }
    public DataStringLiteral(String litertal) {
        this.registerName = -1;
        this.litertal = litertal;
        this.globalDataLabel = null;
        this.stackShift = 2147483647 >> 1;
    }

    @Override
    public String toAddress() {
        return this.globalDataLabel.toString();
    }
}
