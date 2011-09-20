/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

/**
 *
 * @author laurent
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
