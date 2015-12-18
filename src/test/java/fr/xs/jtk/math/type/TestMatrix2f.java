package fr.xs.jtk.math.type;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMatrix2f {
	protected Matrix2f m, n, o;
	protected Vector2f   v, w, x;

	@Before
	public void setUp() {
		m = new Matrix2f(1.0f, 0.0f, 0.0f, 1.0f);
		n = new Matrix2f(1.0f, 1.0f, 1.0f, 1.0f);
		o = new Matrix2f().setIdentity();
		v = new Vector2f(1.0f, 1.0f);
		x = new Vector2f();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void addOperations() {
		Matrix2f add = new Matrix2f(2.0f, 1.0f, 1.0f, 2.0f);

		assertEquals(Matrix2f.plus(m, n), add);
		assertEquals(Matrix2f.plus(m, n, o), add);
		assertEquals(m.plus(n), add);

		o = m.clone();
		o.add(n);
		assertEquals(o, add);
	}

	@Test
	public void subOperations() {
		Matrix2f sub = new Matrix2f(0.0f, -1.0f, -1.0f, 0.0f);

		assertEquals(Matrix2f.minus(m, n), sub);
		assertEquals(Matrix2f.minus(m, n, o), sub);
		assertEquals(m.minus(n), sub);

		o = m.clone();
		o.substract(n);
		assertEquals(o, sub);
	}

	@Test
	public void mulOperations() {
		m = new Matrix2f(1.0f, 0.0f, 0.0f, 1.0f);
		n = new Matrix2f(1.0f, 1.0f, 1.0f, 1.0f);
		o = new Matrix2f().setIdentity();
		v = new Vector2f(1.0f, 1.0f);
		w = new Vector2f(1.0f, 1.0f);
		Matrix2f mul = new Matrix2f(1.0f, 1.0f, 1.0f, 1.0f);
		
		assertEquals(Matrix2f.times(m, v), w);
		assertEquals(Matrix2f.times(m, n), mul);
		assertEquals(Matrix2f.times(m, n, o), mul);

		o = m.clone();
		o.multiply(n);
		assertEquals(o, mul);

		assertEquals(Matrix2f.timesTranspose(m, v), w);
		assertEquals(Matrix2f.timesTranspose(m, n), mul);

		x = m.timesTranspose(v);
		assertEquals(x, w);

		o = m.timesTranspose(n);
		assertEquals(o, mul);
	}

	@Test
	public void absOperations() {
		Matrix2f abs = new Matrix2f(1.0f, 0.0f, 0.0f, 1.0f);

		assertEquals(m.abs(), abs);

		n = m.clone();
		n.setAbs();
		assertEquals(n, abs);
	}

	@Test
	public void invOperations() {
		Matrix2f inv = new Matrix2f(1.0f, -0.0f, -0.0f, 1.0f);

		assertEquals(m.invert(), inv);
		assertEquals(m.inverse(), inv);
	}

	@Test
	public void solveOperations() {
		Vector2f B   = new Vector2f(1.0f, 1.0f);
		Vector2f sol = new Vector2f(1.0f, 1.0f);

		assertEquals(m.solve(B), sol);
		assertEquals(m.solve(B, x), sol);
	}

	@Test
	public void rotationOperations() {
		m = Matrix2f.createRotationalTransform((float) Math.PI / 2);
		assertEquals(m, m);
	}

	@Test
	public void scaleOperations() {
		m = Matrix2f.createScaleTransform((float) Math.PI / 2);
		assertEquals(m, m);
	}

}
