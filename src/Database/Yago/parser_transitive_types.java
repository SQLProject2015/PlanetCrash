package Database.Yago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.entity_country;
import entities.entity_person;



public class parser_transitive_types extends AbstractYagoParser{
	
	private final int max_list_size = 1000;
	
	HashMap<String, entity_country> countries_map;
	HashMap<String, entity_person> persons_map;

	public parser_transitive_types(String filepath,  HashMap<String, entity_country> countries_map,
			HashMap<String,entity_person> persons_map) {
		super(filepath);
		
		this.countries_map = countries_map;
		this.persons_map = persons_map;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse(YagoEntry toParse) {
		// TODO Auto-generated method stub
		if (toParse.rentity.equals("<wikicat_Countries>")){
			entity_country new_country = new entity_country();
			new_country.setYagoName(toParse.lentity);
			countries_map.put(toParse.lentity, new_country);
			//System.out.println(toParse.lentity);
		}		
		else if (toParse.rentity.equals("<wordnet_musician_110339966>") || toParse.rentity.equals("<wordnet_scientist_110560637>") ||
				toParse.rentity.equals("<wordnet_politician_110450303>") || toParse.rentity.equals("<wordnet_actor_109765278>") ||
				toParse.rentity.equals("<wordnet_athlete_109820263>")){
			if (persons_map.containsKey(toParse.lentity)){
				persons_map.get(toParse.lentity).addProfession(toParse.rentity);
			}
			else{
				entity_person new_person = new entity_person();
				new_person.setName(toParse.lentity);
				new_person.addProfession(toParse.rentity);
				persons_map.put(toParse.lentity, new_person);					
			}
			//System.out.println(toParse.lentity);
	
		}
	}
	

}
