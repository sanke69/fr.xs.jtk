package fr.xs.jtk.beans.contacts.attributes;

public class EAddressBean {
	public enum Type {
		aol("aol"), applelink("applelink"), attmail("attmail"), cis("cis"), eworld("eworld"), internet("internet"), ibmmail("ibmmail"), mcimail("mcimail"), powershare("powershare"), prodigy("prodigy"), tlx("tlx"), x400("x400");
		String code;
		Type(String _name) { code = _name; }
	};

	Type type;
	String email_address;

	public EAddressBean() {
		type = null;
		email_address = null;
	}
	public EAddressBean(String _address) {
		type = null;
		email_address = _address;
	}
	public EAddressBean(Type _type, String _address) {
		this(_address);
		type = _type;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getEmail_address() {
		return email_address;
	}
	public void setUrl(String email_address) {
		this.email_address = email_address;
	}

	public String asVCard() {
		return "EMAIL" + (type != null ? ";TYPE=" + type.code : "") + ":" + email_address + "\n";
	}
	public String asMeCard() {
		return "EMAIL:" + email_address + ";";
	}

}
