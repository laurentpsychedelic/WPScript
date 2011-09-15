/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public class VariableAssignment extends Calculable {
    String var_name;
    Expression expression;
    public VariableAssignment(GrammarParser _interpreter, String _var_name, Expression _expresion) {
        expression = _expresion;
        var_name = _var_name;
        interpreter = _interpreter;
        line_number = interpreter.line_number;
    }

    @Override
    public Object eval() {
        Object ret_val = expression.eval();
        interpreter.memory.put(var_name, ret_val);
        return ret_val;
    }

    @Override
    public void compilationCheck() throws CompilationErrorException {
        interpreter.compilation_memory.put(var_name, null);
        expression.compilationCheck();
    }

    @Override
    public String toString() {
        return "VAR_ASSIGN:: [VAR:" + var_name + "] <- [EXPR:" + expression.toString() + "]";
    }

}
