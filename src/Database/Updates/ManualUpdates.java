package Database.Updates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import config.config;
import entities.entity_country;
import entities.entity_person;
import Database.DatabaseHandler;
import Exceptions.NotFoundException;

public class ManualUpdates {
	public static HashMap<String, entity_country> countries_map_bck = new HashMap<String, entity_country>();
	public static HashMap<String, entity_person> persons_map_bck = new HashMap<String, entity_person>();
	
	
	private static void updateFromYago(DatabaseHandler dbh,config conf) throws SQLException{
		backupManualUpdates(dbh,conf.get_db_name());
		deleteAllYagoData(dbh,conf.get_db_name());
		Importer i = new Importer(dbh, conf);
		
	}
	private static void insertManualBackupData(DatabaseHandler dbh,String dbname){
		//insert manual updates about persons
		for(String personName:persons_map_bck.keySet()){
			entity_person person = persons_map_bck.get(personName);
			String existsQuery = String.format("SELECT * FROM %s.Country WHERE Name=\"%s\";", dbname,personName);
			ResultSet rs;
			try {
				rs = dbh.executeQuery(existsQuery);
				if(rs.first()){
					String updateQuery = "UPDATE "+dbname+".Person "
							+ "SET idPlaceOfBirth="+getIdFromDB("Person","idPlaceOfBirth",person.getPlaceOfBirth(),dbh)
							+ ", yearOfBirth="+person.getYearOfBirth()+ ", yearOfBirth="+person.getYearOfDeath()+
							", isManual=1"+"WHERE Person.Name = "+personName+";";
					rs.close();
					int update = dbh.executeUpdate(updateQuery);
				}
				else{
					String insertQuery = "INSERT INTO "+dbname+".Person "
							+ "VALUES ("+personName+","+person.getPlaceOfBirth()+","+person.getYearOfDeath()+
							"'"+getIdFromDB("Person","idPlaceOfBirth",person.getPlaceOfBirth(),dbh)+");";
					int insert = dbh.executeUpdate(insertQuery);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	public static int getIdFromDB(String tableName, String column, String valueToSearch, DatabaseHandler dbh) {
		ResultSet rs;
		int retId = 0;
		try {
			rs=dbh.executeFormatQuery(tableName, new String[]{column}, "WHERE Name = \""+valueToSearch+"\"");
			if(rs.first()){
				retId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	private static void backupManualUpdates(DatabaseHandler dbh,String dbname){
		/*backup person manual updates*/
		String personQuery = "SELECT * FROM "+dbname+".Person WHERE Person.isManual=1";
		try {
			ResultSet rs = dbh.executeQuery(personQuery);
			while(rs.next()){
				entity_person person = new entity_person();
				int idPerson = rs.getInt("idPerson");
				person.setYearOfBirth(rs.getInt("yearOfBirth"));
				person.setYearOfDeath(rs.getInt("yearOfDeath"));
				String name = rs.getString("Name");
				person.setName(name);
				person.setPlaceOfBirth(getNameFromDB("City", "Name", rs.getInt("idPlaceOfBirth"), dbh));
				String professionQuery = "SELECT Profession.Name "+
										"FROM "+dbname+".Person_Profession, "+dbname+".Profession"+
										" WHERE Profession.idProfession = Person_Profession.idProfession and"+
										" Person_Profession.idProfession ='"+idPerson+"';";
				rs.close();
				rs = dbh.executeQuery(professionQuery);
				while(rs.next()){
					person.addProfession(rs.getString("Name"));
				}
				persons_map_bck.put(name, person);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*backup countries manual updates*/
		String countryQuery = "SELECT * FROM "+dbname+".Country WHERE Country.isManual=1";
		try {
			ResultSet rs = dbh.executeQuery(countryQuery);
			while(rs.next()){
				entity_country country = new entity_country();
				String countryName = rs.getString("Name");
				country.setYagoName(countryName);
				country.setPopulation_size(rs.getInt("PopulationSize"));
				country.setCapital(getNameFromDB("City", "Name", rs.getInt("idCapital"), dbh));
				country.setContinent(getNameFromDB("Continent", "Name", rs.getInt("idContinent"), dbh));
				country.setCurrency(getNameFromDB("Currency", "Name", rs.getInt("idCurrency"), dbh));
				country.setLanguage(getNameFromDB("Language", "Name", rs.getInt("idLanguage"), dbh));
				countries_map_bck.put(countryName, country);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void deleteAllYagoData(DatabaseHandler dbh,String dbname){
		String tablesQuery = "SHOW TABLES FROM "+dbname+";";
		try {
			ResultSet rs = dbh.executeQuery(tablesQuery);
			while(rs.next()){
				String tableName = rs.getString(1);
				String delete = "DELETE FROM "+tableName+";";
				int deleted = dbh.executeUpdate(delete);
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
