import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.config;
import entities.entity_city;
import entities.entity_country;
import entities.entity_currency;
import entities.entity_person;
import entities.entity_university;
import Database.ConnectionPool;
import Database.DatabaseHandler;
import Database.Yago.parser_transitive_types;
import Database.Yago.parser_yago_date_facts;
import Database.Yago.parser_yago_facts;
import Database.Yago.parser_yago_facts2;
import Database.Yago.parser_yago_literal_facts;
import Database.Yago.Uploaders.AwardWinnersUploader;
import Database.Yago.Uploaders.CitiesUploader;
import Database.Yago.Uploaders.CountriesCitiesUploader;
import Database.Yago.Uploaders.CountriesUploader;
import Database.Yago.Uploaders.CurrenciesUploader;
import Database.Yago.Uploaders.LanguagesUploader;
import Database.Yago.Uploaders.PersonProfessionUploader;
import Database.Yago.Uploaders.PersonsUploader;
import Database.Yago.Uploaders.UniversitiesUploader;
import GUI.GameGUI;



public class Main {
	private static ConnectionPool mConnPool;

	public static final int BATCHSIZE=1000;
	private static List<Object[]> batch;


	public static void main(String args[]) {
		
		//Game GUI
//				GameGUI gg = new GameGUI();
//				gg.start();
		
		String username;
		System.out.println("Enter username");
		
		//Init connection pool
		mConnPool = new ConnectionPool();

		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
		config properties = new config();
		
		try{
			UserHandler.add_new_user("osher", "1234", dbh);
		}catch(Exception ex){
		}

		HashMap<String, entity_country> countries_map = new HashMap<String, entity_country>();
		HashMap<String, entity_city> cities_map = new HashMap<String, entity_city>();
		HashMap<String, entity_person> persons_map = new HashMap<String, entity_person>();
		HashSet<String> currency_set = new HashSet<String>();
		HashSet<String> language_set = new HashSet<String>();
		HashMap<String,Set<String>> countries_cities_map = new HashMap<String,Set<String>>();
		HashMap<String, entity_university> universities_map = new HashMap<String,entity_university>();
		HashMap<String, entity_person> lite_persons_map = new HashMap<String, entity_person>();
		HashSet<String> lite_city_set = new HashSet<String>();
		
		long start = System.currentTimeMillis();
		
		try {			
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Europe"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Asia"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Africa"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Australia"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"North America"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"South America"});
			dbh.singleInsert("Continent", new String[]{"Name"}, new String[]{"Oceania"});
			
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Musician"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Scientist"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Politician"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Athlete"});
			dbh.singleInsert("Profession", new String[]{"Name"}, new String[]{"Actor"});
			
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"Grammy Award"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"Grammy Lifetime Achievement Award"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"Academy Award for Best Actor"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"Academy Award for Best Actress"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"Nobel Prize in Physics"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"Nobel Prize in Chemistry"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"Nobel Peace Prize"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"FIFA World Player of the Year"});
			dbh.singleInsert("Award", new String[]{"Name"}, new String[]{"World Music Awards"});
			
		} catch (SQLException e) {

		}
		
		
		try{
			System.out.println("parsing transitive types " + (System.currentTimeMillis()-start)/1000f);
			parser_transitive_types c = new parser_transitive_types(properties.get_yago_transitive_types_path(), countries_map,
					persons_map, cities_map, currency_set, language_set, countries_cities_map, universities_map);		
			c.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
			System.out.println("parsing facts " + (System.currentTimeMillis()-start)/1000f);
			parser_yago_facts d = new parser_yago_facts(properties.get_yago_facts_path(),  countries_map,  countries_cities_map, cities_map,universities_map,persons_map, lite_persons_map);
			d.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
			System.out.println("parsing facts second time " + (System.currentTimeMillis()-start)/1000f);
			parser_yago_facts2 h = new parser_yago_facts2(properties.get_yago_facts_path(),  countries_map,  countries_cities_map, cities_map,universities_map,persons_map, lite_persons_map, lite_city_set);
			h.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
			System.out.println("parsing literal facts " + (System.currentTimeMillis()-start)/1000f);
			parser_yago_literal_facts g = new parser_yago_literal_facts(properties.get_yago_literal_facts_path(), countries_map);
			g.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
			System.out.println("parsing date facts " + (System.currentTimeMillis()-start)/1000f);
			parser_yago_date_facts f = new parser_yago_date_facts(properties.get_yago_date_facts_path(), persons_map);
			f.populate();
			System.out.println("done " + (System.currentTimeMillis()-start)/1000f);
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
//// 		
//// 	
		/// CITIES
// 		System.out.println("inserting cities " + (System.currentTimeMillis()-start)/1000f);
// 		int total = 0;
// 		batch = new ArrayList<Object[]>();
// 		for (String city : cities_set){
// 			try {
// 				//				dbh.executeUpdate("INSERT INTO Country (Name, yearOfBirth, yearOfDeath) VALUES(\""+persons_map.get(person).getName()+"\",\""+
// 				//						persons_map.get(person).getYearOfBirth()+"\",\""+ 
// 				//						persons_map.get(person).getYearOfDeath()+"\");");
// 				//				dbh.singleInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"},
// 				//						new String[]{persons_map.get(person).getName(),""+persons_map.get(person).getYearOfBirth(),""+persons_map.get(person).getYearOfDeath()});
// 				if(batch.size()>=BATCHSIZE) {
// 					System.out.println("clearing batch! total : " + total);
// 					total+=BATCHSIZE;
// 					dbh.batchInsert("City", new String[]{"Name"}, batch);
// 					batch = new ArrayList<Object[]>();
// 				}
// 				//System.out.println("Adding person: "+persons_map.get(person).getName()+" "+persons_map.get(person).getYearOfBirth()+" "+persons_map.get(person).getYearOfDeath());
// 				
// 			} catch (SQLException e) {
// 				// TODO Auto-generated catch block
// 				e.printStackTrace();
// 				for(Object[] arr : batch) {
// 					try {
// 						dbh.executeUpdate("INSERT INTO City (Name) VALUES(\""
// 								+arr[0]+"\",\""+arr[1]+"\",\""+arr[2]+"\");");
// 					} catch (SQLException e1) {
// 						// TODO Auto-generated catch block
// 						System.out.println("Failed: "+arr[0]+","+arr[1]+","+arr[2]);
// 						e1.printStackTrace();
// 					}
// 				}
// 				batch = new ArrayList<Object[]>();
// 			}
// 			batch.add(new Object[]{city});
// 		}
// 		try {
// 			dbh.batchInsert("City", new String[]{"Name"}, batch);
// 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			//e.printStackTrace();
// 		}
//		
////		System.out.println("inserting persons " + (System.currentTimeMillis()-start)/1000f);
////		for (entity_person person : persons_map.values()){
////			try {
////				//				dbh.executeUpdate("INSERT INTO Language (Name) VALUES(\""+lang+"\");");
////				dbh.singleInsert("Person", new String[]{"Name"}, new String[]{city});
////			} catch (SQLException e) {
////				// TODO Auto-generated catch block
////				//				System.out.println(lang);
////				//				e.printStackTrace();
////			}
////		}

		
//		System.out.println("inserting persons " + (System.currentTimeMillis()-start)/1000f);
//		int total = 0;
//		batch = new ArrayList<Object[]>();
//		for (String person : lite_persons_map.keySet()){
//			try {
//				//				dbh.executeUpdate("INSERT INTO Country (Name, yearOfBirth, yearOfDeath) VALUES(\""+persons_map.get(person).getName()+"\",\""+
//				//						persons_map.get(person).getYearOfBirth()+"\",\""+ 
//				//						persons_map.get(person).getYearOfDeath()+"\");");
//				//				dbh.singleInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"},
//				//						new String[]{persons_map.get(person).getName(),""+persons_map.get(person).getYearOfBirth(),""+persons_map.get(person).getYearOfDeath()});
//				if(batch.size()>=BATCHSIZE) {
//					System.out.println("clearing batch! total : " + total);
//					total+=BATCHSIZE;
//					dbh.batchInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"}, batch);
//					batch = new ArrayList<Object[]>();
//				}
//				//System.out.println("Adding person: "+persons_map.get(person).getName()+" "+persons_map.get(person).getYearOfBirth()+" "+persons_map.get(person).getYearOfDeath());
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				for(Object[] arr : batch) {
//					try {
//						dbh.executeUpdate("INSERT INTO Person (Name, yearOfBirth, yearOfDeath) VALUES(\""
//								+arr[0]+"\",\""+arr[1]+"\",\""+arr[2]+"\");");
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						System.out.println("Failed: "+arr[0]+","+arr[1]+","+arr[2]);
//						e1.printStackTrace();
//					}
//				}
//				batch = new ArrayList<Object[]>();
//			}
//			batch.add(new Object[]{lite_persons_map.get(person).getName(),lite_persons_map.get(person).getYearOfBirth(),lite_persons_map.get(person).getYearOfDeath()});
//		}
//		try {
//			dbh.batchInsert("Person", new String[]{"Name","yearOfBirth","yearOfDeath"}, batch);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		}

		
//		for (String country : countries_map.keySet()){
//			try {
//				//				dbh.executeUpdate("INSERT INTO Country (Name) VALUES(\""+countries_map.get(country).getYagoName()+"\");");
//				dbh.singleInsert("Country", new String[]{"Name"}, new String[]{countries_map.get(country).getYagoName()});
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				//				System.out.println(countries_map.get(country).getYagoName());
//				//				e.printStackTrace();
//			}
//		}
		
// 		System.out.println("inserting currency " + (System.currentTimeMillis()-start)/1000f);
// 		for (String curr : currency_set){
// 			try {
// 				//				dbh.executeUpdate("INSERT INTO Currency (Name) VALUES(\""+curr+"\");");
// 				dbh.singleInsert("Currency", new String[]{"Name"}, new String[]{curr});
// 			} catch (SQLException e) {
// 				// TODO Auto-generated catch block
// 				//System.out.println(curr);
// 				//e.printStackTrace();
// 			}
// 		}
//// 
// 		System.out.println("inserting languages " + (System.currentTimeMillis()-start)/1000f);
// 		for (String lang : language_set){
// 			try {
// 				//				dbh.executeUpdate("INSERT INTO Language (Name) VALUES(\""+lang+"\");");
// 				dbh.singleInsert("Language", new String[]{"Name"}, new String[]{lang});
// 			} catch (SQLException e) {
// 				// TODO Auto-generated catch block
// 				//				System.out.println(lang);
// 				//				e.printStackTrace();
// 			}
// 		}
		
		//		
//		
//		//Currency
		System.out.println("inserting currency " + (System.currentTimeMillis()-start)/1000f);
		CurrenciesUploader currencyUploader = new CurrenciesUploader(currency_set, dbh);
		currencyUploader.upload();
//		
////		//Language
		System.out.println("inserting languags " + (System.currentTimeMillis()-start)/1000f);
		LanguagesUploader languageUploader = new LanguagesUploader(language_set, dbh);
		languageUploader.upload();

		
		//COUNTRIES
		System.out.println("inserting countries " + (System.currentTimeMillis()-start)/1000f);
		CountriesUploader cUploader = new CountriesUploader(countries_map, dbh);
		cUploader.upload();
		
//		//CITIES
		System.out.println("inserting cities " + (System.currentTimeMillis()-start)/1000f);
		CitiesUploader citiesUploader = new CitiesUploader(cities_map, dbh);
		citiesUploader.upload();
		
//		//PERSONS
		System.out.println("inserting persons " + (System.currentTimeMillis()-start)/1000f);
		PersonsUploader pUploader = new PersonsUploader(lite_persons_map, dbh);
		pUploader.upload();
	
		//PERSONS_PROFESSION
		System.out.println("inserting person_profession " + (System.currentTimeMillis()-start)/1000f);
		PersonProfessionUploader ppUploader = new PersonProfessionUploader(lite_persons_map, dbh);
		ppUploader.upload();
		
		//AWARD_WINNERS
		System.out.println("inserting award winners " + (System.currentTimeMillis()-start)/1000f);
		AwardWinnersUploader awUploader = new AwardWinnersUploader(lite_persons_map, dbh);
		awUploader.upload();
		
		//UNIVERSIIES
		System.out.println("inserting universities  " + (System.currentTimeMillis()-start)/1000f);
		UniversitiesUploader uniUploader = new UniversitiesUploader(universities_map, dbh);
		uniUploader.upload();		
		
		int i =0;
		if (i == 0){
			System.out.println("yes " + (System.currentTimeMillis()-start)/1000f);
		}
	}

}
