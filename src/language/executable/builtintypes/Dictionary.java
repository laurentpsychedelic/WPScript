/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable.builtintypes;

import java.util.HashMap;
import java.util.Set;
import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;
import language.executable.Calculable;

/**
 *
 * @author laurent
 */
public class Dictionary extends BuiltInType {
    private HashMap dictionary;
    
    public Dictionary(ScriptParser _interpreter, HashMap _value){
        _init(_interpreter, _value);
        line_number = interpreter.getLineNumber();
    }
    public Dictionary(ScriptParser _interpreter, int _line_number, HashMap _value){
        _init(_interpreter, _value);
        line_number = _line_number;
    }
    
    private void _init(ScriptParser _interpreter, HashMap _value) {
        dictionary = _value;
        interpreter = _interpreter;
    }

    @Override
    public Object getNativeValue() 
    {
        Set keys = dictionary.keySet();
        HashMap dictionary2 = new HashMap();
        for (Object o : keys) {
            dictionary2.put(((BuiltInType)o).getNativeValue(), ((BuiltInType) (dictionary.get(o))).getNativeValue());
        }
        return dictionary2;
    }
    
    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        _doEval();
        return this;
    }
    
    private void _doEval() throws PanicException, RuntimeErrorException {
        HashMap new_dictionary = new HashMap();
        for (Object _key : dictionary.keySet()) {
            Object new_key = null;
            Object new_value = null;
            if (_key instanceof Calculable) {
                new_key = ((Calculable) _key).eval();
            } else {
                interpreter.scriptPanic("Dictionary key must be calculable! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                new_value = ((Calculable) _value).eval();
            } else {
                interpreter.scriptPanic("Dictionary value must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
            new_dictionary.put(new_key, new_value);
        }
        dictionary = new_dictionary;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
	for (Object _key : dictionary.keySet()) {
            if (_key instanceof Calculable) {
                ((Calculable) _key).compilationCheck();
            } else {
                interpreter.scriptPanic("Dictionary key must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                ((Calculable) _value).compilationCheck();
            } else {
                interpreter.scriptPanic("Dictionary value must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
        }
    }

    @Override
    public String toString() {
        return "DICTIONARY:: " + dictionary.toString();
    }
    
    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        HashMap new_dictionary = new HashMap();
        for (Object _key : dictionary.keySet()) {
            Object new_key = null;
            Object new_value = null;
            if (_key instanceof Calculable) {
                new_key = ((Calculable) _key).getSimplifiedCalculable();
            } else {
                interpreter.scriptPanic("Dictionary key must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                new_value = ((Calculable) _value).getSimplifiedCalculable();
            } else {
                interpreter.scriptPanic("Dictionary value must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
            new_dictionary.put(new_key, new_value);
        }
        return new Dictionary(interpreter, line_number, new_dictionary);
    }
}
