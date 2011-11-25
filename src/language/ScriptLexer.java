// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g 2011-11-25 11:27:07

package language;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ScriptLexer extends Lexer {
    public static final int FUNCTION=33;
    public static final int WHILE=17;
    public static final int FOR=18;
    public static final int DQUOTE=42;
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
    public static final int STRING_LITERAL=40;
    public static final int RIGHT_B=36;
    public static final int PLUS_PLUS=11;
    public static final int CONTINUE=8;
    public static final int COMMA=34;
    public static final int CMP_LT_EQ=28;
    public static final int EQUAL=10;
    public static final int LEFT_B=35;
    public static final int BLOCK_COMMENT=44;
    public static final int PV=19;
    public static final int PLUS=21;
    public static final int LEFT_P=14;
    public static final int CMP_NEQ=32;
    public static final int LINE_COMMENT=43;
    public static final int ELSE=16;
    public static final int BOOL=38;
    public static final int TP=41;
    public static final int RIGHT_P=15;
    public static final int MINUS=22;
    public static final int MULT=25;
    public static final int CMP_GT=29;
    public static final int NUM=37;
    public static final int WS=45;
    public static final int NEWLINE=5;
    public static final int OR=24;
    public static final int CONSTANT=39;
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
    public String getGrammarFileName() { return "/home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g"; }

    // $ANTLR start "CONSTANT"
    public final void mCONSTANT() throws RecognitionException {
        try {
            int _type = CONSTANT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:620:9: ( ( 'P' 'I' ) | ( 'e' ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='P') ) {
                alt1=1;
            }
            else if ( (LA1_0=='e') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:620:11: ( 'P' 'I' )
                    {
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:620:11: ( 'P' 'I' )
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:620:12: 'P' 'I'
                    {
                    match('P'); 
                    match('I'); 

                    }


                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:620:23: ( 'e' )
                    {
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:620:23: ( 'e' )
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:620:24: 'e'
                    {
                    match('e'); 

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
    // $ANTLR end "CONSTANT"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:5: ( ( '0' .. '9' )+ ( '.' ( ( '0' .. '9' )+ )? )? )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:9: ( '0' .. '9' )+ ( '.' ( ( '0' .. '9' )+ )? )?
            {
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:9: ( '0' .. '9' )+
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:9: '0' .. '9'
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

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:19: ( '.' ( ( '0' .. '9' )+ )? )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='.') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:20: '.' ( ( '0' .. '9' )+ )?
                    {
                    match('.'); 
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:24: ( ( '0' .. '9' )+ )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:25: ( '0' .. '9' )+
                            {
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:25: ( '0' .. '9' )+
                            int cnt3=0;
                            loop3:
                            do {
                                int alt3=2;
                                int LA3_0 = input.LA(1);

                                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                                    alt3=1;
                                }


                                switch (alt3) {
                            	case 1 :
                            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:621:25: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); 

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt3 >= 1 ) break loop3;
                                        EarlyExitException eee =
                                            new EarlyExitException(3, input);
                                        throw eee;
                                }
                                cnt3++;
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:622:5: ( ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) ) | ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='T'||LA6_0=='t') ) {
                alt6=1;
            }
            else if ( (LA6_0=='F'||LA6_0=='f') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:622:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    {
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:622:7: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:622:8: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:622:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    {
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:622:51: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:622:52: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:623:5: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:623:7: ( 'I' | 'i' ) ( 'F' | 'f' )
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:624:5: ( ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:624:7: ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:625:6: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:625:8: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'E' | 'e' )
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:626:5: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:626:7: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:627:6: ( ( 'B' | 'b' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'K' | 'k' ) )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:627:8: ( 'B' | 'b' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'K' | 'k' )
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:628:9: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:628:11: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'E' | 'e' )
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

    // $ANTLR start "FUNCTION"
    public final void mFUNCTION() throws RecognitionException {
        try {
            int _type = FUNCTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:629:9: ( ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:629:11: ( 'F' | 'f' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
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

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FUNCTION"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:630:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:630:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:630:33: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:
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
            	    break loop7;
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:631:6: ( '=' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:631:8: '='
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:632:6: ( ',' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:632:8: ','
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:633:7: ( '\"' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:633:9: '\"'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:634:7: ( '(' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:634:9: '('
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:635:8: ( ')' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:635:10: ')'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:636:5: ( '*' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:636:7: '*'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:637:4: ( '/' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:637:6: '/'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:638:5: ( '+' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:638:7: '+'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:639:10: ( '+' '+' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:639:12: '+' '+'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:640:6: ( '-' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:640:8: '-'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:641:12: ( '-' '-' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:641:14: '-' '-'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:642:7: ( '<' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:642:9: '<'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:643:10: ( '<' '=' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:643:12: '<' '='
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:644:7: ( '>' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:644:9: '>'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:645:10: ( '>' '=' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:645:12: '>' '='
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:646:7: ( '=' '=' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:646:9: '=' '='
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:647:8: ( '!' '=' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:647:9: '!' '='
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:648:4: ( '&' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:648:6: '&'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:649:4: ( '|' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:649:6: '|'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:15: ( '\"' ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )? )+ '\"' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:17: '\"' ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )? )+ '\"'
            {
            match('\"'); 
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:21: ( ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )? )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==' '||LA10_0=='#'||(LA10_0>='.' && LA10_0<=':')||LA10_0=='='||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='{')||LA10_0=='}') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:22: ( ( ' ' )? ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '{' | '}' | '#' | '=' | '.' | '/' | ':' ) ( ' ' )?
            	    {
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:22: ( ( ' ' )? )
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:23: ( ' ' )?
            	    {
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:23: ( ' ' )?
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==' ') ) {
            	        alt8=1;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:23: ' '
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

            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:90: ( ' ' )?
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0==' ') ) {
            	        alt9=1;
            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:650:91: ' '
            	            {
            	            match(' '); 

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:651:9: ( '{' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:651:11: '{'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:652:10: ( '}' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:652:12: '}'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:653:8: ( '[' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:653:10: '['
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:654:8: ( ']' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:654:10: ']'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:655:6: ( '-' '>' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:655:8: '-' '>'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:656:8: ( '\\n' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:656:9: '\\n'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:657:13: ( '//' (~ ( '\\n' ) )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:657:15: '//' (~ ( '\\n' ) )*
            {
            match("//"); 

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:657:20: (~ ( '\\n' ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\u0000' && LA11_0<='\t')||(LA11_0>='\u000B' && LA11_0<='\uFFFF')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:657:20: ~ ( '\\n' )
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
            	    break loop11;
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:660:14: ( '/*' ( . )* '*/' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:660:16: '/*' ( . )* '*/'
            {
            match("/*"); 

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:660:21: ( . )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='*') ) {
                    int LA12_1 = input.LA(2);

                    if ( (LA12_1=='/') ) {
                        alt12=2;
                    }
                    else if ( ((LA12_1>='\u0000' && LA12_1<='.')||(LA12_1>='0' && LA12_1<='\uFFFF')) ) {
                        alt12=1;
                    }


                }
                else if ( ((LA12_0>='\u0000' && LA12_0<=')')||(LA12_0>='+' && LA12_0<='\uFFFF')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:660:21: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop12;
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:663:3: ( ':' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:663:5: ':'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:664:3: ( ';' )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:664:5: ';'
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:665:4: ( ( ' ' | '\\t' | '\\r' )+ )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:665:8: ( ' ' | '\\t' | '\\r' )+
            {
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:665:8: ( ' ' | '\\t' | '\\r' )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='\t'||LA13_0=='\r'||LA13_0==' ') ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:
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
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
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
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:8: ( CONSTANT | NUM | BOOL | IF | ELSE | WHILE | FOR | BREAK | CONTINUE | FUNCTION | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | LINE_COMMENT | BLOCK_COMMENT | TP | PV | WS )
        int alt14=42;
        alt14 = dfa14.predict(input);
        switch (alt14) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:10: CONSTANT
                {
                mCONSTANT(); 

                }
                break;
            case 2 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:19: NUM
                {
                mNUM(); 

                }
                break;
            case 3 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:23: BOOL
                {
                mBOOL(); 

                }
                break;
            case 4 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:28: IF
                {
                mIF(); 

                }
                break;
            case 5 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:31: ELSE
                {
                mELSE(); 

                }
                break;
            case 6 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:36: WHILE
                {
                mWHILE(); 

                }
                break;
            case 7 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:42: FOR
                {
                mFOR(); 

                }
                break;
            case 8 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:46: BREAK
                {
                mBREAK(); 

                }
                break;
            case 9 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:52: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 10 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:61: FUNCTION
                {
                mFUNCTION(); 

                }
                break;
            case 11 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:70: ID
                {
                mID(); 

                }
                break;
            case 12 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:73: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 13 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:79: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 14 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:85: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 15 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:92: LEFT_P
                {
                mLEFT_P(); 

                }
                break;
            case 16 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:99: RIGHT_P
                {
                mRIGHT_P(); 

                }
                break;
            case 17 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:107: MULT
                {
                mMULT(); 

                }
                break;
            case 18 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:112: DIV
                {
                mDIV(); 

                }
                break;
            case 19 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:116: PLUS
                {
                mPLUS(); 

                }
                break;
            case 20 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:121: PLUS_PLUS
                {
                mPLUS_PLUS(); 

                }
                break;
            case 21 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:131: MINUS
                {
                mMINUS(); 

                }
                break;
            case 22 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:137: MINUS_MINUS
                {
                mMINUS_MINUS(); 

                }
                break;
            case 23 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:149: CMP_LT
                {
                mCMP_LT(); 

                }
                break;
            case 24 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:156: CMP_LT_EQ
                {
                mCMP_LT_EQ(); 

                }
                break;
            case 25 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:166: CMP_GT
                {
                mCMP_GT(); 

                }
                break;
            case 26 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:173: CMP_GT_EQ
                {
                mCMP_GT_EQ(); 

                }
                break;
            case 27 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:183: CMP_EQ
                {
                mCMP_EQ(); 

                }
                break;
            case 28 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:190: CMP_NEQ
                {
                mCMP_NEQ(); 

                }
                break;
            case 29 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:198: AND
                {
                mAND(); 

                }
                break;
            case 30 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:202: OR
                {
                mOR(); 

                }
                break;
            case 31 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:205: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 32 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:220: LEFT_CB
                {
                mLEFT_CB(); 

                }
                break;
            case 33 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:228: RIGHT_CB
                {
                mRIGHT_CB(); 

                }
                break;
            case 34 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:237: LEFT_B
                {
                mLEFT_B(); 

                }
                break;
            case 35 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:244: RIGHT_B
                {
                mRIGHT_B(); 

                }
                break;
            case 36 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:252: ARROW
                {
                mARROW(); 

                }
                break;
            case 37 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:258: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 38 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:266: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 39 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:279: BLOCK_COMMENT
                {
                mBLOCK_COMMENT(); 

                }
                break;
            case 40 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:293: TP
                {
                mTP(); 

                }
                break;
            case 41 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:296: PV
                {
                mPV(); 

                }
                break;
            case 42 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:1:299: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA14_eotS =
        "\1\uffff\1\13\1\44\1\uffff\7\13\1\uffff\1\56\1\uffff\1\57\3\uffff"+
        "\1\63\1\65\1\70\1\72\1\74\13\uffff\1\44\1\13\1\uffff\4\13\1\102"+
        "\3\13\20\uffff\3\13\1\111\1\13\1\uffff\3\13\1\116\1\117\1\13\1\uffff"+
        "\4\13\2\uffff\1\117\1\13\1\126\1\127\2\13\2\uffff\3\13\1\135\1\136"+
        "\2\uffff";
    static final String DFA14_eofS =
        "\137\uffff";
    static final String DFA14_minS =
        "\1\11\1\111\1\60\1\uffff\1\122\1\101\1\106\1\114\1\110\1\122\1\117"+
        "\1\uffff\1\75\1\uffff\1\40\3\uffff\1\52\1\53\1\55\2\75\13\uffff"+
        "\1\60\1\123\1\uffff\1\125\1\114\1\122\1\116\1\60\1\111\1\105\1\116"+
        "\20\uffff\2\105\1\123\1\60\1\103\1\uffff\1\114\1\101\1\124\2\60"+
        "\1\105\1\uffff\1\124\1\105\1\113\1\111\2\uffff\1\60\1\111\2\60\1"+
        "\116\1\117\2\uffff\1\125\1\116\1\105\2\60\2\uffff";
    static final String DFA14_maxS =
        "\1\175\1\111\1\172\1\uffff\1\162\1\165\1\146\1\154\1\150\1\162\1"+
        "\157\1\uffff\1\75\1\uffff\1\175\3\uffff\1\57\1\53\1\76\2\75\13\uffff"+
        "\1\172\1\163\1\uffff\1\165\1\154\1\162\1\156\1\172\1\151\1\145\1"+
        "\156\20\uffff\2\145\1\163\1\172\1\143\1\uffff\1\154\1\141\1\164"+
        "\2\172\1\145\1\uffff\1\164\1\145\1\153\1\151\2\uffff\1\172\1\151"+
        "\2\172\1\156\1\157\2\uffff\1\165\1\156\1\145\2\172\2\uffff";
    static final String DFA14_acceptS =
        "\3\uffff\1\2\7\uffff\1\13\1\uffff\1\15\1\uffff\1\17\1\20\1\21\5"+
        "\uffff\1\34\1\35\1\36\1\40\1\41\1\42\1\43\1\45\1\50\1\51\1\52\2"+
        "\uffff\1\1\10\uffff\1\33\1\14\1\16\1\37\1\46\1\47\1\22\1\24\1\23"+
        "\1\26\1\44\1\25\1\30\1\27\1\32\1\31\5\uffff\1\4\6\uffff\1\7\4\uffff"+
        "\1\5\1\3\6\uffff\1\6\1\10\5\uffff\1\12\1\11";
    static final String DFA14_specialS =
        "\137\uffff}>";
    static final String[] DFA14_transitionS = {
            "\1\41\1\36\2\uffff\1\41\22\uffff\1\41\1\27\1\16\3\uffff\1\30"+
            "\1\uffff\1\17\1\20\1\21\1\23\1\15\1\24\1\uffff\1\22\12\3\1\37"+
            "\1\40\1\25\1\14\1\26\2\uffff\1\13\1\11\1\12\1\13\1\7\1\5\2\13"+
            "\1\6\6\13\1\1\3\13\1\4\2\13\1\10\3\13\1\34\1\uffff\1\35\1\uffff"+
            "\1\13\1\uffff\1\13\1\11\1\12\1\13\1\2\1\5\2\13\1\6\12\13\1\4"+
            "\2\13\1\10\3\13\1\32\1\31\1\33",
            "\1\42",
            "\12\13\7\uffff\13\13\1\43\16\13\4\uffff\1\13\1\uffff\13\13"+
            "\1\43\16\13",
            "",
            "\1\45\37\uffff\1\45",
            "\1\46\15\uffff\1\47\5\uffff\1\50\13\uffff\1\46\15\uffff\1\47"+
            "\5\uffff\1\50",
            "\1\51\37\uffff\1\51",
            "\1\43\37\uffff\1\43",
            "\1\52\37\uffff\1\52",
            "\1\53\37\uffff\1\53",
            "\1\54\37\uffff\1\54",
            "",
            "\1\55",
            "",
            "\1\60\2\uffff\1\60\12\uffff\15\60\2\uffff\1\60\3\uffff\32\60"+
            "\4\uffff\1\60\1\uffff\33\60\1\uffff\1\60",
            "",
            "",
            "",
            "\1\62\4\uffff\1\61",
            "\1\64",
            "\1\66\20\uffff\1\67",
            "\1\71",
            "\1\73",
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
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\1\75\37\uffff\1\75",
            "",
            "\1\76\37\uffff\1\76",
            "\1\77\37\uffff\1\77",
            "\1\100\37\uffff\1\100",
            "\1\101\37\uffff\1\101",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\1\103\37\uffff\1\103",
            "\1\104\37\uffff\1\104",
            "\1\105\37\uffff\1\105",
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
            "\1\106\37\uffff\1\106",
            "\1\107\37\uffff\1\107",
            "\1\110\37\uffff\1\110",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\1\112\37\uffff\1\112",
            "",
            "\1\113\37\uffff\1\113",
            "\1\114\37\uffff\1\114",
            "\1\115\37\uffff\1\115",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\1\120\37\uffff\1\120",
            "",
            "\1\121\37\uffff\1\121",
            "\1\122\37\uffff\1\122",
            "\1\123\37\uffff\1\123",
            "\1\124\37\uffff\1\124",
            "",
            "",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\1\125\37\uffff\1\125",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\1\130\37\uffff\1\130",
            "\1\131\37\uffff\1\131",
            "",
            "",
            "\1\132\37\uffff\1\132",
            "\1\133\37\uffff\1\133",
            "\1\134\37\uffff\1\134",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "\12\13\7\uffff\32\13\4\uffff\1\13\1\uffff\32\13",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( CONSTANT | NUM | BOOL | IF | ELSE | WHILE | FOR | BREAK | CONTINUE | FUNCTION | ID | EQUAL | COMMA | DQUOTE | LEFT_P | RIGHT_P | MULT | DIV | PLUS | PLUS_PLUS | MINUS | MINUS_MINUS | CMP_LT | CMP_LT_EQ | CMP_GT | CMP_GT_EQ | CMP_EQ | CMP_NEQ | AND | OR | STRING_LITERAL | LEFT_CB | RIGHT_CB | LEFT_B | RIGHT_B | ARROW | NEWLINE | LINE_COMMENT | BLOCK_COMMENT | TP | PV | WS );";
        }
    }
 

}