package fr.xs.jtk.system.base;

public interface NativeProcessInterface {

	public Process exec(String... _cmdLine);
	public Process execScript(String... _scriptInstructions);

}
