package language.executable;

import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.exceptions.RuntimeErrorException;
import language.memory.Environment;

/**
 * 実際のリターン値（計算された値）意外に戻るタイプ。
 * BREAK, CONTINUEなどです。
 * 入れ子構造を越えたBREAKなどを可能にするため。
 * @author Laurent FABRE
 */
public class ReturnValue extends Calculable {
    /**
     * リターン値のタイプを表す列挙型
     */
    private enum Type {
        /**
         * 各リターンバリューを表すタイプ
         */
        RETURN_BREAK("BREAK"), RETURN_CONTINUE("CONTINUE"), RETURN_RETURN("RETURN"), RETURN_NULL("VOID");
        /**
         * このタイプを表す文字列
         */
        private final String string;
        /**
         * コンストラクタ
         * @param _string
         */
        private Type(String _string) {
            string = _string;
        }
    }
    /**
     * このリターンバリューのタイプ
     */
    private final Type type;
    /**
     * BREAKが戻り値のリターン値
     */
    public static final ReturnValue RETURN_BREAK = new ReturnValue(Type.RETURN_BREAK);
    /**
     * CONTINUEが戻り値のリターン値
     */
    public static final ReturnValue RETURN_CONTINUE = new ReturnValue(Type.RETURN_CONTINUE);
    /**
     * RETURNが戻り値のリターン値
     */
    public static final ReturnValue RETURN_RETURN = new ReturnValue(Type.RETURN_RETURN);
    /**
     * NULLが戻り値のリターン値
     */
    public static final ReturnValue RETURN_NULL = new ReturnValue(Type.RETURN_NULL);
    /**
     * コンストラクタ
     * @param _type このリターン値のタイプ
     */
    private ReturnValue(Type _type) {
        type = _type;
    }

    @Override
    public void setEnv(Environment _env) {
        //NOTHING
    }

    @Override
    public void compilationCheck() throws CompilationErrorException, PanicException {
        //NOTHING
    }

    @Override
    public String toString() {
        return type.string;
    }

    @Override
    public Object eval() throws PanicException, RuntimeErrorException {
        return this;
    }

    @Override
    public Calculable getSimplifiedCalculable() throws PanicException {
        return this;
    }
}
