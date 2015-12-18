package fr.xs.jtk.math;

import java.util.Random;

import fr.xs.jtk.math.algorithms.OptimizedMethod;
import fr.xs.jtk.math.type.Vector2f;

/**
 * A few math methods that don't fit very well anywhere else.
 */
public class MathUtils extends OptimizedMethod {
	public static final float PI = (float) Math.PI;
	public static final float TWOPI = (float) (Math.PI * 2);
	public static final float INV_PI = 1f / PI;
	public static final float HALF_PI = PI / 2;
	public static final float QUARTER_PI = PI / 4;
	public static final float THREE_HALVES_PI = TWOPI - HALF_PI;

	public static boolean FAST_ABS = true;
	public static boolean FAST_FLOOR = true;
	public static boolean FAST_CEIL = true;
	public static boolean FAST_ROUND = true;
	public static boolean FAST_ATAN2 = true;
	public static boolean FAST_POW = true;
	public static int CONTACT_STACK_INIT_SIZE = 10;
	public static boolean SINCOS_LUT_ENABLED = true;

	/**
	 * Degrees to radians conversion factor
	 */
	public static final float DEG2RAD = PI / 180;

	/**
	 * Radians to degrees conversion factor
	 */
	public static final float RAD2DEG = 180 / PI;

	public static final float[] sinLUT = new float[MathUtilsSettings.SINCOS_LUT_LENGTH];

	static {
		for(int i = 0; i < MathUtilsSettings.SINCOS_LUT_LENGTH; i++) {
			sinLUT[i] = (float) Math.sin(i * MathUtilsSettings.SINCOS_LUT_PRECISION);
		}
	}

	public static final float sin(float x) {
		if(MathUtilsSettings.SINCOS_LUT_ENABLED) {
			return sinLUT(x);
		} else {
			return (float) StrictMath.sin(x);
		}
	}

	public static final float sinLUT(float x) {
		x %= TWOPI;

		if(x < 0) {
			x += TWOPI;
		}

		if(MathUtilsSettings.SINCOS_LUT_LERP) {

			x /= MathUtilsSettings.SINCOS_LUT_PRECISION;

			final int index = (int) x;

			if(index != 0) {
				x %= index;
			}

			// the next index is 0
			if(index == MathUtilsSettings.SINCOS_LUT_LENGTH - 1) {
				return ((1 - x) * sinLUT[index] + x * sinLUT[0]);
			} else {
				return ((1 - x) * sinLUT[index] + x * sinLUT[index + 1]);
			}

		} else {
			return sinLUT[MathUtils.round(x / MathUtilsSettings.SINCOS_LUT_PRECISION) % MathUtilsSettings.SINCOS_LUT_LENGTH];
		}
	}

	public static final float cos(float x) {
		if(MathUtilsSettings.SINCOS_LUT_ENABLED) {
			return sinLUT(HALF_PI - x);
		} else {
			return (float) StrictMath.cos(x);
		}
	}

	public static final float abs(final float x) {
		if(MathUtilsSettings.FAST_ABS) {
			return x > 0 ? x : -x;
		} else {
			return StrictMath.abs(x);
		}
	}

	public static final float fastAbs(final float x) {
		return x > 0 ? x : -x;
	}

	public static final int abs(int x) {
		int y = x >> 31;
		return (x ^ y) - y;
	}

	public static final int floor(final float x) {
		if(MathUtilsSettings.FAST_FLOOR) {
			return fastFloor(x);
		} else {
			return (int) StrictMath.floor(x);
		}
	}

	public static final int fastFloor(final float x) {
		int y = (int) x;
		if(x < y) {
			return y - 1;
		}
		return y;
	}

	public static final int ceil(final float x) {
		if(MathUtilsSettings.FAST_CEIL) {
			return fastCeil(x);
		} else {
			return (int) StrictMath.ceil(x);
		}
	}

	public static final int fastCeil(final float x) {
		int y = (int) x;
		if(x > y) {
			return y + 1;
		}
		return y;
	}

	public static final int round(final float x) {
		if(MathUtilsSettings.FAST_ROUND) {
			return floor(x + .5f);
		} else {
			return StrictMath.round(x);
		}
	}

	/**
	 * Rounds up the value to the nearest higher power^2 value.
	 * 
	 * @param x
	 * @return power^2 value
	 */
	public static final int ceilPowerOf2(int x) {
		int pow2 = 1;
		while(pow2 < x) {
			pow2 <<= 1;
		}
		return pow2;
	}

