/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable;

import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.exceptions.RuntimeErrorException;
import language.memory.Environment;

/**
 *
 * @author laurent
 */
public class ReturnValue extends Calculable {
    public static final ReturnValue RETURN_BREAK = new ReturnValue() {
        @Override
        public String toString() {
            return "BREAK";
        }
    };
    public static final ReturnValue RETURN_CONTINUE = new ReturnValue() {
        @Override
        public String toString() {
            return "CONTINUE";
        }
    };
    public static final ReturnValue RETURN_RETURN = new ReturnValue() {
        @Override
        public String toString() {
            return "RETURN";
        }
    };
    public static final ReturnValue RETURN_NULL = new ReturnValue() {
        @Override
        public String toString() {
            return "VOID";
        }
    };

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
        return toString();
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
