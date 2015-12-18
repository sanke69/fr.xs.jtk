package fr.xs.jtk.databases.beans;

import fr.xs.jtk.helpers.StringHelper;

public class ColumnBean {

	TableBean parent;
	String    name;
	DataBean  type;

	public ColumnBean() {
		parent = null;
		name = null;
		type = null;
	}
	public ColumnBean(String _name) {
		this();
		name = _name;
	}
	public ColumnBean(String _name, DataBean _type) {
		this(_name);
		type = _type;
	}
	public ColumnBean(String _name, DataBean _type, TableBean _table) {
		this(_name, _type);
		parent = _table;
	}
	public ColumnBean(String _name, String _type, TableBean _table) {
		this(_name, null);
		parent = _table;
	}

	public TableBean getParent() {
		return parent;
	}
	public String getName() {
		return name;
	}
	public DataBean getDataType() {
		return type;
	}

	public String tostring() {
		return StringHelper.padRight("COLUMN", 9) + ": name=" + StringHelper.padRight(name, 12) + ", type=" + type;
	}
}
