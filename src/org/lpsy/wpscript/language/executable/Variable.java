package org.lpsy.wpscript.language.executable;

import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class Variable extends Calculable {
    private String name;
    public String getName() {
	return name;
    }
    public Variable(ScriptParser _interpreter, String _name) {
        name = _name;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
    }

    @Override
    public String toString() {
        return "VAR:: " + name;
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
	if (!env.compilation_map.containsKey(name)
	    && !ScriptParser.env_const.containsEntry(name)) {
            throw new CompilationErrorException("Variable used before being defined [" + name + "]", line_number);
        }
    }

    @Override
    public Object eval() throws RuntimeErrorException {
        Object val = env.getValue(this.name);
        if (val == null) {
	    val = ScriptParser.env_const.getValue(this.name);
	    if (val==null) {
		interpreter.runtimeError("Variable unknown [" + name + "]", line_number);
	    }
        }
        return val;
    }

    @Override
    public Calculable getSimplifiedCalculable() {
        return this;
    }

}
