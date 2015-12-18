package fr.xs.jtk.beans.calendar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RendezVousBean {

	LocalDateTime start, end;
	boolean whole_start, whole_end;
	String title, location, description;

	public RendezVousBean() {
		whole_start = whole_end = true;
		start = end = null;
		title = location = description = null;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public boolean isWholeStart() {
		return whole_start;
	}

	public void setStart(LocalDateTime _start, boolean _is_whole_day) {
		this.start = _start;
		whole_start = _is_whole_day;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime _end, boolean _is_whole_day) {
		this.end = _end;
		whole_end = _is_whole_day;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String asQRCodeMessage() {
		DateTimeFormatter patd = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter patt = DateTimeFormatter.ofPattern("HHmmss");

    	String db = start.format(patd);
    	String b  = db + "T" + start.format(patt) + "Z";
    	String de = end.format(patd);
    	String e = de + "T" + end.format(patt) + "Z";
   
		String str = "BEGIN:VEVENT" + "\n";

		str += (title != null ? "SUMMARY:" + title + "\n" : "");
		str += (whole_start ? "DTSTART:" + b + "\n" : "DTSTART;VALUE=DATE:" + db + "\n" );
		str += (whole_end   ? "DTEND:" + e + "\n" : "DTEND;VALUE=DATE:" + de + "\n" );
		str += (location != null ? "LOCATION:" + location + "\n" : "" );
		str += (description != null ? "DESCRIPTION:" + description + "\n" : "" );

		str += "END:VEVENT";
		
		return str;
    }

}
