package cn.edu.sjtu.songyuke.mental.type;

import cn.edu.sjtu.songyuke.mental.symbols.SymbolFunction;

/**
 * Created by Songyu on 16/4/1.
 */
public class Function extends TypeBase {
    public SymbolFunction functionHead;
    public Function() {
        this.functionHead = new SymbolFunction();
    }
    @Override
    public String toString() {
        return "function";
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null) {
            if (other instanceof Function) {
                if (this.functionHead.equals(((Function) other).functionHead)) {
                    return true;
                }
            }
        }
        return false;
    }
}
