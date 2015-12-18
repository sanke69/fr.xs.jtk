package fr.xs.jtk.beans.contacts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import fr.xs.jtk.beans.contacts.attributes.AddressBean;
import fr.xs.jtk.beans.contacts.attributes.EAddressBean;
import fr.xs.jtk.beans.contacts.attributes.Language;
import fr.xs.jtk.beans.contacts.attributes.PhoneBean;

public class ContactBean {
	
	String lastname, firstname, additionalname, prefix, suffix;
	String formattedname;
	
	String organization, department;
	String title, role;
	
	LocalDate birthday, revision;
	String    timezone;

	Collection<AddressBean> adresses;
	Collection<PhoneBean>   phones;
	Collection<EAddressBean>   emails;
	
	double longitude, latitude;
	
	Language language;
	
	String note, url, uid;
	
	public ContactBean() {
		lastname = null;
		firstname = null;
		additionalname = null;
		prefix = null;
		suffix = null;

		formattedname = null;
		
		organization = null;
		department = null;
		
		title = null;
		role = null;
		
		birthday = null;
		revision = null;
		timezone = null;
		
		adresses = new ArrayList<AddressBean>();
		phones = new ArrayList<PhoneBean>();
		emails = new ArrayList<EAddressBean>();
		
		longitude = 666.666;
		latitude = 666.666;
		
		language = null;
		
		note = null;
		url = null;
		uid = null;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getAdditionalname() {
		return additionalname;
	}

	public void setAdditionalname(String additionalname) {
		this.additionalname = additionalname;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFormattedname() {
		return formattedname;
	}

	public void setFormattedname(String formattedname) {
		this.formattedname = formattedname;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public LocalDate getRevision() {
		return revision;
	}

	public void setRevision(LocalDate revision) {
		this.revision = revision;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Collection<AddressBean> getAdresses() {
		return adresses;
	}
	public void addAddress(AddressBean adresses) {
		this.adresses.add(adresses);
	}
	public void addAdresses(AddressBean... _adresses) {
		for(AddressBean adress : _adresses)
			this.adresses.add(adress);
	}

	public Collection<EAddressBean> getEmails() {
		return emails;
	}
	public void addEmail(EAddressBean _email) {
		emails.add(_email);
	}
	public void addEmails(EAddressBean... _emails) {
		for(EAddressBean email : _emails)
			emails.add(email);
	}

	public Collection<PhoneBean> getPhones() {
		return phones;
	}
	public void addPhone(PhoneBean _phone) {
		phones.add(_phone);
	}
	public void addPhones(PhoneBean... _phones) {
		for(PhoneBean phone : _phones)
			phones.add(phone);
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String asVCard() {
		String str = "BEGIN:VCARD" + "\n" + "VERSION:3.0" + "\n";

		str += "N:" + (lastname       == null ? "" : lastname) + ";" 
					+ (firstname      == null ? "" : firstname) + ";" 
					+ (additionalname == null ? "" : additionalname) + ";" 
					+ (prefix         == null ? "" : prefix) + ";" 
					+ (suffix         == null ? "" : lastname) + ";"
					+ "\n";

		str += (formattedname != null ? "FN:" + (formattedname       == null ? "" : formattedname) + ";" + "\n" : "");
		
		str += (birthday != null ? "BDAY:" + birthday.getYear() + "-" + birthday.getMonthValue() + "-" + birthday.getDayOfMonth() + "\n" : "");

		for(AddressBean adress : adresses)
			str += adress.asVCard();

		str += (longitude != 666.666 ? "GEO:" + longitude + ";" + latitude + "\n" : "");

		for(EAddressBean email : emails)
			str += email.asVCard();

		for(PhoneBean phone : phones)
			str += phone.asVCard();

		str += (language != null ? "LANG:" + language.getCode() + "\n" : "");
		str += (note != null ? "NOTE:" + note + "\n" : "");

		str += (timezone != null ? "TZ:" + timezone + "\n" : "");

		str += (title != null ? "TITLE:" + title + "\n" : "");
		str += (role != null ? "ROLE:" + role + "\n" : "");
		
		str += ( (organization != null) || (department != null)  ) ?
				"ORG:" + (organization != null ? organization + ";" : ";") + (department != null ? department + ";": ";") : "";

		str += (url != null ? "URL:" + url + "\n" : "");

		str += (revision != null ? "REV:" + revision.getYear() + "-" + revision.getMonthValue() + "-" + revision.getDayOfMonth() + "\n" : "");
		str += (uid != null ? "UID:" + uid + "\n" : "");

		str += "END:VCARD\n";

		return str;
	}

	public String asMeCard() {
		String str = "MECARD:" + (lastname != null ? lastname + " " : "") + (firstname != null ? firstname : "") + ";";

		str += ( (organization != null) || (department != null)  ) ?
				"ORG:" + (organization != null ? organization : "") + (department != null ? department : "") : ";";

		str += (url != null ? "URL:" + url + "\n" : "");

		if(!phones.isEmpty())
			str += phones.iterator().next().asMeCard();

		if(!adresses.isEmpty())
			str += adresses.iterator().next().asMeCard();

		if(!emails.isEmpty())
			str += emails.iterator().next().asMeCard();

		str += (note != null ? "NOTE:" + note + "\n" : ";;");
		
		return str;
	}

}
/*
 * http://www.evenx.com/vcard-3-0-format-specification
 * 
KEY[;TYPE=type]: 	Public key (type = X509; PGP) 	KEY;ENCODING=b:SBBIEhvd2VzMS
EwHwYJKoZIhvcNAQ
kBFhJob3dlc0BuZX


LABEL[;TYPE= type*[„,“type]]: 	To specify formatted text corresponding to delivery address. It allows to use =0D=0A (\n) as line break for individual lines within the vCard.
(type (can be concatenated with „,“) = dom(domestic), intl(international)(default), postal(default), parcel(default), home, work(default)) 	LABEL;TYPE=work,dom,postal: Wallstr.1\n Berlin\nGermany
*/



































































