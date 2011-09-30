/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable;

import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;

/**
 *
 * @author laurent
 */
public abstract class Calculable {
    public ScriptParser interpreter;
    public abstract void compilationCheck() throws CompilationErrorException, PanicException;
    @Override
    public abstract String toString();
    public abstract Object eval() throws PanicException, RuntimeErrorException;
    public abstract Calculable getSimplifiedCalculable() throws PanicException;
    protected int line_number = -1;
}
