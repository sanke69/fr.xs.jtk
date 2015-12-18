package fr.xs.jtk.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.xs.jtk.security.encryption.io.ByteArrayEncrypter;
import fr.xs.jtk.security.encryption.onekey.Blowfish;
import fr.xs.jtk.security.encryption.onekey.SimpleKey;
import fr.xs.jtk.security.encryption.twokey.PairKey;
import fr.xs.jtk.security.encryption.twokey.RSA;

import fr.xs.jtk.helpers.OSHelper;

public class FileEncryption {

	public static void main(String... argv) {
		//blowfishTest();
		RSAStreamTest();
		//RSAFileTest();
	}

	public static void blowfishTest() {
		String plaintext = "Yes It Works !!!";
		String path = null;
		
		switch(OSHelper.get()) {
		case WINDOWS : 	path = "H:/01 - Bust Out.mp3";
					  	break;
		case LINUX : 	path = "/media/sanke/share/01 - Bust Out.mp3";
		   				break;
		}

		byte[] data      = null;
		InputStream stream;
		try {
			stream = new FileInputStream(new File(path));
			data = new byte[stream.available()]; // plaintext.getBytes();
			stream.read(data);
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

		SimpleKey key     = SimpleKey.generateKey(128, SimpleKey.Algorithm.BLOWFISH);
		byte[]    cipher  = Blowfish.codec().encrypt(data, key);
		String    cypher  = new String(cipher);
		
		System.out.println("cipher= " + cypher + "\t(" + cipher.length + ")");

		SimpleKey theKey  = new SimpleKey(key.getKey(), key.getType());

		byte[]    decoded = Blowfish.codec().decrypt(cipher, theKey);
		String    plain   = new String(decoded);

		System.out.println("message= " + plain + "\t(" + decoded.length + ")");

		return ;
	}

	public static void RSAStreamTest() { int n = 2 * 64000;
		byte[] pattern   = "<< 69 Bytes - - - Test Array - - - sanke@sp-web.fr - - - P&L - - - >>".getBytes();

		byte[] data      = new byte[n * pattern.length];
		for(int i = 0; i < n; ++i)
			System.arraycopy(pattern, 0, data, i * pattern.length, pattern.length);

		ByteArrayEncrypter codec = new ByteArrayEncrypter( RSA.codec() );

		System.out.println("source (" + data.length + ")\n" + new String("data"));

		PairKey   key     = PairKey.generatePairKey(4096, PairKey.Algorithm.RSA);
		byte[]    cipher  = codec.encrypt(data, key);

		System.out.println("cipher (" + cipher.length + ")\n" + new String("cipher"));

		PairKey   theKey  = new PairKey(key.getPublicKey(), key.getPrivateKey(), key.getKeySize(), key.getType());
		byte[]    decoded = codec.decrypt(cipher, theKey);

		System.out.println("message (" + decoded.length + ")\n" + new String("decoded"));

		for(int i = 0; i < data.length; ++i)
			if(decoded[i] != data[i])
				System.err.println(i + "\t" + decoded[i] + " != " + data[i]);
		return ;
	}

	public static void RSAFileTest() {
		String file = null, path = null;

		switch(OSHelper.get()) {
		case WINDOWS : 	file = "H:/01 - Bust Out.mp3";
						path = file.substring(0, file.lastIndexOf("/") + 1);
					  	break;
		case LINUX : 	file = "/media/sanke/share/01 - Bust Out.mp3";
						path = file.substring(0, file.lastIndexOf("/") + 1);
		   				break;
		case ANDROID:
						break;
		case OSX:
						break;
		case SOLARIS:
						break;
		case UNKNOWN:
						break;
		}

		byte[] data = readFile(file);

		System.out.println("Source file (" + data.length + " o)\n");

		ByteArrayEncrypter codec = new ByteArrayEncrypter( RSA.codec() );

		System.out.println("Encrypting...\n");

		PairKey   key     = PairKey.generatePairKey(512, PairKey.Algorithm.RSA);
		byte[]    cipher  = codec.encrypt(data, key);

		System.out.println("Cipher file (" + cipher.length + " o)\n");

		System.out.println("Writing cipher...\n");

		writeFile(path + "Cipher" + file.substring(file.lastIndexOf(".")), cipher);

		System.out.println("Decrypting...\n");

		PairKey   theKey  = new PairKey(key.getPublicKey(), key.getPrivateKey(), key.getKeySize(), key.getType());
		byte[]    decoded = codec.decrypt(cipher, theKey);

		System.out.println("Message file (" + decoded.length + " o)\n");

		System.out.println("Writing message...\n");

		writeFile(path + "Message" + file.substring(file.lastIndexOf(".")), decoded);

		for(int i = 0; i < data.length; ++i)
			if(decoded[i] != data[i])
				System.err.println(i + "\t" + decoded[i] + " != " + data[i]);
		return ;
	}

	public static byte[] readFile(String _path) {
		InputStream stream;
		byte[] data = null;
		try {
			stream = new FileInputStream(new File(_path));
			data   = new byte[stream.available()];
			stream.read(data);
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	public static void writeFile(String _path, byte[] _in) {
		OutputStream stream;
		try {
			stream = new FileOutputStream(new File(_path));
			stream.write(_in);
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
