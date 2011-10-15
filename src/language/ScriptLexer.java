// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g 2011-10-15 16:08:01

package language;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ScriptLexer extends Lexer {
    public static final int WHILE=17;
    public static final int FOR=18;
    public static final int DQUOTE=38;
    public static final int CMP_EQ=31;
    public static final int AND=23;
    public static final int ID=9;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int BREAK=7;
    public static final int RIGHT_CB=6;
    public static final int IF=13;
    public static final int CMP_GT_EQ=30;
    public static final int CMP_LT=27;
    public static final int RIGHT_B=40;
    public static final int STRING_LITERAL=36;
    public static final int PLUS_PLUS=11;
    public static final int CONTINUE=8;
    public static final int COMMA=33;
    public static final int CMP_LT_EQ=28;
    public static final int EQUAL=10;
    public static final int LEFT_B=39;
    public static final int BLOCK_COMMENT=42;
    public static final int PV=19;
    public static final int PLUS=21;
    public static final int LEFT_P=14;
    public static final int CMP_NEQ=32;
    public static final int LINE_COMMENT=41;
    public static final int ELSE=16;
    public static final int BOOL=35;
    public static final int TP=37;
    public static final int RIGHT_P=15;
    public static final int MINUS=22;
    public static final int MULT=25;
    public static final int CMP_GT=29;
    public static final int NUM=34;
    public static final int WS=43;
    public static final int NEWLINE=5;
    public static final int OR=24;
    public static final int ARROW=20;
    public static final int DIV=26;
    public static final int MINUS_MINUS=12;

    // delegates
    // delegators

    public ScriptLexer() {;} 
    public ScriptLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ScriptLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g"; }

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:5: ( ( '0' .. '9' )+ ( '.' ( ( '0' .. '9' )+ )? )? )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:9: ( '0' .. '9' )+ ( '.' ( ( '0' .. '9' )+ )? )?
            {
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:9: ( '0' .. '9' )+
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
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:9: '0' .. '9'
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

            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:19: ( '.' ( ( '0' .. '9' )+ )? )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='.') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:20: '.' ( ( '0' .. '9' )+ )?
                    {
                    match('.'); 
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:24: ( ( '0' .. '9' )+ )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:25: ( '0' .. '9' )+
                            {
                            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:25: ( '0' .. '9' )+
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
                            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:556:25: '0' .. '9'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:557:5: ( ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) ) | ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='T'||LA5_0=='t') ) {
                alt5=1;
            }
            else if ( (LA5_0=='F'||LA5_0=='f') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:557:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    {
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:557:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:557:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:557:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    {
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:557:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:557:52: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:558:5: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:558:7: ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:559:5: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:559:7: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:560:6: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:560:8: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:561:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:561:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:562:6: ( ( 'B' | 'b' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'K' | 'k' ) )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:562:8: ( 'B' | 'b' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'K' | 'k' )
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:563:9: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:563:11: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:564:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:564:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:564:33: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:
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
            	    break loop6;
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:565:6: ( '=' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:565:8: '='
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:566:6: ( ',' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:566:8: ','
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:567:7: ( '\"' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:567:9: '\"'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:568:7: ( '(' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:568:9: '('
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:569:8: ( ')' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:569:10: ')'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:570:5: ( '*' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:570:7: '*'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:571:4: ( '/' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:571:6: '/'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:572:5: ( '+' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:572:7: '+'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:573:10: ( '+' '+' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:573:12: '+' '+'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:574:6: ( '-' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:574:8: '-'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:575:12: ( '-' '-' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:575:14: '-' '-'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:576:7: ( '<' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:576:9: '<'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:577:10: ( '<' '=' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:577:12: '<' '='
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:578:7: ( '>' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:578:9: '>'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:579:10: ( '>' '=' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:579:12: '>' '='
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:580:7: ( '=' '=' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:580:9: '=' '='
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:581:8: ( '!' '=' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:581:9: '!' '='
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:582:4: ( '&' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:582:6: '&'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:583:4: ( '|' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:583:6: '|'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:15: ( '\"' ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )? )+ '\"' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:17: '\"' ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )? )+ '\"'
            {
            match('\"'); 
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:21: ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )? )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==' '||LA9_0=='#'||(LA9_0>='.' && LA9_0<=':')||LA9_0=='='||(LA9_0>='A' && LA9_0<='Z')||LA9_0=='_'||(LA9_0>='a' && LA9_0<='{')||LA9_0=='}') ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:22: ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )?
            	    {
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:22: ( ( ' ' )? )
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:23: ( ' ' )?
            	    {
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:23: ( ' ' )?
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==' ') ) {
            	        alt7=1;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:23: ' '
            	            {
            	            match(' '); 

            	            }
            	            break;

            	    }


            	    }

            	    if ( input.LA(1)=='#'||(input.LA(1)>='.' && input.LA(1)<=':')||input.LA(1)=='='||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='{')||input.LA(1)=='}' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}

            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:90: ( ' ' )?
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==' ') ) {
            	        alt8=1;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:584:91: ' '
            	            {
            	            match(' '); 

            	            }
            	            break;

            	    }


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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:585:9: ( '{' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:585:11: '{'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:586:10: ( '}' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:586:12: '}'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:587:8: ( '[' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:587:10: '['
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:588:8: ( ']' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:588:10: ']'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:589:6: ( '-' '>' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:589:8: '-' '>'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:590:8: ( '\\n' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:590:9: '\\n'
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

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:591:13: ( '//' (~ ( '\\n' ) )* )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:591:15: '//' (~ ( '\\n' ) )*
            {
            match("//"); 

            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:591:20: (~ ( '\\n' ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:591:20: ~ ( '\\n' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


                _channel = HIDDEN;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "BLOCK_COMMENT"
    public final void mBLOCK_COMMENT() throws RecognitionException {
        try {
            int _type = BLOCK_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:594:14: ( '/*' ( . )* '*/' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:594:16: '/*' ( . )* '*/'
            {
            match("/*"); 

            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:594:21: ( . )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='*') ) {
                    int LA11_1 = input.LA(2);

                    if ( (LA11_1=='/') ) {
                        alt11=2;
                    }
                    else if ( ((LA11_1>='\u0000' && LA11_1<='.')||(LA11_1>='0' && LA11_1<='\uFFFF')) ) {
                        alt11=1;
                    }


                }
                else if ( ((LA11_0>='\u0000' && LA11_0<=')')||(LA11_0>='+' && LA11_0<='\uFFFF')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:594:21: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            match("*/"); 


                _channel = HIDDEN;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BLOCK_COMMENT"

    // $ANTLR start "TP"
    public final void mTP() throws RecognitionException {
        try {
            int _type = TP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:597:3: ( ':' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:597:5: ':'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:598:3: ( ';' )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:598:5: ';'
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
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:599:4: ( ( ' ' | '\\t' | '\\r' )+ )
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:599:8: ( ' ' | '\\t' | '\\r' )+
            {
            // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:599:8: ( ' ' | '\\t' | '\\r' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='\t'||LA12_0=='\r'||LA12_0==' ') ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:
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
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);

             
                _channel = HIDDEN;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:8: ( NUM | BOOL | IF | ELSE | WHILE | FOR | BREAK | CONTINUE | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | LINE_COMMENT | BLOCK_COMMENT | TP | PV | WS )
        int alt13=40;
        alt13 = dfa13.predict(input);
        switch (alt13) {
            case 1 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:10: NUM
                {
                mNUM(); 

                }
                break;
            case 2 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:14: BOOL
                {
                mBOOL(); 

                }
                break;
            case 3 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:19: IF
                {
                mIF(); 

                }
                break;
            case 4 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:22: ELSE
                {
                mELSE(); 

                }
                break;
            case 5 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:27: WHILE
                {
                mWHILE(); 

                }
                break;
            case 6 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:33: FOR
                {
                mFOR(); 

                }
                break;
            case 7 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:37: BREAK
                {
                mBREAK(); 

                }
                break;
            case 8 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:43: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 9 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:52: ID
                {
                mID(); 

                }
                break;
            case 10 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:55: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 11 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:61: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 12 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:67: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 13 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:74: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 14 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:81: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 15 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:89: MULT
                {
                mMULT(); 

                }
                break;
            case 16 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:94: DIV
                {
                mDIV(); 

                }
                break;
            case 17 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:98: PLUS
                {
                mPLUS(); 

                }
                break;
            case 18 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:103: PLUS_PLUS
                {
                mPLUS_PLUS(); 

                }
                break;
            case 19 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:113: MINUS
                {
                mMINUS(); 

                }
                break;
            case 20 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:119: MINUS_MINUS
                {
                mMINUS_MINUS(); 

                }
                break;
            case 21 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:131: CMP_LT
                {
                mCMP_LT(); 

                }
                break;
            case 22 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:138: CMP_LT_EQ
                {
                mCMP_LT_EQ(); 

                }
                break;
            case 23 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:148: CMP_GT
                {
                mCMP_GT(); 

                }
                break;
            case 24 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:155: CMP_GT_EQ
                {
                mCMP_GT_EQ(); 

                }
                break;
            case 25 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:165: CMP_EQ
                {
                mCMP_EQ(); 

                }
                break;
            case 26 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:172: CMP_NEQ
                {
                mCMP_NEQ(); 

                }
                break;
            case 27 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:180: AND
                {
                mAND(); 

                }
                break;
            case 28 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:184: OR
                {
                mOR(); 

                }
                break;
            case 29 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:187: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 30 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:202: LEFT_CB
                {
                mLEFT_CB(); 

                }
                break;
            case 31 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:210: RIGHT_CB
                {
                mRIGHT_CB(); 

                }
                break;
            case 32 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:219: LEFT_B
                {
                mLEFT_B(); 

                }
                break;
            case 33 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:226: RIGHT_B
                {
                mRIGHT_B(); 

                }
                break;
            case 34 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:234: ARROW
                {
                mARROW(); 

                }
                break;
            case 35 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:240: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 36 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:248: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 37 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:261: BLOCK_COMMENT
                {
                mBLOCK_COMMENT(); 

                }
                break;
            case 38 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:275: TP
                {
                mTP(); 

                }
                break;
            case 39 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:278: PV
                {
                mPV(); 

                }
                break;
            case 40 :
                // C:\\cygwin\\home\\Laurent_dev\\dev\\WPA\\WPAScript\\working_copy\\src\\language\\Script.g:1:281: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS =
        "\2\uffff\7\11\1\uffff\1\51\1\uffff\1\52\3\uffff\1\56\1\60\1\63"+
        "\1\65\1\67\13\uffff\3\11\1\73\4\11\20\uffff\2\11\1\102\1\uffff\4"+
        "\11\1\107\1\11\1\uffff\1\111\3\11\1\uffff\1\107\1\uffff\1\115\1"+
        "\116\1\11\2\uffff\2\11\1\122\1\uffff";
    static final String DFA13_eofS =
        "\123\uffff";
    static final String DFA13_minS =
        "\1\11\1\uffff\1\122\1\101\1\106\1\114\1\110\1\122\1\117\1\uffff"+
        "\1\75\1\uffff\1\40\3\uffff\1\52\1\53\1\55\2\75\13\uffff\1\125\1"+
        "\114\1\122\1\60\1\123\1\111\1\105\1\116\20\uffff\1\105\1\123\1\60"+
        "\1\uffff\1\105\1\114\1\101\1\124\1\60\1\105\1\uffff\1\60\1\105\1"+
        "\113\1\111\1\uffff\1\60\1\uffff\2\60\1\116\2\uffff\1\125\1\105\1"+
        "\60\1\uffff";
    static final String DFA13_maxS =
        "\1\175\1\uffff\1\162\1\157\1\146\1\154\1\150\1\162\1\157\1\uffff"+
        "\1\75\1\uffff\1\175\3\uffff\1\57\1\53\1\76\2\75\13\uffff\1\165\1"+
        "\154\1\162\1\172\1\163\1\151\1\145\1\156\20\uffff\1\145\1\163\1"+
        "\172\1\uffff\1\145\1\154\1\141\1\164\1\172\1\145\1\uffff\1\172\1"+
        "\145\1\153\1\151\1\uffff\1\172\1\uffff\2\172\1\156\2\uffff\1\165"+
        "\1\145\1\172\1\uffff";
    static final String DFA13_acceptS =
        "\1\uffff\1\1\7\uffff\1\11\1\uffff\1\13\1\uffff\1\15\1\16\1\17\5"+
        "\uffff\1\32\1\33\1\34\1\36\1\37\1\40\1\41\1\43\1\46\1\47\1\50\10"+
        "\uffff\1\31\1\12\1\14\1\35\1\44\1\45\1\20\1\22\1\21\1\24\1\42\1"+
        "\23\1\26\1\25\1\30\1\27\3\uffff\1\3\6\uffff\1\6\4\uffff\1\2\1\uffff"+
        "\1\4\3\uffff\1\5\1\7\3\uffff\1\10";
    static final String DFA13_specialS =
        "\123\uffff}>";
    static final String[] DFA13_transitionS = {
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
            "\1\53\2\uffff\1\53\12\uffff\15\53\2\uffff\1\53\3\uffff\32"+
            "\53\4\uffff\1\53\1\uffff\33\53\1\uffff\1\53",
            "",
            "",
            "",
            "\1\55\4\uffff\1\54",
            "\1\57",
            "\1\61\20\uffff\1\62",
            "\1\64",
            "\1\66",
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
            "\1\70\37\uffff\1\70",
            "\1\71\37\uffff\1\71",
            "\1\72\37\uffff\1\72",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\74\37\uffff\1\74",
            "\1\75\37\uffff\1\75",
            "\1\76\37\uffff\1\76",
            "\1\77\37\uffff\1\77",
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
            "",
            "",
            "",
            "\1\100\37\uffff\1\100",
            "\1\101\37\uffff\1\101",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "",
            "\1\103\37\uffff\1\103",
            "\1\104\37\uffff\1\104",
            "\1\105\37\uffff\1\105",
            "\1\106\37\uffff\1\106",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\110\37\uffff\1\110",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\112\37\uffff\1\112",
            "\1\113\37\uffff\1\113",
            "\1\114\37\uffff\1\114",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\117\37\uffff\1\117",
            "",
            "",
            "\1\120\37\uffff\1\120",
            "\1\121\37\uffff\1\121",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( NUM | BOOL | IF | ELSE | WHILE | FOR | BREAK | CONTINUE | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | LINE_COMMENT | BLOCK_COMMENT | TP | PV | WS );";
        }
    }
 

}