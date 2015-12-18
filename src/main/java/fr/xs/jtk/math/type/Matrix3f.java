package fr.xs.jtk.math.type;

import java.io.Serializable;

public class Matrix3f implements Serializable {
	private static final long serialVersionUID = 1L;

	public float m00, m01, m02, m10, m11, m12, m20, m21, m22;

	public final static void setScaleTransform(float scale, Matrix3f out) {
		out.m00 = scale;
		out.m11 = scale;
	}

	public static final Matrix3f times(final Matrix3f _A, final Matrix3f _B) {
		return new Matrix3f(
							_A.m00*_B.m00 + _A.m01*_B.m10 + _A.m02*_B.m20,
							_A.m00*_B.m01 + _A.m01*_B.m11 + _A.m02*_B.m21,
							_A.m00*_B.m02 + _A.m01*_B.m12 + _A.m02*_B.m22,
							_A.m10*_B.m00 + _A.m11*_B.m10 + _A.m12*_B.m20,
							_A.m10*_B.m01 + _A.m11*_B.m11 + _A.m12*_B.m21,
							_A.m10*_B.m02 + _A.m11*_B.m12 + _A.m12*_B.m22,
							_A.m20*_B.m00 + _A.m21*_B.m10 + _A.m22*_B.m20,
							_A.m20*_B.m01 + _A.m21*_B.m11 + _A.m22*_B.m21,
							_A.m20*_B.m02 + _A.m21*_B.m12 + _A.m22*_B.m22
								 );
	}
	public static final Matrix3f times(final Matrix3f _A, final Matrix3f _B, final Matrix3f _out) {
		return _out.set(
							_A.m00*_B.m00 + _A.m01*_B.m10 + _A.m02*_B.m20,
							_A.m00*_B.m01 + _A.m01*_B.m11 + _A.m02*_B.m21,
							_A.m00*_B.m02 + _A.m01*_B.m12 + _A.m02*_B.m22,
							_A.m10*_B.m00 + _A.m11*_B.m10 + _A.m12*_B.m20,
							_A.m10*_B.m01 + _A.m11*_B.m11 + _A.m12*_B.m21,
							_A.m10*_B.m02 + _A.m11*_B.m12 + _A.m12*_B.m22,
							_A.m20*_B.m00 + _A.m21*_B.m10 + _A.m22*_B.m20,
							_A.m20*_B.m01 + _A.m21*_B.m11 + _A.m22*_B.m21,
							_A.m20*_B.m02 + _A.m21*_B.m12 + _A.m22*_B.m22
								 );
	}
	public static final Vector3f times(final Matrix3f A, final Vector3f v) {
		return new Vector3f(v.x * A.m00 + v.y * A.m01 + v.z + A.m02, v.x * A.m10 + v.y * A.m11 + v.z * A.m12, v.x * A.m20 + v.y * A.m21 + v.z * A.m22);
	}
	public static final Vector3f times(final Matrix3f A, final Vector3f v, final Vector3f _out) {
		return _out.set(v.x * A.m00 + v.y * A.m01 + v.z + A.m02, v.x * A.m10 + v.y * A.m11 + v.z * A.m12, v.x * A.m20 + v.y * A.m21 + v.z * A.m22);
	}
	public static final Vector2f times(final Matrix3f A, final Vector2f v) {
		return new Vector2f(A.m00 * v.x + A.m01 * v.y, A.m10 * v.x + A.m11 * v.y);
	}
	public static final Vector2f times(final Matrix3f A, final Vector2f v, final Vector2f _out) {
		return _out.set(A.m00 * v.x + A.m01 * v.y, A.m10 * v.x + A.m11 * v.y);
	}

