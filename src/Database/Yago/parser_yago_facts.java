package Database.Yago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.entity_country;
import parser_entities.Importer;
import parser_entities.entity_movie;
import parser_entities.entity_person;



public class parser_yago_facts extends AbstractYagoParser{
	
	private final int max_list_size = 1000;
	
	HashMap<String, entity_country> countries_map;
	
	public parser_yago_facts(String filepath, HashMap<String, entity_country> countries_map) {		
		super(filepath);
		
		countries_map = countries_map;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse(YagoEntry toParse) {
		// TODO Auto-generated method stub
		if (toParse.relation.equals("<isLocatedIn>")){
			if (countries_map.containsKey(toParse.lentity)){
				countries_map.get(toParse.lentity).setContinent(toParse.rentity);
			}
			else{
				entity_country new_country = new entity_country();
				new_country.setContinent(toParse.rentity);
				countries_map.put(toParse.lentity, new_country);
			}
		}		
	}
	

}
