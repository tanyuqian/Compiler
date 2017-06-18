package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataIntLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataStringLiteral;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/27.
 */
public class MIPSMachine {
    public Data[] registerData;
    public int[] loadTime;
    public int[] updateTime;
    public int[] storeTime;
    public int globalTime;
    public int regLeft;
    public int regRight;
    public int useCount;
    public MIPSMachine() {
        this.loadTime = new int [32];
        this.updateTime = new int [32];
        this.storeTime = new int [32];
        this.registerData = new Data[32];
        this.globalTime = 1;
        this.regLeft = MIPSRegister.t0;
        this.regRight = MIPSRegister.t9;
        for (int i = 0; i < 32; ++i) {
            this.registerData[i] = null;
            this.loadTime[i] = -1;
            this.updateTime[i] = -1;
            this.storeTime[i] = -1;
        }
    }

    public int getFirstLoadRegister() {
        int res = this.regLeft;
        for (int i = this.regLeft + 1; i <= regRight; ++i) {
            if (this.loadTime[i] < this.loadTime[res]) {
                res = i;
            }
        }
        return res;
    }

    public String storeFirstLoadRegister() {
        int reg = getFirstLoadRegister();
        if (this.loadTime[reg] == -1) {
//            return "#store nothing";
            return "";
        }
        if (this.storeTime[reg] >= this.updateTime[reg]) {
//            return "#store nothing";
            return "";
        }
        if (this.registerData[reg] == null) {
            return "";
        } else {
            if (this.registerData[reg].globalID == -1 && this.registerData[reg].refCount == 0) {
                return "";
            }
        }
        this.storeTime[reg] = this.globalTime++;
        return String.format("\tsw $%d, %s", reg, this.registerData[reg].toAddress());
    }

    public void rewriteFirstLoadRegister(Data newData) {
        int reg = getFirstLoadRegister();
        if (this.registerData[reg] != null) {
            this.registerData[reg].registerName = -1;
            this.registerData[reg] = null;
        }
        this.registerData[reg] = newData;
        newData.registerName = reg;
        this.loadTime[reg] = this.globalTime++;
        this.updateTime[reg] = this.loadTime[reg];
        this.storeTime[reg] = -1;
    }

    public void clearSpecifiedRegister(int reg) {
        if (this.registerData[reg] != null) {
            this.registerData[reg].registerName = -1;
            this.registerData[reg] = null;
        }
        this.loadTime[reg] = -1;
        this.updateTime[reg] = -1;
        this.storeTime[reg] = -1;
    }

    public void rewriteSpecifiedRegister(int reg, Data newData) {
        if (this.registerData[reg] != null) {
            this.registerData[reg].registerName = -1;
            this.registerData[reg] = null;
        }
        this.registerData[reg] = newData;
        newData.registerName = reg;
        this.loadTime[reg] = this.globalTime++;
        this.updateTime[reg] = this.loadTime[reg];
        this.storeTime[reg] = -1;
    }

    public String replaceFirstLoadRegisterWithLoad(Data newData) {
        int reg = getFirstLoadRegister();

        if (this.registerData[reg] != null) {
            this.registerData[reg].registerName = -1;
            this.registerData[reg] = null;
        }

        this.registerData[reg] = newData;
        newData.registerName = reg;

        this.loadTime[reg] = this.globalTime++;
        this.updateTime[reg] = this.loadTime[reg];
        this.storeTime[reg] = this.loadTime[reg];
        if (newData instanceof DataIntLiteral) {
            return String.format("\tli $%d, %s", reg, ((DataIntLiteral) newData).literal);
        } else if (newData instanceof DataStringLiteral) {
            return String.format("\tla $%d, %s", reg, newData.toAddress());
        } else {
            return String.format("\tlw $%d, %s", reg, newData.toAddress());
        }
    }

    public void updateRegister(int registerName) {
        if (registerName == 0) {
            return;
        }
        if (this.registerData[registerName] == null) {
            throw new RuntimeException("cannot update an empty register.");
        }
        this.updateTime[registerName] = this.globalTime++;
    }

    public void refreshRegister(int registerName) {
        if (registerName == 0) {
            return;
        }
        if (this.registerData[registerName] == null) {
            throw new RuntimeException("cannot refresh an empty register.");
        }
        this.loadTime[registerName] = this.globalTime++;
    }

    public String storeAndCleanMachine() {
        LinkedList<String> storeInstructions = new LinkedList<>();
        for (int i = this.regLeft; i <= this.regRight; ++i) {
            if (this.registerData[i] != null) {
                if ((this.registerData[i] instanceof DataStringLiteral)
                        || (this.registerData[i] instanceof DataIntLiteral)) {
                    this.registerData[i].registerName = -1;
                    this.registerData[i] = null;
                    this.loadTime[i] = -1;
                    this.updateTime[i] = -1;
                    this.storeTime[i] = -1;
                    continue;
                }
                if (this.updateTime[i] > this.storeTime[i]) {
                    if (this.registerData[i].globalID != -1 || this.registerData[i].refCount > 0) {
                        storeInstructions.add(
                                String.format("\tsw $%d, %s", i, this.registerData[i].toAddress())
                        );
                    }
                }
                this.registerData[i].registerName = -1;
                this.registerData[i] = null;
                this.loadTime[i] = -1;
                this.updateTime[i] = -1;
                this.storeTime[i] = -1;
            }
        }
        String str = "";
        for (String statement : storeInstructions) {
            if (statement.length() > 0) {
                str += statement + "\n";
            }
        }
        if (str.length() == 0) {
            str = " ";
        }
        return str.substring(0, str.length() - 1);
    }
}
