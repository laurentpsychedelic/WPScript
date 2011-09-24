/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

/**
 *
 * @author laurent
 */
public class LoopExpression extends Calculable {
    Expression pre_calculation;
    Expression increment_calculation;
    Calculable condition;
    Expression calculation;
    //FOR LOOP
    public LoopExpression(GrammarParser _interpreter, Expression _pre_calculation, Expression _increment_calculation, Calculable _condition, Expression _expression) {
        _init(_interpreter, _pre_calculation, _increment_calculation, _condition, _expression);
        line_number = interpreter.line_number;
    }
    //WHILE LOOP
    public LoopExpression(GrammarParser _interpreter, Calculable _condition, Expression _expression) {
        _init(_interpreter, null, null, _condition, _expression);
        line_number = interpreter.line_number;
    }
    
    public LoopExpression(GrammarParser _interpreter, int _line_number, Expression _pre_calculation, Expression _increment_calculation, Calculable _condition, Expression _expression) {
        _init(_interpreter, _pre_calculation, _increment_calculation, _condition, _expression);
        line_number = _line_number;
    }  
    private void _init(GrammarParser _interpreter, Expression _pre_calculation, Expression _increment_calculation, Calculable _condition, Expression _calculation) {
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
        if (pre_calculation==null && increment_calculation==null) {
            return "LOOP expression:: WHILE[" + condition.toString() + "] {" + (calculation!=null?calculation.toString():"null") + "}";
        } else {
            return "LOOP expression:: FOR[" + (pre_calculation==null ? "null":pre_calculation.toString()) + " ; " + (increment_calculation==null ? "null":increment_calculation.toString() + " ; " + condition.toString()) + "] {" + (calculation!=null?calculation.toString():"null") + "}";
        }
    }

    @Override
    public Object eval() {
        if (pre_calculation != null) {
            pre_calculation.eval();
        }
        while (true) {
            Object cond = condition.eval();
            if (!(cond instanceof Bool)) {
                interpreter._WPAScriptRuntimeError("Condition must be an instance of BOOL [" + cond.getClass() + "]", line_number);
                return null;
            }
            if ((Boolean)((Bool)cond).getNativeValue()) {
                if (calculation!=null) {
                    Object ret_val = calculation.eval();
                    if (ret_val == ReturnValue.RETURN_BREAK) {
                        return ReturnValue.RETURN_BREAK;
                    }
                }
                if (increment_calculation != null) {
                    increment_calculation.eval();
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public Calculable getSimplifiedCalculable() {
        Expression new_pre_calculation = (Expression) (pre_calculation==null ? null : pre_calculation.getSimplifiedCalculable());
        Expression new_increment_calculation = (Expression) (increment_calculation==null ? null : increment_calculation.getSimplifiedCalculable());
        Calculable new_condition = (Calculable) condition.getSimplifiedCalculable();
        Expression new_calculation = (Expression) calculation.getSimplifiedCalculable();
        return new LoopExpression(interpreter, line_number, new_pre_calculation, new_increment_calculation, new_condition, new_calculation);
    }
    
}
