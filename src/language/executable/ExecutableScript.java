/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.executable;

import language.exceptions.CompilationErrorException;
import language.exceptions.PanicException;
import language.ScriptLexer;
import language.ScriptParser;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 *
 * @author laurent
 */
public class ExecutableScript {
    ScriptParser parser;
    private ScriptParser.prog_return tree;
    private boolean compilation_ok = false;
    public ExecutableScript(String program) throws CompilationErrorException, PanicException {
        ScriptLexer lex = new ScriptLexer(new ANTLRStringStream(program));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        parser = new ScriptParser(tokens);

        try {
            tree = parser.prog();            
        } catch (RecognitionException e)  {
            e.printStackTrace();//TODO remove
            parser.compilationError("Compilation failed! Wrong syntax", e.line);
            throw new CompilationErrorException("Compilation failed! Wrong syntax", e.line);
        }
        
        //System.out.println("COMMANDS BEFORE SIMPILIFICATION");
        //parser.dumpScriptCommands();
        parser.treeRefactoring();
        //System.out.println("COMMANDS AFTER SIMPILIFICATION");
        //parser.dumpScriptCommands();
        
        compilation_ok = parser.compilationCheck();
        if (!compilation_ok) {
            parser.compilationError("Compilation failed! Semantic error]", parser.getLineNumber());
            throw new CompilationErrorException("Compilation failed! Semantic error]", parser.getLineNumber());
        }
    }
    public Object execute() throws PanicException {
        Object result = null;
        if (compilation_ok) {
            result = parser.execute();
        } else {
            parser.runtimeError("Cannot execute! [compilation failed]", 0);
        }
        return result;
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
}
