package fr.xs.jtk.tools;

public class HashTag {
	long   value;
	String hashtag;

	public static HashTag generate(String _string) {
		return generate(_string.getBytes());
	}
	public static HashTag generate(byte[] _data) {
		// cf. Jenkins One At a Time Hash - Bret Mulvey
	    long hash = 0;
	    for(byte b : _data) {   
	        hash += b;
	        hash += (hash << 10);
	        hash ^= (hash >> 6);    
	    }

	    hash += (hash << 3);
	    hash ^= (hash >> 11);
	    hash += (hash << 15);

	    hash  = (long) (hash % 1e16);
	    hash *= hash > 0 ? 1 : -1;

		return new HashTag(hash);
	}


	public HashTag() {
		value = -1L;
	}
	public HashTag(long _value) {
		value   = _value;
		hashtag = Long.toHexString(value);
	}
	public HashTag(String _hashtag) {
		hashtag = _hashtag;
		value   = Long.parseLong(_hashtag, 16);
	}

	public Long toLong() {
	    return value;
	}
	public String toString() {
	    return hashtag; //String.format("%018d", value);
	}
	
}
