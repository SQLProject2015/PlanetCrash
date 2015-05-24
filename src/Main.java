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



public class Main {
	private static ConnectionPool mConnPool;
	
	public static final int BATCHSIZE=50000;
	private static List<String[]> batch;
	
	public static void main(String args[]) throws FileNotFoundException{
		//Init connection pool
		mConnPool = new ConnectionPool();
		
		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
		

		HashMap<String, entity_country> countries_map = new HashMap<String, entity_country>();
		HashMap<String, entity_person> persons_map = new HashMap<String, entity_person>();
		HashSet<String> currency_set = new HashSet<String>();
		HashSet<String> language_set = new HashSet<String>();
		HashSet<String> cities_set = new HashSet<String>();
		HashMap<String,Set<String>> countries_cities_map = new HashMap<String,Set<String>>(); 
		
		
		parser_transitive_types c = new parser_transitive_types("D:\\db project\\yago3_tsv\\yagoTransitiveType.tsv", countries_map,
				persons_map, cities_set, currency_set, language_set, countries_cities_map);		
		c.populate();
		
		parser_yago_facts d = new parser_yago_facts("D:\\db project\\yago3_tsv\\yagoFacts.tsv",  countries_map,  countries_cities_map, cities_set);
		d.populate();
						
		
		parser_yago_literal_facts g = new parser_yago_literal_facts("D:\\db project\\yago3_tsv\\yagoLiteralFacts.tsv", countries_map);
		g.populate();
		
		parser_yago_date_facts f = new parser_yago_date_facts("D:\\db project\\yago3_tsv\\yagoDateFacts.tsv", persons_map);
		f.populate();
						
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
		
		batch = new ArrayList<String[]>();
		for (String person : persons_map.keySet()){
			try {
//				dbh.executeUpdate("INSERT INTO Country (Name, yearOfBirth, yearOfDeath) VALUES(\""+persons_map.get(person).getName()+"\",\""+
//						persons_map.get(person).getYearOfBirth()+"\",\""+ 
//						persons_map.get(person).getYearOfDeath()+"\");");
//				dbh.singleInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"},
//						new String[]{persons_map.get(person).getName(),""+persons_map.get(person).getYearOfBirth(),""+persons_map.get(person).getYearOfDeath()});
				if(batch.size()>=BATCHSIZE) {
					System.out.println("clearing batch!");
					dbh.batchInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"}, batch);
					batch.removeAll(batch);
				}
				batch.add(new String[]{persons_map.get(person).getName(),""+persons_map.get(person).getYearOfBirth(),""+persons_map.get(person).getYearOfDeath()});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			dbh.batchInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"}, batch);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
				
		int i =0;
		if (i == 0){
			System.out.println("yes");
		}
	}
	
}
