package fr.xs.jtk.tools;

import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;

public class Logger {

	private FileWriter logFile = null;

	/* Another way of defining a Singleton in java */
	static private final Logger inst;
	static {
		inst = new Logger();
	}
	private Logger() {
		Date             date   = new Date();
		String           format = "yyyyMMdd_HHmm";
		SimpleDateFormat sdf    = new SimpleDateFormat(format);

		try {
			logFile = new FileWriter(sdf.format(date) + ".log");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**/

	public static void log(String _str) {
		Date             date   = new Date();
		String           format = "HH:mm:ss.SSS";
		SimpleDateFormat sdf    = new SimpleDateFormat(format);
		try {
			inst.logFile.write(sdf.format(date) + " [PRINT] " + _str + "\n");
			inst.logFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void err(String _str) {
		Date             date   = new Date();
		String           format = "HH:mm:ss.SSS";
		SimpleDateFormat sdf    = new SimpleDateFormat(format);
		try {
			inst.logFile.write(sdf.format(date) + " [ERROR] " + _str + "\n");
			inst.logFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void close() {
		try {
			inst.logFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
