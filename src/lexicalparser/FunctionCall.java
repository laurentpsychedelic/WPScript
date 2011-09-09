/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
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

    public static MeasurementSet newMeasurementSet(Double W, Double H) {
        return new MeasurementSet((int)W.doubleValue(), (int)H.doubleValue());
    }

    public static Object callFunction(LinkedList <Object> name_params) {
        try {
            String name = (String) (name_params.get(0));
            Class [] types = new Class[name_params.size()-1];
            for (int k=1; k<name_params.size(); k++) {
                Class cl = name_params.get(k).getClass();
                types[k-1] = cl;
            }
            //java.lang.String
            Method method = FunctionCall.class.getMethod(name, types);
            System.out.println(method.getGenericParameterTypes().length);
            LinkedList <Object> params = new LinkedList();
            for (int k=1; k<name_params.size(); k++) {
                params.add(name_params.get(k));
            }
            return method.invoke(null, params.toArray());
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
        return null;
    }
}
