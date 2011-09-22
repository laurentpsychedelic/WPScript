/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public class IfExpression extends Calculable {
    Calculable condition;
    Expression calculation_if;
    Expression calculation_else;
    public IfExpression(GrammarParser _interpreter, Calculable _condition, Expression _calculation_if, Expression _calculation_else) {
        _init(_interpreter, _condition, _calculation_if, _calculation_else);
        line_number = interpreter.line_number;
    }
    public IfExpression(GrammarParser _interpreter, int _line_number, Calculable _condition, Expression _calculation_if, Expression _calculation_else) {
        _init(_interpreter, _condition, _calculation_if, _calculation_else);
        line_number = _line_number;
    }
    
    private void _init(GrammarParser _interpreter, Calculable _condition, Expression _calculation_if, Expression _calculation_else) {
        condition = _condition;
        calculation_if = _calculation_if;
        calculation_else = _calculation_else;
        interpreter = _interpreter;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
        condition.compilationCheck();
        if (calculation_if==null && calculation_else==null) {
            interpreter._WPAScriptCompilationError("Both IF and ELSE block are null!", line_number);
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
    public Object eval() {
        Object cond = condition.eval();
        if (!(cond instanceof Bool)) {
            interpreter._WPAScriptRuntimeError("Condition must be an instance of BOOL [" + cond.getClass() + "]", line_number);
        }
        if ((Boolean)((Bool)cond).getNativeValue()) {
            if (calculation_if!=null) {
                calculation_if.eval();
            }
        } else {
            if (calculation_else!=null) {
                calculation_else.eval();
            }
        }
        return null;
    }

    @Override
    public Calculable getSimplifiedCalculable() {
        Calculable new_condition = (Calculable) condition.getSimplifiedCalculable();
        Expression new_calculation_if = (Expression) calculation_if.getSimplifiedCalculable();
        Expression new_calculation_else = (Expression) calculation_else.getSimplifiedCalculable();
        return new IfExpression(interpreter, line_number, new_condition, new_calculation_if, new_calculation_else);
    }

}
