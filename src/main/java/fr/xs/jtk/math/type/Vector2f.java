package fr.xs.jtk.math.type;

import java.io.Serializable;

public class Vector2f implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final float EPSILON = 0;

	public float x, y;


	public static final Vector2f plus(final Vector2f _a, final Vector2f _b) {
		return new Vector2f(_a.x+_b.x, _a.y+_b.y);
	}
	public static final Vector2f plus(final Vector2f _a, final Vector2f _b, final Vector2f _out) {
		return _out.set(_a.x+_b.x, _a.y+_b.y);
	}

	public static final Vector2f minus(final Vector2f _a, final Vector2f _b) {
		return new Vector2f(_a.x-_b.x, _a.y-_b.y);
	}
	public static final Vector2f minus(final Vector2f _a, final Vector2f _b, final Vector2f _out) {
		return _out.set(_a.x-_b.x, _a.y-_b.y);
	}

	public final static Vector2f min(final Vector2f a, final Vector2f b) {
		return new Vector2f(a.x < b.x ? a.x : b.x, a.y < b.y ? a.y : b.y);
	}
	public final static Vector2f min(final Vector2f a, final Vector2f b, final Vector2f _out) {
		return _out.set(a.x < b.x ? a.x : b.x, a.y < b.y ? a.y : b.y);
	}

	public final static Vector2f max(final Vector2f a, final Vector2f b) {
		return new Vector2f(a.x > b.x ? a.x : b.x, a.y > b.y ? a.y : b.y);
	}
	public final static Vector2f max(final Vector2f a, final Vector2f b, final Vector2f _out) {
		return _out.set(a.x > b.x ? a.x : b.x, a.y > b.y ? a.y : b.y);
	}

	public final static Vector2f abs(final Vector2f _v, final Vector2f _out) {
		return _out.set(Math.abs(_v.x), Math.abs(_v.y));
	}

	/** Perform ' dot product 'between a and b vectors */ 
	public final static float dot(final Vector2f a, final Vector2f b) {
		return a.x * b.x + a.y * b.y;
	}
	public final static float cross(final Vector2f a, final Vector2f b) {
		return a.x * b.y - a.y * b.x;
	}

	/** Perform ' cross product 'between a and b vectors */
	public final static Vector2f crossProduct(final Vector2f a, final Vector2f b) {
		return new Vector2f(  a.y*b.x - a.x*b.y, 
							a.x*b.y - a.y*b.x  );
	}
	public final static Vector2f crossProduct(final Vector2f a, final Vector2f b, final Vector2f _out) {
		return _out.set(  a.y*b.x - a.x*b.y, 
						  a.x*b.y - a.y*b.x  );
	}


	public final static Vector2f cross(final Vector2f a, final float s) {
		return new Vector2f(s * a.y, -s * a.x);
	}
	public final static Vector2f cross(final Vector2f a, final float s, final Vector2f _out) {
		return _out.set(s * a.y, -s * a.x);
	}

	public final static Vector2f cross(final float s, final Vector2f a) {
		return new Vector2f(-s * a.y, s * a.x);
	}
	public final static Vector2f cross(final float s, final Vector2f a, final Vector2f _out) {
		return _out.set(-s * a.y, s * a.x);
	}

	public Vector2f() {
	    this(0, 0);
	}
	public Vector2f(final float _x, final float _y) {
		x = _x;
		y = _y;
	}
	public Vector2f(Vector2f _copy) {
	    this(_copy.x, _copy.y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/** Zero out this vector. */
	public final Vector2f setZero() {
		x = 0.0f;
		y = 0.0f;
		return this;
	}
	/** Return a new vector that has positive components. */
	public final Vector2f setAbs() {
		x = Math.abs(x);
		y = Math.abs(y);
		return this;
	}
	public final Vector2f setNegate() {
		x = - x;
		y = - y;
		return this;
	}
	public final Vector2f setSkew() {
		float _x = x;
		x = -y;
		y = _x;
		return this;
	}

	/** Set the vector component-wise. */
	public final Vector2f set(final float x, final float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public final Vector2f set(final Vector2f _d) {
		x = _d.x;
		y = _d.y;
		return this;
	}

	public final float getX() {
		return x;
	}
	public final void setX(float x) {
		this.x = x;
	}

	public final float getY() {
		return y;
	}
	public final void setY(float y) {
		this.y = y;
	}

	public final float[] asFloats() {
		return new float[] { x, y };
	}
	public final int[] asInts() {
		return new int[] { (int) x, (int) y };
	}

	public final Vector2f add(final float _t) {
		x += _t;
		y += _t;
		return this;
	}
	public final Vector2f add(final float _u, final float _v) {
		x += _u;
		y += _v;
		return this;
	}
	public final Vector2f add(final Vector2f _d) {
		x += _d.x;
		y += _d.y;
		return this;
	}

	/** Return the sum of this vector and another; does not alter either one. */
	public final Vector2f plus(final float _t) {
		return new Vector2f(x+_t, y+_t);
	}
	public final Vector2f plus(final float _u, final float _v) {
		return new Vector2f(x+_u, y+_v);
	}
	public final Vector2f plus(final Vector2f _d) {
		return new Vector2f(x+_d.x, y+_d.y);
	}

	public final Vector2f substract(final float _t) {
		x -= _t;
		y -= _t;
		return this;
	}
	public final Vector2f substract(final float _u, final float _v) {
		x -= _u;
		y -= _v;
		return this;
	}
	public final Vector2f substract(final Vector2f _d) {
		x -= _d.x;
		y -= _d.y;
		return this;
	}

	/** Return the difference of this vector and another; does not alter either one. */
	public final Vector2f minus(final float _t) {
		return new Vector2f(x-_t, y-_t);
	}
	public final Vector2f minus(final float _u, final float _v) {
		return new Vector2f(x-_u, y-_v);
	}
	public final Vector2f minus(final Vector2f _d) {
		return new Vector2f(x-_d.x, y-_d.y);
	}

	public final Vector2f multiply(final float _t) {
		x *= _t;
		y *= _t;
		return this;
	}
	public final Vector2f multiply(final float _u, final float _v) {
		x *= _u;
		y *= _v;
		return this;
	}
	public final Vector2f multiply(final Vector2f _d) {
		x *= _d.x;
		y *= _d.y;
		return this;
	}

	/** Return this vector multiplied by a scalar; does not alter this vector. */
	public final Vector2f times(final float _t) {
		return new Vector2f(x*_t, y*_t);
	}
	public final Vector2f times(final float _u, final float _v) {
		return new Vector2f(x*_u, y*_v);
	}
	public final Vector2f times(final Vector2f _d) {
		return new Vector2f(x*_d.x, y*_d.y);
	}

	public final Vector2f divide(final float _t) throws Exception {
		if(_t == 0) throw new Exception();
		x /= _t;
		y /= _t;
		return this;
	}
	public final Vector2f divide(final float _u, final float _v) throws Exception {
		if(_u == 0 || _v == 0) throw new Exception();
		x /= _u;
		y /= _v;
		return this;
	}

	public final Vector2f divideConst(final float _t) throws Exception {
		if(_t == 0) throw new Exception();
		return new Vector2f(x/_t, y/_t);
	}
	public final Vector2f divideConst(final float _u, final float _v) throws Exception {
		if(_u == 0 || _v == 0) throw new Exception();
		return new Vector2f(x/_u, y/_v);
	}

	/** Return the negation of this vector; does not alter this vector. */
	public final Vector2f negate() {
		return new Vector2f(-x, -y);
	}

	/** Get the skew vector such that dot(skew_vec, other) == cross(vec, other) */
	public final Vector2f skew() {
		return new Vector2f(-y, x);
	}

	/** Return a new vector that has positive components. */
	public final Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}
	public final Vector2f abs(final Vector2f _out) {
		return _out.set(Math.abs(x), Math.abs(y));
	}

	/** Return a copy of this vector. */
	public final Vector2f clone() {
		return new Vector2f(x, y);
	}

	/** Return the length of this vector. */
	public final float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }
	/** Return the norm of this vector. */
	public final float norm() {
		return (float) Math.sqrt(x * x + y * y);
	}
	/** Return the squared norm of this vector. */
	public final float norm2() {
		return x * x + y * y;
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
		return length;
	}
	
	public final Vector2f normalized() {
		float length = length();
		if(length < EPSILON) {
			return this;
		}

		float invLength = 1.0f / length;
		return new Vector2f(x * invLength, y * invLength);
	}

	/** True if the vector represents a pair of valid, non-infinite floating point numbers. */
	public final boolean isValid() {
		return !Float.isNaN(x) && !Float.isInfinite(x) && !Float.isNaN(y) && !Float.isInfinite(y);
	}

	public final boolean isEqual(final float _t) {
		return (x == _t && y == _t) ? true : false;
	}
	public final boolean isEqual(final float _u, final float _v) {
		return (x == _u && y == _v) ? true : false;
	}
	public final boolean isEqual(final Vector2f _d) {
		return (x == _d.x && y == _d.y) ? true : false;
	}

	public final boolean isDifferent(final float _t) {
		return (x != _t || y != _t) ? true : false;
	}
	public final boolean isDifferent(final float _u, final float _v) {
		return (x != _u || y != _v) ? true : false;
	}
	public final boolean isDifferent(final Vector2f _d) {
		return (x != _d.x || y != _d.y) ? true : false;
	}

	public final boolean isColinear(final Vector2f _vec) {
		return (x * _vec.y) == (y * _vec.x);
	}
	public final boolean isColinearToSegment(final Vector2f _A, final Vector2f _B) {
		return (x * (_B.y - _A.y)) == (y * (_B.x - _A.x));
	}
	public final boolean isColinearToLine(final Vector2f _P, final Vector2f _N) {
		return (x * _N.y) == (y * _N.x);
	}
	
	public final boolean isOrthogonal(final Vector2f _vec) {
		return (dot(this, _vec) < EPSILON) ? true : false;
	}
	public final boolean isOrthogonalToSegment(final Vector2f _A, final Vector2f _B) {
		return (dot(this, _B.substract(_A)) < EPSILON) ? true : false;
	}
	public final boolean isOrthogonalToLine(final Vector2f _P, final Vector2f _N) {
		return (dot(this, _N) < EPSILON) ? true : false;
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
		Vector2f other = (Vector2f) obj;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if(Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "(" + x + "," + y + ")";
	}

}