	public final static float max(final float a, final float b) {
		return a > b ? a : b;
	}

	public final static int max(final int a, final int b) {
		return a > b ? a : b;
	}

	public final static float min(final float a, final float b) {
		return a < b ? a : b;
	}

	public final static int min(final int a, final int b) {
		return a < b ? a : b;
	}

	public final static float map(final float val, final float fromMin, final float fromMax, final float toMin, final float toMax) {
		final float mult = (val - fromMin) / (fromMax - fromMin);
		final float res = toMin + mult * (toMax - toMin);
		return res;
	}

	/** Returns the closest value to 'a' that is in between 'low' and 'high' */
	public final static float clamp(final float a, final float low, final float high) {
		return max(low, min(a, high));
	}

	public final static Vector2f clamp(final Vector2f a, final Vector2f low, final Vector2f high) {
		final Vector2f min = new Vector2f();
		min.x = a.x < high.x ? a.x : high.x;
		min.y = a.y < high.y ? a.y : high.y;
		min.x = low.x > min.x ? low.x : min.x;
		min.y = low.y > min.y ? low.y : min.y;
		return min;
	}

	public final static void clampToOut(final Vector2f a, final Vector2f low, final Vector2f high, final Vector2f dest) {
		dest.x = a.x < high.x ? a.x : high.x;
		dest.y = a.y < high.y ? a.y : high.y;
		dest.x = low.x > dest.x ? low.x : dest.x;
		dest.y = low.y > dest.y ? low.y : dest.y;
	}

	/**
	 * Next Largest Power of 2: Given a binary integer value x, the next largest
	 * power of 2 can be computed by a SWAR algorithm that recursively "folds"
	 * the upper bits into the lower bits. This process yields a bit vector with
	 * the same most significant 1 as x, but all 1's below it. Adding 1 to that
	 * value yields the next largest power of 2.
	 */
	public final static int nextPowerOfTwo(int x) {
		x |= x >> 1;
		x |= x >> 2;
		x |= x >> 4;
		x |= x >> 8;
		x |= x >> 16;
		return x + 1;
	}

	public final static boolean isPowerOfTwo(final int x) {
		return x > 0 && (x & x - 1) == 0;
	}

	public static final float pow(float a, float b) {
		if(MathUtilsSettings.FAST_POW) {
			return fastPow(a, b);
		} else {
			return (float) StrictMath.pow(a, b);
		}
	}

	public static final float atan2(final float y, final float x) {
		if(MathUtilsSettings.FAST_ATAN2) {
			return fastAtan2(y, x);
		} else {
			return (float) StrictMath.atan2(y, x);
		}
	}

	public static final float fastAtan2(float y, float x) {
		if(x == 0.0f) {
			if(y > 0.0f)
				return HALF_PI;
			if(y == 0.0f)
				return 0.0f;
			return -HALF_PI;
		}
		float atan;
		final float z = y / x;
		if(abs(z) < 1.0f) {
			atan = z / (1.0f + 0.28f * z * z);
			if(x < 0.0f) {
				if(y < 0.0f)
					return atan - PI;
				return atan + PI;
			}
		} else {
			atan = HALF_PI - z / (z * z + 0.28f);
			if(y < 0.0f)
				return atan - PI;
		}
		return atan;
	}

	public static final float reduceAngle(float theta) {
		theta %= TWOPI;
		if(abs(theta) > PI) {
			theta = theta - TWOPI;
		}
		if(abs(theta) > HALF_PI) {
			theta = PI - theta;
		}
		return theta;
	}

	public static final float randomFloat(float argLow, float argHigh) {
		return (float) Math.random() * (argHigh - argLow) + argLow;
	}

	public static final float randomFloat(Random r, float argLow, float argHigh) {
		return r.nextFloat() * (argHigh - argLow) + argLow;
	}

	public static final float sqrt(float x) {
		return (float) StrictMath.sqrt(x);
	}

	public final static float distanceSquared(Vector2f v1, Vector2f v2) {
		float dx = (v1.x - v2.x);
		float dy = (v1.y - v2.y);
		return dx * dx + dy * dy;
	}

	public final static float distance(Vector2f v1, Vector2f v2) {
		return sqrt(distanceSquared(v1, v2));
	}
}