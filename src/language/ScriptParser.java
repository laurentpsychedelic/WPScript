// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g 2011-10-31 19:20:46

package language;

import java.io.PrintStream;
import java.util.LinkedList;
import language.memory.*;
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "BREAK", "CONTINUE", "ID", "EQUAL", "PLUS_PLUS", "MINUS_MINUS", "IF", "LEFT_P", "RIGHT_P", "ELSE", "WHILE", "FOR", "PV", "ARROW", "PLUS", "MINUS", "AND", "OR", "MULT", "DIV", "CMP_LT", "CMP_LT_EQ", "CMP_GT", "CMP_GT_EQ", "CMP_EQ", "CMP_NEQ", "COMMA", "LEFT_B", "RIGHT_B", "NUM", "BOOL", "CONSTANT", "STRING_LITERAL", "TP", "DQUOTE", "LINE_COMMENT", "BLOCK_COMMENT", "WS"
    };
    public static final int WHILE=17;
    public static final int FOR=18;
    public static final int DQUOTE=41;
    public static final int CMP_EQ=31;
    public static final int ID=9;
    public static final int AND=23;
    public static final int EOF=-1;
    public static final int LEFT_CB=4;
    public static final int BREAK=7;
    public static final int IF=13;
    public static final int RIGHT_CB=6;
    public static final int CMP_GT_EQ=30;
    public static final int CMP_LT=27;
    public static final int RIGHT_B=35;
    public static final int STRING_LITERAL=39;
    public static final int PLUS_PLUS=11;
    public static final int CONTINUE=8;
    public static final int COMMA=33;
    public static final int CMP_LT_EQ=28;
    public static final int EQUAL=10;
    public static final int LEFT_B=34;
    public static final int BLOCK_COMMENT=43;
    public static final int PV=19;
    public static final int PLUS=21;
    public static final int LEFT_P=14;
    public static final int CMP_NEQ=32;
    public static final int LINE_COMMENT=42;
    public static final int ELSE=16;
    public static final int BOOL=37;
    public static final int TP=40;
    public static final int RIGHT_P=15;
    public static final int MINUS=22;
    public static final int MULT=25;
    public static final int CMP_GT=29;
    public static final int NUM=36;
    public static final int WS=44;
    public static final int NEWLINE=5;
    public static final int OR=24;
    public static final int CONSTANT=38;
    public static final int ARROW=20;
    public static final int DIV=26;
    public static final int MINUS_MINUS=12;

    // delegates
    // delegators


        public ScriptParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ScriptParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[81+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ScriptParser.tokenNames; }
    public String getGrammarFileName() { return "/home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g"; }


        
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
        public Environment env = new Environment(null);
        public static final Environment env_const = new Environment(null);
        static {
            env_const.addConstants();
        }
        public Environment compilation_env =  new Environment(null);
        private LinkedList <Expression> commands = new LinkedList();
        private int line_number = 1;
        public int getLineNumber() {
            return line_number;
        }

        private boolean _PANIC_STATE_ = false;
        private boolean _COMPILATION_ERROR_STATE_ = false;
        private boolean _RUNTIME_ERROR_STATE_ = false;

        public void dumpGlobalMemory(PrintStream ps) {
            ps.println("\nGLOBAL env DUMP");
            for (Object o : env.getEntries()) {
                Object val = env.getValue(o.toString());
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
                compilation_env.clear();
                System.out.println("\nCOMPILATION CHECK");
                for (Object o : commands) {
                    if (!(o instanceof Expression)) {
                        scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                    }
                    ((Expression) o).compilationCheck();
                }
                compilation_env.clear();
                System.out.println("\nCOMPILATION OK");
                return true;
            } catch (CompilationErrorException e) {
                compilationError(e.getMessage(), e.getLineNumber());
                return false;
            }
        }

        public Object execute() throws PanicException, RuntimeErrorException {
            env.clear();
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:190:1: prog : s= stats ;
    public final ScriptParser.prog_return prog() throws RecognitionException {
        ScriptParser.prog_return retval = new ScriptParser.prog_return();
        retval.start = input.LT(1);
        int prog_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stats_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:190:6: (s= stats )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:191:5: s= stats
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_stats_in_prog81);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:198:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:198:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:199:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block98); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:199:13: ( NEWLINE )?
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE2=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_block100); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE2_tree = (Object)adaptor.create(NEWLINE2);
                    adaptor.addChild(root_0, NEWLINE2_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stats_in_block103);
            stats3=stats();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, stats3.getTree());
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:199:28: ( NEWLINE )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NEWLINE) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE4=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_block105); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE4_tree = (Object)adaptor.create(NEWLINE4);
                    adaptor.addChild(root_0, NEWLINE4_tree);
                    }

                    }
                    break;

            }

            RIGHT_CB5=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_block108); if (state.failed) return retval;
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:203:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final ScriptParser.stats_return stats() throws RecognitionException {
        ScriptParser.stats_return retval = new ScriptParser.stats_return();
        retval.start = input.LT(1);
        int stats_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stat_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:203:51: (s= stat (s= stat )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:204:5: s= stat (s= stat )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_stat_in_stats127);
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:209:7: (s= stat )*
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
                else if ( (LA3_0==LEFT_CB||(LA3_0>=BREAK && LA3_0<=ID)||(LA3_0>=PLUS_PLUS && LA3_0<=LEFT_P)||(LA3_0>=WHILE && LA3_0<=FOR)||LA3_0==MINUS||LA3_0==LEFT_B||(LA3_0>=NUM && LA3_0<=STRING_LITERAL)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:209:8: s= stat
            	    {
            	    pushFollow(FOLLOW_stat_in_stats134);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:216:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:217:5: ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression )
            int alt4=6;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:217:7: pre_stat NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pre_stat_in_stat159);
                    pre_stat6=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pre_stat6.getTree());
                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat161); if (state.failed) return retval;
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:221:7: NEWLINE
                    {
                    root_0 = (Object)adaptor.nil();

                    NEWLINE8=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat171); if (state.failed) return retval;
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:224:7: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_stat181);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:227:7: if_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_if_expression_in_stat191);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:230:7: while_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_while_expression_in_stat201);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:233:7: for_expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_for_expression_in_stat211);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:236:1: pre_stat returns [Expression expr] : ( expression | BREAK | CONTINUE | ID EQUAL expression | array_element_reference EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.pre_stat_return pre_stat() throws RecognitionException {
        ScriptParser.pre_stat_return retval = new ScriptParser.pre_stat_return();
        retval.start = input.LT(1);
        int pre_stat_StartIndex = input.index();
        Object root_0 = null;

        Token BREAK14=null;
        Token CONTINUE15=null;
        Token ID16=null;
        Token EQUAL17=null;
        Token EQUAL20=null;
        Token ID22=null;
        Token PLUS_PLUS23=null;
        Token PLUS_PLUS24=null;
        Token ID25=null;
        Token ID26=null;
        Token MINUS_MINUS27=null;
        Token MINUS_MINUS28=null;
        Token ID29=null;
        ScriptParser.expression_return expression13 = null;

        ScriptParser.expression_return expression18 = null;

        ScriptParser.array_element_reference_return array_element_reference19 = null;

        ScriptParser.expression_return expression21 = null;


        Object BREAK14_tree=null;
        Object CONTINUE15_tree=null;
        Object ID16_tree=null;
        Object EQUAL17_tree=null;
        Object EQUAL20_tree=null;
        Object ID22_tree=null;
        Object PLUS_PLUS23_tree=null;
        Object PLUS_PLUS24_tree=null;
        Object ID25_tree=null;
        Object ID26_tree=null;
        Object MINUS_MINUS27_tree=null;
        Object MINUS_MINUS28_tree=null;
        Object ID29_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:237:5: ( expression | BREAK | CONTINUE | ID EQUAL expression | array_element_reference EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
            int alt5=9;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:237:7: expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_pre_stat228);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:240:7: BREAK
                    {
                    root_0 = (Object)adaptor.nil();

                    BREAK14=(Token)match(input,BREAK,FOLLOW_BREAK_in_pre_stat238); if (state.failed) return retval;
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:243:7: CONTINUE
                    {
                    root_0 = (Object)adaptor.nil();

                    CONTINUE15=(Token)match(input,CONTINUE,FOLLOW_CONTINUE_in_pre_stat248); if (state.failed) return retval;
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:246:7: ID EQUAL expression
                    {
                    root_0 = (Object)adaptor.nil();

                    ID16=(Token)match(input,ID,FOLLOW_ID_in_pre_stat258); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID16_tree = (Object)adaptor.create(ID16);
                    adaptor.addChild(root_0, ID16_tree);
                    }
                    EQUAL17=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_stat260); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL17_tree = (Object)adaptor.create(EQUAL17);
                    adaptor.addChild(root_0, EQUAL17_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_stat262);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:249:7: array_element_reference EQUAL expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_array_element_reference_in_pre_stat272);
                    array_element_reference19=array_element_reference();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, array_element_reference19.getTree());
                    EQUAL20=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_stat274); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL20_tree = (Object)adaptor.create(EQUAL20);
                    adaptor.addChild(root_0, EQUAL20_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_stat276);
                    expression21=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression21.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new StorageAccessor(this, StorageAccessor.ASSIGNMENT, (array_element_reference19!=null?array_element_reference19.accessor:null), (expression21!=null?expression21.expr:null)));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:252:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID22=(Token)match(input,ID,FOLLOW_ID_in_pre_stat286); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID22_tree = (Object)adaptor.create(ID22);
                    adaptor.addChild(root_0, ID22_tree);
                    }
                    PLUS_PLUS23=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat288); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS23_tree = (Object)adaptor.create(PLUS_PLUS23);
                    adaptor.addChild(root_0, PLUS_PLUS23_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new VariableAssignment(this, (ID22!=null?ID22.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 7 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:255:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS24=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat298); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS24_tree = (Object)adaptor.create(PLUS_PLUS24);
                    adaptor.addChild(root_0, PLUS_PLUS24_tree);
                    }
                    ID25=(Token)match(input,ID,FOLLOW_ID_in_pre_stat300); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID25_tree = (Object)adaptor.create(ID25);
                    adaptor.addChild(root_0, ID25_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID25!=null?ID25.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 8 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:258:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID26=(Token)match(input,ID,FOLLOW_ID_in_pre_stat310); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID26_tree = (Object)adaptor.create(ID26);
                    adaptor.addChild(root_0, ID26_tree);
                    }
                    MINUS_MINUS27=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat312); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS27_tree = (Object)adaptor.create(MINUS_MINUS27);
                    adaptor.addChild(root_0, MINUS_MINUS27_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID26!=null?ID26.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
                    }

                    }
                    break;
                case 9 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:261:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS28=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat322); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS28_tree = (Object)adaptor.create(MINUS_MINUS28);
                    adaptor.addChild(root_0, MINUS_MINUS28_tree);
                    }
                    ID29=(Token)match(input,ID,FOLLOW_ID_in_pre_stat324); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID29_tree = (Object)adaptor.create(ID29);
                    adaptor.addChild(root_0, ID29_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID29!=null?ID29.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:267:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final ScriptParser.if_expression_return if_expression() throws RecognitionException {
        ScriptParser.if_expression_return retval = new ScriptParser.if_expression_return();
        retval.start = input.LT(1);
        int if_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_if_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:268:5: (p= pre_if_expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:268:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression354);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:284:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final ScriptParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        ScriptParser.pre_if_expression_return retval = new ScriptParser.pre_if_expression_return();
        retval.start = input.LT(1);
        int pre_if_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IF30=null;
        Token LEFT_P31=null;
        Token RIGHT_P32=null;
        Token NEWLINE33=null;
        Token NEWLINE34=null;
        Token ELSE35=null;
        Token NEWLINE36=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object IF30_tree=null;
        Object LEFT_P31_tree=null;
        Object RIGHT_P32_tree=null;
        Object NEWLINE33_tree=null;
        Object NEWLINE34_tree=null;
        Object ELSE35_tree=null;
        Object NEWLINE36_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:285:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:285:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF30=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression373); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF30_tree = (Object)adaptor.create(IF30);
            adaptor.addChild(root_0, IF30_tree);
            }
            LEFT_P31=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression375); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P31_tree = (Object)adaptor.create(LEFT_P31);
            adaptor.addChild(root_0, LEFT_P31_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression379);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P32=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression381); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P32_tree = (Object)adaptor.create(RIGHT_P32);
            adaptor.addChild(root_0, RIGHT_P32_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:285:38: ( NEWLINE )?
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE33=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression383); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE33_tree = (Object)adaptor.create(NEWLINE33);
                    adaptor.addChild(root_0, NEWLINE33_tree);
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

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:7: ( NEWLINE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NEWLINE) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred18_Script()) ) {
                    alt7=1;
                }
            }
            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE34=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression392); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE34_tree = (Object)adaptor.create(NEWLINE34);
                    adaptor.addChild(root_0, NEWLINE34_tree);
                    }

                    }
                    break;

            }

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==ELSE) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred20_Script()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE35=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression396); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE35_tree = (Object)adaptor.create(ELSE35);
                    adaptor.addChild(root_0, ELSE35_tree);
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:22: ( NEWLINE )?
                    int alt8=2;
                    alt8 = dfa8.predict(input);
                    switch (alt8) {
                        case 1 :
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE36=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression398); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE36_tree = (Object)adaptor.create(NEWLINE36);
                            adaptor.addChild(root_0, NEWLINE36_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression403);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:293:1: while_expression returns [LoopExpression expr] : p= pre_while_expression ;
    public final ScriptParser.while_expression_return while_expression() throws RecognitionException {
        ScriptParser.while_expression_return retval = new ScriptParser.while_expression_return();
        retval.start = input.LT(1);
        int while_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_while_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:294:5: (p= pre_while_expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:294:7: p= pre_while_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_while_expression_in_while_expression425);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:306:1: pre_while_expression returns [LinkedList<Expression> exprs] : WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ;
    public final ScriptParser.pre_while_expression_return pre_while_expression() throws RecognitionException {
        ScriptParser.pre_while_expression_return retval = new ScriptParser.pre_while_expression_return();
        retval.start = input.LT(1);
        int pre_while_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WHILE37=null;
        Token LEFT_P38=null;
        Token RIGHT_P39=null;
        Token NEWLINE40=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object WHILE37_tree=null;
        Object LEFT_P38_tree=null;
        Object RIGHT_P39_tree=null;
        Object NEWLINE40_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:307:5: ( WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:307:7: WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            WHILE37=(Token)match(input,WHILE,FOLLOW_WHILE_in_pre_while_expression443); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE37_tree = (Object)adaptor.create(WHILE37);
            adaptor.addChild(root_0, WHILE37_tree);
            }
            LEFT_P38=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_while_expression445); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P38_tree = (Object)adaptor.create(LEFT_P38);
            adaptor.addChild(root_0, LEFT_P38_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_while_expression449);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P39=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_while_expression451); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P39_tree = (Object)adaptor.create(RIGHT_P39);
            adaptor.addChild(root_0, RIGHT_P39_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:307:41: ( NEWLINE )?
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE40=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_while_expression453); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE40_tree = (Object)adaptor.create(NEWLINE40);
                    adaptor.addChild(root_0, NEWLINE40_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_while_expression458);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:313:1: for_expression returns [LoopExpression expr] : p= pre_for_expression ;
    public final ScriptParser.for_expression_return for_expression() throws RecognitionException {
        ScriptParser.for_expression_return retval = new ScriptParser.for_expression_return();
        retval.start = input.LT(1);
        int for_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_for_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:314:5: (p= pre_for_expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:314:7: p= pre_for_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_for_expression_in_for_expression478);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:326:1: pre_for_expression returns [LinkedList<Expression> exprs] : ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat );
    public final ScriptParser.pre_for_expression_return pre_for_expression() throws RecognitionException {
        ScriptParser.pre_for_expression_return retval = new ScriptParser.pre_for_expression_return();
        retval.start = input.LT(1);
        int pre_for_expression_StartIndex = input.index();
        Object root_0 = null;

        Token FOR41=null;
        Token LEFT_P42=null;
        Token PV43=null;
        Token PV44=null;
        Token RIGHT_P45=null;
        Token NEWLINE46=null;
        Token FOR47=null;
        Token LEFT_P48=null;
        Token ID49=null;
        Token EQUAL50=null;
        Token RIGHT_P52=null;
        Token NEWLINE53=null;
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;

        ScriptParser.range_return range51 = null;


        Object FOR41_tree=null;
        Object LEFT_P42_tree=null;
        Object PV43_tree=null;
        Object PV44_tree=null;
        Object RIGHT_P45_tree=null;
        Object NEWLINE46_tree=null;
        Object FOR47_tree=null;
        Object LEFT_P48_tree=null;
        Object ID49_tree=null;
        Object EQUAL50_tree=null;
        Object RIGHT_P52_tree=null;
        Object NEWLINE53_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:5: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==FOR) ) {
                int LA13_1 = input.LA(2);

                if ( (synpred23_Script()) ) {
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR41=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression496); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR41_tree = (Object)adaptor.create(FOR41);
                    adaptor.addChild(root_0, FOR41_tree);
                    }
                    LEFT_P42=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression498); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P42_tree = (Object)adaptor.create(LEFT_P42);
                    adaptor.addChild(root_0, LEFT_P42_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression502);
                    e_init=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_init.getTree());
                    PV43=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression504); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV43_tree = (Object)adaptor.create(PV43);
                    adaptor.addChild(root_0, PV43_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_for_expression508);
                    e_cond=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_cond.getTree());
                    PV44=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression510); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV44_tree = (Object)adaptor.create(PV44);
                    adaptor.addChild(root_0, PV44_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression514);
                    e_inc=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_inc.getTree());
                    RIGHT_P45=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression516); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P45_tree = (Object)adaptor.create(RIGHT_P45);
                    adaptor.addChild(root_0, RIGHT_P45_tree);
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:81: ( NEWLINE )?
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE46=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression518); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE46_tree = (Object)adaptor.create(NEWLINE46);
                            adaptor.addChild(root_0, NEWLINE46_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression523);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:334:7: FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR47=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression533); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR47_tree = (Object)adaptor.create(FOR47);
                    adaptor.addChild(root_0, FOR47_tree);
                    }
                    LEFT_P48=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression535); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P48_tree = (Object)adaptor.create(LEFT_P48);
                    adaptor.addChild(root_0, LEFT_P48_tree);
                    }
                    ID49=(Token)match(input,ID,FOLLOW_ID_in_pre_for_expression537); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID49_tree = (Object)adaptor.create(ID49);
                    adaptor.addChild(root_0, ID49_tree);
                    }
                    EQUAL50=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_for_expression539); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL50_tree = (Object)adaptor.create(EQUAL50);
                    adaptor.addChild(root_0, EQUAL50_tree);
                    }
                    pushFollow(FOLLOW_range_in_pre_for_expression541);
                    range51=range();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, range51.getTree());
                    RIGHT_P52=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression543); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P52_tree = (Object)adaptor.create(RIGHT_P52);
                    adaptor.addChild(root_0, RIGHT_P52_tree);
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:334:41: ( NEWLINE )?
                    int alt12=2;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE53=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression545); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE53_tree = (Object)adaptor.create(NEWLINE53);
                            adaptor.addChild(root_0, NEWLINE53_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression550);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    if ( state.backtracking==0 ) {

                              retval.exprs = new LinkedList();

                              boolean plus_minus = true;

                              Calculable init = (range51!=null?range51.range_ele:null).get(0);
                              Calculable increment = (range51!=null?range51.range_ele:null).size()==3 ? (range51!=null?range51.range_ele:null).get(1) : new Numeric(1.0f);
                              Calculable condition = (range51!=null?range51.range_ele:null).get((range51!=null?range51.range_ele:null).size()-1);

                              try {
                                  if (increment.getSimplifiedCalculable() instanceof Numeric) {
                                      double val = (Double) ((Numeric) increment.getSimplifiedCalculable()).getNativeValue();
                                      plus_minus = val>=0;
                                  }
                              } catch (Exception e) {
                                  //NOTHING
                              }

                              VariableAssignment va = new VariableAssignment(this, (ID49!=null?ID49.getText():null), init);
                              Expression init_expr = new Expression(true, this, va);
                              
                              LinkedList<Object> term_ele = new LinkedList();
                              term_ele.add(new Variable(this, (ID49!=null?ID49.getText():null)));
                              term_ele.add(Operator.OPERATOR_PLUS);
                              term_ele.add(increment);
                              Term t = new Term(this, term_ele);
                              VariableAssignment vai = new VariableAssignment(this, (ID49!=null?ID49.getText():null), t);
                              Expression increment_expr = new Expression(true, this, vai);

                              LinkedList<Object> term_elec = new LinkedList();
                              term_elec.add(new Variable(this, (ID49!=null?ID49.getText():null)));
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:377:1: range returns [LinkedList<Calculable> range_ele] : a= expression ( ARROW b= range )* ;
    public final ScriptParser.range_return range() throws RecognitionException {
        ScriptParser.range_return retval = new ScriptParser.range_return();
        retval.start = input.LT(1);
        int range_StartIndex = input.index();
        Object root_0 = null;

        Token ARROW54=null;
        ScriptParser.expression_return a = null;

        ScriptParser.range_return b = null;


        Object ARROW54_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:378:5: (a= expression ( ARROW b= range )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:378:7: a= expression ( ARROW b= range )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_range570);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.range_ele = new LinkedList();
                      retval.range_ele.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:381:7: ( ARROW b= range )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==ARROW) ) {
                    int LA14_2 = input.LA(2);

                    if ( (synpred25_Script()) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:381:8: ARROW b= range
            	    {
            	    ARROW54=(Token)match(input,ARROW,FOLLOW_ARROW_in_range575); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    ARROW54_tree = (Object)adaptor.create(ARROW54);
            	    adaptor.addChild(root_0, ARROW54_tree);
            	    }
            	    pushFollow(FOLLOW_range_in_range579);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:388:1: expression returns [Expression expr] : terms ;
    public final ScriptParser.expression_return expression() throws RecognitionException {
        ScriptParser.expression_return retval = new ScriptParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.terms_return terms55 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:389:5: ( terms )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:389:7: terms
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_terms_in_expression600);
            terms55=terms();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, terms55.getTree());
            if ( state.backtracking==0 ) {

                      retval.expr = new Expression( this, new Term(this, (terms55!=null?terms55.terms:null)) );
                  
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:393:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* ;
    public final ScriptParser.terms_return terms() throws RecognitionException {
        ScriptParser.terms_return retval = new ScriptParser.terms_return();
        retval.start = input.LT(1);
        int terms_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS56=null;
        Token MINUS57=null;
        Token AND58=null;
        Token OR59=null;
        ScriptParser.term_return t = null;


        Object PLUS56_tree=null;
        Object MINUS57_tree=null;
        Object AND58_tree=null;
        Object OR59_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:394:5: (t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:394:7: t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms620);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:397:7: ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            loop15:
            do {
                int alt15=5;
                switch ( input.LA(1) ) {
                case PLUS:
                    {
                    alt15=1;
                    }
                    break;
                case MINUS:
                    {
                    alt15=2;
                    }
                    break;
                case AND:
                    {
                    alt15=3;
                    }
                    break;
                case OR:
                    {
                    alt15=4;
                    }
                    break;

                }

                switch (alt15) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:397:9: PLUS t= term
            	    {
            	    PLUS56=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms626); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS56_tree = (Object)adaptor.create(PLUS56);
            	    adaptor.addChild(root_0, PLUS56_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms630);
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:401:9: MINUS t= term
            	    {
            	    MINUS57=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms642); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS57_tree = (Object)adaptor.create(MINUS57);
            	    adaptor.addChild(root_0, MINUS57_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms646);
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:405:9: AND t= term
            	    {
            	    AND58=(Token)match(input,AND,FOLLOW_AND_in_terms658); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND58_tree = (Object)adaptor.create(AND58);
            	    adaptor.addChild(root_0, AND58_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms662);
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:409:9: OR t= term
            	    {
            	    OR59=(Token)match(input,OR,FOLLOW_OR_in_terms674); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR59_tree = (Object)adaptor.create(OR59);
            	    adaptor.addChild(root_0, OR59_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms678);
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
            	    break loop15;
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:415:1: term returns [LinkedList<Object> atoms] : (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.term_return term() throws RecognitionException {
        ScriptParser.term_return retval = new ScriptParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token MULT60=null;
        Token DIV61=null;
        Token CMP_LT62=null;
        Token CMP_LT_EQ63=null;
        Token CMP_GT64=null;
        Token CMP_GT_EQ65=null;
        Token CMP_EQ66=null;
        Token CMP_NEQ67=null;
        Token ID68=null;
        Token PLUS_PLUS69=null;
        Token PLUS_PLUS70=null;
        Token ID71=null;
        Token ID72=null;
        Token MINUS_MINUS73=null;
        Token MINUS_MINUS74=null;
        Token ID75=null;
        ScriptParser.atom_return a = null;


        Object MULT60_tree=null;
        Object DIV61_tree=null;
        Object CMP_LT62_tree=null;
        Object CMP_LT_EQ63_tree=null;
        Object CMP_GT64_tree=null;
        Object CMP_GT_EQ65_tree=null;
        Object CMP_EQ66_tree=null;
        Object CMP_NEQ67_tree=null;
        Object ID68_tree=null;
        Object PLUS_PLUS69_tree=null;
        Object PLUS_PLUS70_tree=null;
        Object ID71_tree=null;
        Object ID72_tree=null;
        Object MINUS_MINUS73_tree=null;
        Object MINUS_MINUS74_tree=null;
        Object ID75_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:416:5: (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
            int alt17=5;
            switch ( input.LA(1) ) {
            case LEFT_CB:
            case LEFT_P:
            case MINUS:
            case LEFT_B:
            case NUM:
            case BOOL:
            case CONSTANT:
            case STRING_LITERAL:
                {
                alt17=1;
                }
                break;
            case ID:
                {
                switch ( input.LA(2) ) {
                case EOF:
                case NEWLINE:
                case RIGHT_CB:
                case LEFT_P:
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
                case LEFT_B:
                case RIGHT_B:
                case TP:
                    {
                    alt17=1;
                    }
                    break;
                case PLUS_PLUS:
                    {
                    alt17=2;
                    }
                    break;
                case MINUS_MINUS:
                    {
                    alt17=4;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 2, input);

                    throw nvae;
                }

                }
                break;
            case PLUS_PLUS:
                {
                alt17=3;
                }
                break;
            case MINUS_MINUS:
                {
                alt17=5;
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:416:7: a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term702);
                    a=atom();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              retval.atoms.add((a!=null?a.value:null));
                          
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:419:7: ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    loop16:
                    do {
                        int alt16=9;
                        switch ( input.LA(1) ) {
                        case MULT:
                            {
                            alt16=1;
                            }
                            break;
                        case DIV:
                            {
                            alt16=2;
                            }
                            break;
                        case CMP_LT:
                            {
                            alt16=3;
                            }
                            break;
                        case CMP_LT_EQ:
                            {
                            alt16=4;
                            }
                            break;
                        case CMP_GT:
                            {
                            alt16=5;
                            }
                            break;
                        case CMP_GT_EQ:
                            {
                            alt16=6;
                            }
                            break;
                        case CMP_EQ:
                            {
                            alt16=7;
                            }
                            break;
                        case CMP_NEQ:
                            {
                            alt16=8;
                            }
                            break;

                        }

                        switch (alt16) {
                    	case 1 :
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:419:9: MULT a= atom
                    	    {
                    	    MULT60=(Token)match(input,MULT,FOLLOW_MULT_in_term708); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    MULT60_tree = (Object)adaptor.create(MULT60);
                    	    adaptor.addChild(root_0, MULT60_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term712);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:423:9: DIV a= atom
                    	    {
                    	    DIV61=(Token)match(input,DIV,FOLLOW_DIV_in_term724); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DIV61_tree = (Object)adaptor.create(DIV61);
                    	    adaptor.addChild(root_0, DIV61_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term728);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:427:9: CMP_LT a= atom
                    	    {
                    	    CMP_LT62=(Token)match(input,CMP_LT,FOLLOW_CMP_LT_in_term740); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT62_tree = (Object)adaptor.create(CMP_LT62);
                    	    adaptor.addChild(root_0, CMP_LT62_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term744);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:431:9: CMP_LT_EQ a= atom
                    	    {
                    	    CMP_LT_EQ63=(Token)match(input,CMP_LT_EQ,FOLLOW_CMP_LT_EQ_in_term757); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT_EQ63_tree = (Object)adaptor.create(CMP_LT_EQ63);
                    	    adaptor.addChild(root_0, CMP_LT_EQ63_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term761);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:435:9: CMP_GT a= atom
                    	    {
                    	    CMP_GT64=(Token)match(input,CMP_GT,FOLLOW_CMP_GT_in_term774); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT64_tree = (Object)adaptor.create(CMP_GT64);
                    	    adaptor.addChild(root_0, CMP_GT64_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term778);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:439:9: CMP_GT_EQ a= atom
                    	    {
                    	    CMP_GT_EQ65=(Token)match(input,CMP_GT_EQ,FOLLOW_CMP_GT_EQ_in_term790); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT_EQ65_tree = (Object)adaptor.create(CMP_GT_EQ65);
                    	    adaptor.addChild(root_0, CMP_GT_EQ65_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term794);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:443:9: CMP_EQ a= atom
                    	    {
                    	    CMP_EQ66=(Token)match(input,CMP_EQ,FOLLOW_CMP_EQ_in_term807); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_EQ66_tree = (Object)adaptor.create(CMP_EQ66);
                    	    adaptor.addChild(root_0, CMP_EQ66_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term811);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:447:9: CMP_NEQ a= atom
                    	    {
                    	    CMP_NEQ67=(Token)match(input,CMP_NEQ,FOLLOW_CMP_NEQ_in_term823); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_NEQ67_tree = (Object)adaptor.create(CMP_NEQ67);
                    	    adaptor.addChild(root_0, CMP_NEQ67_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term827);
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
                    	    break loop16;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:452:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID68=(Token)match(input,ID,FOLLOW_ID_in_term844); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID68_tree = (Object)adaptor.create(ID68);
                    adaptor.addChild(root_0, ID68_tree);
                    }
                    PLUS_PLUS69=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term846); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS69_tree = (Object)adaptor.create(PLUS_PLUS69);
                    adaptor.addChild(root_0, PLUS_PLUS69_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID68!=null?ID68.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:457:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS70=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term856); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS70_tree = (Object)adaptor.create(PLUS_PLUS70);
                    adaptor.addChild(root_0, PLUS_PLUS70_tree);
                    }
                    ID71=(Token)match(input,ID,FOLLOW_ID_in_term858); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID71_tree = (Object)adaptor.create(ID71);
                    adaptor.addChild(root_0, ID71_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID71!=null?ID71.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:462:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID72=(Token)match(input,ID,FOLLOW_ID_in_term868); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID72_tree = (Object)adaptor.create(ID72);
                    adaptor.addChild(root_0, ID72_tree);
                    }
                    MINUS_MINUS73=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term870); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS73_tree = (Object)adaptor.create(MINUS_MINUS73);
                    adaptor.addChild(root_0, MINUS_MINUS73_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID72!=null?ID72.getText():null), Operator.OPERATOR_MINUS_MINUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:467:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS74=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term880); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS74_tree = (Object)adaptor.create(MINUS_MINUS74);
                    adaptor.addChild(root_0, MINUS_MINUS74_tree);
                    }
                    ID75=(Token)match(input,ID,FOLLOW_ID_in_term882); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID75_tree = (Object)adaptor.create(ID75);
                    adaptor.addChild(root_0, ID75_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID75!=null?ID75.getText():null), Operator.OPERATOR_MINUS_MINUS);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:474:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P ( args )? RIGHT_P ;
    public final ScriptParser.function_call_return function_call() throws RecognitionException {
        ScriptParser.function_call_return retval = new ScriptParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token ID76=null;
        Token LEFT_P77=null;
        Token RIGHT_P79=null;
        ScriptParser.args_return args78 = null;


        Object ID76_tree=null;
        Object LEFT_P77_tree=null;
        Object RIGHT_P79_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:474:55: ( ID LEFT_P ( args )? RIGHT_P )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:475:5: ID LEFT_P ( args )? RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID76=(Token)match(input,ID,FOLLOW_ID_in_function_call900); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID76_tree = (Object)adaptor.create(ID76);
            adaptor.addChild(root_0, ID76_tree);
            }
            LEFT_P77=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call902); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P77_tree = (Object)adaptor.create(LEFT_P77);
            adaptor.addChild(root_0, LEFT_P77_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:475:15: ( args )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==LEFT_CB||LA18_0==ID||(LA18_0>=PLUS_PLUS && LA18_0<=MINUS_MINUS)||LA18_0==LEFT_P||LA18_0==MINUS||LA18_0==LEFT_B||(LA18_0>=NUM && LA18_0<=STRING_LITERAL)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: args
                    {
                    pushFollow(FOLLOW_args_in_function_call904);
                    args78=args();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, args78.getTree());

                    }
                    break;

            }

            RIGHT_P79=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call907); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P79_tree = (Object)adaptor.create(RIGHT_P79);
            adaptor.addChild(root_0, RIGHT_P79_tree);
            }
            if ( state.backtracking==0 ) {

                      if ((args78!=null?args78.params:null) != null) {
                          retval.name_params = (args78!=null?args78.params:null);
                          retval.name_params.add(0, (ID76!=null?ID76.getText():null));
                      } else {
                          retval.name_params = new LinkedList();
                          retval.name_params.add((ID76!=null?ID76.getText():null));
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:486:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final ScriptParser.args_return args() throws RecognitionException {
        ScriptParser.args_return retval = new ScriptParser.args_return();
        retval.start = input.LT(1);
        int args_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA80=null;
        ScriptParser.expression_return a = null;

        ScriptParser.args_return b = null;


        Object COMMA80_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:486:41: (a= expression ( COMMA b= args )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:487:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args927);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:490:7: ( COMMA b= args )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==COMMA) ) {
                    int LA19_2 = input.LA(2);

                    if ( (synpred43_Script()) ) {
                        alt19=1;
                    }


                }


                switch (alt19) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:490:8: COMMA b= args
            	    {
            	    COMMA80=(Token)match(input,COMMA,FOLLOW_COMMA_in_args932); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA80_tree = (Object)adaptor.create(COMMA80);
            	    adaptor.addChild(root_0, COMMA80_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args936);
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

    public static class array_return extends ParserRuleReturnScope {
        public ObjectArray array;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "array"
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:499:1: array returns [ObjectArray array] : LEFT_B a= args RIGHT_B ;
    public final ScriptParser.array_return array() throws RecognitionException {
        ScriptParser.array_return retval = new ScriptParser.array_return();
        retval.start = input.LT(1);
        int array_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_B81=null;
        Token RIGHT_B82=null;
        ScriptParser.args_return a = null;


        Object LEFT_B81_tree=null;
        Object RIGHT_B82_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:499:34: ( LEFT_B a= args RIGHT_B )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:500:5: LEFT_B a= args RIGHT_B
            {
            root_0 = (Object)adaptor.nil();

            LEFT_B81=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array958); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_B81_tree = (Object)adaptor.create(LEFT_B81);
            adaptor.addChild(root_0, LEFT_B81_tree);
            }
            pushFollow(FOLLOW_args_in_array962);
            a=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            RIGHT_B82=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array964); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_B82_tree = (Object)adaptor.create(RIGHT_B82);
            adaptor.addChild(root_0, RIGHT_B82_tree);
            }
            if ( state.backtracking==0 ) {

                      retval.array = new ObjectArray(this, (a!=null?a.params:null));
                  
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
            if ( state.backtracking>0 ) { memoize(input, 18, array_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "array"

    public static class array_element_reference_return extends ParserRuleReturnScope {
        public StorageAccessor accessor;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "array_element_reference"
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:506:1: array_element_reference returns [StorageAccessor accessor] : (a= ID LEFT_B b= ID RIGHT_B | a= ID LEFT_B b= NUM RIGHT_B | a= ID LEFT_B s= string_literal RIGHT_B );
    public final ScriptParser.array_element_reference_return array_element_reference() throws RecognitionException {
        ScriptParser.array_element_reference_return retval = new ScriptParser.array_element_reference_return();
        retval.start = input.LT(1);
        int array_element_reference_StartIndex = input.index();
        Object root_0 = null;

        Token a=null;
        Token b=null;
        Token LEFT_B83=null;
        Token RIGHT_B84=null;
        Token LEFT_B85=null;
        Token RIGHT_B86=null;
        Token LEFT_B87=null;
        Token RIGHT_B88=null;
        ScriptParser.string_literal_return s = null;


        Object a_tree=null;
        Object b_tree=null;
        Object LEFT_B83_tree=null;
        Object RIGHT_B84_tree=null;
        Object LEFT_B85_tree=null;
        Object RIGHT_B86_tree=null;
        Object LEFT_B87_tree=null;
        Object RIGHT_B88_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:507:5: (a= ID LEFT_B b= ID RIGHT_B | a= ID LEFT_B b= NUM RIGHT_B | a= ID LEFT_B s= string_literal RIGHT_B )
            int alt20=3;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ID) ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1==LEFT_B) ) {
                    switch ( input.LA(3) ) {
                    case ID:
                        {
                        alt20=1;
                        }
                        break;
                    case NUM:
                        {
                        alt20=2;
                        }
                        break;
                    case STRING_LITERAL:
                        {
                        alt20=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 2, input);

                        throw nvae;
                    }

                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:507:7: a= ID LEFT_B b= ID RIGHT_B
                    {
                    root_0 = (Object)adaptor.nil();

                    a=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference986); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (Object)adaptor.create(a);
                    adaptor.addChild(root_0, a_tree);
                    }
                    LEFT_B83=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array_element_reference988); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_B83_tree = (Object)adaptor.create(LEFT_B83);
                    adaptor.addChild(root_0, LEFT_B83_tree);
                    }
                    b=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference992); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    b_tree = (Object)adaptor.create(b);
                    adaptor.addChild(root_0, b_tree);
                    }
                    RIGHT_B84=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array_element_reference994); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_B84_tree = (Object)adaptor.create(RIGHT_B84);
                    adaptor.addChild(root_0, RIGHT_B84_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, (a!=null?a.getText():null)), new Variable(this, (b!=null?b.getText():null)), null);
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:510:7: a= ID LEFT_B b= NUM RIGHT_B
                    {
                    root_0 = (Object)adaptor.nil();

                    a=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference1006); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (Object)adaptor.create(a);
                    adaptor.addChild(root_0, a_tree);
                    }
                    LEFT_B85=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array_element_reference1008); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_B85_tree = (Object)adaptor.create(LEFT_B85);
                    adaptor.addChild(root_0, LEFT_B85_tree);
                    }
                    b=(Token)match(input,NUM,FOLLOW_NUM_in_array_element_reference1012); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    b_tree = (Object)adaptor.create(b);
                    adaptor.addChild(root_0, b_tree);
                    }
                    RIGHT_B86=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array_element_reference1014); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_B86_tree = (Object)adaptor.create(RIGHT_B86);
                    adaptor.addChild(root_0, RIGHT_B86_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, (a!=null?a.getText():null)), new Numeric(Double.parseDouble((b!=null?b.getText():null))), null);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:513:7: a= ID LEFT_B s= string_literal RIGHT_B
                    {
                    root_0 = (Object)adaptor.nil();

                    a=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference1026); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (Object)adaptor.create(a);
                    adaptor.addChild(root_0, a_tree);
                    }
                    LEFT_B87=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array_element_reference1028); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_B87_tree = (Object)adaptor.create(LEFT_B87);
                    adaptor.addChild(root_0, LEFT_B87_tree);
                    }
                    pushFollow(FOLLOW_string_literal_in_array_element_reference1032);
                    s=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    RIGHT_B88=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array_element_reference1034); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_B88_tree = (Object)adaptor.create(RIGHT_B88);
                    adaptor.addChild(root_0, RIGHT_B88_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, (a!=null?a.getText():null)), (s!=null?s.value:null), null);
                          
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
            if ( state.backtracking>0 ) { memoize(input, 19, array_element_reference_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "array_element_reference"

    public static class atom_return extends ParserRuleReturnScope {
        public Object value;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:517:1: atom returns [Object value] : ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | CONSTANT | string_literal | dictionary | array | function_call | array_element_reference );
    public final ScriptParser.atom_return atom() throws RecognitionException {
        ScriptParser.atom_return retval = new ScriptParser.atom_return();
        retval.start = input.LT(1);
        int atom_StartIndex = input.index();
        Object root_0 = null;

        Token NUM89=null;
        Token MINUS90=null;
        Token NUM91=null;
        Token BOOL92=null;
        Token LEFT_P93=null;
        Token RIGHT_P95=null;
        Token ID96=null;
        Token CONSTANT97=null;
        ScriptParser.expression_return expression94 = null;

        ScriptParser.string_literal_return string_literal98 = null;

        ScriptParser.dictionary_return dictionary99 = null;

        ScriptParser.array_return array100 = null;

        ScriptParser.function_call_return function_call101 = null;

        ScriptParser.array_element_reference_return array_element_reference102 = null;


        Object NUM89_tree=null;
        Object MINUS90_tree=null;
        Object NUM91_tree=null;
        Object BOOL92_tree=null;
        Object LEFT_P93_tree=null;
        Object RIGHT_P95_tree=null;
        Object ID96_tree=null;
        Object CONSTANT97_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:518:5: ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | CONSTANT | string_literal | dictionary | array | function_call | array_element_reference )
            int alt21=11;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:518:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM89=(Token)match(input,NUM,FOLLOW_NUM_in_atom1052); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM89_tree = (Object)adaptor.create(NUM89);
                    adaptor.addChild(root_0, NUM89_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Double.parseDouble((NUM89!=null?NUM89.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:521:7: MINUS NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS90=(Token)match(input,MINUS,FOLLOW_MINUS_in_atom1062); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS90_tree = (Object)adaptor.create(MINUS90);
                    adaptor.addChild(root_0, MINUS90_tree);
                    }
                    NUM91=(Token)match(input,NUM,FOLLOW_NUM_in_atom1064); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM91_tree = (Object)adaptor.create(NUM91);
                    adaptor.addChild(root_0, NUM91_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( -1.0 * Double.parseDouble((NUM91!=null?NUM91.getText():null)) );
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:524:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL92=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom1074); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL92_tree = (Object)adaptor.create(BOOL92);
                    adaptor.addChild(root_0, BOOL92_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL92!=null?BOOL92.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL92!=null?BOOL92.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              }
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:531:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P93=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom1084); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P93_tree = (Object)adaptor.create(LEFT_P93);
                    adaptor.addChild(root_0, LEFT_P93_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom1086);
                    expression94=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression94.getTree());
                    RIGHT_P95=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom1088); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P95_tree = (Object)adaptor.create(RIGHT_P95);
                    adaptor.addChild(root_0, RIGHT_P95_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression94!=null?expression94.expr:null);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:534:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID96=(Token)match(input,ID,FOLLOW_ID_in_atom1098); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID96_tree = (Object)adaptor.create(ID96);
                    adaptor.addChild(root_0, ID96_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID96!=null?ID96.getText():null));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:537:7: CONSTANT
                    {
                    root_0 = (Object)adaptor.nil();

                    CONSTANT97=(Token)match(input,CONSTANT,FOLLOW_CONSTANT_in_atom1108); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    CONSTANT97_tree = (Object)adaptor.create(CONSTANT97);
                    adaptor.addChild(root_0, CONSTANT97_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (CONSTANT97!=null?CONSTANT97.getText():null));
                          
                    }

                    }
                    break;
                case 7 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:540:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom1118);
                    string_literal98=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal98.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal98!=null?string_literal98.value:null);
                          
                    }

                    }
                    break;
                case 8 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:543:7: dictionary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dictionary_in_atom1128);
                    dictionary99=dictionary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary99.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (dictionary99!=null?dictionary99.value:null);
                          
                    }

                    }
                    break;
                case 9 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:546:7: array
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_array_in_atom1138);
                    array100=array();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, array100.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (array100!=null?array100.array:null);
                          
                    }

                    }
                    break;
                case 10 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:549:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_atom1148);
                    function_call101=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call101.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = new Expression( this, new FunctionCall( this, (function_call101!=null?function_call101.name_params:null) ) );
                          
                    }

                    }
                    break;
                case 11 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:552:7: array_element_reference
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_array_element_reference_in_atom1158);
                    array_element_reference102=array_element_reference();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, array_element_reference102.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = new Expression( this, (array_element_reference102!=null?array_element_reference102.accessor:null));
                          
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
            if ( state.backtracking>0 ) { memoize(input, 20, atom_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:563:1: string_literal returns [CharString value] : s= STRING_LITERAL ;
    public final ScriptParser.string_literal_return string_literal() throws RecognitionException {
        ScriptParser.string_literal_return retval = new ScriptParser.string_literal_return();
        retval.start = input.LT(1);
        int string_literal_StartIndex = input.index();
        Object root_0 = null;

        Token s=null;

        Object s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:563:43: (s= STRING_LITERAL )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:563:45: s= STRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_string_literal1181); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 21, string_literal_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:568:1: dictionary returns [Dictionary value] : LEFT_CB dictionary_elements RIGHT_CB ;
    public final ScriptParser.dictionary_return dictionary() throws RecognitionException {
        ScriptParser.dictionary_return retval = new ScriptParser.dictionary_return();
        retval.start = input.LT(1);
        int dictionary_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB103=null;
        Token RIGHT_CB105=null;
        ScriptParser.dictionary_elements_return dictionary_elements104 = null;


        Object LEFT_CB103_tree=null;
        Object RIGHT_CB105_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:568:39: ( LEFT_CB dictionary_elements RIGHT_CB )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:569:5: LEFT_CB dictionary_elements RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB103=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_dictionary1200); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB103_tree = (Object)adaptor.create(LEFT_CB103);
            adaptor.addChild(root_0, LEFT_CB103_tree);
            }
            pushFollow(FOLLOW_dictionary_elements_in_dictionary1202);
            dictionary_elements104=dictionary_elements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary_elements104.getTree());
            RIGHT_CB105=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_dictionary1204); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB105_tree = (Object)adaptor.create(RIGHT_CB105);
            adaptor.addChild(root_0, RIGHT_CB105_tree);
            }
            if ( state.backtracking==0 ) {

                      HashMap vs = new HashMap();
                      int size = (dictionary_elements104!=null?dictionary_elements104.keys_values:null).size();
                      for (int k=0; k<size; k+=2) {
                          vs.put((dictionary_elements104!=null?dictionary_elements104.keys_values:null).get(k), (dictionary_elements104!=null?dictionary_elements104.keys_values:null).get(k+1));
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
            if ( state.backtracking>0 ) { memoize(input, 22, dictionary_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:578:1: dictionary_elements returns [LinkedList<Object> keys_values] : (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* ;
    public final ScriptParser.dictionary_elements_return dictionary_elements() throws RecognitionException {
        ScriptParser.dictionary_elements_return retval = new ScriptParser.dictionary_elements_return();
        retval.start = input.LT(1);
        int dictionary_elements_StartIndex = input.index();
        Object root_0 = null;

        Token TP106=null;
        Token NEWLINE107=null;
        Token COMMA108=null;
        Token NEWLINE109=null;
        ScriptParser.expression_return e1 = null;

        ScriptParser.expression_return e2 = null;

        ScriptParser.dictionary_elements_return d = null;


        Object TP106_tree=null;
        Object NEWLINE107_tree=null;
        Object COMMA108_tree=null;
        Object NEWLINE109_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:578:62: ( (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:579:5: (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:579:5: (e1= expression TP e2= expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:579:6: e1= expression TP e2= expression
            {
            pushFollow(FOLLOW_expression_in_dictionary_elements1225);
            e1=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
            TP106=(Token)match(input,TP,FOLLOW_TP_in_dictionary_elements1227); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TP106_tree = (Object)adaptor.create(TP106);
            adaptor.addChild(root_0, TP106_tree);
            }
            pushFollow(FOLLOW_expression_in_dictionary_elements1231);
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:7: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==NEWLINE) ) {
                    int LA24_2 = input.LA(2);

                    if ( (synpred58_Script()) ) {
                        alt24=1;
                    }


                }
                else if ( (LA24_0==COMMA) ) {
                    int LA24_3 = input.LA(2);

                    if ( (synpred58_Script()) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
            	    {
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:8: ( NEWLINE )?
            	    int alt22=2;
            	    int LA22_0 = input.LA(1);

            	    if ( (LA22_0==NEWLINE) ) {
            	        alt22=1;
            	    }
            	    switch (alt22) {
            	        case 1 :
            	            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE107=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1237); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE107_tree = (Object)adaptor.create(NEWLINE107);
            	            adaptor.addChild(root_0, NEWLINE107_tree);
            	            }

            	            }
            	            break;

            	    }

            	    COMMA108=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictionary_elements1240); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA108_tree = (Object)adaptor.create(COMMA108);
            	    adaptor.addChild(root_0, COMMA108_tree);
            	    }
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:23: ( NEWLINE )?
            	    int alt23=2;
            	    int LA23_0 = input.LA(1);

            	    if ( (LA23_0==NEWLINE) ) {
            	        alt23=1;
            	    }
            	    switch (alt23) {
            	        case 1 :
            	            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE109=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1242); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE109_tree = (Object)adaptor.create(NEWLINE109);
            	            adaptor.addChild(root_0, NEWLINE109_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_dictionary_elements_in_dictionary_elements1247);
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
            if ( state.backtracking>0 ) { memoize(input, 23, dictionary_elements_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dictionary_elements"

    // $ANTLR start synpred1_Script
    public final void synpred1_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:199:13: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:199:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Script100); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Script

    // $ANTLR start synpred3_Script
    public final void synpred3_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:209:8: (s= stat )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:209:8: s= stat
        {
        pushFollow(FOLLOW_stat_in_synpred3_Script134);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Script

    // $ANTLR start synpred4_Script
    public final void synpred4_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:217:7: ( pre_stat NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:217:7: pre_stat NEWLINE
        {
        pushFollow(FOLLOW_pre_stat_in_synpred4_Script159);
        pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred4_Script161); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Script

    // $ANTLR start synpred6_Script
    public final void synpred6_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:224:7: ( block )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:224:7: block
        {
        pushFollow(FOLLOW_block_in_synpred6_Script181);
        block();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Script

    // $ANTLR start synpred9_Script
    public final void synpred9_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:237:7: ( expression )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:237:7: expression
        {
        pushFollow(FOLLOW_expression_in_synpred9_Script228);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Script

    // $ANTLR start synpred14_Script
    public final void synpred14_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:252:7: ( ID PLUS_PLUS )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:252:7: ID PLUS_PLUS
        {
        match(input,ID,FOLLOW_ID_in_synpred14_Script286); if (state.failed) return ;
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred14_Script288); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Script

    // $ANTLR start synpred15_Script
    public final void synpred15_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:255:7: ( PLUS_PLUS ID )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:255:7: PLUS_PLUS ID
        {
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred15_Script298); if (state.failed) return ;
        match(input,ID,FOLLOW_ID_in_synpred15_Script300); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Script

    // $ANTLR start synpred16_Script
    public final void synpred16_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:258:7: ( ID MINUS_MINUS )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:258:7: ID MINUS_MINUS
        {
        match(input,ID,FOLLOW_ID_in_synpred16_Script310); if (state.failed) return ;
        match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_synpred16_Script312); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Script

    // $ANTLR start synpred17_Script
    public final void synpred17_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:285:38: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:285:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred17_Script383); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Script

    // $ANTLR start synpred18_Script
    public final void synpred18_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:7: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred18_Script392); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_Script

    // $ANTLR start synpred19_Script
    public final void synpred19_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:22: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred19_Script398); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Script

    // $ANTLR start synpred20_Script
    public final void synpred20_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:17: ( ELSE ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred20_Script396); if (state.failed) return ;
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:289:22: ( NEWLINE )?
        int alt25=2;
        int LA25_0 = input.LA(1);

        if ( (LA25_0==NEWLINE) ) {
            int LA25_1 = input.LA(2);

            if ( ((LA25_1>=LEFT_CB && LA25_1<=NEWLINE)||(LA25_1>=BREAK && LA25_1<=ID)||(LA25_1>=PLUS_PLUS && LA25_1<=LEFT_P)||(LA25_1>=WHILE && LA25_1<=FOR)||LA25_1==MINUS||LA25_1==LEFT_B||(LA25_1>=NUM && LA25_1<=STRING_LITERAL)) ) {
                alt25=1;
            }
        }
        switch (alt25) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred20_Script398); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred20_Script403);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_Script

    // $ANTLR start synpred21_Script
    public final void synpred21_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:307:41: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:307:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred21_Script453); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_Script

    // $ANTLR start synpred22_Script
    public final void synpred22_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:81: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:81: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred22_Script518); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_Script

    // $ANTLR start synpred23_Script
    public final void synpred23_Script_fragment() throws RecognitionException {   
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:7: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
        {
        match(input,FOR,FOLLOW_FOR_in_synpred23_Script496); if (state.failed) return ;
        match(input,LEFT_P,FOLLOW_LEFT_P_in_synpred23_Script498); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred23_Script502);
        e_init=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred23_Script504); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred23_Script508);
        e_cond=expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred23_Script510); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred23_Script514);
        e_inc=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,RIGHT_P,FOLLOW_RIGHT_P_in_synpred23_Script516); if (state.failed) return ;
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:327:81: ( NEWLINE )?
        int alt26=2;
        int LA26_0 = input.LA(1);

        if ( (LA26_0==NEWLINE) ) {
            int LA26_1 = input.LA(2);

            if ( ((LA26_1>=LEFT_CB && LA26_1<=NEWLINE)||(LA26_1>=BREAK && LA26_1<=ID)||(LA26_1>=PLUS_PLUS && LA26_1<=LEFT_P)||(LA26_1>=WHILE && LA26_1<=FOR)||LA26_1==MINUS||LA26_1==LEFT_B||(LA26_1>=NUM && LA26_1<=STRING_LITERAL)) ) {
                alt26=1;
            }
        }
        switch (alt26) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred23_Script518); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred23_Script523);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_Script

    // $ANTLR start synpred24_Script
    public final void synpred24_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:334:41: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:334:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred24_Script545); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_Script

    // $ANTLR start synpred25_Script
    public final void synpred25_Script_fragment() throws RecognitionException {   
        ScriptParser.range_return b = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:381:8: ( ARROW b= range )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:381:8: ARROW b= range
        {
        match(input,ARROW,FOLLOW_ARROW_in_synpred25_Script575); if (state.failed) return ;
        pushFollow(FOLLOW_range_in_synpred25_Script579);
        b=range();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_Script

    // $ANTLR start synpred43_Script
    public final void synpred43_Script_fragment() throws RecognitionException {   
        ScriptParser.args_return b = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:490:8: ( COMMA b= args )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:490:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred43_Script932); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred43_Script936);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred43_Script

    // $ANTLR start synpred58_Script
    public final void synpred58_Script_fragment() throws RecognitionException {   
        ScriptParser.dictionary_elements_return d = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:8: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
        {
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:8: ( NEWLINE )?
        int alt28=2;
        int LA28_0 = input.LA(1);

        if ( (LA28_0==NEWLINE) ) {
            alt28=1;
        }
        switch (alt28) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred58_Script1237); if (state.failed) return ;

                }
                break;

        }

        match(input,COMMA,FOLLOW_COMMA_in_synpred58_Script1240); if (state.failed) return ;
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:583:23: ( NEWLINE )?
        int alt29=2;
        int LA29_0 = input.LA(1);

        if ( (LA29_0==NEWLINE) ) {
            alt29=1;
        }
        switch (alt29) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred58_Script1242); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_dictionary_elements_in_synpred58_Script1247);
        d=dictionary_elements();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred58_Script

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
    public final boolean synpred58_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_Script_fragment(); // can never throw exception
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
    public final boolean synpred25_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_Script_fragment(); // can never throw exception
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
    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA1_eotS =
        "\23\uffff";
    static final String DFA1_eofS =
        "\23\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\21\uffff";
    static final String DFA1_maxS =
        "\1\47\1\0\21\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\17\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\21\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\1\2\1\uffff\4\2",
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
            return "199:13: ( NEWLINE )?";
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
                        if ( (synpred1_Script()) ) {s = 18;}

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
        "\23\uffff";
    static final String DFA4_eofS =
        "\23\uffff";
    static final String DFA4_minS =
        "\1\4\7\uffff\1\0\12\uffff";
    static final String DFA4_maxS =
        "\1\47\7\uffff\1\0\12\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\14\uffff\1\2\1\4\1\5\1\6\1\3";
    static final String DFA4_specialS =
        "\10\uffff\1\0\12\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\10\1\16\1\uffff\3\1\1\uffff\2\1\1\17\1\1\2\uffff\1\20\1\21"+
            "\3\uffff\1\1\13\uffff\1\1\1\uffff\4\1",
            "",
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
            return "216:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Script()) ) {s = 1;}

                        else if ( (synpred6_Script()) ) {s = 18;}

                         
                        input.seek(index4_8);
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
        "\30\uffff";
    static final String DFA5_eofS =
        "\30\uffff";
    static final String DFA5_minS =
        "\1\4\1\uffff\1\5\2\11\2\uffff\1\11\2\0\1\uffff\2\0\3\43\4\uffff"+
        "\3\5\1\uffff";
    static final String DFA5_maxS =
        "\1\47\1\uffff\1\42\2\11\2\uffff\1\47\2\0\1\uffff\2\0\3\43\4\uffff"+
        "\3\40\1\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\3\uffff\1\2\1\3\3\uffff\1\4\5\uffff\1\6\1\10\1\7\1"+
        "\11\3\uffff\1\5";
    static final String DFA5_specialS =
        "\10\uffff\1\3\1\0\1\uffff\1\2\1\1\13\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1\2\uffff\1\5\1\6\1\2\1\uffff\1\3\1\4\1\uffff\1\1\7\uffff"+
            "\1\1\13\uffff\1\1\1\uffff\4\1",
            "",
            "\1\1\4\uffff\1\12\1\10\1\11\1\uffff\2\1\3\uffff\1\1\1\uffff"+
            "\14\1\1\uffff\1\7",
            "\1\13",
            "\1\14",
            "",
            "",
            "\1\15\32\uffff\1\16\2\uffff\1\17",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\24",
            "\1\25",
            "\1\26",
            "",
            "",
            "",
            "",
            "\1\1\4\uffff\1\27\4\uffff\1\1\3\uffff\1\1\1\uffff\14\1",
            "\1\1\4\uffff\1\27\4\uffff\1\1\3\uffff\1\1\1\uffff\14\1",
            "\1\1\4\uffff\1\27\4\uffff\1\1\3\uffff\1\1\1\uffff\14\1",
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
            return "236:1: pre_stat returns [Expression expr] : ( expression | BREAK | CONTINUE | ID EQUAL expression | array_element_reference EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_9 = input.LA(1);

                         
                        int index5_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred16_Script()) ) {s = 17;}

                         
                        input.seek(index5_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_12 = input.LA(1);

                         
                        int index5_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (true) ) {s = 19;}

                         
                        input.seek(index5_12);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_11 = input.LA(1);

                         
                        int index5_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred15_Script()) ) {s = 18;}

                         
                        input.seek(index5_11);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_8 = input.LA(1);

                         
                        int index5_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_Script()) ) {s = 1;}

                        else if ( (synpred14_Script()) ) {s = 16;}

                         
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
        "\23\uffff";
    static final String DFA6_eofS =
        "\23\uffff";
    static final String DFA6_minS =
        "\1\4\1\0\21\uffff";
    static final String DFA6_maxS =
        "\1\47\1\0\21\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\17\uffff\1\1";
    static final String DFA6_specialS =
        "\1\uffff\1\0\21\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\1\2\1\uffff\4\2",
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
            return "285:38: ( NEWLINE )?";
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
                        if ( (synpred17_Script()) ) {s = 18;}

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
        "\23\uffff";
    static final String DFA8_eofS =
        "\23\uffff";
    static final String DFA8_minS =
        "\1\4\1\0\21\uffff";
    static final String DFA8_maxS =
        "\1\47\1\0\21\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\17\uffff\1\1";
    static final String DFA8_specialS =
        "\1\uffff\1\0\21\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\1\2\1\uffff\4\2",
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
            return "289:22: ( NEWLINE )?";
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
                        if ( (synpred19_Script()) ) {s = 18;}

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
        "\23\uffff";
    static final String DFA10_eofS =
        "\23\uffff";
    static final String DFA10_minS =
        "\1\4\1\0\21\uffff";
    static final String DFA10_maxS =
        "\1\47\1\0\21\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\17\uffff\1\1";
    static final String DFA10_specialS =
        "\1\uffff\1\0\21\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\1\2\1\uffff\4\2",
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
            return "307:41: ( NEWLINE )?";
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
                        if ( (synpred21_Script()) ) {s = 18;}

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
        "\23\uffff";
    static final String DFA11_eofS =
        "\23\uffff";
    static final String DFA11_minS =
        "\1\4\1\0\21\uffff";
    static final String DFA11_maxS =
        "\1\47\1\0\21\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\17\uffff\1\1";
    static final String DFA11_specialS =
        "\1\uffff\1\0\21\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\1\2\1\uffff\4\2",
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
            return "327:81: ( NEWLINE )?";
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
                        if ( (synpred22_Script()) ) {s = 18;}

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
        "\23\uffff";
    static final String DFA12_eofS =
        "\23\uffff";
    static final String DFA12_minS =
        "\1\4\1\0\21\uffff";
    static final String DFA12_maxS =
        "\1\47\1\0\21\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\17\uffff\1\1";
    static final String DFA12_specialS =
        "\1\uffff\1\0\21\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\13"+
            "\uffff\1\2\1\uffff\4\2",
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
            return "334:41: ( NEWLINE )?";
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
                        if ( (synpred24_Script()) ) {s = 18;}

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
    static final String DFA21_eotS =
        "\15\uffff";
    static final String DFA21_eofS =
        "\5\uffff\1\14\7\uffff";
    static final String DFA21_minS =
        "\1\4\4\uffff\1\5\7\uffff";
    static final String DFA21_maxS =
        "\1\47\4\uffff\1\50\7\uffff";
    static final String DFA21_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\uffff\1\6\1\7\1\10\1\11\1\12\1\13\1\5";
    static final String DFA21_specialS =
        "\15\uffff}>";
    static final String[] DFA21_transitionS = {
            "\1\10\4\uffff\1\5\4\uffff\1\4\7\uffff\1\2\13\uffff\1\11\1\uffff"+
            "\1\1\1\3\1\6\1\7",
            "",
            "",
            "",
            "",
            "\2\14\7\uffff\1\12\1\14\3\uffff\17\14\1\13\1\14\4\uffff\1\14",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "517:1: atom returns [Object value] : ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | CONSTANT | string_literal | dictionary | array | function_call | array_element_reference );";
        }
    }
 

    public static final BitSet FOLLOW_stats_in_prog81 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_block98 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_block100 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stats_in_block103 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block105 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats127 = new BitSet(new long[]{0x000000F400467BB2L});
    public static final BitSet FOLLOW_stat_in_stats134 = new BitSet(new long[]{0x000000F400467BB2L});
    public static final BitSet FOLLOW_pre_stat_in_stat159 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_expression_in_stat201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_expression_in_stat211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_pre_stat228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_pre_stat238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONTINUE_in_pre_stat248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat258 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat260 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_pre_stat262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_element_reference_in_pre_stat272 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat274 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_pre_stat276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat286 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat298 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_stat300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat310 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat322 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_stat324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression373 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression375 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression379 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression381 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression383 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression388 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression392 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression396 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression398 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_while_expression_in_while_expression425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_pre_while_expression443 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_while_expression445 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_pre_while_expression449 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_while_expression451 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_while_expression453 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_while_expression458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_for_expression_in_for_expression478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression496 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression498 = new BitSet(new long[]{0x000000F400405B90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression502 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression504 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_pre_for_expression508 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression510 = new BitSet(new long[]{0x000000F400405B90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression514 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression516 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression518 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression533 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression535 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_for_expression537 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_for_expression539 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_range_in_pre_for_expression541 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression543 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression545 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_range570 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ARROW_in_range575 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_range_in_range579 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_terms_in_expression600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms620 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_PLUS_in_terms626 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_term_in_terms630 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_MINUS_in_terms642 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_term_in_terms646 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_AND_in_terms658 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_term_in_terms662 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_OR_in_terms674 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_term_in_terms678 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_atom_in_term702 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_MULT_in_term708 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term712 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_DIV_in_term724 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term728 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_LT_in_term740 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term744 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_LT_EQ_in_term757 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term761 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_GT_in_term774 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term778 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_GT_EQ_in_term790 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term794 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_EQ_in_term807 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term811 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_NEQ_in_term823 = new BitSet(new long[]{0x000000F400404210L});
    public static final BitSet FOLLOW_atom_in_term827 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_ID_in_term844 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term856 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_term858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term868 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term880 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_term882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_function_call900 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call902 = new BitSet(new long[]{0x000000F40040DA10L});
    public static final BitSet FOLLOW_args_in_function_call904 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args927 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_COMMA_in_args932 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_args_in_args936 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_LEFT_B_in_array958 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_args_in_array962 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_array_element_reference986 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_LEFT_B_in_array_element_reference988 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_array_element_reference992 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array_element_reference994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_array_element_reference1006 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_LEFT_B_in_array_element_reference1008 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_NUM_in_array_element_reference1012 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array_element_reference1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_array_element_reference1026 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_LEFT_B_in_array_element_reference1028 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_string_literal_in_array_element_reference1032 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array_element_reference1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_atom1052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_atom1062 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_NUM_in_atom1064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom1074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom1084 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_atom1086 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom1088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom1098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_in_atom1108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom1118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dictionary_in_atom1128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_in_atom1138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_atom1148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_element_reference_in_atom1158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal1181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_dictionary1200 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary1202 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_dictionary1204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1225 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_TP_in_dictionary_elements1227 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1231 = new BitSet(new long[]{0x0000000200000022L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1237 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_COMMA_in_dictionary_elements1240 = new BitSet(new long[]{0x000000F400405A30L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1242 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary_elements1247 = new BitSet(new long[]{0x0000000200000022L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Script100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Script134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_stat_in_synpred4_Script159 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred4_Script161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_synpred6_Script181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred9_Script228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred14_Script286 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred14_Script288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred15_Script298 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_synpred15_Script300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred16_Script310 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_synpred16_Script312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred17_Script383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred18_Script392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred19_Script398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred20_Script396 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred20_Script398 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stat_in_synpred20_Script403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred21_Script453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred22_Script518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_synpred23_Script496 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_synpred23_Script498 = new BitSet(new long[]{0x000000F400405B90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred23_Script502 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_synpred23_Script504 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_expression_in_synpred23_Script508 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_synpred23_Script510 = new BitSet(new long[]{0x000000F400405B90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred23_Script514 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_synpred23_Script516 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred23_Script518 = new BitSet(new long[]{0x000000F400467BB0L});
    public static final BitSet FOLLOW_stat_in_synpred23_Script523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred24_Script545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARROW_in_synpred25_Script575 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_range_in_synpred25_Script579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred43_Script932 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_args_in_synpred43_Script936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred58_Script1237 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred58_Script1240 = new BitSet(new long[]{0x000000F400405A30L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred58_Script1242 = new BitSet(new long[]{0x000000F400405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_synpred58_Script1247 = new BitSet(new long[]{0x0000000000000002L});

}