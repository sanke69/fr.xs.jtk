package fr.xs.jtk.databases.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import fr.xs.jtk.databases.enums.DataOption;
import fr.xs.jtk.databases.enums.DataType;

public class DataBean {
	DataType               type;
	int                    high = 0, low = -1;
	Collection<DataOption> options;

	public static DataBean newDataBeanFromString(String _string) {
		DataBean data = new DataBean();

		StringTokenizer tokens = new StringTokenizer(_string.replaceAll("  ",  " "), " ");
		
		String aToken = tokens.nextToken();

		if(aToken.contains("(")) {
			String param = aToken.substring(aToken.indexOf("(") + 1, aToken.indexOf(")"));
			if(param.contains(",")) {
				data.high  = Integer.parseInt(param.substring(0, param.indexOf(",")));
				data.low   = Integer.parseInt(param.substring(param.indexOf(",") + 1, param.length()));				
			} else
				data.high  = Integer.parseInt(param);
			aToken = aToken.substring(0, aToken.indexOf("("));
		}

		for(DataType type : DataType.values())
			for(String tag : type.getTags())
				if(aToken.compareToIgnoreCase(tag) == 0)
					data.type = type;	

		while(tokens.hasMoreTokens()) {
			aToken = tokens.nextToken();
			
			if(aToken.compareToIgnoreCase("not") == 0 || aToken.compareToIgnoreCase("auto") == 0)
				aToken += " " + tokens.nextToken();

			if(aToken.compareToIgnoreCase("unsigned") == 0)
				data.options.add(DataOption.UNSIGNED);

			if(aToken.compareToIgnoreCase("not null") == 0)
				data.options.add(DataOption.NOT_NULL);

			if(aToken.compareToIgnoreCase("auto_increment") == 0)
				data.options.add(DataOption.AUTO_INCREMENT);
		}
		
		if(data.type == null)
			System.err.println("PROBLEM !!!");
		return data;
	}

	public DataBean() {
		type   = null;
		options = new ArrayList<DataOption>();
	}
	public DataBean(DataType _type) {
		this();
		type = _type;
	}
	public DataBean(DataType _type, DataOption... _options) {
		this(_type);
		for(DataOption opt : _options)
			options.add(opt);
	}

	public boolean canBeNull() {
		for(DataOption opt : options)
			if(opt == DataOption.NOT_NULL)
				return false;		
		return true;
	}
	public boolean autoIncrement() {
		for(DataOption opt : options)
			if(opt == DataOption.AUTO_INCREMENT)
				return true;	
		return false;
	}

	@Override
	public String toString() {
		String opt = " ";
		if(options != null)
			for(DataOption option : options)
				opt += option.toString() + " ";
		return type.getName() + opt + (high != 0 ? " (sz=" + high + ")" : "");
	}

	
	
/* Equivalence Type
	MySQL Type      Java Type
	----------------------------------------
	CHAR            String
	VARCHAR         String
	LONGVARCHAR     String
	NUMERIC         java.math.BigDecimal
	DECIMAL         java.math.BigDecimal
	BIT             boolean
	TINYINT         byte
	SMALLINT        short
	INTEGER         int
	BIGINT          long
	REAL            float
	FLOAT           double
	DOUBLE          double
	BINARY          byte []
	VARBINARY       byte []
	LONGVARBINARY   byte []
	DATE            java.sql.Date
	TIME            java.sql.Time
	TIMESTAMP       java.sql.Tiimestamp
*/
	public String asMySQL() {
		String opt = "";
		for(DataOption option : options)
			opt += option.asMySQL() + " ";
		switch(type) {
		case CHAR:		return "CHAR";
		case DATE:		return "DATE";
		case DATETIME:	return "DATETIME";
		case NUMBER:	if(low != -1)
							return "DECIMAL" + (high != 0 ? "(" + high + (low != -1 ? "," + low + ")" : ")") : "") + (opt.length() != 0 ? " " + opt : "");
						else
							return "INTEGER" + (high != 0 ? "(" + high + ")" : "") + (opt.length() != 0 ? " " + opt : "");
		case DOUBLE:	return "DOUBLE";
		case FLOAT:		return "DOUBLE";
		case INT:		return "INT" + (high != 0 ? "(" + high + ")" : "") + (opt.length() != 0 ? " " + opt : "");
		case LONG:		return "INT" + (high != 0 ? "(" + high + ")" : "") + (opt.length() != 0 ? " " + opt : "");
		case SHORT:		return "INT" + (high != 0 ? "(" + high + ")" : "") + (opt.length() != 0 ? " " + opt : "");
		case STRING:	return "VARCHAR" + (high != 0 ? "(" + high + ")" : "") + (opt.length() != 0 ? " " + opt : "");
		default:		return null;
		}
	}

}
