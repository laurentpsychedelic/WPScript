/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package language.executable;

/**
 *
 * @author laurent
 */
public class Operator extends Object {
    public static final Operator OPERATOR_PLUS       = new Operator();
    public static final Operator OPERATOR_PLUS_PLUS  = new Operator();
    public static final Operator OPERATOR_MINUS      = new Operator();
    public static final Operator OPERATOR_MINUS_MINUS= new Operator();
    public static final Operator OPERATOR_MULT = new Operator();
    public static final Operator OPERATOR_DIV  = new Operator();
    public static final Operator OPERATOR_AND  = new Operator();
    public static final Operator OPERATOR_OR   = new Operator();
    public static final Operator OPERATOR_CMP_LT    = new Operator();
    public static final Operator OPERATOR_CMP_LT_EQ = new Operator();
    public static final Operator OPERATOR_CMP_GT    = new Operator();
    public static final Operator OPERATOR_CMP_GT_EQ = new Operator();
    public static final Operator OPERATOR_CMP_EQ    = new Operator();
    public static final Operator OPERATOR_CMP_NEQ   = new Operator();
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
        } else if (this.equals(OPERATOR_CMP_LT)) {
            str = "<";
        } else if (this.equals(OPERATOR_CMP_LT_EQ)) {
            str = "<=";
        } else if (this.equals(OPERATOR_CMP_GT)) {
            str = ">";
        } else if (this.equals(OPERATOR_CMP_GT_EQ)) {
            str = ">=";
        } else if (this.equals(OPERATOR_CMP_EQ)) {
            str = "==";
        } else if (this.equals(OPERATOR_CMP_NEQ)) {
            str = "!=";
        } else if (this.equals(OPERATOR_AND)) {
            str = "&";
        } else if (this.equals(OPERATOR_OR)) {
            str = "|";
        }
        return str;
    }
}
