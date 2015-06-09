import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseHandler;


public class UserHandler {
	
	public static boolean validate_user(String username, String password, DatabaseHandler dbh) throws SQLException{
		ResultSet rs;
		rs=dbh.executeFormatQuery("Users", new String[]{"idUser"}, "WHERE Username = \""+username+"\" AND Password = \""+password+"\"");
		if(rs.first())
			return true;
		
		return false;
	}
	
	public static boolean add_new_user(String username, String password, DatabaseHandler dbh) throws SQLException{
		ResultSet rs;

		rs=dbh.executeFormatQuery("Users", new String[]{"idUser"}, "WHERE Username = \""+username+"\"");
		if(rs.first()){
			return false;
		}
		dbh.singleInsert("Users", new String[]{"Username", "Password"}, new String[]{username,password});
		return true;
	}
}
