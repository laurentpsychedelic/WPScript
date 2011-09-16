// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g 2011-09-16 18:21:32

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "ID", "EQUAL", "IF", "LEFT_P", "RIGHT_P", "ELSE", "PLUS", "MINUS", "MULT", "DIV", "COMMA", "NUM", "BOOL", "DQUOTE", "WS"
    };
    public static final int ELSE=12;
    public static final int BOOL=19;
    public static final int RIGHT_P=11;
    public static final int DQUOTE=20;
    public static final int MINUS=14;
    public static final int MULT=15;
    public static final int ID=7;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int NUM=18;
    public static final int RIGHT_CB=6;
    public static final int IF=9;
    public static final int WS=21;
    public static final int NEWLINE=5;
    public static final int COMMA=17;
    public static final int EQUAL=8;
    public static final int PLUS=13;
    public static final int DIV=16;
    public static final int LEFT_P=10;

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
        protected HashMap memory = new HashMap();
        protected HashMap compilation_memory =  new HashMap();
        private LinkedList <Expression> commands = new LinkedList();
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
                        _WPAScriptPanic("Command must be an instance of Expression [" + o.getClass() + "]");
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
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:130:1: prog : s= stats ;
    public final GrammarParser.prog_return prog() throws RecognitionException {
        GrammarParser.prog_return retval = new GrammarParser.prog_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.stats_return s = null;



        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:130:6: (s= stats )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:131:5: s= stats
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_stats_in_prog60);
            s=stats();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      commands.clear();
                      for (Expression e : (s!=null?s.expressions:null)) {
                          commands.add(e);
                      }
                  
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
    // $ANTLR end "prog"

    public static class block_return extends ParserRuleReturnScope {
        public LinkedList<Expression> expressions;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "block"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:138:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
    public final GrammarParser.block_return block() throws RecognitionException {
        GrammarParser.block_return retval = new GrammarParser.block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LEFT_CB1=null;
        Token NEWLINE2=null;
        Token NEWLINE4=null;
        Token RIGHT_CB5=null;
        GrammarParser.stats_return stats3 = null;


        Object LEFT_CB1_tree=null;
        Object NEWLINE2_tree=null;
        Object NEWLINE4_tree=null;
        Object RIGHT_CB5_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:138:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:139:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block77); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:139:13: ( NEWLINE )?
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE2=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_block79); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE2_tree = (Object)adaptor.create(NEWLINE2);
                    adaptor.addChild(root_0, NEWLINE2_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stats_in_block82);
            stats3=stats();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, stats3.getTree());
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:139:28: ( NEWLINE )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NEWLINE) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE4=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_block84); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE4_tree = (Object)adaptor.create(NEWLINE4);
                    adaptor.addChild(root_0, NEWLINE4_tree);
                    }

                    }
                    break;

            }

            RIGHT_CB5=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_block87); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB5_tree = (Object)adaptor.create(RIGHT_CB5);
            adaptor.addChild(root_0, RIGHT_CB5_tree);
            }
            if ( state.backtracking==0 ) {

                      retval.expressions = (stats3!=null?stats3.expressions:null);
                  
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
    // $ANTLR end "block"

    public static class stats_return extends ParserRuleReturnScope {
        public LinkedList<Expression> expressions;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stats"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:143:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final GrammarParser.stats_return stats() throws RecognitionException {
        GrammarParser.stats_return retval = new GrammarParser.stats_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.stat_return s = null;



        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:143:51: (s= stat (s= stat )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:144:5: s= stat (s= stat )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_stat_in_stats106);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.expressions = new LinkedList();
                      if ((s!=null?s.expr:null)!=null) {
                          retval.expressions.add((s!=null?s.expr:null));
                      }
                  
            }
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:149:7: (s= stat )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==NEWLINE) ) {
                    int LA3_2 = input.LA(2);

                    if ( (synpred3_Grammar()) ) {
                        alt3=1;
                    }


                }
                else if ( (LA3_0==LEFT_CB||LA3_0==ID||(LA3_0>=IF && LA3_0<=LEFT_P)||(LA3_0>=NUM && LA3_0<=DQUOTE)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:149:8: s= stat
            	    {
            	    pushFollow(FOLLOW_stat_in_stats113);
            	    s=stat();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            	    if ( state.backtracking==0 ) {

            	              if ((s!=null?s.expr:null)!=null) {
            	                  retval.expressions.add((s!=null?s.expr:null));
            	              }
            	          
            	    }

            	    }
            	    break;

            	default :
            	    break loop3;
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
    // $ANTLR end "stats"

    public static class stat_return extends ParserRuleReturnScope {
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stat"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:156:1: stat returns [Expression expr] : ( expression NEWLINE | ID EQUAL expression NEWLINE | NEWLINE | block | if_expression );
    public final GrammarParser.stat_return stat() throws RecognitionException {
        GrammarParser.stat_return retval = new GrammarParser.stat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NEWLINE7=null;
        Token ID8=null;
        Token EQUAL9=null;
        Token NEWLINE11=null;
        Token NEWLINE12=null;
        GrammarParser.expression_return expression6 = null;

        GrammarParser.expression_return expression10 = null;

        GrammarParser.block_return block13 = null;

        GrammarParser.if_expression_return if_expression14 = null;


        Object NEWLINE7_tree=null;
        Object ID8_tree=null;
        Object EQUAL9_tree=null;
        Object NEWLINE11_tree=null;
        Object NEWLINE12_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:157:5: ( expression NEWLINE | ID EQUAL expression NEWLINE | NEWLINE | block | if_expression )
            int alt4=5;
            switch ( input.LA(1) ) {
            case LEFT_P:
            case NUM:
            case BOOL:
            case DQUOTE:
                {
                alt4=1;
                }
                break;
            case ID:
                {
                int LA4_2 = input.LA(2);

                if ( (LA4_2==NEWLINE||LA4_2==LEFT_P||(LA4_2>=PLUS && LA4_2<=DIV)) ) {
                    alt4=1;
                }
                else if ( (LA4_2==EQUAL) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }
                }
                break;
            case NEWLINE:
                {
                alt4=3;
                }
                break;
            case LEFT_CB:
                {
                alt4=4;
                }
                break;
            case IF:
                {
                alt4=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:157:7: expression NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_stat138);
                    expression6=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression6.getTree());
                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat140); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE7_tree = (Object)adaptor.create(NEWLINE7);
                    adaptor.addChild(root_0, NEWLINE7_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = (expression6!=null?expression6.expr:null);
                              line_number++;
                          
                    }

                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:161:7: ID EQUAL expression NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    ID8=(Token)match(input,ID,FOLLOW_ID_in_stat150); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID8_tree = (Object)adaptor.create(ID8);
                    adaptor.addChild(root_0, ID8_tree);
                    }
                    EQUAL9=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_stat152); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL9_tree = (Object)adaptor.create(EQUAL9);
                    adaptor.addChild(root_0, EQUAL9_tree);
                    }
                    pushFollow(FOLLOW_expression_in_stat154);
                    expression10=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression10.getTree());
                    NEWLINE11=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat156); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE11_tree = (Object)adaptor.create(NEWLINE11);
                    adaptor.addChild(root_0, NEWLINE11_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(this,  new VariableAssignment(this, (ID8!=null?ID8.getText():null), (expression10!=null?expression10.expr:null)) );
                              line_number++;
                          
                    }

                    }
                    break;
                case 3 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:165:7: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE12=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat166); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE12_tree = (Object)adaptor.create(NEWLINE12);
                    adaptor.addChild(root_0, NEWLINE12_tree);
                    }
                    if ( state.backtracking==0 ) {

                              line_number++;
                          
                    }

                    }
                    break;
                case 4 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:168:7: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_stat176);
                    block13=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block13.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(this, (block13!=null?block13.expressions:null));
                          
                    }

                    }
                    break;
                case 5 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:171:7: if_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_if_expression_in_stat186);
                    if_expression14=if_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_expression14.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(this, (if_expression14!=null?if_expression14.expr:null));
                          
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

    public static class if_expression_return extends ParserRuleReturnScope {
        public IfExpression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_expression"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:176:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final GrammarParser.if_expression_return if_expression() throws RecognitionException {
        GrammarParser.if_expression_return retval = new GrammarParser.if_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.pre_if_expression_return p = null;



        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:177:5: (p= pre_if_expression )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:177:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression211);
            p=pre_if_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, p.getTree());
            if ( state.backtracking==0 ) {

                      Expression condition = null;
                      Expression expr_if = null;
                      Expression expr_else = null;
                      if (0 < (p!=null?p.exprs:null).size()) {
                          condition = (p!=null?p.exprs:null).get(0);
                      }
                      if (1 < (p!=null?p.exprs:null).size()) {
                          expr_if = (p!=null?p.exprs:null).get(1);
                      }
                      if (2 < (p!=null?p.exprs:null).size()) {
                          expr_else = (p!=null?p.exprs:null).get(2);
                      }
                      retval.expr = new IfExpression( this, condition, expr_if, expr_else );
                  
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
    // $ANTLR end "if_expression"

    public static class pre_if_expression_return extends ParserRuleReturnScope {
        public LinkedList<Expression> exprs;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pre_if_expression"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:193:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final GrammarParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        GrammarParser.pre_if_expression_return retval = new GrammarParser.pre_if_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IF15=null;
        Token LEFT_P16=null;
        Token RIGHT_P17=null;
        Token NEWLINE18=null;
        Token NEWLINE19=null;
        Token ELSE20=null;
        Token NEWLINE21=null;
        GrammarParser.expression_return e = null;

        GrammarParser.stat_return s = null;


        Object IF15_tree=null;
        Object LEFT_P16_tree=null;
        Object RIGHT_P17_tree=null;
        Object NEWLINE18_tree=null;
        Object NEWLINE19_tree=null;
        Object ELSE20_tree=null;
        Object NEWLINE21_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:194:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:194:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF15=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression230); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF15_tree = (Object)adaptor.create(IF15);
            adaptor.addChild(root_0, IF15_tree);
            }
            LEFT_P16=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression232); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P16_tree = (Object)adaptor.create(LEFT_P16);
            adaptor.addChild(root_0, LEFT_P16_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression236);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P17=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression238); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P17_tree = (Object)adaptor.create(RIGHT_P17);
            adaptor.addChild(root_0, RIGHT_P17_tree);
            }
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:194:38: ( NEWLINE )?
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE18=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression240); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE18_tree = (Object)adaptor.create(NEWLINE18);
                    adaptor.addChild(root_0, NEWLINE18_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_if_expression245);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:7: ( NEWLINE )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==NEWLINE) ) {
                int LA6_1 = input.LA(2);

                if ( (synpred9_Grammar()) ) {
                    alt6=1;
                }
            }
            switch (alt6) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE19=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression249); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE19_tree = (Object)adaptor.create(NEWLINE19);
                    adaptor.addChild(root_0, NEWLINE19_tree);
                    }

                    }
                    break;

            }

            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ELSE) ) {
                int LA8_1 = input.LA(2);

                if ( (synpred11_Grammar()) ) {
                    alt8=1;
                }
            }
            switch (alt8) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE20=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression253); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE20_tree = (Object)adaptor.create(ELSE20);
                    adaptor.addChild(root_0, ELSE20_tree);
                    }
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:22: ( NEWLINE )?
                    int alt7=2;
                    alt7 = dfa7.predict(input);
                    switch (alt7) {
                        case 1 :
                            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:0:0: NEWLINE
                            {
                            NEWLINE21=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression255); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE21_tree = (Object)adaptor.create(NEWLINE21);
                            adaptor.addChild(root_0, NEWLINE21_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression260);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    if ( state.backtracking==0 ) {

                              retval.exprs.add( (s!=null?s.expr:null) );
                          
                    }

                    }
                    break;

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
    // $ANTLR end "pre_if_expression"

    public static class expression_return extends ParserRuleReturnScope {
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:202:1: expression returns [Expression expr] : ( terms | function_call );
    public final GrammarParser.expression_return expression() throws RecognitionException {
        GrammarParser.expression_return retval = new GrammarParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        GrammarParser.terms_return terms22 = null;

        GrammarParser.function_call_return function_call23 = null;



        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:203:5: ( terms | function_call )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==LEFT_P||(LA9_0>=NUM && LA9_0<=DQUOTE)) ) {
                alt9=1;
            }
            else if ( (LA9_0==ID) ) {
                int LA9_2 = input.LA(2);

                if ( (LA9_2==LEFT_P) ) {
                    alt9=2;
                }
                else if ( (LA9_2==EOF||LA9_2==NEWLINE||LA9_2==RIGHT_P||(LA9_2>=PLUS && LA9_2<=COMMA)) ) {
                    alt9=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:203:7: terms
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_terms_in_expression280);
                    terms22=terms();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, terms22.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new Term(this, (terms22!=null?terms22.terms:null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:206:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_expression290);
                    function_call23=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call23.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new FunctionCall( this, (function_call23!=null?function_call23.name_params:null) ) );
                          
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
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:210:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term )* ;
    public final GrammarParser.terms_return terms() throws RecognitionException {
        GrammarParser.terms_return retval = new GrammarParser.terms_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PLUS24=null;
        Token MINUS25=null;
        GrammarParser.term_return t = null;


        Object PLUS24_tree=null;
        Object MINUS25_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:211:5: (t= term ( PLUS t= term | MINUS t= term )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:211:7: t= term ( PLUS t= term | MINUS t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms310);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:214:7: ( PLUS t= term | MINUS t= term )*
            loop10:
            do {
                int alt10=3;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==PLUS) ) {
                    alt10=1;
                }
                else if ( (LA10_0==MINUS) ) {
                    alt10=2;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:214:9: PLUS t= term
            	    {
            	    PLUS24=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms316); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS24_tree = (Object)adaptor.create(PLUS24);
            	    adaptor.addChild(root_0, PLUS24_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms320);
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
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:218:9: MINUS t= term
            	    {
            	    MINUS25=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms332); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS25_tree = (Object)adaptor.create(MINUS25);
            	    adaptor.addChild(root_0, MINUS25_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms336);
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
            	    break loop10;
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
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:224:1: term returns [LinkedList<Object> atoms] : a= atom ( MULT a= atom | DIV a= atom )* ;
    public final GrammarParser.term_return term() throws RecognitionException {
        GrammarParser.term_return retval = new GrammarParser.term_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MULT26=null;
        Token DIV27=null;
        GrammarParser.atom_return a = null;


        Object MULT26_tree=null;
        Object DIV27_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:225:5: (a= atom ( MULT a= atom | DIV a= atom )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:225:7: a= atom ( MULT a= atom | DIV a= atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_term360);
            a=atom();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.atoms = new LinkedList();
                      retval.atoms.add((a!=null?a.value:null));
                  
            }
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:228:7: ( MULT a= atom | DIV a= atom )*
            loop11:
            do {
                int alt11=3;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==MULT) ) {
                    alt11=1;
                }
                else if ( (LA11_0==DIV) ) {
                    alt11=2;
                }


                switch (alt11) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:228:9: MULT a= atom
            	    {
            	    MULT26=(Token)match(input,MULT,FOLLOW_MULT_in_term366); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MULT26_tree = (Object)adaptor.create(MULT26);
            	    adaptor.addChild(root_0, MULT26_tree);
            	    }
            	    pushFollow(FOLLOW_atom_in_term370);
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
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:232:9: DIV a= atom
            	    {
            	    DIV27=(Token)match(input,DIV,FOLLOW_DIV_in_term382); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DIV27_tree = (Object)adaptor.create(DIV27);
            	    adaptor.addChild(root_0, DIV27_tree);
            	    }
            	    pushFollow(FOLLOW_atom_in_term386);
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
            	    break loop11;
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
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:239:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
    public final GrammarParser.function_call_return function_call() throws RecognitionException {
        GrammarParser.function_call_return retval = new GrammarParser.function_call_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID28=null;
        Token LEFT_P29=null;
        Token RIGHT_P31=null;
        GrammarParser.args_return args30 = null;


        Object ID28_tree=null;
        Object LEFT_P29_tree=null;
        Object RIGHT_P31_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:239:55: ( ID LEFT_P args RIGHT_P )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:240:5: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID28=(Token)match(input,ID,FOLLOW_ID_in_function_call411); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID28_tree = (Object)adaptor.create(ID28);
            adaptor.addChild(root_0, ID28_tree);
            }
            LEFT_P29=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call413); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P29_tree = (Object)adaptor.create(LEFT_P29);
            adaptor.addChild(root_0, LEFT_P29_tree);
            }
            pushFollow(FOLLOW_args_in_function_call415);
            args30=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, args30.getTree());
            RIGHT_P31=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call417); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P31_tree = (Object)adaptor.create(RIGHT_P31);
            adaptor.addChild(root_0, RIGHT_P31_tree);
            }
            if ( state.backtracking==0 ) {
                
                      retval.name_params = (args30!=null?args30.params:null);
                      retval.name_params.add(0, (ID28!=null?ID28.getText():null));
                  
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
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:246:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final GrammarParser.args_return args() throws RecognitionException {
        GrammarParser.args_return retval = new GrammarParser.args_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA32=null;
        GrammarParser.expression_return a = null;

        GrammarParser.args_return b = null;


        Object COMMA32_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:246:41: (a= expression ( COMMA b= args )* )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:247:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args437);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:250:7: ( COMMA b= args )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==COMMA) ) {
                    int LA12_2 = input.LA(2);

                    if ( (synpred17_Grammar()) ) {
                        alt12=1;
                    }


                }


                switch (alt12) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:250:8: COMMA b= args
            	    {
            	    COMMA32=(Token)match(input,COMMA,FOLLOW_COMMA_in_args442); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA32_tree = (Object)adaptor.create(COMMA32);
            	    adaptor.addChild(root_0, COMMA32_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args446);
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
            	    break loop12;
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
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:259:1: atom returns [Object value] : ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal );
    public final GrammarParser.atom_return atom() throws RecognitionException {
        GrammarParser.atom_return retval = new GrammarParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM33=null;
        Token BOOL34=null;
        Token LEFT_P35=null;
        Token RIGHT_P37=null;
        Token ID38=null;
        GrammarParser.expression_return expression36 = null;

        GrammarParser.string_literal_return string_literal39 = null;


        Object NUM33_tree=null;
        Object BOOL34_tree=null;
        Object LEFT_P35_tree=null;
        Object RIGHT_P37_tree=null;
        Object ID38_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:260:5: ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal )
            int alt13=5;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt13=1;
                }
                break;
            case BOOL:
                {
                alt13=2;
                }
                break;
            case LEFT_P:
                {
                alt13=3;
                }
                break;
            case ID:
                {
                alt13=4;
                }
                break;
            case DQUOTE:
                {
                alt13=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:260:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM33=(Token)match(input,NUM,FOLLOW_NUM_in_atom469); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM33_tree = (Object)adaptor.create(NUM33);
                    adaptor.addChild(root_0, NUM33_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Float.parseFloat((NUM33!=null?NUM33.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:263:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL34=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL34_tree = (Object)adaptor.create(BOOL34);
                    adaptor.addChild(root_0, BOOL34_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL34!=null?BOOL34.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL34!=null?BOOL34.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              } else {
                                  _WPAScriptPanic("Token [" + (BOOL34!=null?BOOL34.getText():null) + "] must be equal to \"true\" or \"false\" (boolean type)");
                              }
                          
                    }

                    }
                    break;
                case 3 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:272:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P35=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom489); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P35_tree = (Object)adaptor.create(LEFT_P35);
                    adaptor.addChild(root_0, LEFT_P35_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom491);
                    expression36=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression36.getTree());
                    RIGHT_P37=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom493); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P37_tree = (Object)adaptor.create(RIGHT_P37);
                    adaptor.addChild(root_0, RIGHT_P37_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression36!=null?expression36.expr:null);
                          
                    }

                    }
                    break;
                case 4 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:275:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID38=(Token)match(input,ID,FOLLOW_ID_in_atom503); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID38_tree = (Object)adaptor.create(ID38);
                    adaptor.addChild(root_0, ID38_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID38!=null?ID38.getText():null));
                          
                    }

                    }
                    break;
                case 5 :
                    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:278:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom513);
                    string_literal39=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal39.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal39!=null?string_literal39.value:null);
                          
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
    // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:289:1: string_literal returns [String value] : DQUOTE ID DQUOTE ;
    public final GrammarParser.string_literal_return string_literal() throws RecognitionException {
        GrammarParser.string_literal_return retval = new GrammarParser.string_literal_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DQUOTE40=null;
        Token ID41=null;
        Token DQUOTE42=null;

        Object DQUOTE40_tree=null;
        Object ID41_tree=null;
        Object DQUOTE42_tree=null;

        try {
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:289:39: ( DQUOTE ID DQUOTE )
            // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:289:41: DQUOTE ID DQUOTE
            {
            root_0 = (Object)adaptor.nil();

            DQUOTE40=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal538); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DQUOTE40_tree = (Object)adaptor.create(DQUOTE40);
            adaptor.addChild(root_0, DQUOTE40_tree);
            }
            ID41=(Token)match(input,ID,FOLLOW_ID_in_string_literal540); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID41_tree = (Object)adaptor.create(ID41);
            adaptor.addChild(root_0, ID41_tree);
            }
            DQUOTE42=(Token)match(input,DQUOTE,FOLLOW_DQUOTE_in_string_literal542); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DQUOTE42_tree = (Object)adaptor.create(DQUOTE42);
            adaptor.addChild(root_0, DQUOTE42_tree);
            }
            if ( state.backtracking==0 ) {

                      retval.value = (ID41!=null?ID41.getText():null);
                  
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

    // $ANTLR start synpred1_Grammar
    public final void synpred1_Grammar_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:139:13: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:139:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Grammar79); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Grammar

    // $ANTLR start synpred3_Grammar
    public final void synpred3_Grammar_fragment() throws RecognitionException {   
        GrammarParser.stat_return s = null;


        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:149:8: (s= stat )
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:149:8: s= stat
        {
        pushFollow(FOLLOW_stat_in_synpred3_Grammar113);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Grammar

    // $ANTLR start synpred8_Grammar
    public final void synpred8_Grammar_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:194:38: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:194:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred8_Grammar240); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_Grammar

    // $ANTLR start synpred9_Grammar
    public final void synpred9_Grammar_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:7: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred9_Grammar249); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Grammar

    // $ANTLR start synpred10_Grammar
    public final void synpred10_Grammar_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:22: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred10_Grammar255); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_Grammar

    // $ANTLR start synpred11_Grammar
    public final void synpred11_Grammar_fragment() throws RecognitionException {   
        GrammarParser.stat_return s = null;


        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:17: ( ELSE ( NEWLINE )? s= stat )
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred11_Grammar253); if (state.failed) return ;
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:198:22: ( NEWLINE )?
        int alt14=2;
        int LA14_0 = input.LA(1);

        if ( (LA14_0==NEWLINE) ) {
            int LA14_1 = input.LA(2);

            if ( ((LA14_1>=LEFT_CB && LA14_1<=NEWLINE)||LA14_1==ID||(LA14_1>=IF && LA14_1<=LEFT_P)||(LA14_1>=NUM && LA14_1<=DQUOTE)) ) {
                alt14=1;
            }
        }
        switch (alt14) {
            case 1 :
                // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred11_Grammar255); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred11_Grammar260);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Grammar

    // $ANTLR start synpred17_Grammar
    public final void synpred17_Grammar_fragment() throws RecognitionException {   
        GrammarParser.args_return b = null;


        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:250:8: ( COMMA b= args )
        // D:\\PA-WPA-View\\GUI\\WPAScript\\working_copy\\src\\lexicalparser\\Grammar.g:250:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred17_Grammar442); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred17_Grammar446);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Grammar

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
    public final boolean synpred1_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA1_eotS =
        "\12\uffff";
    static final String DFA1_eofS =
        "\12\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\10\uffff";
    static final String DFA1_maxS =
        "\1\24\1\0\10\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\6\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\10\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\2\2\7\uffff\3\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "139:13: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA1_1 = input.LA(1);

                         
                        int index1_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Grammar()) ) {s = 9;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index1_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 1, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA5_eotS =
        "\12\uffff";
    static final String DFA5_eofS =
        "\12\uffff";
    static final String DFA5_minS =
        "\1\4\1\0\10\uffff";
    static final String DFA5_maxS =
        "\1\24\1\0\10\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\2\6\uffff\1\1";
    static final String DFA5_specialS =
        "\1\uffff\1\0\10\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\2\2\7\uffff\3\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "194:38: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_1 = input.LA(1);

                         
                        int index5_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Grammar()) ) {s = 9;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index5_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 5, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA7_eotS =
        "\12\uffff";
    static final String DFA7_eofS =
        "\12\uffff";
    static final String DFA7_minS =
        "\1\4\1\0\10\uffff";
    static final String DFA7_maxS =
        "\1\24\1\0\10\uffff";
    static final String DFA7_acceptS =
        "\2\uffff\1\2\6\uffff\1\1";
    static final String DFA7_specialS =
        "\1\uffff\1\0\10\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\2\2\7\uffff\3\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "198:22: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_1 = input.LA(1);

                         
                        int index7_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Grammar()) ) {s = 9;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index7_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_stats_in_prog60 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_block77 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_NEWLINE_in_block79 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_stats_in_block82 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block84 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block87 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats106 = new BitSet(new long[]{0x00000000001C06B2L});
    public static final BitSet FOLLOW_stat_in_stats113 = new BitSet(new long[]{0x00000000001C06B2L});
    public static final BitSet FOLLOW_expression_in_stat138 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat150 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_EQUAL_in_stat152 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_expression_in_stat154 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression230 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression232 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression236 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression238 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression240 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression245 = new BitSet(new long[]{0x0000000000001022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression249 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression253 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression255 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_terms_in_expression280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms310 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_PLUS_in_terms316 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_term_in_terms320 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_MINUS_in_terms332 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_term_in_terms336 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_atom_in_term360 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_MULT_in_term366 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_atom_in_term370 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_DIV_in_term382 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_atom_in_term386 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_ID_in_function_call411 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call413 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_args_in_function_call415 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args437 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_args442 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_args_in_args446 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_NUM_in_atom469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom489 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_expression_in_atom491 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal538 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_string_literal540 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DQUOTE_in_string_literal542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Grammar79 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Grammar113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred8_Grammar240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred9_Grammar249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred10_Grammar255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred11_Grammar253 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred11_Grammar255 = new BitSet(new long[]{0x00000000001C06B0L});
    public static final BitSet FOLLOW_stat_in_synpred11_Grammar260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred17_Grammar442 = new BitSet(new long[]{0x00000000001C0480L});
    public static final BitSet FOLLOW_args_in_synpred17_Grammar446 = new BitSet(new long[]{0x0000000000000002L});

}