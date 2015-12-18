package fr.xs.jtk.system;

import fr.xs.jtk.helpers.OSHelper;
import fr.xs.jtk.helpers.OSHelper.NameOS;
import fr.xs.jtk.system.base.SystemProcessInterface;
import fr.xs.jtk.system.impl.FakeSystemProcess;
import fr.xs.jtk.system.impl.linux.DebianSystemProcess;
import fr.xs.jtk.system.impl.windows.WindowsSystemProcess;

public class SystemProcess {
	public static final NameOS os = OSHelper.get();

	public static final SystemProcessInterface impl;

	static {
		switch(os) {
		case LINUX :	impl = new DebianSystemProcess();
						break;
		case WINDOWS :	impl = new WindowsSystemProcess();
						break;
		case OSX :		impl = new FakeSystemProcess();
						break;
		case ANDROID :	impl = new FakeSystemProcess();
						break;
		case SOLARIS :	impl = new FakeSystemProcess();
						break;
		case UNKNOWN :	impl = new FakeSystemProcess();
						break;
		default :		impl = null;
						break;
		}
	}

	public static boolean isExist(String _path) {
		return impl.isExist(_path);
	};
	public static boolean isFile(String _path) {
		return impl.isFile(_path);
	};
	public static boolean isDirectory(String _path) {
		return impl.isDirectory(_path);
	};

	public static boolean touchFile(String _path) {
		return impl.touchFile(_path);
	};
	public static boolean copyFile(String _from, String _to) {
		return impl.copyFile(_from,_to);
	};
	public static boolean moveFile(String _from, String _to) {
		return impl.moveFile(_from,_to);
	};

	public static boolean createFile(String _path, String _content) {
		return impl.createFile(_path,_content);
	};
	public static boolean createDirectory(String _path, boolean _recursive) {
		return impl.createDirectory(_path, _recursive);
	};

	public static String getHostname() {
		return impl.getHostname();
	};

	public static boolean download(String _url, String _localPath) {
		return impl.download(_url, _localPath);
	};
	public static boolean untar(String _archivePath, String _extractPath, boolean _cut_root_dir) {
		return impl.untar(_archivePath, _extractPath, _cut_root_dir);
	};

	public static boolean addCrontabTask(int _minute, int _hour, int _dayOfMonth, int _month, int _dayOfWeek, String _task) {
		return impl.addCrontabTask(_minute, _hour, _dayOfMonth, _month, _dayOfWeek, _task);
	};

}
