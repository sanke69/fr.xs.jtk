package fr.xs.jtk.security.signature;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import fr.xs.jtk.tools.Debugger;

public class SHA {

	public static byte[] get(byte[] _data) {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			Debugger.printException(e);
		}

		md.update(_data);
		return md.digest();
	}
	public static byte[] get(InputStream _stream) {
		byte[] data = null;

		try {
			data = new byte[_stream.available()];
			_stream.read(data);
		} catch (IOException e) {
			Debugger.printException(e);
		}

		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			Debugger.printException(e);
		}

		md.update(data);
		return md.digest();
	}

}
