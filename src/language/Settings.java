/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

/**
 *
 * @author LFabre
 */
public class Settings {
    public static void setRuntimeClassLocation(String location) {
        language.executable.FunctionCall.setRuntimeClassLocation(location);
    }
}
