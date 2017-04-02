package Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by tan on 4/2/17.
 */
public class SymbleTable {
    public Map<String, Stack<Symble>> map;

    public SymbleTable() {
        map = new HashMap<>();
    }
}
