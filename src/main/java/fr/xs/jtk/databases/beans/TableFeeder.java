package fr.xs.jtk.databases.beans;

public class TableFeeder {
	
	public interface Transformer {
		public String[] transform(String[] _values);
	}

	String      table;
	String      file;
	long        argCount;
	char        delim;
	boolean     skipFistLine;
	String      request;
	Transformer transformer;
	
	public TableFeeder(String _table, String _file, long _argCount, char _delim, boolean _skipFistLine, String _request) {
		table    = _table;
		file     = _file;
		argCount = _argCount;
		delim    = _delim;
		skipFistLine = _skipFistLine;
		request  = _request;
	}

	public String getTable() {
		return table;
	}
	public String getFile() {
		return file;
	}
	public long getArgCount() {
		return argCount;
	}
	public char getDelimiter() {
		return delim;
	}
	public String getRequest() {
		return request;
	}
	public boolean skipFistLine() {
		return skipFistLine;
	}
	
	public void setTransformer(Transformer _t) {
		transformer = _t;
	}
	public Transformer getTransformer() {
		return transformer;
	}
	
}
