package fr.xs.jtk.math.algorithms;

public class OptimizedMethod {

	private static final float SHIFT23 = 1 << 23;
	private static final float INV_SHIFT23 = 1.0f / SHIFT23;

	public static final float fastPow(float a, float b) {
		float x = Float.floatToRawIntBits(a);
		x *= INV_SHIFT23;
		x -= 127;
		float y = x - (x >= 0 ? (int) x : (int) x - 1);
		b *= x + (y - y * y) * 0.346607f;
		y = b - (b >= 0 ? (int) b : (int) b - 1);
		y = (y - y * y) * 0.33971f;
		return Float.intBitsToFloat((int) ((b + 127 - y) * SHIFT23));
	}

}
