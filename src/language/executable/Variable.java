/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable;

import language.exceptions.CompilationErrorException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;
/**
 *
 * @author laurent
 */
public class Variable extends Calculable {
    private String name;

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
    public void compilationCheck() throws CompilationErrorException {
        if (!interpreter.compilation_env.containsEntry(name)) {
            throw new CompilationErrorException("Variable used before being defined [" + name + "]", line_number);
        }
    }

    @Override
    public Object eval() throws RuntimeErrorException {
        Object val = interpreter.env.getValue(this.name);
        if (val == null) {
            interpreter.runtimeError("Variable unknown [" + name + "]", line_number);
        }
        return val;
    }

    @Override
    public Calculable getSimplifiedCalculable() {
        return this;
    }

}
