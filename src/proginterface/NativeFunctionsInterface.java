/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proginterface;

import proginterface.types.MeasurementSet;
import language.executable.builtintypes.*;

/**
 *
 * @author laurent
 */
public class NativeFunctionsInterface {
    public static MeasurementSet newMeasurementSet(Object W, Object H) throws NoSuchMethodException {
        if (W instanceof Numeric && H instanceof Numeric) {
            return new MeasurementSet( (int) Math.round((Double)((Numeric)W).getNativeValue()),  (int) Math.round((Double)((Numeric)H).getNativeValue()));
        } else {
            throw new NoSuchMethodException("newMeasurementSet(" + language.Utilities.getSimplifiedClassName(W.getClass().toString()) + ", " + language.Utilities.getSimplifiedClassName(H.getClass().toString()) + ")");
        }
    }
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
