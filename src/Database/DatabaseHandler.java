package Database;

import java.sql.*;

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
