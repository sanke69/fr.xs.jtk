package fr.xs.jtk.math.generic;

import static fr.xs.jtk.constant.Mathematics.pi;

import java.util.HashSet;
import java.util.Set;

public class Vector {

	public enum LinearDirection {
		LEFT, RIGHT, CENTER, BOTH;
	}
	
	public enum PaddingMode {
			ZPD, // Zero-Padding
			PPD, // Periodized Extension
			SYM; // Symetric Extension
	}

	public enum SmoothingMethod {
		MovingAverageSymetric,
		MovingAverageForward,
		MovingAverageBackward,
		Gaussian, // nadaraya_watson
		LocalMaxima,
		LocalMinima;
	}

	public double[] data;

	public double[] data() { return data; }
	public double   data(int _id) { return data[_id]; }
	public int      dim()  { return data.length; }

	public Vector() {
		;
	}
	public Vector(final int _size) {
		data = new double[_size];
	}
	public Vector(final int _size, final double _value) {
		data = new double[_size];
		Equal(_value);
	}
	public Vector(final int _size, final double[] _values) {
		data = new double[_size];
		Equal(_values);
	}
	public Vector(final int _size, final Vector _vector) {
		;
	}

	public Vector Equal(final double _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] = _d;
		return this;
	}
	public Vector Equal(final double[] _d) {
		data = new double[_d.length];
		System.arraycopy(_d, 0, data, 0, _d.length);
		return this;
	}
	public Vector Equal(final Vector _v) {
		data = new double[_v.data.length];
		System.arraycopy(_v.data, 0, data, 0, _v.data.length);
		return this;
	}

	public Vector Add(final double _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] += _d;
		return this;
	}
	public Vector Add(final double[] _d) {
		int s = Math.max(data.length, _d.length);
		
		for(int i = 0; i < s; ++i)
			data[i] += _d[i];
		return this;
	}
	public Vector Add(final Vector _v) {
		int s = Math.max(data.length, _v.data.length);
		for(int i = 0; i < s; ++i)
			data[i] += _v.data[i];
		return this;
	}

	public Vector Substract(final double _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] -= _d;
		return this;
	}
	public Vector Substract(final double[] _d) {
		int s = Math.max(data.length, _d.length);
		
		for(int i = 0; i < s; ++i)
			data[i] -= _d[i];
		return this;
	}
	public Vector Substract(final Vector _v) {
		int s = Math.max(data.length, _v.data.length);
		for(int i = 0; i < s; ++i)
			data[i] -= _v.data[i];
		return this;
	}

	public Vector Multiply(final double _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] *= _d;
		return this;
	}
	public Vector Multiply(final double[] _d) {
		int s = Math.max(data.length, _d.length);
		
		for(int i = 0; i < s; ++i)
			data[i] *= _d[i];
		return this;
	}
	public Vector Multiply(final Vector _v) {
		int s = Math.max(data.length, _v.data.length);
		for(int i = 0; i < s; ++i)
			data[i] *= _v.data[i];
		return this;
	}

	public Vector Divide(final double _d) {
		for(int i = 0; i < data.length; ++i)
			data[i] /= _d;
		return this;
	}
	public Vector Divide(final double[] _d) {
		int s = Math.max(data.length, _d.length);
		
		for(int i = 0; i < s; ++i)
			data[i] /= _d[i];
		return this;
	}
	public Vector Divide(final Vector _v) {
		int s = Math.max(data.length, _v.data.length);
		for(int i = 0; i < s; ++i)
			data[i] /= _v.data[i];
		return this;
	}

	public double dotProduct(final Vector _v) {
		double sum = 0;
		
		for(int i = 0; i < data.length; ++i)
			sum += data[i] * _v.data[i];

		return sum;
	}
	/*
	public Complex dotProd( const TVector<TComplex<T> >& _v1, const TVector<TComplex<T> >& _v2 ) {
		CASSERT( _v1.dim() == _v2.dim() );

		TComplex<T> sum = 0;
		typename TVector<TComplex<T> >::const_iterator itr1 = _v1.begin();
		typename TVector<TComplex<T> >::const_iterator itr2 = _v2.begin();

		while( itr1 != _v1.end() )
			sum += (*itr1++) * conj(*itr2++);

		return sum;
	}
	*/

	public static Vector CosVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.cos(_v.data[i]);

		return tmp;
	}
	public static Vector SinVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.sin(_v.data[i]);

		return tmp;
	}
	public static Vector TanVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.tan(_v.data[i]);

		return tmp;
	}
	public static Vector AcosVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.acos(_v.data[i]);

		return tmp;
	}
	public static Vector AsinVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.asin(_v.data[i]);

		return tmp;
	}
	public static Vector AtanVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.atan(_v.data[i]);

		return tmp;
	}

	public static Vector ExpVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.exp(_v.data[i]);

		return tmp;
	}
	public static Vector LogVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.log(_v.data[i]);

		return tmp;
	}
	public static Vector Log10Vector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.log10(_v.data[i]);

		return tmp;
	}
	public static Vector SqrtVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.sqrt(_v.data[i]);

		return tmp;
	}
	public static Vector AbsVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.abs(_v.data[i]);

		return tmp;
	}
	public static Vector ArgVector(final Vector _v) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = -1; // TODO:: Math.arg(_v.data[i]);

		return tmp;
	}
	public static Vector PowVector(final Vector _v, final double _pow) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.pow(_v.data[i], _pow);

		return tmp;
	}
	public static Vector PowVector(final Vector _v, final Vector _pow) {
		Vector tmp = new Vector( _v.data.length );

		for(int i = 0; i < _v.data.length; ++i)
			tmp.data[i] = Math.pow(_v.data[i], _pow.data[i]);

		return tmp;
	}
	public static Vector PowVector(final double _v, final Vector _pow) {
		Vector tmp = new Vector( _pow.data.length );

		for(int i = 0; i < _pow.data.length; ++i)
			tmp.data[i] = Math.pow(_v, _pow.data[i]);

		return tmp;
	}
	
	/**
	 * Normal distribution with expectation "u" and variance "r".
	 * 

//		tmp = (tmp-_u)*(tmp-_u) / ( -2*_r*_r );
//		tmp = exp(tmp) / T( (std::sqrt(2*constant::pi)*_r) );

	 */
	public static Vector GaussVector(final Vector _x, final double _u, final double _r ) {
		double two_r_r = - 2 *_r *_r,
			   sq_2piR = Math.sqrt(2*pi) * _r;
		Vector T    = new Vector( _x.data.length );
		Vector TmU  = T.Substract(_u),
			   tmp = TmU.Multiply(TmU).Divide(two_r_r),
			   exp = ExpVector(tmp).Divide(sq_2piR);

		return exp;
	}

	public double getSum() {
		double sum = 0;

		for(int i = 0; i < data.length; ++i)
			sum += data[i];

		return sum;
	}

	public double getMin() {
		double min = data[0];

		for(int i = 1; i < data.length; ++i)
			if( min > data[i] )
				min = data[i];

		return min;
	}

	public double getMax() {
		double max = data[0];

		for(int i = 1; i < data.length; ++i)
			if( max < data[i] )
				max = data[i];

		return max;
	}

	public double getNorm() {
		double sum = 0;

		for(int i = 1; i < data.length; ++i)
			sum += data[i] * data[i];

		return Math.sqrt(sum);
	}
	/*
	public double norm( const TVector<TComplex<T> >& _vector ) {
		T sum = 0;
		typename TVector<TComplex<T> >::const_iterator itr = _vector.begin();

		while( itr != _vector.end() ) {
//			sum += std::norm(*itr++);
			sum += abs(*itr) * abs(*itr);
			itr++;
		}

		return T(std::sqrt(1.0*sum));
	}
	*/

	public Vector linspace(double _a, double _b, int _size) {
		if( _size < 1 )
			return new Vector();
		else if( _size == 1 )
			return new Vector(1, _a);
		else {
			double dx = (_b-_a) / (_size-1);

			Vector tmp = new Vector(_size);
			for(int i=0; i<_size; ++i)
				tmp.data[i] = _a + i*dx;

			return tmp;
		}
	}
	/*
	template <typename T>
	TVector<T> abs( const TVector< TComplex<T> >& _vector ) {
		TVector<T> tmp( _vector.dim() );
		typename TVector<T>::iterator itrL = tmp.begin();
		typename TVector< TComplex<T> >::const_iterator itrR = _vector.begin();

		while( itrL != tmp.end() )
			*itrL++ = abs(*itrR++);

		return tmp;
	}
	*/



	public Vector Reverse() {
		Vector tmp = new Vector( data.length );
		
		for(int i = 0; i < data.length; ++i)
			tmp.data[i] = data[data.length - 1 - i];

		return tmp;
	}
	public Vector Flip() {
		return this.Reverse();
	}

	public Vector Shift(int _shiftSize) {
		Vector tmp = new Vector(data.length);

		if( _shiftSize >= 0 ) {
			for(int i = 0; i < data.length - _shiftSize; ++i)
				tmp.data[i] = data[i + _shiftSize];
		} else {
			for(int i = 0; i < data.length - _shiftSize; ++i)	// TODO::
				tmp.data[i] = data[i + _shiftSize];
		}

		return tmp;
	}

	public Vector CircShift(int _shiftSize) {
		Vector tmp = new Vector(data.length);

		if( _shiftSize >= 0 ) {
			for(int i = 0; i < data.length - _shiftSize; ++i)
				tmp.data[i] = data[i + _shiftSize];
			for(int i = data.length - _shiftSize; i < data.length; ++i)
				tmp.data[i] = data[i - data.length - _shiftSize];
		} else {
			for(int i = 0; i < data.length - _shiftSize; ++i)	// TODO::
				tmp.data[i] = data[i + _shiftSize];
			for(int i = data.length - _shiftSize; i < data.length; ++i)
				tmp.data[i] = data[i - data.length - _shiftSize];
		}

		return tmp;
	}

	public Vector FftShift() {
		int shiftsize = data.length - data.length/2 - 1;
		return CircShift( shiftsize );
	}

	public Vector dyadUp(final int evenodd) {
		int    length = data.length;
		Vector tmp;

		if(evenodd%2 == 0 ){
			tmp = new Vector(2*length+1);
			for(int i = 0; i < length; ++i) {
				tmp.data[2*i] = 0;
				tmp.data[2*i+1] = data[i];
			}
			tmp.data[2*length] = 0;
		} else {
			tmp = new Vector(2*length-1);
			for(int i = 0; i < length-1; ++i) {
				tmp.data[2*i] = data[i];
				tmp.data[2*i+1] = 0;
			}
			tmp.data[2*length-2] = data[length-1];
		}

		return tmp;
	}

	public Vector dyadDown(final int _evenodd) {
		int length = data.length;
		Vector tmp;

		if( _evenodd%2 == 0 ) {
			tmp = new Vector( (length+1)/2 );
			for(int i = 0; i < tmp.data.length; ++i)
				tmp.data[i] = data[2*i];
		} else {
			tmp = new Vector( length/2 );
			for(int i = 0; i < tmp.data.length; ++i)
				tmp.data[i] = data[2*i+1];
		}

		return tmp;
	}
