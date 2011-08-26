grammar Grammar;

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

atom returns [int value]
    :   INT {$value = Integer.parseInt($INT.text);}
    |   ID
        {
        Integer v = (Integer)memory.get($ID.text);
        if ( v!=null ) $value = v.intValue();
        else System.err.println("undefined variable "+$ID.text);
        }
    |   '(' expr ')' {$value = $expr.value;}
    ;

ID  :   ('a'..'z'|'A'..'Z')+ ;
INT :   '0'..'9'+ ;
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;