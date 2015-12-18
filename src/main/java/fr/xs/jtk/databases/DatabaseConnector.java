package fr.xs.jtk.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.xs.jtk.databases.beans.DatabaseAccountBean;

public class DatabaseConnector {
	static final private String sql_driver = "com.mysql.jdbc.Driver";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

    private Connection con;

    public DatabaseConnector() {
    	con = null;
    }
    public DatabaseConnector(DatabaseAccountBean _account) {
    	this();
    	connectTo(_account);
    }

	public boolean connectTo(DatabaseAccountBean _account) {
		try {
			Class.forName(sql_driver);
			con = DriverManager.getConnection("jdbc:mysql://" + _account.getHostname(), _account.getUsername(), _account.getPassword());
		} catch(ClassNotFoundException e) {
			return false;
		} catch(SQLException e) {
			System.err.println("OoO >>> SQL ERROR <<< OoO");
			e.printStackTrace();
			return false;
		}

		return true;
	}
    public boolean disconnect() {
        try {
			if (con != null && !con.isClosed()){
			    con.close();
		        return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;
    }

	public ResultSet query(String _request) {
		Statement stmt   = null;
		ResultSet result = null;
		
		try {
			stmt   = con.createStatement();
			result = stmt.executeQuery(_request);
//			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	public boolean update(String _request) throws SQLException {
		Statement stmt   = null;
		int       result = 0;
		
//		try {
			stmt   = con.createStatement();
			result = stmt.executeUpdate(_request);
			stmt.close();
//		} catch(SQLException e) {
//			e.printStackTrace();
//			return false;
//		}

		return true;
	}

    public int getLastID() throws SQLException {
        ResultSet r = query("SELECT LAST_INSERT_ID() AS id");
        r.next();
        return r.getInt("id");
    }
   
}
