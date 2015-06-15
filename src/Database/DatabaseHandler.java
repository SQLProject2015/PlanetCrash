package Database;

import java.sql.*;
import java.util.List;

public class DatabaseHandler {

	ConnectionPool cPool;
	Connection conn;

	private static final String CONNPATH = "jdbc:mysql://host_addr:port/schema_name"; //"jdbc:mysql://localhost:3306/dbexample"; //TODO: change this
	private static final String USER = ""; //TODO: change this
	private static final String PASS = ""; //TODO: change this

	public DatabaseHandler(ConnectionPool connPool) {
		this.cPool=connPool;

		//Try and open connection
		try{
			//while connection pool is active and we can't get a connection, continue trying
			while((!cPool.isDestroyed())&&(this.conn=cPool.getConnection())==null) 
				Thread.sleep(1500);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param query
	 * @return ResultSet of the query
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		Statement stmnt = conn.createStatement();
		ResultSet rs = stmnt.executeQuery(query);
		return rs;
	}

	/**
	 * 
	 * @param table table name to search
	 * @param columns column names to search (or empty to search all)
	 * @param condition condition to search (e.g "WHERE NAME = \"JOHN\"") (or null/empty for nothing)
	 * @return result set for your query
	 * @throws SQLException 
	 */
	public ResultSet executeFormatQuery(String table, String[] columns, String condition) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		if(columns.length==0 || columns==null)
			sql.append("*");
		else {
			sql.append("(");
			for(int i=0;i<columns.length;i++)
				sql.append(columns[i]+(i<columns.length-1?",":""));
			sql.append(")");
		}
		
		sql.append("FROM "+table+(condition==null?"":" "+condition)+";");
		
		return executeQuery(sql.toString());
	}
	
	/**
	 * 
	 * @param update
	 * @return number of rows affected by update
	 * @throws SQLException
	 */
//	public int executeUpdate(String update) throws SQLException {
//		Statement stmnt = conn.createStatement();
//		return stmnt.executeUpdate(update);
//	}

	/**
	 * 
	 * @param table name of table to insert into
	 * @param columns names of columns corresponding with the batch arrays
	 * @param batch arrays of values to insert into the columns
	 * @return array of ints indicating rows affected
	 * @throws SQLException 
	 */
	public int[] batchInsert(String table, String[] columns, List<Object[]> batch) throws SQLException {
		//Create SQL statement
		String sql = "INSERT INTO "+table+" (";
		for(int i=0;i<columns.length;i++) 
			sql+=columns[i]+(i<columns.length-1?",":"");
		sql+=") VALUES(";
		for(int i=0;i<columns.length;i++)
			sql+="?"+(i<columns.length-1?",":"");
		sql+=");";
		//System.out.println(sql);
		//set auto-commit to false
		conn.setAutoCommit(false);
		
		//Create prepared statement
		PreparedStatement pstmnt = conn.prepareStatement(sql);

		//set the variables
		for(Object[] b: batch) {
			if (b.length!=columns.length) {
				System.out.println("Wrong value array length!");
				continue;
			}
			for(int i=0;i<columns.length;i++) {
				if(b[i]==null)
					pstmnt.setNull(i+1, java.sql.Types.NULL);
				else if(b[i] instanceof String)
					pstmnt.setString(i+1, (String)b[i]);
				else if(b[i] instanceof Integer)
					pstmnt.setInt(i+1, (Integer)b[i]);
			}
			pstmnt.addBatch(); //add to batch
		}
		
		int[] count = pstmnt.executeBatch(); //TODO: REROLL TRANSACTION IF FAILED
		conn.commit();
		conn.setAutoCommit(true);
		return count;
	}

	public void singleUpdate(String table, String[] columns, Object[] values) throws SQLException {
		genericFormatUpdate("UPDATE", table, columns, values);
	}
	
	public void singleDelete(String table, String[] columns, Object[] values) throws SQLException {
		genericFormatUpdate("DELETE FROM", table, columns, values);
	}
	
	public void singleInsert(String table, String[] columns, Object[] values) throws SQLException {
		genericFormatUpdate("INSERT INTO", table, columns, values);
	}
	
	private void genericFormatUpdate(String command, String table, String[] columns, Object[] values) throws SQLException {
		if(columns.length!=values.length) {
			System.out.println("Mismatching colums-values");
			return;
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(command+" "+table+" (");
		for(int i=0;i<columns.length;i++)
			sql.append(columns[i]+(i<columns.length-1?",":""));
		sql.append(") VALUES(");
		for(int i=0;i<columns.length;i++) 
			sql.append("?"+(i<columns.length-1?",":""));
		sql.append(");");
		//System.out.println(sql.toString());
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		for (int i=0; i<values.length;i++)
			ps.setString(1+i, values[i].toString());
		ps.executeUpdate();
	}
	
	//	/*
	//	 * Opens a new connection to the database
	//	 */
	//	private Connection openConnection() throws DatabaseException {
	//		
	//		Connection newConn;
	//		
	//		// loading the driver
	//		try {
	//			Class.forName("com.mysql.jdbc.Driver");
	//		} catch (ClassNotFoundException e) {
	//			throw new DatabaseException("Unable to load the MySQL JDBC driver..\n"+e.getMessage());
	//		}
	//		System.out.println("Driver loaded successfully");
	//
	//		// creating the connection
	//		System.out.print("Trying to connect... ");
	//		try {
	//			newConn = DriverManager.getConnection(CONNPATH,USER,PASS);
	//		} catch (SQLException e) {
	//			throw new DatabaseException("Unable to connect - " + e.getMessage());
	//		}
	//		System.out.println("Connected!");
	//		return newConn;
	//	}


	public void close() throws SQLException {
		this.cPool.disposeConnection(conn);
	}
}
