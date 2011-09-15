/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public class BuiltInFunctionsInterface {
    public static MeasurementSet newMeasurementSet(Numeric W, Numeric H) {
      return new MeasurementSet( (int) Math.round((Double)W.eval()),  (int) Math.round((Double)H.eval()));
    }
}
