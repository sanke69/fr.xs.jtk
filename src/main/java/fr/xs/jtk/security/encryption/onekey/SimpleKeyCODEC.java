package fr.xs.jtk.security.encryption.onekey;

public interface SimpleKeyCODEC {

	public byte[] encrypt(byte[] _data, SimpleKey _key);
	public byte[] decrypt(byte[] _cypher, SimpleKey _key);

	/**
	 * Ajoute un byte de valeur 1 au début du message afin d'éviter que ce
	 * dernier ne corresponde pas à un nombre négatif lorsqu'il sera transformé
	 * en BigInteger.
	 */
	public static byte[] addOneByte(byte[] input) {
		byte[] result = new byte[input.length + 1];
		result[0] = 1;
		for(int i = 0; i < input.length; i++)
			result[i + 1] = input[i];
		return result;
	}

	/**
	 * Retire le byte ajouté par la méthode addOneByte.
	 */
	public static byte[] removeOneByte(byte[] input) {
		byte[] result = new byte[input.length - 1];
		for(int i = 0; i < result.length; i++)
			result[i] = input[i + 1];
		return result;
	}

}
