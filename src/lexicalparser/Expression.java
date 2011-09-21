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

    LinkedList<Calculable> calculation = new LinkedList();
    public Expression(GrammarParser _interpreter, Object _calculation) {
        _init(_interpreter, _calculation);
        line_number = interpreter.line_number;
    }
    public Expression(GrammarParser _interpreter, int _line_number, Object _calculation) {
        _init(_interpreter, _calculation);
        line_number = _line_number;
    }
    private void _init(GrammarParser _interpreter, Object _calculation) {
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
    public Object eval() {
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
    public void compilationCheck() throws CompilationErrorException {
        for (Calculable c : calculation) {
            c.compilationCheck();
        }
    }

    @Override
    public Calculable getSimplifiedCalculable() {
        interpreter._WPAScriptCompilationWarning("TODO: implement tree refactoring for element Expression!", line_number);
        LinkedList<Calculable> new_calculation = new LinkedList();
        for (Calculable c : calculation) {
            new_calculation.add(c.getSimplifiedCalculable());
        }
        return new Expression(interpreter, line_number, new_calculation);
    }

}
