grammar Script;

options {
    output = AST;
    backtrack = true;
    memoize = true;
}

@lexer::header {
package language;
}

@header {
package language;

import java.io.PrintStream;
import java.util.LinkedList;
import language.memory.*;
import language.executable.*;
import language.executable.builtintypes.*;
import language.exceptions.*;
}

@rulecatch {
    catch (RecognitionException rec_exc) {
        throw rec_exc;
    }
}

@members {
    
    /*@Override
    protected void mismatch(IntStream input, int ttype, BitSet follow)
        throws RecognitionException
    {
      throw new MismatchedTokenException(ttype, input);
    }*/
    @Override
    public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow)
        throws RecognitionException
    {
        throw e;
    }

    /** Map variable name to Integer object holding value */
    private Environment env = new Environment(null);
    public static final Environment env_const = new Environment(null);
    static {
        env_const.addConstants();
    }
    public Environment functions_env = new Environment(null);
    private LinkedList <Expression> commands = new LinkedList();
    private int line_number = 1;
    public int getLineNumber() {
        return line_number;
    }

    private boolean _PANIC_STATE_ = false;
    private boolean _COMPILATION_ERROR_STATE_ = false;
    private boolean _RUNTIME_ERROR_STATE_ = false;

    public boolean __DEBUG__ = false;

    public void dumpGlobalMemory(PrintStream ps) {
        ps.println("\nGLOBAL env DUMP");
        for (Object o : env.getEntries()) {
            Object val = env.getValue(o.toString());
            ps.println("VAR [" + o + "]->" + val);
        }
        ps.println("\nUSER DEFINED FUNCTIONS");
        for (Object o : functions_env.getEntries()) {
            Object val = functions_env.getValue(o.toString());
            ps.println("USER FUNC->" + val);
        }
    }

    public void dumpScriptCommands() {
        System.out.println("\nSCRIPT COMMANDS DUMP");
        for (Object o : commands) {
            System.out.println("COMMAND->" + o);
        }
    }

    public void treeRefactoring() throws PanicException {
        LinkedList <Expression> new_commands = new LinkedList();
        if (__DEBUG__) {
            System.out.println("\nTREE REFACTORING...");
        }
        for (Object o : commands) {
            if (!(o instanceof Expression)) {
                scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
            }
            Calculable new_command = ((Expression) o).getSimplifiedCalculable();
            if (new_command instanceof Expression) {
                new_commands.add( (Expression) new_command );
            } else {
                scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
            }
        }
        commands = new_commands;
        if (__DEBUG__) {
            System.out.println(" OVER");
        }
    }

    public void setEnv() throws PanicException {
        for (Object o : commands) {
            if (!(o instanceof Expression)) {
                scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
            }
            ((Expression) o).setEnv(env);
        }
    }

    public boolean compilationCheck() throws PanicException, CompilationErrorException {
        try {
            env.compilation_map.clear();
            if (__DEBUG__) {
                System.out.println("\nCOMPILATION CHECK...");
            }
            for (Object o : commands) {
                if (!(o instanceof Expression)) {
                    scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                }
                ((Expression) o).compilationCheck();
            }
            env.compilation_map.clear();
            if (__DEBUG__) {
                System.out.println(" OK");
            }
            return true;
        } catch (CompilationErrorException e) {
            compilationError(e.getMessage(), e.getLineNumber());
            return false;
        }
    }

    public Object execute() throws PanicException, RuntimeErrorException {
        env.clear();
        Object ret_val = null;
        for (Object c : commands) {
            if (!(c instanceof Expression)) {
                scriptPanic("Top level command must be instances of Expression!", 0);
            }
            ret_val = ((Expression) c).eval();
        }
        return ret_val;
    }

    public void scriptPanic(String message, int line_num) throws PanicException {

        if (!_PANIC_STATE_) {
            _PANIC_STATE_ = true;
            System.err.println("PANIC OCCURED!");
        }
        System.err.println("ERROR (l" + line_num + "):: " + message);
        if (__DEBUG__) {
            dumpGlobalMemory(System.err);
        }
        throw new PanicException("Panic: " + "ERROR (l" + line_num + "):: " + message, line_num);
    }


    public void compilationError(String message, int line_num) throws CompilationErrorException {
        if (!_COMPILATION_ERROR_STATE_) {
            _COMPILATION_ERROR_STATE_ = true;
            System.err.println("COMPILATION");
        }
        System.err.println("ERROR (l" + line_num + "):: " + message);
        throw new CompilationErrorException("Compilation error: " + "ERROR (l" + line_num + "):: " + message, line_number);
    }


    public void compilationWarning(String message, int line_num) {
        if (!_COMPILATION_ERROR_STATE_) {
            _COMPILATION_ERROR_STATE_ = true;
            System.err.println("COMPILATION");
        }
        System.err.println("WARNING (l" + line_num + "):: " + message);
    }

    public void runtimeError(String message, int line_num) throws RuntimeErrorException {
        if (!_RUNTIME_ERROR_STATE_) {
            _RUNTIME_ERROR_STATE_ = true;
            System.err.println("RUNTIME");
        }
        if (__DEBUG__) {
            dumpGlobalMemory(System.err);
        }
        System.err.println("ERROR (l" + line_num + "):: " + message);
        throw new RuntimeErrorException("Runtime error: " + "ERROR (l" + line_num + "):: " + message, line_num);
    }

    public void runtimeWarning(String message, int line_num) {
        if (!_RUNTIME_ERROR_STATE_) {
            _RUNTIME_ERROR_STATE_ = true;
            System.err.println("RUNTIME");
        }
        System.err.println("WARNING (l" + line_num + "):: " + message);
    }

    public static void main(String[] args) throws Exception {

        ScriptLexer lex = new ScriptLexer(new ANTLRStringStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        ScriptParser parser = new ScriptParser(tokens);

        try {
            parser.prog();
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}

prog :
    s=stats {
        commands.clear();
        for (Expression e : $s.expressions) {
            commands.add(e);
        }
    };

block returns [LinkedList<Expression> expressions]:
    LEFT_CB NEWLINE? stats NEWLINE? RIGHT_CB {
        $expressions = $stats.expressions;
    };

stats returns [LinkedList<Expression> expressions]:
    s=stat {
        $expressions = new LinkedList();
        if ($s.expr!=null) {
            $expressions.add($s.expr);
        }
    } (s=stat {
        if ($s.expr!=null) {
            $expressions.add($s.expr);
        }
    }
    )*;

stat returns [Expression expr]
    : pre_stat NEWLINE {
        $expr = $pre_stat.expr;
        line_number++;
    }
    | NEWLINE {
        line_number++;
    }
    | block {
        $expr = new Expression(true, this, $block.expressions);
    }
    | if_expression {
        $expr = new Expression(true, this, $if_expression.expr);
    }
    | while_expression {
        $expr = new Expression(true, this, $while_expression.expr);
    }
    | for_expression {
        $expr = new Expression(true, this, $for_expression.expr);
    }
    | function_declaration {
        $expr = new Expression(true, this, $function_declaration.expr);
    };
pre_stat returns [Expression expr]
    : expression {
        $expr = new Expression(true, $expression.expr);
    }
    | BREAK {
        $expr = new Expression(true, this, ReturnValue.RETURN_BREAK);
    }
    | CONTINUE {
        $expr = new Expression(true, this, ReturnValue.RETURN_CONTINUE);
    }
    | ID EQUAL expression {
        $expr = new Expression(true, this, new VariableAssignment(this, $ID.text, $expression.expr) );
    }
    | array_element_reference EQUAL expression {
        $expr = new Expression(true, this, new StorageAccessor(this, StorageAccessor.ASSIGNMENT, $array_element_reference.accessor, $expression.expr));
    }
    | ID PLUS_PLUS {
        $expr = new Expression(true, this, new VariableAssignment(this, $ID.text, Operator.OPERATOR_PLUS_PLUS));
    }
    | PLUS_PLUS ID {
        $expr = new Expression(true, this,  new VariableAssignment(this, $ID.text, Operator.OPERATOR_PLUS_PLUS));
    }
    | ID MINUS_MINUS {
        $expr = new Expression(true, this,  new VariableAssignment(this, $ID.text, Operator.OPERATOR_MINUS_MINUS));
    }
    | MINUS_MINUS ID {
        $expr = new Expression(true, this,  new VariableAssignment(this, $ID.text, Operator.OPERATOR_MINUS_MINUS));
    }
    ;
    

if_expression returns [IfExpression expr]
    : p=pre_if_expression {
        Expression condition = null;
        Expression expr_if = null;
        Expression expr_else = null;
        if (0 < $p.exprs.size()) {
            condition = $p.exprs.get(0);
        }
        if (1 < $p.exprs.size()) {
            expr_if = $p.exprs.get(1);
        }
        if (2 < $p.exprs.size()) {
            expr_else = $p.exprs.get(2);
        }
        $expr = new IfExpression( this, condition, expr_if, expr_else );
    };

pre_if_expression returns [LinkedList<Expression> exprs] 
    : IF LEFT_P e=expression RIGHT_P NEWLINE? s=stat {
        $exprs = new LinkedList();
        $exprs.add( $e.expr );
        $exprs.add( $s.expr );
    } NEWLINE? (ELSE NEWLINE? s=stat {
        $exprs.add( $s.expr );
    })?;

while_expression returns [LoopExpression expr]
    : p=pre_while_expression {
        Expression condition = null;
        Expression expression = null;
        if (0 < $p.exprs.size()) {
            condition = $p.exprs.get(0);
        }
        if (1 < $p.exprs.size()) {
            expression = $p.exprs.get(1);
        }
        $expr = new LoopExpression( this, condition, expression);
    };

pre_while_expression returns [LinkedList<Expression> exprs]
    : WHILE LEFT_P e=expression RIGHT_P NEWLINE? s=stat {
        $exprs = new LinkedList();
        $exprs.add( $e.expr );
        $exprs.add( $s.expr );
    };

for_expression returns [LoopExpression expr]
    : p=pre_for_expression {
        Expression init = null;
        Expression increment = null;
        Expression condition = null;
        Expression expression = null;
        init = $p.exprs.get(0);
        condition = $p.exprs.get(1);
        increment = $p.exprs.get(2);
        expression = $p.exprs.get(3);
        $expr = new LoopExpression( this, init, increment, condition, expression);
    };

pre_for_expression returns [LinkedList<Expression> exprs]
    : FOR LEFT_P e_init=pre_stat PV e_cond=expression PV e_inc=pre_stat RIGHT_P NEWLINE? s=stat {
        $exprs = new LinkedList();
        $exprs.add( $e_init.expr );
        $exprs.add( $e_cond.expr );
        $exprs.add( $e_inc.expr );
        $exprs.add( $s.expr );
    }
    | FOR LEFT_P ID EQUAL range RIGHT_P NEWLINE? s=stat {
        $exprs = new LinkedList();

        boolean plus_minus = true;

        Calculable init = $range.range_ele.get(0);
        Calculable increment = $range.range_ele.size()==3 ? $range.range_ele.get(1) : new Numeric(1.0f);
        Calculable condition = $range.range_ele.get($range.range_ele.size()-1);

        try {
            if (increment.getSimplifiedCalculable() instanceof Numeric) {
                double val = (Double) ((Numeric) increment.getSimplifiedCalculable()).getNativeValue();
                plus_minus = val>=0;
            }
        } catch (Exception e) {
            //NOTHING
        }

        VariableAssignment va = new VariableAssignment(this, $ID.text, init);
        Expression init_expr = new Expression(true, this, va);
        
        LinkedList<Object> term_ele = new LinkedList();
        term_ele.add(new Variable(this, $ID.text));
        term_ele.add(Operator.OPERATOR_PLUS);
        term_ele.add(increment);
        Term t = new Term(this, term_ele);
        VariableAssignment vai = new VariableAssignment(this, $ID.text, t);
        Expression increment_expr = new Expression(true, this, vai);

        LinkedList<Object> term_elec = new LinkedList();
        term_elec.add(new Variable(this, $ID.text));
        term_elec.add(plus_minus ? Operator.OPERATOR_CMP_LT_EQ : Operator.OPERATOR_CMP_GT_EQ);
        term_elec.add(condition);
        Term tc = new Term(this, term_elec);
        Expression condition_expr = new Expression(true, this, tc);

        $exprs = new LinkedList();
        $exprs.add( init_expr );
        $exprs.add( condition_expr );
        $exprs.add( increment_expr );
        $exprs.add( $s.expr );
    };

range returns [LinkedList<Calculable> range_ele]
    : a=expression {
        $range_ele = new LinkedList();
        $range_ele.add($a.expr);
    } (ARROW b=range {
        for (int k=0; k<$range_ele.size(); k++) {
            $b.range_ele.add(0, $range_ele.get(k));
        }
        $range_ele = $b.range_ele;
    } )*;

expression returns [Expression expr]
    : terms {
        $expr = new Expression( this, new Term(this, $terms.terms) );
    };

terms returns [LinkedList<Object> terms]
    : t=term {
        $terms = new LinkedList();
        $terms.add( new Term(this, $t.atoms) );
    } ( PLUS t=term {
            $terms.add(Operator.OPERATOR_PLUS);
            $terms.add( new Term(this, $t.atoms) );
        }
      | MINUS t=term {
            $terms.add(Operator.OPERATOR_MINUS);
            $terms.add( new Term(this, $t.atoms) );
        }
      | AND t=term {
            $terms.add(Operator.OPERATOR_AND);
            $terms.add( new Term(this, $t.atoms) );
        }
      | OR t=term {
            $terms.add(Operator.OPERATOR_OR);
            $terms.add( new Term(this, $t.atoms) );
        } )*;
//terms : term ( PLUS term | MINUS term )*;

term returns [LinkedList<Object> atoms]
    : a=atom {
        $atoms = new LinkedList();
        $atoms.add($a.value);
    } ( MULT a=atom {
            $atoms.add(Operator.OPERATOR_MULT);
            $atoms.add($a.value);
        }
      | DIV a=atom {
            $atoms.add(Operator.OPERATOR_DIV);
            $atoms.add($a.value);
        }
      | CMP_LT a=atom {
            $atoms.add(Operator.OPERATOR_CMP_LT);
            $atoms.add($a.value);
        } 
      | CMP_LT_EQ a=atom {
            $atoms.add(Operator.OPERATOR_CMP_LT_EQ);
            $atoms.add($a.value);
        } 
      | CMP_GT a=atom {
            $atoms.add(Operator.OPERATOR_CMP_GT);
            $atoms.add($a.value);
        }
      | CMP_GT_EQ a=atom {
            $atoms.add(Operator.OPERATOR_CMP_GT_EQ);
            $atoms.add($a.value);
        } 
      | CMP_EQ a=atom {
            $atoms.add(Operator.OPERATOR_CMP_EQ);
            $atoms.add($a.value);
        }
      | CMP_NEQ a=atom {
            $atoms.add(Operator.OPERATOR_CMP_NEQ);
            $atoms.add($a.value);
        }
    )*
    | ID PLUS_PLUS {
        $atoms = new LinkedList();
        VariableAssignment va = new VariableAssignment(this, $ID.text, Operator.OPERATOR_PLUS_PLUS);
        $atoms.add(va);
    }
    | PLUS_PLUS ID {
        $atoms = new LinkedList();
        VariableAssignment va = new VariableAssignment(this, $ID.text, Operator.OPERATOR_PLUS_PLUS);
        $atoms.add(va);
    }
    | ID MINUS_MINUS {
        $atoms = new LinkedList();
        VariableAssignment va = new VariableAssignment(this, $ID.text, Operator.OPERATOR_MINUS_MINUS);
        $atoms.add(va);
    }
    | MINUS_MINUS ID {
        $atoms = new LinkedList();
        VariableAssignment va = new VariableAssignment(this, $ID.text, Operator.OPERATOR_MINUS_MINUS);
        $atoms.add(va);
    };
//term : atom ( MULT atom | DIV atom )*;

function_declaration returns [FunctionDeclaration expr] :
    FUNCTION ID LEFT_P args? RIGHT_P NEWLINE? s=stat {
        $expr = new FunctionDeclaration(this, $ID.text, $args.params, $s.expr);
    };

function_call returns [LinkedList<Object> name_params]:
    ID LEFT_P args? RIGHT_P {
        if ($args.params != null) {
            $name_params = $args.params;
            $name_params.add(0, $ID.text);
        } else {
            $name_params = new LinkedList();
            $name_params.add($ID.text);
        }
    };
//function_call : ID LEFT_P args RIGHT_P;

args returns [LinkedList<Object> params]:
    a=expression {
        $params = new LinkedList();
        $params.add($a.expr);
    } (COMMA b=args {
        for (int k=0; k<$params.size(); k++) {
            $b.params.add(0, $params.get(k));
        }
        $params = $b.params;
    } )*;
//args :
//    atom (COMMA atom)*;

array returns [ObjectArray array]:
    LEFT_B a=args RIGHT_B {
        $array = new ObjectArray(this, $a.params);
    };
//array :
//  LEFT_B args RIGTH_B

array_element_reference returns [StorageAccessor accessor]
    : a=ID LEFT_B b=ID RIGHT_B {
        $accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, $a.text), new Variable(this, $b.text), null);
    }
    | a=ID LEFT_B b=NUM RIGHT_B {
        $accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, $a.text), new Numeric(Double.parseDouble($b.text)), null);
    }
    | a=ID LEFT_B s=string_literal RIGHT_B {
        $accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, $a.text), $s.value, null);
    };

