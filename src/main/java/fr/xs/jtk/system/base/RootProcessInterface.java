package fr.xs.jtk.system.base;

public interface RootProcessInterface {

	public boolean isUserExist(String _username);

	public boolean createUser(int _id, String _name, String _group, String _home);

	public boolean createGroup(int _id, String _name);

	public boolean changeUserGroup(String _user, String _group);
	public boolean addUserToSecondariesGroup(String _user, String... _groups);

	public boolean changeOwner(String _path, String _user, String _group, boolean _recursive);
	public boolean changeAccess(String _path, int _OGA, boolean _recursive);

	public boolean createFile(String _path, String _content);
	public boolean createDirectory(String _dir);

	public boolean setInstallProperties(String _package, String _section, String _key, String _value);
	public boolean installPackage(String _package);
	public boolean installPackages(String... _packages);

}
