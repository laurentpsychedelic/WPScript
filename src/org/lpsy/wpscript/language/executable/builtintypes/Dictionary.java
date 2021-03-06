package org.lpsy.wpscript.language.executable.builtintypes;

import java.util.HashMap;
import java.util.Set;
import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.executable.Calculable;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class Dictionary extends BuiltInType {
    private HashMap dictionary;

    public Dictionary(ScriptParser _interpreter, HashMap _value){
        _init(_interpreter, _value);
        line_number = interpreter.getLineNumber();
    }
    public Dictionary(ScriptParser _interpreter, int _line_number, HashMap _value){
        _init(_interpreter, _value);
        line_number = _line_number;
    }

    private void _init(ScriptParser _interpreter, HashMap _value) {
        dictionary = _value;
        interpreter = _interpreter;
    }

    @Override
    public Object getNativeValue()
    {
        Set keys = dictionary.keySet();
        HashMap dictionary2 = new HashMap();
        for (Object o : keys) {
	    Object new_key = o;
	    if (o instanceof BuiltInType) {
		new_key = ((BuiltInType)o).getNativeValue();
	    }
            dictionary2.put(new_key, ((BuiltInType) (dictionary.get(o))).getNativeValue());
        }
        return dictionary2;
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        _doEval();
        return this;
    }

    private void _doEval() throws PanicException, RuntimeErrorException {
        HashMap new_dictionary = new HashMap();
        for (Object _key : dictionary.keySet()) {
            Object new_key = null;
            Object new_value = null;
            if (_key instanceof Calculable) {
                new_key = ((Calculable) _key).eval();
		if (new_key instanceof BuiltInType) {
		    new_key = ((BuiltInType) new_key).getNativeValue();
		}
            } else {
		new_key = _key;
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                new_value = ((Calculable) _value).eval();
            } else {
		new_value = _value;
            }
            new_dictionary.put(new_key, new_value);
        }
        dictionary = new_dictionary;
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
	for (Object _key : dictionary.keySet()) {
            if (_key instanceof Calculable) {
                ((Calculable) _key).setEnv(_env);
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                ((Calculable) _value).setEnv(_env);
            }
        }
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
	for (Object _key : dictionary.keySet()) {
            if (_key instanceof Calculable) {
                ((Calculable) _key).compilationCheck();
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                ((Calculable) _value).compilationCheck();
            }
	}
    }

    @Override
    public String toString() {
        return "DICTIONARY:: " + dictionary.toString();
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        HashMap new_dictionary = new HashMap();
        for (Object _key : dictionary.keySet()) {
            Object new_key = null;
            Object new_value = null;
            if (_key instanceof Calculable) {
                new_key = ((Calculable) _key).getSimplifiedCalculable();
            } else {
		new_key = _key;
            }
            Object _value = dictionary.get(_key);
            if (_value instanceof Calculable) {
                new_value = ((Calculable) _value).getSimplifiedCalculable();
            } else {
		new_value = _value;
            }
            new_dictionary.put(new_key, new_value);
        }
        return new Dictionary(interpreter, line_number, new_dictionary);
    }
    public Object get(Object _index) throws RuntimeErrorException {
        Object index = null;
        if (_index instanceof BuiltInType) {
            index = ((BuiltInType) _index).getNativeValue();
	}
	Object dict_value = dictionary.get(index);
	if (dict_value==null) {
	    interpreter.runtimeError("DICTIONARY::get>> No such entry [" + _index + "]", line_number);
	}
	return dict_value;
    }
    public Object set(Object _index, Object _value) throws RuntimeErrorException {
	Object index = null;
        if (_index instanceof BuiltInType) {
            index = ((BuiltInType) _index).getNativeValue();
        }
	return dictionary.put(index, _value);
    }

}
