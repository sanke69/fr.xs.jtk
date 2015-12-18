package fr.xs.jtk.math.generic;

import static fr.xs.jtk.constant.Mathematics.pi;

public class Complex {
	
	public double re, im;

	public static Complex polar(final double _abs, final double _arg) {
		return new Complex(_abs * Math.cos(_arg), _abs * Math.sin(_arg));
	}
	public static Complex expi(final double _k) {
		return new Complex(Math.cos(_k), Math.sin(_k));
	}

	
	public Complex() { re = 0; im = 0; }
	
	public Complex(double _re, double _im) { re = _re; im = _im; }

	public Complex(Complex _c) { re = _c.re; im = _c.im; }

	public Complex Equal(final double _d) {
		re = _d;
		im = _d;
		
		return this;
	}
	public Complex Equal(final double _re, final double _im) {
		re = _re;
		im = _im;
		
		return this;
	}
	public Complex Equal(final Complex _d) {
		re = _d.re;
		im = _d.im;
		
		return this;
	}

	public Complex Add(final double _t) {
		re += _t;
		im += _t;
		
		return this;
	}
	public Complex Add(final double _re, final double _im) {
		re += _re;
		im += _im;
		
		return this;
	}
	public Complex Add(final Complex _d) {
		re += _d.re;
		im += _d.im;
		
		return this;
	}

	public Complex Substract(final double _t) {
		re -= _t;
		im -= _t;
		
		return this;
	}
	public Complex Substract(final double _re, final double _im) {
		re -= _re;
		im -= _im;
		
		return this;
	}
	public Complex Substract(final Complex _d) {
		re -= _d.re;
		im -= _d.im;
		
		return this;
	}

	public Complex Multiply(final double _t) {
		re *= _t;
		im *= _t;
		
		return this;
	}
	public Complex Multiply(final double _re, final double _im) {
		Complex tmp = new Complex(re, im);
		re = tmp.re * _re - tmp.im * _im;
		im = tmp.re * _im + tmp.im * _re;
		
		return this;
	}
	public Complex Multiply(final Complex _c) {
		Complex tmp = new Complex(re, im);
		re = tmp.re * _c.re - tmp.im * _c.im;
		im = tmp.re * _c.im + tmp.im * _c.re;
		
		return this;
	}

	public Complex Divide(final double _d) throws Exception {
		if(_d == 0) throw new Exception();
		re /= _d;
		im /= _d;
		
		return this;
	}
	public Complex Divide(final double _k_re, final double _k_im) throws Exception {
		if(_k_re == 0 || _k_im == 0) throw new Exception();
		re /= _k_re;
		im /= _k_im;
		
		return this;
	}
	public Complex Divide(final Complex _c) throws Exception {
		if(_c.re == 0 && _c.im == 0) throw new Exception();

		Complex tmp = new Complex(re, im);
		double Q  = _c.re*_c.re + _c.im*_c.im;
		re = (tmp.re * _c.re + tmp.im * _c.im) / Q;
		im = (tmp.im * _c.re - tmp.re * _c.im) / Q;
		return this;
	}
	

	public double real() {
		return re;
	}
	public double imag() {
		return im;
	}

	public double abs() {
		return Math.sqrt(re*re + im*im);
	}
	public double arg() {
		double a = Math.atan2(im, re);
		if(a < 0)
			a += 2.0 * pi;
		return a;
	}
	public Complex conj() {
		return new Complex(re, - im);
	}


	public boolean isEqual(double _d) {
		return (re == _d && im == 0.0) ? true : false;
	}
	public boolean isEqual(Complex _c) {
		return (re == _c.re && _c.im == _c.im) ? true : false;
	}

	public boolean isDifferent(double _d) {
		return (re != _d || im != 0.0) ? true : false;
	}
	public boolean isDifferent(Complex _c) {
		return (re != _c.re || _c.im != _c.im) ? true : false;
	}

	/*
	template<typename T>
	int transpose(TComplex<T>* _c, const unsigned int& _nx, const unsigned int& _ny, const bool& _asReal = false) {
		TComplex<T>* tmp = new TComplex<T>[_nx * _ny];

		for(unsigned int i = 0, j = 0; i < _nx*_ny; i++, j = (unsigned int) (i / _nx)) {
			if(!_asReal)
				tmp[i] = conj( _c[(i%_nx) * _ny + j] );
			else
				tmp[i] = _c[(i%_nx) * _ny + j];
		}

		memcpy(_c, tmp, _nx * _ny * sizeof(TComplex<T>));

		delete [] tmp;
		return 0;
	}
	*/

}
