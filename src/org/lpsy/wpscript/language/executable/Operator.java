package org.lpsy.wpscript.language.executable;

/**
 * Operator class
 * @author Laurent FABRE, 2011-2015
 */
public enum Operator {
    OPERATOR_PLUS("+"), OPERATOR_PLUS_PLUS("++"), OPERATOR_MINUS("-"), OPERATOR_MINUS_MINUS("--"),
        OPERATOR_MULT("*"), OPERATOR_DIV("/"),
        OPERATOR_AND("&"), OPERATOR_OR("|"),
        OPERATOR_CMP_LT("<"), OPERATOR_CMP_LT_EQ("<="), OPERATOR_CMP_GT(">"), OPERATOR_CMP_GT_EQ(">="),
        OPERATOR_CMP_EQ("=="), OPERATOR_CMP_NEQ("!=");
    private final String string;
    private Operator(String _string) {
        string = _string;
    }
    @Override
    public String toString() {
        return string;
    }
}
