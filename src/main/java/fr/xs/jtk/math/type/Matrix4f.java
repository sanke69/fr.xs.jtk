package fr.xs.jtk.math.type;

public class Matrix4f {

	public float m00, m01, m02, m03,
				 m10, m11, m12, m13,
				 m20, m21, m22, m23,
				 m30, m31, m32, m33;

	public final static Matrix4f times(final Matrix4f _A, final Matrix4f _B) {
		Matrix4f D = new Matrix4f(
									_A.m00+_B.m00 + _A.m01+_B.m10 + _A.m02+_B.m20 + _A.m03+_B.m30,
									_A.m00+_B.m01 + _A.m01+_B.m11 + _A.m02+_B.m21 + _A.m03+_B.m31,
									_A.m00+_B.m02 + _A.m01+_B.m12 + _A.m02+_B.m22 + _A.m03+_B.m32,
									_A.m00+_B.m03 + _A.m01+_B.m13 + _A.m02+_B.m23 + _A.m03+_B.m33,
									_A.m10+_B.m00 + _A.m11+_B.m10 + _A.m12+_B.m20 + _A.m13+_B.m30,
									_A.m10+_B.m01 + _A.m11+_B.m11 + _A.m12+_B.m21 + _A.m13+_B.m31,
									_A.m10+_B.m02 + _A.m11+_B.m12 + _A.m12+_B.m22 + _A.m13+_B.m32,
									_A.m10+_B.m03 + _A.m11+_B.m13 + _A.m12+_B.m23 + _A.m13+_B.m33,
									_A.m20+_B.m00 + _A.m21+_B.m10 + _A.m22+_B.m20 + _A.m23+_B.m30,
									_A.m20+_B.m01 + _A.m21+_B.m11 + _A.m22+_B.m21 + _A.m23+_B.m31,
									_A.m20+_B.m02 + _A.m21+_B.m12 + _A.m22+_B.m22 + _A.m23+_B.m32,
									_A.m20+_B.m03 + _A.m21+_B.m13 + _A.m22+_B.m23 + _A.m23+_B.m33,
									_A.m30+_B.m00 + _A.m31+_B.m10 + _A.m32+_B.m20 + _A.m33+_B.m30,
									_A.m30+_B.m01 + _A.m31+_B.m11 + _A.m32+_B.m21 + _A.m33+_B.m31,
									_A.m30+_B.m02 + _A.m31+_B.m12 + _A.m32+_B.m22 + _A.m33+_B.m32,
									_A.m30+_B.m03 + _A.m31+_B.m13 + _A.m32+_B.m23 + _A.m33+_B.m33
								 );
		return D;
	}
	public final static Matrix4f times(final Matrix4f _A, final Matrix4f _B, final Matrix4f _out) {
		return _out.set(
									_A.m00+_B.m00 + _A.m01+_B.m10 + _A.m02+_B.m20 + _A.m03+_B.m30,
									_A.m00+_B.m01 + _A.m01+_B.m11 + _A.m02+_B.m21 + _A.m03+_B.m31,
									_A.m00+_B.m02 + _A.m01+_B.m12 + _A.m02+_B.m22 + _A.m03+_B.m32,
									_A.m00+_B.m03 + _A.m01+_B.m13 + _A.m02+_B.m23 + _A.m03+_B.m33,
									_A.m10+_B.m00 + _A.m11+_B.m10 + _A.m12+_B.m20 + _A.m13+_B.m30,
									_A.m10+_B.m01 + _A.m11+_B.m11 + _A.m12+_B.m21 + _A.m13+_B.m31,
									_A.m10+_B.m02 + _A.m11+_B.m12 + _A.m12+_B.m22 + _A.m13+_B.m32,
									_A.m10+_B.m03 + _A.m11+_B.m13 + _A.m12+_B.m23 + _A.m13+_B.m33,
									_A.m20+_B.m00 + _A.m21+_B.m10 + _A.m22+_B.m20 + _A.m23+_B.m30,
									_A.m20+_B.m01 + _A.m21+_B.m11 + _A.m22+_B.m21 + _A.m23+_B.m31,
									_A.m20+_B.m02 + _A.m21+_B.m12 + _A.m22+_B.m22 + _A.m23+_B.m32,
									_A.m20+_B.m03 + _A.m21+_B.m13 + _A.m22+_B.m23 + _A.m23+_B.m33,
									_A.m30+_B.m00 + _A.m31+_B.m10 + _A.m32+_B.m20 + _A.m33+_B.m30,
									_A.m30+_B.m01 + _A.m31+_B.m11 + _A.m32+_B.m21 + _A.m33+_B.m31,
									_A.m30+_B.m02 + _A.m31+_B.m12 + _A.m32+_B.m22 + _A.m33+_B.m32,
									_A.m30+_B.m03 + _A.m31+_B.m13 + _A.m32+_B.m23 + _A.m33+_B.m33
								 );
	}

