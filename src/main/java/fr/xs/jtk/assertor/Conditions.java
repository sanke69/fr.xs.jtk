/**
 * 
 * @author Steve PECHBERTI
 *
 */
package fr.xs.jtk.assertor;

public class Conditions {

	public static void check(boolean _condition, String _error_msg, Object... _error_values) {
		if(!_condition) {
			System.out.println(_error_msg);
			new Exception();
		}
			
	}

}
