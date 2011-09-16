/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

import java.util.LinkedList;

/**
 *
 * @author laurent
 */
public class IfExpression extends Calculable {
    Calculable condition;
    Expression calculation_if;
    Expression calculation_else;
    public IfExpression(GrammarParser _interpreter, Calculable _condition, Expression _calculation_if, Expression _calculation_else) {
        condition = _condition;
        calculation_if = _calculation_if;
        calculation_else = _calculation_else;
        interpreter = _interpreter;
        line_number = interpreter.line_number;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
        //NOTHING //TODO check variables existence
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
        if ((Boolean)((Bool)cond).eval()) {
            if (calculation_if!=null) {
                calculation_if.eval();
            } else {
                System.err.println("Nothing in IF block");
            }
        } else {
            if (calculation_else!=null) {
                calculation_else.eval();
            } else {
                System.err.println("Nothing in ELSE block");
            }
        }
        return null;
    }

}
