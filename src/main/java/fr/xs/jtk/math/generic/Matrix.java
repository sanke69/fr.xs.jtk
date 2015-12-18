package fr.xs.jtk.math.generic;

import java.util.HashMap;
import java.util.Map;

public class Matrix {

	public double[][] data;

	public double[][] data() { return data; }
	public double     data(int _i, int _j) { return data[_i][_j]; }
	public int        rows() { return data.length; }
	public int        cols() { return data[0].length; }

	public Matrix() {
		;
	}
	public Matrix(final int _nRow, final int _nColumn) {
		data = new double[_nRow][_nColumn];
	}
	public Matrix(final int _nRow, final int _nColumn, final double _defValue) {
		data = new double[_nRow][_nColumn];
		Equal(_defValue);
	}
	public Matrix(final int _nRow, final int _nColumn, final double[] _values) {
		data = new double[_nRow][_nColumn];
		Equal(_values);
	}
    Matrix(final Matrix _matrix) {
		;
	}

	public Matrix Equal(final double _d) {
		data = new double[1][1];
		data[0][0] = _d;
		return this;
	}
	public Matrix Equal(final double[] _d) {
		data = new double[_d.length][1];
		System.arraycopy(_d, 0, data, 0, _d.length);
		return this;
	}
	public Matrix Equal(final double[][] _d) {
		data = new double[_d.length][_d[0].length];
		System.arraycopy(_d, 0, data, 0, _d.length * _d[0].length);
		return this;
	}
	public Matrix Equal(final Vector _v) {
		data = new double[_v.data.length][1];
		System.arraycopy(_v.data, 0, data, 0, _v.data.length);
		return this;
	}
	public Matrix Equal(final Matrix _m) {
		data = new double[_m.data.length][_m.data[0].length];
		System.arraycopy(_m.data, 0, data, 0, _m.data.length * _m.data[0].length);
		return this;
	}

