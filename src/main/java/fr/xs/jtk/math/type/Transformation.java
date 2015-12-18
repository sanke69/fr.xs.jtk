package fr.xs.jtk.math.type;

public class Transformation extends Matrix4f {
	
	public static Transformation uniformRotationMatrix2D(final double _angle) {
		Transformation R = new Transformation();
		R.setIdentity();
		float c = (float) Math.cos(_angle), s = (float) Math.sin(_angle); 
		R.m00 = c;		R.m01 = - s;
		R.m10 = s;		R.m11 = c;
		return R;
	}
	public static Transformation uniformTranslationMatrix2D(final float _Tx, final float _Ty) {
		Transformation T = new Transformation();
		T.setIdentity();
		T.m02 = _Tx;		T.m12 = _Ty;
		return T;
	}
	public static Transformation uniformScaleMatrix2D(final float _ratio) {
		Transformation S = new Transformation();
		S.setIdentity();
		S.m00 *= _ratio;	S.m11 *= _ratio;
		return S;
	}
	public static Transformation uniformTransformationMatrix2D(final Vector2f _t, final float _angle) {
		Transformation S = new Transformation();
		float    	   c = (float) Math.cos(_angle), s = (float) Math.sin(_angle); 
		S.setIdentity();
		S.m00 = c;		S.m01 = - s;	S.m02 = _t.x;
		S.m10 = s;		S.m11 = c;		S.m12 = _t.y;
		return S;
	}
	public static Transformation uniformTransformationMatrix2D(final float _tx, final float _ty, final float _angle, final float _scale) {
		Transformation S = new Transformation();
		float    	   c = (float) Math.cos(_angle), s = (float) Math.sin(_angle); 
		S.setIdentity();
		S.m00 = c * _scale;		S.m01 = - s;			S.m02 = _tx;
		S.m10 = s;				S.m11 = c * _scale;		S.m12 = _ty;
		return S;
	}

	public static Transformation extractUniformRotationMatrix2D(final Transformation _trans) {
		Transformation R = new Transformation();
		R.setIdentity();
		R.m00 = _trans.m00;	R.m01 = _trans.m01;
		R.m10 = _trans.m10;	R.m11 = _trans.m11;
		return R;
	}
	public static float          extractRotationAngle2D(final Transformation _trans) {
		return (float) Math.atan2(_trans.m01, _trans.m00);
	}
	public static Transformation extractUniformTranslationMatrix2D(final Transformation _trans) {
		Transformation T = new Transformation();
		T.setIdentity();
		T.m02 = _trans.m02;	T.m12 = _trans.m12;
		return T;
	}
	public static Vector2f         extractTranslationVector2D(final Transformation _trans) {
		return new Vector2f(_trans.m02, _trans.m12);
	}
	public static Transformation extractUniformScale2D(final Transformation _trans) {
		Transformation S = new Transformation();
		S.setIdentity();
		S.m00 = _trans.m00;	S.m11 = _trans.m11;
		return S;
	}
	public static float          extractScale2D(final Transformation _trans) {
		return _trans.m02; // _trans.m12;
	}

	// TODO:: Check !!!
	public static Transformation uniformRotationMatrix3DaroundX(final float _angle) {
		Transformation R = new Transformation();
		R.m00 = 1.0f;
		R.m11 = (float) Math.cos(_angle);		R.m12 = (float) - Math.sin(_angle);
		R.m21 = (float) Math.sin(_angle);		R.m12 = (float) Math.cos(_angle);
		R.m33 = 1.0f;
		return R;
	}
	public static Transformation uniformRotationMatrix3DaroundY(final float _angle) {
		Transformation R = new Transformation();
		R.m00 = (float) Math.cos(_angle);		R.m12 = (float) - Math.sin(_angle);
		R.m11 = 1.0f;
		R.m20 = (float) Math.sin(_angle);		R.m12 = (float) Math.cos(_angle);
		R.m33 = 1.0f;
		return R;
	}
	public static Transformation uniformRotationMatrix3DaroundZ(final float _angle) {
		Transformation R = new Transformation();
		R.m00 = (float) Math.cos(_angle);		R.m01 = (float) - Math.sin(_angle);
		R.m01 = (float) Math.sin(_angle);		R.m11 = (float) Math.cos(_angle);
		R.m22 = 1.0f;
		R.m33 = 1.0f;
		return R;
	}
	public static Transformation uniformTranslationMatrix3D(final float _Tx, final float _Ty, final float _Tz) {
		Transformation T = new Transformation();
		T.setIdentity();
		T.m03 = _Tx;		T.m13 = _Ty;		T.m23 = _Tz;
		return T;
	}
	public static Transformation uniformScaleMatrix3D(final float _ratio) {
		Transformation S = new Transformation();
		S.m00 = _ratio;		S.m11 = _ratio;		S.m22 = _ratio;		S.m33 = 1.0f;
		return S;
	}

