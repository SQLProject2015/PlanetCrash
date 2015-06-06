package Database.Yago.Uploaders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHandler;

public abstract class AbstractUploader {
	public static final int BATCHSIZE=10000;
	
	protected DatabaseHandler dbh;
	
	protected AbstractUploader(DatabaseHandler dbh) {
		this.dbh=dbh;
	}
	
	public abstract void upload();
	
	protected void insertBatch(List<Object[]> batch, String table, String[] columns) {
		try {
			dbh.batchInsert(table, columns, batch);
			batch = new ArrayList<Object[]>();
		} catch (SQLException e) {
			for(Object[] arr : batch) { //insert individually
				StringBuilder sql = new StringBuilder();
				try { 
//					dbh.executeUpdate("INSERT INTO Person (Name, yearOfBirth, yearOfDeath) VALUES(\""
//							+arr[0]+"\",\""+arr[1]+"\",\""+arr[2]+"\");");
					
					//StringBuilder sql = new StringBuilder();
					sql.append("INSERT INTO "+table+"(");
					for(int i=0;i<columns.length;i++)
						sql.append(columns[i]+(i<columns.length-1?",":""));
					sql.append(") VALUES(");
					for(int i=0;i<columns.length;i++)
						sql.append((arr[i]==null?"NULL":"\""+arr[i]+"\"")+(i<columns.length-1?",":""));
					sql.append(");");
					
					dbh.executeUpdate(sql.toString());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//System.out.println("Failed: "+arr[0]+","+arr[1]+","+arr[2]);
					e1.printStackTrace();
				}
			}
		}
		batch = new ArrayList<Object[]>();
	}
}
