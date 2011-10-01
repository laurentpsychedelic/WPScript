/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TestFrame.java
 *
 * Created on 2011/09/16, 16:14:08
 */
package editor;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import language.exceptions.CompilationErrorException;
import language.executable.ExecutableScript;
import language.ScriptLexer;
import language.exceptions.PanicException;
import language.exceptions.RuntimeErrorException;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;

/**
 *
 * @author LFabre
 */
public class ScriptWindow extends javax.swing.JFrame {
    BufferedReader reader_out;
    BufferedReader reader_err;
    
    private static class LanguageInformation {
        static final int JAPANESE = 0;
        static final int ENGLISH = 1;
        static int LANGUAGE = JAPANESE;
        static String [][] StrLst = {
        /*0*/ { "WPAScript Ver. 0.1 :: エディターウィンドウ", "WPAScript Ver. 0.1 :: Editor Window" },
        /*1*/ { "コンパイル", "Compile" },
        /*2*/ { "実行", "Run" },
        /*3*/ { "", "" },
        };
        static String getString(int i) {
            return StrLst[i][LANGUAGE];
        }
    }
    
    private static final int W = 690;
    private static final int H = 380;
    
    /** Creates new form TestFrame */
    public ScriptWindow() {
        initComponents();
        
        setTitle(LanguageInformation.getString(0));
        getContentPane().setBackground(new Color(51, 51, 51));    
        setSize(W, H);
        
        PipedInputStream pIn_out = null;
        PipedInputStream pIn_err = null;
        PipedOutputStream pOut_out = new PipedOutputStream();
        PipedOutputStream pOut_err = new PipedOutputStream();
        PrintStream my_stream_out = new PrintStream(pOut_out);
        PrintStream my_stream_err = new PrintStream(pOut_err);
        System.setOut(my_stream_out);
        System.setErr(my_stream_err);
        try {
            pIn_out = new PipedInputStream(pOut_out);
            pIn_err = new PipedInputStream(pOut_err);
        } catch (IOException ex) {
            Logger.getLogger(ScriptWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        reader_out = new BufferedReader(new InputStreamReader(pIn_out));
        reader_err = new BufferedReader(new InputStreamReader(pIn_err));
        // Write to standard output and error and the log files
         
        StyledDocument doc = jScriptPane.getStyledDocument();
        _addStylesToScriptPaneDocument(doc);
        doc = jMessagesPane.getStyledDocument();
        _addStylesToMessagesPaneDocument(doc);
        
        _updateScriptPane();
        
        _start_deamon();
    }
    
    private void _start_deamon() {
        Thread t = new Thread(deamon_out);
        t.setDaemon(true);
        t.start();
        Thread t2 = new Thread(deamon_err);
        t2.setDaemon(true);
        t2.start();
    }
    
    boolean CLOSING = false;
    
    Runnable deamon_out = new Runnable() {

        @Override
        public void run() {
            while (!CLOSING) {
                if (reader_out!=null) {
                    try {
                        String line = reader_out.readLine();
                        if(line != null) {
                            StyledDocument doc = jMessagesPane.getStyledDocument();
                            try {
                                doc.insertString(doc.getLength(), line+"\n", doc.getStyle("regular"));
                            } catch (BadLocationException ex) {
                                //TODO
                            }
                        }
                    } catch (IOException ex) {
                        //NOTHING
                    }
                }
            }
        }
    };
    
    Runnable deamon_err = new Runnable() {

        @Override
        public void run() {
            while (!CLOSING) {
                if (reader_err!=null) {
                    try {
                        String line = reader_err.readLine();
                        if(line != null) {
                            StyledDocument doc = jMessagesPane.getStyledDocument();
                            try {
                                doc.insertString(doc.getLength(), line+"\n", doc.getStyle("error"));
                            } catch (BadLocationException ex) {
                                //TODO
                            }
                        }
                    } catch (IOException ex) {
                        //NOTHING
                    }
                }
            }
        }
    };
    
    //String prog = "a = 2 + 1\n";
    
    //String prog = "a = { \"mode\" : \"triple\",\n\"accuracy\" : \"standard\",\n \"object\" : newMeasurementSet(1,2),\n\"number\" : 1+2 }\n";
    
    //String prog = "a = 2 * \"string\" + \"yo\" * 3\n";
    
    //String prog = "a=0\nwhile (a<2) {\n    print(a)\n    a = a + 1\n}\n";
    
    //String prog = "a=1\nb=a++\n";
    //String prog = "for (a = 0; a <= 2; a++) {\n    print(a)\n}\n";
    String prog = "for (a=10->-2->-10) {\n    print(\"Number a = \" + a)\n    if (a==0) {\n        a=-5\n    } else if (a == -7) {\n        print(\"7\")\n        continue\n    }\n    print(\"loop\")\n\n}\n";
   
    //String prog = "a=0\nprint(a)\n";
    
    //String prog = "a = 0\nb = a==0\nc= b & false\n";
    
    /*String prog = "a = 1 + 3.4\n"
                       //+ "b=a / 1.9+3\n"
                       + "b = newMeasurementSet(a, a)\n"
                       //+ "b=a\n"
                       + "c = true\n"
                       + "c = false\n"
                       + "if (c) {\n"
                       + "    if_var = 1\n"
                       + "} else {\n"
                       + "    else_var = \"my_string\"\n"
                       + "}\n";*/
    
    private void _updateScriptPane() {
        int cp = jScriptPane.getCaretPosition();
        
        jScriptPane.setText("");
        
        ScriptLexer lex = new ScriptLexer(new ANTLRStringStream(prog));
        lex.skip_ws = false;
        CommonTokenStream tokens = new CommonTokenStream(lex);
        
        LinkedList<String> initStyles = new LinkedList();
        LinkedList<String> initString = new LinkedList();
                
        List lt = tokens.getTokens();
        for (Object t : lt) {
            int type = ((Token)t).getType();
            String text = ((Token)t).getText();
            String style = _getStyle(type);
            initStyles.add(style);
            initString.add(text);
        }
       
        StyledDocument doc = jScriptPane.getStyledDocument();
        
        try {
            for (int i=0; i < initString.size(); i++) {
                doc.insertString(doc.getLength(), initString.get(i),
                                 doc.getStyle(initStyles.get(i)));
            }
        } catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text into text pane.");
        }
        
        try {
            jScriptPane.setCaretPosition(cp);
        } catch (IllegalArgumentException e) {
            //NOTHING
        }
    }
    
    private String _getStyle(int token_type) {
        String style = "regular";
        switch (token_type) {
            case ScriptLexer.ID :
                style = "word";
                break;            
            case ScriptLexer.NUM :
                style = "number";
                break;
            case ScriptLexer.STRING_LITERAL:
                style = "string_literal";
                break;
            case ScriptLexer.BOOL :
                style = "bool";
                break;
            case ScriptLexer.IF ://FALL-THROUGH
            case ScriptLexer.ELSE :
            case ScriptLexer.WHILE :
            case ScriptLexer.FOR :
                style = "if_else_loop";
                break;
            case ScriptLexer.BREAK ://FALL-THROUGH
            case ScriptLexer.CONTINUE :
                style = "break_continue";
                break;
            case ScriptLexer.PLUS ://FALL-THROUGH
            case ScriptLexer.PLUS_PLUS:
            case ScriptLexer.MINUS :
            case ScriptLexer.MINUS_MINUS:
            case ScriptLexer.MULT :
            case ScriptLexer.DIV :
            case ScriptLexer.CMP_EQ:
            case ScriptLexer.CMP_NEQ:
            case ScriptLexer.CMP_LT:
            case ScriptLexer.CMP_LT_EQ:
            case ScriptLexer.CMP_GT:
            case ScriptLexer.CMP_GT_EQ:
            case ScriptLexer.AND:
            case ScriptLexer.OR:
            case ScriptLexer.ARROW:
                style = "operator";
                break;
            case ScriptLexer.LEFT_B ://FALL-THROUGH
            case ScriptLexer.RIGHT_B :
            case ScriptLexer.LEFT_CB :
            case ScriptLexer.RIGHT_CB :
            case ScriptLexer.LEFT_P :
            case ScriptLexer.RIGHT_P :
            case ScriptLexer.COMMA :
            case ScriptLexer.EQUAL :
            case ScriptLexer.TP:
            case ScriptLexer.PV:
            case ScriptLexer.DQUOTE :
                style = "punctuation";
                break;
            case ScriptLexer.NEWLINE ://FALL-THROUGH
            case ScriptLexer.EOF :
            case ScriptLexer.WS :
                style = "regular";
                break;
            default:
                style = "unknown";
                break;
        }
        return style;
    }
    
    private static final int font_size_script = 16; 
    
    private void _addStylesToScriptPaneDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");
        StyleConstants.setFontSize(def, font_size_script);
        
        Style bool = doc.addStyle("bool", regular);
        StyleConstants.setForeground(bool, Color.blue);
        
        Style word = doc.addStyle("word", regular);
        StyleConstants.setBold(word, true);
        StyleConstants.setItalic(word, true);
        
        Style number = doc.addStyle("number", regular);
        StyleConstants.setItalic(number, true);
        StyleConstants.setForeground(number, Color.magenta);
        
        Style if_else = doc.addStyle("if_else_loop", regular);
        StyleConstants.setForeground(if_else, Color.blue);
        
        Style break_continue = doc.addStyle("break_continue", if_else);
        StyleConstants.setForeground(break_continue, Color.red);
        
        Style operator = doc.addStyle("operator", regular);
        StyleConstants.setForeground(operator, new Color(0, 0, 128));
        StyleConstants.setBold(operator, true);
        
        Style punctuation = doc.addStyle("punctuation", operator);
        StyleConstants.setForeground(punctuation, Color.gray);
        
        Style string_literal = doc.addStyle("string_literal", operator);
        StyleConstants.setForeground(string_literal, Color.orange);
        StyleConstants.setItalic(string_literal, true);
        
        Style unknown = doc.addStyle("unknown", regular);
        StyleConstants.setItalic(unknown, true);
        StyleConstants.setForeground(unknown, Color.red);
        StyleConstants.setStrikeThrough(unknown, true);
        //"bool"
        //"word"
        //"number"
        //"if_else"
        //"regular"
        //"operator"
        //"punctuation"
        //"unknown"

    }
    