	// TODO:: Check !!!
	public static Transformation extractUniformRotationMatrix3DaroundX(final Transformation _trans) {
		Transformation R = new Transformation();
		R.setIdentity();
		R.m11 = _trans.m11;		R.m12 = _trans.m12;
		R.m21 = _trans.m21;		R.m22 = _trans.m22;
		return R;
	}
	public static Transformation extractUniformRotationMatrix3DaroundY(final Transformation _trans) {
		Transformation R = new Transformation();
		R.setIdentity();
		R.m11 = _trans.m11;		R.m12 = _trans.m12;
		R.m21 = _trans.m21;		R.m22 = _trans.m22;
		return R;
	}
	public static Transformation extractUniformRotationMatrix3DaroundZ(final Transformation _trans) {
		Transformation R = new Transformation();
		R.setIdentity();
		R.m11 = _trans.m11;		R.m12 = _trans.m12;
		R.m21 = _trans.m21;		R.m22 = _trans.m22;
		return R;
	}
	public static Transformation extractUniformTranslationMatrix3D(final Transformation _trans) {
		Transformation T = new Transformation();
		T.setIdentity();
		T.m03 = _trans.m03;		T.m13 = _trans.m13;		T.m23 = _trans.m23;
		return T;
	}
	public static Transformation extractUniformScaleMatrix3D(final Transformation _trans) {
		Transformation S = new Transformation();
		S.setIdentity();
		S.m00 = _trans.m00;		S.m11 = _trans.m11;		S.m22 = _trans.m22;		S.m33 = _trans.m33;
		return S;
	}
	public static float          extractScale3D(final Transformation _trans) {
		return _trans.m02; // _trans.m12;
	}

	public Transformation() {
		super();
		setIdentity();
	}
	public Transformation(Matrix4f _m) {
		super(_m);
	}
	public Transformation(Transformation _t) {
		super(_t);
	}

	@Override
	public Transformation clone() {
		return new Transformation(this);
	}

	public Vector2f transformPoint2D(Vector2f _p) {
		Vector3f up = new Vector3f(_p.x, _p.y, 1.0f);
		return as3D().times(up).as2D();
	}
	public Vector2f transformPoint2D(Vector2f _p, Vector2f _out) {
		Vector3f up = new Vector3f(_p.x, _p.y, 1.0f);
		return as3D().times(up).as2D(_out);
	}
	public Vector2f transformVector2D(Vector2f _v) {
		Vector3f uv = new Vector3f(_v.x, _v.y, 0.0f);
		return as3D().times(uv).as2D();
	}
	public Vector2f transformVector2D(Vector2f _v, Vector2f _out) {
		Vector3f uv = new Vector3f(_v.x, _v.y, 0.0f);
		return as3D().times(uv).as2D(_out);
	}

	public Vector3f transformUniform2D(Vector3f _p) {
		return _p.set( as3D().times(_p) );
	}
	public Vector3f transformUniform2D(Vector3f _p, Vector3f _out) {
		return _out.set( as3D().times(_p) );
	}

	public Vector3f transformPoint3D(Vector3f _p) {
		Vector4f up = new Vector4f(_p.x, _p.y, _p.z, 1.0f);
		return times(up).as3D();
	}
	public Vector3f transformPoint3D(Vector3f _p, Vector3f _out) {
		Vector4f up = new Vector4f(_p.x, _p.y, _p.z, 1.0f);
		return times(up).as3D(_out);
	}
	public Vector3f transformVector3D(Vector3f _v) {
		Vector4f uv = new Vector4f(_v.x, _v.y, _v.z, 0.0f);
		return times(uv).as3D();
	}
	public Vector3f transformVector3D(Vector3f _v, Vector3f _out) {
		Vector4f uv = new Vector4f(_v.x, _v.y, _v.z, 0.0f);
		return times(uv).as3D(_out);
	}

	public Vector4f transformUniform3D(Vector4f _p) {
		return _p.set( times(_p) );
	}
	public Vector4f transformUniform3D(Vector4f _p, Vector4f _out) {
		return _out.set( times(_p) );
	}

	@Deprecated
	public static Transformation rotationMatrix2D(final double _angle) {
		Transformation R = new Transformation();
		R.setIdentity();
		float    c = (float) Math.cos(_angle), s = (float) Math.sin(_angle); 
		R.m00 = c;	R.m01 = - s;
		R.m10 = s;	R.m11 = c;
		return R;
	}
	@Deprecated
	public static Transformation scaleMatrix2D(final float _ratio) {
		Transformation S = new Transformation();
		S.setIdentity();
		S.m00 = _ratio;		S.m11 = _ratio;
		return S;
	}

	@Deprecated
	public static Transformation rotationMatrix3DaroundX(final float _angle) {
		Transformation R = new Transformation();
		R.setIdentity();
		R.m11 = (float) Math.cos(_angle);		R.m12 = (float) - Math.sin(_angle);
		R.m21 = (float) Math.sin(_angle);		R.m12 = (float) Math.cos(_angle);
		return R;
	}
	@Deprecated
	public static Transformation rotationMatrix3DaroundY(final float _angle) {
		Transformation R = new Transformation();
		R.setIdentity();
		R.m00 = (float) Math.cos(_angle);		R.m12 = (float) - Math.sin(_angle);
		R.m20 = (float) Math.sin(_angle);		R.m12 = (float) Math.cos(_angle);
		return R;
	}
	@Deprecated
	public static Transformation rotationMatrix3DaroundZ(final float _angle) {
		Transformation R = new Transformation();
		R.setIdentity();
		R.m00 = (float) Math.cos(_angle);		R.m01 = (float) - Math.sin(_angle);
		R.m01 = (float) Math.sin(_angle);		R.m11 = (float) Math.cos(_angle);
		return R;
	}
	@Deprecated
	public static Transformation translationMatrix3D(final float _x, final float _y, final float _z) {
		Transformation Z = new Transformation();
		Z.setIdentity();
		Z.m03 = _x;		Z.m13 = _y;		Z.m23 = _z;
		return Z;
	}
	@Deprecated
	public static Transformation scaleMatrix3D(final float _ratio) {
		Transformation S = new Transformation();
		S.setIdentity();
		S.m00 = _ratio;		S.m11 = _ratio;		S.m22 = _ratio;
		return S;
	}
	
}
