package fr.xs.jtk.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import fr.xs.jtk.helpers.OSHelper;
import fr.xs.jtk.helpers.OSHelper.NameOS;
import fr.xs.jtk.system.base.NativeProcessInterface;
import fr.xs.jtk.system.base.SystemProcessInterface;
import fr.xs.jtk.system.impl.FakeNativeProcess;
import fr.xs.jtk.system.impl.FakeSystemProcess;
import fr.xs.jtk.system.impl.linux.DebianNativeProcess;
import fr.xs.jtk.system.impl.linux.DebianSystemProcess;
import fr.xs.jtk.system.impl.windows.WindowsNativeProcess;
import fr.xs.jtk.system.impl.windows.WindowsSystemProcess;

public class NativeProcess {
	public static final NameOS os;

	public static final NativeProcessInterface impl;

	static {
		os = OSHelper.get();

		switch(os) {
		case LINUX :	impl  = new DebianNativeProcess();
						break;
		case WINDOWS :	impl  = new WindowsNativeProcess();
						break;
		case OSX :		impl  = new FakeNativeProcess();
						break;
		case ANDROID :	impl  = new FakeNativeProcess();
						break;
		case SOLARIS :	impl  = new FakeNativeProcess();
						break;
		case UNKNOWN :	impl  = new FakeNativeProcess();
						break;
		default :		impl  = null;
						break;
		}
	}

	public static NativeProcessInterface runtime() {
		return impl;
	}

	public static int getExitCode(Process _proc) {
		if(_proc == null) return -1;

		try {
			_proc.waitFor();
		} catch(InterruptedException e) { return 666; }
		return _proc.exitValue();
	}
	public static String getOutput(Process _proc) {
		if(_proc == null) return "null";

		try {
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(_proc.getInputStream()));
			return stdInput.readLine();
		} catch(IOException e) { return null; }
	}
	public static Collection<String> getOutputs(Process _proc) {
		if(_proc == null) return new ArrayList<String>();

		Collection<String> s = new ArrayList<String>();
		String tmp;
		try {
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(_proc.getInputStream()));
			while((tmp = stdInput.readLine()) != null)
			    s.add(tmp);
			return s;
		} catch(IOException e) { return null; }
	}

	public static boolean terminate(Process _proc, int _ms) {
		_proc.destroy();
		if(_ms != -1)
			try { Thread.sleep(_ms); } catch(InterruptedException e) { return false; }
		else
			while(_proc.isAlive())
				try { Thread.sleep(20); } catch(InterruptedException e) { return false; }
		return true;
	}

}
