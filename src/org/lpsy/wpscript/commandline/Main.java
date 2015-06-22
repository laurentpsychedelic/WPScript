package org.lpsy.wpscript.commandline;

import org.lpsy.wpscript.language.exceptions.PanicException;
import org.lpsy.wpscript.language.exceptions.RuntimeErrorException;
import org.lpsy.wpscript.language.exceptions.CompilationErrorException;
import org.lpsy.wpscript.language.executable.ExecutableScript;
import org.lpsy.wpscript.editor.ScriptWindow;
import org.lpsy.wpscript.utilities.FileIO;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class Main {
    public static final String VERSION = "1.0";
    public static void printMan() {
	System.err.print(manual);
    }
    public static void printVersion() {
	System.err.println("WPScript version " + VERSION  + "\n"
                         + "(c) 2011-2015, FABRE Laurent");
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

    public static final String manual = "NNAAMMEE  WWPPSSccrriipptt  :: aa ssccrriippttiinngg llaanngguuaaggee ddeessiiggnneedd ffoorr PPAA//WWPPAA//MMEE sseerriieess ssooffttwwaarree,,\n"+
"       PPhhoottoonniicc LLaattttiiccee,, IInncc..\n"+
"SSYYNNOOPPSSIISS\n"+
"       wwppssccrriipptt ffiilleeppaatthh ::  EExxeeccuuttee  tthhee  ssccrriipptt  iinn  tthhee  ffiillee  ssppeecciiffiieedd  bbyy\n"+
"       ffiilleeppaatthh..\n"+
"\n"+
"       wwppssccrriipptt --cc ccoommmmaannddss :: EExxeeccuuttee tthhee ccoommmmaannddss ssppeecciiffiieedd bbyy ccoommmmaannddss..\n"+
"\n"+
"\n"+
"DDEESSCCRRIIPPTTIIOONN\n"+
"       _W_P_S_c_r_i_p_t  is  an  interpreter engine to operate calculation and manipu-\n"+
"       lates data sets available in Photonic Lattice, Inc. software.\n"+
"\n"+
"\n"+
"OOPPTTIIOONNSS\n"+
"       When no option is provided, the first argument is used as a script file\n"+
"       path to be read and executed.\n"+
"\n"+
"\n"+
"       --cc     The argument provided after -c is direct WPScript commands to be\n"+
"              executed.\n"+
"\n"+
"\n"+
"       ----ddeebbuugg\n"+
"              Enable debug information output on the console (or on the  redi-\n"+
"              rected console output for script editor window).\n"+
"\n"+
"\n"+
"       ----hheellpp,,_-_-_m_a_n\n"+
"              Displays this manual page\n"+
"\n"+
"\n"+
"       ----vveerrssiioonn\n"+
"              Displays version information"+
"\n"+
"\n"+
"CCOOPPYYRRIIGGHHTT\n"+
"        (c) 2011, FABRE Laurent, Photonic Lattice, Inc.\n"+
"\n"+
"\n"+
"AAUUTTHHOORRSS\n"+
"       Laurent FABRE, E-mail: fabre@photonic-lattice.com\n";
}
