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
    public Object eval() {
        _doEval();
        return this;
    }
    
    private void _doEval() {
        HashMap new_dictionary = new HashMap();
        for (Object _key : dictionary.keySet()) {
            Object new_key = null;
            Object new_value = null;
            if (_key instanceof Expression) {
                new_key = ((Expression) _key).eval();
            } else {
                interpreter._WPAScriptPanic("Dictionary key must be an instance of Expression! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Expression) {
                new_value = ((Expression) _value).eval();
            } else {
                interpreter._WPAScriptPanic("Dictionary value must be an instance of Expression! [" + _key.getClass() + "]", line_number);
            }
            new_dictionary.put(new_key, new_value);
        }
        dictionary = new_dictionary;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
	for (Object _key : dictionary.keySet()) {
            if (_key instanceof Expression) {
                ((Expression) _key).compilationCheck();
            } else {
                interpreter._WPAScriptPanic("Dictionary key must be an instance of Expression! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Expression) {
                ((Expression) _value).compilationCheck();
            } else {
                interpreter._WPAScriptPanic("Dictionary value must be an instance of Expression! [" + _key.getClass() + "]", line_number);
            }
        }
    }

    @Override
    public String toString() {
        return "DICTIONARY:: " + dictionary.toString();
    }
    
    @Override
    public Calculable getSimplifiedCalculable() {
        HashMap new_dictionary = new HashMap();
        for (Object _key : dictionary.keySet()) {
            Object new_key = null;
            Object new_value = null;
            if (_key instanceof Expression) {
                new_key = ((Expression) _key).getSimplifiedCalculable();
            } else {
                interpreter._WPAScriptPanic("Dictionary key must be an instance of Expression! [" + _key.getClass() + "]", line_number);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Expression) {
                new_value = ((Expression) _value).getSimplifiedCalculable();
            } else {
                interpreter._WPAScriptPanic("Dictionary value must be an instance of Expression! [" + _key.getClass() + "]", line_number);
            }
            new_dictionary.put(new_key, new_value);
        }
        return new Dictionary(interpreter, line_number, new_dictionary);
    }
}
