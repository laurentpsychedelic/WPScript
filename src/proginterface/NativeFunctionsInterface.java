/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proginterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import proginterface.types.MeasurementSet;
import language.executable.builtintypes.*;

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
    public static void print(CharString _format, Object... args) {
        String format_str = (String) _format.getNativeValue();
        String str = format_str;
        if (args!=null && args.length>=1 && Pattern.matches(".*[{]#[0-9]+[}].*", format_str)){
            String pattern_str = "[{]#[0-9]+[}]";
            Pattern pattern = Pattern.compile(pattern_str);
            Matcher matcher = pattern.matcher(format_str);
            while (matcher.find()) {
                String f = matcher.group();
                f = f.replaceAll("[{]|[}]|#", "");
                int index_arg = Integer.parseInt(f);
                if (index_arg <= args.length) {
                    String ptn = "[{]#" + index_arg + "[}]";
                    str =str.replaceAll(ptn, args[index_arg-1].toString());
                }
            }
        }
        _print(str);
    }
}
