// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g 2011-09-07 18:12:33

package lexicalparser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GrammarLexer extends Lexer {
    public static final int WS=11;
    public static final int NEWLINE=4;
    public static final int T__12=12;
    public static final int COMMA=8;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int EQUAL=10;
    public static final int RIGHT_P=7;
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
    public String getGrammarFileName() { return "D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g"; }

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:7:7: ( '+' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:7:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:8:7: ( '-' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:8:9: '-'
            {
            match('-'); 

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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:9:7: ( '*' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:9:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:92:5: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:92:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
            {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:92:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
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
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:93:5: ( ( '0' .. '9' )+ )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:93:9: ( '0' .. '9' )+
            {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:93:9: ( '0' .. '9' )+
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
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:93:9: '0' .. '9'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:94:6: ( '=' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:94:8: '='
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:95:6: ( ',' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:95:8: ','
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

    // $ANTLR start "LEFT_P"
    public final void mLEFT_P() throws RecognitionException {
        try {
            int _type = LEFT_P;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:96:7: ( '(' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:96:9: '('
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:97:8: ( ')' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:97:10: ')'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:98:8: ( ( '\\r' )? '\\n' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:98:9: ( '\\r' )? '\\n'
            {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:98:9: ( '\\r' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\r') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:98:9: '\\r'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:99:5: ( ( ' ' | '\\t' )+ )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:99:9: ( ' ' | '\\t' )+
            {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:99:9: ( ' ' | '\\t' )+
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
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:
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
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:8: ( T__12 | T__13 | T__14 | ID | INT | EQUAL | COMMA | LEFT_P | RIGHT_P | NEWLINE | WS )
        int alt5=11;
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
        case '(':
            {
            alt5=8;
            }
            break;
        case ')':
            {
            alt5=9;
            }
            break;
        case '\n':
        case '\r':
            {
            alt5=10;
            }
            break;
        case '\t':
        case ' ':
            {
            alt5=11;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 5, 0, input);

            throw nvae;
        }

        switch (alt5) {
            case 1 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:10: T__12
                {
                mT__12(); 

                }
                break;
            case 2 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:16: T__13
                {
                mT__13(); 

                }
                break;
            case 3 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:22: T__14
                {
                mT__14(); 

                }
                break;
            case 4 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:28: ID
                {
                mID(); 

                }
                break;
            case 5 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:31: INT
                {
                mINT(); 

                }
                break;
            case 6 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:35: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 7 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:41: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 8 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:47: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 9 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:54: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 10 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:62: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 11 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:70: WS
                {
                mWS(); 

                }
                break;

        }

    }


 

}