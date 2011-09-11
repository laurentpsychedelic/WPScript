// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g 2011-09-11 20:47:32

package lexicalparser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GrammarLexer extends Lexer {
    public static final int T__20=20;
    public static final int BOOL=10;
    public static final int RIGHT_P=7;
    public static final int DQUOTE=11;
    public static final int ID=5;
    public static final int EOF=-1;
    public static final int LEFT_CB=14;
    public static final int NUM=9;
    public static final int IF=12;
    public static final int RIGHT_CB=15;
    public static final int T__19=19;
    public static final int WS=16;
    public static final int T__18=18;
    public static final int NEWLINE=4;
    public static final int T__17=17;
    public static final int COMMA=8;
    public static final int EQUAL=13;
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

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
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
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
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
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
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
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:10:7: ( '/' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:10:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:5: ( ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )? )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:9: ( '0' .. '9' )+ ( '.' ( '0' .. '9' )+ )?
            {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:9: ( '0' .. '9' )+
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:9: '0' .. '9'
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

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:19: ( '.' ( '0' .. '9' )+ )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='.') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:20: '.' ( '0' .. '9' )+
                    {
                    match('.'); 
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:24: ( '0' .. '9' )+
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:159:24: '0' .. '9'
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:160:5: ( ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) ) | ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) ) )
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
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:160:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    {
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:160:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:160:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:160:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    {
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:160:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:160:52: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:161:5: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:161:7: ( 'I' | 'i' ) ( 'F' | 'f' )
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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:162:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:162:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:162:33: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:163:6: ( '=' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:163:8: '='
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:164:6: ( ',' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:164:8: ','
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:165:7: ( '\"' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:165:9: '\"'
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:166:7: ( '(' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:166:9: '('
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:167:8: ( ')' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:167:10: ')'
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

    // $ANTLR start "LEFT_CB"
    public final void mLEFT_CB() throws RecognitionException {
        try {
            int _type = LEFT_CB;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:168:9: ( '{' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:168:11: '{'
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:169:10: ( '}' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:169:12: '}'
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

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:170:8: ( ( '\\r' )? '\\n' )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:170:9: ( '\\r' )? '\\n'
            {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:170:9: ( '\\r' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\r') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:170:9: '\\r'
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:171:5: ( ( ' ' | '\\t' )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:171:9: ( ' ' | '\\t' )+
            {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:171:9: ( ' ' | '\\t' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='\t'||LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
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
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
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
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:8: ( T__17 | T__18 | T__19 | T__20 | NUM | BOOL | IF | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | LEFT_CB | RIGHT_CB | NEWLINE | WS )
        int alt8=17;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:10: T__17
                {
                mT__17(); 

                }
                break;
            case 2 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:16: T__18
                {
                mT__18(); 

                }
                break;
            case 3 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:22: T__19
                {
                mT__19(); 

                }
                break;
            case 4 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:28: T__20
                {
                mT__20(); 

                }
                break;
            case 5 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:34: NUM
                {
                mNUM(); 

                }
                break;
            case 6 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:38: BOOL
                {
                mBOOL(); 

                }
                break;
            case 7 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:43: IF
                {
                mIF(); 

                }
                break;
            case 8 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:46: ID
                {
                mID(); 

                }
                break;
            case 9 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:49: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 10 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:55: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 11 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:61: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 12 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:68: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 13 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:75: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 14 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:83: LEFT_CB
                {
                mLEFT_CB(); 

                }
                break;
            case 15 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:91: RIGHT_CB
                {
                mRIGHT_CB(); 

                }
                break;
            case 16 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:100: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 17 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:1:108: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\6\uffff\3\11\12\uffff\2\11\1\30\2\11\1\uffff\1\33\1\11\1\uffff"+
        "\1\33";
    static final String DFA8_eofS =
        "\35\uffff";
    static final String DFA8_minS =
        "\1\11\5\uffff\1\122\1\101\1\106\12\uffff\1\125\1\114\1\60\1\105"+
        "\1\123\1\uffff\1\60\1\105\1\uffff\1\60";
    static final String DFA8_maxS =
        "\1\175\5\uffff\1\162\1\141\1\146\12\uffff\1\165\1\154\1\172\1\145"+
        "\1\163\1\uffff\1\172\1\145\1\uffff\1\172";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\3\uffff\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\5\uffff\1\7\2\uffff\1\6\1\uffff";
    static final String DFA8_specialS =
        "\35\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\22\1\21\2\uffff\1\21\22\uffff\1\22\1\uffff\1\14\5\uffff\1"+
            "\15\1\16\1\3\1\1\1\13\1\2\1\uffff\1\4\12\5\3\uffff\1\12\3\uffff"+
            "\5\11\1\7\2\11\1\10\12\11\1\6\6\11\4\uffff\1\11\1\uffff\5\11"+
            "\1\7\2\11\1\10\12\11\1\6\6\11\1\17\1\uffff\1\20",
            "",
            "",
            "",
            "",
            "",
            "\1\23\37\uffff\1\23",
            "\1\24\37\uffff\1\24",
            "\1\25\37\uffff\1\25",
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
            "\1\26\37\uffff\1\26",
            "\1\27\37\uffff\1\27",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\31\37\uffff\1\31",
            "\1\32\37\uffff\1\32",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11",
            "\1\34\37\uffff\1\34",
            "",
            "\12\11\7\uffff\32\11\4\uffff\1\11\1\uffff\32\11"
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__17 | T__18 | T__19 | T__20 | NUM | BOOL | IF | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | LEFT_CB | RIGHT_CB | NEWLINE | WS );";
        }
    }
 

}