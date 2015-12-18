package fr.xs.jtk.system.impl.linux;

import fr.xs.jtk.system.NativeProcess;
import fr.xs.jtk.system.base.NativeServiceInterface;
import fr.xs.jtk.tools.Debugger;

public abstract class DebianNativeService implements NativeServiceInterface {
	public enum SystemUsed { initd, systemd, custom };

	protected String     serviceName;
	protected SystemUsed systemUsed;

	public DebianNativeService(String _name) {
		serviceName  = _name;
		systemUsed   = SystemUsed.initd;
	}
	public DebianNativeService(String _name, boolean _systemd) {
		serviceName  = _name;
		systemUsed   = SystemUsed.systemd;
	}
	public DebianNativeService(String _name, SystemUsed _systemUsed) {
		serviceName  = _name;
		systemUsed   = _systemUsed;
	}

	public String getName() { return serviceName; }

	public abstract String[] packages();

	public abstract void install();
	public abstract void configure();

	@Override
	public boolean isServiceExist() {
		for(int i = 0; i < packages().length; ++i)
			if( NativeProcess.getExitCode( NativeProcess.runtime().execScript("dpkg --get-selections | grep " + packages()[i] + " | grep install") ) != 0 )
				return false;
		return true;

//		String[] script = new String[packages().length];
//		for(int i = 0; i < packages().length; ++i)
//			script[i] = "dpkg --get-selections | grep " + packages()[i] + " | grep install";
//		return NativeProcess.getOutputs( NativeProcess.runtime().execScript(script) ).size() >= packages().length;
	}

	@Override
	public boolean isServiceAlive() {
		String[] cmdLine = null;
		switch(systemUsed) {
		case initd : 	cmdLine = new String[] { "/etc/init.d/" + serviceName, "status" }; break;
		case systemd : 	cmdLine = new String[] { "service", serviceName, "status" }; break;
		case custom : 	Debugger.error("You must override the method isServiceAlive"); return false;
		default :		return false;
		}

		String answer = NativeProcess.getOutput( NativeProcess.runtime().exec(cmdLine) );
		if( answer == null ) return false;
		if( answer.contains("is running") ) return true;
		if( answer.contains("start/running, process") ) return true;
		return false;
	}

	@Override
	public boolean start() {
		String[] cmdLine = null;
		switch(systemUsed) {
		case initd : 	cmdLine = new String[] { "/etc/init.d/" + serviceName, "start" }; break;
		case systemd : 	cmdLine = new String[] { "service", serviceName, "start" }; break;
		default :		return false;
		}

		Process p = NativeProcess.runtime().exec(cmdLine);

		if( NativeProcess.getExitCode( p ) == 0 ) return true;

		String answer = NativeProcess.getOutput( p );
		if( answer.contains("start/running, process") ) return true;
		return false;
	}
	@Override
	public boolean stop() {
		String[] cmdLine = null;
		switch(systemUsed) {
		case initd : 	cmdLine = new String[] { "/etc/init.d/" + serviceName, "stop" }; break;
		case systemd : 	cmdLine = new String[] { "service", serviceName, "stop" }; break;
		default :		return false;
		}

		Process p = NativeProcess.runtime().exec(cmdLine);

		if( NativeProcess.getExitCode( p ) == 0 ) return true;

		String answer = NativeProcess.getOutput( p );
		if( answer == null ) return false;
		if( answer.contains("stop") ) return true;
		return false;
	}
	@Override
	public boolean restart() {
		if(stop())
			return start();
		return false;
	}

}