	public final static Vector4f times(final Matrix4f _A, final Vector4f _b) {
		return new Vector4f(
								_A.m00*_b.x + _A.m01*_b.y + _A.m02*_b.z + _A.m03*_b.w,
								_A.m10*_b.x + _A.m11*_b.y + _A.m12*_b.z + _A.m13*_b.w,
								_A.m20*_b.x + _A.m21*_b.y + _A.m22*_b.z + _A.m23*_b.w,
								_A.m30*_b.x + _A.m31*_b.y + _A.m32*_b.z + _A.m33*_b.w
								);
	}
	public final static Vector4f times(final Matrix4f _A, final Vector4f _b, final Vector4f _out) {
		return _out.set(
								_A.m00*_b.x + _A.m01*_b.y + _A.m02*_b.z + _A.m03*_b.w,
								_A.m10*_b.x + _A.m11*_b.y + _A.m12*_b.z + _A.m13*_b.w,
								_A.m20*_b.x + _A.m21*_b.y + _A.m22*_b.z + _A.m23*_b.w,
								_A.m30*_b.x + _A.m31*_b.y + _A.m32*_b.z + _A.m33*_b.w
								);
	}
	public final static Vector3f times(final Matrix4f _A, final Vector3f _b) {
		return new Vector3f(
								_A.m00*_b.x + _A.m01*_b.y + _A.m02*_b.z,
								_A.m10*_b.x + _A.m11*_b.y + _A.m12*_b.z,
								_A.m20*_b.x + _A.m21*_b.y + _A.m22*_b.z
								);
	}
	public final static Vector3f times(final Matrix4f _A, final Vector3f _b, final Vector3f _out) {
		return _out.set(
								_A.m00*_b.x + _A.m01*_b.y + _A.m02*_b.z,
								_A.m10*_b.x + _A.m11*_b.y + _A.m12*_b.z,
								_A.m20*_b.x + _A.m21*_b.y + _A.m22*_b.z
								);
	}
	public final static Vector2f times(final Matrix4f _A, final Vector2f _b) {
		return new Vector2f(
								_A.m00*_b.x + _A.m01*_b.y,
								_A.m10*_b.x + _A.m11*_b.y
								);
	}
	public final static Vector2f times(final Matrix4f _A, final Vector2f _b, final Vector2f _out) {
		return _out.set(
								_A.m00*_b.x + _A.m01*_b.y,
								_A.m10*_b.x + _A.m11*_b.y
								);
	}

