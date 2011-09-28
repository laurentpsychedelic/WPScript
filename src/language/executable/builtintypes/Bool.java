/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable.builtintypes;

/**
 *
 * @author laurent
 */
public class Bool extends BuiltInType {

    private boolean value;

    public Bool(boolean _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return "BOOL:: " + Boolean.toString(value);
    }

    @Override
    public void compilationCheck() {
        //NOTHING
    }

    @Override
    public Object getNativeValue() {
        return value;
    }

}
