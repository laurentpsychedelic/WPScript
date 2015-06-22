package org.lpsy.wpscript.language.executable;

import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * @author Laurent FABRE, 2011-2015
 */
public abstract class Calculable {
    public ScriptParser interpreter;
    public Environment env;
    public abstract void setEnv(Environment _env);
    public abstract void compilationCheck() throws CompilationErrorException, PanicException;
    @Override
    public abstract String toString();
    public abstract Object eval() throws PanicException, RuntimeErrorException;
    public abstract Calculable getSimplifiedCalculable() throws PanicException;
    protected int line_number = -1;
}
