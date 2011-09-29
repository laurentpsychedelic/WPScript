// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPAScript/working_copy/src/language/Script.g 2011-09-29 21:01:16

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "ID", "EQUAL", "PLUS_PLUS", "MINUS_MINUS", "IF", "LEFT_P", "RIGHT_P", "ELSE", "WHILE", "FOR", "PV", "ARROW", "PLUS", "MINUS", "AND", "OR", "MULT", "DIV", "CMP_LT", "CMP_LT_EQ", "CMP_GT", "CMP_GT_EQ", "CMP_EQ", "CMP_NEQ", "COMMA", "NUM", "BOOL", "STRING_LITERAL", "TP", "DQUOTE", "LEFT_B", "RIGHT_B", "WS"
    };
    public static final int CMP_NEQ=30;
    public static final int WHILE=15;
    public static final int ELSE=14;
    public static final int TP=35;
    public static final int BOOL=33;
    public static final int RIGHT_P=13;
    public static final int DQUOTE=36;
    public static final int FOR=16;
    public static final int CMP_EQ=29;
    public static final int MULT=23;
    public static final int MINUS=20;
    public static final int AND=21;
    public static final int ID=7;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int CMP_GT=27;
    public static final int NUM=32;
    public static final int RIGHT_CB=6;
    public static final int IF=11;
    public static final int CMP_GT_EQ=28;
    public static final int CMP_LT=25;
    public static final int WS=39;
    public static final int RIGHT_B=38;
    public static final int STRING_LITERAL=34;
    public static final int PLUS_PLUS=9;
    public static final int NEWLINE=5;
    public static final int COMMA=31;
    public static final int CMP_LT_EQ=26;
    public static final int EQUAL=8;
    public static final int OR=22;
    public static final int LEFT_B=37;
    public static final int PV=17;
    public static final int ARROW=18;
    public static final int PLUS=19;
    public static final int DIV=24;
    public static final int LEFT_P=12;
    public static final int MINUS_MINUS=10;

    // delegates
    // delegators


        public ScriptParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ScriptParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[70+1];
             
             
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

        public boolean compilationCheck() throws PanicException {
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

        public Object execute() throws PanicException {
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
            throw new PanicException(message, line_num);
        }


        public void compilationError(String message, int line_num) {
            if (!_COMPILATION_ERROR_STATE_) {
                _COMPILATION_ERROR_STATE_ = true;
                System.err.println("COMPILATION");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);
        }


        public void compilationWarning(String message, int line_num) {
            if (!_COMPILATION_ERROR_STATE_) {
                _COMPILATION_ERROR_STATE_ = true;
                System.err.println("COMPILATION");
            }
            System.err.println("WARNING (l" + line_num + "):: " + message);
        }

        public void runtimeError(String message, int line_num) {
            if (!_RUNTIME_ERROR_STATE_) {
                _RUNTIME_ERROR_STATE_ = true;
                System.err.println("RUNTIME");
            }
            System.err.println("ERROR (l" + line_num + "):: " + message);
            
            dumpGlobalMemory(System.err);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:188:1: prog : s= stats ;
    public final ScriptParser.prog_return prog() throws RecognitionException {
        ScriptParser.prog_return retval = new ScriptParser.prog_return();
        retval.start = input.LT(1);
        int prog_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stats_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:188:6: (s= stats )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:189:5: s= stats
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:196:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:196:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:197:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block107); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:197:13: ( NEWLINE )?
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:197:28: ( NEWLINE )?
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:201:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final ScriptParser.stats_return stats() throws RecognitionException {
        ScriptParser.stats_return retval = new ScriptParser.stats_return();
        retval.start = input.LT(1);
        int stats_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stat_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:201:51: (s= stat (s= stat )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:202:5: s= stat (s= stat )*
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:207:7: (s= stat )*
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
                else if ( (LA3_0==LEFT_CB||LA3_0==ID||(LA3_0>=PLUS_PLUS && LA3_0<=LEFT_P)||(LA3_0>=WHILE && LA3_0<=FOR)||LA3_0==MINUS||(LA3_0>=NUM && LA3_0<=STRING_LITERAL)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:207:8: s= stat
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:214:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:215:5: ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:215:7: pre_stat NEWLINE
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:219:7: NEWLINE
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:222:7: block
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:225:7: if_expression
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:228:7: while_expression
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:231:7: for_expression
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:234:1: pre_stat returns [Expression expr] : ( expression | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.pre_stat_return pre_stat() throws RecognitionException {
        ScriptParser.pre_stat_return retval = new ScriptParser.pre_stat_return();
        retval.start = input.LT(1);
        int pre_stat_StartIndex = input.index();
        Object root_0 = null;

        Token ID14=null;
        Token EQUAL15=null;
        Token ID17=null;
        Token PLUS_PLUS18=null;
        Token PLUS_PLUS19=null;
        Token ID20=null;
        Token ID21=null;
        Token MINUS_MINUS22=null;
        Token MINUS_MINUS23=null;
        Token ID24=null;
        ScriptParser.expression_return expression13 = null;

        ScriptParser.expression_return expression16 = null;


        Object ID14_tree=null;
        Object EQUAL15_tree=null;
        Object ID17_tree=null;
        Object PLUS_PLUS18_tree=null;
        Object PLUS_PLUS19_tree=null;
        Object ID20_tree=null;
        Object ID21_tree=null;
        Object MINUS_MINUS22_tree=null;
        Object MINUS_MINUS23_tree=null;
        Object ID24_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:235:5: ( expression | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
            int alt5=6;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:235:7: expression
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:238:7: ID EQUAL expression
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:241:7: ID PLUS_PLUS
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

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID17!=null?ID17.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:244:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS19=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat273); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS19_tree = (Object)adaptor.create(PLUS_PLUS19);
                    adaptor.addChild(root_0, PLUS_PLUS19_tree);
                    }
                    ID20=(Token)match(input,ID,FOLLOW_ID_in_pre_stat275); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID20_tree = (Object)adaptor.create(ID20);
                    adaptor.addChild(root_0, ID20_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID20!=null?ID20.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:247:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID21=(Token)match(input,ID,FOLLOW_ID_in_pre_stat285); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID21_tree = (Object)adaptor.create(ID21);
                    adaptor.addChild(root_0, ID21_tree);
                    }
                    MINUS_MINUS22=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat287); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS22_tree = (Object)adaptor.create(MINUS_MINUS22);
                    adaptor.addChild(root_0, MINUS_MINUS22_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID21!=null?ID21.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:250:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS23=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat297); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS23_tree = (Object)adaptor.create(MINUS_MINUS23);
                    adaptor.addChild(root_0, MINUS_MINUS23_tree);
                    }
                    ID24=(Token)match(input,ID,FOLLOW_ID_in_pre_stat299); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID24_tree = (Object)adaptor.create(ID24);
                    adaptor.addChild(root_0, ID24_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID24!=null?ID24.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:256:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final ScriptParser.if_expression_return if_expression() throws RecognitionException {
        ScriptParser.if_expression_return retval = new ScriptParser.if_expression_return();
        retval.start = input.LT(1);
        int if_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_if_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:257:5: (p= pre_if_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:257:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression329);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:273:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final ScriptParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        ScriptParser.pre_if_expression_return retval = new ScriptParser.pre_if_expression_return();
        retval.start = input.LT(1);
        int pre_if_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IF25=null;
        Token LEFT_P26=null;
        Token RIGHT_P27=null;
        Token NEWLINE28=null;
        Token NEWLINE29=null;
        Token ELSE30=null;
        Token NEWLINE31=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object IF25_tree=null;
        Object LEFT_P26_tree=null;
        Object RIGHT_P27_tree=null;
        Object NEWLINE28_tree=null;
        Object NEWLINE29_tree=null;
        Object ELSE30_tree=null;
        Object NEWLINE31_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:274:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:274:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF25=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression348); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF25_tree = (Object)adaptor.create(IF25);
            adaptor.addChild(root_0, IF25_tree);
            }
            LEFT_P26=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression350); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P26_tree = (Object)adaptor.create(LEFT_P26);
            adaptor.addChild(root_0, LEFT_P26_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression354);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P27=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression356); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P27_tree = (Object)adaptor.create(RIGHT_P27);
            adaptor.addChild(root_0, RIGHT_P27_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:274:38: ( NEWLINE )?
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE28=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression358); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE28_tree = (Object)adaptor.create(NEWLINE28);
                    adaptor.addChild(root_0, NEWLINE28_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_if_expression363);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:7: ( NEWLINE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NEWLINE) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred15_Script()) ) {
                    alt7=1;
                }
            }
            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE29=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression367); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE29_tree = (Object)adaptor.create(NEWLINE29);
                    adaptor.addChild(root_0, NEWLINE29_tree);
                    }

                    }
                    break;

            }

            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==ELSE) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred17_Script()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE30=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression371); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE30_tree = (Object)adaptor.create(ELSE30);
                    adaptor.addChild(root_0, ELSE30_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:22: ( NEWLINE )?
                    int alt8=2;
                    alt8 = dfa8.predict(input);
                    switch (alt8) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE31=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression373); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE31_tree = (Object)adaptor.create(NEWLINE31);
                            adaptor.addChild(root_0, NEWLINE31_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression378);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:282:1: while_expression returns [LoopExpression expr] : p= pre_while_expression ;
    public final ScriptParser.while_expression_return while_expression() throws RecognitionException {
        ScriptParser.while_expression_return retval = new ScriptParser.while_expression_return();
        retval.start = input.LT(1);
        int while_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_while_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:283:5: (p= pre_while_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:283:7: p= pre_while_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_while_expression_in_while_expression400);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:295:1: pre_while_expression returns [LinkedList<Expression> exprs] : WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ;
    public final ScriptParser.pre_while_expression_return pre_while_expression() throws RecognitionException {
        ScriptParser.pre_while_expression_return retval = new ScriptParser.pre_while_expression_return();
        retval.start = input.LT(1);
        int pre_while_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WHILE32=null;
        Token LEFT_P33=null;
        Token RIGHT_P34=null;
        Token NEWLINE35=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object WHILE32_tree=null;
        Object LEFT_P33_tree=null;
        Object RIGHT_P34_tree=null;
        Object NEWLINE35_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:296:5: ( WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:296:7: WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            WHILE32=(Token)match(input,WHILE,FOLLOW_WHILE_in_pre_while_expression418); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE32_tree = (Object)adaptor.create(WHILE32);
            adaptor.addChild(root_0, WHILE32_tree);
            }
            LEFT_P33=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_while_expression420); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P33_tree = (Object)adaptor.create(LEFT_P33);
            adaptor.addChild(root_0, LEFT_P33_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_while_expression424);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P34=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_while_expression426); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P34_tree = (Object)adaptor.create(RIGHT_P34);
            adaptor.addChild(root_0, RIGHT_P34_tree);
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:296:41: ( NEWLINE )?
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE35=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_while_expression428); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE35_tree = (Object)adaptor.create(NEWLINE35);
                    adaptor.addChild(root_0, NEWLINE35_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_while_expression433);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:302:1: for_expression returns [LoopExpression expr] : p= pre_for_expression ;
    public final ScriptParser.for_expression_return for_expression() throws RecognitionException {
        ScriptParser.for_expression_return retval = new ScriptParser.for_expression_return();
        retval.start = input.LT(1);
        int for_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_for_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:303:5: (p= pre_for_expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:303:7: p= pre_for_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_for_expression_in_for_expression453);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:315:1: pre_for_expression returns [LinkedList<Expression> exprs] : ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat );
    public final ScriptParser.pre_for_expression_return pre_for_expression() throws RecognitionException {
        ScriptParser.pre_for_expression_return retval = new ScriptParser.pre_for_expression_return();
        retval.start = input.LT(1);
        int pre_for_expression_StartIndex = input.index();
        Object root_0 = null;

        Token FOR36=null;
        Token LEFT_P37=null;
        Token PV38=null;
        Token PV39=null;
        Token RIGHT_P40=null;
        Token NEWLINE41=null;
        Token FOR42=null;
        Token LEFT_P43=null;
        Token ID44=null;
        Token EQUAL45=null;
        Token RIGHT_P47=null;
        Token NEWLINE48=null;
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;

        ScriptParser.range_return range46 = null;


        Object FOR36_tree=null;
        Object LEFT_P37_tree=null;
        Object PV38_tree=null;
        Object PV39_tree=null;
        Object RIGHT_P40_tree=null;
        Object NEWLINE41_tree=null;
        Object FOR42_tree=null;
        Object LEFT_P43_tree=null;
        Object ID44_tree=null;
        Object EQUAL45_tree=null;
        Object RIGHT_P47_tree=null;
        Object NEWLINE48_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:5: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==FOR) ) {
                int LA13_1 = input.LA(2);

                if ( (synpred20_Script()) ) {
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR36=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression471); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR36_tree = (Object)adaptor.create(FOR36);
                    adaptor.addChild(root_0, FOR36_tree);
                    }
                    LEFT_P37=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression473); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P37_tree = (Object)adaptor.create(LEFT_P37);
                    adaptor.addChild(root_0, LEFT_P37_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression477);
                    e_init=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_init.getTree());
                    PV38=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV38_tree = (Object)adaptor.create(PV38);
                    adaptor.addChild(root_0, PV38_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_for_expression483);
                    e_cond=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_cond.getTree());
                    PV39=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression485); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV39_tree = (Object)adaptor.create(PV39);
                    adaptor.addChild(root_0, PV39_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression489);
                    e_inc=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_inc.getTree());
                    RIGHT_P40=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression491); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P40_tree = (Object)adaptor.create(RIGHT_P40);
                    adaptor.addChild(root_0, RIGHT_P40_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:81: ( NEWLINE )?
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE41=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression493); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE41_tree = (Object)adaptor.create(NEWLINE41);
                            adaptor.addChild(root_0, NEWLINE41_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression498);
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:323:7: FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR42=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression508); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR42_tree = (Object)adaptor.create(FOR42);
                    adaptor.addChild(root_0, FOR42_tree);
                    }
                    LEFT_P43=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression510); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P43_tree = (Object)adaptor.create(LEFT_P43);
                    adaptor.addChild(root_0, LEFT_P43_tree);
                    }
                    ID44=(Token)match(input,ID,FOLLOW_ID_in_pre_for_expression512); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID44_tree = (Object)adaptor.create(ID44);
                    adaptor.addChild(root_0, ID44_tree);
                    }
                    EQUAL45=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_for_expression514); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL45_tree = (Object)adaptor.create(EQUAL45);
                    adaptor.addChild(root_0, EQUAL45_tree);
                    }
                    pushFollow(FOLLOW_range_in_pre_for_expression516);
                    range46=range();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, range46.getTree());
                    RIGHT_P47=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression518); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P47_tree = (Object)adaptor.create(RIGHT_P47);
                    adaptor.addChild(root_0, RIGHT_P47_tree);
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:323:41: ( NEWLINE )?
                    int alt12=2;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE48=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression520); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE48_tree = (Object)adaptor.create(NEWLINE48);
                            adaptor.addChild(root_0, NEWLINE48_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression525);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    if ( state.backtracking==0 ) {

                              retval.exprs = new LinkedList();

                              boolean plus_minus = true;

                              Calculable init = (range46!=null?range46.range_ele:null).get(0);
                              Calculable increment = (range46!=null?range46.range_ele:null).size()==3 ? (range46!=null?range46.range_ele:null).get(1) : new Numeric(1.0f);
                              Calculable condition = (range46!=null?range46.range_ele:null).get((range46!=null?range46.range_ele:null).size()-1);

                              try {
                                  if (increment.getSimplifiedCalculable() instanceof Numeric) {
                                      double val = (Double) ((Numeric) increment.getSimplifiedCalculable()).getNativeValue();
                                      plus_minus = val>=0;
                                  }
                              } catch (Exception e) {
                                  //NOTHING
                              }

                              VariableAssignment va = new VariableAssignment(this, (ID44!=null?ID44.getText():null), init);
                              Expression init_expr = new Expression(true, this, va);
                              
                              LinkedList<Object> term_ele = new LinkedList();
                              term_ele.add(new Variable(this, (ID44!=null?ID44.getText():null)));
                              term_ele.add(Operator.OPERATOR_PLUS);
                              term_ele.add(increment);
                              Term t = new Term(this, term_ele);
                              VariableAssignment vai = new VariableAssignment(this, (ID44!=null?ID44.getText():null), t);
                              Expression increment_expr = new Expression(true, this, vai);

                              LinkedList<Object> term_elec = new LinkedList();
                              term_elec.add(new Variable(this, (ID44!=null?ID44.getText():null)));
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:366:1: range returns [LinkedList<Calculable> range_ele] : a= expression ( ARROW b= range )* ;
    public final ScriptParser.range_return range() throws RecognitionException {
        ScriptParser.range_return retval = new ScriptParser.range_return();
        retval.start = input.LT(1);
        int range_StartIndex = input.index();
        Object root_0 = null;

        Token ARROW49=null;
        ScriptParser.expression_return a = null;

        ScriptParser.range_return b = null;


        Object ARROW49_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:367:5: (a= expression ( ARROW b= range )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:367:7: a= expression ( ARROW b= range )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_range545);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.range_ele = new LinkedList();
                      retval.range_ele.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:370:7: ( ARROW b= range )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==ARROW) ) {
                    int LA14_2 = input.LA(2);

                    if ( (synpred22_Script()) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:370:8: ARROW b= range
            	    {
            	    ARROW49=(Token)match(input,ARROW,FOLLOW_ARROW_in_range550); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    ARROW49_tree = (Object)adaptor.create(ARROW49);
            	    adaptor.addChild(root_0, ARROW49_tree);
            	    }
            	    pushFollow(FOLLOW_range_in_range554);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:377:1: expression returns [Expression expr] : ( terms | function_call );
    public final ScriptParser.expression_return expression() throws RecognitionException {
        ScriptParser.expression_return retval = new ScriptParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.terms_return terms50 = null;

        ScriptParser.function_call_return function_call51 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:378:5: ( terms | function_call )
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:378:7: terms
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_terms_in_expression575);
                    terms50=terms();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, terms50.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new Term(this, (terms50!=null?terms50.terms:null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:381:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_expression585);
                    function_call51=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call51.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression( this, new FunctionCall( this, (function_call51!=null?function_call51.name_params:null) ) );
                          
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:385:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* ;
    public final ScriptParser.terms_return terms() throws RecognitionException {
        ScriptParser.terms_return retval = new ScriptParser.terms_return();
        retval.start = input.LT(1);
        int terms_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS52=null;
        Token MINUS53=null;
        Token AND54=null;
        Token OR55=null;
        ScriptParser.term_return t = null;


        Object PLUS52_tree=null;
        Object MINUS53_tree=null;
        Object AND54_tree=null;
        Object OR55_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:386:5: (t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:386:7: t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms605);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:389:7: ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:389:9: PLUS t= term
            	    {
            	    PLUS52=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms611); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS52_tree = (Object)adaptor.create(PLUS52);
            	    adaptor.addChild(root_0, PLUS52_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms615);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:393:9: MINUS t= term
            	    {
            	    MINUS53=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms627); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS53_tree = (Object)adaptor.create(MINUS53);
            	    adaptor.addChild(root_0, MINUS53_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms631);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:397:9: AND t= term
            	    {
            	    AND54=(Token)match(input,AND,FOLLOW_AND_in_terms643); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND54_tree = (Object)adaptor.create(AND54);
            	    adaptor.addChild(root_0, AND54_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms647);
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
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:401:9: OR t= term
            	    {
            	    OR55=(Token)match(input,OR,FOLLOW_OR_in_terms659); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR55_tree = (Object)adaptor.create(OR55);
            	    adaptor.addChild(root_0, OR55_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms663);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:407:1: term returns [LinkedList<Object> atoms] : (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.term_return term() throws RecognitionException {
        ScriptParser.term_return retval = new ScriptParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token MULT56=null;
        Token DIV57=null;
        Token CMP_LT58=null;
        Token CMP_LT_EQ59=null;
        Token CMP_GT60=null;
        Token CMP_GT_EQ61=null;
        Token CMP_EQ62=null;
        Token CMP_NEQ63=null;
        Token ID64=null;
        Token PLUS_PLUS65=null;
        Token PLUS_PLUS66=null;
        Token ID67=null;
        Token ID68=null;
        Token MINUS_MINUS69=null;
        Token MINUS_MINUS70=null;
        Token ID71=null;
        ScriptParser.atom_return a = null;


        Object MULT56_tree=null;
        Object DIV57_tree=null;
        Object CMP_LT58_tree=null;
        Object CMP_LT_EQ59_tree=null;
        Object CMP_GT60_tree=null;
        Object CMP_GT_EQ61_tree=null;
        Object CMP_EQ62_tree=null;
        Object CMP_NEQ63_tree=null;
        Object ID64_tree=null;
        Object PLUS_PLUS65_tree=null;
        Object PLUS_PLUS66_tree=null;
        Object ID67_tree=null;
        Object ID68_tree=null;
        Object MINUS_MINUS69_tree=null;
        Object MINUS_MINUS70_tree=null;
        Object ID71_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:408:5: (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:408:7: a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term687);
                    a=atom();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              retval.atoms.add((a!=null?a.value:null));
                          
                    }
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:411:7: ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:411:9: MULT a= atom
                    	    {
                    	    MULT56=(Token)match(input,MULT,FOLLOW_MULT_in_term693); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    MULT56_tree = (Object)adaptor.create(MULT56);
                    	    adaptor.addChild(root_0, MULT56_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term697);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:415:9: DIV a= atom
                    	    {
                    	    DIV57=(Token)match(input,DIV,FOLLOW_DIV_in_term709); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DIV57_tree = (Object)adaptor.create(DIV57);
                    	    adaptor.addChild(root_0, DIV57_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term713);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:419:9: CMP_LT a= atom
                    	    {
                    	    CMP_LT58=(Token)match(input,CMP_LT,FOLLOW_CMP_LT_in_term725); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT58_tree = (Object)adaptor.create(CMP_LT58);
                    	    adaptor.addChild(root_0, CMP_LT58_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term729);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:423:9: CMP_LT_EQ a= atom
                    	    {
                    	    CMP_LT_EQ59=(Token)match(input,CMP_LT_EQ,FOLLOW_CMP_LT_EQ_in_term742); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT_EQ59_tree = (Object)adaptor.create(CMP_LT_EQ59);
                    	    adaptor.addChild(root_0, CMP_LT_EQ59_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term746);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:427:9: CMP_GT a= atom
                    	    {
                    	    CMP_GT60=(Token)match(input,CMP_GT,FOLLOW_CMP_GT_in_term759); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT60_tree = (Object)adaptor.create(CMP_GT60);
                    	    adaptor.addChild(root_0, CMP_GT60_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term763);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:431:9: CMP_GT_EQ a= atom
                    	    {
                    	    CMP_GT_EQ61=(Token)match(input,CMP_GT_EQ,FOLLOW_CMP_GT_EQ_in_term775); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT_EQ61_tree = (Object)adaptor.create(CMP_GT_EQ61);
                    	    adaptor.addChild(root_0, CMP_GT_EQ61_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term779);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:435:9: CMP_EQ a= atom
                    	    {
                    	    CMP_EQ62=(Token)match(input,CMP_EQ,FOLLOW_CMP_EQ_in_term792); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_EQ62_tree = (Object)adaptor.create(CMP_EQ62);
                    	    adaptor.addChild(root_0, CMP_EQ62_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term796);
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
                    	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:439:9: CMP_NEQ a= atom
                    	    {
                    	    CMP_NEQ63=(Token)match(input,CMP_NEQ,FOLLOW_CMP_NEQ_in_term808); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_NEQ63_tree = (Object)adaptor.create(CMP_NEQ63);
                    	    adaptor.addChild(root_0, CMP_NEQ63_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term812);
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:444:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID64=(Token)match(input,ID,FOLLOW_ID_in_term829); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID64_tree = (Object)adaptor.create(ID64);
                    adaptor.addChild(root_0, ID64_tree);
                    }
                    PLUS_PLUS65=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term831); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS65_tree = (Object)adaptor.create(PLUS_PLUS65);
                    adaptor.addChild(root_0, PLUS_PLUS65_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID64!=null?ID64.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:449:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS66=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term841); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS66_tree = (Object)adaptor.create(PLUS_PLUS66);
                    adaptor.addChild(root_0, PLUS_PLUS66_tree);
                    }
                    ID67=(Token)match(input,ID,FOLLOW_ID_in_term843); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID67_tree = (Object)adaptor.create(ID67);
                    adaptor.addChild(root_0, ID67_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID67!=null?ID67.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:454:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID68=(Token)match(input,ID,FOLLOW_ID_in_term853); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID68_tree = (Object)adaptor.create(ID68);
                    adaptor.addChild(root_0, ID68_tree);
                    }
                    MINUS_MINUS69=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term855); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS69_tree = (Object)adaptor.create(MINUS_MINUS69);
                    adaptor.addChild(root_0, MINUS_MINUS69_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID68!=null?ID68.getText():null), Operator.OPERATOR_MINUS_MINUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:459:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS70=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term865); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS70_tree = (Object)adaptor.create(MINUS_MINUS70);
                    adaptor.addChild(root_0, MINUS_MINUS70_tree);
                    }
                    ID71=(Token)match(input,ID,FOLLOW_ID_in_term867); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID71_tree = (Object)adaptor.create(ID71);
                    adaptor.addChild(root_0, ID71_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID71!=null?ID71.getText():null), Operator.OPERATOR_MINUS_MINUS);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:466:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P args RIGHT_P ;
    public final ScriptParser.function_call_return function_call() throws RecognitionException {
        ScriptParser.function_call_return retval = new ScriptParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token ID72=null;
        Token LEFT_P73=null;
        Token RIGHT_P75=null;
        ScriptParser.args_return args74 = null;


        Object ID72_tree=null;
        Object LEFT_P73_tree=null;
        Object RIGHT_P75_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:466:55: ( ID LEFT_P args RIGHT_P )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:467:5: ID LEFT_P args RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID72=(Token)match(input,ID,FOLLOW_ID_in_function_call885); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID72_tree = (Object)adaptor.create(ID72);
            adaptor.addChild(root_0, ID72_tree);
            }
            LEFT_P73=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call887); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P73_tree = (Object)adaptor.create(LEFT_P73);
            adaptor.addChild(root_0, LEFT_P73_tree);
            }
            pushFollow(FOLLOW_args_in_function_call889);
            args74=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, args74.getTree());
            RIGHT_P75=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call891); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P75_tree = (Object)adaptor.create(RIGHT_P75);
            adaptor.addChild(root_0, RIGHT_P75_tree);
            }
            if ( state.backtracking==0 ) {
                
                      retval.name_params = (args74!=null?args74.params:null);
                      retval.name_params.add(0, (ID72!=null?ID72.getText():null));
                  
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:473:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final ScriptParser.args_return args() throws RecognitionException {
        ScriptParser.args_return retval = new ScriptParser.args_return();
        retval.start = input.LT(1);
        int args_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA76=null;
        ScriptParser.expression_return a = null;

        ScriptParser.args_return b = null;


        Object COMMA76_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:473:41: (a= expression ( COMMA b= args )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:474:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args911);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:477:7: ( COMMA b= args )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==COMMA) ) {
                    int LA19_2 = input.LA(2);

                    if ( (synpred40_Script()) ) {
                        alt19=1;
                    }


                }


                switch (alt19) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:477:8: COMMA b= args
            	    {
            	    COMMA76=(Token)match(input,COMMA,FOLLOW_COMMA_in_args916); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA76_tree = (Object)adaptor.create(COMMA76);
            	    adaptor.addChild(root_0, COMMA76_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args920);
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:486:1: atom returns [Object value] : ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary );
    public final ScriptParser.atom_return atom() throws RecognitionException {
        ScriptParser.atom_return retval = new ScriptParser.atom_return();
        retval.start = input.LT(1);
        int atom_StartIndex = input.index();
        Object root_0 = null;

        Token NUM77=null;
        Token MINUS78=null;
        Token NUM79=null;
        Token BOOL80=null;
        Token LEFT_P81=null;
        Token RIGHT_P83=null;
        Token ID84=null;
        ScriptParser.expression_return expression82 = null;

        ScriptParser.string_literal_return string_literal85 = null;

        ScriptParser.dictionary_return dictionary86 = null;


        Object NUM77_tree=null;
        Object MINUS78_tree=null;
        Object NUM79_tree=null;
        Object BOOL80_tree=null;
        Object LEFT_P81_tree=null;
        Object RIGHT_P83_tree=null;
        Object ID84_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:487:5: ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | string_literal | dictionary )
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
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:487:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM77=(Token)match(input,NUM,FOLLOW_NUM_in_atom943); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM77_tree = (Object)adaptor.create(NUM77);
                    adaptor.addChild(root_0, NUM77_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Float.parseFloat((NUM77!=null?NUM77.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:490:7: MINUS NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS78=(Token)match(input,MINUS,FOLLOW_MINUS_in_atom953); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS78_tree = (Object)adaptor.create(MINUS78);
                    adaptor.addChild(root_0, MINUS78_tree);
                    }
                    NUM79=(Token)match(input,NUM,FOLLOW_NUM_in_atom955); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM79_tree = (Object)adaptor.create(NUM79);
                    adaptor.addChild(root_0, NUM79_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( -1.0 * Float.parseFloat((NUM79!=null?NUM79.getText():null)) );
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:493:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL80=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom965); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL80_tree = (Object)adaptor.create(BOOL80);
                    adaptor.addChild(root_0, BOOL80_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL80!=null?BOOL80.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL80!=null?BOOL80.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              }
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:500:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P81=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom975); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P81_tree = (Object)adaptor.create(LEFT_P81);
                    adaptor.addChild(root_0, LEFT_P81_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom977);
                    expression82=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression82.getTree());
                    RIGHT_P83=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom979); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P83_tree = (Object)adaptor.create(RIGHT_P83);
                    adaptor.addChild(root_0, RIGHT_P83_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression82!=null?expression82.expr:null);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:503:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID84=(Token)match(input,ID,FOLLOW_ID_in_atom989); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID84_tree = (Object)adaptor.create(ID84);
                    adaptor.addChild(root_0, ID84_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID84!=null?ID84.getText():null));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:506:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom999);
                    string_literal85=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal85.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal85!=null?string_literal85.value:null);
                          
                    }

                    }
                    break;
                case 7 :
                    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:509:7: dictionary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dictionary_in_atom1009);
                    dictionary86=dictionary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary86.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (dictionary86!=null?dictionary86.value:null);
                          
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:521:1: string_literal returns [CharString value] : s= STRING_LITERAL ;
    public final ScriptParser.string_literal_return string_literal() throws RecognitionException {
        ScriptParser.string_literal_return retval = new ScriptParser.string_literal_return();
        retval.start = input.LT(1);
        int string_literal_StartIndex = input.index();
        Object root_0 = null;

        Token s=null;

        Object s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:521:43: (s= STRING_LITERAL )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:521:45: s= STRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_string_literal1037); if (state.failed) return retval;
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:526:1: dictionary returns [Dictionary value] : LEFT_CB dictionary_elements RIGHT_CB ;
    public final ScriptParser.dictionary_return dictionary() throws RecognitionException {
        ScriptParser.dictionary_return retval = new ScriptParser.dictionary_return();
        retval.start = input.LT(1);
        int dictionary_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB87=null;
        Token RIGHT_CB89=null;
        ScriptParser.dictionary_elements_return dictionary_elements88 = null;


        Object LEFT_CB87_tree=null;
        Object RIGHT_CB89_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:526:39: ( LEFT_CB dictionary_elements RIGHT_CB )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:527:5: LEFT_CB dictionary_elements RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB87=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_dictionary1056); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB87_tree = (Object)adaptor.create(LEFT_CB87);
            adaptor.addChild(root_0, LEFT_CB87_tree);
            }
            pushFollow(FOLLOW_dictionary_elements_in_dictionary1058);
            dictionary_elements88=dictionary_elements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary_elements88.getTree());
            RIGHT_CB89=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_dictionary1060); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB89_tree = (Object)adaptor.create(RIGHT_CB89);
            adaptor.addChild(root_0, RIGHT_CB89_tree);
            }
            if ( state.backtracking==0 ) {

                      HashMap vs = new HashMap();
                      int size = (dictionary_elements88!=null?dictionary_elements88.keys_values:null).size();
                      for (int k=0; k<size; k+=2) {
                          vs.put((dictionary_elements88!=null?dictionary_elements88.keys_values:null).get(k), (dictionary_elements88!=null?dictionary_elements88.keys_values:null).get(k+1));
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
    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:536:1: dictionary_elements returns [LinkedList<Object> keys_values] : (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* ;
    public final ScriptParser.dictionary_elements_return dictionary_elements() throws RecognitionException {
        ScriptParser.dictionary_elements_return retval = new ScriptParser.dictionary_elements_return();
        retval.start = input.LT(1);
        int dictionary_elements_StartIndex = input.index();
        Object root_0 = null;

        Token TP90=null;
        Token NEWLINE91=null;
        Token COMMA92=null;
        Token NEWLINE93=null;
        ScriptParser.expression_return e1 = null;

        ScriptParser.expression_return e2 = null;

        ScriptParser.dictionary_elements_return d = null;


        Object TP90_tree=null;
        Object NEWLINE91_tree=null;
        Object COMMA92_tree=null;
        Object NEWLINE93_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:536:62: ( (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:537:5: (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:537:5: (e1= expression TP e2= expression )
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:537:6: e1= expression TP e2= expression
            {
            pushFollow(FOLLOW_expression_in_dictionary_elements1081);
            e1=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
            TP90=(Token)match(input,TP,FOLLOW_TP_in_dictionary_elements1083); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TP90_tree = (Object)adaptor.create(TP90);
            adaptor.addChild(root_0, TP90_tree);
            }
            pushFollow(FOLLOW_expression_in_dictionary_elements1087);
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
            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:7: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==NEWLINE) ) {
                    int LA23_2 = input.LA(2);

                    if ( (synpred49_Script()) ) {
                        alt23=1;
                    }


                }
                else if ( (LA23_0==COMMA) ) {
                    int LA23_3 = input.LA(2);

                    if ( (synpred49_Script()) ) {
                        alt23=1;
                    }


                }


                switch (alt23) {
            	case 1 :
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
            	    {
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:8: ( NEWLINE )?
            	    int alt21=2;
            	    int LA21_0 = input.LA(1);

            	    if ( (LA21_0==NEWLINE) ) {
            	        alt21=1;
            	    }
            	    switch (alt21) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE91=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1093); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE91_tree = (Object)adaptor.create(NEWLINE91);
            	            adaptor.addChild(root_0, NEWLINE91_tree);
            	            }

            	            }
            	            break;

            	    }

            	    COMMA92=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictionary_elements1096); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA92_tree = (Object)adaptor.create(COMMA92);
            	    adaptor.addChild(root_0, COMMA92_tree);
            	    }
            	    // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:23: ( NEWLINE )?
            	    int alt22=2;
            	    int LA22_0 = input.LA(1);

            	    if ( (LA22_0==NEWLINE) ) {
            	        alt22=1;
            	    }
            	    switch (alt22) {
            	        case 1 :
            	            // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE93=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1098); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE93_tree = (Object)adaptor.create(NEWLINE93);
            	            adaptor.addChild(root_0, NEWLINE93_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_dictionary_elements_in_dictionary_elements1103);
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
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:197:13: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:197:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Script109); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Script

    // $ANTLR start synpred3_Script
    public final void synpred3_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:207:8: (s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:207:8: s= stat
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
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:215:7: ( pre_stat NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:215:7: pre_stat NEWLINE
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
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:222:7: ( block )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:222:7: block
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
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:235:7: ( expression )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:235:7: expression
        {
        pushFollow(FOLLOW_expression_in_synpred9_Script237);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Script

    // $ANTLR start synpred11_Script
    public final void synpred11_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:241:7: ( ID PLUS_PLUS )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:241:7: ID PLUS_PLUS
        {
        match(input,ID,FOLLOW_ID_in_synpred11_Script261); if (state.failed) return ;
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred11_Script263); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Script

    // $ANTLR start synpred12_Script
    public final void synpred12_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:244:7: ( PLUS_PLUS ID )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:244:7: PLUS_PLUS ID
        {
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred12_Script273); if (state.failed) return ;
        match(input,ID,FOLLOW_ID_in_synpred12_Script275); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_Script

    // $ANTLR start synpred13_Script
    public final void synpred13_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:247:7: ( ID MINUS_MINUS )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:247:7: ID MINUS_MINUS
        {
        match(input,ID,FOLLOW_ID_in_synpred13_Script285); if (state.failed) return ;
        match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_synpred13_Script287); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Script

    // $ANTLR start synpred14_Script
    public final void synpred14_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:274:38: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:274:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred14_Script358); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Script

    // $ANTLR start synpred15_Script
    public final void synpred15_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:7: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred15_Script367); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Script

    // $ANTLR start synpred16_Script
    public final void synpred16_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:22: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred16_Script373); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Script

    // $ANTLR start synpred17_Script
    public final void synpred17_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:17: ( ELSE ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred17_Script371); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:278:22: ( NEWLINE )?
        int alt24=2;
        int LA24_0 = input.LA(1);

        if ( (LA24_0==NEWLINE) ) {
            int LA24_1 = input.LA(2);

            if ( ((LA24_1>=LEFT_CB && LA24_1<=NEWLINE)||LA24_1==ID||(LA24_1>=PLUS_PLUS && LA24_1<=LEFT_P)||(LA24_1>=WHILE && LA24_1<=FOR)||LA24_1==MINUS||(LA24_1>=NUM && LA24_1<=STRING_LITERAL)) ) {
                alt24=1;
            }
        }
        switch (alt24) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred17_Script373); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred17_Script378);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Script

    // $ANTLR start synpred18_Script
    public final void synpred18_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:296:41: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:296:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred18_Script428); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_Script

    // $ANTLR start synpred19_Script
    public final void synpred19_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:81: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:81: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred19_Script493); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Script

    // $ANTLR start synpred20_Script
    public final void synpred20_Script_fragment() throws RecognitionException {   
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:7: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
        {
        match(input,FOR,FOLLOW_FOR_in_synpred20_Script471); if (state.failed) return ;
        match(input,LEFT_P,FOLLOW_LEFT_P_in_synpred20_Script473); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred20_Script477);
        e_init=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred20_Script479); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred20_Script483);
        e_cond=expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred20_Script485); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred20_Script489);
        e_inc=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,RIGHT_P,FOLLOW_RIGHT_P_in_synpred20_Script491); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:316:81: ( NEWLINE )?
        int alt25=2;
        int LA25_0 = input.LA(1);

        if ( (LA25_0==NEWLINE) ) {
            int LA25_1 = input.LA(2);

            if ( ((LA25_1>=LEFT_CB && LA25_1<=NEWLINE)||LA25_1==ID||(LA25_1>=PLUS_PLUS && LA25_1<=LEFT_P)||(LA25_1>=WHILE && LA25_1<=FOR)||LA25_1==MINUS||(LA25_1>=NUM && LA25_1<=STRING_LITERAL)) ) {
                alt25=1;
            }
        }
        switch (alt25) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred20_Script493); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred20_Script498);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_Script

    // $ANTLR start synpred21_Script
    public final void synpred21_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:323:41: ( NEWLINE )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:323:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred21_Script520); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_Script

    // $ANTLR start synpred22_Script
    public final void synpred22_Script_fragment() throws RecognitionException {   
        ScriptParser.range_return b = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:370:8: ( ARROW b= range )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:370:8: ARROW b= range
        {
        match(input,ARROW,FOLLOW_ARROW_in_synpred22_Script550); if (state.failed) return ;
        pushFollow(FOLLOW_range_in_synpred22_Script554);
        b=range();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_Script

    // $ANTLR start synpred40_Script
    public final void synpred40_Script_fragment() throws RecognitionException {   
        ScriptParser.args_return b = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:477:8: ( COMMA b= args )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:477:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred40_Script916); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred40_Script920);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred40_Script

    // $ANTLR start synpred49_Script
    public final void synpred49_Script_fragment() throws RecognitionException {   
        ScriptParser.dictionary_elements_return d = null;


        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:8: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
        {
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:8: ( NEWLINE )?
        int alt27=2;
        int LA27_0 = input.LA(1);

        if ( (LA27_0==NEWLINE) ) {
            alt27=1;
        }
        switch (alt27) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred49_Script1093); if (state.failed) return ;

                }
                break;

        }

        match(input,COMMA,FOLLOW_COMMA_in_synpred49_Script1096); if (state.failed) return ;
        // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:541:23: ( NEWLINE )?
        int alt28=2;
        int LA28_0 = input.LA(1);

        if ( (LA28_0==NEWLINE) ) {
            alt28=1;
        }
        switch (alt28) {
            case 1 :
                // /home/laurent/dev/WPAScript/working_copy/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred49_Script1098); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_dictionary_elements_in_synpred49_Script1103);
        d=dictionary_elements();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred49_Script

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
    public final boolean synpred11_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_Script_fragment(); // can never throw exception
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
    public final boolean synpred40_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred40_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred49_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred49_Script_fragment(); // can never throw exception
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
        "\17\uffff";
    static final String DFA1_eofS =
        "\17\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\15\uffff";
    static final String DFA1_maxS =
        "\1\42\1\0\15\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\13\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\15\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "197:13: ( NEWLINE )?";
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
                        if ( (synpred1_Script()) ) {s = 14;}

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
        "\17\uffff";
    static final String DFA4_eofS =
        "\17\uffff";
    static final String DFA4_minS =
        "\1\4\6\uffff\1\0\7\uffff";
    static final String DFA4_maxS =
        "\1\42\6\uffff\1\0\7\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\10\uffff\1\2\1\4\1\5\1\6\1\3";
    static final String DFA4_specialS =
        "\7\uffff\1\0\7\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\7\1\12\1\uffff\1\1\1\uffff\2\1\1\13\1\1\2\uffff\1\14\1\15"+
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
            return "214:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );";
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

                        else if ( (synpred6_Script()) ) {s = 14;}

                         
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
        "\16\uffff";
    static final String DFA5_eofS =
        "\16\uffff";
    static final String DFA5_minS =
        "\1\4\1\uffff\1\5\2\7\2\0\1\uffff\2\0\4\uffff";
    static final String DFA5_maxS =
        "\1\42\1\uffff\1\36\2\7\2\0\1\uffff\2\0\4\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\5\uffff\1\2\2\uffff\1\3\1\5\1\4\1\6";
    static final String DFA5_specialS =
        "\5\uffff\1\0\1\1\1\uffff\1\3\1\2\4\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1\2\uffff\1\2\1\uffff\1\3\1\4\1\uffff\1\1\7\uffff\1\1\13"+
            "\uffff\3\1",
            "",
            "\1\1\2\uffff\1\7\1\5\1\6\1\uffff\2\1\3\uffff\1\1\1\uffff\14"+
            "\1",
            "\1\10",
            "\1\11",
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
            return "234:1: pre_stat returns [Expression expr] : ( expression | ID EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_5 = input.LA(1);

                         
                        int index5_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred11_Script()) ) {s = 10;}

                         
                        input.seek(index5_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_6 = input.LA(1);

                         
                        int index5_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred13_Script()) ) {s = 11;}

                         
                        input.seek(index5_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_9 = input.LA(1);

                         
                        int index5_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index5_9);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_8 = input.LA(1);

                         
                        int index5_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred12_Script()) ) {s = 12;}

                         
                        input.seek(index5_8);
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
        "\17\uffff";
    static final String DFA6_eofS =
        "\17\uffff";
    static final String DFA6_minS =
        "\1\4\1\0\15\uffff";
    static final String DFA6_maxS =
        "\1\42\1\0\15\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\13\uffff\1\1";
    static final String DFA6_specialS =
        "\1\uffff\1\0\15\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "274:38: ( NEWLINE )?";
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
                        if ( (synpred14_Script()) ) {s = 14;}

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
        "\17\uffff";
    static final String DFA8_eofS =
        "\17\uffff";
    static final String DFA8_minS =
        "\1\4\1\0\15\uffff";
    static final String DFA8_maxS =
        "\1\42\1\0\15\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\13\uffff\1\1";
    static final String DFA8_specialS =
        "\1\uffff\1\0\15\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "278:22: ( NEWLINE )?";
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
                        if ( (synpred16_Script()) ) {s = 14;}

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
        "\17\uffff";
    static final String DFA10_eofS =
        "\17\uffff";
    static final String DFA10_minS =
        "\1\4\1\0\15\uffff";
    static final String DFA10_maxS =
        "\1\42\1\0\15\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\13\uffff\1\1";
    static final String DFA10_specialS =
        "\1\uffff\1\0\15\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "296:41: ( NEWLINE )?";
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
                        if ( (synpred18_Script()) ) {s = 14;}

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
        "\17\uffff";
    static final String DFA11_eofS =
        "\17\uffff";
    static final String DFA11_minS =
        "\1\4\1\0\15\uffff";
    static final String DFA11_maxS =
        "\1\42\1\0\15\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\13\uffff\1\1";
    static final String DFA11_specialS =
        "\1\uffff\1\0\15\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "316:81: ( NEWLINE )?";
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
                        if ( (synpred19_Script()) ) {s = 14;}

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
        "\17\uffff";
    static final String DFA12_eofS =
        "\17\uffff";
    static final String DFA12_minS =
        "\1\4\1\0\15\uffff";
    static final String DFA12_maxS =
        "\1\42\1\0\15\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\13\uffff\1\1";
    static final String DFA12_specialS =
        "\1\uffff\1\0\15\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\1\1\uffff\1\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
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
            return "323:41: ( NEWLINE )?";
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
                        if ( (synpred21_Script()) ) {s = 14;}

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
    public static final BitSet FOLLOW_LEFT_CB_in_block107 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_block109 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stats_in_block112 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block114 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats136 = new BitSet(new long[]{0x0000000700119EB2L});
    public static final BitSet FOLLOW_stat_in_stats143 = new BitSet(new long[]{0x0000000700119EB2L});
    public static final BitSet FOLLOW_pre_stat_in_stat168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_expression_in_stat210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_expression_in_stat220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_pre_stat237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat247 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat249 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_expression_in_pre_stat251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat261 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat273 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_pre_stat275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat285 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat297 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_pre_stat299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression348 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression350 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression354 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression356 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression358 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression363 = new BitSet(new long[]{0x0000000000004022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression367 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression371 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression373 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_while_expression_in_while_expression400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_pre_while_expression418 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_while_expression420 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_expression_in_pre_while_expression424 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_while_expression426 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_while_expression428 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stat_in_pre_while_expression433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_for_expression_in_for_expression453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression471 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression473 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression477 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression479 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_expression_in_pre_for_expression483 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression485 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression489 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression491 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression493 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression508 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression510 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_pre_for_expression512 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_EQUAL_in_pre_for_expression514 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_range_in_pre_for_expression516 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression518 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression520 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_range545 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_ARROW_in_range550 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_range_in_range554 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_terms_in_expression575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms605 = new BitSet(new long[]{0x0000000000780002L});
    public static final BitSet FOLLOW_PLUS_in_terms611 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_term_in_terms615 = new BitSet(new long[]{0x0000000000780002L});
    public static final BitSet FOLLOW_MINUS_in_terms627 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_term_in_terms631 = new BitSet(new long[]{0x0000000000780002L});
    public static final BitSet FOLLOW_AND_in_terms643 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_term_in_terms647 = new BitSet(new long[]{0x0000000000780002L});
    public static final BitSet FOLLOW_OR_in_terms659 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_term_in_terms663 = new BitSet(new long[]{0x0000000000780002L});
    public static final BitSet FOLLOW_atom_in_term687 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_MULT_in_term693 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term697 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_DIV_in_term709 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term713 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_CMP_LT_in_term725 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term729 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_CMP_LT_EQ_in_term742 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term746 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_CMP_GT_in_term759 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term763 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_CMP_GT_EQ_in_term775 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term779 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_CMP_EQ_in_term792 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term796 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_CMP_NEQ_in_term808 = new BitSet(new long[]{0x0000000700101090L});
    public static final BitSet FOLLOW_atom_in_term812 = new BitSet(new long[]{0x000000007F800002L});
    public static final BitSet FOLLOW_ID_in_term829 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term841 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_term843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term853 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term865 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_term867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_function_call885 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call887 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_args_in_function_call889 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args911 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_COMMA_in_args916 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_args_in_args920 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_NUM_in_atom943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_atom953 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_NUM_in_atom955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom975 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_expression_in_atom977 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dictionary_in_atom1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal1037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_dictionary1056 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary1058 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_dictionary1060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1081 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_TP_in_dictionary_elements1083 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1087 = new BitSet(new long[]{0x0000000080000022L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1093 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_COMMA_in_dictionary_elements1096 = new BitSet(new long[]{0x00000007001016B0L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1098 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary_elements1103 = new BitSet(new long[]{0x0000000080000022L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Script109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Script143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_stat_in_synpred4_Script168 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred4_Script170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_synpred6_Script190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred9_Script237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred11_Script261 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred11_Script263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred12_Script273 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_synpred12_Script275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred13_Script285 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_synpred13_Script287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred14_Script358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred15_Script367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred16_Script373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred17_Script371 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred17_Script373 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stat_in_synpred17_Script378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred18_Script428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred19_Script493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_synpred20_Script471 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFT_P_in_synpred20_Script473 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_pre_stat_in_synpred20_Script477 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_PV_in_synpred20_Script479 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_expression_in_synpred20_Script483 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_PV_in_synpred20_Script485 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_pre_stat_in_synpred20_Script489 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RIGHT_P_in_synpred20_Script491 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred20_Script493 = new BitSet(new long[]{0x0000000700119EB0L});
    public static final BitSet FOLLOW_stat_in_synpred20_Script498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred21_Script520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARROW_in_synpred22_Script550 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_range_in_synpred22_Script554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred40_Script916 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_args_in_synpred40_Script920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred49_Script1093 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred49_Script1096 = new BitSet(new long[]{0x00000007001016B0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred49_Script1098 = new BitSet(new long[]{0x0000000700101690L});
    public static final BitSet FOLLOW_dictionary_elements_in_synpred49_Script1103 = new BitSet(new long[]{0x0000000000000002L});

}