/*
	template <typename T>
	TVector<T> fftInterp( const TVector<T> &sn, int factor ) {
		int N = sn.size(),
			halfN = N/2,
			offset = (factor-1)*N;

		TVector< TComplex<T> > Sk = fft(sn);
		TVector< TComplex<T> > Xk(factor*N);

		for( int i=0; i<=halfN; ++i )
			Xk[i] = T(factor)*Sk[i];
		for( int i=halfN+1; i<N; ++i )
			Xk[offset+i] = T(factor)*Sk[i];

		return ifftc2r(Xk);
	}

	template <typename T>
	TVector<TComplex<T> > fftInterp( const TVector<TComplex<T> > &sn, int factor ) {
		int N = sn.size(),
			halfN = N/2,
			offset = (factor-1)*N;

		TVector< TComplex<T> > Sk = fft(sn);
		TVector< TComplex<T> > Xk(factor*N);

		for( int i=0; i<=halfN; ++i )
			Xk[i] = T(factor)*Sk[i];
		for( int i=halfN+1; i<N; ++i )
			Xk[offset+i] = T(factor)*Sk[i];

		return ifft(Xk);
	}
*/
	public Vector wkeep(int length, int first) {
		Vector tmp = new Vector(length);

		if( ( 0 < length ) && ( length <= data.length-first ) )
		{
			for( int i=0; i<length; ++i )
				tmp.data[i] = data[first+i];

			return tmp;
		}
		else
		{
			return tmp;
		}
	}

	public Vector wkeep(final int _length, final LinearDirection _direction) {
		int lv = data.length;
		Vector tmp = new Vector(_length);

		if( ( 0 <= _length ) && ( _length <= lv ) ) {
			switch(_direction) {
			case RIGHT :	for(int i = 0; i < _length; ++i)
								tmp.data[i] = data[lv - _length + i];
							break ;
			case LEFT :		for(int i = 0; i < _length; ++i)
								tmp.data[i] = data[i];
							break ;
			case CENTER :
			default :
							int first = (lv - _length) / 2;
							for(int i = 0; i < _length; ++i)
								tmp.data[i] = data[first + i];
							break ;
			};
			return tmp;
		} else {
			return tmp;
		}
	}

	public Vector wextend(final int _extLength, final LinearDirection _direction, final PaddingMode _mode) {
		if( _extLength >= 0 ) {
			Vector tmp = null;
			int lv = data.length;

			switch(_direction) {
			case RIGHT :	tmp = new Vector( lv + _extLength );
							for(int i = 0; i < lv; ++i)
								tmp.data[i] = data[i];

							switch(_mode) {
							case SYM :	for(int i = 0; i < _extLength; ++i)
											tmp.data[lv + i] = data[lv - 1 - i];
										break ;
							case PPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[lv + i] = data[i];
										break ;
							case ZPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[lv + i] = 0;
										break ;
							};
							break ;

			case LEFT :		tmp = new Vector( lv+_extLength );

							switch(_mode) {
							case SYM :	for(int i = 0; i < _extLength; ++i)
											tmp.data[i] = data[_extLength - 1 - i];
										break ;
							case PPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[i] = data[lv - _extLength + i];
										break ;
							case ZPD :	for(int i = 0; i < _extLength; ++i)
											tmp.data[i] = 0;
										break ;
							};
							
							for(int i = 0; i < lv; ++i)
								tmp.data[i + _extLength] = data[i];
							break ;

			case BOTH :		tmp = new Vector( lv + 2 * _extLength );
							for(int i = 0; i < lv; ++i)
								tmp.data[i + _extLength] = data[i];

							switch(_mode) {
							case SYM :	for(int i = 0; i < _extLength; ++i) {
											tmp.data[i] = data[_extLength-1-i];
											tmp.data[lv + _extLength + i] = data[lv-1-i];
										}
										break ;
							case PPD :	for(int i = 0; i < _extLength; ++i) {
											tmp.data[i] = data[lv-_extLength+i];
											tmp.data[lv + _extLength + i] = data[i];
										}
										break ;
							case ZPD :	for(int i = 0; i < _extLength; ++i) {
											tmp.data[i] = 0;
											tmp.data[lv + _extLength + i] = 0;
										}
										break ;
							};
							break ;
			default :		break;
			}
			return tmp;
		} else {
			return new Vector();
		}
	}

	public Vector Derive(final double _dt) {
		Vector d = new Vector( data.length - 1 );

		for(int i = 0; i < data.length; ++i)
			d.data[i] = (data[i + 1] - data[i]) / _dt;

		return d;
	}
	public Vector Sign() {
		Vector s = new Vector( data.length );

		for(int i = 0; i < data.length; ++i)
			s.data[i] = data[i] > 0 ? 1 : data[i] < 0 ? -1 : 0;

		return s;
	}

	public Vector Smooth(final SmoothingMethod _method, final int _degree) {
		Vector smoothed = new Vector( data.length );

		// pour GAUSSIAN method
		double G = 0.0, sumG = 0.0;
		// pour LOCAL_EXTREMA method
		Set<Integer> POI = new HashSet<Integer>();
		int xp = 0, xn = 0;
		double yp, yn;

		switch(_method) {
			case MovingAverageSymetric	:	for(int i = 0; i < _degree; ++i)
												smoothed.data[i] = data[i];
											for(int i = _degree; i < data.length - _degree; ++i)
												for(int j = 0; j < _degree; ++j)
													smoothed.data[i] += ( data[i-j] + data[i+j] ) / (2 * _degree + 2);
											for(int i = data.length - _degree; i < data.length; ++i)
												smoothed.data[i] = data[i];
											break;
			case MovingAverageForward	:	for(int i = 0; i < data.length - _degree; ++i)
												for(int j = 0; j < _degree; ++j)
													smoothed.data[i] += data[i+j] / (_degree + 1);
											for(int i = data.length - _degree; i < data.length; ++i)
												smoothed.data[i] = data[i];
											break;
			case MovingAverageBackward	:	for(int i = 0; i < _degree; ++i)
												smoothed.data[i] = data[i];
											for(int i = _degree; i < data.length; ++i)
												for(int j = 0; j < _degree; ++j)
													smoothed.data[i] += data[i-j] / (_degree + 1);
											break;
			case Gaussian				:	for(int i = 0; i < _degree; ++i)
												smoothed.data[i] = data[i];
											for(int i = _degree; i < data.length - _degree; ++i) {
												for(int j = 0; j < _degree; ++j) {
													G = Math.exp( - (double) j*j / (double) _degree );
													sumG += 2.0 * G;
													smoothed.data[i] += G * data[i-j]
													            +  G * data[i+j];
												}
												smoothed.data[i] /= sumG;
												sumG = 0.0;
											}
											for(int i = data.length - _degree; i < data.length; ++i)
												smoothed.data[i] = data[i];
											break;
			case LocalMaxima			:	POI = ListMaxima();
											yp  = data[0];
											for(Integer i : POI) {
												xn = i;
												yn = data[i];

												for(int j = xp; j < xn; ++j) {
													smoothed.data[j] = (j - xp) * (yn - yp) / (xn - xp) + yp;;
												}

												xp = xn;
												yp = yn;
											}
											xn = data.length;
											yn = data[data.length - 1];
											for(int j = xp; j < data.length; ++j) {
												smoothed.data[j] = (j - xp) * (yn - yp) / (xn - xp) + yp;;
											}
											break;
			case LocalMinima			:	break;
		};

		return smoothed;
	}