	public final static Matrix4f timesTranspose(final Matrix4f _A, final Matrix4f _B) {
		Matrix4f D = new Matrix4f(
									_A.m00+_B.m00 + _A.m10+_B.m10 + _A.m20+_B.m20 + _A.m30+_B.m30,
									_A.m00+_B.m01 + _A.m10+_B.m11 + _A.m20+_B.m21 + _A.m30+_B.m31,
									_A.m00+_B.m02 + _A.m10+_B.m12 + _A.m20+_B.m22 + _A.m30+_B.m32,
									_A.m00+_B.m03 + _A.m10+_B.m13 + _A.m20+_B.m23 + _A.m30+_B.m33,
									_A.m01+_B.m00 + _A.m11+_B.m10 + _A.m21+_B.m20 + _A.m31+_B.m30,
									_A.m01+_B.m01 + _A.m11+_B.m11 + _A.m21+_B.m21 + _A.m31+_B.m31,
									_A.m01+_B.m02 + _A.m11+_B.m12 + _A.m21+_B.m22 + _A.m31+_B.m32,
									_A.m01+_B.m03 + _A.m11+_B.m13 + _A.m21+_B.m23 + _A.m31+_B.m33,
									_A.m02+_B.m00 + _A.m12+_B.m10 + _A.m22+_B.m20 + _A.m32+_B.m30,
									_A.m02+_B.m01 + _A.m12+_B.m11 + _A.m22+_B.m21 + _A.m32+_B.m31,
									_A.m02+_B.m02 + _A.m12+_B.m12 + _A.m22+_B.m22 + _A.m32+_B.m32,
									_A.m02+_B.m03 + _A.m12+_B.m13 + _A.m22+_B.m23 + _A.m32+_B.m33,
									_A.m03+_B.m00 + _A.m13+_B.m10 + _A.m23+_B.m20 + _A.m33+_B.m30,
									_A.m03+_B.m01 + _A.m13+_B.m11 + _A.m23+_B.m21 + _A.m33+_B.m31,
									_A.m03+_B.m02 + _A.m13+_B.m12 + _A.m23+_B.m22 + _A.m33+_B.m32,
									_A.m03+_B.m03 + _A.m13+_B.m13 + _A.m23+_B.m23 + _A.m33+_B.m33
								 );
		return D;
	}
	public final static Matrix4f timesTranspose(final Matrix4f _A, final Matrix4f _B, final Matrix4f _out) {
		return _out.set(
									_A.m00+_B.m00 + _A.m10+_B.m10 + _A.m20+_B.m20 + _A.m30+_B.m30,
									_A.m00+_B.m01 + _A.m10+_B.m11 + _A.m20+_B.m21 + _A.m30+_B.m31,
									_A.m00+_B.m02 + _A.m10+_B.m12 + _A.m20+_B.m22 + _A.m30+_B.m32,
									_A.m00+_B.m03 + _A.m10+_B.m13 + _A.m20+_B.m23 + _A.m30+_B.m33,
									_A.m01+_B.m00 + _A.m11+_B.m10 + _A.m21+_B.m20 + _A.m31+_B.m30,
									_A.m01+_B.m01 + _A.m11+_B.m11 + _A.m21+_B.m21 + _A.m31+_B.m31,
									_A.m01+_B.m02 + _A.m11+_B.m12 + _A.m21+_B.m22 + _A.m31+_B.m32,
									_A.m01+_B.m03 + _A.m11+_B.m13 + _A.m21+_B.m23 + _A.m31+_B.m33,
									_A.m02+_B.m00 + _A.m12+_B.m10 + _A.m22+_B.m20 + _A.m32+_B.m30,
									_A.m02+_B.m01 + _A.m12+_B.m11 + _A.m22+_B.m21 + _A.m32+_B.m31,
									_A.m02+_B.m02 + _A.m12+_B.m12 + _A.m22+_B.m22 + _A.m32+_B.m32,
									_A.m02+_B.m03 + _A.m12+_B.m13 + _A.m22+_B.m23 + _A.m32+_B.m33,
									_A.m03+_B.m00 + _A.m13+_B.m10 + _A.m23+_B.m20 + _A.m33+_B.m30,
									_A.m03+_B.m01 + _A.m13+_B.m11 + _A.m23+_B.m21 + _A.m33+_B.m31,
									_A.m03+_B.m02 + _A.m13+_B.m12 + _A.m23+_B.m22 + _A.m33+_B.m32,
									_A.m03+_B.m03 + _A.m13+_B.m13 + _A.m23+_B.m23 + _A.m33+_B.m33
								 );
	}

