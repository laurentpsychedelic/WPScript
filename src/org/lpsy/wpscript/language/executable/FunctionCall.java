package org.lpsy.wpscript.language.executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.Utilities;
import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.executable.builtintypes.BuiltInType;
import org.lpsy.wpscript.language.memory.Environment;
import org.lpsy.wpscript.proginterface.NativeFunctionsInterface;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class FunctionCall extends Calculable {
    LinkedList <Object> name_params;

    public FunctionCall(ScriptParser _interpreter, LinkedList <Object> _name_params) {
        name_params = _name_params;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
    }

    String getFunctionKeyForStoring(String name, Class [] types) {
        return getFunctionKeyForStoring(name, types, false /* Not simplified */);
    }
    String getFunctionKeyForStoring(String name, Class [] types, boolean SIMPLIFIED) {
        StringBuilder keyBuilder = new StringBuilder(name).append("(");
        for (int k = 0, len = types.length; k < len; k++) {
            Class clazz = types[k];
            keyBuilder.append(SIMPLIFIED ? Utilities.getSimplifiedClassName(clazz.toString()) : clazz.toString());
            if (k != len - 1) {
                keyBuilder.append(SIMPLIFIED ? ", " : ",");
            }
        }
        keyBuilder.append(")");
        return keyBuilder.toString();
    }
    Method lookupNativeMethod(String name, Class [] types) throws Exception {
        final String methodKey = getFunctionKeyForStoring(name, types);
        if (native_methods.containsKey(methodKey))
            return native_methods.get(methodKey);
        final Method method = NativeFunctionsInterface.class.getMethod(name, types);
        native_methods.put(methodKey, method);
        return method;
    }
    Method lookupRuntimeMethod(String name, Class [] types) throws Exception {
        final String methodKey = getFunctionKeyForStoring(name, types);
        if (runtime_methods.containsKey(methodKey))
            return runtime_methods.get(methodKey);
        for (Class module : runtime_modules) {
            try {
                final Method method = module.getMethod(name, types);
                runtime_methods.put(methodKey, method);
                return method;
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        try {
            String name = (String) (name_params.get(0));
            Class [] types = new Class[name_params.size()-1];
            for (int k = 1; k < name_params.size(); k++) {
                types[k - 1] = Object.class;
            }

            Method method = null;

            boolean runtime_method = false;
            boolean failed = false;
            try {
                method = lookupNativeMethod(name, types);
                if (method == null)
                    failed = true;
            } catch (Exception nsme) {
                runtime_method = true;
                failed = true;
            }

            if (failed) {
                failed = false;
                try {
                    method = lookupRuntimeMethod(name, types);
                    if (method == null)
                        failed = true;
                } catch (Exception nsme) {
                    failed = true;
                }
            }
            LinkedList<Object> params_values = new LinkedList();
            for (int k = 1; k < name_params.size(); k++) {
                Object par_val = (Object) ((Calculable) name_params.get(k)).eval();
                if (par_val instanceof BuiltInType && runtime_method) {
                    par_val = ((BuiltInType) par_val).getNativeValue();
                }
                params_values.add( par_val );
            }
            if (failed) {
                ArrayList<Class> argClasses = new ArrayList<Class>();
                for (Object arg : params_values) {
                    argClasses.add(arg.getClass());
                }
                final String methodStr = getFunctionKeyForStoring(name, argClasses.toArray(new Class [] {}), true /* Simplified */);
                throw new NoSuchMethodException(methodStr);
            } else {
                return method.invoke(null, params_values.toArray());
            }
        } catch (NoSuchMethodException ex) {
            interpreter.runtimeError("FUNC_CALL>> No such method: " + ex.getMessage(), line_number);
        } catch (SecurityException ex) {
            interpreter.scriptPanic("FUNC_CALL>> Security Exception!", line_number);
        }
        catch (IllegalAccessException ex) {
            interpreter.runtimeError("FUNC_CALL>> Illegal access Exception!", line_number);
        } catch (IllegalArgumentException ex) {
            interpreter.runtimeError("FUNC_CALL>> Illegal argument Exception!", line_number);
        } catch (InvocationTargetException ex) {
            StringBuilder error = new StringBuilder("FUNC_CALL>> Invocation target Exception!\n");
            __appendStackTraceToStringBuilder(null, ex, error);
            interpreter.runtimeError(error.toString(), line_number);
        }
        return null;
    }

    private static volatile boolean SANITIZATION_ENABLED = true;
    private static volatile boolean GROOVY_AVAILABLE_CHECKED = false;
    private static volatile Class groovyClass = null;
    private static Throwable __getSanitizedTrace(Throwable t) {
        if (SANITIZATION_ENABLED && !GROOVY_AVAILABLE_CHECKED) {
            try {
                groovyClass = Class.forName("org.codehaus.groovy.runtime.StackTraceUtils");
                // System.out.println("Groovy runtime detected {" + groovyClass + "}!");
            } catch (ClassNotFoundException cnde) {
                /* NOTHING */
            }
            GROOVY_AVAILABLE_CHECKED = true;
        }
        if (groovyClass != null) {
            try {
                Method sanitize = groovyClass.getMethod("deepSanitize", Throwable.class);
                t = (Throwable) sanitize.invoke(null, t);
            } catch (NoSuchMethodException nsme) {
                /* NOTHING */
            } catch (IllegalAccessException iae) {
                /* NOTHING */
            } catch (InvocationTargetException ite) {
                /* NOTHING */
            }
        }
        return t;
    }
    private static void __appendStackTraceToStringBuilder(Thread t, Throwable e, StringBuilder stringBuilder) {
        String newline = "\n";
        int STACK_TRACE_LENGTH = 20;
        e = __getSanitizedTrace(e);
        if (e != null) {
            if ( t != null ) {
                stringBuilder.append(newline);
            }
            stringBuilder.append(e.toString()).append(newline).append(newline);
            StackTraceElement [] s_t = e.getStackTrace();
            int l = s_t.length;
            if (l > STACK_TRACE_LENGTH) {
                l = STACK_TRACE_LENGTH;
            }
            for (int i = 0; i < l; i++) {
                stringBuilder.append(s_t[i].toString()).append(newline);
            }
            if (e.getCause() != null) {
                e = e.getCause();
                if (t != null) {
                    stringBuilder.append(newline);
                }
                stringBuilder.append(e.toString()).append(newline).append(newline);
                s_t = e.getStackTrace();
                l = s_t.length;
                if (l > STACK_TRACE_LENGTH) {
                    l = STACK_TRACE_LENGTH;
                }
                for (int i = 0; i < l; i++) {
                    stringBuilder.append(s_t[i].toString()).append(newline);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("FUNCTION CALL:: ");
        str.append(name_params.get(0));
        str.append("(");
        int size = name_params.size();
        for (int k = 1; k < size; k++) {
            str.append(name_params.get(k));
            if (k != size-1) {
                str.append(", ");
            }
        }
        str.append(")");
        return str.toString();
        //return "Function call [" + name_params.get(0) + "] " + ((Object) this).hashCode();
    }

    static final boolean _DEBUG_ = true;
    private static HashMap<String, Method> native_methods = new HashMap<String, Method>();
    private static HashMap<String, Method> runtime_methods = new HashMap<String, Method>();
    private static ArrayList<Class> runtime_modules = new ArrayList<Class>();

    public static void clearModules() {
        clearCachedFunctions();
        runtime_modules.clear();
    }
    public static void clearCachedFunctions() {
        native_methods.clear();
        runtime_methods.clear();
    }

    static {
        try {
            runtime_modules.add(Class.forName("proginterface.RuntimeFunctionsInterface"));
        } catch (ClassNotFoundException cnfe) {
            /* NOTHING */
        }
    }

    public static void addRuntimeModule(String runtimeModuleFQCN) throws ClassNotFoundException {
        Class runtimeModule = Class.forName(runtimeModuleFQCN);
        if (!runtime_modules.contains(runtimeModule))
            runtime_modules.add(runtimeModule);
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
	for (int k = 1, len = name_params.size(); k < len; k++) {
	    Object param = name_params.get(k);
	    if (param instanceof Calculable) {
		((Calculable) param).setEnv(_env);
	    }
	}
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        boolean not_found = true;
        if (name_params == null || name_params.get(0) == null) {
            interpreter.scriptPanic("FUNCTION_CALL:: Name or parameters not correctly defined!", line_number);
        }
        String name = ((String) name_params.get(0));
        Class [] types = new Class[name_params.size()-1];
        for (int k = 1; k < name_params.size(); k++) {
            types[k - 1] = Object.class;
        }

        // Look among user defined functions
	if (interpreter.functions_env.compilation_map.containsKey(name)) {
	    not_found = false;
	    FunctionDeclaration fd = (FunctionDeclaration) ( interpreter.functions_env.compilation_map.get(name) );
	    int n_params_real = fd.getNumberOfArguments();
	    int n_params_expected = name_params.size() - 1;
	    if (n_params_real != n_params_expected) {
		interpreter.compilationError("Wrong number of arguments: function [" + name + "] ! Expected: " + n_params_expected + " ; dound: " + n_params_real + " .", line_number);
	    }
	}

	// Lookup among native functions
        if (not_found) {
            try {
                final Method method = lookupNativeMethod(name, types);
                if (method != null)
                    not_found = false;
            } catch (Exception nsme) {
                not_found = true;
            }

	    if (not_found) {
                try {
                    final Method method = lookupRuntimeMethod(name, types);
                    if (method != null)
                        not_found = false;
                } catch (Exception nsme) {
                    not_found = true;
                }
	    }
	}
        if (not_found) {
            interpreter.compilationError("Function [" + name + "] not found!", line_number);
        }
        for (int k = 1; k < name_params.size(); k++) {
            Object param = name_params.get(k);
            if (param instanceof Calculable) {
                ((Calculable) param).compilationCheck();
            }
        }
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        LinkedList<Object> new_name_params = new LinkedList();
        if (name_params==null || name_params.get(0)== null) {
            interpreter.scriptPanic("FUNCTION_CALL:: Name or parameters not correctly defined!", line_number);
        }
        String name = "";
        new_name_params.add((String)name_params.get(0));
        for (int k = 1; k < name_params.size(); k++) {
            new_name_params.add(((Calculable)name_params.get(k)).getSimplifiedCalculable());
        }
        return new FunctionCall(interpreter, new_name_params);
    }
}
