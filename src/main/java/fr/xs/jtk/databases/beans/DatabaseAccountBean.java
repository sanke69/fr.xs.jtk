package fr.xs.jtk.databases.beans;

public class DatabaseAccountBean {

	String host;
	String user;
	String password;
	String database;

	public DatabaseAccountBean() {
		host     = null;
		user     = null;
		password = null;
		database = null;
	}
	public DatabaseAccountBean(String _hostname, String _user, String _password) {
		host     = _hostname;
		user     = _user;
		password = _password;
		database = null;
	}
	public DatabaseAccountBean(String _hostname, String _database, String _user, String _password) {
		this(_hostname, _user, _password);
		database = _database;
	}

	public String getHostname() {
		return host;
	}
	public String getDatabase() {
		return database;
	}
	public String getUsername() {
		return user;
	}
	public String getPassword() {
		return password;
	}

}