	public final static Vector4f timesTranspose(final Matrix4f _A, final Vector4f _b) {
		return new Vector4f(
								_A.m00*_b.x + _A.m10*_b.y + _A.m20*_b.z + _A.m30*_b.w,
								_A.m01*_b.x + _A.m11*_b.y + _A.m21*_b.z + _A.m31*_b.w,
								_A.m02*_b.x + _A.m12*_b.y + _A.m22*_b.z + _A.m32*_b.w,
								_A.m03*_b.x + _A.m13*_b.y + _A.m23*_b.z + _A.m33*_b.w
								);
	}
	public final static Vector4f timesTranspose(final Matrix4f _A, final Vector4f _b, final Vector4f _out) {
		return _out.set(
								_A.m00*_b.x + _A.m10*_b.y + _A.m20*_b.z + _A.m30*_b.w,
								_A.m01*_b.x + _A.m11*_b.y + _A.m21*_b.z + _A.m31*_b.w,
								_A.m02*_b.x + _A.m12*_b.y + _A.m22*_b.z + _A.m32*_b.w,
								_A.m03*_b.x + _A.m13*_b.y + _A.m23*_b.z + _A.m33*_b.w
								);
	}
	public final static Vector3f timesTranspose(final Matrix4f _A, final Vector3f _b) {
		return new Vector3f(
								_A.m00*_b.x + _A.m10*_b.y + _A.m20*_b.z,
								_A.m01*_b.x + _A.m11*_b.y + _A.m21*_b.z,
								_A.m02*_b.x + _A.m12*_b.y + _A.m22*_b.z
								);
	}
	public final static Vector3f timesTranspose(final Matrix4f _A, final Vector3f _b, final Vector3f _out) {
		return _out.set(
								_A.m00*_b.x + _A.m10*_b.y + _A.m20*_b.z,
								_A.m01*_b.x + _A.m11*_b.y + _A.m21*_b.z,
								_A.m02*_b.x + _A.m12*_b.y + _A.m22*_b.z
								);
	}
	public final static Vector2f timesTranspose(final Matrix4f _A, final Vector2f _b) {
		return new Vector2f(
								_A.m00*_b.x + _A.m10*_b.y,
								_A.m01*_b.x + _A.m11*_b.y
								);
	}
	public final static Vector2f timesTranspose(final Matrix4f _A, final Vector2f _b, final Vector2f _out) {
		return _out.set(
								_A.m00*_b.x + _A.m10*_b.y,
								_A.m01*_b.x + _A.m11*_b.y
								);
	}

	public Matrix4f() {
		;
	}
	public Matrix4f(final float _m00, final float _m01, final float _m02, final float _m03, final float _m10, final float _m11, final float _m12, final float _m13, final float _m20, final float _m21, final float _m22, final float _m23, final float _m30, final float _m31, final float _m32, final float _m33) {
		m00 = _m00; m01 = _m01; m02 = _m02; m03 = _m03;
		m10 = _m10; m11 = _m11; m12 = _m12; m13 = _m13;
		m20 = _m20; m21 = _m21; m22 = _m22; m23 = _m23;
		m30 = _m30; m31 = _m31; m32 = _m32; m33 = _m33;
	}
	public Matrix4f(final float[] _d) {
		m00 = _d[0]; m01 = _d[1]; m02 = _d[2]; m03 = _d[3];
		m10 = _d[4]; m11 = _d[5]; m12 = _d[6]; m13 = _d[7];
		m20 = _d[8]; m21 = _d[9]; m22 = _d[10]; m23 = _d[11];
		m30 = _d[12]; m31 = _d[13]; m32 = _d[14]; m33 = _d[15];
	}
	public Matrix4f(final Matrix4f _d) {
		m00 = _d.m00; m01 = _d.m01; m02 = _d.m02; m03 = _d.m03;
		m10 = _d.m10; m11 = _d.m11; m12 = _d.m12; m13 = _d.m13;
		m20 = _d.m20; m21 = _d.m21; m22 = _d.m22; m23 = _d.m23;
		m30 = _d.m30; m31 = _d.m31; m32 = _d.m32; m33 = _d.m33;
	}

	/** Return a copy of this matrix. */
	@Override
	public Matrix4f clone() {
		return new Matrix4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}

