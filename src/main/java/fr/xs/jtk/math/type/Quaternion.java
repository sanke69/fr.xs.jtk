package fr.xs.jtk.math.type;

import javafx.geometry.Point3D;
import javafx.scene.Node;

public class Quaternion {

	protected float x, y, z, w;

	public Quaternion() {
		x = 0.0f;y = 0.0f; z = 0.0f; w = 1.0f;
	}
	public Quaternion(final float _x, final float _y, final float _z, final float _w) {
		x = _x;y = _y; z = _z; w = _w;
	}
	public Quaternion(final Vector3f _axe, final float _angle) {
		float cos = (float) Math.cos(_angle / 2);
		float sin = (float) Math.sin(_angle / 2);
	
		x = _axe.x * sin;
		y = _axe.y * sin;
		z = _axe.z * sin;
		w = cos;
	
		float norm = x*x + y*y + z*z + w*w;
	
		if(Math.abs(norm) > 1e-13) {
			x /= norm;
			y /= norm;
			z /= norm;
			w /= norm;
		}
	}
	public Quaternion(final Matrix4f _m) {
		float trace = _m.m00 + _m.m11 + _m.m22 + 1.0f;
	
		if(trace > 0) {
			float s = 0.5f / (float) Math.sqrt(trace);
			x = (_m.m21 - _m.m12) * s;
			y = (_m.m02 - _m.m20) * s;
			z = (_m.m10 - _m.m01) * s;
			w = 0.25f / s;
		} else {
			if((_m.m00 > _m.m11) && (_m.m00 > _m.m22)) {
				float s = (float) Math.sqrt(1 + _m.m00 - _m.m11 - _m.m22) * 2;
				x = 0.5f / s;
				y = (_m.m01 + _m.m10) / s;
				z = (_m.m02 + _m.m20) / s;
				w = (_m.m12 + _m.m21) / s;
			} else if(_m.m11 > _m.m22) {
				float s = (float) Math.sqrt(1 - _m.m00 + _m.m11 - _m.m22) * 2;
				x = (_m.m01 + _m.m10) / s;
				y = 0.5f / s;
				z = (_m.m12 + _m.m21) / s;
				w = (_m.m02 + _m.m20) / s;
			} else {
				float s = (float) Math.sqrt(1 - _m.m00 - _m.m11 + _m.m22) * 2;
				x = (_m.m02 + _m.m20) / s;
				y = (_m.m12 + _m.m21) / s;
				z = 0.5f / s;
				w = (_m.m01 + _m.m10) / s;
			}
		}
	}
	public Quaternion(final Quaternion _q) {
		x = _q.x; y = _q.y; z = _q.z; w = _q.w;
	}

	public Quaternion copy() {
		return new Quaternion(this);
	}
	
	public void set(final Quaternion _q) {
		x = _q.x; y = _q.y; z = _q.z; w = _q.w;
	}

	// OK
	public Quaternion multiply(final Quaternion _r) {
		return new Quaternion(	w * _r.x + x * _r.w + y * _r.z - z * _r.y,
								w * _r.y + y * _r.w + z * _r.x - x * _r.z,
								w * _r.z + z * _r.w + x * _r.y - y * _r.x,
								w * _r.w - x * _r.x - y * _r.z - z * _r.z
							);
	}
	public Quaternion setIdentity() {
		x = 0.0f;y = 0.0f; z = 0.0f; w = 1.0f;
		return this;
	}

	public static Quaternion Identity() {
		return new Quaternion( 0.0f, 0.0f, 0.0f, 1.0f );
	}
	// OK
	public static Quaternion FromAxisAngle(final Vector3f _v, final float _angle) {
		float A = _angle, sinA;
		A *= 0.5f;
		Vector3f vn = new Vector3f(_v);
		vn.normalize();
	 
		sinA = (float) Math.sin(A);

		return new Quaternion(vn.x * sinA, vn.y * sinA, vn.z * sinA, (float) Math.cos(A));
	}
	// OK
	public static Quaternion FromEulerAngle(final float _pitch, final float _yaw, final float _roll) {
		float PIOVER180 = (float) (Math.PI / 180.0f);
		float p = _pitch * PIOVER180 / 2.0f;
		float y = _yaw * PIOVER180 / 2.0f;
		float r = _roll * PIOVER180 / 2.0f;
	 
		float sinp = (float) Math.sin(p);
		float siny = (float) Math.sin(y);
		float sinr = (float) Math.sin(r);
		float cosp = (float) Math.cos(p);
		float cosy = (float) Math.cos(y);
		float cosr = (float) Math.cos(r);
	 
		Quaternion q = new Quaternion(
		sinr * cosp * cosy - cosr * sinp * siny,
		cosr * sinp * cosy + sinr * cosp * siny,
		cosr * cosp * siny - sinr * sinp * cosy,
		cosr * cosp * cosy + sinr * sinp * siny
		);
	 
		q.normalize();
		return q;

/*
		Quaternion Qx = new Quaternion(new Vector3f(1, 0, 0), _pitch);
		Quaternion Qy = new Quaternion(new Vector3f(0, 1, 0), _yaw);
		Quaternion Qz = new Quaternion(new Vector3f(0, 0, 1), _roll);
		Quaternion Q  = Qx.multiply(Qy).multiply(Qz);
		return Q;
*/
	}

