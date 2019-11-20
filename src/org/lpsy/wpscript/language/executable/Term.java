package org.lpsy.wpscript.language.executable;

import org.lpsy.wpscript.language.executable.builtintypes.CharString;
import org.lpsy.wpscript.language.executable.builtintypes.Numeric;
import org.lpsy.wpscript.language.executable.builtintypes.BuiltInType;
import org.lpsy.wpscript.language.executable.builtintypes.Bool;
import java.util.LinkedList;
import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.memory.Environment;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class Term extends Calculable {
    LinkedList<Object> elements;
    public Term(ScriptParser _interpreter, LinkedList<Object> _elements) {
        _init(_interpreter, _elements);
        line_number = interpreter.getLineNumber();
    }
    public Term(ScriptParser _interpreter, int _line_number, LinkedList<Object> _elements) {
        _init(_interpreter, _elements);
        line_number = _line_number;
    }

    private void _init(ScriptParser _interpreter, LinkedList<Object> _elements) {
        elements = _elements;
        interpreter = _interpreter;
    }

    private BuiltInType _doOperation(Object ele1, Operator operator, Object ele2) throws PanicException, RuntimeErrorException {
        BuiltInType return_val = null;
        try {
            if (ele1 instanceof Numeric && ele2 instanceof Numeric) {
                double val1 = (Double) ((Numeric) ele1).getNativeValue();
                double val2 = (Double) ((Numeric) ele2).getNativeValue();
                if (operator == Operator.OPERATOR_PLUS || operator == Operator.OPERATOR_MINUS || operator == Operator.OPERATOR_MULT || operator == Operator.OPERATOR_DIV) {
                    double val = Double.NaN;
                    /*   */if (operator == Operator.OPERATOR_PLUS) {
                            val = val1 + val2;
                    } else if (operator == Operator.OPERATOR_MINUS) {
                            val = val1 - val2;
                    } else if (operator == Operator.OPERATOR_MULT) {
                            val = val1 * val2;
                    } else if (operator == Operator.OPERATOR_DIV) {
                        if (val2 == 0) {
                            interpreter.runtimeError("Division by 0 !", line_number);
                        } else {
                            val = val1 / val2;
                        }
                    } else {
                        interpreter.scriptPanic("Wrong operator! [" + operator + "]", line_number);
                    }
                    return_val =  new Numeric(val);
                } else if (operator == Operator.OPERATOR_CMP_LT || operator == Operator.OPERATOR_CMP_GT
                        || operator == Operator.OPERATOR_CMP_LT_EQ || operator == Operator.OPERATOR_CMP_GT_EQ
                        || operator == Operator.OPERATOR_CMP_EQ || operator == Operator.OPERATOR_CMP_NEQ) {
                    boolean val = false;
                    /*   */if (operator == Operator.OPERATOR_CMP_LT) {
                        val = val1 <  val2;
                    } else if (operator == Operator.OPERATOR_CMP_LT_EQ) {
                        val = val1 <= val2;
                    } else if (operator == Operator.OPERATOR_CMP_GT) {
                        val = val1 >  val2;
                    } else if (operator == Operator.OPERATOR_CMP_GT_EQ) {
                        val = val1 >= val2;
                    } else if (operator == Operator.OPERATOR_CMP_EQ) {
                        val = val1 == val2;
                    } else if (operator == Operator.OPERATOR_CMP_NEQ) {
                        val = val1 != val2;
                    }
                    return_val = new Bool(val);
                } else {
                    interpreter.scriptPanic("Wrong operator! [" + operator + "]", line_number);
                }
            } else if (ele1 instanceof Bool && ele2 instanceof Bool) {
                boolean val = false;
                boolean val1 = (Boolean) ((Bool) ele1).getNativeValue();
                boolean val2 = (Boolean) ((Bool) ele2).getNativeValue();
                if (operator == Operator.OPERATOR_AND || operator == Operator.OPERATOR_OR) {
                    /*   */if (operator == Operator.OPERATOR_AND) {
                        val = val1 & val2;
                    } else if (operator == Operator.OPERATOR_OR) {
                        val = val1 | val2;
                    }
                    return_val = new Bool(val);
                } else {
                    interpreter.scriptPanic("Wrong operator! [" + operator + "]", line_number);
                }
            } else if (ele1 instanceof CharString || ele2 instanceof CharString) {
                if (operator == Operator.OPERATOR_PLUS) {
                    if (ele1 instanceof CharString && ele2 instanceof CharString) {
                        String str1 = (String) ((CharString) ele1).getNativeValue();
                        String str2 = (String) ((CharString) ele2).getNativeValue();
                        return_val = new CharString(str1 + str2);
                    } else {
                        if (ele2 instanceof CharString) {
                            String str1 = null;
                            if (ele1 instanceof BuiltInType) {
                                str1 = ((Numeric) ele1).getNativeValue().toString();
                            } else {
                                str1 = ele1.toString();
                            }
                            String str2 = (String) ((CharString) ele2).getNativeValue();
                            return_val = new CharString(str1 + str2);
                        } else if (ele1 instanceof CharString) {
                            String str1 = (String) ((CharString) ele1).getNativeValue();
                            String str2 = null;
                            if (ele2 instanceof BuiltInType) {
                                str2 = ((BuiltInType) ele2).getNativeValue().toString();
                            } else {
                                str2 = ele2.toString();
                            }
                            return_val = new CharString(str1 + str2);
                        } else {
                            interpreter.scriptPanic("String concatenation undefined for given types [" + ele1 + " " + operator + " " + ele2 + "]" , line_number);
                        }
                    }
                } else if (operator == Operator.OPERATOR_MULT) {
                    if (ele1 instanceof CharString && ele2 instanceof CharString) {
                        interpreter.scriptPanic("String duplication undefined for given types [" + ele1 + " " + operator + " " + ele2 + "]" , line_number);
                    } else {
                        if (ele1 instanceof Numeric) {
                            int num = (int) Math.round( (Double) ((Numeric) ele1).getNativeValue());
                            String str_ele = (String) ((CharString) ele2).getNativeValue();
                            StringBuilder str = new StringBuilder();
                            for (int k=0; k<num; k++) {
                                str.append(str_ele);
                            }
                            return_val = new CharString(str.toString());
                        } else if (ele2 instanceof Numeric) {
                            int num = (int) Math.round( (Double) ((Numeric) ele2).getNativeValue());
                            String str_ele = (String) ((CharString) ele1).getNativeValue();
                            StringBuilder str = new StringBuilder();
                            for (int k=0; k<num; k++) {
                                str.append(str_ele);
                            }
                            return_val = new CharString(str.toString());
                        } else {
                            interpreter.scriptPanic("String duplication undefined for given types [" + ele1 + " " + operator + " " + ele2 + "]" , line_number);
                        }
                    }
                } else {
                    interpreter.runtimeError("Operation unsupported in current language version for given types [" + ele1 + " " + operator + " " + ele2 + "]" , line_number);
                }
            } else {
                interpreter.runtimeError("Unknown operation for given types [" + ele1 + " " + operator + ele2 + "]" , line_number);
            }

        } catch (ClassCastException e) {
            interpreter.scriptPanic(e.getMessage(), line_number);
        }
        return return_val;
    }
    /**
     * Calculate the product/division of atoms represented by this object
     * @return
     */
    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        if (elements.size()%2!=1) {
            interpreter.scriptPanic("Wrong number of elements in TERM list.", line_number);
        }
        try {
            Object val;
            Object ele = elements.get(0);
            if (ele instanceof Calculable) {
                ele = ((Calculable) ele).eval();
            } else if (!(ele instanceof BuiltInType)) {
                interpreter.scriptPanic("TERM element must be a Numeric type or an expression [" + ele.getClass() + "]", line_number);
            }
            val = ele;
            for (int k=1; k<elements.size(); k+=2) {
                Operator operator = (Operator) (elements.get(k));
                Object ele2 = elements.get(k+1);
                if (ele2 instanceof Calculable) {
                    ele2 = ((Calculable) ele2).eval();
                } else {
                    interpreter.scriptPanic("TERM element must be a Numeric type or an expression [" + ele2.getClass() + "]", line_number);
                }
                val = _doOperation(ele, operator, ele2);
                ele = val;
            }
            return val;
        } catch (ClassCastException e) {
            interpreter.scriptPanic(e.getMessage(), line_number);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("TERMS:: ");
        Object ele = elements.get(0);
        if (ele instanceof Term) {
            str.append("(");
            str.append(((Term) ele).toString());
            str.append(")");
        } else if (ele instanceof Calculable) {
            str.append(((Calculable) ele).toString());
        } else if (ele instanceof String) {
            str.append("VAR:").append((String) ele);
        }
        for (int k=1; k<elements.size(); k+=2) {
            Operator operator = (Operator) (elements.get(k));
            str.append(" ").append(operator.toString()).append(" ");
            Object ele2 = elements.get(k+1);
            if (ele2 instanceof Term) {
                str.append("(");
                str.append(((Term) ele2).toString());
                str.append(")");
            } else if (ele2 instanceof Calculable) {
                str.append(((Calculable) ele2).toString());
            }
        }
        return str.toString();
    }

    @Override
    public void setEnv(Environment _env) {
	env = _env;
	for (Object element : elements) {
	    if (element instanceof Calculable) {
		((Calculable) element).setEnv(_env);
	    }
	}
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        if (elements.size()%2!=1) {
            interpreter.scriptPanic("Wrong number of elements in TERM list.", line_number);
        }
        try {
            Object ele = elements.get(0);
            if (ele instanceof Calculable) {
                ((Calculable) ele).compilationCheck();
            } else if (ele instanceof String) {
                //NOTHING
            } else {
                interpreter.scriptPanic("TERM element must be a Numeric type or an expression [" + ele.getClass() + "[", line_number);
            }
            for (int k=1; k<elements.size(); k+=2) {
                Object operator = elements.get(k);
                if (!(operator instanceof Operator)) {
                    interpreter.scriptPanic("Must be an operator [" + operator.getClass() + "]", line_number);
                }

                Object ele2 = elements.get(k+1);
                if (ele2 instanceof Calculable) {
                    ((Calculable) ele2).compilationCheck();
                } else {
                    interpreter.scriptPanic("TERM element must be a Numeric type or an expression [" + ele2.getClass() + "]", line_number);
                }
            }
        } catch (ClassCastException e) {
            interpreter.scriptPanic(e.getMessage(), line_number);
        }
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        if (elements.size()==1) {
            if (elements.get(0) instanceof Calculable) {
                return ((Calculable) elements.get(0)).getSimplifiedCalculable();
            }
        }
        LinkedList<Object> new_elements = new LinkedList();
        Object ele = elements.get(0);
        if (ele instanceof Calculable) {
            ele = ((Calculable)ele).getSimplifiedCalculable();
            new_elements.add(((Calculable)ele).getSimplifiedCalculable());
        } else {
            interpreter.scriptPanic("TERM element must be a Numeric type or an expression [" + ele.getClass() + "]", line_number);
        }
        for (int k=1; k<elements.size(); k+=2) {
            Object ele2 = elements.get(k+1);

            if  (ele2 instanceof Calculable) {
                ele2 = ((Calculable)ele2).getSimplifiedCalculable();
            } else {
                interpreter.scriptPanic("TERM element must be a Numeric type or an expression [" + ele2.getClass() + "]", line_number);
            }
            Operator operator = (Operator) (elements.get(k));
            /* !! BUGGY(OPERATOR PRIORITY): TODO FIX
            if (ele instanceof BuiltInType && ele2 instanceof BuiltInType) {
                BuiltInType new_ele = null;
                try {
                    new_ele = _doOperation(ele, operator, ele2);
                } catch (RuntimeErrorException re) {
                    throw new PanicException(re.getMessage(), re.getLineNumber());
                }
                new_elements.set(new_elements.size()-1, new_ele);
                ele = new_ele;
            } else */ {
                new_elements.add(operator);
                Calculable new_ele = ((Calculable)ele2).getSimplifiedCalculable();
                new_elements.add(new_ele);
                ele = new_ele;
            }
        }
        if (new_elements.size()==1) {
            if (new_elements.get(0) instanceof Calculable) {
                return ((Calculable) new_elements.get(0)).getSimplifiedCalculable();
            }
        }
        return new Term(interpreter, line_number, new_elements);
    }
}
