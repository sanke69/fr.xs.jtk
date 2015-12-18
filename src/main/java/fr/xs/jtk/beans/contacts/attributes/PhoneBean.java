package fr.xs.jtk.beans.contacts.attributes;

public class PhoneBean {
	public enum Type {
		pref("PREF"), work("WORK"), home("HOME"), voice("VOICE"), fax("FAX"), msg("MSG"), cell("CELL"), pager("PAGER"), bbs("BBS"), modem("MODEM"), car("CAR"), isdn("ISDN"), video("VIDEO");
		String code;
		Type(String _name) { code = _name; }
	};

	Type type;
	String number;

	public PhoneBean() {
		type = null;
		number = null;
	}
	public PhoneBean(String _number) {
		type = null;
		number = _number;
	}
	public PhoneBean(Type _type, String _number) {
		this(_number);
		type = _type;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String asVCard() {
		return "TEL" + (type != null ? ";TYPE=" + type.code : "") + ":" + number + "\n";
	}
	public String asMeCard() {
		return "TEL" + ":" + number + ";";
	}

}