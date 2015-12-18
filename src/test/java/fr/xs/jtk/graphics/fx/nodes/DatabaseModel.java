package fr.xs.jtk.graphics.fx.nodes;

public class DatabaseModel {
	String name;
	String value;
	
	public DatabaseModel() {
		name  = "test";
		value = "toto";
	}
	public DatabaseModel(String _name, String _value) {
		name  = _name;
		value = _value;
	}
	
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}

};