/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 *
 * @author LFabre
 */
public class FunctionCall extends Calculable {
    LinkedList <Object> name_params;

    public FunctionCall(GrammarParser _interpreter, LinkedList <Object> _name_params) {
        name_params = _name_params;
        interpreter = _interpreter;
        line_number = interpreter.line_number;
    }

    @Override
    public Object eval() {
        boolean failed = false;
        try {
            String name = (String) (name_params.get(0));
            Class [] types = new Class[name_params.size()-1];
            for (int k=1; k<name_params.size(); k++) {
                Object par_val = ((Calculable) name_params.get(k)).eval();
                Class cl = par_val.getClass();
                types[k-1] = cl;
            }
            LinkedList<Object> params_values = new LinkedList();
            for (int k=1; k<name_params.size(); k++) {
                Object par_val = ((Calculable) name_params.get(k)).eval();
                params_values.add( par_val );
            }

            //java.lang.String
            Method method = BuiltInFunctionsInterface.class.getMethod(name, types);
            return method.invoke(null, params_values.toArray());
        } catch (NoSuchMethodException ex) {
            failed = true;
        } catch (SecurityException ex) {
            interpreter._WPAScriptPanic("FUNC_CALL>> Security Exception!", line_number);
            failed = true;
        }
        catch (IllegalAccessException ex) {
            interpreter._WPAScriptRuntimeError("FUNC_CALL>> Illegal access Exception!", line_number);
            failed = true;
        } catch (IllegalArgumentException ex) {
            interpreter._WPAScriptRuntimeError("FUNC_CALL>> Illegal argument Exception!", line_number);
            failed = true;
        } catch (InvocationTargetException ex) {
            interpreter._WPAScriptRuntimeError("FUNC_CALL>> Invocation target Exception!\n" + ex.getCause(), line_number);
            failed = true;
        }
        if (failed) {
            interpreter._WPAScriptRuntimeError("FUNC_CALL>> No such function [" + name_params.get(0) + "]", line_number);
        }

        return null;
    }

    @Override
    public String toString() {
        return "Function call [" + name_params.get(0) + "] " + ((Object) this).hashCode();
    }
    
    private Method [] methods = BuiltInFunctionsInterface.class.getMethods();

    @Override
    public void compilationCheck() {
        boolean not_found = true;
        if (name_params==null || name_params.get(0)== null) {
            interpreter._WPAScriptPanic("FUNCTION_CALL:: Name or parameters not correctly defined!", line_number);
        }
        String name = "";
        name = ((String)name_params.get(0));
        if (methods != null) {
            for (Method method : methods) {
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
            interpreter._WPAScriptCompilationError("Function [" + name + "] not found!", line_number);
        }
    }
}
