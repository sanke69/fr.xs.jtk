package fr.xs.jtk.beans.contacts.attributes;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AddressBean {
	public enum AddressType {
		domestic("dom"), international("intl"), postal("postal"), parcel("parcel"), home("home"), work("work");
		String code;
		AddressType(String _name) { code = _name; }
	}

	AddressType[] types;
	String postoffice_address, extended_address, street, locality, region, postalcode, country;

	public AddressBean() {
		types = null;
		
		postoffice_address = "";
		extended_address = "";
		street = "";
		locality = "";
		region = "";
		postalcode = "";
		country = "";
	}
	
	public AddressType[] getTypes() {
		return types;
	}
	public void setTypes(AddressType[] types) {
		this.types = types;
	}

	public String getPostoffice_address() {
		return postoffice_address;
	}
	public void setPostoffice_address(String postoffice_address) {
		this.postoffice_address = postoffice_address;
	}
	public String getExtended_address() {
		return extended_address;
	}
	public void setExtended_address(String extended_address) {
		this.extended_address = extended_address;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	AddressBean(String _address) {
		types = null;
		postoffice_address = _address;
	}
	AddressBean(AddressType[] _type, String _address) {
		this(_address);
		types = _type;
	}

	public String asVCard() {
		String[] typecodes = null;
		if(types != null) {
			typecodes = new String[types.length];
			int i = 0;
			for(AddressType type : types)
				typecodes[i++] = type.code;
		}

		return "ADR" + (types != null ? ";TYPE=" + Arrays.stream(typecodes).collect(Collectors.joining(",")) : "") + ":"
				+ (postoffice_address != null ? postoffice_address + ";" : ";")
				+ (extended_address != null ? extended_address + ";" : ";")
				+ (street != null ? street + ";" : ";")
				+ (locality != null ? locality + ";" : ";")
				+ (region != null ? region + ";" : ";")
				+ (postalcode != null ? postalcode + ";" : ";")
				+ (country != null ? country + ";" : ";") + "\n";
	}

	public String asMeCard() {
		return "ADR" + ":" + postoffice_address + ";";
	}

}
