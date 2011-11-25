/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable;

import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;
import language.executable.builtintypes.Bool;
import language.memory.Environment;

/**
 *
 * @author laurent
 */
public class IfExpression extends Calculable {
    Calculable condition;
    Expression calculation_if;
    Expression calculation_else;
    public IfExpression(ScriptParser _interpreter, Calculable _condition, Expression _calculation_if, Expression _calculation_else) {
        _init(_interpreter, _condition, _calculation_if, _calculation_else);
        line_number = interpreter.getLineNumber();
    }
    public IfExpression(ScriptParser _interpreter, int _line_number, Calculable _condition, Expression _calculation_if, Expression _calculation_else) {
        _init(_interpreter, _condition, _calculation_if, _calculation_else);
        line_number = _line_number;
    }
    
    private void _init(ScriptParser _interpreter, Calculable _condition, Expression _calculation_if, Expression _calculation_else) {
        condition = _condition;
        calculation_if = _calculation_if;
        calculation_else = _calculation_else;
        interpreter = _interpreter;
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
	condition.setEnv(_env);
	calculation_if.setEnv(_env);
	calculation_else.setEnv(_env);
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        condition.compilationCheck();
        if (calculation_if==null && calculation_else==null) {
            interpreter.compilationError("Both IF and ELSE block are null!", line_number);
        }
        if (calculation_if != null) {
            calculation_if.compilationCheck();
        }
        if (calculation_else != null) {
            calculation_else.compilationCheck();
        }
    }

    @Override
    public String toString() {
        return "IF expression:: IF[" + condition.toString() + "] {" + (calculation_if!=null?calculation_if.toString():"null") + "} else {" + (calculation_else!=null?calculation_else.toString():"null") + "}";
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        Object cond = condition.eval();
        Object ret_val = null;
        if (!(cond instanceof Bool)) {
            interpreter.runtimeError("Condition must be an instance of BOOL [" + cond.getClass() + "]", line_number);
        }
        if ((Boolean)((Bool)cond).getNativeValue()) {
            if (calculation_if!=null) {
                ret_val = calculation_if.eval();
            }
        } else {
            if (calculation_else!=null) {
                ret_val = calculation_else.eval();
            }
        }
        return ret_val;
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        Calculable new_condition = (Calculable) condition.getSimplifiedCalculable();
        Expression new_calculation_if = (Expression) calculation_if.getSimplifiedCalculable();
        Expression new_calculation_else = calculation_else!=null ? (Expression) calculation_else.getSimplifiedCalculable() : null;
        return new IfExpression(interpreter, line_number, new_condition, new_calculation_if, new_calculation_else);
    }

}