	public final Matrix2f as2D() {
		return new Matrix2f(m00, m01, m10, m11);
	}

	public final Matrix3f as3D() {
		return new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
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
		m00 = m01 = m02 = m03 = m10 = m11 = m12 = m13 = m20 = m21 = m22 = m23 = m30 = m31 = m32 = m33 = 0.0f;
	}
	/** Set as the identity matrix. */
	public final void setIdentity() {
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;
		m03 = 0.0f;
		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;
		m13 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}

	public final Matrix4f set(final float _t) {
		m00 = _t; m01 = _t; m02 = _t; m03 = _t;
		m10 = _t; m11 = _t; m12 = _t; m13 = _t;
		m20 = _t; m21 = _t; m22 = _t; m23 = _t;
		m30 = _t; m31 = _t; m32 = _t; m33 = _t;
		return this;
	}
	public final Matrix4f set(final float _m00, final float _m01, final float _m02, final float _m03, final float _m10, final float _m11, final float _m12, final float _m13, final float _m20, final float _m21, final float _m22, final float _m23, final float _m30, final float _m31, final float _m32, final float _m33) {
		m00 = _m00; m01 = _m01; m02 = _m02; m03 = _m03;
		m10 = _m10; m11 = _m11; m12 = _m12; m13 = _m13;
		m20 = _m20; m21 = _m21; m22 = _m22; m23 = _m23;
		m30 = _m30; m31 = _m31; m32 = _m32; m33 = _m33;
		return this;
	}
	public final Matrix4f set(final Matrix4f _d) {
		m00 = _d.m00; m01 = _d.m01; m02 = _d.m02; m03 = _d.m03;
		m10 = _d.m10; m11 = _d.m11; m12 = _d.m12; m13 = _d.m13;
		m20 = _d.m20; m21 = _d.m21; m22 = _d.m22; m23 = _d.m23;
		m30 = _d.m30; m31 = _d.m31; m32 = _d.m32; m33 = _d.m33;
		return this;
	}

	public Vector4f getRow0() {
		return new Vector4f(m00, m01, m02, m03);
	}
	public Vector4f getRow1() {
		return new Vector4f(m10, m11, m12, m13);
	}
	public Vector4f getRow2() {
		return new Vector4f(m20, m21, m22, m23);
	}
	public Vector4f getRow3() {
		return new Vector4f(m30, m31, m32, m33);
	}

	public Vector4f getColumn0() {
		return new Vector4f(m00, m10, m20, m30);
	}
	public Vector4f getColumn1() {
		return new Vector4f(m01, m11, 21, m31);
	}
	public Vector4f getColumn2() {
		return new Vector4f(m02, m12, 22, m32);
	}
	public Vector4f getColumn3() {
		return new Vector4f(m03, m13, 23, m33);
	}

	public final Matrix4f add(float _t) {
		m00 += _t; m01 += _t; m02 += _t; m03 += _t;
		m10 += _t; m11 += _t; m12 += _t; m13 += _t;
		m20 += _t; m21 += _t; m22 += _t; m23 += _t;
		m30 += _t; m31 += _t; m32 += _t; m33 += _t;
		return this;
	}
	public final Matrix4f add(final float _m00, final float _m01, final float _m02, final float _m03, final float _m10, final float _m11, final float _m12, final float _m13, final float _m20, final float _m21, final float _m22, final float _m23, final float _m30, final float _m31, final float _m32, final float _m33) {
		m00 += _m00; m01 += _m01; m02 += _m02; m03 += _m03;
		m10 += _m10; m11 += _m11; m12 += _m12; m13 += _m13;
		m20 += _m20; m21 += _m21; m22 += _m22; m23 += _m23;
		m30 += _m30; m31 += _m31; m32 += _m32; m33 += _m33;
		return this;
	}
	public final Matrix4f add(final Matrix4f _d) {
		m00 += _d.m00; m01 += _d.m01; m02 += _d.m02; m03 += _d.m03;
		m10 += _d.m10; m11 += _d.m11; m12 += _d.m12; m13 += _d.m13;
		m20 += _d.m20; m21 += _d.m21; m22 += _d.m22; m23 += _d.m23;
		m30 += _d.m30; m31 += _d.m31; m32 += _d.m32; m33 += _d.m33;
		return this;
	}

