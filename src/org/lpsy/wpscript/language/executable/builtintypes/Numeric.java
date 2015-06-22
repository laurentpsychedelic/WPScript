package org.lpsy.wpscript.language.executable.builtintypes;

/**
 * @author Laurent FABRE, 2011-2015
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
