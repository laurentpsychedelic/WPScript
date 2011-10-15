package editor.filefilters;

import editor.ScriptWindow;
import java.io.File;
/**
 * <B>WPSスクリプトファイルのファイルフィルター</B>のクラス。<BR>
 * @author Laurent FABRE, Photonic Lattice, Inc.
 */
public class WpsFileFilter extends javax.swing.filechooser.FileFilter {
    /**
     * このフィルターでファイルチューザのファイルが表示されるかどうか。
     * @param f ファイル。
     * @return OK・NG。
     */
    @Override
    public boolean accept(File f) {
        return (f.isDirectory() || f.getName().toLowerCase().endsWith(".wps"));
    }
    /**
     * テキストファイルの説明を返す。
     * @return 説明。
     */
    @Override
    public String getDescription() {
        return explanation[ScriptWindow.getLanguage()]+extension;
    }
    /**
     * ファイルの説明。
     */
    private static final String [] explanation = {"WPSスクリプトファイル ","WPS script file "};
    /**
     * フィルターがファイルをWPSスクリプトファイルと認めるか。
     * @param f ファイル。
     * @return OK・NG。
     */
    public boolean isWps(File f){
        return (f.getName().toLowerCase().endsWith(extension));
    }
    /**
     * テキストファイルの拡張子。
     */
    public static final String extension=".wps";
}
