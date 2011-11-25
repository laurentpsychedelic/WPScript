/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable;

import language.exceptions.CompilationErrorException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;
import language.memory.Environment;

/**
 *
 * @author laurent
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
