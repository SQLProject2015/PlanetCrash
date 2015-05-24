import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.entity_country;
import entities.entity_currency;
import entities.entity_person;
import Database.ConnectionPool;
import Database.DatabaseHandler;
import Database.Yago.parser_transitive_types;
import Database.Yago.parser_yago_facts;
import Database.Yago.parser_yago_literal_facts;



public class Main {
	private static ConnectionPool mConnPool;
	
	public static void main(String args[]) throws FileNotFoundException{
		//Init connection pool
		mConnPool = new ConnectionPool();
		
		DatabaseHandler dbh = new DatabaseHandler(mConnPool);
		

		HashMap<String, entity_country> countries_map = new HashMap<String, entity_country>();
		HashMap<String, entity_person> persons_map = new HashMap<String, entity_person>();
		ArrayList<String> currency_array = new ArrayList<String>();
		ArrayList<String> language_array = new ArrayList<String>();
		
		parser_transitive_types c = new parser_transitive_types("D:\\db project\\yago3_tsv\\yagoTransitiveType.tsv", countries_map, persons_map, currency_array, language_array);		
		c.populate();
		
		parser_yago_facts d = new parser_yago_facts("D:\\db project\\yago3_tsv\\yagoFacts.tsv", countries_map);
		d.populate();
						
		for (String curr : currency_array){
			try {
				dbh.executeUpdate("INSERT INTO Currency (Name) VALUES(\""+curr+"\");");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		for (String lang : language_array){
			try {
				dbh.executeUpdate("INSERT INTO Currency (Name) VALUES(\""+lang+"\");");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		for (String country : countries_map.keySet()){
			try {
				dbh.executeUpdate("INSERT INTO Country (Name) VALUES(\""+countries_map.get(country).getYagoName()+"\");");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		for (String person : persons_map.keySet()){
			try {
				dbh.executeUpdate("INSERT INTO Country (Name, yearOfBirth, yearOfDeath) VALUES(\""+persons_map.get(person).getName()+"\",\""+
						persons_map.get(person).getYearOfBirth()+"\",\""+ 
						persons_map.get(person).getYearOfDeath()+"\");");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		parser_yago_literal_facts e = new parser_yago_literal_facts("D:\\db project\\yago3_tsv\\yagoLiteralFacts.tsv", countries_map);
		e.populate();
		
		int i =0;
		if (i == 0){
			System.out.println("yes");
		}
	}
	
}
