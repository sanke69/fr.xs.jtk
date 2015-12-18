package fr.xs.jtk.databases.enums;

import java.util.ArrayList;
import java.util.Collection;

public enum TableOption {
	PRIMARY_KEY      ("PrimaryKey",    "PRIMARY KEY",     new String[] { "PrimaryKey",    "Primary Key",    "PRIMARY KEY" }),
	UNIQUE_KEY       ("UniqueKey",     "UNIQUE KEY",      new String[] { "UniqueKey",     "Unique Key",     "UNIQUE KEY" }),
	FOREIGN_KEY      ("ForeignKey",    "FOREIGN KEY",     new String[] { "ForeignKey",    "Foreign Key",    "FOREIGN KEY" }),
	KEY       		 ("Key",      	   "KEY",             new String[] { "Key",           "Key",            "KEY" }),
	ENGINE           ("UsedEngine",    "ENGINE",          new String[] { "UsedEngine",    "Used Engine",    "ENGINE" }),
	DEFAULT_CHARSET  ("UsedCharset",   "DEFAULT CHARSET", new String[] { "UsedCharset",   "Used Charset",   "DEFAULT CHARSET" }),
	AUTO_INCREMENT   ("AutoIncrement", "AUTO_INCREMENT",  new String[] { "AutoIncrement", "Auto Increment", "AUTO_INCREMENT" }),
	NONE("", "");

	String             name;
	String             mysqlTag;
	Collection<String> tags;

	public static TableOption newTableOption(String _string) {
		for(TableOption opt : TableOption.values())
			for(String tag : opt.tags)
				if(tag.compareToIgnoreCase(_string) == 0)
					return opt;		
		return NONE;
	}

	TableOption(String _name, String _mysql, String... _tags) {
		name     = _name;
		mysqlTag = _mysql;
		tags     = new ArrayList<String>();
		for(String tag : _tags)
			tags.add(tag);
	}

	public String getName() {
		return name;
	}
	public Collection<String> getTags() {
		return tags;
	}
	public String asMySQL() {
		return mysqlTag;
	}

}
