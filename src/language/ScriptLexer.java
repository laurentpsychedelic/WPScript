// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/language/Script.g 2011-09-29 21:01:16

package language;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ScriptLexer extends Lexer {
    public static final int CMP_NEQ=30;
    public static final int WHILE=15;
    public static final int ELSE=14;
    public static final int TP=35;
    public static final int BOOL=33;
    public static final int RIGHT_P=13;
    public static final int DQUOTE=36;
    public static final int FOR=16;
    public static final int CMP_EQ=29;
    public static final int MULT=23;
    public static final int MINUS=20;
    public static final int AND=21;
    public static final int ID=7;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int CMP_GT=27;
    public static final int NUM=32;
    public static final int IF=11;
    public static final int RIGHT_CB=6;
    public static final int CMP_GT_EQ=28;
    public static final int CMP_LT=25;
    public static final int STRING_LITERAL=34;
    public static final int RIGHT_B=38;
    public static final int WS=39;
    public static final int PLUS_PLUS=9;
    public static final int NEWLINE=5;
    public static final int COMMA=31;
    public static final int CMP_LT_EQ=26;
    public static final int EQUAL=8;
    public static final int OR=22;
    public static final int LEFT_B=37;
    public static final int ARROW=18;
    public static final int PV=17;
    public static final int PLUS=19;
    public static final int DIV=24;
    public static final int LEFT_P=12;
    public static final int MINUS_MINUS=10;

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
    public String getGrammarFileName() { return "/home/laurent/dev/WPAScript/working_copy/src/language/Script.g"; }

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:5: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:9: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
            {
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:9: ( '0' .. '9' )+
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:9: '0' .. '9'
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

            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:19: ( '.' ( '0' .. '9' )+ )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='.') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:20: '.' ( '0' .. '9' )+
                    {
                    match('.'); 
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:24: ( '0' .. '9' )+
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:549:24: '0' .. '9'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:550:5: ( ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) ) | ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) ) )
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:550:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    {
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:550:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:550:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:550:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    {
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:550:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:550:52: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:551:5: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:551:7: ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:552:5: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:552:7: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:553:6: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:553:8: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:554:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:554:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:555:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:555:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:555:33: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:556:6: ( '=' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:556:8: '='
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:557:6: ( ',' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:557:8: ','
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:558:7: ( '\"' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:558:9: '\"'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:559:7: ( '(' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:559:9: '('
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:560:8: ( ')' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:560:10: ')'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:561:5: ( '*' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:561:7: '*'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:562:4: ( '/' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:562:6: '/'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:563:5: ( '+' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:563:7: '+'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:564:10: ( '+' '+' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:564:12: '+' '+'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:565:6: ( '-' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:565:8: '-'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:566:12: ( '-' '-' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:566:14: '-' '-'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:567:7: ( '<' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:567:9: '<'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:568:10: ( '<' '=' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:568:12: '<' '='
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:569:7: ( '>' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:569:9: '>'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:570:10: ( '>' '=' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:570:12: '>' '='
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:571:7: ( '=' '=' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:571:9: '=' '='
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:572:8: ( '!' '=' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:572:9: '!' '='
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:573:4: ( '&' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:573:6: '&'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:574:4: ( '|' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:574:6: '|'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:575:15: ( '\"' ID '\"' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:575:17: '\"' ID '\"'
            {
            match('\"'); 
            mID(); 
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:576:9: ( '{' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:576:11: '{'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:577:10: ( '}' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:577:12: '}'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:578:8: ( '[' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:578:10: '['
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:579:8: ( ']' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:579:10: ']'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:580:6: ( '-' '>' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:580:8: '-' '>'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:581:8: ( '\\n' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:581:9: '\\n'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:582:3: ( ':' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:582:5: ':'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:583:3: ( ';' )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:583:5: ';'
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:584:4: ( ( ' ' | '\\t' | '\\r' )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:584:8: ( ' ' | '\\t' | '\\r' )+
            {
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:584:8: ( ' ' | '\\t' | '\\r' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\t'||LA6_0=='\r'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:
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
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
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
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:8: ( NUM | BOOL | IF | ELSE | WHILE | FOR | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | TP | PV | WS )
        int alt7=36;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:10: NUM
                {
                mNUM(); 

                }
                break;
            case 2 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:14: BOOL
                {
                mBOOL(); 

                }
                break;
            case 3 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:19: IF
                {
                mIF(); 

                }
                break;
            case 4 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:22: ELSE
                {
                mELSE(); 

                }
                break;
            case 5 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:27: WHILE
                {
                mWHILE(); 

                }
                break;
            case 6 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:33: FOR
                {
                mFOR(); 

                }
                break;
            case 7 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:37: ID
                {
                mID(); 

                }
                break;
            case 8 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:40: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 9 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:46: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 10 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:52: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 11 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:59: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 12 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:66: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 13 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:74: MULT
                {
                mMULT(); 

                }
                break;
            case 14 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:79: DIV
                {
                mDIV(); 

                }
                break;
            case 15 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:83: PLUS
                {
                mPLUS(); 

                }
                break;
            case 16 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:88: PLUS_PLUS
                {
                mPLUS_PLUS(); 

                }
                break;
            case 17 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:98: MINUS
                {
                mMINUS(); 

                }
                break;
            case 18 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:104: MINUS_MINUS
                {
                mMINUS_MINUS(); 

                }
                break;
            case 19 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:116: CMP_LT
                {
                mCMP_LT(); 

                }
                break;
            case 20 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:123: CMP_LT_EQ
                {
                mCMP_LT_EQ(); 

                }
                break;
            case 21 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:133: CMP_GT
                {
                mCMP_GT(); 

                }
                break;
            case 22 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:140: CMP_GT_EQ
                {
                mCMP_GT_EQ(); 

                }
                break;
            case 23 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:150: CMP_EQ
                {
                mCMP_EQ(); 

                }
                break;
            case 24 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:157: CMP_NEQ
                {
                mCMP_NEQ(); 

                }
                break;
            case 25 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:165: AND
                {
                mAND(); 

                }
                break;
            case 26 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:169: OR
                {
                mOR(); 

                }
                break;
            case 27 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:172: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 28 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:187: LEFT_CB
                {
                mLEFT_CB(); 

                }
                break;
            case 29 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:195: RIGHT_CB
                {
                mRIGHT_CB(); 

                }
                break;
            case 30 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:204: LEFT_B
                {
                mLEFT_B(); 

                }
                break;
            case 31 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:211: RIGHT_B
                {
                mRIGHT_B(); 

                }
                break;
            case 32 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:219: ARROW
                {
                mARROW(); 

                }
                break;
            case 33 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:225: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 34 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:233: TP
                {
                mTP(); 

                }
                break;
            case 35 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:236: PV
                {
                mPV(); 

                }
                break;
            case 36 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:1:239: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\2\uffff\5\7\1\uffff\1\45\1\uffff\1\47\4\uffff\1\51\1\54\1\56\1"+
        "\60\13\uffff\3\7\1\64\2\7\15\uffff\2\7\1\71\1\uffff\2\7\1\74\1\7"+
        "\1\uffff\1\76\1\7\1\uffff\1\74\1\uffff\1\100\1\uffff";
    static final String DFA7_eofS =
        "\101\uffff";
    static final String DFA7_minS =
        "\1\11\1\uffff\1\122\1\101\1\106\1\114\1\110\1\uffff\1\75\1\uffff"+
        "\1\101\4\uffff\1\53\1\55\2\75\13\uffff\1\125\1\114\1\122\1\60\1"+
        "\123\1\111\15\uffff\1\105\1\123\1\60\1\uffff\1\105\1\114\1\60\1"+
        "\105\1\uffff\1\60\1\105\1\uffff\1\60\1\uffff\1\60\1\uffff";
    static final String DFA7_maxS =
        "\1\175\1\uffff\1\162\1\157\1\146\1\154\1\150\1\uffff\1\75\1\uffff"+
        "\1\172\4\uffff\1\53\1\76\2\75\13\uffff\1\165\1\154\1\162\1\172\1"+
        "\163\1\151\15\uffff\1\145\1\163\1\172\1\uffff\1\145\1\154\1\172"+
        "\1\145\1\uffff\1\172\1\145\1\uffff\1\172\1\uffff\1\172\1\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\5\uffff\1\7\1\uffff\1\11\1\uffff\1\13\1\14\1\15\1\16"+
        "\4\uffff\1\30\1\31\1\32\1\34\1\35\1\36\1\37\1\41\1\42\1\43\1\44"+
        "\6\uffff\1\27\1\10\1\33\1\12\1\20\1\17\1\22\1\40\1\21\1\24\1\23"+
        "\1\26\1\25\3\uffff\1\3\4\uffff\1\6\2\uffff\1\2\1\uffff\1\4\1\uffff"+
        "\1\5";
    static final String DFA7_specialS =
        "\101\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\35\1\32\2\uffff\1\35\22\uffff\1\35\1\23\1\12\3\uffff\1\24"+
            "\1\uffff\1\13\1\14\1\15\1\17\1\11\1\20\1\uffff\1\16\12\1\1\33"+
            "\1\34\1\21\1\10\1\22\2\uffff\4\7\1\5\1\3\2\7\1\4\12\7\1\2\2"+
            "\7\1\6\3\7\1\30\1\uffff\1\31\1\uffff\1\7\1\uffff\4\7\1\5\1\3"+
            "\2\7\1\4\12\7\1\2\2\7\1\6\3\7\1\26\1\25\1\27",
            "",
            "\1\36\37\uffff\1\36",
            "\1\37\15\uffff\1\40\21\uffff\1\37\15\uffff\1\40",
            "\1\41\37\uffff\1\41",
            "\1\42\37\uffff\1\42",
            "\1\43\37\uffff\1\43",
            "",
            "\1\44",
            "",
            "\32\46\4\uffff\1\46\1\uffff\32\46",
            "",
            "",
            "",
            "",
            "\1\50",
            "\1\52\20\uffff\1\53",
            "\1\55",
            "\1\57",
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
            "\1\61\37\uffff\1\61",
            "\1\62\37\uffff\1\62",
            "\1\63\37\uffff\1\63",
            "\12\7\7\uffff\32\7\4\uffff\1\7\1\uffff\32\7",
            "\1\65\37\uffff\1\65",
            "\1\66\37\uffff\1\66",
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
            "\1\67\37\uffff\1\67",
            "\1\70\37\uffff\1\70",
            "\12\7\7\uffff\32\7\4\uffff\1\7\1\uffff\32\7",
            "",
            "\1\72\37\uffff\1\72",
            "\1\73\37\uffff\1\73",
            "\12\7\7\uffff\32\7\4\uffff\1\7\1\uffff\32\7",
            "\1\75\37\uffff\1\75",
            "",
            "\12\7\7\uffff\32\7\4\uffff\1\7\1\uffff\32\7",
            "\1\77\37\uffff\1\77",
            "",
            "\12\7\7\uffff\32\7\4\uffff\1\7\1\uffff\32\7",
            "",
            "\12\7\7\uffff\32\7\4\uffff\1\7\1\uffff\32\7",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( NUM | BOOL | IF | ELSE | WHILE | FOR | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | TP | PV | WS );";
        }
    }
 

}