package fr.xs.jtk.tools;

import java.math.BigInteger;

public class UUID extends BigInteger {
	private static final long serialVersionUID = 1L;
	public static final UUID UNDEF = new UUID();

	public UUID() {
		super("-1");
	}
	public UUID(int _value) {
		super(Integer.toString(_value));
	}
	public UUID(long _value) {
		super(Long.toString(_value));
	}
	public UUID(String _value) {
		super(_value);
	}

}
