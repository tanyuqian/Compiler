package cn.edu.sjtu.songyuke.mental.symbols;

/**
 * Created by Songyu on 16/3/29.
 */
public class SymbolBase {
    public int stackLayer;
    public SymbolBase() {
        this.stackLayer = 0;
    }
    @Override
    public String toString() {
        return "SymbolBase";
    }
    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof SymbolBase;
    }
}
