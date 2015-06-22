package org.lpsy.wpscript.language.executable.builtintypes;

/**
 * @author Laurent FABRE, 2011-2015
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
