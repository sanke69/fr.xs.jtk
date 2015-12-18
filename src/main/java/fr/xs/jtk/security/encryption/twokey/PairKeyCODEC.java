package fr.xs.jtk.security.encryption.twokey;

import java.util.Arrays;

public interface PairKeyCODEC {

	public byte[] encrypt(byte[] _data, PairKey _key);
	public byte[] decrypt(byte[] _cypher, PairKey _key);

	/**
	 * Ajoute un byte de valeur 1 au début du message afin d'éviter que ce
	 * dernier ne corresponde pas à un nombre négatif lorsqu'il sera transformé
	 * en BigInteger.
	 */
	public static byte[] addOneByte(byte[] input) {
		byte[] result = new byte[input.length + 1];
		result[0] = 1;
		System.arraycopy(input, 0, result, 1, input.length);
		return result;
	}

	/**
	 * Retire le byte ajouté par la méthode addOneByte.
	 */
	public static byte[] removeOneByte(byte[] input) {
		return Arrays.copyOfRange(input, 1, input.length);
	}

}
