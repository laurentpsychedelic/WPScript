/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 *
 * @author LFabre
 */
public class LexicalParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        //String program = "a=1\nb=a\na=2\nb\n";
        //String program = "myfunc(1)\n";
        //String program = "myFunc(newTitle)\n";
        String program = "a=1+3.4\n"
                       //+ "b=a / 1.9+3\n"
                       + "b=newMeasurementSet(a,a)\n"
                       //+ "b=a\n"
                       + "c=true\n"
                       + "c=false\n"
                       + "if (c)\n"
                       + "  if_var=1\n"
                       + "else \n"
                       + "  else_var=1+1\n"
                       + "\n";
                       ;
        GrammarLexer lex = new GrammarLexer(new ANTLRStringStream(program));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        GrammarParser parser = new GrammarParser(tokens);

        try {
            GrammarParser.prog_return r = parser.prog();
            
            System.out.println("\nTREE : \n" + ((Tree)r.tree).toStringTree());
            
            parser.dumpScriptCommands();
            System.out.flush();
            /*System.err.flush();
            System.out.println();
            parser.compilationCheck();
            */
            boolean compilation_ok = parser.compilationCheck();
            if (compilation_ok) {
                parser.execute();
                parser.dumpGlobalMemory(System.out);
            }
            
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}
