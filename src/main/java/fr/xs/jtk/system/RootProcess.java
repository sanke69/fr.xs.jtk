package fr.xs.jtk.system;

import fr.xs.jtk.helpers.OSHelper;
import fr.xs.jtk.helpers.OSHelper.NameOS;
import fr.xs.jtk.system.base.RootProcessInterface;
import fr.xs.jtk.system.impl.FakeRootProcess;
import fr.xs.jtk.system.impl.linux.DebianRootProcess;
import fr.xs.jtk.system.impl.windows.WindowsRootProcess;

public class RootProcess {
	public static final NameOS os = OSHelper.get();

	public static final RootProcessInterface impl;

	static {
		switch(os) {
		case LINUX :	impl = new DebianRootProcess();
						break;
		case WINDOWS :	impl = new WindowsRootProcess();
						break;
		case OSX :		impl = new FakeRootProcess();
						break;
		case ANDROID :	impl = new FakeRootProcess();
						break;
		case SOLARIS :	impl = new FakeRootProcess();
						break;
		case UNKNOWN :	impl = new FakeRootProcess();
						break;
		default :		impl = null;
						break;
		}
	}

	public static RootProcessInterface runtime() {
		return impl;
	}

	public static boolean isUserExist(String _username) {
		return impl.isUserExist(_username);
	}

	public static boolean createUser(int _id, String _name, String _group, String _home) {
		return impl.createUser(_id, _name, _group, _home);
	}

	public static boolean createGroup(int _id, String _name) {
		return impl.createGroup(_id, _name);
	}

	public static boolean changeUserGroup(String _user, String _group) {
		return impl.changeUserGroup(_user, _group);
	}

	public static boolean addUserToSecondariesGroup(String _user, String... _groups) {
		return impl.addUserToSecondariesGroup(_user, _groups);
	}

	public static boolean changeOwner(String _path, String _user, String _group, boolean _recursive) {
		return impl.changeOwner(_path, _user, _group, _recursive);
	}

	public static boolean changeAccess(String _path, int _OGA, boolean _recursive) {
		return impl.changeAccess(_path, _OGA, _recursive);
	}

	public static boolean createFile(String _path, String _content) {
		return impl.createFile(_path, _content);
	}
	public static boolean createDirectory(String _dir) {
		return impl.createDirectory(_dir);
	}

	public static boolean setInstallProperties(String _package, String _section, String _key, String _value) {
		return impl.setInstallProperties(_package,_section, _key, _value);
	}
	public static boolean installPackage(String _package) {
		return impl.installPackage(_package);
	}
	public static boolean installPackages(String... _packages) {
		return impl.installPackages(_packages);
	}

}
