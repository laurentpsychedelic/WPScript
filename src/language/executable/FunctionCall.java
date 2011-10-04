/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import proginterface.NativeFunctionsInterface;
import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;

/**
 *
 * @author LFabre
 */
public class FunctionCall extends Calculable {
    LinkedList <Object> name_params;

    public FunctionCall(ScriptParser _interpreter, LinkedList <Object> _name_params) {
        name_params = _name_params;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        try {
            String name = (String) (name_params.get(0));
            Class [] types = new Class[name_params.size()-1];
            for (int k=1; k<name_params.size(); k++) {
                types[k-1] = Object.class;
            }
            LinkedList<Object> params_values = new LinkedList();
            for (int k=1; k<name_params.size(); k++) {
                Object par_val = (Object) ((Calculable) name_params.get(k)).eval();
                params_values.add( par_val );
            }

            Method method = null;
            
            boolean failed = false;
            try {
                method = NativeFunctionsInterface.class.getMethod(name, types);
            } catch (NoSuchMethodException nsme) {
                failed = true;
            }
            
            if (failed) {
                if (runtime_functions_class != null) {
                    failed = false;
                    try {
                        method = runtime_functions_class.getMethod(name, types);
                    } catch (NoSuchMethodException nsme) {
                        failed = true;
                    }
                }
            }
                
            if (failed) {
                StringBuilder args_str = new StringBuilder();
                int size = params_values.size();
                for (int k=0; k<size; k++) {
                    args_str.append(language.Utilities.getSimplifiedClassName(params_values.get(k).getClass().toString()));
                    if (k != size-1) {
                        args_str.append(", ");
                    }
                }
                throw new NoSuchMethodException(name + "(" + args_str.toString() + ")");
            } else {
                //if (params_values.size() > 0) {
                    return method.invoke(null, params_values.toArray());
                /*} else {
                    return method.invoke(null);
                }*/
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
            interpreter.runtimeError("FUNC_CALL>> Invocation target Exception!\n" + ex.getCause(), line_number);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("FUNCTION CALL:: ");
        str.append(name_params.get(0));
        str.append("(");
        int size = name_params.size();
        for (int k=1; k<size; k++) {
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
    private static Method [] native_methods;
    private static Method [] runtime_methods;
    private static Class runtime_functions_class = null;
    private static String runtime_functions_class_name = "proginterface.RuntimeFunctionsInterface"; 
    static {
        native_methods = NativeFunctionsInterface.class.getMethods();
        _updateRuntimeEnvironment();
    }
    
    public static void setRuntimeClassLocation(String location) {
        runtime_functions_class_name = location;
        _updateRuntimeEnvironment();
    }
    
    private static void _updateRuntimeEnvironment() {
        try {
            runtime_functions_class = Class.forName(runtime_functions_class_name);
        } catch (ClassNotFoundException cnfe) {
            //NOTHING
            System.err.println("Warning: no runtime functions class found");
        }
        if (runtime_functions_class != null) {
            runtime_methods = runtime_functions_class.getMethods();
        }
    }
    
    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        boolean not_found = true;
        if (name_params==null || name_params.get(0)== null) {
            interpreter.scriptPanic("FUNCTION_CALL:: Name or parameters not correctly defined!", line_number);
        }
        String name = "";
        name = ((String)name_params.get(0));
        if (native_methods != null) {
            for (Method method : native_methods) {
                if (method == null) {
                    continue;
                }
                if (name.equals(method.getName())) {
                    not_found = false;
                    break;
                }
            }        
        }
        if (not_found) {
            if (runtime_methods != null) {
                for (Method method : runtime_methods) {
                    if (method == null) {
                        continue;
                    }
                    if (name.equals(method.getName())) {
                        not_found = false;
                        break;
                    }
                }        
            }   
        }
        if (not_found) {
            interpreter.compilationError("Function [" + name + "] not found!", line_number);
        }
        for (int k=1; k<name_params.size(); k++) {
            Object param = name_params.get(k);
            if (param instanceof Calculable) {
                ((Calculable)param).compilationCheck();
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
        for (int k=1; k<name_params.size(); k++) {
            new_name_params.add(((Calculable)name_params.get(k)).getSimplifiedCalculable());
        }
        return new FunctionCall(interpreter, new_name_params);
    }
}
