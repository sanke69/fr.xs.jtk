package fr.xs.jtk.math.type;

import java.io.Serializable;

public class Matrix2f implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public float m00, m01, m10, m11;


	public final static Matrix2f plus(final Matrix2f _a, final Matrix2f _b) {
		return new Matrix2f(_a.m00 + _b.m00, _a.m01 + _b.m01, _a.m10 + _b.m10, _a.m11 + _b.m11);
	}
	public final static Matrix2f plus(final Matrix2f _a, final Matrix2f _b, final Matrix2f _out) {
		return _out.set(_a.m00 + _b.m00, _a.m01 + _b.m01, _a.m10 + _b.m10, _a.m11 + _b.m11);
	}

	public final static Matrix2f minus(final Matrix2f _a, final Matrix2f _b) {
		return new Matrix2f(_a.m00 - _b.m00, _a.m01 - _b.m01, _a.m10 - _b.m10, _a.m11 - _b.m11);
	}
	public final static Matrix2f minus(final Matrix2f _a, final Matrix2f _b, final Matrix2f _out) {
		return _out.set(_a.m00 - _b.m00, _a.m01 - _b.m01, _a.m10 - _b.m10, _a.m11 - _b.m11);
	}

	public final static Matrix2f times(final Matrix2f A, final Matrix2f B) {
		final Matrix2f C = new Matrix2f();
		C.m00 = A.m00 * B.m00 + A.m01 * B.m10;
		C.m10 = A.m10 * B.m00 + A.m11 * B.m10;
		C.m01 = A.m00 * B.m01 + A.m01 * B.m11;
		C.m11 = A.m10 * B.m01 + A.m11 * B.m11;
		return C;
	}
	public final static Matrix2f times(final Matrix2f A, final Matrix2f B, final Matrix2f _out) {
		return _out.set(A.m00 * B.m00 + A.m01 * B.m10,
						A.m10 * B.m00 + A.m11 * B.m10,
						A.m00 * B.m01 + A.m01 * B.m11,
						A.m10 * B.m01 + A.m11 * B.m11);
	}

	public final static Vector2f times(final Matrix2f R, final Vector2f v) {
		return new Vector2f(R.m00 * v.x + R.m01 * v.y, R.m10 * v.x + R.m11 * v.y);
	}
	public final static Vector2f times(final Matrix2f R, final Vector2f v, final Vector2f _out) {
		return _out.set(R.m00 * v.x + R.m01 * v.y, R.m10 * v.x + R.m11 * v.y);
	}

	public final static Matrix2f timesTranspose(final Matrix2f A, final Matrix2f B) {
		final Matrix2f C = new Matrix2f();
		C.m00 = A.m00 * B.m00 + A.m10 * B.m10;
		C.m10 = A.m01 * B.m00 + A.m11 * B.m10;
		C.m01 = A.m00 * B.m01 + A.m10 * B.m11;
		C.m11 = A.m01 * B.m01 + A.m11 * B.m11;
		return C;
	}
	public final static Matrix2f timesTranspose(final Matrix2f A, final Matrix2f B, final Matrix2f out) {
		return out.set( A.m00 * B.m00 + A.m10 * B.m10,
						A.m01 * B.m00 + A.m11 * B.m10,
						A.m00 * B.m01 + A.m10 * B.m11,
						A.m01 * B.m01 + A.m11 * B.m11 );
	}

	public final static Vector2f timesTranspose(final Matrix2f R, final Vector2f v) {
		return new Vector2f((v.x * R.m00 + v.y * R.m10), (v.x * R.m01 + v.y * R.m11));
	}
	public final static Vector2f timesTranspose(final Matrix2f R, final Vector2f v, final Vector2f _out) {
		return _out.set((v.x * R.m00 + v.y * R.m10), (v.x * R.m01 + v.y * R.m11));
	}

	public Matrix2f() {
	}
	public Matrix2f(final float _m00, final float _m01, final float _m10, final float _m11) {
		m00 = _m00; m01 = _m01; m10 = _m10; m11 = _m11;
	}
	public Matrix2f(Matrix2f _d) {
		m00 = _d.m00; m01 = _d.m01; m10 = _d.m10; m11 = _d.m11;
	}

	/** Return a copy of this matrix. */
	@Override
	public final Matrix2f clone() {
		return new Matrix2f(m00, m01, m10, m11);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getColumn0().hashCode();
		result = prime * result + getColumn1().hashCode();
		return result;
	}

	/** Zero out this matrix. */
	public Matrix2f setZero() {
		m00 = m01 = m10 = m11 = 0.0f;
		return this;
	}
	/** Set as the identity matrix. */
	public final Matrix2f setIdentity() {
		m00 = 1.0f;
		m01 = 0.0f;
		m10 = 0.0f;
		m11 = 1.0f;
		return this;
	}
	public final Matrix2f setAbs() {
		m00 = Math.abs(m00);
		m01 = Math.abs(m01);
		m10 = Math.abs(m10);
		m11 = Math.abs(m11);
		return this;
	}


	/** Set the vector component-wise. */
	public Matrix2f set(final float _t) {
		m00 = _t; m01 = _t; m10 = _t; m11 = _t;
		return this;
	}
	public Matrix2f set(final float _m00, final float _m01, final float _m10, final float _m11) {
		m00 = _m00; m01 = _m01; m10 = _m10; m11 = _m11;
		return this;
	}
	public Matrix2f set(final Vector2f _col0, final Vector2f _col1) {
		m00 = _col0.x; m01 = _col1.x; m10 = _col0.y; m11 = _col1.y;
		return this;
	}
	public Matrix2f set(final Matrix2f _d) {
		m00 = _d.m00; m01 = _d.m01; m10 = _d.m10; m11 = _d.m11;
		return this;
	}

	public Vector2f getRow0() {
		return new Vector2f(m00, m01);
	}
	public void getRow0(final Vector2f _out) {
		_out.set(m00, m01);
	}
	public Vector2f getRow1() {
		return new Vector2f(m10, m11);
	}
	public void getRow1(final Vector2f _out) {
		_out.set(m10, m11);
	}

	public Vector2f getColumn0() {
		return new Vector2f(m00, m10);
	}
	public void getColumn0(final Vector2f _out) {
		_out.set(m00, m10);
	}
	public Vector2f getColumn1() {
		return new Vector2f(m01, m11);
	}
	public void getColumn1(final Vector2f _out) {
		_out.set(m01, m11);
	}

	public Matrix2f add(final float _t) {
		m00 += _t; m01 += _t; m10 += _t; m11 += _t;
		return this;
	}
	public Matrix2f add(final Matrix2f _d) {
		m00 += _d.m00; m01 += _d.m01; m10 += _d.m10; m11 += _d.m11;
		return this;
	}

	/** Return the sum of this matrix and another; does not alter either one. */
	public Matrix2f plus(final float _t) {
		return new Matrix2f(m00 + _t, m01 + _t, m10 + _t, m11 + _t);
	}
	public Matrix2f plus(final Matrix2f _d) {
		return new Matrix2f(m00 + _d.m00, m01 + _d.m01, m10 + _d.m10, m11 + _d.m11);
	}

	public Matrix2f substract(final float _t) {
		m00 -= _t; m01 -= _t; m10 -= _t; m11 -= _t;
		return this;
	}
	public Matrix2f substract(final Matrix2f _d) {
		m00 -= _d.m00; m01 -= _d.m01; m10 -= _d.m10; m11 -= _d.m11;
		return this;
	}

	/** Return the difference of this matrix and another; does not alter either one. */
	public Matrix2f minus(final float _t) {
		return new Matrix2f(m00 - _t, m01 - _t, m10 - _t, m11 - _t);
	}
	public Matrix2f minus(final Matrix2f _d) {
		return new Matrix2f(m00 - _d.m00, m01 - _d.m01, m10 - _d.m10, m11 - _d.m11);
	}

	public Matrix2f multiply(final float _t) {
		m00 *= _t; m01 *= _t; m10 *= _t; m11 *= _t;
		return this;
	}
	public Matrix2f multiply(final Matrix2f _d) {
		Matrix2f D = new Matrix2f(
									m00*_d.m00 + m01*_d.m10,
									m00*_d.m01 + m01*_d.m11,
									m10*_d.m00 + m11*_d.m10,
									m10*_d.m01 + m11*_d.m11
								 );
		this.set(D);
		return this;
	}
	public Matrix2f multiplyTranspose(final Matrix2f _d) {
		Matrix2f.timesTranspose(this, _d, this);
		return this;
	}

	/** Return this matrix multiplied by a scalar; does not alter this matrix. */
	public Matrix2f times(final float _t) {
		return new Matrix2f(m00 * _t, m01 * _t, m10 * _t, m11 * _t);
	}
	public final Vector2f times(final Vector2f v) {
		return new Vector2f(m00 * v.x + m01 * v.y, m10 * v.x + m11 * v.y);
	}
	public final Vector2f times(final Vector2f v, final Vector2f _out) {
		return _out.set(m00 * v.x + m01 * v.y, m10 * v.x + m11 * v.y);
	}
	/** Return this matrix multiplied by a matrix; does not alter this matrix. */
	public Matrix2f times(final Matrix2f _d) {
		return new Matrix2f(	m00*_d.m00 + m01*_d.m10,
								m00*_d.m01 + m01*_d.m11,
								m10*_d.m00 + m11*_d.m10,
								m10*_d.m01 + m11*_d.m11
							 );
	}

	public final Matrix2f timesTranspose(final Matrix2f B) {
		return new Matrix2f(
								Vector2f.dot(getColumn0(), B.getColumn0()),
								Vector2f.dot(getColumn0(), B.getColumn1()),
								Vector2f.dot(getColumn1(), B.getColumn0()),
								Vector2f.dot(getColumn1(), B.getColumn1())
							);
	}
	public final Vector2f timesTranspose(final Vector2f v) {
		return new Vector2f((v.x * m00 + v.y * m10), (v.x * m01 + v.y * m11));
	}


	public Matrix2f divide(final float _t) throws Exception {
		if(_t == 0) throw new Exception();
		m00 /= _t; m01 /= _t; m10 /= _t; m11 /= _t;
		return this;
	}

	/** Return a new vector that has positive components. */
	public final Matrix2f abs() {
		return new Matrix2f(Math.abs(m00), Math.abs(m01), Math.abs(m10), Math.abs(m11));
	}

	public final Matrix2f inverse() {
		final float a = m00, b = m01, c = m10, d = m11;
		float det = a * d - b * c;
		if(det != 0)
			det = 1.0f / det;
		m00 = det * d;
		m01 = -det * b;
		m10 = -det * c;
		m11 = det * a;
		return this;
	}

	/** Returns the inverted matrix - does not alter this matrix */
	public final Matrix2f invert() {
	    final float a = m00, b = m01, c = m10, d = m11;
		final Matrix2f B = new Matrix2f();
		float det = a * d - b * c;
		if(det != 0)
			det = 1.0f / det;
		B.m00 = det * d;
		B.m01 = -det * b;
		B.m10 = -det * c;
		B.m11 = det * a;
		return B;
	}
	public final Matrix2f invert(final Matrix2f _out) {
	    final float a = m00, b = m01, c = m10, d = m11;
		float det = a * d - b * c;
		if(det != 0)
			det = 1.0f / det;
		_out.m00 = det * d;
		_out.m01 = -det * b;
		_out.m10 = -det * c;
		_out.m11 = det * a;
		return _out;
	}

	/**
	 * Solve A * x = b where A = this matrix.
	 * 
	 * @return The vector x that solves the above equation.
	 */
	public final Vector2f solve(final Vector2f b) {
		final float a11 = m00, a12 = m01, a21 = m10, a22 = m11;
		float det = a11 * a22 - a12 * a21;
		if(det != 0.0f)
			det = 1.0f / det;
		final Vector2f x = new Vector2f(det * (a22 * b.x - a12 * b.y), det * (a11 * b.y - a21 * b.x));
		return x;
	}
	public final Vector2f solve(final Vector2f b, final Vector2f _out) {
		final float a11 = m00, a12 = m01, a21 = m10, a22 = m11;
		float det = a11 * a22 - a12 * a21;
		if(det != 0.0f)
			det = 1.0f / det;
		_out.set(det * (a22 * b.x - a12 * b.y), det * (a11 * b.y - a21 * b.x));
		return _out;
	}

	/** ASSUMED AS TRANSFORMATION MATRIX */

	/** Extract the angle from this matrix (assumed to be a rotation matrix). */
	public final float getAngle() {
		return (float) Math.atan2(m01, m00);
	}
	public final float getCos() {
		return m00;
//		return (float) m00 == m11 ? m00 : -3.9f;
	}
	public final float getSin() {
		return m10;
//		return (float) m01 == -m10 ? m10 : -3.9f;
	}

	public final static Matrix2f createRotationalTransform(float angle) {
		Matrix2f mat = new Matrix2f();
		final float c = (float) Math.cos(angle);
		final float s = (float) Math.sin(angle);
		mat.m00 = c;
		mat.m01 = -s;
		mat.m10 = s;
		mat.m11 = c;
		return mat;
	}

	public final static Matrix2f createScaleTransform(float scale) {
		return new Matrix2f(scale, 0.0f, 0.0f, scale);
	}
	public final static Matrix2f createScaleTransform(float scale, final Matrix2f _out) {
		return _out.set(scale, 0.0f, 0.0f, scale);
	}


	/** @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Matrix2f other = (Matrix2f) obj;
		if(Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00))
			return false;
		if(Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01))
			return false;
		if(Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10))
			return false;
		if(Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "[" + m00 + " " + m01 + ", " + m10 + " " + m11 + "]";
	}

}
