import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import config.config;
import Database.DatabaseHandler;
import Database.Yago.parser_transitive_types;
import Database.Yago.parser_yago_date_facts;
import Database.Yago.parser_yago_facts;
import Database.Yago.parser_yago_literal_facts;
import Database.Yago.Uploaders.AwardWinnersUploader;
import Database.Yago.Uploaders.CapitalsUploader;
import Database.Yago.Uploaders.CitiesUploader;
import Database.Yago.Uploaders.CountriesUploader;
import Database.Yago.Uploaders.CurrenciesUploader;
import Database.Yago.Uploaders.LanguagesUploader;
import Database.Yago.Uploaders.PersonProfessionUploader;
import Database.Yago.Uploaders.PersonsUploader;
import Database.Yago.Uploaders.UniversitiesUploader;
import entities.entity_city;
import entities.entity_country;
import entities.entity_person;
import entities.entity_university;


public class Init {
	
	

	public static final int BATCHSIZE=1000;
	//private static List<Object[]> batch;
	static config conf = new config();
	
	public Init(DatabaseHandler dbh, config properties){		
		
		HashMap<String, entity_country> countries_map = new HashMap<String, entity_country>();
		HashMap<String, entity_city> cities_map = new HashMap<String, entity_city>();
		HashMap<String, entity_person> persons_map = new HashMap<String, entity_person>();
		HashSet<String> currency_set = new HashSet<String>();
		HashSet<String> language_set = new HashSet<String>();
		HashMap<String,Set<String>> countries_cities_map = new HashMap<String,Set<String>>();
		HashMap<String, entity_university> universities_map = new HashMap<String,entity_university>();
		HashMap<String, entity_person> lite_persons_map = new HashMap<String, entity_person>();
		HashSet<String> awards_set = new HashSet<String>();

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
			parser_yago_facts d = new parser_yago_facts(properties.get_yago_facts_path(),  countries_map,  countries_cities_map, cities_map,universities_map,persons_map, lite_persons_map, awards_set);
			d.populate();
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
		
		ResultSet rs;
		try{
			rs = dbh.executeQuery(String.format("SELECT Name FROM %s.Award;",conf.get_db_name()));
			while (rs.next()) {	        
	            String Name = rs.getString("Name");
	            awards_set.add(Name);
	        }
		}catch(SQLException e){
			System.out.println("Error");
		}

	
		//CURRENCY
		System.out.println("inserting currency " + (System.currentTimeMillis()-start)/1000f);
		CurrenciesUploader currencyUploader = new CurrenciesUploader(currency_set, dbh);
		currencyUploader.upload();
				
		//LANGUAGE
		System.out.println("inserting languags " + (System.currentTimeMillis()-start)/1000f);
		LanguagesUploader languageUploader = new LanguagesUploader(language_set, dbh);
		languageUploader.upload();
				
		//COUNTRIES
		System.out.println("inserting countries " + (System.currentTimeMillis()-start)/1000f);
		CountriesUploader countriesUploader = new CountriesUploader(countries_map, dbh);
		countriesUploader.upload();
			
		//CITIES
		System.out.println("inserting cities " + (System.currentTimeMillis()-start)/1000f);
		CitiesUploader citiesUploader = new CitiesUploader(cities_map, dbh);
		citiesUploader.upload();

		//CAPITALS
		System.out.println("inserting capitals " + (System.currentTimeMillis()-start)/1000f);
		CapitalsUploader capitalsUploader = new CapitalsUploader(countries_map, dbh);
		capitalsUploader.upload();

		//PERSONS
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

		//UNIVERSITIES
		System.out.println("inserting universities  " + (System.currentTimeMillis()-start)/1000f);
		UniversitiesUploader uniUploader = new UniversitiesUploader(universities_map, dbh);
		uniUploader.upload();		

		int i =0;
		if (i == 0){
			System.out.println("yes " + (System.currentTimeMillis()-start)/1000f);
		}
	}
}
