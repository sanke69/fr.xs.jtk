package fr.xs.jtk.math.type.geom;

import fr.xs.jtk.math.type.Vector2f;

public class Vector2D extends Vector2f {
	private static final long serialVersionUID = 1L;

	public final static Vector2D X = new Vector2D(1.0f, 0.0f);
	public final static Vector2D Y = new Vector2D(0.0f, 1.0f);

	public Vector2D() {
	    this(0, 0);
	}
	public Vector2D(final float _x, final float _y) {
		x = _x;
		y = _y;
	}
	public Vector2D(Vector2f _copy) {
	    this(_copy.x, _copy.y);
	}

}
