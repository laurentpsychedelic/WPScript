/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proginterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import language.executable.builtintypes.*;

/**
 *
 * @author laurent
 */
public class NativeFunctionsInterface {
    public static void print(Object _format, Object _object) throws NoSuchMethodException {
	if (_format instanceof CharString && _object instanceof Numeric) {
	    String format_str = (String) ((CharString)_format).getNativeValue();
	    String str = format_str;
	    if (_object!=null && Pattern.matches(".*[{]#[0-9]([.][0-9])?[}].*", format_str)){
		String pattern_str = "[{]#[0-9]([.][0-9])?[}]";
		Pattern pattern = Pattern.compile(pattern_str);
		Matcher matcher = pattern.matcher(format_str);
		while (matcher.find()) {
		    String f = matcher.group();
		    boolean is_double = f.contains(".");
		    String format = "%" + f.replaceAll("[{]|[}]|#", "") + (is_double ? "f" : "d");
		    Double object = (Double) ((Numeric) _object).getNativeValue();
		    if (is_double) {
			str = str.replace(f, String.format(format, object));
		    } else {
			str = str.replace(f, String.format(format, (int) Math.round(object)));
		    }
		}
	    }
	    _print(str);
	} else {
	    throw new NoSuchMethodException();
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
    public static Numeric sin(Object in) throws NoSuchMethodException {
        if (in instanceof Numeric) {
            return new Numeric( Math.sin((Double)((Numeric) in).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    public static Numeric cos(Object in) throws NoSuchMethodException {
        if (in instanceof Numeric) {
            return new Numeric( Math.cos((Double)((Numeric) in).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
}
