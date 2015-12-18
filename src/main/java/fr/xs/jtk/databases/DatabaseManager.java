package fr.xs.jtk.databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IllegalFormatException;
import java.util.Iterator;

import fr.xs.jtk.databases.beans.ColumnBean;
import fr.xs.jtk.databases.beans.DataBean;
import fr.xs.jtk.databases.beans.DatabaseAccountBean;
import fr.xs.jtk.databases.beans.DatabaseBean;
import fr.xs.jtk.databases.beans.TableBean;
import fr.xs.jtk.databases.beans.TableFeeder;
import fr.xs.jtk.databases.enums.TableOption;
import fr.xs.jtk.format.csv.CsvReader;
import fr.xs.jtk.format.json.JSONArray;
import fr.xs.jtk.format.json.JSONObject;
import fr.xs.jtk.format.json.parser.JSONParser;
import fr.xs.jtk.format.json.parser.ParseException;
import fr.xs.jtk.tools.Debugger;

public class DatabaseManager {
	
	public enum Access {
		ALL("ALL PRIVILEGES"),	// as we saw previously, this would allow a MySQL user all access to a designated database (or if no database is selected, across the system)
		CREATE("CREATE"),		// allows them to create new tables or databases
		DROP("DROP"),			// allows them to them to delete tables or databases
		INSERT("INSERT"),		// allows them to insert rows into tables
		DELETE("DELETE"),		// allows them to delete rows from tables
		SELECT("SELECT"),		// allows them to use the Select command to read through databases
		UPDATE("UPDATE"),		// allow them to update table rows
		GRANT("GRANT OPTION"),	// allows them to grant or remove other users' privileges
		USAGE("USAGE");

		String mySQL;
		
		Access(String _mysql) {
			mySQL = _mysql;
		}

	}
	
	public static boolean isUserExist(DatabaseConnector _conn, DatabaseAccountBean _account) {
		// SELECT EXISTS (SELECT DISTINCT user FROM mysql.user WHERE user = "username") 
		String request = "SELECT user FROM mysql.user WHERE user = '" + _account.getUsername() + "';";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		return false;
	}

