// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g 2011-09-11 20:47:31

package lexicalparser;
import java.util.LinkedList;
import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class GrammarParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEWLINE", "ID", "LEFT_P", "RIGHT_P", "COMMA", "NUM", "BOOL", "DQUOTE", "IF", "EQUAL", "LEFT_CB", "RIGHT_CB", "WS", "'+'", "'-'", "'*'", "'/'"
    };
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
    public String getGrammarFileName() { return "/home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g"; }


        /** Map variable name to Integer object holding value */
        HashMap memory = new HashMap();

        public void dumpGlobalMemory() {
            System.out.println("\nGLOBAL MEMORY DUMP");
            for (Object o : memory.keySet()) {
                Object val = memory.get(o);
                System.out.println("VAR [" + o + "]->" + val);
            }
        }

        private void _WPAScriptPanic(String message) {
            System.out.println(message);
            System.exit(0);
        }

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
        public Object value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "prog"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:46:1: prog returns [Object value] : (e= stat )+ ;
    public final GrammarParser.prog_return prog() throws RecognitionException {
        GrammarParser.prog_return retval = new GrammarParser.prog_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.stat_return e = null;



        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:47:5: ( (e= stat )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:47:8: (e= stat )+
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:47:8: (e= stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=NEWLINE && LA1_0<=LEFT_P)||(LA1_0>=NUM && LA1_0<=DQUOTE)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:47:9: e= stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog50);
            	    e=stat();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());

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


                    retval.value = (e!=null?e.value:null);
                

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
        public Object value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stat"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:51:1: stat returns [Object value] : ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE );
    public final GrammarParser.stat_return stat() throws RecognitionException {
        GrammarParser.stat_return retval = new GrammarParser.stat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NEWLINE2=null;
        Token ID3=null;
        Token char_literal4=null;
        Token NEWLINE6=null;
        Token NEWLINE7=null;
        GrammarParser.expr_return expr1 = null;

        GrammarParser.expr_return expr5 = null;


        Object NEWLINE2_tree=null;
        Object ID3_tree=null;
        Object char_literal4_tree=null;
        Object NEWLINE6_tree=null;
        Object NEWLINE7_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:52:5: ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE )
            int alt2=3;
            switch ( input.LA(1) ) {
            case LEFT_P:
            case NUM:
            case BOOL:
            case DQUOTE:
                {
                alt2=1;
                }
                break;
            case ID:
                {
                int LA2_2 = input.LA(2);

                if ( (LA2_2==NEWLINE||LA2_2==LEFT_P||(LA2_2>=17 && LA2_2<=20)) ) {
                    alt2=1;
                }
                else if ( (LA2_2==EQUAL) ) {
                    alt2=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 2, input);

                    throw nvae;
                }
                }
                break;
            case NEWLINE:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:52:9: expr NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expr_in_stat90);
                    expr1=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr1.getTree());
                    NEWLINE2=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat92); 
                    NEWLINE2_tree = (Object)adaptor.create(NEWLINE2);
                    adaptor.addChild(root_0, NEWLINE2_tree);


                                retval.value = (expr1!=null?expr1.value:null);
                            

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:55:9: ID '=' expr NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    ID3=(Token)match(input,ID,FOLLOW_ID_in_stat104); 
                    ID3_tree = (Object)adaptor.create(ID3);
                    adaptor.addChild(root_0, ID3_tree);

                    char_literal4=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_stat106); 
                    char_literal4_tree = (Object)adaptor.create(char_literal4);
                    adaptor.addChild(root_0, char_literal4_tree);

                    pushFollow(FOLLOW_expr_in_stat108);
                    expr5=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr5.getTree());
                    NEWLINE6=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat110); 
                    NEWLINE6_tree = (Object)adaptor.create(NEWLINE6);
                    adaptor.addChild(root_0, NEWLINE6_tree);


                                memory.put((ID3!=null?ID3.getText():null), (expr5!=null?expr5.value:null));
                                retval.value = (expr5!=null?expr5.value:null);
                            

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:60:9: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat130); 
                    NEWLINE7_tree = (Object)adaptor.create(NEWLINE7);
                    adaptor.addChild(root_0, NEWLINE7_tree);


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
        public Object value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:63:1: expr returns [Object value] : (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* | func_call );
    public final GrammarParser.expr_return expr() throws RecognitionException {
        GrammarParser.expr_return retval = new GrammarParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal8=null;
        Token char_literal9=null;
        GrammarParser.multExpr_return e = null;

        GrammarParser.func_call_return func_call10 = null;


        Object char_literal8_tree=null;
        Object char_literal9_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:64:5: (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* | func_call )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LEFT_P||(LA4_0>=NUM && LA4_0<=DQUOTE)) ) {
                alt4=1;
            }
            else if ( (LA4_0==ID) ) {
                int LA4_2 = input.LA(2);

                if ( (LA4_2==LEFT_P) ) {
                    alt4=2;
                }
                else if ( (LA4_2==NEWLINE||LA4_2==RIGHT_P||(LA4_2>=17 && LA4_2<=20)) ) {
                    alt4=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:64:9: e= multExpr ( '+' e= multExpr | '-' e= multExpr )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_multExpr_in_expr155);
                    e=multExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, e.getTree());
                    retval.value = (e!=null?e.value:null);
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:65:9: ( '+' e= multExpr | '-' e= multExpr )*
                    loop3:
                    do {
                        int alt3=3;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==17) ) {
                            alt3=1;
                        }
                        else if ( (LA3_0==18) ) {
                            alt3=2;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:65:13: '+' e= multExpr
                    	    {
                    	    char_literal8=(Token)match(input,17,FOLLOW_17_in_expr171); 
                    	    char_literal8_tree = (Object)adaptor.create(char_literal8);
                    	    adaptor.addChild(root_0, char_literal8_tree);

                    	    pushFollow(FOLLOW_multExpr_in_expr175);
                    	    e=multExpr();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, e.getTree());

                    	                if (retval.value instanceof Numeric && (e!=null?e.value:null) instanceof Numeric) {
                    	                    retval.value = new Numeric( ((Numeric) (retval.value)).value + ((Numeric) ((e!=null?e.value:null))).value );
                    	                } else {
                    	                    System.err.println("[+] is defined only between numeric types");
                    	                }
                    	            

                    	    }
                    	    break;
                    	case 2 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:72:10: '-' e= multExpr
                    	    {
                    	    char_literal9=(Token)match(input,18,FOLLOW_18_in_expr188); 
                    	    char_literal9_tree = (Object)adaptor.create(char_literal9);
                    	    adaptor.addChild(root_0, char_literal9_tree);

                    	    pushFollow(FOLLOW_multExpr_in_expr192);
                    	    e=multExpr();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, e.getTree());

                    	                if (retval.value instanceof Numeric && (e!=null?e.value:null) instanceof Numeric) {
                    	                    retval.value = new Numeric( ((Numeric) retval.value).value - ((Numeric) (e!=null?e.value:null)).value );
                    	                } else {
                    	                    System.err.println("[-] is defined only between numeric types");
                    	                }
                    	            

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:80:8: func_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_func_call_in_expr214);
                    func_call10=func_call();

                    state._fsp--;

                    adaptor.addChild(root_0, func_call10.getTree());

                                retval.value = (func_call10!=null?func_call10.result:null);
                            

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
    // $ANTLR end "expr"

    public static class multExpr_return extends ParserRuleReturnScope {
        public Object value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multExpr"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:86:1: multExpr returns [Object value] : e= atom ( '*' e= atom | '/' e= atom )* ;
    public final GrammarParser.multExpr_return multExpr() throws RecognitionException {
        GrammarParser.multExpr_return retval = new GrammarParser.multExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal11=null;
        Token char_literal12=null;
        GrammarParser.atom_return e = null;


        Object char_literal11_tree=null;
        Object char_literal12_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:87:5: (e= atom ( '*' e= atom | '/' e= atom )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:87:9: e= atom ( '*' e= atom | '/' e= atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_multExpr249);
            e=atom();

            state._fsp--;

            adaptor.addChild(root_0, e.getTree());

                            retval.value = (e!=null?e.value:null);       
                        
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:91:9: ( '*' e= atom | '/' e= atom )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==19) ) {
                    alt5=1;
                }
                else if ( (LA5_0==20) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:91:13: '*' e= atom
            	    {
            	    char_literal11=(Token)match(input,19,FOLLOW_19_in_multExpr277); 
            	    char_literal11_tree = (Object)adaptor.create(char_literal11);
            	    adaptor.addChild(root_0, char_literal11_tree);

            	    pushFollow(FOLLOW_atom_in_multExpr281);
            	    e=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());

            	                    if (retval.value instanceof Numeric && (e!=null?e.value:null) instanceof Numeric) {
            	                        retval.value = new Numeric( ((Numeric) retval.value).value * ((Numeric) (e!=null?e.value:null)).value );
            	                    } else {
            	                        System.err.println("[*] is defined only between numeric types");
            	                    }
            	                

            	    }
            	    break;
            	case 2 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:98:13: '/' e= atom
            	    {
            	    char_literal12=(Token)match(input,20,FOLLOW_20_in_multExpr297); 
            	    char_literal12_tree = (Object)adaptor.create(char_literal12);
            	    adaptor.addChild(root_0, char_literal12_tree);

            	    pushFollow(FOLLOW_atom_in_multExpr301);
            	    e=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());

            	                    if (retval.value instanceof Numeric && (e!=null?e.value:null) instanceof Numeric) {
            	                        retval.value = new Numeric( ((Numeric) retval.value).value / ((Numeric) (e!=null?e.value:null)).value );
            	                    } else {
            	                        System.err.println("[/] is defined only between numeric types");
            	                    }
            	                

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
    // $ANTLR end "multExpr"

    public static class func_call_return extends ParserRuleReturnScope {
        public Object result;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "func_call"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:109:1: func_call returns [Object result] : simple_func ;
    public final GrammarParser.func_call_return func_call() throws RecognitionException {
        GrammarParser.func_call_return retval = new GrammarParser.func_call_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.simple_func_return simple_func13 = null;



        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:109:34: ( simple_func )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:109:36: simple_func
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_simple_func_in_func_call331);
            simple_func13=simple_func();

            state._fsp--;

            adaptor.addChild(root_0, simple_func13.getTree());

                    retval.result = FunctionCall.callFunction((simple_func13!=null?simple_func13.name_params:null));
                

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
    // $ANTLR end "func_call"

    public static class simple_func_return extends ParserRuleReturnScope {
        public LinkedList<Object> name_params;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "simple_func"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:113:1: simple_func returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:113:53: ( ID LEFT_P args RIGHT_P )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:113:55: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID14=(Token)match(input,ID,FOLLOW_ID_in_simple_func344); 
            ID14_tree = (Object)adaptor.create(ID14);
            adaptor.addChild(root_0, ID14_tree);

            LEFT_P15=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_simple_func346); 
            LEFT_P15_tree = (Object)adaptor.create(LEFT_P15);
            adaptor.addChild(root_0, LEFT_P15_tree);

            pushFollow(FOLLOW_args_in_simple_func348);
            args16=args();

            state._fsp--;

            adaptor.addChild(root_0, args16.getTree());
            RIGHT_P17=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_simple_func350); 
            RIGHT_P17_tree = (Object)adaptor.create(RIGHT_P17);
            adaptor.addChild(root_0, RIGHT_P17_tree);

              
                    retval.name_params = (args16!=null?args16.params:null);
                    retval.name_params.add(0, (ID14!=null?ID14.getText():null));
                

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
        public LinkedList<Object> params;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "args"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:119:1: args returns [LinkedList<Object> params] : a= atom ( COMMA b= args )* ;
    public final GrammarParser.args_return args() throws RecognitionException {
        GrammarParser.args_return retval = new GrammarParser.args_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA18=null;
        GrammarParser.atom_return a = null;

        GrammarParser.args_return b = null;


        Object COMMA18_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:119:41: (a= atom ( COMMA b= args )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:120:5: a= atom ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_args373);
            a=atom();

            state._fsp--;

            adaptor.addChild(root_0, a.getTree());
            retval.params = new LinkedList(); retval.params.add((a!=null?a.value:null));
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:120:65: ( COMMA b= args )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:120:66: COMMA b= args
            	    {
            	    COMMA18=(Token)match(input,COMMA,FOLLOW_COMMA_in_args378); 
            	    COMMA18_tree = (Object)adaptor.create(COMMA18);
            	    adaptor.addChild(root_0, COMMA18_tree);

            	    pushFollow(FOLLOW_args_in_args382);
            	    b=args();

            	    state._fsp--;

            	    adaptor.addChild(root_0, b.getTree());

            	                for (int k=0; k<retval.params.size(); k++) {
            	                    (b!=null?b.params:null).add(0, retval.params.get(k));
            	                }
            	                retval.params = (b!=null?b.params:null);
            	               

            	    }
            	    break;

            	default :
            	    break loop6;
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
        public Object value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:129:1: atom returns [Object value] : ( NUM | BOOL | ID | '(' expr ')' | string_literal );
    public final GrammarParser.atom_return atom() throws RecognitionException {
        GrammarParser.atom_return retval = new GrammarParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM19=null;
        Token BOOL20=null;
        Token ID21=null;
        Token char_literal22=null;
        Token char_literal24=null;
        GrammarParser.expr_return expr23 = null;

        GrammarParser.string_literal_return string_literal25 = null;


        Object NUM19_tree=null;
        Object BOOL20_tree=null;
        Object ID21_tree=null;
        Object char_literal22_tree=null;
        Object char_literal24_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:130:5: ( NUM | BOOL | ID | '(' expr ')' | string_literal )
            int alt7=5;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt7=1;
                }
                break;
            case BOOL:
                {
                alt7=2;
                }
                break;
            case ID:
                {
                alt7=3;
                }
                break;
            case LEFT_P:
                {
                alt7=4;
                }
                break;
            case DQUOTE:
                {
                alt7=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:130:9: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM19=(Token)match(input,NUM,FOLLOW_NUM_in_atom417); 
                    NUM19_tree = (Object)adaptor.create(NUM19);
                    adaptor.addChild(root_0, NUM19_tree);

                    retval.value = new Numeric(Double.parseDouble((NUM19!=null?NUM19.getText():null)));

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:131:9: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL20=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom429); 
                    BOOL20_tree = (Object)adaptor.create(BOOL20);
                    adaptor.addChild(root_0, BOOL20_tree);


                                if ((BOOL20!=null?BOOL20.getText():null).equalsIgnoreCase("TRUE")) {
                                    retval.value = new Boolean(true);
                                } else if ((BOOL20!=null?BOOL20.getText():null).equalsIgnoreCase("FALSE")) {
                                    retval.value = new Boolean(false);
                                } else {
                                    _WPAScriptPanic("BOOL value: " + (BOOL20!=null?BOOL20.getText():null) + " invalid! BOOL type value must be TRUE or FALSE");
                                }
                            

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:140:9: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID21=(Token)match(input,ID,FOLLOW_ID_in_atom441); 
                    ID21_tree = (Object)adaptor.create(ID21);
                    adaptor.addChild(root_0, ID21_tree);


                                Object v = (Object)memory.get((ID21!=null?ID21.getText():null));
                                if ( v!=null ) {
                                    retval.value = v;
                                } else {                
                                    System.err.println("undefined variable "+(ID21!=null?ID21.getText():null));
                                }
                            

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:149:9: '(' expr ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal22=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom461); 
                    char_literal22_tree = (Object)adaptor.create(char_literal22);
                    adaptor.addChild(root_0, char_literal22_tree);

                    pushFollow(FOLLOW_expr_in_atom463);
                    expr23=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr23.getTree());
                    char_literal24=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom465); 
                    char_literal24_tree = (Object)adaptor.create(char_literal24);
                    adaptor.addChild(root_0, char_literal24_tree);

                    retval.value = (expr23!=null?expr23.value:null);

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:150:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom475);
                    string_literal25=string_literal();

                    state._fsp--;

                    adaptor.addChild(root_0, string_literal25.getTree());
                    retval.value = (string_literal25!=null?string_literal25.value:null);

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

    public static class string_literal_return extends ParserRuleReturnScope {
        public String value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "string_literal"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:153:1: string_literal returns [String value] : DQUOTE ID DQUOTE ;
    public final GrammarParser.string_literal_return string_literal() throws RecognitionException {
        GrammarParser.string_literal_return retval = new GrammarParser.string_literal_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DQUOTE26=null;
        Token ID27=null;
        Token DQUOTE28=null;

        Object DQUOTE26_tree=null;
        Object ID27_tree=null;
        Object DQUOTE28_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:153:38: ( DQUOTE ID DQUOTE )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:154:5: DQUOTE ID DQUOTE
            {
            root_0 = (Object)adaptor.nil();

            DQUOTE26=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal497); 
            DQUOTE26_tree = (Object)adaptor.create(DQUOTE26);
            adaptor.addChild(root_0, DQUOTE26_tree);

            ID27=(Token)match(input,ID,FOLLOW_ID_in_string_literal499); 
            ID27_tree = (Object)adaptor.create(ID27);
            adaptor.addChild(root_0, ID27_tree);

            DQUOTE28=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal501); 
            DQUOTE28_tree = (Object)adaptor.create(DQUOTE28);
            adaptor.addChild(root_0, DQUOTE28_tree);


                    retval.value = (ID27!=null?ID27.getText():null);
                

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
    // $ANTLR end "string_literal"

    // Delegated rules


 

    public static final BitSet FOLLOW_stat_in_prog50 = new BitSet(new long[]{0x0000000000000E72L});
    public static final BitSet FOLLOW_expr_in_stat90 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat92 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat104 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_EQUAL_in_stat106 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_expr_in_stat108 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_expr155 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_17_in_expr171 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_multExpr_in_expr175 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_18_in_expr188 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_multExpr_in_expr192 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_func_call_in_expr214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_multExpr249 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_19_in_multExpr277 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_atom_in_multExpr281 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_20_in_multExpr297 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_atom_in_multExpr301 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_simple_func_in_func_call331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_func344 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LEFT_P_in_simple_func346 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_args_in_simple_func348 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RIGHT_P_in_simple_func350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_args373 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_args378 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_args_in_args382 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_NUM_in_atom417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom461 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_expr_in_atom463 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal497 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_string_literal499 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal501 = new BitSet(new long[]{0x0000000000000002L});

}