/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public class Expression extends Calculable {

    Calculable calculation;
    public Expression(GrammarParser _interpreter, Calculable _calculation) {
        calculation = _calculation;
        interpreter = _interpreter;
        line_number = interpreter.line_number;
    }

    @Override
    public Object eval() {
        return calculation.eval();
    }

    @Override
    public String toString() {
        return "EXPRESSION:: " + "\n    " + calculation.toString();
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
        calculation.compilationCheck();
    }

}
