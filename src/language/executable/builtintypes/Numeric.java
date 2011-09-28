/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable.builtintypes;

/**
 *
 * @author laurent
 */
public class Numeric extends BuiltInType {
    private double value = 0.0f;
    public Numeric(double _value) {
        value = _value;
    }
    @Override
    public String toString() {
        return "NUMERIC:: " + Double.toString(value);
    }

    @Override
    public Object getNativeValue() {
        return value;
    }

    @Override
    public void compilationCheck() {
        //NOTHING
    }
}