/*
	public static Vector phase(final Vector _in0, final Vector _in1) {
		Vector out = new Vector( _in0.data.length );
		t_complex  c0, c1;

		for(t_uint i = 0; i < _in0.dim(); ++i) {
			c0 = t_complex(_in0[i], 0);
			c1 = t_complex(_in1[i], 0);

			out[i] = arg( c1 / c0 );
			if(out[i] < 0.0f)
				out[i] += 2.0f * constant::pi;
		}

		return out;
	}
*
	Vector phase(const TVector<TComplex<T> >& _in0, const TVector<TComplex<T> >& _in1) {
		CASSERT(_in0.dim() == _in1.dim())

		TVector<T> out( _in0.dim() );

		for(t_uint i = 0; i < _in0.dim(); ++i) {
			out[i] = arg( _in1[i] / _in0[i] );
			if(out[i] < 0.0f)
				out[i] += constant::twopi;
			else if(out[i] > constant::twopi)
				out[i] -= constant::twopi;
		}

		return out;
	}
*/
	
	public Set<Integer>	ListExtrema() {
		Set<Integer> extrema = new HashSet<Integer>();

		Vector s = Derive(1.0).Sign();

		for(int i = 1; i < s.data.length; ++i)
			if( (s.data[i] - s.data[i - 1] == -2) /* ruptures franches */ ||  ( s.data[i - 1] > 0 && s.data[i] == 0 && data[i + 1] <= data[i] ) /* "plateau" */ ) {
				extrema.add(i);
			} else if( (s.data[i] - s.data[i - 1] == 2) /* ruptures franches */ ||  ( s.data[i - 1] < 0 && s.data[i] == 0 && data[i + 1] >= data[i] ) /* "plateau" */ ) {
				extrema.add(i);
			}

		return extrema;
	}
	public Set<Integer>	ListMaxima() {
		Set<Integer> maxima = new HashSet<Integer>();

		Vector s = Derive(1.0).Sign();

		for(int i = 1; i < s.data.length - 1; ++i)
			if( (s.data[i] - s.data[i - 1] == -2) /* ruptures franches */ ||  ( s.data[i - 1] > 0 && s.data[i] == 0 && data[i + 1] <= data[i] ) /* "plateau" */ )
				maxima.add(i);

		return maxima;
	}
	public Set<Integer>	ListMinima() {
		Set<Integer> minima = new HashSet<Integer>();

		Vector s = Derive(1.0).Sign();

		for(int i = 1; i < s.data.length - 1; ++i)
			if( (s.data[i] - s.data[i - 1] == 2) /* ruptures franches */ ||  ( s.data[i - 1] < 0 && s.data[i] == 0 && data[i + 1] >= data[i] ) /* "plateau" */ )
				minima.add(i);

		return minima;
	}

	public int getFirstAbove(final double _threshold, final int _offset) {
		for(int i = _offset; i < data.length - 1; ++i)
			if(data[i] >= _threshold) return i;
		return -1;
	}
	public int getLastAbove(final double _threshold, final int _offset) {
		int offset = (_offset == -1) ? data.length - 1 : _offset;

		for(int i = offset; i > 0; --i)
			if(data[i] >= _threshold)
				return i;
		return -1;
	}
	public Set<Integer>	list_maxima_above(final double _threshold, final int _jump) {
		Set<Integer> above = new HashSet<Integer>();

		Vector s = Derive(1.0).Sign();

		for(int i = 1; i < s.data.length - 1; ++i)
			if( ( ( s.data[i] - s.data[i - 1] == -2 ) /* ruptures franches */ ||  ( s.data[i - 1] > 0 && s.data[i] == 0 && data[i + 1] <= data[i] ) /* "plateau" */ )
					&& data[i] >= _threshold) {
				above.add(i);
				i += _jump;
			}

		return above;
	}

	public int first_below(final double _threshold, final int _offset) {
		for(int i = _offset; i < data.length - 1; ++i)
			if(data[i] <= _threshold) return i;
		return -1;
	}
	public int last_below(final double _threshold, final int _offset) {
		int offset = (_offset == -1) ? data.length - 1 : _offset;

		for(int i = offset; i > 0; --i)
			if(data[i] <= _threshold) return i;
		return -1;
	}
	public Set<Integer>	list_minima_below(final double _threshold, final int _jump) {
		Set<Integer> below = new HashSet<Integer>();

		Vector s = Derive(1.0).Sign();

		for(int i = 1; i < s.data.length - 1; ++i)
			if( (s.data[i] - s.data[i - 1] == 2) /* ruptures franches */ ||  ( s.data[i - 1] < 0 && s.data[i] == 0 && data[i + 1] >= data[i] ) /* "plateau" */
					&& data[i] <= _threshold) {
				below.add(i);
				i += _jump;
			}

		return below;
	}

}
