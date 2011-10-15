/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editor.scriptio;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Laurent_dev
 */
public class ScriptIO {
    public static String readScript(String filepath) {
        StringBuilder str = new StringBuilder();
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String line = "";
            while (line != null) {
                line = br.readLine();
                if (line!=null) {
                    str.append(line).append("\n");
                }
            }
            in.close();
        } catch (IOException e) {
            //NOTHING
        } finally {
            return str.toString();
        }
    }
}
