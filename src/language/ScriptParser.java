// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g 2011-11-17 19:13:41

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFT_CB", "NEWLINE", "RIGHT_CB", "BREAK", "CONTINUE", "ID", "EQUAL", "PLUS_PLUS", "MINUS_MINUS", "IF", "LEFT_P", "RIGHT_P", "ELSE", "WHILE", "FOR", "PV", "ARROW", "PLUS", "MINUS", "AND", "OR", "MULT", "DIV", "CMP_LT", "CMP_LT_EQ", "CMP_GT", "CMP_GT_EQ", "CMP_EQ", "CMP_NEQ", "FUNCTION", "COMMA", "LEFT_B", "RIGHT_B", "NUM", "BOOL", "CONSTANT", "STRING_LITERAL", "TP", "DQUOTE", "LINE_COMMENT", "BLOCK_COMMENT", "WS"
    };
    public static final int FUNCTION=33;
    public static final int WHILE=17;
    public static final int FOR=18;
    public static final int DQUOTE=42;
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
    public static final int RIGHT_B=36;
    public static final int STRING_LITERAL=40;
    public static final int PLUS_PLUS=11;
    public static final int CONTINUE=8;
    public static final int COMMA=34;
    public static final int CMP_LT_EQ=28;
    public static final int EQUAL=10;
    public static final int LEFT_B=35;
    public static final int BLOCK_COMMENT=44;
    public static final int PV=19;
    public static final int PLUS=21;
    public static final int LEFT_P=14;
    public static final int CMP_NEQ=32;
    public static final int LINE_COMMENT=43;
    public static final int ELSE=16;
    public static final int BOOL=38;
    public static final int TP=41;
    public static final int RIGHT_P=15;
    public static final int MINUS=22;
    public static final int MULT=25;
    public static final int CMP_GT=29;
    public static final int NUM=37;
    public static final int WS=45;
    public static final int NEWLINE=5;
    public static final int OR=24;
    public static final int CONSTANT=39;
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
            this.state.ruleMemo = new HashMap[85+1];
             
             
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

        public boolean __DEBUG__ = false;

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
            if (__DEBUG__) {
                System.out.println("\nTREE REFACTORING...");
            }
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
            if (__DEBUG__) {
                System.out.println(" OVER");
            }
        }

        public boolean compilationCheck() throws PanicException, CompilationErrorException {
            try {
                compilation_env.clear();
                if (__DEBUG__) {
                    System.out.println("\nCOMPILATION CHECK...");
                }
                for (Object o : commands) {
                    if (!(o instanceof Expression)) {
                        scriptPanic("Command must be an instance of Expression [" + o.getClass() + "]", line_number);
                    }
                    ((Expression) o).compilationCheck();
                }
                compilation_env.clear();
                if (__DEBUG__) {
                    System.out.println(" OK");
                }
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
            if (__DEBUG__) {
                dumpGlobalMemory(System.err);
            }
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
            if (__DEBUG__) {
                dumpGlobalMemory(System.err);
            }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:203:1: prog : s= stats ;
    public final ScriptParser.prog_return prog() throws RecognitionException {
        ScriptParser.prog_return retval = new ScriptParser.prog_return();
        retval.start = input.LT(1);
        int prog_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stats_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:203:6: (s= stats )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:204:5: s= stats
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:211:1: block returns [LinkedList<Expression> expressions] : LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB ;
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:211:51: ( LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:212:5: LEFT_CB ( NEWLINE )? stats ( NEWLINE )? RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB1=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_block98); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB1_tree = (Object)adaptor.create(LEFT_CB1);
            adaptor.addChild(root_0, LEFT_CB1_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:212:13: ( NEWLINE )?
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:212:28: ( NEWLINE )?
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:216:1: stats returns [LinkedList<Expression> expressions] : s= stat (s= stat )* ;
    public final ScriptParser.stats_return stats() throws RecognitionException {
        ScriptParser.stats_return retval = new ScriptParser.stats_return();
        retval.start = input.LT(1);
        int stats_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.stat_return s = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:216:51: (s= stat (s= stat )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:217:5: s= stat (s= stat )*
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:222:7: (s= stat )*
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
                else if ( (LA3_0==LEFT_CB||(LA3_0>=BREAK && LA3_0<=ID)||(LA3_0>=PLUS_PLUS && LA3_0<=LEFT_P)||(LA3_0>=WHILE && LA3_0<=FOR)||LA3_0==MINUS||LA3_0==FUNCTION||LA3_0==LEFT_B||(LA3_0>=NUM && LA3_0<=STRING_LITERAL)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:222:8: s= stat
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:229:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression | function_declaration );
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

        ScriptParser.function_declaration_return function_declaration13 = null;


        Object NEWLINE7_tree=null;
        Object NEWLINE8_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:230:5: ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression | function_declaration )
            int alt4=7;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:230:7: pre_stat NEWLINE
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:234:7: NEWLINE
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:237:7: block
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:240:7: if_expression
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:243:7: while_expression
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:246:7: for_expression
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
                case 7 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:249:7: function_declaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_declaration_in_stat221);
                    function_declaration13=function_declaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_declaration13.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, (function_declaration13!=null?function_declaration13.expr:null));
                          
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:252:1: pre_stat returns [Expression expr] : ( expression | BREAK | CONTINUE | ID EQUAL expression | array_element_reference EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.pre_stat_return pre_stat() throws RecognitionException {
        ScriptParser.pre_stat_return retval = new ScriptParser.pre_stat_return();
        retval.start = input.LT(1);
        int pre_stat_StartIndex = input.index();
        Object root_0 = null;

        Token BREAK15=null;
        Token CONTINUE16=null;
        Token ID17=null;
        Token EQUAL18=null;
        Token EQUAL21=null;
        Token ID23=null;
        Token PLUS_PLUS24=null;
        Token PLUS_PLUS25=null;
        Token ID26=null;
        Token ID27=null;
        Token MINUS_MINUS28=null;
        Token MINUS_MINUS29=null;
        Token ID30=null;
        ScriptParser.expression_return expression14 = null;

        ScriptParser.expression_return expression19 = null;

        ScriptParser.array_element_reference_return array_element_reference20 = null;

        ScriptParser.expression_return expression22 = null;


        Object BREAK15_tree=null;
        Object CONTINUE16_tree=null;
        Object ID17_tree=null;
        Object EQUAL18_tree=null;
        Object EQUAL21_tree=null;
        Object ID23_tree=null;
        Object PLUS_PLUS24_tree=null;
        Object PLUS_PLUS25_tree=null;
        Object ID26_tree=null;
        Object ID27_tree=null;
        Object MINUS_MINUS28_tree=null;
        Object MINUS_MINUS29_tree=null;
        Object ID30_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:253:5: ( expression | BREAK | CONTINUE | ID EQUAL expression | array_element_reference EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
            int alt5=9;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:253:7: expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_pre_stat238);
                    expression14=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression14.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, (expression14!=null?expression14.expr:null));
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:256:7: BREAK
                    {
                    root_0 = (Object)adaptor.nil();

                    BREAK15=(Token)match(input,BREAK,FOLLOW_BREAK_in_pre_stat248); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BREAK15_tree = (Object)adaptor.create(BREAK15);
                    adaptor.addChild(root_0, BREAK15_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, ReturnValue.RETURN_BREAK);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:259:7: CONTINUE
                    {
                    root_0 = (Object)adaptor.nil();

                    CONTINUE16=(Token)match(input,CONTINUE,FOLLOW_CONTINUE_in_pre_stat258); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    CONTINUE16_tree = (Object)adaptor.create(CONTINUE16);
                    adaptor.addChild(root_0, CONTINUE16_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, ReturnValue.RETURN_CONTINUE);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:262:7: ID EQUAL expression
                    {
                    root_0 = (Object)adaptor.nil();

                    ID17=(Token)match(input,ID,FOLLOW_ID_in_pre_stat268); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID17_tree = (Object)adaptor.create(ID17);
                    adaptor.addChild(root_0, ID17_tree);
                    }
                    EQUAL18=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_stat270); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL18_tree = (Object)adaptor.create(EQUAL18);
                    adaptor.addChild(root_0, EQUAL18_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_stat272);
                    expression19=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression19.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new VariableAssignment(this, (ID17!=null?ID17.getText():null), (expression19!=null?expression19.expr:null)) );
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:265:7: array_element_reference EQUAL expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_array_element_reference_in_pre_stat282);
                    array_element_reference20=array_element_reference();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, array_element_reference20.getTree());
                    EQUAL21=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_stat284); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL21_tree = (Object)adaptor.create(EQUAL21);
                    adaptor.addChild(root_0, EQUAL21_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_stat286);
                    expression22=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression22.getTree());
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new StorageAccessor(this, StorageAccessor.ASSIGNMENT, (array_element_reference20!=null?array_element_reference20.accessor:null), (expression22!=null?expression22.expr:null)));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:268:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID23=(Token)match(input,ID,FOLLOW_ID_in_pre_stat296); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID23_tree = (Object)adaptor.create(ID23);
                    adaptor.addChild(root_0, ID23_tree);
                    }
                    PLUS_PLUS24=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat298); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS24_tree = (Object)adaptor.create(PLUS_PLUS24);
                    adaptor.addChild(root_0, PLUS_PLUS24_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this, new VariableAssignment(this, (ID23!=null?ID23.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 7 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:271:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS25=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_pre_stat308); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS25_tree = (Object)adaptor.create(PLUS_PLUS25);
                    adaptor.addChild(root_0, PLUS_PLUS25_tree);
                    }
                    ID26=(Token)match(input,ID,FOLLOW_ID_in_pre_stat310); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID26_tree = (Object)adaptor.create(ID26);
                    adaptor.addChild(root_0, ID26_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID26!=null?ID26.getText():null), Operator.OPERATOR_PLUS_PLUS));
                          
                    }

                    }
                    break;
                case 8 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:274:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID27=(Token)match(input,ID,FOLLOW_ID_in_pre_stat320); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID27_tree = (Object)adaptor.create(ID27);
                    adaptor.addChild(root_0, ID27_tree);
                    }
                    MINUS_MINUS28=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat322); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS28_tree = (Object)adaptor.create(MINUS_MINUS28);
                    adaptor.addChild(root_0, MINUS_MINUS28_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID27!=null?ID27.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
                    }

                    }
                    break;
                case 9 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:277:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS29=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_pre_stat332); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS29_tree = (Object)adaptor.create(MINUS_MINUS29);
                    adaptor.addChild(root_0, MINUS_MINUS29_tree);
                    }
                    ID30=(Token)match(input,ID,FOLLOW_ID_in_pre_stat334); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID30_tree = (Object)adaptor.create(ID30);
                    adaptor.addChild(root_0, ID30_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.expr = new Expression(true, this,  new VariableAssignment(this, (ID30!=null?ID30.getText():null), Operator.OPERATOR_MINUS_MINUS));
                          
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:283:1: if_expression returns [IfExpression expr] : p= pre_if_expression ;
    public final ScriptParser.if_expression_return if_expression() throws RecognitionException {
        ScriptParser.if_expression_return retval = new ScriptParser.if_expression_return();
        retval.start = input.LT(1);
        int if_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_if_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:284:5: (p= pre_if_expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:284:7: p= pre_if_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_if_expression_in_if_expression364);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:300:1: pre_if_expression returns [LinkedList<Expression> exprs] : IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? ;
    public final ScriptParser.pre_if_expression_return pre_if_expression() throws RecognitionException {
        ScriptParser.pre_if_expression_return retval = new ScriptParser.pre_if_expression_return();
        retval.start = input.LT(1);
        int pre_if_expression_StartIndex = input.index();
        Object root_0 = null;

        Token IF31=null;
        Token LEFT_P32=null;
        Token RIGHT_P33=null;
        Token NEWLINE34=null;
        Token NEWLINE35=null;
        Token ELSE36=null;
        Token NEWLINE37=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object IF31_tree=null;
        Object LEFT_P32_tree=null;
        Object RIGHT_P33_tree=null;
        Object NEWLINE34_tree=null;
        Object NEWLINE35_tree=null;
        Object ELSE36_tree=null;
        Object NEWLINE37_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:301:5: ( IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )? )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:301:7: IF LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ( NEWLINE )? ( ELSE ( NEWLINE )? s= stat )?
            {
            root_0 = (Object)adaptor.nil();

            IF31=(Token)match(input,IF,FOLLOW_IF_in_pre_if_expression383); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF31_tree = (Object)adaptor.create(IF31);
            adaptor.addChild(root_0, IF31_tree);
            }
            LEFT_P32=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_if_expression385); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P32_tree = (Object)adaptor.create(LEFT_P32);
            adaptor.addChild(root_0, LEFT_P32_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_if_expression389);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P33=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_if_expression391); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P33_tree = (Object)adaptor.create(RIGHT_P33);
            adaptor.addChild(root_0, RIGHT_P33_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:301:38: ( NEWLINE )?
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE34=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression393); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE34_tree = (Object)adaptor.create(NEWLINE34);
                    adaptor.addChild(root_0, NEWLINE34_tree);
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

                      retval.exprs = new LinkedList();
                      retval.exprs.add( (e!=null?e.expr:null) );
                      retval.exprs.add( (s!=null?s.expr:null) );
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:7: ( NEWLINE )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NEWLINE) ) {
                int LA7_1 = input.LA(2);

                if ( (synpred19_Script()) ) {
                    alt7=1;
                }
            }
            switch (alt7) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE35=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression402); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE35_tree = (Object)adaptor.create(NEWLINE35);
                    adaptor.addChild(root_0, NEWLINE35_tree);
                    }

                    }
                    break;

            }

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:16: ( ELSE ( NEWLINE )? s= stat )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==ELSE) ) {
                int LA9_1 = input.LA(2);

                if ( (synpred21_Script()) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:17: ELSE ( NEWLINE )? s= stat
                    {
                    ELSE36=(Token)match(input,ELSE,FOLLOW_ELSE_in_pre_if_expression406); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE36_tree = (Object)adaptor.create(ELSE36);
                    adaptor.addChild(root_0, ELSE36_tree);
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:22: ( NEWLINE )?
                    int alt8=2;
                    alt8 = dfa8.predict(input);
                    switch (alt8) {
                        case 1 :
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE37=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_if_expression408); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE37_tree = (Object)adaptor.create(NEWLINE37);
                            adaptor.addChild(root_0, NEWLINE37_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_if_expression413);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:309:1: while_expression returns [LoopExpression expr] : p= pre_while_expression ;
    public final ScriptParser.while_expression_return while_expression() throws RecognitionException {
        ScriptParser.while_expression_return retval = new ScriptParser.while_expression_return();
        retval.start = input.LT(1);
        int while_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_while_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:310:5: (p= pre_while_expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:310:7: p= pre_while_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_while_expression_in_while_expression435);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:322:1: pre_while_expression returns [LinkedList<Expression> exprs] : WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat ;
    public final ScriptParser.pre_while_expression_return pre_while_expression() throws RecognitionException {
        ScriptParser.pre_while_expression_return retval = new ScriptParser.pre_while_expression_return();
        retval.start = input.LT(1);
        int pre_while_expression_StartIndex = input.index();
        Object root_0 = null;

        Token WHILE38=null;
        Token LEFT_P39=null;
        Token RIGHT_P40=null;
        Token NEWLINE41=null;
        ScriptParser.expression_return e = null;

        ScriptParser.stat_return s = null;


        Object WHILE38_tree=null;
        Object LEFT_P39_tree=null;
        Object RIGHT_P40_tree=null;
        Object NEWLINE41_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:323:5: ( WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:323:7: WHILE LEFT_P e= expression RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            WHILE38=(Token)match(input,WHILE,FOLLOW_WHILE_in_pre_while_expression453); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE38_tree = (Object)adaptor.create(WHILE38);
            adaptor.addChild(root_0, WHILE38_tree);
            }
            LEFT_P39=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_while_expression455); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P39_tree = (Object)adaptor.create(LEFT_P39);
            adaptor.addChild(root_0, LEFT_P39_tree);
            }
            pushFollow(FOLLOW_expression_in_pre_while_expression459);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RIGHT_P40=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_while_expression461); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P40_tree = (Object)adaptor.create(RIGHT_P40);
            adaptor.addChild(root_0, RIGHT_P40_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:323:41: ( NEWLINE )?
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE41=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_while_expression463); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE41_tree = (Object)adaptor.create(NEWLINE41);
                    adaptor.addChild(root_0, NEWLINE41_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_pre_while_expression468);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:329:1: for_expression returns [LoopExpression expr] : p= pre_for_expression ;
    public final ScriptParser.for_expression_return for_expression() throws RecognitionException {
        ScriptParser.for_expression_return retval = new ScriptParser.for_expression_return();
        retval.start = input.LT(1);
        int for_expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.pre_for_expression_return p = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:330:5: (p= pre_for_expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:330:7: p= pre_for_expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_pre_for_expression_in_for_expression488);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:342:1: pre_for_expression returns [LinkedList<Expression> exprs] : ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat );
    public final ScriptParser.pre_for_expression_return pre_for_expression() throws RecognitionException {
        ScriptParser.pre_for_expression_return retval = new ScriptParser.pre_for_expression_return();
        retval.start = input.LT(1);
        int pre_for_expression_StartIndex = input.index();
        Object root_0 = null;

        Token FOR42=null;
        Token LEFT_P43=null;
        Token PV44=null;
        Token PV45=null;
        Token RIGHT_P46=null;
        Token NEWLINE47=null;
        Token FOR48=null;
        Token LEFT_P49=null;
        Token ID50=null;
        Token EQUAL51=null;
        Token RIGHT_P53=null;
        Token NEWLINE54=null;
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;

        ScriptParser.range_return range52 = null;


        Object FOR42_tree=null;
        Object LEFT_P43_tree=null;
        Object PV44_tree=null;
        Object PV45_tree=null;
        Object RIGHT_P46_tree=null;
        Object NEWLINE47_tree=null;
        Object FOR48_tree=null;
        Object LEFT_P49_tree=null;
        Object ID50_tree=null;
        Object EQUAL51_tree=null;
        Object RIGHT_P53_tree=null;
        Object NEWLINE54_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:5: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat | FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==FOR) ) {
                int LA13_1 = input.LA(2);

                if ( (synpred24_Script()) ) {
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR42=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression506); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR42_tree = (Object)adaptor.create(FOR42);
                    adaptor.addChild(root_0, FOR42_tree);
                    }
                    LEFT_P43=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression508); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P43_tree = (Object)adaptor.create(LEFT_P43);
                    adaptor.addChild(root_0, LEFT_P43_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression512);
                    e_init=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_init.getTree());
                    PV44=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression514); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV44_tree = (Object)adaptor.create(PV44);
                    adaptor.addChild(root_0, PV44_tree);
                    }
                    pushFollow(FOLLOW_expression_in_pre_for_expression518);
                    e_cond=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_cond.getTree());
                    PV45=(Token)match(input,PV,FOLLOW_PV_in_pre_for_expression520); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PV45_tree = (Object)adaptor.create(PV45);
                    adaptor.addChild(root_0, PV45_tree);
                    }
                    pushFollow(FOLLOW_pre_stat_in_pre_for_expression524);
                    e_inc=pre_stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e_inc.getTree());
                    RIGHT_P46=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression526); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P46_tree = (Object)adaptor.create(RIGHT_P46);
                    adaptor.addChild(root_0, RIGHT_P46_tree);
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:81: ( NEWLINE )?
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE47=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression528); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE47_tree = (Object)adaptor.create(NEWLINE47);
                            adaptor.addChild(root_0, NEWLINE47_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression533);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:350:7: FOR LEFT_P ID EQUAL range RIGHT_P ( NEWLINE )? s= stat
                    {
                    root_0 = (Object)adaptor.nil();

                    FOR48=(Token)match(input,FOR,FOLLOW_FOR_in_pre_for_expression543); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FOR48_tree = (Object)adaptor.create(FOR48);
                    adaptor.addChild(root_0, FOR48_tree);
                    }
                    LEFT_P49=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_pre_for_expression545); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P49_tree = (Object)adaptor.create(LEFT_P49);
                    adaptor.addChild(root_0, LEFT_P49_tree);
                    }
                    ID50=(Token)match(input,ID,FOLLOW_ID_in_pre_for_expression547); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID50_tree = (Object)adaptor.create(ID50);
                    adaptor.addChild(root_0, ID50_tree);
                    }
                    EQUAL51=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_pre_for_expression549); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL51_tree = (Object)adaptor.create(EQUAL51);
                    adaptor.addChild(root_0, EQUAL51_tree);
                    }
                    pushFollow(FOLLOW_range_in_pre_for_expression551);
                    range52=range();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, range52.getTree());
                    RIGHT_P53=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_pre_for_expression553); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P53_tree = (Object)adaptor.create(RIGHT_P53);
                    adaptor.addChild(root_0, RIGHT_P53_tree);
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:350:41: ( NEWLINE )?
                    int alt12=2;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                            {
                            NEWLINE54=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_pre_for_expression555); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NEWLINE54_tree = (Object)adaptor.create(NEWLINE54);
                            adaptor.addChild(root_0, NEWLINE54_tree);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_stat_in_pre_for_expression560);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    if ( state.backtracking==0 ) {

                              retval.exprs = new LinkedList();

                              boolean plus_minus = true;

                              Calculable init = (range52!=null?range52.range_ele:null).get(0);
                              Calculable increment = (range52!=null?range52.range_ele:null).size()==3 ? (range52!=null?range52.range_ele:null).get(1) : new Numeric(1.0f);
                              Calculable condition = (range52!=null?range52.range_ele:null).get((range52!=null?range52.range_ele:null).size()-1);

                              try {
                                  if (increment.getSimplifiedCalculable() instanceof Numeric) {
                                      double val = (Double) ((Numeric) increment.getSimplifiedCalculable()).getNativeValue();
                                      plus_minus = val>=0;
                                  }
                              } catch (Exception e) {
                                  //NOTHING
                              }

                              VariableAssignment va = new VariableAssignment(this, (ID50!=null?ID50.getText():null), init);
                              Expression init_expr = new Expression(true, this, va);
                              
                              LinkedList<Object> term_ele = new LinkedList();
                              term_ele.add(new Variable(this, (ID50!=null?ID50.getText():null)));
                              term_ele.add(Operator.OPERATOR_PLUS);
                              term_ele.add(increment);
                              Term t = new Term(this, term_ele);
                              VariableAssignment vai = new VariableAssignment(this, (ID50!=null?ID50.getText():null), t);
                              Expression increment_expr = new Expression(true, this, vai);

                              LinkedList<Object> term_elec = new LinkedList();
                              term_elec.add(new Variable(this, (ID50!=null?ID50.getText():null)));
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:393:1: range returns [LinkedList<Calculable> range_ele] : a= expression ( ARROW b= range )* ;
    public final ScriptParser.range_return range() throws RecognitionException {
        ScriptParser.range_return retval = new ScriptParser.range_return();
        retval.start = input.LT(1);
        int range_StartIndex = input.index();
        Object root_0 = null;

        Token ARROW55=null;
        ScriptParser.expression_return a = null;

        ScriptParser.range_return b = null;


        Object ARROW55_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:394:5: (a= expression ( ARROW b= range )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:394:7: a= expression ( ARROW b= range )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_range580);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.range_ele = new LinkedList();
                      retval.range_ele.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:397:7: ( ARROW b= range )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==ARROW) ) {
                    int LA14_2 = input.LA(2);

                    if ( (synpred26_Script()) ) {
                        alt14=1;
                    }


                }


                switch (alt14) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:397:8: ARROW b= range
            	    {
            	    ARROW55=(Token)match(input,ARROW,FOLLOW_ARROW_in_range585); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    ARROW55_tree = (Object)adaptor.create(ARROW55);
            	    adaptor.addChild(root_0, ARROW55_tree);
            	    }
            	    pushFollow(FOLLOW_range_in_range589);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:404:1: expression returns [Expression expr] : terms ;
    public final ScriptParser.expression_return expression() throws RecognitionException {
        ScriptParser.expression_return retval = new ScriptParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        ScriptParser.terms_return terms56 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:405:5: ( terms )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:405:7: terms
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_terms_in_expression610);
            terms56=terms();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, terms56.getTree());
            if ( state.backtracking==0 ) {

                      retval.expr = new Expression( this, new Term(this, (terms56!=null?terms56.terms:null)) );
                  
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:409:1: terms returns [LinkedList<Object> terms] : t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* ;
    public final ScriptParser.terms_return terms() throws RecognitionException {
        ScriptParser.terms_return retval = new ScriptParser.terms_return();
        retval.start = input.LT(1);
        int terms_StartIndex = input.index();
        Object root_0 = null;

        Token PLUS57=null;
        Token MINUS58=null;
        Token AND59=null;
        Token OR60=null;
        ScriptParser.term_return t = null;


        Object PLUS57_tree=null;
        Object MINUS58_tree=null;
        Object AND59_tree=null;
        Object OR60_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:410:5: (t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:410:7: t= term ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_term_in_terms630);
            t=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {

                      retval.terms = new LinkedList();
                      retval.terms.add( new Term(this, (t!=null?t.atoms:null)) );
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:413:7: ( PLUS t= term | MINUS t= term | AND t= term | OR t= term )*
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:413:9: PLUS t= term
            	    {
            	    PLUS57=(Token)match(input,PLUS,FOLLOW_PLUS_in_terms636); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    PLUS57_tree = (Object)adaptor.create(PLUS57);
            	    adaptor.addChild(root_0, PLUS57_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms640);
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:417:9: MINUS t= term
            	    {
            	    MINUS58=(Token)match(input,MINUS,FOLLOW_MINUS_in_terms652); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    MINUS58_tree = (Object)adaptor.create(MINUS58);
            	    adaptor.addChild(root_0, MINUS58_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms656);
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:421:9: AND t= term
            	    {
            	    AND59=(Token)match(input,AND,FOLLOW_AND_in_terms668); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND59_tree = (Object)adaptor.create(AND59);
            	    adaptor.addChild(root_0, AND59_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms672);
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
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:425:9: OR t= term
            	    {
            	    OR60=(Token)match(input,OR,FOLLOW_OR_in_terms684); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR60_tree = (Object)adaptor.create(OR60);
            	    adaptor.addChild(root_0, OR60_tree);
            	    }
            	    pushFollow(FOLLOW_term_in_terms688);
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:431:1: term returns [LinkedList<Object> atoms] : (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );
    public final ScriptParser.term_return term() throws RecognitionException {
        ScriptParser.term_return retval = new ScriptParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token MULT61=null;
        Token DIV62=null;
        Token CMP_LT63=null;
        Token CMP_LT_EQ64=null;
        Token CMP_GT65=null;
        Token CMP_GT_EQ66=null;
        Token CMP_EQ67=null;
        Token CMP_NEQ68=null;
        Token ID69=null;
        Token PLUS_PLUS70=null;
        Token PLUS_PLUS71=null;
        Token ID72=null;
        Token ID73=null;
        Token MINUS_MINUS74=null;
        Token MINUS_MINUS75=null;
        Token ID76=null;
        ScriptParser.atom_return a = null;


        Object MULT61_tree=null;
        Object DIV62_tree=null;
        Object CMP_LT63_tree=null;
        Object CMP_LT_EQ64_tree=null;
        Object CMP_GT65_tree=null;
        Object CMP_GT_EQ66_tree=null;
        Object CMP_EQ67_tree=null;
        Object CMP_NEQ68_tree=null;
        Object ID69_tree=null;
        Object PLUS_PLUS70_tree=null;
        Object PLUS_PLUS71_tree=null;
        Object ID72_tree=null;
        Object ID73_tree=null;
        Object MINUS_MINUS74_tree=null;
        Object MINUS_MINUS75_tree=null;
        Object ID76_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:432:5: (a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )* | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID )
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:432:7: a= atom ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_term712);
                    a=atom();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              retval.atoms.add((a!=null?a.value:null));
                          
                    }
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:435:7: ( MULT a= atom | DIV a= atom | CMP_LT a= atom | CMP_LT_EQ a= atom | CMP_GT a= atom | CMP_GT_EQ a= atom | CMP_EQ a= atom | CMP_NEQ a= atom )*
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:435:9: MULT a= atom
                    	    {
                    	    MULT61=(Token)match(input,MULT,FOLLOW_MULT_in_term718); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    MULT61_tree = (Object)adaptor.create(MULT61);
                    	    adaptor.addChild(root_0, MULT61_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term722);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:439:9: DIV a= atom
                    	    {
                    	    DIV62=(Token)match(input,DIV,FOLLOW_DIV_in_term734); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DIV62_tree = (Object)adaptor.create(DIV62);
                    	    adaptor.addChild(root_0, DIV62_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term738);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:443:9: CMP_LT a= atom
                    	    {
                    	    CMP_LT63=(Token)match(input,CMP_LT,FOLLOW_CMP_LT_in_term750); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT63_tree = (Object)adaptor.create(CMP_LT63);
                    	    adaptor.addChild(root_0, CMP_LT63_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term754);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:447:9: CMP_LT_EQ a= atom
                    	    {
                    	    CMP_LT_EQ64=(Token)match(input,CMP_LT_EQ,FOLLOW_CMP_LT_EQ_in_term767); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_LT_EQ64_tree = (Object)adaptor.create(CMP_LT_EQ64);
                    	    adaptor.addChild(root_0, CMP_LT_EQ64_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term771);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:451:9: CMP_GT a= atom
                    	    {
                    	    CMP_GT65=(Token)match(input,CMP_GT,FOLLOW_CMP_GT_in_term784); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT65_tree = (Object)adaptor.create(CMP_GT65);
                    	    adaptor.addChild(root_0, CMP_GT65_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term788);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:455:9: CMP_GT_EQ a= atom
                    	    {
                    	    CMP_GT_EQ66=(Token)match(input,CMP_GT_EQ,FOLLOW_CMP_GT_EQ_in_term800); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_GT_EQ66_tree = (Object)adaptor.create(CMP_GT_EQ66);
                    	    adaptor.addChild(root_0, CMP_GT_EQ66_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term804);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:459:9: CMP_EQ a= atom
                    	    {
                    	    CMP_EQ67=(Token)match(input,CMP_EQ,FOLLOW_CMP_EQ_in_term817); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_EQ67_tree = (Object)adaptor.create(CMP_EQ67);
                    	    adaptor.addChild(root_0, CMP_EQ67_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term821);
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
                    	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:463:9: CMP_NEQ a= atom
                    	    {
                    	    CMP_NEQ68=(Token)match(input,CMP_NEQ,FOLLOW_CMP_NEQ_in_term833); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    CMP_NEQ68_tree = (Object)adaptor.create(CMP_NEQ68);
                    	    adaptor.addChild(root_0, CMP_NEQ68_tree);
                    	    }
                    	    pushFollow(FOLLOW_atom_in_term837);
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
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:468:7: ID PLUS_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID69=(Token)match(input,ID,FOLLOW_ID_in_term854); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID69_tree = (Object)adaptor.create(ID69);
                    adaptor.addChild(root_0, ID69_tree);
                    }
                    PLUS_PLUS70=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term856); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS70_tree = (Object)adaptor.create(PLUS_PLUS70);
                    adaptor.addChild(root_0, PLUS_PLUS70_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID69!=null?ID69.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:473:7: PLUS_PLUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    PLUS_PLUS71=(Token)match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_term866); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS_PLUS71_tree = (Object)adaptor.create(PLUS_PLUS71);
                    adaptor.addChild(root_0, PLUS_PLUS71_tree);
                    }
                    ID72=(Token)match(input,ID,FOLLOW_ID_in_term868); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID72_tree = (Object)adaptor.create(ID72);
                    adaptor.addChild(root_0, ID72_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID72!=null?ID72.getText():null), Operator.OPERATOR_PLUS_PLUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:478:7: ID MINUS_MINUS
                    {
                    root_0 = (Object)adaptor.nil();

                    ID73=(Token)match(input,ID,FOLLOW_ID_in_term878); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID73_tree = (Object)adaptor.create(ID73);
                    adaptor.addChild(root_0, ID73_tree);
                    }
                    MINUS_MINUS74=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term880); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS74_tree = (Object)adaptor.create(MINUS_MINUS74);
                    adaptor.addChild(root_0, MINUS_MINUS74_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID73!=null?ID73.getText():null), Operator.OPERATOR_MINUS_MINUS);
                              retval.atoms.add(va);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:483:7: MINUS_MINUS ID
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS_MINUS75=(Token)match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_term890); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS_MINUS75_tree = (Object)adaptor.create(MINUS_MINUS75);
                    adaptor.addChild(root_0, MINUS_MINUS75_tree);
                    }
                    ID76=(Token)match(input,ID,FOLLOW_ID_in_term892); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID76_tree = (Object)adaptor.create(ID76);
                    adaptor.addChild(root_0, ID76_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.atoms = new LinkedList();
                              VariableAssignment va = new VariableAssignment(this, (ID76!=null?ID76.getText():null), Operator.OPERATOR_MINUS_MINUS);
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

    public static class function_declaration_return extends ParserRuleReturnScope {
        public FunctionDeclaration expr;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_declaration"
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:490:1: function_declaration returns [FunctionDeclaration expr] : FUNCTION ID LEFT_P ( args )? RIGHT_P ( NEWLINE )? s= stat ;
    public final ScriptParser.function_declaration_return function_declaration() throws RecognitionException {
        ScriptParser.function_declaration_return retval = new ScriptParser.function_declaration_return();
        retval.start = input.LT(1);
        int function_declaration_StartIndex = input.index();
        Object root_0 = null;

        Token FUNCTION77=null;
        Token ID78=null;
        Token LEFT_P79=null;
        Token RIGHT_P81=null;
        Token NEWLINE82=null;
        ScriptParser.stat_return s = null;

        ScriptParser.args_return args80 = null;


        Object FUNCTION77_tree=null;
        Object ID78_tree=null;
        Object LEFT_P79_tree=null;
        Object RIGHT_P81_tree=null;
        Object NEWLINE82_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:490:57: ( FUNCTION ID LEFT_P ( args )? RIGHT_P ( NEWLINE )? s= stat )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:491:5: FUNCTION ID LEFT_P ( args )? RIGHT_P ( NEWLINE )? s= stat
            {
            root_0 = (Object)adaptor.nil();

            FUNCTION77=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_function_declaration911); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FUNCTION77_tree = (Object)adaptor.create(FUNCTION77);
            adaptor.addChild(root_0, FUNCTION77_tree);
            }
            ID78=(Token)match(input,ID,FOLLOW_ID_in_function_declaration913); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID78_tree = (Object)adaptor.create(ID78);
            adaptor.addChild(root_0, ID78_tree);
            }
            LEFT_P79=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_declaration915); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P79_tree = (Object)adaptor.create(LEFT_P79);
            adaptor.addChild(root_0, LEFT_P79_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:491:24: ( args )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==LEFT_CB||LA18_0==ID||(LA18_0>=PLUS_PLUS && LA18_0<=MINUS_MINUS)||LA18_0==LEFT_P||LA18_0==MINUS||LA18_0==LEFT_B||(LA18_0>=NUM && LA18_0<=STRING_LITERAL)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: args
                    {
                    pushFollow(FOLLOW_args_in_function_declaration917);
                    args80=args();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, args80.getTree());

                    }
                    break;

            }

            RIGHT_P81=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_declaration920); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P81_tree = (Object)adaptor.create(RIGHT_P81);
            adaptor.addChild(root_0, RIGHT_P81_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:491:38: ( NEWLINE )?
            int alt19=2;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                    {
                    NEWLINE82=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_function_declaration922); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE82_tree = (Object)adaptor.create(NEWLINE82);
                    adaptor.addChild(root_0, NEWLINE82_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_stat_in_function_declaration927);
            s=stat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if ( state.backtracking==0 ) {

                      retval.expr = new FunctionDeclaration(this, (ID78!=null?ID78.getText():null), (args80!=null?args80.params:null), (s!=null?s.expr:null));
                  
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
            if ( state.backtracking>0 ) { memoize(input, 16, function_declaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function_declaration"

    public static class function_call_return extends ParserRuleReturnScope {
        public LinkedList<Object> name_params;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:495:1: function_call returns [LinkedList<Object> name_params] : ID LEFT_P ( args )? RIGHT_P ;
    public final ScriptParser.function_call_return function_call() throws RecognitionException {
        ScriptParser.function_call_return retval = new ScriptParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token ID83=null;
        Token LEFT_P84=null;
        Token RIGHT_P86=null;
        ScriptParser.args_return args85 = null;


        Object ID83_tree=null;
        Object LEFT_P84_tree=null;
        Object RIGHT_P86_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:495:55: ( ID LEFT_P ( args )? RIGHT_P )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:496:5: ID LEFT_P ( args )? RIGHT_P
            {
            root_0 = (Object)adaptor.nil();

            ID83=(Token)match(input,ID,FOLLOW_ID_in_function_call944); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID83_tree = (Object)adaptor.create(ID83);
            adaptor.addChild(root_0, ID83_tree);
            }
            LEFT_P84=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_function_call946); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_P84_tree = (Object)adaptor.create(LEFT_P84);
            adaptor.addChild(root_0, LEFT_P84_tree);
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:496:15: ( args )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==LEFT_CB||LA20_0==ID||(LA20_0>=PLUS_PLUS && LA20_0<=MINUS_MINUS)||LA20_0==LEFT_P||LA20_0==MINUS||LA20_0==LEFT_B||(LA20_0>=NUM && LA20_0<=STRING_LITERAL)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: args
                    {
                    pushFollow(FOLLOW_args_in_function_call948);
                    args85=args();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, args85.getTree());

                    }
                    break;

            }

            RIGHT_P86=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_function_call951); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_P86_tree = (Object)adaptor.create(RIGHT_P86);
            adaptor.addChild(root_0, RIGHT_P86_tree);
            }
            if ( state.backtracking==0 ) {

                      if ((args85!=null?args85.params:null) != null) {
                          retval.name_params = (args85!=null?args85.params:null);
                          retval.name_params.add(0, (ID83!=null?ID83.getText():null));
                      } else {
                          retval.name_params = new LinkedList();
                          retval.name_params.add((ID83!=null?ID83.getText():null));
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
            if ( state.backtracking>0 ) { memoize(input, 17, function_call_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:507:1: args returns [LinkedList<Object> params] : a= expression ( COMMA b= args )* ;
    public final ScriptParser.args_return args() throws RecognitionException {
        ScriptParser.args_return retval = new ScriptParser.args_return();
        retval.start = input.LT(1);
        int args_StartIndex = input.index();
        Object root_0 = null;

        Token COMMA87=null;
        ScriptParser.expression_return a = null;

        ScriptParser.args_return b = null;


        Object COMMA87_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:507:41: (a= expression ( COMMA b= args )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:508:5: a= expression ( COMMA b= args )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_args971);
            a=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            if ( state.backtracking==0 ) {

                      retval.params = new LinkedList();
                      retval.params.add((a!=null?a.expr:null));
                  
            }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:511:7: ( COMMA b= args )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==COMMA) ) {
                    int LA21_2 = input.LA(2);

                    if ( (synpred46_Script()) ) {
                        alt21=1;
                    }


                }


                switch (alt21) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:511:8: COMMA b= args
            	    {
            	    COMMA87=(Token)match(input,COMMA,FOLLOW_COMMA_in_args976); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA87_tree = (Object)adaptor.create(COMMA87);
            	    adaptor.addChild(root_0, COMMA87_tree);
            	    }
            	    pushFollow(FOLLOW_args_in_args980);
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
            	    break loop21;
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
            if ( state.backtracking>0 ) { memoize(input, 18, args_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:520:1: array returns [ObjectArray array] : LEFT_B a= args RIGHT_B ;
    public final ScriptParser.array_return array() throws RecognitionException {
        ScriptParser.array_return retval = new ScriptParser.array_return();
        retval.start = input.LT(1);
        int array_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_B88=null;
        Token RIGHT_B89=null;
        ScriptParser.args_return a = null;


        Object LEFT_B88_tree=null;
        Object RIGHT_B89_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:520:34: ( LEFT_B a= args RIGHT_B )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:521:5: LEFT_B a= args RIGHT_B
            {
            root_0 = (Object)adaptor.nil();

            LEFT_B88=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array1002); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_B88_tree = (Object)adaptor.create(LEFT_B88);
            adaptor.addChild(root_0, LEFT_B88_tree);
            }
            pushFollow(FOLLOW_args_in_array1006);
            a=args();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
            RIGHT_B89=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array1008); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_B89_tree = (Object)adaptor.create(RIGHT_B89);
            adaptor.addChild(root_0, RIGHT_B89_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 19, array_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:527:1: array_element_reference returns [StorageAccessor accessor] : (a= ID LEFT_B b= ID RIGHT_B | a= ID LEFT_B b= NUM RIGHT_B | a= ID LEFT_B s= string_literal RIGHT_B );
    public final ScriptParser.array_element_reference_return array_element_reference() throws RecognitionException {
        ScriptParser.array_element_reference_return retval = new ScriptParser.array_element_reference_return();
        retval.start = input.LT(1);
        int array_element_reference_StartIndex = input.index();
        Object root_0 = null;

        Token a=null;
        Token b=null;
        Token LEFT_B90=null;
        Token RIGHT_B91=null;
        Token LEFT_B92=null;
        Token RIGHT_B93=null;
        Token LEFT_B94=null;
        Token RIGHT_B95=null;
        ScriptParser.string_literal_return s = null;


        Object a_tree=null;
        Object b_tree=null;
        Object LEFT_B90_tree=null;
        Object RIGHT_B91_tree=null;
        Object LEFT_B92_tree=null;
        Object RIGHT_B93_tree=null;
        Object LEFT_B94_tree=null;
        Object RIGHT_B95_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:528:5: (a= ID LEFT_B b= ID RIGHT_B | a= ID LEFT_B b= NUM RIGHT_B | a= ID LEFT_B s= string_literal RIGHT_B )
            int alt22=3;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==ID) ) {
                int LA22_1 = input.LA(2);

                if ( (LA22_1==LEFT_B) ) {
                    switch ( input.LA(3) ) {
                    case ID:
                        {
                        alt22=1;
                        }
                        break;
                    case NUM:
                        {
                        alt22=2;
                        }
                        break;
                    case STRING_LITERAL:
                        {
                        alt22=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 22, 2, input);

                        throw nvae;
                    }

                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:528:7: a= ID LEFT_B b= ID RIGHT_B
                    {
                    root_0 = (Object)adaptor.nil();

                    a=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference1030); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (Object)adaptor.create(a);
                    adaptor.addChild(root_0, a_tree);
                    }
                    LEFT_B90=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array_element_reference1032); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_B90_tree = (Object)adaptor.create(LEFT_B90);
                    adaptor.addChild(root_0, LEFT_B90_tree);
                    }
                    b=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference1036); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    b_tree = (Object)adaptor.create(b);
                    adaptor.addChild(root_0, b_tree);
                    }
                    RIGHT_B91=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array_element_reference1038); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_B91_tree = (Object)adaptor.create(RIGHT_B91);
                    adaptor.addChild(root_0, RIGHT_B91_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, (a!=null?a.getText():null)), new Variable(this, (b!=null?b.getText():null)), null);
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:531:7: a= ID LEFT_B b= NUM RIGHT_B
                    {
                    root_0 = (Object)adaptor.nil();

                    a=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference1050); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (Object)adaptor.create(a);
                    adaptor.addChild(root_0, a_tree);
                    }
                    LEFT_B92=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array_element_reference1052); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_B92_tree = (Object)adaptor.create(LEFT_B92);
                    adaptor.addChild(root_0, LEFT_B92_tree);
                    }
                    b=(Token)match(input,NUM,FOLLOW_NUM_in_array_element_reference1056); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    b_tree = (Object)adaptor.create(b);
                    adaptor.addChild(root_0, b_tree);
                    }
                    RIGHT_B93=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array_element_reference1058); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_B93_tree = (Object)adaptor.create(RIGHT_B93);
                    adaptor.addChild(root_0, RIGHT_B93_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.accessor = new StorageAccessor(this, StorageAccessor.REFERENCE, new Variable(this, (a!=null?a.getText():null)), new Numeric(Double.parseDouble((b!=null?b.getText():null))), null);
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:534:7: a= ID LEFT_B s= string_literal RIGHT_B
                    {
                    root_0 = (Object)adaptor.nil();

                    a=(Token)match(input,ID,FOLLOW_ID_in_array_element_reference1070); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    a_tree = (Object)adaptor.create(a);
                    adaptor.addChild(root_0, a_tree);
                    }
                    LEFT_B94=(Token)match(input,LEFT_B,FOLLOW_LEFT_B_in_array_element_reference1072); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_B94_tree = (Object)adaptor.create(LEFT_B94);
                    adaptor.addChild(root_0, LEFT_B94_tree);
                    }
                    pushFollow(FOLLOW_string_literal_in_array_element_reference1076);
                    s=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    RIGHT_B95=(Token)match(input,RIGHT_B,FOLLOW_RIGHT_B_in_array_element_reference1078); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_B95_tree = (Object)adaptor.create(RIGHT_B95);
                    adaptor.addChild(root_0, RIGHT_B95_tree);
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
            if ( state.backtracking>0 ) { memoize(input, 20, array_element_reference_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:538:1: atom returns [Object value] : ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | CONSTANT | string_literal | dictionary | array | function_call | array_element_reference );
    public final ScriptParser.atom_return atom() throws RecognitionException {
        ScriptParser.atom_return retval = new ScriptParser.atom_return();
        retval.start = input.LT(1);
        int atom_StartIndex = input.index();
        Object root_0 = null;

        Token NUM96=null;
        Token MINUS97=null;
        Token NUM98=null;
        Token BOOL99=null;
        Token LEFT_P100=null;
        Token RIGHT_P102=null;
        Token ID103=null;
        Token CONSTANT104=null;
        ScriptParser.expression_return expression101 = null;

        ScriptParser.string_literal_return string_literal105 = null;

        ScriptParser.dictionary_return dictionary106 = null;

        ScriptParser.array_return array107 = null;

        ScriptParser.function_call_return function_call108 = null;

        ScriptParser.array_element_reference_return array_element_reference109 = null;


        Object NUM96_tree=null;
        Object MINUS97_tree=null;
        Object NUM98_tree=null;
        Object BOOL99_tree=null;
        Object LEFT_P100_tree=null;
        Object RIGHT_P102_tree=null;
        Object ID103_tree=null;
        Object CONSTANT104_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:539:5: ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | CONSTANT | string_literal | dictionary | array | function_call | array_element_reference )
            int alt23=11;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:539:7: NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM96=(Token)match(input,NUM,FOLLOW_NUM_in_atom1096); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM96_tree = (Object)adaptor.create(NUM96);
                    adaptor.addChild(root_0, NUM96_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( Double.parseDouble((NUM96!=null?NUM96.getText():null)) );
                          
                    }

                    }
                    break;
                case 2 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:542:7: MINUS NUM
                    {
                    root_0 = (Object)adaptor.nil();

                    MINUS97=(Token)match(input,MINUS,FOLLOW_MINUS_in_atom1106); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS97_tree = (Object)adaptor.create(MINUS97);
                    adaptor.addChild(root_0, MINUS97_tree);
                    }
                    NUM98=(Token)match(input,NUM,FOLLOW_NUM_in_atom1108); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM98_tree = (Object)adaptor.create(NUM98);
                    adaptor.addChild(root_0, NUM98_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Numeric( -1.0 * Double.parseDouble((NUM98!=null?NUM98.getText():null)) );
                          
                    }

                    }
                    break;
                case 3 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:545:7: BOOL
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOL99=(Token)match(input,BOOL,FOLLOW_BOOL_in_atom1118); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOL99_tree = (Object)adaptor.create(BOOL99);
                    adaptor.addChild(root_0, BOOL99_tree);
                    }
                    if ( state.backtracking==0 ) {

                              if ((BOOL99!=null?BOOL99.getText():null).equalsIgnoreCase("true")) {
                                  retval.value = new Bool(true);
                              } else if ((BOOL99!=null?BOOL99.getText():null).equalsIgnoreCase("false")) {
                                  retval.value = new Bool(false);
                              }
                          
                    }

                    }
                    break;
                case 4 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:552:7: LEFT_P expression RIGHT_P
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFT_P100=(Token)match(input,LEFT_P,FOLLOW_LEFT_P_in_atom1128); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFT_P100_tree = (Object)adaptor.create(LEFT_P100);
                    adaptor.addChild(root_0, LEFT_P100_tree);
                    }
                    pushFollow(FOLLOW_expression_in_atom1130);
                    expression101=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression101.getTree());
                    RIGHT_P102=(Token)match(input,RIGHT_P,FOLLOW_RIGHT_P_in_atom1132); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHT_P102_tree = (Object)adaptor.create(RIGHT_P102);
                    adaptor.addChild(root_0, RIGHT_P102_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = (expression101!=null?expression101.expr:null);
                          
                    }

                    }
                    break;
                case 5 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:555:7: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID103=(Token)match(input,ID,FOLLOW_ID_in_atom1142); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID103_tree = (Object)adaptor.create(ID103);
                    adaptor.addChild(root_0, ID103_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (ID103!=null?ID103.getText():null));
                          
                    }

                    }
                    break;
                case 6 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:558:7: CONSTANT
                    {
                    root_0 = (Object)adaptor.nil();

                    CONSTANT104=(Token)match(input,CONSTANT,FOLLOW_CONSTANT_in_atom1152); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    CONSTANT104_tree = (Object)adaptor.create(CONSTANT104);
                    adaptor.addChild(root_0, CONSTANT104_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.value = new Variable(this, (CONSTANT104!=null?CONSTANT104.getText():null));
                          
                    }

                    }
                    break;
                case 7 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:561:7: string_literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_string_literal_in_atom1162);
                    string_literal105=string_literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, string_literal105.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (string_literal105!=null?string_literal105.value:null);
                          
                    }

                    }
                    break;
                case 8 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:564:7: dictionary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dictionary_in_atom1172);
                    dictionary106=dictionary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary106.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (dictionary106!=null?dictionary106.value:null);
                          
                    }

                    }
                    break;
                case 9 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:567:7: array
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_array_in_atom1182);
                    array107=array();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, array107.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = (array107!=null?array107.array:null);
                          
                    }

                    }
                    break;
                case 10 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:570:7: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_atom1192);
                    function_call108=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call108.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = new Expression( this, new FunctionCall( this, (function_call108!=null?function_call108.name_params:null) ) );
                          
                    }

                    }
                    break;
                case 11 :
                    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:573:7: array_element_reference
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_array_element_reference_in_atom1202);
                    array_element_reference109=array_element_reference();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, array_element_reference109.getTree());
                    if ( state.backtracking==0 ) {

                              retval.value = new Expression( this, (array_element_reference109!=null?array_element_reference109.accessor:null));
                          
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
            if ( state.backtracking>0 ) { memoize(input, 21, atom_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:584:1: string_literal returns [CharString value] : s= STRING_LITERAL ;
    public final ScriptParser.string_literal_return string_literal() throws RecognitionException {
        ScriptParser.string_literal_return retval = new ScriptParser.string_literal_return();
        retval.start = input.LT(1);
        int string_literal_StartIndex = input.index();
        Object root_0 = null;

        Token s=null;

        Object s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:584:43: (s= STRING_LITERAL )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:584:45: s= STRING_LITERAL
            {
            root_0 = (Object)adaptor.nil();

            s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_string_literal1225); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 22, string_literal_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:589:1: dictionary returns [Dictionary value] : LEFT_CB dictionary_elements RIGHT_CB ;
    public final ScriptParser.dictionary_return dictionary() throws RecognitionException {
        ScriptParser.dictionary_return retval = new ScriptParser.dictionary_return();
        retval.start = input.LT(1);
        int dictionary_StartIndex = input.index();
        Object root_0 = null;

        Token LEFT_CB110=null;
        Token RIGHT_CB112=null;
        ScriptParser.dictionary_elements_return dictionary_elements111 = null;


        Object LEFT_CB110_tree=null;
        Object RIGHT_CB112_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:589:39: ( LEFT_CB dictionary_elements RIGHT_CB )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:590:5: LEFT_CB dictionary_elements RIGHT_CB
            {
            root_0 = (Object)adaptor.nil();

            LEFT_CB110=(Token)match(input,LEFT_CB,FOLLOW_LEFT_CB_in_dictionary1244); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFT_CB110_tree = (Object)adaptor.create(LEFT_CB110);
            adaptor.addChild(root_0, LEFT_CB110_tree);
            }
            pushFollow(FOLLOW_dictionary_elements_in_dictionary1246);
            dictionary_elements111=dictionary_elements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dictionary_elements111.getTree());
            RIGHT_CB112=(Token)match(input,RIGHT_CB,FOLLOW_RIGHT_CB_in_dictionary1248); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHT_CB112_tree = (Object)adaptor.create(RIGHT_CB112);
            adaptor.addChild(root_0, RIGHT_CB112_tree);
            }
            if ( state.backtracking==0 ) {

                      HashMap vs = new HashMap();
                      int size = (dictionary_elements111!=null?dictionary_elements111.keys_values:null).size();
                      for (int k=0; k<size; k+=2) {
                          vs.put((dictionary_elements111!=null?dictionary_elements111.keys_values:null).get(k), (dictionary_elements111!=null?dictionary_elements111.keys_values:null).get(k+1));
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
            if ( state.backtracking>0 ) { memoize(input, 23, dictionary_StartIndex); }
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
    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:599:1: dictionary_elements returns [LinkedList<Object> keys_values] : (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* ;
    public final ScriptParser.dictionary_elements_return dictionary_elements() throws RecognitionException {
        ScriptParser.dictionary_elements_return retval = new ScriptParser.dictionary_elements_return();
        retval.start = input.LT(1);
        int dictionary_elements_StartIndex = input.index();
        Object root_0 = null;

        Token TP113=null;
        Token NEWLINE114=null;
        Token COMMA115=null;
        Token NEWLINE116=null;
        ScriptParser.expression_return e1 = null;

        ScriptParser.expression_return e2 = null;

        ScriptParser.dictionary_elements_return d = null;


        Object TP113_tree=null;
        Object NEWLINE114_tree=null;
        Object COMMA115_tree=null;
        Object NEWLINE116_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:599:62: ( (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )* )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:600:5: (e1= expression TP e2= expression ) ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            {
            root_0 = (Object)adaptor.nil();

            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:600:5: (e1= expression TP e2= expression )
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:600:6: e1= expression TP e2= expression
            {
            pushFollow(FOLLOW_expression_in_dictionary_elements1269);
            e1=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
            TP113=(Token)match(input,TP,FOLLOW_TP_in_dictionary_elements1271); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TP113_tree = (Object)adaptor.create(TP113);
            adaptor.addChild(root_0, TP113_tree);
            }
            pushFollow(FOLLOW_expression_in_dictionary_elements1275);
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
            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:7: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==NEWLINE) ) {
                    int LA26_2 = input.LA(2);

                    if ( (synpred61_Script()) ) {
                        alt26=1;
                    }


                }
                else if ( (LA26_0==COMMA) ) {
                    int LA26_3 = input.LA(2);

                    if ( (synpred61_Script()) ) {
                        alt26=1;
                    }


                }


                switch (alt26) {
            	case 1 :
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
            	    {
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:8: ( NEWLINE )?
            	    int alt24=2;
            	    int LA24_0 = input.LA(1);

            	    if ( (LA24_0==NEWLINE) ) {
            	        alt24=1;
            	    }
            	    switch (alt24) {
            	        case 1 :
            	            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE114=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1281); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE114_tree = (Object)adaptor.create(NEWLINE114);
            	            adaptor.addChild(root_0, NEWLINE114_tree);
            	            }

            	            }
            	            break;

            	    }

            	    COMMA115=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictionary_elements1284); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA115_tree = (Object)adaptor.create(COMMA115);
            	    adaptor.addChild(root_0, COMMA115_tree);
            	    }
            	    // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:23: ( NEWLINE )?
            	    int alt25=2;
            	    int LA25_0 = input.LA(1);

            	    if ( (LA25_0==NEWLINE) ) {
            	        alt25=1;
            	    }
            	    switch (alt25) {
            	        case 1 :
            	            // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
            	            {
            	            NEWLINE116=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_dictionary_elements1286); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NEWLINE116_tree = (Object)adaptor.create(NEWLINE116);
            	            adaptor.addChild(root_0, NEWLINE116_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_dictionary_elements_in_dictionary_elements1291);
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
            	    break loop26;
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
            if ( state.backtracking>0 ) { memoize(input, 24, dictionary_elements_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dictionary_elements"

    // $ANTLR start synpred1_Script
    public final void synpred1_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:212:13: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:212:13: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred1_Script100); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Script

    // $ANTLR start synpred3_Script
    public final void synpred3_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:222:8: (s= stat )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:222:8: s= stat
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
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:230:7: ( pre_stat NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:230:7: pre_stat NEWLINE
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
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:237:7: ( block )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:237:7: block
        {
        pushFollow(FOLLOW_block_in_synpred6_Script181);
        block();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Script

    // $ANTLR start synpred10_Script
    public final void synpred10_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:253:7: ( expression )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:253:7: expression
        {
        pushFollow(FOLLOW_expression_in_synpred10_Script238);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_Script

    // $ANTLR start synpred15_Script
    public final void synpred15_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:268:7: ( ID PLUS_PLUS )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:268:7: ID PLUS_PLUS
        {
        match(input,ID,FOLLOW_ID_in_synpred15_Script296); if (state.failed) return ;
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred15_Script298); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_Script

    // $ANTLR start synpred16_Script
    public final void synpred16_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:271:7: ( PLUS_PLUS ID )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:271:7: PLUS_PLUS ID
        {
        match(input,PLUS_PLUS,FOLLOW_PLUS_PLUS_in_synpred16_Script308); if (state.failed) return ;
        match(input,ID,FOLLOW_ID_in_synpred16_Script310); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_Script

    // $ANTLR start synpred17_Script
    public final void synpred17_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:274:7: ( ID MINUS_MINUS )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:274:7: ID MINUS_MINUS
        {
        match(input,ID,FOLLOW_ID_in_synpred17_Script320); if (state.failed) return ;
        match(input,MINUS_MINUS,FOLLOW_MINUS_MINUS_in_synpred17_Script322); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_Script

    // $ANTLR start synpred18_Script
    public final void synpred18_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:301:38: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:301:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred18_Script393); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_Script

    // $ANTLR start synpred19_Script
    public final void synpred19_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:7: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:7: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred19_Script402); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_Script

    // $ANTLR start synpred20_Script
    public final void synpred20_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:22: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:22: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred20_Script408); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_Script

    // $ANTLR start synpred21_Script
    public final void synpred21_Script_fragment() throws RecognitionException {   
        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:17: ( ELSE ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:17: ELSE ( NEWLINE )? s= stat
        {
        match(input,ELSE,FOLLOW_ELSE_in_synpred21_Script406); if (state.failed) return ;
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:305:22: ( NEWLINE )?
        int alt27=2;
        int LA27_0 = input.LA(1);

        if ( (LA27_0==NEWLINE) ) {
            int LA27_1 = input.LA(2);

            if ( ((LA27_1>=LEFT_CB && LA27_1<=NEWLINE)||(LA27_1>=BREAK && LA27_1<=ID)||(LA27_1>=PLUS_PLUS && LA27_1<=LEFT_P)||(LA27_1>=WHILE && LA27_1<=FOR)||LA27_1==MINUS||LA27_1==FUNCTION||LA27_1==LEFT_B||(LA27_1>=NUM && LA27_1<=STRING_LITERAL)) ) {
                alt27=1;
            }
        }
        switch (alt27) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred21_Script408); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred21_Script413);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_Script

    // $ANTLR start synpred22_Script
    public final void synpred22_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:323:41: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:323:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred22_Script463); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_Script

    // $ANTLR start synpred23_Script
    public final void synpred23_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:81: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:81: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred23_Script528); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_Script

    // $ANTLR start synpred24_Script
    public final void synpred24_Script_fragment() throws RecognitionException {   
        ScriptParser.pre_stat_return e_init = null;

        ScriptParser.expression_return e_cond = null;

        ScriptParser.pre_stat_return e_inc = null;

        ScriptParser.stat_return s = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:7: ( FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:7: FOR LEFT_P e_init= pre_stat PV e_cond= expression PV e_inc= pre_stat RIGHT_P ( NEWLINE )? s= stat
        {
        match(input,FOR,FOLLOW_FOR_in_synpred24_Script506); if (state.failed) return ;
        match(input,LEFT_P,FOLLOW_LEFT_P_in_synpred24_Script508); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred24_Script512);
        e_init=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred24_Script514); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred24_Script518);
        e_cond=expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,PV,FOLLOW_PV_in_synpred24_Script520); if (state.failed) return ;
        pushFollow(FOLLOW_pre_stat_in_synpred24_Script524);
        e_inc=pre_stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,RIGHT_P,FOLLOW_RIGHT_P_in_synpred24_Script526); if (state.failed) return ;
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:343:81: ( NEWLINE )?
        int alt28=2;
        int LA28_0 = input.LA(1);

        if ( (LA28_0==NEWLINE) ) {
            int LA28_1 = input.LA(2);

            if ( ((LA28_1>=LEFT_CB && LA28_1<=NEWLINE)||(LA28_1>=BREAK && LA28_1<=ID)||(LA28_1>=PLUS_PLUS && LA28_1<=LEFT_P)||(LA28_1>=WHILE && LA28_1<=FOR)||LA28_1==MINUS||LA28_1==FUNCTION||LA28_1==LEFT_B||(LA28_1>=NUM && LA28_1<=STRING_LITERAL)) ) {
                alt28=1;
            }
        }
        switch (alt28) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred24_Script528); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_stat_in_synpred24_Script533);
        s=stat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_Script

    // $ANTLR start synpred25_Script
    public final void synpred25_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:350:41: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:350:41: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred25_Script555); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_Script

    // $ANTLR start synpred26_Script
    public final void synpred26_Script_fragment() throws RecognitionException {   
        ScriptParser.range_return b = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:397:8: ( ARROW b= range )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:397:8: ARROW b= range
        {
        match(input,ARROW,FOLLOW_ARROW_in_synpred26_Script585); if (state.failed) return ;
        pushFollow(FOLLOW_range_in_synpred26_Script589);
        b=range();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_Script

    // $ANTLR start synpred44_Script
    public final void synpred44_Script_fragment() throws RecognitionException {   
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:491:38: ( NEWLINE )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:491:38: NEWLINE
        {
        match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred44_Script922); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred44_Script

    // $ANTLR start synpred46_Script
    public final void synpred46_Script_fragment() throws RecognitionException {   
        ScriptParser.args_return b = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:511:8: ( COMMA b= args )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:511:8: COMMA b= args
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred46_Script976); if (state.failed) return ;
        pushFollow(FOLLOW_args_in_synpred46_Script980);
        b=args();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred46_Script

    // $ANTLR start synpred61_Script
    public final void synpred61_Script_fragment() throws RecognitionException {   
        ScriptParser.dictionary_elements_return d = null;


        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:8: ( ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements )
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:8: ( NEWLINE )? COMMA ( NEWLINE )? d= dictionary_elements
        {
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:8: ( NEWLINE )?
        int alt30=2;
        int LA30_0 = input.LA(1);

        if ( (LA30_0==NEWLINE) ) {
            alt30=1;
        }
        switch (alt30) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred61_Script1281); if (state.failed) return ;

                }
                break;

        }

        match(input,COMMA,FOLLOW_COMMA_in_synpred61_Script1284); if (state.failed) return ;
        // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:604:23: ( NEWLINE )?
        int alt31=2;
        int LA31_0 = input.LA(1);

        if ( (LA31_0==NEWLINE) ) {
            alt31=1;
        }
        switch (alt31) {
            case 1 :
                // /home/laurent/dev/WPA/SCRIPTING/WPScript/working_copy_git/src/language/Script.g:0:0: NEWLINE
                {
                match(input,NEWLINE,FOLLOW_NEWLINE_in_synpred61_Script1286); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_dictionary_elements_in_synpred61_Script1291);
        d=dictionary_elements();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred61_Script

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
    public final boolean synpred61_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred44_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred44_Script_fragment(); // can never throw exception
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
    public final boolean synpred10_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_Script_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_Script_fragment(); // can never throw exception
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
    public final boolean synpred46_Script() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred46_Script_fragment(); // can never throw exception
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
    protected DFA19 dfa19 = new DFA19(this);
    protected DFA23 dfa23 = new DFA23(this);
    static final String DFA1_eotS =
        "\24\uffff";
    static final String DFA1_eofS =
        "\24\uffff";
    static final String DFA1_minS =
        "\1\4\1\0\22\uffff";
    static final String DFA1_maxS =
        "\1\50\1\0\22\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\20\uffff\1\1";
    static final String DFA1_specialS =
        "\1\uffff\1\0\22\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\12"+
            "\uffff\1\2\1\uffff\1\2\1\uffff\4\2",
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
            return "212:13: ( NEWLINE )?";
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
                        if ( (synpred1_Script()) ) {s = 19;}

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
        "\24\uffff";
    static final String DFA4_eofS =
        "\24\uffff";
    static final String DFA4_minS =
        "\1\4\7\uffff\1\0\13\uffff";
    static final String DFA4_maxS =
        "\1\50\7\uffff\1\0\13\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\14\uffff\1\2\1\4\1\5\1\6\1\7\1\3";
    static final String DFA4_specialS =
        "\10\uffff\1\0\13\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\10\1\16\1\uffff\3\1\1\uffff\2\1\1\17\1\1\2\uffff\1\20\1\21"+
            "\3\uffff\1\1\12\uffff\1\22\1\uffff\1\1\1\uffff\4\1",
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
            return "229:1: stat returns [Expression expr] : ( pre_stat NEWLINE | NEWLINE | block | if_expression | while_expression | for_expression | function_declaration );";
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

                        else if ( (synpred6_Script()) ) {s = 19;}

                         
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
        "\1\4\1\uffff\1\5\2\11\2\uffff\1\11\2\0\1\uffff\2\0\3\44\4\uffff"+
        "\3\5\1\uffff";
    static final String DFA5_maxS =
        "\1\50\1\uffff\1\43\2\11\2\uffff\1\50\2\0\1\uffff\2\0\3\44\4\uffff"+
        "\3\40\1\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\3\uffff\1\2\1\3\3\uffff\1\4\5\uffff\1\6\1\10\1\7\1"+
        "\11\3\uffff\1\5";
    static final String DFA5_specialS =
        "\10\uffff\1\0\1\3\1\uffff\1\2\1\1\13\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1\2\uffff\1\5\1\6\1\2\1\uffff\1\3\1\4\1\uffff\1\1\7\uffff"+
            "\1\1\14\uffff\1\1\1\uffff\4\1",
            "",
            "\1\1\4\uffff\1\12\1\10\1\11\1\uffff\2\1\3\uffff\1\1\1\uffff"+
            "\14\1\2\uffff\1\7",
            "\1\13",
            "\1\14",
            "",
            "",
            "\1\15\33\uffff\1\16\2\uffff\1\17",
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
            return "252:1: pre_stat returns [Expression expr] : ( expression | BREAK | CONTINUE | ID EQUAL expression | array_element_reference EQUAL expression | ID PLUS_PLUS | PLUS_PLUS ID | ID MINUS_MINUS | MINUS_MINUS ID );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_8 = input.LA(1);

                         
                        int index5_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Script()) ) {s = 1;}

                        else if ( (synpred15_Script()) ) {s = 16;}

                         
                        input.seek(index5_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_12 = input.LA(1);

                         
                        int index5_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Script()) ) {s = 1;}

                        else if ( (true) ) {s = 19;}

                         
                        input.seek(index5_12);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_11 = input.LA(1);

                         
                        int index5_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Script()) ) {s = 1;}

                        else if ( (synpred16_Script()) ) {s = 18;}

                         
                        input.seek(index5_11);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_9 = input.LA(1);

                         
                        int index5_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Script()) ) {s = 1;}

                        else if ( (synpred17_Script()) ) {s = 17;}

                         
                        input.seek(index5_9);
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
        "\24\uffff";
    static final String DFA6_eofS =
        "\24\uffff";
    static final String DFA6_minS =
        "\1\4\1\0\22\uffff";
    static final String DFA6_maxS =
        "\1\50\1\0\22\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\2\20\uffff\1\1";
    static final String DFA6_specialS =
        "\1\uffff\1\0\22\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\12"+
            "\uffff\1\2\1\uffff\1\2\1\uffff\4\2",
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
            return "301:38: ( NEWLINE )?";
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
                        if ( (synpred18_Script()) ) {s = 19;}

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
        "\24\uffff";
    static final String DFA8_eofS =
        "\24\uffff";
    static final String DFA8_minS =
        "\1\4\1\0\22\uffff";
    static final String DFA8_maxS =
        "\1\50\1\0\22\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\20\uffff\1\1";
    static final String DFA8_specialS =
        "\1\uffff\1\0\22\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\12"+
            "\uffff\1\2\1\uffff\1\2\1\uffff\4\2",
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
            return "305:22: ( NEWLINE )?";
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
                        if ( (synpred20_Script()) ) {s = 19;}

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
        "\24\uffff";
    static final String DFA10_eofS =
        "\24\uffff";
    static final String DFA10_minS =
        "\1\4\1\0\22\uffff";
    static final String DFA10_maxS =
        "\1\50\1\0\22\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\1\2\20\uffff\1\1";
    static final String DFA10_specialS =
        "\1\uffff\1\0\22\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\12"+
            "\uffff\1\2\1\uffff\1\2\1\uffff\4\2",
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
            return "323:41: ( NEWLINE )?";
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
                        if ( (synpred22_Script()) ) {s = 19;}

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
        "\24\uffff";
    static final String DFA11_eofS =
        "\24\uffff";
    static final String DFA11_minS =
        "\1\4\1\0\22\uffff";
    static final String DFA11_maxS =
        "\1\50\1\0\22\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\2\20\uffff\1\1";
    static final String DFA11_specialS =
        "\1\uffff\1\0\22\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\12"+
            "\uffff\1\2\1\uffff\1\2\1\uffff\4\2",
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
            return "343:81: ( NEWLINE )?";
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
                        if ( (synpred23_Script()) ) {s = 19;}

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
        "\24\uffff";
    static final String DFA12_eofS =
        "\24\uffff";
    static final String DFA12_minS =
        "\1\4\1\0\22\uffff";
    static final String DFA12_maxS =
        "\1\50\1\0\22\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\20\uffff\1\1";
    static final String DFA12_specialS =
        "\1\uffff\1\0\22\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\12"+
            "\uffff\1\2\1\uffff\1\2\1\uffff\4\2",
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
            return "350:41: ( NEWLINE )?";
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
                        if ( (synpred25_Script()) ) {s = 19;}

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
    static final String DFA19_eotS =
        "\24\uffff";
    static final String DFA19_eofS =
        "\24\uffff";
    static final String DFA19_minS =
        "\1\4\1\0\22\uffff";
    static final String DFA19_maxS =
        "\1\50\1\0\22\uffff";
    static final String DFA19_acceptS =
        "\2\uffff\1\2\20\uffff\1\1";
    static final String DFA19_specialS =
        "\1\uffff\1\0\22\uffff}>";
    static final String[] DFA19_transitionS = {
            "\1\2\1\1\1\uffff\3\2\1\uffff\4\2\2\uffff\2\2\3\uffff\1\2\12"+
            "\uffff\1\2\1\uffff\1\2\1\uffff\4\2",
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
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "491:38: ( NEWLINE )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_1 = input.LA(1);

                         
                        int index19_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred44_Script()) ) {s = 19;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index19_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA23_eotS =
        "\15\uffff";
    static final String DFA23_eofS =
        "\5\uffff\1\14\7\uffff";
    static final String DFA23_minS =
        "\1\4\4\uffff\1\5\7\uffff";
    static final String DFA23_maxS =
        "\1\50\4\uffff\1\51\7\uffff";
    static final String DFA23_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\uffff\1\6\1\7\1\10\1\11\1\12\1\13\1\5";
    static final String DFA23_specialS =
        "\15\uffff}>";
    static final String[] DFA23_transitionS = {
            "\1\10\4\uffff\1\5\4\uffff\1\4\7\uffff\1\2\14\uffff\1\11\1\uffff"+
            "\1\1\1\3\1\6\1\7",
            "",
            "",
            "",
            "",
            "\2\14\7\uffff\1\12\1\14\3\uffff\16\14\1\uffff\1\14\1\13\1\14"+
            "\4\uffff\1\14",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "538:1: atom returns [Object value] : ( NUM | MINUS NUM | BOOL | LEFT_P expression RIGHT_P | ID | CONSTANT | string_literal | dictionary | array | function_call | array_element_reference );";
        }
    }
 

    public static final BitSet FOLLOW_stats_in_prog81 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_block98 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_block100 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stats_in_block103 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_NEWLINE_in_block105 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_block108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_stats127 = new BitSet(new long[]{0x000001EA00467BB2L});
    public static final BitSet FOLLOW_stat_in_stats134 = new BitSet(new long[]{0x000001EA00467BB2L});
    public static final BitSet FOLLOW_pre_stat_in_stat159 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_stat161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expression_in_stat191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_expression_in_stat201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_expression_in_stat211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_declaration_in_stat221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_pre_stat238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_pre_stat248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONTINUE_in_pre_stat258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat268 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat270 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_pre_stat272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_element_reference_in_pre_stat282 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_stat284 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_pre_stat286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat296 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_pre_stat308 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_stat310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pre_stat320 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_pre_stat332 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_stat334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_if_expression_in_if_expression364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_pre_if_expression383 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_if_expression385 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_pre_if_expression389 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_if_expression391 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression393 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression398 = new BitSet(new long[]{0x0000000000010022L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression402 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ELSE_in_pre_if_expression406 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_if_expression408 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_if_expression413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_while_expression_in_while_expression435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_pre_while_expression453 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_while_expression455 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_pre_while_expression459 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_while_expression461 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_while_expression463 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_while_expression468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_for_expression_in_for_expression488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression506 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression508 = new BitSet(new long[]{0x000001E800405B90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression512 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression514 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_pre_for_expression518 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_pre_for_expression520 = new BitSet(new long[]{0x000001E800405B90L});
    public static final BitSet FOLLOW_pre_stat_in_pre_for_expression524 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression526 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression528 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_pre_for_expression543 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_pre_for_expression545 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_pre_for_expression547 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_pre_for_expression549 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_range_in_pre_for_expression551 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_pre_for_expression553 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_pre_for_expression555 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_pre_for_expression560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_range580 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_ARROW_in_range585 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_range_in_range589 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_terms_in_expression610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms630 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_PLUS_in_terms636 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_term_in_terms640 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_MINUS_in_terms652 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_term_in_terms656 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_AND_in_terms668 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_term_in_terms672 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_OR_in_terms684 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_term_in_terms688 = new BitSet(new long[]{0x0000000001E00002L});
    public static final BitSet FOLLOW_atom_in_term712 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_MULT_in_term718 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term722 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_DIV_in_term734 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term738 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_LT_in_term750 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term754 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_LT_EQ_in_term767 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term771 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_GT_in_term784 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term788 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_GT_EQ_in_term800 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term804 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_EQ_in_term817 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term821 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_CMP_NEQ_in_term833 = new BitSet(new long[]{0x000001E800404210L});
    public static final BitSet FOLLOW_atom_in_term837 = new BitSet(new long[]{0x00000001FE000002L});
    public static final BitSet FOLLOW_ID_in_term854 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_term866 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_term868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term878 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_term890 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_term892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCTION_in_function_declaration911 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_function_declaration913 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_function_declaration915 = new BitSet(new long[]{0x000001E80040DA10L});
    public static final BitSet FOLLOW_args_in_function_declaration917 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_declaration920 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_function_declaration922 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_function_declaration927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_function_call944 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_function_call946 = new BitSet(new long[]{0x000001E80040DA10L});
    public static final BitSet FOLLOW_args_in_function_call948 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_function_call951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_args971 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_COMMA_in_args976 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_args_in_args980 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_LEFT_B_in_array1002 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_args_in_array1006 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array1008 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_array_element_reference1030 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_LEFT_B_in_array_element_reference1032 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_array_element_reference1036 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array_element_reference1038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_array_element_reference1050 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_LEFT_B_in_array_element_reference1052 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_NUM_in_array_element_reference1056 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array_element_reference1058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_array_element_reference1070 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_LEFT_B_in_array_element_reference1072 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_string_literal_in_array_element_reference1076 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_RIGHT_B_in_array_element_reference1078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_atom1096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_atom1106 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_NUM_in_atom1108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_atom1118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_P_in_atom1128 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_atom1130 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_atom1132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom1142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_in_atom1152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_string_literal_in_atom1162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dictionary_in_atom1172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_in_atom1182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_atom1192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_element_reference_in_atom1202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal1225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_CB_in_dictionary1244 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary1246 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RIGHT_CB_in_dictionary1248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1269 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_TP_in_dictionary_elements1271 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_dictionary_elements1275 = new BitSet(new long[]{0x0000000400000022L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1281 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COMMA_in_dictionary_elements1284 = new BitSet(new long[]{0x000001E800405A30L});
    public static final BitSet FOLLOW_NEWLINE_in_dictionary_elements1286 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_dictionary_elements1291 = new BitSet(new long[]{0x0000000400000022L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred1_Script100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_synpred3_Script134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_stat_in_synpred4_Script159 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred4_Script161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_synpred6_Script181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred10_Script238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred15_Script296 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred15_Script298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_PLUS_in_synpred16_Script308 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ID_in_synpred16_Script310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred17_Script320 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MINUS_MINUS_in_synpred17_Script322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred18_Script393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred19_Script402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred20_Script408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_synpred21_Script406 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred21_Script408 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_synpred21_Script413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred22_Script463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred23_Script528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_synpred24_Script506 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_LEFT_P_in_synpred24_Script508 = new BitSet(new long[]{0x000001E800405B90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred24_Script512 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_synpred24_Script514 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_expression_in_synpred24_Script518 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_PV_in_synpred24_Script520 = new BitSet(new long[]{0x000001E800405B90L});
    public static final BitSet FOLLOW_pre_stat_in_synpred24_Script524 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_P_in_synpred24_Script526 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred24_Script528 = new BitSet(new long[]{0x000001EA00467BB0L});
    public static final BitSet FOLLOW_stat_in_synpred24_Script533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred25_Script555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARROW_in_synpred26_Script585 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_range_in_synpred26_Script589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred44_Script922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred46_Script976 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_args_in_synpred46_Script980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred61_Script1281 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred61_Script1284 = new BitSet(new long[]{0x000001E800405A30L});
    public static final BitSet FOLLOW_NEWLINE_in_synpred61_Script1286 = new BitSet(new long[]{0x000001E800405A10L});
    public static final BitSet FOLLOW_dictionary_elements_in_synpred61_Script1291 = new BitSet(new long[]{0x0000000000000002L});

}