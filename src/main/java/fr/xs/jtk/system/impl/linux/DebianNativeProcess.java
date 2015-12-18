package fr.xs.jtk.system.impl.linux;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import fr.xs.jtk.system.base.NativeProcessInterface;

public class DebianNativeProcess implements NativeProcessInterface {

	private static String[] runScript(String... _scriptInstructions) {
		return new String[] {
			"/bin/sh",
			"-c",
			"bash << --EOF--" + "\n" + Arrays.stream(_scriptInstructions).collect(Collectors.joining("\n")) + "\n" + "--EOF--\n"
		};
	}

	private static String[] runSudoCommandLine(String _password, String... _cmdLine) {
		return new String[] {
				"/bin/sh",
				"-c",
				"echo " + _password + " | sudo -S " + Arrays.stream(_cmdLine).collect(Collectors.joining(" "))
		};
	}

	private static String[] runSudoScript(String _password, String... _scriptInstructions) {
		return 
/*
				new String[] {
					"/bin/sh",
					"-c",
					"bash << EOF" + "\n" +
					"script=\"$(mktemp -p /dev/shm/)\"" + "\n" +
					"cat > \"$script\" <<-'CATEOF'" + "\n" +
					Arrays.stream(_scriptInstructions).collect(Collectors.joining("\n")) + "\n" + 
					"CATEOF" + "\n" +
					"chmod +x $script" + "\n" +
					"/bin/sh -c \"echo SanKe69 | sudo -S $script\"" + "\n" +
					"EOF"
				};
/**/
/**/
				new String[] {
					"/bin/sh",
					"-c",
					"bash << --EOF--" + "\n" + 
	
					"'echo " + _password + " | sudo -S " + "bash <<-\"SUDOEOF\"" + "\n" + 
					Arrays.stream(_scriptInstructions).collect(Collectors.joining("\n")) + "\n" + 
					"SUDOEOF'" +
	
					"--EOF--"
				};
/**/
/*
				new String[] {
					"/bin/sh",
					"-c",
					"echo " + _password + " | sudo -S " + "bash << EOF" + "\n" + Arrays.stream(_scriptInstructions).collect(Collectors.joining("\n")) + "\n" + "EOF"
				};
/**/
	}

	protected static boolean sudoEnable = false;
	protected static String  sudoPassword = "MaybeItWillWork";

	public static void setSudo(boolean _enabled, String _password) {
		sudoEnable  = _enabled;
		sudoPassword = _password;
	}

	@Override
	public Process exec(String... _cmdLine) {
		try {
			if(sudoEnable)
				return Runtime.getRuntime().exec(runSudoCommandLine(sudoPassword, _cmdLine));
			else
				return Runtime.getRuntime().exec(_cmdLine);
			
		} catch(IOException e) { return null; }
	}

	@Override
	public Process execScript(String... _scriptInstructions) {
		try {
			if(sudoEnable) {
				if(_scriptInstructions.length == 1){
					String[] s0 = runSudoCommandLine(sudoPassword, _scriptInstructions);
					return Runtime.getRuntime().exec(runSudoCommandLine(sudoPassword, _scriptInstructions));
				}else
					return Runtime.getRuntime().exec(runSudoScript(sudoPassword, _scriptInstructions));
			} else
				return Runtime.getRuntime().exec(runScript(_scriptInstructions));
		} catch(IOException e) { return null; }
	}

}
