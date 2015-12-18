package fr.xs.jtk.math.type;

import java.io.Serializable;

public class Vector3f implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final float EPSILON = (float) 1e-12;

	public float x, y, z;

	public final static Vector3f min(Vector3f _a, Vector3f _b) {
		return new Vector3f(_a.x < _b.x ? _a.x : _b.x, _a.y < _b.y ? _a.y : _b.y, _a.z < _b.z ? _a.z : _b.z);
	}

	public final static Vector3f max(Vector3f _a, Vector3f _b) {
		return new Vector3f(_a.x > _b.x ? _a.x : _b.x, _a.y > _b.y ? _a.y : _b.y, _a.z > _b.z ? _a.z : _b.z);
	}

	public final static Vector3f add(final Vector3f _a, final Vector3f _b, final Vector3f _out) {
		return _out.add(_a.plus(_b));
	}
	
	/** Perform ' dot product 'between a and b vectors */
	public final static float dot(final Vector3f _a, final Vector3f _b) {
		return _a.x * _b.x + _a.y * _b.y + _a.z * _b.z;
	}
    public final static float dotProduct(final Vector3f _a, final Vector3f _b) {
        return _a.x * _b.x + _a.y * _b.y + _a.z * _b.z;
    }

	/** Perform ' cross product 'between a and b vectors */
	public final static Vector3f cross(final Vector3f _a, final Vector3f _b) {
		return new Vector3f( _a.y * _b.z - _a.z * _b.y,
							 _a.z * _b.x - _a.x * _b.z,
							 _a.x * _b.y - _a.y * _b.x  );
	}
	public final static Vector3f cross(final Vector3f _a, final Vector3f _b, final Vector3f _out) {
		return _out.set( 	_a.y * _b.z - _a.z * _b.y,
							_a.z * _b.x - _a.x * _b.z,
							_a.x * _b.y - _a.y * _b.x  );
	}
	public final static Vector3f crossProduct(final Vector3f _a, final Vector3f _b) {
		return cross(_a, _b);
	}
	public final static Vector3f crossProduct(final Vector3f _a, final Vector3f _b, final Vector3f _out) {
		return cross(_a, _b, _out);
	}

	public Vector3f() {
		 x = (float) 0.0;
		 y = (float) 0.0;
		 z = (float) 0.0;
	}
	public Vector3f(float _x, float _y, float _z) {
		 x = _x;
		 y = _y;
		 z = _z;
	}
	public Vector3f(Vector3f _d) {
		 this(_d.x, _d.y, _d.z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	public final Vector2f as2D() {
		return new Vector2f(x, y);
	}
	public final Vector2f as2D(Vector2f _out) {
		return _out.set(x, y);
	}
	
	/** Zero out this vector. */
	public final Vector3f setZero() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		return this;
	}
	public final Vector3f setNegate() {
		x = - x;
		y = - y;
		z = - z;
		return this;
	}
	/** Set the vector component-wise. */
	public final Vector3f set(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	public final Vector3f set(final Vector3f _d) {
		x = _d.x;
		y = _d.y;
		z = _d.z;
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

	public final float getZ() {
		return z;
	}
	public final void setZ(float z) {
		this.z = z;
	}

	public final float[] asFloats() {
		return new float[] { x, y, z };
	}
	public final int[] asInts() {
		return new int[] { (int) x, (int) y, (int) z };
	}

	public final Vector3f add(final float _t) {
		x += _t;
		y += _t;
		z += _t;
		return this;
	}
	public final Vector3f add(final float _u, final float _v, final float _w) {
		x += _u;
		y += _v;
		z += _w;
		return this;
	}
	public final Vector3f add(final Vector3f _d) {
		x += _d.x;
		y += _d.y;
		z += _d.z;
		return this;
	}

	/** Return the sum of this vector and another; does not alter either one. */
	public final Vector3f plus(final float _t) {
		return new Vector3f(x+_t, y+_t, z+_t);
	}
	public final Vector3f plus(final float _u, final float _v, final float _w) {
		return new Vector3f(x+_u, y+_v, z+_w);
	}
	public final Vector3f plus(final Vector3f _d) {
		return new Vector3f(x+_d.x, y+_d.y, z+_d.z);
	}

	public final Vector3f substract(final float _t) {
		x -= _t;
		y -= _t;
		z -= _t;
		return this;
	}
	public final Vector3f substract(final float _u, final float _v, final float _w) {
		x -= _u;
		y -= _v;
		z -= _w;
		return this;
	}
	public final Vector3f substract(final Vector3f _d) {
		x -= _d.x;
		y -= _d.y;
		z -= _d.z;
		return this;
	}

	/** Return the difference of this vector and another; does not alter either one. */
	public final Vector3f minus(final float _t) {
		return new Vector3f(x-_t, y-_t, z-_t);
	}
	public final Vector3f minus(final float _u, final float _v, final float _w) {
		return new Vector3f(x-_u, y-_v, z-_w);
	}
	public final Vector3f minus(final Vector3f _d) {
		return new Vector3f(x-_d.x, y-_d.y, z-_d.z);
	}

	public final Vector3f multiply(final float _t) {
		x *= _t;
		y *= _t;
		z *= _t;
		return this;
	}
	public final Vector3f multiply(final float _u, final float _v, final float _w) {
		x *= _u;
		y *= _v;
		z *= _w;
		return this;
	}
	public final Vector3f multiply(final Vector3f _d) {
		x *= _d.x;
		y *= _d.y;
		z *= _d.z;
		return this;
	}

	/** Return this vector multiplied by a scalar; does not alter this vector. */
	public final Vector3f times(final float _t) {
		return new Vector3f(x*_t, y*_t, z*_t);
	}
	public final Vector3f times(final float _u, final float _v, final float _w) {
		return new Vector3f(x*_u, y*_v, z*_w);
	}
	public final Vector3f times(final Vector3f _d) {
		return new Vector3f(x*_d.x, y*_d.y, z*_d.z);
	}
	
	public final Vector3f divide(final float _t) /*throws Exception*/ {
		if(_t == 0) return null; //throw new Exception();
		x /= _t;
		y /= _t;
		z /= _t;
		
		return this;
	}
	public final Vector3f divide(final float _u, final float _v, final float _w) /*throws Exception*/ {
		if(_u == 0 || _v == 0 || _w == 0) return null; //throw new Exception();
		x /= _u;
		y /= _v;
		z /= _w;
		
		return this;
	}

	public final Vector3f divideConst(final float _t) /*throws Exception*/ {
		if(_t == 0) return null; //throw new Exception();
		return new Vector3f(x/_t, y/_t, z/_t);
	}
	public final Vector3f divideConst(final float _u, final float _v, final float _w) /*throws Exception*/ {
		if(_u == 0 || _v == 0 || _w == 0) return null; //throw new Exception();
		return new Vector3f(x/_u, y/_v, z/_w);
	}

	public final float dot(final Vector3f _b) {
        return x * _b.x + y * _b.y + z * _b.z;
    }
	public final float dotProduct(final Vector3f _b) {
        return x * _b.x + y * _b.y + z * _b.z;
    }
	public final Vector3f cross(final Vector3f _b) {
		return new Vector3f( 	y * _b.z - z * _b.y,
							z * _b.x - x * _b.z,
							x * _b.y - y * _b.x  );
	}
	public final Vector3f crossProduct(final Vector3f _b) {
		return new Vector3f( 	y * _b.z - z * _b.y,
							z * _b.x - x * _b.z,
							x * _b.y - y * _b.x  );
	}
	
	/** Return the negation of this vector; does not alter this vector. */
	public final Vector3f negate() {
		return new Vector3f(-x, -y, -z);
	}

	/** Return a new vector that has positive components. */
	public final Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/** Return a copy of this vector. */
	public final Vector3f clone() {
		return new Vector3f(x, y, z);
	}

	/** Return the length of this vector. */
	public final float length() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
    public float magnitude() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }
	/** Return the norm of this vector. */
	public final float norm() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	/** Return the squared norm of this vector. */
	public final float norm2() {
		return x*x + y*y + z*z;
	}

	/** Normalize this vector and return it. Alters this vector. */
	public final Vector3f normalize() {
		float length = length();
		if(length < EPSILON) {
			return this;
		}

		float invLength = 1.0f / length;
		x *= invLength;
		y *= invLength;
		z *= invLength;
		return this;
	}

	public final Vector3f normalized() {
		float length = length();
		if(length < EPSILON) {
			return this;
		}

		float invLength = 1.0f / length;
		return new Vector3f(x * invLength, y * invLength, z * invLength);
	}

	/** True if the vector represents a pair of valid, non-infinite floating point numbers. */
	public final boolean isValid() {
		return !Float.isNaN(x) && !Float.isInfinite(x) && !Float.isNaN(y) && !Float.isInfinite(y) && !Float.isNaN(z) && !Float.isInfinite(z);
	}

	public final boolean isEqual(final float _t) {
		return (x == _t && y == _t && z == _t) ? true : false;
	}
	public final boolean isEqual(final float _u, final float _v, final float _w) {
		return (x == _u && y == _v && z == _w) ? true : false;
	}
	public final boolean isEqual(final Vector3f _d) {
		return (x == _d.x && y == _d.y && z == _d.z) ? true : false;
	}

	public final boolean isDifferent(final float _t) {
		return (x != _t || y != _t || z != _t) ? true : false;
	}
	public final boolean isDifferent(final float _u, final float _v, final float _w) {
		return (x != _u || y != _v || z != _w) ? true : false;
	}
	public final boolean isDifferent(final Vector3f _d) {
		return (x != _d.x || y != _d.y || z != _d.z) ? true : false;
	}

	public final boolean isColinear(final Vector3f _vec) {
		return (x * _vec.y) == (y * _vec.x) && (x * _vec.z) == (z * _vec.x);
	}
	public final boolean isColinearToSegment(final Vector3f _A, final Vector3f _B) {
		return (x * (_B.y - _A.y)) == (y * (_B.x - _A.x)) && (x * (_B.z - _A.z)) == (z * (_B.x - _A.x));
	}
	public final boolean isColinearToLine(final Vector3f _P, final Vector3f _N) {
		return (x * _N.y) == (y * _N.x) && (x * _N.z) == (z * _N.x);
	}

	public final boolean isOrthogonal(final Vector3f _vec) {
		return (dot(this, _vec) < EPSILON) ? true : false;
	}
	public final boolean isOrthogonalToSegment(final Vector3f _A, final Vector3f _B) {
		return (dot(this, _B.substract(_A)) < EPSILON) ? true : false;
	}
	public final boolean isOrthogonalToLine(final Vector3f _P, final Vector3f _N) {
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
		Vector3f other = (Vector3f) obj;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if(Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if(Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

}