	public Matrix Add(final double _d) {
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] += _d;
		return this;
	}
	public Matrix Add(final double[][] _d) throws Exception {
		if(data.length != _d.length || data[0].length != _d[0].length)
			throw new Exception();
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] += _d[i][j];
		return this;
	}
	public Matrix Add(final Matrix _m) throws Exception {
		if(data.length != _m.data.length || data[0].length != _m.data[0].length)
			throw new Exception();
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] += _m.data[i][j];
		return this;
	}

	public Matrix Substract(final double _d) {
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] += _d;
		return this;
	}
	public Matrix Substract(final double[][] _d) throws Exception {
		if(data.length != _d.length || data[0].length != _d[0].length)
			throw new Exception();
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] += _d[i][j];
		return this;
	}
	public Matrix Substract(final Matrix _m) throws Exception {
		if(data.length != _m.data.length || data[0].length != _m.data[0].length)
			throw new Exception();
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] += _m.data[i][j];
		return this;
	}

	public Matrix Multiply(final double _d) {
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] *= _d;
		return this;
	}

	public Matrix Divide(final double _d) {
		for(int i = 0; i < data.length; ++i)
			for(int j = 0; j < data[0].length; ++j)
				data[i][j] /= _d;
		return this;
	}
	

	public static Matrix MatProduct(final Matrix _matA, final Matrix _matB) {
		int M = _matA.rows();
		int N = _matB.cols();
		int K = _matA.cols();

		Matrix C = new Matrix(M, N, 0.0);

		double sum;
		
		for(int i = 0; i < M; i++)
			for(int j = 0; j < N; ++j) {
				sum  = 0;
				for(int k = 0; k < K; ++k)
					sum += _matA.data[i][k] * _matB.data[k][j];

				C.data[i][j] = sum;
			}
		return C;
	}
	public static Vector VecProduct(final Matrix _matA, final Vector _vecB) {
		int M = _matA.rows();
		int N = _matA.cols();

		Vector _vecC = new Vector( M );
		
		double sum;

		for(int i = 0; i < M; i++) {
			sum  = 0;
			for(int j = 0; j < N; ++j)
				sum += _matA.data[i][j] * _vecB.data[j];

			_vecC.data[i] = sum;
		}
		return _vecC;
	}

	public Matrix Transpose() {
		Matrix tmp  = new Matrix( rows(), cols() );
		for(int i = 0; i < rows(); ++i)
			for(int j = 0; j < cols(); ++j)
				tmp.data[i][j] = data[j][i];

		return tmp;
	}
	public static Matrix Transpose(final Vector _v) {
		return new Matrix(1, _v.dim(), _v.data());
	}

	/**
	 * matrix conjugate tranpose
	 */
	public Matrix TransposeH() {
		int rows = this.cols();
		int columns = this.rows();

		Matrix tmp = new Matrix( rows, columns );
		for(int i = 0; i < rows; ++i )
			for(int j = 0; j < columns; ++j )
				tmp.data[i][j] = -1.0; // conj(this.data[j][i]);

		return tmp;
	}

	/**
	 * matrix-matrix tranpose multiplication: A^T * B.
	 */
	public Matrix MultiplyTranspose(final Matrix _matB) {
		int rows = cols();
		int cols = _matB.cols();
		int K    = rows();

		Matrix tmp = new Matrix( rows, cols );
		for(int i = 0; i < rows; ++i)
			for(int j = 0; j < cols; ++j)
				for(int k = 0; k < K; ++k)
				   tmp.data[i][j] += data[k][i] * _matB.data[k][j];

		return tmp;
	}

	/**
	 * matrix-vector tranpose multiplication: A^T * b.
	 */
	public Vector MultiplyTranspose(final Vector _vecB) {
		assert( rows() == _vecB.dim() );

		int rows = rows();
		int cols = cols();

		Vector tmp = new Vector( cols );
		for(int i = 0; i < cols; ++i)
			for(int j = 0; j < rows; ++j)
				tmp.data[i] += data[j][i] * _vecB.data(j);

		return tmp;
	}

	/**
	 * matrix-matrix tranpose multiplication: A * B^T.
	 */
	public Matrix MultiplyTransposeB(final Matrix _matB) {
		assert( cols() == _matB.cols() );

		int rows = rows();
		int cols = _matB.rows();
		int K    = cols();

		Matrix tmp = new Matrix( rows, cols );
		for(int i = 0; i < rows; ++i)
			for(int j = 0; j < cols; ++j)
				for(int k = 0; k < K; ++k)
				   tmp.data[i][j] += data[i][k] * _matB.data(j,k);

		return tmp;
	}

	/**
	 * vector-vector tranpose multiplication: a * b^T.
	 */
	public static Matrix MultiplyVectorTranspose(final Vector _vecA, final Vector _vecB) {
		int rows = _vecA.dim();
		int cols = _vecB.dim();

		Matrix tmp = new Matrix( rows, cols );
		for(int i = 0; i < rows; ++i)
			for(int j = 0; j < cols; ++j)
				tmp.data[i][j] = _vecA.data(i) * _vecB.data(j);

		return tmp;
	}

	/*
	 * matrix-matrix tranpose multiplication: A^H * B.
	 * /
	template <typename T>
	TMatrix<TComplex<T> > trMult( const TMatrix<TComplex<T> >& _matA, const TMatrix<TComplex<T> >& _matB) {
		CASSERT( _matA.rows() == _matB.rows() )

		int rows    = _matA.cols();
		int columns = _matB.cols();
		int K       = _matA.rows();

		TMatrix<TComplex<T> > tmp( rows, columns );
		for(int i = 0; i < rows; ++i)
			for(int j = 0; j < columns; ++j)
				for(int k = 0; k < K; ++k)
				   tmp[i][j] += conj(_matA[k][i]) * _matB[k][j];

		return tmp;
	}
	/* matrix-matrix tranpose multiplication: A^H * B END */

	/*
	 * matrix-vector tranpose multiplication: A^H * b.
	 * /
	template <typename T>
	TVector<TComplex<T> > trMult( const TMatrix<TComplex<T> >& _matrix, const TVector<TComplex<T> >& _vector) {
		CASSERT( _matrix.rows() == _vector.dim() )

		int rows    = _matrix.rows();
		int columns = _matrix.cols();

		TVector<TComplex<T> > tmp( columns );
		for(int i = 0; i < columns; ++i)
			for(int j = 0; j < rows; ++j)
				tmp[i] += conj(_matrix[j][i]) * _vector[j];

		return tmp;
	}
	/* matrix-vector tranpose multiplication: A^H * b END */

	/*
	 * matrix-matrix tranpose multiplication: A * B^H.
	 * /
	template <typename T>
	TMatrix<TComplex<T> > multTr( const TMatrix<TComplex<T> >& _matA, const TMatrix<TComplex<T> >& _matB) {
		CASSERT( _matA.cols() == _matB.cols() )

		int rows    = _matA.rows();
		int columns = _matB.rows();
		int K       = _matA.cols();

		TMatrix<TComplex<T> > tmp( rows, columns );
		for(int i = 0; i < rows; ++i)
			for(int j = 0; j < columns; ++j)
				for(int k = 0; k < K; ++k)
				   tmp[i][j] += _matA[i][k] * conj(_matB[j][k]);

		return tmp;
	}

	/**
	 * vector-vector tranpose multiplication: a * b^H.
	 * /
	template <typename T>
	TMatrix<TComplex<T> > multTr( const TVector<TComplex<T> >& _vecA, const TVector<TComplex<T> >& _vecB) {
		int rows    = _vecA.dim();
		int columns = _vecB.dim();

		TMatrix<TComplex<T> > tmp( rows, columns );
		for(int i = 0; i < rows; ++i)
			for(int j = 0; j < columns; ++j)
				tmp[i][j] = _vecA[i] * conj(_vecB[j]);

		return tmp;
	}

	/*
	 * some tests on matrix
	 */
	public boolean isScalar() {
		return rows() == 1 && cols() == 1;
	}
	public boolean isVector() {
		return rows() == 1 || cols() == 1;
	}
	public boolean isSquare() {
		return rows() == cols();
	}
	public boolean isSymetric() {
		assert( isSquare() );

		for(int i = 0; i < rows(); ++i)
			for(int j = i + 1; j < cols(); ++j)
				if(Math.abs( data[i][j] - data[j][i] ) > 1e-9)
					return false;
		return true;
	}
	public boolean isAntisymetric() {
		assert( isSquare() );

		for(int i = 0; i < rows(); ++i)
			for(int j = i + 1; j < cols(); ++j)
				if(Math.abs( data[i][j] + data[j][i] ) > 1e-9)
					return false;
		return true;
	}
	public boolean isPositiveDefinite() {
		return false;
/*		assert( isSquare() );

		Vector x    = new Vector(_m.rows(), 1);
		Matrix test = new Matrix();

		return x * _m * x > 0;
*/	}

	/**
	 * Generate the identity matrix.
	 */
	public static Matrix eye(int _N, double _value) {
		Matrix tmp = new Matrix( _N, _N );
		for(int i = 0; i < _N; ++i)
			tmp.data[i][i] = _value;

		return tmp;
	}

	/**
	 * Get the diagonal entries of matrix.
	 */
	public Vector Diagonal() {
		int dim = rows();
		if( dim > cols() )
			dim = cols();

		Vector tmp = new Vector( dim );
		for(int i = 0; i < dim; ++i)
			tmp.data[i] = data[i][i];

		return tmp;
	}

	/**
	 * Generate the diagonal of matrix by given its diagonal elements.
	 */
	public static Matrix DiagMatrix(final Vector _v) {
		int N = _v.dim();

		Matrix tmp = new Matrix( N, N );
		for(int i = 0; i < N; ++i)
			tmp.data[i][i] = _v.data[i];

		return tmp;
	}

	/**
	 * CoMatrice
	 */
	public Matrix CoMatrix() {
		int dim = cols();

		Matrix Com = new Matrix(dim, dim);
		for(int i = 0; i < dim; ++i) {
			for(int j = 0; j < dim; ++j) {
				Matrix m2 = new Matrix(dim - 1, dim - 1);

				int m2_l = -1;
				for(int i2 = 0; i2 < dim; ++i2) {
					if(i2 != i) {
						++m2_l;
						int m2_c = -1;
						for(int j2 = 0; j2 < dim; ++j2)
							if(j2 != j)
								m2.data[m2_l][++m2_c] = data[i2][j2];
					}
				}

				if((i + j) % 2 == 0)
					Com.data[i][j] = - m2.Determinant();
				else
					Com.data[i][j] = m2.Determinant();
			}
		}
		return Com;
	}

	/**
	 * Determinant
	 */
	public double Determinant() {
		assert(rows() == cols());

		int    dim = rows();
		double det = 0.0;

		Matrix m2 = new Matrix(dim - 1, dim - 1);
		
		switch(dim) {
		case 1  :	det = data[0][0];
					break;
		case 2  :	det = data[0][0] * data[1][1] - data[1][0] * data[0][1];
					break;
		default :	for(int i = 0; i < dim; ++i) {
						int m2_r = -1;
						for(int k = 0; k < dim;++k) {
							if(k != i) {
								++m2_r;
								for(int j = 0; j < dim - 1; ++j)
									m2.data[m2_r][j] = data[k][j];
							}
						}
						if(i % 2 == 0) {
							det -= data[i][0] * m2.Determinant();
						} else {
							det += data[i][0] * m2.Determinant();
						}
					}
					break;
		}

		return det;
	}

	/*
	 *  Trace
	 */
	public double Trace() {
		assert(rows() == cols());

		double tr = 0.0;
		for(int i = 0; i < rows(); ++i)
			tr += data[i][i];

		return tr;
	}

	/**
	 * Inverse matrix
	 */
	protected static Vector lu_bksb(Matrix _m, Vector _idx, Vector _b) {
		int n = _m.rows(), ii = 0, ip;
		double sum;

		for(int i = 0; i < n; ++i) {
			ip     = (int) _idx.data[i];
			sum    = _b.data[ip];
			_b.data[ip] = _b.data[i];
			if(ii != 0)
				for(int j = ii; j < i-1; ++j) sum -= _m.data[i][j] * _b.data[j];
			else if(sum != 0) ii = i;
			_b.data[i] = sum;
		}
		for(int i = n-1; i > 0; --i) {
			sum = _b.data[i];
			for(int j = i; j < n; ++j) sum -= _m.data[i][j] * _b.data[j];
			_b.data[i] = sum / _m.data[i][i];
		}
		
		return _b;
	}
	protected static Matrix lu_dcmp(Matrix _m, Vector _idx, double d) {
		int n = _m.rows(), i, imax = 0, j, k;
		double big, dum, sum, temp;
		
		Vector v = new Vector(n);

		d = 1.0;
		for(i = 0; i < n; ++i) {
			big = 0.0;
			for(j = 0; j < n; ++j)
				if((temp = Math.abs(_m.data[i][j])) > big)
					big = temp;
			
				if (big == 0.0)
					return null;

			v.data[i] = 1.0 / big;
		}

		for(j = 0; j < n; ++j) {
			for(i = 0; i < j; ++i) {
				sum = _m.data[i][j];
				for(k = 0; k < i; ++k) 
					sum -= _m.data[i][k] * _m.data[k][j];

				_m.data[i][j] = sum;
			}
			big = 0.0;

			for(i = j; i < n; ++i) {
				sum = _m.data[i][j];
				for(k = 0; k < j; ++k)
					sum -= _m.data[i][k] * _m.data[k][j];

				_m.data[i][j] = sum;
				if( (dum = v.data[i] * Math.abs(sum)) >= big ) {
					big  = dum;
					imax = i;
				}
			}
			if (j != imax) {
				for(k = 0; k < n; ++k) {
					dum              = _m.data[imax][k];
					_m.data[imax][k] = _m.data[j][k];
					_m.data[j][k]    = dum;
				}
				d = - d;
				v.data[imax] = v.data[j];
			}
			_idx.data[j] = imax;
			if(_m.data[j][j] == 0.0)
				_m.data[j][j] = 1e-20;
			if(j != n - 1) {
				dum = 1.0 / (_m.data[j][j]);
				for(i = j; i < n; ++i) {
					_m.data[i][j] *= dum;
				}
			}
		}
		
		return null;
	}
	public Matrix Inverse() {
		assert( isSquare() );

		int    n = rows();
		double d = 0.0;
		Matrix mp = new Matrix(this), inv = new Matrix(rows(), cols());
		Vector col = new Vector(n);
		Vector idx= new Vector(n);

		lu_dcmp(mp, idx, d);
		for(int j = 0; j < n; ++j) {
			col.Equal(0.0); col.data[j] = 1.0;
			lu_bksb(mp, idx, col);
			for(int i = 0; i < n; ++i)
				inv.data[i][j] = col.data[i];
		}

		return inv;
	}
	public Matrix InverseDet() {
		Matrix inverse = new Matrix(this);

		if((rows() == 1) && (cols() == 1)){
			inverse = new Matrix(1, 1, 1.0 / data[0][0]);
		} else {
			double det = Determinant();
			if(det == 0.0)
				return null;

			inverse = CoMatrix().Transpose();

			for(int i = 0; i < cols(); ++i)
				for(int j = 0; j < rows(); ++j)
					inverse.data[i][j] /= det;
		}

		return inverse;
	}

	/**
	 * Triangulation
	 */
	public Matrix TriangulationCholesky() {
		assert( isSymetric() && isPositiveDefinite() );

		int dim = rows();
		Matrix chol = new Matrix( dim, dim );

		double sum = 0.0;
		for(int i = 0; i < dim; ++i) {
			for(int k = 0; k < i; ++k)
				sum += (chol.data[i][k] * chol.data[i][k]);

			chol.data[i][i] = Math.sqrt(data[i][i] - sum);

			if(chol.data[i][i] == 0.0)
				return null;

			sum = 0.0;
			for(int j = i + 1; j < dim; ++j) {
				for(int k = 0; k < i; ++k)
					sum = sum + chol.data[i][k] * chol.data[j][k];

				chol.data[j][i] = (data[i][j] - sum) / chol.data[i][i];
				sum = 0.0;
			}
		}
		return chol;
	}

	/**
	 * Compute Frobenius norm of matrix.
	 */
	double Norm() {
		int m = rows();
		int n = cols();

		double sum = 0;
		for(int i = 0; i < m; ++i)
			for(int j = 0; j < n; ++j)
				sum += data(i,j) * data(i,j);

		return Math.sqrt(sum);
	}
	/*
	double norm( const TMatrix<TComplex<T> >& _matrix ) {
		int m = _matrix.rows();
		int n = _matrix.cols();

		T sum = 0;
		for(int i = 0; i < m; ++i)
			for(int j = 0; j < n; ++j)
				sum += norm( _matrix(i,j) );

		return sqrt(sum);
	}

	/**
	 * Matrix's column vectors sum.
	 */
	public Vector VectorSum() {
		int m = rows();
		int n = cols();
		Vector sum = new Vector(n);
		double s = 0.0;
		
		for(int j = 0; j < n; ++j) {
			for(int i = 0; i < m; ++i)
				s += data[i][j];
			sum.data[j] = s;
		}

		return sum;
	}

	/**
	 * Minimum of matrix's column vecotrs.
	 */
	public Vector VectorMin() {
		int m = rows();
		int n = cols();
		Vector min = new Vector(n);

		for(int j = 0; j < n; ++j) {
			double tmp = data[0][j];
			for(int i = 1; i < m; ++i)
				if( tmp > data[i][j] )
					tmp = data[i][j];
			min.data[j] = tmp;
		}

		return min;
	}

	/**
	 * Maximum of matrix's column vectors.
	 */
	Vector VectorMax() {
		int m = rows();
		int n = cols();
		Vector max = new Vector(n);

		for(int j = 0; j < n; ++j) {
			double tmp = data[0][j];
			for(int i = 1; i < m; ++i)
				if(tmp < data[i][j])
					tmp = data[i][j];
			max.data[j] = tmp;
		}

		return max;
	}

	/**
	 * Matrix's column vectors mean.
	 */
	public Vector VectorMean() {
		return VectorSum().Divide(rows());
	}

	/*
	 * Convert real matrix to complex matrix.
	 * /
	public Matrix<TComplex<T> > complexMatrix( const TMatrix<T>& _rA ) {
		int rows    = _rA.rows();
		int columns = _rA.cols();

		TMatrix<TComplex<T> > cA( rows, columns );
		for(int i = 0; i < rows; ++i )
			for(int j = 0; j < columns; ++j )
				cA[i][j] = _rA[i][j];

		return cA;
	}

	template <typename T>
	TMatrix<TComplex<T> > complexMatrix( const TMatrix<T>& _mR, const TMatrix<T>& _mI ) {
		int rows    = _mR.rows();
		int columns = _mR.cols();

		CASSERT( rows == _mI.rows() )
		CASSERT( columns == _mI.cols() )

		TMatrix<TComplex<T> > cA( rows, columns );
		for(int i = 0; i < rows; ++i )
			for(int j = 0; j < columns; ++j )
				cA[i][j] = TComplex<T>( _mR[i][j], _mI[i][j] );

		return cA;
	}
	/**/
	
	public Matrix FftShift() {
/*		Matrix m = new Matrix(rows(), cols());

		for(int i = 0; i < rows() / 2; ++i) {
			Vector tmp = getRow(i);
			setRow(getRow(i + rows()/2), i);
			setRow(tmp, i + rows()/2);
		}

		return m;
*/		return null;
	}


	/*
	 * Get magnitude of a complex matrix.
	 * /
	template <typename T>
	TMatrix<T> abs( const TMatrix<TComplex<T> >& _matrix ) {
		int m = _matrix.rows(),
			   n = _matrix.cols();
		TMatrix<T> tmp( m, n );

		for(int i = 0; i < m; ++i)
			for(int j = 0; j < n; ++j)
				tmp[i][j] = abs( _matrix[i][j] );

		return tmp;
	}


	/**
	 * Get angle of a complex matrix.
	 * /
	template <typename T>
	TMatrix<T> arg( const TMatrix<TComplex<T> >& _matrix ) {
		int m = _matrix.rows(),
			   n = _matrix.cols();
		TMatrix<T> tmp( m, n );

		for(int i = 0; i < m; ++i)
			for(int j = 0; j < n; ++j)
				tmp[i][j] = arg( _matrix[i][j] );

		return tmp;
	}

	/**
	 * Get real part of a complex matrix.
	 * /
	template <typename T>
	TMatrix<T> real( const TMatrix<TComplex<T> >& _matrix ) {
		int m = _matrix.rows(),
			   n = _matrix.cols();
		TMatrix<T> tmp( m, n );

		for(int i = 0; i < m; ++i)
			for(int j = 0; j < n; ++j)
				tmp[i][j] = _matrix[i][j].real();

		return tmp;
	}

	/*
	 * Get imaginary part of a complex matrix.
	 * /
	template <typename T>
	TMatrix<T> imag( const TMatrix<TComplex<T> >& _matrix ) {
		int m = _matrix.rows(),
			   n = _matrix.cols();
		TMatrix<T> tmp( m, n );

		for(int i = 0; i < m; ++i)
			for(int j = 0; j < n; ++j)
				tmp[i][j] = _matrix[i][j].imag();

		return tmp;
	}
	/**/

	public Map<Integer, Integer> ListExtrema() {
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for(int i = 0; i < rows(); ++i) {
			for(int j = 0; j < cols(); ++j) {
				if(i != 0 && j != 0) {
					if(data[i][j] > data[i-1][j] && data[i][j] > data[i+1][j] && data[i][j] > data[i][j-1] && data[i][j] > data[i][j+1])
						candidates.put( i, j );
					if(data[i][j] < data[i-1][j] && data[i][j] < data[i+1][j] && data[i][j] < data[i][j-1] && data[i][j] < data[i][j+1])
						candidates.put( i, j );
				} else if(i != 0) {
					if(data[i][j] > data[i-1][j] && data[i][j] > data[i+1][j] && data[i][j] > data[i][j+1])
						candidates.put( i, j );
					if(data[i][j] < data[i-1][j] && data[i][j] < data[i+1][j] && data[i][j] < data[i][j+1])
						candidates.put( i, j );
				} else if(j != 0) {
					if(data[i][j] > data[i+1][j] && data[i][j] > data[i][j-1] && data[i][j] > data[i][j+1])
						candidates.put( i, j );
					if(data[i][j] < data[i+1][j] && data[i][j] < data[i][j-1] && data[i][j] < data[i][j+1])
						candidates.put( i, j );
				}
			}
		}

		return candidates;
	}
	public Map<Integer, Integer> ListMaxima() {
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for(int i = 0; i < rows(); ++i) {
			for(int j = 0; j < cols(); ++j) {
				if(i != 0 && j != 0) {
					if(data[i][j] > data[i-1][j] && data[i][j] > data[i+1][j] && data[i][j] > data[i][j-1] && data[i][j] > data[i][j+1])
						candidates.put( i, j );
				} else if(i != 0) {
					if(data[i][j] > data[i-1][j] && data[i][j] > data[i+1][j] && data[i][j] > data[i][j+1])
						candidates.put( i, j );
				} else if(j != 0) {
					if(data[i][j] > data[i+1][j] && data[i][j] > data[i][j-1] && data[i][j] > data[i][j+1])
						candidates.put( i, j );
				}
			}
		}

		return candidates;
	}
	public Map<Integer, Integer> ListMinima() {
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for(int i = 0; i < rows(); ++i) {
			for(int j = 0; j < cols(); ++j) {
				if(i != 0 && j != 0) {
					if(data[i][j] < data[i-1][j] && data[i][j] < data[i+1][j] && data[i][j] < data[i][j-1] && data[i][j] < data[i][j+1])
						candidates.put( i, j );
				} else if(i != 0) {
					if(data[i][j] < data[i-1][j] && data[i][j] < data[i+1][j] && data[i][j] < data[i][j+1])
						candidates.put( i, j );
				} else if(j != 0) {
					if(data[i][j] < data[i+1][j] && data[i][j] < data[i][j-1] && data[i][j] < data[i][j+1])
						candidates.put( i, j );
				}
			}
		}

		return candidates;
	}

	public Map<Integer, Integer> ListMaximaAbove(final double _threshold, final int _jump) {
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for(int i = 1; i < rows() - 1; ++i) {
			for(int j = 1; j < cols() - 1; ++j) {
				if(data[i][j] > data[i-1][j] && data[i][j] > data[i+1][j] && data[i][j] > data[i][j-1] && data[i][j] > data[i][j+1] && data[i][j] > _threshold)
					candidates.put( i, j );
			}
		}

		return candidates;
	}

	public Map<Integer, Integer> ListMinimaBelow(final double _threshold, final int _jump) {
		Map<Integer, Integer> candidates = new HashMap<Integer, Integer>();

		for(int i = 0; i < rows(); ++i) {
			for(int j = 0; j < cols(); ++j) {
				if(i != 0 && j != 0) {
					if(data[i][j] < data[i-1][j] && data[i][j] < data[i+1][j] && data[i][j] < data[i][j-1] && data[i][j] < data[i][j+1] && data[i][j] < _threshold)
						candidates.put( i, j );
				} else if(i != 0) {
					if(data[i][j] < data[i-1][j] && data[i][j] < data[i+1][j] && data[i][j] < data[i][j+1] && data[i][j] < _threshold)
						candidates.put( i, j );
				} else if(j != 0) {
					if(data[i][j] < data[i+1][j] && data[i][j] < data[i][j-1] && data[i][j] < data[i][j+1] && data[i][j] < _threshold)
						candidates.put( i, j );
				}
			}
		}

		return candidates;
	}

}
