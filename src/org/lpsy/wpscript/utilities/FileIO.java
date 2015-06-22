package org.lpsy.wpscript.utilities;

import java.io.*;

/**
 * Script I/O
 * @author Laurent FABRE, 2011-2015
 */
public class FileIO {

    public static String readScript(String filepath) {
	String script = "";
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String line = br.readLine();
	    while (line != null) {
		script += line + "\n";
		line = br.readLine();
	    }
	    in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	return script;
    }

}
