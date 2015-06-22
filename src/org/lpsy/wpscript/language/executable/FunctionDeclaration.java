package org.lpsy.wpscript.language.executable;

import java.util.LinkedList;
import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class FunctionDeclaration extends Calculable {
    String name;
    LinkedList <Object> params;
    Expression calculation;
    Environment compilation_env = new Environment(null);

    public FunctionDeclaration(ScriptParser _interpreter, String _name, LinkedList <Object> _params, Expression _calculation) {
        name = _name;
        params = _params;
        calculation = _calculation;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
    }

    public int getNumberOfArguments() {
	return params.size();
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
	for (Object param : params) {
	    if (param instanceof Variable) {
		env.addEntry(((Variable) param).getName(), null);
	    } else {
		interpreter.runtimeError("Parameters of variables declarations must be variable names [" + param.getClass() + "]", line_number);
	    }
	}
        interpreter.functions_env.addEntry(name, this);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("FUNCTION DECLARATION:: ");
	str.append(name);
        str.append("(");
        int size = params.size();
        for (int k=0; k<size; k++) {
            str.append(params.get(k));
            if (k != size-1) {
                str.append(", ");
            }
        }
        str.append(") {\n");
        str.append(calculation.toString());
	str.append("}");
        return str.toString();
    }

    @Override
    public void setEnv(Environment _env) {
	env = new Environment(_env);
	calculation.setEnv(env);
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
	for (Object param : params) {
	    if (param instanceof Variable) {
		env.compilation_map.put(((Variable) param).getName(), null);
	    } else {
		interpreter.compilationError("Function parameters must be variables [" + param.getClass() + "]", line_number);
	    }
	}
        interpreter.functions_env.compilation_map.put(name, this);
        calculation.compilationCheck();
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
	LinkedList <Object> new_params = new LinkedList();
	for (Object param : params) {
	    if (param instanceof Calculable) {
		param = ((Calculable) param).getSimplifiedCalculable();
	    }
	    new_params.add(param);
	}
	params = new_params;
        Expression new_calculation = (Expression) (calculation.getSimplifiedCalculable());
	calculation = new_calculation;
        return this;
    }
}
