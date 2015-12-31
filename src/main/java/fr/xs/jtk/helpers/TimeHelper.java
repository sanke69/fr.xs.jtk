package fr.xs.jtk.helpers;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

public class TimeHelper {
	public static final String sqlFormat = "yyyy-MM-dd HH:mm:ss";

//	public static final ZoneId 				localZoneID     = ZoneId.of("Europe/Paris");
//															  ZoneId.of(ZoneId.SHORT_IDS.get("PST"))
//	public static final ZoneOffset			localZoneOffset = ZoneOffset.from(ZonedDateTime.now(localZoneID));

	public static final ZoneId 				localZoneID     = ZoneId.systemDefault();
	public static final ZoneOffset			localZoneOffset = ZoneOffset.UTC;


	public static String hashTag() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
		
		try {
		    return LocalDateTime.ofInstant(Instant.now(), localZoneOffset).format(format);
		} catch (DateTimeException exc) {
		    System.out.printf("%s can't be formatted with pattern!%n", Instant.now(), "yyyyMMdd.HHmmss");
		    throw exc;
		}
	}


	/**
	 * Get an Instant from local parameters: year, month, day, hour, minutes, seconds
	 * 
	 * @return an Instant
	 */
	public static final Instant instant(int _y, int _m, int _d, int _H, int _M, int _S) {
		return LocalDateTime.of(_y, _m, _d, _H, _M, _S).toInstant(localZoneOffset);
	}

	/**
	 * Get an Instant from string at local zone
	 * 
	 * @return the corresponding Instant
	 */
	public static final Instant parse(String _date, String _format) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(_format);
		
		try {
			return LocalDateTime.parse(_date, format).toInstant(localZoneOffset);
		} catch (DateTimeParseException e) {
			try {
				return LocalDate.parse(_date, format).atStartOfDay().toInstant(localZoneOffset);
			} catch (DateTimeParseException e2) {
			    throw e2;
			}
		}
	}

	/**
	 * Return a string from Instant using format _format
	 * @param _instant
	 */
	public static final String format(Instant _instant, String _format) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(_format);
		
		try {
		    return LocalDateTime.ofInstant(_instant, localZoneOffset).format(format);
		} catch (DateTimeException exc) {
		    System.out.printf("%s can't be formatted with pattern!%n", _instant, _format);
		    throw exc;
		}
	}

	public static final Instant fromSQL(java.sql.Timestamp _sqlDateTimestamp) {		
		return new java.util.Date(_sqlDateTimestamp.getTime()).toInstant();
   }
	public static final Instant fromSQL(java.sql.Time _sqlDateTime) {		
		return new java.util.Date(_sqlDateTime.getTime()).toInstant();
   }
	public static final Instant fromSQL(java.sql.Date _sqlDate) {		
		return new java.util.Date(_sqlDate.getTime()).toInstant();
		/*
		Instant        timestamp = utilDate.toInstant();
		
		LocalDateTime date = LocalDateTime.ofInstant(timestamp, localZoneID);

		//Calendar to Instant
		Instant time = Calendar.getInstance().toInstant();
		System.out.println(time);
		//TimeZone to ZoneId
		ZoneId defaultZone = TimeZone.getDefault().toZoneId();
		System.out.println(defaultZone);
    
		//ZonedDateTime from specific Calendar
		ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
		System.out.println(gregorianCalendarDateTime);
    
		//Date API to Legacy classes
		Date dt = Date.from(Instant.now());
		System.out.println(dt);
    
		TimeZone tz = TimeZone.getTimeZone(defaultZone);
		System.out.println(tz);
    
		GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
		System.out.println(gc);
*/        
   }
	public static final String toSQL(Instant _instant) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(sqlFormat);
		
		try {
		    return LocalDateTime.ofInstant(_instant, localZoneOffset).format(format);
		} catch (DateTimeException exc) { throw exc; }
	}


	/**
	 * Get an Instant from elapsed millisecond since Epoch origin <br>
	 * 
	 * @return the corresponding Instant
	 */
	public static final Instant fromEpochMilli(long _milli) {
		return Instant.ofEpochMilli(_milli);
	}
	/**
	 * Get Instant as elapsed millisecond since Epoch origin <br>
	 * 
	 */
	public static final long toEpochMilli(Instant _instant) {
		return _instant.toEpochMilli();
	}

	/**
	 * Get LocalDateTime as Instant for defined localZone <br>
	 * 
	 */
	public static final Instant fromLocalDateTime(LocalDateTime _ldt) {
		return _ldt.toInstant(localZoneOffset);
	}
	/**
	 * Get Instant as LocalDateTime for defined localZone <br>
	 * 
	 */
	public static final LocalDateTime toLocalDateTime(Instant _instant) {
		return LocalDateTime.ofInstant(_instant, localZoneID);
	}

	/**
	 * Add _milli milliseconds to _date <br>
	 * 
	 */
	public static final Instant plusMilli(Instant _date, long _milli) {
	    return _date.plus(_milli, ChronoField.MILLI_OF_DAY.getBaseUnit());
	}

	
	
	
	
	
	
	
	
	
	
	/**
	 * Mini Chronomètre
	 * !!! Ne pas utiliser plusieurs fois en même temps !!!
	 * !!! Not multi instance safe !!!
	 */
	private static long resetNanos;
	
	static {
		TimeHelper.reset();
	}

	public static void reset() {
		resetNanos = System.nanoTime();
	}
	public static float getMilliseconds() {
		return (System.nanoTime() - resetNanos) / 1000 * 1f / 1000;
	}

}
