// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g 2011-09-23 06:16:11

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "ID", "EQUAL", "IF", "LEFT_P", "RIGHT_P", "ELSE", "WHILE", "PLUS", "MINUS", "MULT", "DIV", "COMMA", "NUM", "BOOL", "STRING_LITERAL", "TP", "DQUOTE", "LEFT_B", "RIGHT_B", "WS"
    };
    public static final int WHILE=13;
    public static final int ELSE=12;
    public static final int TP=22;
    public static final int BOOL=20;
    public static final int RIGHT_P=11;
    public static final int DQUOTE=23;
    public static final int MINUS=15;
    public static final int MULT=16;
    public static final int ID=7;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int NUM=19;
    public static final int RIGHT_CB=6;
    public static final int IF=9;
    public static final int WS=26;
    public static final int RIGHT_B=25;
    public static final int STRING_LITERAL=21;
    public static final int NEWLINE=5;
    public static final int COMMA=18;
    public static final int EQUAL=8;
    public static final int LEFT_B=24;
    public static final int PLUS=14;
    public static final int DIV=17;
    public static final int LEFT_P=10;

    // delegates
    // delegators


        public GrammarParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GrammarParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[44+1];
             
             
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

        public void treeRefactoring() {
            LinkedList <Expression> new_commands = new LinkedList();
            System.out.println("\nTREE REFACTORING");
            for (Object o : commands) {
                if (!(o instanceof Expression)) {
                    _WPAScriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                }
                new_commands.add( (Expression) ((Expression) o).getSimplifiedCalculable() );
            }
            commands = new_commands;
            System.out.println("\nTREE REFACTORING OVER");
        }

        public boolean compilationCheck() {
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

        public Object execute() {
            Object ret_val = null;
            for (Object c : commands) {
                if (!(c instanceof Expression)) {
                    _WPAScriptPanic("Top level command must be instances of Expression!", 0);
                }
                ret_val = ((Expression) c).eval();
            }
            return ret_val;
        }

        protected void _WPAScriptPanic(String message, int line_num) {

            if (!_PANIC_STATE_) {
                _PANIC_STATE_ = true;
                System.err.println("PANIC OCCURED!");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);

            dumpGlobalMemory(System.err);
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:168:1: prog : s= stats ;
    public final GrammarParser.prog_return prog() throws RecognitionException {
        GrammarParser.prog_return retval = new GrammarParser.prog_return();
        retval.start = input.LT(1);
        int prog_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.stats_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:168:6: (s= stats )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:169:5: s= stats
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:176:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:176:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:177:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block107); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:177:13: ( NEWLINE )?
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:177:28: ( NEWLINE )?
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:181:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final GrammarParser.stats_return stats() throws RecognitionException {
        GrammarParser.stats_return retval = new GrammarParser.stats_return();
        retval.start = input.LT(1);
        int stats_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.stat_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:181:51: (s= stat (s= stat )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:182:5: s= stat (s= stat )*
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:187:7: (s= stat )*
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
                else if ( (LA3_0==LEFT_CB||LA3_0==ID||(LA3_0>=IF && LA3_0<=LEFT_P)||LA3_0==WHILE||(LA3_0>=NUM && LA3_0<=STRING_LITERAL)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:187:8: s= stat
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:194:1: stat returns [Expression expr] : ( expression NEWLINE | ID EQUAL expression NEWLINE | NEWLINE | block | if_expression | while_expression );
    public final GrammarParser.stat_return stat() throws RecognitionException {
        GrammarParser.stat_return retval = new GrammarParser.stat_return();
        retval.start = input.LT(1);
        int stat_StartIndex = input.index();
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

        GrammarParser.while_expression_return while_expression15 = null;


        Object NEWLINE7_tree=null;
        Object ID8_tree=null;
        Object EQUAL9_tree=null;
        Object NEWLINE11_tree=null;
        Object NEWLINE12_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:195:5: ( expression NEWLINE | ID EQUAL expression NEWLINE | NEWLINE | block | if_expression | while_expression )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:195:7: expression NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_stat168);
                    expression6=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression6.getTree());
                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat170); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE7_tree = (Object)adaptor.create(NEWLINE7);
                    adaptor.addChild(root_0, NEWLINE7_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, (expression6!=null?expression6.expr:null));
                              line_number++;
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:199:7: ID EQUAL expression NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    ID8=(Token)match(input,ID,FOLLOW_ID_in_stat180); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID8_tree = (Object)adaptor.create(ID8);
                    adaptor.addChild(root_0, ID8_tree);
                    }
                    EQUAL9=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_stat182); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL9_tree = (Object)adaptor.create(EQUAL9);
                    adaptor.addChild(root_0, EQUAL9_tree);
                    }
                    pushFollow(FOLLOW_expression_in_stat184);
                    expression10=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression10.getTree());
                    NEWLINE11=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat186); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE11_tree = (Object)adaptor.create(NEWLINE11);
                    adaptor.addChild(root_0, NEWLINE11_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID8!=null?ID8.getText():null), (expression10!=null?expression10.expr:null)) );
                              line_number++;
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:203:7: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE12=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat196); if (state.failed) return retval;
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
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:206:7: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_stat206);
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
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:209:7: if_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_if_expression_in_stat216);
                    if_expression14=if_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_expression14.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(this, (if_expression14!=null?if_expression14.expr:null));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:212:7: while_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_while_expression_in_stat226);
                    while_expression15=while_expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, while_expression15.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(this, (while_expression15!=null?while_expression15.expr:null));
                          
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

    public static class if_expression_return extends ParserRuleReturnScope {
        public IfExpression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:217:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final GrammarParser.if_expression_return if_expression() throws RecognitionException {
        GrammarParser.if_expression_return retval = new GrammarParser.if_expression_return();
        retval.start = input.LT(1);
        int if_expression_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.pre_if_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:218:5: (p= pre_if_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:218:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression251);
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
            if ( state.backtracking>0 ) { memoize(input, 5, if_expression_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:234:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final GrammarParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        GrammarParser.pre_if_expression_return retval = new GrammarParser.pre_if_expression_return();
        retval.start = input.LT(1);
        int pre_if_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IF16=null;
        Token LEFT_P17=null;
        Token RIGHT_P18=null;
        Token NEWLINE19=null;
        Token NEWLINE20=null;
        Token ELSE21=null;
        Token NEWLINE22=null;
        GrammarParser.expression_return e = null;

        GrammarParser.stat_return s = null;


        Object IF16_tree=null;
        Object LEFT_P17_tree=null;
        Object RIGHT_P18_tree=null;
        Object NEWLINE19_tree=null;
        Object NEWLINE20_tree=null;
        Object ELSE21_tree=null;
        Object NEWLINE22_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:235:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:235:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF16=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression270); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF16_tree = (Object)adaptor.create(IF16);
            adaptor.addChild(root_0, IF16_tree);
            }
            LEFT_P17=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression272); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P17_tree = (Object)adaptor.create(LEFT_P17);
            adaptor.addChild(root_0, LEFT_P17_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression276);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P18=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression278); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P18_tree = (Object)adaptor.create(RIGHT_P18);
            adaptor.addChild(root_0, RIGHT_P18_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:235:38: ( NEWLINE )?
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE19=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression280); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE19_tree = (Object)adaptor.create(NEWLINE19);
                    adaptor.addChild(root_0, NEWLINE19_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_if_expression285);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:7: ( NEWLINE )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==NEWLINE) ) {
                int LA6_1 = input.LA(2);

                if ( (synpred10_Grammar()) ) {
                    alt6=1;
                }
            }
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE20=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression289); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE20_tree = (Object)adaptor.create(NEWLINE20);
                    adaptor.addChild(root_0, NEWLINE20_tree);
                    }

                    }
                    break;

            }

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==ELSE) ) {
                int LA8_1 = input.LA(2);

                if ( (synpred12_Grammar()) ) {
                    alt8=1;
                }
            }
            switch (alt8) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE21=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression293); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE21_tree = (Object)adaptor.create(ELSE21);
                    adaptor.addChild(root_0, ELSE21_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:22: ( NEWLINE )?
                    int alt7=2;
                    alt7 = dfa7.predict(input);
                    switch (alt7) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                            {
                            NEWLINE22=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression295); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE22_tree = (Object)adaptor.create(NEWLINE22);
                            adaptor.addChild(root_0, NEWLINE22_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression300);
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
            if ( state.backtracking>0 ) { memoize(input, 6, pre_if_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pre_if_expression"

    public static class while_expression_return extends ParserRuleReturnScope {
        public WhileExpression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "while_expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:243:1: while_expression returns [WhileExpression expr] : p= pre_while_expression ;
    public final GrammarParser.while_expression_return while_expression() throws RecognitionException {
        GrammarParser.while_expression_return retval = new GrammarParser.while_expression_return();
        retval.start = input.LT(1);
        int while_expression_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.pre_while_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:244:5: (p= pre_while_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:244:7: p= pre_while_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_while_expression_in_while_expression322);
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
                      retval.expr = new WhileExpression( this, condition, expression);
                  
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
            if ( state.backtracking>0 ) { memoize(input, 7, while_expression_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:256:1: pre_while_expression returns [LinkedList<Expression> exprs] : WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ;
    public final GrammarParser.pre_while_expression_return pre_while_expression() throws RecognitionException {
        GrammarParser.pre_while_expression_return retval = new GrammarParser.pre_while_expression_return();
        retval.start = input.LT(1);
        int pre_while_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WHILE23=null;
        Token LEFT_P24=null;
        Token RIGHT_P25=null;
        Token NEWLINE26=null;
        GrammarParser.expression_return e = null;

        GrammarParser.stat_return s = null;


        Object WHILE23_tree=null;
        Object LEFT_P24_tree=null;
        Object RIGHT_P25_tree=null;
        Object NEWLINE26_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:257:5: ( WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:257:7: WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            WHILE23=(Token)match(input,WHILE,FOLLOW_WHILE_in_pre_while_expression340); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE23_tree = (Object)adaptor.create(WHILE23);
            adaptor.addChild(root_0, WHILE23_tree);
            }
            LEFT_P24=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_while_expression342); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P24_tree = (Object)adaptor.create(LEFT_P24);
            adaptor.addChild(root_0, LEFT_P24_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_while_expression346);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P25=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_while_expression348); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P25_tree = (Object)adaptor.create(RIGHT_P25);
            adaptor.addChild(root_0, RIGHT_P25_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:257:41: ( NEWLINE )?
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                    {
                    NEWLINE26=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_while_expression350); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE26_tree = (Object)adaptor.create(NEWLINE26);
                    adaptor.addChild(root_0, NEWLINE26_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_while_expression355);
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
            if ( state.backtracking>0 ) { memoize(input, 8, pre_while_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pre_while_expression"

    public static class expression_return extends ParserRuleReturnScope {
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:263:1: expression returns [Expression expr] : ( terms | function_call );
    public final GrammarParser.expression_return expression() throws RecognitionException {
        GrammarParser.expression_return retval = new GrammarParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        GrammarParser.terms_return terms27 = null;

        GrammarParser.function_call_return function_call28 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:264:5: ( terms | function_call )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==LEFT_CB||LA10_0==LEFT_P||(LA10_0>=NUM && LA10_0<=STRING_LITERAL)) ) {
                alt10=1;
            }
            else if ( (LA10_0==ID) ) {
                int LA10_2 = input.LA(2);

                if ( (LA10_2==LEFT_P) ) {
                    alt10=2;
                }
                else if ( (LA10_2==EOF||(LA10_2>=NEWLINE && LA10_2<=RIGHT_CB)||LA10_2==RIGHT_P||(LA10_2>=PLUS && LA10_2<=COMMA)||LA10_2==TP) ) {
                    alt10=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:264:7: terms
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_terms_in_expression373);
                    terms27=terms();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, terms27.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new Term(this, (terms27!=null?terms27.terms:null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:267:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_expression383);
                    function_call28=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call28.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new FunctionCall( this, (function_call28!=null?function_call28.name_params:null) ) );
                          
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
            if ( state.backtracking>0 ) { memoize(input, 9, expression_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:271:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term )* ;
    public final GrammarParser.terms_return terms() throws RecognitionException {
        GrammarParser.terms_return retval = new GrammarParser.terms_return();
        retval.start = input.LT(1);
        int terms_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS29=null;
        Token MINUS30=null;
        GrammarParser.term_return t = null;


        Object PLUS29_tree=null;
        Object MINUS30_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:272:5: (t= term ( PLUS t= term | MINUS t= term )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:272:7: t= term ( PLUS t= term | MINUS t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms403);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:275:7: ( PLUS t= term | MINUS t= term )*
            loop11:
            do {
                int alt11=3;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==PLUS) ) {
                    alt11=1;
                }
                else if ( (LA11_0==MINUS) ) {
                    alt11=2;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:275:9: PLUS t= term
            	    {
            	    PLUS29=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms409); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS29_tree = (Object)adaptor.create(PLUS29);
            	    adaptor.addChild(root_0, PLUS29_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms413);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:279:9: MINUS t= term
            	    {
            	    MINUS30=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms425); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS30_tree = (Object)adaptor.create(MINUS30);
            	    adaptor.addChild(root_0, MINUS30_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms429);
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, terms_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:285:1: term returns [LinkedList<Object> atoms] : a= atom ( MULT a= atom | DIV a= atom )* ;
    public final GrammarParser.term_return term() throws RecognitionException {
        GrammarParser.term_return retval = new GrammarParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token MULT31=null;
        Token DIV32=null;
        GrammarParser.atom_return a = null;


        Object MULT31_tree=null;
        Object DIV32_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:286:5: (a= atom ( MULT a= atom | DIV a= atom )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:286:7: a= atom ( MULT a= atom | DIV a= atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_term453);
            a=atom();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.atoms = new LinkedList();
                      retval.atoms.add((a!=null?a.value:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:289:7: ( MULT a= atom | DIV a= atom )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==MULT) ) {
                    alt12=1;
                }
                else if ( (LA12_0==DIV) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:289:9: MULT a= atom
            	    {
            	    MULT31=(Token)match(input,MULT,FOLLOW_MULT_in_term459); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MULT31_tree = (Object)adaptor.create(MULT31);
            	    adaptor.addChild(root_0, MULT31_tree);
            	    }
            	    pushFollow(FOLLOW_atom_in_term463);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:293:9: DIV a= atom
            	    {
            	    DIV32=(Token)match(input,DIV,FOLLOW_DIV_in_term475); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DIV32_tree = (Object)adaptor.create(DIV32);
            	    adaptor.addChild(root_0, DIV32_tree);
            	    }
            	    pushFollow(FOLLOW_atom_in_term479);
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

            catch (RecognitionException rec_exc) {
                throw rec_exc;
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, term_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:300:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
    public final GrammarParser.function_call_return function_call() throws RecognitionException {
        GrammarParser.function_call_return retval = new GrammarParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token ID33=null;
        Token LEFT_P34=null;
        Token RIGHT_P36=null;
        GrammarParser.args_return args35 = null;


        Object ID33_tree=null;
        Object LEFT_P34_tree=null;
        Object RIGHT_P36_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:300:55: ( ID LEFT_P args RIGHT_P )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:301:5: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID33=(Token)match(input,ID,FOLLOW_ID_in_function_call504); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID33_tree = (Object)adaptor.create(ID33);
            adaptor.addChild(root_0, ID33_tree);
            }
            LEFT_P34=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call506); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P34_tree = (Object)adaptor.create(LEFT_P34);
            adaptor.addChild(root_0, LEFT_P34_tree);
            }
            pushFollow(FOLLOW_args_in_function_call508);
            args35=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, args35.getTree());
            RIGHT_P36=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call510); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P36_tree = (Object)adaptor.create(RIGHT_P36);
            adaptor.addChild(root_0, RIGHT_P36_tree);
            }
            if ( state.backtracking==0 ) {
                
                      retval.name_params = (args35!=null?args35.params:null);
                      retval.name_params.add(0, (ID33!=null?ID33.getText():null));
                  
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
            if ( state.backtracking>0 ) { memoize(input, 12, function_call_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:307:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final GrammarParser.args_return args() throws RecognitionException {
        GrammarParser.args_return retval = new GrammarParser.args_return();
        retval.start = input.LT(1);
        int args_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA37=null;
        GrammarParser.expression_return a = null;

        GrammarParser.args_return b = null;


        Object COMMA37_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:307:41: (a= expression ( COMMA b= args )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:308:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args530);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:311:7: ( COMMA b= args )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==COMMA) ) {
                    int LA13_2 = input.LA(2);

                    if ( (synpred19_Grammar()) ) {
                        alt13=1;
                    }


                }


                switch (alt13) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:311:8: COMMA b= args
            	    {
            	    COMMA37=(Token)match(input,COMMA,FOLLOW_COMMA_in_args535); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA37_tree = (Object)adaptor.create(COMMA37);
            	    adaptor.addChild(root_0, COMMA37_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args539);
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
            if ( state.backtracking>0 ) { memoize(input, 13, args_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:320:1: atom returns [Object value] : ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary );
    public final GrammarParser.atom_return atom() throws RecognitionException {
        GrammarParser.atom_return retval = new GrammarParser.atom_return();
        retval.start = input.LT(1);
        int atom_StartIndex = input.index();
        Object root_0 = null;

        Token NUM38=null;
        Token BOOL39=null;
        Token LEFT_P40=null;
        Token RIGHT_P42=null;
        Token ID43=null;
        GrammarParser.expression_return expression41 = null;

        GrammarParser.string_literal_return string_literal44 = null;

        GrammarParser.dictionary_return dictionary45 = null;


        Object NUM38_tree=null;
        Object BOOL39_tree=null;
        Object LEFT_P40_tree=null;
        Object RIGHT_P42_tree=null;
        Object ID43_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:321:5: ( NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary )
            int alt14=6;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt14=1;
                }
                break;
            case BOOL:
                {
                alt14=2;
                }
                break;
            case LEFT_P:
                {
                alt14=3;
                }
                break;
            case ID:
                {
                alt14=4;
                }
                break;
            case STRING_LITERAL:
                {
                alt14=5;
                }
                break;
            case LEFT_CB:
                {
                alt14=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:321:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM38=(Token)match(input,NUM,FOLLOW_NUM_in_atom562); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM38_tree = (Object)adaptor.create(NUM38);
                    adaptor.addChild(root_0, NUM38_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Float.parseFloat((NUM38!=null?NUM38.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:324:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL39=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom572); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL39_tree = (Object)adaptor.create(BOOL39);
                    adaptor.addChild(root_0, BOOL39_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL39!=null?BOOL39.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL39!=null?BOOL39.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              } else {
                                  _WPAScriptPanic("Token [" + (BOOL39!=null?BOOL39.getText():null) + "] must be equal to \"true\" or \"false\" (boolean type)", line_number);
                              }
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:333:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P40=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom582); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P40_tree = (Object)adaptor.create(LEFT_P40);
                    adaptor.addChild(root_0, LEFT_P40_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom584);
                    expression41=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression41.getTree());
                    RIGHT_P42=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom586); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P42_tree = (Object)adaptor.create(RIGHT_P42);
                    adaptor.addChild(root_0, RIGHT_P42_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression41!=null?expression41.expr:null);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:336:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID43=(Token)match(input,ID,FOLLOW_ID_in_atom596); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID43_tree = (Object)adaptor.create(ID43);
                    adaptor.addChild(root_0, ID43_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID43!=null?ID43.getText():null));
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:339:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom606);
                    string_literal44=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal44.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal44!=null?string_literal44.value:null);
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:342:7: dictionary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dictionary_in_atom616);
                    dictionary45=dictionary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary45.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (dictionary45!=null?dictionary45.value:null);
                          
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
            if ( state.backtracking>0 ) { memoize(input, 14, atom_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:354:1: string_literal returns [CharString value] : s= STRING_LITERAL ;
    public final GrammarParser.string_literal_return string_literal() throws RecognitionException {
        GrammarParser.string_literal_return retval = new GrammarParser.string_literal_return();
        retval.start = input.LT(1);
        int string_literal_StartIndex = input.index();
        Object root_0 = null;

        Token s=null;

        Object s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:354:43: (s= STRING_LITERAL )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:354:45: s= STRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_string_literal644); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 15, string_literal_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:359:1: dictionary returns [Dictionary value] : LEFT_CB dictionary_elements RIGHT_CB ;
    public final GrammarParser.dictionary_return dictionary() throws RecognitionException {
        GrammarParser.dictionary_return retval = new GrammarParser.dictionary_return();
        retval.start = input.LT(1);
        int dictionary_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB46=null;
        Token RIGHT_CB48=null;
        GrammarParser.dictionary_elements_return dictionary_elements47 = null;


        Object LEFT_CB46_tree=null;
        Object RIGHT_CB48_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:359:39: ( LEFT_CB dictionary_elements RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:360:5: LEFT_CB dictionary_elements RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB46=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_dictionary663); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB46_tree = (Object)adaptor.create(LEFT_CB46);
            adaptor.addChild(root_0, LEFT_CB46_tree);
            }
            pushFollow(FOLLOW_dictionary_elements_in_dictionary665);
            dictionary_elements47=dictionary_elements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary_elements47.getTree());
            RIGHT_CB48=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_dictionary667); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB48_tree = (Object)adaptor.create(RIGHT_CB48);
            adaptor.addChild(root_0, RIGHT_CB48_tree);
            }
            if ( state.backtracking==0 ) {

                      HashMap vs = new HashMap();
                      int size = (dictionary_elements47!=null?dictionary_elements47.keys_values:null).size();
                      if (size==0 || size%2!=0) {
                          _WPAScriptPanic("Dictionary must have a even number of elements!", line_number);
                      }
                      for (int k=0; k<size; k+=2) {
                          vs.put((dictionary_elements47!=null?dictionary_elements47.keys_values:null).get(k), (dictionary_elements47!=null?dictionary_elements47.keys_values:null).get(k+1));
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
            if ( state.backtracking>0 ) { memoize(input, 16, dictionary_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:372:1: dictionary_elements returns [LinkedList<Object> keys_values] : (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* ;
    public final GrammarParser.dictionary_elements_return dictionary_elements() throws RecognitionException {
        GrammarParser.dictionary_elements_return retval = new GrammarParser.dictionary_elements_return();
        retval.start = input.LT(1);
        int dictionary_elements_StartIndex = input.index();
        Object root_0 = null;

        Token TP49=null;
        Token NEWLINE50=null;
        Token COMMA51=null;
        Token NEWLINE52=null;
        GrammarParser.expression_return e1 = null;

        GrammarParser.expression_return e2 = null;

        GrammarParser.dictionary_elements_return d = null;


        Object TP49_tree=null;
        Object NEWLINE50_tree=null;
        Object COMMA51_tree=null;
        Object NEWLINE52_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:372:62: ( (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:373:5: (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:373:5: (e1= expression TP e2= expression )
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:373:6: e1= expression TP e2= expression
            {
            pushFollow(FOLLOW_expression_in_dictionary_elements688);
            e1=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
            TP49=(Token)match(input,TP,FOLLOW_TP_in_dictionary_elements690); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TP49_tree = (Object)adaptor.create(TP49);
            adaptor.addChild(root_0, TP49_tree);
            }
            pushFollow(FOLLOW_expression_in_dictionary_elements694);
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
            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:7: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==NEWLINE) ) {
                    int LA17_2 = input.LA(2);

                    if ( (synpred27_Grammar()) ) {
                        alt17=1;
                    }


                }
                else if ( (LA17_0==COMMA) ) {
                    int LA17_3 = input.LA(2);

                    if ( (synpred27_Grammar()) ) {
                        alt17=1;
                    }


                }


                switch (alt17) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
            	    {
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:8: ( NEWLINE )?
            	    int alt15=2;
            	    int LA15_0 = input.LA(1);

            	    if ( (LA15_0==NEWLINE) ) {
            	        alt15=1;
            	    }
            	    switch (alt15) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
            	            {
            	            NEWLINE50=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements700); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE50_tree = (Object)adaptor.create(NEWLINE50);
            	            adaptor.addChild(root_0, NEWLINE50_tree);
            	            }

            	            }
            	            break;

            	    }

            	    COMMA51=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictionary_elements703); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA51_tree = (Object)adaptor.create(COMMA51);
            	    adaptor.addChild(root_0, COMMA51_tree);
            	    }
            	    // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:23: ( NEWLINE )?
            	    int alt16=2;
            	    int LA16_0 = input.LA(1);

            	    if ( (LA16_0==NEWLINE) ) {
            	        alt16=1;
            	    }
            	    switch (alt16) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
            	            {
            	            NEWLINE52=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements705); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE52_tree = (Object)adaptor.create(NEWLINE52);
            	            adaptor.addChild(root_0, NEWLINE52_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_dictionary_elements_in_dictionary_elements710);
            	    d=dictionary_elements();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
            	    if ( state.backtracking==0 ) {

            	                  if ((d!=null?d.keys_values:null)==null) {
            	                      _WPAScriptPanic("Dictionary elements sublist is null!", line_number);
            	                  }
            	                  for (int k=0; k<(d!=null?d.keys_values:null).size(); k++) {
            	                      retval.keys_values.add((d!=null?d.keys_values:null).get(k));
            	                  }
            	              
            	    }

            	    }
            	    break;

            	default :
            	    break loop17;
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
            if ( state.backtracking>0 ) { memoize(input, 17, dictionary_elements_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dictionary_elements"

    // $ANTLR start synpred1_Grammar
    public final void synpred1_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:177:13: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:177:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Grammar109); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Grammar

    // $ANTLR start synpred3_Grammar
    public final void synpred3_Grammar_fragment() throws RecognitionException {   
        GrammarParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:187:8: (s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:187:8: s= stat
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
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:195:7: ( expression NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:195:7: expression NEWLINE
        {
        pushFollow(FOLLOW_expression_in_synpred4_Grammar168);
        expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred4_Grammar170); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Grammar

    // $ANTLR start synpred5_Grammar
    public final void synpred5_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:199:7: ( ID EQUAL expression NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:199:7: ID EQUAL expression NEWLINE
        {
        match(input,ID,FOLLOW_ID_in_synpred5_Grammar180); if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred5_Grammar182); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred5_Grammar184);
        expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred5_Grammar186); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Grammar

    // $ANTLR start synpred7_Grammar
    public final void synpred7_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:206:7: ( block )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:206:7: block
        {
        pushFollow(FOLLOW_block_in_synpred7_Grammar206);
        block();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_Grammar

    // $ANTLR start synpred9_Grammar
    public final void synpred9_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:235:38: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:235:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred9_Grammar280); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Grammar

    // $ANTLR start synpred10_Grammar
    public final void synpred10_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:7: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred10_Grammar289); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_Grammar

    // $ANTLR start synpred11_Grammar
    public final void synpred11_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:22: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred11_Grammar295); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Grammar

    // $ANTLR start synpred12_Grammar
    public final void synpred12_Grammar_fragment() throws RecognitionException {   
        GrammarParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:17: ( ELSE ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred12_Grammar293); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:239:22: ( NEWLINE )?
        int alt18=2;
        int LA18_0 = input.LA(1);

        if ( (LA18_0==NEWLINE) ) {
            int LA18_1 = input.LA(2);

            if ( ((LA18_1>=LEFT_CB && LA18_1<=NEWLINE)||LA18_1==ID||(LA18_1>=IF && LA18_1<=LEFT_P)||LA18_1==WHILE||(LA18_1>=NUM && LA18_1<=STRING_LITERAL)) ) {
                alt18=1;
            }
        }
        switch (alt18) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred12_Grammar295); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred12_Grammar300);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_Grammar

    // $ANTLR start synpred13_Grammar
    public final void synpred13_Grammar_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:257:41: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:257:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred13_Grammar350); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Grammar

    // $ANTLR start synpred19_Grammar
    public final void synpred19_Grammar_fragment() throws RecognitionException {   
        GrammarParser.args_return b = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:311:8: ( COMMA b= args )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:311:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred19_Grammar535); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred19_Grammar539);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Grammar

    // $ANTLR start synpred27_Grammar
    public final void synpred27_Grammar_fragment() throws RecognitionException {   
        GrammarParser.dictionary_elements_return d = null;


        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:8: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
        {
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:8: ( NEWLINE )?
        int alt19=2;
        int LA19_0 = input.LA(1);

        if ( (LA19_0==NEWLINE) ) {
            alt19=1;
        }
        switch (alt19) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred27_Grammar700); if (state.failed) return ;

                }
                break;

        }

        match(input,COMMA,FOLLOW_COMMA_in_synpred27_Grammar703); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:377:23: ( NEWLINE )?
        int alt20=2;
        int LA20_0 = input.LA(1);

        if ( (LA20_0==NEWLINE) ) {
            alt20=1;
        }
        switch (alt20) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/lexicalparser/Grammar.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred27_Grammar705); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_dictionary_elements_in_synpred27_Grammar710);
        d=dictionary_elements();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_Grammar

    // Delegated rules

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
    public final boolean synpred19_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Grammar_fragment(); // can never throw exception
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
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA1_eotS =
        "\13\uffff";
    static final String DFA1_eofS =
        "\13\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\11\uffff";
    static final String DFA1_maxS =
        "\1\25\1\0\11\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\7\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\11\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\2\2\2\uffff\1\2\5\uffff\3\2",
            "\1\uffff",
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
            return "177:13: ( NEWLINE )?";
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
                        if ( (synpred1_Grammar()) ) {s = 10;}

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
        "\1\4\3\uffff\1\0\1\uffff\1\0\5\uffff";
    static final String DFA4_maxS =
        "\1\25\3\uffff\1\0\1\uffff\1\0\5\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\5\uffff\1\3\1\5\1\6\1\2\1\4";
    static final String DFA4_specialS =
        "\4\uffff\1\0\1\uffff\1\1\5\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\6\1\7\1\uffff\1\4\1\uffff\1\10\1\1\2\uffff\1\11\5\uffff\3"+
            "\1",
            "",
            "",
            "",
            "\1\uffff",
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
            return "194:1: stat returns [Expression expr] : ( expression NEWLINE | ID EQUAL expression NEWLINE | NEWLINE | block | if_expression | while_expression );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_4 = input.LA(1);

                         
                        int index4_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Grammar()) ) {s = 1;}

                        else if ( (synpred5_Grammar()) ) {s = 10;}

                         
                        input.seek(index4_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_6 = input.LA(1);

                         
                        int index4_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Grammar()) ) {s = 1;}

                        else if ( (synpred7_Grammar()) ) {s = 11;}

                         
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
    static final String DFA5_eotS =
        "\13\uffff";
    static final String DFA5_eofS =
        "\13\uffff";
    static final String DFA5_minS =
        "\1\4\1\0\11\uffff";
    static final String DFA5_maxS =
        "\1\25\1\0\11\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\2\7\uffff\1\1";
    static final String DFA5_specialS =
        "\1\uffff\1\0\11\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\2\2\2\uffff\1\2\5\uffff\3\2",
            "\1\uffff",
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
            return "235:38: ( NEWLINE )?";
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
                        if ( (synpred9_Grammar()) ) {s = 10;}

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
        "\13\uffff";
    static final String DFA7_eofS =
        "\13\uffff";
    static final String DFA7_minS =
        "\1\4\1\0\11\uffff";
    static final String DFA7_maxS =
        "\1\25\1\0\11\uffff";
    static final String DFA7_acceptS =
        "\2\uffff\1\2\7\uffff\1\1";
    static final String DFA7_specialS =
        "\1\uffff\1\0\11\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\2\2\2\uffff\1\2\5\uffff\3\2",
            "\1\uffff",
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
            return "239:22: ( NEWLINE )?";
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
                        if ( (synpred11_Grammar()) ) {s = 10;}

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
    static final String DFA9_eotS =
        "\13\uffff";
    static final String DFA9_eofS =
        "\13\uffff";
    static final String DFA9_minS =
        "\1\4\1\0\11\uffff";
    static final String DFA9_maxS =
        "\1\25\1\0\11\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\2\7\uffff\1\1";
    static final String DFA9_specialS =
        "\1\uffff\1\0\11\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\2\2\2\uffff\1\2\5\uffff\3\2",
            "\1\uffff",
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

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "257:41: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_1 = input.LA(1);

                         
                        int index9_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Grammar()) ) {s = 10;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index9_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_stats_in_prog90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_block107 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_NEWLINE_in_block109 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_stats_in_block112 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block114 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats136 = new BitSet(new long[]{0x00000000003826B2L});
    public static final BitSet FOLLOW_stat_in_stats143 = new BitSet(new long[]{0x00000000003826B2L});
    public static final BitSet FOLLOW_expression_in_stat168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat180 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_EQUAL_in_stat182 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_expression_in_stat184 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_expression_in_stat226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression270 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression272 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression276 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression278 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression280 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression285 = new BitSet(new long[]{0x0000000000001022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression289 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression293 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression295 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_while_expression_in_while_expression322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_pre_while_expression340 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_while_expression342 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_expression_in_pre_while_expression346 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_while_expression348 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_while_expression350 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_stat_in_pre_while_expression355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_terms_in_expression373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms403 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_PLUS_in_terms409 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_term_in_terms413 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_MINUS_in_terms425 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_term_in_terms429 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_atom_in_term453 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_MULT_in_term459 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_atom_in_term463 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_DIV_in_term475 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_atom_in_term479 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_ID_in_function_call504 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call506 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_args_in_function_call508 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args530 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_COMMA_in_args535 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_args_in_args539 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_NUM_in_atom562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom582 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_expression_in_atom584 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dictionary_in_atom616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_dictionary663 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary665 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_dictionary667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements688 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_TP_in_dictionary_elements690 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements694 = new BitSet(new long[]{0x0000000000040022L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements700 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COMMA_in_dictionary_elements703 = new BitSet(new long[]{0x00000000003804B0L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements705 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary_elements710 = new BitSet(new long[]{0x0000000000040022L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Grammar109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Grammar143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred4_Grammar168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred4_Grammar170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred5_Grammar180 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_EQUAL_in_synpred5_Grammar182 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_expression_in_synpred5_Grammar184 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred5_Grammar186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_synpred7_Grammar206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred9_Grammar280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred10_Grammar289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred11_Grammar295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred12_Grammar293 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred12_Grammar295 = new BitSet(new long[]{0x00000000003826B0L});
    public static final BitSet FOLLOW_stat_in_synpred12_Grammar300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred13_Grammar350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred19_Grammar535 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_args_in_synpred19_Grammar539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred27_Grammar700 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COMMA_in_synpred27_Grammar703 = new BitSet(new long[]{0x00000000003804B0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred27_Grammar705 = new BitSet(new long[]{0x0000000000380490L});
    public static final BitSet FOLLOW_dictionary_elements_in_synpred27_Grammar710 = new BitSet(new long[]{0x0000000000000002L});

}