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
import entities.entity_city;
import entities.entity_country;

public class CitiesUploader extends AbstractUploader{
	Map<String, entity_city> cmap;
	config conf = new config();
	String table = "City";
	String[] columns = {"Name","idCountry"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public CitiesUploader(Map<String, entity_city> cities_map, DatabaseHandler dbh) {
		super(dbh);
		this.cmap=cities_map;
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		int c=0;
		ResultSet country_rs;
		HashMap<String, Integer> country_id_name_map = new HashMap<String, Integer>();

		try{
			country_rs = dbh.executeQuery(String.format("SELECT idCountry, Name FROM %s.Country;",conf.get_db_name()));
			while (country_rs.next()) {	        
	            int idCountry = country_rs.getInt("idCountry");
	            String Name = country_rs.getString("Name");
	            country_id_name_map.put(Name, idCountry);
	        }
		}catch(SQLException e){
			System.out.println("Error");
		}

		Collection<entity_city> cities = cmap.values();
		List<Object[]> batch = new ArrayList<Object[]>();
		for(entity_city city : cities) {

			//Get relevant ids
			ResultSet rs;
			Object[] values = new Object[columns.length];
//			try {
				//Name
				values[0] = city.getName();

				//idCountry
//				rs=dbh.executeFormatQuery("Country", new String[]{"idCountry"}, "WHERE Name = \""+city.getCountry()+"\"");
//				if(rs.first())
//					values[1] = rs.getInt(1);
				values[1] = country_id_name_map.get(city.getCountry());

//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				System.out.println("Error initializing city: "+city.getName());
//				e.printStackTrace();
//				continue;
//			}

			if(batch.size()>=BATCHSIZE) {
				insertBatch(batch, table, columns);
				c+=BATCHSIZE;
				//System.out.println("total "+c);
				batch = new ArrayList<Object[]>();
			}
			batch.add(values);
		}
		if(batch.size()>0) //empty what's left
			insertBatch(batch, table, columns);

	}
}
