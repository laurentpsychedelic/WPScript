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
    public static void printVersion() {
	System.err.println("TODO write version...");
    }
    public static void executeScript(String prog, boolean DEBUG) {
	try {
	    ExecutableScript script = new ExecutableScript(prog, DEBUG);
	    try {
                script.execute();
            } catch (PanicException pe) {
                pe.printStackTrace();
            } catch (RuntimeErrorException re) {
                re.printStackTrace();
            }
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
	    boolean __DEBUG__ = false;
	    boolean __PRINT_COMMAND__ = false;
	    for (String arg : args) {
		if (arg.equals("--debug")) {
		    __DEBUG__ = true;
		} else if (arg.equals("--version")) {
		    __PRINT_COMMAND__ = true;
		    printVersion();
		} else if (arg.equals("--help") || arg.equals("--man")) {
		    __PRINT_COMMAND__ = true;
		    printMan();
		}
	    }
	    if (!__PRINT_COMMAND__) {
		if (__DEBUG__) {
		    if (args.length<2) {
			System.err.println("Not enough arguments...");
			printMan();
			return;
		    }
		}
		for (int i=0; i<args.length; i++) {
		    String arg = args[i];
		    if (arg.equals("-c")) {
			if (i+1<args.length) {
			    String script = args[i+1];
			    executeScript(script, __DEBUG__);
			    break;
			} else {
			    System.err.println("No command specified after -c...");
			    printMan();
			    break;
			}
		    } else if (!arg.startsWith("-")) {
			String filepath = arg;
			String script = FileIO.readScript(filepath);
			executeScript(script, __DEBUG__);
			break;
		    } else {
			System.err.println("Unknown option " + args[0] + "...");
			printMan();
			break;
		    }
		}
	    }
	}
    }
}
