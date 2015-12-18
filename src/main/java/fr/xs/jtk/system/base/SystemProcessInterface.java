package fr.xs.jtk.system.base;

public interface SystemProcessInterface {
	public boolean isExist(String _path);
	public boolean isFile(String _path);
	public boolean isDirectory(String _path);

	public boolean touchFile(String _path);
	public boolean copyFile(String _from, String _to);
	public boolean moveFile(String _from, String _to);

	public boolean createFile(String _path, String _content);
//	public boolean createFile(String _path, byte[] _content);
	public boolean createDirectory(String _path, boolean _recursive);

	public boolean writeFile(String _path, String _content);
	public boolean replaceInFile(String _path, String _find, String _repl, boolean _all);

	public String  getHostname();

	public boolean download(String _url, String _localPath); // TODO:: USE URL and implementation JAVA fake method!
	public boolean untar(String _archivePath, String _extractPath, boolean _cut_root_dir); // TODO:: USE URL and implementation JAVA fake method!

	public boolean addCrontabTask(int _minute, int _hour, int _dayOfMonth, int _month, int _dayOfWeek, String _task);

}
