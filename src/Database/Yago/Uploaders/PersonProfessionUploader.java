package Database.Yago.Uploaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.xml.internal.ws.client.sei.ValueSetter;

import Database.DatabaseHandler;
import entities.entity_country;
import entities.entity_person;

public class PersonProfessionUploader extends AbstractUploader{
	HashMap<String, entity_person> pmap;

	String table = "Person_Profession";
	String[] columns = {"idPerson","idProfession"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public PersonProfessionUploader(HashMap<String, entity_person> persons_map, DatabaseHandler dbh) {
		super(dbh);
		this.pmap=persons_map;
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		//Collection<Set<String>> countries = ccmap.keySet();
		
	    Iterator it = pmap.entrySet().iterator();
	    List<Object[]> batch = new ArrayList<Object[]>();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        String person = (String)pair.getKey();
	        entity_person person_details = (entity_person)pair.getValue();
	        
	        ResultSet rs;
	        int person_id=0;
	        	        
	        try{
				//idCountry
				rs=dbh.executeFormatQuery("Person", new String[]{"idPerson"}, "WHERE Name = \""+person+"\"");
				if(rs.first())
					person_id = rs.getInt(1);
	        }catch(SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error initializing country: "+person);
				e.printStackTrace();
				continue;        	
	        }
	        
	        if(person_id==0){
	        	continue;
	        }
	        
	        for (String profession : person_details.getProfession_list()){
	        	
	        	Object[] values = new Object[columns.length];
	        	values[0] = person_id;
				//Get relevant ids
				ResultSet rs2;
				
				try {
					//idProfession
					rs2=dbh.executeFormatQuery("Profession", new String[]{"idProfession"}, "WHERE Name =\""+profession+"\"");
					if(rs2.first())
						values[1]=rs2.getInt(1);


				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error initializing profession: "+profession);
					e.printStackTrace();
					continue;
				}

				if(batch.size()>=BATCHSIZE) {
					insertBatch(batch, table, columns);
					batch = new ArrayList<Object[]>();
				}
				batch.add(values);
			}	        
	    }
		if(batch.size()>0) //empty what's left
			insertBatch(batch, table, columns);

	}
}