	public static boolean isDatabaseExist(DatabaseConnector _conn, DatabaseBean _database) {
		String request = "SHOW DATABASES LIKE '" + _database.getName() + "';";
		
		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		ResultSet res = _conn.query(request);
		
		try {
			return res.first();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isTableExist(DatabaseConnector _conn, TableBean _table) {
		String request = "SHOW TABLES LIKE '" + _table.getName() + "';";
		
		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		ResultSet res = _conn.query(request);
		
		if(res == null)
			return false;

		try {
			return res.first();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean createUser(DatabaseConnector _conn, DatabaseAccountBean _account) {
		String request = "CREATE USER '" + _account.getUsername() + "'@'%' IDENTIFIED BY '" + _account.getPassword() + "';";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}
	public static boolean deleteUser(DatabaseConnector _conn, DatabaseAccountBean _account) {
		String request = "FLUSH PRIVILEGES;";
		request += "REVOKE ALL PRIVILEGES, GRANT OPTION FROM '" + _account.getUsername() + "'@'%';";
		request += "DROP USER " + _account.getUsername() + "@'localhost';";
		request += "DROP USER " + _account.getUsername() + ";";
		request += "FLUSH PRIVILEGES;";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}

	public static boolean grantPermission(DatabaseConnector _conn, DatabaseAccountBean _user, DatabaseBean _db, Access _right, boolean _use_password) {
		String request = "GRANT " + _right.mySQL + " ON " + _db.getName() + ".* TO '" + _user.getUsername() + "'@'%'" + (_use_password ? " IDENTIFIED BY '" + _user.getPassword() + "';" : ";");

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { 
			if(!_conn.update(request))
				return false;
			request = "GRANT " + _right.mySQL + " ON " + _db.getName() + ".* TO '" + _user.getUsername() + "'@'localhost'" + (_use_password ? " IDENTIFIED BY '" + _user.getPassword() + "';" : ";");
			return _conn.update(request);
		} catch(Exception e) { return false; }
	}
	public static boolean revokePermission(DatabaseConnector _conn, DatabaseAccountBean _user, DatabaseBean _db, Access _right) {
		String request = "REVOKE " + _right.mySQL + " ON " + _db.getName() + ".* FROM '" + _user.getUsername() + "'@'%';"; // " IDENTIFIED BY '" + _user.getPassword() + "'";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}
	public static boolean updatePermission(DatabaseConnector _conn) {
		String request = "FLUSH PRIVILEGES;";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}
	
	

	public static boolean createDatabase(DatabaseConnector _conn, DatabaseBean _database, boolean _only_if_not_exist) {
		String request = "CREATE DATABASE " + (_only_if_not_exist ? "IF NOT EXISTS " : "") + _database.getName() + ";";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}

	public static boolean useDatabase(DatabaseConnector _conn, String _database_name) {
		String request = "USE " + _database_name + ";";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}
	public static boolean useDatabase(DatabaseConnector _conn, DatabaseBean _database) {
		return useDatabase(_conn, _database.getName());
	}

	public static boolean deleteDatabase(DatabaseConnector _conn, String _database_name) {
		String request = "DROP DATABASE IF EXISTS " + _database_name + ";";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}
	public static boolean deleteDatabase(DatabaseConnector _conn, DatabaseBean _database) {
		return useDatabase(_conn, _database.getName());
	}

	public static boolean createTable(DatabaseConnector _conn, TableBean _table, boolean _only_if_not_exist) {
		String request = "";

		request += "CREATE TABLE " + (_only_if_not_exist ? "IF NOT EXISTS " : "") + _table.getName() + " (" + "\n";

		for(ColumnBean col : _table.getColumns()) {
			request += col.getName() + " " + col.getDataType().asMySQL() + "," + "\n";
		}

		if(_table.getOption("key") != null)
			for(String key : _table.getOption("key"))
				request += TableOption.KEY.asMySQL() + " " + key + " (" + key + ")" + ',' + "\n";

		if(_table.getOption("primary key") != null)
			for(String primary : _table.getOption("primary key"))
				request += TableOption.PRIMARY_KEY.asMySQL() + " " + primary + " (" + primary + ")" + ',' + "\n";

		if(_table.getOption("unique key") != null)
			for(String unique : _table.getOption("unique key"))
				request += TableOption.UNIQUE_KEY.asMySQL() + " " + unique + " (" + unique + ")" + ',' + "\n";

		if(_table.getOption("foreign key") != null)
			for(String foreign : _table.getOption("foreign key"))
				request += TableOption.FOREIGN_KEY.asMySQL() + " " + foreign + ',' + "\n";

		request = request.substring(0, request.length() - 2) + "\n) ";

		for(TableOption opt : _table.getOptions().keySet()) {
			if(opt != TableOption.KEY && opt != TableOption.FOREIGN_KEY && opt != TableOption.PRIMARY_KEY && opt != TableOption.UNIQUE_KEY)
				for(String value : _table.getOption(opt.getName()))
					request += opt.asMySQL() + "=" + value + " ";
		}	
		request += ";";

		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}

	public static boolean deleteTable(DatabaseConnector _conn, TableBean _table, boolean _temporary) {
		String request = "";

		request += "DROP "
		         + (_temporary ? "TEMPORARY " : "")
				 + "TABLE IF EXISTS " + _table.getName() + ";";
	
		if(_conn == null) {
			System.out.println(request);
			return false;
		}

		try { return _conn.update(request); } catch(Exception e) { return false; }
	}

	public static boolean feedTable(DatabaseConnector _conn, DatabaseBean _db, TableBean _table, TableFeeder _feeder, InputStream _data, String _charset) {
		useDatabase(_conn, _db);

		try {
			CsvReader csv = new CsvReader(_data, _feeder.getDelimiter(), Charset.forName(_charset));
			if(_feeder.skipFistLine()) csv.skipLine();
			int nRecords = 0;

			while( csv.readRecord() ) {
				String[] values = new String[csv.getColumnCount()];
				for(int i = 0; i < csv.getColumnCount(); ++i)
					values[i] = csv.get(i).replaceAll(",", ".").replaceAll("'", "`");

				if(_feeder.getTransformer() != null)
					values = _feeder.getTransformer().transform(values);

				String request = null;
				try {
					request = String.format(_feeder.getRequest(), (Object[]) values).replaceAll(", ,", ", 0,").replaceAll(", ,", ", 0,");
					_conn.update(request);
					
				} catch(IllegalFormatException  e) {
					Debugger.error("Failed to parse:\n" + values.length + "\t" + _feeder.getArgCount() + "\t" + _feeder.getRequest());
				} catch(SQLException e) {
					if(!e.getMessage().contains("Duplicate entry"))
						Debugger.error("Failed to update: " + "[" + _table.getName() + "] " + e.getMessage() + "\n" + request);
					else
						Debugger.error(". -> " + request);
				}

				nRecords++;
				if(nRecords % 256 == 0)	System.out.print('.');
				if(nRecords % (128 * 256) == 0)	System.out.println(nRecords);
			};
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	
	public static long getRowCount(DatabaseConnector _conn, TableBean _table) {
		String query = "SELECT COUNT(*) FROM " + _table.getName() + ";";

		if(_conn == null) {
			System.out.println(query);
			return -1;
		}
		
		ResultSet result = _conn.query(query);

		ArrayList<String> res = new ArrayList<String>();
		try {
			if(result.next())
				return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return -1;
	}

	public static DatabaseBean loadModelFromJSON(Reader _reader) {
		JSONParser parser = new JSONParser();

		Collection<DatabaseBean> answer = new ArrayList<DatabaseBean>();

		try {
			Object obj = parser.parse(_reader);

			if(obj == null)
				return null;

			JSONObject   dbDef = (JSONObject) ((JSONObject) obj).get("DATABASE");
			String       dbName = dbDef.keySet().toArray()[0].toString();
			DatabaseBean dbBean = new DatabaseBean(dbName);

			JSONArray table = (JSONArray) ((JSONObject) dbDef.get(dbName)).get("TABLES");
			Iterator<JSONObject> iTable = table.iterator();
			while(iTable.hasNext()) {
				JSONObject tableDef  = iTable.next();
				String     tableName = tableDef.keySet().toArray()[0].toString();
				TableBean  tableBean = new TableBean(tableName, dbBean);

				JSONObject columns = (JSONObject) ((JSONObject) tableDef.get(tableName)).get("COLUMNS");
				Collection<String> col = columns.keySet();
				for(String s : col) {
					tableBean.addColumn(new ColumnBean(s, DataBean.newDataBeanFromString(columns.get(s).toString()), tableBean));
				}

				JSONObject options = (JSONObject) ((JSONObject) tableDef.get(tableName)).get("OPTIONS");
				Collection<String> opt = options.keySet();
				for(String o : opt) {
					String content = options.get(o).toString();
					
					if(content.contains(",")) {
						String values[] = content.split(",");
						
						for(String value : values)
							tableBean.addOption(TableOption.newTableOption(o), value.replaceFirst(" ", ""));
						
					} else {
						tableBean.addOption(TableOption.newTableOption(o), options.get(o).toString());
					}
				}

				JSONObject feeder = (JSONObject) ((JSONObject) tableDef.get(tableName)).get("POPULATE_FROM_FILE");
				if(feeder != null) {
					String  file    = (String)  feeder.get("file");
					long    nbArgs  = (long)    feeder.get("nb_column");
					char    delim   = (char)    (feeder.get("delimiter") != null ? ((String) feeder.get("delimiter")).charAt(0) : ',');
					boolean skip    = (boolean) (feeder.get("delimiter") != null ? (((String) feeder.get("skipFirstLine")).compareToIgnoreCase("true") == 0 ? true : false) : false);
					String  request = (String)  feeder.get("request");
					
					dbBean.addTableFeeder(new TableFeeder(tableName, file, nbArgs, delim, skip, request));
				}

			}
			
			return dbBean;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static DatabaseBean loadModelFromJSON(InputStream _stream) {
		return loadModelFromJSON(new InputStreamReader(_stream));
	}

	public static DatabaseBean loadModelFromJSON(String _path) {
		try {
			return loadModelFromJSON(new FileReader(_path));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Collection<DatabaseBean> loadModelsFromJSON(Reader _reader) {
		JSONParser parser = new JSONParser();

		Collection<DatabaseBean> answer = new ArrayList<DatabaseBean>();

		try {
			Object obj = parser.parse(_reader);

			if(obj == null)
				return null;

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray  databases  = (JSONArray) jsonObject.get("DATABASES");
			Iterator<JSONObject> iDatabase = databases.iterator();
			while(iDatabase.hasNext()) {
				JSONObject   dbDef  = iDatabase.next();
				String       dbName = dbDef.keySet().toArray()[0].toString();
				DatabaseBean dbBean = new DatabaseBean(dbName);

				JSONArray table = (JSONArray) ((JSONObject) dbDef.get(dbName)).get("TABLES");
				Iterator<JSONObject> iTable = table.iterator();
				while(iTable.hasNext()) {
					JSONObject tableDef  = iTable.next();
					String     tableName = tableDef.keySet().toArray()[0].toString();
					TableBean  tableBean = new TableBean(tableName, dbBean);

					JSONObject columns = (JSONObject) ((JSONObject) tableDef.get(tableName)).get("COLUMNS");
					Collection<String> col = columns.keySet();
					for(String s : col) {
						tableBean.addColumn(new ColumnBean(s, DataBean.newDataBeanFromString(columns.get(s).toString()), tableBean));
					}

					JSONObject options = (JSONObject) ((JSONObject) tableDef.get(tableName)).get("OPTIONS");
					Collection<String> opt = options.keySet();
					for(String o : opt) {
						String content = options.get(o).toString();
						
						if(content.contains(",")) {
							String values[] = content.split(",");
							
							for(String value : values)
								tableBean.addOption(TableOption.newTableOption(o), value.replaceFirst(" ", ""));
							
						} else {
							tableBean.addOption(TableOption.newTableOption(o), options.get(o).toString());
						}
					}

					JSONObject feeder = (JSONObject) ((JSONObject) tableDef.get(tableName)).get("POPULATE_FROM_FILE");
					if(feeder != null) {
						String  file    = (String)  feeder.get("file");
						long    nbArgs  = (long)    feeder.get("nb_column");
						char    delim   = (char)    (feeder.get("delimiter") != null ? ((String) feeder.get("delimiter")).charAt(0) : ',');
						boolean skip    = (boolean) (feeder.get("delimiter") != null ? (((String)feeder.get("skipFirstLine")).compareToIgnoreCase("true") == 0 ? true : false) : false);
						String  request = (String)  feeder.get("request");
						
						dbBean.addTableFeeder(new TableFeeder(tableName, file, nbArgs, delim, skip, request));
					}

				}

				answer.add(dbBean);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return answer;
	}

	public static Collection<DatabaseBean> loadModelsFromJSON(InputStream _stream) {
		return loadModelsFromJSON(new InputStreamReader(_stream));
	}

	public static Collection<DatabaseBean> loadModelsFromJSON(String _path) {
		try {
			return loadModelsFromJSON(new FileReader(_path));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void printDatabaseBean(DatabaseBean _bean) {
		System.out.println(" - Nom de la DB       : " + _bean.getName());
		System.out.println(" - Nombre de table(s) : " + _bean.getTables().size());

		for(TableBean table : _bean.getTables()) {
			System.out.println("  - Table : " + table.getName());
			for(ColumnBean col : table.getColumns())
				System.out.println("   - Colonne : " + String.format("%1$15s", col.getName())  + "\t" + col.getDataType().toString());
			for(TableOption opt : table.getOptions().keySet())
				for(String oVal : table.getOption(opt.getName()))
					System.out.println("   - Option  : " + String.format("%1$15s", opt.getName()) + "\t" + oVal);
		}

	}



	public static boolean create(DatabaseAccountBean _root, DatabaseAccountBean _db_user, DatabaseBean _database) {
		DatabaseConnector 	rootConn = new DatabaseConnector(),
							userConn = null;

		if( !rootConn.connectTo(_root) ) {
			System.err.println("FAILED");
			return false;
		}

		///
		///  C R E A T E   U S E R
		///
/* Useless with 'grant access' using password...
		if( !DatabaseManager.isUserExist(rootConn, user) ) {
			if( !DatabaseManager.createUser(rootConn, user) ) {
				return false;
			}
		}
*/

		///
		///  C R E A T E   D A T A B A S E
		///
		if( !createDatabase(rootConn, _database, true) ) {
			System.err.println("Error during creating database");
			return false;
		}


		///
		///  A N D   G R A N T   U S E R   A C C E S S
		///
//		if( !grantPermission(rootConn, _db_user, _database, Access.SELECT, true) ) {
		if( !grantPermission(rootConn, _db_user, _database, Access.ALL, true) ) {
			System.err.println("Error during granting access");
			return false;
		}

		///
		///  F L U S H   P R I V I L E G E
		///
		if( !updatePermission(rootConn) ) {
			System.err.println("Error during updating privilege");
			return false;
		}

		///
		///  C R E A T E   T A B L E S
		///
		userConn = new DatabaseConnector(_db_user);

		if( ! useDatabase(userConn, _database) ) {
			System.err.println("User not allowed to acces DB " + _database.getName());
			return false;
		}

		for(TableBean table : _database.getTables())
			if( !createTable(userConn, table, true) ) {
				System.err.println("Error during creating table " + table.getName());
				return false;
			}

		return false;
	}

}
