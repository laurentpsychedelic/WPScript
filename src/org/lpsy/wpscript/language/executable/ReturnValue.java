package org.lpsy.wpscript.language.executable;

import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * Return types other than real return (like "break", "continue"
 * It is needed to allow returning value through nested loop structures with "break" etc.
 * @author Laurent FABRE, 2011-2015
 */
public class ReturnValue extends Calculable {
    private enum Type {
        RETURN_BREAK("BREAK"), RETURN_CONTINUE("CONTINUE"), RETURN_RETURN("RETURN"), RETURN_NULL("VOID");
        private final String string;
        private Type(String _string) {
            string = _string;
        }
    }
    private final Type type;
    public static final ReturnValue RETURN_BREAK = new ReturnValue(Type.RETURN_BREAK);
    public static final ReturnValue RETURN_CONTINUE = new ReturnValue(Type.RETURN_CONTINUE);
    public static final ReturnValue RETURN_RETURN = new ReturnValue(Type.RETURN_RETURN);
    public static final ReturnValue RETURN_NULL = new ReturnValue(Type.RETURN_NULL);
    private ReturnValue(Type _type) {
        type = _type;
    }

    @Override
    public void setEnv(Environment _env) {
        //NOTHING
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        //NOTHING
    }

    @Override
    public String toString() {
        return type.string;
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        return this;
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        return this;
    }
}
