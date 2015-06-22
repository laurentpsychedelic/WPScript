package org.lpsy.wpscript.language;

import org.lpsy.wpscript.language.executable.FunctionCall;

/**
 * @author Laurent FABRE, 2011-2015
 */
public class Settings {
    public static void addRuntimeModule(String runtimeModuleFQCN) throws ClassNotFoundException {
        FunctionCall.addRuntimeModule(runtimeModuleFQCN);
    }
}
