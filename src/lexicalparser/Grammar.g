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

prog:   stat+ ;
                
stat:   expr NEWLINE {System.out.println($expr.value);}
    |   ID '=' expr NEWLINE
        {memory.put($ID.text, $expr.value);}
    |   NEWLINE
    ;

expr returns [Object value]
    :   e=multExpr {$value = $e.value;}
        (   '+' e=multExpr {
            if ($value instanceof Double && $e.value instanceof Double) {
                $value = (Double) $value + (Double) $e.value;
            } else {
                System.err.println("[+] is defined only between numbers");
            }
        }
        |   '-' e=multExpr {
            if ($value instanceof Double && $e.value instanceof Double) {
                $value = (Double) $value - (Double) $e.value;
            } else {
                System.err.println("[-] is defined only between numbers");
            }
        }
        )*
        | func_call
        {
            $value = $func_call.result;
            //System.out.println($func_call.result);
        }
    ;

multExpr returns [Object value]
    :   e=atom {$value = $e.value;} ('*' e=atom {
            if ($value instanceof Double && $e.value instanceof Double) {
                $value = (Double) $value * (Double) $e.value;
            } else {
                System.err.println("[*] is defined only between numbers");
            }
    }   )*
    ; 

func_call returns [Object result]: simple_func {
        System.out.print("Function call: " + $simple_func.name_params.get(0) + "(");
        for (int k=1; k<$simple_func.name_params.size(); k++) {
            System.out.print($simple_func.name_params.get(k));
            if (k<$simple_func.name_params.size()-1) {
                System.out.print(",");
            } else {
                System.out.println(")");
            }
        }
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
    :   INT {$value = new Double(Double.parseDouble($INT.text));}
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

/*
variable returns [String value]
    : 

param_list : atom COMMA param_list { $value = $atom.value + ", " + $param_list.value }
    ;
*/

//function_call : ID LEFT_P param_list RIGHT_P NEWLINE 
//    ;

ID  :   ('a'..'z'|'A'..'Z')+ ;
INT :   '0'..'9'+ ;
EQUAL: '=';
COMMA: ',';
DQUOTE: '"';
LEFT_P: '(';
RIGHT_P: ')';
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;