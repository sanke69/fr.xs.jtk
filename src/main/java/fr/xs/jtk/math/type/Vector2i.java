package fr.xs.jtk.math.type;

public class Vector2i {

	public int x, y;

	public Vector2i() {
		x = 0;
		y = 0;
	}
	public Vector2i(final int _x, final int _y) {
		x = _x;
		y = _y;
	}
	public Vector2i(Vector2i _d) {
		x = _d.x;
		y = _d.y;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int[] asFloats() {
		return new int[] { x, y };
	}

	public int[] asInts() {
		return new int[] { (int) x, (int) y };
	}

	public Vector2i Equal(final int _t) {
		x = _t;
		y = _t;
		
		return this;
	}
	public Vector2i Equal(final int _u, final int _v) {
		x = _u;
		y = _v;
		
		return this;
	}
	public Vector2i Equal(final Vector2i _d) {
		x = _d.x;
		y = _d.y;
		
		return this;
	}

	public Vector2i Add(final int _t) {
		x += _t;
		y += _t;
		
		return this;
	}
	public Vector2i Add(final int _u, final int _v) {
		x += _u;
		y += _v;
		
		return this;
	}
	public Vector2i Add(final Vector2i _d) {
		x += _d.x;
		y += _d.y;
		
		return this;
	}

	// /!\ MULTIPLICATION DES INSTANCES /!\
	public Vector2i AddConst(final int _t) {
		return new Vector2i(x+_t, y+_t);
	}
	public Vector2i AddConst(final int _u, final int _v) {
		return new Vector2i(x+_u, y+_v);
	}
	public Vector2i AddConst(final Vector2i _d) {
		return new Vector2i(x+_d.x, y+_d.y);
	}

	public Vector2i Substract(final int _t) {
		x -= _t;
		y -= _t;
		
		return this;
	}
	public Vector2i Substract(final int _u, final int _v) {
		x -= _u;
		y -= _v;
		
		return this;
	}
	public Vector2i Substract(final Vector2i _d) {
		x -= _d.x;
		y -= _d.y;
		
		return this;
	}

	// /!\ MULTIPLICATION DES INSTANCES /!\
	public Vector2i SubstractConst(final int _t) {
		return new Vector2i(x-_t, y-_t);
	}
	public Vector2i SubstractConst(final int _u, final int _v) {
		return new Vector2i(x-_u, y-_v);
	}
	public Vector2i SubstractConst(final Vector2i _d) {
		return new Vector2i(x-_d.x, y-_d.y);
	}

	public Vector2i Multiply(final int _t) {
		x *= _t;
		y *= _t;
		
		return this;
	}
	public Vector2i Multiply(final int _u, final int _v) {
		x *= _u;
		y *= _v;
		
		return this;
	}
	public Vector2i Multiply(final Vector2i _d) {
		x *= _d.x;
		y *= _d.y;
		
		return this;
	}

	// /!\ MULTIPLICATION DES INSTANCES /!\
	public Vector2i MultiplyConst(final int _t) {
		return new Vector2i(x*_t, y*_t);
	}
	public Vector2i MultiplyConst(final int _u, final int _v) {
		return new Vector2i(x*_u, y*_v);
	}
	public Vector2i MultiplyConst(final Vector2i _d) {
		return new Vector2i(x*_d.x, y*_d.y);
	}

	public Vector2i Divide(final int _t) throws Exception {
		if(_t == 0) throw new Exception();
		x /= _t;
		y /= _t;
		
		return this;
	}
	public Vector2i Divide(final int _u, final int _v) throws Exception {
		if(_u == 0 || _v == 0) throw new Exception();
		x /= _u;
		y /= _v;
		
		return this;
	}

	// /!\ MULTIPLICATION DES INSTANCES /!\
	public Vector2i DivideConst(final int _t) throws Exception {
		if(_t == 0) throw new Exception();
		return new Vector2i(x/_t, y/_t);
	}
	public Vector2i DivideConst(final int _u, final int _v) throws Exception {
		if(_u == 0 || _v == 0) throw new Exception();
		return new Vector2i(x/_u, y/_v);
	}

	public boolean isEqual(final int _t) {
		return (x == _t && y == _t) ? true : false;
	}
	public boolean isEqual(final int _u, final int _v) {
		return (x == _u && y == _v) ? true : false;
	}
	public boolean isEqual(final Vector2i _d) {
		return (x == _d.x && y == _d.y) ? true : false;
	}

	public boolean isDifferent(final int _t) {
		return (x != _t || y != _t) ? true : false;
	}
	public boolean isDifferent(final int _u, final int _v) {
		return (x != _u || y != _v) ? true : false;
	}
	public boolean isDifferent(final Vector2i _d) {
		return (x != _d.x || y != _d.y) ? true : false;
	}

	public Vector2i Normalize() {
		if(x == 0 && y == 0) return this;
		
		int l = (int) Math.sqrt(x*x + y*y);
		
		x /= l;
		y /= l;
		
		return this;
	}

	// /!\ MULTIPLICATION DES INSTANCES /!\
	public Vector2i NormalizeConst() {
		if(x == 0 && y == 0) return this;

		int l = (int) Math.sqrt(x*x + y*y);

		return new Vector2i(x / l, y / l);
	}


	public int getNorm() {
		return (int) Math.sqrt(x*x + y*y);
	}
	public int getLength() {
		return (int) Math.sqrt(x*x + y*y);
	}
	public int getNorm2() {
		return x*x + y*y;
	}

}
