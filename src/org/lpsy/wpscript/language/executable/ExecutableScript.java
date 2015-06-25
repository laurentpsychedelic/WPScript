package org.lpsy.wpscript.language.executable;

import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.ScriptLexer;
import org.lpsy.wpscript.language.ScriptParser;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class ExecutableScript {
    ScriptParser parser;
    private ScriptParser.prog_return tree;
    private boolean compilation_ok = false;
    private String program;
    public ExecutableScript(String program) throws CompilationErrorException, PanicException {
	this(program, false);
    }
    public ExecutableScript(String program, boolean __DEBUG__) throws CompilationErrorException, PanicException {
	if (!program.endsWith("\n")) {
            program = program + "\n";
        }

        ScriptLexer lex = new ScriptLexer(new ANTLRStringStream(program));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        this.program = tokens.toString();

        parser = new ScriptParser(tokens);
	parser.__DEBUG__ = __DEBUG__;

        try {
	    if (parser.__DEBUG__) {
		System.out.println("COMPILATION...");
            }
	    tree = parser.prog();
	    if (parser.__DEBUG__) {
		System.out.println(" OVER");
	    }
        } catch (RecognitionException e)  {
            e.printStackTrace();//TODO remove
            parser.compilationError("Compilation failed! Wrong syntax", e.line);
            throw new CompilationErrorException("Compilation failed! Wrong syntax", e.line);
        }

        parser.treeRefactoring();
	parser.setEnv();

        if (parser.__DEBUG__) {
	    parser.dumpScriptCommands();
        }

        compilation_ok = parser.compilationCheck();
        if (!compilation_ok) {
            parser.compilationError("Compilation failed! Semantic error]", parser.getLineNumber());
        }
    }
    public Object execute() throws PanicException, RuntimeErrorException {
        Object res =  parser.execute();
	if (parser.__DEBUG__) {
	    parser.dumpGlobalMemory(System.out);
	}
	return res;
    }
    public void dumpGlobalMemory() {
        parser.dumpGlobalMemory(System.out);
    }
    public void printTree() {
        if (tree!=null) {
            System.out.println("\nTREE : \n" + ((Tree)tree.getTree()).toStringTree());
        }
    }
    public void dumpCommands() {
        if (compilation_ok) {
            parser.dumpScriptCommands();
        }
    }
    @Override
    public String toString() {
        return this.program;
    }
}
