/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable.builtintypes;

/**
 *
 * @author laurent
 */
public class ReturnValue extends Object {
    public static final ReturnValue RETURN_BREAK = new ReturnValue();
    public static final ReturnValue RETURN_CONTINUE = new ReturnValue();
    public static final ReturnValue RETURN_RETURN = new ReturnValue();
    public static final ReturnValue RETURN_VOID = new ReturnValue();
}
