import java.io.FileNotFoundException;
import java.util.HashMap;

import entities.entity_country;
import entities.entity_person;
import Database.Yago.parser_transitive_types;
import Database.Yago.parser_yago_facts;



public class Main {
	public static void main(String args[]) throws FileNotFoundException{
		
		HashMap<String, entity_country> countries_map = new HashMap<String, entity_country>();
		HashMap<String, entity_person> persons_map = new HashMap<String, entity_person>();
		
		parser_transitive_types c = new parser_transitive_types("D:\\db project\\yago3_tsv\\yagoTransitiveType.tsv", countries_map, persons_map);		
		c.populate();
		
		parser_yago_facts d = new parser_yago_facts("D:\\db project\\yago3_tsv\\yagoFacts.tsv", countries_map);
		d.populate();
		
		int i =0;
		if (i == 0){
			System.out.println("yes");
		}
	}
	
}
