package Database.Updates;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseHandler;

public class ManualUpdates {
	
	public static void deleteAllYagoData(DatabaseHandler dbh,String dbname){
		String tablesQuery = "SHOW TABLES FROM "+dbname+";";
		try {
			ResultSet rs = dbh.executeQuery(tablesQuery);
			while(rs.next()){
				String tableName = rs.getString(1);
				String delete = "DELETE FROM "+tableName+" WHERE "+tableName+".isManual=0;";
				int deleted = dbh.executeUpdate(delete);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
