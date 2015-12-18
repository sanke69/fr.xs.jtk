package fr.xs.jtk.bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class ByteStreams {

	private static final class FastByteArrayOutputStream extends ByteArrayOutputStream {
		/**
		 * Writes the contents of the internal buffer to the given array
		 * starting at the given offset. Assumes the array has space to hold
		 * count bytes.
		 */
		void writeTo(byte[] b, int off) {
			System.arraycopy(buf, 0, b, off, count);
		}
	}

	public static long copy(InputStream from, OutputStream to) throws IOException {
	    byte[] buf = new byte[0x1000]; // 4K
	    long total = 0;
	    while (true) {
	      int r = from.read(buf);
	      if (r == -1) {
	        break;
	      }
	      to.write(buf, 0, r);
	      total += r;
	    }
	    return total;
	  }
	  
	public static byte[] toByteArray(InputStream in) {
		int    expectedSize = -1;
		byte[] result = null;
		try {
			expectedSize = in.available();

			byte[] bytes = new byte[expectedSize];
			int remaining = expectedSize;
			while(remaining > 0) {
				int off = expectedSize - remaining;
				int read = in.read(bytes, off, remaining);
				if(read == -1)
					// end of stream before reading expectedSize bytes
					// just return the bytes read so far
					return Arrays.copyOf(bytes, off);
				remaining -= read;
			}
	
			// bytes is now full
			int b = in.read();
			if(b == -1) {
				return bytes;
			}

			// the stream was longer, so read the rest normally
			FastByteArrayOutputStream out = new FastByteArrayOutputStream();
			out.write(b); // write the byte we read when testing for end of stream
			copy(in, out);
	
			result = new byte[bytes.length + out.size()];
			System.arraycopy(bytes, 0, result, 0, bytes.length);
			out.writeTo(result, bytes.length);

		} catch(IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
