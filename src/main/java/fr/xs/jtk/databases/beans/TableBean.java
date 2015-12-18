package fr.xs.jtk.databases.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.xs.jtk.databases.enums.TableOption;
import fr.xs.jtk.helpers.StringHelper;

public class TableBean {
	DatabaseBean             parent;
	String                   name;
	Collection<ColumnBean>   columns;
	Map<TableOption, List<String>> options;

	public TableBean() {
		parent  = null;
		name    = null;
		columns = new ArrayList<ColumnBean>();
		options = new HashMap<TableOption, List<String>>();
	}
	public TableBean(String _name) {
		this();
		name = _name;
	}
	public TableBean(String _name, DatabaseBean _db) {
		this(_name);
		parent = _db;
		parent.addTable(this);
	}

	public DatabaseBean getParent() {
		return parent;
	}
	public String getName() {
		return name;
	}
	public Collection<ColumnBean> getColumns() {
		return columns;
	}
	public ColumnBean getColumn(String _name) {
		for(ColumnBean column : columns)
			if(column.getName().compareToIgnoreCase(_name) == 0)
				return column;
		return null;
	}
	public Map<TableOption, List<String>> getOptions() {
		return options;
	}
	public List<String> getOption(String _name) {
		for(TableOption o : options.keySet())
			if(o == TableOption.newTableOption(_name))
				return options.get(o);
		return null;
	}

	public void addColumn(ColumnBean _column) {
		columns.add(_column);
	}
	public void addOption(TableOption o, String value) {
		List<String> old = options.get(o);

		if(old != null) {
			old.add(value);
			options.put(o, old);
		} else {
			old = new ArrayList<String>();
			old.add(value);
			options.put(o, old);
		}
	}

	public String toString() {
		String toString = "TABLE: name=" + name + ", " + "nbColumn=" + columns.size() + ", nbOption=" + options.size() + "\n";
		for(ColumnBean col : columns)
			toString += col.tostring() + "\n";
		for (Map.Entry<TableOption, List<String>> entry : options.entrySet()) {
			toString += StringHelper.padRight("OPT", 9) + ": name=" + StringHelper.padRight(entry.getKey().getName(), 12) + ", values=";
			for(String val : entry.getValue())
				toString += "'" + val + "' ";
			toString += "\n";
		}
		
		return toString;
	}

}
