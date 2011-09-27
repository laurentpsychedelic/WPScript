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
public class Expression extends Calculable {
    private boolean TOP_LEVEL = false;
    LinkedList<Calculable> calculation = new LinkedList();
    public Expression(boolean _TOP_LEVEL, Expression _expression) {
        this(_TOP_LEVEL, _expression.interpreter, _expression.calculation);
    }
    public Expression(GrammarParser _interpreter, Object _calculation) {
        this(false, _interpreter, _calculation);
    }
    public Expression(boolean _TOP_LEVEL, GrammarParser _interpreter, Object _calculation) {
        _init(_TOP_LEVEL, _interpreter, _calculation);
        line_number = interpreter.line_number;
    }
    public Expression(boolean _TOP_LEVEL, GrammarParser _interpreter, int _line_number, Object _calculation) {
        _init(_TOP_LEVEL, _interpreter, _calculation);
        line_number = _line_number;
    }
    private void _init(boolean _TOP_LEVEL, GrammarParser _interpreter, Object _calculation) {
        TOP_LEVEL = _TOP_LEVEL;
        if (_calculation instanceof Calculable){
            calculation.add((Calculable)_calculation);
        } else if (_calculation instanceof LinkedList){
            for (Calculable _c : (LinkedList <Calculable>)_calculation) {
                calculation.add(_c);
            }
        }
        interpreter = _interpreter;
    }

    @Override
    public Object eval() throws PanicException {
        Object ret_val = null;
        for (Calculable c : calculation) {
            ret_val = c.eval();
        }
        return ret_val;
    }

    @Override
    public String toString() {
        return "EXPRESSION:: " + "\n    " + calculation.toString();
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        for (Calculable c : calculation) {
            c.compilationCheck();
        }
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        if (!TOP_LEVEL) {
            if (calculation.size()==1) {
                return calculation.get(0).getSimplifiedCalculable();
            }
        }
        LinkedList<Calculable> new_calculation = new LinkedList();
        for (Calculable c : calculation) {
            new_calculation.add(c.getSimplifiedCalculable());
        }
        return new Expression(TOP_LEVEL, interpreter, line_number, new_calculation);
    }

}
