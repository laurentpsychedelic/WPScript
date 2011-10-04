// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g 2011-10-04 17:20:13

package language;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ScriptLexer extends Lexer {
    public static final int CMP_NEQ=32;
    public static final int WHILE=17;
    public static final int ELSE=16;
    public static final int TP=37;
    public static final int BOOL=35;
    public static final int RIGHT_P=15;
    public static final int DQUOTE=38;
    public static final int FOR=18;
    public static final int CMP_EQ=31;
    public static final int MULT=25;
    public static final int MINUS=22;
    public static final int AND=23;
    public static final int ID=9;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int CMP_GT=29;
    public static final int BREAK=7;
    public static final int NUM=34;
    public static final int IF=13;
    public static final int RIGHT_CB=6;
    public static final int CMP_GT_EQ=30;
    public static final int CMP_LT=27;
    public static final int STRING_LITERAL=36;
    public static final int RIGHT_B=40;
    public static final int WS=41;
    public static final int PLUS_PLUS=11;
    public static final int NEWLINE=5;
    public static final int CONTINUE=8;
    public static final int COMMA=33;
    public static final int CMP_LT_EQ=28;
    public static final int EQUAL=10;
    public static final int OR=24;
    public static final int LEFT_B=39;
    public static final int PV=19;
    public static final int ARROW=20;
    public static final int PLUS=21;
    public static final int DIV=26;
    public static final int LEFT_P=14;
    public static final int MINUS_MINUS=12;

        public boolean skip_ws = true;


    // delegates
    // delegators

    public ScriptLexer() {;} 
    public ScriptLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ScriptLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g"; }

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:5: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:9: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
            {
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:9: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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

            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:19: ( '.' ( '0' .. '9' )+ )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='.') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:20: '.' ( '0' .. '9' )+
                    {
                    match('.'); 
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:24: ( '0' .. '9' )+
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:561:24: '0' .. '9'
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
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM"

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            int _type = BOOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:562:5: ( ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) ) | ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='T'||LA4_0=='t') ) {
                alt4=1;
            }
            else if ( (LA4_0=='F'||LA4_0=='f') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:562:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    {
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:562:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:562:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
                    {
                    if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }


                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:562:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    {
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:562:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:562:52: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
                    {
                    if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOL"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:563:5: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:563:7: ( 'I' | 'i' ) ( 'F' | 'f' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:564:5: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:564:7: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:565:6: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:565:8: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:566:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:566:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "BREAK"
    public final void mBREAK() throws RecognitionException {
        try {
            int _type = BREAK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:567:6: ( ( 'B' | 'b' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'K' | 'k' ) )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:567:8: ( 'B' | 'b' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'K' | 'k' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BREAK"

    // $ANTLR start "CONTINUE"
    public final void mCONTINUE() throws RecognitionException {
        try {
            int _type = CONTINUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:568:9: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:568:11: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTINUE"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:569:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:569:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:569:33: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:570:6: ( '=' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:570:8: '='
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:571:6: ( ',' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:571:8: ','
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:572:7: ( '\"' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:572:9: '\"'
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:573:7: ( '(' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:573:9: '('
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:574:8: ( ')' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:574:10: ')'
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

    // $ANTLR start "MULT"
    public final void mMULT() throws RecognitionException {
        try {
            int _type = MULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:575:5: ( '*' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:575:7: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MULT"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:576:4: ( '/' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:576:6: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:577:5: ( '+' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:577:7: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "PLUS_PLUS"
    public final void mPLUS_PLUS() throws RecognitionException {
        try {
            int _type = PLUS_PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:578:10: ( '+' '+' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:578:12: '+' '+'
            {
            match('+'); 
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS_PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:579:6: ( '-' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:579:8: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MINUS_MINUS"
    public final void mMINUS_MINUS() throws RecognitionException {
        try {
            int _type = MINUS_MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:580:12: ( '-' '-' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:580:14: '-' '-'
            {
            match('-'); 
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS_MINUS"

    // $ANTLR start "CMP_LT"
    public final void mCMP_LT() throws RecognitionException {
        try {
            int _type = CMP_LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:581:7: ( '<' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:581:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CMP_LT"

    // $ANTLR start "CMP_LT_EQ"
    public final void mCMP_LT_EQ() throws RecognitionException {
        try {
            int _type = CMP_LT_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:582:10: ( '<' '=' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:582:12: '<' '='
            {
            match('<'); 
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CMP_LT_EQ"

    // $ANTLR start "CMP_GT"
    public final void mCMP_GT() throws RecognitionException {
        try {
            int _type = CMP_GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:583:7: ( '>' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:583:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CMP_GT"

    // $ANTLR start "CMP_GT_EQ"
    public final void mCMP_GT_EQ() throws RecognitionException {
        try {
            int _type = CMP_GT_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:584:10: ( '>' '=' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:584:12: '>' '='
            {
            match('>'); 
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CMP_GT_EQ"

    // $ANTLR start "CMP_EQ"
    public final void mCMP_EQ() throws RecognitionException {
        try {
            int _type = CMP_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:585:7: ( '=' '=' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:585:9: '=' '='
            {
            match('='); 
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CMP_EQ"

    // $ANTLR start "CMP_NEQ"
    public final void mCMP_NEQ() throws RecognitionException {
        try {
            int _type = CMP_NEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:586:8: ( '!' '=' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:586:9: '!' '='
            {
            match('!'); 
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CMP_NEQ"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:587:4: ( '&' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:587:6: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:588:4: ( '|' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:588:6: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:15: ( '\"' ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' ) ( ' ' )? )+ '\"' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:17: '\"' ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' ) ( ' ' )? )+ '\"'
            {
            match('\"'); 
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:21: ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' ) ( ' ' )? )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==' '||LA8_0=='#'||(LA8_0>='0' && LA8_0<='9')||LA8_0=='='||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='_'||(LA8_0>='a' && LA8_0<='{')||LA8_0=='}') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:22: ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' ) ( ' ' )?
            	    {
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:22: ( ( ' ' )? )
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:23: ( ' ' )?
            	    {
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:23: ( ' ' )?
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0==' ') ) {
            	        alt6=1;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:23: ' '
            	            {
            	            match(' '); 

            	            }
            	            break;

            	    }


            	    }

            	    if ( input.LA(1)=='#'||(input.LA(1)>='0' && input.LA(1)<='9')||input.LA(1)=='='||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='{')||input.LA(1)=='}' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}

            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:78: ( ' ' )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==' ') ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:589:79: ' '
            	            {
            	            match(' '); 

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "LEFT_CB"
    public final void mLEFT_CB() throws RecognitionException {
        try {
            int _type = LEFT_CB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:590:9: ( '{' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:590:11: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFT_CB"

    // $ANTLR start "RIGHT_CB"
    public final void mRIGHT_CB() throws RecognitionException {
        try {
            int _type = RIGHT_CB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:591:10: ( '}' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:591:12: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHT_CB"

    // $ANTLR start "LEFT_B"
    public final void mLEFT_B() throws RecognitionException {
        try {
            int _type = LEFT_B;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:592:8: ( '[' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:592:10: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFT_B"

    // $ANTLR start "RIGHT_B"
    public final void mRIGHT_B() throws RecognitionException {
        try {
            int _type = RIGHT_B;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:593:8: ( ']' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:593:10: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHT_B"

    // $ANTLR start "ARROW"
    public final void mARROW() throws RecognitionException {
        try {
            int _type = ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:594:6: ( '-' '>' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:594:8: '-' '>'
            {
            match('-'); 
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARROW"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:595:8: ( '\\n' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:595:9: '\\n'
            {
            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "TP"
    public final void mTP() throws RecognitionException {
        try {
            int _type = TP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:596:3: ( ':' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:596:5: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TP"

    // $ANTLR start "PV"
    public final void mPV() throws RecognitionException {
        try {
            int _type = PV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:597:3: ( ';' )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:597:5: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PV"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:598:4: ( ( ' ' | '\\t' | '\\r' )+ )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:598:8: ( ' ' | '\\t' | '\\r' )+
            {
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:598:8: ( ' ' | '\\t' | '\\r' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='\t'||LA9_0=='\r'||LA9_0==' ') ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);

             
            if (skip_ws) {
                skip();
            }  


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:8: ( NUM | BOOL | IF | ELSE | WHILE | FOR | BREAK | CONTINUE | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | TP | PV | WS )
        int alt10=38;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:10: NUM
                {
                mNUM(); 

                }
                break;
            case 2 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:14: BOOL
                {
                mBOOL(); 

                }
                break;
            case 3 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:19: IF
                {
                mIF(); 

                }
                break;
            case 4 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:22: ELSE
                {
                mELSE(); 

                }
                break;
            case 5 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:27: WHILE
                {
                mWHILE(); 

                }
                break;
            case 6 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:33: FOR
                {
                mFOR(); 

                }
                break;
            case 7 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:37: BREAK
                {
                mBREAK(); 

                }
                break;
            case 8 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:43: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 9 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:52: ID
                {
                mID(); 

                }
                break;
            case 10 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:55: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 11 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:61: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 12 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:67: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 13 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:74: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 14 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:81: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 15 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:89: MULT
                {
                mMULT(); 

                }
                break;
            case 16 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:94: DIV
                {
                mDIV(); 

                }
                break;
            case 17 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:98: PLUS
                {
                mPLUS(); 

                }
                break;
            case 18 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:103: PLUS_PLUS
                {
                mPLUS_PLUS(); 

                }
                break;
            case 19 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:113: MINUS
                {
                mMINUS(); 

                }
                break;
            case 20 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:119: MINUS_MINUS
                {
                mMINUS_MINUS(); 

                }
                break;
            case 21 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:131: CMP_LT
                {
                mCMP_LT(); 

                }
                break;
            case 22 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:138: CMP_LT_EQ
                {
                mCMP_LT_EQ(); 

                }
                break;
            case 23 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:148: CMP_GT
                {
                mCMP_GT(); 

                }
                break;
            case 24 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:155: CMP_GT_EQ
                {
                mCMP_GT_EQ(); 

                }
                break;
            case 25 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:165: CMP_EQ
                {
                mCMP_EQ(); 

                }
                break;
            case 26 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:172: CMP_NEQ
                {
                mCMP_NEQ(); 

                }
                break;
            case 27 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:180: AND
                {
                mAND(); 

                }
                break;
            case 28 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:184: OR
                {
                mOR(); 

                }
                break;
            case 29 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:187: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 30 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:202: LEFT_CB
                {
                mLEFT_CB(); 

                }
                break;
            case 31 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:210: RIGHT_CB
                {
                mRIGHT_CB(); 

                }
                break;
            case 32 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:219: LEFT_B
                {
                mLEFT_B(); 

                }
                break;
            case 33 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:226: RIGHT_B
                {
                mRIGHT_B(); 

                }
                break;
            case 34 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:234: ARROW
                {
                mARROW(); 

                }
                break;
            case 35 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:240: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 36 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:248: TP
                {
                mTP(); 

                }
                break;
            case 37 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:251: PV
                {
                mPV(); 

                }
                break;
            case 38 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:1:254: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\2\uffff\7\11\1\uffff\1\51\1\uffff\1\53\4\uffff\1\55\1\60\1\62"+
        "\1\64\13\uffff\3\11\1\70\4\11\15\uffff\2\11\1\77\1\uffff\4\11\1"+
        "\104\1\11\1\uffff\1\106\3\11\1\uffff\1\104\1\uffff\1\112\1\113\1"+
        "\11\2\uffff\2\11\1\117\1\uffff";
    static final String DFA10_eofS =
        "\120\uffff";
    static final String DFA10_minS =
        "\1\11\1\uffff\1\122\1\101\1\106\1\114\1\110\1\122\1\117\1\uffff"+
        "\1\75\1\uffff\1\40\4\uffff\1\53\1\55\2\75\13\uffff\1\125\1\114\1"+
        "\122\1\60\1\123\1\111\1\105\1\116\15\uffff\1\105\1\123\1\60\1\uffff"+
        "\1\105\1\114\1\101\1\124\1\60\1\105\1\uffff\1\60\1\105\1\113\1\111"+
        "\1\uffff\1\60\1\uffff\2\60\1\116\2\uffff\1\125\1\105\1\60\1\uffff";
    static final String DFA10_maxS =
        "\1\175\1\uffff\1\162\1\157\1\146\1\154\1\150\1\162\1\157\1\uffff"+
        "\1\75\1\uffff\1\175\4\uffff\1\53\1\76\2\75\13\uffff\1\165\1\154"+
        "\1\162\1\172\1\163\1\151\1\145\1\156\15\uffff\1\145\1\163\1\172"+
        "\1\uffff\1\145\1\154\1\141\1\164\1\172\1\145\1\uffff\1\172\1\145"+
        "\1\153\1\151\1\uffff\1\172\1\uffff\2\172\1\156\2\uffff\1\165\1\145"+
        "\1\172\1\uffff";
    static final String DFA10_acceptS =
        "\1\uffff\1\1\7\uffff\1\11\1\uffff\1\13\1\uffff\1\15\1\16\1\17\1"+
        "\20\4\uffff\1\32\1\33\1\34\1\36\1\37\1\40\1\41\1\43\1\44\1\45\1"+
        "\46\10\uffff\1\31\1\12\1\35\1\14\1\22\1\21\1\24\1\42\1\23\1\26\1"+
        "\25\1\30\1\27\3\uffff\1\3\6\uffff\1\6\4\uffff\1\2\1\uffff\1\4\3"+
        "\uffff\1\5\1\7\3\uffff\1\10";
    static final String DFA10_specialS =
        "\120\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\37\1\34\2\uffff\1\37\22\uffff\1\37\1\25\1\14\3\uffff\1\26"+
            "\1\uffff\1\15\1\16\1\17\1\21\1\13\1\22\1\uffff\1\20\12\1\1\35"+
            "\1\36\1\23\1\12\1\24\2\uffff\1\11\1\7\1\10\1\11\1\5\1\3\2\11"+
            "\1\4\12\11\1\2\2\11\1\6\3\11\1\32\1\uffff\1\33\1\uffff\1\11"+
            "\1\uffff\1\11\1\7\1\10\1\11\1\5\1\3\2\11\1\4\12\11\1\2\2\11"+
            "\1\6\3\11\1\30\1\27\1\31",
            "",
            "\1\40\37\uffff\1\40",
            "\1\41\15\uffff\1\42\21\uffff\1\41\15\uffff\1\42",
            "\1\43\37\uffff\1\43",
            "\1\44\37\uffff\1\44",
            "\1\45\37\uffff\1\45",
            "\1\46\37\uffff\1\46",
            "\1\47\37\uffff\1\47",
            "",
            "\1\50",
            "",
            "\1\52\2\uffff\1\52\14\uffff\12\52\3\uffff\1\52\3\uffff\32"+
            "\52\4\uffff\1\52\1\uffff\33\52\1\uffff\1\52",
            "",
            "",
            "",
            "",
            "\1\54",
            "\1\56\20\uffff\1\57",
            "\1\61",
            "\1\63",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\65\37\uffff\1\65",
            "\1\66\37\uffff\1\66",
            "\1\67\37\uffff\1\67",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\71\37\uffff\1\71",
            "\1\72\37\uffff\1\72",
            "\1\73\37\uffff\1\73",
            "\1\74\37\uffff\1\74",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\75\37\uffff\1\75",
            "\1\76\37\uffff\1\76",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "",
            "\1\100\37\uffff\1\100",
            "\1\101\37\uffff\1\101",
            "\1\102\37\uffff\1\102",
            "\1\103\37\uffff\1\103",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\105\37\uffff\1\105",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\107\37\uffff\1\107",
            "\1\110\37\uffff\1\110",
            "\1\111\37\uffff\1\111",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\114\37\uffff\1\114",
            "",
            "",
            "\1\115\37\uffff\1\115",
            "\1\116\37\uffff\1\116",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( NUM | BOOL | IF | ELSE | WHILE | FOR | BREAK | CONTINUE | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | TP | PV | WS );";
        }
    }
 

}