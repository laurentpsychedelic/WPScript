/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

import java.util.HashMap;

/**
 *
 * @author laurent
 */
public class Dictionary extends BuiltInType {
    private HashMap dictionary;
    
    public Dictionary(GrammarParser _interpreter, HashMap _value){
        _init(_interpreter, _value);
        line_number = interpreter.line_number;
    }
    public Dictionary(GrammarParser _interpreter, int _line_number, HashMap _value){
        _init(_interpreter, _value);
        line_number = _line_number;
    }
    
    private void _init(GrammarParser _interpreter, HashMap _value) {
        dictionary = _value;
        interpreter = _interpreter;
    }

    @Override
    public Object getNativeValue() 
    {
        return dictionary;
    }
    
    @Override
    public Object eval() throws PanicException {
        _doEval();
        return this;
    }
    
    private void _doEval() throws PanicException {
        HashMap new_dictionary = new HashMap();
        for (Object _key : dictionary.keySet()) {
            Object new_key = null;
            Object new_value = null;
            if (_key instanceof Calculable) {
                new_key = ((Calculable) _key).eval();
            } else {
                interpreter._WPAScriptPanic("Dictionary key must be calculable! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                new_value = ((Calculable) _value).eval();
            } else {
                interpreter._WPAScriptPanic("Dictionary value must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
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
                interpreter._WPAScriptPanic("Dictionary key must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                ((Calculable) _value).compilationCheck();
            } else {
                interpreter._WPAScriptPanic("Dictionary value must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
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
                interpreter._WPAScriptPanic("Dictionary key must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                new_value = ((Calculable) _value).getSimplifiedCalculable();
            } else {
                interpreter._WPAScriptPanic("Dictionary value must be an instance of Calculable! [" + _key.getClass() + "]", line_number);
            }
            new_dictionary.put(new_key, new_value);
        }
        return new Dictionary(interpreter, line_number, new_dictionary);
    }
}
