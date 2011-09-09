// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g 2011-09-08 21:09:55

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEWLINE", "ID", "LEFT_P", "RIGHT_P", "COMMA", "INT", "DQUOTE", "EQUAL", "WS", "'+'", "'-'", "'*'"
    };
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:32:1: prog : ( stat )+ ;
    public final GrammarParser.prog_return prog() throws RecognitionException {
        GrammarParser.prog_return retval = new GrammarParser.prog_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.stat_return stat1 = null;



        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:32:5: ( ( stat )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:32:9: ( stat )+
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:32:9: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=NEWLINE && LA1_0<=LEFT_P)||(LA1_0>=INT && LA1_0<=DQUOTE)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:32:9: stat
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:34:1: stat : ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE );
    public final GrammarParser.stat_return stat() throws RecognitionException {
        GrammarParser.stat_return retval = new GrammarParser.stat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NEWLINE3=null;
        Token ID4=null;
        Token char_literal5=null;
        Token NEWLINE7=null;
        Token NEWLINE8=null;
        GrammarParser.expr_return expr2 = null;

        GrammarParser.expr_return expr6 = null;


        Object NEWLINE3_tree=null;
        Object ID4_tree=null;
        Object char_literal5_tree=null;
        Object NEWLINE7_tree=null;
        Object NEWLINE8_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:34:5: ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE )
            int alt2=3;
            switch ( input.LA(1) ) {
            case LEFT_P:
            case INT:
            case DQUOTE:
                {
                alt2=1;
                }
                break;
            case ID:
                {
                int LA2_2 = input.LA(2);

                if ( (LA2_2==NEWLINE||LA2_2==LEFT_P||(LA2_2>=13 && LA2_2<=15)) ) {
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
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:34:9: expr NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expr_in_stat66);
                    expr2=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr2.getTree());
                    NEWLINE3=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat68); 
                    NEWLINE3_tree = (Object)adaptor.create(NEWLINE3);
                    adaptor.addChild(root_0, NEWLINE3_tree);

                    System.out.println((expr2!=null?expr2.value:null));

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:35:9: ID '=' expr NEWLINE
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

                    memory.put((ID4!=null?ID4.getText():null), (expr6!=null?expr6.value:null));

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:37:9: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE8=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat106); 
                    NEWLINE8_tree = (Object)adaptor.create(NEWLINE8);
                    adaptor.addChild(root_0, NEWLINE8_tree);


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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:40:1: expr returns [Object value] : (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* | func_call );
    public final GrammarParser.expr_return expr() throws RecognitionException {
        GrammarParser.expr_return retval = new GrammarParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal9=null;
        Token char_literal10=null;
        GrammarParser.multExpr_return e = null;

        GrammarParser.func_call_return func_call11 = null;


        Object char_literal9_tree=null;
        Object char_literal10_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:41:5: (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* | func_call )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LEFT_P||(LA4_0>=INT && LA4_0<=DQUOTE)) ) {
                alt4=1;
            }
            else if ( (LA4_0==ID) ) {
                int LA4_2 = input.LA(2);

                if ( (LA4_2==LEFT_P) ) {
                    alt4=2;
                }
                else if ( (LA4_2==NEWLINE||LA4_2==RIGHT_P||(LA4_2>=13 && LA4_2<=15)) ) {
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
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:41:9: e= multExpr ( '+' e= multExpr | '-' e= multExpr )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_multExpr_in_expr131);
                    e=multExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, e.getTree());
                    retval.value = (e!=null?e.value:null);
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:42:9: ( '+' e= multExpr | '-' e= multExpr )*
                    loop3:
                    do {
                        int alt3=3;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==13) ) {
                            alt3=1;
                        }
                        else if ( (LA3_0==14) ) {
                            alt3=2;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:42:13: '+' e= multExpr
                    	    {
                    	    char_literal9=(Token)match(input,13,FOLLOW_13_in_expr147); 
                    	    char_literal9_tree = (Object)adaptor.create(char_literal9);
                    	    adaptor.addChild(root_0, char_literal9_tree);

                    	    pushFollow(FOLLOW_multExpr_in_expr151);
                    	    e=multExpr();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, e.getTree());

                    	                if (retval.value instanceof Double && (e!=null?e.value:null) instanceof Double) {
                    	                    retval.value = (Double) retval.value + (Double) (e!=null?e.value:null);
                    	                } else {
                    	                    System.err.println("[+] is defined only between numbers");
                    	                }
                    	            

                    	    }
                    	    break;
                    	case 2 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:49:13: '-' e= multExpr
                    	    {
                    	    char_literal10=(Token)match(input,14,FOLLOW_14_in_expr167); 
                    	    char_literal10_tree = (Object)adaptor.create(char_literal10);
                    	    adaptor.addChild(root_0, char_literal10_tree);

                    	    pushFollow(FOLLOW_multExpr_in_expr171);
                    	    e=multExpr();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, e.getTree());

                    	                if (retval.value instanceof Double && (e!=null?e.value:null) instanceof Double) {
                    	                    retval.value = (Double) retval.value - (Double) (e!=null?e.value:null);
                    	                } else {
                    	                    System.err.println("[-] is defined only between numbers");
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
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:57:11: func_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_func_call_in_expr196);
                    func_call11=func_call();

                    state._fsp--;

                    adaptor.addChild(root_0, func_call11.getTree());

                                retval.value = (func_call11!=null?func_call11.result:null);
                                //System.out.println((func_call11!=null?func_call11.result:null));
                            

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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:64:1: multExpr returns [Object value] : e= atom ( '*' e= atom )* ;
    public final GrammarParser.multExpr_return multExpr() throws RecognitionException {
        GrammarParser.multExpr_return retval = new GrammarParser.multExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal12=null;
        GrammarParser.atom_return e = null;


        Object char_literal12_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:65:5: (e= atom ( '*' e= atom )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:65:9: e= atom ( '*' e= atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_multExpr231);
            e=atom();

            state._fsp--;

            adaptor.addChild(root_0, e.getTree());
            retval.value = (e!=null?e.value:null);
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:65:37: ( '*' e= atom )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==15) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:65:38: '*' e= atom
            	    {
            	    char_literal12=(Token)match(input,15,FOLLOW_15_in_multExpr236); 
            	    char_literal12_tree = (Object)adaptor.create(char_literal12);
            	    adaptor.addChild(root_0, char_literal12_tree);

            	    pushFollow(FOLLOW_atom_in_multExpr240);
            	    e=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());

            	                if (retval.value instanceof Double && (e!=null?e.value:null) instanceof Double) {
            	                    retval.value = (Double) retval.value * (Double) (e!=null?e.value:null);
            	                } else {
            	                    System.err.println("[*] is defined only between numbers");
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:74:1: func_call returns [Object result] : simple_func ;
    public final GrammarParser.func_call_return func_call() throws RecognitionException {
        GrammarParser.func_call_return retval = new GrammarParser.func_call_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.simple_func_return simple_func13 = null;



        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:74:34: ( simple_func )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:74:36: simple_func
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_simple_func_in_func_call264);
            simple_func13=simple_func();

            state._fsp--;

            adaptor.addChild(root_0, simple_func13.getTree());

                    System.out.print("Function call: " + (simple_func13!=null?simple_func13.name_params:null).get(0) + "(");
                    for (int k=1; k<(simple_func13!=null?simple_func13.name_params:null).size(); k++) {
                        System.out.print((simple_func13!=null?simple_func13.name_params:null).get(k));
                        if (k<(simple_func13!=null?simple_func13.name_params:null).size()-1) {
                            System.out.print(",");
                        } else {
                            System.out.println(")");
                        }
                    }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:87:1: simple_func returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:87:53: ( ID LEFT_P args RIGHT_P )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:87:55: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID14=(Token)match(input,ID,FOLLOW_ID_in_simple_func277); 
            ID14_tree = (Object)adaptor.create(ID14);
            adaptor.addChild(root_0, ID14_tree);

            LEFT_P15=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_simple_func279); 
            LEFT_P15_tree = (Object)adaptor.create(LEFT_P15);
            adaptor.addChild(root_0, LEFT_P15_tree);

            pushFollow(FOLLOW_args_in_simple_func281);
            args16=args();

            state._fsp--;

            adaptor.addChild(root_0, args16.getTree());
            RIGHT_P17=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_simple_func283); 
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:93:1: args returns [LinkedList<Object> params] : a= atom ( COMMA b= args )* ;
    public final GrammarParser.args_return args() throws RecognitionException {
        GrammarParser.args_return retval = new GrammarParser.args_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA18=null;
        GrammarParser.atom_return a = null;

        GrammarParser.args_return b = null;


        Object COMMA18_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:93:41: (a= atom ( COMMA b= args )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:94:5: a= atom ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_args306);
            a=atom();

            state._fsp--;

            adaptor.addChild(root_0, a.getTree());
            retval.params = new LinkedList(); retval.params.add((a!=null?a.value:null));
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:94:65: ( COMMA b= args )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:94:66: COMMA b= args
            	    {
            	    COMMA18=(Token)match(input,COMMA,FOLLOW_COMMA_in_args311); 
            	    COMMA18_tree = (Object)adaptor.create(COMMA18);
            	    adaptor.addChild(root_0, COMMA18_tree);

            	    pushFollow(FOLLOW_args_in_args315);
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:102:1: atom returns [Object value] : ( INT | ID | '(' expr ')' | string_literal );
    public final GrammarParser.atom_return atom() throws RecognitionException {
        GrammarParser.atom_return retval = new GrammarParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INT19=null;
        Token ID20=null;
        Token char_literal21=null;
        Token char_literal23=null;
        GrammarParser.expr_return expr22 = null;

        GrammarParser.string_literal_return string_literal24 = null;


        Object INT19_tree=null;
        Object ID20_tree=null;
        Object char_literal21_tree=null;
        Object char_literal23_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:103:5: ( INT | ID | '(' expr ')' | string_literal )
            int alt7=4;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt7=1;
                }
                break;
            case ID:
                {
                alt7=2;
                }
                break;
            case LEFT_P:
                {
                alt7=3;
                }
                break;
            case DQUOTE:
                {
                alt7=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:103:9: INT
                    {
                    root_0 = (Object)adaptor.nil();

                    INT19=(Token)match(input,INT,FOLLOW_INT_in_atom349); 
                    INT19_tree = (Object)adaptor.create(INT19);
                    adaptor.addChild(root_0, INT19_tree);

                    retval.value = new Double(Double.parseDouble((INT19!=null?INT19.getText():null)));

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:104:9: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID20=(Token)match(input,ID,FOLLOW_ID_in_atom361); 
                    ID20_tree = (Object)adaptor.create(ID20);
                    adaptor.addChild(root_0, ID20_tree);


                                Object v = (Object)memory.get((ID20!=null?ID20.getText():null));
                                if ( v!=null ) {
                                    retval.value = v;
                                } else {                
                                    System.err.println("undefined variable "+(ID20!=null?ID20.getText():null));
                                }
                            

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:113:9: '(' expr ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal21=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom381); 
                    char_literal21_tree = (Object)adaptor.create(char_literal21);
                    adaptor.addChild(root_0, char_literal21_tree);

                    pushFollow(FOLLOW_expr_in_atom383);
                    expr22=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr22.getTree());
                    char_literal23=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom385); 
                    char_literal23_tree = (Object)adaptor.create(char_literal23);
                    adaptor.addChild(root_0, char_literal23_tree);

                    retval.value = (expr22!=null?expr22.value:null);

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:114:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom395);
                    string_literal24=string_literal();

                    state._fsp--;

                    adaptor.addChild(root_0, string_literal24.getTree());
                    retval.value = (string_literal24!=null?string_literal24.value:null);

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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:117:1: string_literal returns [String value] : DQUOTE ID DQUOTE ;
    public final GrammarParser.string_literal_return string_literal() throws RecognitionException {
        GrammarParser.string_literal_return retval = new GrammarParser.string_literal_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DQUOTE25=null;
        Token ID26=null;
        Token DQUOTE27=null;

        Object DQUOTE25_tree=null;
        Object ID26_tree=null;
        Object DQUOTE27_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:117:38: ( DQUOTE ID DQUOTE )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:118:5: DQUOTE ID DQUOTE
            {
            root_0 = (Object)adaptor.nil();

            DQUOTE25=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal417); 
            DQUOTE25_tree = (Object)adaptor.create(DQUOTE25);
            adaptor.addChild(root_0, DQUOTE25_tree);

            ID26=(Token)match(input,ID,FOLLOW_ID_in_string_literal419); 
            ID26_tree = (Object)adaptor.create(ID26);
            adaptor.addChild(root_0, ID26_tree);

            DQUOTE27=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal421); 
            DQUOTE27_tree = (Object)adaptor.create(DQUOTE27);
            adaptor.addChild(root_0, DQUOTE27_tree);


                    retval.value = (ID26!=null?ID26.getText():null);
                

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


 

    public static final BitSet FOLLOW_stat_in_prog39 = new BitSet(new long[]{0x0000000000000672L});
    public static final BitSet FOLLOW_expr_in_stat66 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat80 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_stat82 = new BitSet(new long[]{0x0000000000000660L});
    public static final BitSet FOLLOW_expr_in_stat84 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_expr131 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_13_in_expr147 = new BitSet(new long[]{0x0000000000000660L});
    public static final BitSet FOLLOW_multExpr_in_expr151 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_14_in_expr167 = new BitSet(new long[]{0x0000000000000660L});
    public static final BitSet FOLLOW_multExpr_in_expr171 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_func_call_in_expr196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_multExpr231 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_multExpr236 = new BitSet(new long[]{0x0000000000000660L});
    public static final BitSet FOLLOW_atom_in_multExpr240 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_simple_func_in_func_call264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_simple_func277 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LEFT_P_in_simple_func279 = new BitSet(new long[]{0x0000000000000660L});
    public static final BitSet FOLLOW_args_in_simple_func281 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RIGHT_P_in_simple_func283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_args306 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_args311 = new BitSet(new long[]{0x0000000000000660L});
    public static final BitSet FOLLOW_args_in_args315 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_INT_in_atom349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom381 = new BitSet(new long[]{0x0000000000000660L});
    public static final BitSet FOLLOW_expr_in_atom383 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal417 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_string_literal419 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal421 = new BitSet(new long[]{0x0000000000000002L});

}