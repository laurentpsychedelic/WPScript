/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

import java.util.LinkedList;

/**
 *
 * @author laurent
 */
public class Term extends Calculable {
    LinkedList<Object> elements;
    public Term(GrammarParser _interpreter, LinkedList<Object> _elements) {
        elements = _elements;
        interpreter = _interpreter;
        line_number = interpreter.line_number;
    }

    private Numeric _doOperation(Object ele1, Operator operator, Object ele2) {
        double val = 0;
        try {
            double val1 = Double.NaN;
            double val2 = Double.NaN;
            if (ele1 instanceof Numeric) {
                val1 = (Double) ((Numeric) ele1).getNativeValue();
            } else {
                interpreter._WPAScriptPanic("Arithmetic operation must be done with Numeric or Double type!", line_number);
            }
            if (ele2 instanceof Numeric) {
                val2 = (Double) ((Numeric) ele2).getNativeValue();
            } else {
                interpreter._WPAScriptPanic("Arithmetic operation must be done with Numeric or Double type!", line_number);
            }

            if (operator == Operator.OPERATOR_PLUS) {
                    val = val1 + val2;
            } else if (operator == Operator.OPERATOR_MINUS) {
                    val = val1 - val2;
            } else if (operator == Operator.OPERATOR_MULT) {
                    val = val1 * val2;
            } else if (operator == Operator.OPERATOR_DIV) {
                if (val2 == 0) {
                    interpreter._WPAScriptRuntimeError("Division by 0 !", line_number);
                } else {
                    val = val1 / val2;
                }
            } else {
                interpreter._WPAScriptPanic("Wrong operator! [" + operator + "]", line_number);
            }
        } catch (ClassCastException e) {
            interpreter._WPAScriptPanic(e.getMessage(), line_number);
        }
        return new Numeric(val);
    }
    /**
     * Calculate the product/division of atoms represented by this object
     * @return
     */
    @Override
    public Object eval() {
        if (elements.size()%2!=1) {
            interpreter._WPAScriptPanic("Wrong number of elements in TERM list.", line_number);
        }
        try {
            Object val;
            Object ele = elements.get(0);
            if (ele instanceof Calculable) {
                ele = ((Calculable) ele).eval();
            } else if (ele instanceof String) {
                
            } else {
                interpreter._WPAScriptPanic("TERM element must be a Numeric type or an expression [" + ele.getClass() + "]", line_number);
            }
            val = ele;
            for (int k=1; k<elements.size(); k+=2) {
                Operator operator = (Operator) (elements.get(k));
                Object ele2 = elements.get(k+1);
                if (ele2 instanceof Calculable) {
                    ele2 = ((Calculable) ele2).eval();
                } else {
                    interpreter._WPAScriptPanic("TERM element must be a Numeric type or an expression [" + ele2.getClass() + "]", line_number);
                }
                val = _doOperation(ele, operator, ele2);
                ele = val;
            }
            return val;
        } catch (ClassCastException e) {
            interpreter._WPAScriptPanic(e.getMessage(), line_number);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("TERMS:: " + ((Object) this).hashCode());
        str.append(" >> ");
        Object ele = elements.get(0);
        if (ele instanceof Term) {
            str.append("(");
            str.append(((Term) ele).toString());
            str.append(")");
        } else if (ele instanceof Calculable) {
            str.append(((Calculable) ele).toString());
        } else if (ele instanceof String) {
            str.append("VAR:").append((String) ele);
        } else {
            interpreter._WPAScriptPanic("TERM element must be a Numeric type or an expression [" + ele.getClass() + "]", line_number);
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
            } else {
                interpreter._WPAScriptPanic("TERM element must be a Numeric type or an expression [" + ele2.getClass() + "]", line_number);
            }
        }
        return str.toString();
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
        if (elements.size()%2!=1) {
            interpreter._WPAScriptPanic("Wrong number of elements in TERM list.", line_number);
        }
        try {
            Object ele = elements.get(0);
            if (ele instanceof Calculable) {
                ((Calculable) ele).compilationCheck();
            } else if (ele instanceof String) {
                //NOTHING
            } else {
                interpreter._WPAScriptPanic("TERM element must be a Numeric type or an expression [" + ele.getClass() + "[", line_number);
            }
            for (int k=1; k<elements.size(); k+=2) {
                Object operator = elements.get(k);
                if (!(operator instanceof Operator)) {
                    interpreter._WPAScriptPanic("Must be an operator [" + operator.getClass() + "]", line_number);
                }

                Object ele2 = elements.get(k+1);
                if (ele2 instanceof Calculable) {
                    ((Calculable) ele2).compilationCheck();
                } else {
                    interpreter._WPAScriptPanic("TERM element must be a Numeric type or an expression [" + ele2.getClass() + "]", line_number);
                }
            }
        } catch (ClassCastException e) {
            interpreter._WPAScriptPanic(e.getMessage(), line_number);
        }
    }
}
