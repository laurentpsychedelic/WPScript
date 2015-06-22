package org.lpsy.wpscript.editor;

import org.lpsy.wpscript.editor.filefilters.WpsFileFilter;
import org.lpsy.wpscript.editor.scriptio.ScriptIO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.executable.ExecutableScript;
import org.lpsy.wpscript.language.ScriptLexer;
import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;

/**
 * @author Laurent FABRE, 2011-2015
 * Created on 2011/09/16, 16:14:08
 */
public class ScriptWindow extends javax.swing.JDialog {
    public static String DEFAULT_SCRIPT_FOLDER;
    private static final String path;
    static {
        // Persistence
        String _path = ScriptWindow.class.getResource("").getPath();
        if (_path.contains(":")) {
            _path = _path.split(":")[1];
        }
        if (_path.contains("!")) {
            _path = _path.split("!")[0];
        }
        if (_path.contains("/build/classes")) {
            _path = _path.split("/build/classes")[0];
        }
        File file = new File(_path);
        if (!file.isDirectory()) {
            _path = file.getParent();
        }
        path = _path;
        String ini_file_path = path + "/editor.ini";
        if (path!=null && (new File(ini_file_path)).exists()) {
            readIniFile(ini_file_path);
        }
    }
    public static void writeIniFile(String filepath) {
        try {
            java.util.Properties prop2 = new java.util.Properties();
            prop2.setProperty("DEFAULT_SCRIPT_FOLDER", DEFAULT_SCRIPT_FOLDER==null ? path : DEFAULT_SCRIPT_FOLDER);
            prop2.store(new java.io.FileOutputStream(filepath),"Editor Window persistence data");
        } catch (java.io.IOException e) {
            System.err.println("Error while writing editor window persistence file at [" + filepath + "]");
        }
    }

    public static void readIniFile(String filepath) {
        try {
            java.util.Properties prop = new java.util.Properties();
            prop.load(new java.io.FileInputStream(filepath));
            DEFAULT_SCRIPT_FOLDER = prop.getProperty("DEFAULT_SCRIPT_FOLDER","");
        } catch (java.io.IOException e) {
            System.err.println("Error while reading editor window persistence file at [" + filepath + "]");
        }
    }

    BufferedReader reader_out;
    BufferedReader reader_err;
    public static int getLanguage() {
        return LanguageInformation.LANGUAGE;
    }
    public static String getString(int i) {
        return LanguageInformation.getString(i);
    }
    private static class LanguageInformation {
        static final int JAPANESE = 0;
        static final int ENGLISH = 1;
        private static int LANGUAGE = JAPANESE;
        static String [][] StrLst = {
        /* 0*/ { "WPAScript Ver. 0.1 :: エディターウィンドウ", "WPAScript Ver. 0.1 :: Editor Window" },
        /* 1*/ { "ビルド（B）", "Build (B)" },
        /* 2*/ { "実行（R）", "Run (R)" },
        /* 3*/ { "デバッグ出力（D）", "Debug output (D)" },
        /* 4*/ { "スクリプトを開く", "Open script file" },
        /* 5*/ { "開く（O）", "Open (O)" },
        /* 6*/ { "", "" },
        /* 7*/ { "", "" },
        /* 8*/ { "", "" },
        /* 9*/ { "", "" },
        /*10*/ { "", "" }
        };
        static String getString(int i) {
            return StrLst[i][LANGUAGE];
        }
    }

    private static final int W = 690;
    private static final int H = 380;

    private boolean __DEBUG__ = true;

    private PrintStream default_out = System.out;
    private PrintStream default_err = System.err;

    /** Creates new form TestFrame */
    public ScriptWindow(java.awt.Window parent, final String _prog) {
        super(parent, java.awt.Dialog.ModalityType.MODELESS);

        prog = _prog;

        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/org/lpsy/wpscript/resources/icon.png")).getImage());

        setTitle(LanguageInformation.getString(0));
        getContentPane().setBackground(new Color(51, 51, 51));
        setSize(W, H);

        jCheckBoxDebug.setSelected(__DEBUG__);

        boolean REDIRECT_STREAMS = true;
        if (REDIRECT_STREAMS) {
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

            _start_deamon();


        }

        _updateScriptPane();

        _addKeyboardShortcuts();
    }

