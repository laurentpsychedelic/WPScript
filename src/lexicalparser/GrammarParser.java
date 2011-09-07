// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g 2011-09-07 18:12:33

package lexicalparser;

import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class GrammarParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEWLINE", "ID", "LEFT_P", "RIGHT_P", "COMMA", "INT", "EQUAL", "WS", "'+'", "'-'", "'*'"
    };
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


        public GrammarParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GrammarParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return GrammarParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g"; }


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


    public static class prog_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "prog"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:32:1: prog : ( stat )+ ;
    public final GrammarParser.prog_return prog() throws RecognitionException {
        GrammarParser.prog_return retval = new GrammarParser.prog_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.stat_return stat1 = null;



        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:32:5: ( ( stat )+ )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:32:9: ( stat )+
            {
            root_0 = (Object)adaptor.nil();

            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:32:9: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=NEWLINE && LA1_0<=LEFT_P)||LA1_0==INT) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:32:9: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog39);
            	    stat1=stat();

            	    state._fsp--;

            	    adaptor.addChild(root_0, stat1.getTree());

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

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "prog"

    public static class stat_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stat"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:34:1: stat : ( expr NEWLINE | ID '=' expr NEWLINE | simple_func NEWLINE | NEWLINE );
    public final GrammarParser.stat_return stat() throws RecognitionException {
        GrammarParser.stat_return retval = new GrammarParser.stat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NEWLINE3=null;
        Token ID4=null;
        Token char_literal5=null;
        Token NEWLINE7=null;
        Token NEWLINE9=null;
        Token NEWLINE10=null;
        GrammarParser.expr_return expr2 = null;

        GrammarParser.expr_return expr6 = null;

        GrammarParser.simple_func_return simple_func8 = null;


        Object NEWLINE3_tree=null;
        Object ID4_tree=null;
        Object char_literal5_tree=null;
        Object NEWLINE7_tree=null;
        Object NEWLINE9_tree=null;
        Object NEWLINE10_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:34:5: ( expr NEWLINE | ID '=' expr NEWLINE | simple_func NEWLINE | NEWLINE )
            int alt2=4;
            switch ( input.LA(1) ) {
            case LEFT_P:
            case INT:
                {
                alt2=1;
                }
                break;
            case ID:
                {
                switch ( input.LA(2) ) {
                case EQUAL:
                    {
                    alt2=2;
                    }
                    break;
                case LEFT_P:
                    {
                    alt2=3;
                    }
                    break;
                case NEWLINE:
                case 12:
                case 13:
                case 14:
                    {
                    alt2=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 2, input);

                    throw nvae;
                }

                }
                break;
            case NEWLINE:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:34:9: expr NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expr_in_stat66);
                    expr2=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr2.getTree());
                    NEWLINE3=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat68); 
                    NEWLINE3_tree = (Object)adaptor.create(NEWLINE3);
                    adaptor.addChild(root_0, NEWLINE3_tree);

                    System.out.println((expr2!=null?expr2.value:0));

                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:35:9: ID '=' expr NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    ID4=(Token)match(input,ID,FOLLOW_ID_in_stat80); 
                    ID4_tree = (Object)adaptor.create(ID4);
                    adaptor.addChild(root_0, ID4_tree);

                    char_literal5=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_stat82); 
                    char_literal5_tree = (Object)adaptor.create(char_literal5);
                    adaptor.addChild(root_0, char_literal5_tree);

                    pushFollow(FOLLOW_expr_in_stat84);
                    expr6=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr6.getTree());
                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat86); 
                    NEWLINE7_tree = (Object)adaptor.create(NEWLINE7);
                    adaptor.addChild(root_0, NEWLINE7_tree);

                    memory.put((ID4!=null?ID4.getText():null), new Integer((expr6!=null?expr6.value:0)));

                    }
                    break;
                case 3 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:37:9: simple_func NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_simple_func_in_stat106);
                    simple_func8=simple_func();

                    state._fsp--;

                    adaptor.addChild(root_0, simple_func8.getTree());
                    NEWLINE9=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat108); 
                    NEWLINE9_tree = (Object)adaptor.create(NEWLINE9);
                    adaptor.addChild(root_0, NEWLINE9_tree);


                            String func_name = (simple_func8!=null?simple_func8.func_name:null).split("[()]")[0];
                            String [] args = (simple_func8!=null?simple_func8.func_name:null).split("[()]")[1].split(",");
                            System.out.println("TRY CALLING [" + func_name + "]...");
                            FunctionCall.callFunction(func_name, args);
                        

                    }
                    break;
                case 4 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:44:9: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE10=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat125); 
                    NEWLINE10_tree = (Object)adaptor.create(NEWLINE10);
                    adaptor.addChild(root_0, NEWLINE10_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stat"

    public static class expr_return extends ParserRuleReturnScope {
        public int value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:47:1: expr returns [int value] : e= multExpr ( '+' e= multExpr | '-' e= multExpr )* ;
    public final GrammarParser.expr_return expr() throws RecognitionException {
        GrammarParser.expr_return retval = new GrammarParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal11=null;
        Token char_literal12=null;
        GrammarParser.multExpr_return e = null;


        Object char_literal11_tree=null;
        Object char_literal12_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:48:5: (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:48:9: e= multExpr ( '+' e= multExpr | '-' e= multExpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multExpr_in_expr150);
            e=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, e.getTree());
            retval.value = (e!=null?e.value:0);
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:49:9: ( '+' e= multExpr | '-' e= multExpr )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==12) ) {
                    alt3=1;
                }
                else if ( (LA3_0==13) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:49:13: '+' e= multExpr
            	    {
            	    char_literal11=(Token)match(input,12,FOLLOW_12_in_expr166); 
            	    char_literal11_tree = (Object)adaptor.create(char_literal11);
            	    adaptor.addChild(root_0, char_literal11_tree);

            	    pushFollow(FOLLOW_multExpr_in_expr170);
            	    e=multExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());
            	    retval.value += (e!=null?e.value:0);

            	    }
            	    break;
            	case 2 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:50:13: '-' e= multExpr
            	    {
            	    char_literal12=(Token)match(input,13,FOLLOW_13_in_expr186); 
            	    char_literal12_tree = (Object)adaptor.create(char_literal12);
            	    adaptor.addChild(root_0, char_literal12_tree);

            	    pushFollow(FOLLOW_multExpr_in_expr190);
            	    e=multExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());
            	    retval.value -= (e!=null?e.value:0);

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class multExpr_return extends ParserRuleReturnScope {
        public int value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multExpr"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:54:1: multExpr returns [int value] : e= atom ( '*' e= atom )* ;
    public final GrammarParser.multExpr_return multExpr() throws RecognitionException {
        GrammarParser.multExpr_return retval = new GrammarParser.multExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal13=null;
        GrammarParser.atom_return e = null;


        Object char_literal13_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:55:5: (e= atom ( '*' e= atom )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:55:9: e= atom ( '*' e= atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_multExpr228);
            e=atom();

            state._fsp--;

            adaptor.addChild(root_0, e.getTree());
            retval.value = (e!=null?e.value:0);
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:55:37: ( '*' e= atom )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==14) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:55:38: '*' e= atom
            	    {
            	    char_literal13=(Token)match(input,14,FOLLOW_14_in_multExpr233); 
            	    char_literal13_tree = (Object)adaptor.create(char_literal13);
            	    adaptor.addChild(root_0, char_literal13_tree);

            	    pushFollow(FOLLOW_atom_in_multExpr237);
            	    e=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());
            	    retval.value *= (e!=null?e.value:0);

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "multExpr"

    public static class simple_func_return extends ParserRuleReturnScope {
        public String func_name;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "simple_func"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:58:1: simple_func returns [String func_name] : ID LEFT_P args RIGHT_P ;
    public final GrammarParser.simple_func_return simple_func() throws RecognitionException {
        GrammarParser.simple_func_return retval = new GrammarParser.simple_func_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID14=null;
        Token LEFT_P15=null;
        Token RIGHT_P17=null;
        GrammarParser.args_return args16 = null;


        Object ID14_tree=null;
        Object LEFT_P15_tree=null;
        Object RIGHT_P17_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:58:39: ( ID LEFT_P args RIGHT_P )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:58:41: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID14=(Token)match(input,ID,FOLLOW_ID_in_simple_func258); 
            ID14_tree = (Object)adaptor.create(ID14);
            adaptor.addChild(root_0, ID14_tree);

            LEFT_P15=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_simple_func260); 
            LEFT_P15_tree = (Object)adaptor.create(LEFT_P15);
            adaptor.addChild(root_0, LEFT_P15_tree);

            pushFollow(FOLLOW_args_in_simple_func262);
            args16=args();

            state._fsp--;

            adaptor.addChild(root_0, args16.getTree());
            RIGHT_P17=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_simple_func264); 
            RIGHT_P17_tree = (Object)adaptor.create(RIGHT_P17);
            adaptor.addChild(root_0, RIGHT_P17_tree);

              
                    retval.func_name = (ID14!=null?ID14.getText():null) + "(" + (args16!=null?args16.params_list:null) + ")";
                

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "simple_func"

    public static class args_return extends ParserRuleReturnScope {
        public String params_list;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "args"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:63:1: args returns [String params_list] : a= ID ( COMMA b= args )* ;
    public final GrammarParser.args_return args() throws RecognitionException {
        GrammarParser.args_return retval = new GrammarParser.args_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token a=null;
        Token COMMA18=null;
        GrammarParser.args_return b = null;


        Object a_tree=null;
        Object COMMA18_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:63:34: (a= ID ( COMMA b= args )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:64:5: a= ID ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            a=(Token)match(input,ID,FOLLOW_ID_in_args287); 
            a_tree = (Object)adaptor.create(a);
            adaptor.addChild(root_0, a_tree);

            retval.params_list = (a!=null?a.getText():null);
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:64:36: ( COMMA b= args )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:64:37: COMMA b= args
            	    {
            	    COMMA18=(Token)match(input,COMMA,FOLLOW_COMMA_in_args292); 
            	    COMMA18_tree = (Object)adaptor.create(COMMA18);
            	    adaptor.addChild(root_0, COMMA18_tree);

            	    pushFollow(FOLLOW_args_in_args296);
            	    b=args();

            	    state._fsp--;

            	    adaptor.addChild(root_0, b.getTree());
            	    retval.params_list += ", " + (b!=null?b.params_list:null);

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "args"

    public static class atom_return extends ParserRuleReturnScope {
        public int value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:66:1: atom returns [int value] : ( INT | ID | '(' expr ')' );
    public final GrammarParser.atom_return atom() throws RecognitionException {
        GrammarParser.atom_return retval = new GrammarParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INT19=null;
        Token ID20=null;
        Token char_literal21=null;
        Token char_literal23=null;
        GrammarParser.expr_return expr22 = null;


        Object INT19_tree=null;
        Object ID20_tree=null;
        Object char_literal21_tree=null;
        Object char_literal23_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:67:5: ( INT | ID | '(' expr ')' )
            int alt6=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt6=1;
                }
                break;
            case ID:
                {
                alt6=2;
                }
                break;
            case LEFT_P:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:67:9: INT
                    {
                    root_0 = (Object)adaptor.nil();

                    INT19=(Token)match(input,INT,FOLLOW_INT_in_atom318); 
                    INT19_tree = (Object)adaptor.create(INT19);
                    adaptor.addChild(root_0, INT19_tree);

                    retval.value = Integer.parseInt((INT19!=null?INT19.getText():null));

                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:68:9: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID20=(Token)match(input,ID,FOLLOW_ID_in_atom330); 
                    ID20_tree = (Object)adaptor.create(ID20);
                    adaptor.addChild(root_0, ID20_tree);


                                Integer v = (Integer)memory.get((ID20!=null?ID20.getText():null));
                                if ( v!=null ) {
                                    retval.value = v.intValue();
                                } else {                
                                    System.err.println("undefined variable "+(ID20!=null?ID20.getText():null));
                                }
                            

                    }
                    break;
                case 3 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:77:9: '(' expr ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal21=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom350); 
                    char_literal21_tree = (Object)adaptor.create(char_literal21);
                    adaptor.addChild(root_0, char_literal21_tree);

                    pushFollow(FOLLOW_expr_in_atom352);
                    expr22=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr22.getTree());
                    char_literal23=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom354); 
                    char_literal23_tree = (Object)adaptor.create(char_literal23);
                    adaptor.addChild(root_0, char_literal23_tree);

                    retval.value = (expr22!=null?expr22.value:0);

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_stat_in_prog39 = new BitSet(new long[]{0x0000000000000272L});
    public static final BitSet FOLLOW_expr_in_stat66 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat80 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_stat82 = new BitSet(new long[]{0x0000000000000260L});
    public static final BitSet FOLLOW_expr_in_stat84 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_func_in_stat106 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_expr150 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_12_in_expr166 = new BitSet(new long[]{0x0000000000000260L});
    public static final BitSet FOLLOW_multExpr_in_expr170 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_13_in_expr186 = new BitSet(new long[]{0x0000000000000260L});
    public static final BitSet FOLLOW_multExpr_in_expr190 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_atom_in_multExpr228 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_14_in_multExpr233 = new BitSet(new long[]{0x0000000000000260L});
    public static final BitSet FOLLOW_atom_in_multExpr237 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_ID_in_simple_func258 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LEFT_P_in_simple_func260 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_args_in_simple_func262 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RIGHT_P_in_simple_func264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_args287 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_args292 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_args_in_args296 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_INT_in_atom318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom350 = new BitSet(new long[]{0x0000000000000260L});
    public static final BitSet FOLLOW_expr_in_atom352 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom354 = new BitSet(new long[]{0x0000000000000002L});

}