/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proginterface;

import language.executable.builtintypes.*;

/**
 *
 * @author laurent
 */
public class NativeFunctionsInterface {
    private static void _print(Object object) {
        System.out.println(_getPrintString(object));   
    }
    private static String _getPrintString(Object object) {
        if (object instanceof BuiltInType) {
            return ((BuiltInType)object).getNativeValue().toString();
        } else {
            return object.toString();
        }
    }
    public static void print(Object object) {
        _print(object);
    }
    public static void print() {
        print("\n");
    }
    
}
