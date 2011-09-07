/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LFabre
 */
public class FunctionCall {
    
    public static void myFunc(String title) {
        System.out.println("TITLE SET TO: " + title);
    }
    
    public static void callFunction(String name, String... params) {
        try {
            Class [] types = new Class[params.length];
            for (int k=0; k<types.length; k++) {
                types[k] = java.lang.String.class;
            }
            //java.lang.String
            Method method = FunctionCall.class.getMethod(name, types);
            method.invoke(null, (Object[]) params);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FunctionCall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(FunctionCall.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(FunctionCall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FunctionCall.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FunctionCall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
