package PlanetCrash.db.Yago.Uploaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import PlanetCrash.db.DatabaseHandler;
import PlanetCrash.db.Updates.Importer;
import PlanetCrash.parsing.entities.entity_country;

public class LanguagesUploader extends AbstractUploader{
	HashSet<String> lset;

	String table = "Language";
	String[] columns = {"Name"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public LanguagesUploader(HashSet<String> language_set, DatabaseHandler dbh) {
		super(dbh);
		this.lset=language_set;
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		int c=0;
		List<Object[]> batch = new ArrayList<Object[]>();
		for(String lang : lset) {

			//Get relevant ids
			//ResultSet rs;
			Object[] values = new Object[columns.length];
			//try {
				//Name
				values[0] = lang;

			//} catch (SQLException e) {
				// TODO Auto-generated catch block
//				System.out.println("Error initializing country: "+lang);
//				e.printStackTrace();
//				continue;
			//}

			if(batch.size()>=BATCHSIZE) {
				insertBatch(batch, table, columns);
				c+=BATCHSIZE;
				//System.out.println("total "+c);
				batch = new ArrayList<Object[]>();
			}
			Importer.uploading_finished++;
			batch.add(values);
		}
		if(batch.size()>0) //empty what's left
			insertBatch(batch, table, columns);

	}
}