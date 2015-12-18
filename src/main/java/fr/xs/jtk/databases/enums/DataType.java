package fr.xs.jtk.databases.enums;

import java.util.ArrayList;
import java.util.Collection;

public enum DataType {
	CHAR     ("Char",   new String[] { "CHAR" }),
	STRING   ("String", new String[] { "VARCHAR", "VARCHAR2", "STRING", "TEXT" }),
	DATE     ("Date",   new String[] { "DATE" }),
	DATETIME ("Date",   new String[] { "DATETIME" }),
	NUMBER   ("Number", new String[] { "NUMBER" }),
	SHORT    ("Short",  new String[] { "TINYINT", "SHORT" }),
	INT      ("Int",    new String[] { "INT", "INT UNSIGNED" }),
	LONG     ("Long",   new String[] { "LONG", "LONG UNSIGNED" }),
	FLOAT    ("Float",  new String[] { "FLOAT" }),
	DOUBLE   ("Double", new String[] { "DOUBLE" }),
	;

	String             name;
	Collection<String> tags;

	DataType(String _name, String... _tags) {
		name = _name;
		tags = new ArrayList<String>();
		for(String tag : _tags)
			tags.add(tag);
	}	

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}
	public Collection<String> getTags() {
		return tags;
	}

}
