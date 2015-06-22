package org.lpsy.wpscript.language.memory;

import java.util.HashMap;
import java.util.Set;
import org.lpsy.wpscript.language.executable.builtintypes.Numeric;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class Environment {
    private HashMap memory = new HashMap();
    public HashMap compilation_map = new HashMap();
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
    public void addConstants() {
	memory.put("PI", new Numeric(Math.PI));
	memory.put("e", new Numeric(Math.E));
    }
    public void dump() {
	System.out.println("---ENV DUMP---");
	for (Object key : memory.keySet()) {
	    System.out.println(key+"->"+memory.get(key));
	}
	System.out.println("---COMPILATION MAP DUMP---");
	for (Object key : compilation_map.keySet()) {
	    System.out.println(key+"->"+compilation_map.get(key));
	}

    }
}
