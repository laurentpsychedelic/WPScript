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
    private HashMap value;
    
    public Dictionary(GrammarParser _interpreter, HashMap _value){
        value = _value;
        interpreter = _interpreter;
        line_number = interpreter.line_number;
    }

    @Override
    public Object getNativeValue() {
        return value;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
        interpreter._WPAScriptCompilationWarning("Cannot check Dictionary type at compilation time in current language version!", line_number);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
