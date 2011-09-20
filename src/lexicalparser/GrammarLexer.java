// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g 2011-09-20 16:06:21

package lexicalparser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GrammarLexer extends Lexer {
    public static final int ELSE=12;
    public static final int BOOL=19;
    public static final int TP=21;
    public static final int RIGHT_P=11;
    public static final int DQUOTE=22;
    public static final int MINUS=14;
    public static final int MULT=15;
    public static final int ID=7;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int NUM=18;
    public static final int RIGHT_CB=6;
    public static final int IF=9;
    public static final int WS=25;
    public static final int RIGHT_B=24;
    public static final int STRING_LITERAL=20;
    public static final int NEWLINE=5;
    public static final int COMMA=17;
    public static final int EQUAL=8;
    public static final int LEFT_B=23;
    public static final int PLUS=13;
    public static final int DIV=16;
    public static final int LEFT_P=10;

        public boolean skip_ws = true;


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

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:5: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:9: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
            {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:9: ( '0' .. '9' )+
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
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:9: '0' .. '9'
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

            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:19: ( '.' ( '0' .. '9' )+ )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='.') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:20: '.' ( '0' .. '9' )+
                    {
                    match('.'); 
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:24: ( '0' .. '9' )+
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
                    	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:352:24: '0' .. '9'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:353:5: ( ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) ) | ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) ) )
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
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:353:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    {
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:353:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:353:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:353:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    {
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:353:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:353:52: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:354:5: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:354:7: ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:355:5: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:355:7: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:356:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:356:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:356:33: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:357:6: ( '=' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:357:8: '='
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:358:6: ( ',' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:358:8: ','
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:359:7: ( '\"' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:359:9: '\"'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:360:7: ( '(' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:360:9: '('
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:361:8: ( ')' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:361:10: ')'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:362:5: ( '*' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:362:7: '*'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:363:4: ( '/' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:363:6: '/'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:364:5: ( '+' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:364:7: '+'
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

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:365:15: ( '\"' ID '\"' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:365:17: '\"' ID '\"'
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

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:366:6: ( '-' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:366:8: '-'
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

    // $ANTLR start "LEFT_CB"
    public final void mLEFT_CB() throws RecognitionException {
        try {
            int _type = LEFT_CB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:367:9: ( '{' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:367:11: '{'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:368:10: ( '}' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:368:12: '}'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:369:8: ( '[' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:369:10: '['
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:370:8: ( ']' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:370:10: ']'
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

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:371:8: ( '\\n' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:371:9: '\\n'
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
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:372:3: ( ':' )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:372:5: ':'
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

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:373:4: ( ( ' ' | '\\t' | '\\r' )+ )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:373:8: ( ' ' | '\\t' | '\\r' )+
            {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:373:8: ( ' ' | '\\t' | '\\r' )+
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
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:
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
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:8: ( NUM | BOOL | IF | ELSE | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | STRING_LITERAL | MINUS | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | NEWLINE | TP | WS )
        int alt7=22;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:10: NUM
                {
                mNUM(); 

                }
                break;
            case 2 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:14: BOOL
                {
                mBOOL(); 

                }
                break;
            case 3 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:19: IF
                {
                mIF(); 

                }
                break;
            case 4 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:22: ELSE
                {
                mELSE(); 

                }
                break;
            case 5 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:27: ID
                {
                mID(); 

                }
                break;
            case 6 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:30: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 7 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:36: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 8 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:42: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 9 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:49: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 10 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:56: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 11 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:64: MULT
                {
                mMULT(); 

                }
                break;
            case 12 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:69: DIV
                {
                mDIV(); 

                }
                break;
            case 13 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:73: PLUS
                {
                mPLUS(); 

                }
                break;
            case 14 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:78: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 15 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:93: MINUS
                {
                mMINUS(); 

                }
                break;
            case 16 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:99: LEFT_CB
                {
                mLEFT_CB(); 

                }
                break;
            case 17 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:107: RIGHT_CB
                {
                mRIGHT_CB(); 

                }
                break;
            case 18 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:116: LEFT_B
                {
                mLEFT_B(); 

                }
                break;
            case 19 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:123: RIGHT_B
                {
                mRIGHT_B(); 

                }
                break;
            case 20 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:131: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 21 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:139: TP
                {
                mTP(); 

                }
                break;
            case 22 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:1:142: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\2\uffff\4\6\3\uffff\1\33\15\uffff\2\6\1\37\1\6\2\uffff\2\6\1\uffff"+
        "\1\6\1\44\1\6\1\46\1\uffff\1\44\1\uffff";
    static final String DFA7_eofS =
        "\47\uffff";
    static final String DFA7_minS =
        "\1\11\1\uffff\1\122\1\101\1\106\1\114\3\uffff\1\101\15\uffff\1"+
        "\125\1\114\1\60\1\123\2\uffff\1\105\1\123\1\uffff\1\105\1\60\1\105"+
        "\1\60\1\uffff\1\60\1\uffff";
    static final String DFA7_maxS =
        "\1\175\1\uffff\1\162\1\141\1\146\1\154\3\uffff\1\172\15\uffff\1"+
        "\165\1\154\1\172\1\163\2\uffff\1\145\1\163\1\uffff\1\145\1\172\1"+
        "\145\1\172\1\uffff\1\172\1\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\4\uffff\1\5\1\6\1\7\1\uffff\1\11\1\12\1\13\1\14\1"+
        "\15\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\4\uffff\1\10\1\16\2"+
        "\uffff\1\3\4\uffff\1\2\1\uffff\1\4";
    static final String DFA7_specialS =
        "\47\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\26\1\24\2\uffff\1\26\22\uffff\1\26\1\uffff\1\11\5\uffff"+
            "\1\12\1\13\1\14\1\16\1\10\1\17\1\uffff\1\15\12\1\1\25\2\uffff"+
            "\1\7\3\uffff\4\6\1\5\1\3\2\6\1\4\12\6\1\2\6\6\1\22\1\uffff\1"+
            "\23\1\uffff\1\6\1\uffff\4\6\1\5\1\3\2\6\1\4\12\6\1\2\6\6\1\20"+
            "\1\uffff\1\21",
            "",
            "\1\27\37\uffff\1\27",
            "\1\30\37\uffff\1\30",
            "\1\31\37\uffff\1\31",
            "\1\32\37\uffff\1\32",
            "",
            "",
            "",
            "\32\34\4\uffff\1\34\1\uffff\32\34",
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
            "\1\35\37\uffff\1\35",
            "\1\36\37\uffff\1\36",
            "\12\6\7\uffff\32\6\4\uffff\1\6\1\uffff\32\6",
            "\1\40\37\uffff\1\40",
            "",
            "",
            "\1\41\37\uffff\1\41",
            "\1\42\37\uffff\1\42",
            "",
            "\1\43\37\uffff\1\43",
            "\12\6\7\uffff\32\6\4\uffff\1\6\1\uffff\32\6",
            "\1\45\37\uffff\1\45",
            "\12\6\7\uffff\32\6\4\uffff\1\6\1\uffff\32\6",
            "",
            "\12\6\7\uffff\32\6\4\uffff\1\6\1\uffff\32\6",
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
            return "1:1: Tokens : ( NUM | BOOL | IF | ELSE | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | STRING_LITERAL | MINUS | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | NEWLINE | TP | WS );";
        }
    }
 

}