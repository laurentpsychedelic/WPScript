// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g 2011-09-27 23:32:10

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "ID", "EQUAL", "PLUS_PLUS", "IF", "LEFT_P", "RIGHT_P", "ELSE", "WHILE", "FOR", "PV", "PLUS", "MINUS", "AND", "OR", "MULT", "DIV", "CMP_LT", "CMP_LT_EQ", "CMP_GT", "CMP_GT_EQ", "CMP_EQ", "CMP_NEQ", "COMMA", "NUM", "BOOL", "STRING_LITERAL", "TP", "DQUOTE", "MINUS_MINUS", "LEFT_B", "RIGHT_B", "WS"
    };
    public static final int CMP_NEQ=28;
    public static final int WHILE=14;
    public static final int ELSE=13;
    public static final int TP=33;
    public static final int BOOL=31;
    public static final int RIGHT_P=12;
    public static final int DQUOTE=34;
    public static final int FOR=15;
    public static final int CMP_EQ=27;
    public static final int MULT=21;
    public static final int MINUS=18;
    public static final int ID=7;
    public static final int AND=19;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int CMP_GT=25;
    public static final int NUM=30;
    public static final int RIGHT_CB=6;
    public static final int IF=10;
    public static final int CMP_GT_EQ=26;
    public static final int CMP_LT=23;
    public static final int WS=38;
    public static final int RIGHT_B=37;
    public static final int STRING_LITERAL=32;
    public static final int PLUS_PLUS=9;
    public static final int NEWLINE=5;
    public static final int COMMA=29;
    public static final int CMP_LT_EQ=24;
    public static final int EQUAL=8;
    public static final int OR=20;
    public static final int LEFT_B=36;
    public static final int PV=16;
    public static final int PLUS=17;
    public static final int DIV=22;
    public static final int LEFT_P=11;
    public static final int MINUS_MINUS=35;

    // delegates
    // delegators


        public GrammarParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GrammarParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[59+1];
             
             
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



        /*@Override
        protected void mismatch(IntStream input, int ttype, BitSet follow)
            throws RecognitionException
        {
          throw new MismatchedTokenException(ttype, input);
        }*/
        @Override
        public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow)
            throws RecognitionException
        {
            throw e;
        }

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

        public void treeRefactoring() throws PanicException {
            LinkedList <Expression> new_commands = new LinkedList();
            System.out.println("\nTREE REFACTORING");
            for (Object o : commands) {
                if (!(o instanceof Expression)) {
                    _WPAScriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                }
                Calculable new_command = ((Expression) o).getSimplifiedCalculable();
                if (new_command instanceof Expression) {
                    new_commands.add( (Expression) new_command );
                } else {
                    _WPAScriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                }
            }
            commands = new_commands;
            System.out.println("\nTREE REFACTORING OVER");
        }

        public boolean compilationCheck() throws PanicException {
            try {
                compilation_memory.clear();
                System.out.println("\nCOMPILATION CHECK");
                for (Object o : commands) {
                    if (!(o instanceof Expression)) {
                        _WPAScriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                    }
                    ((Expression) o).compilationCheck();
                }
                compilation_memory.clear();
                System.out.println("\nCOMPILATION OK");
                return true;
            } catch (CompilationErrorException e) {
                _WPAScriptCompilationError(e.getMessage(), e.getLineNumber());
                return false;
            }
        }

        public Object execute() throws PanicException {
            Object ret_val = null;
            for (Object c : commands) {
                if (!(c instanceof Expression)) {
                    _WPAScriptPanic("Top level command must be instances of Expression!", 0);
                }
                ret_val = ((Expression) c).eval();
            }
            return ret_val;
        }

        protected void _WPAScriptPanic(String message, int line_num) throws PanicException {

            if (!_PANIC_STATE_) {
                _PANIC_STATE_ = true;
                System.err.println("PANIC OCCURED!");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);

            dumpGlobalMemory(System.err);
            throw new PanicException(message, line_num);
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
        }

        protected void _WPAScriptRuntimeWarning(String message, int line_num) {
            if (!_RUNTIME_ERROR_STATE_) {
                _RUNTIME_ERROR_STATE_ = true;
                System.err.println("RUNTIME");
            }
            System.err.println("WARNING (l" + line_num + "):: " + message);
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:182:1: prog : s= stats ;
    public final GrammarParser.prog_return prog() throws RecognitionException {
        GrammarParser.prog_return retval = new GrammarParser.prog_return();
        retval.start = input.LT(1);
        int prog_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.stats_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:182:6: (s= stats )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:183:5: s= stats
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_stats_in_prog90);
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, prog_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:190:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
    public final GrammarParser.block_return block() throws RecognitionException {
        GrammarParser.block_return retval = new GrammarParser.block_return();
        retval.start = input.LT(1);
        int block_StartIndex = input.index();
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:190:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:191:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block107); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:191:13: ( NEWLINE )?
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE2=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_block109); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE2_tree = (Object)adaptor.create(NEWLINE2);
                    adaptor.addChild(root_0, NEWLINE2_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stats_in_block112);
            stats3=stats();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, stats3.getTree());
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:191:28: ( NEWLINE )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NEWLINE) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE4=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_block114); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE4_tree = (Object)adaptor.create(NEWLINE4);
                    adaptor.addChild(root_0, NEWLINE4_tree);
                    }

                    }
                    break;

            }

            RIGHT_CB5=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_block117); if (state.failed) return retval;
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, block_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:195:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final GrammarParser.stats_return stats() throws RecognitionException {
        GrammarParser.stats_return retval = new GrammarParser.stats_return();
        retval.start = input.LT(1);
        int stats_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.stat_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:195:51: (s= stat (s= stat )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:196:5: s= stat (s= stat )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_stat_in_stats136);
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:201:7: (s= stat )*
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
                else if ( (LA3_0==LEFT_CB||LA3_0==ID||(LA3_0>=IF && LA3_0<=LEFT_P)||(LA3_0>=WHILE && LA3_0<=FOR)||(LA3_0>=NUM && LA3_0<=STRING_LITERAL)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:201:8: s= stat
            	    {
            	    pushFollow(FOLLOW_stat_in_stats143);
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, stats_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:208:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );
    public final GrammarParser.stat_return stat() throws RecognitionException {
        GrammarParser.stat_return retval = new GrammarParser.stat_return();
        retval.start = input.LT(1);
        int stat_StartIndex = input.index();
        Object root_0 = null;

        Token NEWLINE7=null;
        Token NEWLINE8=null;
        GrammarParser.pre_stat_return pre_stat6 = null;

        GrammarParser.block_return block9 = null;

        GrammarParser.if_expression_return if_expression10 = null;

        GrammarParser.while_expression_return while_expression11 = null;

        GrammarParser.for_expression_return for_expression12 = null;


        Object NEWLINE7_tree=null;
        Object NEWLINE8_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:209:5: ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:209:7: pre_stat NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pre_stat_in_stat168);
                    pre_stat6=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pre_stat6.getTree());
                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat170); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE7_tree = (Object)adaptor.create(NEWLINE7);
                    adaptor.addChild(root_0, NEWLINE7_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = (pre_stat6!=null?pre_stat6.expr:null);
                              line_number++;
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:213:7: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE8=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat180); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE8_tree = (Object)adaptor.create(NEWLINE8);
                    adaptor.addChild(root_0, NEWLINE8_tree);
                    }
                    if ( state.backtracking==0 ) {

                              line_number++;
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:216:7: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_stat190);
                    block9=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block9.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, (block9!=null?block9.expressions:null));
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:219:7: if_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_if_expression_in_stat200);
                    if_expression10=if_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_expression10.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, (if_expression10!=null?if_expression10.expr:null));
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:222:7: while_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_while_expression_in_stat210);
                    while_expression11=while_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, while_expression11.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, (while_expression11!=null?while_expression11.expr:null));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:225:7: for_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_for_expression_in_stat220);
                    for_expression12=for_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, for_expression12.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, (for_expression12!=null?for_expression12.expr:null));
                          
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, stat_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "stat"

    public static class pre_stat_return extends ParserRuleReturnScope {
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pre_stat"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:228:1: pre_stat returns [Expression expr] : ( expression | ID EQUAL expression | ID PLUS_PLUS );
    public final GrammarParser.pre_stat_return pre_stat() throws RecognitionException {
        GrammarParser.pre_stat_return retval = new GrammarParser.pre_stat_return();
        retval.start = input.LT(1);
        int pre_stat_StartIndex = input.index();
        Object root_0 = null;

        Token ID14=null;
        Token EQUAL15=null;
        Token ID17=null;
        Token PLUS_PLUS18=null;
        GrammarParser.expression_return expression13 = null;

        GrammarParser.expression_return expression16 = null;


        Object ID14_tree=null;
        Object EQUAL15_tree=null;
        Object ID17_tree=null;
        Object PLUS_PLUS18_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:229:5: ( expression | ID EQUAL expression | ID PLUS_PLUS )
            int alt5=3;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==LEFT_CB||LA5_0==LEFT_P||(LA5_0>=NUM && LA5_0<=STRING_LITERAL)) ) {
                alt5=1;
            }
            else if ( (LA5_0==ID) ) {
                switch ( input.LA(2) ) {
                case PLUS_PLUS:
                    {
                    int LA5_3 = input.LA(3);

                    if ( (synpred9_Grammar()) ) {
                        alt5=1;
                    }
                    else if ( (true) ) {
                        alt5=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case NEWLINE:
                case LEFT_P:
                case RIGHT_P:
                case PV:
                case PLUS:
                case MINUS:
                case AND:
                case OR:
                case MULT:
                case DIV:
                case CMP_LT:
                case CMP_LT_EQ:
                case CMP_GT:
                case CMP_GT_EQ:
                case CMP_EQ:
                case CMP_NEQ:
                    {
                    alt5=1;
                    }
                    break;
                case EQUAL:
                    {
                    alt5=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 2, input);

                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:229:7: expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_pre_stat237);
                    expression13=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression13.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, (expression13!=null?expression13.expr:null));
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:232:7: ID EQUAL expression
                    {
                    root_0 = (Object)adaptor.nil();

                    ID14=(Token)match(input,ID,FOLLOW_ID_in_pre_stat247); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID14_tree = (Object)adaptor.create(ID14);
                    adaptor.addChild(root_0, ID14_tree);
                    }
                    EQUAL15=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_stat249); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL15_tree = (Object)adaptor.create(EQUAL15);
                    adaptor.addChild(root_0, EQUAL15_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_stat251);
                    expression16=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression16.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID14!=null?ID14.getText():null), (expression16!=null?expression16.expr:null)) );
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:235:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID17=(Token)match(input,ID,FOLLOW_ID_in_pre_stat261); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID17_tree = (Object)adaptor.create(ID17);
                    adaptor.addChild(root_0, ID17_tree);
                    }
                    PLUS_PLUS18=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat263); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS18_tree = (Object)adaptor.create(PLUS_PLUS18);
                    adaptor.addChild(root_0, PLUS_PLUS18_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID17!=null?ID17.getText():null), true));
                          
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, pre_stat_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pre_stat"

    public static class if_expression_return extends ParserRuleReturnScope {
        public IfExpression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:241:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final GrammarParser.if_expression_return if_expression() throws RecognitionException {
        GrammarParser.if_expression_return retval = new GrammarParser.if_expression_return();
        retval.start = input.LT(1);
        int if_expression_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.pre_if_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:242:5: (p= pre_if_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:242:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression293);
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, if_expression_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:258:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final GrammarParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        GrammarParser.pre_if_expression_return retval = new GrammarParser.pre_if_expression_return();
        retval.start = input.LT(1);
        int pre_if_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IF19=null;
        Token LEFT_P20=null;
        Token RIGHT_P21=null;
        Token NEWLINE22=null;
        Token NEWLINE23=null;
        Token ELSE24=null;
        Token NEWLINE25=null;
        GrammarParser.expression_return e = null;

        GrammarParser.stat_return s = null;


        Object IF19_tree=null;
        Object LEFT_P20_tree=null;
        Object RIGHT_P21_tree=null;
        Object NEWLINE22_tree=null;
        Object NEWLINE23_tree=null;
        Object ELSE24_tree=null;
        Object NEWLINE25_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:259:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:259:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF19=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression312); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF19_tree = (Object)adaptor.create(IF19);
            adaptor.addChild(root_0, IF19_tree);
            }
            LEFT_P20=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression314); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P20_tree = (Object)adaptor.create(LEFT_P20);
            adaptor.addChild(root_0, LEFT_P20_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression318);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P21=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression320); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P21_tree = (Object)adaptor.create(RIGHT_P21);
            adaptor.addChild(root_0, RIGHT_P21_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:259:38: ( NEWLINE )?
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE22=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression322); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE22_tree = (Object)adaptor.create(NEWLINE22);
                    adaptor.addChild(root_0, NEWLINE22_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_if_expression327);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:7: ( NEWLINE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NEWLINE) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred12_Grammar()) ) {
                    alt7=1;
                }
            }
            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE23=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression331); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE23_tree = (Object)adaptor.create(NEWLINE23);
                    adaptor.addChild(root_0, NEWLINE23_tree);
                    }

                    }
                    break;

            }

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==ELSE) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred14_Grammar()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE24=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression335); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE24_tree = (Object)adaptor.create(ELSE24);
                    adaptor.addChild(root_0, ELSE24_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:22: ( NEWLINE )?
                    int alt8=2;
                    alt8 = dfa8.predict(input);
                    switch (alt8) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                            {
                            NEWLINE25=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression337); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE25_tree = (Object)adaptor.create(NEWLINE25);
                            adaptor.addChild(root_0, NEWLINE25_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression342);
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, pre_if_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pre_if_expression"

    public static class while_expression_return extends ParserRuleReturnScope {
        public LoopExpression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "while_expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:267:1: while_expression returns [LoopExpression expr] : p= pre_while_expression ;
    public final GrammarParser.while_expression_return while_expression() throws RecognitionException {
        GrammarParser.while_expression_return retval = new GrammarParser.while_expression_return();
        retval.start = input.LT(1);
        int while_expression_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.pre_while_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:268:5: (p= pre_while_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:268:7: p= pre_while_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_while_expression_in_while_expression364);
            p=pre_while_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, p.getTree());
            if ( state.backtracking==0 ) {

                      Expression condition = null;
                      Expression expression = null;
                      if (0 < (p!=null?p.exprs:null).size()) {
                          condition = (p!=null?p.exprs:null).get(0);
                      }
                      if (1 < (p!=null?p.exprs:null).size()) {
                          expression = (p!=null?p.exprs:null).get(1);
                      }
                      retval.expr = new LoopExpression( this, condition, expression);
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, while_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "while_expression"

    public static class pre_while_expression_return extends ParserRuleReturnScope {
        public LinkedList<Expression> exprs;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pre_while_expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:280:1: pre_while_expression returns [LinkedList<Expression> exprs] : WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ;
    public final GrammarParser.pre_while_expression_return pre_while_expression() throws RecognitionException {
        GrammarParser.pre_while_expression_return retval = new GrammarParser.pre_while_expression_return();
        retval.start = input.LT(1);
        int pre_while_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WHILE26=null;
        Token LEFT_P27=null;
        Token RIGHT_P28=null;
        Token NEWLINE29=null;
        GrammarParser.expression_return e = null;

        GrammarParser.stat_return s = null;


        Object WHILE26_tree=null;
        Object LEFT_P27_tree=null;
        Object RIGHT_P28_tree=null;
        Object NEWLINE29_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:281:5: ( WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:281:7: WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            WHILE26=(Token)match(input,WHILE,FOLLOW_WHILE_in_pre_while_expression382); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE26_tree = (Object)adaptor.create(WHILE26);
            adaptor.addChild(root_0, WHILE26_tree);
            }
            LEFT_P27=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_while_expression384); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P27_tree = (Object)adaptor.create(LEFT_P27);
            adaptor.addChild(root_0, LEFT_P27_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_while_expression388);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P28=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_while_expression390); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P28_tree = (Object)adaptor.create(RIGHT_P28);
            adaptor.addChild(root_0, RIGHT_P28_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:281:41: ( NEWLINE )?
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE29=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_while_expression392); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE29_tree = (Object)adaptor.create(NEWLINE29);
                    adaptor.addChild(root_0, NEWLINE29_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_while_expression397);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, pre_while_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pre_while_expression"

    public static class for_expression_return extends ParserRuleReturnScope {
        public LoopExpression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "for_expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:287:1: for_expression returns [LoopExpression expr] : p= pre_for_expression ;
    public final GrammarParser.for_expression_return for_expression() throws RecognitionException {
        GrammarParser.for_expression_return retval = new GrammarParser.for_expression_return();
        retval.start = input.LT(1);
        int for_expression_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.pre_for_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:288:5: (p= pre_for_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:288:7: p= pre_for_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_for_expression_in_for_expression417);
            p=pre_for_expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, p.getTree());
            if ( state.backtracking==0 ) {

                      Expression init = null;
                      Expression increment = null;
                      Expression condition = null;
                      Expression expression = null;
                      init = (p!=null?p.exprs:null).get(0);
                      condition = (p!=null?p.exprs:null).get(1);
                      increment = (p!=null?p.exprs:null).get(2);
                      expression = (p!=null?p.exprs:null).get(3);
                      retval.expr = new LoopExpression( this, init, increment, condition, expression);
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, for_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "for_expression"

    public static class pre_for_expression_return extends ParserRuleReturnScope {
        public LinkedList<Expression> exprs;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pre_for_expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:300:1: pre_for_expression returns [LinkedList<Expression> exprs] : FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat ;
    public final GrammarParser.pre_for_expression_return pre_for_expression() throws RecognitionException {
        GrammarParser.pre_for_expression_return retval = new GrammarParser.pre_for_expression_return();
        retval.start = input.LT(1);
        int pre_for_expression_StartIndex = input.index();
        Object root_0 = null;

        Token FOR30=null;
        Token LEFT_P31=null;
        Token PV32=null;
        Token PV33=null;
        Token RIGHT_P34=null;
        Token NEWLINE35=null;
        GrammarParser.pre_stat_return e_init = null;

        GrammarParser.expression_return e_cond = null;

        GrammarParser.pre_stat_return e_inc = null;

        GrammarParser.stat_return s = null;


        Object FOR30_tree=null;
        Object LEFT_P31_tree=null;
        Object PV32_tree=null;
        Object PV33_tree=null;
        Object RIGHT_P34_tree=null;
        Object NEWLINE35_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:301:5: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:301:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            FOR30=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression435); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FOR30_tree = (Object)adaptor.create(FOR30);
            adaptor.addChild(root_0, FOR30_tree);
            }
            LEFT_P31=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression437); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P31_tree = (Object)adaptor.create(LEFT_P31);
            adaptor.addChild(root_0, LEFT_P31_tree);
            }
            pushFollow(FOLLOW_pre_stat_in_pre_for_expression441);
            e_init=pre_stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e_init.getTree());
            PV32=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression443); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            PV32_tree = (Object)adaptor.create(PV32);
            adaptor.addChild(root_0, PV32_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_for_expression447);
            e_cond=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e_cond.getTree());
            PV33=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression449); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            PV33_tree = (Object)adaptor.create(PV33);
            adaptor.addChild(root_0, PV33_tree);
            }
            pushFollow(FOLLOW_pre_stat_in_pre_for_expression453);
            e_inc=pre_stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e_inc.getTree());
            RIGHT_P34=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression455); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P34_tree = (Object)adaptor.create(RIGHT_P34);
            adaptor.addChild(root_0, RIGHT_P34_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:301:81: ( NEWLINE )?
            int alt11=2;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE35=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression457); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE35_tree = (Object)adaptor.create(NEWLINE35);
                    adaptor.addChild(root_0, NEWLINE35_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_for_expression462);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e_init!=null?e_init.expr:null) );
                      retval.exprs.add( (e_cond!=null?e_cond.expr:null) );
                      retval.exprs.add( (e_inc!=null?e_inc.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, pre_for_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pre_for_expression"

    public static class expression_return extends ParserRuleReturnScope {
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:309:1: expression returns [Expression expr] : ( terms | function_call );
    public final GrammarParser.expression_return expression() throws RecognitionException {
        GrammarParser.expression_return retval = new GrammarParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.terms_return terms36 = null;

        GrammarParser.function_call_return function_call37 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:310:5: ( terms | function_call )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==LEFT_CB||LA12_0==LEFT_P||(LA12_0>=NUM && LA12_0<=STRING_LITERAL)) ) {
                alt12=1;
            }
            else if ( (LA12_0==ID) ) {
                int LA12_2 = input.LA(2);

                if ( (LA12_2==EOF||(LA12_2>=NEWLINE && LA12_2<=RIGHT_CB)||LA12_2==PLUS_PLUS||LA12_2==RIGHT_P||(LA12_2>=PV && LA12_2<=COMMA)||LA12_2==TP) ) {
                    alt12=1;
                }
                else if ( (LA12_2==LEFT_P) ) {
                    alt12=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:310:7: terms
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_terms_in_expression480);
                    terms36=terms();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, terms36.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new Term(this, (terms36!=null?terms36.terms:null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:313:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_expression490);
                    function_call37=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call37.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new FunctionCall( this, (function_call37!=null?function_call37.name_params:null) ) );
                          
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, expression_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:317:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* ;
    public final GrammarParser.terms_return terms() throws RecognitionException {
        GrammarParser.terms_return retval = new GrammarParser.terms_return();
        retval.start = input.LT(1);
        int terms_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS38=null;
        Token MINUS39=null;
        Token AND40=null;
        Token OR41=null;
        GrammarParser.term_return t = null;


        Object PLUS38_tree=null;
        Object MINUS39_tree=null;
        Object AND40_tree=null;
        Object OR41_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:318:5: (t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:318:7: t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms510);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:321:7: ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            loop13:
            do {
                int alt13=5;
                switch ( input.LA(1) ) {
                case PLUS:
                    {
                    alt13=1;
                    }
                    break;
                case MINUS:
                    {
                    alt13=2;
                    }
                    break;
                case AND:
                    {
                    alt13=3;
                    }
                    break;
                case OR:
                    {
                    alt13=4;
                    }
                    break;

                }

                switch (alt13) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:321:9: PLUS t= term
            	    {
            	    PLUS38=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms516); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS38_tree = (Object)adaptor.create(PLUS38);
            	    adaptor.addChild(root_0, PLUS38_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms520);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:325:9: MINUS t= term
            	    {
            	    MINUS39=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms532); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS39_tree = (Object)adaptor.create(MINUS39);
            	    adaptor.addChild(root_0, MINUS39_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms536);
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
            	case 3 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:329:9: AND t= term
            	    {
            	    AND40=(Token)match(input,AND,FOLLOW_AND_in_terms548); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND40_tree = (Object)adaptor.create(AND40);
            	    adaptor.addChild(root_0, AND40_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms552);
            	    t=term();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            	    if ( state.backtracking==0 ) {

            	                  retval.terms.add(Operator.OPERATOR_AND);
            	                  retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
            	              
            	    }

            	    }
            	    break;
            	case 4 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:333:9: OR t= term
            	    {
            	    OR41=(Token)match(input,OR,FOLLOW_OR_in_terms564); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR41_tree = (Object)adaptor.create(OR41);
            	    adaptor.addChild(root_0, OR41_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms568);
            	    t=term();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            	    if ( state.backtracking==0 ) {

            	                  retval.terms.add(Operator.OPERATOR_OR);
            	                  retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
            	              
            	    }

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, terms_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:339:1: term returns [LinkedList<Object> atoms] : (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS );
    public final GrammarParser.term_return term() throws RecognitionException {
        GrammarParser.term_return retval = new GrammarParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token MULT42=null;
        Token DIV43=null;
        Token CMP_LT44=null;
        Token CMP_LT_EQ45=null;
        Token CMP_GT46=null;
        Token CMP_GT_EQ47=null;
        Token CMP_EQ48=null;
        Token CMP_NEQ49=null;
        Token ID50=null;
        Token PLUS_PLUS51=null;
        GrammarParser.atom_return a = null;


        Object MULT42_tree=null;
        Object DIV43_tree=null;
        Object CMP_LT44_tree=null;
        Object CMP_LT_EQ45_tree=null;
        Object CMP_GT46_tree=null;
        Object CMP_GT_EQ47_tree=null;
        Object CMP_EQ48_tree=null;
        Object CMP_NEQ49_tree=null;
        Object ID50_tree=null;
        Object PLUS_PLUS51_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:340:5: (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==LEFT_CB||LA15_0==LEFT_P||(LA15_0>=NUM && LA15_0<=STRING_LITERAL)) ) {
                alt15=1;
            }
            else if ( (LA15_0==ID) ) {
                int LA15_2 = input.LA(2);

                if ( (LA15_2==PLUS_PLUS) ) {
                    alt15=2;
                }
                else if ( (LA15_2==EOF||(LA15_2>=NEWLINE && LA15_2<=RIGHT_CB)||LA15_2==RIGHT_P||(LA15_2>=PV && LA15_2<=COMMA)||LA15_2==TP) ) {
                    alt15=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:340:7: a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term592);
                    a=atom();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              retval.atoms.add((a!=null?a.value:null));
                          
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:343:7: ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    loop14:
                    do {
                        int alt14=9;
                        switch ( input.LA(1) ) {
                        case MULT:
                            {
                            alt14=1;
                            }
                            break;
                        case DIV:
                            {
                            alt14=2;
                            }
                            break;
                        case CMP_LT:
                            {
                            alt14=3;
                            }
                            break;
                        case CMP_LT_EQ:
                            {
                            alt14=4;
                            }
                            break;
                        case CMP_GT:
                            {
                            alt14=5;
                            }
                            break;
                        case CMP_GT_EQ:
                            {
                            alt14=6;
                            }
                            break;
                        case CMP_EQ:
                            {
                            alt14=7;
                            }
                            break;
                        case CMP_NEQ:
                            {
                            alt14=8;
                            }
                            break;

                        }

                        switch (alt14) {
                    	case 1 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:343:9: MULT a= atom
                    	    {
                    	    MULT42=(Token)match(input,MULT,FOLLOW_MULT_in_term598); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    MULT42_tree = (Object)adaptor.create(MULT42);
                    	    adaptor.addChild(root_0, MULT42_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term602);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:347:9: DIV a= atom
                    	    {
                    	    DIV43=(Token)match(input,DIV,FOLLOW_DIV_in_term614); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DIV43_tree = (Object)adaptor.create(DIV43);
                    	    adaptor.addChild(root_0, DIV43_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term618);
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
                    	case 3 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:351:9: CMP_LT a= atom
                    	    {
                    	    CMP_LT44=(Token)match(input,CMP_LT,FOLLOW_CMP_LT_in_term630); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT44_tree = (Object)adaptor.create(CMP_LT44);
                    	    adaptor.addChild(root_0, CMP_LT44_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term634);
                    	    a=atom();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    	    if ( state.backtracking==0 ) {

                    	                  retval.atoms.add(Operator.OPERATOR_CMP_LT);
                    	                  retval.atoms.add((a!=null?a.value:null));
                    	              
                    	    }

                    	    }
                    	    break;
                    	case 4 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:355:9: CMP_LT_EQ a= atom
                    	    {
                    	    CMP_LT_EQ45=(Token)match(input,CMP_LT_EQ,FOLLOW_CMP_LT_EQ_in_term647); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT_EQ45_tree = (Object)adaptor.create(CMP_LT_EQ45);
                    	    adaptor.addChild(root_0, CMP_LT_EQ45_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term651);
                    	    a=atom();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    	    if ( state.backtracking==0 ) {

                    	                  retval.atoms.add(Operator.OPERATOR_CMP_LT_EQ);
                    	                  retval.atoms.add((a!=null?a.value:null));
                    	              
                    	    }

                    	    }
                    	    break;
                    	case 5 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:359:9: CMP_GT a= atom
                    	    {
                    	    CMP_GT46=(Token)match(input,CMP_GT,FOLLOW_CMP_GT_in_term664); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT46_tree = (Object)adaptor.create(CMP_GT46);
                    	    adaptor.addChild(root_0, CMP_GT46_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term668);
                    	    a=atom();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    	    if ( state.backtracking==0 ) {

                    	                  retval.atoms.add(Operator.OPERATOR_CMP_GT);
                    	                  retval.atoms.add((a!=null?a.value:null));
                    	              
                    	    }

                    	    }
                    	    break;
                    	case 6 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:363:9: CMP_GT_EQ a= atom
                    	    {
                    	    CMP_GT_EQ47=(Token)match(input,CMP_GT_EQ,FOLLOW_CMP_GT_EQ_in_term680); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT_EQ47_tree = (Object)adaptor.create(CMP_GT_EQ47);
                    	    adaptor.addChild(root_0, CMP_GT_EQ47_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term684);
                    	    a=atom();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    	    if ( state.backtracking==0 ) {

                    	                  retval.atoms.add(Operator.OPERATOR_CMP_GT_EQ);
                    	                  retval.atoms.add((a!=null?a.value:null));
                    	              
                    	    }

                    	    }
                    	    break;
                    	case 7 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:367:9: CMP_EQ a= atom
                    	    {
                    	    CMP_EQ48=(Token)match(input,CMP_EQ,FOLLOW_CMP_EQ_in_term697); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_EQ48_tree = (Object)adaptor.create(CMP_EQ48);
                    	    adaptor.addChild(root_0, CMP_EQ48_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term701);
                    	    a=atom();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    	    if ( state.backtracking==0 ) {

                    	                  retval.atoms.add(Operator.OPERATOR_CMP_EQ);
                    	                  retval.atoms.add((a!=null?a.value:null));
                    	              
                    	    }

                    	    }
                    	    break;
                    	case 8 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:371:9: CMP_NEQ a= atom
                    	    {
                    	    CMP_NEQ49=(Token)match(input,CMP_NEQ,FOLLOW_CMP_NEQ_in_term713); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_NEQ49_tree = (Object)adaptor.create(CMP_NEQ49);
                    	    adaptor.addChild(root_0, CMP_NEQ49_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term717);
                    	    a=atom();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    	    if ( state.backtracking==0 ) {

                    	                  retval.atoms.add(Operator.OPERATOR_CMP_NEQ);
                    	                  retval.atoms.add((a!=null?a.value:null));
                    	              
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:376:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID50=(Token)match(input,ID,FOLLOW_ID_in_term734); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID50_tree = (Object)adaptor.create(ID50);
                    adaptor.addChild(root_0, ID50_tree);
                    }
                    PLUS_PLUS51=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term736); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS51_tree = (Object)adaptor.create(PLUS_PLUS51);
                    adaptor.addChild(root_0, PLUS_PLUS51_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID50!=null?ID50.getText():null), true);
                              retval.atoms.add(va);
                          
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, term_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:383:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
    public final GrammarParser.function_call_return function_call() throws RecognitionException {
        GrammarParser.function_call_return retval = new GrammarParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token ID52=null;
        Token LEFT_P53=null;
        Token RIGHT_P55=null;
        GrammarParser.args_return args54 = null;


        Object ID52_tree=null;
        Object LEFT_P53_tree=null;
        Object RIGHT_P55_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:383:55: ( ID LEFT_P args RIGHT_P )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:384:5: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID52=(Token)match(input,ID,FOLLOW_ID_in_function_call754); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID52_tree = (Object)adaptor.create(ID52);
            adaptor.addChild(root_0, ID52_tree);
            }
            LEFT_P53=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call756); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P53_tree = (Object)adaptor.create(LEFT_P53);
            adaptor.addChild(root_0, LEFT_P53_tree);
            }
            pushFollow(FOLLOW_args_in_function_call758);
            args54=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, args54.getTree());
            RIGHT_P55=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call760); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P55_tree = (Object)adaptor.create(RIGHT_P55);
            adaptor.addChild(root_0, RIGHT_P55_tree);
            }
            if ( state.backtracking==0 ) {
                
                      retval.name_params = (args54!=null?args54.params:null);
                      retval.name_params.add(0, (ID52!=null?ID52.getText():null));
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, function_call_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:390:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final GrammarParser.args_return args() throws RecognitionException {
        GrammarParser.args_return retval = new GrammarParser.args_return();
        retval.start = input.LT(1);
        int args_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA56=null;
        GrammarParser.expression_return a = null;

        GrammarParser.args_return b = null;


        Object COMMA56_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:390:41: (a= expression ( COMMA b= args )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:391:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args780);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:394:7: ( COMMA b= args )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==COMMA) ) {
                    int LA16_2 = input.LA(2);

                    if ( (synpred31_Grammar()) ) {
                        alt16=1;
                    }


                }


                switch (alt16) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:394:8: COMMA b= args
            	    {
            	    COMMA56=(Token)match(input,COMMA,FOLLOW_COMMA_in_args785); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA56_tree = (Object)adaptor.create(COMMA56);
            	    adaptor.addChild(root_0, COMMA56_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args789);
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
            	    break loop16;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, args_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:403:1: atom returns [Object value] : ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary );
    public final GrammarParser.atom_return atom() throws RecognitionException {
        GrammarParser.atom_return retval = new GrammarParser.atom_return();
        retval.start = input.LT(1);
        int atom_StartIndex = input.index();
        Object root_0 = null;

        Token NUM57=null;
        Token BOOL58=null;
        Token LEFT_P59=null;
        Token RIGHT_P61=null;
        Token ID62=null;
        GrammarParser.expression_return expression60 = null;

        GrammarParser.string_literal_return string_literal63 = null;

        GrammarParser.dictionary_return dictionary64 = null;


        Object NUM57_tree=null;
        Object BOOL58_tree=null;
        Object LEFT_P59_tree=null;
        Object RIGHT_P61_tree=null;
        Object ID62_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:404:5: ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary )
            int alt17=6;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt17=1;
                }
                break;
            case BOOL:
                {
                alt17=2;
                }
                break;
            case LEFT_P:
                {
                alt17=3;
                }
                break;
            case ID:
                {
                alt17=4;
                }
                break;
            case STRING_LITERAL:
                {
                alt17=5;
                }
                break;
            case LEFT_CB:
                {
                alt17=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:404:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM57=(Token)match(input,NUM,FOLLOW_NUM_in_atom812); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM57_tree = (Object)adaptor.create(NUM57);
                    adaptor.addChild(root_0, NUM57_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Float.parseFloat((NUM57!=null?NUM57.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:407:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL58=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom822); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL58_tree = (Object)adaptor.create(BOOL58);
                    adaptor.addChild(root_0, BOOL58_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL58!=null?BOOL58.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL58!=null?BOOL58.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              }
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:414:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P59=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom832); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P59_tree = (Object)adaptor.create(LEFT_P59);
                    adaptor.addChild(root_0, LEFT_P59_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom834);
                    expression60=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression60.getTree());
                    RIGHT_P61=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom836); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P61_tree = (Object)adaptor.create(RIGHT_P61);
                    adaptor.addChild(root_0, RIGHT_P61_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression60!=null?expression60.expr:null);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:417:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID62=(Token)match(input,ID,FOLLOW_ID_in_atom846); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID62_tree = (Object)adaptor.create(ID62);
                    adaptor.addChild(root_0, ID62_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID62!=null?ID62.getText():null));
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:420:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom856);
                    string_literal63=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal63.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal63!=null?string_literal63.value:null);
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:423:7: dictionary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dictionary_in_atom866);
                    dictionary64=dictionary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary64.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (dictionary64!=null?dictionary64.value:null);
                          
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, atom_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "atom"

    public static class string_literal_return extends ParserRuleReturnScope {
        public CharString value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "string_literal"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:435:1: string_literal returns [CharString value] : s= STRING_LITERAL ;
    public final GrammarParser.string_literal_return string_literal() throws RecognitionException {
        GrammarParser.string_literal_return retval = new GrammarParser.string_literal_return();
        retval.start = input.LT(1);
        int string_literal_StartIndex = input.index();
        Object root_0 = null;

        Token s=null;

        Object s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:435:43: (s= STRING_LITERAL )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:435:45: s= STRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_string_literal894); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            s_tree = (Object)adaptor.create(s);
            adaptor.addChild(root_0, s_tree);
            }
            if ( state.backtracking==0 ) {

                      retval.value = new CharString( (s!=null?s.getText():null).replaceAll("^\"", "").replaceAll("\"$", "") );
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, string_literal_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "string_literal"

    public static class dictionary_return extends ParserRuleReturnScope {
        public Dictionary value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dictionary"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:440:1: dictionary returns [Dictionary value] : LEFT_CB dictionary_elements RIGHT_CB ;
    public final GrammarParser.dictionary_return dictionary() throws RecognitionException {
        GrammarParser.dictionary_return retval = new GrammarParser.dictionary_return();
        retval.start = input.LT(1);
        int dictionary_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB65=null;
        Token RIGHT_CB67=null;
        GrammarParser.dictionary_elements_return dictionary_elements66 = null;


        Object LEFT_CB65_tree=null;
        Object RIGHT_CB67_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:440:39: ( LEFT_CB dictionary_elements RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:441:5: LEFT_CB dictionary_elements RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB65=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_dictionary913); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB65_tree = (Object)adaptor.create(LEFT_CB65);
            adaptor.addChild(root_0, LEFT_CB65_tree);
            }
            pushFollow(FOLLOW_dictionary_elements_in_dictionary915);
            dictionary_elements66=dictionary_elements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary_elements66.getTree());
            RIGHT_CB67=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_dictionary917); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB67_tree = (Object)adaptor.create(RIGHT_CB67);
            adaptor.addChild(root_0, RIGHT_CB67_tree);
            }
            if ( state.backtracking==0 ) {

                      HashMap vs = new HashMap();
                      int size = (dictionary_elements66!=null?dictionary_elements66.keys_values:null).size();
                      for (int k=0; k<size; k+=2) {
                          vs.put((dictionary_elements66!=null?dictionary_elements66.keys_values:null).get(k), (dictionary_elements66!=null?dictionary_elements66.keys_values:null).get(k+1));
                      }
                      retval.value = new Dictionary(this, vs);
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, dictionary_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dictionary"

    public static class dictionary_elements_return extends ParserRuleReturnScope {
        public LinkedList<Object> keys_values;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dictionary_elements"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:450:1: dictionary_elements returns [LinkedList<Object> keys_values] : (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* ;
    public final GrammarParser.dictionary_elements_return dictionary_elements() throws RecognitionException {
        GrammarParser.dictionary_elements_return retval = new GrammarParser.dictionary_elements_return();
        retval.start = input.LT(1);
        int dictionary_elements_StartIndex = input.index();
        Object root_0 = null;

        Token TP68=null;
        Token NEWLINE69=null;
        Token COMMA70=null;
        Token NEWLINE71=null;
        GrammarParser.expression_return e1 = null;

        GrammarParser.expression_return e2 = null;

        GrammarParser.dictionary_elements_return d = null;


        Object TP68_tree=null;
        Object NEWLINE69_tree=null;
        Object COMMA70_tree=null;
        Object NEWLINE71_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:450:62: ( (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:451:5: (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:451:5: (e1= expression TP e2= expression )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:451:6: e1= expression TP e2= expression
            {
            pushFollow(FOLLOW_expression_in_dictionary_elements938);
            e1=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
            TP68=(Token)match(input,TP,FOLLOW_TP_in_dictionary_elements940); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TP68_tree = (Object)adaptor.create(TP68);
            adaptor.addChild(root_0, TP68_tree);
            }
            pushFollow(FOLLOW_expression_in_dictionary_elements944);
            e2=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());

            }

            if ( state.backtracking==0 ) {

                      retval.keys_values = new LinkedList();
                      retval.keys_values.add((e1!=null?e1.expr:null));
                      retval.keys_values.add((e2!=null?e2.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:7: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==NEWLINE) ) {
                    int LA20_2 = input.LA(2);

                    if ( (synpred39_Grammar()) ) {
                        alt20=1;
                    }


                }
                else if ( (LA20_0==COMMA) ) {
                    int LA20_3 = input.LA(2);

                    if ( (synpred39_Grammar()) ) {
                        alt20=1;
                    }


                }


                switch (alt20) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
            	    {
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:8: ( NEWLINE )?
            	    int alt18=2;
            	    int LA18_0 = input.LA(1);

            	    if ( (LA18_0==NEWLINE) ) {
            	        alt18=1;
            	    }
            	    switch (alt18) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
            	            {
            	            NEWLINE69=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements950); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE69_tree = (Object)adaptor.create(NEWLINE69);
            	            adaptor.addChild(root_0, NEWLINE69_tree);
            	            }

            	            }
            	            break;

            	    }

            	    COMMA70=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictionary_elements953); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA70_tree = (Object)adaptor.create(COMMA70);
            	    adaptor.addChild(root_0, COMMA70_tree);
            	    }
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:23: ( NEWLINE )?
            	    int alt19=2;
            	    int LA19_0 = input.LA(1);

            	    if ( (LA19_0==NEWLINE) ) {
            	        alt19=1;
            	    }
            	    switch (alt19) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
            	            {
            	            NEWLINE71=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements955); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE71_tree = (Object)adaptor.create(NEWLINE71);
            	            adaptor.addChild(root_0, NEWLINE71_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_dictionary_elements_in_dictionary_elements960);
            	    d=dictionary_elements();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
            	    if ( state.backtracking==0 ) {

            	                  for (int k=0; k<(d!=null?d.keys_values:null).size(); k++) {
            	                      retval.keys_values.add((d!=null?d.keys_values:null).get(k));
            	                  }
            	              
            	    }

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, dictionary_elements_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dictionary_elements"

    // $ANTLR start synpred1_Grammar
    public final void synpred1_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:191:13: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:191:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Grammar109); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Grammar

    // $ANTLR start synpred3_Grammar
    public final void synpred3_Grammar_fragment() throws RecognitionException {   
        GrammarParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:201:8: (s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:201:8: s= stat
        {
        pushFollow(FOLLOW_stat_in_synpred3_Grammar143);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Grammar

    // $ANTLR start synpred4_Grammar
    public final void synpred4_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:209:7: ( pre_stat NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:209:7: pre_stat NEWLINE
        {
        pushFollow(FOLLOW_pre_stat_in_synpred4_Grammar168);
        pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred4_Grammar170); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Grammar

    // $ANTLR start synpred6_Grammar
    public final void synpred6_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:216:7: ( block )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:216:7: block
        {
        pushFollow(FOLLOW_block_in_synpred6_Grammar190);
        block();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Grammar

    // $ANTLR start synpred9_Grammar
    public final void synpred9_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:229:7: ( expression )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:229:7: expression
        {
        pushFollow(FOLLOW_expression_in_synpred9_Grammar237);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Grammar

    // $ANTLR start synpred11_Grammar
    public final void synpred11_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:259:38: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:259:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred11_Grammar322); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Grammar

    // $ANTLR start synpred12_Grammar
    public final void synpred12_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:7: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred12_Grammar331); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_Grammar

    // $ANTLR start synpred13_Grammar
    public final void synpred13_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:22: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred13_Grammar337); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Grammar

    // $ANTLR start synpred14_Grammar
    public final void synpred14_Grammar_fragment() throws RecognitionException {   
        GrammarParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:17: ( ELSE ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred14_Grammar335); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:22: ( NEWLINE )?
        int alt21=2;
        int LA21_0 = input.LA(1);

        if ( (LA21_0==NEWLINE) ) {
            int LA21_1 = input.LA(2);

            if ( ((LA21_1>=LEFT_CB && LA21_1<=NEWLINE)||LA21_1==ID||(LA21_1>=IF && LA21_1<=LEFT_P)||(LA21_1>=WHILE && LA21_1<=FOR)||(LA21_1>=NUM && LA21_1<=STRING_LITERAL)) ) {
                alt21=1;
            }
        }
        switch (alt21) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred14_Grammar337); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred14_Grammar342);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Grammar

    // $ANTLR start synpred15_Grammar
    public final void synpred15_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:281:41: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:281:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred15_Grammar392); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Grammar

    // $ANTLR start synpred16_Grammar
    public final void synpred16_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:301:81: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:301:81: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred16_Grammar457); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Grammar

    // $ANTLR start synpred31_Grammar
    public final void synpred31_Grammar_fragment() throws RecognitionException {   
        GrammarParser.args_return b = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:394:8: ( COMMA b= args )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:394:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred31_Grammar785); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred31_Grammar789);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred31_Grammar

    // $ANTLR start synpred39_Grammar
    public final void synpred39_Grammar_fragment() throws RecognitionException {   
        GrammarParser.dictionary_elements_return d = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:8: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
        {
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:8: ( NEWLINE )?
        int alt23=2;
        int LA23_0 = input.LA(1);

        if ( (LA23_0==NEWLINE) ) {
            alt23=1;
        }
        switch (alt23) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred39_Grammar950); if (state.failed) return ;

                }
                break;

        }

        match(input,COMMA,FOLLOW_COMMA_in_synpred39_Grammar953); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:455:23: ( NEWLINE )?
        int alt24=2;
        int LA24_0 = input.LA(1);

        if ( (LA24_0==NEWLINE) ) {
            alt24=1;
        }
        switch (alt24) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred39_Grammar955); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_dictionary_elements_in_synpred39_Grammar960);
        d=dictionary_elements();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred39_Grammar

    // Delegated rules

    public final boolean synpred31_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred31_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
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
    public final boolean synpred12_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Grammar_fragment(); // can never throw exception
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
    public final boolean synpred4_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_Grammar_fragment(); // can never throw exception
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


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA1_eotS =
        "\14\uffff";
    static final String DFA1_eofS =
        "\14\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\12\uffff";
    static final String DFA1_maxS =
        "\1\40\1\0\12\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\10\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\12\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\1\2\2\uffff\2\2\2\uffff\2\2\16\uffff\3\2",
            "\1\uffff",
            "",
            "",
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
            return "191:13: ( NEWLINE )?";
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
                        if ( (synpred1_Grammar()) ) {s = 11;}

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
    static final String DFA4_eotS =
        "\14\uffff";
    static final String DFA4_eofS =
        "\14\uffff";
    static final String DFA4_minS =
        "\1\4\5\uffff\1\0\5\uffff";
    static final String DFA4_maxS =
        "\1\40\5\uffff\1\0\5\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\5\uffff\1\2\1\4\1\5\1\6\1\3";
    static final String DFA4_specialS =
        "\6\uffff\1\0\5\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\6\1\7\1\uffff\1\1\2\uffff\1\10\1\1\2\uffff\1\11\1\12\16\uffff"+
            "\3\1",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "208:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_6 = input.LA(1);

                         
                        int index4_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Grammar()) ) {s = 1;}

                        else if ( (synpred6_Grammar()) ) {s = 11;}

                         
                        input.seek(index4_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 4, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA6_eotS =
        "\14\uffff";
    static final String DFA6_eofS =
        "\14\uffff";
    static final String DFA6_minS =
        "\1\4\1\0\12\uffff";
    static final String DFA6_maxS =
        "\1\40\1\0\12\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\10\uffff\1\1";
    static final String DFA6_specialS =
        "\1\uffff\1\0\12\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\2\1\1\1\uffff\1\2\2\uffff\2\2\2\uffff\2\2\16\uffff\3\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "259:38: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA6_1 = input.LA(1);

                         
                        int index6_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Grammar()) ) {s = 11;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index6_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 6, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA8_eotS =
        "\14\uffff";
    static final String DFA8_eofS =
        "\14\uffff";
    static final String DFA8_minS =
        "\1\4\1\0\12\uffff";
    static final String DFA8_maxS =
        "\1\40\1\0\12\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\10\uffff\1\1";
    static final String DFA8_specialS =
        "\1\uffff\1\0\12\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\1\1\1\uffff\1\2\2\uffff\2\2\2\uffff\2\2\16\uffff\3\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
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
            return "263:22: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_1 = input.LA(1);

                         
                        int index8_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Grammar()) ) {s = 11;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index8_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA10_eotS =
        "\14\uffff";
    static final String DFA10_eofS =
        "\14\uffff";
    static final String DFA10_minS =
        "\1\4\1\0\12\uffff";
    static final String DFA10_maxS =
        "\1\40\1\0\12\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\10\uffff\1\1";
    static final String DFA10_specialS =
        "\1\uffff\1\0\12\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\2\1\1\1\uffff\1\2\2\uffff\2\2\2\uffff\2\2\16\uffff\3\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "281:41: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_1 = input.LA(1);

                         
                        int index10_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_Grammar()) ) {s = 11;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index10_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA11_eotS =
        "\14\uffff";
    static final String DFA11_eofS =
        "\14\uffff";
    static final String DFA11_minS =
        "\1\4\1\0\12\uffff";
    static final String DFA11_maxS =
        "\1\40\1\0\12\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\10\uffff\1\1";
    static final String DFA11_specialS =
        "\1\uffff\1\0\12\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\2\1\1\1\uffff\1\2\2\uffff\2\2\2\uffff\2\2\16\uffff\3\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "301:81: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_1 = input.LA(1);

                         
                        int index11_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_Grammar()) ) {s = 11;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index11_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_stats_in_prog90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_block107 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_NEWLINE_in_block109 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_stats_in_block112 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block114 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats136 = new BitSet(new long[]{0x00000001C000CCB2L});
    public static final BitSet FOLLOW_stat_in_stats143 = new BitSet(new long[]{0x00000001C000CCB2L});
    public static final BitSet FOLLOW_pre_stat_in_stat168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_expression_in_stat210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_expression_in_stat220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_pre_stat237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat247 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat249 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_expression_in_pre_stat251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat261 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression312 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression314 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression318 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression320 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression322 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression327 = new BitSet(new long[]{0x0000000000002022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression331 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression335 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression337 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_while_expression_in_while_expression364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_pre_while_expression382 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_while_expression384 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_expression_in_pre_while_expression388 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_while_expression390 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_while_expression392 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_stat_in_pre_while_expression397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_for_expression_in_for_expression417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression435 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression437 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression441 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression443 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_expression_in_pre_for_expression447 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression449 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression453 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression455 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression457 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_terms_in_expression480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms510 = new BitSet(new long[]{0x00000000001E0002L});
    public static final BitSet FOLLOW_PLUS_in_terms516 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_term_in_terms520 = new BitSet(new long[]{0x00000000001E0002L});
    public static final BitSet FOLLOW_MINUS_in_terms532 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_term_in_terms536 = new BitSet(new long[]{0x00000000001E0002L});
    public static final BitSet FOLLOW_AND_in_terms548 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_term_in_terms552 = new BitSet(new long[]{0x00000000001E0002L});
    public static final BitSet FOLLOW_OR_in_terms564 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_term_in_terms568 = new BitSet(new long[]{0x00000000001E0002L});
    public static final BitSet FOLLOW_atom_in_term592 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_MULT_in_term598 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term602 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_DIV_in_term614 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term618 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_CMP_LT_in_term630 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term634 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_CMP_LT_EQ_in_term647 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term651 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_CMP_GT_in_term664 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term668 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_CMP_GT_EQ_in_term680 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term684 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_CMP_EQ_in_term697 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term701 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_CMP_NEQ_in_term713 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_atom_in_term717 = new BitSet(new long[]{0x000000001FE00002L});
    public static final BitSet FOLLOW_ID_in_term734 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_function_call754 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call756 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_args_in_function_call758 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args780 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_COMMA_in_args785 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_args_in_args789 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_NUM_in_atom812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom832 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_expression_in_atom834 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dictionary_in_atom866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_dictionary913 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary915 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_dictionary917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements938 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_TP_in_dictionary_elements940 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements944 = new BitSet(new long[]{0x0000000020000022L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements950 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_COMMA_in_dictionary_elements953 = new BitSet(new long[]{0x00000001C00008B0L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements955 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary_elements960 = new BitSet(new long[]{0x0000000020000022L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Grammar109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Grammar143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_stat_in_synpred4_Grammar168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred4_Grammar170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_synpred6_Grammar190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred9_Grammar237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred11_Grammar322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred12_Grammar331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred13_Grammar337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred14_Grammar335 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred14_Grammar337 = new BitSet(new long[]{0x00000001C000CCB0L});
    public static final BitSet FOLLOW_stat_in_synpred14_Grammar342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred15_Grammar392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred16_Grammar457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred31_Grammar785 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_args_in_synpred31_Grammar789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred39_Grammar950 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred39_Grammar953 = new BitSet(new long[]{0x00000001C00008B0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred39_Grammar955 = new BitSet(new long[]{0x00000001C0000890L});
    public static final BitSet FOLLOW_dictionary_elements_in_synpred39_Grammar960 = new BitSet(new long[]{0x0000000000000002L});

}