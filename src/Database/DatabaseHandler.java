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
	 * @param update
	 * @return number of rows affected by update
	 * @throws SQLException
	 */
	public int executeUpdate(String update) throws SQLException {
		Statement stmnt = conn.createStatement();
		return stmnt.executeUpdate(update);
	}

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

		//Create prepared statement
		PreparedStatement pstmnt = conn.prepareStatement(sql);

		//set auto-commit to false
		conn.setAutoCommit(false);

		//set the variables
		for(Object[] b: batch) {
			if (b.length!=columns.length) {
				System.out.println("Wrong value array length!");
				continue;
			}
			for(int i=0;i<columns.length;i++) {
				if(b[i] instanceof Integer)
					pstmnt.setInt(i, (Integer)b[i]);
				else if(b[i] instanceof String)
					pstmnt.setString(i, (String)b[i]);
			}
			pstmnt.addBatch(); //add to batch
		}
		
		int[] count = pstmnt.executeBatch();
		conn.setAutoCommit(true);
		return count;
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
