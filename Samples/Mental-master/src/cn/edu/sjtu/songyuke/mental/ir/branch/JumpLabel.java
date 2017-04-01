package cn.edu.sjtu.songyuke.mental.ir.branch;

import cn.edu.sjtu.songyuke.mental.ir.label.IRLabel;

/**
 * Created by Songyu on 16/4/25.
 */
public class JumpLabel extends Branch {
    public JumpLabel() {
        this.gotoLabel = null;
    }
    public JumpLabel(IRLabel gotoLabel) {
        this.gotoLabel = gotoLabel;
    }
}
