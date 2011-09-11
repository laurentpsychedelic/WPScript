grammar Grammar;
options {output=AST;}

@lexer::header {
package lexicalparser;
}

@header {
package lexicalparser;
import java.util.LinkedList;
import java.util.HashMap;
}

@members {
    /** Map variable name to Integer object holding value */
    HashMap memory = new HashMap();

    public void dumpGlobalMemory() {
        System.out.println("\nGLOBAL MEMORY DUMP");
        for (Object o : memory.keySet()) {
            Object val = memory.get(o);
            System.out.println("VAR [" + o + "]->" + val);
        }
    }

    private void _WPAScriptPanic(String message) {
        System.out.println(message);
        System.exit(0);
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

prog returns [Object value]
    :  (e=stat)+ {
        $value = $e.value;
    };
                
stat   returns [Object value]
    :   expr NEWLINE {
            $value = $expr.value;
        }
    |   ID '=' expr NEWLINE
        {
            memory.put($ID.text, $expr.value);
            $value = $expr.value;
        }
    |   NEWLINE
    ;

expr returns [Object value]
    :   e=multExpr {$value = $e.value;}
        (   '+' e=multExpr {
            if ($value instanceof Numeric && $e.value instanceof Numeric) {
                $value = new Numeric( ((Numeric) ($value)).value + ((Numeric) ($e.value)).value );
            } else {
                System.err.println("[+] is defined only between numeric types");
            }
        }
     |   '-' e=multExpr {
            if ($value instanceof Numeric && $e.value instanceof Numeric) {
                $value = new Numeric( ((Numeric) $value).value - ((Numeric) $e.value).value );
            } else {
                System.err.println("[-] is defined only between numeric types");
            }
        }
        )*
     | func_call
        {
            $value = $func_call.result;
        }
    ;

multExpr returns [Object value]
    :   e=atom
            {
                $value = $e.value;       
            }
        (   '*' e=atom {
                if ($value instanceof Numeric && $e.value instanceof Numeric) {
                    $value = new Numeric( ((Numeric) $value).value * ((Numeric) $e.value).value );
                } else {
                    System.err.println("[*] is defined only between numeric types");
                }
            }
        |   '/' e=atom {
                if ($value instanceof Numeric && $e.value instanceof Numeric) {
                    $value = new Numeric( ((Numeric) $value).value / ((Numeric) $e.value).value );
                } else {
                    System.err.println("[/] is defined only between numeric types");
                }
            }
       )*
    ; 


func_call returns [Object result]: simple_func {
        $result = FunctionCall.callFunction($simple_func.name_params);
    };

simple_func returns [LinkedList<Object> name_params]: ID LEFT_P args RIGHT_P
    {  
        $name_params = $args.params;
        $name_params.add(0, $ID.text);
    };

args returns [LinkedList<Object> params]:
    a=atom {$params = new LinkedList(); $params.add($a.value);} (COMMA b=args 
           {
            for (int k=0; k<$params.size(); k++) {
                $b.params.add(0, $params.get(k));
            }
            $params = $b.params;
           })*;


atom returns [Object value]
    :   NUM {$value = new Numeric(Double.parseDouble($NUM.text));}
    |   BOOL {
            if ($BOOL.text.equalsIgnoreCase("TRUE")) {
                $value = new Boolean(true);
            } else if ($BOOL.text.equalsIgnoreCase("FALSE")) {
                $value = new Boolean(false);
            } else {
                _WPAScriptPanic("BOOL value: " + $BOOL.text + " invalid! BOOL type value must be TRUE or FALSE");
            }
        }
    |   ID
        {
            Object v = (Object)memory.get($ID.text);
            if ( v!=null ) {
                $value = v;
            } else {                
                System.err.println("undefined variable "+$ID.text);
            }
        }
    |   '(' expr ')' {$value = $expr.value;}
    | string_literal {$value = $string_literal.value;}
    ;

string_literal returns [String value]:
    DQUOTE ID DQUOTE {
        $value = $ID.text;
    }
    ;

NUM :   '0'..'9'+ ('.' '0'..'9'+)?;
BOOL: (('T'|'t') ('R'|'r') ('U'|'u') ('E'|'e')) | (('F'|'f') ('A'|'a') ('L'|'l') ('S'|'s') ('E'|'e'));
IF  : ('I'|'i') ('F'|'f');
ID  :   ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
EQUAL: '=';
COMMA: ',';
DQUOTE: '"';
LEFT_P: '(';
RIGHT_P: ')';
LEFT_CB : '{'; // left curved bracket
RIGHT_CB : '}'; // right curved bracket
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;