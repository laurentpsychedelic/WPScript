package org.lpsy.wpscript.language.executable;

import java.util.LinkedList;
import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.executable.builtintypes.Numeric;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class VariableAssignment extends Calculable {
    String var_name;
    public String getVariableName() {
        return var_name;
    }
    Calculable expression;
    public VariableAssignment(ScriptParser _interpreter, String _var_name, Operator _increment_decrement_operator) {
        LinkedList<Object> elements = new LinkedList();
        elements.add(new Variable(_interpreter, _var_name));
        if (_increment_decrement_operator == Operator.OPERATOR_PLUS_PLUS) {
	    elements.add(Operator.OPERATOR_PLUS);
	} else if (_increment_decrement_operator == Operator.OPERATOR_MINUS_MINUS) {
	    elements.add(Operator.OPERATOR_MINUS);
	}
        elements.add(new Numeric(1.0f));
        Term _expression = new Term(_interpreter, elements);
        _init(_interpreter, _var_name,  _expression);
        line_number = interpreter.getLineNumber();
    }
    public VariableAssignment(ScriptParser _interpreter, String _var_name, Calculable _expression) {
        _init(_interpreter, _var_name,  _expression);
        line_number = interpreter.getLineNumber();
    }
    public VariableAssignment(ScriptParser _interpreter, int _line_number, String _var_name, Calculable _expression) {
        _init(_interpreter, _var_name, _expression);
        line_number = _line_number;
    }

    private void _init(ScriptParser _interpreter, String _var_name, Calculable _expresion) {
        expression = _expresion;
        var_name = _var_name;
        interpreter = _interpreter;
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        Object ret_val = expression.eval();
        env.addEntry(var_name, ret_val);
        return ret_val;
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
	expression.setEnv(_env);
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        env.compilation_map.put(var_name, null);
        expression.compilationCheck();
    }

    @Override
    public String toString() {
        return "VAR_ASSIGN:: [VAR:" + var_name + "] <- [EXPR:" + expression.toString() + "]";
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        Calculable new_expression = expression.getSimplifiedCalculable();
        return new VariableAssignment(interpreter, line_number, var_name, new_expression);
    }

}
