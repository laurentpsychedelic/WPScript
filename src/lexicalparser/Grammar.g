grammar Grammar;

options {
    output = AST;
    backtrack = true;
    memoize = true;
}

@lexer::header {
package lexicalparser;
}

@header {
package lexicalparser;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.HashMap;
}

@lexer::members {
    public boolean skip_ws = true;
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
    protected HashMap memory = new HashMap();
    protected HashMap compilation_memory =  new HashMap();
    private LinkedList <Expression> commands = new LinkedList();
    protected int line_number = 1;

    private boolean _PANIC_STATE_ = false;
    private boolean _COMPILATION_ERROR_STATE_ = false;
    private boolean _RUNTIME_ERROR_STATE_ = false;

    public void dumpGlobalMemory(PrintStream ps) {
        ps.println("\nGLOBAL MEMORY DUMP");
        for (Object o : memory.keySet()) {
            Object val = memory.get(o);
            ps.println("VAR [" + o + "]->" + val);
        }
    }

    public void dumpScriptCommands() {
        System.out.println("\nSCRIPT COMMANDS DUMP");
        for (Object o : commands) {
            System.out.println("COMMAND->" + o);
        }
    }

    public boolean compilationCheck() {
        try {
            compilation_memory.clear();
            System.out.println("\nCOMPILATION CHECK");
            for (Object o : commands) {
                if (!(o instanceof Expression)) {
                    _WPAScriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                }
                ((Expression) o).compilationCheck();
            }
            compilation_memory.clear();
            System.out.println("\nCOMPILATION OK");
            return true;
        } catch (CompilationErrorException e) {
            _WPAScriptCompilationError(e.getMessage(), e.getLineNumber());
            return false;
        }
    }

    public Object execute() {
        Object ret_val = null;
        for (Object c : commands) {
            if (!(c instanceof Expression)) {
                _WPAScriptPanic("Top level command must be instances of Expression!", 0);
            }
            ret_val = ((Expression) c).eval();
        }
        return ret_val;
    }

    protected void _WPAScriptPanic(String message, int line_num) {

        if (!_PANIC_STATE_) {
            _PANIC_STATE_ = true;
            System.err.println("PANIC OCCURED!");
        }
        System.err.println("ERROR (l" + line_num + "):: " + message);

        dumpGlobalMemory(System.err);
    }


    protected void _WPAScriptCompilationError(String message, int line_num) {
        if (!_COMPILATION_ERROR_STATE_) {
            _COMPILATION_ERROR_STATE_ = true;
            System.err.println("COMPILATION");
        }
        System.err.println("ERROR (l" + line_num + "):: " + message);
    }


    protected void _WPAScriptCompilationWarning(String message, int line_num) {
        if (!_COMPILATION_ERROR_STATE_) {
            _COMPILATION_ERROR_STATE_ = true;
            System.err.println("COMPILATION");
        }
        System.err.println("WARNING (l" + line_num + "):: " + message);
    }

    protected void _WPAScriptRuntimeError(String message, int line_num) {
        if (!_RUNTIME_ERROR_STATE_) {
            _RUNTIME_ERROR_STATE_ = true;
            System.err.println("RUNTIME");
        }
        System.err.println("ERROR (l" + line_num + "):: " + message);
        
        dumpGlobalMemory(System.err);
    }

    public static void main(String[] args) throws Exception {

        GrammarLexer lex = new GrammarLexer(new ANTLRStringStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        GrammarParser parser = new GrammarParser(tokens);

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
    : expression NEWLINE {
        $expr = $expression.expr;
        line_number++;
    }
    | ID EQUAL expression NEWLINE {
        $expr = new Expression(this,  new VariableAssignment(this, $ID.text, $expression.expr) );
        line_number++;
    }
    | NEWLINE {
        line_number++;
    }
    | block {
        $expr = new Expression(this, $block.expressions);
    }
    | if_expression {
        $expr = new Expression(this, $if_expression.expr);
    };
    

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

expression returns [Expression expr]
    : terms {
        $expr = new Expression( this, new Term(this, $terms.terms) );
    }
    | function_call {
        $expr = new Expression( this, new FunctionCall( this, $function_call.name_params ) );
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
    )*;
//term : atom ( MULT atom | DIV atom )*;

function_call returns [LinkedList<Object> name_params]:
    ID LEFT_P args RIGHT_P {  
        $name_params = $args.params;
        $name_params.add(0, $ID.text);
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

atom returns [Object value]
    : NUM {
        $value = new Numeric( Float.parseFloat($NUM.text) );
    }
    | BOOL {
        if ($BOOL.text.equalsIgnoreCase("true")) {
            $value = new Bool(true);
        } else if ($BOOL.text.equalsIgnoreCase("false")) {
            $value = new Bool(false);
        } else {
            _WPAScriptPanic("Token [" + $BOOL.text + "] must be equal to \"true\" or \"false\" (boolean type)", line_number);
        }
    }
    | LEFT_P expression RIGHT_P {
        $value = $expression.expr;
    }
    | ID {
        $value = new Variable(this, $ID.text);
    }
    | string_literal {
        $value = $string_literal.value;
    }
    | dictionary {
        $value = $dictionary.value;
    }
    ;
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
    LEFT_B dictionary_elements RIGHT_B {
        HashMap vs = new HashMap();
        int size = $dictionary_elements.keys_values.size();
        if (size==0 || size%2!=0) {
            _WPAScriptPanic("Dictionary must have a even number of elements!", line_number);
        }
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
    } (e1=expression TP e2=expression)* {
        $keys_values.add($e1.expr);
        $keys_values.add($e2.expr);
    };

NUM :   '0'..'9'+ ('.' '0'..'9'+)?;
BOOL: (('T'|'t') ('R'|'r') ('U'|'u') ('E'|'e')) | (('F'|'f') ('A'|'a') ('L'|'l') ('S'|'s') ('E'|'e'));
IF  : ('I'|'i') ('F'|'f');
ELSE: ('E'|'e') ('L'|'l') ('S'|'s') ('E'|'e');
ID  :   ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
EQUAL: '=';
COMMA: ',';
DQUOTE: '"';
LEFT_P: '(';
RIGHT_P: ')';
MULT: '*';
DIV: '/';
PLUS: '+';
STRING_LITERAL: '"' ID '"';
MINUS: '-';
LEFT_CB : '{'; // left curved bracket
RIGHT_CB : '}'; // right curved bracket
LEFT_B : '[';
RIGHT_B: ']';
NEWLINE:'\n';
TP: ':';
WS :   (' '|'\t'|'\r')+ { 
if (skip_ws) {
    skip();
}  
};
