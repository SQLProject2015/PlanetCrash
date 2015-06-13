package Database.Updates;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.config;
import Database.DatabaseHandler;
import Exceptions.NotFoundException;


public class ManualUpdater {
	static config conf = new config();
	
	public static boolean add_Person(String name, int yearOfBirth, int yearOfDeath, String city, String profession, DatabaseHandler dbh) throws SQLException, NotFoundException{
		// get city id from DB
		int idCity = getIdFromDB("City", "idCity", city, dbh);
		
		// get profession id from DB
		int idProfession = getIdFromDB("Profession", "idProfession", profession, dbh);
		
		// insert to Person table
		dbh.singleInsert(String.format("%s.Person",conf.get_db_name()), new String[]{"`Name`","`yearOfBirth`", "`yearOfDeath`", "`idPlaceOfBirth`","`isManual`"},
				new Object[]{name, yearOfBirth, yearOfDeath, idCity,1});
		
		// get person id from DB
		int idPerson = getIdFromDB("Person", "idPerson", name, dbh);
		
		// insert to Person_Profession table
		dbh.singleInsert(String.format("%s.Person_Profession",conf.get_db_name()), new String[]{"`idPerson`","`idProfession`", "`isManual`"},
				new Object[]{idPerson, idProfession,1});
		
		return true;
	}
	
	public static boolean add_Country(String name, String continent, String currency, String language, String capital, int population_size, DatabaseHandler dbh) throws SQLException, NotFoundException{
		// get capital id from DB
		int idCaptitalCity = getIdFromDB("City", "idCity", capital, dbh);

		// get continent id from DB
		int idContinent = getIdFromDB("Continent", "idContinent", continent, dbh);
		
		// get currency id from DB
		int idCurrency = getIdFromDB("Currency", "idCurrency", currency, dbh);
		
		// get language id from DB
		int idLanguage = getIdFromDB("Language", "idLanguage", language, dbh);

		dbh.singleInsert(String.format("%s.Country",conf.get_db_name()), new String[]{"`Name`","`idContinent`","`idCurrency`","`idLanguage`","`idCapital`","`PopulationSize`","`isManual`"},
				new Object[]{name, idContinent, idCurrency, idLanguage,idCaptitalCity, population_size,1});
		
		return true;
	}
	
	public static boolean update_country(String name, String continent, String currency, String language, String capital, int population_size, DatabaseHandler dbh) throws SQLException, NotFoundException{
		// get capital id from DB
		int idCaptitalCity = getIdFromDB("City", "idCity", capital, dbh);

		// get continent id from DB
		int idContinent = getIdFromDB("Continent", "idContinent", continent, dbh);
		
		// get currency id from DB
		int idCurrency = getIdFromDB("Currency", "idCurrency", currency, dbh);
		
		// get language id from DB
		int idLanguage = getIdFromDB("Language", "idLanguage", language, dbh);

		dbh.singleInsert(String.format("%s.Country",conf.get_db_name()), new String[]{"`Name`","`idContinent`","`idCurrency`","`idLanguage`","`idCapital`","`PopulationSize`","`isManual`"},
				new Object[]{name, idContinent, idCurrency, idLanguage,idCaptitalCity, population_size,1});
		
		return true;
	}
	
	
	public static int getIdFromDB(String tableName, String column, String valueToSearch, DatabaseHandler dbh) throws NotFoundException, SQLException{
		ResultSet rs;
		int retId = 0;
		rs=dbh.executeFormatQuery(tableName, new String[]{column}, "WHERE Name = \""+valueToSearch+"\"");
		if(rs.first())
			retId = rs.getInt(1);
		else{
			throw new NotFoundException(String.format("'%s' was not found in the DB", valueToSearch));
		}
		return retId;
		
	}
	
}
