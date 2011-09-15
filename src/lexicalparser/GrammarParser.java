// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g 2011-09-15 17:20:47

package lexicalparser;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class GrammarParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEWLINE", "ID", "EQUAL", "PLUS", "MINUS", "MULT", "DIV", "LEFT_P", "RIGHT_P", "COMMA", "NUM", "BOOL", "DQUOTE", "IF", "LEFT_CB", "RIGHT_CB", "WS"
    };
    public static final int BOOL=15;
    public static final int RIGHT_P=12;
    public static final int DQUOTE=16;
    public static final int MINUS=8;
    public static final int MULT=9;
    public static final int ID=5;
    public static final int EOF=-1;
    public static final int LEFT_CB=18;
    public static final int NUM=14;
    public static final int IF=17;
    public static final int RIGHT_CB=19;
    public static final int WS=20;
    public static final int NEWLINE=4;
    public static final int COMMA=13;
    public static final int EQUAL=6;
    public static final int PLUS=7;
    public static final int DIV=10;
    public static final int LEFT_P=11;

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
        protected HashMap memory = new HashMap();
        protected HashMap compilation_memory =  new HashMap();
        private LinkedList <Object> commands = new LinkedList();
        protected int line_number = 1;

        private boolean _PANIC_STATE_ = false;
        private boolean _COMPILATION_ERROR_STATE_ = false;
        private boolean _RUNTIME_ERROR_STATE_ = false;

        public void dumpGlobalMemory(PrintStream ps) {
            ps.println("\nGLOBAL MEMORY DUMP");
            for (Object o : memory.keySet()) {
                Object val = memory.get(o);
                ps.println("VAR [" + o + "]->" + val);
            }
        }

        public void dumpScriptCommands() {
            System.out.println("\nSCRIPT COMMANDS DUMP");
            for (Object o : commands) {
                System.out.println("COMMAND->" + o);
            }
        }

        public boolean compilationCheck() {
            try {
                compilation_memory.clear();
                System.out.println("\nCOMPILATION CHECK");
                for (Object o : commands) {
                    if (!(o instanceof Expression)) {
                        _WPAScriptPanic("Command must be an instance of Expression");
                    }
                    ((Expression) o).compilationCheck();
                }
                compilation_memory.clear();
                return true;
            } catch (CompilationErrorException e) {
                _WPAScriptCompilationError(e.getMessage(), e.getLineNumber());
                return false;
            }
        }

        public Object execute() {
            Object ret_val = null;
            for (Object c : commands) {
                if (!(c instanceof Expression)) {
                    _WPAScriptPanic("Top level command must be instances of Expression!");
                }
                ret_val = ((Expression) c).eval();
            }
            return ret_val;
        }

        protected void _WPAScriptPanic(String message) {

            if (!_PANIC_STATE_) {
                _PANIC_STATE_ = true;
                System.err.println("PANIC OCCURED!");
            }System.err.println(message);

            dumpGlobalMemory(System.err);

            System.exit(0);
        }

        protected void _WPAScriptCompilationError(String message, int line_num) {
            if (!_COMPILATION_ERROR_STATE_) {
                _COMPILATION_ERROR_STATE_ = true;
                System.err.println("COMPILATION");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);
        }

        protected void _WPAScriptCompilationWarning(String message, int line_num) {
            if (!_COMPILATION_ERROR_STATE_) {
                _COMPILATION_ERROR_STATE_ = true;
                System.err.println("COMPILATION");
            }
            System.err.println("WARNING (l" + line_num + "):: " + message);
        }

        protected void _WPAScriptRuntimeError(String message, int line_num) {
            if (!_RUNTIME_ERROR_STATE_) {
                _RUNTIME_ERROR_STATE_ = true;
                System.err.println("RUNTIME");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);
            
            dumpGlobalMemory(System.err);

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
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "prog"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:130:1: prog : (s= stat )+ ;
    public final GrammarParser.prog_return prog() throws RecognitionException {
        GrammarParser.prog_return retval = new GrammarParser.prog_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.stat_return s = null;



        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:130:6: ( (s= stat )+ )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:130:9: (s= stat )+
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:130:9: (s= stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=NEWLINE && LA1_0<=ID)||LA1_0==LEFT_P||(LA1_0>=NUM && LA1_0<=DQUOTE)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:130:10: s= stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog58);
            	    s=stat();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            	    if ( state.backtracking==0 ) {
            	       commands.add((s!=null?s.expr:null)); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stat"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:132:1: stat returns [Expression expr] : ( expression NEWLINE | ID EQUAL expression NEWLINE | NEWLINE );
    public final GrammarParser.stat_return stat() throws RecognitionException {
        GrammarParser.stat_return retval = new GrammarParser.stat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NEWLINE2=null;
        Token ID3=null;
        Token EQUAL4=null;
        Token NEWLINE6=null;
        Token NEWLINE7=null;
        GrammarParser.expression_return expression1 = null;

        GrammarParser.expression_return expression5 = null;


        Object NEWLINE2_tree=null;
        Object ID3_tree=null;
        Object EQUAL4_tree=null;
        Object NEWLINE6_tree=null;
        Object NEWLINE7_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:133:5: ( expression NEWLINE | ID EQUAL expression NEWLINE | NEWLINE )
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

                if ( (LA2_2==NEWLINE||(LA2_2>=PLUS && LA2_2<=LEFT_P)) ) {
                    alt2=1;
                }
                else if ( (LA2_2==EQUAL) ) {
                    alt2=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
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
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:133:7: expression NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_stat79);
                    expression1=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression1.getTree());
                    NEWLINE2=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat81); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE2_tree = (Object)adaptor.create(NEWLINE2);
                    adaptor.addChild(root_0, NEWLINE2_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = (expression1!=null?expression1.expr:null);
                              line_number++;
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:137:7: ID EQUAL expression NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    ID3=(Token)match(input,ID,FOLLOW_ID_in_stat91); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID3_tree = (Object)adaptor.create(ID3);
                    adaptor.addChild(root_0, ID3_tree);
                    }
                    EQUAL4=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_stat93); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL4_tree = (Object)adaptor.create(EQUAL4);
                    adaptor.addChild(root_0, EQUAL4_tree);
                    }
                    pushFollow(FOLLOW_expression_in_stat95);
                    expression5=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression5.getTree());
                    NEWLINE6=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat97); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE6_tree = (Object)adaptor.create(NEWLINE6);
                    adaptor.addChild(root_0, NEWLINE6_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(this,  new VariableAssignment(this, (ID3!=null?ID3.getText():null), (expression5!=null?expression5.expr:null)) );
                              line_number++;
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:141:7: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat107); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE7_tree = (Object)adaptor.create(NEWLINE7);
                    adaptor.addChild(root_0, NEWLINE7_tree);
                    }
                    if ( state.backtracking==0 ) {

                              line_number++;
                          
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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

    public static class expression_return extends ParserRuleReturnScope {
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:146:1: expression returns [Expression expr] : ( terms | function_call );
    public final GrammarParser.expression_return expression() throws RecognitionException {
        GrammarParser.expression_return retval = new GrammarParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.terms_return terms8 = null;

        GrammarParser.function_call_return function_call9 = null;



        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:147:5: ( terms | function_call )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==LEFT_P||(LA3_0>=NUM && LA3_0<=DQUOTE)) ) {
                alt3=1;
            }
            else if ( (LA3_0==ID) ) {
                int LA3_2 = input.LA(2);

                if ( (LA3_2==LEFT_P) ) {
                    alt3=2;
                }
                else if ( (LA3_2==EOF||LA3_2==NEWLINE||(LA3_2>=PLUS && LA3_2<=DIV)||(LA3_2>=RIGHT_P && LA3_2<=COMMA)) ) {
                    alt3=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:147:7: terms
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_terms_in_expression130);
                    terms8=terms();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, terms8.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new Term(this, (terms8!=null?terms8.terms:null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:150:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_expression140);
                    function_call9=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call9.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new FunctionCall( this, (function_call9!=null?function_call9.name_params:null) ) );
                          
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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
    // $ANTLR end "expression"

    public static class terms_return extends ParserRuleReturnScope {
        public LinkedList<Object> terms;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "terms"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:154:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term )* ;
    public final GrammarParser.terms_return terms() throws RecognitionException {
        GrammarParser.terms_return retval = new GrammarParser.terms_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PLUS10=null;
        Token MINUS11=null;
        GrammarParser.term_return t = null;


        Object PLUS10_tree=null;
        Object MINUS11_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:155:5: (t= term ( PLUS t= term | MINUS t= term )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:155:7: t= term ( PLUS t= term | MINUS t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms160);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:158:7: ( PLUS t= term | MINUS t= term )*
            loop4:
            do {
                int alt4=3;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==PLUS) ) {
                    alt4=1;
                }
                else if ( (LA4_0==MINUS) ) {
                    alt4=2;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:158:9: PLUS t= term
            	    {
            	    PLUS10=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms166); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS10_tree = (Object)adaptor.create(PLUS10);
            	    adaptor.addChild(root_0, PLUS10_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms170);
            	    t=term();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            	    if ( state.backtracking==0 ) {

            	                  retval.terms.add(Operator.OPERATOR_PLUS);
            	                  retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
            	              
            	    }

            	    }
            	    break;
            	case 2 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:162:9: MINUS t= term
            	    {
            	    MINUS11=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms182); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS11_tree = (Object)adaptor.create(MINUS11);
            	    adaptor.addChild(root_0, MINUS11_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms186);
            	    t=term();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            	    if ( state.backtracking==0 ) {

            	                  retval.terms.add(Operator.OPERATOR_MINUS);
            	                  retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
            	              
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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
    // $ANTLR end "terms"

    public static class term_return extends ParserRuleReturnScope {
        public LinkedList<Object> atoms;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:168:1: term returns [LinkedList<Object> atoms] : a= atom ( MULT a= atom | DIV a= atom )* ;
    public final GrammarParser.term_return term() throws RecognitionException {
        GrammarParser.term_return retval = new GrammarParser.term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MULT12=null;
        Token DIV13=null;
        GrammarParser.atom_return a = null;


        Object MULT12_tree=null;
        Object DIV13_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:169:5: (a= atom ( MULT a= atom | DIV a= atom )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:169:7: a= atom ( MULT a= atom | DIV a= atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_term210);
            a=atom();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.atoms = new LinkedList();
                      retval.atoms.add((a!=null?a.value:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:172:7: ( MULT a= atom | DIV a= atom )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==MULT) ) {
                    alt5=1;
                }
                else if ( (LA5_0==DIV) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:172:9: MULT a= atom
            	    {
            	    MULT12=(Token)match(input,MULT,FOLLOW_MULT_in_term216); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MULT12_tree = (Object)adaptor.create(MULT12);
            	    adaptor.addChild(root_0, MULT12_tree);
            	    }
            	    pushFollow(FOLLOW_atom_in_term220);
            	    a=atom();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            	    if ( state.backtracking==0 ) {

            	                  retval.atoms.add(Operator.OPERATOR_MULT);
            	                  retval.atoms.add((a!=null?a.value:null));
            	              
            	    }

            	    }
            	    break;
            	case 2 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:176:9: DIV a= atom
            	    {
            	    DIV13=(Token)match(input,DIV,FOLLOW_DIV_in_term232); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DIV13_tree = (Object)adaptor.create(DIV13);
            	    adaptor.addChild(root_0, DIV13_tree);
            	    }
            	    pushFollow(FOLLOW_atom_in_term236);
            	    a=atom();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            	    if ( state.backtracking==0 ) {

            	                  retval.atoms.add(Operator.OPERATOR_DIV);
            	                  retval.atoms.add((a!=null?a.value:null));
            	              
            	    }

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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
    // $ANTLR end "term"

    public static class function_call_return extends ParserRuleReturnScope {
        public LinkedList<Object> name_params;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:183:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
    public final GrammarParser.function_call_return function_call() throws RecognitionException {
        GrammarParser.function_call_return retval = new GrammarParser.function_call_return();
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:183:55: ( ID LEFT_P args RIGHT_P )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:184:5: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID14=(Token)match(input,ID,FOLLOW_ID_in_function_call261); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID14_tree = (Object)adaptor.create(ID14);
            adaptor.addChild(root_0, ID14_tree);
            }
            LEFT_P15=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call263); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P15_tree = (Object)adaptor.create(LEFT_P15);
            adaptor.addChild(root_0, LEFT_P15_tree);
            }
            pushFollow(FOLLOW_args_in_function_call265);
            args16=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, args16.getTree());
            RIGHT_P17=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call267); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P17_tree = (Object)adaptor.create(RIGHT_P17);
            adaptor.addChild(root_0, RIGHT_P17_tree);
            }
            if ( state.backtracking==0 ) {
                
                      retval.name_params = (args16!=null?args16.params:null);
                      retval.name_params.add(0, (ID14!=null?ID14.getText():null));
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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
    // $ANTLR end "function_call"

    public static class args_return extends ParserRuleReturnScope {
        public LinkedList<Object> params;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "args"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:190:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final GrammarParser.args_return args() throws RecognitionException {
        GrammarParser.args_return retval = new GrammarParser.args_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA18=null;
        GrammarParser.expression_return a = null;

        GrammarParser.args_return b = null;


        Object COMMA18_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:190:41: (a= expression ( COMMA b= args )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:191:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args287);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:194:7: ( COMMA b= args )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    int LA6_2 = input.LA(2);

                    if ( (synpred9_Grammar()) ) {
                        alt6=1;
                    }


                }


                switch (alt6) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:194:8: COMMA b= args
            	    {
            	    COMMA18=(Token)match(input,COMMA,FOLLOW_COMMA_in_args292); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA18_tree = (Object)adaptor.create(COMMA18);
            	    adaptor.addChild(root_0, COMMA18_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args296);
            	    b=args();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
            	    if ( state.backtracking==0 ) {

            	              for (int k=0; k<retval.params.size(); k++) {
            	                  (b!=null?b.params:null).add(0, retval.params.get(k));
            	              }
            	              retval.params = (b!=null?b.params:null);
            	          
            	    }

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:203:1: atom returns [Object value] : ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal );
    public final GrammarParser.atom_return atom() throws RecognitionException {
        GrammarParser.atom_return retval = new GrammarParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM19=null;
        Token BOOL20=null;
        Token LEFT_P21=null;
        Token RIGHT_P23=null;
        Token ID24=null;
        GrammarParser.expression_return expression22 = null;

        GrammarParser.string_literal_return string_literal25 = null;


        Object NUM19_tree=null;
        Object BOOL20_tree=null;
        Object LEFT_P21_tree=null;
        Object RIGHT_P23_tree=null;
        Object ID24_tree=null;

        try {
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:204:5: ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal )
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
            case LEFT_P:
                {
                alt7=3;
                }
                break;
            case ID:
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
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:204:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM19=(Token)match(input,NUM,FOLLOW_NUM_in_atom319); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM19_tree = (Object)adaptor.create(NUM19);
                    adaptor.addChild(root_0, NUM19_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Float.parseFloat((NUM19!=null?NUM19.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:207:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL20=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom329); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL20_tree = (Object)adaptor.create(BOOL20);
                    adaptor.addChild(root_0, BOOL20_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL20!=null?BOOL20.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(this, true);
                              } else if ((BOOL20!=null?BOOL20.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(this, false);
                              } else {
                                  _WPAScriptPanic("Token [" + (BOOL20!=null?BOOL20.getText():null) + "] must be equal to \"true\" or \"false\" (boolean type)");
                              }
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:216:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P21=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom339); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P21_tree = (Object)adaptor.create(LEFT_P21);
                    adaptor.addChild(root_0, LEFT_P21_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom341);
                    expression22=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression22.getTree());
                    RIGHT_P23=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom343); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P23_tree = (Object)adaptor.create(RIGHT_P23);
                    adaptor.addChild(root_0, RIGHT_P23_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression22!=null?expression22.expr:null);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:219:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID24=(Token)match(input,ID,FOLLOW_ID_in_atom353); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID24_tree = (Object)adaptor.create(ID24);
                    adaptor.addChild(root_0, ID24_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID24!=null?ID24.getText():null));
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:222:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom363);
                    string_literal25=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal25.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal25!=null?string_literal25.value:null);
                          
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:233:1: string_literal returns [String value] : DQUOTE ID DQUOTE ;
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:233:39: ( DQUOTE ID DQUOTE )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:233:41: DQUOTE ID DQUOTE
            {
            root_0 = (Object)adaptor.nil();

            DQUOTE26=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal388); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DQUOTE26_tree = (Object)adaptor.create(DQUOTE26);
            adaptor.addChild(root_0, DQUOTE26_tree);
            }
            ID27=(Token)match(input,ID,FOLLOW_ID_in_string_literal390); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID27_tree = (Object)adaptor.create(ID27);
            adaptor.addChild(root_0, ID27_tree);
            }
            DQUOTE28=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal392); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DQUOTE28_tree = (Object)adaptor.create(DQUOTE28);
            adaptor.addChild(root_0, DQUOTE28_tree);
            }
            if ( state.backtracking==0 ) {

                      retval.value = (ID27!=null?ID27.getText():null);
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
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

    // $ANTLR start synpred9_Grammar
    public final void synpred9_Grammar_fragment() throws RecognitionException {   
        GrammarParser.args_return b = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:194:8: ( COMMA b= args )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:194:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred9_Grammar292); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred9_Grammar296);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Grammar

    // Delegated rules

    public final boolean synpred9_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_stat_in_prog58 = new BitSet(new long[]{0x000000000001C832L});
    public static final BitSet FOLLOW_expression_in_stat79 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat81 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat91 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_stat93 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_expression_in_stat95 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_terms_in_expression130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms160 = new BitSet(new long[]{0x0000000000000182L});
    public static final BitSet FOLLOW_PLUS_in_terms166 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_term_in_terms170 = new BitSet(new long[]{0x0000000000000182L});
    public static final BitSet FOLLOW_MINUS_in_terms182 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_term_in_terms186 = new BitSet(new long[]{0x0000000000000182L});
    public static final BitSet FOLLOW_atom_in_term210 = new BitSet(new long[]{0x0000000000000602L});
    public static final BitSet FOLLOW_MULT_in_term216 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_atom_in_term220 = new BitSet(new long[]{0x0000000000000602L});
    public static final BitSet FOLLOW_DIV_in_term232 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_atom_in_term236 = new BitSet(new long[]{0x0000000000000602L});
    public static final BitSet FOLLOW_ID_in_function_call261 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call263 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_args_in_function_call265 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args287 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_args292 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_args_in_args296 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_NUM_in_atom319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom339 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_expression_in_atom341 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal388 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ID_in_string_literal390 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred9_Grammar292 = new BitSet(new long[]{0x000000000001C820L});
    public static final BitSet FOLLOW_args_in_synpred9_Grammar296 = new BitSet(new long[]{0x0000000000000002L});

}