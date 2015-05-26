import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.entity_country;
import entities.entity_currency;
import entities.entity_person;
import Database.ConnectionPool;
import Database.DatabaseHandler;
import Database.Yago.parser_transitive_types;
import Database.Yago.parser_yago_date_facts;
import Database.Yago.parser_yago_facts;
import Database.Yago.parser_yago_literal_facts;
import Database.Yago.Uploaders.CountriesUploader;



public class Main {
	private static ConnectionPool mConnPool;

	public static final int BATCHSIZE=5000;
	private static List<Object[]> batch;

	private static final String YAGO_TRANSITIVETYPE = "D:\\Temp\\yagoTransitiveType.tsv";
	private static final String YAGO_FACTS = "D:\\Temp\\yagoFacts.tsv";
	private static final String YAGO_LITERALFACTS = "D:\\Temp\\yagoLiteralFacts.tsv";
	private static final String YAGO_DATEFACTS = "D:\\Temp\\yagoDateFacts.tsv";

	public static void main(String args[]) {
		//Init connection pool
		mConnPool = new ConnectionPool();

		DatabaseHandler dbh = new DatabaseHandler(mConnPool);


		HashMap<String, entity_country> countries_map = new HashMap<String, entity_country>();
		HashMap<String, entity_person> persons_map = new HashMap<String, entity_person>();
		HashSet<String> currency_set = new HashSet<String>();
		HashSet<String> language_set = new HashSet<String>();
		HashSet<String> cities_set = new HashSet<String>();
		HashMap<String,Set<String>> countries_cities_map = new HashMap<String,Set<String>>(); 

		long start = System.currentTimeMillis();
		
		try {			
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Europe"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Asia"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Africa"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Australia"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"North America"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"South America"});
			
			
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Musician"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Scientist"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Politician"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Athlete"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Actor"});
		} catch (SQLException e) {

		}
		
		
		try{
			System.out.println("parsing transitive types " + (System.currentTimeMillis()-start)/1000f);
			parser_transitive_types c = new parser_transitive_types(YAGO_TRANSITIVETYPE, countries_map,
					persons_map, cities_set, currency_set, language_set, countries_cities_map);		
			c.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
			System.out.println("parsing facts " + (System.currentTimeMillis()-start)/1000f);
			parser_yago_facts d = new parser_yago_facts(YAGO_FACTS,  countries_map,  countries_cities_map, cities_set);
			d.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
			System.out.println("parsing literal facts " + (System.currentTimeMillis()-start)/1000f);
			parser_yago_literal_facts g = new parser_yago_literal_facts(YAGO_LITERALFACTS, countries_map);
			g.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
			System.out.println("parsing date facts " + (System.currentTimeMillis()-start)/1000f);
			parser_yago_date_facts f = new parser_yago_date_facts(YAGO_DATEFACTS, persons_map);
			f.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("inserting currency " + (System.currentTimeMillis()-start)/1000f);
		for (String curr : currency_set){
			try {
				//				dbh.executeUpdate("INSERT INTO Currency (Name) VALUES(\""+curr+"\");");
				dbh.singleInsert("Currency", new String[]{"Name"}, new String[]{curr});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//System.out.println(curr);
				//e.printStackTrace();
			}
		}

		System.out.println("inserting languages " + (System.currentTimeMillis()-start)/1000f);
		for (String lang : language_set){
			try {
				//				dbh.executeUpdate("INSERT INTO Language (Name) VALUES(\""+lang+"\");");
				dbh.singleInsert("Language", new String[]{"Name"}, new String[]{lang});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//				System.out.println(lang);
				//				e.printStackTrace();
			}
		}

		
		System.out.println("inserting persons " + (System.currentTimeMillis()-start)/1000f);
		int total = 0;
		batch = new ArrayList<Object[]>();
		for (String person : persons_map.keySet()){
			try {
				//				dbh.executeUpdate("INSERT INTO Country (Name, yearOfBirth, yearOfDeath) VALUES(\""+persons_map.get(person).getName()+"\",\""+
				//						persons_map.get(person).getYearOfBirth()+"\",\""+ 
				//						persons_map.get(person).getYearOfDeath()+"\");");
				//				dbh.singleInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"},
				//						new String[]{persons_map.get(person).getName(),""+persons_map.get(person).getYearOfBirth(),""+persons_map.get(person).getYearOfDeath()});
				if(batch.size()>=BATCHSIZE) {
					System.out.println("clearing batch! total : " + total);
					total+=BATCHSIZE;
					dbh.batchInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"}, batch);
					batch = new ArrayList<Object[]>();
				}
				//System.out.println("Adding person: "+persons_map.get(person).getName()+" "+persons_map.get(person).getYearOfBirth()+" "+persons_map.get(person).getYearOfDeath());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			batch.add(new Object[]{persons_map.get(person).getName(),persons_map.get(person).getYearOfBirth(),persons_map.get(person).getYearOfDeath()});
		}
		try {
			dbh.batchInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"}, batch);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		System.out.println("inserting countries " + (System.currentTimeMillis()-start)/1000f);
		for (String country : countries_map.keySet()){
			try {
				//				dbh.executeUpdate("INSERT INTO Country (Name) VALUES(\""+countries_map.get(country).getYagoName()+"\");");
				dbh.singleInsert("Country", new String[]{"Name"}, new String[]{countries_map.get(country).getYagoName()});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//				System.out.println(countries_map.get(country).getYagoName());
				//				e.printStackTrace();
			}
		}
//		CountriesUploader cUploader = new CountriesUploader(countries_map, dbh);
//		cUploader.upload();
		
		int i =0;
		if (i == 0){
			System.out.println("yes");
		}
	}

}
