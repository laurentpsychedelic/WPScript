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
        dictionary = _value;
        interpreter = _interpreter;
        line_number = interpreter.line_number;
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
        interpreter._WPAScriptCompilationWarning("Cannot check Dictionary type at compilation time in current language version!", line_number);
    }

    @Override
    public String toString() {
        return "DICTIONARY:: " + dictionary.toString();
    }
}
