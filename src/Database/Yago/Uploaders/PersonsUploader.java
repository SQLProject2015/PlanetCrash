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
import entities.entity_country;
import entities.entity_person;

public class PersonsUploader extends AbstractUploader{
	Map<String, entity_person> pmap;
	config conf = new config();

	String table = "Person";
	String[] columns = {"Name","yearOfBirth", "yearOfDeath","idPlaceOfBirth"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public PersonsUploader(HashMap<String, entity_person> lite_persons_map, DatabaseHandler dbh) {
		super(dbh);
		this.pmap=lite_persons_map;
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		Collection<entity_person> persons = pmap.values();
		
		ResultSet city_rs;
		HashMap<String, Integer> city_id_name_map = new HashMap<String, Integer>();

		try{
			city_rs = dbh.executeQuery(String.format("SELECT idCity, Name FROM %s.City;",conf.get_db_name()));
			while (city_rs.next()) {	        
	            int idCity = city_rs.getInt("idCity");
	            String Name = city_rs.getString("Name");
	            city_id_name_map.put(Name, idCity);
	        }
		}catch(SQLException e){
			System.out.println("Error");
		}
		

		List<Object[]> batch = new ArrayList<Object[]>();
		for(entity_person person : persons) {
			//Get relevant ids
			ResultSet rs;
			Object[] values = new Object[columns.length];
			//try {
				//Name
				values[0] = person.getName();
				values[1] = person.getYearOfBirth();
				values[2] = person.getYearOfDeath();

				//idContinent
				values[3] = city_id_name_map.get(person.getPlaceOfBirth());
//				rs=dbh.executeFormatQuery("City", new String[]{"idCity"}, "WHERE Name =\""+person.getPlaceOfBirth()+"\"");
//				if(rs.first())
//					values[3] = rs.getInt(1);


			//} catch (SQLException e) {
				// TODO Auto-generated catch block
//				System.out.println("Error initializing country: "+person.getName());
//				e.printStackTrace();
//				continue;
//			}

			if(batch.size()>=BATCHSIZE) {
				insertBatch(batch, table, columns);
				batch = new ArrayList<Object[]>();
			}
			batch.add(values);
		}
		if(batch.size()>0) //empty what's left
			insertBatch(batch, table, columns);

	}
}
