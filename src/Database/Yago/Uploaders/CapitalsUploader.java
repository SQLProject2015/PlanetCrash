package Database.Yago.Uploaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Database.DatabaseHandler;
import entities.entity_country;

public class CapitalsUploader extends AbstractUploader{
	Map<String, entity_country> cmap;

	String table = "Country";
	String[] columns = {"Name","idContinent","idCurrency","idLanguage","idCapital","PopulationSize","currentLeader"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public CapitalsUploader(Map<String, entity_country> countries_map, DatabaseHandler dbh) {
		super(dbh);
		this.cmap=countries_map;
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		Collection<entity_country> countries = cmap.values();

		for(entity_country country : countries) {

			//Get relevant ids
			ResultSet rs;
			int idCity, idCountry;
			try {
				//idContinent
				rs=dbh.executeFormatQuery("City", new String[]{"idCity"}, "WHERE Name = \""+country.getCapital()+"\"");
				if(rs.first())
					idCity = rs.getInt(1);
				else{
					continue;
				}
				
				rs=dbh.executeFormatQuery("Country", new String[]{"idCountry"}, "WHERE Name = \""+country.getYagoName()+"\"");
				if(rs.first())
					idCountry = rs.getInt(1);
				else{
					continue;
				}
				
				String query = String.format("UPDATE `DbMysql14`.`Country` SET `idCapital`='%d' WHERE `idCountry`='%d'", idCity,idCountry);
				dbh.executeQuery(query);

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
