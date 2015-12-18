package fr.xs.jtk.security.encryption.onekey;

import javax.crypto.Cipher;

public class Blowfish implements SimpleKeyCODEC {

	@Override
	public byte[] encrypt(byte[] _data, SimpleKey _key) {
	    try {
		      Cipher cipher = Cipher.getInstance("Blowfish");
		      cipher.init(Cipher.ENCRYPT_MODE, _key.asKey());
		      return cipher.doFinal(_data);    
		    }
		    catch (Exception e) {System.out.println(e);} 
		    return null;
	}

	@Override
	public byte[] decrypt(byte[] _cipher, SimpleKey _key) {
	    try {
		      Cipher cipher = Cipher.getInstance("Blowfish");
		      cipher.init(Cipher.DECRYPT_MODE, _key.asKey());
		      return cipher.doFinal(_cipher);
		    }
		    catch (Exception e) {System.out.println(e);} 
		    return null;
	}

	private static Blowfish instance;
	private Blowfish() {
		;
	}
	public static Blowfish codec() {
		if(instance == null)
			instance = new Blowfish();
		return instance;
	}

}
