package PlanetCrash.db.Updates;
import java.sql.ResultSet;
import java.sql.SQLException;

import PlanetCrash.core.Exceptions.NotFoundException;
import PlanetCrash.core.Game.GameUtils;
import PlanetCrash.core.config.Config;
import PlanetCrash.db.DatabaseHandler;
import PlanetCrash.parsing.entities.entity_country;


public class ManualUpdater {
	static Config conf = new Config();
	
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
		int idCaptitalCity;
		int idContinent;
		int idCurrency;
		int idLanguage;
		int idCountry;
		
		// get capital id from DB
		try{
			idCaptitalCity = getIdFromDB("City", "idCity", capital, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("City", new String[]{"Name", "isManual"}, new Object[]{capital, 1});
			idCaptitalCity = getIdFromDB("City", "idCity", capital, dbh);
		}
		

		// get continent id from DB
		try{
			idContinent = getIdFromDB("Continent", "idContinent", continent, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("Continent", new String[]{"Name", "isManual"}, new Object[]{continent, 1});
			idContinent = getIdFromDB("Continent", "idContinent", continent, dbh);
		}
		
		// get currency id from DB
		try{
			idCurrency = getIdFromDB("Currency", "idCurrency", currency, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("Currency", new String[]{"Name", "isManual"}, new Object[]{currency, 1});
			idCurrency = getIdFromDB("Currency", "idCurrency", currency, dbh);
		}
		
		// get language id from DB
		try{
		idLanguage = getIdFromDB("Language", "idLanguage", language, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("Language", new String[]{"Name", "isManual"}, new Object[]{language, 1});
			idLanguage = getIdFromDB("Language", "idLanguage", language, dbh);
		}

		dbh.singleInsert(String.format("%s.Country",conf.get_db_name()), new String[]{"`Name`","`idContinent`","`idCurrency`","`idLanguage`","`idCapital`","`PopulationSize`","`isManual`"},
				new Object[]{name, idContinent, idCurrency, idLanguage,idCaptitalCity, population_size,1});
		
		idCountry = getIdFromDB("Country", "idCountry", name, dbh);
		
		dbh.singleUpdate("City",new String[]{"idCountry"},new Integer[]{idCountry}, new String[]{"idCity"},new Integer[]{idCaptitalCity});

		
		return true;
	}
	
	public static boolean update_country(String name, String continent, String currency, String language, String capital, int population_size, DatabaseHandler dbh) throws SQLException, NotFoundException{
//		// get continent id from DB
//		int idContinent = getIdFromDB("Continent", "idContinent", continent, dbh);
//		
//		// get currency id from DB
//		int idCurrency = getIdFromDB("Currency", "idCurrency", currency, dbh);
//		
//		// get language id from DB
//		int idLanguage = getIdFromDB("Language", "idLanguage", language, dbh);
//		
//		// get capital id from DB
//		int idCaptitalCity = getIdFromDB("City", "idCity", capital, dbh);
//		

//		
////		String query = String.format("UPDATE %s.Country SET idContinent=%d,idCurrency=%d,"
////									+ "idLanguage=%d, idCapital=%d, PopulationSize=%d, isManual=1 WHERE idCountry=%d",
////									conf.get_db_name(),idContinent,idCurrency,idLanguage,idCaptitalCity,population_size,idCountry);
////		dbh.executeUpdate(query);
//		dbh.singleUpdate(conf.get_db_name()+".Country", 
//				new String[]{"idContinent","idCurrency","idLanguage","idCapital","PopulationSize","isManual"},
//				new Object[]{idContinent,idCurrency,idLanguage,idCaptitalCity,population_size,1},
//				new String[]{"idCountry"},
//				new Object[]{idCountry});
		
		int idCaptitalCity;
		int idContinent;
		int idCurrency;
		int idLanguage;
		
		//get country id from DB
		int idCountry = getIdFromDB("Country", "idCountry", name, dbh);
		
		//	String query = String.format("UPDATE %s.Country SET idContinent=%d,idCurrency=%d,"
		//	+ "idLanguage=%d, idCapital=%d, PopulationSize=%d, isManual=1 WHERE idCountry=%d",
		//	conf.get_db_name(),idContinent,idCurrency,idLanguage,idCaptitalCity,population_size,idCountry);
		//dbh.executeUpdate(query);
	
		// get capital id from DB
		try{
			idCaptitalCity = getIdFromDB("City", "idCity", capital, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("City", new String[]{"Name", "isManual"}, new Object[]{capital, 1});
			idCaptitalCity = getIdFromDB("City", "idCity", capital, dbh);
		}
		

		// get continent id from DB
		try{
			idContinent = getIdFromDB("Continent", "idContinent", continent, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("Continent", new String[]{"Name", "isManual"}, new Object[]{continent, 1});
			idContinent = getIdFromDB("Continent", "idContinent", continent, dbh);
		}
		
		// get currency id from DB
		try{
			idCurrency = getIdFromDB("Currency", "idCurrency", currency, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("Currency", new String[]{"Name", "isManual"}, new Object[]{currency, 1});
			idCurrency = getIdFromDB("Currency", "idCurrency", currency, dbh);
		}
		
		// get language id from DB
		try{
		idLanguage = getIdFromDB("Language", "idLanguage", language, dbh);
		}
		catch(Exception e){
			dbh.singleInsert("Language", new String[]{"Name", "isManual"}, new Object[]{language, 1});
			idLanguage = getIdFromDB("Language", "idLanguage", language, dbh);
		}
		
		dbh.singleUpdate(conf.get_db_name()+".Country", 
		new String[]{"idContinent","idCurrency","idLanguage","idCapital","PopulationSize","isManual"},
		new Object[]{idContinent,idCurrency,idLanguage,idCaptitalCity,population_size,1},
		new String[]{"idCountry"},
		new Object[]{idCountry});
		
		dbh.singleUpdate("City",new String[]{"idCountry"},new Integer[]{idCountry}, new String[]{"idCity"},new Integer[]{idCaptitalCity});
		
		return true;
	}
	
	/** 
	 * returns an Object[] array with details in the following order: continent, currency, language, capitalCity, populationSize
	 * @throws SQLException 
	 * @throws NotFoundException 
	 */
	public static entity_country get_country_details(String countryName, DatabaseHandler dbh) throws SQLException, NotFoundException{
		//get details from the db (by id's)
		
		String query = String.format("SELECT * FROM %s.Country WHERE Name=\"%s\";", conf.get_db_name(),countryName);
		ResultSet rs = dbh.executeQuery(query);
		int idContinent=0, idLanguage=0, idCurrency=0 , idCapital=0, populationSize=0;
		if (rs.first()) {
			idContinent = rs.getInt(3);
			idCurrency = rs.getInt(4);
			idLanguage = rs.getInt(5);
			idCapital = rs.getInt(6);
			populationSize = rs.getInt(7);
		}
		else{
			throw new NotFoundException(String.format("Country %s not found in DB", countryName));
		}
		
		// get continent name from DB
		String continent = getNameFromDB("Continent", "Name", idContinent, dbh);
		
		// get currency name from DB
		String currency = getNameFromDB("Currency", "Name", idCurrency, dbh);
		
		// get language name from DB
		String language = getNameFromDB("Language", "Name", idLanguage, dbh);
		
		// get capital name from DB
		String capitalCity = getNameFromDB("City", "Name", idCapital, dbh);
		
		return new entity_country(countryName,continent, currency, language, capitalCity, populationSize);
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
	private static String getNameFromDB(String tableName, String column, int valueToSearch, DatabaseHandler dbh){
		ResultSet rs;
		String retString = "";
		try {
			rs=dbh.executeFormatQuery(tableName, new String[]{column}, "WHERE id"+tableName+" ='"+valueToSearch+"'");
			if(rs.first()){
				retString = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retString;
		
	}

}