atom returns [Object value]
    : NUM {
        $value = new Numeric( Double.parseDouble($NUM.text) );
    }
    | MINUS NUM {
        $value = new Numeric( -1.0 * Double.parseDouble($NUM.text) );
    }
    | BOOL {
        if ($BOOL.text.equalsIgnoreCase("true")) {
            $value = new Bool(true);
        } else if ($BOOL.text.equalsIgnoreCase("false")) {
            $value = new Bool(false);
        }
    }
    | LEFT_P expression RIGHT_P {
        $value = $expression.expr;
    }
    | ID {
        $value = new Variable(this, $ID.text);
    }
    | CONSTANT {
        $value = new Variable(this, $CONSTANT.text);
    }
    | string_literal {
        $value = $string_literal.value;
    }
    | dictionary {
        $value = $dictionary.value;
    }
    | array {
        $value = $array.array;
    }
    | function_call {
        $value = new Expression( this, new FunctionCall( this, $function_call.name_params ) );
    }
    | array_element_reference {
        $value = new Expression( this, $array_element_reference.accessor);
    };
//atom returns [Object value]
//    : NUM
//    | BOOL
//    | LEFT_P expression RIGHT_P
//    | ID
//    | string_literal
//    | dictionary;

string_literal returns [CharString value] : s=STRING_LITERAL {
        $value = new CharString( $s.text.replaceAll("^\"", "").replaceAll("\"$", "") );
    };
