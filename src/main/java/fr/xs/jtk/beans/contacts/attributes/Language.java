package fr.xs.jtk.beans.contacts.attributes;

public enum Language {
	fr("fr-fr"), en("en-UK"), us("en-US");

	String code;
	Language(String _name) { code = _name; }
	
	public String getCode() {
		return code;
	}

}
