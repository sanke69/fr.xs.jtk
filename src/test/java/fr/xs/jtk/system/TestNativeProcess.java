package fr.xs.jtk.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import fr.xs.jtk.helpers.OSHelper;
import fr.xs.jtk.helpers.OSHelper.NameOS;
import fr.xs.jtk.system.impl.linux.DebianNativeProcess;
import fr.xs.jtk.tools.Debugger;

public class TestNativeProcess {
	
	public static void main(String... argv) { Debugger.initialize(0, 0);
		boolean  isLinux  = OSHelper.get() == NameOS.LINUX;
		String   password = "SanKe69";

		String[] cmdLine     = { "echo", "Hello World" };
		String[] cmdLineProc = { "ping", "sp-web.fr", isLinux ? "-c" : "-n", "3" };
		String[] desktop     = { isLinux ? "blender" : "notepad" };
		String[] script      = { "echo Hello I m $(whoami)",
								 "[ -s \"" + "/home/sanke" + "\" ] && exit 0 || exit 1" };

		if( OSHelper.get() == NameOS.LINUX && password != null )
			commandLineTest(password, "whoami");
		commandLineTest(password, cmdLine);
		commandLineProcessTest(password, cmdLineProc);
		desktopProcessTest(desktop);
		scriptTest(password, script);
	}

	public static void commandLineTest(String _password, String... _cmdLine) {
		Debugger.log( 500, "Running test... cmd=", _cmdLine[0] );

		Debugger.log( 500, "exec1... exit=",    NativeProcess.getExitCode( NativeProcess.runtime().exec(_cmdLine) ) );
		Debugger.log( 500, "exec2... output=",  NativeProcess.getOutput( NativeProcess.runtime().exec(_cmdLine) ) );
		Debugger.log( 500, "exec3... outputs=", NativeProcess.getOutputs( NativeProcess.runtime().exec(_cmdLine) ) );

		if( OSHelper.get() == NameOS.LINUX && _password != null ) {
			DebianNativeProcess.setSudo(true, _password);

			Debugger.log( 500, "sudo_exec1... exit=",    NativeProcess.getExitCode( NativeProcess.runtime().exec(_cmdLine) ) );
			Debugger.log( 500, "sudo_exec2... output=",  NativeProcess.getOutput( NativeProcess.runtime().exec(_cmdLine) ) );
			Debugger.log( 500, "sudo_exec3... outputs=", NativeProcess.getOutputs( NativeProcess.runtime().exec(_cmdLine) ) );

			DebianNativeProcess.setSudo(false, null);
		}
	}

	public static void commandLineProcessTest(String _password, String[] _cmdLine) {
		Process CLP = NativeProcess.runtime().exec(_cmdLine);

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(CLP.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(CLP.getErrorStream()));

		new Thread(() -> {
			Debugger.log( 500, "Here is the standard output of the command:" );
			String s = null;
			int N = 10, i = 0;
			try {
				while ((s = stdInput.readLine()) != null) {
					Debugger.log( 500, s);
				    if(i++ > N) break;
				}
			} catch(Exception e) { e.printStackTrace(); }
		}).start();

		new Thread(() -> {
			Debugger.error( "Here is the standard error of the command (if any):" );
			String s = null;
			int N = 10, i = 0;
			try {
				while ((s = stdError.readLine()) != null) {
					Debugger.error(s);
				    if(i++ > N) break;
				}
			} catch(Exception e) { e.printStackTrace(); }
		}).start();

//		try { Thread.sleep(5500); } catch(InterruptedException e) { e.printStackTrace(); }
		try { CLP.waitFor(); } catch (InterruptedException e) { e.printStackTrace(); }

		CLP.destroy();
		Debugger.log( 500, CLP.exitValue());
	}

	public static void desktopProcessTest(String[] _cmdLine) {
		Process CLP = NativeProcess.runtime().exec(_cmdLine);

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(CLP.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(CLP.getErrorStream()));

		new Thread(() -> {
			Debugger.log( 500, "Here is the standard output of the command:" );
			String s = null;
			int N = 10, i = 0;
			try {
				while ((s = stdInput.readLine()) != null) {
					Debugger.log( 500, s );
				    if(i++ > N) break;
				}
			} catch(Exception e) { e.printStackTrace(); }
		}).start();

		new Thread(() -> {
			Debugger.error( "Here is the standard error of the command (if any):" );
			String s = null;
			int N = 10, i = 0;
			try {
				while ((s = stdError.readLine()) != null) {
					Debugger.error(s);
				    if(i++ > N) break;
				}
			} catch(Exception e) { e.printStackTrace(); }
		}).start();

		try { CLP.waitFor(); } catch(InterruptedException e) { e.printStackTrace(); }

		CLP.destroy();
		Debugger.log( 500, CLP.exitValue());
	}

	public static void scriptTest(String _password, String[] _scriptInstructions) {
		Debugger.log( 500, "Running test... script=", _scriptInstructions[0] );

		Debugger.log( 500, "exec1... exit=",    NativeProcess.getExitCode( NativeProcess.runtime().execScript(_scriptInstructions) ) );
		Debugger.log( 500, "exec2... output=",  NativeProcess.getOutput( NativeProcess.runtime().execScript(_scriptInstructions) ) );
		Debugger.log( 500, "exec3... outputs=", NativeProcess.getOutputs( NativeProcess.runtime().execScript(_scriptInstructions) ) );

		if( OSHelper.get() == NameOS.LINUX && _password != null ) {
			DebianNativeProcess.setSudo(true, _password);

			Debugger.log( 500, "sudo_exec1... exit=",    NativeProcess.getExitCode( NativeProcess.runtime().execScript(_scriptInstructions) ) );
			Debugger.log( 500, "sudo_exec2... output=",  NativeProcess.getOutput( NativeProcess.runtime().execScript(_scriptInstructions) ) );
			Debugger.log( 500, "sudo_exec3... outputs=", NativeProcess.getOutputs( NativeProcess.runtime().execScript(_scriptInstructions) ) );

			DebianNativeProcess.setSudo(false, null);
		}
	}

	
}
