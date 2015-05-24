package Database;

import java.sql.*;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Pools DB connections
 */
public class ConnectionPool {

	private static final String CONNPATH = "jdbc:mysql://localhost:3305/DbMysql14"; //"jdbc:mysql://localhost:3306/dbexample"; //TODO: change this
	private static final String USER = "DbMysql14"; //TODO: change this
	private static final String PASS = "DbMysql14"; //TODO: change this

	private static final int POOLMAX = 5;
	private static final int POOLMIN = 3;
	
	private AtomicInteger count;
	
	private Stack<Connection> cStack;
	
	public ConnectionPool() {
		this.cStack = new Stack<Connection>();
		this.count.set(0);
		
		//populate stack
		while(cStack.size()<POOLMIN)
			try {
				cStack.push(createConnection());
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private Connection createConnection() throws DatabaseException {
		Connection newConn;
		
		// loading the driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Unable to load the MySQL JDBC driver..\n"+e.getMessage());
		}
		System.out.println("Driver loaded successfully");

		// creating the connection
		System.out.print("Trying to connect... ");
		try {
			newConn = DriverManager.getConnection(CONNPATH,USER,PASS);
		} catch (SQLException e) {
			throw new DatabaseException("Unable to connect - " + e.getMessage());
		}
		System.out.println("Connected!");
		return newConn;
	}
	
	public synchronized Connection getConnection() {
		return null;
	}
	
	public synchronized void disposeConnection(Connection connection) {
		//TODO: return connection to pool
	}
	
}
