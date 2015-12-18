package fr.xs.jtk.math.type;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestVector2f {
	protected Vector2f v0, v1, v2;

	@Before
	public void setUp() {
		v0 = new Vector2f(1.0f, 0.0f);
		v1 = new Vector2f(1.0f, 1.0f);
		v2 = new Vector2f();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void addOperations() {
		Vector2f add = new Vector2f(2.0f, 1.0f);

		assertEquals(Vector2f.plus(v0, v1), add);
		assertEquals(Vector2f.plus(v0, v1, v2), add);
		assertEquals(v0.plus(1), add);
		assertEquals(v0.plus(1, 1), add);
		assertEquals(v0.plus(v1), add);

		v2 = v0.clone();
		v2.add(1);
		assertEquals(v2, add);

		v2 = v0.clone();
		v2.add(1, 1);
		assertEquals(v2, add);

		v2 = v0.clone();
		v2.add(v1);
		assertEquals(v2, add);
	}

	@Test
	public void subOperations() {
		Vector2f sub = new Vector2f(0.0f, -1.0f);

		assertEquals(Vector2f.minus(v0, v1), sub);
		assertEquals(Vector2f.minus(v0, v1, v2), sub);
		assertEquals(v0.minus(1), sub);
		assertEquals(v0.minus(1, 1), sub);
		assertEquals(v0.minus(v1), sub);

		v2 = v0.clone();
		v2.substract(1);
		assertEquals(v2, sub);

		v2 = v0.clone();
		v2.substract(1, 1);
		assertEquals(v2, sub);

		v2 = v0.clone();
		v2.substract(v1);
		assertEquals(v2, sub);
	}

	@Test
	public void mulOperations() {
		Vector2f mul = new Vector2f(1.0f, 0.0f);

		assertEquals(v0.times(1), mul);
		assertEquals(v0.times(1, 1), mul);
		assertEquals(v0.times(v1), mul);

		v2 = v0.clone();
		v2.multiply(1);
		assertEquals(v2, mul);

		v2 = v0.clone();
		v2.multiply(1, 1);
		assertEquals(v2, mul);

		v2 = v0.clone();
		v2.multiply(v1);
		assertEquals(v2, mul);
	}

	@Test
	public void negOperations() {
		Vector2f neg = new Vector2f(-1.0f, -0.0f);

		assertEquals(v0.negate(), neg);

		v2 = v0.clone();
		v2.setNegate();
		assertEquals(v2, neg);
	}

	@Test
	public void skewOperations() {
		Vector2f skew = new Vector2f(-0.0f, 1.0f);

		assertEquals(v0.skew(), skew);

		v2 = v0.clone();
		v2.setSkew();
		assertEquals(v2, skew);
	}

	@Test
	public void absOperations() {
		Vector2f abs = new Vector2f(1.0f, 0.0f);

		assertEquals(v0.abs(), abs);

		v2 = v0.clone();
		v2.setAbs();
		assertEquals(v2, abs);
	}

	@Test
	public void normOperations() {
		float l = 1.0f, n = 1.0f, n2 = 1.0f;

		assertEquals(v0.length() == l, true);
		assertEquals(v0.norm()   == n, true);
		assertEquals(v0.norm2()  == n2, true);
		
		assertEquals(v0.normalized(), v0);

		v2 = v0.clone();
		v2.normalize();
		assertEquals(v2, v0);		
	}

	@Test
	public void testOperations() {
		assertEquals(v0.isValid(), true);
		assertEquals(v0.isEqual(1), false);
		assertEquals(v1.isEqual(1), true);
		assertEquals(v0.isEqual(1, 1), false);
		assertEquals(v1.isEqual(1, 1), true);
		assertEquals(v0.isEqual(v1), false);
		assertEquals(v1.isEqual(v1), true);
		assertEquals(v0.isDifferent(1), true);
		assertEquals(v1.isDifferent(1), false);
		assertEquals(v0.isDifferent(1, 1), true);
		assertEquals(v1.isDifferent(1, 1), false);
		assertEquals(v0.isDifferent(v1), true);
		assertEquals(v1.isDifferent(v1), false);
	}

}
