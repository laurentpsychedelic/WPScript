package utilities;

import java.io.*;

/**
 * <B>スクリプト入出力</B>のためののクラス。<BR>
 * @author Laurent FABRE
 */
public class FileIO {

    public static String readScript(String filepath) {
	String script = "";
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String line = br.readLine();
	    while (line!=null) {
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