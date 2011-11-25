/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable;

import java.util.LinkedList;
import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;
import language.memory.Environment;

/**
 *
 * @author LFabre
 */
public class FunctionDeclaration extends Calculable {
    String name;
    LinkedList <Object> params;
    Expression calculation;
    Environment local_env;

    public FunctionDeclaration(ScriptParser _interpreter, String _name, LinkedList <Object> _params, Expression _calculation) {
        name = _name;
        params = _params;
        calculation = _calculation;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
	local_env = new Environment(_interpreter.env);
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
	for (Object param : params) {
	    if (param instanceof Variable) {
		local_env.addEntry(((Variable) param).getName(), null);
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
    public void compilationCheck() throws CompilationErrorException, PanicException {
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
