/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public class Numeric {
    public double value = 0.0f;
    public Numeric(double _value) {
        value = _value;
    }
    @Override
    public String toString() {
        return Double.toString(value);
    }
}
