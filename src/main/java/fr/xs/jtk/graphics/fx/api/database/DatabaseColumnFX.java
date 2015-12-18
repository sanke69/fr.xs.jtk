package fr.xs.jtk.graphics.fx.api.database;

import java.lang.reflect.Method;

public class DatabaseColumnFX<T> {

	String  label;
	int     width;
	boolean fixedWidth = true;

    private Class<T> clazz;
    public  Method   method;
	
	public DatabaseColumnFX(Class<T> _clazz, String _label, int _width, boolean _fixed_width, Method _method) {
		clazz      = _clazz;
		label      = _label;
		width      = _width;
		fixedWidth = _fixed_width;
		method     = _method;
	}

}