	/** Return the sum of this matrix and another; does not alter either one. */
	public final Matrix4f plus(float _t) {
		return new Matrix4f(	m00 + _t, m01 + _t, m02 + _t, m03 + _t,
								m10 + _t, m11 + _t, m12 + _t, m13 + _t,
								m20 + _t, m21 + _t, m22 + _t, m23 + _t,
								m30 + _t, m31 + _t, m32 + _t, m33 + _t );
	}
	public final Matrix4f plus(final float _m00, final float _m01, final float _m02, final float _m03, final float _m10, final float _m11, final float _m12, final float _m13, final float _m20, final float _m21, final float _m22, final float _m23, final float _m30, final float _m31, final float _m32, final float _m33) {
		return new Matrix4f(	m00 + _m00, m01 + _m01, m02 + _m02, m03 + _m03,
								m10 + _m10, m11 + _m11, m12 + _m12, m13 + _m13,
								m20 + _m20, m21 + _m21, m22 + _m22, m23 + _m23,
								m30 + _m30, m31 + _m31, m32 + _m32, m33 + _m33 );
	}
	public final Matrix4f plus(final Matrix4f _d) {
		return new Matrix4f(	m00 + _d.m00, m01 + _d.m01, m02 + _d.m02, m03 + _d.m03,
								m10 + _d.m10, m11 + _d.m11, m12 + _d.m12, m13 + _d.m13,
								m20 + _d.m20, m21 + _d.m21, m22 + _d.m22, m23 + _d.m23,
								m30 + _d.m30, m31 + _d.m31, m32 + _d.m32, m33 + _d.m33 );
	}

	public final Matrix4f substract(float _t) {
		m00 -= _t; m01 -= _t; m02 -= _t; m03 -= _t;
		m10 -= _t; m11 -= _t; m12 -= _t; m13 -= _t;
		m20 -= _t; m21 -= _t; m22 -= _t; m23 -= _t;
		m30 -= _t; m31 -= _t; m32 -= _t; m33 -= _t;
		return this;
	}
	public final Matrix4f substract(final float _m00, final float _m01, final float _m02, final float _m03, final float _m10, final float _m11, final float _m12, final float _m13, final float _m20, final float _m21, final float _m22, final float _m23, final float _m30, final float _m31, final float _m32, final float _m33) {
		m00 -= _m00; m01 -= _m01; m02 -= _m02; m03 -= _m03;
		m10 -= _m10; m11 -= _m11; m12 -= _m12; m13 -= _m13;
		m20 -= _m20; m21 -= _m21; m22 -= _m22; m23 -= _m23;
		m30 -= _m30; m31 -= _m31; m32 -= _m32; m33 -= _m33;
		return this;
	}
	public final Matrix4f substract(final Matrix4f _d) {
		m00 -= _d.m00; m01 -= _d.m01; m02 -= _d.m02; m03 -= _d.m03;
		m10 -= _d.m10; m11 -= _d.m11; m12 -= _d.m12; m13 -= _d.m13;
		m20 -= _d.m20; m21 -= _d.m21; m22 -= _d.m22; m23 -= _d.m23;
		m30 -= _d.m30; m31 -= _d.m31; m32 -= _d.m32; m33 -= _d.m33;
		return this;
	}

