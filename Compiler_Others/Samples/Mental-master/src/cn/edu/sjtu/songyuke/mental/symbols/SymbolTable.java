package cn.edu.sjtu.songyuke.mental.symbols;

import cn.edu.sjtu.songyuke.mental.type.*;
import cn.edu.sjtu.songyuke.mental.type.MxString;
import cn.edu.sjtu.songyuke.mental.type.Void;

import java.util.HashMap;

/**
 * Created by Songyu on 16/3/29.
 */
public class SymbolTable {
    public static final Int mentalInt = new Int();
    public static final MxString MENTAL_M_STRING = new MxString();
    public static final Bool mentalBool = new Bool();
    public static final Void mentalVoid = new Void();
    public static final Null mentalNull = new Null();
    public static final UnknownType mentalUnknownType = new UnknownType();
    public static final int maxLayer = 2147483647;
    private HashMap<java.lang.String, SymbolBase> table;
    public int stackLayer;
    public SymbolTable() {
        this.stackLayer = 0;
        this.table = new HashMap<>();
        SymbolType symbolInt = new SymbolType();

        symbolInt.setType(mentalInt);
        symbolInt.stackLayer = maxLayer;
        this.table.put("int", symbolInt);

        SymbolType symbolString = new SymbolType();
        symbolString.setType(MENTAL_M_STRING);
        symbolString.stackLayer = maxLayer;
        this.table.put("string", symbolString);

        SymbolType symbolBool = new SymbolType();
        symbolBool.setType(mentalBool);
        symbolBool.stackLayer = maxLayer;
        this.table.put("bool", symbolBool);

        SymbolType symbolVoid = new SymbolType();
        symbolVoid.setType(mentalVoid);
        symbolVoid.stackLayer = maxLayer;
        this.table.put("void", symbolVoid);
    }
    public SymbolTable(SymbolTable other) {
        this.stackLayer = other.stackLayer;
        this.table = new HashMap<>(other.table);
    }
    public void add(java.lang.String id, SymbolBase symbol) {
        if (this.table.get(id) != null) {
            if (this.table.get(id).stackLayer < symbol.stackLayer) {
                this.table.replace(id, symbol);
            } else {
                System.err.println("fatal: cannot override the identifier `" + id + "`.");
                System.exit(1);
            }
        } else {
            this.table.put(id, symbol);
        }
    }
    public HashMap<java.lang.String, SymbolBase> getTable() {
        return this.table;
    }
    public SymbolBase getSymbol(java.lang.String id) {
        return this.table.get(id);
    }
}
