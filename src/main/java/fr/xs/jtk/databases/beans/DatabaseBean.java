package fr.xs.jtk.databases.beans;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseBean {

	String name;
	Collection<TableBean>   tables;
	Collection<TableFeeder> feeders;

	public DatabaseBean() {
		name    = null;
		tables  = new ArrayList<TableBean>();
		feeders = new ArrayList<TableFeeder>();
	}
	public DatabaseBean(String _name) {
		this();
		name = _name;
	}

	public String getName() {
		return name;
	}
	public Collection<TableBean> getTables() {
		return tables;
	}
	public TableBean getTable(String _name) {
		for(TableBean table : tables)
			if(table.getName().compareToIgnoreCase(_name) == 0)
				return table;
		return null;
	}
	public void addTable(TableBean _table) {
		tables.add(_table);
	}

	public Collection<TableFeeder> getFeeders() {
		return feeders;
	}
	public TableFeeder getFeeder(String _tableName) {
		for(TableFeeder feeder : feeders)
			if(feeder.getTable().compareToIgnoreCase(_tableName) == 0)
				return feeder;
		return null;
	}
	public void addTableFeeder(TableFeeder _feeder) {
		feeders.add(_feeder);
	}

	public String toString() {
		String toString = "Database: nom='" + name + "', nbTables=" + tables.size() + ", nbFeeders=" + feeders.size() + "\n";
		for(TableBean bean : tables)
			toString += bean + "\n";
		return toString;
	}
}
