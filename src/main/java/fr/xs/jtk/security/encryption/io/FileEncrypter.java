package fr.xs.jtk.security.encryption.io;

import java.nio.ByteBuffer;
import java.util.Arrays;

import fr.xs.jtk.security.encryption.twokey.PairKey;
import fr.xs.jtk.security.encryption.twokey.PairKeyCODEC;

public class FileEncrypter {
	private int blockSize;
	private byte[] block;

	PairKeyCODEC codec;

	public FileEncrypter(int _blockSize, PairKeyCODEC _codec) {
		blockSize = _blockSize;
		block = new byte[blockSize];
		codec = _codec;
	}

	public void setBlockSize(int _blockSize) {
		if(_blockSize != blockSize) {
			blockSize = _blockSize;
			block = new byte[blockSize];
		}
	}

	public byte[] encrypt(byte[] _data, PairKey _key) {
		int size = _data.length, beg = 0, end = blockSize, retSize = 0;
		byte[]     cipher = new byte[2 * _data.length];
		ByteBuffer Cipher = ByteBuffer.wrap(cipher);

		while(size >= blockSize) {
			block = Arrays.copyOfRange(_data, beg, end);

			byte[] buffer = codec.encrypt(block, _key);
			Cipher.put(buffer);

			size -= blockSize;
			retSize += buffer.length;
			beg += blockSize;
			end += blockSize;
		}

		if(size != 0) {
			block = Arrays.copyOfRange(_data, beg, _data.length);

			byte[] buffer = codec.encrypt(block, _key);
			Cipher.put(buffer);

			size -= blockSize;
			retSize += buffer.length;
			beg += blockSize;
			end += blockSize;
		}

		return Arrays.copyOfRange(cipher, 0, retSize);
	}

	public byte[] decrypt(byte[] _cypher, PairKey _key) {
		int size = _cypher.length, beg = 0, end = blockSize, retSize = 0;
		byte[] plain = new byte[2 * _cypher.length];
		ByteBuffer Plain = ByteBuffer.wrap(plain);

		while(size >= blockSize) {
			block = Arrays.copyOfRange(_cypher, beg, end);

			byte[] buffer = codec.decrypt(block, _key);
			Plain.put(buffer);

			size -= blockSize;
			retSize += buffer.length;
			beg += blockSize;
			end += blockSize;
		}

		if(size != 0) {
			block = Arrays.copyOfRange(_cypher, beg, _cypher.length);

			byte[] buffer = codec.decrypt(block, _key);
			Plain.put(buffer);

			size -= blockSize;
			retSize += buffer.length;
			beg += blockSize;
			end += blockSize;
		}

		return Arrays.copyOfRange(plain, 0, retSize);
	}

	public void pad(byte[] in, int off, int len) {
		if(in == null)
			return;
		if((off + len) > in.length)
			throw new IllegalArgumentException("Buffer too small to hold padding");
		byte paddingOctet = (byte) (len & 0xff);
		for(int i = 0; i < len; i++)
			in[i + off] = paddingOctet;
	}

	public int unpad(byte[] in, int off, int len) {
		if((in == null) || (len == 0))
			return 0;
		byte lastByte = in[off + len - 1];
		int padValue = (int) lastByte & 0x0ff;
		if((padValue < 0x01) || (padValue > blockSize))
			return -1;
		int start = off + len - ((int) lastByte & 0x0ff);
		if(start < off)
			return -1;
		for(int i = 0; i < ((int) lastByte & 0x0ff); i++)
			if(in[start + i] != lastByte)
				return -1;
		return start;
	}

}
