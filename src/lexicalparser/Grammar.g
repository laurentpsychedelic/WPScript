grammar Grammar;
options {output=AST;}

@lexer::header {
package lexicalparser;
}

@header {
package lexicalparser;

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
        {memory.put($ID.text, new Integer($expr.value));}
    |   simple_func NEWLINE 
    {
        String func_name = $simple_func.func_name.split("[()]")[0];
        String [] args = $simple_func.func_name.split("[()]")[1].split(",");
        System.out.println("TRY CALLING [" + func_name + "]...");
        FunctionCall.callFunction(func_name, args);
    }
    |   NEWLINE
    ;

expr returns [int value]
    :   e=multExpr {$value = $e.value;}
        (   '+' e=multExpr {$value += $e.value;}
        |   '-' e=multExpr {$value -= $e.value;}
        )*
    ;

multExpr returns [int value]
    :   e=atom {$value = $e.value;} ('*' e=atom {$value *= $e.value;})*
    ; 

simple_func returns [String func_name]: ID LEFT_P args RIGHT_P
    {  
        $func_name = $ID.text + "(" + $args.params_list + ")";
    };

args returns [String params_list]:
    a=ID {$params_list = $a.text;} (COMMA b=args {$params_list += ", " + $b.params_list;})*;

atom returns [int value]
    :   INT {$value = Integer.parseInt($INT.text);}
    |   ID
        {
            Integer v = (Integer)memory.get($ID.text);
            if ( v!=null ) {
                $value = v.intValue();
            } else {                
                System.err.println("undefined variable "+$ID.text);
            }
        }
    |   '(' expr ')' {$value = $expr.value;}
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
LEFT_P: '(';
RIGHT_P: ')';
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;