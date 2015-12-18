package fr.xs.jtk.security.encryption.onekey;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SimpleKey {
	Key       key;
	Algorithm algorithm;

	public enum Algorithm {
		BLOWFISH("Blowfish"), ;
		
		String javaName;

		Algorithm(String _name) {
			javaName = _name;
		}
		
		public String toString() {
			return javaName;
		}

	}

	public static SimpleKey generateKey(int _keySize, Algorithm _algorithm) {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance(_algorithm.toString());
			keyGen.init(_keySize);
			return new SimpleKey(keyGen.generateKey(), _algorithm);

		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SimpleKey(byte[] _data, Algorithm _algorithm) {
		try {
			key    = new SecretKeySpec(_data, _algorithm.toString());
		} catch(IllegalArgumentException e) {}
	}
	public SimpleKey(SecretKey _key, Algorithm _algorithm) {
		key       = _key;
		algorithm = _algorithm;
	}

	public Key asKey() {
		return key;
	};

	public byte[] getKey() {
		return key.getEncoded();
	}
	public Algorithm getType() {
		return algorithm;
	}

	public String asString() {
		return new String(key.getEncoded());
	}

}
