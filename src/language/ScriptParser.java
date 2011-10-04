// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g 2011-10-04 17:28:06

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "BREAK", "CONTINUE", "ID", "EQUAL", "PLUS_PLUS", "MINUS_MINUS", "IF", "LEFT_P", "RIGHT_P", "ELSE", "WHILE", "FOR", "PV", "ARROW", "PLUS", "MINUS", "AND", "OR", "MULT", "DIV", "CMP_LT", "CMP_LT_EQ", "CMP_GT", "CMP_GT_EQ", "CMP_EQ", "CMP_NEQ", "COMMA", "NUM", "BOOL", "STRING_LITERAL", "TP", "DQUOTE", "LEFT_B", "RIGHT_B", "WS"
    };
    public static final int CMP_NEQ=32;
    public static final int WHILE=17;
    public static final int ELSE=16;
    public static final int TP=37;
    public static final int BOOL=35;
    public static final int RIGHT_P=15;
    public static final int DQUOTE=38;
    public static final int FOR=18;
    public static final int CMP_EQ=31;
    public static final int MULT=25;
    public static final int MINUS=22;
    public static final int AND=23;
    public static final int ID=9;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int CMP_GT=29;
    public static final int BREAK=7;
    public static final int NUM=34;
    public static final int RIGHT_CB=6;
    public static final int IF=13;
    public static final int CMP_GT_EQ=30;
    public static final int CMP_LT=27;
    public static final int WS=41;
    public static final int RIGHT_B=40;
    public static final int STRING_LITERAL=36;
    public static final int PLUS_PLUS=11;
    public static final int NEWLINE=5;
    public static final int CONTINUE=8;
    public static final int COMMA=33;
    public static final int CMP_LT_EQ=28;
    public static final int EQUAL=10;
    public static final int OR=24;
    public static final int LEFT_B=39;
    public static final int ARROW=20;
    public static final int PV=19;
    public static final int PLUS=21;
    public static final int DIV=26;
    public static final int LEFT_P=14;
    public static final int MINUS_MINUS=12;

    // delegates
    // delegators


        public ScriptParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ScriptParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[73+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ScriptParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g"; }



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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:189:1: prog : s= stats ;
    public final ScriptParser.prog_return prog() throws RecognitionException {
        ScriptParser.prog_return retval = new ScriptParser.prog_return();
        retval.start = input.LT(1);
        int prog_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stats_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:189:6: (s= stats )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:190:5: s= stats
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:197:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:197:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:198:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block107); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:198:13: ( NEWLINE )?
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:198:28: ( NEWLINE )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NEWLINE) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:202:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final ScriptParser.stats_return stats() throws RecognitionException {
        ScriptParser.stats_return retval = new ScriptParser.stats_return();
        retval.start = input.LT(1);
        int stats_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stat_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:202:51: (s= stat (s= stat )* )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:203:5: s= stat (s= stat )*
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:208:7: (s= stat )*
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
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:208:8: s= stat
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:215:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:216:5: ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:216:7: pre_stat NEWLINE
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:220:7: NEWLINE
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:223:7: block
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:226:7: if_expression
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:229:7: while_expression
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:232:7: for_expression
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:235:1: pre_stat returns [Expression expr] : ( expression | BREAK | CONTINUE | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.pre_stat_return pre_stat() throws RecognitionException {
        ScriptParser.pre_stat_return retval = new ScriptParser.pre_stat_return();
        retval.start = input.LT(1);
        int pre_stat_StartIndex = input.index();
        Object root_0 = null;

        Token BREAK14=null;
        Token CONTINUE15=null;
        Token ID16=null;
        Token EQUAL17=null;
        Token ID19=null;
        Token PLUS_PLUS20=null;
        Token PLUS_PLUS21=null;
        Token ID22=null;
        Token ID23=null;
        Token MINUS_MINUS24=null;
        Token MINUS_MINUS25=null;
        Token ID26=null;
        ScriptParser.expression_return expression13 = null;

        ScriptParser.expression_return expression18 = null;


        Object BREAK14_tree=null;
        Object CONTINUE15_tree=null;
        Object ID16_tree=null;
        Object EQUAL17_tree=null;
        Object ID19_tree=null;
        Object PLUS_PLUS20_tree=null;
        Object PLUS_PLUS21_tree=null;
        Object ID22_tree=null;
        Object ID23_tree=null;
        Object MINUS_MINUS24_tree=null;
        Object MINUS_MINUS25_tree=null;
        Object ID26_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:236:5: ( expression | BREAK | CONTINUE | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
            int alt5=8;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:236:7: expression
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:239:7: BREAK
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:242:7: CONTINUE
                    {
                    root_0 = (Object)adaptor.nil();

                    CONTINUE15=(Token)match(input,CONTINUE,FOLLOW_CONTINUE_in_pre_stat257); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    CONTINUE15_tree = (Object)adaptor.create(CONTINUE15);
                    adaptor.addChild(root_0, CONTINUE15_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, ReturnValue.RETURN_CONTINUE);
                          
                    }

                    }
                    break;
                case 4 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:245:7: ID EQUAL expression
                    {
                    root_0 = (Object)adaptor.nil();

                    ID16=(Token)match(input,ID,FOLLOW_ID_in_pre_stat267); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID16_tree = (Object)adaptor.create(ID16);
                    adaptor.addChild(root_0, ID16_tree);
                    }
                    EQUAL17=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_stat269); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL17_tree = (Object)adaptor.create(EQUAL17);
                    adaptor.addChild(root_0, EQUAL17_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_stat271);
                    expression18=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression18.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new VariableAssignment(this, (ID16!=null?ID16.getText():null), (expression18!=null?expression18.expr:null)) );
                          
                    }

                    }
                    break;
                case 5 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:248:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID19=(Token)match(input,ID,FOLLOW_ID_in_pre_stat281); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID19_tree = (Object)adaptor.create(ID19);
                    adaptor.addChild(root_0, ID19_tree);
                    }
                    PLUS_PLUS20=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat283); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS20_tree = (Object)adaptor.create(PLUS_PLUS20);
                    adaptor.addChild(root_0, PLUS_PLUS20_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new VariableAssignment(this, (ID19!=null?ID19.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 6 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:251:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS21=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat293); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS21_tree = (Object)adaptor.create(PLUS_PLUS21);
                    adaptor.addChild(root_0, PLUS_PLUS21_tree);
                    }
                    ID22=(Token)match(input,ID,FOLLOW_ID_in_pre_stat295); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID22_tree = (Object)adaptor.create(ID22);
                    adaptor.addChild(root_0, ID22_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID22!=null?ID22.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 7 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:254:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID23=(Token)match(input,ID,FOLLOW_ID_in_pre_stat305); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID23_tree = (Object)adaptor.create(ID23);
                    adaptor.addChild(root_0, ID23_tree);
                    }
                    MINUS_MINUS24=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat307); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS24_tree = (Object)adaptor.create(MINUS_MINUS24);
                    adaptor.addChild(root_0, MINUS_MINUS24_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID23!=null?ID23.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
                    }

                    }
                    break;
                case 8 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:257:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS25=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat317); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS25_tree = (Object)adaptor.create(MINUS_MINUS25);
                    adaptor.addChild(root_0, MINUS_MINUS25_tree);
                    }
                    ID26=(Token)match(input,ID,FOLLOW_ID_in_pre_stat319); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID26_tree = (Object)adaptor.create(ID26);
                    adaptor.addChild(root_0, ID26_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID26!=null?ID26.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:263:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final ScriptParser.if_expression_return if_expression() throws RecognitionException {
        ScriptParser.if_expression_return retval = new ScriptParser.if_expression_return();
        retval.start = input.LT(1);
        int if_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_if_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:264:5: (p= pre_if_expression )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:264:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression349);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:280:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final ScriptParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        ScriptParser.pre_if_expression_return retval = new ScriptParser.pre_if_expression_return();
        retval.start = input.LT(1);
        int pre_if_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IF27=null;
        Token LEFT_P28=null;
        Token RIGHT_P29=null;
        Token NEWLINE30=null;
        Token NEWLINE31=null;
        Token ELSE32=null;
        Token NEWLINE33=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object IF27_tree=null;
        Object LEFT_P28_tree=null;
        Object RIGHT_P29_tree=null;
        Object NEWLINE30_tree=null;
        Object NEWLINE31_tree=null;
        Object ELSE32_tree=null;
        Object NEWLINE33_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:281:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:281:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF27=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression368); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF27_tree = (Object)adaptor.create(IF27);
            adaptor.addChild(root_0, IF27_tree);
            }
            LEFT_P28=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression370); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P28_tree = (Object)adaptor.create(LEFT_P28);
            adaptor.addChild(root_0, LEFT_P28_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression374);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P29=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression376); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P29_tree = (Object)adaptor.create(RIGHT_P29);
            adaptor.addChild(root_0, RIGHT_P29_tree);
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:281:38: ( NEWLINE )?
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                    {
                    NEWLINE30=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression378); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE30_tree = (Object)adaptor.create(NEWLINE30);
                    adaptor.addChild(root_0, NEWLINE30_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_if_expression383);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:7: ( NEWLINE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NEWLINE) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred17_Script()) ) {
                    alt7=1;
                }
            }
            switch (alt7) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                    {
                    NEWLINE31=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression387); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE31_tree = (Object)adaptor.create(NEWLINE31);
                    adaptor.addChild(root_0, NEWLINE31_tree);
                    }

                    }
                    break;

            }

            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==ELSE) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred19_Script()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE32=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression391); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE32_tree = (Object)adaptor.create(ELSE32);
                    adaptor.addChild(root_0, ELSE32_tree);
                    }
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:22: ( NEWLINE )?
                    int alt8=2;
                    alt8 = dfa8.predict(input);
                    switch (alt8) {
                        case 1 :
                            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                            {
                            NEWLINE33=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression393); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE33_tree = (Object)adaptor.create(NEWLINE33);
                            adaptor.addChild(root_0, NEWLINE33_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression398);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:289:1: while_expression returns [LoopExpression expr] : p= pre_while_expression ;
    public final ScriptParser.while_expression_return while_expression() throws RecognitionException {
        ScriptParser.while_expression_return retval = new ScriptParser.while_expression_return();
        retval.start = input.LT(1);
        int while_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_while_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:290:5: (p= pre_while_expression )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:290:7: p= pre_while_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_while_expression_in_while_expression420);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:302:1: pre_while_expression returns [LinkedList<Expression> exprs] : WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ;
    public final ScriptParser.pre_while_expression_return pre_while_expression() throws RecognitionException {
        ScriptParser.pre_while_expression_return retval = new ScriptParser.pre_while_expression_return();
        retval.start = input.LT(1);
        int pre_while_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WHILE34=null;
        Token LEFT_P35=null;
        Token RIGHT_P36=null;
        Token NEWLINE37=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object WHILE34_tree=null;
        Object LEFT_P35_tree=null;
        Object RIGHT_P36_tree=null;
        Object NEWLINE37_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:303:5: ( WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:303:7: WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            WHILE34=(Token)match(input,WHILE,FOLLOW_WHILE_in_pre_while_expression438); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE34_tree = (Object)adaptor.create(WHILE34);
            adaptor.addChild(root_0, WHILE34_tree);
            }
            LEFT_P35=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_while_expression440); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P35_tree = (Object)adaptor.create(LEFT_P35);
            adaptor.addChild(root_0, LEFT_P35_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_while_expression444);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P36=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_while_expression446); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P36_tree = (Object)adaptor.create(RIGHT_P36);
            adaptor.addChild(root_0, RIGHT_P36_tree);
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:303:41: ( NEWLINE )?
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                    {
                    NEWLINE37=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_while_expression448); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE37_tree = (Object)adaptor.create(NEWLINE37);
                    adaptor.addChild(root_0, NEWLINE37_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_while_expression453);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:309:1: for_expression returns [LoopExpression expr] : p= pre_for_expression ;
    public final ScriptParser.for_expression_return for_expression() throws RecognitionException {
        ScriptParser.for_expression_return retval = new ScriptParser.for_expression_return();
        retval.start = input.LT(1);
        int for_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_for_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:310:5: (p= pre_for_expression )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:310:7: p= pre_for_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_for_expression_in_for_expression473);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:322:1: pre_for_expression returns [LinkedList<Expression> exprs] : ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat );
    public final ScriptParser.pre_for_expression_return pre_for_expression() throws RecognitionException {
        ScriptParser.pre_for_expression_return retval = new ScriptParser.pre_for_expression_return();
        retval.start = input.LT(1);
        int pre_for_expression_StartIndex = input.index();
        Object root_0 = null;

        Token FOR38=null;
        Token LEFT_P39=null;
        Token PV40=null;
        Token PV41=null;
        Token RIGHT_P42=null;
        Token NEWLINE43=null;
        Token FOR44=null;
        Token LEFT_P45=null;
        Token ID46=null;
        Token EQUAL47=null;
        Token RIGHT_P49=null;
        Token NEWLINE50=null;
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;

        ScriptParser.range_return range48 = null;


        Object FOR38_tree=null;
        Object LEFT_P39_tree=null;
        Object PV40_tree=null;
        Object PV41_tree=null;
        Object RIGHT_P42_tree=null;
        Object NEWLINE43_tree=null;
        Object FOR44_tree=null;
        Object LEFT_P45_tree=null;
        Object ID46_tree=null;
        Object EQUAL47_tree=null;
        Object RIGHT_P49_tree=null;
        Object NEWLINE50_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:5: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==FOR) ) {
                int LA13_1 = input.LA(2);

                if ( (synpred22_Script()) ) {
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR38=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression491); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR38_tree = (Object)adaptor.create(FOR38);
                    adaptor.addChild(root_0, FOR38_tree);
                    }
                    LEFT_P39=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression493); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P39_tree = (Object)adaptor.create(LEFT_P39);
                    adaptor.addChild(root_0, LEFT_P39_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression497);
                    e_init=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_init.getTree());
                    PV40=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression499); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV40_tree = (Object)adaptor.create(PV40);
                    adaptor.addChild(root_0, PV40_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_for_expression503);
                    e_cond=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_cond.getTree());
                    PV41=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression505); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV41_tree = (Object)adaptor.create(PV41);
                    adaptor.addChild(root_0, PV41_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression509);
                    e_inc=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_inc.getTree());
                    RIGHT_P42=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression511); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P42_tree = (Object)adaptor.create(RIGHT_P42);
                    adaptor.addChild(root_0, RIGHT_P42_tree);
                    }
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:81: ( NEWLINE )?
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                            {
                            NEWLINE43=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression513); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE43_tree = (Object)adaptor.create(NEWLINE43);
                            adaptor.addChild(root_0, NEWLINE43_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression518);
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:330:7: FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR44=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression528); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR44_tree = (Object)adaptor.create(FOR44);
                    adaptor.addChild(root_0, FOR44_tree);
                    }
                    LEFT_P45=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression530); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P45_tree = (Object)adaptor.create(LEFT_P45);
                    adaptor.addChild(root_0, LEFT_P45_tree);
                    }
                    ID46=(Token)match(input,ID,FOLLOW_ID_in_pre_for_expression532); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID46_tree = (Object)adaptor.create(ID46);
                    adaptor.addChild(root_0, ID46_tree);
                    }
                    EQUAL47=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_for_expression534); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL47_tree = (Object)adaptor.create(EQUAL47);
                    adaptor.addChild(root_0, EQUAL47_tree);
                    }
                    pushFollow(FOLLOW_range_in_pre_for_expression536);
                    range48=range();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, range48.getTree());
                    RIGHT_P49=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression538); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P49_tree = (Object)adaptor.create(RIGHT_P49);
                    adaptor.addChild(root_0, RIGHT_P49_tree);
                    }
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:330:41: ( NEWLINE )?
                    int alt12=2;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                            {
                            NEWLINE50=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression540); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE50_tree = (Object)adaptor.create(NEWLINE50);
                            adaptor.addChild(root_0, NEWLINE50_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression545);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    if ( state.backtracking==0 ) {

                              retval.exprs = new LinkedList();

                              boolean plus_minus = true;

                              Calculable init = (range48!=null?range48.range_ele:null).get(0);
                              Calculable increment = (range48!=null?range48.range_ele:null).size()==3 ? (range48!=null?range48.range_ele:null).get(1) : new Numeric(1.0f);
                              Calculable condition = (range48!=null?range48.range_ele:null).get((range48!=null?range48.range_ele:null).size()-1);

                              try {
                                  if (increment.getSimplifiedCalculable() instanceof Numeric) {
                                      double val = (Double) ((Numeric) increment.getSimplifiedCalculable()).getNativeValue();
                                      plus_minus = val>=0;
                                  }
                              } catch (Exception e) {
                                  //NOTHING
                              }

                              VariableAssignment va = new VariableAssignment(this, (ID46!=null?ID46.getText():null), init);
                              Expression init_expr = new Expression(true, this, va);
                              
                              LinkedList<Object> term_ele = new LinkedList();
                              term_ele.add(new Variable(this, (ID46!=null?ID46.getText():null)));
                              term_ele.add(Operator.OPERATOR_PLUS);
                              term_ele.add(increment);
                              Term t = new Term(this, term_ele);
                              VariableAssignment vai = new VariableAssignment(this, (ID46!=null?ID46.getText():null), t);
                              Expression increment_expr = new Expression(true, this, vai);

                              LinkedList<Object> term_elec = new LinkedList();
                              term_elec.add(new Variable(this, (ID46!=null?ID46.getText():null)));
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:373:1: range returns [LinkedList<Calculable> range_ele] : a= expression ( ARROW b= range )* ;
    public final ScriptParser.range_return range() throws RecognitionException {
        ScriptParser.range_return retval = new ScriptParser.range_return();
        retval.start = input.LT(1);
        int range_StartIndex = input.index();
        Object root_0 = null;

        Token ARROW51=null;
        ScriptParser.expression_return a = null;

        ScriptParser.range_return b = null;


        Object ARROW51_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:374:5: (a= expression ( ARROW b= range )* )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:374:7: a= expression ( ARROW b= range )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_range565);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.range_ele = new LinkedList();
                      retval.range_ele.add((a!=null?a.expr:null));
                  
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:377:7: ( ARROW b= range )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==ARROW) ) {
                    int LA14_2 = input.LA(2);

                    if ( (synpred24_Script()) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:377:8: ARROW b= range
            	    {
            	    ARROW51=(Token)match(input,ARROW,FOLLOW_ARROW_in_range570); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    ARROW51_tree = (Object)adaptor.create(ARROW51);
            	    adaptor.addChild(root_0, ARROW51_tree);
            	    }
            	    pushFollow(FOLLOW_range_in_range574);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:384:1: expression returns [Expression expr] : ( terms | function_call );
    public final ScriptParser.expression_return expression() throws RecognitionException {
        ScriptParser.expression_return retval = new ScriptParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.terms_return terms52 = null;

        ScriptParser.function_call_return function_call53 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:385:5: ( terms | function_call )
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:385:7: terms
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_terms_in_expression595);
                    terms52=terms();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, terms52.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new Term(this, (terms52!=null?terms52.terms:null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:388:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_expression605);
                    function_call53=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call53.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new FunctionCall( this, (function_call53!=null?function_call53.name_params:null) ) );
                          
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:392:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* ;
    public final ScriptParser.terms_return terms() throws RecognitionException {
        ScriptParser.terms_return retval = new ScriptParser.terms_return();
        retval.start = input.LT(1);
        int terms_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS54=null;
        Token MINUS55=null;
        Token AND56=null;
        Token OR57=null;
        ScriptParser.term_return t = null;


        Object PLUS54_tree=null;
        Object MINUS55_tree=null;
        Object AND56_tree=null;
        Object OR57_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:393:5: (t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:393:7: t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms625);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:396:7: ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
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
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:396:9: PLUS t= term
            	    {
            	    PLUS54=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms631); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS54_tree = (Object)adaptor.create(PLUS54);
            	    adaptor.addChild(root_0, PLUS54_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms635);
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
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:400:9: MINUS t= term
            	    {
            	    MINUS55=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms647); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS55_tree = (Object)adaptor.create(MINUS55);
            	    adaptor.addChild(root_0, MINUS55_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms651);
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
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:404:9: AND t= term
            	    {
            	    AND56=(Token)match(input,AND,FOLLOW_AND_in_terms663); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND56_tree = (Object)adaptor.create(AND56);
            	    adaptor.addChild(root_0, AND56_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms667);
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
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:408:9: OR t= term
            	    {
            	    OR57=(Token)match(input,OR,FOLLOW_OR_in_terms679); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR57_tree = (Object)adaptor.create(OR57);
            	    adaptor.addChild(root_0, OR57_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms683);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:414:1: term returns [LinkedList<Object> atoms] : (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.term_return term() throws RecognitionException {
        ScriptParser.term_return retval = new ScriptParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token MULT58=null;
        Token DIV59=null;
        Token CMP_LT60=null;
        Token CMP_LT_EQ61=null;
        Token CMP_GT62=null;
        Token CMP_GT_EQ63=null;
        Token CMP_EQ64=null;
        Token CMP_NEQ65=null;
        Token ID66=null;
        Token PLUS_PLUS67=null;
        Token PLUS_PLUS68=null;
        Token ID69=null;
        Token ID70=null;
        Token MINUS_MINUS71=null;
        Token MINUS_MINUS72=null;
        Token ID73=null;
        ScriptParser.atom_return a = null;


        Object MULT58_tree=null;
        Object DIV59_tree=null;
        Object CMP_LT60_tree=null;
        Object CMP_LT_EQ61_tree=null;
        Object CMP_GT62_tree=null;
        Object CMP_GT_EQ63_tree=null;
        Object CMP_EQ64_tree=null;
        Object CMP_NEQ65_tree=null;
        Object ID66_tree=null;
        Object PLUS_PLUS67_tree=null;
        Object PLUS_PLUS68_tree=null;
        Object ID69_tree=null;
        Object ID70_tree=null;
        Object MINUS_MINUS71_tree=null;
        Object MINUS_MINUS72_tree=null;
        Object ID73_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:415:5: (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:415:7: a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term707);
                    a=atom();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              retval.atoms.add((a!=null?a.value:null));
                          
                    }
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:418:7: ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:418:9: MULT a= atom
                    	    {
                    	    MULT58=(Token)match(input,MULT,FOLLOW_MULT_in_term713); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    MULT58_tree = (Object)adaptor.create(MULT58);
                    	    adaptor.addChild(root_0, MULT58_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term717);
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:422:9: DIV a= atom
                    	    {
                    	    DIV59=(Token)match(input,DIV,FOLLOW_DIV_in_term729); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DIV59_tree = (Object)adaptor.create(DIV59);
                    	    adaptor.addChild(root_0, DIV59_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term733);
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:426:9: CMP_LT a= atom
                    	    {
                    	    CMP_LT60=(Token)match(input,CMP_LT,FOLLOW_CMP_LT_in_term745); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT60_tree = (Object)adaptor.create(CMP_LT60);
                    	    adaptor.addChild(root_0, CMP_LT60_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term749);
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:430:9: CMP_LT_EQ a= atom
                    	    {
                    	    CMP_LT_EQ61=(Token)match(input,CMP_LT_EQ,FOLLOW_CMP_LT_EQ_in_term762); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT_EQ61_tree = (Object)adaptor.create(CMP_LT_EQ61);
                    	    adaptor.addChild(root_0, CMP_LT_EQ61_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term766);
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:434:9: CMP_GT a= atom
                    	    {
                    	    CMP_GT62=(Token)match(input,CMP_GT,FOLLOW_CMP_GT_in_term779); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT62_tree = (Object)adaptor.create(CMP_GT62);
                    	    adaptor.addChild(root_0, CMP_GT62_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term783);
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:438:9: CMP_GT_EQ a= atom
                    	    {
                    	    CMP_GT_EQ63=(Token)match(input,CMP_GT_EQ,FOLLOW_CMP_GT_EQ_in_term795); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT_EQ63_tree = (Object)adaptor.create(CMP_GT_EQ63);
                    	    adaptor.addChild(root_0, CMP_GT_EQ63_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term799);
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:442:9: CMP_EQ a= atom
                    	    {
                    	    CMP_EQ64=(Token)match(input,CMP_EQ,FOLLOW_CMP_EQ_in_term812); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_EQ64_tree = (Object)adaptor.create(CMP_EQ64);
                    	    adaptor.addChild(root_0, CMP_EQ64_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term816);
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
                    	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:446:9: CMP_NEQ a= atom
                    	    {
                    	    CMP_NEQ65=(Token)match(input,CMP_NEQ,FOLLOW_CMP_NEQ_in_term828); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_NEQ65_tree = (Object)adaptor.create(CMP_NEQ65);
                    	    adaptor.addChild(root_0, CMP_NEQ65_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term832);
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
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:451:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID66=(Token)match(input,ID,FOLLOW_ID_in_term849); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID66_tree = (Object)adaptor.create(ID66);
                    adaptor.addChild(root_0, ID66_tree);
                    }
                    PLUS_PLUS67=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term851); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS67_tree = (Object)adaptor.create(PLUS_PLUS67);
                    adaptor.addChild(root_0, PLUS_PLUS67_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID66!=null?ID66.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 3 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:456:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS68=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term861); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS68_tree = (Object)adaptor.create(PLUS_PLUS68);
                    adaptor.addChild(root_0, PLUS_PLUS68_tree);
                    }
                    ID69=(Token)match(input,ID,FOLLOW_ID_in_term863); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID69_tree = (Object)adaptor.create(ID69);
                    adaptor.addChild(root_0, ID69_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID69!=null?ID69.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 4 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:461:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID70=(Token)match(input,ID,FOLLOW_ID_in_term873); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID70_tree = (Object)adaptor.create(ID70);
                    adaptor.addChild(root_0, ID70_tree);
                    }
                    MINUS_MINUS71=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term875); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS71_tree = (Object)adaptor.create(MINUS_MINUS71);
                    adaptor.addChild(root_0, MINUS_MINUS71_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID70!=null?ID70.getText():null), Operator.OPERATOR_MINUS_MINUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 5 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:466:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS72=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term885); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS72_tree = (Object)adaptor.create(MINUS_MINUS72);
                    adaptor.addChild(root_0, MINUS_MINUS72_tree);
                    }
                    ID73=(Token)match(input,ID,FOLLOW_ID_in_term887); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID73_tree = (Object)adaptor.create(ID73);
                    adaptor.addChild(root_0, ID73_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID73!=null?ID73.getText():null), Operator.OPERATOR_MINUS_MINUS);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:473:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P ( args )? RIGHT_P ;
    public final ScriptParser.function_call_return function_call() throws RecognitionException {
        ScriptParser.function_call_return retval = new ScriptParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token ID74=null;
        Token LEFT_P75=null;
        Token RIGHT_P77=null;
        ScriptParser.args_return args76 = null;


        Object ID74_tree=null;
        Object LEFT_P75_tree=null;
        Object RIGHT_P77_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:473:55: ( ID LEFT_P ( args )? RIGHT_P )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:474:5: ID LEFT_P ( args )? RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID74=(Token)match(input,ID,FOLLOW_ID_in_function_call905); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID74_tree = (Object)adaptor.create(ID74);
            adaptor.addChild(root_0, ID74_tree);
            }
            LEFT_P75=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call907); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P75_tree = (Object)adaptor.create(LEFT_P75);
            adaptor.addChild(root_0, LEFT_P75_tree);
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:474:15: ( args )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==LEFT_CB||LA19_0==ID||(LA19_0>=PLUS_PLUS && LA19_0<=MINUS_MINUS)||LA19_0==LEFT_P||LA19_0==MINUS||(LA19_0>=NUM && LA19_0<=STRING_LITERAL)) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: args
                    {
                    pushFollow(FOLLOW_args_in_function_call909);
                    args76=args();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, args76.getTree());

                    }
                    break;

            }

            RIGHT_P77=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call912); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P77_tree = (Object)adaptor.create(RIGHT_P77);
            adaptor.addChild(root_0, RIGHT_P77_tree);
            }
            if ( state.backtracking==0 ) {

                      if ((args76!=null?args76.params:null) != null) {
                          retval.name_params = (args76!=null?args76.params:null);
                          retval.name_params.add(0, (ID74!=null?ID74.getText():null));
                      } else {
                          retval.name_params = new LinkedList();
                          retval.name_params.add((ID74!=null?ID74.getText():null));
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:485:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final ScriptParser.args_return args() throws RecognitionException {
        ScriptParser.args_return retval = new ScriptParser.args_return();
        retval.start = input.LT(1);
        int args_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA78=null;
        ScriptParser.expression_return a = null;

        ScriptParser.args_return b = null;


        Object COMMA78_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:485:41: (a= expression ( COMMA b= args )* )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:486:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args932);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:489:7: ( COMMA b= args )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==COMMA) ) {
                    int LA20_2 = input.LA(2);

                    if ( (synpred43_Script()) ) {
                        alt20=1;
                    }


                }


                switch (alt20) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:489:8: COMMA b= args
            	    {
            	    COMMA78=(Token)match(input,COMMA,FOLLOW_COMMA_in_args937); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA78_tree = (Object)adaptor.create(COMMA78);
            	    adaptor.addChild(root_0, COMMA78_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args941);
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:498:1: atom returns [Object value] : ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary );
    public final ScriptParser.atom_return atom() throws RecognitionException {
        ScriptParser.atom_return retval = new ScriptParser.atom_return();
        retval.start = input.LT(1);
        int atom_StartIndex = input.index();
        Object root_0 = null;

        Token NUM79=null;
        Token MINUS80=null;
        Token NUM81=null;
        Token BOOL82=null;
        Token LEFT_P83=null;
        Token RIGHT_P85=null;
        Token ID86=null;
        ScriptParser.expression_return expression84 = null;

        ScriptParser.string_literal_return string_literal87 = null;

        ScriptParser.dictionary_return dictionary88 = null;


        Object NUM79_tree=null;
        Object MINUS80_tree=null;
        Object NUM81_tree=null;
        Object BOOL82_tree=null;
        Object LEFT_P83_tree=null;
        Object RIGHT_P85_tree=null;
        Object ID86_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:499:5: ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary )
            int alt21=7;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt21=1;
                }
                break;
            case MINUS:
                {
                alt21=2;
                }
                break;
            case BOOL:
                {
                alt21=3;
                }
                break;
            case LEFT_P:
                {
                alt21=4;
                }
                break;
            case ID:
                {
                alt21=5;
                }
                break;
            case STRING_LITERAL:
                {
                alt21=6;
                }
                break;
            case LEFT_CB:
                {
                alt21=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:499:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM79=(Token)match(input,NUM,FOLLOW_NUM_in_atom964); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM79_tree = (Object)adaptor.create(NUM79);
                    adaptor.addChild(root_0, NUM79_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Float.parseFloat((NUM79!=null?NUM79.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:502:7: MINUS NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS80=(Token)match(input,MINUS,FOLLOW_MINUS_in_atom974); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS80_tree = (Object)adaptor.create(MINUS80);
                    adaptor.addChild(root_0, MINUS80_tree);
                    }
                    NUM81=(Token)match(input,NUM,FOLLOW_NUM_in_atom976); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM81_tree = (Object)adaptor.create(NUM81);
                    adaptor.addChild(root_0, NUM81_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( -1.0 * Float.parseFloat((NUM81!=null?NUM81.getText():null)) );
                          
                    }

                    }
                    break;
                case 3 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:505:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL82=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom986); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL82_tree = (Object)adaptor.create(BOOL82);
                    adaptor.addChild(root_0, BOOL82_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL82!=null?BOOL82.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL82!=null?BOOL82.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              }
                          
                    }

                    }
                    break;
                case 4 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:512:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P83=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom996); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P83_tree = (Object)adaptor.create(LEFT_P83);
                    adaptor.addChild(root_0, LEFT_P83_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom998);
                    expression84=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression84.getTree());
                    RIGHT_P85=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom1000); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P85_tree = (Object)adaptor.create(RIGHT_P85);
                    adaptor.addChild(root_0, RIGHT_P85_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression84!=null?expression84.expr:null);
                          
                    }

                    }
                    break;
                case 5 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:515:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID86=(Token)match(input,ID,FOLLOW_ID_in_atom1010); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID86_tree = (Object)adaptor.create(ID86);
                    adaptor.addChild(root_0, ID86_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID86!=null?ID86.getText():null));
                          
                    }

                    }
                    break;
                case 6 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:518:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom1020);
                    string_literal87=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal87.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal87!=null?string_literal87.value:null);
                          
                    }

                    }
                    break;
                case 7 :
                    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:521:7: dictionary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dictionary_in_atom1030);
                    dictionary88=dictionary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary88.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (dictionary88!=null?dictionary88.value:null);
                          
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:533:1: string_literal returns [CharString value] : s= STRING_LITERAL ;
    public final ScriptParser.string_literal_return string_literal() throws RecognitionException {
        ScriptParser.string_literal_return retval = new ScriptParser.string_literal_return();
        retval.start = input.LT(1);
        int string_literal_StartIndex = input.index();
        Object root_0 = null;

        Token s=null;

        Object s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:533:43: (s= STRING_LITERAL )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:533:45: s= STRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_string_literal1058); if (state.failed) return retval;
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:538:1: dictionary returns [Dictionary value] : LEFT_CB dictionary_elements RIGHT_CB ;
    public final ScriptParser.dictionary_return dictionary() throws RecognitionException {
        ScriptParser.dictionary_return retval = new ScriptParser.dictionary_return();
        retval.start = input.LT(1);
        int dictionary_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB89=null;
        Token RIGHT_CB91=null;
        ScriptParser.dictionary_elements_return dictionary_elements90 = null;


        Object LEFT_CB89_tree=null;
        Object RIGHT_CB91_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:538:39: ( LEFT_CB dictionary_elements RIGHT_CB )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:539:5: LEFT_CB dictionary_elements RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB89=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_dictionary1077); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB89_tree = (Object)adaptor.create(LEFT_CB89);
            adaptor.addChild(root_0, LEFT_CB89_tree);
            }
            pushFollow(FOLLOW_dictionary_elements_in_dictionary1079);
            dictionary_elements90=dictionary_elements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary_elements90.getTree());
            RIGHT_CB91=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_dictionary1081); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB91_tree = (Object)adaptor.create(RIGHT_CB91);
            adaptor.addChild(root_0, RIGHT_CB91_tree);
            }
            if ( state.backtracking==0 ) {

                      HashMap vs = new HashMap();
                      int size = (dictionary_elements90!=null?dictionary_elements90.keys_values:null).size();
                      for (int k=0; k<size; k+=2) {
                          vs.put((dictionary_elements90!=null?dictionary_elements90.keys_values:null).get(k), (dictionary_elements90!=null?dictionary_elements90.keys_values:null).get(k+1));
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
    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:548:1: dictionary_elements returns [LinkedList<Object> keys_values] : (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* ;
    public final ScriptParser.dictionary_elements_return dictionary_elements() throws RecognitionException {
        ScriptParser.dictionary_elements_return retval = new ScriptParser.dictionary_elements_return();
        retval.start = input.LT(1);
        int dictionary_elements_StartIndex = input.index();
        Object root_0 = null;

        Token TP92=null;
        Token NEWLINE93=null;
        Token COMMA94=null;
        Token NEWLINE95=null;
        ScriptParser.expression_return e1 = null;

        ScriptParser.expression_return e2 = null;

        ScriptParser.dictionary_elements_return d = null;


        Object TP92_tree=null;
        Object NEWLINE93_tree=null;
        Object COMMA94_tree=null;
        Object NEWLINE95_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:548:62: ( (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:549:5: (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            {
            root_0 = (Object)adaptor.nil();

            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:549:5: (e1= expression TP e2= expression )
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:549:6: e1= expression TP e2= expression
            {
            pushFollow(FOLLOW_expression_in_dictionary_elements1102);
            e1=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
            TP92=(Token)match(input,TP,FOLLOW_TP_in_dictionary_elements1104); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TP92_tree = (Object)adaptor.create(TP92);
            adaptor.addChild(root_0, TP92_tree);
            }
            pushFollow(FOLLOW_expression_in_dictionary_elements1108);
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
            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:7: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==NEWLINE) ) {
                    int LA24_2 = input.LA(2);

                    if ( (synpred52_Script()) ) {
                        alt24=1;
                    }


                }
                else if ( (LA24_0==COMMA) ) {
                    int LA24_3 = input.LA(2);

                    if ( (synpred52_Script()) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
            	case 1 :
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
            	    {
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:8: ( NEWLINE )?
            	    int alt22=2;
            	    int LA22_0 = input.LA(1);

            	    if ( (LA22_0==NEWLINE) ) {
            	        alt22=1;
            	    }
            	    switch (alt22) {
            	        case 1 :
            	            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE93=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1114); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE93_tree = (Object)adaptor.create(NEWLINE93);
            	            adaptor.addChild(root_0, NEWLINE93_tree);
            	            }

            	            }
            	            break;

            	    }

            	    COMMA94=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictionary_elements1117); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA94_tree = (Object)adaptor.create(COMMA94);
            	    adaptor.addChild(root_0, COMMA94_tree);
            	    }
            	    // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:23: ( NEWLINE )?
            	    int alt23=2;
            	    int LA23_0 = input.LA(1);

            	    if ( (LA23_0==NEWLINE) ) {
            	        alt23=1;
            	    }
            	    switch (alt23) {
            	        case 1 :
            	            // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE95=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1119); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE95_tree = (Object)adaptor.create(NEWLINE95);
            	            adaptor.addChild(root_0, NEWLINE95_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_dictionary_elements_in_dictionary_elements1124);
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
            	    break loop24;
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
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:198:13: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:198:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Script109); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Script

    // $ANTLR start synpred3_Script
    public final void synpred3_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:208:8: (s= stat )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:208:8: s= stat
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
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:216:7: ( pre_stat NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:216:7: pre_stat NEWLINE
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
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:223:7: ( block )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:223:7: block
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
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:236:7: ( expression )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:236:7: expression
        {
        pushFollow(FOLLOW_expression_in_synpred9_Script237);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Script

    // $ANTLR start synpred13_Script
    public final void synpred13_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:248:7: ( ID PLUS_PLUS )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:248:7: ID PLUS_PLUS
        {
        match(input,ID,FOLLOW_ID_in_synpred13_Script281); if (state.failed) return ;
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred13_Script283); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Script

    // $ANTLR start synpred14_Script
    public final void synpred14_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:251:7: ( PLUS_PLUS ID )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:251:7: PLUS_PLUS ID
        {
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred14_Script293); if (state.failed) return ;
        match(input,ID,FOLLOW_ID_in_synpred14_Script295); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Script

    // $ANTLR start synpred15_Script
    public final void synpred15_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:254:7: ( ID MINUS_MINUS )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:254:7: ID MINUS_MINUS
        {
        match(input,ID,FOLLOW_ID_in_synpred15_Script305); if (state.failed) return ;
        match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_synpred15_Script307); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Script

    // $ANTLR start synpred16_Script
    public final void synpred16_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:281:38: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:281:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred16_Script378); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Script

    // $ANTLR start synpred17_Script
    public final void synpred17_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:7: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred17_Script387); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Script

    // $ANTLR start synpred18_Script
    public final void synpred18_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:22: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred18_Script393); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_Script

    // $ANTLR start synpred19_Script
    public final void synpred19_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:17: ( ELSE ( NEWLINE )? s= stat )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred19_Script391); if (state.failed) return ;
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:285:22: ( NEWLINE )?
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
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred19_Script393); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred19_Script398);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Script

    // $ANTLR start synpred20_Script
    public final void synpred20_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:303:41: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:303:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred20_Script448); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_Script

    // $ANTLR start synpred21_Script
    public final void synpred21_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:81: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:81: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred21_Script513); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_Script

    // $ANTLR start synpred22_Script
    public final void synpred22_Script_fragment() throws RecognitionException {   
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;


        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:7: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
        {
        match(input,FOR,FOLLOW_FOR_in_synpred22_Script491); if (state.failed) return ;
        match(input,LEFT_P,FOLLOW_LEFT_P_in_synpred22_Script493); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred22_Script497);
        e_init=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred22_Script499); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred22_Script503);
        e_cond=expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred22_Script505); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred22_Script509);
        e_inc=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,RIGHT_P,FOLLOW_RIGHT_P_in_synpred22_Script511); if (state.failed) return ;
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:323:81: ( NEWLINE )?
        int alt26=2;
        int LA26_0 = input.LA(1);

        if ( (LA26_0==NEWLINE) ) {
            int LA26_1 = input.LA(2);

            if ( ((LA26_1>=LEFT_CB && LA26_1<=NEWLINE)||(LA26_1>=BREAK && LA26_1<=ID)||(LA26_1>=PLUS_PLUS && LA26_1<=LEFT_P)||(LA26_1>=WHILE && LA26_1<=FOR)||LA26_1==MINUS||(LA26_1>=NUM && LA26_1<=STRING_LITERAL)) ) {
                alt26=1;
            }
        }
        switch (alt26) {
            case 1 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred22_Script513); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred22_Script518);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_Script

    // $ANTLR start synpred23_Script
    public final void synpred23_Script_fragment() throws RecognitionException {   
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:330:41: ( NEWLINE )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:330:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred23_Script540); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_Script

    // $ANTLR start synpred24_Script
    public final void synpred24_Script_fragment() throws RecognitionException {   
        ScriptParser.range_return b = null;


        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:377:8: ( ARROW b= range )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:377:8: ARROW b= range
        {
        match(input,ARROW,FOLLOW_ARROW_in_synpred24_Script570); if (state.failed) return ;
        pushFollow(FOLLOW_range_in_synpred24_Script574);
        b=range();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_Script

    // $ANTLR start synpred43_Script
    public final void synpred43_Script_fragment() throws RecognitionException {   
        ScriptParser.args_return b = null;


        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:489:8: ( COMMA b= args )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:489:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred43_Script937); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred43_Script941);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred43_Script

    // $ANTLR start synpred52_Script
    public final void synpred52_Script_fragment() throws RecognitionException {   
        ScriptParser.dictionary_elements_return d = null;


        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:8: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
        {
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:8: ( NEWLINE )?
        int alt28=2;
        int LA28_0 = input.LA(1);

        if ( (LA28_0==NEWLINE) ) {
            alt28=1;
        }
        switch (alt28) {
            case 1 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred52_Script1114); if (state.failed) return ;

                }
                break;

        }

        match(input,COMMA,FOLLOW_COMMA_in_synpred52_Script1117); if (state.failed) return ;
        // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:553:23: ( NEWLINE )?
        int alt29=2;
        int LA29_0 = input.LA(1);

        if ( (LA29_0==NEWLINE) ) {
            alt29=1;
        }
        switch (alt29) {
            case 1 :
                // D:\\PA-WPA-View\\GUI\\SCRIPTING\\WPAScript\\working_copy\\src\\language\\Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred52_Script1119); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_dictionary_elements_in_synpred52_Script1124);
        d=dictionary_elements();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred52_Script

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
    public final boolean synpred24_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_Script_fragment(); // can never throw exception
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
    public final boolean synpred52_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred52_Script_fragment(); // can never throw exception
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
    public final boolean synpred43_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred43_Script_fragment(); // can never throw exception
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
        "\21\uffff";
    static final String DFA1_eofS =
        "\21\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\17\uffff";
    static final String DFA1_maxS =
        "\1\44\1\0\17\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\15\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\17\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
                        if ( (synpred1_Script()) ) {s = 16;}

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
        "\21\uffff";
    static final String DFA4_eofS =
        "\21\uffff";
    static final String DFA4_minS =
        "\1\4\6\uffff\1\0\11\uffff";
    static final String DFA4_maxS =
        "\1\44\6\uffff\1\0\11\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\12\uffff\1\2\1\4\1\5\1\6\1\3";
    static final String DFA4_specialS =
        "\7\uffff\1\0\11\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\7\1\14\1\uffff\3\1\1\uffff\2\1\1\15\1\1\2\uffff\1\16\1\17"+
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

                        else if ( (synpred6_Script()) ) {s = 16;}

                         
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
        "\20\uffff";
    static final String DFA5_eofS =
        "\20\uffff";
    static final String DFA5_minS =
        "\1\4\1\uffff\1\5\2\11\2\uffff\2\0\1\uffff\2\0\4\uffff";
    static final String DFA5_maxS =
        "\1\44\1\uffff\1\40\2\11\2\uffff\2\0\1\uffff\2\0\4\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\3\uffff\1\2\1\3\2\uffff\1\4\2\uffff\1\5\1\7\1\6\1"+
        "\10";
    static final String DFA5_specialS =
        "\7\uffff\1\0\1\1\1\uffff\1\3\1\2\4\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1\2\uffff\1\5\1\6\1\2\1\uffff\1\3\1\4\1\uffff\1\1\7\uffff"+
            "\1\1\13\uffff\3\1",
            "",
            "\1\1\4\uffff\1\11\1\7\1\10\1\uffff\2\1\3\uffff\1\1\1\uffff"+
            "\14\1",
            "\1\12",
            "\1\13",
            "",
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
            return "235:1: pre_stat returns [Expression expr] : ( expression | BREAK | CONTINUE | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_7 = input.LA(1);

                         
                        int index5_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred13_Script()) ) {s = 12;}

                         
                        input.seek(index5_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_8 = input.LA(1);

                         
                        int index5_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred15_Script()) ) {s = 13;}

                         
                        input.seek(index5_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_11 = input.LA(1);

                         
                        int index5_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (true) ) {s = 15;}

                         
                        input.seek(index5_11);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_10 = input.LA(1);

                         
                        int index5_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred14_Script()) ) {s = 14;}

                         
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
        "\21\uffff";
    static final String DFA6_eofS =
        "\21\uffff";
    static final String DFA6_minS =
        "\1\4\1\0\17\uffff";
    static final String DFA6_maxS =
        "\1\44\1\0\17\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\15\uffff\1\1";
    static final String DFA6_specialS =
        "\1\uffff\1\0\17\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "281:38: ( NEWLINE )?";
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
                        if ( (synpred16_Script()) ) {s = 16;}

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
        "\21\uffff";
    static final String DFA8_eofS =
        "\21\uffff";
    static final String DFA8_minS =
        "\1\4\1\0\17\uffff";
    static final String DFA8_maxS =
        "\1\44\1\0\17\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\15\uffff\1\1";
    static final String DFA8_specialS =
        "\1\uffff\1\0\17\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "285:22: ( NEWLINE )?";
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
                        if ( (synpred18_Script()) ) {s = 16;}

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
        "\21\uffff";
    static final String DFA10_eofS =
        "\21\uffff";
    static final String DFA10_minS =
        "\1\4\1\0\17\uffff";
    static final String DFA10_maxS =
        "\1\44\1\0\17\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\15\uffff\1\1";
    static final String DFA10_specialS =
        "\1\uffff\1\0\17\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "303:41: ( NEWLINE )?";
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
                        if ( (synpred20_Script()) ) {s = 16;}

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
        "\21\uffff";
    static final String DFA11_eofS =
        "\21\uffff";
    static final String DFA11_minS =
        "\1\4\1\0\17\uffff";
    static final String DFA11_maxS =
        "\1\44\1\0\17\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\15\uffff\1\1";
    static final String DFA11_specialS =
        "\1\uffff\1\0\17\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "323:81: ( NEWLINE )?";
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
                        if ( (synpred21_Script()) ) {s = 16;}

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
        "\21\uffff";
    static final String DFA12_eofS =
        "\21\uffff";
    static final String DFA12_minS =
        "\1\4\1\0\17\uffff";
    static final String DFA12_maxS =
        "\1\44\1\0\17\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\15\uffff\1\1";
    static final String DFA12_specialS =
        "\1\uffff\1\0\17\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "330:41: ( NEWLINE )?";
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
                        if ( (synpred23_Script()) ) {s = 16;}

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
    public static final BitSet FOLLOW_LEFT_CB_in_block107 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_block109 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stats_in_block112 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block114 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats136 = new BitSet(new long[]{0x0000001C00467BB2L});
    public static final BitSet FOLLOW_stat_in_stats143 = new BitSet(new long[]{0x0000001C00467BB2L});
    public static final BitSet FOLLOW_pre_stat_in_stat168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_expression_in_stat210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_expression_in_stat220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_pre_stat237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_pre_stat247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONTINUE_in_pre_stat257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat267 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat269 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_expression_in_pre_stat271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat281 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat293 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_stat295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat305 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat317 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_stat319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression368 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression370 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression374 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression376 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression378 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression383 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression387 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression391 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression393 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_while_expression_in_while_expression420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_pre_while_expression438 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_while_expression440 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_expression_in_pre_while_expression444 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_while_expression446 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_while_expression448 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_while_expression453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_for_expression_in_for_expression473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression491 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression493 = new BitSet(new long[]{0x0000001C00405B90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression497 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression499 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_expression_in_pre_for_expression503 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression505 = new BitSet(new long[]{0x0000001C00405B90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression509 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression511 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression513 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression528 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression530 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_for_expression532 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_for_expression534 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_range_in_pre_for_expression536 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression538 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression540 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_range565 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ARROW_in_range570 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_range_in_range574 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_terms_in_expression595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms625 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_PLUS_in_terms631 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_term_in_terms635 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_MINUS_in_terms647 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_term_in_terms651 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_AND_in_terms663 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_term_in_terms667 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_OR_in_terms679 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_term_in_terms683 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_atom_in_term707 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_MULT_in_term713 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term717 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_DIV_in_term729 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term733 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_LT_in_term745 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term749 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_LT_EQ_in_term762 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term766 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_GT_in_term779 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term783 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_GT_EQ_in_term795 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term799 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_EQ_in_term812 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term816 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_NEQ_in_term828 = new BitSet(new long[]{0x0000001C00404210L});
    public static final BitSet FOLLOW_atom_in_term832 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_ID_in_term849 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term861 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_term863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term873 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term885 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_term887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_function_call905 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call907 = new BitSet(new long[]{0x0000001C0040DA10L});
    public static final BitSet FOLLOW_args_in_function_call909 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args932 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_COMMA_in_args937 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_args_in_args941 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_NUM_in_atom964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_atom974 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_NUM_in_atom976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom996 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_expression_in_atom998 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom1000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom1010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom1020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dictionary_in_atom1030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal1058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_dictionary1077 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary1079 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_dictionary1081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1102 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_TP_in_dictionary_elements1104 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1108 = new BitSet(new long[]{0x0000000200000022L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1114 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_COMMA_in_dictionary_elements1117 = new BitSet(new long[]{0x0000001C00405A30L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1119 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary_elements1124 = new BitSet(new long[]{0x0000000200000022L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Script109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Script143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_stat_in_synpred4_Script168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred4_Script170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_synpred6_Script190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred9_Script237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred13_Script281 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred13_Script283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred14_Script293 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_synpred14_Script295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred15_Script305 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_synpred15_Script307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred16_Script378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred17_Script387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred18_Script393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred19_Script391 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred19_Script393 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stat_in_synpred19_Script398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred20_Script448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred21_Script513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_synpred22_Script491 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_synpred22_Script493 = new BitSet(new long[]{0x0000001C00405B90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred22_Script497 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_synpred22_Script499 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_expression_in_synpred22_Script503 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_synpred22_Script505 = new BitSet(new long[]{0x0000001C00405B90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred22_Script509 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_synpred22_Script511 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred22_Script513 = new BitSet(new long[]{0x0000001C00467BB0L});
    public static final BitSet FOLLOW_stat_in_synpred22_Script518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred23_Script540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARROW_in_synpred24_Script570 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_range_in_synpred24_Script574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred43_Script937 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_args_in_synpred43_Script941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred52_Script1114 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred52_Script1117 = new BitSet(new long[]{0x0000001C00405A30L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred52_Script1119 = new BitSet(new long[]{0x0000001C00405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_synpred52_Script1124 = new BitSet(new long[]{0x0000000000000002L});

}