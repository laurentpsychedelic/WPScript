/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proginterface;

import proginterface.types.MeasurementSet;
import language.executable.builtintypes.CharString;
import language.executable.builtintypes.Numeric;

/**
 *
 * @author laurent
 */
public class NativeFunctionsInterface {
    public static MeasurementSet newMeasurementSet(Numeric W, Numeric H) {
      return new MeasurementSet( (int) Math.round((Double)W.getNativeValue()),  (int) Math.round((Double)H.getNativeValue()));
    }
    private static void _print(Object object) {
        System.out.println(object.toString());
    }
    public static void print(Numeric number) {
        _print(number.getNativeValue());
    }
    public static void print(CharString number) {
        _print(number.getNativeValue());
    }
}
