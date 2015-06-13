package Database.Yago.Uploaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import config.config;
import Database.DatabaseHandler;
import Database.Updates.Importer;
import entities.entity_country;

public class CapitalsUploader extends AbstractUploader{
	Map<String, entity_country> cmap;
	HashMap<String, Integer> country_id_name_map;
	HashMap<String, Integer> city_id_name_map;

	String table = "Country";
	String[] columns = {"Name","idContinent","idCurrency","idLanguage","idCapital","PopulationSize"};
	config conf = new config();
	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public CapitalsUploader(Map<String, entity_country> countries_map, HashMap<String, Integer> country_id_name_map, HashMap<String, Integer> city_id_name_map, DatabaseHandler dbh) {
		super(dbh);
		this.cmap=countries_map;
		this.country_id_name_map = country_id_name_map;
		this.city_id_name_map = city_id_name_map;
		
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		Collection<entity_country> countries = cmap.values();
		for(entity_country country : countries) {

			//Get relevant ids
			ResultSet rs;
			Integer idCity, idCountry;
			try {
				
				idCity = city_id_name_map.get(country.getCapital());
				idCountry = country_id_name_map.get(country.getYagoName());
				
//				//idContinent
//				rs=dbh.executeFormatQuery("City", new String[]{"idCity"}, "WHERE Name = \""+country.getCapital()+"\"");
//				if(rs.first())
//					idCity = rs.getInt(1);
//				else{
//					continue;
//				}
//				
//				rs=dbh.executeFormatQuery("Country", new String[]{"idCountry"}, "WHERE Name = \""+country.getYagoName()+"\"");
//				if(rs.first())
//					idCountry = rs.getInt(1);
//				else{
//					continue;
//				}
				Importer.finished++;
				String query = String.format("UPDATE %s.Country SET idCapital=%d WHERE idCountry=%d", conf.get_db_name(),idCity,idCountry);
				dbh.executeUpdate(query);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error initializing country: "+country.getYagoName());
				e.printStackTrace();
				continue;
			}

//			if(batch.size()>=BATCHSIZE) {
//				insertBatch(batch, table, columns);
//			}
//			batch.add(values);
		}
//		if(batch.size()>0) //empty what's left
//			insertBatch(batch, table, columns);

	}
}
