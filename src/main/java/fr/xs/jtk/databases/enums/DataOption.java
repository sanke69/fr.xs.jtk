package fr.xs.jtk.databases.enums;

public enum DataOption {
	NOT_NULL		("Not Null",       "NOT NULL"),
	AUTO_INCREMENT	("Auto Increment", "AUTO_INCREMENT"),
	UNSIGNED		("Unsigned",       "UNSIGNED");

	String description;
	String mysql;

	DataOption(String _desc, String _mysql) {
		description = _desc;
		mysql       = _mysql;
	}

	@Override
	public String toString() {
		return description;
	}

	public String asMySQL() {
		return mysql;
	}

}
