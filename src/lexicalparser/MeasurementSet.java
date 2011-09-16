/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

import DataStructures.RetardanceResult;
import DataStructures.StokesParametersDataSet;

/**
 *
 * @author laurent
 */
public class MeasurementSet {
    RetardanceResult result_object;
    StokesParametersDataSet stokes_set;
    public int W() {
        return result_object.W;
    }
    public int H(){
        return result_object.H;
    }
    @Override
    public String toString() {
        return "MEASUREMENT SET:: [" + W() + "," + H() + "]";
    }
    public MeasurementSet(int W, int H) {
        result_object = new RetardanceResult(W, H);
        stokes_set = new StokesParametersDataSet(W, H);
    }
}
