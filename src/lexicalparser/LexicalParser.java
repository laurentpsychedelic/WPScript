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
        String program = "myFunc(newTitle)\n";
                
        GrammarLexer lex = new GrammarLexer(new ANTLRStringStream(program));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        GrammarParser parser = new GrammarParser(tokens);

        try {
            GrammarParser.prog_return r = parser.prog();
            
            System.out.println("\n\nTREE : \n" + ((Tree)r.tree).toStringTree());
            
            System.out.println("\n\n");
            for (Object o : parser.memory.keySet()) {
                System.out.println("VAR [" + o + "]->" + parser.memory.get(o));
            }
            
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}
