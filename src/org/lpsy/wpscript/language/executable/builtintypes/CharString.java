package org.lpsy.wpscript.language.executable.builtintypes;

import org.lpsy.wpscript.language.exceptions.CompilationErrorException;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class CharString extends BuiltInType {

    private String value;

    public CharString(String _value) {
        value = _value;
    }

    @Override
    public Object getNativeValue() {
        return value;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
        //NOTHING
    }

    @Override
    public String toString() {
        return "STRING:: " + value;
    }

}
