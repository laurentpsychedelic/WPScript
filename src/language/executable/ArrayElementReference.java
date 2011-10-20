/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable;

import language.exceptions.CompilationErrorException;
import language.ScriptParser;
import language.exceptions.PanicException;
import language.exceptions.RuntimeErrorException;
import language.executable.builtintypes.Numeric;
import language.executable.builtintypes.ObjectArray;
/**
 *
 * @author laurent
 */
public class ArrayElementReference extends Calculable {
    Calculable expression_array;
    Calculable expression_index;

    public ArrayElementReference(ScriptParser _interpreter, Calculable _expression_array, Calculable _expression_index) {
        expression_array = _expression_array;
        expression_index = _expression_index;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
    }

    @Override
    public String toString() {
        return "ARRAY ELE:: " + expression_array.toString() + " [ " + expression_index + " ]";
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        expression_array.compilationCheck();
        expression_index.compilationCheck();
    }

    @Override
    public Object eval() throws RuntimeErrorException, PanicException {
        Object val = expression_array.eval();
        if (val == null) {
            interpreter.scriptPanic("ARRAY ELEMENT REFERENCE>> array name expression returned null", line_number);
        }
        if (val instanceof ObjectArray) {
            ObjectArray array = (ObjectArray) val;
            Object ind = expression_index.eval();
            if (val == null) {
                interpreter.scriptPanic("ARRAY ELEMENT REFERENCE>> array index expression returned null", line_number);
            }
            if (ind instanceof Numeric) {
                int index = (int) Math.round((Double) ((Numeric) ind).getNativeValue());
                Object _native_array = array.getNativeValue();
                try {
                    Object [] native_array = (Object []) _native_array;
                    if (index>=0 && index<native_array.length) {
                        return native_array[index];
                    } else {
                        interpreter.runtimeError("ARRAY ELEMENT REFERENCE>> Index out of range [" + index + "]", line_number);
                    }
                } catch (ClassCastException cce) {
                    interpreter.scriptPanic("ARRAY ELEMENT REFERENCE>> Array internal storage must be Object[] [" + _native_array.getClass() + "]", line_number);
                }
            } else {
                interpreter.runtimeError("ARRAY ELEMENT REFERENCE>> Index must be of numeric type", line_number);
            }
        } else {
            interpreter.runtimeError("ARRAY ELEMENT REFERENCE>> Only Array types can be referenced [" + val.getClass() + "]", line_number);
        }
        return val;
    }

    @Override
    public Calculable getSimplifiedCalculable() {
        return this;
    }

}
