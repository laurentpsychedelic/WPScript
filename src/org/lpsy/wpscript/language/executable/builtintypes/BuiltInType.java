package org.lpsy.wpscript.language.executable.builtintypes;

import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.executable.Calculable;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * @author Laurent FABRE, 2011-2015
 */
public abstract class BuiltInType extends Calculable {
    abstract public Object getNativeValue();
    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        return this;
    }
    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        return this;
    }
    @Override
    public void setEnv(Environment _env) {
	env = _env;
    }
}
