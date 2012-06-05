package language.executable;

/**
 * オペレータのクラス。
 * @author Laurent Fabre
 */
public enum Operator {
    /**
     * 各オペレータタイプ
     */
    OPERATOR_PLUS("+"), OPERATOR_PLUS_PLUS("++"), OPERATOR_MINUS("-"), OPERATOR_MINUS_MINUS("--"),
        OPERATOR_MULT("*"), OPERATOR_DIV("/"),
        OPERATOR_AND("&"), OPERATOR_OR("|"),
        OPERATOR_CMP_LT("<"), OPERATOR_CMP_LT_EQ("<="), OPERATOR_CMP_GT(">"), OPERATOR_CMP_GT_EQ(">="),
        OPERATOR_CMP_EQ("=="), OPERATOR_CMP_NEQ("!=");
    /**
     * このオペレータを表す文字列
     */
    private final String string;
    /**
     * コンストラクタ
     * @param _string
     */
    private Operator(String _string) {
        string = _string;
    }
    @Override
    public String toString() {
        return string;
    }
}
