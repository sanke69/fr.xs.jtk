package fr.xs.jtk.security.encryption.io;

import java.util.Arrays;

import fr.xs.jtk.security.encryption.twokey.PairKey;
import fr.xs.jtk.security.encryption.twokey.PairKeyCODEC;

public class ByteArrayEncrypter {
	PairKeyCODEC codec;

	public ByteArrayEncrypter(PairKeyCODEC _codec) {
		codec     = _codec;
	}

	public byte[] encrypt(byte[] _data, PairKey _key) {
		setMagicConstant(_key, false);

		int    size   = _data.length, beg = 0, retIndex = 0, retSize = 0;
		byte[] cipher = new byte[2 * magicRatio * _data.length], block, encrypted;

		while(size >= magicStep) {
			block     = Arrays.copyOfRange(_data, beg, beg + magicStep);
			encrypted = codec.encrypt(block, _key);
			encrypted = enchant(encrypted);

			if(encrypted.length != blockSize + magicPad) System.err.println("!!!");
			System.arraycopy(encrypted, 0, cipher, retIndex, encrypted.length);

			retIndex += encrypted.length;
			retSize  += encrypted.length;
			beg      += magicStep;
			size     -= magicStep;
		}

		if(size != 0) {
			block     = Arrays.copyOfRange(_data, beg, beg + size);
			encrypted = codec.encrypt(block, _key);
			encrypted = enchant(encrypted);

			System.arraycopy(encrypted, 0, cipher, retIndex, encrypted.length);

			retSize += encrypted.length;
		}

		return Arrays.copyOfRange(cipher, 0, retSize);
	}
	int mod = 80, counter = 0;
	public byte[] decrypt(byte[] _cypher, PairKey _key) {
		setMagicConstant(_key, true);

		int size = _cypher.length, beg = 0, retIndex = 0, retSize = 0;
		byte[] plain = new byte[2 * _cypher.length], block, decrypted;

		counter = 0;
		while(size >= counter) {
			block        = Arrays.copyOfRange(_cypher, beg, beg + magicStep);
			block        = desenchant(block);
			decrypted    = codec.decrypt(block, _key);

			System.arraycopy(decrypted, 0, plain, retIndex, decrypted.length);

			retIndex += decrypted.length;
			retSize  += decrypted.length;
			beg      += magicStep;
			size     -= magicStep;
			
			++counter;
			System.out.print(".");
			if(counter % mod == 0)
				System.out.println("  " + counter + "  " + retSize + "/" + size + "/" + _cypher.length);
		}

		return Arrays.copyOfRange(plain, 0, retSize);
	}


	private final boolean noMagic = false;

	public byte[] enchant(byte[] _in) {
		if(noMagic)
			return _in;

		int neededPad = blockSize + magicPad - _in.length;

		byte[] enchanted = new byte[blockSize + magicPad];
		for(int i = 0; i < neededPad; ++i)
			enchanted[i] = (byte) (neededPad & 0xFF);

		System.arraycopy(_in, 0, enchanted, neededPad, _in.length);

		return enchanted;
	}
	public byte[] desenchant(byte[] _in) {
		if(noMagic)
			return _in;

		return Arrays.copyOfRange(_in, _in[0], _in.length);
	}

	private boolean decrypt = false;
	private int     blockSize, magicStep, magicRatio, magicPad;
	public void setMagicConstant(PairKey _key, boolean _decrypt) {
		blockSize = _key.getKeySize() / 8;
		decrypt   = _decrypt;

		if(noMagic) {
			magicRatio = 1; // [1..4..]
			magicStep  = blockSize / magicRatio;
		} else {
			magicRatio = 1; // [1..4..]
			magicStep  = !decrypt ? blockSize / magicRatio : blockSize + magicPad;
			magicPad   = 2;
		}
	}

}
