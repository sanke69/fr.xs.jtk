package fr.xs.jtk.tools;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Debugger {
	static int     debugDefaultLevel;
	static int     debugDisplayLevel;
	static boolean printStackTrace = true;
	
	static {
		debugDefaultLevel =   0;
		debugDisplayLevel = 999;
	}

	static public void initialize(int _default) {
		debugDefaultLevel = _default;
	}
	static public void initialize(int _default, int _display) {
		debugDefaultLevel = _default;
		debugDisplayLevel = _display;
	}

	public static void info(String _msg) {
		System.out.println("INFO::" + _msg);
	}
	static public void log(String _msg) {
		if(debugDefaultLevel > debugDisplayLevel)
			System.out.println(_msg);
	}
	static public void log(String... _msg) {
		if(debugDefaultLevel > debugDisplayLevel)
			System.out.println(Arrays.stream(_msg).collect(Collectors.joining(" ")));
	}
	static public void log(int _lvl, String _msg) {
		if(_lvl > debugDisplayLevel)
			System.out.println(_msg);
	}
	static public void log(int _lvl, String... _msg) {
		if(_lvl > debugDisplayLevel)
			System.out.println(Arrays.stream(_msg).collect(Collectors.joining(" ")));
	}
	static public void log(int _lvl, Object... _msg) {
		String log = "";
		for(Object o : _msg)
			log += o.toString() + " ";
		if(_lvl > debugDisplayLevel)
			System.out.println(log);
	}
	static public void warning(String _msg) {
		System.out.println("WARNING::" + _msg);
	}
	static public void error(String _msg) {
		System.err.println("ERROR::" + _msg);
	}
	static public void exit_error(int _code, String _msg) {
		System.err.println("ERROR::" + _msg);
		System.exit(_code);
	}

	static public void append(int _lvl, String... _txt) {
		if(_lvl > debugDisplayLevel)
			for(String s : _txt)
				System.out.print(s);
	}

	static public void printObject(Object... O) {
		for(Object o : O)
			System.out.print(o.toString() + "\t");
		System.out.println();
	}
	static public void printInt(int... I) {
		for(int i : I)
			System.out.print(i + "\t");
		System.out.println();
	}
	static public void printDouble(double... D) {
		for(double d : D)
			System.out.print(d + "\t");
		System.out.println();
	}

	static public void printException(Exception e) {
		System.out.println(e);
		if(printStackTrace)
			e.printStackTrace();
	}

}
