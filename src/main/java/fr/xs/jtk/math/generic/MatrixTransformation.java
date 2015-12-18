package fr.xs.jtk.math.generic;

import fr.xs.jtk.math.generic.Matrix;

public class MatrixTransformation {

	public static Matrix rotationMatrix2D(final double _angle) {
		Matrix R = new Matrix(2, 2);
		R.data[0][0] = Math.cos(_angle);		R.data[0][1] = - Math.sin(_angle);
		R.data[1][0] = Math.sin(_angle);		R.data[1][1] = Math.cos(_angle);
		return R;
	}
	public static Matrix uniformRotationMatrix2D(final double _angle) {
		Matrix R = new Matrix(3, 3);
		R.data[0][0] = Math.cos(_angle);		R.data[0][1] = - Math.sin(_angle);
		R.data[1][0] = Math.sin(_angle);		R.data[1][1] = Math.cos(_angle);		R.data[2][2] = 1.0;
		return R;
	}

	public static Matrix uniformTranslationMatrix2D(final double _Tx, final double _Ty) {
		Matrix T = Matrix.eye(3, 1.0);
		T.data[0][2] = _Tx;		T.data[1][2] = _Ty;
		return T;
	}

	public static Matrix uniformScaleMatrix2D(final double _ratio) {
		Matrix Z = Matrix.eye(3, _ratio); Z.data[2][2] = 1.0;
		return Z;
	}

	public static Matrix rotationMatrix3D_aroundX(final double _angle) {
		Matrix R = new Matrix(3, 3, 0.0);
		R.data[0][0] = 1.0;
		R.data[1][1] = Math.cos(_angle);		R.data[1][2] = - Math.sin(_angle);
		R.data[2][1] = Math.sin(_angle);		R.data[1][2] = Math.cos(_angle);
		return R;
	}
	public static Matrix rotationMatrix3D_aroundY(final double _angle) {
		Matrix R = new Matrix(3, 3, 0.0);
		R.data[0][0] = Math.cos(_angle);		R.data[1][2] = - Math.sin(_angle);
		R.data[1][1] = 1.0;
		R.data[2][0] = Math.sin(_angle);		R.data[1][2] = Math.cos(_angle);
		return R;
	}
	public static Matrix rotationMatrix3D_aroundZ(final double _angle) {
		Matrix R = new Matrix(3, 3, 0.0);
		R.data[0][0] = Math.cos(_angle);		R.data[0][1] = - Math.sin(_angle);
		R.data[0][1] = Math.sin(_angle);		R.data[1][1] = Math.cos(_angle);
		R.data[2][2] = 1.0;
		return R;
	}
	public static Matrix uniformRotationMatrix3D_aroundX(final double _angle) {
		Matrix R = new Matrix(4, 4, 0.0);
		R.data[0][0] = 1.0;
		R.data[1][1] = Math.cos(_angle);		R.data[1][2] = - Math.sin(_angle);
		R.data[2][1] = Math.sin(_angle);		R.data[1][2] = Math.cos(_angle);
		R.data[3][3] = 1.0;
		return R;
	}
	public static Matrix uniformRotationMatrix3D_aroundY(final double _angle) {
		Matrix R = new Matrix(4, 4, 0.0);
		R.data[0][0] = Math.cos(_angle);		R.data[1][2] = - Math.sin(_angle);
		R.data[1][1] = 1.0;
		R.data[2][0] = Math.sin(_angle);		R.data[1][2] = Math.cos(_angle);
		R.data[3][3] = 1.0;
		return R;
	}
	public static Matrix uniformRotationMatrix3D_aroundZ(final double _angle) {
		Matrix R = new Matrix(4, 4, 0.0);
		R.data[0][0] = Math.cos(_angle);		R.data[0][1] = - Math.sin(_angle);
		R.data[0][1] = Math.sin(_angle);		R.data[1][1] = Math.cos(_angle);
		R.data[2][2] = 1.0;
		R.data[3][3] = 1.0;
		return R;
	}

	public static Matrix uniformTranslationMatrix3D(final double _Tx, final double _Ty, final double _Tz) {
		Matrix T = Matrix.eye(4, 1.0);
		T.data[0][3] = _Tx;		T.data[1][3] = _Ty;		T.data[2][3] = _Tz;
		return T;
	}

	public static Matrix uniformScaleMatrix3D(final double _ratio) {
		Matrix Z = Matrix.eye(4, _ratio); Z.data[3][3] = 1.0;
		return Z;
	}

}
