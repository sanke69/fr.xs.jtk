package fr.xs.jtk.security.encryption.twokey;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

public class RSA implements PairKeyCODEC {
	
	final boolean onlyPositiveValue = false;

	@Override
	public byte[] encrypt(byte[] _data, PairKey _keys) {
		byte[] data = null;
		if(onlyPositiveValue) data = PairKeyCODEC.addOneByte(_data);
		else                  data = _data;

		return crypt(new BigInteger(data), _keys).toByteArray();
	}

	@Override
	public byte[] decrypt(byte[] _cipher, PairKey _keys) {
		byte[] msg = decrypt(new BigInteger(_cipher), _keys).toByteArray();

		if(onlyPositiveValue) return PairKeyCODEC.removeOneByte(msg);
		else                  return msg;
	}

	private BigInteger crypt(BigInteger plaintext, PairKey _keys) {
		return plaintext.modPow(((RSAPublicKey) _keys.asPublicKey()).getPublicExponent(), ((RSAPublicKey) _keys.asPublicKey()).getModulus());
	}

	private BigInteger decrypt(BigInteger ciphertext, PairKey _keys) {
		return ciphertext.modPow(((RSAPrivateKey) _keys.asPrivateKey()).getPrivateExponent(), ((RSAPrivateKey) _keys.asPrivateKey()).getModulus());
	}

	private static RSA instance;
	private RSA() {
		;
	}
	public static RSA codec() {
		if(instance == null)
			instance = new RSA();
		return instance;
	}

}