	public static final Matrix3f timesTranspose(final Matrix3f _A, final Matrix3f _B) {
		return new Matrix3f(
							_A.m00*_B.m00 + _A.m10*_B.m10 + _A.m20*_B.m20,
							_A.m00*_B.m01 + _A.m10*_B.m11 + _A.m20*_B.m21,
							_A.m00*_B.m02 + _A.m10*_B.m12 + _A.m20*_B.m22,
							_A.m01*_B.m00 + _A.m11*_B.m10 + _A.m21*_B.m20,
							_A.m01*_B.m01 + _A.m11*_B.m11 + _A.m21*_B.m21,
							_A.m01*_B.m02 + _A.m11*_B.m12 + _A.m21*_B.m22,
							_A.m02*_B.m00 + _A.m12*_B.m10 + _A.m22*_B.m20,
							_A.m02*_B.m01 + _A.m12*_B.m11 + _A.m22*_B.m21,
							_A.m02*_B.m02 + _A.m12*_B.m12 + _A.m22*_B.m22
								 );
	}
	public static final Matrix3f timesTranspose(final Matrix3f _A, final Matrix3f _B, final Matrix3f _out) {
		return _out.set(
							_A.m00*_B.m00 + _A.m10*_B.m10 + _A.m20*_B.m20,
							_A.m00*_B.m01 + _A.m10*_B.m11 + _A.m20*_B.m21,
							_A.m00*_B.m02 + _A.m10*_B.m12 + _A.m20*_B.m22,
							_A.m01*_B.m00 + _A.m11*_B.m10 + _A.m21*_B.m20,
							_A.m01*_B.m01 + _A.m11*_B.m11 + _A.m21*_B.m21,
							_A.m01*_B.m02 + _A.m11*_B.m12 + _A.m21*_B.m22,
							_A.m02*_B.m00 + _A.m12*_B.m10 + _A.m22*_B.m20,
							_A.m02*_B.m01 + _A.m12*_B.m11 + _A.m22*_B.m21,
							_A.m02*_B.m02 + _A.m12*_B.m12 + _A.m22*_B.m22
								 );
	}
	public static final Vector3f timesTranspose(final Matrix3f A, final Vector3f v) {
		return new Vector3f(	v.x * A.m00 + v.y * A.m10 + v.z + A.m20, v.x * A.m01 + v.y * A.m11 + v.z * A.m21, v.x * A.m02 + v.y * A.m12 + v.z * A.m22);
	}
	public static final Vector3f timesTranspose(final Matrix3f A, final Vector3f v, final Vector3f _out) {
		return _out.set(v.x * A.m00 + v.y * A.m10 + v.z + A.m20, v.x * A.m01 + v.y * A.m11 + v.z * A.m21, v.x * A.m02 + v.y * A.m12 + v.z * A.m22);
	}
	public static final Vector2f timesTranspose(final Matrix3f A, final Vector2f v) {
		return new Vector2f(A.m00 * v.x + A.m10 * v.y, A.m01 * v.x + A.m11 * v.y);
	}
	public static final Vector2f timesTranspose(final Matrix3f A, final Vector2f v, final Vector2f _out) {
		return _out.set(A.m00 * v.x + A.m10 * v.y, A.m01 * v.x + A.m11 * v.y);
	}

	public Matrix3f() {
	}
	public Matrix3f(final float _m00, final float _m01, final float _m02, final float _m10, final float _m11, final float _m12, final float _m20, final float _m21, final float _m22) {
		m00 = _m00; m01 = _m01; m02 = _m02;
		m10 = _m10; m11 = _m11; m12 = _m12;
		m20 = _m20; m21 = _m21; m22 = _m22;
	}
	public Matrix3f(final Matrix3f _d) {
		m00 = _d.m00; m01 = _d.m01; m02 = _d.m02;
		m10 = _d.m10; m11 = _d.m11; m12 = _d.m12;
		m20 = _d.m20; m21 = _d.m21; m22 = _d.m22;
	}