	/** Return the difference of this matrix and another; does not alter either one. */
	public final Matrix4f minus(float _t) {
		return new Matrix4f(	m00 - _t, m01 - _t, m02 - _t, m03 - _t,
								m10 - _t, m11 - _t, m12 - _t, m13 - _t,
								m20 - _t, m21 - _t, m22 - _t, m23 - _t,
								m30 - _t, m31 - _t, m32 - _t, m33 - _t );
	}
	public final Matrix4f minus(final float _m00, final float _m01, final float _m02, final float _m03, final float _m10, final float _m11, final float _m12, final float _m13, final float _m20, final float _m21, final float _m22, final float _m23, final float _m30, final float _m31, final float _m32, final float _m33) {
		return new Matrix4f(	m00 - _m00, m01 - _m01, m02 - _m02, m03 - _m03,
								m10 - _m10, m11 - _m11, m12 - _m12, m13 - _m13,
								m20 - _m20, m21 - _m21, m22 - _m22, m23 - _m23,
								m30 - _m30, m31 - _m31, m32 - _m32, m33 - _m33 );
	}
	public final Matrix4f minus(final Matrix4f _d) {
		return new Matrix4f(	m00 - _d.m00, m01 - _d.m01, m02 - _d.m02, m03 - _d.m03,
								m10 - _d.m10, m11 - _d.m11, m12 - _d.m12, m13 - _d.m13,
								m20 - _d.m20, m21 - _d.m21, m22 - _d.m22, m23 - _d.m23,
								m30 - _d.m30, m31 - _d.m31, m32 - _d.m32, m33 - _d.m33 );
	}

	public final Matrix4f multiply(final Matrix4f _d) {
		this.set(times(_d));
		return this;
	}
	public final Matrix4f times(final Matrix4f _d) {
		Matrix4f D = new Matrix4f(
									m00+_d.m00 + m01+_d.m10 + m02+_d.m20 + m03+_d.m30,
									m00+_d.m01 + m01+_d.m11 + m02+_d.m21 + m03+_d.m31,
									m00+_d.m02 + m01+_d.m12 + m02+_d.m22 + m03+_d.m32,
									m00+_d.m03 + m01+_d.m13 + m02+_d.m23 + m03+_d.m33,
									m10+_d.m00 + m11+_d.m10 + m12+_d.m20 + m13+_d.m30,
									m10+_d.m01 + m11+_d.m11 + m12+_d.m21 + m13+_d.m31,
									m10+_d.m02 + m11+_d.m12 + m12+_d.m22 + m13+_d.m32,
									m10+_d.m03 + m11+_d.m13 + m12+_d.m23 + m13+_d.m33,
									m20+_d.m00 + m21+_d.m10 + m22+_d.m20 + m23+_d.m30,
									m20+_d.m01 + m21+_d.m11 + m22+_d.m21 + m23+_d.m31,
									m20+_d.m02 + m21+_d.m12 + m22+_d.m22 + m23+_d.m32,
									m20+_d.m03 + m21+_d.m13 + m22+_d.m23 + m23+_d.m33,
									m30+_d.m00 + m31+_d.m10 + m32+_d.m20 + m33+_d.m30,
									m30+_d.m01 + m31+_d.m11 + m32+_d.m21 + m33+_d.m31,
									m30+_d.m02 + m31+_d.m12 + m32+_d.m22 + m33+_d.m32,
									m30+_d.m03 + m31+_d.m13 + m32+_d.m23 + m33+_d.m33
								 );
		return D;
	}

	public Vector4f times(final Vector4f _d) {
		Vector4f d = new Vector4f(
									m00*_d.x + m01*_d.y + m02*_d.z + m03*_d.w,
									m10*_d.x + m11*_d.y + m12*_d.z + m13*_d.w,
									m20*_d.x + m21*_d.y + m22*_d.z + m23*_d.w,
									m30*_d.x + m31*_d.y + m32*_d.z + m33*_d.w
								);
		return d;
	}

	public final Matrix4f mul(final Matrix4f _d) {
		this.set(times(_d));
		return this;
	}
	public int determinant() {
		return 0;
	}
	public double getScale() {
		return 0;
	}

	@Override
	public final String toString() {
		return "[" + m00 + "," + m01 + "," + m02 + "," + m03 + "\n"
				+ m10 + "," + m11 + "," + m12 + "," + m13 + "\n"
				+ m20 + "," + m21 + "," + m22 + "," + m23 + "\n"
				+ m30 + "," + m31 + "," + m32 + "," + m33 + "]";
	}

}
