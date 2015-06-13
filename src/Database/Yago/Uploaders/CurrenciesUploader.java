package Database.Yago.Uploaders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Database.DatabaseHandler;
import Database.Updates.Importer;
import entities.entity_country;

public class CurrenciesUploader extends AbstractUploader{
	HashSet<String> cset;

	String table = "Currency";
	String[] columns = {"Name"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public CurrenciesUploader(HashSet<String> currency_set, DatabaseHandler dbh) {
		super(dbh);
		this.cset=currency_set;
	}

	/**
	 * Upload all country entities to the database
	 */
	public void upload() {
		int c=0;
		List<Object[]> batch = new ArrayList<Object[]>();
		for(String curr : cset) {

			//Get relevant ids
			//ResultSet rs;
			Object[] values = new Object[columns.length];
			//try {
				//Name
				values[0] = curr;

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
			Importer.finished++;
			batch.add(values);
		}
		if(batch.size()>0) //empty what's left
			insertBatch(batch, table, columns);

	}
}
