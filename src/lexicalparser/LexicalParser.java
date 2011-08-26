/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalparser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LFabre
 */
public class LexicalParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            GrammarParser.main(new String [] {"1-2\n"});
        } catch (Exception ex) {
            Logger.getLogger(LexicalParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
