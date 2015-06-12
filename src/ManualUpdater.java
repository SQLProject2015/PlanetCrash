import java.sql.ResultSet;
import java.sql.SQLException;

import config.config;
import Database.DatabaseHandler;


public class ManualUpdater {
	static config conf = new config();
	
	public static boolean add_Person(String name, String yearOfBirth, String yearOfDeath, String city, DatabaseHandler dbh) throws SQLException{
		ResultSet rs;
		String idCity = "0";
		rs=dbh.executeFormatQuery("City", new String[]{"idCity"}, "WHERE Name = \""+city+"\"");
		if(rs.first())
			idCity = rs.getString(1);
		
		dbh.singleInsert(String.format("%s.Person",conf.get_db_name()), new String[]{"`Name`","`yearOfBirth`", "`yearOfDeath`", "`idPlaceOfBirth`"},
				new Object[]{name, yearOfBirth, yearOfDeath, idCity});
		
		return false;
	}
	
	public static boolean add_Country(String name, String continent, String currency, String language, String capital, int population_size, DatabaseHandler dbh) throws SQLException{
		ResultSet rs;
		String idCity = "0";
		rs=dbh.executeFormatQuery("City", new String[]{"idCity"}, "WHERE Name = \""+name+"\"");
		if(rs.first())
			idCity = rs.getString(1);
		
		//dbh.singleInsert("DbMysql14.Country", new String[]{"`Name`","`yearOfBirth`", "`yearOfDeath`", "`idPlaceOfBirth`"},
		//		new Object[]{name, yearOfBirth, yearOfDeath, idCity});
		
		return false;
	}
	
}