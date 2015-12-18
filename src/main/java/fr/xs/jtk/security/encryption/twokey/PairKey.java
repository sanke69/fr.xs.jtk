package fr.xs.jtk.security.encryption.twokey;

import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;

public class PairKey {
	Key       publicKey, privateKey;
	Algorithm algorithm;
	int       size;

	public enum Algorithm {
		RSA("RSA"), DSA("DSA");
		
		String javaName;

		Algorithm(String _name) {
			javaName = _name;
		}
		
		public String toString() {
			return javaName;
		}

	}

	public static PairKey generatePairKey(int _keySize, Algorithm _algorithm) {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance(_algorithm.toString());
			keyGen.initialize(_keySize, new SecureRandom(new byte[]{ 'S', 'P', '-', 'T', 'E', 'S', 'T' }));
		    KeyPair kp = keyGen.generateKeyPair();

			return new PairKey(kp.getPublic(), kp.getPrivate(), _keySize, _algorithm);

		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PairKey(byte[] _public, byte[] _private, int _size, Algorithm _algorithm) {
		size = _size;
		algorithm = _algorithm;
		try {
			switch(algorithm) {
			case RSA : 	if(_public != null) {
							X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(_public);
							KeyFactory keyFactory = KeyFactory.getInstance(algorithm.toString());
							publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
						}
						if(_private != null) {
							PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(_private);
							KeyFactory keyFactory = KeyFactory.getInstance(algorithm.toString());
							privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
						}
						break;
			}
		} catch (Exception e) {System.out.println(e);}
	}
	public PairKey(Key _public, Key _private, int _size, Algorithm _algorithm) {
		size = _size;
		if(_public != null && _private != null)
			if(_public.getAlgorithm().compareToIgnoreCase(_private.getAlgorithm()) != 0)
				System.err.println("Mismatch type for key pair");
		
		publicKey  = _public;
		privateKey = _private;
		algorithm  = _algorithm;
	}

	public Key asPublicKey() {
		return publicKey;
	};
	public Key asPrivateKey() {
		return privateKey;
	};

	public byte[] getPublicKey() {
		return publicKey.getEncoded();
	}
	public byte[] getPrivateKey() {
		return privateKey.getEncoded();
	}
	public int getKeySize() {
		return size;
	}
	public Algorithm getType() {
		return algorithm;
	}

	public String asString() {
		return new String(publicKey.getEncoded());
	}

}
