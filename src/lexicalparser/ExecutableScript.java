/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 *
 * @author laurent
 */
public class ExecutableScript {
    GrammarParser parser;
    private GrammarParser.prog_return tree;
    private boolean compilation_ok = false;
    public ExecutableScript(String program) throws CompilationErrorException  {
        GrammarLexer lex = new GrammarLexer(new ANTLRStringStream(program));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        parser = new GrammarParser(tokens);

        try {
            tree = parser.prog();            
        } catch (RecognitionException e)  {
            parser._WPAScriptCompilationError("Compilation failed! Wrong syntax", e.line);
            throw new CompilationErrorException("Compilation failed! Wrong syntax", e.line);
        }
        compilation_ok = parser.compilationCheck();
        if (!compilation_ok) {
            parser._WPAScriptCompilationError("Compilation failed! Semantic error]", parser.line_number);
            throw new CompilationErrorException("Compilation failed! Semantic error]", parser.line_number);
        }
    }
    public Object execute() {
        Object result = null;
        if (compilation_ok) {
            result = parser.execute();
        } else {
            parser._WPAScriptRuntimeError("Cannot execute! [compilation failed]", 0);
        }
        return result;
    }
    public void dumpGlobalMemory() {
        parser.dumpGlobalMemory(System.out);
    }
    public void printTree() {
        if (tree!=null) {
            System.out.println("\nTREE : \n" + ((Tree)tree.tree).toStringTree());
        }
    }
    public void dumpCommands() {
        if (compilation_ok) {
            parser.dumpScriptCommands();
        }
    }
}
