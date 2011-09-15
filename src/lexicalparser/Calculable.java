/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public abstract class Calculable {
    GrammarParser interpreter;
    public abstract void compilationCheck() throws CompilationErrorException;
    @Override
    public abstract String toString();
    public abstract Object eval();
    protected int line_number = -1;
}
