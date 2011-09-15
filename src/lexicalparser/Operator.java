/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexicalparser;

/**
 *
 * @author laurent
 */
public class Operator extends Object {
    public static final Operator OPERATOR_PLUS = new Operator();
    public static final Operator OPERATOR_MINUS= new Operator();
    public static final Operator OPERATOR_MULT = new Operator();
    public static final Operator OPERATOR_DIV  = new Operator();
    @Override
    public String toString() {
        String str = "?";
        if (this.equals(OPERATOR_PLUS)) {
            str = "+";
        } else if (this.equals(OPERATOR_MINUS)) {
            str = "-";
        } else if (this.equals(OPERATOR_MULT)) {
            str = "*";
        } else if (this.equals(OPERATOR_DIV)) {
            str = "/";
        }
        return str;
    }
}
