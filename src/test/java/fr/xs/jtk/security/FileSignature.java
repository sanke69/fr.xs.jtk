package fr.xs.jtk.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;

import fr.xs.jtk.security.encryption.twokey.PairKey;
import fr.xs.jtk.security.signature.DSA;
import fr.xs.jtk.security.signature.MD5;
import fr.xs.jtk.security.signature.SHA;
import fr.xs.jtk.tools.Debugger;

import fr.xs.jtk.helpers.OSHelper;

public class FileSignature {

	public static void main(String[] args) {
		testSimpleSignature();
		testSecuredSignature(false, true);
	}

	public static void testSimpleSignature() {
		String path = null;

		switch(OSHelper.get()) {
		case WINDOWS : 	path = "H:/zik/15 - I Tried  Cultore.mp3";
					  	break;
		case LINUX : 	path = "/media/sanke/share/zik/15 - I Tried  Cultore.mp3";
		   				break;
		}

		byte[] sign;

		try {
			sign = SHA.get(new FileInputStream(new File(path)));

			System.out.println("Algo: " + "SHA" + " (" + sign.length + " bytes)");
			for(int i = 0; i < sign.length; i++)
				System.out.print(sign[i] + " ");
			System.out.println("\nDecimal: " + new BigInteger(sign));
			System.out.println("Hexadecimal: " + new BigInteger(sign).toString(16).replace("-", "X"));

			sign = MD5.get(new FileInputStream(new File(path)));

			System.out.println("Algo: " + "MD5" + " (" + sign.length + " bytes)");
			for(int i = 0; i < sign.length; i++)
				System.out.print(sign[i] + " ");
			System.out.println("\nDecimal: " + new BigInteger(sign));
			System.out.println("Hexadecimal: " + new BigInteger(sign).toString(16).replace("-", "X"));
		} catch (Exception e) {
			Debugger.printException(e);
		}
	}

	public static void testSecuredSignature(boolean CHEAT_TEXT, boolean CHEAT_SIGNATURE) {
		String path = null;

		switch(OSHelper.get()) {
		case WINDOWS : 	path = "H:/01 - Bust Out.mp3";
					  	break;
		case LINUX : 	path = "/media/sanke/share/01 - Bust Out.mp3";
		   				break;
		}

		byte[] sign;

		try {
			PairKey keys = PairKey.generatePairKey(1024, PairKey.Algorithm.DSA);  // [512..1024]

			sign = DSA.get(new FileInputStream(new File(path)), keys);

			System.out.println("Algo: " + "DSA" + " (" + sign.length + " bytes)");
			for(int i = 0; i < sign.length; i++)
				System.out.print(sign[i] + " ");
			System.out.println("\nDecimal: " + new BigInteger(sign));
			System.out.println("Hexadecimal: " + new BigInteger(sign).toString(16).replace("-", "X"));

			InputStream stream = new FileInputStream(new File(path));
			byte[] data = new byte[stream.available()];
			stream.read(data);

			if(CHEAT_TEXT)      data[0]++;
			if(CHEAT_SIGNATURE) sign[sign.length - 1]++;

			PairKey theKeys = new PairKey(keys.asPublicKey(), null, keys.getKeySize(), keys.getType());
			
			System.out.println("Valid? : " + DSA.check(data, sign, theKeys));

		} catch (Exception e) {
			Debugger.printException(e);
		}
	}

}
