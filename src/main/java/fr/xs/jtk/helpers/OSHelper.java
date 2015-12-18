package fr.xs.jtk.helpers;

import java.net.MalformedURLException;
import java.net.URL;

public final class OSHelper {
	private static String OS = null;
	private static String ARCH = null;
	private static NameOS ENUM = null;

	static { getOsName(); }
	
	public static enum NameOS { UNKNOWN, WINDOWS, LINUX, OSX, SOLARIS, ANDROID; }

	public static String getOsName() {
		if(OS == null) {
			OS   = System.getProperty("os.name");
			ARCH = System.getProperty("os.arch");
			if(isWindows()) {
				ENUM = NameOS.WINDOWS;
			} else if(isMac()) {
				ENUM = NameOS.OSX;
			} else if(isUnix()) {
				ENUM = NameOS.LINUX;
			} else if(isSolaris()) {
				ENUM = NameOS.SOLARIS;
			} else {
				ENUM = NameOS.UNKNOWN;
			}
		}
		return OS;
	}

	public static String getWorkingDirectory() {
		return System.getProperty("user.dir");
	}
	public static String getHomeDirectory() {
		return System.getProperty("user.home");
	}
	public static String getTemporaryFolder() {
		return System.getProperty("java.io.tmpdir");
	}

	public static NameOS get() {
		return ENUM;
	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0 || OS.indexOf("Win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	public static void main(String[] args) {
		System.out.println(OS);
		System.out.println(ARCH);

		if(isWindows()) {
			System.out.println("This is Windows");
		} else if(isMac()) {
			System.out.println("This is Mac");
		} else if(isUnix()) {
			System.out.println("This is Unix or Linux");
		} else if(isSolaris()) {
			System.out.println("This is Solaris");
		} else {
			System.out.println("Your OS is not support!!");
		}
	}

}