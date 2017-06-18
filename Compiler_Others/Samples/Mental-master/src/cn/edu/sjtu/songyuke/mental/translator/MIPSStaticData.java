package cn.edu.sjtu.songyuke.mental.translator;

import cn.edu.sjtu.songyuke.mental.ir.data.Data;
import cn.edu.sjtu.songyuke.mental.ir.data.DataStringLiteral;
import cn.edu.sjtu.songyuke.mental.ir.data.DataValue;

import java.util.LinkedList;

/**
 * Created by Songyu on 16/4/29.
 */
public class MIPSStaticData {
    public LinkedList<String> mipsStatements;
    public MIPSStaticData() {
        this.mipsStatements = new LinkedList<>();
        this.mipsStatements.add("\t.data");
    }
    public void translate(Data data) {
        if (data instanceof DataValue) {
            if (((DataValue) data).globalDataLabel != null) {
//                mipsStatements.add("\t.data");
//                mipsStatements.add("\t.align 2");
                mipsStatements.add(((DataValue) data).globalDataLabel.toString() + ":");
                mipsStatements.add("\t.word 0");
            } else {
                throw new RuntimeException("not a static variable, cannot put it to static area.");
            }
        } else if (data instanceof DataStringLiteral) {
            // mipsStatements.add("\t.data");
            String str = ((DataStringLiteral) data).litertal;
            int realLength = str.length();
            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == '\\') {
                    if (i < str.length() - 1) {
                        if (str.charAt(i - 1) != '\\') {
                            if (str.charAt(i + 1) == 't'
                                    || str.charAt(i + 1) == 'n'
                                    || str.charAt(i + 1) == 't'
                                    || str.charAt(i + 1) == 'r'
                                    || str.charAt(i + 1) == '\"') {
                                realLength--;
                            }
                        }
                    }
                }
            }
            mipsStatements.add(String.format("\t.word %d", realLength - 2));
            mipsStatements.add(((DataStringLiteral) data).globalDataLabel.toString() + ":");
            mipsStatements.add(String.format("\t.asciiz %s", ((DataStringLiteral) data).litertal));
            mipsStatements.add("\t.align 2");
        }
    }
}