	// OK
	public void normalize() {
	    float norm = x*x + y*y + z*z + w*w;
	
	    if(Math.abs(norm) > 1e-13) {
	        x /= norm;
	        y /= norm;
	        z /= norm;
	        w /= norm;
	    }
	}
	// OK
	public Quaternion conjugate() {
		return new Quaternion(- x, - y, - z, w);
	}
	// OK
	public Quaternion inverse() {
	    float norm = x*x + y*y + z*z + w*w;
		return new Quaternion(x/norm, - y/norm, - z/norm, -w/norm);
	}
	// OK
	public Matrix4f toMatrix() {
	    float 
			xx = x * x, xy = x * y, xz = x * z, 
			xw = x * w, yy = y * y, yz = y * z, 
			yw = y * w, zz = z * z, zw = z * w;
	
	    return new Matrix4f(1 - 2 * (yy + zz),     2 * (xy - zw),     2 * (xz + yw), 0,
                              	2 * (xy + zw), 1 - 2 * (xx + zz),     2 * (yz - xw), 0,
                                2 * (xz - yw),     2 * (yz + xw), 1 - 2 * (xx + yy), 0,
                                			0,                 0,                 0, 1);
	}
	public Vector4f toAxisAngle() {
		Vector4f axisAngle = new Vector4f();
		
		axisAngle.w = (float) Math.acos(w) * 2.0f;
	
	    float norm = (float) Math.sqrt(x*x + y*y + z*z);
	    if(Math.abs(norm) > 1e-6) {
	    	axisAngle.x = x / norm;
	    	axisAngle.y = y / norm;
	    	axisAngle.z = z / norm;
	    } else {
	    	axisAngle.x = 0.0f;
	    	axisAngle.y = 1.0f;
	    	axisAngle.z = 0.0f;
	    }
	    
	    return axisAngle;
	}

	// OK
	public Vector3f transform(final Vector3f _p) {
		Vector3f p = new Vector3f(_p);
		p.normalize();
		
		Quaternion vecQuat = new Quaternion(p.x, p.y, p.z, 0.0f), resQuat = new Quaternion();
		resQuat = vecQuat.multiply(conjugate());
		resQuat = this.multiply(resQuat);
		
		return new Vector3f(resQuat.x, resQuat.y, resQuat.z);
/*
		float 
			m0  = 1.0f - 2.0f * (y * y + z * z),
			m1  =        2.0f * (x * y + z * w),
			m2  =        2.0f * (x * z - y * w),
			m4  =        2.0f * (x * y - z * w),
			m5  = 1.0f - 2.0f * (x * x + z * z),
			m6  =        2.0f * (z * y + x * w),
			m8  =        2.0f * (x * z + y * w),
			m9  =        2.0f * (y * z - x * w),
			m10 = 1.0f - 2.0f * (x * x + y * y);
	
		p.x = _p.x * m0 + _p.y * m4 + _p.z * m8;
		p.y = _p.x * m1 + _p.y * m5 + _p.z * m9;
		p.z = _p.x * m2 + _p.y * m6 + _p.z * m10;
	
		return p;
*/
	}
	public Transformation toTransformation() {
		return new Transformation(toMatrix());
	}

	// http://stackoverflow.com/questions/30145414/rotate-a-3d-object-on-3-axis-in-javafx-properly
	protected void matrixRotateNode0(Node n, double alf, double bet, double gam){
	    double A11=Math.cos(alf)*Math.cos(gam);
	    double A12=Math.cos(bet)*Math.sin(alf)+Math.cos(alf)*Math.sin(bet)*Math.sin(gam);
	    double A13=Math.sin(alf)*Math.sin(bet)-Math.cos(alf)*Math.cos(bet)*Math.sin(gam);
	    double A21=-Math.cos(gam)*Math.sin(alf);
	    double A22=Math.cos(alf)*Math.cos(bet)-Math.sin(alf)*Math.sin(bet)*Math.sin(gam);
	    double A23=Math.cos(alf)*Math.sin(bet)+Math.cos(bet)*Math.sin(alf)*Math.sin(gam);
	    double A31=Math.sin(gam);
	    double A32=-Math.cos(gam)*Math.sin(bet);
	    double A33=Math.cos(bet)*Math.cos(gam);

	    double d = Math.acos((A11+A22+A33-1d)/2d);
	    if(d!=0d){
	        double den=2d*Math.sin(d);
	        Point3D p= new Point3D((A32-A23)/den,(A13-A31)/den,(A21-A12)/den);
	        n.setRotationAxis(p);
	        n.setRotate(Math.toDegrees(d));                    
	    }
	}
}
