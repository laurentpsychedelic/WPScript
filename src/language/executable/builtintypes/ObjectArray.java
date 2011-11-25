/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable.builtintypes;

import java.util.LinkedList;
import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.ScriptParser;
import language.exceptions.RuntimeErrorException;
import language.executable.Calculable;
import language.memory.Environment;

/**
 *
 * @author laurent
 */
public class ObjectArray extends BuiltInType {
    private LinkedList<Object> value;
    
    public ObjectArray(ScriptParser _interpreter, LinkedList<Object> _value){
        _init(_interpreter, _value);
        line_number = interpreter.getLineNumber();
    }
    public ObjectArray(ScriptParser _interpreter, int _line_number, LinkedList<Object> _value){
        _init(_interpreter, _value);
        line_number = _line_number;
    }
    
    private void _init(ScriptParser _interpreter, LinkedList<Object> _value) {
        value = _value;
        interpreter = _interpreter;
    }

    @Override
    public Object getNativeValue() 
    {
        LinkedList<Object> value2 = new LinkedList();
        for (int k=0; k<value.size(); k++) {
            value2.add(((BuiltInType)value.get(k)).getNativeValue());
        }
        return value2.toArray();
    }
    
    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        _doEval();
        return this;
    }
    
    private void _doEval() throws PanicException, RuntimeErrorException {
        LinkedList<Object> new_value = new LinkedList();
        for (int k=0; k<value.size(); k++) {
            Object object = value.get(k);
            Object new_object = null;
            if (object instanceof Calculable) {
                new_object = ((Calculable) object).eval();
            } else {
                interpreter.scriptPanic("Dictionary key must be calculable! [" + object.getClass() + "]", line_number);
            }
            new_value.add(new_object);
        }
        value = new_value;
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
	for (int k=0; k<value.size(); k++) {
            Object object = value.get(k);
            if (object instanceof Calculable) {
                ((Calculable) object).setEnv(_env);
            }
        }
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
	for (int k=0; k<value.size(); k++) {
            Object object = value.get(k);
            if (object instanceof Calculable) {
                ((Calculable) object).compilationCheck();
            } else {
                interpreter.scriptPanic("Dictionary key must be an instance of Calculable! [" + object.getClass() + "]", line_number);
            }
        }
    }

    @Override
    public String toString() {
        return "ARRAY:: " + value.toString();
    }
    
    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        LinkedList<Object> new_value = new LinkedList();
        for (int k=0; k<value.size(); k++) {
            Object object = value.get(k);
            Object new_object = null;
            if (object instanceof Calculable) {
                new_object = ((Calculable) object).getSimplifiedCalculable();
            } else {
                interpreter.scriptPanic("Dictionary key must be an instance of Calculable! [" + object.getClass() + "]", line_number);
            }
            new_value.add(new_object);
        }
        return new ObjectArray(interpreter, line_number, new_value);
    }
    
    public Object get(Object _index) throws RuntimeErrorException {
        int index = -1;
        if (_index instanceof Numeric) {
            index = (int) Math.round((Double) ((Numeric) _index).getNativeValue());
        } else {
            interpreter.runtimeError("OBJECT ARRAY::get>> Index must be type Numeric [" + _index.getClass() + "]", line_number);
        }
        if (index>=0 && index<value.size()) {
            return value.get(index);
        } else {
            interpreter.runtimeError("OBJECT ARRAY::get>> Index out of range [" + index + "]", line_number);
        }
        return null;
    }

    public Object set(Object _index, Object _value) throws RuntimeErrorException {
        int index = -1;
        if (_index instanceof Numeric) {
            index = (int) Math.round((Double) ((Numeric) _index).getNativeValue());
        } else {
            interpreter.runtimeError("OBJECT ARRAY::set>> Index must be type Numeric [" + _index.getClass() + "]", line_number);
        }
        if (index>=0 && index<value.size()) {
            return value.set(index, _value);
        } else {
            interpreter.runtimeError("OBJECT ARRAY::set>> Index out of range [" + index + "]", line_number);
        }
        return null;
    }
}
