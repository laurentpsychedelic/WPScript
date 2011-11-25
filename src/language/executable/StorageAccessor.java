/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable;

import language.exceptions.CompilationErrorException;
import language.ScriptParser;
import language.exceptions.PanicException;
import language.exceptions.RuntimeErrorException;
import language.executable.builtintypes.Numeric;
import language.executable.builtintypes.ObjectArray;
import language.executable.builtintypes.Dictionary;
import language.executable.builtintypes.CharString;
import language.memory.Environment;

/**
 *
 * @author laurent
 */
public class StorageAccessor extends Calculable {
    Calculable expression_array;
    Calculable expression_index;
    Calculable expression_content;
    Object accessor_type;
    public static final Object REFERENCE = new Object();
    public static final Object ASSIGNMENT = new Object();

    public StorageAccessor(ScriptParser _interpreter, Object _accessor_type, StorageAccessor _reference_accessor, Calculable _expression_content) {
        accessor_type = _accessor_type;
        expression_array = _reference_accessor.expression_array;
        expression_index = _reference_accessor.expression_index;
        expression_content = _expression_content;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
    }
    public StorageAccessor(ScriptParser _interpreter, Object _accessor_type, Calculable _expression_array, Calculable _expression_index, Calculable _expression_content) {
        accessor_type = _accessor_type;
        expression_array = _expression_array;
        expression_index = _expression_index;
        expression_content = _expression_content;
        interpreter = _interpreter;
        line_number = interpreter.getLineNumber();
    }

    @Override
	public String toString() {
	if (accessor_type==REFERENCE) {
	    return "ARRAY_ELE:: " + expression_array + " [ " + expression_index + " ]";
	} else if (accessor_type==ASSIGNMENT) {
	    return "ARRAY_ELE_ASSIGN:: [" + expression_array  + " [ " + expression_index + " ] " + "] <- [EXPR:" + expression_content + "]";
	} else {
	    return "ARRAY_ELE[UNKNOW REFERENCE TYPE]:: [ARRAY:" + expression_array  + " ; INDEX:" + expression_index + " ; " + " ; CONTENT:" + expression_content + "]";
	}
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
	expression_array.setEnv(_env);
	expression_index.setEnv(_env);
	if (expression_content!=null) {
	    expression_content.setEnv(_env);
	}
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        expression_array.compilationCheck();
        expression_index.compilationCheck();
	if (expression_content!=null) {
	    expression_content.compilationCheck();
	}
    }

    @Override
    public Object eval() throws RuntimeErrorException, PanicException {
        Object val = expression_array.eval();
        if (val == null) {
            interpreter.scriptPanic("ARRAY ELEMENT REFERENCE>> array name expression returned null", line_number);
        }
        if (val instanceof ObjectArray || val instanceof Dictionary) {
	    if (val instanceof ObjectArray) {
		ObjectArray array = (ObjectArray) val;
		Object index = expression_index.eval();
		if (val == null) {
		    interpreter.scriptPanic("STORAGE ACCESSOR>> array index expression returned null", line_number);
		}
		if (index instanceof Numeric) {
		    if (accessor_type==ASSIGNMENT) {
			if (expression_content==null) {
			    interpreter.scriptPanic("STORAGE ACCESSOR>> expression for array element assignment returned null", line_number);
			}
			Object new_value = expression_content.eval();
			array.set(index, new_value);
		    }
		    return array.get(index);
		} else {
		    interpreter.runtimeError("STORAGE ACCESSOR>> Index must be of type Numeric", line_number);
		}
	    } else { //Dictionary
		Dictionary dict = (Dictionary) val;
		Object index = expression_index.eval();
		if (val == null) {
		    interpreter.scriptPanic("STORAGE ACCESSOR>> dictionary index expression returned null", line_number);
		}
		if (index instanceof CharString) {
		    if (accessor_type==ASSIGNMENT) {
			if (expression_content==null) {
			    interpreter.scriptPanic("STORAGE ACCESSOR>> expression for dict element assignment returned null", line_number);
			}
			Object new_value = expression_content.eval();
			dict.set(index, new_value);
		    }
		    return dict.get(index);
		} else {
		    interpreter.runtimeError("STORAGE ACCESSOR>> Index must be of type CharString", line_number);
		}
	    }
        } else {
            interpreter.runtimeError("STORAGE ACCESSOR>> Only types ObjectArray or Dictionary can be referenced [" + val.getClass() + "]", line_number);
        }
        return val;
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
	expression_array = expression_array.getSimplifiedCalculable();
	expression_index = expression_index.getSimplifiedCalculable();
	if (expression_content!=null) {
	    expression_content = expression_content.getSimplifiedCalculable();
	}
	return this;
    }

}
