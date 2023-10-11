package org.lpsy.wpscript.proginterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lpsy.wpscript.language.executable.builtintypes.*;

/**
 * @author Laurent FABRE, 2011-2015
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
    public static Numeric abs(Object in) throws NoSuchMethodException {
        if (in instanceof Numeric) {
            return new Numeric( Math.abs((Double)((Numeric) in).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    public static Numeric sqrt(Object in) throws NoSuchMethodException {
        if (in instanceof Numeric) {
            return new Numeric( Math.sqrt((Double)((Numeric) in).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    public static Numeric log(Object in) throws NoSuchMethodException {
        if (in instanceof Numeric) {
            return new Numeric( Math.log((Double)((Numeric) in).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    public static Numeric log10(Object in) throws NoSuchMethodException {
        if (in instanceof Numeric) {
            return new Numeric( Math.log10((Double)((Numeric) in).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    public static Numeric pow(Object in, Object in2) throws NoSuchMethodException {
        if ((in instanceof Numeric) && (in2 instanceof Numeric)) {
            return new Numeric( Math.pow((Double)((Numeric) in).getNativeValue(), (Double)((Numeric) in2).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    public static Numeric max(Object in, Object in2) throws NoSuchMethodException {
        if ((in instanceof Numeric) && (in2 instanceof Numeric)) {
            return new Numeric( Math.max((Double)((Numeric) in).getNativeValue(), (Double)((Numeric) in2).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    public static Numeric min(Object in, Object in2) throws NoSuchMethodException {
        if ((in instanceof Numeric) && (in2 instanceof Numeric)) {
            return new Numeric( Math.min((Double)((Numeric) in).getNativeValue(), (Double)((Numeric) in2).getNativeValue()) );
        } else {
            throw new NoSuchMethodException();
        }
    }
    private static Map<String, Proxy> proxys = new HashMap<String, Proxy>();
    public static Proxy getProxy(String name) { return proxys.get(name); }
    public static Method getMethod(String name, Class [] types) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Method _method : Math.class.getMethods()) {
            final Method method = _method;
            if (method.getName().equals(name) && method.getParameterCount() == types.length) {
                Proxy proxy = getProxy(name);
                if (proxy == null) {
                    proxy = new Proxy(method);
                    proxys.put(name, proxy);
                }
                return proxy.getClass().getMethod("apply", Object[].class);
            }
        }
        throw new NoSuchMethodException("Method: \"" + name + "\"");
    }
    public static class Proxy {
        private final Method method;
        Proxy(Method method) { this.method = method; }
        public Numeric apply(Object [] args) throws IllegalAccessException, InvocationTargetException {
            final int len = args.length;
            final Double [] argVals = new Double[len];
            for (int i = 0; i < len; ++i)
                argVals[i] = (Double) ((Numeric) args[i]).getNativeValue();
            return new Numeric( (Double) method.invoke(null, (Object[]) argVals) );
        }
    }
}
