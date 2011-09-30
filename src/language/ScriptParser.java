// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/language/Script.g 2011-09-30 09:07:07

package language;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.HashMap;
import language.executable.*;
import language.executable.builtintypes.*;
import language.exceptions.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class ScriptParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "BREAK", "ID", "EQUAL", "PLUS_PLUS", "MINUS_MINUS", "IF", "LEFT_P", "RIGHT_P", "ELSE", "WHILE", "FOR", "PV", "ARROW", "PLUS", "MINUS", "AND", "OR", "MULT", "DIV", "CMP_LT", "CMP_LT_EQ", "CMP_GT", "CMP_GT_EQ", "CMP_EQ", "CMP_NEQ", "COMMA", "NUM", "BOOL", "STRING_LITERAL", "TP", "CONTINUE", "DQUOTE", "LEFT_B", "RIGHT_B", "WS"
    };
    public static final int CMP_NEQ=31;
    public static final int WHILE=16;
    public static final int ELSE=15;
    public static final int TP=36;
    public static final int BOOL=34;
    public static final int RIGHT_P=14;
    public static final int DQUOTE=38;
    public static final int FOR=17;
    public static final int CMP_EQ=30;
    public static final int MULT=24;
    public static final int MINUS=21;
    public static final int AND=22;
    public static final int ID=8;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int CMP_GT=28;
    public static final int BREAK=7;
    public static final int NUM=33;
    public static final int RIGHT_CB=6;
    public static final int IF=12;
    public static final int CMP_GT_EQ=29;
    public static final int CMP_LT=26;
    public static final int WS=41;
    public static final int RIGHT_B=40;
    public static final int STRING_LITERAL=35;
    public static final int PLUS_PLUS=10;
    public static final int NEWLINE=5;
    public static final int CONTINUE=37;
    public static final int COMMA=32;
    public static final int CMP_LT_EQ=27;
    public static final int EQUAL=9;
    public static final int OR=23;
    public static final int LEFT_B=39;
    public static final int PV=18;
    public static final int ARROW=19;
    public static final int PLUS=20;
    public static final int DIV=25;
    public static final int LEFT_P=13;
    public static final int MINUS_MINUS=11;

    // delegates
    // delegators


        public ScriptParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ScriptParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[71+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ScriptParser.tokenNames; }
    public String getGrammarFileName() { return "/home/laurent/dev/WPAScript/working_copy/src/language/Script.g"; }



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
        public HashMap memory = new HashMap();
        public HashMap compilation_memory =  new HashMap();
        private LinkedList <Expression> commands = new LinkedList();
        private int line_number = 1;
        public int getLineNumber() {
            return line_number;
        }

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
                    scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                }
                Calculable new_command = ((Expression) o).getSimplifiedCalculable();
                if (new_command instanceof Expression) {
                    new_commands.add( (Expression) new_command );
                } else {
                    scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                }
            }
            commands = new_commands;
            System.out.println("\nTREE REFACTORING OVER");
        }

        public boolean compilationCheck() throws PanicException, CompilationErrorException {
            try {
                compilation_memory.clear();
                System.out.println("\nCOMPILATION CHECK");
                for (Object o : commands) {
                    if (!(o instanceof Expression)) {
                        scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                    }
                    ((Expression) o).compilationCheck();
                }
                compilation_memory.clear();
                System.out.println("\nCOMPILATION OK");
                return true;
            } catch (CompilationErrorException e) {
                compilationError(e.getMessage(), e.getLineNumber());
                return false;
            }
        }

        public Object execute() throws PanicException, RuntimeErrorException {
            Object ret_val = null;
            for (Object c : commands) {
                if (!(c instanceof Expression)) {
                    scriptPanic("Top level command must be instances of Expression!", 0);
                }
                ret_val = ((Expression) c).eval();
            }
            return ret_val;
        }

        public void scriptPanic(String message, int line_num) throws PanicException {

            if (!_PANIC_STATE_) {
                _PANIC_STATE_ = true;
                System.err.println("PANIC OCCURED!");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);

            dumpGlobalMemory(System.err);
            throw new PanicException("Panic: " + "ERROR (l" + line_num + "):: " + message, line_num);
        }


        public void compilationError(String message, int line_num) throws CompilationErrorException {
            if (!_COMPILATION_ERROR_STATE_) {
                _COMPILATION_ERROR_STATE_ = true;
                System.err.println("COMPILATION");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);
            throw new CompilationErrorException("Compilation error: " + "ERROR (l" + line_num + "):: " + message, line_number);
        }


        public void compilationWarning(String message, int line_num) {
            if (!_COMPILATION_ERROR_STATE_) {
                _COMPILATION_ERROR_STATE_ = true;
                System.err.println("COMPILATION");
            }
            System.err.println("WARNING (l" + line_num + "):: " + message);
        }

        public void runtimeError(String message, int line_num) throws RuntimeErrorException {
            if (!_RUNTIME_ERROR_STATE_) {
                _RUNTIME_ERROR_STATE_ = true;
                System.err.println("RUNTIME");
            }
            dumpGlobalMemory(System.err);
            System.err.println("ERROR (l" + line_num + "):: " + message);
            throw new RuntimeErrorException("Runtime error: " + "ERROR (l" + line_num + "):: " + message, line_num);
        }

        public void runtimeWarning(String message, int line_num) {
            if (!_RUNTIME_ERROR_STATE_) {
                _RUNTIME_ERROR_STATE_ = true;
                System.err.println("RUNTIME");
            }
            System.err.println("WARNING (l" + line_num + "):: " + message);
        }

        public static void main(String[] args) throws Exception {

            ScriptLexer lex = new ScriptLexer(new ANTLRStringStream(args[0]));
            CommonTokenStream tokens = new CommonTokenStream(lex);

            ScriptParser parser = new ScriptParser(tokens);

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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:189:1: prog : s= stats ;
    public final ScriptParser.prog_return prog() throws RecognitionException {
        ScriptParser.prog_return retval = new ScriptParser.prog_return();
        retval.start = input.LT(1);
        int prog_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stats_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:189:6: (s= stats )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:190:5: s= stats
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:197:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
    public final ScriptParser.block_return block() throws RecognitionException {
        ScriptParser.block_return retval = new ScriptParser.block_return();
        retval.start = input.LT(1);
        int block_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB1=null;
        Token NEWLINE2=null;
        Token NEWLINE4=null;
        Token RIGHT_CB5=null;
        ScriptParser.stats_return stats3 = null;


        Object LEFT_CB1_tree=null;
        Object NEWLINE2_tree=null;
        Object NEWLINE4_tree=null;
        Object RIGHT_CB5_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:197:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:198:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block107); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:198:13: ( NEWLINE )?
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:198:28: ( NEWLINE )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NEWLINE) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:202:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final ScriptParser.stats_return stats() throws RecognitionException {
        ScriptParser.stats_return retval = new ScriptParser.stats_return();
        retval.start = input.LT(1);
        int stats_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stat_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:202:51: (s= stat (s= stat )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:203:5: s= stat (s= stat )*
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:208:7: (s= stat )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==NEWLINE) ) {
                    int LA3_2 = input.LA(2);

                    if ( (synpred3_Script()) ) {
                        alt3=1;
                    }


                }
                else if ( (LA3_0==LEFT_CB||(LA3_0>=BREAK && LA3_0<=ID)||(LA3_0>=PLUS_PLUS && LA3_0<=LEFT_P)||(LA3_0>=WHILE && LA3_0<=FOR)||LA3_0==MINUS||(LA3_0>=NUM && LA3_0<=STRING_LITERAL)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:208:8: s= stat
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:215:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );
    public final ScriptParser.stat_return stat() throws RecognitionException {
        ScriptParser.stat_return retval = new ScriptParser.stat_return();
        retval.start = input.LT(1);
        int stat_StartIndex = input.index();
        Object root_0 = null;

        Token NEWLINE7=null;
        Token NEWLINE8=null;
        ScriptParser.pre_stat_return pre_stat6 = null;

        ScriptParser.block_return block9 = null;

        ScriptParser.if_expression_return if_expression10 = null;

        ScriptParser.while_expression_return while_expression11 = null;

        ScriptParser.for_expression_return for_expression12 = null;


        Object NEWLINE7_tree=null;
        Object NEWLINE8_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:216:5: ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:216:7: pre_stat NEWLINE
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:220:7: NEWLINE
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:223:7: block
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:226:7: if_expression
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:229:7: while_expression
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:232:7: for_expression
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:235:1: pre_stat returns [Expression expr] : ( expression | BREAK | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.pre_stat_return pre_stat() throws RecognitionException {
        ScriptParser.pre_stat_return retval = new ScriptParser.pre_stat_return();
        retval.start = input.LT(1);
        int pre_stat_StartIndex = input.index();
        Object root_0 = null;

        Token BREAK14=null;
        Token ID15=null;
        Token EQUAL16=null;
        Token ID18=null;
        Token PLUS_PLUS19=null;
        Token PLUS_PLUS20=null;
        Token ID21=null;
        Token ID22=null;
        Token MINUS_MINUS23=null;
        Token MINUS_MINUS24=null;
        Token ID25=null;
        ScriptParser.expression_return expression13 = null;

        ScriptParser.expression_return expression17 = null;


        Object BREAK14_tree=null;
        Object ID15_tree=null;
        Object EQUAL16_tree=null;
        Object ID18_tree=null;
        Object PLUS_PLUS19_tree=null;
        Object PLUS_PLUS20_tree=null;
        Object ID21_tree=null;
        Object ID22_tree=null;
        Object MINUS_MINUS23_tree=null;
        Object MINUS_MINUS24_tree=null;
        Object ID25_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:236:5: ( expression | BREAK | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
            int alt5=7;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:236:7: expression
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:239:7: BREAK
                    {
                    root_0 = (Object)adaptor.nil();

                    BREAK14=(Token)match(input,BREAK,FOLLOW_BREAK_in_pre_stat247); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BREAK14_tree = (Object)adaptor.create(BREAK14);
                    adaptor.addChild(root_0, BREAK14_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, ReturnValue.RETURN_BREAK);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:242:7: ID EQUAL expression
                    {
                    root_0 = (Object)adaptor.nil();

                    ID15=(Token)match(input,ID,FOLLOW_ID_in_pre_stat257); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID15_tree = (Object)adaptor.create(ID15);
                    adaptor.addChild(root_0, ID15_tree);
                    }
                    EQUAL16=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_stat259); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL16_tree = (Object)adaptor.create(EQUAL16);
                    adaptor.addChild(root_0, EQUAL16_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_stat261);
                    expression17=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression17.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new VariableAssignment(this, (ID15!=null?ID15.getText():null), (expression17!=null?expression17.expr:null)) );
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:245:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID18=(Token)match(input,ID,FOLLOW_ID_in_pre_stat271); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID18_tree = (Object)adaptor.create(ID18);
                    adaptor.addChild(root_0, ID18_tree);
                    }
                    PLUS_PLUS19=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat273); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS19_tree = (Object)adaptor.create(PLUS_PLUS19);
                    adaptor.addChild(root_0, PLUS_PLUS19_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new VariableAssignment(this, (ID18!=null?ID18.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:248:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS20=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat283); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS20_tree = (Object)adaptor.create(PLUS_PLUS20);
                    adaptor.addChild(root_0, PLUS_PLUS20_tree);
                    }
                    ID21=(Token)match(input,ID,FOLLOW_ID_in_pre_stat285); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID21_tree = (Object)adaptor.create(ID21);
                    adaptor.addChild(root_0, ID21_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID21!=null?ID21.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:251:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID22=(Token)match(input,ID,FOLLOW_ID_in_pre_stat295); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID22_tree = (Object)adaptor.create(ID22);
                    adaptor.addChild(root_0, ID22_tree);
                    }
                    MINUS_MINUS23=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat297); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS23_tree = (Object)adaptor.create(MINUS_MINUS23);
                    adaptor.addChild(root_0, MINUS_MINUS23_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID22!=null?ID22.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
                    }

                    }
                    break;
                case 7 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:254:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS24=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat307); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS24_tree = (Object)adaptor.create(MINUS_MINUS24);
                    adaptor.addChild(root_0, MINUS_MINUS24_tree);
                    }
                    ID25=(Token)match(input,ID,FOLLOW_ID_in_pre_stat309); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID25_tree = (Object)adaptor.create(ID25);
                    adaptor.addChild(root_0, ID25_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID25!=null?ID25.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:260:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final ScriptParser.if_expression_return if_expression() throws RecognitionException {
        ScriptParser.if_expression_return retval = new ScriptParser.if_expression_return();
        retval.start = input.LT(1);
        int if_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_if_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:261:5: (p= pre_if_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:261:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression339);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:277:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final ScriptParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        ScriptParser.pre_if_expression_return retval = new ScriptParser.pre_if_expression_return();
        retval.start = input.LT(1);
        int pre_if_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IF26=null;
        Token LEFT_P27=null;
        Token RIGHT_P28=null;
        Token NEWLINE29=null;
        Token NEWLINE30=null;
        Token ELSE31=null;
        Token NEWLINE32=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object IF26_tree=null;
        Object LEFT_P27_tree=null;
        Object RIGHT_P28_tree=null;
        Object NEWLINE29_tree=null;
        Object NEWLINE30_tree=null;
        Object ELSE31_tree=null;
        Object NEWLINE32_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF26=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression358); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF26_tree = (Object)adaptor.create(IF26);
            adaptor.addChild(root_0, IF26_tree);
            }
            LEFT_P27=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression360); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P27_tree = (Object)adaptor.create(LEFT_P27);
            adaptor.addChild(root_0, LEFT_P27_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression364);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P28=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression366); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P28_tree = (Object)adaptor.create(RIGHT_P28);
            adaptor.addChild(root_0, RIGHT_P28_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:38: ( NEWLINE )?
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE29=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression368); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE29_tree = (Object)adaptor.create(NEWLINE29);
                    adaptor.addChild(root_0, NEWLINE29_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_if_expression373);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:7: ( NEWLINE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NEWLINE) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred16_Script()) ) {
                    alt7=1;
                }
            }
            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE30=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression377); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE30_tree = (Object)adaptor.create(NEWLINE30);
                    adaptor.addChild(root_0, NEWLINE30_tree);
                    }

                    }
                    break;

            }

            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==ELSE) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred18_Script()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE31=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression381); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE31_tree = (Object)adaptor.create(ELSE31);
                    adaptor.addChild(root_0, ELSE31_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:22: ( NEWLINE )?
                    int alt8=2;
                    alt8 = dfa8.predict(input);
                    switch (alt8) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE32=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression383); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE32_tree = (Object)adaptor.create(NEWLINE32);
                            adaptor.addChild(root_0, NEWLINE32_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression388);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:286:1: while_expression returns [LoopExpression expr] : p= pre_while_expression ;
    public final ScriptParser.while_expression_return while_expression() throws RecognitionException {
        ScriptParser.while_expression_return retval = new ScriptParser.while_expression_return();
        retval.start = input.LT(1);
        int while_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_while_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:287:5: (p= pre_while_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:287:7: p= pre_while_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_while_expression_in_while_expression410);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:299:1: pre_while_expression returns [LinkedList<Expression> exprs] : WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ;
    public final ScriptParser.pre_while_expression_return pre_while_expression() throws RecognitionException {
        ScriptParser.pre_while_expression_return retval = new ScriptParser.pre_while_expression_return();
        retval.start = input.LT(1);
        int pre_while_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WHILE33=null;
        Token LEFT_P34=null;
        Token RIGHT_P35=null;
        Token NEWLINE36=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object WHILE33_tree=null;
        Object LEFT_P34_tree=null;
        Object RIGHT_P35_tree=null;
        Object NEWLINE36_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:300:5: ( WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:300:7: WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            WHILE33=(Token)match(input,WHILE,FOLLOW_WHILE_in_pre_while_expression428); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE33_tree = (Object)adaptor.create(WHILE33);
            adaptor.addChild(root_0, WHILE33_tree);
            }
            LEFT_P34=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_while_expression430); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P34_tree = (Object)adaptor.create(LEFT_P34);
            adaptor.addChild(root_0, LEFT_P34_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_while_expression434);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P35=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_while_expression436); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P35_tree = (Object)adaptor.create(RIGHT_P35);
            adaptor.addChild(root_0, RIGHT_P35_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:300:41: ( NEWLINE )?
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE36=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_while_expression438); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE36_tree = (Object)adaptor.create(NEWLINE36);
                    adaptor.addChild(root_0, NEWLINE36_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_while_expression443);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:306:1: for_expression returns [LoopExpression expr] : p= pre_for_expression ;
    public final ScriptParser.for_expression_return for_expression() throws RecognitionException {
        ScriptParser.for_expression_return retval = new ScriptParser.for_expression_return();
        retval.start = input.LT(1);
        int for_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_for_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:307:5: (p= pre_for_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:307:7: p= pre_for_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_for_expression_in_for_expression463);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:319:1: pre_for_expression returns [LinkedList<Expression> exprs] : ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat );
    public final ScriptParser.pre_for_expression_return pre_for_expression() throws RecognitionException {
        ScriptParser.pre_for_expression_return retval = new ScriptParser.pre_for_expression_return();
        retval.start = input.LT(1);
        int pre_for_expression_StartIndex = input.index();
        Object root_0 = null;

        Token FOR37=null;
        Token LEFT_P38=null;
        Token PV39=null;
        Token PV40=null;
        Token RIGHT_P41=null;
        Token NEWLINE42=null;
        Token FOR43=null;
        Token LEFT_P44=null;
        Token ID45=null;
        Token EQUAL46=null;
        Token RIGHT_P48=null;
        Token NEWLINE49=null;
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;

        ScriptParser.range_return range47 = null;


        Object FOR37_tree=null;
        Object LEFT_P38_tree=null;
        Object PV39_tree=null;
        Object PV40_tree=null;
        Object RIGHT_P41_tree=null;
        Object NEWLINE42_tree=null;
        Object FOR43_tree=null;
        Object LEFT_P44_tree=null;
        Object ID45_tree=null;
        Object EQUAL46_tree=null;
        Object RIGHT_P48_tree=null;
        Object NEWLINE49_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:5: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==FOR) ) {
                int LA13_1 = input.LA(2);

                if ( (synpred21_Script()) ) {
                    alt13=1;
                }
                else if ( (true) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR37=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression481); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR37_tree = (Object)adaptor.create(FOR37);
                    adaptor.addChild(root_0, FOR37_tree);
                    }
                    LEFT_P38=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression483); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P38_tree = (Object)adaptor.create(LEFT_P38);
                    adaptor.addChild(root_0, LEFT_P38_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression487);
                    e_init=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_init.getTree());
                    PV39=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression489); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV39_tree = (Object)adaptor.create(PV39);
                    adaptor.addChild(root_0, PV39_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_for_expression493);
                    e_cond=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_cond.getTree());
                    PV40=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression495); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV40_tree = (Object)adaptor.create(PV40);
                    adaptor.addChild(root_0, PV40_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression499);
                    e_inc=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_inc.getTree());
                    RIGHT_P41=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression501); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P41_tree = (Object)adaptor.create(RIGHT_P41);
                    adaptor.addChild(root_0, RIGHT_P41_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:81: ( NEWLINE )?
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE42=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression503); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE42_tree = (Object)adaptor.create(NEWLINE42);
                            adaptor.addChild(root_0, NEWLINE42_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression508);
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
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:327:7: FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR43=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression518); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR43_tree = (Object)adaptor.create(FOR43);
                    adaptor.addChild(root_0, FOR43_tree);
                    }
                    LEFT_P44=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression520); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P44_tree = (Object)adaptor.create(LEFT_P44);
                    adaptor.addChild(root_0, LEFT_P44_tree);
                    }
                    ID45=(Token)match(input,ID,FOLLOW_ID_in_pre_for_expression522); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID45_tree = (Object)adaptor.create(ID45);
                    adaptor.addChild(root_0, ID45_tree);
                    }
                    EQUAL46=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_for_expression524); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL46_tree = (Object)adaptor.create(EQUAL46);
                    adaptor.addChild(root_0, EQUAL46_tree);
                    }
                    pushFollow(FOLLOW_range_in_pre_for_expression526);
                    range47=range();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, range47.getTree());
                    RIGHT_P48=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression528); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P48_tree = (Object)adaptor.create(RIGHT_P48);
                    adaptor.addChild(root_0, RIGHT_P48_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:327:41: ( NEWLINE )?
                    int alt12=2;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE49=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression530); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE49_tree = (Object)adaptor.create(NEWLINE49);
                            adaptor.addChild(root_0, NEWLINE49_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression535);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    if ( state.backtracking==0 ) {

                              retval.exprs = new LinkedList();

                              boolean plus_minus = true;

                              Calculable init = (range47!=null?range47.range_ele:null).get(0);
                              Calculable increment = (range47!=null?range47.range_ele:null).size()==3 ? (range47!=null?range47.range_ele:null).get(1) : new Numeric(1.0f);
                              Calculable condition = (range47!=null?range47.range_ele:null).get((range47!=null?range47.range_ele:null).size()-1);

                              try {
                                  if (increment.getSimplifiedCalculable() instanceof Numeric) {
                                      double val = (Double) ((Numeric) increment.getSimplifiedCalculable()).getNativeValue();
                                      plus_minus = val>=0;
                                  }
                              } catch (Exception e) {
                                  //NOTHING
                              }

                              VariableAssignment va = new VariableAssignment(this, (ID45!=null?ID45.getText():null), init);
                              Expression init_expr = new Expression(true, this, va);
                              
                              LinkedList<Object> term_ele = new LinkedList();
                              term_ele.add(new Variable(this, (ID45!=null?ID45.getText():null)));
                              term_ele.add(Operator.OPERATOR_PLUS);
                              term_ele.add(increment);
                              Term t = new Term(this, term_ele);
                              VariableAssignment vai = new VariableAssignment(this, (ID45!=null?ID45.getText():null), t);
                              Expression increment_expr = new Expression(true, this, vai);

                              LinkedList<Object> term_elec = new LinkedList();
                              term_elec.add(new Variable(this, (ID45!=null?ID45.getText():null)));
                              term_elec.add(plus_minus ? Operator.OPERATOR_CMP_LT_EQ : Operator.OPERATOR_CMP_GT_EQ);
                              term_elec.add(condition);
                              Term tc = new Term(this, term_elec);
                              Expression condition_expr = new Expression(true, this, tc);

                              retval.exprs = new LinkedList();
                              retval.exprs.add( init_expr );
                              retval.exprs.add( condition_expr );
                              retval.exprs.add( increment_expr );
                              retval.exprs.add( (s!=null?s.expr:null) );
                          
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
            if ( state.backtracking>0 ) { memoize(input, 11, pre_for_expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pre_for_expression"

    public static class range_return extends ParserRuleReturnScope {
        public LinkedList<Calculable> range_ele;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "range"
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:370:1: range returns [LinkedList<Calculable> range_ele] : a= expression ( ARROW b= range )* ;
    public final ScriptParser.range_return range() throws RecognitionException {
        ScriptParser.range_return retval = new ScriptParser.range_return();
        retval.start = input.LT(1);
        int range_StartIndex = input.index();
        Object root_0 = null;

        Token ARROW50=null;
        ScriptParser.expression_return a = null;

        ScriptParser.range_return b = null;


        Object ARROW50_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:371:5: (a= expression ( ARROW b= range )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:371:7: a= expression ( ARROW b= range )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_range555);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.range_ele = new LinkedList();
                      retval.range_ele.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:374:7: ( ARROW b= range )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==ARROW) ) {
                    int LA14_2 = input.LA(2);

                    if ( (synpred23_Script()) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:374:8: ARROW b= range
            	    {
            	    ARROW50=(Token)match(input,ARROW,FOLLOW_ARROW_in_range560); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    ARROW50_tree = (Object)adaptor.create(ARROW50);
            	    adaptor.addChild(root_0, ARROW50_tree);
            	    }
            	    pushFollow(FOLLOW_range_in_range564);
            	    b=range();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
            	    if ( state.backtracking==0 ) {

            	              for (int k=0; k<retval.range_ele.size(); k++) {
            	                  (b!=null?b.range_ele:null).add(0, retval.range_ele.get(k));
            	              }
            	              retval.range_ele = (b!=null?b.range_ele:null);
            	          
            	    }

            	    }
            	    break;

            	default :
            	    break loop14;
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
            if ( state.backtracking>0 ) { memoize(input, 12, range_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "range"

    public static class expression_return extends ParserRuleReturnScope {
        public Expression expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:381:1: expression returns [Expression expr] : ( terms | function_call );
    public final ScriptParser.expression_return expression() throws RecognitionException {
        ScriptParser.expression_return retval = new ScriptParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.terms_return terms51 = null;

        ScriptParser.function_call_return function_call52 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:382:5: ( terms | function_call )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==LEFT_CB||(LA15_0>=PLUS_PLUS && LA15_0<=MINUS_MINUS)||LA15_0==LEFT_P||LA15_0==MINUS||(LA15_0>=NUM && LA15_0<=STRING_LITERAL)) ) {
                alt15=1;
            }
            else if ( (LA15_0==ID) ) {
                int LA15_2 = input.LA(2);

                if ( (LA15_2==EOF||(LA15_2>=NEWLINE && LA15_2<=RIGHT_CB)||(LA15_2>=PLUS_PLUS && LA15_2<=MINUS_MINUS)||LA15_2==RIGHT_P||(LA15_2>=PV && LA15_2<=COMMA)||LA15_2==TP) ) {
                    alt15=1;
                }
                else if ( (LA15_2==LEFT_P) ) {
                    alt15=2;
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:382:7: terms
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_terms_in_expression585);
                    terms51=terms();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, terms51.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new Term(this, (terms51!=null?terms51.terms:null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:385:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_expression595);
                    function_call52=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call52.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new FunctionCall( this, (function_call52!=null?function_call52.name_params:null) ) );
                          
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
            if ( state.backtracking>0 ) { memoize(input, 13, expression_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:389:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* ;
    public final ScriptParser.terms_return terms() throws RecognitionException {
        ScriptParser.terms_return retval = new ScriptParser.terms_return();
        retval.start = input.LT(1);
        int terms_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS53=null;
        Token MINUS54=null;
        Token AND55=null;
        Token OR56=null;
        ScriptParser.term_return t = null;


        Object PLUS53_tree=null;
        Object MINUS54_tree=null;
        Object AND55_tree=null;
        Object OR56_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:390:5: (t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:390:7: t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms615);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:393:7: ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            loop16:
            do {
                int alt16=5;
                switch ( input.LA(1) ) {
                case PLUS:
                    {
                    alt16=1;
                    }
                    break;
                case MINUS:
                    {
                    alt16=2;
                    }
                    break;
                case AND:
                    {
                    alt16=3;
                    }
                    break;
                case OR:
                    {
                    alt16=4;
                    }
                    break;

                }

                switch (alt16) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:393:9: PLUS t= term
            	    {
            	    PLUS53=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms621); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS53_tree = (Object)adaptor.create(PLUS53);
            	    adaptor.addChild(root_0, PLUS53_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms625);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:397:9: MINUS t= term
            	    {
            	    MINUS54=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms637); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS54_tree = (Object)adaptor.create(MINUS54);
            	    adaptor.addChild(root_0, MINUS54_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms641);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:401:9: AND t= term
            	    {
            	    AND55=(Token)match(input,AND,FOLLOW_AND_in_terms653); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND55_tree = (Object)adaptor.create(AND55);
            	    adaptor.addChild(root_0, AND55_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms657);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:405:9: OR t= term
            	    {
            	    OR56=(Token)match(input,OR,FOLLOW_OR_in_terms669); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR56_tree = (Object)adaptor.create(OR56);
            	    adaptor.addChild(root_0, OR56_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms673);
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
            if ( state.backtracking>0 ) { memoize(input, 14, terms_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:411:1: term returns [LinkedList<Object> atoms] : (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.term_return term() throws RecognitionException {
        ScriptParser.term_return retval = new ScriptParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token MULT57=null;
        Token DIV58=null;
        Token CMP_LT59=null;
        Token CMP_LT_EQ60=null;
        Token CMP_GT61=null;
        Token CMP_GT_EQ62=null;
        Token CMP_EQ63=null;
        Token CMP_NEQ64=null;
        Token ID65=null;
        Token PLUS_PLUS66=null;
        Token PLUS_PLUS67=null;
        Token ID68=null;
        Token ID69=null;
        Token MINUS_MINUS70=null;
        Token MINUS_MINUS71=null;
        Token ID72=null;
        ScriptParser.atom_return a = null;


        Object MULT57_tree=null;
        Object DIV58_tree=null;
        Object CMP_LT59_tree=null;
        Object CMP_LT_EQ60_tree=null;
        Object CMP_GT61_tree=null;
        Object CMP_GT_EQ62_tree=null;
        Object CMP_EQ63_tree=null;
        Object CMP_NEQ64_tree=null;
        Object ID65_tree=null;
        Object PLUS_PLUS66_tree=null;
        Object PLUS_PLUS67_tree=null;
        Object ID68_tree=null;
        Object ID69_tree=null;
        Object MINUS_MINUS70_tree=null;
        Object MINUS_MINUS71_tree=null;
        Object ID72_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:412:5: (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
            int alt18=5;
            switch ( input.LA(1) ) {
            case LEFT_CB:
            case LEFT_P:
            case MINUS:
            case NUM:
            case BOOL:
            case STRING_LITERAL:
                {
                alt18=1;
                }
                break;
            case ID:
                {
                switch ( input.LA(2) ) {
                case PLUS_PLUS:
                    {
                    alt18=2;
                    }
                    break;
                case MINUS_MINUS:
                    {
                    alt18=4;
                    }
                    break;
                case EOF:
                case NEWLINE:
                case RIGHT_CB:
                case RIGHT_P:
                case PV:
                case ARROW:
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
                case COMMA:
                case TP:
                    {
                    alt18=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;
                }

                }
                break;
            case PLUS_PLUS:
                {
                alt18=3;
                }
                break;
            case MINUS_MINUS:
                {
                alt18=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:412:7: a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term697);
                    a=atom();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              retval.atoms.add((a!=null?a.value:null));
                          
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:415:7: ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    loop17:
                    do {
                        int alt17=9;
                        switch ( input.LA(1) ) {
                        case MULT:
                            {
                            alt17=1;
                            }
                            break;
                        case DIV:
                            {
                            alt17=2;
                            }
                            break;
                        case CMP_LT:
                            {
                            alt17=3;
                            }
                            break;
                        case CMP_LT_EQ:
                            {
                            alt17=4;
                            }
                            break;
                        case CMP_GT:
                            {
                            alt17=5;
                            }
                            break;
                        case CMP_GT_EQ:
                            {
                            alt17=6;
                            }
                            break;
                        case CMP_EQ:
                            {
                            alt17=7;
                            }
                            break;
                        case CMP_NEQ:
                            {
                            alt17=8;
                            }
                            break;

                        }

                        switch (alt17) {
                    	case 1 :
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:415:9: MULT a= atom
                    	    {
                    	    MULT57=(Token)match(input,MULT,FOLLOW_MULT_in_term703); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    MULT57_tree = (Object)adaptor.create(MULT57);
                    	    adaptor.addChild(root_0, MULT57_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term707);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:419:9: DIV a= atom
                    	    {
                    	    DIV58=(Token)match(input,DIV,FOLLOW_DIV_in_term719); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DIV58_tree = (Object)adaptor.create(DIV58);
                    	    adaptor.addChild(root_0, DIV58_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term723);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:423:9: CMP_LT a= atom
                    	    {
                    	    CMP_LT59=(Token)match(input,CMP_LT,FOLLOW_CMP_LT_in_term735); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT59_tree = (Object)adaptor.create(CMP_LT59);
                    	    adaptor.addChild(root_0, CMP_LT59_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term739);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:427:9: CMP_LT_EQ a= atom
                    	    {
                    	    CMP_LT_EQ60=(Token)match(input,CMP_LT_EQ,FOLLOW_CMP_LT_EQ_in_term752); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT_EQ60_tree = (Object)adaptor.create(CMP_LT_EQ60);
                    	    adaptor.addChild(root_0, CMP_LT_EQ60_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term756);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:431:9: CMP_GT a= atom
                    	    {
                    	    CMP_GT61=(Token)match(input,CMP_GT,FOLLOW_CMP_GT_in_term769); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT61_tree = (Object)adaptor.create(CMP_GT61);
                    	    adaptor.addChild(root_0, CMP_GT61_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term773);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:435:9: CMP_GT_EQ a= atom
                    	    {
                    	    CMP_GT_EQ62=(Token)match(input,CMP_GT_EQ,FOLLOW_CMP_GT_EQ_in_term785); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT_EQ62_tree = (Object)adaptor.create(CMP_GT_EQ62);
                    	    adaptor.addChild(root_0, CMP_GT_EQ62_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term789);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:439:9: CMP_EQ a= atom
                    	    {
                    	    CMP_EQ63=(Token)match(input,CMP_EQ,FOLLOW_CMP_EQ_in_term802); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_EQ63_tree = (Object)adaptor.create(CMP_EQ63);
                    	    adaptor.addChild(root_0, CMP_EQ63_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term806);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:443:9: CMP_NEQ a= atom
                    	    {
                    	    CMP_NEQ64=(Token)match(input,CMP_NEQ,FOLLOW_CMP_NEQ_in_term818); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_NEQ64_tree = (Object)adaptor.create(CMP_NEQ64);
                    	    adaptor.addChild(root_0, CMP_NEQ64_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term822);
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
                    	    break loop17;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:448:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID65=(Token)match(input,ID,FOLLOW_ID_in_term839); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID65_tree = (Object)adaptor.create(ID65);
                    adaptor.addChild(root_0, ID65_tree);
                    }
                    PLUS_PLUS66=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term841); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS66_tree = (Object)adaptor.create(PLUS_PLUS66);
                    adaptor.addChild(root_0, PLUS_PLUS66_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID65!=null?ID65.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:453:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS67=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term851); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS67_tree = (Object)adaptor.create(PLUS_PLUS67);
                    adaptor.addChild(root_0, PLUS_PLUS67_tree);
                    }
                    ID68=(Token)match(input,ID,FOLLOW_ID_in_term853); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID68_tree = (Object)adaptor.create(ID68);
                    adaptor.addChild(root_0, ID68_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID68!=null?ID68.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:458:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID69=(Token)match(input,ID,FOLLOW_ID_in_term863); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID69_tree = (Object)adaptor.create(ID69);
                    adaptor.addChild(root_0, ID69_tree);
                    }
                    MINUS_MINUS70=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term865); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS70_tree = (Object)adaptor.create(MINUS_MINUS70);
                    adaptor.addChild(root_0, MINUS_MINUS70_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID69!=null?ID69.getText():null), Operator.OPERATOR_MINUS_MINUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:463:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS71=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term875); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS71_tree = (Object)adaptor.create(MINUS_MINUS71);
                    adaptor.addChild(root_0, MINUS_MINUS71_tree);
                    }
                    ID72=(Token)match(input,ID,FOLLOW_ID_in_term877); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID72_tree = (Object)adaptor.create(ID72);
                    adaptor.addChild(root_0, ID72_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID72!=null?ID72.getText():null), Operator.OPERATOR_MINUS_MINUS);
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
            if ( state.backtracking>0 ) { memoize(input, 15, term_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:470:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
    public final ScriptParser.function_call_return function_call() throws RecognitionException {
        ScriptParser.function_call_return retval = new ScriptParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token ID73=null;
        Token LEFT_P74=null;
        Token RIGHT_P76=null;
        ScriptParser.args_return args75 = null;


        Object ID73_tree=null;
        Object LEFT_P74_tree=null;
        Object RIGHT_P76_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:470:55: ( ID LEFT_P args RIGHT_P )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:471:5: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID73=(Token)match(input,ID,FOLLOW_ID_in_function_call895); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID73_tree = (Object)adaptor.create(ID73);
            adaptor.addChild(root_0, ID73_tree);
            }
            LEFT_P74=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call897); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P74_tree = (Object)adaptor.create(LEFT_P74);
            adaptor.addChild(root_0, LEFT_P74_tree);
            }
            pushFollow(FOLLOW_args_in_function_call899);
            args75=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, args75.getTree());
            RIGHT_P76=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call901); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P76_tree = (Object)adaptor.create(RIGHT_P76);
            adaptor.addChild(root_0, RIGHT_P76_tree);
            }
            if ( state.backtracking==0 ) {
                
                      retval.name_params = (args75!=null?args75.params:null);
                      retval.name_params.add(0, (ID73!=null?ID73.getText():null));
                  
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
            if ( state.backtracking>0 ) { memoize(input, 16, function_call_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:477:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final ScriptParser.args_return args() throws RecognitionException {
        ScriptParser.args_return retval = new ScriptParser.args_return();
        retval.start = input.LT(1);
        int args_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA77=null;
        ScriptParser.expression_return a = null;

        ScriptParser.args_return b = null;


        Object COMMA77_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:477:41: (a= expression ( COMMA b= args )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:478:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args921);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:481:7: ( COMMA b= args )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==COMMA) ) {
                    int LA19_2 = input.LA(2);

                    if ( (synpred41_Script()) ) {
                        alt19=1;
                    }


                }


                switch (alt19) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:481:8: COMMA b= args
            	    {
            	    COMMA77=(Token)match(input,COMMA,FOLLOW_COMMA_in_args926); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA77_tree = (Object)adaptor.create(COMMA77);
            	    adaptor.addChild(root_0, COMMA77_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args930);
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
            	    break loop19;
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
            if ( state.backtracking>0 ) { memoize(input, 17, args_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:490:1: atom returns [Object value] : ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary );
    public final ScriptParser.atom_return atom() throws RecognitionException {
        ScriptParser.atom_return retval = new ScriptParser.atom_return();
        retval.start = input.LT(1);
        int atom_StartIndex = input.index();
        Object root_0 = null;

        Token NUM78=null;
        Token MINUS79=null;
        Token NUM80=null;
        Token BOOL81=null;
        Token LEFT_P82=null;
        Token RIGHT_P84=null;
        Token ID85=null;
        ScriptParser.expression_return expression83 = null;

        ScriptParser.string_literal_return string_literal86 = null;

        ScriptParser.dictionary_return dictionary87 = null;


        Object NUM78_tree=null;
        Object MINUS79_tree=null;
        Object NUM80_tree=null;
        Object BOOL81_tree=null;
        Object LEFT_P82_tree=null;
        Object RIGHT_P84_tree=null;
        Object ID85_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:491:5: ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary )
            int alt20=7;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt20=1;
                }
                break;
            case MINUS:
                {
                alt20=2;
                }
                break;
            case BOOL:
                {
                alt20=3;
                }
                break;
            case LEFT_P:
                {
                alt20=4;
                }
                break;
            case ID:
                {
                alt20=5;
                }
                break;
            case STRING_LITERAL:
                {
                alt20=6;
                }
                break;
            case LEFT_CB:
                {
                alt20=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:491:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM78=(Token)match(input,NUM,FOLLOW_NUM_in_atom953); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM78_tree = (Object)adaptor.create(NUM78);
                    adaptor.addChild(root_0, NUM78_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Float.parseFloat((NUM78!=null?NUM78.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:494:7: MINUS NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS79=(Token)match(input,MINUS,FOLLOW_MINUS_in_atom963); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS79_tree = (Object)adaptor.create(MINUS79);
                    adaptor.addChild(root_0, MINUS79_tree);
                    }
                    NUM80=(Token)match(input,NUM,FOLLOW_NUM_in_atom965); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM80_tree = (Object)adaptor.create(NUM80);
                    adaptor.addChild(root_0, NUM80_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( -1.0 * Float.parseFloat((NUM80!=null?NUM80.getText():null)) );
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:497:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL81=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom975); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL81_tree = (Object)adaptor.create(BOOL81);
                    adaptor.addChild(root_0, BOOL81_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL81!=null?BOOL81.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL81!=null?BOOL81.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              }
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:504:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P82=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom985); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P82_tree = (Object)adaptor.create(LEFT_P82);
                    adaptor.addChild(root_0, LEFT_P82_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom987);
                    expression83=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression83.getTree());
                    RIGHT_P84=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom989); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P84_tree = (Object)adaptor.create(RIGHT_P84);
                    adaptor.addChild(root_0, RIGHT_P84_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression83!=null?expression83.expr:null);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:507:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID85=(Token)match(input,ID,FOLLOW_ID_in_atom999); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID85_tree = (Object)adaptor.create(ID85);
                    adaptor.addChild(root_0, ID85_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID85!=null?ID85.getText():null));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:510:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom1009);
                    string_literal86=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal86.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal86!=null?string_literal86.value:null);
                          
                    }

                    }
                    break;
                case 7 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:513:7: dictionary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dictionary_in_atom1019);
                    dictionary87=dictionary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary87.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (dictionary87!=null?dictionary87.value:null);
                          
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
            if ( state.backtracking>0 ) { memoize(input, 18, atom_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:525:1: string_literal returns [CharString value] : s= STRING_LITERAL ;
    public final ScriptParser.string_literal_return string_literal() throws RecognitionException {
        ScriptParser.string_literal_return retval = new ScriptParser.string_literal_return();
        retval.start = input.LT(1);
        int string_literal_StartIndex = input.index();
        Object root_0 = null;

        Token s=null;

        Object s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:525:43: (s= STRING_LITERAL )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:525:45: s= STRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_string_literal1047); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 19, string_literal_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:530:1: dictionary returns [Dictionary value] : LEFT_CB dictionary_elements RIGHT_CB ;
    public final ScriptParser.dictionary_return dictionary() throws RecognitionException {
        ScriptParser.dictionary_return retval = new ScriptParser.dictionary_return();
        retval.start = input.LT(1);
        int dictionary_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB88=null;
        Token RIGHT_CB90=null;
        ScriptParser.dictionary_elements_return dictionary_elements89 = null;


        Object LEFT_CB88_tree=null;
        Object RIGHT_CB90_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:530:39: ( LEFT_CB dictionary_elements RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:531:5: LEFT_CB dictionary_elements RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB88=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_dictionary1066); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB88_tree = (Object)adaptor.create(LEFT_CB88);
            adaptor.addChild(root_0, LEFT_CB88_tree);
            }
            pushFollow(FOLLOW_dictionary_elements_in_dictionary1068);
            dictionary_elements89=dictionary_elements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary_elements89.getTree());
            RIGHT_CB90=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_dictionary1070); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB90_tree = (Object)adaptor.create(RIGHT_CB90);
            adaptor.addChild(root_0, RIGHT_CB90_tree);
            }
            if ( state.backtracking==0 ) {

                      HashMap vs = new HashMap();
                      int size = (dictionary_elements89!=null?dictionary_elements89.keys_values:null).size();
                      for (int k=0; k<size; k+=2) {
                          vs.put((dictionary_elements89!=null?dictionary_elements89.keys_values:null).get(k), (dictionary_elements89!=null?dictionary_elements89.keys_values:null).get(k+1));
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
            if ( state.backtracking>0 ) { memoize(input, 20, dictionary_StartIndex); }
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:540:1: dictionary_elements returns [LinkedList<Object> keys_values] : (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* ;
    public final ScriptParser.dictionary_elements_return dictionary_elements() throws RecognitionException {
        ScriptParser.dictionary_elements_return retval = new ScriptParser.dictionary_elements_return();
        retval.start = input.LT(1);
        int dictionary_elements_StartIndex = input.index();
        Object root_0 = null;

        Token TP91=null;
        Token NEWLINE92=null;
        Token COMMA93=null;
        Token NEWLINE94=null;
        ScriptParser.expression_return e1 = null;

        ScriptParser.expression_return e2 = null;

        ScriptParser.dictionary_elements_return d = null;


        Object TP91_tree=null;
        Object NEWLINE92_tree=null;
        Object COMMA93_tree=null;
        Object NEWLINE94_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:540:62: ( (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:5: (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:5: (e1= expression TP e2= expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:6: e1= expression TP e2= expression
            {
            pushFollow(FOLLOW_expression_in_dictionary_elements1091);
            e1=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
            TP91=(Token)match(input,TP,FOLLOW_TP_in_dictionary_elements1093); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TP91_tree = (Object)adaptor.create(TP91);
            adaptor.addChild(root_0, TP91_tree);
            }
            pushFollow(FOLLOW_expression_in_dictionary_elements1097);
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:7: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==NEWLINE) ) {
                    int LA23_2 = input.LA(2);

                    if ( (synpred50_Script()) ) {
                        alt23=1;
                    }


                }
                else if ( (LA23_0==COMMA) ) {
                    int LA23_3 = input.LA(2);

                    if ( (synpred50_Script()) ) {
                        alt23=1;
                    }


                }


                switch (alt23) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
            	    {
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:8: ( NEWLINE )?
            	    int alt21=2;
            	    int LA21_0 = input.LA(1);

            	    if ( (LA21_0==NEWLINE) ) {
            	        alt21=1;
            	    }
            	    switch (alt21) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE92=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1103); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE92_tree = (Object)adaptor.create(NEWLINE92);
            	            adaptor.addChild(root_0, NEWLINE92_tree);
            	            }

            	            }
            	            break;

            	    }

            	    COMMA93=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictionary_elements1106); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA93_tree = (Object)adaptor.create(COMMA93);
            	    adaptor.addChild(root_0, COMMA93_tree);
            	    }
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:23: ( NEWLINE )?
            	    int alt22=2;
            	    int LA22_0 = input.LA(1);

            	    if ( (LA22_0==NEWLINE) ) {
            	        alt22=1;
            	    }
            	    switch (alt22) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE94=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1108); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE94_tree = (Object)adaptor.create(NEWLINE94);
            	            adaptor.addChild(root_0, NEWLINE94_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_dictionary_elements_in_dictionary_elements1113);
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
            	    break loop23;
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
            if ( state.backtracking>0 ) { memoize(input, 21, dictionary_elements_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dictionary_elements"

    // $ANTLR start synpred1_Script
    public final void synpred1_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:198:13: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:198:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Script109); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Script

    // $ANTLR start synpred3_Script
    public final void synpred3_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:208:8: (s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:208:8: s= stat
        {
        pushFollow(FOLLOW_stat_in_synpred3_Script143);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Script

    // $ANTLR start synpred4_Script
    public final void synpred4_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:216:7: ( pre_stat NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:216:7: pre_stat NEWLINE
        {
        pushFollow(FOLLOW_pre_stat_in_synpred4_Script168);
        pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred4_Script170); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Script

    // $ANTLR start synpred6_Script
    public final void synpred6_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:223:7: ( block )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:223:7: block
        {
        pushFollow(FOLLOW_block_in_synpred6_Script190);
        block();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Script

    // $ANTLR start synpred9_Script
    public final void synpred9_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:236:7: ( expression )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:236:7: expression
        {
        pushFollow(FOLLOW_expression_in_synpred9_Script237);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Script

    // $ANTLR start synpred12_Script
    public final void synpred12_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:245:7: ( ID PLUS_PLUS )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:245:7: ID PLUS_PLUS
        {
        match(input,ID,FOLLOW_ID_in_synpred12_Script271); if (state.failed) return ;
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred12_Script273); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_Script

    // $ANTLR start synpred13_Script
    public final void synpred13_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:248:7: ( PLUS_PLUS ID )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:248:7: PLUS_PLUS ID
        {
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred13_Script283); if (state.failed) return ;
        match(input,ID,FOLLOW_ID_in_synpred13_Script285); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Script

    // $ANTLR start synpred14_Script
    public final void synpred14_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:251:7: ( ID MINUS_MINUS )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:251:7: ID MINUS_MINUS
        {
        match(input,ID,FOLLOW_ID_in_synpred14_Script295); if (state.failed) return ;
        match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_synpred14_Script297); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Script

    // $ANTLR start synpred15_Script
    public final void synpred15_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:38: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred15_Script368); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Script

    // $ANTLR start synpred16_Script
    public final void synpred16_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:7: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred16_Script377); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Script

    // $ANTLR start synpred17_Script
    public final void synpred17_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:22: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred17_Script383); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Script

    // $ANTLR start synpred18_Script
    public final void synpred18_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:17: ( ELSE ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred18_Script381); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:22: ( NEWLINE )?
        int alt24=2;
        int LA24_0 = input.LA(1);

        if ( (LA24_0==NEWLINE) ) {
            int LA24_1 = input.LA(2);

            if ( ((LA24_1>=LEFT_CB && LA24_1<=NEWLINE)||(LA24_1>=BREAK && LA24_1<=ID)||(LA24_1>=PLUS_PLUS && LA24_1<=LEFT_P)||(LA24_1>=WHILE && LA24_1<=FOR)||LA24_1==MINUS||(LA24_1>=NUM && LA24_1<=STRING_LITERAL)) ) {
                alt24=1;
            }
        }
        switch (alt24) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred18_Script383); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred18_Script388);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_Script

    // $ANTLR start synpred19_Script
    public final void synpred19_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:300:41: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:300:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred19_Script438); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Script

    // $ANTLR start synpred20_Script
    public final void synpred20_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:81: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:81: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred20_Script503); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_Script

    // $ANTLR start synpred21_Script
    public final void synpred21_Script_fragment() throws RecognitionException {   
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:7: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
        {
        match(input,FOR,FOLLOW_FOR_in_synpred21_Script481); if (state.failed) return ;
        match(input,LEFT_P,FOLLOW_LEFT_P_in_synpred21_Script483); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred21_Script487);
        e_init=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred21_Script489); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred21_Script493);
        e_cond=expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred21_Script495); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred21_Script499);
        e_inc=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,RIGHT_P,FOLLOW_RIGHT_P_in_synpred21_Script501); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:320:81: ( NEWLINE )?
        int alt25=2;
        int LA25_0 = input.LA(1);

        if ( (LA25_0==NEWLINE) ) {
            int LA25_1 = input.LA(2);

            if ( ((LA25_1>=LEFT_CB && LA25_1<=NEWLINE)||(LA25_1>=BREAK && LA25_1<=ID)||(LA25_1>=PLUS_PLUS && LA25_1<=LEFT_P)||(LA25_1>=WHILE && LA25_1<=FOR)||LA25_1==MINUS||(LA25_1>=NUM && LA25_1<=STRING_LITERAL)) ) {
                alt25=1;
            }
        }
        switch (alt25) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred21_Script503); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred21_Script508);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_Script

    // $ANTLR start synpred22_Script
    public final void synpred22_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:327:41: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:327:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred22_Script530); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_Script

    // $ANTLR start synpred23_Script
    public final void synpred23_Script_fragment() throws RecognitionException {   
        ScriptParser.range_return b = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:374:8: ( ARROW b= range )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:374:8: ARROW b= range
        {
        match(input,ARROW,FOLLOW_ARROW_in_synpred23_Script560); if (state.failed) return ;
        pushFollow(FOLLOW_range_in_synpred23_Script564);
        b=range();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_Script

    // $ANTLR start synpred41_Script
    public final void synpred41_Script_fragment() throws RecognitionException {   
        ScriptParser.args_return b = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:481:8: ( COMMA b= args )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:481:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred41_Script926); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred41_Script930);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred41_Script

    // $ANTLR start synpred50_Script
    public final void synpred50_Script_fragment() throws RecognitionException {   
        ScriptParser.dictionary_elements_return d = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:8: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
        {
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:8: ( NEWLINE )?
        int alt27=2;
        int LA27_0 = input.LA(1);

        if ( (LA27_0==NEWLINE) ) {
            alt27=1;
        }
        switch (alt27) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred50_Script1103); if (state.failed) return ;

                }
                break;

        }

        match(input,COMMA,FOLLOW_COMMA_in_synpred50_Script1106); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:545:23: ( NEWLINE )?
        int alt28=2;
        int LA28_0 = input.LA(1);

        if ( (LA28_0==NEWLINE) ) {
            alt28=1;
        }
        switch (alt28) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred50_Script1108); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_dictionary_elements_in_synpred50_Script1113);
        d=dictionary_elements();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred50_Script

    // Delegated rules

    public final boolean synpred20_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred41_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred41_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred50_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred50_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Script_fragment(); // can never throw exception
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
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA1_eotS =
        "\20\uffff";
    static final String DFA1_eofS =
        "\20\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\16\uffff";
    static final String DFA1_maxS =
        "\1\43\1\0\16\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\14\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\16\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\2\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\3\2",
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
            return "198:13: ( NEWLINE )?";
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
                        if ( (synpred1_Script()) ) {s = 15;}

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
        "\20\uffff";
    static final String DFA4_eofS =
        "\20\uffff";
    static final String DFA4_minS =
        "\1\4\6\uffff\1\0\10\uffff";
    static final String DFA4_maxS =
        "\1\43\6\uffff\1\0\10\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\11\uffff\1\2\1\4\1\5\1\6\1\3";
    static final String DFA4_specialS =
        "\7\uffff\1\0\10\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\7\1\13\1\uffff\2\1\1\uffff\2\1\1\14\1\1\2\uffff\1\15\1\16"+
            "\3\uffff\1\1\13\uffff\3\1",
            "",
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
            return "215:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Script()) ) {s = 1;}

                        else if ( (synpred6_Script()) ) {s = 15;}

                         
                        input.seek(index4_7);
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
        "\17\uffff";
    static final String DFA5_eofS =
        "\17\uffff";
    static final String DFA5_minS =
        "\1\4\1\uffff\1\5\2\10\1\uffff\2\0\1\uffff\2\0\4\uffff";
    static final String DFA5_maxS =
        "\1\43\1\uffff\1\37\2\10\1\uffff\2\0\1\uffff\2\0\4\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\3\uffff\1\2\2\uffff\1\3\2\uffff\1\4\1\6\1\5\1\7";
    static final String DFA5_specialS =
        "\6\uffff\1\0\1\2\1\uffff\1\1\1\3\4\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1\2\uffff\1\5\1\2\1\uffff\1\3\1\4\1\uffff\1\1\7\uffff\1\1"+
            "\13\uffff\3\1",
            "",
            "\1\1\3\uffff\1\10\1\6\1\7\1\uffff\2\1\3\uffff\1\1\1\uffff\14"+
            "\1",
            "\1\11",
            "\1\12",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
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
            return "235:1: pre_stat returns [Expression expr] : ( expression | BREAK | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_6 = input.LA(1);

                         
                        int index5_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred12_Script()) ) {s = 11;}

                         
                        input.seek(index5_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_9 = input.LA(1);

                         
                        int index5_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred13_Script()) ) {s = 13;}

                         
                        input.seek(index5_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_7 = input.LA(1);

                         
                        int index5_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred14_Script()) ) {s = 12;}

                         
                        input.seek(index5_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_10 = input.LA(1);

                         
                        int index5_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index5_10);
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
    static final String DFA6_eotS =
        "\20\uffff";
    static final String DFA6_eofS =
        "\20\uffff";
    static final String DFA6_minS =
        "\1\4\1\0\16\uffff";
    static final String DFA6_maxS =
        "\1\43\1\0\16\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\14\uffff\1\1";
    static final String DFA6_specialS =
        "\1\uffff\1\0\16\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\2\1\1\1\uffff\2\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\3\2",
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
            return "278:38: ( NEWLINE )?";
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
                        if ( (synpred15_Script()) ) {s = 15;}

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
        "\20\uffff";
    static final String DFA8_eofS =
        "\20\uffff";
    static final String DFA8_minS =
        "\1\4\1\0\16\uffff";
    static final String DFA8_maxS =
        "\1\43\1\0\16\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\14\uffff\1\1";
    static final String DFA8_specialS =
        "\1\uffff\1\0\16\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\1\1\1\uffff\2\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\3\2",
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
            return "282:22: ( NEWLINE )?";
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
                        if ( (synpred17_Script()) ) {s = 15;}

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
        "\20\uffff";
    static final String DFA10_eofS =
        "\20\uffff";
    static final String DFA10_minS =
        "\1\4\1\0\16\uffff";
    static final String DFA10_maxS =
        "\1\43\1\0\16\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\14\uffff\1\1";
    static final String DFA10_specialS =
        "\1\uffff\1\0\16\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\2\1\1\1\uffff\2\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\3\2",
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
            return "300:41: ( NEWLINE )?";
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
                        if ( (synpred19_Script()) ) {s = 15;}

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
        "\20\uffff";
    static final String DFA11_eofS =
        "\20\uffff";
    static final String DFA11_minS =
        "\1\4\1\0\16\uffff";
    static final String DFA11_maxS =
        "\1\43\1\0\16\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\14\uffff\1\1";
    static final String DFA11_specialS =
        "\1\uffff\1\0\16\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\2\1\1\1\uffff\2\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\3\2",
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
            return "320:81: ( NEWLINE )?";
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
                        if ( (synpred20_Script()) ) {s = 15;}

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
    static final String DFA12_eotS =
        "\20\uffff";
    static final String DFA12_eofS =
        "\20\uffff";
    static final String DFA12_minS =
        "\1\4\1\0\16\uffff";
    static final String DFA12_maxS =
        "\1\43\1\0\16\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\14\uffff\1\1";
    static final String DFA12_specialS =
        "\1\uffff\1\0\16\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\1\1\uffff\2\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\3\2",
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
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "327:41: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA12_1 = input.LA(1);

                         
                        int index12_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_Script()) ) {s = 15;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index12_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_stats_in_prog90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_block107 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_block109 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stats_in_block112 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block114 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats136 = new BitSet(new long[]{0x0000000E00233DB2L});
    public static final BitSet FOLLOW_stat_in_stats143 = new BitSet(new long[]{0x0000000E00233DB2L});
    public static final BitSet FOLLOW_pre_stat_in_stat168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_expression_in_stat210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_expression_in_stat220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_pre_stat237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_pre_stat247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat257 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat259 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_expression_in_pre_stat261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat271 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat283 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_pre_stat285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat295 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat307 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_pre_stat309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression358 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression360 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression364 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression366 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression368 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression373 = new BitSet(new long[]{0x0000000000008022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression377 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression381 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression383 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_while_expression_in_while_expression410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_pre_while_expression428 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_while_expression430 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_expression_in_pre_while_expression434 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_while_expression436 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_while_expression438 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stat_in_pre_while_expression443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_for_expression_in_for_expression463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression481 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression483 = new BitSet(new long[]{0x0000000E00202D90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression487 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression489 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_expression_in_pre_for_expression493 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression495 = new BitSet(new long[]{0x0000000E00202D90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression499 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression501 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression503 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression518 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression520 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_pre_for_expression522 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUAL_in_pre_for_expression524 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_range_in_pre_for_expression526 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression528 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression530 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_range555 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_ARROW_in_range560 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_range_in_range564 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_terms_in_expression585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms615 = new BitSet(new long[]{0x0000000000F00002L});
    public static final BitSet FOLLOW_PLUS_in_terms621 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_term_in_terms625 = new BitSet(new long[]{0x0000000000F00002L});
    public static final BitSet FOLLOW_MINUS_in_terms637 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_term_in_terms641 = new BitSet(new long[]{0x0000000000F00002L});
    public static final BitSet FOLLOW_AND_in_terms653 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_term_in_terms657 = new BitSet(new long[]{0x0000000000F00002L});
    public static final BitSet FOLLOW_OR_in_terms669 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_term_in_terms673 = new BitSet(new long[]{0x0000000000F00002L});
    public static final BitSet FOLLOW_atom_in_term697 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_MULT_in_term703 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term707 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_DIV_in_term719 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term723 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_CMP_LT_in_term735 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term739 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_CMP_LT_EQ_in_term752 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term756 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_CMP_GT_in_term769 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term773 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_CMP_GT_EQ_in_term785 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term789 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_CMP_EQ_in_term802 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term806 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_CMP_NEQ_in_term818 = new BitSet(new long[]{0x0000000E00202110L});
    public static final BitSet FOLLOW_atom_in_term822 = new BitSet(new long[]{0x00000000FF000002L});
    public static final BitSet FOLLOW_ID_in_term839 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term851 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_term853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term863 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term875 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_term877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_function_call895 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call897 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_args_in_function_call899 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args921 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_COMMA_in_args926 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_args_in_args930 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_NUM_in_atom953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_atom963 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_NUM_in_atom965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom985 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_expression_in_atom987 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dictionary_in_atom1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal1047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_dictionary1066 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary1068 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_dictionary1070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1091 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_TP_in_dictionary_elements1093 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1097 = new BitSet(new long[]{0x0000000100000022L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1103 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_COMMA_in_dictionary_elements1106 = new BitSet(new long[]{0x0000000E00202D30L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1108 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary_elements1113 = new BitSet(new long[]{0x0000000100000022L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Script109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Script143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_stat_in_synpred4_Script168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred4_Script170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_synpred6_Script190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred9_Script237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred12_Script271 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred12_Script273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred13_Script283 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_synpred13_Script285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred14_Script295 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_synpred14_Script297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred15_Script368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred16_Script377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred17_Script383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred18_Script381 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred18_Script383 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stat_in_synpred18_Script388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred19_Script438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred20_Script503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_synpred21_Script481 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LEFT_P_in_synpred21_Script483 = new BitSet(new long[]{0x0000000E00202D90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred21_Script487 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_PV_in_synpred21_Script489 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_expression_in_synpred21_Script493 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_PV_in_synpred21_Script495 = new BitSet(new long[]{0x0000000E00202D90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred21_Script499 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RIGHT_P_in_synpred21_Script501 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred21_Script503 = new BitSet(new long[]{0x0000000E00233DB0L});
    public static final BitSet FOLLOW_stat_in_synpred21_Script508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred22_Script530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARROW_in_synpred23_Script560 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_range_in_synpred23_Script564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred41_Script926 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_args_in_synpred41_Script930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred50_Script1103 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred50_Script1106 = new BitSet(new long[]{0x0000000E00202D30L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred50_Script1108 = new BitSet(new long[]{0x0000000E00202D10L});
    public static final BitSet FOLLOW_dictionary_elements_in_synpred50_Script1113 = new BitSet(new long[]{0x0000000000000002L});

}