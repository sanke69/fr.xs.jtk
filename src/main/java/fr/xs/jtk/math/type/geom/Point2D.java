package fr.xs.jtk.math.type.geom;

import fr.xs.jtk.math.type.Vector2f;

public class Point2D extends Vector2f {
	private static final long serialVersionUID = 1L;

	public final static Point2D X = new Point2D(1.0f, 0.0f);
	public final static Point2D Y = new Point2D(0.0f, 1.0f);

	public Point2D() {
	    this(0, 0);
	}
	public Point2D(final float _x, final float _y) {
		x = _x;
		y = _y;
	}
	public Point2D(Vector2f _copy) {
	    this(_copy.x, _copy.y);
	}

}
