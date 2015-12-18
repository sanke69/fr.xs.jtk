package fr.xs.jtk.system.impl.linux;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import fr.xs.jtk.system.NativeProcess;
import fr.xs.jtk.system.SystemProcess;
import fr.xs.jtk.system.base.RootProcessInterface;

/**
 * USE THIS CLASS WITH CAUTION !!!
 * 
 * @author sanke
 *
 */
public class DebianRootProcess implements RootProcessInterface {

	public boolean isUserExist(String _username) {
 		String script = "id -u " + _username + " >/dev/null 2>&1 && exit 0 || exit 1";

		return NativeProcess.getExitCode( NativeProcess.runtime().execScript(script) ) == 0;
	}

	public boolean createUser(int _id, String _name, String _group, String _home) {
		String[] cmdLine = { "useradd", "-g", _group, "-u", Integer.toString(_id), _name, "-d", _home };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}

	public boolean createGroup(int _id, String _name) {
		String[] cmdLine = { "groupadd", "-g", Integer.toString(_id), _name };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}

	public boolean changeUserGroup(String _user, String _group) {
		String[] cmdLine = { "usermod", "-g", _group, _user };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}
	public boolean addUserToSecondariesGroup(String _user, String... _groups) {
		String[] cmdLine = { "usermod", "-a", "-G", Arrays.stream(_groups).collect(Collectors.joining(",")), _user };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}

	public boolean changeOwner(String _path, String _user, String _group, boolean _recursive) {
		String[] cmdLine = { "chown", _recursive ? "-R" : "", _user+":"+_group, _path };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}
	public boolean changeAccess(String _path, int _OGA, boolean _recursive) {
		String[] cmdLine = { "chmod", _recursive ? "-R" : "", Integer.toString(_OGA), _path };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}

	@Override
	public boolean createFile(String _path, String _content) {
		SystemProcess.touchFile(_path);
		changeAccess(_path, 777, false);
		SystemProcess.createFile(_path, _content);
		changeAccess(_path, 644, false);
		return true;
	}
	@Override
	public boolean createDirectory(String _path) {
		return SystemProcess.createDirectory(_path, true);
	}

	@Override
	public boolean setInstallProperties(String _package, String _section, String _key, String _value) {
		String scriptLine = "debconf-set-selections <<< '" + _package + " " +  _section + " " + _key + " " + _value + "'";
		return NativeProcess.getExitCode( NativeProcess.runtime().execScript(scriptLine) ) == 0;
	}

	@Override
	public boolean installPackage(String _package) {
		String[] cmdLine = { "apt-get", "install", "-y", _package };

		Process p = NativeProcess.runtime().execScript(cmdLine);
		try {
			System.out.println("install");
			while(p.isAlive() && p.waitFor(500, TimeUnit.MILLISECONDS))
				System.out.print(".");
			System.out.println(" done");
		} catch(InterruptedException e) { return false; }
		return p.exitValue() == 0;
	}

	@Override
	public boolean installPackages(String... _packages) {
		String[] cmdLine = { "apt-get", "install", "-y", Arrays.stream(_packages).collect(Collectors.joining(" ")) };

		Process p = NativeProcess.runtime().execScript(cmdLine);
		try {
			System.out.println("install");
			while(p.isAlive() && p.waitFor(500, TimeUnit.MILLISECONDS))
				System.out.print(".");
			System.out.println(" done");
		} catch(InterruptedException e) { return false; }
		return p.exitValue() == 0;
	}

}
