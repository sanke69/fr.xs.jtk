package fr.xs.jtk.math.type.geom;

import java.util.stream.DoubleStream;

import fr.xs.jtk.math.type.Vector3f;

public class Point3D extends Vector3f {
	private static final long serialVersionUID = 1L;

	public float f;
	public Point3D() {
		super();
		f = 0.0f;
	}
	public Point3D(float _x, float _y, float _z) {
		super(_x, _y, _z);
		f = 0.0f;
	}
	public Point3D(float _x, float _y, float _z, float _f) {
		super(_x, _y, _z);
		f = _f;
	}
	public Point3D(Vector3f _v) {
		super(_v.x, _v.y, _v.z);
		f = 0.0f;
	}
	public Point3D(Point3D _p) {
		super(_p.x, _p.y, _p.z);
		f = _p.f;
	}

    public DoubleStream getCoordinates() { return DoubleStream.of(x,y,z); }
    public DoubleStream getCoordinates(float factor) { return DoubleStream.of(factor*x,factor*y,factor*z); }

}
