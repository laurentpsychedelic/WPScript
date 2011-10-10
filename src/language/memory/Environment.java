/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.memory;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author laurent
 */
public class Environment {
    private HashMap memory = new HashMap();
    public Environment parent = null;
    public Environment(Environment _parent) {
        parent = _parent;
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
    }
}
