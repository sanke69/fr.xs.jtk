package fr.xs.jtk.math;

public class MathUtilsSettings {

	  public static final float SINCOS_LUT_PRECISION = .00011f;
	  public static final int SINCOS_LUT_LENGTH = (int) Math.ceil(Math.PI * 2 / SINCOS_LUT_PRECISION);
	  /**
	   * Use if the table's precision is large (eg .006 or greater). Although it is more expensive, it
	   * greatly increases accuracy. Look in the MathUtils source for some test results on the accuracy
	   * and speed of lerp vs non lerp. Or, run the tests yourself in  SinCosTest.
	   */
	  public static boolean SINCOS_LUT_LERP = false;

	  public static boolean FAST_ABS = true;
	  public static boolean FAST_FLOOR = true;
	  public static boolean FAST_CEIL = true;
	  public static boolean FAST_ROUND = true;
	  public static boolean FAST_ATAN2 = true;
	  public static boolean FAST_POW = true;
	  public static int CONTACT_STACK_INIT_SIZE = 10;
	  public static boolean SINCOS_LUT_ENABLED = true;
}
