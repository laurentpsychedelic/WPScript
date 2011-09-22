/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

/**
 *
 * @author laurent
 */
public class WhileExpression extends Calculable {
    Calculable condition;
    Expression calculation;
    public WhileExpression(GrammarParser _interpreter, Calculable _condition, Expression _expression) {
        _init(_interpreter, _condition, _expression);
        line_number = interpreter.line_number;
    }
    public WhileExpression(GrammarParser _interpreter, int _line_number, Calculable _condition, Expression _expression) {
        _init(_interpreter, _condition, _expression);
        line_number = _line_number;
    }  
    private void _init(GrammarParser _interpreter, Calculable _condition, Expression _calculation) {
         interpreter = _interpreter;
         condition = _condition;
         calculation = _calculation;
    }
    @Override
    public void compilationCheck() throws CompilationErrorException {
        condition.compilationCheck();
        if (calculation != null) {
            calculation.compilationCheck();
        }
    }

    @Override
    public String toString() {
        return "WHILE expression:: IF[" + condition.toString() + "] {" + (calculation!=null?calculation.toString():"null") + "}";
    }

    @Override
    public Object eval() {
        Object cond = condition.eval();
        if (!(cond instanceof Bool)) {
            interpreter._WPAScriptRuntimeError("Condition must be an instance of BOOL [" + cond.getClass() + "]", line_number);
        }
        while ((Boolean)((Bool)cond).getNativeValue()) {
            if (calculation!=null) {
                calculation.eval();
            }
        }
        return null;
    }

    @Override
    public Calculable getSimplifiedCalculable() {
        Calculable new_condition = (Calculable) condition.getSimplifiedCalculable();
        Expression new_calculation = (Expression) calculation.getSimplifiedCalculable();
        return new WhileExpression(interpreter, line_number, new_condition, new_calculation);
    }
    
}
