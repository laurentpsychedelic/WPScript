/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable.builtintypes;

import language.exceptions.PanicException;
import language.exceptions.RuntimeErrorException;
import language.executable.Calculable;
import language.memory.Environment;

/**
 *
 * @author laurent
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
