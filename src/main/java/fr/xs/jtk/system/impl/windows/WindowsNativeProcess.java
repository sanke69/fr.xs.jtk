package fr.xs.jtk.system.impl.windows;

import java.io.IOException;

import fr.xs.jtk.system.base.NativeProcessInterface;
import fr.xs.jtk.system.impl.FakeNativeProcess;
import fr.xs.jtk.tools.Debugger;

public class WindowsNativeProcess extends FakeNativeProcess implements NativeProcessInterface {

	@Override
	public Process exec(String... _cmdLine) {
		try {
			return Runtime.getRuntime().exec(_cmdLine);
		} catch(IOException e) { return null; }
	}

	@Override
	public Process execScript(String... _scriptInstructions) {
		Debugger.log(500, "Not yet implemented");
		return null;
	}

}
