/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

/**
 *
 * @author laurent
 */
public class Utilities {
    public static String getSimplifiedClassName(String class_name) {
        String [] strs = class_name.split("[.]");
        String simple_name = class_name;
        if (strs.length>1) {
            simple_name = strs[strs.length-1];
        }
        return simple_name;
    }    
}
