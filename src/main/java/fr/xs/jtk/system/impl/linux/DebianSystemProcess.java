package fr.xs.jtk.system.impl.linux;

import java.util.concurrent.TimeUnit;

import fr.xs.jtk.helpers.MediaHelper;
import fr.xs.jtk.system.NativeProcess;
import fr.xs.jtk.system.base.SystemProcessInterface;
import fr.xs.jtk.system.impl.FakeSystemProcess;
import fr.xs.jtk.tools.Debugger;

public class DebianSystemProcess extends FakeSystemProcess implements SystemProcessInterface {
	private static final boolean useJavaImplementation = false;

	@Override
	public boolean isExist(String _path) {
		String[] cmdLine = { "[ -s " + _path + " ] && exit 0 || exit 1" };
		return NativeProcess.getExitCode( NativeProcess.runtime().execScript(cmdLine) ) == 0;
	}
	@Override
	public boolean isFile(String _path) {
		String[] cmdLine = { "[ -f \"" + _path + "\" ] && exit 0 || exit 1" };
		return NativeProcess.getExitCode( NativeProcess.runtime().execScript(cmdLine) ) == 0;
	}
	@Override
	public boolean isDirectory(String _path) {
		String[] cmdLine = { "[ -d \"" + _path + "\" ] && exit 0 || exit 1" };
		return NativeProcess.getExitCode( NativeProcess.runtime().execScript(cmdLine) ) == 0;
	}

	public boolean touchFile(String _path) {
		String[] cmdLine = { "touch", _path };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}
	public boolean copyFile(String _from, String _to) {
		String[] cmdLine = { "cp", _from, _to };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}
	public boolean moveFile(String _from, String _to) {
		String[] cmdLine = { "mv", _from, _to };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}

	public boolean createFile(String _path, String _content) {
		if( !touchFile(_path) )
			return false;
//		changeAccess(_path, 777, false);
		MediaHelper.createAsciiFile(_path, _content);
//		changeAccess(_path, 644, false);
		return true;	
	}/*
	public boolean createFile(String _path, byte[] _content) {
		if( !touchFile(_path) )
			return false;
//		changeAccess(_path, 777, false);
		MediaHelper.createAsciiFile(_path, _content);
//		changeAccess(_path, 644, false);
		return true;	
	}*/
	public boolean createDirectory(String _path, boolean _recursive) {
		String[] cmdLine = { "mkdir", _recursive ? "-p" : "", _path };
		return NativeProcess.getExitCode( NativeProcess.runtime().exec(cmdLine) ) == 0;
	}

	@Override
	public String getHostname() {
		Process p = NativeProcess.runtime().exec("hostname");
		try {
			p.waitFor(); // Normally instantaneous
		} catch(InterruptedException e) { return "@$!undef!$£€"; }
		return NativeProcess.getOutput(p);
	}
	
	@Override
	public boolean download(String _url, String _localPath) {
		if(useJavaImplementation)
			super.download(_url, _localPath);

		Debugger.log(500, "DebianSystemProcess use wget for download() method");
		Process p = NativeProcess.runtime().execScript("wget " + _url + " -O " + _localPath);
		try {
			Debugger.append(500, "wget");
			while(p.isAlive()) {
				Debugger.append(500, ".");
				p.waitFor(500, TimeUnit.MILLISECONDS);
			}
		} catch(InterruptedException e) { return false; }
		return p.exitValue() == 0;
	}
	@Override
	public boolean untar(String _archivePath, String _extractPath, boolean _cut_root_dir) {
		if(useJavaImplementation)
			super.untar(_archivePath, _extractPath, _cut_root_dir);

		Process p = NativeProcess.runtime().execScript("tar xvjf " + _archivePath + (_extractPath != null ? " -C " + _extractPath : "") + (_cut_root_dir ? " --strip-components=1" : ""));
		try {
			while(p.isAlive()) {
				System.out.println("still extracting...");
				p.waitFor(500, TimeUnit.MILLISECONDS);
			}
		} catch(InterruptedException e) { return false; }
		return p.exitValue() == 0;
	}


	@Override
	public boolean addCrontabTask(int _minute, int _hour, int _dayOfMonth, int _month, int _dayOfWeek, String _task) {
		String m   = (_minute < 0 || _minute >= 60) ? "*" : "" + _minute;
		String h   = (_hour < 0 || _hour >= 24) ? "*" : "" + _hour;
		String dom = (_dayOfMonth < 0 || _dayOfMonth > 31) ? "*" : "" + _dayOfMonth;
		String mon = (_month < 0 || _month > 12) ? "*" : "" + _month;
		String dow = (_dayOfWeek < 0 || _dayOfWeek > 7) ? "*" : "" + _dayOfWeek;

		String[] crontab = { "(crontab -l 2>/dev/null; echo '" + m + " " + h + " " + dom + " " + mon + " " + dow + " " + _task + "') | crontab -" };

		Process p = NativeProcess.runtime().execScript(crontab);
		try {
			p.waitFor(); // Normally instantaneous
		} catch(InterruptedException e) { return false; }
		return p.exitValue() == 0;
	}

}
