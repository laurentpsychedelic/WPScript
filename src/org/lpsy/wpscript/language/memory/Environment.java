package org.lpsy.wpscript.language.memory;

import java.util.HashMap;
import java.util.Set;
import org.lpsy.wpscript.language.executable.builtintypes.Numeric;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class Environment {
    private HashMap<String, Object> memory = new HashMap();
    public HashMap<String, Object> compilation_map = new HashMap();
    public Environment parent = null;
    public Environment() {
        this(null);
    }
    public Environment(Environment _parent) {
        parent = _parent;
    }
    public boolean containsEntry(String entry) {
        return memory.containsKey(entry);
    }
    public Object getValue(String entry) {
        return memory.get(entry);
    }
    public Set<String> getEntries() {
        return memory.keySet();
    }
    public void addCompilationMapEntry(String entry, Object value) {
        compilation_map.put(entry, value);
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
	for (String key : memory.keySet())
	    System.out.println(key+"->"+memory.get(key));
	System.out.println("---COMPILATION MAP DUMP---");
	for (String key : compilation_map.keySet())
	    System.out.println(key+"->"+compilation_map.get(key));
    }
}