    private void _addKeyboardShortcuts() {
        AbstractAction act_compile = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButtonCompilation.doClick();
            }
        };
        jScrollPaneScript.getActionMap().put("name_compile", act_compile);
        InputMap im = jScrollPaneScript.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK), "name_compile");

        AbstractAction act_run = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButtonExecute.doClick();
            }
        };
        jScrollPaneScript.getActionMap().put("name_run", act_run);
        im = jScrollPaneScript.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), "name_run");

        AbstractAction act_read = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButtonOpen.doClick();
            }
        };
        jScrollPaneScript.getActionMap().put("name_open", act_read);
        im = jScrollPaneScript.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "name_open");

        AbstractAction act_debug_on_off = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jCheckBoxDebug.doClick();
            }
        };
        jScrollPaneScript.getActionMap().put("name_debug_on_off", act_debug_on_off);
        im = jScrollPaneScript.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "name_debug_on_off");
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

    String prog = "\n";

    private void _updateScriptPane() {
        int cp = jScriptPane.getCaretPosition();

        jScriptPane.setText("");

        ScriptLexer lex = new ScriptLexer(new ANTLRStringStream(prog));
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
            case ScriptLexer.CONSTANT :
                style = "constant";
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
            case ScriptLexer.FUNCTION :
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
            case ScriptLexer.LINE_COMMENT ://FALL-THROUGH
            case ScriptLexer.BLOCK_COMMENT :
                style = "comment";
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

        Style comment = doc.addStyle("comment", def);
        StyleConstants.setItalic(comment, true);
        StyleConstants.setForeground(comment, Color.gray);

        Style bool = doc.addStyle("bool", regular);
        StyleConstants.setForeground(bool, Color.blue);

        Style word = doc.addStyle("word", regular);
        StyleConstants.setBold(word, true);
        StyleConstants.setItalic(word, true);

        Style constant = doc.addStyle("constant", word);
        StyleConstants.setForeground(constant, Color.red);

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
        jButtonOpen = new javax.swing.JButton();
        jButtonIcon = new javax.swing.JButton();
        jLabelIcon = new javax.swing.JLabel();
        jCheckBoxDebug = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jScriptPane.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jScriptPaneFocusLost(evt);
            }
        });
        jScriptPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jScriptPaneKeyTyped(evt);
            }
        });
        jScrollPaneScript.setViewportView(jScriptPane);

        getContentPane().add(jScrollPaneScript);
        jScrollPaneScript.setBounds(12, 12, 535, 189);

        jButtonExecute.setFont(new java.awt.Font("MS UI Gothic", 0, 10)); // NOI18N
        jButtonExecute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/lpsy/wpscript/resources/run.png"))); // NOI18N
        jButtonExecute.setText(getString(2));
        jButtonExecute.setToolTipText(getString(2));
        jButtonExecute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonExecute.setMargin(new java.awt.Insets(0, 4, 0, 0));
        jButtonExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExecuteActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExecute);
        jButtonExecute.setBounds(550, 177, 117, 24);

        jScrollPaneMessages.setBackground(java.awt.Color.lightGray);

        jMessagesPane.setBackground(java.awt.Color.lightGray);
        jMessagesPane.setEditable(false);
        jScrollPaneMessages.setViewportView(jMessagesPane);

        getContentPane().add(jScrollPaneMessages);
        jScrollPaneMessages.setBounds(12, 207, 660, 130);

        jButtonCompilation.setFont(new java.awt.Font("MS UI Gothic", 0, 10)); // NOI18N
        jButtonCompilation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/lpsy/wpscript/resources/compile.png"))); // NOI18N
        jButtonCompilation.setText(getString(1));
        jButtonCompilation.setToolTipText(getString(1));
        jButtonCompilation.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCompilation.setMargin(new java.awt.Insets(0, 4, 0, 0));
        jButtonCompilation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompilationActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCompilation);
        jButtonCompilation.setBounds(550, 153, 117, 24);

        jButtonOpen.setFont(new java.awt.Font("MS UI Gothic", 0, 10)); // NOI18N
        jButtonOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/lpsy/wpscript/resources/open_file_small.png"))); // NOI18N
        jButtonOpen.setText(getString(5));
        jButtonOpen.setToolTipText(getString(4));
        jButtonOpen.setFocusable(false);
        jButtonOpen.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonOpen);
        jButtonOpen.setBounds(550, 130, 100, 22);

        jButtonIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/lpsy/wpscript/resources/icon.png"))); // NOI18N
        jButtonIcon.setBorder(null);
        jButtonIcon.setEnabled(false);
        getContentPane().add(jButtonIcon);
        jButtonIcon.setBounds(640, 30, 0, 0);

        jLabelIcon.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        jLabelIcon.setForeground(new java.awt.Color(216, 197, 255));
        jLabelIcon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIcon.setText("WPScript");
        jLabelIcon.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabelIcon);
        jLabelIcon.setBounds(560, 10, 110, 50);

        jCheckBoxDebug.setFont(new java.awt.Font("MS UI Gothic", 0, 10)); // NOI18N
        jCheckBoxDebug.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxDebug.setText(getString(3));
        jCheckBoxDebug.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckBoxDebug.setOpaque(false);
        jCheckBoxDebug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxDebugActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBoxDebug);
        jCheckBoxDebug.setBounds(550, 100, 120, 17);

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
        } else {
            System.err.println("No valid script to execute!");
        }
    }//GEN-LAST:event_jButtonExecuteActionPerformed

    private void _updateScriptPaneLater() {
        SwingUtilities.invokeLater(update_script_pane);
    }

    private Runnable update_script_pane = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                //NOTHING
            }
            prog = jScriptPane.getText();
            _updateScriptPane();
        }
    };

    private void jScriptPaneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScriptPaneKeyTyped
        if (evt.getKeyChar()=='\n') {
            _updateScriptPaneLater();
        }
    }//GEN-LAST:event_jScriptPaneKeyTyped


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        CLOSING = true;
        //Reset streams
        System.setOut(default_out);
        System.setErr(default_err);
        //Save INI file
        String ini_file_path = path + "/editor.ini";
        writeIniFile(ini_file_path);

    }//GEN-LAST:event_formWindowClosing

    private void jButtonCompilationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompilationActionPerformed
        prog = jScriptPane.getText();
        jMessagesPane.setText("");
        script = null;
        try {
            script = new ExecutableScript(prog, __DEBUG__);
        } catch (CompilationErrorException cee) {
            //NOTHING
        } catch (PanicException pe) {
            //NOTHING
        }
    }//GEN-LAST:event_jButtonCompilationActionPerformed

    private void jScriptPaneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScriptPaneFocusLost
        _updateScriptPaneLater();
    }//GEN-LAST:event_jScriptPaneFocusLost

    private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenActionPerformed

        final JFileChooser fc = new JFileChooser();
        if (DEFAULT_SCRIPT_FOLDER != null) {
            fc.setCurrentDirectory(new File(DEFAULT_SCRIPT_FOLDER));
        }
        fc.setAcceptAllFileFilterUsed(false);

        WpsFileFilter filter = new WpsFileFilter();
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);


        fc.setMultiSelectionEnabled(true);
        fc.setApproveButtonText(getString(5));
        fc.setDialogTitle(getString(4));
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (!file.exists()) {
                return;
            }
            DEFAULT_SCRIPT_FOLDER = file.getParent();

            String filepath = file.getAbsolutePath();
            if (filter.isWps(file)) {
                prog = ScriptIO.readScript(filepath);
                if (!prog.endsWith("\n")) {
                    prog += "\n";
                }
                _updateScriptPane();
            }
        }
    }//GEN-LAST:event_jButtonOpenActionPerformed

    private void jCheckBoxDebugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxDebugActionPerformed
        __DEBUG__ = jCheckBoxDebug.isSelected();
    }//GEN-LAST:event_jCheckBoxDebugActionPerformed

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

        final String _prog = "";

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScriptWindow(null, _prog).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCompilation;
    private javax.swing.JButton jButtonExecute;
    private javax.swing.JButton jButtonIcon;
    private javax.swing.JButton jButtonOpen;
    private javax.swing.JCheckBox jCheckBoxDebug;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JTextPane jMessagesPane;
    private javax.swing.JTextPane jScriptPane;
    private javax.swing.JScrollPane jScrollPaneMessages;
    private javax.swing.JScrollPane jScrollPaneScript;
    // End of variables declaration//GEN-END:variables
}