	/** Return a copy of this matrix. */
	@Override
	public final Matrix3f clone() {
		return new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	public final Matrix2f as2D() {
		return new Matrix2f(m00, m01, m10, m11);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getColumn0().hashCode();
		result = prime * result + getColumn1().hashCode();
	    result = prime * result + getColumn2().hashCode();
		return result;
	}

	/** Zero out this matrix. */
	public void setZero() {
		m00 = m01 = m02 = m10 = m11 = m21 = m20 = m21 = m22 = 0.0f;
	}
	/** Set as the identity matrix. */
	public final void setIdentity() {
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;
		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;
	}

	public final Matrix3f set(final float _t) {
		m00 = _t; m01 = _t; m02 = _t;
		m10 = _t; m11 = _t; m12 = _t;
		m20 = _t; m21 = _t; m22 = _t;
		return this;
	}
	public final Matrix3f set(final float _m00, final float _m01, final float _m02, final float _m10, final float _m11, final float _m12, final float _m20, final float _m21, final float _m22) {
		m00 = _m00; m01 = _m01; m02 = _m02;
		m10 = _m10; m11 = _m11; m12 = _m12;
		m20 = _m20; m21 = _m21; m22 = _m22;
		return this;
	}
	public final Matrix3f set(final Matrix3f _d) {
		m00 = _d.m00; m01 = _d.m01; m02 = _d.m02;
		m10 = _d.m10; m11 = _d.m11; m12 = _d.m12;
		m20 = _d.m20; m21 = _d.m21; m22 = _d.m22;
		return this;
	}

	public Vector3f getRow0() {
		return new Vector3f(m00, m01, m02);
	}
	public Vector3f getRow1() {
		return new Vector3f(m10, m11, m12);
	}
	public Vector3f getRow2() {
		return new Vector3f(m20, m21, m22);
	}

	public Vector3f getColumn0() {
		return new Vector3f(m00, m10, m20);
	}
	public Vector3f getColumn1() {
		return new Vector3f(m01, m11, 21);
	}
	public Vector3f getColumn2() {
		return new Vector3f(m02, m12, 22);
	}

	public final Matrix3f add(final float _t) {
		m00 += _t; m01 += _t; m02 += _t;
		m10 += _t; m11 += _t; m12 += _t;
		m20 += _t; m21 += _t; m22 += _t;
		return this;
	}
	public final Matrix3f add(final float _m00, final float _m01, final float _m02, final float _m10, final float _m11, final float _m12, final float _m20, final float _m21, final float _m22) {
		m00 += _m00; m01 += _m01; m02 += _m02;
		m10 += _m10; m11 += _m11; m12 += _m12;
		m20 += _m20; m21 += _m21; m22 += _m22;
		return this;
	}
	public final Matrix3f add(final Matrix3f _d) {
		m00 += _d.m00; m01 += _d.m01; m02 += _d.m02;
		m10 += _d.m10; m11 += _d.m11; m12 += _d.m12;
		m20 += _d.m20; m21 += _d.m21; m22 += _d.m22;
		return this;
	}

	/** Return the sum of this matrix and another; does not alter either one. */
	public final Matrix3f plus(final float _t) {
		return new Matrix3f(m00 + _t, m01 + _t, m02 + _t,
							m10 + _t, m11 + _t, m12 + _t,
							m20 + _t, m21 + _t, m22 + _t);
	}
	public final Matrix3f plus(final float _m00, final float _m01, final float _m02, final float _m10, final float _m11, final float _m12, final float _m20, final float _m21, final float _m22) {
		return new Matrix3f(m00 + _m00, m01 + _m01, m02 + _m02,
							m10 + _m10, m11 + _m11, m12 + _m12,
							m20 + _m20, m21 + _m21, m22 + _m22);
	}
	public final Matrix3f plus(final Matrix3f _d) {
		return new Matrix3f(m00 + _d.m00, m01 + _d.m01, m02 + _d.m02,
							m10 + _d.m10, m11 + _d.m11, m12 + _d.m12,
							m20 + _d.m20, m21 + _d.m21, m22 + _d.m22);
	}
	
	public final Matrix3f substract(final float _t) {
		m00 -= _t; m01 -= _t; m02 -= _t;
		m10 -= _t; m11 -= _t; m12 -= _t;
		m20 -= _t; m21 -= _t; m22 -= _t;
		return this;
	}
	public final Matrix3f substract(final float _m00, final float _m01, final float _m02, final float _m10, final float _m11, final float _m12, final float _m20, final float _m21, final float _m22) {
		m00 -= _m00; m01 -= _m01; m02 -= _m02;
		m10 -= _m10; m11 -= _m11; m12 -= _m12;
		m20 -= _m20; m21 -= _m21; m22 -= _m22;
		return this;
	}
	public final Matrix3f substract(final Matrix3f _d) {
		m00 -= _d.m00; m01 -= _d.m01; m02 -= _d.m02;
		m10 -= _d.m10; m11 -= _d.m11; m12 -= _d.m12;
		m20 -= _d.m20; m21 -= _d.m21; m22 -= _d.m22;
		return this;
	}

	/** Return the difference of this matrix and another; does not alter either one. */
	public final Matrix3f minus(final float _t) {
		return new Matrix3f(m00 - _t, m01 - _t, m02 - _t,
							m10 - _t, m11 - _t, m12 - _t,
							m20 - _t, m21 - _t, m22 - _t);
	}
	public final Matrix3f minus(final float _m00, final float _m01, final float _m02, final float _m10, final float _m11, final float _m12, final float _m20, final float _m21, final float _m22) {
		return new Matrix3f(m00 - _m00, m01 - _m01, m02 - _m02,
							m10 - _m10, m11 - _m11, m12 - _m12,
							m20 - _m20, m21 - _m21, m22 - _m22);
	}
	public final Matrix3f minus(final Matrix3f _d) {
		return new Matrix3f(m00 - _d.m00, m01 - _d.m01, m02 - _d.m02,
							m10 - _d.m10, m11 - _d.m11, m12 - _d.m12,
							m20 - _d.m20, m21 - _d.m21, m22 - _d.m22);
	}

	public final Matrix3f multiply(final Matrix3f _d) {
		this.set(times(_d));
		return this;
	}
	public final Matrix3f times(final Matrix3f _d) {
		return new Matrix3f(
									m00*_d.m00 + m01*_d.m10 + m02*_d.m20,
									m00*_d.m01 + m01*_d.m11 + m02*_d.m21,
									m00*_d.m02 + m01*_d.m12 + m02*_d.m22,
									m10*_d.m00 + m11*_d.m10 + m12*_d.m20,
									m10*_d.m01 + m11*_d.m11 + m12*_d.m21,
									m10*_d.m02 + m11*_d.m12 + m12*_d.m22,
									m20*_d.m00 + m21*_d.m10 + m22*_d.m20,
									m20*_d.m01 + m21*_d.m11 + m22*_d.m21,
									m20*_d.m02 + m21*_d.m12 + m22*_d.m22
								 );
	}

	public Vector3f times(final Vector3f _v) {
		return new Vector3f(
							m00*_v.x + m01*_v.y + m02*_v.z,
							m10*_v.x + m11*_v.y + m12*_v.z,
							m20*_v.x + m21*_v.y + m22*_v.z
						  );
	}

	
	/**
	 * Solve A * x = b, where b is a column vector. This is more efficient than
	 * computing the inverse in one-shot cases.
	 * 
	 * @param b
	 * @return
	 */
	public final Vector2f solve22(Vector2f b) {
		final float a11 = m00, a12 = m01, a21 = m10, a22 = m11;
		float det = a11 * a22 - a12 * a21;
		if(det != 0.0f)
			det = 1.0f / det;
		
		return new Vector2f( det * (a22 * b.x - a12 * b.y), det * (a11 * b.y - a21 * b.x) );
	}
	public final Vector2f solve22(Vector2f b, Vector2f _out) {
		final float a11 = m00, a12 = m01, a21 = m10, a22 = m11;
		float det = a11 * a22 - a12 * a21;
		if(det != 0.0f)
			det = 1.0f / det;
		
		return _out.set( det * (a22 * b.x - a12 * b.y), det * (a11 * b.y - a21 * b.x) );
	}


	  /**
	 * Solve A * x = b, where b is a column vector. This is more efficient than
	 * computing the inverse in one-shot cases.
	 * 
	 */
	public final Vector3f solve33(Vector3f b) {
		Vector3f out = Vector3f.cross(getColumn1(), getColumn2());
		float det = Vector3f.dot(getColumn0(), out);
		if(det != 0.0f)
			det = 1.0f / det;

		out = Vector3f.cross(getColumn1(), getColumn2());
		final float x = det * Vector3f.dot(b, out);
		out = Vector3f.cross(b, getColumn2());
		final float y = det * Vector3f.dot(getColumn0(), out);
		out = Vector3f.cross(getColumn1(), b);
		final float z = det * Vector3f.dot(getColumn0(), out);
		
		out.x = x;
		out.y = y;
		out.z = z;
		return out;
	}
	public final Vector3f solve33(final Vector3f b, final Vector3f _out) {
		_out.set(Vector3f.cross(getColumn1(), getColumn2()));
		float det = Vector3f.dot(getColumn0(), _out);
		if(det != 0.0f)
			det = 1.0f / det;

		_out.set(Vector3f.cross(getColumn1(), getColumn2()));
		final float x = det * Vector3f.dot(b, _out);
		_out.set(Vector3f.cross(b, getColumn2()));
		final float y = det * Vector3f.dot(getColumn0(), _out);
		_out.set(Vector3f.cross(getColumn1(), b));
		final float z = det * Vector3f.dot(getColumn0(), _out);
		
		_out.x = x;
		_out.y = y;
		_out.z = z;
		return _out;
	}


	public Matrix3f invert2d() {
		float a = m00, b = m01, c = m10, d = m11;
		float det = a * d - b * c;
		if(det != 0.0f)
			det = 1.0f / det;

		return new Matrix3f(det * d, -det * b, 0.0f, -det * c, det * a, 0.0f, 0.0f, 0.0f, 0.0f);
	}
	public Matrix3f invert2d(Matrix3f _out) {
		float a = m00, b = m01, c = m10, d = m11;
		float det = a * d - b * c;
		if(det != 0.0f)
			det = 1.0f / det;

		return _out.set(det * d, -det * b, 0.0f, -det * c, det * a, 0.0f, 0.0f, 0.0f, 0.0f);
	}


	// / Returns the zero matrix if singular.
	public Matrix3f symInvert3d() {
		float bx = m11 * m22 - m12 * m21;
		float by = m12 * m20 - m10 * m22;
		float bz = m10 * m21 - m11 * m20;
		float det = m00 * bx + m10 * by + m20 * bz;
		if(det != 0.0f)
			det = 1.0f / det;

		float a11 = m00, a12 = m01, a13 = m20;
		float a22 = m11, a23 = m21;
		float a33 = m22;

		return new Matrix3f(  	det * (a22 * a33 - a23 * a23),
								det * (a22 * a33 - a23 * a23),
								det * (a22 * a33 - a23 * a23),

								det * (a13 * a23 - a12 * a33),
								det * (a11 * a33 - a13 * a13),
								det * (a13 * a12 - a11 * a23),

								det * (a12 * a23 - a13 * a22),
								det * (a13 * a12 - a11 * a23),
								det * (a11 * a22 - a12 * a12)
							);
	}
	public Matrix3f symInvert3d(Matrix3f _out) {
		float bx = m11 * m22 - m12 * m21;
		float by = m12 * m20 - m10 * m22;
		float bz = m10 * m21 - m11 * m20;
		float det = m00 * bx + m10 * by + m20 * bz;
		if(det != 0.0f)
			det = 1.0f / det;

		float a11 = m00, a12 = m01, a13 = m20;
		float a22 = m11, a23 = m21;
		float a33 = m22;

		return _out.set(  	det * (a22 * a33 - a23 * a23),
								det * (a22 * a33 - a23 * a23),
								det * (a22 * a33 - a23 * a23),

								det * (a13 * a23 - a12 * a33),
								det * (a11 * a33 - a13 * a13),
								det * (a13 * a12 - a11 * a23),

								det * (a12 * a23 - a13 * a22),
								det * (a13 * a12 - a11 * a23),
								det * (a11 * a22 - a12 * a12)
							);
	}

}
