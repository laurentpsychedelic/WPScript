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
public class VariableAssignment extends Calculable {
    String var_name;
    Calculable expression;
    public VariableAssignment(GrammarParser _interpreter, String _var_name, boolean _increment_decrement) {
        LinkedList<Object> elements = new LinkedList();
        elements.add(new Variable(_interpreter, _var_name));
        elements.add(_increment_decrement ? Operator.OPERATOR_PLUS : Operator.OPERATOR_MINUS);
        elements.add(new Numeric(1.0f));
        Term _expression = new Term(_interpreter, elements);
        _init(_interpreter, _var_name,  _expression);
        line_number = interpreter.line_number;
    }
    public VariableAssignment(GrammarParser _interpreter, String _var_name, Calculable _expression) {
        _init(_interpreter, _var_name,  _expression);
        line_number = interpreter.line_number;
    }
    public VariableAssignment(GrammarParser _interpreter, int _line_number, String _var_name, Calculable _expression) {
        _init(_interpreter, _var_name, _expression);
        line_number = _line_number;
    }
    
    private void _init(GrammarParser _interpreter, String _var_name, Calculable _expresion) {
        expression = _expresion;
        var_name = _var_name;
        interpreter = _interpreter;
    }

    @Override
    public Object eval() throws PanicException {
        Object ret_val = expression.eval();
        interpreter.memory.put(var_name, ret_val);
        return ret_val;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        interpreter.compilation_memory.put(var_name, null);
        expression.compilationCheck();
    }

    @Override
    public String toString() {
        return "VAR_ASSIGN:: [VAR:" + var_name + "] <- [EXPR:" + expression.toString() + "]";
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        Calculable new_expression = expression.getSimplifiedCalculable();
        return new VariableAssignment(interpreter, line_number, var_name, new_expression);
    }

}