//string_literal: DQUOTE ID DQUOTE;

dictionary returns [Dictionary value] :
    LEFT_CB dictionary_elements RIGHT_CB {
        HashMap vs = new HashMap();
        int size = $dictionary_elements.keys_values.size();
        for (int k=0; k<size; k+=2) {
            vs.put($dictionary_elements.keys_values.get(k), $dictionary_elements.keys_values.get(k+1));
        }
        $value = new Dictionary(this, vs);
    };

dictionary_elements returns [LinkedList<Object> keys_values] :
    (e1=expression TP e2=expression) {
        $keys_values = new LinkedList();
        $keys_values.add($e1.expr);
        $keys_values.add($e2.expr);
    } (NEWLINE? COMMA NEWLINE? d=dictionary_elements {
            for (int k=0; k<$d.keys_values.size(); k++) {
                $keys_values.add($d.keys_values.get(k));
            }
        }
        )* 
    ;
CONSTANT: ('P' 'I') | ('e');
NUM :   '0'..'9'+ ('.' ('0'..'9'+)?)?;
BOOL: (('T'|'t') ('R'|'r') ('U'|'u') ('E'|'e')) | (('F'|'f') ('A'|'a') ('L'|'l') ('S'|'s') ('E'|'e'));
IF  : ('I'|'i') ('F'|'f');
ELSE: ('E'|'e') ('L'|'l') ('S'|'s') ('E'|'e');
WHILE: ('W'|'w') ('H'|'h') ('I'|'i') ('L'|'l') ('E'|'e');
FOR : ('F'|'f') ('O'|'o') ('R'|'r');
BREAK: ('B'|'b') ('R'|'r') ('E'|'e') ('A'|'a') ('K'|'k');
CONTINUE: ('C'|'c') ('O'|'o') ('N'|'n') ('T'|'t') ('I'|'i') ('N'|'n') ('U'|'u') ('E'|'e');
FUNCTION: ('F'|'f') ('U'|'u') ('N'|'n') ('C'|'c') ('T'|'t') ('I'|'i') ('O'|'o') ('N'|'n');
ID  :   ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
EQUAL: '=';
COMMA: ',';
DQUOTE: '"';
LEFT_P: '(';
RIGHT_P: ')';
MULT: '*';
DIV: '/';
PLUS: '+';
PLUS_PLUS: '+' '+';
MINUS: '-';
MINUS_MINUS: '-' '-';
CMP_LT: '<';
CMP_LT_EQ: '<' '=';
CMP_GT: '>';
CMP_GT_EQ: '>' '=';
CMP_EQ: '=' '=';
CMP_NEQ:'!' '=';
AND: '&';
OR : '|';
STRING_LITERAL: '"' (~'"')* '"';
LEFT_CB : '{'; // left curved bracket
RIGHT_CB : '}'; // right curved bracket
LEFT_B : '[';
RIGHT_B: ']';
ARROW: '-' '>';
NEWLINE:'\n';
LINE_COMMENT: '//' ~('\n')* {
    $channel = HIDDEN;
};
BLOCK_COMMENT: '/*' .* '*/' {
    $channel = HIDDEN;
};
TP: ':';
PV: ';';
WS :   (' '|'\t'|'\r')+ { 
    $channel = HIDDEN;
};
