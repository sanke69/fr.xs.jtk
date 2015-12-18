package fr.xs.jtk.security.signature;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

import fr.xs.jtk.security.encryption.twokey.PairKey;
import fr.xs.jtk.tools.Debugger;

public class DSA {

	public static byte[] get(byte[] _data, PairKey _keys) {
		try {
			Signature signature = Signature.getInstance("DSA");
			signature.initSign((PrivateKey) (_keys.asPrivateKey()));
			signature.update(_data);

			return signature.sign();

		} catch (Exception e) {
			Debugger.printException(e);
		}
		return null;
	}
	public static byte[] get(InputStream _stream, PairKey _keys) {
		try {
			byte[] data = null;

			data = new byte[_stream.available()];
			_stream.read(data);

			Signature signature = Signature.getInstance("DSA");
			signature.initSign((PrivateKey) (_keys.asPrivateKey()));
			signature.update(data);

			return signature.sign();

		} catch (Exception e) {
			Debugger.printException(e);
		}
		return null;
	}

	public static boolean check(byte[] _data, byte[] _signature, PairKey _keys) {
		try {
			Signature signature = Signature.getInstance("DSA");
			signature.initVerify((PublicKey) (_keys.asPublicKey()));
			signature.update(_data);

			return signature.verify(_signature);

		} catch (Exception e) {
			Debugger.printException(e);
		}
		return false;
	}
	public static boolean check(InputStream _stream, byte[] _signature, PairKey _keys) {
		try {
			byte[] data = null;

			data = new byte[_stream.available()];
			_stream.read(data);

			Signature signature = Signature.getInstance("DSA");
			signature.initVerify((PublicKey) (_keys.asPublicKey()));
			signature.update(data);

			return signature.verify(_signature);

		} catch (Exception e) {
			Debugger.printException(e);
		}
		return false;
	}
	  
}
