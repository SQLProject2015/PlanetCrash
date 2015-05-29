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

public class CountriesUploader extends AbstractUploader{
	Map<String, entity_country> cmap;

	String table = "Country";
	String[] columns = {"Name","idContinent","idCurrency","idLanguage","idCapital","PopulationSize","currentLeader"};

	/**
	 * Assumes all relevant data (cities, currencies etc.) is already in the database
	 * @param countries_map
	 */
	public CountriesUploader(Map<String, entity_country> countries_map, DatabaseHandler dbh) {
		super(dbh);
		this.cmap=countries_map;
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
				if(rs.first())
					values[1] = rs.getInt(1);

				//idCurrency
				rs=dbh.executeFormatQuery("Currency", new String[]{"idCurrency"}, "WHERE Name =\""+country.getCurrency()+"\"");
				if(rs.first())
					values[2]=rs.getInt(1);

				//idLanguage
				rs=dbh.executeFormatQuery("Language", new String[]{"idLanguage"}, "WHERE Name =\""+country.getLanguage()+"\"");
				if(rs.first())
					values[3]=rs.getInt(1);

				//idCapital
				rs=dbh.executeFormatQuery("City", new String[]{"idCity"}, "WHERE Name =\""+country.getCapital()+"\"");
				if(rs.first())
					values[4]=rs.getInt(1);

				//PopulationSize
				values[5]=country.getPopulation_size();

				//currentLeader
				rs=dbh.executeFormatQuery("Person", new String[]{"idPerson"}, "WHERE Name =\""+country.getLeader()+"\"");
				if(rs.first())
					values[6]=rs.getInt(1);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error initializing country: "+country.getYagoName());
				e.printStackTrace();
				continue;
			}

			if(batch.size()>=BATCHSIZE) {
				insertBatch(batch, table, columns);
			}
			batch.add(values);
		}
		if(batch.size()>0) //empty what's left
			insertBatch(batch, table, columns);

	}
}
