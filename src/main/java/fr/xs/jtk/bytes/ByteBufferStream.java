package fr.xs.jtk.bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteBufferStream {
	final ByteBuffer   buffer;
	final OutputStream os;
	final InputStream  is;

	public ByteBufferStream() {
		buffer = ByteBuffer.allocate(10);
		is = new ByteBufferBackedInputStream(buffer);
		os = new ByteBufferBackedOutputStream(buffer);
	}
	public ByteBufferStream(int _size) {
		buffer = ByteBuffer.allocate(_size);
		is = new ByteBufferBackedInputStream(buffer);
		os = new ByteBufferBackedOutputStream(buffer);
	}
	
	public synchronized InputStream  asInput()  { return is; }
	public synchronized OutputStream asOutput() { return os; }

}

class ByteBufferBackedInputStream extends InputStream {

	ByteBuffer buf;

	ByteBufferBackedInputStream(ByteBuffer buf) {
		this.buf = buf;
	}

	public synchronized int read() throws IOException {
		if(!buf.hasRemaining()) {
			return -1;
		}
		return buf.get();
	}

	public synchronized int read(byte[] bytes, int off, int len) throws IOException {
		len = Math.min(len, buf.remaining());
		buf.get(bytes, off, len);
		return len;
	}
}

class ByteBufferBackedOutputStream extends OutputStream {
	ByteBuffer buf;

	ByteBufferBackedOutputStream(ByteBuffer buf) {
		this.buf = buf;
	}

	public synchronized void write(int b) throws IOException {
		buf.put((byte) b);
	}

	public synchronized void write(byte[] bytes, int off, int len) throws IOException {
		buf.put(bytes, off, len);
	}

}

class MyByteArrayOutputStream extends ByteArrayOutputStream {
	public MyByteArrayOutputStream() {
	}

	public MyByteArrayOutputStream(int size) {
		super(size);
	}

	public int getCount() {
		return count;
	}

	public byte[] getBuf() {
		return buf;
	}

}