package org.lpsy.wpscript.language.exceptions;

/**
 * @author Laurent FABRE, 2011-2015
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
