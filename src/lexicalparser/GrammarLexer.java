// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g 2011-09-08 21:09:56

package lexicalparser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GrammarLexer extends Lexer {
    public static final int WS=12;
    public static final int T__15=15;
    public static final int NEWLINE=4;
    public static final int COMMA=8;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int EQUAL=11;
    public static final int RIGHT_P=7;
    public static final int DQUOTE=10;
    public static final int INT=9;
    public static final int ID=5;
    public static final int EOF=-1;
    public static final int LEFT_P=6;

    // delegates
    // delegators

    public GrammarLexer() {;} 
    public GrammarLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public GrammarLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g"; }

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:7:7: ( '+' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:7:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:8:7: ( '-' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:8:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:9:7: ( '*' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:9:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:134:5: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:134:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
            {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:134:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='A' && LA1_0<='Z')||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:
            	    {
            	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:135:5: ( ( '0' .. '9' )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:135:9: ( '0' .. '9' )+
            {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:135:9: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:135:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:136:6: ( '=' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:136:8: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:137:6: ( ',' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:137:8: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "DQUOTE"
    public final void mDQUOTE() throws RecognitionException {
        try {
            int _type = DQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:138:7: ( '\"' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:138:9: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DQUOTE"

    // $ANTLR start "LEFT_P"
    public final void mLEFT_P() throws RecognitionException {
        try {
            int _type = LEFT_P;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:139:7: ( '(' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:139:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFT_P"

    // $ANTLR start "RIGHT_P"
    public final void mRIGHT_P() throws RecognitionException {
        try {
            int _type = RIGHT_P;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:140:8: ( ')' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:140:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHT_P"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:141:8: ( ( '\\r' )? '\\n' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:141:9: ( '\\r' )? '\\n'
            {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:141:9: ( '\\r' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\r') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:141:9: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:142:5: ( ( ' ' | '\\t' )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:142:9: ( ' ' | '\\t' )+
            {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:142:9: ( ' ' | '\\t' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='\t'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:8: ( T__13 | T__14 | T__15 | ID | INT | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | NEWLINE | WS )
        int alt5=12;
        switch ( input.LA(1) ) {
        case '+':
            {
            alt5=1;
            }
            break;
        case '-':
            {
            alt5=2;
            }
            break;
        case '*':
            {
            alt5=3;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt5=4;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt5=5;
            }
            break;
        case '=':
            {
            alt5=6;
            }
            break;
        case ',':
            {
            alt5=7;
            }
            break;
        case '\"':
            {
            alt5=8;
            }
            break;
        case '(':
            {
            alt5=9;
            }
            break;
        case ')':
            {
            alt5=10;
            }
            break;
        case '\n':
        case '\r':
            {
            alt5=11;
            }
            break;
        case '\t':
        case ' ':
            {
            alt5=12;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 5, 0, input);

            throw nvae;
        }

        switch (alt5) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:10: T__13
                {
                mT__13(); 

                }
                break;
            case 2 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:16: T__14
                {
                mT__14(); 

                }
                break;
            case 3 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:22: T__15
                {
                mT__15(); 

                }
                break;
            case 4 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:28: ID
                {
                mID(); 

                }
                break;
            case 5 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:31: INT
                {
                mINT(); 

                }
                break;
            case 6 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:35: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 7 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:41: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 8 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:47: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 9 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:54: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 10 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:61: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 11 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:69: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 12 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:77: WS
                {
                mWS(); 

                }
                break;

        }

    }


 

}