package org.lpsy.wpscript.editor.filefilters;

import org.lpsy.wpscript.editor.ScriptWindow;
import java.io.File;
/**
 * WPScript script typre file filter
 * @author Laurent FABRE, 2011-2015
 */
public class WpsFileFilter extends javax.swing.filechooser.FileFilter {
    @Override
    public boolean accept(File f) {
        return (f.isDirectory() || f.getName().toLowerCase().endsWith(".wps"));
    }
    @Override
    public String getDescription() {
        return "WPScript script file";
    }
    public boolean isWps(File f) {
        return (f.getName().toLowerCase().endsWith(extension));
    }
    public static final String extension = ".wps";
}
