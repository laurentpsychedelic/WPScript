package commandline;

import language.exceptions.PanicException;
import language.exceptions.RuntimeErrorException;
import language.exceptions.CompilationErrorException;
import language.executable.ExecutableScript;
import editor.ScriptWindow;
import utilities.FileIO;

/**
 *
 * @author laurent
 */
public class Main {
    public static final String VERSION = "1.0";
    public static void printMan() {
	System.err.println("TODO write manual...");
    }
    public static void executeScript(String prog) {
	try {
	    ExecutableScript script = new ExecutableScript(prog);
            script.printTree();
            script.dumpCommands();
	    try {
                script.execute();
            } catch (PanicException pe) {
                pe.printStackTrace();
            } catch (RuntimeErrorException re) {
                re.printStackTrace();
            }
            script.dumpGlobalMemory();
        } catch (CompilationErrorException cee) {
            cee.printStackTrace();
        } catch (PanicException pe) {
            pe.printStackTrace();
        }
    }
    public static void main(String [] args) {
	if (args.length==0) { //SHOW SCRIPT WINDOW
	    ScriptWindow.main(new String [] {});
	} else {
	    if (args.length==1) { //EXECUTE SCRIPT FROM FILE
		String filepath = args[0];
		String script = FileIO.readScript(filepath);
		executeScript(script);
	    } else if (args.length==2) { //EXECUTE COMMAND
		if (args[0].equals("-c")) {
		    String script = args[1];
		    executeScript(script);
		} else {
		    System.err.println("Unknown option " + args[0] + "...");
		    printMan();
		}
	    } else {
		System.err.println("Wrong number of arguments....");
		printMan();
	    }
	}
    }
}
