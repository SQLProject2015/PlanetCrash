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

public class CountriesUploader implements Uploader{
	Map<String, entity_country> cmap;
	DatabaseHandler dbh;
	
	String[] columns = {"Name","idContinent","idCurrency","idLanguage","idCapital","PopulationSize","currentLeader"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public CountriesUploader(Map<String, entity_country> countries_map, DatabaseHandler dbh) {
		this.cmap=countries_map;
		this.dbh=dbh;
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		Collection<entity_country> countries = cmap.values();
		
		List<Object[]> batch = new ArrayList<Object[]>();
		for(entity_country country : countries) {
			
			//Get relevant ids
			ResultSet rs;
			Object[] values = new Object[columns.length];
			try {
				//Name
				values[0] = country.getYagoName();
				
				//idContinent
				rs=dbh.executeFormatQuery("Continent", new String[]{"idContinent"}, "WHERE Name = \""+country.getContinent()+"\"");
				rs.first();
				values[1] = rs.getInt(0);
				
				//idCurrency
				rs=dbh.executeFormatQuery("Currency", new String[]{"idCurrency"}, "WHERE Name =\""+country.getCurrency()+"\"");
				rs.first();
				values[2]=rs.getInt(0);
				
				//idLanguage
				rs=dbh.executeFormatQuery("Language", new String[]{"idLanguage"}, "WHERE Name =\""+country.getLanguage()+"\"");
				rs.first();
				values[3]=rs.getInt(0);
				
				//idCapital
				rs=dbh.executeFormatQuery("City", new String[]{"idCity"}, "WHERE Name =\""+country.getCapital()+"\"");
				rs.first();
				values[4]=rs.getInt(0);
				
				//PopulationSize
				values[5]=country.getPopulation_size();
				
				//currentLeader
				rs=dbh.executeFormatQuery("Person", new String[]{"idPerson"}, "WHERE Name =\""+country.getLeader()+"\"");
				rs.first();
				values[6]=rs.getInt(0);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error initializing country: "+country.getYagoName());
				//e.printStackTrace();
				continue;
			}
			
			if(batch.size()>=BATCHSIZE) {
				//clear the batch
				try {
					dbh.batchInsert("Country", columns, batch);
					batch = new ArrayList<Object[]>();
				} catch (SQLException e) {
					for(Object[] arr : batch) {
						try {
							dbh.executeUpdate("INSERT INTO Person (Name, yearOfBirth, yearOfDeath) VALUES(\""
									+arr[0]+"\",\""+arr[1]+"\",\""+arr[2]+"\");");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.println("Failed: "+arr[0]+","+arr[1]+","+arr[2]);
							e1.printStackTrace();
						}
					}
					batch = new ArrayList<Object[]>();
				}
			}
			batch.add(values);
		}
		
	}
}
