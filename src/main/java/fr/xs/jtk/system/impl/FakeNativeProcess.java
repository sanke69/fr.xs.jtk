package fr.xs.jtk.system.impl;

import java.io.IOException;

import fr.xs.jtk.system.base.NativeProcessInterface;
import fr.xs.jtk.tools.Debugger;

public class FakeNativeProcess implements NativeProcessInterface {

	@Override
	public Process exec(String... _cmdLine) {
		Debugger.log(500, "Fake implementation");
		try {
			return Runtime.getRuntime().exec(_cmdLine);
		} catch(IOException e) { return null; }
	}

	@Override
	public Process execScript(String... _scriptInstructions) {
		Debugger.log(500, "Fake implementation");
		return null;
	}

}