    private final int font_size_messages = 10;
    
    private void _addStylesToMessagesPaneDocument(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");
        StyleConstants.setForeground(regular, Color.gray);
        StyleConstants.setFontSize(def, font_size_messages);
        
        Style error = doc.addStyle("error", regular);
        StyleConstants.setForeground(error, Color.red);
        StyleConstants.setBold(error, true);
        
        //"regular"
        //"error"
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneScript = new javax.swing.JScrollPane();
        jScriptPane = new javax.swing.JTextPane();
        jButtonExecute = new javax.swing.JButton();
        jScrollPaneMessages = new javax.swing.JScrollPane();
        jMessagesPane = new javax.swing.JTextPane();
        jButtonCompilation = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jScrollPaneScript.setBackground(new java.awt.Color(202, 223, 210));

        jScriptPane.setBackground(new java.awt.Color(202, 223, 210));
        jScriptPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jScriptPaneKeyTyped(evt);
            }
        });
        jScrollPaneScript.setViewportView(jScriptPane);

        getContentPane().add(jScrollPaneScript);
        jScrollPaneScript.setBounds(12, 12, 505, 189);

        jButtonExecute.setText(LanguageInformation.getString(2));
        jButtonExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExecuteActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExecute);
        jButtonExecute.setBounds(520, 170, 147, 34);

        jScrollPaneMessages.setBackground(java.awt.Color.lightGray);

        jMessagesPane.setBackground(java.awt.Color.lightGray);
        jMessagesPane.setEditable(false);
        jScrollPaneMessages.setViewportView(jMessagesPane);

        getContentPane().add(jScrollPaneMessages);
        jScrollPaneMessages.setBounds(12, 207, 660, 130);

        jButtonCompilation.setText(LanguageInformation.getString(1));
        jButtonCompilation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompilationActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCompilation);
        jButtonCompilation.setBounds(520, 140, 147, 34);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ExecutableScript script;
    
    private void jButtonExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExecuteActionPerformed
        jMessagesPane.setText("");
        if (script!=null) {
            try {
                script.execute();
            } catch (PanicException pe) {
                //NOTHING
            } catch (RuntimeErrorException re) {
                //NOTHING
            }
            script.dumpGlobalMemory();
        } else {
            System.err.println("No valid script to execute!");
        }
    }//GEN-LAST:event_jButtonExecuteActionPerformed

    private void jScriptPaneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScriptPaneKeyTyped
        if (true){//evt.getKeyCode() == KeyEvent.VK_SPACE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            prog = jScriptPane.getText(); 
            _updateScriptPane();
        }
    }//GEN-LAST:event_jScriptPaneKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        CLOSING = true;
    }//GEN-LAST:event_formWindowClosing

    private void jButtonCompilationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompilationActionPerformed
        prog = jScriptPane.getText();
        if (!prog.endsWith("\n")) {
            prog = prog + "\n";
        }
        jMessagesPane.setText("");
        script = null;
        try {
            script = new ExecutableScript(prog);
            script.printTree();
            script.dumpCommands();
        } catch (CompilationErrorException cee) {
            //NOTHING
        } catch (PanicException pe) {
            //NOTHING
        }
    }//GEN-LAST:event_jButtonCompilationActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ScriptWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScriptWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScriptWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScriptWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
          
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScriptWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCompilation;
    private javax.swing.JButton jButtonExecute;
    private javax.swing.JTextPane jMessagesPane;
    private javax.swing.JTextPane jScriptPane;
    private javax.swing.JScrollPane jScrollPaneMessages;
    private javax.swing.JScrollPane jScrollPaneScript;
    // End of variables declaration//GEN-END:variables
}
