/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.memory;

import java.util.HashMap;
import java.util.Set;
import language.executable.builtintypes.Numeric;

/**
 *
 * @author laurent
 */
public class Environment {
    private HashMap memory = new HashMap();
    public Environment parent = null;
    public Environment(Environment _parent) {
        parent = _parent;
        _addConstants();
    }
    public boolean containsEntry(String entry) {
        return memory.containsKey(entry);
    }
    public Object getValue(String entry) {
        return memory.get(entry);
    }
    public Set getEntries() {
        return memory.keySet();
    }
    public void addEntry(String entry, Object value) {
        memory.put(entry, value);
    }
    public void clear() {
        memory.clear();
	_addConstants();
    }
    private void _addConstants() {
	memory.put("PI", new Numeric(Math.PI));
	memory.put("e", new Numeric(Math.E));
    }
}
