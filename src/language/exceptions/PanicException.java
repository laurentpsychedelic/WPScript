/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.exceptions;

/**
 *
 * @author laurent
 */
public class PanicException extends Exception{
    private int line_number = -1;
    private String message = null;

    public int getLineNumber() {
        return line_number;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public PanicException(String _message, int _line_number) {
        super(_message);
        message = _message;
        line_number = _line_number;
    }    
}
