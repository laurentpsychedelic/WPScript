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
        if (_calculation instanceof Calculable){
            calculation.add((Calculable)_calculation);
        } else if (_calculation instanceof LinkedList){
            for (Calculable _c : (LinkedList <Calculable>)_calculation) {
                calculation.add(_c);
            }
        }
        interpreter = _interpreter;
        line_number = interpreter.line_number;
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

}
