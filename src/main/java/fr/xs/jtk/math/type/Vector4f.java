package fr.xs.jtk.math.type;

import java.io.Serializable;

public class Vector4f implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final float EPSILON = 0;

	public float x, y, z, w;

	public final static Vector4f min(Vector4f a, Vector4f b) {
		return new Vector4f(a.x < b.x ? a.x : b.x, a.y < b.y ? a.y : b.y, a.z < b.z ? a.z : b.z, a.w < b.w ? a.w : b.w);
	}

	public final static Vector4f max(Vector4f a, Vector4f b) {
		return new Vector4f(a.x > b.x ? a.x : b.x, a.y > b.y ? a.y : b.y, a.z > b.z ? a.z : b.z, a.w > b.w ? a.w : b.w);
	}

	/** Perform ' dot product 'between a and b vectors */ 
	public final static float dot(final Vector4f a, final Vector4f b) {
		return -1;//a.x * b.x + a.y * b.y;
	}

	/** Perform ' cross product 'between a and b vectors */
	public static Vector4f cross(final Vector4f a, final Vector4f b) {
		return new Vector4f(  a.y*b.x - a.x*b.y, 
							a.x*b.y - a.y*b.x, 0, 0  );
	}

	public Vector4f() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}
	public Vector4f(final float _x, final float _y, final float _z, final float _w) {
		x = _x;
		y = _y;
		z = _z;
		w = _w;
	}
	public Vector4f(final Vector4f _d) {
		x = _d.x;
		y = _d.y;
		z = _d.z;
		w = _d.w;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + Double.doubleToLongBits(x);
		result = prime * result + Double.doubleToLongBits(y);
		result = prime * result + Double.doubleToLongBits(z);
		result = prime * result + Double.doubleToLongBits(w);
		return (int) result;
	}

	public final Vector3f as3D() {
		return new Vector3f(x, y, z);
	}
	public final Vector3f as3D(Vector3f _out) {
		return _out.set(x, y, z);
	}
	
	/** Zero out this vector. */
	public final void setZero() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		w = 0.0f;
	}
	/** Set the vector component-wise. */
	public final Vector4f set(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	public final Vector4f set(final Vector4f _d) {
		x = _d.x;
		y = _d.y;
		z = _d.z;
		w = _d.w;
		return this;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}

	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}

	public float[] asFloats() {
		return new float[] { x, y, z, w };
	}
	public int[] asInts() {
		return new int[] { (int) x, (int) y, (int) z, (int) w };
	}

	public final Vector4f add(final float _t) {
		x += _t;
		y += _t;
		z += _t;
		return this;
	}
	public final Vector4f add(final float _t, final float _u, final float _v, final float _w) {
		x += _t;
		y += _u;
		z += _v;
		w += _w;
		return this;
	}
	public final Vector4f add(final Vector4f _d) {
		x += _d.x;
		y += _d.y;
		z += _d.z;
		w += _d.w;
		return this;
	}

	/** Return the sum of this vector and another; does not alter either one. */
	public final Vector4f plus(final float _t) {
		return new Vector4f(x+_t, y+_t, z+_t, w+_t);
	}
	public final Vector4f plus(final float _t, final float _u, final float _v, final float _w) {
		return new Vector4f(x+_t, y+_u, z+_v, w+_w);
	}
	public final Vector4f plus(final Vector4f _d) {
		return new Vector4f(x+_d.x, y+_d.y, z+_d.z, w+_d.w);
	}

	public final Vector4f substract(final float _t) {
		x -= _t;
		y -= _t;
		z -= _t;
		w -= _t;
		return this;
	}
	public final Vector4f substract(final float _t, final float _u, final float _v, final float _w) {
		x -= _t;
		y -= _u;
		z -= _v;
		w -= _w;
		return this;
	}
	public final Vector4f substract(final Vector4f _d) {
		x -= _d.x;
		y -= _d.y;
		z -= _d.z;
		w -= _d.w;
		return this;
	}

	/** Return the difference of this vector and another; does not alter either one. */
	public final Vector4f minus(final float _t) {
		return new Vector4f(x-_t, y-_t, z-_t, w-_t);
	}
	public final Vector4f minus(final float _t, final float _u, final float _v, final float _w) {
		return new Vector4f(x-_t, y-_u, z-_v, w-_w);
	}
	public final Vector4f minus(final Vector4f _d) {
		return new Vector4f(x-_d.x, y-_d.y, z-_d.z, w-_d.w);
	}

	public final Vector4f multiply(final float _t) {
		x *= _t;
		y *= _t;
		z *= _t;
		w *= _t;
		return this;
	}
	public final Vector4f multiply(final float _t, final float _u, final float _v, final float _w) {
		x *= _t;
		y *= _u;
		z *= _v;
		w *= _w;
		return this;
	}
	public final Vector4f multiply(final Vector4f _d) {
		x *= _d.x;
		y *= _d.y;
		z *= _d.z;
		w *= _d.w;
		return this;
	}

	/** Return this vector multiplied by a scalar; does not alter this vector. */
	public final Vector4f times(final float _t) {
		return new Vector4f(x*_t, y*_t, z*_t, w*_t);
	}
	public final Vector4f times(final float _t, final float _u, final float _v, final float _w) {
		return new Vector4f(x*_t, y*_u, z*_v, w*_w);
	}
	public final Vector4f times(final Vector4f _d) {
		return new Vector4f(x*_d.x, y*_d.y, z*_d.z, w*_d.w);
	}
	
	public final Vector4f divide(final float _t) /*throws Exception*/ {
		if(_t == 0) return null; //throw new Exception();
		x /= _t;
		y /= _t;
		z /= _t;
		w /= _t;
		
		return this;
	}
	public final Vector4f divide(final float _t, final float _u, final float _v, final float _w) /*throws Exception*/ {
		if(_t == 0 || _u == 0 || _v == 0 || _w == 0) return null; //throw new Exception();
		x /= _t;
		y /= _u;
		z /= _v;
		w /= _w;
		return this;
	}

	public final Vector4f divideConst(final float _t) /*throws Exception*/ {
		if(_t == 0) return null; //throw new Exception();
		return new Vector4f(x/_t, y/_t, z/_t, w/_t);
	}
	public final Vector4f divideConst(final float _t, final float _u, final float _v, final float _w) /*throws Exception*/ {
		if(_u == 0 || _v == 0 || _w == 0) return null; //throw new Exception();
		return new Vector4f(x/_t, y/_u, z/_v, w/_w);
	}

	/** Return the negation of this vector; does not alter this vector. */
	public final Vector4f negate() {
		return new Vector4f(-x, -y, -z, -w);
	}

	/** Return a new vector that has positive components. */
	public final Vector4f abs() {
		return new Vector4f(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
	}

	/** Return a copy of this vector. */
	public final Vector4f clone() {
		return new Vector4f(x, y, z, w);
	}

	/** Return the length of this vector. */
	public final float length() {
		return (float) Math.sqrt(x*x + y*y + z*z + w*w);
	}
	/** Return the norm of this vector. */
	public final float norm() {
		return (float) Math.sqrt(x*x + y*y + z*z + w*w);
	}
	/** Return the squared norm of this vector. */
	public final float norm2() {
		return x*x + y*y + z*z + w*w;
	}

	/** Normalize this vector and return the length before normalization. Alters this vector. */
	public final float normalize() {
		float length = length();
		if(length < EPSILON) {
			return 0f;
		}

		float invLength = 1.0f / length;
		x *= invLength;
		y *= invLength;
		z *= invLength;
		w *= invLength;
		return length;
	}

	public final Vector4f normalized() {
		float length = length();
		if(length < EPSILON) {
			return this;
		}

		float invLength = 1.0f / length;
		return new Vector4f(x * invLength, y * invLength, z * invLength, w * invLength);
	}

	/** True if the vector represents a pair of valid, non-infinite floating point numbers. */
	public final boolean isValid() {
		return !Float.isNaN(x) && !Float.isInfinite(x) && !Float.isNaN(y) && !Float.isInfinite(y) && !Float.isNaN(z) && !Float.isInfinite(z) && !Float.isNaN(w) && !Float.isInfinite(w);
	}

	public boolean isEqual(final float _t) {
		return (x == _t && y == _t && z == _t && w == _t) ? true : false;
	}
	public boolean isEqual(final float _u, final float _v, final float _w, final float _z) {
		return (x == _u && y == _v && z == _w && w == _z) ? true : false;
	}
	public boolean isEqual(final Vector4f _d) {
		return (x == _d.x && y == _d.y && z == _d.z && w == _d.w) ? true : false;
	}

	public boolean isDifferent(final float _t) {
		return (x != _t || y != _t || z != _t) ? true : false;
	}
	public boolean isDifferent(final float _u, final float _v, final float _w, final float _z) {
		return (x != _u || y != _v || z != _w || w != _z) ? true : false;
	}
	public boolean isDifferent(final Vector4f _d) {
		return (x != _d.x || y != _d.y || z != _d.z || w != _d.w) ? true : false;
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
		Vector4f other = (Vector4f) obj;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if(Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if(Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		if(Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}

}
