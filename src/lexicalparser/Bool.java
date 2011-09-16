/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public class Bool extends Calculable {

    private boolean value;

    public Bool(boolean _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return "BOOL:: " + Boolean.toString(value);
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public void compilationCheck() {
        //NOTHING
    }

}
